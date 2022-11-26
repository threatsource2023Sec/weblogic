package org.python.icu.impl.coll;

public final class CollationKeys {
   public static final LevelCallback SIMPLE_LEVEL_FALLBACK = new LevelCallback();
   private static final int SEC_COMMON_LOW = 5;
   private static final int SEC_COMMON_MIDDLE = 37;
   static final int SEC_COMMON_HIGH = 69;
   private static final int SEC_COMMON_MAX_COUNT = 33;
   private static final int CASE_LOWER_FIRST_COMMON_LOW = 1;
   private static final int CASE_LOWER_FIRST_COMMON_MIDDLE = 7;
   private static final int CASE_LOWER_FIRST_COMMON_HIGH = 13;
   private static final int CASE_LOWER_FIRST_COMMON_MAX_COUNT = 7;
   private static final int CASE_UPPER_FIRST_COMMON_LOW = 3;
   private static final int CASE_UPPER_FIRST_COMMON_HIGH = 15;
   private static final int CASE_UPPER_FIRST_COMMON_MAX_COUNT = 13;
   private static final int TER_ONLY_COMMON_LOW = 5;
   private static final int TER_ONLY_COMMON_MIDDLE = 101;
   private static final int TER_ONLY_COMMON_HIGH = 197;
   private static final int TER_ONLY_COMMON_MAX_COUNT = 97;
   private static final int TER_LOWER_FIRST_COMMON_LOW = 5;
   private static final int TER_LOWER_FIRST_COMMON_MIDDLE = 37;
   private static final int TER_LOWER_FIRST_COMMON_HIGH = 69;
   private static final int TER_LOWER_FIRST_COMMON_MAX_COUNT = 33;
   private static final int TER_UPPER_FIRST_COMMON_LOW = 133;
   private static final int TER_UPPER_FIRST_COMMON_MIDDLE = 165;
   private static final int TER_UPPER_FIRST_COMMON_HIGH = 197;
   private static final int TER_UPPER_FIRST_COMMON_MAX_COUNT = 33;
   private static final int QUAT_COMMON_LOW = 28;
   private static final int QUAT_COMMON_MIDDLE = 140;
   private static final int QUAT_COMMON_HIGH = 252;
   private static final int QUAT_COMMON_MAX_COUNT = 113;
   private static final int QUAT_SHIFTED_LIMIT_BYTE = 27;
   private static final int[] levelMasks = new int[]{2, 6, 22, 54, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 54};

   private static SortKeyLevel getSortKeyLevel(int levels, int level) {
      return (levels & level) != 0 ? new SortKeyLevel() : null;
   }

   private CollationKeys() {
   }

