package org.apache.xmlbeans;

import javax.xml.namespace.QName;

public final class QNameCache {
   private static final float DEFAULT_LOAD = 0.7F;
   private final float loadFactor;
   private int numEntries;
   private int threshold;
   private int hashmask;
   private QName[] table;

   public QNameCache(int initialCapacity, float loadFactor) {
      this.numEntries = 0;

      assert initialCapacity > 0;

      assert loadFactor > 0.0F && loadFactor < 1.0F;

      int capacity;
      for(capacity = 16; capacity < initialCapacity; capacity <<= 1) {
      }

      this.loadFactor = loadFactor;
      this.hashmask = capacity - 1;
      this.threshold = (int)((float)capacity * loadFactor);
      this.table = new QName[capacity];
   }

   public QNameCache(int initialCapacity) {
      this(initialCapacity, 0.7F);
   }

   public QName getName(String uri, String localName) {
      return this.getName(uri, localName, "");
   }

   public QName getName(String uri, String localName, String prefix) {
      assert localName != null;

      if (uri == null) {
         uri = "";
      }

      if (prefix == null) {
         prefix = "";
      }

      int index = hash(uri, localName, prefix) & this.hashmask;

      while(true) {
         QName q = this.table[index];
         if (q == null) {
            ++this.numEntries;
            if (this.numEntries >= this.threshold) {
               this.rehash();
            }

            return this.table[index] = new QName(uri, localName, prefix);
         }

         if (equals(q, uri, localName, prefix)) {
            return q;
         }

         index = index - 1 & this.hashmask;
      }
   }

   private void rehash() {
      int newLength = this.table.length * 2;
      QName[] newTable = new QName[newLength];
      int newHashmask = newLength - 1;

      for(int i = 0; i < this.table.length; ++i) {
         QName q = this.table[i];
         if (q != null) {
            int newIndex;
            for(newIndex = hash(q.getNamespaceURI(), q.getLocalPart(), q.getPrefix()) & newHashmask; newTable[newIndex] != null; newIndex = newIndex - 1 & newHashmask) {
            }

            newTable[newIndex] = q;
         }
      }

      this.table = newTable;
      this.hashmask = newHashmask;
      this.threshold = (int)((float)newLength * this.loadFactor);
   }

   private static int hash(String uri, String localName, String prefix) {
      int h = 0;
      h += prefix.hashCode() << 10;
      h += uri.hashCode() << 5;
      h += localName.hashCode();
      return h;
   }

   private static boolean equals(QName q, String uri, String localName, String prefix) {
      return q.getLocalPart().equals(localName) && q.getNamespaceURI().equals(uri) && q.getPrefix().equals(prefix);
   }
}
