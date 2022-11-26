package org.python.modules.sre;

import org.python.core.Options;
import org.python.core.Py;
import org.python.core.PyString;
import org.python.google.common.cache.CacheBuilder;
import org.python.google.common.cache.CacheLoader;
import org.python.google.common.cache.LoadingCache;
import org.python.google.common.cache.Weigher;

public class SRE_STATE {
   public static final int SRE_MAGIC = 20031017;
   public static final int SRE_OP_FAILURE = 0;
   public static final int SRE_OP_SUCCESS = 1;
   public static final int SRE_OP_ANY = 2;
   public static final int SRE_OP_ANY_ALL = 3;
   public static final int SRE_OP_ASSERT = 4;
   public static final int SRE_OP_ASSERT_NOT = 5;
   public static final int SRE_OP_AT = 6;
   public static final int SRE_OP_BRANCH = 7;
   public static final int SRE_OP_CALL = 8;
   public static final int SRE_OP_CATEGORY = 9;
   public static final int SRE_OP_CHARSET = 10;
   public static final int SRE_OP_BIGCHARSET = 11;
   public static final int SRE_OP_GROUPREF = 12;
   public static final int SRE_OP_GROUPREF_EXISTS = 13;
   public static final int SRE_OP_GROUPREF_IGNORE = 14;
   public static final int SRE_OP_IN = 15;
   public static final int SRE_OP_IN_IGNORE = 16;
   public static final int SRE_OP_INFO = 17;
   public static final int SRE_OP_JUMP = 18;
   public static final int SRE_OP_LITERAL = 19;
   public static final int SRE_OP_LITERAL_IGNORE = 20;
   public static final int SRE_OP_MARK = 21;
   public static final int SRE_OP_MAX_UNTIL = 22;
   public static final int SRE_OP_MIN_UNTIL = 23;
   public static final int SRE_OP_NOT_LITERAL = 24;
   public static final int SRE_OP_NOT_LITERAL_IGNORE = 25;
   public static final int SRE_OP_NEGATE = 26;
   public static final int SRE_OP_RANGE = 27;
   public static final int SRE_OP_REPEAT = 28;
   public static final int SRE_OP_REPEAT_ONE = 29;
   public static final int SRE_OP_SUBPATTERN = 30;
   public static final int SRE_OP_MIN_REPEAT_ONE = 31;
   public static final int SRE_AT_BEGINNING = 0;
   public static final int SRE_AT_BEGINNING_LINE = 1;
   public static final int SRE_AT_BEGINNING_STRING = 2;
   public static final int SRE_AT_BOUNDARY = 3;
   public static final int SRE_AT_NON_BOUNDARY = 4;
   public static final int SRE_AT_END = 5;
   public static final int SRE_AT_END_LINE = 6;
   public static final int SRE_AT_END_STRING = 7;
   public static final int SRE_AT_LOC_BOUNDARY = 8;
   public static final int SRE_AT_LOC_NON_BOUNDARY = 9;
   public static final int SRE_AT_UNI_BOUNDARY = 10;
   public static final int SRE_AT_UNI_NON_BOUNDARY = 11;
   public static final int SRE_CATEGORY_DIGIT = 0;
   public static final int SRE_CATEGORY_NOT_DIGIT = 1;
   public static final int SRE_CATEGORY_SPACE = 2;
   public static final int SRE_CATEGORY_NOT_SPACE = 3;
   public static final int SRE_CATEGORY_WORD = 4;
   public static final int SRE_CATEGORY_NOT_WORD = 5;
   public static final int SRE_CATEGORY_LINEBREAK = 6;
   public static final int SRE_CATEGORY_NOT_LINEBREAK = 7;
   public static final int SRE_CATEGORY_LOC_WORD = 8;
   public static final int SRE_CATEGORY_LOC_NOT_WORD = 9;
   public static final int SRE_CATEGORY_UNI_DIGIT = 10;
   public static final int SRE_CATEGORY_UNI_NOT_DIGIT = 11;
   public static final int SRE_CATEGORY_UNI_SPACE = 12;
   public static final int SRE_CATEGORY_UNI_NOT_SPACE = 13;
   public static final int SRE_CATEGORY_UNI_WORD = 14;
   public static final int SRE_CATEGORY_UNI_NOT_WORD = 15;
   public static final int SRE_CATEGORY_UNI_LINEBREAK = 16;
   public static final int SRE_CATEGORY_UNI_NOT_LINEBREAK = 17;
   public static final int SRE_FLAG_TEMPLATE = 1;
   public static final int SRE_FLAG_IGNORECASE = 2;
   public static final int SRE_FLAG_LOCALE = 4;
   public static final int SRE_FLAG_MULTILINE = 8;
   public static final int SRE_FLAG_DOTALL = 16;
   public static final int SRE_FLAG_UNICODE = 32;
   public static final int SRE_FLAG_VERBOSE = 64;
   public static final int SRE_INFO_PREFIX = 1;
   public static final int SRE_INFO_LITERAL = 2;
   public static final int SRE_INFO_CHARSET = 4;
   public static final int USE_RECURSION_LIMIT = 5000;
   public static final int SRE_ERROR_ILLEGAL = -1;
   public static final int SRE_ERROR_STATE = -2;
   public static final int SRE_ERROR_RECURSION_LIMIT = -3;
   static final int SRE_DIGIT_MASK = 1;
   static final int SRE_SPACE_MASK = 2;
   static final int SRE_LINEBREAK_MASK = 4;
   static final int SRE_ALNUM_MASK = 8;
   static final int SRE_WORD_MASK = 16;
   static byte[] sre_char_info = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 6, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 0, 0, 0, 0, 0, 0, 0, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 0, 0, 0, 0, 16, 0, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 24, 0, 0, 0, 0, 0};
   static byte[] sre_char_lower = new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125, 126, 127};
   int ptr;
   int beginning;
   int start;
   int end;
   int[] str;
   int pos;
   int endpos;
   int charsize;
   int lastindex;
   int lastmark;
   int[] mark = new int[200];
   int[] mark_stack;
   int mark_stack_size;
   int mark_stack_base;
   SRE_REPEAT repeat;
   int maxlevel;
   int flags;

   final boolean SRE_IS_DIGIT(int ch) {
      return ch < 128 ? (sre_char_info[ch] & 1) != 0 : false;
   }

   final boolean SRE_IS_SPACE(int ch) {
      return ch < 128 ? (sre_char_info[ch] & 2) != 0 : false;
   }

   final boolean SRE_IS_LINEBREAK(int ch) {
      return ch == 10;
   }

   final boolean SRE_IS_WORD(int ch) {
      return ch < 128 ? (sre_char_info[ch] & 16) != 0 : false;
   }

   final int lower(int ch) {
      if ((this.flags & 4) != 0) {
         return ch < 256 ? Character.toLowerCase(ch) : ch;
      } else if ((this.flags & 32) != 0) {
         return Character.toLowerCase(ch);
      } else {
         return ch < 128 ? (char)sre_char_lower[ch] : ch;
      }
   }

   final boolean SRE_LOC_IS_WORD(int ch) {
      return Character.isLetterOrDigit(ch) || ch == 95;
   }

   final boolean SRE_UNI_IS_LINEBREAK(int ch) {
      switch (ch) {
         case 10:
         case 13:
         case 28:
         case 29:
         case 30:
         case 133:
         case 8232:
         case 8233:
            return true;
         default:
            return false;
      }
   }

   final boolean sre_category(int category, int ch) {
      switch (category) {
         case 0:
            return this.SRE_IS_DIGIT(ch);
         case 1:
            return !this.SRE_IS_DIGIT(ch);
         case 2:
            return this.SRE_IS_SPACE(ch);
         case 3:
            return !this.SRE_IS_SPACE(ch);
         case 4:
            return this.SRE_IS_WORD(ch);
         case 5:
            return !this.SRE_IS_WORD(ch);
         case 6:
            return this.SRE_IS_LINEBREAK(ch);
         case 7:
            return !this.SRE_IS_LINEBREAK(ch);
         case 8:
            return this.SRE_LOC_IS_WORD(ch);
         case 9:
            return !this.SRE_LOC_IS_WORD(ch);
         case 10:
            return Character.isDigit(ch);
         case 11:
            return !Character.isDigit(ch);
         case 12:
            return Character.isSpaceChar(ch) || Character.isWhitespace(ch) || ch == 133;
         case 13:
            return !Character.isSpaceChar(ch) && !Character.isWhitespace(ch) && ch != 133;
         case 14:
            return Character.isLetterOrDigit(ch) || ch == 95;
         case 15:
            return !Character.isLetterOrDigit(ch) && ch != 95;
         case 16:
            return this.SRE_UNI_IS_LINEBREAK(ch);
         case 17:
            return !this.SRE_UNI_IS_LINEBREAK(ch);
         default:
            return false;
      }
   }

   private void mark_fini() {
      this.mark_stack = null;
      this.mark_stack_size = this.mark_stack_base = 0;
   }

   private int mark_save(int lo, int hi) {
      if (hi <= lo) {
         return this.mark_stack_base;
      } else {
         int size = hi - lo + 1;
         int newsize = this.mark_stack_size;
         int minsize = this.mark_stack_base + size;
         if (newsize < minsize) {
            int[] stack;
            if (newsize == 0) {
               newsize = 512;
               if (newsize < minsize) {
                  newsize = minsize;
               }

               stack = new int[newsize];
            } else {
               while(newsize < minsize) {
                  newsize += newsize;
               }

               stack = new int[newsize];
               System.arraycopy(this.mark_stack, 0, stack, 0, this.mark_stack.length);
            }

            this.mark_stack = stack;
            this.mark_stack_size = newsize;
         }

         System.arraycopy(this.mark, lo, this.mark_stack, this.mark_stack_base, size);
         this.mark_stack_base += size;
         return this.mark_stack_base;
      }
   }

   private void mark_restore(int lo, int hi, int mark_stack_base) {
      if (hi > lo) {
         int size = hi - lo + 1;
         this.mark_stack_base = mark_stack_base - size;
         System.arraycopy(this.mark_stack, this.mark_stack_base, this.mark, lo, size);
      }
   }

   final boolean SRE_AT(int ptr, int at) {
      boolean thatp;
      boolean thisp;
      switch (at) {
         case 0:
         case 2:
            return ptr == this.beginning;
         case 1:
            return ptr == this.beginning || this.SRE_IS_LINEBREAK(this.str[ptr - 1]);
         case 3:
            if (this.beginning == this.end) {
               return false;
            }

            thatp = ptr > this.beginning ? this.SRE_IS_WORD(this.str[ptr - 1]) : false;
            thisp = ptr < this.end ? this.SRE_IS_WORD(this.str[ptr]) : false;
            return thisp != thatp;
         case 4:
            if (this.beginning == this.end) {
               return false;
            }

            thatp = ptr > this.beginning ? this.SRE_IS_WORD(this.str[ptr - 1]) : false;
            thisp = ptr < this.end ? this.SRE_IS_WORD(this.str[ptr]) : false;
            return thisp == thatp;
         case 5:
            return ptr + 1 == this.end && this.SRE_IS_LINEBREAK(this.str[ptr]) || ptr == this.end;
         case 6:
            return ptr == this.end || this.SRE_IS_LINEBREAK(this.str[ptr]);
         case 7:
            return ptr == this.end;
         case 8:
         case 10:
            if (this.beginning == this.end) {
               return false;
            }

            thatp = ptr > this.beginning ? this.SRE_LOC_IS_WORD(this.str[ptr - 1]) : false;
            thisp = ptr < this.end ? this.SRE_LOC_IS_WORD(this.str[ptr]) : false;
            return thisp != thatp;
         case 9:
         case 11:
            if (this.beginning == this.end) {
               return false;
            }

            thatp = ptr > this.beginning ? this.SRE_LOC_IS_WORD(this.str[ptr - 1]) : false;
            thisp = ptr < this.end ? this.SRE_LOC_IS_WORD(this.str[ptr]) : false;
            return thisp == thatp;
         default:
            return false;
      }
   }

   final boolean SRE_CHARSET(int[] set, int setidx, int ch) {
      boolean ok = true;

      while(true) {
         switch (set[setidx++]) {
            case 0:
               return !ok;
            case 9:
               if (this.sre_category(set[setidx], ch)) {
                  return ok;
               }

               ++setidx;
               break;
            case 10:
               if (ch < 256 && (set[setidx + (ch >> 5)] & 1 << (ch & 31)) != 0) {
                  return ok;
               }

               setidx += 8;
               break;
            case 11:
               int count = set[setidx++];
               int block;
               if (ch < 65536) {
                  block = set[setidx + ch >> 8];
               } else {
                  block = -1;
               }

               setidx += 64;
               if (block >= 0 && (set[setidx + block * 8 + ((ch & 255) >> 5)] & 1 << (ch & 31)) != 0) {
                  return ok;
               }

               setidx += count * 8;
               break;
            case 19:
               if (ch == set[setidx]) {
                  return ok;
               }

               ++setidx;
               break;
            case 26:
               ok = !ok;
               break;
            case 27:
               if (set[setidx] <= ch && ch <= set[setidx + 1]) {
                  return ok;
               }

               setidx += 2;
               break;
            default:
               return false;
         }
      }
   }

   private int SRE_COUNT(int[] pattern, int pidx, int maxcount, int level) {
      int ptr = this.ptr;
      int end = this.end;
      if (maxcount < end - ptr && maxcount != 65535) {
         end = ptr + maxcount;
      }

      int chr;
      switch (pattern[pidx]) {
         case 2:
            while(ptr < end && !this.SRE_IS_LINEBREAK(this.str[ptr])) {
               ++ptr;
            }

            return ptr - this.ptr;
         case 3:
            ptr = end;
            break;
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 16:
         case 17:
         case 18:
         case 21:
         case 22:
         case 23:
         default:
            while(this.ptr < end) {
               int i = this.SRE_MATCH(pattern, pidx, level);
               if (i < 0) {
                  return i;
               }

               if (i == 0) {
                  break;
               }
            }

            return this.ptr - ptr;
         case 15:
            while(ptr < end && this.SRE_CHARSET(pattern, pidx + 2, this.str[ptr])) {
               ++ptr;
            }

            return ptr - this.ptr;
         case 19:
            for(chr = pattern[pidx + 1]; ptr < end && this.str[ptr] == chr; ++ptr) {
            }

            return ptr - this.ptr;
         case 20:
            for(chr = pattern[pidx + 1]; ptr < end && this.lower(this.str[ptr]) == chr; ++ptr) {
            }

            return ptr - this.ptr;
         case 24:
            for(chr = pattern[pidx + 1]; ptr < end && this.str[ptr] != chr; ++ptr) {
            }

            return ptr - this.ptr;
         case 25:
            for(chr = pattern[pidx + 1]; ptr < end && this.lower(this.str[ptr]) != chr; ++ptr) {
            }
      }

      return ptr - this.ptr;
   }

   final int SRE_MATCH(int[] pattern, int pidx, int level) {
      int end = this.end;
      int ptr = this.ptr;
      int mark_stack_base = 0;
      if (level > 5000) {
         return -3;
      } else {
         if (pattern[pidx] == 17) {
            if (pattern[pidx + 3] != 0 && end - ptr < pattern[pidx + 3]) {
               return 0;
            }

            pidx += pattern[pidx + 1] + 1;
         }

         while(true) {
            int i;
            int lastmark;
            int lastindex;
            int count;
            int p;
            int e;
            SRE_REPEAT rp;
            switch (pattern[pidx++]) {
               case 0:
                  return 0;
               case 1:
                  this.ptr = ptr;
                  return 1;
               case 2:
                  if (ptr < end && !this.SRE_IS_LINEBREAK(this.str[ptr])) {
                     ++ptr;
                     break;
                  }

                  return 0;
               case 3:
                  if (ptr >= end) {
                     return 0;
                  }

                  ++ptr;
                  break;
               case 4:
                  this.ptr = ptr - pattern[pidx + 1];
                  if (this.ptr < this.beginning) {
                     return 0;
                  }

                  i = this.SRE_MATCH(pattern, pidx + 2, level + 1);
                  if (i <= 0) {
                     return i;
                  }

                  pidx += pattern[pidx];
                  break;
               case 5:
                  this.ptr = ptr - pattern[pidx + 1];
                  if (this.ptr >= this.beginning) {
                     i = this.SRE_MATCH(pattern, pidx + 2, level + 1);
                     if (i < 0) {
                        return i;
                     }

                     if (i != 0) {
                        return 0;
                     }
                  }

                  pidx += pattern[pidx];
                  break;
               case 6:
                  if (!this.SRE_AT(ptr, pattern[pidx])) {
                     return 0;
                  }

                  ++pidx;
                  break;
               case 7:
                  lastmark = this.lastmark;
                  lastindex = this.lastindex;
                  if (this.repeat != null) {
                     mark_stack_base = this.mark_save(0, lastmark);
                  }

                  for(; pattern[pidx] != 0; pidx += pattern[pidx]) {
                     if ((pattern[pidx + 1] != 19 || ptr < end && this.str[ptr] == pattern[pidx + 2]) && (pattern[pidx + 1] != 15 || ptr < end && this.SRE_CHARSET(pattern, pidx + 3, this.str[ptr]))) {
                        this.ptr = ptr;
                        i = this.SRE_MATCH(pattern, pidx + 1, level + 1);
                        if (i != 0) {
                           return i;
                        }

                        if (this.repeat != null) {
                           this.mark_restore(0, lastmark, mark_stack_base);
                        }

                        this.LASTMARK_RESTORE(lastmark, lastindex);
                     }
                  }

                  return 0;
               case 8:
               case 10:
               case 11:
               case 26:
               case 27:
               case 30:
               default:
                  return -1;
               case 9:
                  if (ptr < end && this.sre_category(pattern[pidx], this.str[ptr])) {
                     ++pidx;
                     ++ptr;
                     break;
                  }

                  return 0;
               case 12:
                  i = pattern[pidx];
                  p = this.mark[i + i];
                  e = this.mark[i + i + 1];
                  if (p != -1 && e != -1 && e >= p) {
                     while(p < e) {
                        if (ptr >= end || this.str[ptr] != this.str[p]) {
                           return 0;
                        }

                        ++p;
                        ++ptr;
                     }

                     ++pidx;
                     break;
                  }

                  return 0;
               case 13:
                  i = pattern[pidx];
                  p = this.mark[i + i];
                  e = this.mark[i + i + 1];
                  if (p != -1 && e != -1 && e >= p) {
                     pidx += 2;
                     break;
                  }

                  pidx += pattern[pidx + 1];
                  break;
               case 14:
                  i = pattern[pidx];
                  p = this.mark[i + i];
                  e = this.mark[i + i + 1];
                  if (p != -1 && e != -1 && e >= p) {
                     while(p < e) {
                        if (ptr >= end || this.lower(this.str[ptr]) != this.lower(this.str[p])) {
                           return 0;
                        }

                        ++p;
                        ++ptr;
                     }

                     ++pidx;
                     break;
                  }

                  return 0;
               case 15:
                  if (ptr < end && this.SRE_CHARSET(pattern, pidx + 1, this.str[ptr])) {
                     pidx += pattern[pidx];
                     ++ptr;
                     break;
                  }

                  return 0;
               case 16:
                  if (ptr < end && this.SRE_CHARSET(pattern, pidx + 1, this.lower(this.str[ptr]))) {
                     pidx += pattern[pidx];
                     ++ptr;
                     break;
                  }

                  return 0;
               case 17:
               case 18:
                  pidx += pattern[pidx];
                  break;
               case 19:
                  if (ptr < end && this.str[ptr] == pattern[pidx]) {
                     ++pidx;
                     ++ptr;
                     break;
                  }

                  return 0;
               case 20:
                  if (ptr < end && this.lower(this.str[ptr]) == this.lower(pattern[pidx])) {
                     ++pidx;
                     ++ptr;
                     break;
                  }

                  return 0;
               case 21:
                  i = pattern[pidx];
                  if ((i & 1) != 0) {
                     this.lastindex = i / 2 + 1;
                  }

                  if (i > this.lastmark) {
                     this.lastmark = i;
                  }

                  this.mark[i] = ptr;
                  ++pidx;
                  break;
               case 22:
                  rp = this.repeat;
                  if (rp == null) {
                     return -2;
                  }

                  this.ptr = ptr;
                  count = rp.count + 1;
                  if (count < pattern[rp.pidx + 1]) {
                     rp.count = count;
                     i = this.SRE_MATCH(pattern, rp.pidx + 3, level + 1);
                     if (i != 0) {
                        return i;
                     }

                     rp.count = count - 1;
                     this.ptr = ptr;
                     return 0;
                  }

                  if ((count < pattern[rp.pidx + 2] || pattern[rp.pidx + 2] == 65535) && ptr != rp.last_ptr) {
                     rp.count = count;
                     rp.last_ptr = ptr;
                     lastmark = this.lastmark;
                     lastindex = this.lastindex;
                     mark_stack_base = this.mark_save(0, lastmark);
                     i = this.SRE_MATCH(pattern, rp.pidx + 3, level + 1);
                     if (i != 0) {
                        return i;
                     }

                     this.mark_restore(0, lastmark, mark_stack_base);
                     this.LASTMARK_RESTORE(lastmark, lastindex);
                     rp.count = count - 1;
                     this.ptr = ptr;
                  }

                  this.repeat = rp.prev;
                  i = this.SRE_MATCH(pattern, pidx, level + 1);
                  if (i != 0) {
                     return i;
                  }

                  this.repeat = rp;
                  this.ptr = ptr;
                  return 0;
               case 23:
                  rp = this.repeat;
                  if (rp == null) {
                     return -2;
                  }

                  this.ptr = ptr;
                  count = rp.count + 1;
                  if (count < pattern[rp.pidx + 1]) {
                     rp.count = count;
                     i = this.SRE_MATCH(pattern, rp.pidx + 3, level + 1);
                     if (i != 0) {
                        return i;
                     }

                     rp.count = count - 1;
                     this.ptr = ptr;
                     return 0;
                  }

                  lastmark = this.lastmark;
                  lastindex = this.lastindex;
                  this.repeat = rp.prev;
                  i = this.SRE_MATCH(pattern, pidx, level + 1);
                  if (i != 0) {
                     return i;
                  }

                  this.ptr = ptr;
                  this.repeat = rp;
                  if (count >= pattern[rp.pidx + 2] && pattern[rp.pidx + 2] != 65535) {
                     return 0;
                  }

                  this.LASTMARK_RESTORE(lastmark, lastindex);
                  rp.count = count;
                  i = this.SRE_MATCH(pattern, rp.pidx + 3, level + 1);
                  if (i != 0) {
                     return i;
                  }

                  rp.count = count - 1;
                  this.ptr = ptr;
                  return 0;
               case 24:
                  if (ptr < end && this.str[ptr] != pattern[pidx]) {
                     ++pidx;
                     ++ptr;
                     break;
                  }

                  return 0;
               case 25:
                  if (ptr < end && this.lower(this.str[ptr]) != this.lower(pattern[pidx])) {
                     ++pidx;
                     ++ptr;
                     break;
                  }

                  return 0;
               case 28:
                  SRE_REPEAT rep = new SRE_REPEAT(this.repeat);
                  rep.count = -1;
                  rep.pidx = pidx;
                  this.repeat = rep;
                  this.ptr = ptr;
                  i = this.SRE_MATCH(pattern, pidx + pattern[pidx], level + 1);
                  this.repeat = rep.prev;
                  return i;
               case 29:
                  int mincount = pattern[pidx + 1];
                  if (ptr + mincount > end) {
                     return 0;
                  }

                  this.ptr = ptr;
                  count = this.SRE_COUNT(pattern, pidx + 3, pattern[pidx + 2], level + 1);
                  if (count < 0) {
                     return count;
                  }

                  ptr += count;
                  if (count < mincount) {
                     return 0;
                  }

                  if (pattern[pidx + pattern[pidx]] == 1) {
                     this.ptr = ptr;
                     return 1;
                  }

                  lastmark = this.lastmark;
                  lastindex = this.lastindex;
                  if (pattern[pidx + pattern[pidx]] == 19) {
                     int chr = pattern[pidx + pattern[pidx] + 1];

                     while(true) {
                        while(count < mincount || ptr < end && this.str[ptr] == chr) {
                           if (count < mincount) {
                              return 0;
                           }

                           this.ptr = ptr;
                           i = this.SRE_MATCH(pattern, pidx + pattern[pidx], level + 1);
                           if (i != 0) {
                              return 1;
                           }

                           --ptr;
                           --count;
                           this.LASTMARK_RESTORE(lastmark, lastindex);
                        }

                        --ptr;
                        --count;
                     }
                  } else {
                     lastmark = this.lastmark;

                     while(count >= mincount) {
                        this.ptr = ptr;
                        i = this.SRE_MATCH(pattern, pidx + pattern[pidx], level + 1);
                        if (i != 0) {
                           return i;
                        }

                        --ptr;
                        --count;
                        this.LASTMARK_RESTORE(lastmark, lastindex);
                     }

                     return 0;
                  }
               case 31:
                  if (ptr + pattern[pidx + 1] > end) {
                     return 0;
                  }

                  this.ptr = ptr;
                  if (pattern[pidx + 1] == 0) {
                     count = 0;
                  } else {
                     count = this.SRE_COUNT(pattern, pidx + 3, pattern[pidx + 1], level + 1);
                     if (count < 0) {
                        return count;
                     }

                     if (count < pattern[pidx + 1]) {
                        return 0;
                     }

                     ptr += count;
                  }

                  if (pattern[pidx + pattern[pidx]] == 1) {
                     this.ptr = ptr;
                     return 1;
                  }

                  boolean matchmax = pattern[pidx + 2] == 65535;
                  lastmark = this.lastmark;
                  lastindex = this.lastindex;

                  while(matchmax || count <= pattern[pidx + 2]) {
                     this.ptr = ptr;
                     i = this.SRE_MATCH(pattern, pidx + pattern[pidx], level + 1);
                     if (i != 0) {
                        return i;
                     }

                     this.ptr = ptr;
                     int c = this.SRE_COUNT(pattern, pidx + 3, 1, level + 1);
                     if (c < 0) {
                        return c;
                     }

                     if (c == 0) {
                        break;
                     }

                     if (c != 1) {
                        throw new IllegalStateException("c should be 1!");
                     }

                     ++ptr;
                     ++count;
                     this.LASTMARK_RESTORE(lastmark, lastindex);
                  }

                  return 0;
            }
         }
      }
   }

   private void LASTMARK_RESTORE(int lastmark, int lastindex) {
      if (this.lastmark > lastmark) {
         while(true) {
            if (this.lastmark <= lastmark) {
               this.lastindex = lastindex;
               break;
            }

            this.mark[this.lastmark--] = -1;
         }
      }

   }

   int SRE_SEARCH(int[] pattern, int pidx) {
      int ptr = this.start;
      int end = this.end;
      int status = 0;
      int prefix_len = 0;
      int prefix_skip = 0;
      int prefix = 0;
      int charset = 0;
      int overlap = 0;
      int flags = 0;
      if (pattern[pidx] == 17) {
         flags = pattern[pidx + 2];
         if (pattern[pidx + 3] > 1) {
            end -= pattern[pidx + 3] - 1;
            if (end <= ptr) {
               end = ptr;
            }
         }

         if ((flags & 1) != 0) {
            prefix_len = pattern[pidx + 5];
            prefix_skip = pattern[pidx + 6];
            prefix = pidx + 7;
            overlap = prefix + prefix_len - 1;
         } else if ((flags & 4) != 0) {
            charset = pidx + 5;
         }

         pidx += 1 + pattern[pidx + 1];
      }

      int i;
      if (prefix_len > 1) {
         i = 0;

         label77:
         for(end = this.end; ptr < end; ++ptr) {
            while(this.str[ptr] != pattern[prefix + i]) {
               if (i == 0) {
                  continue label77;
               }

               i = pattern[overlap + i];
            }

            ++i;
            if (i == prefix_len) {
               this.start = ptr + 1 - prefix_len;
               this.ptr = ptr + 1 - prefix_len + prefix_skip;
               if ((flags & 2) != 0) {
                  return 1;
               }

               status = this.SRE_MATCH(pattern, pidx + 2 * prefix_skip, 1);
               if (status != 0) {
                  return status;
               }

               i = pattern[overlap + i];
            }
         }

         return 0;
      } else {
         if (pattern[pidx] != 19) {
            if (charset == 0) {
               while(ptr <= end) {
                  this.start = this.ptr = ptr++;
                  status = this.SRE_MATCH(pattern, pidx, 1);
                  if (status != 0) {
                     break;
                  }
               }
            } else {
               end = this.end;

               while(true) {
                  while(ptr >= end || this.SRE_CHARSET(pattern, charset, this.str[ptr])) {
                     if (ptr == end) {
                        return 0;
                     }

                     this.start = ptr;
                     this.ptr = ptr;
                     status = this.SRE_MATCH(pattern, pidx, 1);
                     if (status != 0) {
                        return status;
                     }

                     ++ptr;
                  }

                  ++ptr;
               }
            }
         } else {
            i = pattern[pidx + 1];
            end = this.end;

            while(true) {
               while(ptr >= end || this.str[ptr] == i) {
                  if (ptr == end) {
                     return 0;
                  }

                  this.start = ptr++;
                  this.ptr = ptr;
                  if ((flags & 2) != 0) {
                     return 1;
                  }

                  status = this.SRE_MATCH(pattern, pidx + 2, 1);
                  if (status != 0) {
                     return status;
                  }
               }

               ++ptr;
            }
         }

         return status;
      }
   }

   public SRE_STATE(PyString str, int start, int end, int flags) {
      this.str = SRE_STATE.CACHE.INSTANCE.get(str);
      int size = str.__len__();
      this.charsize = 1;
      if (start < 0) {
         start = 0;
      } else if (start > size) {
         start = size;
      }

      if (end < 0) {
         end = 0;
      } else if (end > size) {
         end = size;
      }

      this.start = start;
      this.end = end;
      this.pos = start;
      this.endpos = end;
      this.state_reset();
      this.flags = flags;
   }

   public static int getlower(int ch, int flags) {
      if ((flags & 4) != 0) {
         return ch < 256 ? Character.toLowerCase((char)ch) : ch;
      } else if ((flags & 32) != 0) {
         return Character.toLowerCase((char)ch);
      } else {
         return ch < 128 ? (char)sre_char_lower[ch] : ch;
      }
   }

   String getslice(int index, String string, boolean empty) {
      index = (index - 1) * 2;
      int j;
      int i;
      if (string != null && this.mark[index] != -1 && this.mark[index + 1] != -1) {
         i = this.mark[index];
         j = this.mark[index + 1];
      } else {
         if (!empty) {
            return null;
         }

         j = 0;
         i = 0;
      }

      return string.substring(i, j);
   }

   void state_reset() {
      this.lastmark = 0;

      for(int i = 0; i < this.mark.length; ++i) {
         this.mark[i] = -1;
      }

      this.lastindex = -1;
      this.repeat = null;
      this.mark_fini();
   }

   private static enum CACHE {
      INSTANCE(Options.sreCacheSpec);

      private LoadingCache cache;

      private CACHE(String spec) {
         CacheLoader loader = new CacheLoader() {
            public int[] load(PyString key) {
               return key.toCodePoints();
            }
         };

         CacheBuilder builder;
         try {
            builder = CacheBuilder.from(spec);
         } catch (IllegalArgumentException var7) {
            Py.writeWarning("re", String.format("Incompatible options in python.sre.cachespec '%s' due to: %s", spec, var7.getMessage()));
            Py.writeMessage("re", String.format("Defaulting python.sre.cachespec to '%s'", "weakKeys,concurrencyLevel=4,maximumWeight=2621440,expireAfterAccess=30s"));
            builder = CacheBuilder.from("weakKeys,concurrencyLevel=4,maximumWeight=2621440,expireAfterAccess=30s");
         }

         if (spec.contains("maximumWeight")) {
            this.cache = builder.weigher(new Weigher() {
               public int weigh(PyString k, int[] v) {
                  return v.length;
               }
            }).build(loader);
         } else {
            this.cache = builder.build(loader);
         }

      }

      private int[] get(PyString str) {
         return (int[])this.cache.getUnchecked(str);
      }
   }
}
