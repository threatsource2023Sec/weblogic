package weblogic.websocket.internal;

interface WebSocketMessage {
   String getTextData();

   byte[] getBinaryData();

   Type getType();

   boolean isFinalFragment();

   public static enum Type {
      CONTINUATION((byte)0),
      TEXT((byte)1),
      BINARY((byte)2),
      CLOSING((byte)8),
      PING((byte)9),
      PONG((byte)10);

      private byte opcode;

      private Type(byte opcode) {
         this.opcode = opcode;
      }

      public byte getOpcode() {
         return this.opcode;
      }
   }
}
