package weblogic.servlet.http2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import weblogic.servlet.http2.frame.ContinuationFrame;
import weblogic.servlet.http2.frame.DataFrame;
import weblogic.servlet.http2.frame.Frame;
import weblogic.servlet.http2.frame.FrameType;
import weblogic.servlet.http2.frame.GoAwayFrame;
import weblogic.servlet.http2.frame.HeaderParser;
import weblogic.servlet.http2.frame.HeadersFrame;
import weblogic.servlet.http2.frame.PingFrame;
import weblogic.servlet.http2.frame.PriorityFrame;
import weblogic.servlet.http2.frame.PriorityItems;
import weblogic.servlet.http2.frame.ResetFrame;
import weblogic.servlet.http2.frame.SettingsFrame;
import weblogic.servlet.http2.frame.WindowUpdateFrame;
import weblogic.servlet.http2.hpack.HeaderListener;
import weblogic.servlet.http2.hpack.HpackDecoder;
import weblogic.servlet.http2.hpack.HpackException;
import weblogic.servlet.internal.HTTPDebugLogger;

public class FrameHandler {
   private final HTTP2Connection conn;
   private final StreamManager sm;
   private HeaderParser headerParser;
   private volatile int currentStreamId = -1;
   private volatile int maxActiveRemoteStreamId = -1;

   public FrameHandler(HTTP2Connection conn) {
      this.conn = conn;
      this.sm = conn.getStreamManager();
      this.headerParser = new HeaderParser(new HpackDecoder());
   }

