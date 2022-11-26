package org.glassfish.grizzly.http.io;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.ReadHandler;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpBrokenContent;
import org.glassfish.grizzly.http.HttpBrokenContentException;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpTrailer;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.MimeHeaders;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.threadpool.Threads;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.Exceptions;

public class InputBuffer {
   private static final Logger LOGGER = Grizzly.logger(InputBuffer.class);
   private static final Level LOGGER_LEVEL;
   private HttpHeader httpHeader;
   private FilterChainContext ctx;
   private boolean processingChars;
   private boolean closed;
   private Buffer inputContentBuffer;
   private Connection connection;
   private int markPos = -1;
   private int readAheadLimit = -1;
   private int readCount = 0;
   private String encoding;
   private CharsetDecoder decoder;
   private final Map decoders;
   private boolean contentRead;
   private ReadHandler handler;
   private int requestedSize;
   private final CharBuffer singleCharBuf;
   private float averageCharsPerByte;
   private boolean isWaitingDataAsynchronously;
   protected Map trailers;

   public InputBuffer() {
      this.encoding = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
      this.decoders = new HashMap();
      this.singleCharBuf = (CharBuffer)CharBuffer.allocate(1).position(1);
      this.averageCharsPerByte = 1.0F;
   }

   public void initialize(HttpHeader httpHeader, FilterChainContext ctx) {
      if (ctx == null) {
         throw new IllegalArgumentException("ctx cannot be null.");
      } else {
         this.httpHeader = httpHeader;
         this.ctx = ctx;
         this.connection = ctx.getConnection();
         Object message = ctx.getMessage();
         if (message instanceof HttpContent) {
            HttpContent content = (HttpContent)message;
            checkHttpTrailer(content);
            this.updateInputContentBuffer(content.getContent());
            this.contentRead = content.isLast();
            if (this.contentRead) {
               this.processTrailers();
            }

            content.recycle();
            if (LOGGER.isLoggable(LOGGER_LEVEL)) {
               log("InputBuffer %s initialize with ready content: %s", this, this.inputContentBuffer);
            }
         }

      }
   }

   public void setDefaultEncoding(String encoding) {
      this.encoding = encoding;
   }

   public void recycle() {
      this.inputContentBuffer.tryDispose();
      this.inputContentBuffer = null;
      this.singleCharBuf.position(this.singleCharBuf.limit());
      this.connection = null;
      this.decoder = null;
      this.ctx = null;
      this.handler = null;
      this.trailers = null;
      this.processingChars = false;
      this.closed = false;
      this.contentRead = false;
      this.markPos = -1;
      this.readAheadLimit = -1;
      this.requestedSize = -1;
      this.readCount = 0;
      this.averageCharsPerByte = 1.0F;
      this.isWaitingDataAsynchronously = false;
      this.encoding = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
   }

   public void processingChars() {
      if (!this.processingChars) {
         this.processingChars = true;
         String enc = this.httpHeader.getCharacterEncoding();
         if (enc != null) {
            this.encoding = enc;
            CharsetDecoder localDecoder = this.getDecoder();
            this.averageCharsPerByte = localDecoder.averageCharsPerByte();
         }
      }

   }

