package org.python.icu.text;

import java.nio.BufferOverflowException;
import java.util.Arrays;

public final class Edits {
   private static final int MAX_UNCHANGED_LENGTH = 4096;
   private static final int MAX_UNCHANGED = 4095;
   private static final int MAX_SHORT_WIDTH = 6;
   private static final int MAX_SHORT_CHANGE_LENGTH = 4095;
   private static final int MAX_SHORT_CHANGE = 28671;
   private static final int LENGTH_IN_1TRAIL = 61;
   private static final int LENGTH_IN_2TRAIL = 62;
   private static final int STACK_CAPACITY = 100;
   private char[] array = new char[100];
   private int length;
   private int delta;

   public void reset() {
      this.length = this.delta = 0;
   }

   private void setLastUnit(int last) {
      this.array[this.length - 1] = (char)last;
   }

   private int lastUnit() {
      return this.length > 0 ? this.array[this.length - 1] : '\uffff';
   }

   public void addUnchanged(int unchangedLength) {
      if (unchangedLength < 0) {
         throw new IllegalArgumentException("addUnchanged(" + unchangedLength + "): length must not be negative");
      } else {
         int last = this.lastUnit();
         if (last < 4095) {
            int remaining = 4095 - last;
            if (remaining >= unchangedLength) {
               this.setLastUnit(last + unchangedLength);
               return;
            }

            this.setLastUnit(4095);
            unchangedLength -= remaining;
         }

         while(unchangedLength >= 4096) {
            this.append(4095);
            unchangedLength -= 4096;
         }

         if (unchangedLength > 0) {
            this.append(unchangedLength - 1);
         }

      }
   }

   public void addReplace(int oldLength, int newLength) {
      int newDelta;
      if (oldLength == newLength && 0 < oldLength && oldLength <= 6) {
         newDelta = this.lastUnit();
         if (4095 < newDelta && newDelta < 28671 && newDelta >> 12 == oldLength && (newDelta & 4095) < 4095) {
            this.setLastUnit(newDelta + 1);
         } else {
            this.append(oldLength << 12);
         }
      } else if (oldLength >= 0 && newLength >= 0) {
         if (oldLength != 0 || newLength != 0) {
            newDelta = newLength - oldLength;
            if (newDelta != 0) {
               if (newDelta > 0 && this.delta >= 0 && newDelta > Integer.MAX_VALUE - this.delta || newDelta < 0 && this.delta < 0 && newDelta < Integer.MIN_VALUE - this.delta) {
                  throw new IndexOutOfBoundsException();
               }

               this.delta += newDelta;
            }

            int head = 28672;
            if (oldLength < 61 && newLength < 61) {
               head |= oldLength << 6;
               head |= newLength;
               this.append(head);
            } else if (this.array.length - this.length >= 5 || this.growArray()) {
               int limit = this.length + 1;
               if (oldLength < 61) {
                  head |= oldLength << 6;
               } else if (oldLength <= 32767) {
                  head |= 3904;
                  this.array[limit++] = (char)('耀' | oldLength);
               } else {
                  head |= 62 + (oldLength >> 30) << 6;
                  this.array[limit++] = (char)('耀' | oldLength >> 15);
                  this.array[limit++] = (char)('耀' | oldLength);
               }

               if (newLength < 61) {
                  head |= newLength;
               } else if (newLength <= 32767) {
                  head |= 61;
                  this.array[limit++] = (char)('耀' | newLength);
               } else {
                  head |= 62 + (newLength >> 30);
                  this.array[limit++] = (char)('耀' | newLength >> 15);
                  this.array[limit++] = (char)('耀' | newLength);
               }

               this.array[this.length] = (char)head;
               this.length = limit;
            }

         }
      } else {
         throw new IllegalArgumentException("addReplace(" + oldLength + ", " + newLength + "): both lengths must be non-negative");
      }
   }

   private void append(int r) {
      if (this.length < this.array.length || this.growArray()) {
         this.array[this.length++] = (char)r;
      }

   }

   private boolean growArray() {
      int newCapacity;
      if (this.array.length == 100) {
         newCapacity = 2000;
      } else {
         if (this.array.length == Integer.MAX_VALUE) {
            throw new BufferOverflowException();
         }

         if (this.array.length >= 1073741823) {
            newCapacity = Integer.MAX_VALUE;
         } else {
            newCapacity = 2 * this.array.length;
         }
      }

      if (newCapacity - this.array.length < 5) {
         throw new BufferOverflowException();
      } else {
         this.array = Arrays.copyOf(this.array, newCapacity);
         return true;
      }
   }

   public int lengthDelta() {
      return this.delta;
   }

