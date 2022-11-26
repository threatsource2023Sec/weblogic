package org.python.icu.impl;

import java.util.Arrays;
import java.util.Comparator;

public class PropsVectors {
   private int[] v;
   private int columns;
   private int maxRows;
   private int rows;
   private int prevRow;
   private boolean isCompacted;
   public static final int FIRST_SPECIAL_CP = 1114112;
   public static final int INITIAL_VALUE_CP = 1114112;
   public static final int ERROR_VALUE_CP = 1114113;
   public static final int MAX_CP = 1114113;
   public static final int INITIAL_ROWS = 4096;
   public static final int MEDIUM_ROWS = 65536;
   public static final int MAX_ROWS = 1114114;

   private boolean areElementsSame(int index1, int[] target, int index2, int length) {
      for(int i = 0; i < length; ++i) {
         if (this.v[index1 + i] != target[index2 + i]) {
            return false;
         }
      }

      return true;
   }

   private int findRow(int rangeStart) {
      int index = false;
      int index = this.prevRow * this.columns;
      if (rangeStart >= this.v[index]) {
         if (rangeStart < this.v[index + 1]) {
            return index;
         }

         index += this.columns;
         if (rangeStart < this.v[index + 1]) {
            ++this.prevRow;
            return index;
         }

         index += this.columns;
         if (rangeStart < this.v[index + 1]) {
            this.prevRow += 2;
            return index;
         }

         if (rangeStart - this.v[index + 1] < 10) {
            this.prevRow += 2;

            do {
               ++this.prevRow;
               index += this.columns;
            } while(rangeStart >= this.v[index + 1]);

            return index;
         }
      } else if (rangeStart < this.v[1]) {
         this.prevRow = 0;
         return 0;
      }

      int start = 0;
      int mid = false;
      int limit = this.rows;

      while(start < limit - 1) {
         int mid = (start + limit) / 2;
         index = this.columns * mid;
         if (rangeStart < this.v[index]) {
            limit = mid;
         } else {
            if (rangeStart < this.v[index + 1]) {
               this.prevRow = mid;
               return index;
            }

            start = mid;
         }
      }

      this.prevRow = start;
      index = start * this.columns;
      return index;
   }

   public PropsVectors(int numOfColumns) {
      if (numOfColumns < 1) {
         throw new IllegalArgumentException("numOfColumns need to be no less than 1; but it is " + numOfColumns);
      } else {
         this.columns = numOfColumns + 2;
         this.v = new int[4096 * this.columns];
         this.maxRows = 4096;
         this.rows = 3;
         this.prevRow = 0;
         this.isCompacted = false;
         this.v[0] = 0;
         this.v[1] = 1114112;
         int index = this.columns;

         for(int cp = 1114112; cp <= 1114113; ++cp) {
            this.v[index] = cp;
            this.v[index + 1] = cp + 1;
            index += this.columns;
         }

      }
   }

