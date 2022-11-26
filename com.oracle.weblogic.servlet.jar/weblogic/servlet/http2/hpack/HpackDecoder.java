package weblogic.servlet.http2.hpack;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HpackDecoder {
   private static final int DEFAULT_MAX_HEADERTABLE_SIZE = 4096;
   private DynamicTable dynamicTable;
   private int maxHeaderTableSize;

   public HpackDecoder() {
      this(4096);
   }

   public HpackDecoder(int maxHeaderTableSize) {
      if (maxHeaderTableSize < 0) {
         throw new IllegalArgumentException("Illegal maxHeaderTableSize: " + maxHeaderTableSize);
      } else {
         this.dynamicTable = new DynamicTable(maxHeaderTableSize);
         this.maxHeaderTableSize = maxHeaderTableSize;
      }
   }

   public void decode(ByteBuffer buffer, HeaderListener listener) throws HpackException {
      while(true) {
         if (buffer.hasRemaining()) {
            int origPosition = buffer.position();
            byte firstByte = buffer.get();
            HeaderType headerType = this.detectHeaderType(firstByte);
            buffer.position(buffer.position() - 1);
            if (!headerType.equals(HeaderTypeEnum.TABLE_SIZE_UPDATE)) {
               HeaderEntry entry = this.decodeHeader(buffer);
               if (entry != null) {
                  listener.onRead(entry.getName(), entry.getValue());
                  continue;
               }

               buffer.position(origPosition);
               return;
            }

            if (buffer.remaining() >= HeaderTypeEnum.TABLE_SIZE_UPDATE.getPrefixBits()) {
               this.updateDynamicTableSize(Hpack.decodeInteger(buffer, HeaderTypeEnum.TABLE_SIZE_UPDATE.getPrefixBits()));
               continue;
            }
         }

         return;
      }
   }

   public List decode(ByteBuffer buffer) throws HpackException {
      List headerList = new ArrayList();

      while(buffer.remaining() > 0) {
         HeaderEntry headerEntry = this.decodeHeader(buffer);
         if (headerEntry != null) {
            headerList.add(headerEntry);
         }
      }

      return headerList;
   }

   private HeaderEntry decodeHeader(ByteBuffer buffer) throws HpackException {
      byte firstByte = buffer.get();
      HeaderType headerType = this.detectHeaderType(firstByte);
      buffer.position(buffer.position() - 1);
      if (headerType.equals(HeaderTypeEnum.TABLE_SIZE_UPDATE)) {
         int maxSize = Hpack.decodeInteger(buffer, HeaderTypeEnum.TABLE_SIZE_UPDATE.getPrefixBits());
         this.updateDynamicTableSize(maxSize);
         return null;
      } else {
         return headerType.decoderHeader(buffer, this.dynamicTable);
      }
   }

   public HeaderType detectHeaderType(byte firstByte) {
      HeaderTypeEnum[] var2 = HeaderTypeEnum.values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         HeaderType headerType = var2[var4];
         if (headerType.isTheType(firstByte)) {
            return headerType;
         }
      }

      throw new IllegalArgumentException("Header type [" + firstByte + "] is not a valid type!");
   }

   private void updateDynamicTableSize(int toSize) {
      if (toSize >= 0 && toSize <= Integer.MAX_VALUE) {
         this.maxHeaderTableSize = toSize;
         this.dynamicTable.setMaxTableSize(this.maxHeaderTableSize);
      } else {
         throw new IllegalArgumentException("decoding error with a illegal dynamic table size " + toSize);
      }
   }

   public DynamicTable getDynamicTable() {
      return this.dynamicTable;
   }

   public int getIndexFromTables(HeaderEntry header) {
      int index = StaticTable.getHeaderIndex(header);
      if (index > 0) {
         return index;
      } else {
         index = this.dynamicTable.getIndex(header);
         return index == -1 ? index : index + StaticTable.STATIC_TABLE_LENGTH;
      }
   }
}