   public static void writeSortKeyUpToQuaternary(CollationIterator iter, boolean[] compressibleBytes, CollationSettings settings, SortKeyByteSink sink, int minLevel, LevelCallback callback, boolean preflight) {
      int options = settings.options;
      int levels = levelMasks[CollationSettings.getStrength(options)];
      if ((options & 1024) != 0) {
         levels |= 8;
      }

      levels &= ~((1 << minLevel) - 1);
      if (levels != 0) {
         long variableTop;
         if ((options & 12) == 0) {
            variableTop = 0L;
         } else {
            variableTop = settings.variableTop + 1L;
         }

         int tertiaryMask = CollationSettings.getTertiaryMask(options);
         byte[] p234 = new byte[3];
         SortKeyLevel cases = getSortKeyLevel(levels, 8);
         SortKeyLevel secondaries = getSortKeyLevel(levels, 4);
         SortKeyLevel tertiaries = getSortKeyLevel(levels, 16);
         SortKeyLevel quaternaries = getSortKeyLevel(levels, 32);
         long prevReorderedPrimary = 0L;
         int commonCases = 0;
         int commonSecondaries = 0;
         int commonTertiaries = 0;
         int commonQuaternaries = 0;
         int prevSecondary = 0;
         int secSegmentStart = 0;

         int lower32;
         do {
            long p;
            int t;
            int b;
            do {
               iter.clearCEsIfNoneRemaining();
               long ce = iter.nextCE();
               p = ce >>> 32;
               if (p < variableTop && p > 33554432L) {
                  if (commonQuaternaries != 0) {
                     --commonQuaternaries;

                     while(commonQuaternaries >= 113) {
                        quaternaries.appendByte(140);
                        commonQuaternaries -= 113;
                     }

                     quaternaries.appendByte(28 + commonQuaternaries);
                     commonQuaternaries = 0;
                  }

                  do {
                     if ((levels & 32) != 0) {
                        if (settings.hasReordering()) {
                           p = settings.reorder(p);
                        }

                        if ((int)p >>> 24 >= 27) {
                           quaternaries.appendByte(27);
                        }

                        quaternaries.appendWeight32(p);
                     }

                     do {
                        ce = iter.nextCE();
                        p = ce >>> 32;
                     } while(p == 0L);
                  } while(p < variableTop && p > 33554432L);
               }

               if (p > 1L && (levels & 2) != 0) {
                  boolean isCompressible = compressibleBytes[(int)p >>> 24];
                  if (settings.hasReordering()) {
                     p = settings.reorder(p);
                  }

                  t = (int)p >>> 24;
                  if (!isCompressible || t != (int)prevReorderedPrimary >>> 24) {
                     if (prevReorderedPrimary != 0L) {
                        if (p < prevReorderedPrimary) {
                           if (t > 2) {
                              sink.Append(3);
                           }
                        } else {
                           sink.Append(255);
                        }
                     }

                     sink.Append(t);
                     if (isCompressible) {
                        prevReorderedPrimary = p;
                     } else {
                        prevReorderedPrimary = 0L;
                     }
                  }

                  b = (byte)((int)(p >>> 16));
                  if (b != 0) {
                     p234[0] = (byte)b;
                     p234[1] = (byte)((int)(p >>> 8));
                     p234[2] = (byte)((int)p);
                     sink.Append(p234, p234[1] == 0 ? 1 : (p234[2] == 0 ? 2 : 3));
                  }

                  if (!preflight && sink.Overflowed()) {
                     return;
                  }
               }

               lower32 = (int)ce;
            } while(lower32 == 0);

            if ((levels & 4) != 0) {
               t = lower32 >>> 16;
               if (t != 0) {
                  if (t == 1280 && ((options & 2048) == 0 || p != 33554432L)) {
                     ++commonSecondaries;
                  } else if ((options & 2048) == 0) {
                     if (commonSecondaries != 0) {
                        --commonSecondaries;

                        while(commonSecondaries >= 33) {
                           secondaries.appendByte(37);
                           commonSecondaries -= 33;
                        }

                        if (t < 1280) {
                           b = 5 + commonSecondaries;
                        } else {
                           b = 69 - commonSecondaries;
                        }

                        secondaries.appendByte(b);
                        commonSecondaries = 0;
                     }

                     secondaries.appendWeight16(t);
                  } else {
                     int last;
                     if (commonSecondaries != 0) {
                        --commonSecondaries;
                        b = commonSecondaries % 33;
                        if (prevSecondary < 1280) {
                           last = 5 + b;
                        } else {
                           last = 69 - b;
                        }

                        secondaries.appendByte(last);

                        for(commonSecondaries -= b; commonSecondaries > 0; commonSecondaries -= 33) {
                           secondaries.appendByte(37);
                        }
                     }

                     if (0L < p && p <= 33554432L) {
                        byte[] secs = secondaries.data();

                        byte b;
                        for(last = secondaries.length() - 1; secSegmentStart < last; secs[last--] = b) {
                           b = secs[secSegmentStart];
                           secs[secSegmentStart++] = secs[last];
                        }

                        secondaries.appendByte(p == 1L ? 1 : 2);
                        prevSecondary = 0;
                        secSegmentStart = secondaries.length();
                     } else {
                        secondaries.appendReverseWeight16(t);
                        prevSecondary = t;
                     }
                  }
               }
            }

            if ((levels & 8) != 0) {
               label534: {
                  if (CollationSettings.getStrength(options) == 0) {
                     if (p == 0L) {
                        break label534;
                     }
                  } else if (lower32 >>> 16 == 0) {
                     break label534;
                  }

                  t = lower32 >>> 8 & 255;

                  assert (t & 192) != 192;

                  if ((t & 192) == 0 && t > 1) {
                     ++commonCases;
                  } else {
                     if ((options & 256) == 0) {
                        if (commonCases != 0 && (t > 1 || !cases.isEmpty())) {
                           --commonCases;

                           while(commonCases >= 7) {
                              cases.appendByte(112);
                              commonCases -= 7;
                           }

                           if (t <= 1) {
                              b = 1 + commonCases;
                           } else {
                              b = 13 - commonCases;
                           }

                           cases.appendByte(b << 4);
                           commonCases = 0;
                        }

                        if (t > 1) {
                           t = 13 + (t >>> 6) << 4;
                        }
                     } else {
                        if (commonCases != 0) {
                           --commonCases;

                           while(commonCases >= 13) {
                              cases.appendByte(48);
                              commonCases -= 13;
                           }

                           cases.appendByte(3 + commonCases << 4);
                           commonCases = 0;
                        }

                        if (t > 1) {
                           t = 3 - (t >>> 6) << 4;
                        }
                     }

                     cases.appendByte(t);
                  }
               }
            }

            if ((levels & 16) != 0) {
               t = lower32 & tertiaryMask;

               assert (lower32 & '쀀') != 49152;

               if (t == 1280) {
                  ++commonTertiaries;
               } else if ((tertiaryMask & '耀') == 0) {
                  if (commonTertiaries != 0) {
                     --commonTertiaries;

                     while(commonTertiaries >= 97) {
                        tertiaries.appendByte(101);
                        commonTertiaries -= 97;
                     }

                     if (t < 1280) {
                        b = 5 + commonTertiaries;
                     } else {
                        b = 197 - commonTertiaries;
                     }

                     tertiaries.appendByte(b);
                     commonTertiaries = 0;
                  }

                  if (t > 1280) {
                     t += 49152;
                  }

                  tertiaries.appendWeight16(t);
               } else if ((options & 256) == 0) {
                  if (commonTertiaries != 0) {
                     --commonTertiaries;

                     while(commonTertiaries >= 33) {
                        tertiaries.appendByte(37);
                        commonTertiaries -= 33;
                     }

                     if (t < 1280) {
                        b = 5 + commonTertiaries;
                     } else {
                        b = 69 - commonTertiaries;
                     }

                     tertiaries.appendByte(b);
                     commonTertiaries = 0;
                  }

                  if (t > 1280) {
                     t += 16384;
                  }

                  tertiaries.appendWeight16(t);
               } else {
                  if (t > 256) {
                     if (lower32 >>> 16 != 0) {
                        t ^= 49152;
                        if (t < 50432) {
                           t -= 16384;
                        }
                     } else {
                        assert 34304 <= t && t <= 49151;

                        t += 16384;
                     }
                  }

                  if (commonTertiaries != 0) {
                     --commonTertiaries;

                     while(commonTertiaries >= 33) {
                        tertiaries.appendByte(165);
                        commonTertiaries -= 33;
                     }

                     if (t < 34048) {
                        b = 133 + commonTertiaries;
                     } else {
                        b = 197 - commonTertiaries;
                     }

                     tertiaries.appendByte(b);
                     commonTertiaries = 0;
                  }

                  tertiaries.appendWeight16(t);
               }
            }

            if ((levels & 32) != 0) {
               t = lower32 & '\uffff';
               if ((t & 192) == 0 && t > 256) {
                  ++commonQuaternaries;
               } else if (t == 256 && (options & 12) == 0 && quaternaries.isEmpty()) {
                  quaternaries.appendByte(1);
               } else {
                  if (t == 256) {
                     t = 1;
                  } else {
                     t = 252 + (t >>> 6 & 3);
                  }

                  if (commonQuaternaries != 0) {
                     --commonQuaternaries;

                     while(commonQuaternaries >= 113) {
                        quaternaries.appendByte(140);
                        commonQuaternaries -= 113;
                     }

                     if (t < 28) {
                        b = 28 + commonQuaternaries;
                     } else {
                        b = 252 - commonQuaternaries;
                     }

                     quaternaries.appendByte(b);
                     commonQuaternaries = 0;
                  }

                  quaternaries.appendByte(t);
               }
            }
         } while(lower32 >>> 24 != 1);

         if ((levels & 4) != 0) {
            if (!callback.needToWrite(2)) {
               return;
            }

            sink.Append(1);
            secondaries.appendTo(sink);
         }

         if ((levels & 8) != 0) {
            if (!callback.needToWrite(3)) {
               return;
            }

            sink.Append(1);
            int length = cases.length() - 1;
            byte b = 0;
            int i = 0;

            while(true) {
               if (i >= length) {
                  if (b != 0) {
                     sink.Append(b);
                  }
                  break;
               }

               byte c = cases.getAt(i);

               assert (c & 15) == 0 && c != 0;

               if (b == 0) {
                  b = c;
               } else {
                  sink.Append(b | c >> 4 & 15);
                  b = 0;
               }

               ++i;
            }
         }

         if ((levels & 16) != 0) {
            if (!callback.needToWrite(4)) {
               return;
            }

            sink.Append(1);
            tertiaries.appendTo(sink);
         }

         if ((levels & 32) != 0) {
            if (!callback.needToWrite(5)) {
               return;
            }

            sink.Append(1);
            quaternaries.appendTo(sink);
         }

      }
   }

