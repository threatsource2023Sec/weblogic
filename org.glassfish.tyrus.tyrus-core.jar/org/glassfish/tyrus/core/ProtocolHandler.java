package org.glassfish.tyrus.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.Extension;
import javax.websocket.SendHandler;
import javax.websocket.SendResult;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.core.extension.ExtendedExtension;
import org.glassfish.tyrus.core.frame.BinaryFrame;
import org.glassfish.tyrus.core.frame.CloseFrame;
import org.glassfish.tyrus.core.frame.Frame;
import org.glassfish.tyrus.core.frame.TextFrame;
import org.glassfish.tyrus.core.frame.TyrusFrame;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.core.monitoring.MessageEventListener;
import org.glassfish.tyrus.spi.CompletionHandler;
import org.glassfish.tyrus.spi.UpgradeRequest;
import org.glassfish.tyrus.spi.UpgradeResponse;
import org.glassfish.tyrus.spi.Writer;

public final class ProtocolHandler {
   public static final int MASK_SIZE = 4;
   private static final Logger LOGGER = Logger.getLogger(ProtocolHandler.class.getName());
   private static final int SEND_TIMEOUT = 3000;
   private final boolean client;
   private final MaskingKeyGenerator maskingKeyGenerator;
   private final ParsingState parsingState = new ParsingState();
   private volatile TyrusWebSocket webSocket;
   private volatile byte outFragmentedType;
   private volatile Writer writer;
   private volatile byte inFragmentedType;
   private volatile boolean processingFragment;
   private volatile String subProtocol = null;
   private volatile List extensions;
   private volatile ExtendedExtension.ExtensionContext extensionContext;
   private volatile ByteBuffer remainder = null;
   private volatile boolean hasExtensions = false;
   private volatile MessageEventListener messageEventListener;
   private volatile SendingFragmentState sendingFragment;
   private final Lock lock;
   private final Condition idleCondition;

   ProtocolHandler(boolean client, MaskingKeyGenerator maskingKeyGenerator) {
      this.messageEventListener = MessageEventListener.NO_OP;
      this.sendingFragment = ProtocolHandler.SendingFragmentState.IDLE;
      this.lock = new ReentrantLock();
      this.idleCondition = this.lock.newCondition();
      this.client = client;
      if (client) {
         if (maskingKeyGenerator != null) {
            this.maskingKeyGenerator = maskingKeyGenerator;
         } else {
            this.maskingKeyGenerator = new MaskingKeyGenerator() {
               private final SecureRandom secureRandom = new SecureRandom();

               public int nextInt() {
                  return this.secureRandom.nextInt();
               }
            };
         }
      } else {
         this.maskingKeyGenerator = null;
      }

   }

   public void setWriter(Writer writer) {
      this.writer = writer;
   }

   public boolean hasExtensions() {
      return this.hasExtensions;
   }

   public Handshake handshake(TyrusEndpointWrapper endpointWrapper, UpgradeRequest request, UpgradeResponse response, ExtendedExtension.ExtensionContext extensionContext) throws HandshakeException {
      Handshake handshake = Handshake.createServerHandshake(request, extensionContext);
      this.extensions = handshake.respond(request, response, endpointWrapper);
      this.subProtocol = response.getFirstHeaderValue("Sec-WebSocket-Protocol");
      this.extensionContext = extensionContext;
      this.hasExtensions = this.extensions != null && this.extensions.size() > 0;
      return handshake;
   }

   List getExtensions() {
      return this.extensions;
   }

   public void setExtensions(List extensions) {
      this.extensions = extensions;
      this.hasExtensions = extensions != null && extensions.size() > 0;
   }

   String getSubProtocol() {
      return this.subProtocol;
   }

   public void setWebSocket(TyrusWebSocket webSocket) {
      this.webSocket = webSocket;
   }

   public void setExtensionContext(ExtendedExtension.ExtensionContext extensionContext) {
      this.extensionContext = extensionContext;
   }

   public void setMessageEventListener(MessageEventListener messageEventListener) {
      this.messageEventListener = messageEventListener;
   }

   final Future send(TyrusFrame frame) {
      return this.send((TyrusFrame)frame, (CompletionHandler)null, true);
   }

   private Future send(TyrusFrame frame, CompletionHandler completionHandler, Boolean useTimeout) {
      return this.write(frame, completionHandler, useTimeout);
   }

   private Future send(ByteBuffer frame, CompletionHandler completionHandler, Boolean useTimeout) {
      return this.write(frame, completionHandler, useTimeout);
   }

