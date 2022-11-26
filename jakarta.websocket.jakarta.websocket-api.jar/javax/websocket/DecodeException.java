package javax.websocket;

import java.nio.ByteBuffer;

public class DecodeException extends Exception {
   private final ByteBuffer bb;
   private final String encodedString;
   private static final long serialVersionUID = 6L;

   public DecodeException(ByteBuffer bb, String message, Throwable cause) {
      super(message, cause);
      this.encodedString = null;
      this.bb = bb;
   }

   public DecodeException(String encodedString, String message, Throwable cause) {
      super(message, cause);
      this.encodedString = encodedString;
      this.bb = null;
   }

   public DecodeException(ByteBuffer bb, String message) {
      super(message);
      this.encodedString = null;
      this.bb = bb;
   }

   public DecodeException(String encodedString, String message) {
      super(message);
      this.encodedString = encodedString;
      this.bb = null;
   }

   public ByteBuffer getBytes() {
      return this.bb;
   }

   public String getText() {
      return this.encodedString;
   }
}
