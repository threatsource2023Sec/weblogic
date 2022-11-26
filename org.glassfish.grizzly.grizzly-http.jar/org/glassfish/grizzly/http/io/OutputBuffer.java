package org.glassfish.grizzly.http.io;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.FileTransfer;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.IOEvent;
import org.glassfish.grizzly.WriteHandler;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.Writer.Reentrant;
import org.glassfish.grizzly.asyncqueue.MessageCloner;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.http.HttpContent;
import org.glassfish.grizzly.http.HttpContext;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpServerFilter;
import org.glassfish.grizzly.http.HttpTrailer;
import org.glassfish.grizzly.http.Protocol;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.http.util.HeaderValue;
import org.glassfish.grizzly.http.util.MimeType;
import org.glassfish.grizzly.impl.FutureImpl;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.CompositeBuffer;
import org.glassfish.grizzly.memory.MemoryManager;
import org.glassfish.grizzly.threadpool.Threads;
import org.glassfish.grizzly.utils.Charsets;
import org.glassfish.grizzly.utils.Exceptions;
import org.glassfish.grizzly.utils.Futures;

public class OutputBuffer {
   protected static final Logger LOGGER = Grizzly.logger(OutputBuffer.class);
   private static final int DEFAULT_BUFFER_SIZE = Integer.getInteger(OutputBuffer.class.getName() + ".default-buffer-size", 8192);
   private static final int MAX_CHAR_BUFFER_SIZE = Integer.getInteger(OutputBuffer.class.getName() + ".max-char-buffer-size", 65537);
   private static final int MIN_BUFFER_SIZE = 512;
   private static final boolean IS_BLOCKING = Boolean.getBoolean(OutputBuffer.class.getName() + ".isBlocking");
   private FilterChainContext ctx;
   private CompositeBuffer compositeBuffer;
   private Buffer currentBuffer;
   private final TemporaryHeapBuffer temporaryWriteBuffer = new TemporaryHeapBuffer();
   private final ByteArrayCloner cloner;
   private final List lifeCycleListeners;
   private boolean committed;
   private boolean headersWritten;
   private boolean finished;
   private boolean closed;
   private CharsetEncoder encoder;
   private final Map encoders;
   private char[] charsArray;
   private int charsArrayLength;
   private CharBuffer charsBuffer;
   private MemoryManager memoryManager;
   private Connection connection;
   private WriteHandler handler;
   private InternalWriteHandler asyncWriteHandler;
   private boolean fileTransferRequested;
   private int bufferSize;
   protected boolean sendfileEnabled;
   private HttpHeader outputHeader;
   private Runnable writePossibleRunnable;
   private HttpContent.Builder builder;
   private boolean isNonBlockingWriteGuaranteed;
   private boolean isLastWriteNonBlocking;
   private HttpContext httpContext;
   private Supplier trailersSupplier;

   public OutputBuffer() {
      this.cloner = new ByteArrayCloner(this.temporaryWriteBuffer);
      this.lifeCycleListeners = new ArrayList(2);
      this.encoders = new HashMap();
      this.bufferSize = DEFAULT_BUFFER_SIZE;
   }

   public void initialize(HttpHeader outputHeader, boolean sendfileEnabled, FilterChainContext ctx) {
      this.outputHeader = outputHeader;
      if (this.builder == null) {
         this.builder = outputHeader.httpContentBuilder();
      } else {
         this.builder.httpHeader(outputHeader);
      }

      this.sendfileEnabled = sendfileEnabled;
      this.ctx = ctx;
      this.httpContext = outputHeader.getProcessingState().getHttpContext();
      this.connection = ctx.getConnection();
      this.memoryManager = ctx.getMemoryManager();
   }

   /** @deprecated */
   @Deprecated
   public boolean isAsyncEnabled() {
      return true;
   }

   /** @deprecated */
   @Deprecated
   public void setAsyncEnabled(boolean asyncEnabled) {
   }

   public void prepareCharacterEncoder() {
      this.getEncoder();
   }

   public int getBufferSize() {
      return this.bufferSize;
   }

   public void registerLifeCycleListener(LifeCycleListener listener) {
      this.lifeCycleListeners.add(listener);
   }

   public boolean removeLifeCycleListener(LifeCycleListener listener) {
      return this.lifeCycleListeners.remove(listener);
   }

