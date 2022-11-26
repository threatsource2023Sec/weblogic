package weblogic.servlet.http2;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import weblogic.servlet.http2.frame.DataFrame;
import weblogic.servlet.http2.frame.FrameType;
import weblogic.servlet.http2.frame.HeadersFrame;
import weblogic.servlet.http2.frame.PriorityItems;
import weblogic.servlet.http2.frame.PushPromiseFrame;
import weblogic.servlet.http2.frame.ResetFrame;
import weblogic.servlet.http2.frame.WindowUpdateFrame;
import weblogic.servlet.http2.hpack.HeaderEntry;
import weblogic.servlet.http2.hpack.HeaderListener;
import weblogic.servlet.http2.hpack.HpackEncoder;
import weblogic.servlet.http2.hpack.HpackException;
import weblogic.servlet.internal.AbstractHttpConnectionHandler;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.HttpSocket;
import weblogic.servlet.internal.ReadListenerStateContext;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.socket.MuxableSocket;
import weblogic.socket.NIOOutputSink;
import weblogic.socket.WriteHandler;
import weblogic.utils.StringUtils;
import weblogic.utils.http.HttpRequestParseException;
import weblogic.utils.http.RequestParser;

public class StreamImpl implements Stream, HeaderListener {
   private final Integer streamId;
   private final StreamStateManager stateManager;
   private final HTTP2Connection conn;
   private GoAwayCallBack goAwayCallback = null;
   private long lastAccessTime;
   private HpackEncoder encoder;
   private long sendWindowSize;
   private long recvWindowSize;
   private HeaderState headerState;
   private StreamException headerException;
   private ServletRequestImpl req;
   private ServletResponseImpl resp;
   private ByteBuffer inputBuffer;
   private FrameBasedOutputStream output;
   private FrameBasedInputStream in;
   private AbstractHttpConnectionHandler handler;
   private ByteArrayOutputStream cookies;
   private long receivedPayLoadSize;
   private ReadListenerStateContext readStateHandler;
   private StreamManager manager;
   private static Map METHOD_MAPPING = new HashMap();
   private static Map HEADERNAME_MAPPING = new HashMap();

   public StreamImpl(Integer id, HTTP2Connection conn, StreamManager manager, ServletRequestImpl baseReq) {
      this.headerState = StreamImpl.HeaderState.START;
      this.receivedPayLoadSize = 0L;
      this.streamId = id;
      this.stateManager = new StreamStateManager(id);
      this.conn = conn;
      this.manager = manager;
      this.lastAccessTime = System.currentTimeMillis();
      this.sendWindowSize = (long)conn.getInitSendWindowSizeForStream();
      this.recvWindowSize = (long)conn.getInitRecvWindowSizeForStream();
      this.inputBuffer = ByteBuffer.allocate(conn.getInitRecvWindowSizeForStream());
      this.output = new FrameBasedOutputStream();
      this.handler = new Http2ConnectionHandler((HttpSocket)conn, this.output, this, conn.isSecure());
      this.req = this.handler.getServletRequest();
      this.resp = this.handler.getServletResponse();
      if (baseReq != null) {
         this.req.getInputHelper().getRequestParser().initFromRequstParser(baseReq.getInputHelper().getRequestParser());
         this.stateManager.receivedHeaders();
         this.stateManager.receivedEndOfStream();
      }

      this.encoder = conn.getHpackEncoder();
   }

   public void setReadListener(ReadListenerStateContext readStateHandler) {
      synchronized(this.inputBuffer) {
         this.readStateHandler = readStateHandler;
         if ((this.in.in == null || !this.in.in.hasRemaining()) && this.inputBuffer.position() <= 0) {
            if (!this.stateManager.isFrameTypeAllowedToReceive(FrameType.DATA)) {
               readStateHandler.setReadCompleteState();
            } else {
               readStateHandler.setReadWaitState();
            }
         } else {
            readStateHandler.setReadReadyState();
         }

         if (this.in.contentLength > 0L && this.receivedPayLoadSize >= this.in.contentLength && this.inputBuffer.position() == 0 && this.in.in != null && !this.in.in.hasRemaining()) {
            readStateHandler.setReadCompleteState();
         }

      }
   }

   public boolean hasHeaderException() {
      return this.headerException != null;
   }

   public void throwIfHeaderException() throws StreamException {
      if (this.headerException != null) {
         throw this.headerException;
      }
   }

   public void onRead(String name, byte[] value) throws HpackException {
      boolean isPseudoHeader = name.startsWith(":");
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Receiving header, name=" + name + ", value=" + (value != null ? new String(value) : "") + ", the stream id is " + this.streamId + " on Connection " + this.conn.toString());
      }