   private static final class SortKeyLevel {
      private static final int INITIAL_CAPACITY = 40;
      byte[] buffer = new byte[40];
      int len = 0;

      SortKeyLevel() {
      }

      boolean isEmpty() {
         return this.len == 0;
      }

      int length() {
         return this.len;
      }

      byte getAt(int index) {
         return this.buffer[index];
      }

      byte[] data() {
         return this.buffer;
      }

      void appendByte(int b) {
         if (this.len < this.buffer.length || this.ensureCapacity(1)) {
            this.buffer[this.len++] = (byte)b;
         }

      }

      void appendWeight16(int w) {
         assert (w & '\uffff') != 0;

         byte b0 = (byte)(w >>> 8);
         byte b1 = (byte)w;
         int appendLength = b1 == 0 ? 1 : 2;
         if (this.len + appendLength <= this.buffer.length || this.ensureCapacity(appendLength)) {
            this.buffer[this.len++] = b0;
            if (b1 != 0) {
               this.buffer[this.len++] = b1;
            }
         }

      }

      void appendWeight32(long w) {
         assert w != 0L;

         byte[] bytes = new byte[]{(byte)((int)(w >>> 24)), (byte)((int)(w >>> 16)), (byte)((int)(w >>> 8)), (byte)((int)w)};
         int appendLength = bytes[1] == 0 ? 1 : (bytes[2] == 0 ? 2 : (bytes[3] == 0 ? 3 : 4));
         if (this.len + appendLength <= this.buffer.length || this.ensureCapacity(appendLength)) {
            this.buffer[this.len++] = bytes[0];
            if (bytes[1] != 0) {
               this.buffer[this.len++] = bytes[1];
               if (bytes[2] != 0) {
                  this.buffer[this.len++] = bytes[2];
                  if (bytes[3] != 0) {
                     this.buffer[this.len++] = bytes[3];
                  }
               }
            }
         }

      }

