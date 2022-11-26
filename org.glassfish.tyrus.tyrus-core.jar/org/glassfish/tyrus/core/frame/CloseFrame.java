package org.glassfish.tyrus.core.frame;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import org.glassfish.tyrus.core.CloseReasons;
import org.glassfish.tyrus.core.ProtocolException;
import org.glassfish.tyrus.core.StrictUtf8;
import org.glassfish.tyrus.core.TyrusWebSocket;
import org.glassfish.tyrus.core.Utf8DecodingException;
import org.glassfish.tyrus.core.Utils;

public class CloseFrame extends TyrusFrame {
   private final CloseReason closeReason;
   private static final byte[] EMPTY_BYTES = new byte[0];

   public CloseFrame(Frame frame) {
      super(frame, TyrusFrame.FrameType.CLOSE);
      final byte[] data = frame.getPayloadData();
      if (data.length < 2) {
         throw new ProtocolException("Closing wrappedFrame payload, if present, must be a minimum of 2 bytes in length") {
            private static final long serialVersionUID = -5720682492584668231L;

            public CloseReason getCloseReason() {
               return data.length == 0 ? CloseReasons.NORMAL_CLOSURE.getCloseReason() : super.getCloseReason();
            }
         };
      } else {
         int closeCode = (int)Utils.toLong(data, 0, 2);
         if (closeCode >= 1000 && closeCode != 1004 && closeCode != 1005 && closeCode != 1006 && (closeCode <= 1013 || closeCode >= 3000) && closeCode <= 4999) {
            String closeReasonString;
            if (data.length > 2) {
               closeReasonString = this.utf8Decode(data);
            } else {
               closeReasonString = null;
            }

            this.closeReason = new CloseReason(CloseCodes.getCloseCode(closeCode), closeReasonString);
         } else {
            throw new ProtocolException("Illegal status code: " + closeCode);
         }
      }
   }

   public CloseFrame(CloseReason closeReason) {
      super(Frame.builder().fin(true).opcode((byte)8).payloadData(getPayload(closeReason.getCloseCode().getCode(), closeReason.getReasonPhrase())).build(), TyrusFrame.FrameType.CLOSE);
      this.closeReason = closeReason;
   }

   public CloseReason getCloseReason() {
      return this.closeReason;
   }

   public void respond(TyrusWebSocket socket) {
      socket.onClose(this);
      socket.close();
   }

   private String utf8Decode(byte[] data) {
      ByteBuffer b = ByteBuffer.wrap(data, 2, data.length - 2);
      Charset charset = new StrictUtf8();
      CharsetDecoder decoder = charset.newDecoder();
      int n = (int)((float)b.remaining() * decoder.averageCharsPerByte());
      CharBuffer cb = CharBuffer.allocate(n);

      CoderResult result;
      do {
         while(true) {
            result = decoder.decode(b, cb, true);
            if (result.isUnderflow()) {
               decoder.flush(cb);
               cb.flip();
               String reason = cb.toString();
               return reason;
            }

            if (!result.isOverflow()) {
               break;
            }

            CharBuffer tmp = CharBuffer.allocate(2 * n + 1);
            cb.flip();
            tmp.put(cb);
            cb = tmp;
         }
      } while(!result.isError() && !result.isMalformed());

      throw new Utf8DecodingException();
   }

   private static byte[] getPayload(int closeCode, String closeReason) {
      if (closeCode == -1) {
         return EMPTY_BYTES;
      } else {
         byte[] bytes = Utils.toArray((long)closeCode);
         byte[] reasonBytes = closeReason == null ? EMPTY_BYTES : closeReason.getBytes(new StrictUtf8());
         byte[] frameBytes = new byte[2 + reasonBytes.length];
         System.arraycopy(bytes, bytes.length - 2, frameBytes, 0, 2);
         System.arraycopy(reasonBytes, 0, frameBytes, 2, reasonBytes.length);
         return frameBytes;
      }
   }
}
