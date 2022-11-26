package weblogic.utils.classloaders.index;

public class PackageIndices {
   private static final int DEFAULT_ARRAY_SIZE = 8;
   private static final int ARRAY_GROWTH_FACTOR = 2;
   private int packageDefinedIndex;
   private int[] indices;
   private int count;

   public PackageIndices() {
      this.packageDefinedIndex = -1;
      this.indices = new int[8];
   }

   public PackageIndices(int firstIndex) {
      this();
      this.indices[this.count++] = firstIndex;
   }

   public PackageIndices(int[] indices, int packageDefinedIndex) {
      this.packageDefinedIndex = -1;
      this.indices = indices;
      this.packageDefinedIndex = packageDefinedIndex;
      this.count = indices.length;
   }

   public boolean insert(int newIndex) {
      int insertIndex;
      for(insertIndex = 0; insertIndex < this.count; ++insertIndex) {
         int currentIndex = this.indices[insertIndex];
         if (currentIndex >= newIndex) {
            if (newIndex == currentIndex) {
               return false;
            }
            break;
         }
      }

      this.ensureCapacity();
      if (insertIndex == this.count) {
         this.indices[this.count] = newIndex;
      } else {
         System.arraycopy(this.indices, insertIndex, this.indices, insertIndex + 1, this.count - insertIndex);
         this.indices[insertIndex] = newIndex;
      }

      ++this.count;
      return true;
   }

   public void append(int newIndex) {
      if (this.count > 0 && newIndex <= this.indices[this.count - 1]) {
         throw new IllegalArgumentException(newIndex + " <= " + this.indices[this.count - 1]);
      } else {
         this.ensureCapacity();
         this.indices[this.count++] = newIndex;
      }
   }

   int[] rawAccess() {
      return this.indices;
   }

   public int size() {
      return this.count;
   }

   public int get(int offset) {
      return this.indices[offset];
   }

   public int getPackageDefinedIndex() {
      return this.packageDefinedIndex;
   }

   public void setPackageDefinedIndex(int packageDefinedIndex) {
      this.packageDefinedIndex = packageDefinedIndex;
   }

   private void ensureCapacity() {
      if (this.count >= this.indices.length) {
         int oldLength = this.indices.length;
         int newLength = oldLength * 2;
         int[] newIndices = new int[newLength];
         System.arraycopy(this.indices, 0, newIndices, 0, oldLength);
         this.indices = newIndices;
      }

   }

   public static PackageIndices merge(PackageIndices t, int[] indexes) {
      if (indexes != null && indexes.length > 0) {
         if (t == null) {
            return new PackageIndices(indexes, -1);
         } else {
            int[] mergedIndexes = new int[indexes.length + t.count];
            int count = 0;
            int iPos = 0;
            int tPos = 0;

            label38:
            while(true) {
               if (iPos >= indexes.length) {
                  while(true) {
                     if (tPos >= t.count) {
                        break label38;
                     }

                     mergedIndexes[count++] = t.indices[tPos];
                     ++tPos;
                  }
               }

               if (tPos >= t.count) {
                  while(true) {
                     if (iPos >= indexes.length) {
                        break label38;
                     }

                     mergedIndexes[count++] = indexes[iPos];
                     ++iPos;
                  }
               }

               int a = indexes[iPos];
               int b = t.indices[tPos];
               if (a < b) {
                  mergedIndexes[count++] = indexes[iPos++];
               } else if (b < a) {
                  mergedIndexes[count++] = t.indices[tPos++];
               } else {
                  mergedIndexes[count++] = indexes[iPos++];
                  ++tPos;
               }
            }

            PackageIndices m = new PackageIndices(mergedIndexes, t.packageDefinedIndex);
            m.count = count;
            return m;
         }
      } else {
         return t;
      }
   }
}
