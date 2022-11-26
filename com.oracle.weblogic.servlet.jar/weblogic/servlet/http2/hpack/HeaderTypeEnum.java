package weblogic.servlet.http2.hpack;

import java.nio.ByteBuffer;
import weblogic.utils.StringUtils;

public enum HeaderTypeEnum implements HeaderType {
   INDEXED(128, 7) {
      public boolean isTheType(byte b) {
         return (b & this.getMask()) != 0;
      }

      public HeaderEntry decoderHeader(ByteBuffer buffer, DynamicTable dynamicTable) throws HpackException {
         int index = Hpack.decodeInteger(buffer, 7);
         if (index > 0) {
            HeaderEntry headerField;
            if (index <= StaticTable.STATIC_TABLE_LENGTH) {
               headerField = StaticTable.STATIC_TABLE[index];
            } else {
               if (index - StaticTable.STATIC_TABLE_LENGTH > dynamicTable.length()) {
                  throw new HpackException("The index [" + index + "] is not in the indexing table");
               }

               headerField = dynamicTable.getEntry(index - StaticTable.STATIC_TABLE_LENGTH - 1);
            }

            return headerField;
         } else {
            return null;
         }
      }
   },
   WITH_INCREMENTAL_INDEXING(64, 6) {
      public boolean isTheType(byte b) {
         return (b & this.getMask()) != 0;
      }

      public HeaderEntry decoderHeader(ByteBuffer buffer, DynamicTable dynamicTable) throws HpackException {
         HeaderEntry headerField = HeaderTypeEnum.decoderHeader(buffer, dynamicTable, HeaderTypeEnum.WITH_INCREMENTAL_INDEXING);
         if (headerField == null) {
            return null;
         } else {
            dynamicTable.addEntry(headerField);
            return headerField;
         }
      }
   },
   WITHOUT_INDEXING(0, 4) {
      public boolean isTheType(byte b) {
         return b >> 4 == 0;
      }

      public HeaderEntry decoderHeader(ByteBuffer buffer, DynamicTable dynamicTable) throws HpackException {
         return HeaderTypeEnum.decoderHeader(buffer, dynamicTable, HeaderTypeEnum.WITHOUT_INDEXING);
      }
   },
   NEVER_INDEXED(16, 4) {
      public boolean isTheType(byte b) {
         return b >> 4 == 1;
      }

      public HeaderEntry decoderHeader(ByteBuffer buffer, DynamicTable dynamicTable) throws HpackException {
         return HeaderTypeEnum.decoderHeader(buffer, dynamicTable, HeaderTypeEnum.NEVER_INDEXED);
      }
   },
   TABLE_SIZE_UPDATE(32, 5) {
      public boolean isTheType(byte b) {
         return b >> 5 == 1;
      }

      public HeaderEntry decoderHeader(ByteBuffer buffer, DynamicTable dynamicTable) throws HpackException {
         throw new HpackException("HeaderTypeEnum.TABLE_SIZE_UPDATE does not support decoderHeader() operation");
      }
   };

   private int mask;
   private int prefixBits;

   private HeaderTypeEnum(int mask, int prefixBits) {
      this.mask = 0;
      this.prefixBits = 0;
      this.mask = mask;
      this.prefixBits = prefixBits;
   }

   public int getMask() {
      return this.mask;
   }

   public int getPrefixBits() {
      return this.prefixBits;
   }

   public boolean isTheType(byte b) {
      return false;
   }

   public static HeaderEntry decoderHeader(ByteBuffer buffer, DynamicTable dynamicTable, HeaderType headerType) throws HpackException {
      String headerName = null;
      int index = Hpack.decodeInteger(buffer, headerType.getPrefixBits());
      if (index == -1) {
         throw new HpackException("The index value could not be parsed by " + headerType.toString());
      } else {
         HeaderEntry headerField;
         byte[] headerValue;
         if (index == 0) {
            headerValue = Hpack.decodeStringLiteral(buffer);
            if (headerValue == null) {
               return null;
            }

            headerName = StringUtils.getString(headerValue);
         } else {
            if (index <= StaticTable.STATIC_TABLE_LENGTH) {
               headerField = StaticTable.STATIC_TABLE[index];
            } else {
               if (index - StaticTable.STATIC_TABLE_LENGTH > dynamicTable.length()) {
                  throw new HpackException("The index [" + index + "] is not in the indexing table");
               }

               headerField = dynamicTable.getEntry(index - StaticTable.STATIC_TABLE_LENGTH - 1);
            }

            headerName = headerField.getName();
         }

         headerValue = Hpack.decodeStringLiteral(buffer);
         if (headerValue == null) {
            return null;
         } else {
            headerField = new HeaderEntry(headerName, headerValue);
            return headerField;
         }
      }
   }

   // $FF: synthetic method
   HeaderTypeEnum(int x2, int x3, Object x4) {
      this(x2, x3);
   }
}
