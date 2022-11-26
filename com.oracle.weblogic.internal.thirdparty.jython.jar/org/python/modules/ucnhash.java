package org.python.modules;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.core.ucnhashAPI;

public class ucnhash implements ucnhashAPI {
   private static int n;
   private static int m;
   private static int minchar;
   private static int alphasz;
   private static int maxlen;
   private static int maxidx;
   private static int maxklen;
   private static short[] G;
   private static short[] T0;
   private static short[] T1;
   private static short[] T2;
   private static byte[] worddata;
   private static short[] wordoffs;
   private static short wordstart;
   private static short wordcutoff;
   private static byte[] rawdata;
   private static int[] rawindex;
   private static int[] codepoint;
   public static String[] __depends__ = new String[]{"/org/python/modules/ucnhash.dat"};
   private static final char[] charmap = " ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-()".toCharArray();
   private static final int raw_block = 3;
   private static String cjkPrefix = "CJK COMPATIBILITY IDEOGRAPH-";
   private static int cjkPrefixLen;
   private static boolean initialized;
   private static boolean loaded;

   public static void loadTables() throws Exception {
      InputStream instream = ucnhash.class.getResourceAsStream("ucnhash.dat");
      if (instream == null) {
         throw new IOException("Unicode name database not found: ucnhash.dat");
      } else {
         DataInputStream in = new DataInputStream(new BufferedInputStream(instream));
         n = in.readShort();
         m = in.readShort();
         minchar = in.readShort();
         alphasz = in.readShort();
         maxlen = in.readShort();
         maxidx = maxlen * alphasz - minchar;
         G = readShortTable(in);
         if (in.readShort() != 3) {
            throw new IOException("UnicodeNameMap file corrupt, unknown dimension");
         } else {
            T0 = readShortTable(in);
            T1 = readShortTable(in);
            T2 = readShortTable(in);
            wordoffs = readShortTable(in);
            worddata = readByteTable(in);
            wordstart = in.readShort();
            wordcutoff = in.readShort();
            maxklen = in.readShort();
            rawdata = readByteTable(in);
            rawindex = readIntTable(in);
            codepoint = readIntTable(in);
         }
      }
   }

   private static short[] readShortTable(DataInputStream in) throws IOException {
      if (in.read() != 116) {
         throw new IOException("UnicodeNameMap file corrupt, shorttable");
      } else {
         int n = in.readInt() / 2;
         short[] table = new short[n];

         for(int i = 0; i < n; ++i) {
            table[i] = in.readShort();
         }

         return table;
      }
   }

   private static int[] readIntTable(DataInputStream in) throws IOException {
      if (in.read() != 116) {
         throw new IOException("UnicodeNameMap file corrupt, inttable");
      } else {
         int n = in.readInt() / 4;
         int[] table = new int[n];

         for(int i = 0; i < n; ++i) {
            table[i] = in.readInt();
         }

         return table;
      }
   }

   private static char[] readCharTable(DataInputStream in) throws IOException {
      if (in.read() != 116) {
         throw new IOException("UnicodeNameMap file corrupt, chartable");
      } else {
         int n = in.readInt() / 2;
         char[] table = new char[n];

         for(int i = 0; i < n; ++i) {
            table[i] = in.readChar();
         }

         return table;
      }
   }

   private static byte[] readByteTable(DataInputStream in) throws IOException {
      if (in.read() != 116) {
         throw new IOException("UnicodeNameMap file corrupt, byte table");
      } else {
         int n = in.readInt();
         byte[] table = new byte[n];
         in.readFully(table);
         return table;
      }
   }

   public static int hash(String key) {
      return hash(key, 0, key.length());
   }

   public static int hash(String key, int start, int end) {
      int j = start;
      int i = -minchar;
      int f2 = 0;
      int f1 = 0;

      int f0;
      for(f0 = 0; j < end; ++j) {
         char ch = key.charAt(j);
         if (ch >= 'a' && ch <= 'z') {
            ch = (char)(ch - 97 + 65);
         }

         f0 += T0[i + ch];
         f1 += T1[i + ch];
         f2 += T2[i + ch];
         i += alphasz;
         if (i >= maxidx) {
            i = -minchar;
         }
      }

      f0 %= n;
      f1 %= n;
      f2 %= n;
      return (G[f0] + G[f1] + G[f2]) % m;
   }