   public void setValue(int start, int end, int column, int value, int mask) {
      if (start >= 0 && start <= end && end <= 1114113 && column >= 0 && column < this.columns - 2) {
         if (this.isCompacted) {
            throw new IllegalStateException("Shouldn't be called aftercompact()!");
         } else {
            int limit = end + 1;
            column += 2;
            value &= mask;
            int firstRow = this.findRow(start);
            int lastRow = this.findRow(end);
            boolean splitFirstRow = start != this.v[firstRow] && value != (this.v[firstRow + column] & mask);
            boolean splitLastRow = limit != this.v[lastRow + 1] && value != (this.v[lastRow + column] & mask);
            if (splitFirstRow || splitLastRow) {
               int rowsToExpand = 0;
               if (splitFirstRow) {
                  ++rowsToExpand;
               }

               if (splitLastRow) {
                  ++rowsToExpand;
               }

               int newMaxRows = false;
               if (this.rows + rowsToExpand > this.maxRows) {
                  int newMaxRows;
                  if (this.maxRows < 65536) {
                     newMaxRows = 65536;
                  } else {
                     if (this.maxRows >= 1114114) {
                        throw new IndexOutOfBoundsException("MAX_ROWS exceeded! Increase it to a higher valuein the implementation");
                     }

                     newMaxRows = 1114114;
                  }

                  int[] temp = new int[newMaxRows * this.columns];
                  System.arraycopy(this.v, 0, temp, 0, this.rows * this.columns);
                  this.v = temp;
                  this.maxRows = newMaxRows;
               }

               int count = this.rows * this.columns - (lastRow + this.columns);
               if (count > 0) {
                  System.arraycopy(this.v, lastRow + this.columns, this.v, lastRow + (1 + rowsToExpand) * this.columns, count);
               }

               this.rows += rowsToExpand;
               if (splitFirstRow) {
                  count = lastRow - firstRow + this.columns;
                  System.arraycopy(this.v, firstRow, this.v, firstRow + this.columns, count);
                  lastRow += this.columns;
                  this.v[firstRow + 1] = this.v[firstRow + this.columns] = start;
                  firstRow += this.columns;
               }

               if (splitLastRow) {
                  System.arraycopy(this.v, lastRow, this.v, lastRow + this.columns, this.columns);
                  this.v[lastRow + 1] = this.v[lastRow + this.columns] = limit;
               }
            }

            this.prevRow = lastRow / this.columns;
            firstRow += column;
            lastRow += column;
            mask = ~mask;

            while(true) {
               this.v[firstRow] = this.v[firstRow] & mask | value;
               if (firstRow == lastRow) {
                  return;
               }

               firstRow += this.columns;
            }
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public int getValue(int c, int column) {
      if (!this.isCompacted && c >= 0 && c <= 1114113 && column >= 0 && column < this.columns - 2) {
         int index = this.findRow(c);
         return this.v[index + 2 + column];
      } else {
         return 0;
      }
   }

   public int[] getRow(int rowIndex) {
      if (this.isCompacted) {
         throw new IllegalStateException("Illegal Invocation of the method after compact()");
      } else if (rowIndex >= 0 && rowIndex <= this.rows) {
         int[] rowToReturn = new int[this.columns - 2];
         System.arraycopy(this.v, rowIndex * this.columns + 2, rowToReturn, 0, this.columns - 2);
         return rowToReturn;
      } else {
         throw new IllegalArgumentException("rowIndex out of bound!");
      }
   }

   public int getRowStart(int rowIndex) {
      if (this.isCompacted) {
         throw new IllegalStateException("Illegal Invocation of the method after compact()");
      } else if (rowIndex >= 0 && rowIndex <= this.rows) {
         return this.v[rowIndex * this.columns];
      } else {
         throw new IllegalArgumentException("rowIndex out of bound!");
      }
   }

   public int getRowEnd(int rowIndex) {
      if (this.isCompacted) {
         throw new IllegalStateException("Illegal Invocation of the method after compact()");
      } else if (rowIndex >= 0 && rowIndex <= this.rows) {
         return this.v[rowIndex * this.columns + 1] - 1;
      } else {
         throw new IllegalArgumentException("rowIndex out of bound!");
      }
   }

   public void compact(CompactHandler compactor) {
      if (!this.isCompacted) {
         this.isCompacted = true;
         int valueColumns = this.columns - 2;
         Integer[] indexArray = new Integer[this.rows];

         int count;
         for(count = 0; count < this.rows; ++count) {
            indexArray[count] = this.columns * count;
         }

         Arrays.sort(indexArray, new Comparator() {
            public int compare(Integer o1, Integer o2) {
               int indexOfRow1 = o1;
               int indexOfRow2 = o2;
               int count = PropsVectors.this.columns;
               int index = 2;

               do {
                  if (PropsVectors.this.v[indexOfRow1 + index] != PropsVectors.this.v[indexOfRow2 + index]) {
                     return PropsVectors.this.v[indexOfRow1 + index] < PropsVectors.this.v[indexOfRow2 + index] ? -1 : 1;
                  }

                  ++index;
                  if (index == PropsVectors.this.columns) {
                     index = 0;
                  }

                  --count;
               } while(count > 0);

               return 0;
            }
         });
         count = -valueColumns;

         int i;
         for(int i = 0; i < this.rows; ++i) {
            i = this.v[indexArray[i]];
            if (count < 0 || !this.areElementsSame(indexArray[i] + 2, this.v, indexArray[i - 1] + 2, valueColumns)) {
               count += valueColumns;
            }

            if (i == 1114112) {
               compactor.setRowIndexForInitialValue(count);
            } else if (i == 1114113) {
               compactor.setRowIndexForErrorValue(count);
            }
         }

         count += valueColumns;
         compactor.startRealValues(count);
         int[] temp = new int[count];
         count = -valueColumns;

         for(i = 0; i < this.rows; ++i) {
            int start = this.v[indexArray[i]];
            int limit = this.v[indexArray[i] + 1];
            if (count < 0 || !this.areElementsSame(indexArray[i] + 2, temp, count, valueColumns)) {
               count += valueColumns;
               System.arraycopy(this.v, indexArray[i] + 2, temp, count, valueColumns);
            }

            if (start < 1114112) {
               compactor.setRowIndexForRange(start, limit - 1, count);
            }
         }

         this.v = temp;
         this.rows = count / valueColumns + 1;
      }
   }

   public int[] getCompactedArray() {
      if (!this.isCompacted) {
         throw new IllegalStateException("Illegal Invocation of the method before compact()");
      } else {
         return this.v;
      }
   }

   public int getCompactedRows() {
      if (!this.isCompacted) {
         throw new IllegalStateException("Illegal Invocation of the method before compact()");
      } else {
         return this.rows;
      }
   }

   public int getCompactedColumns() {
      if (!this.isCompacted) {
         throw new IllegalStateException("Illegal Invocation of the method before compact()");
      } else {
         return this.columns - 2;
      }
   }

   public IntTrie compactToTrieWithRowIndexes() {
      PVecToTrieCompactHandler compactor = new PVecToTrieCompactHandler();
      this.compact(compactor);
      return compactor.builder.serialize(new DefaultGetFoldedValue(compactor.builder), new DefaultGetFoldingOffset());
   }

   public interface CompactHandler {
      void setRowIndexForRange(int var1, int var2, int var3);

      void setRowIndexForInitialValue(int var1);

      void setRowIndexForErrorValue(int var1);

      void startRealValues(int var1);
   }

   private static class DefaultGetFoldedValue implements TrieBuilder.DataManipulate {
      private IntTrieBuilder builder;

      public DefaultGetFoldedValue(IntTrieBuilder inBuilder) {
         this.builder = inBuilder;
      }

      public int getFoldedValue(int start, int offset) {
         int initialValue = this.builder.m_initialValue_;
         int limit = start + 1024;

         while(start < limit) {
            boolean[] inBlockZero = new boolean[1];
            int value = this.builder.getValue(start, inBlockZero);
            if (inBlockZero[0]) {
               start += 32;
            } else {
               if (value != initialValue) {
                  return offset;
               }

               ++start;
            }
         }

         return 0;
      }
   }

   private static class DefaultGetFoldingOffset implements Trie.DataManipulate {
      private DefaultGetFoldingOffset() {
      }

      public int getFoldingOffset(int value) {
         return value;
      }

      // $FF: synthetic method
      DefaultGetFoldingOffset(Object x0) {
         this();
      }
   }
}
