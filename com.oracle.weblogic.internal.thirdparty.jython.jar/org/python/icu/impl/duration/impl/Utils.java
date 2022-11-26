package org.python.icu.impl.duration.impl;

import java.util.Locale;

public class Utils {
   public static final Locale localeFromString(String s) {
      String language = s;
      String region = "";
      String variant = "";
      int x = s.indexOf("_");
      if (x != -1) {
         region = s.substring(x + 1);
         language = s.substring(0, x);
      }

      x = region.indexOf("_");
      if (x != -1) {
         variant = region.substring(x + 1);
         region = region.substring(0, x);
      }

      return new Locale(language, region, variant);
   }

   public static String chineseNumber(long n, ChineseDigits zh) {
      if (n < 0L) {
         n = -n;
      }

      if (n <= 10L) {
         return n == 2L ? String.valueOf(zh.liang) : String.valueOf(zh.digits[(int)n]);
      } else {
         char[] buf = new char[40];
         char[] digits = String.valueOf(n).toCharArray();
         boolean inZero = true;
         boolean forcedZero = false;
         int x = buf.length;
         int w = digits.length;
         int i = -1;
         int l = -1;

         while(true) {
            --w;
            int j;
            if (w < 0) {
               if (n > 1000000L) {
                  boolean last = true;
                  i = buf.length - 3;

                  while(buf[i] != '0') {
                     i -= 8;
                     last = !last;
                     if (i <= x) {
                        break;
                     }
                  }

                  i = buf.length - 7;

                  do {
                     if (buf[i] == zh.digits[0] && !last) {
                        buf[i] = '*';
                     }

                     i -= 8;
                     last = !last;
                  } while(i > x);

                  if (n >= 100000000L) {
                     i = buf.length - 8;

                     do {
                        boolean empty = true;
                        j = i - 1;

                        for(int e = Math.max(x - 1, i - 8); j > e; --j) {
                           if (buf[j] != '*') {
                              empty = false;
                              break;
                           }
                        }

                        if (empty) {
                           if (buf[i + 1] != '*' && buf[i + 1] != zh.digits[0]) {
                              buf[i] = zh.digits[0];
                           } else {
                              buf[i] = '*';
                           }
                        }

                        i -= 8;
                     } while(i > x);
                  }
               }

               for(w = x; w < buf.length; ++w) {
                  if (buf[w] == zh.digits[2] && (w >= buf.length - 1 || buf[w + 1] != zh.units[0]) && (w <= x || buf[w - 1] != zh.units[0] && buf[w - 1] != zh.digits[0] && buf[w - 1] != '*')) {
                     buf[w] = zh.liang;
                  }
               }

               if (buf[x] == zh.digits[1] && (zh.ko || buf[x + 1] == zh.units[0])) {
                  ++x;
               }

               w = x;

               for(i = x; i < buf.length; ++i) {
                  if (buf[i] != '*') {
                     buf[w++] = buf[i];
                  }
               }

               return new String(buf, x, w - x);
            }

            if (i == -1) {
               if (l != -1) {
                  --x;
                  buf[x] = zh.levels[l];
                  inZero = true;
                  forcedZero = false;
               }

               ++i;
            } else {
               --x;
               buf[x] = zh.units[i++];
               if (i == 3) {
                  i = -1;
                  ++l;
               }
            }

            j = digits[w] - 48;
            if (j == 0) {
               if (x < buf.length - 1 && i != 0) {
                  buf[x] = '*';
               }

               if (!inZero && !forcedZero) {
                  --x;
                  buf[x] = zh.digits[0];
                  inZero = true;
                  forcedZero = i == 1;
               } else {
                  --x;
                  buf[x] = '*';
               }
            } else {
               inZero = false;
               --x;
               buf[x] = zh.digits[j];
            }
         }
      }
   }

   public static class ChineseDigits {
      final char[] digits;
      final char[] units;
      final char[] levels;
      final char liang;
      final boolean ko;
      public static final ChineseDigits DEBUG = new ChineseDigits("0123456789s", "sbq", "WYZ", 'L', false);
      public static final ChineseDigits TRADITIONAL = new ChineseDigits("零一二三四五六七八九十", "十百千", "萬億兆", '兩', false);
      public static final ChineseDigits SIMPLIFIED = new ChineseDigits("零一二三四五六七八九十", "十百千", "万亿兆", '两', false);
      public static final ChineseDigits KOREAN = new ChineseDigits("영일이삼사오육칠팔구십", "십백천", "만억?", '이', true);

      ChineseDigits(String digits, String units, String levels, char liang, boolean ko) {
         this.digits = digits.toCharArray();
         this.units = units.toCharArray();
         this.levels = levels.toCharArray();
         this.liang = liang;
         this.ko = ko;
      }
   }
}