      if (isPseudoHeader && this.headerState != StreamImpl.HeaderState.PSEUDO) {
         this.headerException = new StreamException(MessageManager.getMessage("stream.header.pseudoHeaderInRegular"), 1, this.getId());
      } else if (isPseudoHeader && this.headerState == StreamImpl.HeaderState.TRAILERS) {
         this.headerException = new StreamException(MessageManager.getMessage("stream.header.pseudoHeaderInTrailer"), 1, this.getId());
      } else {
         if (!isPseudoHeader && this.headerState == StreamImpl.HeaderState.PSEUDO) {
            this.headerState = StreamImpl.HeaderState.REGULAR;
         }

         RequestParser parser = this.req.getInputHelper().getRequestParser();
         switch (name) {
            case ":method":
               if (parser.getMethod() != null) {
                  throw new HpackException(MessageManager.getMessage("stream.header.duplicate", this.getId(), ":method"));
               }

               parser.setMethod(this.converterHeaderName(METHOD_MAPPING, StringUtils.getString(value)));
               break;
            case ":scheme":
               if (parser.getScheme() != null) {
                  throw new HpackException(MessageManager.getMessage("stream.header.duplicate", this.getId(), ":scheme"));
               }

               parser.setScheme(StringUtils.getString(value));
               break;
            case ":path":
               if (parser.getHttpRequestBuffer() != null) {
                  throw new HpackException(MessageManager.getMessage("stream.header.duplicate", this.getId(), ":path"));
               }

               if (value.length == 0) {
                  throw new HpackException(MessageManager.getMessage("stream.header.emptyPath", this.getId()));
               }

               try {
                  parser.parse(value, value.length);
                  break;
               } catch (HttpRequestParseException var9) {
                  throw new HpackException(MessageManager.getMessage("stream.header.invalidPath", this.getId()), var9);
               }
            case ":authority":
               if (parser.getHeader("Host") != null) {
                  throw new HpackException(MessageManager.getMessage("stream.header.duplicate", this.getId(), ":authority"));
               }

               parser.addHeader("Host", value);
               break;
            case "cookie":
               if (this.cookies == null) {
                  this.cookies = new ByteArrayOutputStream();
               }

               if (this.cookies.size() > 0) {
                  try {
                     this.cookies.write("; ".getBytes());
                  } catch (IOException var8) {
                  }
               }

               this.cookies.write(value, 0, value.length);
               break;
            default:
               if (isPseudoHeader) {
                  this.headerException = new StreamException(MessageManager.getMessage("stream.header.unknownPseudoHeader", this.getId()), 1, this.getId());
               }

               if (this.headerState == StreamImpl.HeaderState.TRAILERS) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("Put trailer header, name=" + name + ", value=" + (value != null ? new String(value) : "") + ", the stream id is " + this.streamId + " on Connection " + this.conn.toString());
                  }

                  parser.addTrailerField(this.converterHeaderName(HEADERNAME_MAPPING, name), StringUtils.getString(value));
               } else {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("Put header, name=" + name + ", value=" + (value != null ? new String(value) : "") + ", the stream id is " + this.streamId + " on Connection " + this.conn.toString());
                  }

