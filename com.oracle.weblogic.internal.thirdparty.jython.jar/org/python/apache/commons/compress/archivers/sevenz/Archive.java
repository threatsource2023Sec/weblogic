package org.python.apache.commons.compress.archivers.sevenz;

import java.util.BitSet;

class Archive {
   long packPos;
   long[] packSizes;
   BitSet packCrcsDefined;
   long[] packCrcs;
   Folder[] folders;
   SubStreamsInfo subStreamsInfo;
   SevenZArchiveEntry[] files;
   StreamMap streamMap;

   public String toString() {
      return "Archive with packed streams starting at offset " + this.packPos + ", " + lengthOf(this.packSizes) + " pack sizes, " + lengthOf(this.packCrcs) + " CRCs, " + lengthOf((Object[])this.folders) + " folders, " + lengthOf((Object[])this.files) + " files and " + this.streamMap;
   }

   private static String lengthOf(long[] a) {
      return a == null ? "(null)" : String.valueOf(a.length);
   }

   private static String lengthOf(Object[] a) {
      return a == null ? "(null)" : String.valueOf(a.length);
   }
}