      void appendReverseWeight16(int w) {
         assert (w & '\uffff') != 0;

         byte b0 = (byte)(w >>> 8);
         byte b1 = (byte)w;
         int appendLength = b1 == 0 ? 1 : 2;
         if (this.len + appendLength <= this.buffer.length || this.ensureCapacity(appendLength)) {
            if (b1 == 0) {
               this.buffer[this.len++] = b0;
            } else {
               this.buffer[this.len] = b1;
               this.buffer[this.len + 1] = b0;
               this.len += 2;
            }
         }

      }

      void appendTo(SortKeyByteSink sink) {
         assert this.len > 0 && this.buffer[this.len - 1] == 1;

         sink.Append(this.buffer, this.len - 1);
      }

      private boolean ensureCapacity(int appendCapacity) {
         int newCapacity = 2 * this.buffer.length;
         int altCapacity = this.len + 2 * appendCapacity;
         if (newCapacity < altCapacity) {
            newCapacity = altCapacity;
         }

         if (newCapacity < 200) {
            newCapacity = 200;
         }

         byte[] newbuf = new byte[newCapacity];
         System.arraycopy(this.buffer, 0, newbuf, 0, this.len);
         this.buffer = newbuf;
         return true;
      }
   }

   public static class LevelCallback {
      boolean needToWrite(int level) {
         return true;
      }
   }

   public abstract static class SortKeyByteSink {
      protected byte[] buffer_;
      private int appended_ = 0;

      public SortKeyByteSink(byte[] dest) {
         this.buffer_ = dest;
      }

      public void setBufferAndAppended(byte[] dest, int app) {
         this.buffer_ = dest;
         this.appended_ = app;
      }

      public void Append(byte[] bytes, int n) {
         if (n > 0 && bytes != null) {
            int length = this.appended_;
            this.appended_ += n;
            int available = this.buffer_.length - length;
            if (n <= available) {
               System.arraycopy(bytes, 0, this.buffer_, length, n);
            } else {
               this.AppendBeyondCapacity(bytes, 0, n, length);
            }

         }
      }

      public void Append(int b) {
         if (this.appended_ < this.buffer_.length || this.Resize(1, this.appended_)) {
            this.buffer_[this.appended_] = (byte)b;
         }

         ++this.appended_;
      }

      public int NumberOfBytesAppended() {
         return this.appended_;
      }

      public int GetRemainingCapacity() {
         return this.buffer_.length - this.appended_;
      }

      public boolean Overflowed() {
         return this.appended_ > this.buffer_.length;
      }

      protected abstract void AppendBeyondCapacity(byte[] var1, int var2, int var3, int var4);

      protected abstract boolean Resize(int var1, int var2);
   }
}
