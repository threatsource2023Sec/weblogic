package org.python.icu.text;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import org.python.icu.impl.CharTrie;
import org.python.icu.impl.ICUBinary;
import org.python.icu.impl.Trie;

final class RBBIDataWrapper {
   RBBIDataHeader fHeader;
   short[] fFTable;
   short[] fRTable;
   short[] fSFTable;
   short[] fSRTable;
   CharTrie fTrie;
   String fRuleSource;
   int[] fStatusTable;
   private boolean isBigEndian;
   static final int DATA_FORMAT = 1114794784;
   static final int FORMAT_VERSION = 50397184;
   private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
   static final int DH_SIZE = 24;
   static final int DH_MAGIC = 0;
   static final int DH_FORMATVERSION = 1;
   static final int DH_LENGTH = 2;
   static final int DH_CATCOUNT = 3;
   static final int DH_FTABLE = 4;
   static final int DH_FTABLELEN = 5;
   static final int DH_RTABLE = 6;
   static final int DH_RTABLELEN = 7;
   static final int DH_SFTABLE = 8;
   static final int DH_SFTABLELEN = 9;
   static final int DH_SRTABLE = 10;
   static final int DH_SRTABLELEN = 11;
   static final int DH_TRIE = 12;
   static final int DH_TRIELEN = 13;
   static final int DH_RULESOURCE = 14;
   static final int DH_RULESOURCELEN = 15;
   static final int DH_STATUSTABLE = 16;
   static final int DH_STATUSTABLELEN = 17;
   static final int ACCEPTING = 0;
   static final int LOOKAHEAD = 1;
   static final int TAGIDX = 2;
   static final int RESERVED = 3;
   static final int NEXTSTATES = 4;
   static final int NUMSTATES = 0;
   static final int ROWLEN = 2;
   static final int FLAGS = 4;
   private static final int ROW_DATA = 8;
   static final int RBBI_LOOKAHEAD_HARD_BREAK = 1;
   static final int RBBI_BOF_REQUIRED = 2;
   static TrieFoldingFunc fTrieFoldingFunc = new TrieFoldingFunc();

   int getRowIndex(int state) {
      return 8 + state * (this.fHeader.fCatCount + 4);
   }

