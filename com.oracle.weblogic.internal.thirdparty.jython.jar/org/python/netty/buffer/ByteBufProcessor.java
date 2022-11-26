package org.python.netty.buffer;

import org.python.netty.util.ByteProcessor;

/** @deprecated */
@Deprecated
public interface ByteBufProcessor extends ByteProcessor {
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_NUL = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value != 0;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_NON_NUL = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value == 0;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_CR = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value != 13;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_NON_CR = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value == 13;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_LF = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value != 10;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_NON_LF = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value == 10;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_CRLF = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value != 13 && value != 10;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_NON_CRLF = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value == 13 || value == 10;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_LINEAR_WHITESPACE = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value != 32 && value != 9;
      }
   };
   /** @deprecated */
   @Deprecated
   ByteBufProcessor FIND_NON_LINEAR_WHITESPACE = new ByteBufProcessor() {
      public boolean process(byte value) throws Exception {
         return value == 32 || value == 9;
      }
   };
}
