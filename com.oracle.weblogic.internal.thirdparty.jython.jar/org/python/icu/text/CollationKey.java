package org.python.icu.text;

public final class CollationKey implements Comparable {
   private byte[] m_key_;
   private String m_source_;
   private int m_hashCode_;
   private int m_length_;
   private static final int MERGE_SEPERATOR_ = 2;

   public CollationKey(String source, byte[] key) {
      this(source, key, -1);
   }

   private CollationKey(String source, byte[] key, int length) {
      this.m_source_ = source;
      this.m_key_ = key;
      this.m_hashCode_ = 0;
      this.m_length_ = length;
   }

   public CollationKey(String source, RawCollationKey key) {
      this.m_source_ = source;
      this.m_length_ = key.size - 1;
      this.m_key_ = key.releaseBytes();

      assert this.m_key_[this.m_length_] == 0;

      this.m_hashCode_ = 0;
   }

   public String getSourceString() {
      return this.m_source_;
   }

   public byte[] toByteArray() {
      int length = this.getLength() + 1;
      byte[] result = new byte[length];
      System.arraycopy(this.m_key_, 0, result, 0, length);
      return result;
   }

   public int compareTo(CollationKey target) {
      int i = 0;

      while(true) {
         int l = this.m_key_[i] & 255;
         int r = target.m_key_[i] & 255;
         if (l < r) {
            return -1;
         }

         if (l > r) {
            return 1;
         }

         if (l == 0) {
            return 0;
         }

         ++i;
      }
   }

   public boolean equals(Object target) {
      return !(target instanceof CollationKey) ? false : this.equals((CollationKey)target);
   }

   public boolean equals(CollationKey target) {
      if (this == target) {
         return true;
      } else if (target == null) {
         return false;
      } else {
         CollationKey other = target;

         for(int i = 0; this.m_key_[i] == other.m_key_[i]; ++i) {
            if (this.m_key_[i] == 0) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      if (this.m_hashCode_ == 0) {
         if (this.m_key_ == null) {
            this.m_hashCode_ = 1;
         } else {
            int size = this.m_key_.length >> 1;
            StringBuilder key = new StringBuilder(size);

            int i;
            for(i = 0; this.m_key_[i] != 0 && this.m_key_[i + 1] != 0; i += 2) {
               key.append((char)(this.m_key_[i] << 8 | 255 & this.m_key_[i + 1]));
            }

            if (this.m_key_[i] != 0) {
               key.append((char)(this.m_key_[i] << 8));
            }

            this.m_hashCode_ = key.toString().hashCode();
         }
      }

      return this.m_hashCode_;
   }

   public CollationKey getBound(int boundType, int noOfLevels) {
      int offset = 0;
      int keystrength = 0;
      if (noOfLevels > 0) {
         label50: {
            do {
               do {
                  if (offset >= this.m_key_.length || this.m_key_[offset] == 0) {
                     break label50;
                  }
               } while(this.m_key_[offset++] != 1);

               ++keystrength;
               --noOfLevels;
            } while(noOfLevels != 0 && offset != this.m_key_.length && this.m_key_[offset] != 0);

            --offset;
         }
      }

      if (noOfLevels > 0) {
         throw new IllegalArgumentException("Source collation key has only " + keystrength + " strength level. Call getBound() again  with noOfLevels < " + keystrength);
      } else {
         byte[] resultkey = new byte[offset + boundType + 1];
         System.arraycopy(this.m_key_, 0, resultkey, 0, offset);
         switch (boundType) {
            case 0:
               break;
            case 1:
               resultkey[offset++] = 2;
               break;
            case 2:
               resultkey[offset++] = -1;
               resultkey[offset++] = -1;
               break;
            default:
               throw new IllegalArgumentException("Illegal boundType argument");
         }

         resultkey[offset] = 0;
         return new CollationKey((String)null, resultkey, offset);
      }
   }

   public CollationKey merge(CollationKey source) {
      if (source != null && source.getLength() != 0) {
         byte[] result = new byte[this.getLength() + source.getLength() + 2];
         int rindex = 0;
         int index = 0;
         int sourceindex = 0;

         while(true) {
            while(this.m_key_[index] < 0 || this.m_key_[index] >= 2) {
               result[rindex++] = this.m_key_[index++];
            }

            for(result[rindex++] = 2; source.m_key_[sourceindex] < 0 || source.m_key_[sourceindex] >= 2; result[rindex++] = source.m_key_[sourceindex++]) {
            }

            if (this.m_key_[index] != 1 || source.m_key_[sourceindex] != 1) {
               int remainingLength;
               if ((remainingLength = this.m_length_ - index) > 0) {
                  System.arraycopy(this.m_key_, index, result, rindex, remainingLength);
                  rindex += remainingLength;
               } else if ((remainingLength = source.m_length_ - sourceindex) > 0) {
                  System.arraycopy(source.m_key_, sourceindex, result, rindex, remainingLength);
                  rindex += remainingLength;
               }

               result[rindex] = 0;

               assert rindex == result.length - 1;

               return new CollationKey((String)null, result, rindex);
            }

            ++index;
            ++sourceindex;
            result[rindex++] = 1;
         }
      } else {
         throw new IllegalArgumentException("CollationKey argument can not be null or of 0 length");
      }
   }

   private int getLength() {
      if (this.m_length_ >= 0) {
         return this.m_length_;
      } else {
         int length = this.m_key_.length;

         for(int index = 0; index < length; ++index) {
            if (this.m_key_[index] == 0) {
               length = index;
               break;
            }
         }

         this.m_length_ = length;
         return this.m_length_;
      }
   }

   public static final class BoundMode {
      public static final int LOWER = 0;
      public static final int UPPER = 1;
      public static final int UPPER_LONG = 2;
      /** @deprecated */
      @Deprecated
      public static final int COUNT = 3;

      private BoundMode() {
      }
   }
}