   public boolean hasChanges() {
      if (this.delta != 0) {
         return true;
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (this.array[i] > 4095) {
               return true;
            }
         }

         return false;
      }
   }

   public Iterator getCoarseChangesIterator() {
      return new Iterator(this.array, this.length, true, true);
   }

   public Iterator getCoarseIterator() {
      return new Iterator(this.array, this.length, false, true);
   }

   public Iterator getFineChangesIterator() {
      return new Iterator(this.array, this.length, true, false);
   }

   public Iterator getFineIterator() {
      return new Iterator(this.array, this.length, false, false);
   }

   public static final class Iterator {
      private final char[] array;
      private int index;
      private final int length;
      private int remaining;
      private final boolean onlyChanges_;
      private final boolean coarse;
      private boolean changed;
      private int oldLength_;
      private int newLength_;
      private int srcIndex;
      private int replIndex;
      private int destIndex;

      private Iterator(char[] a, int len, boolean oc, boolean crs) {
         this.array = a;
         this.length = len;
         this.onlyChanges_ = oc;
         this.coarse = crs;
      }

      private int readLength(int head) {
         if (head < 61) {
            return head;
         } else if (head < 62) {
            assert this.index < this.length;

            assert this.array[this.index] >= '耀';

            return this.array[this.index++] & 32767;
         } else {
            assert this.index + 2 <= this.length;

            assert this.array[this.index] >= '耀';

            assert this.array[this.index + 1] >= '耀';

            int len = (head & 1) << 30 | (this.array[this.index] & 32767) << 15 | this.array[this.index + 1] & 32767;
            this.index += 2;
            return len;
         }
      }

      private void updateIndexes() {
         this.srcIndex += this.oldLength_;
         if (this.changed) {
            this.replIndex += this.newLength_;
         }

         this.destIndex += this.newLength_;
      }

      private boolean noNext() {
         this.changed = false;
         this.oldLength_ = this.newLength_ = 0;
         return false;
      }

      public boolean next() {
         return this.next(this.onlyChanges_);
      }

      private boolean next(boolean onlyChanges) {
         this.updateIndexes();
         if (this.remaining > 0) {
            --this.remaining;
            return true;
         } else if (this.index >= this.length) {
            return this.noNext();
         } else {
            int u = this.array[this.index++];
            if (u <= 4095) {
               this.changed = false;

               for(this.oldLength_ = u + 1; this.index < this.length && (u = this.array[this.index]) <= 4095; this.oldLength_ += u + 1) {
                  ++this.index;
               }

               this.newLength_ = this.oldLength_;
               if (!onlyChanges) {
                  return true;
               }

               this.updateIndexes();
               if (this.index >= this.length) {
                  return this.noNext();
               }

               ++this.index;
            }

            this.changed = true;
            int oldLen;
            int newLen;
            if (u <= 28671) {
               if (!this.coarse) {
                  this.oldLength_ = this.newLength_ = u >> 12;
                  this.remaining = u & 4095;
                  return true;
               }

               oldLen = u >> 12;
               newLen = (u & 4095) + 1;
               this.oldLength_ = this.newLength_ = newLen * oldLen;
            } else {
               assert u <= 32767;

               this.oldLength_ = this.readLength(u >> 6 & 63);
               this.newLength_ = this.readLength(u & 63);
               if (!this.coarse) {
                  return true;
               }
            }

            while(this.index < this.length && (u = this.array[this.index]) > 4095) {
               ++this.index;
               if (u <= 28671) {
                  oldLen = u >> 12;
                  newLen = (u & 4095) + 1;
                  newLen *= oldLen;
                  this.oldLength_ += newLen;
                  this.newLength_ += newLen;
               } else {
                  assert u <= 32767;

                  oldLen = this.readLength(u >> 6 & 63);
                  newLen = this.readLength(u & 63);
                  this.oldLength_ += oldLen;
                  this.newLength_ += newLen;
               }
            }

            return true;
         }
      }

      public boolean findSourceIndex(int i) {
         if (i < 0) {
            return false;
         } else {
            if (i < this.srcIndex) {
               this.index = this.remaining = this.oldLength_ = this.newLength_ = this.srcIndex = this.replIndex = this.destIndex = 0;
            } else if (i < this.srcIndex + this.oldLength_) {
               return true;
            }

            while(this.next(false)) {
               if (i < this.srcIndex + this.oldLength_) {
                  return true;
               }

               if (this.remaining > 0) {
                  int len = (this.remaining + 1) * this.oldLength_;
                  if (i < this.srcIndex + len) {
                     int n = (i - this.srcIndex) / this.oldLength_;
                     len = n * this.oldLength_;
                     this.srcIndex += len;
                     this.replIndex += len;
                     this.destIndex += len;
                     this.remaining -= n;
                     return true;
                  }

                  this.oldLength_ = this.newLength_ = len;
                  this.remaining = 0;
               }
            }

            return false;
         }
      }

      public boolean hasChange() {
         return this.changed;
      }

      public int oldLength() {
         return this.oldLength_;
      }

      public int newLength() {
         return this.newLength_;
      }

      public int sourceIndex() {
         return this.srcIndex;
      }

      public int replacementIndex() {
         return this.replIndex;
      }

      public int destinationIndex() {
         return this.destIndex;
      }

      // $FF: synthetic method
      Iterator(char[] x0, int x1, boolean x2, boolean x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