   private static String getWord(int idx) {
      int offset = wordoffs[idx];
      int end = worddata.length;
      if (idx < wordoffs.length - 1) {
         end = wordoffs[idx + 1];
      }

      StringBuilder buf = new StringBuilder();

      for(int i = offset; i < end; ++i) {
         buf.append(charmap[worddata[i]]);
      }

      return buf.toString();
   }

   private static boolean match(int idx, byte[] raw, int begin, int end) {
      int woff = wordoffs[idx];
      int wend = worddata.length;
      if (idx < wordoffs.length - 1) {
         wend = wordoffs[idx + 1];
      }

      if (end - begin != wend - woff) {
         return false;
      } else {
         int l = end - begin;

         for(int i = 0; i < l; ++i) {
            if (worddata[woff + i] != raw[begin + i]) {
               return false;
            }
         }

         return true;
      }
   }

   private static int compare(byte[] a1, int off1, int len1, byte[] a2, int off2, int len2) {
      for(int i = 0; i < len1 && i < len2; ++i) {
         int d = (a1[off1 + i] & 255) - (a2[off2 + i] & 255);
         if (d != 0) {
            return d;
         }
      }

      return len1 - len2;
   }

   private static int binarysearch(byte[] rawlist, int start, int end) {
      int floor = 0;
      int ceiling = rawindex.length / 3;

      int off;
      int len;
      while(floor < ceiling - 1) {
         int middle = (floor + ceiling) / 2;
         off = rawindex[middle * 3];
         len = rawindex[middle * 3 + 3 - 1] & 31;
         int d = compare(rawlist, start, end - start, rawdata, off, len);
         if (d < 0) {
            ceiling = middle;
         } else {
            if (d <= 0) {
               return middle * 12;
            }

            floor = middle;
         }
      }

      int tmp = floor * 3;
      off = rawindex[tmp++];
      long lengths = (long)rawindex[tmp++] << 32 | (long)rawindex[tmp++] & 4294967295L;
      floor *= 12;

      for(int i = 0; i < 12; ++i) {
         len = (int)(lengths >> i * 5) & 31;
         if (compare(rawlist, start, end, rawdata, off, len) == 0) {
            return floor;
         }

         off += len;
         ++floor;
      }

      return -1;
   }

   public static int lookup(String name) {
      return lookup(name, 0, name.length());
   }