   public int readByte() throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s readByte. Ready content: %s", this, this.inputContentBuffer);
      }

      if (this.closed) {
         throw new IOException("Already closed");
      } else if (!this.inputContentBuffer.hasRemaining() && this.fill(1) == -1) {
         return -1;
      } else {
         this.checkMarkAfterRead(1L);
         return this.inputContentBuffer.get() & 255;
      }
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s read byte array of len: %s. Ready content: %s", this, len, this.inputContentBuffer);
      }

      if (this.closed) {
         throw new IOException("Already closed");
      } else if (len == 0) {
         return 0;
      } else if (!this.inputContentBuffer.hasRemaining() && this.fill(1) == -1) {
         return -1;
      } else {
         int nlen = Math.min(this.inputContentBuffer.remaining(), len);
         this.inputContentBuffer.get(b, off, nlen);
         if (!this.checkMarkAfterRead((long)nlen)) {
            this.inputContentBuffer.shrink();
         }

         return nlen;
      }
   }

   public int readyData() {
      if (this.closed) {
         return 0;
      } else {
         return this.processingChars ? this.availableChar() : this.available();
      }
   }

   public int available() {
      return this.closed ? 0 : this.inputContentBuffer.remaining();
   }

   public Buffer getBuffer() {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s getBuffer. Ready content: %s", this, this.inputContentBuffer);
      }

      return this.inputContentBuffer.duplicate();
   }

   public Buffer readBuffer() {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s readBuffer. Ready content: %s", this, this.inputContentBuffer);
      }

      return this.readBuffer(this.inputContentBuffer.remaining());
   }

   public Buffer readBuffer(int size) {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s readBuffer(size), size: %s. Ready content: %s", this, size, this.inputContentBuffer);
      }

      int remaining = this.inputContentBuffer.remaining();
      if (size > remaining) {
         throw new IllegalStateException("Can not read more bytes than available");
      } else {
         Buffer buffer;
         if (size == remaining) {
            buffer = this.inputContentBuffer;
            this.inputContentBuffer = Buffers.EMPTY_BUFFER;
         } else {
            Buffer tmpBuffer = this.inputContentBuffer.split(this.inputContentBuffer.position() + size);
            buffer = this.inputContentBuffer;
            this.inputContentBuffer = tmpBuffer;
         }

         return buffer;
      }
   }

   public ReadHandler getReadHandler() {
      return this.handler;
   }

   public int read(CharBuffer target) throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s read(CharBuffer). Ready content: %s", this, this.inputContentBuffer);
      }

      if (this.closed) {
         throw new IOException("Already closed");
      } else if (!this.processingChars) {
         throw new IllegalStateException();
      } else if (target == null) {
         throw new IllegalArgumentException("target cannot be null.");
      } else {
         int read = this.fillChars(1, target);
         this.checkMarkAfterRead((long)read);
         return read;
      }
   }

   public int readChar() throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s readChar. Ready content: %s", this, this.inputContentBuffer);
      }

      if (this.closed) {
         throw new IOException("Already closed");
      } else if (!this.processingChars) {
         throw new IllegalStateException();
      } else {
         if (!this.singleCharBuf.hasRemaining()) {
            this.singleCharBuf.clear();
            int read = this.read(this.singleCharBuf);
            if (read == -1) {
               return -1;
            }
         }

         return this.singleCharBuf.get();
      }
   }

   public int read(char[] cbuf, int off, int len) throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s read char array, len: %s. Ready content: %s", this, len, this.inputContentBuffer);
      }

      if (this.closed) {
         throw new IOException("Already closed");
      } else if (!this.processingChars) {
         throw new IllegalStateException();
      } else if (len == 0) {
         return 0;
      } else {
         CharBuffer buf = CharBuffer.wrap(cbuf, off, len);
         return this.read(buf);
      }
   }

   public boolean ready() {
      if (this.closed) {
         return false;
      } else if (!this.processingChars) {
         throw new IllegalStateException();
      } else {
         return this.inputContentBuffer.hasRemaining() || this.httpHeader.isExpectContent();
      }
   }

   public void fillFully(int length) throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s fillFully, len: %s. Ready content: %s", this, length, this.inputContentBuffer);
      }

      if (length != 0) {
         if (length > 0) {
            int remaining = length - this.inputContentBuffer.remaining();
            if (remaining > 0) {
               this.fill(remaining);
            }
         } else {
            this.fill(-1);
         }

      }
   }

   public int availableChar() {
      if (!this.singleCharBuf.hasRemaining()) {
         this.singleCharBuf.clear();
         if (this.fillAvailableChars(1, this.singleCharBuf) == 0) {
            this.singleCharBuf.position(this.singleCharBuf.limit());
            return 0;
         }

         this.singleCharBuf.flip();
      }

      return 1 + (int)((float)this.inputContentBuffer.remaining() * this.averageCharsPerByte);
   }

   public void mark(int readAheadLimit) {
      if (readAheadLimit > 0) {
         this.markPos = this.inputContentBuffer.position();
         this.readCount = 0;
         this.readAheadLimit = readAheadLimit;
      }

   }

   public boolean markSupported() {
      if (this.processingChars) {
         throw new IllegalStateException();
      } else {
         return true;
      }
   }

   public void reset() throws IOException {
      if (this.closed) {
         throw new IOException("Already closed");
      } else if (this.readAheadLimit == -1) {
         throw new IOException("Mark not set");
      } else {
         this.readCount = 0;
         this.inputContentBuffer.position(this.markPos);
      }
   }

   public void close() throws IOException {
      this.closed = true;
   }

   /** @deprecated */
   public long skip(long n, boolean block) throws IOException {
      return this.skip(n);
   }

   public long skip(long n) throws IOException {
      if (LOGGER.isLoggable(LOGGER_LEVEL)) {
         log("InputBuffer %s skip %s bytes. Ready content: %s", this, n, this.inputContentBuffer);
      }

      if (!this.processingChars) {
         if (n <= 0L) {
            return 0L;
         } else if (!this.inputContentBuffer.hasRemaining() && this.fill((int)n) == -1) {
            return -1L;
         } else {
            if ((long)this.inputContentBuffer.remaining() < n) {
               this.fill((int)n);
            }

            long nlen = Math.min((long)this.inputContentBuffer.remaining(), n);
            this.inputContentBuffer.position(this.inputContentBuffer.position() + (int)nlen);
            if (!this.checkMarkAfterRead(n)) {
               this.inputContentBuffer.shrink();
            }

            return nlen;
         }
      } else if (n < 0L) {
         throw new IllegalArgumentException();
      } else if (n == 0L) {
         return 0L;
      } else {
         CharBuffer skipBuffer = CharBuffer.allocate((int)n);
         return this.fillChars((int)n, skipBuffer) == -1 ? 0L : Math.min((long)skipBuffer.remaining(), n);
      }
   }

   public Map getTrailers() {
      return this.trailers;
   }

   public boolean areTrailersAvailable() {
      return this.trailers != null;
   }

   protected void finished() {
      if (!this.contentRead) {
         this.contentRead = true;
         ReadHandler localHandler = this.handler;
         this.processTrailers();
         if (localHandler != null) {
            this.handler = null;
            this.invokeHandlerAllRead(localHandler, this.getThreadPool());
         }
      }

   }

   private void finishedInTheCurrentThread(ReadHandler readHandler) {
      if (!this.contentRead) {
         this.contentRead = true;
         this.processTrailers();
         if (readHandler != null) {
            this.invokeHandlerAllRead(readHandler, (Executor)null);
         }
      }

   }

   private void invokeHandlerAllRead(final ReadHandler readHandler, Executor executor) {
      if (executor != null) {
         executor.execute(new Runnable() {
            public void run() {
               try {
                  readHandler.onAllDataRead();
               } catch (Throwable var2) {
                  readHandler.onError(var2);
               }

            }
         });
      } else {
         try {
            readHandler.onAllDataRead();
         } catch (Throwable var4) {
            readHandler.onError(var4);
         }
      }

   }

   private void processTrailers() {
      if (this.trailers == null) {
         MimeHeaders headers = this.httpHeader.getHeaders();
         int trailerSize = headers.trailerSize();
         if (trailerSize > 0) {
            this.trailers = new HashMap(trailerSize);
            Iterator var3 = headers.trailerNames().iterator();

            while(var3.hasNext()) {
               String name = (String)var3.next();
               this.trailers.put(name.toLowerCase(), headers.getHeader(name));
            }
         } else {
            this.trailers = Collections.emptyMap();
         }
      }

   }

   public void replayPayload(Buffer buffer) {
      if (!this.isFinished()) {
         throw new IllegalStateException("Can't replay when InputBuffer is not closed");
      } else {
         if (LOGGER.isLoggable(LOGGER_LEVEL)) {
            log("InputBuffer %s replayPayload to %s", this, buffer);
         }

         this.closed = false;
         this.readCount = 0;
         this.readAheadLimit = -1;
         this.markPos = -1;
         this.inputContentBuffer = buffer;
      }
   }

   public boolean isFinished() {
      return this.contentRead;
   }

   public boolean isClosed() {
      return this.closed;
   }

   public void notifyAvailable(ReadHandler handler) {
      this.notifyAvailable(handler, 1);
   }

   public void notifyAvailable(ReadHandler handler, int size) {
      if (handler == null) {
         throw new IllegalArgumentException("handler cannot be null.");
      } else if (size <= 0) {
         throw new IllegalArgumentException("size should be positive integer");
      } else if (this.handler != null) {
         throw new IllegalStateException("Illegal attempt to register a new handler before the existing handler has been notified");
      } else if (!this.closed && !this.isFinished()) {
         int available = this.readyData();
         if (shouldNotifyNow(size, available)) {
            try {
               handler.onDataAvailable();
            } catch (Throwable var5) {
               handler.onError(var5);
            }

         } else {
            this.requestedSize = size;
            this.handler = handler;
            if (!this.isWaitingDataAsynchronously) {
               this.isWaitingDataAsynchronously = true;
               this.initiateAsyncronousDataReceiving();
            }

         }
      } else {
         try {
            handler.onAllDataRead();
         } catch (Throwable var6) {
            handler.onError(var6);
         }

      }
   }

   public boolean append(HttpContent httpContent) throws IOException {
      this.isWaitingDataAsynchronously = false;
      if (!HttpContent.isBroken(httpContent)) {
         Buffer buffer = httpContent.getContent();
         if (this.closed) {
            buffer.dispose();
            return false;
         }

         ReadHandler localHandler = this.handler;
         boolean isLast = httpContent.isLast();
         boolean askForMoreDataInThisThread = !isLast && localHandler != null;
         boolean invokeDataAvailable = false;
         if (buffer.hasRemaining()) {
            this.updateInputContentBuffer(buffer);
            if (localHandler != null) {
               int available = this.readyData();
               if (available >= this.requestedSize) {
                  invokeDataAvailable = true;
                  askForMoreDataInThisThread = false;
               }
            }
         }

         if (askForMoreDataInThisThread) {
            this.isWaitingDataAsynchronously = true;
            return true;
         }

         this.handler = null;
         if (isLast) {
            checkHttpTrailer(httpContent);
         }

         this.invokeHandlerOnProperThread(localHandler, invokeDataAvailable, isLast);
      } else {
         ReadHandler localHandler = this.handler;
         this.handler = null;
         this.invokeErrorHandlerOnProperThread(localHandler, ((HttpBrokenContent)httpContent).getException());
      }

      return false;
   }

   /** @deprecated */
   public boolean isAsyncEnabled() {
      return true;
   }

   /** @deprecated */
   public void setAsyncEnabled(boolean asyncEnabled) {
   }

   public void terminate() {
      ReadHandler localHandler = this.handler;
      if (localHandler != null) {
         this.handler = null;
         localHandler.onError((Throwable)(this.connection.isOpen() ? new CancellationException() : new EOFException()));
      }

   }

   public void initiateAsyncronousDataReceiving() {
      this.ctx.fork(this.ctx.getStopAction());
   }

   protected Executor getThreadPool() {
      if (!Threads.isService()) {
         return null;
      } else {
         ExecutorService es = this.connection.getTransport().getWorkerThreadPool();
         return es != null && !es.isShutdown() ? es : null;
      }
   }

   private void invokeErrorHandlerOnProperThread(final ReadHandler localHandler, final Throwable error) {
      if (!this.closed && localHandler != null) {
         Executor executor = this.getThreadPool();
         if (executor != null) {
            executor.execute(new Runnable() {
               public void run() {
                  localHandler.onError(error);
               }
            });
         } else {
            localHandler.onError(error);
         }
      }

   }

   private void invokeHandlerOnProperThread(final ReadHandler localHandler, final boolean invokeDataAvailable, final boolean isLast) throws IOException {
      Executor executor = this.getThreadPool();
      if (executor != null) {
         executor.execute(new Runnable() {
            public void run() {
               InputBuffer.this.invokeHandler(localHandler, invokeDataAvailable, isLast);
            }
         });
      } else {
         this.invokeHandler(localHandler, invokeDataAvailable, isLast);
      }

   }

   private void invokeHandler(ReadHandler localHandler, boolean invokeDataAvailable, boolean isLast) {
      try {
         if (invokeDataAvailable) {
            localHandler.onDataAvailable();
         }

         if (isLast) {
            this.finishedInTheCurrentThread(localHandler);
         }
      } catch (Throwable var5) {
         localHandler.onError(var5);
      }

   }

   protected HttpContent blockingRead() throws IOException {
      ReadResult rr = this.ctx.read();
      HttpContent c = (HttpContent)rr.getMessage();
      rr.recycle();
      return c;
   }

   private int fill(int requestedLen) throws IOException {
      int read = 0;

      while((requestedLen == -1 || read < requestedLen) && this.httpHeader.isExpectContent()) {
         HttpContent c = this.blockingRead();
         boolean isLast = c.isLast();
         checkHttpTrailer(c);

         Buffer b;
         try {
            b = c.getContent();
         } catch (HttpBrokenContentException var8) {
            Throwable cause = var8.getCause();
            throw Exceptions.makeIOException((Throwable)(cause != null ? cause : var8));
         }

         read += b.remaining();
         this.updateInputContentBuffer(b);
         c.recycle();
         if (isLast) {
            this.finished();
            break;
         }
      }

      return read <= 0 && requestedLen != 0 ? -1 : read;
   }

   private int fillChars(int requestedLen, CharBuffer dst) throws IOException {
      int read = 0;
      if (dst != this.singleCharBuf && this.singleCharBuf.hasRemaining()) {
         dst.put(this.singleCharBuf.get());
         read = 1;
      }

      if (this.inputContentBuffer.hasRemaining()) {
         read += this.fillAvailableChars(requestedLen - read, dst);
      }

      if (read >= requestedLen) {
         dst.flip();
         return read;
      } else if (!this.httpHeader.isExpectContent()) {
         dst.flip();
         return read > 0 ? read : -1;
      } else {
         CharsetDecoder decoderLocal = this.getDecoder();
         boolean isNeedMoreInput = false;
         boolean last = false;

         while(read < requestedLen && this.httpHeader.isExpectContent()) {
            if (isNeedMoreInput || !this.inputContentBuffer.hasRemaining()) {
               HttpContent c = this.blockingRead();
               this.updateInputContentBuffer(c.getContent());
               last = c.isLast();
               c.recycle();
               isNeedMoreInput = false;
            }

            ByteBuffer bytes = this.inputContentBuffer.toByteBuffer();
            int bytesPos = bytes.position();
            int dstPos = dst.position();
            CoderResult result = decoderLocal.decode(bytes, dst, false);
            int producedChars = dst.position() - dstPos;
            int consumedBytes = bytes.position() - bytesPos;
            read += producedChars;
            if (consumedBytes > 0) {
               bytes.position(bytesPos);
               this.inputContentBuffer.position(this.inputContentBuffer.position() + consumedBytes);
               if (this.readAheadLimit == -1) {
                  this.inputContentBuffer.shrink();
               }
            } else {
               isNeedMoreInput = true;
            }

            if (last || result == CoderResult.OVERFLOW) {
               break;
            }
         }

         dst.flip();
         if (last && read == 0) {
            read = -1;
         }

         return read;
      }
   }

   private int fillAvailableChars(int requestedLen, CharBuffer dst) {
      CharsetDecoder decoderLocal = this.getDecoder();
      ByteBuffer bb = this.inputContentBuffer.toByteBuffer();
      int oldBBPos = bb.position();
      int producedChars = 0;
      int consumedBytes = 0;
      int remaining = requestedLen;

      int producedCharsNow;
      int consumedBytesNow;
      CoderResult result;
      do {
         int charPos = dst.position();
         int bbPos = bb.position();
         result = decoderLocal.decode(bb, dst, false);
         producedCharsNow = dst.position() - charPos;
         consumedBytesNow = bb.position() - bbPos;
         producedChars += producedCharsNow;
         consumedBytes += consumedBytesNow;
         remaining -= producedCharsNow;
      } while(remaining > 0 && (producedCharsNow > 0 || consumedBytesNow > 0) && bb.hasRemaining() && result == CoderResult.UNDERFLOW);

      bb.position(oldBBPos);
      this.inputContentBuffer.position(this.inputContentBuffer.position() + consumedBytes);
      if (this.readAheadLimit == -1) {
         this.inputContentBuffer.shrink();
      }

      return producedChars;
   }

   protected void updateInputContentBuffer(Buffer buffer) {
      buffer.allowBufferDispose(true);
      if (this.inputContentBuffer == null) {
         this.inputContentBuffer = buffer;
      } else if (!this.inputContentBuffer.hasRemaining() && this.readAheadLimit <= 0) {
         this.inputContentBuffer.tryDispose();
         this.inputContentBuffer = buffer;
      } else {
         this.toCompositeInputContentBuffer().append(buffer);
      }

   }

   private static boolean shouldNotifyNow(int size, int available) {
      return available != 0 && available >= size;
   }

   private CharsetDecoder getDecoder() {
      if (this.decoder == null) {
         this.decoder = (CharsetDecoder)this.decoders.get(this.encoding);
         if (this.decoder == null) {
            Charset cs = Charsets.lookupCharset(this.encoding);
            this.decoder = cs.newDecoder();
            this.decoder.onMalformedInput(CodingErrorAction.REPLACE);
            this.decoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.decoders.put(this.encoding, this.decoder);
         } else {
            this.decoder.reset();
         }
      }

      return this.decoder;
   }

   private CompositeBuffer toCompositeInputContentBuffer() {
      if (!this.inputContentBuffer.isComposite()) {
         CompositeBuffer compositeBuffer = CompositeBuffer.newBuffer(this.connection.getMemoryManager());
         compositeBuffer.allowBufferDispose(true);
         compositeBuffer.allowInternalBuffersDispose(true);
         int posAlign = 0;
         if (this.readAheadLimit > 0) {
            this.inputContentBuffer.position(this.inputContentBuffer.position() - this.readCount);
            posAlign = this.readCount;
            this.markPos = 0;
         }

         compositeBuffer.append(this.inputContentBuffer);
         compositeBuffer.position(posAlign);
         this.inputContentBuffer = compositeBuffer;
      }

      return (CompositeBuffer)this.inputContentBuffer;
   }

   private boolean checkMarkAfterRead(long n) {
      if (n > 0L && this.readAheadLimit != -1) {
         if ((long)this.readCount + n <= (long)this.readAheadLimit) {
            this.readCount = (int)((long)this.readCount + n);
            return true;
         }

         this.readAheadLimit = -1;
         this.markPos = -1;
         this.readCount = 0;
      }

      return false;
   }

   private static void checkHttpTrailer(HttpContent httpContent) {
      if (HttpTrailer.isTrailer(httpContent)) {
         HttpTrailer httpTrailer = (HttpTrailer)httpContent;
         HttpHeader httpHeader = httpContent.getHttpHeader();
         httpHeader.getHeaders().mark();
         MimeHeaders trailerHeaders = httpTrailer.getHeaders();
         int size = trailerHeaders.size();

         for(int i = 0; i < size; ++i) {
            httpHeader.addHeader(trailerHeaders.getName(i).toString(), trailerHeaders.getValue(i).toString());
         }
      }

   }

   private static void log(String message, Object... params) {
      String preparedMsg = String.format(message, params);
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, preparedMsg, new Exception("Logged at"));
      } else {
         LOGGER.log(LOGGER_LEVEL, preparedMsg);
      }

   }

   static {
      LOGGER_LEVEL = Level.FINER;
   }
}