   public void setBufferSize(int bufferSize) {
      if (!this.committed) {
         int bufferSizeLocal = Math.max(bufferSize, 512);
         if (this.currentBuffer == null) {
            this.bufferSize = bufferSizeLocal;
         }

         if (this.charsArray != null && this.charsArray.length < bufferSizeLocal) {
            char[] newCharsArray = new char[bufferSizeLocal];
            System.arraycopy(this.charsArray, 0, newCharsArray, 0, this.charsArrayLength);
            this.charsBuffer = CharBuffer.wrap(newCharsArray);
            this.charsArray = newCharsArray;
         }

      }
   }

   public void reset() {
      if (this.committed) {
         throw new IllegalStateException("Cannot reset the response as it has already been committed.");
      } else {
         this.compositeBuffer = null;
         if (this.currentBuffer != null) {
            this.currentBuffer.clear();
         }

         this.charsArrayLength = 0;
         this.encoder = null;
      }
   }

   public boolean isClosed() {
      return this.closed;
   }

   public int getBufferedDataSize() {
      int size = 0;
      if (this.compositeBuffer != null) {
         size += this.compositeBuffer.remaining();
      }

      if (this.currentBuffer != null) {
         size += this.currentBuffer.position();
      }

      size += this.charsArrayLength << 1;
      return size;
   }

   public void recycle() {
      this.outputHeader = null;
      this.builder.reset();
      if (this.compositeBuffer != null) {
         this.compositeBuffer.dispose();
         this.compositeBuffer = null;
      }

      if (this.currentBuffer != null) {
         this.currentBuffer.dispose();
         this.currentBuffer = null;
      }

      this.temporaryWriteBuffer.recycle();
      if (this.charsArray != null) {
         this.charsArrayLength = 0;
         if (this.charsArray.length < MAX_CHAR_BUFFER_SIZE) {
            this.charsBuffer.clear();
         } else {
            this.charsBuffer = null;
            this.charsArray = null;
         }
      }

      this.bufferSize = DEFAULT_BUFFER_SIZE;
      this.fileTransferRequested = false;
      this.encoder = null;
      this.ctx = null;
      this.httpContext = null;
      this.connection = null;
      this.memoryManager = null;
      this.handler = null;
      this.isNonBlockingWriteGuaranteed = false;
      this.isLastWriteNonBlocking = false;
      this.asyncWriteHandler = null;
      this.trailersSupplier = null;
      this.committed = false;
      this.finished = false;
      this.closed = false;
      this.headersWritten = false;
      this.lifeCycleListeners.clear();
   }

   public void endRequest() throws IOException {
      if (!this.finished) {
         InternalWriteHandler asyncWriteQueueHandlerLocal = this.asyncWriteHandler;
         if (asyncWriteQueueHandlerLocal != null) {
            this.asyncWriteHandler = null;
            asyncWriteQueueHandlerLocal.detach();
         }

         if (!this.closed) {
            try {
               this.close();
            } catch (IOException var3) {
            }
         }

         if (this.ctx != null) {
            this.ctx.notifyDownstream(HttpServerFilter.RESPONSE_COMPLETE_EVENT);
         }

         this.finished = true;
      }
   }

   public void acknowledge() throws IOException {
      this.ctx.write(this.outputHeader, IS_BLOCKING);
   }

   public void writeChar(int c) throws IOException {
      this.connection.assertOpen();
      if (!this.closed) {
         this.updateNonBlockingStatus();
         this.checkCharBuffer();
         if (this.charsArrayLength == this.charsArray.length) {
            this.flushCharsToBuf(true);
         }

         this.charsArray[this.charsArrayLength++] = (char)c;
      }
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.connection.assertOpen();
      if (!this.closed && len != 0) {
         this.updateNonBlockingStatus();
         if (this.writingBytes()) {
            this.flushBinaryBuffers(false);
         }

         this.checkCharBuffer();
         int remaining = this.charsArray.length - this.charsArrayLength;
         if (len <= remaining) {
            System.arraycopy(cbuf, off, this.charsArray, this.charsArrayLength, len);
            this.charsArrayLength += len;
         } else if (len - remaining < remaining) {
            System.arraycopy(cbuf, off, this.charsArray, this.charsArrayLength, remaining);
            this.charsArrayLength += remaining;
            this.flushCharsToBuf(true);
            System.arraycopy(cbuf, off + remaining, this.charsArray, 0, len - remaining);
            this.charsArrayLength = len - remaining;
         } else {
            this.flushCharsToBuf(false);
            this.flushCharsToBuf(CharBuffer.wrap(cbuf, off, len), true);
         }

      }
   }