                  parser.addHeader(this.converterHeaderName(HEADERNAME_MAPPING, name), value);
               }
         }

      }
   }

   private String converterHeaderName(Map mapping, String name) {
      return mapping.containsKey(name) ? (String)mapping.get(name) : name;
   }

   public long getLastAccessTime() {
      return this.lastAccessTime;
   }

   public Integer getId() {
      return this.streamId;
   }

   public boolean headerStart() {
      return this.headerState == StreamImpl.HeaderState.START;
   }

   public void handleOnContainer() {
      if (this.cookies != null) {
         RequestParser parser = this.req.getInputHelper().getRequestParser();
         parser.addHeader("Cookie", this.cookies.toByteArray());
      }

      this.handler.dispatch();
   }

   int getRecvWindowSize() {
      return (int)this.recvWindowSize;
   }

   int getSendWindowSize() {
      return (int)this.sendWindowSize;
   }

   public boolean isRemote() {
      return this.streamId % 2 == 0;
   }

   public void initRequestData(DataFrame frame) throws ConnectionException {
      synchronized(this.inputBuffer) {
         ByteBuffer data = frame.getData();
         this.checkPayloadSize(frame.getPayloadSize());
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Add data " + data.toString() + " into Stream " + this.streamId);
         }

         this.inputBuffer.put(data);
         if (this.inputBuffer.position() > 0) {
            if (this.isNonBlockingRead()) {
               this.readStateHandler.setReadReadyState();
               this.readStateHandler.scheduleProcess();
            }

            this.inputBuffer.notifyAll();
         }

      }
   }

   private boolean isNonBlockingRead() {
      return this.readStateHandler != null;
   }

   public void notifyErrorIfNonBlocking(Throwable t) {
      if (this.isNonBlockingRead()) {
         this.readStateHandler.setErrorState(t);
         this.readStateHandler.process();
      }
   }

   private void checkPayloadSize(int payloadSize) throws ConnectionException {
      this.receivedPayLoadSize += (long)payloadSize;
      long clen = this.req.getContentLengthLong();
      if (clen > -1L && this.receivedPayLoadSize > clen) {
         throw new ConnectionException(MessageManager.getMessage("stream.header.ContentLength", this.streamId, this.conn.toString(), this.receivedPayLoadSize, clen), 1);
      }
   }

   public void checkOnReceiving(FrameType frameType) throws HTTP2Exception {
      this.stateManager.checkOnReceiving(frameType);
   }

   public void checkOnSending(FrameType frameType) throws HTTP2Exception {
      this.stateManager.checkOnReceiving(frameType);
   }

   void changeState(StreamState newState) {
      this.stateManager.changeState(newState);
   }

   public void receivedHeaders(HeadersFrame headers) throws HTTP2Exception {
      if (this.headerState == StreamImpl.HeaderState.START) {
         this.headerState = StreamImpl.HeaderState.PSEUDO;
      } else {
         if (!headers.isEndStream()) {
            throw new StreamException(MessageManager.getMessage("stream.trailer.noEndOfStream"), 1, headers.getStreamId());
         }

         this.headerState = StreamImpl.HeaderState.TRAILERS;
      }

      this.stateManager.receivedHeaders();
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Received Headers, " + this.stateManager.toString());
      }

   }

   public void receivedEndOfStream() {
      this.stateManager.receivedEndOfStream();
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Received end of stream, " + this.stateManager.toString());
      }

      if (this.headerState == StreamImpl.HeaderState.TRAILERS) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Mark TrailerFields Ready for stream " + this.streamId + " on Connection " + this.conn.toString());
            HTTPDebugLogger.debug(this.req.getInputHelper().getRequestParser().getTrailers().toString());
         }

         this.req.getInputHelper().getRequestParser().markTrailerFieldsReady();
      }

      synchronized(this.inputBuffer) {
         this.inputBuffer.notifyAll();
      }
   }

   public void receivedReset(boolean ignoreOnIdle) throws ConnectionException {
      if (!this.stateManager.isFrameTypeAllowedToReceive(FrameType.RST_STREAM) && !this.stateManager.isClosed() && !ignoreOnIdle) {
         throw new ConnectionException(MessageManager.getMessage("stream.idle.reset", this.streamId, this.conn.toString()), 1);
      } else {
         if (this.stateManager.isFrameTypeAllowedToReceive(FrameType.RST_STREAM)) {
            this.stateManager.receivedReset();
         }

         synchronized(this.inputBuffer) {
            this.inputBuffer.notifyAll();
         }

         synchronized(this) {
            this.notifyAll();
         }
      }
   }

   void sendReset() {
      this.stateManager.sendReset();
      this.conn.setMaxProcessedStreamId(this.streamId);
      synchronized(this.inputBuffer) {
         this.inputBuffer.notifyAll();
      }

      if (this.goAwayCallback != null) {
         this.goAwayCallback.finished();
      }

   }

   private void sendEndOfStream() {
      this.stateManager.sendEndOfStream();
      this.conn.setMaxProcessedStreamId(this.streamId);
      if (this.goAwayCallback != null) {
         this.goAwayCallback.finished();
      }

   }

   ServletRequestImpl getRequest() {
      return this.req;
   }

   public int sendPushPromise(List headers, int promisedStreamId) {
      if (!this.stateManager.isFrameTypeAllowedToSend(FrameType.PUSH_PROMISE)) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Current Stream is " + this.toString() + ", PushPromise Frame does not allowed to send");
         }

         return 0;
      } else {
         if (HTTPDebugLogger.isEnabled() && headers != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Sending below PushPromise, the stream id is ").append(this.streamId).append(" and the promised stream id is ").append(promisedStreamId).append(" on Connection ").append(this.conn.toString());
            Iterator var4 = headers.iterator();

            while(var4.hasNext()) {
               HeaderEntry h = (HeaderEntry)var4.next();
               sb.append("\r\n");
               sb.append(h.getName()).append("=").append(h.getValue() != null ? new String(h.getValue()) : "");
            }

            HTTPDebugLogger.debug(sb.toString());
         }

         byte[] data = null;

         try {
            data = this.encoder.encodeHeaders(headers);
         } catch (IOException var6) {
         }

         PushPromiseFrame ppFrame = new PushPromiseFrame(this.streamId, promisedStreamId, ByteBuffer.wrap(data));
         ppFrame.setRemoteMaxFrameSize(this.conn.getRemoteSettings().getMaxFrameSize());
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Sending Headers " + ppFrame.toString() + " on Connection " + this.conn.toString());
         }

         ppFrame.send(this.conn);
         return data.length;
      }
   }

   public void sendPushPromise() {
      this.stateManager.promisedBySendingPP();
   }

   public boolean isClosed() {
      return this.stateManager.isClosed();
   }

   public void closeIfIdle() {
      this.stateManager.closeIfIdle();
   }

   public GoAwayCallBack getGoAwayCallback() {
      return this.goAwayCallback;
   }

   public void setGoAwayCallback(GoAwayCallBack goAwayCallback) {
      this.goAwayCallback = goAwayCallback;
   }

   public void close(HTTP2Exception ex) {
      if (ex instanceof StreamException) {
         try {
            this.reset((StreamException)ex);
         } catch (Exception var4) {
            ConnectionException ce = new ConnectionException(MessageManager.getMessage("stream.reset.fail"), 1);
            ce.initCause(var4);
            this.conn.closeConnection(ce);
         }
      } else {
         this.conn.closeConnection(ex);
      }

      this.stateManager.changeState(StreamStates.CLOSED);
   }

   public void close() {
      this.stateManager.changeState(StreamStates.CLOSED);
   }

   public String toString() {
      return "StreamImpl{streamId=" + this.streamId + ", stateManager=" + this.stateManager + ", conn=" + this.conn + '}';
   }

   public void reset(StreamException ex) throws HTTP2Exception {
      ResetFrame reset = new ResetFrame(0, 0, ex.getStreamId());
      reset.setError(ex.getError());
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Sending Reset Frame " + reset.toString() + " on Connection " + this.conn.toString());
      }

      reset.send(this.conn);
      this.sendReset();
   }

   public int sendHeaders(List headers, boolean endOfStream) {
      if (!this.stateManager.isFrameTypeAllowedToSend(FrameType.HEADERS)) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Current Stream is " + this.toString() + ", Header Frame does not allowed to send");
         }

         return 0;
      } else if (this.conn.getRemoteSettings().getMaxConcurrentStreams() == 0L && this.streamId % 2 == 0) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Current MaxConcurrentStreams is " + this.conn.getRemoteSettings().getMaxConcurrentStreams() + ", server can not make this stream to active");
         }

         return 0;
      } else {
         int bytesSent = this.sendHeadersFrame(headers, endOfStream);
         this.stateManager.sendHeaders();
         if (endOfStream) {
            this.stateManager.sendEndOfStream();
         }

         return bytesSent;
      }
   }

   public int sendTrailers(Map trailerFields) {
      if (!this.stateManager.isFrameTypeAllowedToSend(FrameType.HEADERS)) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Current Stream is " + this.toString() + ", Header Frame does not allowed to send");
         }

         return 0;
      } else if (this.conn.getRemoteSettings().getMaxConcurrentStreams() == 0L && this.streamId % 2 == 0) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Current MaxConcurrentStreams is " + this.conn.getRemoteSettings().getMaxConcurrentStreams() + ", server can not make this stream to active");
         }

         return 0;
      } else {
         if (trailerFields == null) {
            trailerFields = Collections.emptyMap();
         }

         List trailers = new ArrayList();
         Iterator var3 = trailerFields.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry trailerEntry = (Map.Entry)var3.next();
            if (!this.resp.skipDisallowedTrailerField((String)trailerEntry.getKey())) {
               trailers.add(new HeaderEntry((String)trailerEntry.getKey(), ((String)trailerEntry.getValue()).getBytes()));
            }
         }

         int bytesSent = this.sendHeadersFrame(trailers, true);
         this.stateManager.sendEndOfStream();
         return bytesSent;
      }
   }

   private int sendHeadersFrame(List headers, boolean endOfStream) {
      if (HTTPDebugLogger.isEnabled() && headers != null) {
         StringBuilder sb = new StringBuilder();
         sb.append("Sending below Headers, the stream id is ").append(this.streamId).append(" on Connection ").append(this.conn.toString());
         Iterator var4 = headers.iterator();

         while(var4.hasNext()) {
            HeaderEntry h = (HeaderEntry)var4.next();
            sb.append("\r\n");
            sb.append(h.getName()).append("=").append(h.getValue() != null ? new String(h.getValue()) : "");
         }

         HTTPDebugLogger.debug(sb.toString());
      }

      byte[] data = null;

      try {
         data = this.encoder.encodeHeaders(headers);
      } catch (IOException var6) {
      }

      HeadersFrame headersFrame = new HeadersFrame(this.streamId, 0, (PriorityItems)null, endOfStream, ByteBuffer.wrap(data));
      headersFrame.setRemoteMaxFrameSize(this.conn.getRemoteSettings().getMaxFrameSize());
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Sending Headers " + headersFrame.toString() + " on Connection " + this.conn.toString());
      }

      headersFrame.send(this.conn);
      return data.length;
   }

   public void completeDataSend() throws IOException {
      this.output.setDataSendComplete(true);
   }

   public void reset(int error) throws HTTP2Exception, IOException {
      if (this.stateManager.isFrameTypeAllowedToSend(FrameType.RST_STREAM)) {
         ResetFrame reset = new ResetFrame(0, this.getId());
         reset.setError(error);
         reset.send(this.conn);
         this.sendReset();
      }
   }

   public HTTP2Connection getHTTP2Connection() {
      return this.conn;
   }

   public boolean isPushSupported() {
      return this.conn.getRemoteSettings().getEnablePush();
   }

   public synchronized int incrementInitSendWindowSize(int windowSizeIncrement) throws ConnectionException {
      this.sendWindowSize += (long)windowSizeIncrement;
      if (this.sendWindowSize > 2147483647L) {
         throw new ConnectionException("A sender MUST NOT allow a flow-control window to exceed max octets", 3);
      } else {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("The latest SendWindowSize is " + this.sendWindowSize + " for stream " + this.streamId);
         }

         if (this.sendWindowSize > 0L && windowSizeIncrement > 0) {
            this.notifyAll();
         }

         return (int)this.sendWindowSize;
      }
   }

   public synchronized int incrementSendWindowSize(int windowSizeIncrement) throws StreamException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("The current SendWindowSize is " + this.sendWindowSize + " and the increment is " + windowSizeIncrement + " for stream " + this.streamId);
      }

      this.sendWindowSize += (long)windowSizeIncrement;
      if (this.sendWindowSize > 2147483647L) {
         throw new StreamException("A sender MUST NOT allow a flow-control window to exceed max octets", 3, this.getId());
      } else {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("The latest SendWindowSize is " + this.sendWindowSize + " for stream " + this.streamId);
         }

         if (this.sendWindowSize > 0L && windowSizeIncrement > 0) {
            this.notifyAll();
         }

         return (int)this.sendWindowSize;
      }
   }

   public synchronized int incrementInitRecvWindowSize(int windowSizeIncrement) throws ConnectionException {
      this.recvWindowSize += (long)windowSizeIncrement;
      if (this.recvWindowSize > 2147483647L) {
         throw new ConnectionException("A receiver MUST NOT allow a flow-control window to exceed max octets", 3);
      } else {
         return (int)this.recvWindowSize;
      }
   }

   public synchronized int incrementRecvWindowSize(int windowSizeIncrement) throws StreamException {
      this.recvWindowSize += (long)windowSizeIncrement;
      if (this.recvWindowSize < 0L && windowSizeIncrement < 0) {
         throw new StreamException("Receive window limits exceeded", 3, this.getId());
      } else {
         return (int)this.recvWindowSize;
      }
   }

   public void sendWindowUpdate(int updateSize) {
      if (!this.stateManager.isFrameTypeAllowedToSend(FrameType.WINDOW_UPDATE)) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Current Stream is " + this.toString() + ", WindowUpdate Frame does not allowed to send");
         }

      } else {
         WindowUpdateFrame f1 = new WindowUpdateFrame(this.streamId, updateSize);
         f1.send(this.conn);
         WindowUpdateFrame f2 = new WindowUpdateFrame(0, updateSize);
         f2.send(this.conn);
      }
   }

   public InputStream getInputStream() {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("The Content length is " + this.req.getContentLengthLong() + " for stream " + this.streamId);
      }

      this.in = new FrameBasedInputStream(this.req.getContentLengthLong());
      return this.in;
   }

   private boolean isReadyIfNonBlocking() {
      return this.isNonBlockingRead() && !this.readStateHandler.isReadReady();
   }

   static {
      METHOD_MAPPING.put("get", "GET");
      METHOD_MAPPING.put("head", "HEAD");
      METHOD_MAPPING.put("post", "POST");
      METHOD_MAPPING.put("put", "PUT");
      METHOD_MAPPING.put("trace", "TRACE");
      METHOD_MAPPING.put("delete", "DELETE");
      METHOD_MAPPING.put("options", "OPTIONS");
      HEADERNAME_MAPPING.put("wl_proxy_ssl", "WL-Proxy-SSL");
      HEADERNAME_MAPPING.put("wl_proxy_client_ip", "WL-Proxy-Client-IP");
      HEADERNAME_MAPPING.put("wl_proxy_client_cert", "WL-Proxy-Client-Cert");
      HEADERNAME_MAPPING.put("content-length", "Content-Length");
      HEADERNAME_MAPPING.put("content-type", "Content-Type");
      HEADERNAME_MAPPING.put("content-encoding", "Content-Encoding");
      HEADERNAME_MAPPING.put("user-agent", "User-Agent");
      HEADERNAME_MAPPING.put("auth_type", "AUTH_TYPE");
      HEADERNAME_MAPPING.put("accept-languages", "Accept-Language");
      HEADERNAME_MAPPING.put("accept-encoding", "Accept-Encoding");
      HEADERNAME_MAPPING.put("expect", "Expect");
      HEADERNAME_MAPPING.put("authorization", "Authorization");
      HEADERNAME_MAPPING.put("referer", "Referer");
      HEADERNAME_MAPPING.put("allow", "Allow");
   }

   private class FrameBasedOutputStream extends OutputStream implements NIOOutputSink {
      private int defaultSize;
      private ByteBuffer outputBuffer;
      private boolean dataSendComplete;
      private StreamImpl stream;
      private boolean nonBlocking;

      private FrameBasedOutputStream() {
         this.defaultSize = 8192;
         this.outputBuffer = ByteBuffer.allocate(this.defaultSize > StreamImpl.this.conn.getInitSendWindowSizeForStream() ? StreamImpl.this.conn.getInitSendWindowSizeForStream() : this.defaultSize);
         this.dataSendComplete = false;
         this.stream = StreamImpl.this;
         this.nonBlocking = false;
      }

      public boolean canWrite() {
         return StreamImpl.this.conn.hasSendWindow() && StreamImpl.this.getSendWindowSize() > 0;
      }

      public void notifyWritePossible(WriteHandler writeHandler) {
         try {
            writeHandler.onWritable();
         } catch (Exception var3) {
            writeHandler.onError(var3);
         }

      }

      public boolean isBlocking() {
         return this.nonBlocking;
      }

      public void configureBlocking() throws InterruptedException {
         this.nonBlocking = false;
      }

      public void configureBlocking(long timeout, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
         this.nonBlocking = false;
      }

      public void configureNonBlocking(MuxableSocket ms) {
         this.nonBlocking = true;
      }

      public synchronized void write(byte[] b, int off, int len) throws IOException {
         ByteBuffer chunk = ByteBuffer.wrap(b, off, len);

         while(StreamImpl.this.stateManager.isFrameTypeAllowedToSend(FrameType.DATA) && chunk.hasRemaining()) {
            this.put(chunk, this.outputBuffer);
            if (!this.outputBuffer.hasRemaining() && chunk.hasRemaining()) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("Begining to flush data, cache:" + this.outputBuffer.toString() + ", output:" + chunk + " on Connection " + StreamImpl.this.conn.toString());
               }

               this.flush();
            }
         }

      }

      private void put(ByteBuffer src, ByteBuffer des) {
         int writeCount = Math.min(des.remaining(), src.remaining());
         int oldLimit = src.limit();
         src.limit(src.position() + writeCount);
         des.put(src);
         src.limit(oldLimit);
      }

      public synchronized void write(int b) throws IOException {
         if (StreamImpl.this.stateManager.isFrameTypeAllowedToSend(FrameType.DATA) && !this.outputBuffer.hasRemaining()) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Begining to flush data, cache:" + this.outputBuffer.toString() + " on Connection " + StreamImpl.this.conn.toString());
            }

            this.flush();
         }

         if (StreamImpl.this.stateManager.isFrameTypeAllowedToSend(FrameType.DATA)) {
            this.outputBuffer.put((byte)b);
         }

      }

      public synchronized void flush() throws IOException {
         if (!StreamImpl.this.stateManager.isFrameTypeAllowedToSend(FrameType.DATA)) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Current Stream is " + this.stream.toString() + ", Data Frame does not allowed to send");
            }

         } else if (this.outputBuffer.position() <= 0 && this.isEndOfStream(0)) {
            this.sendData(0, ByteBuffer.allocate(0), true);
         } else if (this.outputBuffer.position() > 0) {
            this.outputBuffer.flip();
            int allBytes = this.outputBuffer.remaining();

            while(allBytes > 0) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("allBytes=" + allBytes + " for flushing for Stream " + this.stream.getId());
               }

               int streamWindowSize = this.reserveSendWindowSize(allBytes);

               while(streamWindowSize > 0) {
                  int currentWindow = StreamImpl.this.conn.reserveSendWindowSize(streamWindowSize);
                  ByteBuffer currentBuffer = ByteBuffer.allocate(currentWindow);
                  this.put(this.outputBuffer, currentBuffer);
                  currentBuffer.flip();
                  allBytes -= currentWindow;
                  streamWindowSize -= currentWindow;
                  boolean isEndOfStream = this.isEndOfStream(allBytes);
                  this.sendData(currentWindow, currentBuffer, isEndOfStream);
               }
            }

            this.outputBuffer.compact();
         }
      }

      private void sendData(int currentWindow, ByteBuffer currentBuffer, boolean isEndOfStream) {
         DataFrame dataFrame = new DataFrame(currentWindow, StreamImpl.this.getId(), currentBuffer, isEndOfStream, 0);
         dataFrame.setRemoteMaxFrameSize(StreamImpl.this.conn.getRemoteSettings().getMaxFrameSize());
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Sending data frame " + dataFrame.toString() + " on Connection " + StreamImpl.this.conn.toString());
         }

         dataFrame.send(StreamImpl.this.conn);
         if (isEndOfStream) {
            this.stream.sendEndOfStream();
            StreamImpl.this.manager.removeStream(StreamImpl.this.streamId);
         }

      }

      private boolean isEndOfStream(int allBytes) {
         return this.dataSendComplete && allBytes == 0 && StreamImpl.this.resp.getTrailerFields() == null;
      }

      public synchronized void setDataSendComplete(boolean dataSendComplete) throws IOException {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Set Data Complete for " + this.stream.getId());
         }

         this.dataSendComplete = dataSendComplete;
         this.flush();
      }

      private int reserveSendWindowSize(int allBytes) {
         synchronized(this.stream) {
            int streamWindowSize;
            for(streamWindowSize = StreamImpl.this.getSendWindowSize(); streamWindowSize < 1; streamWindowSize = StreamImpl.this.getSendWindowSize()) {
               try {
                  this.stream.wait();
               } catch (InterruptedException var8) {
               }
            }

            int windowSize = allBytes > streamWindowSize ? streamWindowSize : allBytes;
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Reserving Send Window Size " + windowSize + " for Stream " + this.stream.getId() + ", current send window size is " + this.stream.sendWindowSize);
            }

            try {
               StreamImpl.this.incrementSendWindowSize(-windowSize);
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("The left Send Window Size is " + this.stream.sendWindowSize + " for " + this.stream.getId());
               }
            } catch (StreamException var7) {
            }

            return windowSize;
         }
      }

      // $FF: synthetic method
      FrameBasedOutputStream(Object x1) {
         this();
      }
   }

   private class FrameBasedInputStream extends InputStream {
      private long contentLength = -1L;
      private ByteBuffer in = null;
      private byte[] bytes;
      private long nreadFromBuff;
      private long nread;
      private StreamImpl stream;

      FrameBasedInputStream(long clen) {
         this.bytes = new byte[StreamImpl.this.conn.getInitRecvWindowSizeForStream()];
         this.nreadFromBuff = 0L;
         this.nread = 0L;
         this.stream = StreamImpl.this;
         this.contentLength = clen;
      }

      public int read() throws IOException {
         this.checkNonBlockingRead();
         if (this.in == null || !this.in.hasRemaining()) {
            if (this.contentLength > -1L) {
               synchronized(StreamImpl.this.inputBuffer) {
                  if (StreamImpl.this.receivedPayLoadSize >= this.contentLength && StreamImpl.this.inputBuffer.position() == 0) {
                     return -1;
                  }
               }
            }

            if (this.bytes.length != StreamImpl.this.conn.getInitRecvWindowSizeForStream()) {
               this.bytes = new byte[StreamImpl.this.conn.getInitRecvWindowSizeForStream()];
            }

            this.readFromStream();
         }

         if (this.in != null && this.in.hasRemaining()) {
            int b = this.in.get();
            ++this.nread;
            if (StreamImpl.this.isNonBlockingRead()) {
               synchronized(StreamImpl.this.inputBuffer) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("Current State: " + StreamImpl.this.readStateHandler.getCurrentState());
                  }

                  if (this.contentLength > -1L) {
                     if (this.nread >= this.contentLength) {
                        StreamImpl.this.readStateHandler.setReadCompleteState();
                        if (HTTPDebugLogger.isEnabled()) {
                           HTTPDebugLogger.debug("Current State: " + StreamImpl.this.readStateHandler.getCurrentState());
                        }
                     } else if (StreamImpl.this.inputBuffer.position() == 0 && !this.in.hasRemaining()) {
                        StreamImpl.this.readStateHandler.setReadWaitState();
                        if (HTTPDebugLogger.isEnabled()) {
                           HTTPDebugLogger.debug("Current State: " + StreamImpl.this.readStateHandler.getCurrentState());
                        }
                     }
                  } else if (StreamImpl.this.inputBuffer.position() == 0 && !this.in.hasRemaining()) {
                     if (StreamImpl.this.stateManager.isFrameTypeAllowedToReceive(FrameType.DATA)) {
                        StreamImpl.this.readStateHandler.setReadWaitState();
                        if (HTTPDebugLogger.isEnabled()) {
                           HTTPDebugLogger.debug("Current State: " + StreamImpl.this.readStateHandler.getCurrentState());
                        }
                     } else {
                        StreamImpl.this.readStateHandler.setReadCompleteState();
                        if (HTTPDebugLogger.isEnabled()) {
                           HTTPDebugLogger.debug("Current State: " + StreamImpl.this.readStateHandler.getCurrentState());
                        }
                     }
                  }
               }
            }

            return b & 255;
         } else {
            return -1;
         }
      }

      public int read(byte[] b, int off, int len) throws IOException {
         if (!StreamImpl.this.isNonBlockingRead()) {
            return super.read(b, off, len);
         } else if (b == null) {
            throw new NullPointerException();
         } else if (off >= 0 && len >= 0 && len <= b.length - off) {
            if (len == 0) {
               return 0;
            } else {
               this.checkNonBlockingRead();
               int readLength = Math.min(this.available(), len);
               if (readLength == 0) {
                  return -1;
               } else {
                  for(int i = 0; i < readLength; ++i) {
                     b[off + i] = (byte)this.read();
                  }

                  return readLength;
               }
            }
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public int available() throws IOException {
         if (!StreamImpl.this.isNonBlockingRead()) {
            return super.available();
         } else if (StreamImpl.this.isReadyIfNonBlocking()) {
            return 0;
         } else {
            long avai = 0L;
            synchronized(StreamImpl.this.inputBuffer) {
               avai += (long)(this.in == null ? 0 : this.in.remaining());
               StreamImpl.this.inputBuffer.flip();
               avai += (long)StreamImpl.this.inputBuffer.remaining();
               StreamImpl.this.inputBuffer.compact();
            }

            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Current available byte count is " + avai + " for Stream " + this.stream.toString());
            }

            return (int)avai;
         }
      }

      private void checkNonBlockingRead() {
         if (StreamImpl.this.isReadyIfNonBlocking()) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("ByteBufferInputStream read() throws an exception: nread = " + this.nread);
            }

            StreamImpl.this.readStateHandler.setReadWaitState();
            throw new IllegalStateException("Could not read on a not ready stream!");
         }
      }

      private void readFromStream() throws IOException {
         int reads = false;
         int readsx;
         synchronized(StreamImpl.this.inputBuffer) {
            while(StreamImpl.this.inputBuffer.position() == 0 && StreamImpl.this.stateManager.isFrameTypeAllowedToReceive(FrameType.DATA)) {
               try {
                  StreamImpl.this.inputBuffer.wait();
               } catch (InterruptedException var5) {
               }
            }

            StreamImpl.this.inputBuffer.flip();
            if (StreamImpl.this.inputBuffer.remaining() <= 0) {
               StreamImpl.this.inputBuffer.clear();
               return;
            }

            readsx = StreamImpl.this.inputBuffer.remaining();
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Read data from Stream " + StreamImpl.this.streamId + ", reads=" + readsx + ", " + StreamImpl.this.inputBuffer + ", length=" + this.bytes.length);
            }

            try {
               StreamImpl.this.inputBuffer.get(this.bytes, 0, readsx);
            } catch (IndexOutOfBoundsException var7) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("Exception: Read data from Stream " + StreamImpl.this.streamId + ", reads=" + readsx + ", " + StreamImpl.this.inputBuffer + ", length=" + this.bytes.length, var7);
                  throw var7;
               }
            }

            StreamImpl.this.inputBuffer.clear();
            if (StreamImpl.this.inputBuffer.remaining() != StreamImpl.this.conn.getInitRecvWindowSizeForStream()) {
               StreamImpl.this.inputBuffer = ByteBuffer.allocate(StreamImpl.this.conn.getInitRecvWindowSizeForStream());
            }
         }

         this.in = ByteBuffer.wrap(this.bytes, 0, readsx);
         this.nreadFromBuff += (long)readsx;

         try {
            StreamImpl.this.incrementRecvWindowSize(readsx);
            StreamImpl.this.conn.incrementRecvWindowSize(readsx);
            StreamImpl.this.sendWindowUpdate(readsx);
         } catch (Exception var6) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Got Exception when read data from " + this.stream.toString(), var6);
            }
         }

      }
   }

   private static enum HeaderState {
      START,
      PSEUDO,
      REGULAR,
      TRAILERS;
   }
}