   static RBBIDataWrapper get(ByteBuffer bytes) throws IOException {
      RBBIDataWrapper This = new RBBIDataWrapper();
      ICUBinary.readHeader(bytes, 1114794784, IS_ACCEPTABLE);
      This.isBigEndian = bytes.order() == ByteOrder.BIG_ENDIAN;
      This.fHeader = new RBBIDataHeader();
      This.fHeader.fMagic = bytes.getInt();
      This.fHeader.fVersion = bytes.getInt(bytes.position());
      This.fHeader.fFormatVersion[0] = bytes.get();
      This.fHeader.fFormatVersion[1] = bytes.get();
      This.fHeader.fFormatVersion[2] = bytes.get();
      This.fHeader.fFormatVersion[3] = bytes.get();
      This.fHeader.fLength = bytes.getInt();
      This.fHeader.fCatCount = bytes.getInt();
      This.fHeader.fFTable = bytes.getInt();
      This.fHeader.fFTableLen = bytes.getInt();
      This.fHeader.fRTable = bytes.getInt();
      This.fHeader.fRTableLen = bytes.getInt();
      This.fHeader.fSFTable = bytes.getInt();
      This.fHeader.fSFTableLen = bytes.getInt();
      This.fHeader.fSRTable = bytes.getInt();
      This.fHeader.fSRTableLen = bytes.getInt();
      This.fHeader.fTrie = bytes.getInt();
      This.fHeader.fTrieLen = bytes.getInt();
      This.fHeader.fRuleSource = bytes.getInt();
      This.fHeader.fRuleSourceLen = bytes.getInt();
      This.fHeader.fStatusTable = bytes.getInt();
      This.fHeader.fStatusTableLen = bytes.getInt();
      ICUBinary.skipBytes(bytes, 24);
      if (This.fHeader.fMagic == 45472 && (This.fHeader.fVersion == 1 || This.fHeader.fFormatVersion[0] == 3)) {
         int pos = 96;
         if (This.fHeader.fFTable >= pos && This.fHeader.fFTable <= This.fHeader.fLength) {
            ICUBinary.skipBytes(bytes, This.fHeader.fFTable - pos);
            pos = This.fHeader.fFTable;
            This.fFTable = ICUBinary.getShorts(bytes, This.fHeader.fFTableLen / 2, This.fHeader.fFTableLen & 1);
            pos += This.fHeader.fFTableLen;
            ICUBinary.skipBytes(bytes, This.fHeader.fRTable - pos);
            pos = This.fHeader.fRTable;
            This.fRTable = ICUBinary.getShorts(bytes, This.fHeader.fRTableLen / 2, This.fHeader.fRTableLen & 1);
            pos += This.fHeader.fRTableLen;
            if (This.fHeader.fSFTableLen > 0) {
               ICUBinary.skipBytes(bytes, This.fHeader.fSFTable - pos);
               pos = This.fHeader.fSFTable;
               This.fSFTable = ICUBinary.getShorts(bytes, This.fHeader.fSFTableLen / 2, This.fHeader.fSFTableLen & 1);
               pos += This.fHeader.fSFTableLen;
            }

            if (This.fHeader.fSRTableLen > 0) {
               ICUBinary.skipBytes(bytes, This.fHeader.fSRTable - pos);
               pos = This.fHeader.fSRTable;
               This.fSRTable = ICUBinary.getShorts(bytes, This.fHeader.fSRTableLen / 2, This.fHeader.fSRTableLen & 1);
               pos += This.fHeader.fSRTableLen;
            }

            ICUBinary.skipBytes(bytes, This.fHeader.fTrie - pos);
            pos = This.fHeader.fTrie;
            bytes.mark();
            This.fTrie = new CharTrie(bytes, fTrieFoldingFunc);
            bytes.reset();
            if (pos > This.fHeader.fStatusTable) {
               throw new IOException("Break iterator Rule data corrupt");
            } else {
               ICUBinary.skipBytes(bytes, This.fHeader.fStatusTable - pos);
               pos = This.fHeader.fStatusTable;
               This.fStatusTable = ICUBinary.getInts(bytes, This.fHeader.fStatusTableLen / 4, This.fHeader.fStatusTableLen & 3);
               pos += This.fHeader.fStatusTableLen;
               if (pos > This.fHeader.fRuleSource) {
                  throw new IOException("Break iterator Rule data corrupt");
               } else {
                  ICUBinary.skipBytes(bytes, This.fHeader.fRuleSource - pos);
                  pos = This.fHeader.fRuleSource;
                  This.fRuleSource = ICUBinary.getString(bytes, This.fHeader.fRuleSourceLen / 2, This.fHeader.fRuleSourceLen & 1);
                  if (RuleBasedBreakIterator.fDebugEnv != null && RuleBasedBreakIterator.fDebugEnv.indexOf("data") >= 0) {
                     This.dump(System.out);
                  }

                  return This;
               }
            }
         } else {
            throw new IOException("Break iterator Rule data corrupt");
         }
      } else {
         throw new IOException("Break Iterator Rule Data Magic Number Incorrect, or unsupported data version.");
      }
   }

   private int getStateTableNumStates(short[] table) {
      return this.isBigEndian ? table[0] << 16 | table[1] & '\uffff' : table[1] << 16 | table[0] & '\uffff';
   }

   int getStateTableFlags(short[] table) {
      return table[this.isBigEndian ? 5 : 4];
   }

   void dump(PrintStream out) {
      if (this.fFTable.length == 0) {
         throw new NullPointerException();
      } else {
         out.println("RBBI Data Wrapper dump ...");
         out.println();
         out.println("Forward State Table");
         this.dumpTable(out, this.fFTable);
         out.println("Reverse State Table");
         this.dumpTable(out, this.fRTable);
         out.println("Forward Safe Points Table");
         this.dumpTable(out, this.fSFTable);
         out.println("Reverse Safe Points Table");
         this.dumpTable(out, this.fSRTable);
         this.dumpCharCategories(out);
         out.println("Source Rules: " + this.fRuleSource);
      }
   }

   public static String intToString(int n, int width) {
      StringBuilder dest = new StringBuilder(width);
      dest.append(n);

      while(dest.length() < width) {
         dest.insert(0, ' ');
      }

      return dest.toString();
   }

   public static String intToHexString(int n, int width) {
      StringBuilder dest = new StringBuilder(width);
      dest.append(Integer.toHexString(n));

      while(dest.length() < width) {
         dest.insert(0, ' ');
      }

      return dest.toString();
   }