   public void write(char[] cbuf) throws IOException {
      this.write((char[])cbuf, 0, cbuf.length);
   }

   public void write(String str) throws IOException {
      this.write((String)str, 0, str.length());
   }

   public void write(String str, int off, int len) throws IOException {
      this.connection.assertOpen();
      if (!this.closed && len != 0) {
         this.updateNonBlockingStatus();
         if (this.writingBytes()) {
            this.flushBinaryBuffers(false);
         }

         this.checkCharBuffer();
         if (this.charsArray.length - this.charsArrayLength >= len) {
            str.getChars(off, off + len, this.charsArray, this.charsArrayLength);
            this.charsArrayLength += len;
         } else {
            int offLocal = off;
            int lenLocal = len;

            do {
               int remaining = this.charsArray.length - this.charsArrayLength;
               int workingLen = Math.min(lenLocal, remaining);
               str.getChars(offLocal, offLocal + workingLen, this.charsArray, this.charsArrayLength);
               this.charsArrayLength += workingLen;
               offLocal += workingLen;
               lenLocal -= workingLen;
               if (lenLocal > 0) {
                  this.flushCharsToBuf(false);
               }
            } while(lenLocal > 0);

            this.flushBinaryBuffersIfNeeded();
         }
      }
   }

   public void writeByte(int b) throws IOException {
      this.connection.assertOpen();
      if (!this.closed) {
         this.updateNonBlockingStatus();
         if (this.writingChars()) {
            this.flushCharsToBuf(false);
         }

         this.checkCurrentBuffer();
         if (!this.currentBuffer.hasRemaining()) {
            if (this.canWritePayloadChunk()) {
               this.doCommit();
               this.flushBinaryBuffers(false);
               this.checkCurrentBuffer();
               this.blockAfterWriteIfNeeded();
            } else {
               this.finishCurrentBuffer();
               this.checkCurrentBuffer();
            }
         }

         this.currentBuffer.put((byte)b);
      }
   }

   public void write(byte[] b) throws IOException {
      this.write((byte[])b, 0, b.length);
   }

   public void sendfile(File file, CompletionHandler handler) {
      if (file == null) {
         throw new IllegalArgumentException("Argument 'file' cannot be null");
      } else {
         this.sendfile(file, 0L, file.length(), handler);
      }
   }

   public void sendfile(File file, long offset, long length, CompletionHandler handler) {
      if (!this.sendfileEnabled) {
         throw new IllegalStateException("sendfile support isn't available.");
      } else if (this.fileTransferRequested) {
         throw new IllegalStateException("Only one file transfer allowed per request");
      } else {
         this.reset();
         FileTransfer f = new FileTransfer(file, offset, length);
         this.fileTransferRequested = true;
         this.outputHeader.setContentLengthLong((long)f.remaining());
         if (this.outputHeader.getContentType() == null) {
            this.outputHeader.setContentType(MimeType.getByFilename(file.getName()));
         }

         this.outputHeader.setHeader(Header.ContentEncoding, HeaderValue.IDENTITY);

         try {
            this.flush();
         } catch (IOException var9) {
            if (handler != null) {
               handler.failed(var9);
            } else if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, String.format("Failed to transfer file %s.  Cause: %s.", file.getAbsolutePath(), var9.getMessage()), var9);
            }

            return;
         }

