package weblogic.servlet.http2.hpack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class HpackEncoder {
   private int maxHeaderTableSize;
   private boolean noHuffman;
   private DynamicTable dynamicTable;

   public HpackEncoder(int maxHeaderTableSize) {
      this(maxHeaderTableSize, false);
   }

   public HpackEncoder(int maxHeaderTableSize, boolean noHuffman) {
      this.noHuffman = false;
      if (maxHeaderTableSize < 0) {
         throw new IllegalArgumentException("Illegal maxHeaderTableSize: " + maxHeaderTableSize);
      } else {
         this.noHuffman = noHuffman;
         this.maxHeaderTableSize = maxHeaderTableSize;
         this.dynamicTable = new DynamicTable(maxHeaderTableSize);
      }
   }

   public synchronized byte[] encodeHeaders(List headers) throws IOException {
      return this.encodeHeaders(headers, false);
   }

   public byte[] encodeHeaders(List headers, boolean forceIndexing) throws IOException {
      if (headers != null && headers.size() != 0) {
         ByteArrayOutputStream out = new ByteArrayOutputStream();

         HeaderEntry header;
         for(Iterator var4 = headers.iterator(); var4.hasNext(); out.write(this.encodeHeader(header))) {
            header = (HeaderEntry)var4.next();
            if (forceIndexing) {
               header.setUseIndexing(true);
            }
         }

         return out.toByteArray();
      } else {
         throw new IllegalArgumentException("header fileds should not be null");
      }
   }

   public byte[] encodeHeader(HeaderEntry header) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      int entryIndex;
      int nameIndex;
      if (this.maxHeaderTableSize <= 0) {
         entryIndex = StaticTable.getHeaderIndex(header);
         if (entryIndex == -1) {
            nameIndex = StaticTable.getNameIndex(header.getName());
            Hpack.encodeLiteral(header, HeaderTypeEnum.WITHOUT_INDEXING, nameIndex, out, this.noHuffman);
         } else {
            Hpack.encodeInteger(128, 7, entryIndex, out);
         }
      } else if (header.size() > this.maxHeaderTableSize) {
         entryIndex = this.getNameIndexFromTables(header.getName());
         Hpack.encodeLiteral(header, HeaderTypeEnum.WITHOUT_INDEXING, entryIndex, out, this.noHuffman);
      } else {
         entryIndex = this.getIndexFromTables(header);
         if (entryIndex > 0) {
            Hpack.encodeInteger(128, 7, entryIndex, out);
         } else {
            nameIndex = this.getNameIndexFromTables(header.getName());
            if (header.isUseIndexing()) {
               Hpack.encodeLiteral(header, HeaderTypeEnum.WITH_INCREMENTAL_INDEXING, nameIndex, out, this.noHuffman);
               this.dynamicTable.addEntry(header);
            } else {
               Hpack.encodeLiteral(header, nameIndex > 0 ? HeaderTypeEnum.WITHOUT_INDEXING : HeaderTypeEnum.NEVER_INDEXED, nameIndex, out, this.noHuffman);
            }
         }
      }

      return out.toByteArray();
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

   public int getNameIndexFromTables(String name) {
      int index = StaticTable.getNameIndex(name);
      if (index > 0) {
         return index;
      } else {
         if (index == -1) {
            index = this.dynamicTable.getNameIndex(name);
         }

         return index == -1 ? index : index + StaticTable.STATIC_TABLE_LENGTH;
      }
   }

   public DynamicTable getDynamicTable() {
      return this.dynamicTable;
   }
}