   public Frame parse(ByteBuffer buffer, FrameType expectedType) throws HTTP2Exception, IOException {
      Frame frame = this.parseFrameHeader(buffer);
      if (expectedType != null && frame.getType() != expectedType) {
         throw new ProtocalException("The expected frame should be " + expectedType, 1);
      } else {
         if (frame != null) {
            frame.parseBody(buffer);
         }

         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Receiving " + frame.toString() + " on Connection: " + this.conn.toString());
         }

         this.onParse(frame);
         return frame;
      }
   }

   public void generate(ByteBuffer target, Frame frame) throws HTTP2Exception, IOException {
      if (frame != null) {
         frame.setRemoteMaxFrameSize(this.conn.getRemoteSettings().getMaxFrameSize());
         target.put(frame.toBytes());
      }

   }

   private Frame parseFrameHeader(ByteBuffer buffer) throws StreamException, ConnectionException {
      Frame frame = null;
      int payloadSize = this.parsePayloadSize(buffer);
      FrameType type = FrameType.valueOf(buffer.get() & 255);
      int flags = buffer.get() & 255;
      int streamId = buffer.getInt() & Integer.MAX_VALUE;
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("Receiving frame: type=" + type.toString() + ", flags=" + flags + ", streamId=" + streamId + " on Connection: " + this.conn.toString());
      }

      switch (type) {
         case DATA:
            frame = new DataFrame(payloadSize, flags, streamId);
            break;
         case HEADERS:
            frame = new HeadersFrame(payloadSize, flags, streamId);
            break;
         case PRIORITY:
            frame = new PriorityFrame(payloadSize, flags, streamId);
            break;
         case RST_STREAM:
            frame = new ResetFrame(payloadSize, flags, streamId);
            break;
         case SETTINGS:
            frame = new SettingsFrame(payloadSize, flags, streamId);
            break;
         case PUSH_PROMISE:
            throw new ConnectionException("Received PushPromiseFrame. Since client cannot push, so will close connection", 1);
         case PING:
            frame = new PingFrame(payloadSize, flags, streamId);
            break;
         case GOAWAY:
            frame = new GoAwayFrame(payloadSize, flags, streamId);
            break;
         case WINDOW_UPDATE:
            frame = new WindowUpdateFrame(streamId);
            break;
         case CONTINUATION:
            frame = new ContinuationFrame(payloadSize, flags, streamId);
         case UNKNOWN:
      }

      try {
         this.validate(payloadSize, type, streamId, ((Frame)frame).isStreamRequired(), (FrameType)null);
         return (Frame)frame;
      } catch (StreamException var11) {
         StreamException e = var11;
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Validating Frame failed: " + ((Frame)frame).toString(), var11);
         }

         Stream stream = this.sm.getStream(streamId);
         if (stream == null) {
            return (Frame)frame;
         } else {
            if (type == FrameType.DATA) {
               stream.notifyErrorIfNonBlocking(var11);
            }

            try {
               stream.reset(e);
            } catch (HTTP2Exception var10) {
            }

            throw var11;
         }
      } catch (ConnectionException var12) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Validating Frame failed: " + ((Frame)frame).toString(), var12);
         }

         throw var12;
      }
   }

   private int parsePayloadSize(ByteBuffer buffer) {
      return ((buffer.get() & 255) << 16) + ((buffer.get() & 255) << 8) + (buffer.get() & 255);
   }

   protected void validate(int payloadSize, FrameType currentType, int streamId, boolean streamRequired, FrameType expectedFrameType) throws ConnectionException, StreamException {
      if (this.currentStreamId != -1) {
         if (this.currentStreamId != streamId) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.headers.wrongStream", this.conn.toString(), Integer.toString(this.currentStreamId), Integer.toString(streamId)), 1);
         }

         if (currentType != FrameType.CONTINUATION && currentType != FrameType.RST_STREAM) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.headers.wrongFrameType", this.conn.toString(), Integer.toString(this.currentStreamId), currentType.toString(), FrameType.CONTINUATION + " or " + FrameType.RST_STREAM), 1);
         }
      }

      if ((streamId == 0 && streamRequired || streamId != 0 && !streamRequired) && currentType != FrameType.WINDOW_UPDATE) {
         throw new ConnectionException(MessageManager.getMessage("frameType.checkFrameType", currentType), 1);
      } else {
         this.checkFrameSize(currentType, payloadSize, streamId);
         if (payloadSize > this.conn.getLocalSettings().getMaxFrameSize()) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.payloadTooBig", payloadSize, this.conn.getLocalSettings().getMaxFrameSize()), 6);
         } else if (expectedFrameType != null && currentType != expectedFrameType) {
            throw new StreamException(MessageManager.getMessage("processFrame.unexpectedType", expectedFrameType, currentType), 1, streamId);
         }
      }
   }

   private void checkFrameSize(FrameType type, int payloadSize, int streamId) throws ConnectionException, StreamException {
      boolean frameSizeError = false;
      boolean connectionException = false;
      switch (type) {
         case PRIORITY:
            if (payloadSize != 5) {
               frameSizeError = true;
            }
            break;
         case RST_STREAM:
            if (payloadSize != 4) {
               frameSizeError = true;
               connectionException = true;
            }
            break;
         case SETTINGS:
            if (payloadSize % 6 != 0) {
               frameSizeError = true;
            }
         case PUSH_PROMISE:
         default:
            break;
         case PING:
            if (payloadSize != 8) {
               frameSizeError = true;
            }
            break;
         case GOAWAY:
            if (payloadSize < 8) {
               frameSizeError = true;
            }
            break;
         case WINDOW_UPDATE:
            if (payloadSize != 4) {
               frameSizeError = true;
               connectionException = true;
            }
      }

      if (frameSizeError) {
         if (streamId != 0 && !connectionException) {
            throw new StreamException(MessageManager.getMessage("frameType.checkPayloadSize", type, payloadSize), 6, streamId);
         } else {
            throw new ConnectionException(MessageManager.getMessage("frameType.checkPayloadSize", type, payloadSize), 6);
         }
      }
   }

   private void onParse(Frame frame) throws HTTP2Exception, IOException {
      switch (frame.getType()) {
         case DATA:
            this.onData((DataFrame)frame);
            break;
         case HEADERS:
            this.onHeaders((HeadersFrame)frame);
            break;
         case PRIORITY:
            this.onPriority((PriorityFrame)frame);
            break;
         case RST_STREAM:
            this.onReset((ResetFrame)frame);
            break;
         case SETTINGS:
            this.onSettings((SettingsFrame)frame);
         case PUSH_PROMISE:
         case UNKNOWN:
         default:
            break;
         case PING:
            this.onPing((PingFrame)frame);
            break;
         case GOAWAY:
            this.onGoAway((GoAwayFrame)frame);
            break;
         case WINDOW_UPDATE:
            this.onWindowUpdate((WindowUpdateFrame)frame);
            break;
         case CONTINUATION:
            this.onContinuation((ContinuationFrame)frame);
      }

   }

   public void onSettings(SettingsFrame frame) throws ConnectionException, IOException {
      if (frame.isAck()) {
         if (!this.conn.getLocalSettings().onAck()) {
         }
      } else {
         if (frame.getSettings() != null) {
            Iterator var2 = frame.getSettings().entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               Integer key = (Integer)entry.getKey();
               long value = (Long)entry.getValue();
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("Receive Setting, key is " + key + ", value is " + value);
               }

               this.conn.getRemoteSettings().set(key, value);
               if (key == 4) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("The change value for initial window size is " + value);
                  }

                  this.updateInitWindowForStream((int)value, true);
               }
            }
         }

         ByteBuffer output = ByteBuffer.allocate(9);
         Frame.generateAck(output, FrameType.SETTINGS, 0, (byte[])null);
         this.conn.sendBytes(output.array());
      }

   }

   private void updateInitWindowForStream(int value, boolean isSending) throws ConnectionException {
      int oldValue = false;
      int oldValue;
      if (isSending) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Set initial send window size to " + value);
         }

         oldValue = this.conn.getInitSendWindowSizeForStream();
         this.conn.setInitSendWindowSizeForStream(value);
      } else {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("Set initial recv window size to " + value);
         }

         oldValue = this.conn.getInitRecvWindowSizeForStream();
         this.conn.setInitRecvWindowSizeForStream(value);
      }

      int diff = value - oldValue;
      if (isSending) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("The change for initial send window size is " + diff);
         }

         this.sm.incrementInitSendWindowSize(diff);
      } else {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("The change for initial recv window size is " + diff);
         }

         this.sm.incrementInitRecvWindowSize(diff);
      }

   }

   public void onData(DataFrame frame) throws HTTP2Exception {
      Stream stream = this.sm.getStream(frame.getStreamId());
      if (stream != null) {
         stream.checkOnReceiving(FrameType.DATA);
         int dataSize = frame.remaining();

         try {
            stream.incrementRecvWindowSize(-dataSize);
            this.conn.incrementRecvWindowSize(-dataSize);
         } catch (StreamException var7) {
            StreamException e = var7;

            try {
               stream.reset(e);
            } catch (Exception var6) {
            }
         }

         if (frame.paddingLength() > 0) {
            stream.sendWindowUpdate(frame.paddingLength() + 1);
         }

         stream.initRequestData(frame);
         if (frame.isEndStream()) {
            stream.receivedEndOfStream();
         }

      }
   }

   public void onHeaders(HeadersFrame frame) throws HTTP2Exception {
      Integer streamId = frame.getStreamId();
      Stream stream = this.findOrCreateStream(streamId);
      if (frame.getPriority() != null) {
         this.rePriority(streamId, frame.getPriority());
      }

      if (stream.headerStart() && streamId < this.maxActiveRemoteStreamId) {
         throw new ConnectionException(MessageManager.getMessage("stream.id.old", streamId, this.maxActiveRemoteStreamId), 1);
      } else {
         stream.checkOnReceiving(FrameType.HEADERS);
         stream.receivedHeaders(frame);
         if (frame.isEndStream()) {
            this.headerParser.setEndOfStream(true);
         }

         this.closeIdleStreams(streamId);

         try {
            this.headerParser.setHeaderListener((StreamImpl)stream);
            this.headerParser.parse(frame.getData());
         } catch (HpackException var6) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.processHeadersFrame.decodingFailed"), 9, var6);
         }

         if (frame.isEndOfHeader()) {
            this.currentStreamId = -1;

            try {
               this.headersComplete(streamId);
            } catch (StreamException var5) {
               stream.reset(var5);
            }
         } else {
            this.currentStreamId = streamId;
         }

      }
   }

   private Stream findOrCreateStream(Integer streamId) throws HTTP2Exception {
      Stream stream = this.sm.getStream(streamId);
      if (stream == null) {
         stream = this.sm.createRemoteStream(streamId);
      }

      return stream;
   }

   public void onContinuation(ContinuationFrame frame) throws HTTP2Exception {
      if (this.currentStreamId == -1) {
         throw new ConnectionException(MessageManager.getMessage("http2Parser.processContinuationFrame.noHeaders", frame.getStreamId()), 1);
      } else if (frame.getStreamId() != this.currentStreamId) {
         throw new ConnectionException(MessageManager.getMessage("http2Parser.headers.wrongStream", this.conn.toString(), this.currentStreamId, frame.getStreamId()), 1);
      } else {
         Integer streamId = frame.getStreamId();

         try {
            this.headerParser.parse(frame.getData());
         } catch (HpackException var5) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.processHeadersFrame.decodingFailed"), 9, var5);
         }

         if (frame.isEndOfHeader()) {
            this.currentStreamId = -1;

            try {
               this.headersComplete(streamId);
            } catch (StreamException var6) {
               Stream stream = this.sm.getStream(streamId);
               if (stream != null) {
                  stream.reset(var6);
               }
            }
         }

      }
   }

   private void headersComplete(int streamId) throws HTTP2Exception {
      if (!this.headerParser.allDataProcessed()) {
         throw new ConnectionException(MessageManager.getMessage("http2Parser.processHeadersFrame.dataLeft"), 9);
      } else {
         Stream stream = this.sm.getStream(streamId);
         HeaderListener listener = this.headerParser.getHeaderListener();
         if (listener.hasHeaderException()) {
            this.headerParser.reset();
            listener.throwIfHeaderException();
         }

         if (this.headerParser.isEndOfStream()) {
            stream.receivedEndOfStream();
         }

         stream.handleOnContainer();
         this.headerParser.reset();
      }
   }

   private void closeIdleStreams(int currentMaxActiveRemoteStreamId) throws HTTP2Exception {
      for(int i = this.maxActiveRemoteStreamId + 2; i < currentMaxActiveRemoteStreamId; i += 2) {
         Stream stream = this.sm.getStream(i);
         if (stream != null) {
            stream.closeIfIdle();
         }
      }

      this.maxActiveRemoteStreamId = currentMaxActiveRemoteStreamId;
   }

   public void onPriority(PriorityFrame frame) throws HTTP2Exception {
      this.rePriority(frame.getStreamId(), frame.getPriorityItems());
   }

   private void rePriority(int streamId, PriorityItems items) throws HTTP2Exception {
      Stream stream = this.findOrCreateStream(streamId);
      if (items.getParentStreamId() != 0) {
         if (streamId == items.getParentStreamId()) {
            try {
               stream.reset(new StreamException(MessageManager.getMessage("processFrame.dependency.invalid", this.conn.toString(), streamId), 1, streamId));
            } catch (HTTP2Exception var5) {
            }

         }
      }
   }

   public void onReset(ResetFrame frame) throws HTTP2Exception {
      Integer streamId = frame.getStreamId();
      Stream stream = this.sm.getStream(streamId);
      if (stream != null || this.sm.isRemoteStream(streamId)) {
         if (stream != null || !this.sm.isRemoteStream(streamId) || this.sm.canCreate(streamId)) {
            stream = this.findOrCreateStream(streamId);
            stream.checkOnReceiving(FrameType.RST_STREAM);
            stream.receivedReset(false);
         }
      }
   }

   public void onPing(PingFrame frame) {
      this.conn.receivedPing(frame);
   }

   public void onGoAway(GoAwayFrame frame) {
      if (frame.getError() == 0) {
      }

      this.sm.closeUnprocessedStreams(frame.getLastStreamId());
      if (this.sm.hasAliveStreams()) {
         GoAwayCallBack callback = new GoAwayCallBack(this.conn);
         Iterator var3 = this.sm.getAliveStreams().iterator();

         while(var3.hasNext()) {
            Stream stream = (Stream)var3.next();
            stream.setGoAwayCallback(callback);
         }
      } else {
         this.conn.closeConnection((Throwable)null);
      }

   }

   public void onWindowUpdate(WindowUpdateFrame frame) throws HTTP2Exception {
      int windowSize = frame.getUpdateSize();
      Stream stream = null;
      if (frame.getStreamId() > 0) {
         stream = this.sm.getStream(frame.getStreamId());
      }

      if (stream != null) {
         stream.checkOnReceiving(FrameType.WINDOW_UPDATE);
      }

      if (windowSize == 0) {
         if (frame.getStreamId() == 0) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.processFrameWindowUpdate.invalidIncrement", this.conn.toString(), frame.getStreamId(), windowSize), 1);
         } else {
            try {
               if (stream != null) {
                  stream.reset(new StreamException(MessageManager.getMessage("http2Parser.processFrameWindowUpdate.invalidIncrement", this.conn.toString(), frame.getStreamId(), windowSize), 1, frame.getStreamId()));
               }
            } catch (HTTP2Exception var6) {
            }

         }
      } else {
         if (frame.getStreamId() == 0) {
            this.conn.incrementSendWindowSize(windowSize);
         } else {
            try {
               if (stream != null) {
                  stream.incrementSendWindowSize(windowSize);
               }
            } catch (StreamException var8) {
               StreamException se = var8;

               try {
                  stream.reset(se);
               } catch (Exception var7) {
               }
            }
         }

      }
   }
}