   private static int lookup(String name, int start, int end) {
      byte[] rawlist = new byte[32];
      int ridx = 0;
      int rbegin = false;
      int rstart = 0;

      while(true) {
         int rbegin = ridx;

         int i;
         label119:
         for(i = start; i < end; ++i) {
            char ch = name.charAt(i);
            byte v;
            switch (ch) {
               case ' ':
                  start = i + 1;
                  break label119;
               case '!':
               case '"':
               case '#':
               case '$':
               case '%':
               case '&':
               case '\'':
               case '*':
               case '+':
               case ',':
               case '.':
               case '/':
               case ':':
               case ';':
               case '<':
               case '=':
               case '>':
               case '?':
               case '@':
               case '[':
               case '\\':
               case ']':
               case '^':
               case '_':
               case '`':
               default:
                  return -1;
               case '(':
                  v = 38;
                  break;
               case ')':
                  v = 39;
                  break;
               case '-':
                  v = 37;
                  break;
               case '0':
                  v = 27;
                  break;
               case '1':
                  v = 28;
                  break;
               case '2':
                  v = 29;
                  break;
               case '3':
                  v = 30;
                  break;
               case '4':
                  v = 31;
                  break;
               case '5':
                  v = 32;
                  break;
               case '6':
                  v = 33;
                  break;
               case '7':
                  v = 34;
                  break;
               case '8':
                  v = 35;
                  break;
               case '9':
                  v = 36;
                  break;
               case 'A':
                  v = 1;
                  break;
               case 'B':
                  v = 2;
                  break;
               case 'C':
                  v = 3;
                  break;
               case 'D':
                  v = 4;
                  break;
               case 'E':
                  v = 5;
                  break;
               case 'F':
                  v = 6;
                  break;
               case 'G':
                  v = 7;
                  break;
               case 'H':
                  v = 8;
                  break;
               case 'I':
                  v = 9;
                  break;
               case 'J':
                  v = 10;
                  break;
               case 'K':
                  v = 11;
                  break;
               case 'L':
                  v = 12;
                  break;
               case 'M':
                  v = 13;
                  break;
               case 'N':
                  v = 14;
                  break;
               case 'O':
                  v = 15;
                  break;
               case 'P':
                  v = 16;
                  break;
               case 'Q':
                  v = 17;
                  break;
               case 'R':
                  v = 18;
                  break;
               case 'S':
                  v = 19;
                  break;
               case 'T':
                  v = 20;
                  break;
               case 'U':
                  v = 21;
                  break;
               case 'V':
                  v = 22;
                  break;
               case 'W':
                  v = 23;
                  break;
               case 'X':
                  v = 24;
                  break;
               case 'Y':
                  v = 25;
                  break;
               case 'Z':
                  v = 26;
                  break;
               case 'a':
                  v = 1;
                  break;
               case 'b':
                  v = 2;
                  break;
               case 'c':
                  v = 3;
                  break;
               case 'd':
                  v = 4;
                  break;
               case 'e':
                  v = 5;
                  break;
               case 'f':
                  v = 6;
                  break;
               case 'g':
                  v = 7;
                  break;
               case 'h':
                  v = 8;
                  break;
               case 'i':
                  v = 9;
                  break;
               case 'j':
                  v = 10;
                  break;
               case 'k':
                  v = 11;
                  break;
               case 'l':
                  v = 12;
                  break;
               case 'm':
                  v = 13;
                  break;
               case 'n':
                  v = 14;
                  break;
               case 'o':
                  v = 15;
                  break;
               case 'p':
                  v = 16;
                  break;
               case 'q':
                  v = 17;
                  break;
               case 'r':
                  v = 18;
                  break;
               case 's':
                  v = 19;
                  break;
               case 't':
                  v = 20;
                  break;
               case 'u':
                  v = 21;
                  break;
               case 'v':
                  v = 22;
                  break;
               case 'w':
                  v = 23;
                  break;
               case 'x':
                  v = 24;
                  break;
               case 'y':
                  v = 25;
                  break;
               case 'z':
                  v = 26;
            }

            rawlist[ridx++] = v;
            if (ch == '-' && start != i) {
               ++i;
               start = i;
               break;
            }
         }

         int idx = hash(name, start, i);
         if (idx >= 0 && ridx - rbegin > 1 && match(idx, rawlist, rbegin, ridx)) {
            idx += wordstart;
            if (idx > wordcutoff) {
               ridx = rstart + 1;
               rawlist[rstart] = (byte)((idx >> 8) + wordcutoff);
               rawlist[ridx++] = (byte)(idx & 255);
            } else {
               ridx = rstart + 1;
               rawlist[rstart] = (byte)idx;
            }

            rstart = ridx;
            if (i < end) {
               continue;
            }
         } else {
            rstart = ridx;
            if (i < end) {
               rawlist[ridx++] = 0;
               continue;
            }
         }

         idx = binarysearch(rawlist, 0, ridx);
         if (idx < 0) {
            return idx;
         }

         return codepoint[idx];
      }
   }

   public int getCchMax() {
      return !this.initialized() ? -1 : maxklen;
   }

   public int getValue(String s, int start, int end) {
      if (!this.initialized()) {
         return -1;
      } else if (s.regionMatches(start, cjkPrefix, 0, cjkPrefixLen)) {
         try {
            String hex = s.substring(start + cjkPrefixLen, end);
            int v = Integer.parseInt(hex, 16);
            return v;
         } catch (NumberFormatException var6) {
            return -1;
         }
      } else {
         return lookup(s, start, end);
      }
   }

   private synchronized boolean initialized() {
      if (initialized) {
         return loaded;
      } else {
         try {
            loadTables();
            loaded = true;
         } catch (Exception var2) {
            return false;
         }

         initialized = true;
         return true;
      }
   }

   static {
      cjkPrefixLen = cjkPrefix.length();
      initialized = false;
      loaded = false;
   }
}