   public Future send(byte[] data) {
      this.lock.lock();

      Future var2;
      try {
         this.checkSendingFragment();
         var2 = this.send((TyrusFrame)(new BinaryFrame(data, false, true)), (CompletionHandler)null, true);
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public void send(byte[] data, final SendHandler handler) {
      this.lock.lock();

      try {
         this.checkSendingFragment();
         this.send((TyrusFrame)(new BinaryFrame(data, false, true)), new CompletionHandler() {
            public void failed(Throwable throwable) {
               handler.onResult(new SendResult(throwable));
            }

            public void completed(Frame result) {
               handler.onResult(new SendResult());
            }
         }, true);
      } finally {
         this.lock.unlock();
      }

   }

   public Future send(String data) {
      this.lock.lock();

      Future var2;
      try {
         this.checkSendingFragment();
         var2 = this.send((TyrusFrame)(new TextFrame(data, false, true)));
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   public void send(String data, final SendHandler handler) {
      this.lock.lock();

      try {
         this.checkSendingFragment();
         this.send((TyrusFrame)(new TextFrame(data, false, true)), new CompletionHandler() {
            public void failed(Throwable throwable) {
               handler.onResult(new SendResult(throwable));
            }

            public void completed(Frame result) {
               handler.onResult(new SendResult());
            }
         }, true);
      } finally {
         this.lock.unlock();
      }

   }

   public Future sendRawFrame(ByteBuffer data) {
      this.lock.lock();

      Future var2;
      try {
         this.checkSendingFragment();
         var2 = this.send((ByteBuffer)data, (CompletionHandler)null, true);
      } finally {
         this.lock.unlock();
      }

      return var2;
   }

   private void checkSendingFragment() {
      long timeout = System.currentTimeMillis() + 3000L;

      while(this.sendingFragment != ProtocolHandler.SendingFragmentState.IDLE) {
         long currentTimeMillis = System.currentTimeMillis();
         if (currentTimeMillis >= timeout) {
            throw new IllegalStateException();
         }

         try {
            if (!this.idleCondition.await(timeout - currentTimeMillis, TimeUnit.MILLISECONDS)) {
               throw new IllegalStateException();
            }
         } catch (InterruptedException var6) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(var6);
         }
      }

   }

   public Future stream(boolean last, byte[] bytes, int off, int len) {
      this.lock.lock();

      try {
         Future var6;
         switch (this.sendingFragment) {
            case SENDING_BINARY:
               Future frameFuture = this.send((TyrusFrame)(new BinaryFrame(Arrays.copyOfRange(bytes, off, off + len), true, last)));
               if (last) {
                  this.sendingFragment = ProtocolHandler.SendingFragmentState.IDLE;
                  this.idleCondition.signalAll();
               }

               var6 = frameFuture;
               return var6;
            case SENDING_TEXT:
               this.checkSendingFragment();
               this.sendingFragment = last ? ProtocolHandler.SendingFragmentState.IDLE : ProtocolHandler.SendingFragmentState.SENDING_BINARY;
               var6 = this.send((TyrusFrame)(new BinaryFrame(Arrays.copyOfRange(bytes, off, off + len), false, last)));
               return var6;
            default:
               this.sendingFragment = last ? ProtocolHandler.SendingFragmentState.IDLE : ProtocolHandler.SendingFragmentState.SENDING_BINARY;
               var6 = this.send((TyrusFrame)(new BinaryFrame(Arrays.copyOfRange(bytes, off, off + len), false, last)));
               return var6;
         }
      } finally {
         this.lock.unlock();
      }
   }

   public Future stream(boolean last, String fragment) {
      this.lock.lock();

      try {
         Future var4;
         switch (this.sendingFragment) {
            case SENDING_BINARY:
               this.checkSendingFragment();
               this.sendingFragment = last ? ProtocolHandler.SendingFragmentState.IDLE : ProtocolHandler.SendingFragmentState.SENDING_TEXT;
               var4 = this.send((TyrusFrame)(new TextFrame(fragment, false, last)));
               return var4;
            case SENDING_TEXT:
               Future frameFuture = this.send((TyrusFrame)(new TextFrame(fragment, true, last)));
               if (last) {
                  this.sendingFragment = ProtocolHandler.SendingFragmentState.IDLE;
                  this.idleCondition.signalAll();
               }

               var4 = frameFuture;
               return var4;
            default:
               this.sendingFragment = last ? ProtocolHandler.SendingFragmentState.IDLE : ProtocolHandler.SendingFragmentState.SENDING_TEXT;
               var4 = this.send((TyrusFrame)(new TextFrame(fragment, false, last)));
               return var4;
         }
      } finally {
         this.lock.unlock();
      }
   }

   public synchronized Future close(int code, String reason) {
      CloseReason closeReason = new CloseReason(CloseCodes.getCloseCode(code), reason);
      CloseFrame outgoingCloseFrame;
      if (code != CloseCodes.NO_STATUS_CODE.getCode() && code != CloseCodes.CLOSED_ABNORMALLY.getCode() && code != CloseCodes.TLS_HANDSHAKE_FAILURE.getCode() && (!this.client || code != CloseCodes.SERVICE_RESTART.getCode() && code != CloseCodes.TRY_AGAIN_LATER.getCode())) {
         outgoingCloseFrame = new CloseFrame(closeReason);
      } else {
         outgoingCloseFrame = new CloseFrame(new CloseReason(CloseCodes.NORMAL_CLOSURE, reason));
      }

      Future send = this.send((TyrusFrame)outgoingCloseFrame, (CompletionHandler)null, false);
      this.webSocket.onClose(new CloseFrame(closeReason));
      return send;
   }

   private Future write(TyrusFrame frame, CompletionHandler completionHandler, boolean useTimeout) {
      Writer localWriter = this.writer;
      TyrusFuture future = new TyrusFuture();
      if (localWriter == null) {
         throw new IllegalStateException(LocalizationMessages.CONNECTION_NULL());
      } else {
         ByteBuffer byteBuffer = this.frame(frame);
         localWriter.write(byteBuffer, new CompletionHandlerWrapper(completionHandler, future, frame));
         this.messageEventListener.onFrameSent(frame.getFrameType(), frame.getPayloadLength());
         return future;
      }
   }

   private Future write(ByteBuffer frame, CompletionHandler completionHandler, boolean useTimeout) {
      Writer localWriter = this.writer;
      TyrusFuture future = new TyrusFuture();
      if (localWriter == null) {
         throw new IllegalStateException(LocalizationMessages.CONNECTION_NULL());
      } else {
         localWriter.write(frame, new CompletionHandlerWrapper(completionHandler, future, (Frame)null));
         return future;
      }
   }

   private long decodeLength(byte[] bytes) {
      return Utils.toLong(bytes, 0, bytes.length);
   }

   private byte[] encodeLength(long length) {
      byte[] lengthBytes;
      if (length <= 125L) {
         lengthBytes = new byte[]{(byte)((int)length)};
      } else {
         byte[] b = Utils.toArray(length);
         if (length <= 65535L) {
            lengthBytes = new byte[]{126, 0, 0};
            System.arraycopy(b, 6, lengthBytes, 1, 2);
         } else {
            lengthBytes = new byte[9];
            lengthBytes[0] = 127;
            System.arraycopy(b, 0, lengthBytes, 1, 8);
         }
      }

      return lengthBytes;
   }

   private void validate(byte fragmentType, byte opcode) {
      if (opcode != 0 && opcode != fragmentType && !this.isControlFrame(opcode)) {
         throw new ProtocolException(LocalizationMessages.SEND_MESSAGE_INFRAGMENT());
      }
   }

   private byte checkForLastFrame(Frame frame) {
      byte local = frame.getOpcode();
      if (frame.isControlFrame()) {
         local = (byte)(local | 128);
         return local;
      } else {
         if (!frame.isFin()) {
            if (this.outFragmentedType != 0) {
               local = 0;
            } else {
               this.outFragmentedType = local;
               local = (byte)(local & 127);
            }

            this.validate(this.outFragmentedType, local);
         } else if (this.outFragmentedType != 0) {
            local = -128;
            this.outFragmentedType = 0;
         } else {
            local = (byte)(local | 128);
         }

         return local;
      }
   }

   void doClose() {
      Writer localWriter = this.writer;
      if (localWriter == null) {
         throw new IllegalStateException(LocalizationMessages.CONNECTION_NULL());
      } else {
         try {
            localWriter.close();
         } catch (IOException var3) {
            throw new IllegalStateException(LocalizationMessages.IOEXCEPTION_CLOSE(), var3);
         }
      }
   }

   ByteBuffer frame(Frame frame) {
      if (this.client) {
         frame = Frame.builder(frame).maskingKey(this.maskingKeyGenerator.nextInt()).mask(true).build();
      }

      if (this.extensions != null && this.extensions.size() > 0) {
         Iterator var2 = this.extensions.iterator();

         while(var2.hasNext()) {
            Extension extension = (Extension)var2.next();
            if (extension instanceof ExtendedExtension) {
               try {
                  frame = ((ExtendedExtension)extension).processOutgoing(this.extensionContext, frame);
               } catch (Throwable var11) {
                  LOGGER.log(Level.FINE, LocalizationMessages.EXTENSION_EXCEPTION(extension.getName(), var11.getMessage()), var11);
               }
            }
         }
      }

      byte opcode = this.checkForLastFrame(frame);
      if (frame.isRsv1()) {
         opcode = (byte)(opcode | 64);
      }

      if (frame.isRsv2()) {
         opcode = (byte)(opcode | 32);
      }

      if (frame.isRsv3()) {
         opcode = (byte)(opcode | 16);
      }

      byte[] bytes = frame.getPayloadData();
      byte[] lengthBytes = this.encodeLength(frame.getPayloadLength());
      int payloadLength = (int)frame.getPayloadLength();
      int length = 1 + lengthBytes.length + payloadLength + (this.client ? 4 : 0);
      int payloadStart = 1 + lengthBytes.length + (this.client ? 4 : 0);
      byte[] packet = new byte[length];
      packet[0] = opcode;
      System.arraycopy(lengthBytes, 0, packet, 1, lengthBytes.length);
      if (this.client) {
         Integer maskingKey = frame.getMaskingKey();
         if (maskingKey == null) {
            throw new ProtocolException("Masking key cannot be null when sending message from client to server.");
         }

         Masker masker = new Masker(maskingKey);
         packet[1] = (byte)(packet[1] | 128);
         masker.mask(packet, payloadStart, bytes, payloadLength);
         System.arraycopy(masker.getMask(), 0, packet, payloadStart - 4, 4);
      } else {
         System.arraycopy(bytes, 0, packet, payloadStart, payloadLength);
      }

      return ByteBuffer.wrap(packet);
   }

   public Frame unframe(ByteBuffer buffer) {
      try {
         while(true) {
            switch (this.parsingState.state.get()) {
               case 0:
                  if (buffer.remaining() < 2) {
                     return null;
                  }

                  byte opcode = buffer.get();
                  this.parsingState.finalFragment = this.isBitSet(opcode, 7);
                  this.parsingState.controlFrame = this.isControlFrame(opcode);
                  this.parsingState.opcode = (byte)(opcode & 127);
                  if (!this.parsingState.finalFragment && this.parsingState.controlFrame) {
                     throw new ProtocolException(LocalizationMessages.CONTROL_FRAME_FRAGMENTED());
                  }

                  byte lengthCode = buffer.get();
                  this.parsingState.masked = (lengthCode & 128) == 128;
                  this.parsingState.masker = new Masker(buffer);
                  if (this.parsingState.masked) {
                     lengthCode = (byte)(lengthCode ^ 128);
                  }

                  this.parsingState.lengthCode = lengthCode;
                  this.parsingState.state.incrementAndGet();
                  break;
               case 1:
                  if (this.parsingState.lengthCode <= 125) {
                     this.parsingState.length = (long)this.parsingState.lengthCode;
                  } else {
                     if (this.parsingState.controlFrame) {
                        throw new ProtocolException(LocalizationMessages.CONTROL_FRAME_LENGTH());
                     }

                     int lengthBytes = this.parsingState.lengthCode == 126 ? 2 : 8;
                     if (buffer.remaining() < lengthBytes) {
                        return null;
                     }

                     this.parsingState.masker.setBuffer(buffer);
                     this.parsingState.length = this.decodeLength(this.parsingState.masker.unmask(lengthBytes));
                  }

                  this.parsingState.state.incrementAndGet();
                  break;
               case 2:
                  if (this.parsingState.masked) {
                     if (buffer.remaining() < 4) {
                        return null;
                     }

                     this.parsingState.masker.setBuffer(buffer);
                     this.parsingState.masker.readMask();
                  }

                  this.parsingState.state.incrementAndGet();
                  break;
               case 3:
                  if ((long)buffer.remaining() < this.parsingState.length) {
                     return null;
                  }

                  this.parsingState.masker.setBuffer(buffer);
                  byte[] data = this.parsingState.masker.unmask((int)this.parsingState.length);
                  if ((long)data.length != this.parsingState.length) {
                     throw new ProtocolException(LocalizationMessages.DATA_UNEXPECTED_LENGTH(data.length, this.parsingState.length));
                  }

                  Frame frame = Frame.builder().fin(this.parsingState.finalFragment).rsv1(this.isBitSet(this.parsingState.opcode, 6)).rsv2(this.isBitSet(this.parsingState.opcode, 5)).rsv3(this.isBitSet(this.parsingState.opcode, 4)).opcode((byte)(this.parsingState.opcode & 15)).payloadLength(this.parsingState.length).payloadData(data).build();
                  this.parsingState.recycle();
                  return frame;
               default:
                  throw new IllegalStateException(LocalizationMessages.UNEXPECTED_STATE(this.parsingState.state));
            }
         }
      } catch (Exception var6) {
         this.parsingState.recycle();
         throw (RuntimeException)var6;
      }
   }

   public void process(Frame frame, TyrusWebSocket socket) {
      if (!frame.isRsv1() && !frame.isRsv2() && !frame.isRsv3()) {
         byte opcode = frame.getOpcode();
         boolean fin = frame.isFin();
         if (!frame.isControlFrame()) {
            boolean continuationFrame = opcode == 0;
            if (continuationFrame && !this.processingFragment) {
               throw new ProtocolException(LocalizationMessages.UNEXPECTED_END_FRAGMENT());
            }

            if (this.processingFragment && !continuationFrame) {
               throw new ProtocolException(LocalizationMessages.FRAGMENT_INVALID_OPCODE());
            }

            if (!fin && !continuationFrame) {
               this.processingFragment = true;
            }

            if (!fin && this.inFragmentedType == 0) {
               this.inFragmentedType = opcode;
            }
         }

         TyrusFrame tyrusFrame = TyrusFrame.wrap(frame, this.inFragmentedType, this.remainder);
         if (tyrusFrame instanceof TextFrame) {
            this.remainder = ((TextFrame)tyrusFrame).getRemainder();
         }

         if (!this.client && tyrusFrame.isControlFrame() && tyrusFrame instanceof CloseFrame) {
            CloseReason.CloseCode closeCode = ((CloseFrame)tyrusFrame).getCloseReason().getCloseCode();
            if (closeCode.equals(CloseCodes.SERVICE_RESTART) || closeCode.equals(CloseCodes.TRY_AGAIN_LATER)) {
               throw new ProtocolException("Illegal close code: " + closeCode);
            }
         }

         tyrusFrame.respond(socket);
         if (!tyrusFrame.isControlFrame() && fin) {
            this.inFragmentedType = 0;
            this.processingFragment = false;
         }

      } else {
         throw new ProtocolException(LocalizationMessages.RSV_INCORRECTLY_SET());
      }
   }

   private boolean isControlFrame(byte opcode) {
      return (opcode & 8) == 8;
   }

   private boolean isBitSet(byte b, int bit) {
      return (b >> bit & 1) != 0;
   }

   private static class ParsingState {
      final AtomicInteger state;
      volatile byte opcode;
      volatile long length;
      volatile boolean masked;
      volatile Masker masker;
      volatile boolean finalFragment;
      volatile boolean controlFrame;
      private volatile byte lengthCode;

      private ParsingState() {
         this.state = new AtomicInteger(0);
         this.opcode = -1;
         this.length = -1L;
         this.lengthCode = -1;
      }

      void recycle() {
         this.state.set(0);
         this.opcode = -1;
         this.length = -1L;
         this.lengthCode = -1;
         this.masked = false;
         this.masker = null;
         this.finalFragment = false;
         this.controlFrame = false;
      }

      // $FF: synthetic method
      ParsingState(Object x0) {
         this();
      }
   }

   private static class CompletionHandlerWrapper extends CompletionHandler {
      private final CompletionHandler frameCompletionHandler;
      private final TyrusFuture future;
      private final Frame frame;

      private CompletionHandlerWrapper(CompletionHandler frameCompletionHandler, TyrusFuture future, Frame frame) {
         this.frameCompletionHandler = frameCompletionHandler;
         this.future = future;
         this.frame = frame;
      }

      public void cancelled() {
         if (this.frameCompletionHandler != null) {
            this.frameCompletionHandler.cancelled();
         }

         if (this.future != null) {
            this.future.setFailure(new RuntimeException(LocalizationMessages.FRAME_WRITE_CANCELLED()));
         }

      }

      public void failed(Throwable throwable) {
         if (this.frameCompletionHandler != null) {
            this.frameCompletionHandler.failed(throwable);
         }

         if (this.future != null) {
            this.future.setFailure(throwable);
         }

      }

      public void completed(ByteBuffer result) {
         if (this.frameCompletionHandler != null) {
            this.frameCompletionHandler.completed(this.frame);
         }

         if (this.future != null) {
            this.future.setResult(this.frame);
         }

      }

      public void updated(ByteBuffer result) {
         if (this.frameCompletionHandler != null) {
            this.frameCompletionHandler.updated(this.frame);
         }

      }

      // $FF: synthetic method
      CompletionHandlerWrapper(CompletionHandler x0, TyrusFuture x1, Frame x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private static enum SendingFragmentState {
      IDLE,
      SENDING_TEXT,
      SENDING_BINARY;
   }
}
