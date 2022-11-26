package org.python.netty.util;

public interface ByteProcessor {
   ByteProcessor FIND_NUL = new IndexOfProcessor((byte)0);
   ByteProcessor FIND_NON_NUL = new IndexNotOfProcessor((byte)0);
   ByteProcessor FIND_CR = new IndexOfProcessor((byte)13);
   ByteProcessor FIND_NON_CR = new IndexNotOfProcessor((byte)13);
   ByteProcessor FIND_LF = new IndexOfProcessor((byte)10);
   ByteProcessor FIND_NON_LF = new IndexNotOfProcessor((byte)10);
   ByteProcessor FIND_SEMI_COLON = new IndexOfProcessor((byte)59);
   ByteProcessor FIND_CRLF = new ByteProcessor() {
      public boolean process(byte value) {
         return value != 13 && value != 10;
      }
   };
   ByteProcessor FIND_NON_CRLF = new ByteProcessor() {
      public boolean process(byte value) {
         return value == 13 || value == 10;
      }
   };
   ByteProcessor FIND_LINEAR_WHITESPACE = new ByteProcessor() {
      public boolean process(byte value) {
         return value != 32 && value != 9;
      }
   };
   ByteProcessor FIND_NON_LINEAR_WHITESPACE = new ByteProcessor() {
      public boolean process(byte value) {
         return value == 32 || value == 9;
      }
   };

   boolean process(byte var1) throws Exception;

   public static class IndexNotOfProcessor implements ByteProcessor {
      private final byte byteToNotFind;

      public IndexNotOfProcessor(byte byteToNotFind) {
         this.byteToNotFind = byteToNotFind;
      }

      public boolean process(byte value) {
         return value == this.byteToNotFind;
      }
   }

   public static class IndexOfProcessor implements ByteProcessor {
      private final byte byteToFind;

      public IndexOfProcessor(byte byteToFind) {
         this.byteToFind = byteToFind;
      }

      public boolean process(byte value) {
         return value != this.byteToFind;
      }
   }
}
