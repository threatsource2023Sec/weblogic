package weblogic.websocket.internal;

public class WebSocketMessageFactory {
   private static final WebSocketMessage.Type[] FRAME_TYPES;

   static AbstractWebSocketMessage getInstance(byte[] data, byte[] maskBytes, byte opCode, boolean finalFragment, boolean shouldDecode, boolean inContinuationFrames, Utf8Utils.DecodingContext decodingContext) {
      WebSocketMessage.Type type = getFrameType(opCode);
      unMarkPayloadIfNecessary(data, maskBytes);
      if (isInvalidFrame(type)) {
         throw new WebSocketMessageParsingException(1002, "Invalid message type");
      } else if (isInvalidControlFrame(type, data.length)) {
         throw new WebSocketMessageParsingException(1002, "Control frame should not larger than 125");
      } else if (isInvalidContinuationFrame(type, finalFragment)) {
         throw new WebSocketMessageParsingException(1002, "Control frame cannot be fragmented.");
      } else if (type == WebSocketMessage.Type.CLOSING) {
         return new ClosingMessageImpl(data);
      } else if (finalFragment && type != WebSocketMessage.Type.CONTINUATION) {
         if (type == WebSocketMessage.Type.TEXT && finalFragment) {
            if (inContinuationFrames) {
               throw new WebSocketMessageParsingException(1002, "text frame cannot be sent in the middle of the continuation frames");
            } else {
               return new TextMessage(data);
            }
         } else if (type == WebSocketMessage.Type.BINARY && finalFragment) {
            if (inContinuationFrames) {
               throw new WebSocketMessageParsingException(1002, "binary frame cannot be sent in the middle of the continuation frames");
            } else {
               return new BinaryMessage(data);
            }
         } else if (type == WebSocketMessage.Type.PING) {
            return new PingMessage(data);
         } else if (type == WebSocketMessage.Type.PONG) {
            return new PongMessage(data);
         } else {
            throw new WebSocketMessageParsingException(1002, "Unknown frame");
         }
      } else if (type != WebSocketMessage.Type.CONTINUATION && inContinuationFrames) {
         throw new WebSocketMessageParsingException(1002, "identified continuation head frame in the middle of the continuation frames");
      } else if (type == WebSocketMessage.Type.CONTINUATION && !inContinuationFrames) {
         throw new WebSocketMessageParsingException(1002, "continuation head frame has not sent yet");
      } else {
         return new ContinuationMessage(data, finalFragment, type == WebSocketMessage.Type.TEXT, shouldDecode, decodingContext);
      }
   }

   static WebSocketMessage.Type getFrameType(byte opCode) {
      return FRAME_TYPES[(byte)(opCode & 15)];
   }

   private static boolean isInvalidContinuationFrame(WebSocketMessage.Type type, boolean finalFragment) {
      return !finalFragment && isControlFrame(type);
   }

   private static boolean isInvalidControlFrame(WebSocketMessage.Type type, int payloadLength) {
      return isControlFrame(type) && payloadLength >= 126;
   }

   private static boolean isControlFrame(WebSocketMessage.Type type) {
      return (type.getOpcode() >> 3 & 1) == 1;
   }

   private static boolean isInvalidFrame(WebSocketMessage.Type type) {
      return type == null;
   }

   private static void unMarkPayloadIfNecessary(byte[] payload, byte[] maskBytes) {
      if (maskBytes != null) {
         for(int i = 0; i < payload.length; ++i) {
            payload[i] ^= maskBytes[i % 4];
         }
      }

   }

   static {
      FRAME_TYPES = new WebSocketMessage.Type[]{WebSocketMessage.Type.CONTINUATION, WebSocketMessage.Type.TEXT, WebSocketMessage.Type.BINARY, null, null, null, null, null, WebSocketMessage.Type.CLOSING, WebSocketMessage.Type.PING, WebSocketMessage.Type.PONG, null, null, null, null, null, null};
   }
}