   private void dumpTable(PrintStream out, short[] table) {
      if (table == null) {
         out.println("  -- null -- ");
      } else {
         StringBuilder header = new StringBuilder(" Row  Acc Look  Tag");

         int n;
         for(n = 0; n < this.fHeader.fCatCount; ++n) {
            header.append(intToString(n, 5));
         }

         out.println(header.toString());

         for(n = 0; n < header.length(); ++n) {
            out.print("-");
         }

         out.println();

         for(int state = 0; state < this.getStateTableNumStates(table); ++state) {
            this.dumpRow(out, table, state);
         }

         out.println();
      }

   }

   private void dumpRow(PrintStream out, short[] table, int state) {
      StringBuilder dest = new StringBuilder(this.fHeader.fCatCount * 5 + 20);
      dest.append(intToString(state, 4));
      int row = this.getRowIndex(state);
      if (table[row + 0] != 0) {
         dest.append(intToString(table[row + 0], 5));
      } else {
         dest.append("     ");
      }

      if (table[row + 1] != 0) {
         dest.append(intToString(table[row + 1], 5));
      } else {
         dest.append("     ");
      }

      dest.append(intToString(table[row + 2], 5));

      for(int col = 0; col < this.fHeader.fCatCount; ++col) {
         dest.append(intToString(table[row + 4 + col], 5));
      }

      out.println(dest);
   }

   private void dumpCharCategories(PrintStream out) {
      int n = this.fHeader.fCatCount;
      String[] catStrings = new String[n + 1];
      int rangeStart = 0;
      int rangeEnd = 0;
      int lastCat = -1;
      int[] lastNewline = new int[n + 1];

      int category;
      for(category = 0; category <= this.fHeader.fCatCount; ++category) {
         catStrings[category] = "";
      }

      out.println("\nCharacter Categories");
      out.println("--------------------");

      for(int char32 = 0; char32 <= 1114111; ++char32) {
         int category = this.fTrie.getCodePointValue(char32);
         category = category & -16385;
         if (category < 0 || category > this.fHeader.fCatCount) {
            out.println("Error, bad category " + Integer.toHexString(category) + " for char " + Integer.toHexString(char32));
            break;
         }

         if (category == lastCat) {
            rangeEnd = char32;
         } else {
            if (lastCat >= 0) {
               if (catStrings[lastCat].length() > lastNewline[lastCat] + 70) {
                  lastNewline[lastCat] = catStrings[lastCat].length() + 10;
                  catStrings[lastCat] = catStrings[lastCat] + "\n       ";
               }

               catStrings[lastCat] = catStrings[lastCat] + " " + Integer.toHexString(rangeStart);
               if (rangeEnd != rangeStart) {
                  catStrings[lastCat] = catStrings[lastCat] + "-" + Integer.toHexString(rangeEnd);
               }
            }

            lastCat = category;
            rangeEnd = char32;
            rangeStart = char32;
         }
      }

      catStrings[lastCat] = catStrings[lastCat] + " " + Integer.toHexString(rangeStart);
      if (rangeEnd != rangeStart) {
         catStrings[lastCat] = catStrings[lastCat] + "-" + Integer.toHexString(rangeEnd);
      }

      for(category = 0; category <= this.fHeader.fCatCount; ++category) {
         out.println(intToString(category, 5) + "  " + catStrings[category]);
      }

      out.println();
   }

   static class TrieFoldingFunc implements Trie.DataManipulate {
      public int getFoldingOffset(int data) {
         return (data & '耀') != 0 ? data & 32767 : 0;
      }
   }

   static final class RBBIDataHeader {
      int fMagic = 0;
      int fVersion;
      byte[] fFormatVersion = new byte[4];
      int fLength;
      int fCatCount;
      int fFTable;
      int fFTableLen;
      int fRTable;
      int fRTableLen;
      int fSFTable;
      int fSFTableLen;
      int fSRTable;
      int fSRTableLen;
      int fTrie;
      int fTrieLen;
      int fRuleSource;
      int fRuleSourceLen;
      int fStatusTable;
      int fStatusTableLen;

      public RBBIDataHeader() {
      }
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      public boolean isDataVersionAcceptable(byte[] version) {
         return version[0] == 3;
      }

      // $FF: synthetic method
      IsAcceptable(Object x0) {
         this();
      }
   }
}
