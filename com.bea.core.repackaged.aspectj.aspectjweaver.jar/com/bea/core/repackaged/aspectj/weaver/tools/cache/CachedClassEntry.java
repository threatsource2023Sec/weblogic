package com.bea.core.repackaged.aspectj.weaver.tools.cache;

public class CachedClassEntry {
   private final CachedClassReference ref;
   private final byte[] weavedBytes;
   private final EntryType type;

   public CachedClassEntry(CachedClassReference ref, byte[] weavedBytes, EntryType type) {
      this.weavedBytes = weavedBytes;
      this.ref = ref;
      this.type = type;
   }

   public String getClassName() {
      return this.ref.getClassName();
   }

   public byte[] getBytes() {
      return this.weavedBytes;
   }

   public String getKey() {
      return this.ref.getKey();
   }

   public boolean isGenerated() {
      return this.type == CachedClassEntry.EntryType.GENERATED;
   }

   public boolean isWeaved() {
      return this.type == CachedClassEntry.EntryType.WEAVED;
   }

   public boolean isIgnored() {
      return this.type == CachedClassEntry.EntryType.IGNORED;
   }

   public int hashCode() {
      return this.getClassName().hashCode() + this.getKey().hashCode() + this.type.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         CachedClassEntry other = (CachedClassEntry)obj;
         return this.getClassName().equals(other.getClassName()) && this.getKey().equals(other.getKey()) && this.type == other.type;
      }
   }

   public String toString() {
      return this.getClassName() + "[" + this.type + "]";
   }

   static enum EntryType {
      GENERATED,
      WEAVED,
      IGNORED;
   }
}