         this.ctx.write(f, handler);
      }
   }

   public void write(byte[] b, int off, int len) throws IOException {
      this.connection.assertOpen();
      if (!this.closed && len != 0) {
         this.updateNonBlockingStatus();
         if (this.writingChars()) {
            this.flushCharsToBuf(false);
         }

         if (this.bufferSize >= len && (this.currentBuffer == null || this.currentBuffer.remaining() >= len)) {
            this.checkCurrentBuffer();

            assert this.currentBuffer != null;

            this.currentBuffer.put(b, off, len);
         } else if (this.canWritePayloadChunk()) {
            this.temporaryWriteBuffer.reset(b, off, len);
            this.finishCurrentBuffer();
            this.doCommit();
            if (this.compositeBuffer != null) {
               this.compositeBuffer.append(this.temporaryWriteBuffer);
               this.flushBuffer(this.compositeBuffer, false, this.cloner);
               this.compositeBuffer = null;
            } else {
               this.flushBuffer(this.temporaryWriteBuffer, false, this.cloner);
            }

            this.blockAfterWriteIfNeeded();
         } else {
            this.finishCurrentBuffer();
            Buffer cloneBuffer = this.memoryManager.allocate(len);
            cloneBuffer.put(b, off, len);
            cloneBuffer.flip();
            this.checkCompositeBuffer();
            this.compositeBuffer.append(cloneBuffer);
         }

      }
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.closed = true;
         this.connection.assertOpen();
         boolean isJustCommitted = this.doCommit();
         if (!this.flushAllBuffers(true) && (isJustCommitted || this.outputHeader.isChunked())) {
            this.forceCommitHeaders(true);
         }

         this.blockAfterWriteIfNeeded();
      }
   }

   public void flush() throws IOException {
      this.connection.assertOpen();
      boolean isJustCommitted = this.doCommit();
      if (!this.flushAllBuffers(false) && isJustCommitted) {
         this.forceCommitHeaders(false);
      }

      this.blockAfterWriteIfNeeded();
   }

   public void writeByteBuffer(ByteBuffer byteBuffer) throws IOException {
      Buffer w = Buffers.wrap(this.memoryManager, byteBuffer);
      w.allowBufferDispose(false);
      this.writeBuffer(w);
   }

   public void writeBuffer(Buffer buffer) throws IOException {
      this.connection.assertOpen();
      this.updateNonBlockingStatus();
      this.finishCurrentBuffer();
      this.checkCompositeBuffer();
      this.compositeBuffer.append(buffer);
      if (this.canWritePayloadChunk() && this.compositeBuffer.remaining() > this.bufferSize) {
         this.flush();
      }

   }

   /** @deprecated */
   @Deprecated
   public boolean canWriteChar(int length) {
      return this.canWrite();
   }

   /** @deprecated */
   @Deprecated
   public boolean canWrite(int length) {
      return this.canWrite();
   }

   public boolean canWrite() {
      if (!IS_BLOCKING && !this.isNonBlockingWriteGuaranteed) {
         if (this.httpContext.getOutputSink().canWrite()) {
            this.isNonBlockingWriteGuaranteed = true;
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   public void notifyCanWrite(WriteHandler handler, int length) {
      this.notifyCanWrite(handler);
   }

   public void notifyCanWrite(WriteHandler handler) {
      if (this.handler != null) {
         throw new IllegalStateException("Illegal attempt to set a new handler before the existing handler has been notified.");
      } else if (!this.httpContext.getCloseable().isOpen()) {
         handler.onError(this.connection.getCloseReason().getCause());
      } else {
         this.handler = handler;
         if (!this.isNonBlockingWriteGuaranteed && !this.canWrite()) {
            assert !IS_BLOCKING;

            if (this.asyncWriteHandler == null) {
               this.asyncWriteHandler = new InternalWriteHandler(this);
            }

            try {
               this.httpContext.getOutputSink().notifyCanWrite(this.asyncWriteHandler);
            } catch (Exception var3) {
            }

         } else {
            Writer.Reentrant reentrant = Reentrant.getWriteReentrant();
            if (!reentrant.isMaxReentrantsReached()) {
               this.notifyWritePossible();
            } else {
               this.notifyWritePossibleAsync();
            }

         }
      }
   }

   public void setTrailers(Supplier trailersSupplier) {
      this.trailersSupplier = trailersSupplier;
   }

   public Supplier getTrailers() {
      return this.trailersSupplier;
   }

   protected Executor getThreadPool() {
      if (!Threads.isService()) {
         return null;
      } else {
         ExecutorService es = this.connection.getTransport().getWorkerThreadPool();
         return es != null && !es.isShutdown() ? es : null;
      }
   }

   private void notifyWritePossibleAsync() {
      if (this.writePossibleRunnable == null) {
         this.writePossibleRunnable = new Runnable() {
            public void run() {
               OutputBuffer.this.notifyWritePossible();
            }
         };
      }

      this.connection.executeInEventThread(IOEvent.WRITE, this.writePossibleRunnable);
   }

   private void notifyWritePossible() {
      WriteHandler localHandler = this.handler;
      if (localHandler != null) {
         Writer.Reentrant reentrant = Reentrant.getWriteReentrant();

         try {
            this.handler = null;
            reentrant.inc();
            this.isNonBlockingWriteGuaranteed = true;
            localHandler.onWritePossible();
         } catch (Throwable var7) {
            localHandler.onError(var7);
         } finally {
            reentrant.dec();
         }
      }

   }

   private boolean canWritePayloadChunk() {
      return this.outputHeader.isChunkingAllowed() || this.outputHeader.getContentLength() != -1L;
   }

   private void blockAfterWriteIfNeeded() throws IOException {
      if (!IS_BLOCKING && !this.isNonBlockingWriteGuaranteed && !this.isLastWriteNonBlocking) {
         if (!this.httpContext.getOutputSink().canWrite()) {
            final FutureImpl future = Futures.createSafeFuture();
            this.httpContext.getOutputSink().notifyCanWrite(new WriteHandler() {
               public void onWritePossible() throws Exception {
                  future.result(Boolean.TRUE);
               }

               public void onError(Throwable t) {
                  future.failure(Exceptions.makeIOException(t));
               }
            });

            try {
               long writeTimeout = this.connection.getWriteTimeout(TimeUnit.MILLISECONDS);
               if (writeTimeout >= 0L) {
                  future.get(writeTimeout, TimeUnit.MILLISECONDS);
               } else {
                  future.get();
               }

            } catch (ExecutionException var4) {
               this.httpContext.close();
               throw Exceptions.makeIOException(var4.getCause());
            } catch (TimeoutException var5) {
               this.httpContext.close();
               throw new IOException("Write timeout exceeded when trying to flush the data");
            } catch (Exception var6) {
               this.httpContext.close();
               throw Exceptions.makeIOException(var6);
            }
         }
      }
   }

   private boolean flushAllBuffers(boolean isLast) throws IOException {
      if (this.charsArrayLength > 0) {
         this.flushCharsToBuf(false);
      }

      return this.flushBinaryBuffers(isLast);
   }

   private boolean flushBinaryBuffers(boolean isLast) throws IOException {
      if (!this.outputHeader.isChunkingAllowed() && this.outputHeader.getContentLength() == -1L) {
         if (!isLast) {
            return false;
         }

         this.outputHeader.setContentLength(this.getBufferedDataSize());
      }

      boolean isFlushComposite = this.compositeBuffer != null && this.compositeBuffer.hasRemaining();
      Object bufferToFlush;
      if (isFlushComposite) {
         this.finishCurrentBuffer();
         bufferToFlush = this.compositeBuffer;
         this.compositeBuffer = null;
      } else if (this.currentBuffer != null && this.currentBuffer.position() > 0) {
         this.currentBuffer.trim();
         bufferToFlush = this.currentBuffer;
         this.currentBuffer = null;
      } else {
         bufferToFlush = null;
      }

      if (bufferToFlush == null && !isLast) {
         return false;
      } else {
         this.doCommit();
         this.flushBuffer((Buffer)bufferToFlush, isLast, (MessageCloner)null);
         return true;
      }
   }

   private void flushBuffer(Buffer bufferToFlush, boolean isLast, MessageCloner messageCloner) throws IOException {
      Object content;
      if (!isLast || this.trailersSupplier == null || !this.outputHeader.isChunked() && !this.outputHeader.getProtocol().equals((Object)Protocol.HTTP_2_0)) {
         content = this.builder.content(bufferToFlush).last(isLast).build();
      } else {
         this.forceCommitHeaders(false);
         HttpTrailer.Builder tBuilder = (HttpTrailer.Builder)((HttpTrailer.Builder)this.outputHeader.httpTrailerBuilder().content(bufferToFlush)).last(true);
         Map trailers = (Map)this.trailersSupplier.get();
         if (trailers != null && !trailers.isEmpty()) {
            Iterator var7 = trailers.entrySet().iterator();

            while(var7.hasNext()) {
               Map.Entry entry = (Map.Entry)var7.next();
               tBuilder.header((String)entry.getKey(), (String)entry.getValue());
            }
         }

         content = tBuilder.build();
      }

      this.ctx.write((Object)null, content, (CompletionHandler)null, messageCloner, IS_BLOCKING);
   }

   private void checkCharBuffer() {
      if (this.charsArray == null) {
         this.charsArray = new char[this.bufferSize];
         this.charsBuffer = CharBuffer.wrap(this.charsArray);
      }

   }

   private boolean writingChars() {
      return this.charsArray != null && this.charsArrayLength > 0;
   }

   private boolean writingBytes() {
      return this.currentBuffer != null && this.currentBuffer.position() != 0;
   }

   private void checkCurrentBuffer() {
      if (this.currentBuffer == null) {
         this.currentBuffer = this.memoryManager.allocate(this.bufferSize);
         this.currentBuffer.allowBufferDispose(true);
      }

   }

   private void finishCurrentBuffer() {
      if (this.currentBuffer != null && this.currentBuffer.position() > 0) {
         this.currentBuffer.trim();
         this.checkCompositeBuffer();
         this.compositeBuffer.append(this.currentBuffer);
         this.currentBuffer = null;
      }

   }

   private CharsetEncoder getEncoder() {
      if (this.encoder == null) {
         String encoding = this.outputHeader.getCharacterEncoding();
         if (encoding == null) {
            encoding = Constants.DEFAULT_HTTP_CHARACTER_ENCODING;
         }

         this.encoder = (CharsetEncoder)this.encoders.get(encoding);
         if (this.encoder == null) {
            Charset cs = Charsets.lookupCharset(encoding);
            this.encoder = cs.newEncoder();
            this.encoder.onMalformedInput(CodingErrorAction.REPLACE);
            this.encoder.onUnmappableCharacter(CodingErrorAction.REPLACE);
            this.encoders.put(encoding, this.encoder);
         } else {
            this.encoder.reset();
         }
      }

      return this.encoder;
   }

   private boolean doCommit() throws IOException {
      if (!this.committed) {
         this.notifyCommit();
         this.committed = true;
         this.outputHeader.getHeaders().mark();
         return true;
      } else {
         return false;
      }
   }

   private void forceCommitHeaders(boolean isLast) throws IOException {
      if (!this.headersWritten) {
         if (isLast) {
            if (this.outputHeader != null) {
               this.builder.last(true).content((Buffer)null);
               this.ctx.write(this.builder.build(), IS_BLOCKING);
            }
         } else {
            this.ctx.write(this.outputHeader, IS_BLOCKING);
         }
      }

      this.headersWritten = true;
   }

   private void checkCompositeBuffer() {
      if (this.compositeBuffer == null) {
         CompositeBuffer buffer = CompositeBuffer.newBuffer(this.memoryManager);
         buffer.allowBufferDispose(true);
         buffer.allowInternalBuffersDispose(true);
         this.compositeBuffer = buffer;
      }

   }

   private void flushCharsToBuf(boolean canFlushToNet) throws IOException {
      this.charsBuffer.limit(this.charsArrayLength);

      try {
         this.flushCharsToBuf(this.charsBuffer, canFlushToNet);
      } finally {
         this.charsArrayLength = 0;
         this.charsBuffer.clear();
      }

   }

   private void flushCharsToBuf(CharBuffer charBuf, boolean canFlushToNet) throws IOException {
      if (charBuf.hasRemaining()) {
         CharsetEncoder enc = this.getEncoder();
         this.checkCurrentBuffer();
         if (!this.currentBuffer.hasRemaining()) {
            this.finishCurrentBuffer();
            this.checkCurrentBuffer();
         }

         ByteBuffer currentByteBuffer = this.currentBuffer.toByteBuffer();
         int bufferPos = this.currentBuffer.position();
         int byteBufferPos = currentByteBuffer.position();
         CoderResult res = enc.encode(charBuf, currentByteBuffer, true);
         this.currentBuffer.position(bufferPos + (currentByteBuffer.position() - byteBufferPos));

         while(res == CoderResult.OVERFLOW) {
            this.checkCurrentBuffer();
            currentByteBuffer = this.currentBuffer.toByteBuffer();
            bufferPos = this.currentBuffer.position();
            byteBufferPos = currentByteBuffer.position();
            res = enc.encode(charBuf, currentByteBuffer, true);
            this.currentBuffer.position(bufferPos + (currentByteBuffer.position() - byteBufferPos));
            if (res == CoderResult.OVERFLOW) {
               this.finishCurrentBuffer();
            }
         }

         if (res != CoderResult.UNDERFLOW) {
            throw new IOException("Encoding error");
         } else {
            if (canFlushToNet) {
               this.flushBinaryBuffersIfNeeded();
            }

         }
      }
   }

   private void flushBinaryBuffersIfNeeded() throws IOException {
      if (this.compositeBuffer != null) {
         this.flushBinaryBuffers(false);
         this.blockAfterWriteIfNeeded();
      }

   }

   private void notifyCommit() throws IOException {
      int i = 0;

      for(int len = this.lifeCycleListeners.size(); i < len; ++i) {
         ((LifeCycleListener)this.lifeCycleListeners.get(i)).onCommit();
      }

   }

   private void updateNonBlockingStatus() {
      this.isLastWriteNonBlocking = this.isNonBlockingWriteGuaranteed;
      this.isNonBlockingWriteGuaranteed = false;
   }

   private static class InternalWriteHandler implements WriteHandler {
      private volatile OutputBuffer outputBuffer;

      public InternalWriteHandler(OutputBuffer outputBuffer) {
         this.outputBuffer = outputBuffer;
      }

      public void detach() {
         OutputBuffer obLocal = this.outputBuffer;
         if (obLocal != null) {
            this.outputBuffer = null;
            onError0(obLocal, (Throwable)(obLocal.httpContext.getCloseable().isOpen() ? new CancellationException() : new EOFException()));
         }

      }

      public void onWritePossible() throws Exception {
         final OutputBuffer localOB = this.outputBuffer;
         if (localOB != null) {
            Executor executor = localOB.getThreadPool();
            if (executor != null) {
               executor.execute(new Runnable() {
                  public void run() {
                     try {
                        OutputBuffer.InternalWriteHandler.onWritePossible0(localOB);
                     } catch (Exception var2) {
                     }

                  }
               });
            } else {
               onWritePossible0(localOB);
            }
         }

      }

      public void onError(final Throwable t) {
         final OutputBuffer localOB = this.outputBuffer;
         if (localOB != null) {
            Executor executor = localOB.getThreadPool();
            if (executor != null) {
               executor.execute(new Runnable() {
                  public void run() {
                     OutputBuffer.InternalWriteHandler.onError0(localOB, t);
                  }
               });
            } else {
               onError0(localOB, t);
            }
         }

      }

      private static void onWritePossible0(OutputBuffer ob) throws Exception {
         try {
            Writer.Reentrant reentrant = Reentrant.getWriteReentrant();
            if (!reentrant.isMaxReentrantsReached()) {
               ob.notifyWritePossible();
            } else {
               ob.notifyWritePossibleAsync();
            }
         } catch (Exception var2) {
         }

      }

      private static void onError0(OutputBuffer ob, Throwable t) {
         WriteHandler localHandler = ob.handler;
         if (localHandler != null) {
            try {
               ob.handler = null;
               localHandler.onError(t);
            } catch (Exception var4) {
            }
         }

      }
   }

   public interface LifeCycleListener {
      void onCommit() throws IOException;
   }

   static final class ByteArrayCloner implements MessageCloner {
      private final TemporaryHeapBuffer temporaryWriteBuffer;

      public ByteArrayCloner(TemporaryHeapBuffer temporaryWriteBuffer) {
         this.temporaryWriteBuffer = temporaryWriteBuffer;
      }

      public Buffer clone(Connection connection, Buffer originalMessage) {
         return this.temporaryWriteBuffer.isDisposed() ? originalMessage : this.clone0(connection.getMemoryManager(), originalMessage);
      }

      Buffer clone0(MemoryManager memoryManager, Buffer originalMessage) {
         if (originalMessage.isComposite()) {
            CompositeBuffer compositeBuffer = (CompositeBuffer)originalMessage;
            compositeBuffer.shrink();
            if (!this.temporaryWriteBuffer.isDisposed()) {
               if (compositeBuffer.remaining() == this.temporaryWriteBuffer.remaining()) {
                  compositeBuffer.allowInternalBuffersDispose(false);
                  compositeBuffer.tryDispose();
                  return this.temporaryWriteBuffer.cloneContent(memoryManager);
               }

               compositeBuffer.replace(this.temporaryWriteBuffer, this.temporaryWriteBuffer.cloneContent(memoryManager));
            }

            return originalMessage;
         } else {
            return this.temporaryWriteBuffer.cloneContent(memoryManager);
         }
      }
   }
}
