package org.apache.openjpa.kernel.jpql;

import java.io.IOException;
import java.io.PrintStream;

public class JPQLTokenManager implements JPQLConstants {
   public PrintStream debugStream;
   static final long[] jjbitVec0 = new long[]{2301339413881290750L, -16384L, 4294967295L, 432345564227567616L};
   static final long[] jjbitVec2 = new long[]{0L, 0L, 0L, -36028797027352577L};
   static final long[] jjbitVec3 = new long[]{0L, -1L, -1L, -1L};
   static final long[] jjbitVec4 = new long[]{-1L, -1L, 65535L, 0L};
   static final long[] jjbitVec5 = new long[]{-1L, -1L, 0L, 0L};
   static final long[] jjbitVec6 = new long[]{70368744177663L, 0L, 0L, 0L};
   static final long[] jjbitVec7 = new long[]{-2L, -1L, -1L, -1L};
   static final long[] jjbitVec8 = new long[]{0L, 0L, -1L, -1L};
   static final int[] jjnextStates = new int[]{17, 18, 19, 0, 20, 21, 25, 26, 29, 30, 5, 35, 36, 37, 38, 40, 1, 2, 5, 29, 30, 5, 35, 36, 37, 3, 4, 23, 24, 27, 28, 31, 32, 41, 42, 44};
   public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, ",", ".", "=", "<>", ">", ">=", "<", "<=", "+", "-", "*", "/", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "(", ")", ":", "?"};
   public static final String[] lexStateNames = new String[]{"DEFAULT"};
   static final long[] jjtoToken = new long[]{-31L, 31965183L};
   static final long[] jjtoSkip = new long[]{30L, 0L};
   protected JavaCharStream input_stream;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   protected char curChar;
   int curLexState;
   int defaultLexState;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;

   public void setDebugStream(PrintStream ds) {
      this.debugStream = ds;
   }

   private final int jjStopAtPos(int pos, int kind) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;
      return pos + 1;
   }

   private final int jjMoveStringLiteralDfa0_0() {
      switch (this.curChar) {
         case '\t':
            this.jjmatchedKind = 4;
            return this.jjMoveNfa_0(9, 0);
         case '\n':
            this.jjmatchedKind = 2;
            return this.jjMoveNfa_0(9, 0);
         case '\u000b':
         case '\f':
         case '\u000e':
         case '\u000f':
         case '\u0010':
         case '\u0011':
         case '\u0012':
         case '\u0013':
         case '\u0014':
         case '\u0015':
         case '\u0016':
         case '\u0017':
         case '\u0018':
         case '\u0019':
         case '\u001a':
         case '\u001b':
         case '\u001c':
         case '\u001d':
         case '\u001e':
         case '\u001f':
         case '!':
         case '"':
         case '#':
         case '$':
         case '%':
         case '&':
         case '\'':
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
         case ';':
         case '@':
         case 'K':
         case 'P':
         case 'Q':
         case 'R':
         case 'V':
         case 'X':
         case 'Y':
         case 'Z':
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         case 'k':
         case 'p':
         case 'q':
         case 'r':
         case 'v':
         default:
            return this.jjMoveNfa_0(9, 0);
         case '\r':
            this.jjmatchedKind = 3;
            return this.jjMoveNfa_0(9, 0);
         case ' ':
            this.jjmatchedKind = 1;
            return this.jjMoveNfa_0(9, 0);
         case '(':
            this.jjmatchedKind = 85;
            return this.jjMoveNfa_0(9, 0);
         case ')':
            this.jjmatchedKind = 86;
            return this.jjMoveNfa_0(9, 0);
         case '*':
            this.jjmatchedKind = 15;
            return this.jjMoveNfa_0(9, 0);
         case '+':
            this.jjmatchedKind = 13;
            return this.jjMoveNfa_0(9, 0);
         case ',':
            this.jjmatchedKind = 5;
            return this.jjMoveNfa_0(9, 0);
         case '-':
            this.jjmatchedKind = 14;
            return this.jjMoveNfa_0(9, 0);
         case '.':
            this.jjmatchedKind = 6;
            return this.jjMoveNfa_0(9, 0);
         case '/':
            this.jjmatchedKind = 16;
            return this.jjMoveNfa_0(9, 0);
         case ':':
            this.jjmatchedKind = 87;
            return this.jjMoveNfa_0(9, 0);
         case '<':
            this.jjmatchedKind = 11;
            return this.jjMoveStringLiteralDfa1_0(4352L, 0L);
         case '=':
            this.jjmatchedKind = 7;
            return this.jjMoveNfa_0(9, 0);
         case '>':
            this.jjmatchedKind = 9;
            return this.jjMoveStringLiteralDfa1_0(1024L, 0L);
         case '?':
            this.jjmatchedKind = 88;
            return this.jjMoveNfa_0(9, 0);
         case 'A':
            return this.jjMoveStringLiteralDfa1_0(4504716328042496L, 8L);
         case 'B':
            return this.jjMoveStringLiteralDfa1_0(562954315497472L, 0L);
         case 'C':
            return this.jjMoveStringLiteralDfa1_0(504407831189913600L, 0L);
         case 'D':
            return this.jjMoveStringLiteralDfa1_0(-8070450532231151616L, 0L);
         case 'E':
            return this.jjMoveStringLiteralDfa1_0(2152726528L, 0L);
         case 'F':
            return this.jjMoveStringLiteralDfa1_0(2305843009213693952L, 256L);
         case 'G':
            return this.jjMoveStringLiteralDfa1_0(0L, 2L);
         case 'H':
            return this.jjMoveStringLiteralDfa1_0(0L, 4L);
         case 'I':
            return this.jjMoveStringLiteralDfa1_0(134217728L, 576L);
         case 'J':
            return this.jjMoveStringLiteralDfa1_0(0L, 128L);
         case 'L':
            return this.jjMoveStringLiteralDfa1_0(3553622654713856L, 16L);
         case 'M':
            return this.jjMoveStringLiteralDfa1_0(18014501857132544L, 0L);
         case 'N':
            return this.jjMoveStringLiteralDfa1_0(2207613321216L, 0L);
         case 'O':
            return this.jjMoveStringLiteralDfa1_0(550326239232L, 2080L);
         case 'S':
            return this.jjMoveStringLiteralDfa1_0(621505682111201280L, 1024L);
         case 'T':
            return this.jjMoveStringLiteralDfa1_0(299067162755072L, 0L);
         case 'U':
            return this.jjMoveStringLiteralDfa1_0(4611756387171565568L, 0L);
         case 'W':
            return this.jjMoveStringLiteralDfa1_0(0L, 1L);
         case 'a':
            return this.jjMoveStringLiteralDfa1_0(4504716328042496L, 8L);
         case 'b':
            return this.jjMoveStringLiteralDfa1_0(562954315497472L, 0L);
         case 'c':
            return this.jjMoveStringLiteralDfa1_0(504407831189913600L, 0L);
         case 'd':
            return this.jjMoveStringLiteralDfa1_0(-8070450532231151616L, 0L);
         case 'e':
            return this.jjMoveStringLiteralDfa1_0(2152726528L, 0L);
         case 'f':
            return this.jjMoveStringLiteralDfa1_0(2305843009213693952L, 256L);
         case 'g':
            return this.jjMoveStringLiteralDfa1_0(0L, 2L);
         case 'h':
            return this.jjMoveStringLiteralDfa1_0(0L, 4L);
         case 'i':
            return this.jjMoveStringLiteralDfa1_0(134217728L, 576L);
         case 'j':
            return this.jjMoveStringLiteralDfa1_0(0L, 128L);
         case 'l':
            return this.jjMoveStringLiteralDfa1_0(3553622654713856L, 16L);
         case 'm':
            return this.jjMoveStringLiteralDfa1_0(18014501857132544L, 0L);
         case 'n':
            return this.jjMoveStringLiteralDfa1_0(2207613321216L, 0L);
         case 'o':
            return this.jjMoveStringLiteralDfa1_0(550326239232L, 2080L);
         case 's':
            return this.jjMoveStringLiteralDfa1_0(621505682111201280L, 1024L);
         case 't':
            return this.jjMoveStringLiteralDfa1_0(299067162755072L, 0L);
         case 'u':
            return this.jjMoveStringLiteralDfa1_0(4611756387171565568L, 0L);
         case 'w':
            return this.jjMoveStringLiteralDfa1_0(0L, 1L);
      }
   }

   private final int jjMoveStringLiteralDfa1_0(long active0, long active1) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var6) {
         return this.jjMoveNfa_0(9, 0);
      }

      switch (this.curChar) {
         case '=':
            if ((active0 & 1024L) != 0L) {
               this.jjmatchedKind = 10;
               this.jjmatchedPos = 1;
            } else if ((active0 & 4096L) != 0L) {
               this.jjmatchedKind = 12;
               this.jjmatchedPos = 1;
            }
            break;
         case '>':
            if ((active0 & 256L) != 0L) {
               this.jjmatchedKind = 8;
               this.jjmatchedPos = 1;
            }
         case '?':
         case '@':
         case 'C':
         case 'D':
         case 'G':
         case 'J':
         case 'K':
         case 'T':
         case 'W':
         case 'Z':
         case '[':
         case '\\':
         case ']':
         case '^':
         case '_':
         case '`':
         case 'c':
         case 'd':
         case 'g':
         case 'j':
         case 'k':
         case 't':
         case 'w':
         default:
            break;
         case 'A':
            return this.jjMoveStringLiteralDfa2_0(active0, 68719476736L, active1, 4L);
         case 'B':
            return this.jjMoveStringLiteralDfa2_0(active0, 4503599627370496L, active1, 2048L);
         case 'E':
            return this.jjMoveStringLiteralDfa2_0(active0, -8645644642575843328L, active1, 1296L);
         case 'F':
            if ((active0 & 536870912L) != 0L) {
               this.jjmatchedKind = 29;
               this.jjmatchedPos = 1;
            }
            break;
         case 'H':
            return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 1L);
         case 'I':
            return this.jjMoveStringLiteralDfa2_0(active0, 1188950337059291136L, active1, 0L);
         case 'L':
            return this.jjMoveStringLiteralDfa2_0(active0, 262144L, active1, 0L);
         case 'M':
            return this.jjMoveStringLiteralDfa2_0(active0, 4194304L, active1, 0L);
         case 'N':
            if ((active1 & 512L) != 0L) {
               this.jjmatchedKind = 73;
               this.jjmatchedPos = 1;
            }

            return this.jjMoveStringLiteralDfa2_0(active0, 1099512152064L, active1, 64L);
         case 'O':
            return this.jjMoveStringLiteralDfa2_0(active0, 20871204598448128L, active1, 128L);
         case 'P':
            return this.jjMoveStringLiteralDfa2_0(active0, 4611756387171565568L, active1, 0L);
         case 'Q':
            return this.jjMoveStringLiteralDfa2_0(active0, 9007199254740992L, active1, 0L);
         case 'R':
            if ((active0 & 549755813888L) != 0L) {
               this.jjmatchedKind = 39;
               this.jjmatchedPos = 1;
            }

            return this.jjMoveStringLiteralDfa2_0(active0, 2306142076410003456L, active1, 2L);
         case 'S':
            if ((active0 & 134217728L) != 0L) {
               this.jjmatchedKind = 27;
               this.jjmatchedPos = 1;
            } else if ((active1 & 8L) != 0L) {
               this.jjmatchedKind = 67;
               this.jjmatchedPos = 1;
            }

            return this.jjMoveStringLiteralDfa2_0(active0, 2155872256L, active1, 0L);
         case 'U':
            return this.jjMoveStringLiteralDfa2_0(active0, 504412100387405824L, active1, 32L);
         case 'V':
            return this.jjMoveStringLiteralDfa2_0(active0, 17179869184L, active1, 0L);
         case 'X':
            return this.jjMoveStringLiteralDfa2_0(active0, 1048576L, active1, 0L);
         case 'Y':
            if ((active0 & 67108864L) != 0L) {
               this.jjmatchedKind = 26;
               this.jjmatchedPos = 1;
            }
            break;
         case 'a':
            return this.jjMoveStringLiteralDfa2_0(active0, 68719476736L, active1, 4L);
         case 'b':
            return this.jjMoveStringLiteralDfa2_0(active0, 4503599627370496L, active1, 2048L);
         case 'e':
            return this.jjMoveStringLiteralDfa2_0(active0, -8645644642575843328L, active1, 1296L);
         case 'f':
            if ((active0 & 536870912L) != 0L) {
               this.jjmatchedKind = 29;
               this.jjmatchedPos = 1;
            }
            break;
         case 'h':
            return this.jjMoveStringLiteralDfa2_0(active0, 0L, active1, 1L);
         case 'i':
            return this.jjMoveStringLiteralDfa2_0(active0, 1188950337059291136L, active1, 0L);
         case 'l':
            return this.jjMoveStringLiteralDfa2_0(active0, 262144L, active1, 0L);
         case 'm':
            return this.jjMoveStringLiteralDfa2_0(active0, 4194304L, active1, 0L);
         case 'n':
            if ((active1 & 512L) != 0L) {
               this.jjmatchedKind = 73;
               this.jjmatchedPos = 1;
            }

            return this.jjMoveStringLiteralDfa2_0(active0, 1099512152064L, active1, 64L);
         case 'o':
            return this.jjMoveStringLiteralDfa2_0(active0, 20871204598448128L, active1, 128L);
         case 'p':
            return this.jjMoveStringLiteralDfa2_0(active0, 4611756387171565568L, active1, 0L);
         case 'q':
            return this.jjMoveStringLiteralDfa2_0(active0, 9007199254740992L, active1, 0L);
         case 'r':
            if ((active0 & 549755813888L) != 0L) {
               this.jjmatchedKind = 39;
               this.jjmatchedPos = 1;
            }

            return this.jjMoveStringLiteralDfa2_0(active0, 2306142076410003456L, active1, 2L);
         case 's':
            if ((active0 & 134217728L) != 0L) {
               this.jjmatchedKind = 27;
               this.jjmatchedPos = 1;
            } else if ((active1 & 8L) != 0L) {
               this.jjmatchedKind = 67;
               this.jjmatchedPos = 1;
            }

            return this.jjMoveStringLiteralDfa2_0(active0, 2155872256L, active1, 0L);
         case 'u':
            return this.jjMoveStringLiteralDfa2_0(active0, 504412100387405824L, active1, 32L);
         case 'v':
            return this.jjMoveStringLiteralDfa2_0(active0, 17179869184L, active1, 0L);
         case 'x':
            return this.jjMoveStringLiteralDfa2_0(active0, 1048576L, active1, 0L);
         case 'y':
            if ((active0 & 67108864L) != 0L) {
               this.jjmatchedKind = 26;
               this.jjmatchedPos = 1;
            }
      }

      return this.jjMoveNfa_0(9, 1);
   }

   private final int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1) {
      if (((active0 &= old0) | (active1 &= old1)) == 0L) {
         return this.jjMoveNfa_0(9, 1);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            return this.jjMoveNfa_0(9, 1);
         }

         switch (this.curChar) {
            case 'A':
               return this.jjMoveStringLiteralDfa3_0(active0, 422212465065984L, active1, 0L);
            case 'B':
               return this.jjMoveStringLiteralDfa3_0(active0, 8796093022208L, active1, 0L);
            case 'C':
               if ((active0 & 8388608L) != 0L) {
                  this.jjmatchedKind = 23;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 2251801961168896L, active1, 0L);
            case 'D':
               if ((active0 & 1099511627776L) != 0L) {
                  this.jjmatchedKind = 40;
                  this.jjmatchedPos = 2;
               } else if ((active0 & 18014398509481984L) != 0L) {
                  this.jjmatchedKind = 54;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 4611686018460942336L, active1, 0L);
            case 'E':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 1L);
            case 'F':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 16L);
            case 'G':
               if ((active0 & 17179869184L) != 0L) {
                  this.jjmatchedKind = 34;
                  this.jjmatchedPos = 2;
               }
            case 'H':
            case 'Q':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'h':
            case 'q':
            default:
               break;
            case 'I':
               return this.jjMoveStringLiteralDfa3_0(active0, 17592187092992L, active1, 128L);
            case 'J':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 2048L);
            case 'K':
               return this.jjMoveStringLiteralDfa3_0(active0, 1073741824L, active1, 0L);
            case 'L':
               if ((active0 & 262144L) != 0L) {
                  this.jjmatchedKind = 18;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, -8646911275961417728L, active1, 0L);
            case 'M':
               if ((active0 & 137438953472L) != 0L) {
                  this.jjmatchedKind = 37;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 270532608L, active1, 0L);
            case 'N':
               if ((active0 & 34359738368L) != 0L) {
                  this.jjmatchedKind = 35;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 1130297953353728L, active1, 64L);
            case 'O':
               return this.jjMoveStringLiteralDfa3_0(active0, 2305843009213693952L, active1, 2L);
            case 'P':
               return this.jjMoveStringLiteralDfa3_0(active0, 70368748371968L, active1, 0L);
            case 'R':
               return this.jjMoveStringLiteralDfa3_0(active0, 513410357520236544L, active1, 0L);
            case 'S':
               if ((active0 & 4503599627370496L) != 0L) {
                  this.jjmatchedKind = 52;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 1152921504623624192L, active1, 0L);
            case 'T':
               if ((active0 & 2199023255552L) != 0L) {
                  this.jjmatchedKind = 41;
                  this.jjmatchedPos = 2;
               } else if ((active1 & 1024L) != 0L) {
                  this.jjmatchedKind = 74;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 562954248388608L, active1, 288L);
            case 'U':
               return this.jjMoveStringLiteralDfa3_0(active0, 274877906944L, active1, 0L);
            case 'V':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 4L);
            case 'W':
               if ((active0 & 131072L) != 0L) {
                  this.jjmatchedKind = 17;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 35184372088832L, active1, 0L);
            case 'X':
               if ((active0 & 68719476736L) != 0L) {
                  this.jjmatchedKind = 36;
                  this.jjmatchedPos = 2;
               }
               break;
            case 'Y':
               if ((active0 & 524288L) != 0L) {
                  this.jjmatchedKind = 19;
                  this.jjmatchedPos = 2;
               }
               break;
            case 'Z':
               return this.jjMoveStringLiteralDfa3_0(active0, 36028797018963968L, active1, 0L);
            case 'a':
               return this.jjMoveStringLiteralDfa3_0(active0, 422212465065984L, active1, 0L);
            case 'b':
               return this.jjMoveStringLiteralDfa3_0(active0, 8796093022208L, active1, 0L);
            case 'c':
               if ((active0 & 8388608L) != 0L) {
                  this.jjmatchedKind = 23;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 2251801961168896L, active1, 0L);
            case 'd':
               if ((active0 & 1099511627776L) != 0L) {
                  this.jjmatchedKind = 40;
                  this.jjmatchedPos = 2;
               } else if ((active0 & 18014398509481984L) != 0L) {
                  this.jjmatchedKind = 54;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 4611686018460942336L, active1, 0L);
            case 'e':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 1L);
            case 'f':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 16L);
            case 'g':
               if ((active0 & 17179869184L) != 0L) {
                  this.jjmatchedKind = 34;
                  this.jjmatchedPos = 2;
               }
               break;
            case 'i':
               return this.jjMoveStringLiteralDfa3_0(active0, 17592187092992L, active1, 128L);
            case 'j':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 2048L);
            case 'k':
               return this.jjMoveStringLiteralDfa3_0(active0, 1073741824L, active1, 0L);
            case 'l':
               if ((active0 & 262144L) != 0L) {
                  this.jjmatchedKind = 18;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, -8646911275961417728L, active1, 0L);
            case 'm':
               if ((active0 & 137438953472L) != 0L) {
                  this.jjmatchedKind = 37;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 270532608L, active1, 0L);
            case 'n':
               if ((active0 & 34359738368L) != 0L) {
                  this.jjmatchedKind = 35;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 1130297953353728L, active1, 64L);
            case 'o':
               return this.jjMoveStringLiteralDfa3_0(active0, 2305843009213693952L, active1, 2L);
            case 'p':
               return this.jjMoveStringLiteralDfa3_0(active0, 70368748371968L, active1, 0L);
            case 'r':
               return this.jjMoveStringLiteralDfa3_0(active0, 513410357520236544L, active1, 0L);
            case 's':
               if ((active0 & 4503599627370496L) != 0L) {
                  this.jjmatchedKind = 52;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 1152921504623624192L, active1, 0L);
            case 't':
               if ((active0 & 2199023255552L) != 0L) {
                  this.jjmatchedKind = 41;
                  this.jjmatchedPos = 2;
               } else if ((active1 & 1024L) != 0L) {
                  this.jjmatchedKind = 74;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 562954248388608L, active1, 288L);
            case 'u':
               return this.jjMoveStringLiteralDfa3_0(active0, 274877906944L, active1, 0L);
            case 'v':
               return this.jjMoveStringLiteralDfa3_0(active0, 0L, active1, 4L);
            case 'w':
               if ((active0 & 131072L) != 0L) {
                  this.jjmatchedKind = 17;
                  this.jjmatchedPos = 2;
               }

               return this.jjMoveStringLiteralDfa3_0(active0, 35184372088832L, active1, 0L);
            case 'x':
               if ((active0 & 68719476736L) != 0L) {
                  this.jjmatchedKind = 36;
                  this.jjmatchedPos = 2;
               }
               break;
            case 'y':
               if ((active0 & 524288L) != 0L) {
                  this.jjmatchedKind = 19;
                  this.jjmatchedPos = 2;
               }
               break;
            case 'z':
               return this.jjMoveStringLiteralDfa3_0(active0, 36028797018963968L, active1, 0L);
         }

         return this.jjMoveNfa_0(9, 2);
      }
   }

   private final int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1) {
      if (((active0 &= old0) | (active1 &= old1)) == 0L) {
         return this.jjMoveNfa_0(9, 2);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            return this.jjMoveNfa_0(9, 2);
         }

         switch (this.curChar) {
            case 'A':
               return this.jjMoveStringLiteralDfa4_0(active0, 4613937820388556800L, active1, 0L);
            case 'B':
               return this.jjMoveStringLiteralDfa4_0(active0, 268435456L, active1, 0L);
            case 'C':
               if ((active0 & 16777216L) != 0L) {
                  this.jjmatchedKind = 24;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, 4398046511104L, active1, 256L);
            case 'D':
               return this.jjMoveStringLiteralDfa4_0(active0, 140737488355328L, active1, 0L);
            case 'E':
               if ((active0 & 2097152L) != 0L) {
                  this.jjmatchedKind = 21;
                  this.jjmatchedPos = 3;
               } else if ((active0 & 1073741824L) != 0L) {
                  this.jjmatchedKind = 30;
                  this.jjmatchedPos = 3;
               } else if ((active0 & 36028797018963968L) != 0L) {
                  this.jjmatchedKind = 55;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, -8646805731401531392L, active1, 2144L);
            case 'F':
            case 'J':
            case 'K':
            case 'O':
            case 'P':
            case 'Q':
            case 'V':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'f':
            case 'j':
            case 'k':
            case 'o':
            case 'p':
            case 'q':
            case 'v':
            default:
               break;
            case 'G':
               return this.jjMoveStringLiteralDfa4_0(active0, 1125899906842624L, active1, 0L);
            case 'H':
               if ((active0 & 562949953421312L) != 0L) {
                  this.jjmatchedKind = 49;
                  this.jjmatchedPos = 3;
               }
               break;
            case 'I':
               return this.jjMoveStringLiteralDfa4_0(active0, 281474976710656L, active1, 4L);
            case 'L':
               if ((active0 & 8589934592L) != 0L) {
                  this.jjmatchedKind = 33;
                  this.jjmatchedPos = 3;
               }
               break;
            case 'M':
               if ((active0 & 17592186044416L) != 0L) {
                  this.jjmatchedKind = 44;
                  this.jjmatchedPos = 3;
               } else if ((active0 & 2305843009213693952L) != 0L) {
                  this.jjmatchedKind = 61;
                  this.jjmatchedPos = 3;
               }
               break;
            case 'N':
               if ((active1 & 128L) != 0L) {
                  this.jjmatchedKind = 71;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, 274877906944L, active1, 0L);
            case 'R':
               return this.jjMoveStringLiteralDfa4_0(active0, 504403158265495552L, active1, 1L);
            case 'S':
               return this.jjMoveStringLiteralDfa4_0(active0, 8796094070784L, active1, 0L);
            case 'T':
               if ((active0 & 9007199254740992L) != 0L) {
                  this.jjmatchedKind = 53;
                  this.jjmatchedPos = 3;
               } else if ((active1 & 16L) != 0L) {
                  this.jjmatchedKind = 68;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, 1152921504611041280L, active1, 0L);
            case 'U':
               return this.jjMoveStringLiteralDfa4_0(active0, 0L, active1, 2L);
            case 'W':
               return this.jjMoveStringLiteralDfa4_0(active0, 4294967296L, active1, 0L);
            case 'a':
               return this.jjMoveStringLiteralDfa4_0(active0, 4613937820388556800L, active1, 0L);
            case 'b':
               return this.jjMoveStringLiteralDfa4_0(active0, 268435456L, active1, 0L);
            case 'c':
               if ((active0 & 16777216L) != 0L) {
                  this.jjmatchedKind = 24;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, 4398046511104L, active1, 256L);
            case 'd':
               return this.jjMoveStringLiteralDfa4_0(active0, 140737488355328L, active1, 0L);
            case 'e':
               if ((active0 & 2097152L) != 0L) {
                  this.jjmatchedKind = 21;
                  this.jjmatchedPos = 3;
               } else if ((active0 & 1073741824L) != 0L) {
                  this.jjmatchedKind = 30;
                  this.jjmatchedPos = 3;
               } else if ((active0 & 36028797018963968L) != 0L) {
                  this.jjmatchedKind = 55;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, -8646805731401531392L, active1, 2144L);
            case 'g':
               return this.jjMoveStringLiteralDfa4_0(active0, 1125899906842624L, active1, 0L);
            case 'h':
               if ((active0 & 562949953421312L) != 0L) {
                  this.jjmatchedKind = 49;
                  this.jjmatchedPos = 3;
               }
               break;
            case 'i':
               return this.jjMoveStringLiteralDfa4_0(active0, 281474976710656L, active1, 4L);
            case 'l':
               if ((active0 & 8589934592L) != 0L) {
                  this.jjmatchedKind = 33;
                  this.jjmatchedPos = 3;
               }
               break;
            case 'm':
               if ((active0 & 17592186044416L) != 0L) {
                  this.jjmatchedKind = 44;
                  this.jjmatchedPos = 3;
               } else if ((active0 & 2305843009213693952L) != 0L) {
                  this.jjmatchedKind = 61;
                  this.jjmatchedPos = 3;
               }
               break;
            case 'n':
               if ((active1 & 128L) != 0L) {
                  this.jjmatchedKind = 71;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, 274877906944L, active1, 0L);
            case 'r':
               return this.jjMoveStringLiteralDfa4_0(active0, 504403158265495552L, active1, 1L);
            case 's':
               return this.jjMoveStringLiteralDfa4_0(active0, 8796094070784L, active1, 0L);
            case 't':
               if ((active0 & 9007199254740992L) != 0L) {
                  this.jjmatchedKind = 53;
                  this.jjmatchedPos = 3;
               } else if ((active1 & 16L) != 0L) {
                  this.jjmatchedKind = 68;
                  this.jjmatchedPos = 3;
               }

               return this.jjMoveStringLiteralDfa4_0(active0, 1152921504611041280L, active1, 0L);
            case 'u':
               return this.jjMoveStringLiteralDfa4_0(active0, 0L, active1, 2L);
            case 'w':
               return this.jjMoveStringLiteralDfa4_0(active0, 4294967296L, active1, 0L);
         }

         return this.jjMoveNfa_0(9, 3);
      }
   }

   private final int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1) {
      if (((active0 &= old0) | (active1 &= old1)) == 0L) {
         return this.jjMoveNfa_0(9, 3);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            return this.jjMoveNfa_0(9, 3);
         }

         switch (this.curChar) {
            case 'A':
               return this.jjMoveStringLiteralDfa5_0(active0, 4398046511104L, active1, 0L);
            case 'B':
            case 'D':
            case 'F':
            case 'G':
            case 'J':
            case 'K':
            case 'M':
            case 'O':
            case 'Q':
            case 'S':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'b':
            case 'd':
            case 'f':
            case 'g':
            case 'j':
            case 'k':
            case 'm':
            case 'o':
            case 'q':
            case 's':
            case 'u':
            case 'v':
            case 'w':
            case 'x':
            default:
               break;
            case 'C':
               return this.jjMoveStringLiteralDfa5_0(active0, 576460752303423488L, active1, 2048L);
            case 'E':
               if ((active1 & 1L) != 0L) {
                  this.jjmatchedKind = 64;
                  this.jjmatchedPos = 4;
               }

               return this.jjMoveStringLiteralDfa5_0(active0, 504403162828898304L, active1, 0L);
            case 'H':
               if ((active1 & 256L) != 0L) {
                  this.jjmatchedKind = 72;
                  this.jjmatchedPos = 4;
               }
               break;
            case 'I':
               return this.jjMoveStringLiteralDfa5_0(active0, 1153062242095202304L, active1, 0L);
            case 'L':
               return this.jjMoveStringLiteralDfa5_0(active0, 281474976710656L, active1, 0L);
            case 'N':
               return this.jjMoveStringLiteralDfa5_0(active0, 0L, active1, 4L);
            case 'P':
               if ((active1 & 2L) != 0L) {
                  this.jjmatchedKind = 65;
                  this.jjmatchedPos = 4;
               }

               return this.jjMoveStringLiteralDfa5_0(active0, 2147483648L, active1, 0L);
            case 'R':
               if ((active0 & 33554432L) != 0L) {
                  this.jjmatchedKind = 25;
                  this.jjmatchedPos = 4;
               } else if ((active0 & 35184372088832L) != 0L) {
                  this.jjmatchedKind = 45;
                  this.jjmatchedPos = 4;
               } else if ((active0 & 70368744177664L) != 0L) {
                  this.jjmatchedKind = 46;
                  this.jjmatchedPos = 4;
               } else if ((active1 & 32L) != 0L) {
                  this.jjmatchedKind = 69;
                  this.jjmatchedPos = 4;
               } else if ((active1 & 64L) != 0L) {
                  this.jjmatchedKind = 70;
                  this.jjmatchedPos = 4;
               }
               break;
            case 'T':
               if ((active0 & 274877906944L) != 0L) {
                  this.jjmatchedKind = 38;
                  this.jjmatchedPos = 4;
               }

               return this.jjMoveStringLiteralDfa5_0(active0, -4608299522612789248L, active1, 0L);
            case 'Y':
               if ((active0 & 4194304L) != 0L) {
                  this.jjmatchedKind = 22;
                  this.jjmatchedPos = 4;
               }
               break;
            case 'a':
               return this.jjMoveStringLiteralDfa5_0(active0, 4398046511104L, active1, 0L);
            case 'c':
               return this.jjMoveStringLiteralDfa5_0(active0, 576460752303423488L, active1, 2048L);
            case 'e':
               if ((active1 & 1L) != 0L) {
                  this.jjmatchedKind = 64;
                  this.jjmatchedPos = 4;
               }

               return this.jjMoveStringLiteralDfa5_0(active0, 504403162828898304L, active1, 0L);
            case 'h':
               if ((active1 & 256L) != 0L) {
                  this.jjmatchedKind = 72;
                  this.jjmatchedPos = 4;
               }
               break;
            case 'i':
               return this.jjMoveStringLiteralDfa5_0(active0, 1153062242095202304L, active1, 0L);
            case 'l':
               return this.jjMoveStringLiteralDfa5_0(active0, 281474976710656L, active1, 0L);
            case 'n':
               return this.jjMoveStringLiteralDfa5_0(active0, 0L, active1, 4L);
            case 'p':
               if ((active1 & 2L) != 0L) {
                  this.jjmatchedKind = 65;
                  this.jjmatchedPos = 4;
               }

               return this.jjMoveStringLiteralDfa5_0(active0, 2147483648L, active1, 0L);
            case 'r':
               if ((active0 & 33554432L) != 0L) {
                  this.jjmatchedKind = 25;
                  this.jjmatchedPos = 4;
               } else if ((active0 & 35184372088832L) != 0L) {
                  this.jjmatchedKind = 45;
                  this.jjmatchedPos = 4;
               } else if ((active0 & 70368744177664L) != 0L) {
                  this.jjmatchedKind = 46;
                  this.jjmatchedPos = 4;
               } else if ((active1 & 32L) != 0L) {
                  this.jjmatchedKind = 69;
                  this.jjmatchedPos = 4;
               } else if ((active1 & 64L) != 0L) {
                  this.jjmatchedKind = 70;
                  this.jjmatchedPos = 4;
               }
               break;
            case 't':
               if ((active0 & 274877906944L) != 0L) {
                  this.jjmatchedKind = 38;
                  this.jjmatchedPos = 4;
               }

               return this.jjMoveStringLiteralDfa5_0(active0, -4608299522612789248L, active1, 0L);
            case 'y':
               if ((active0 & 4194304L) != 0L) {
                  this.jjmatchedKind = 22;
                  this.jjmatchedPos = 4;
               }
         }

         return this.jjMoveNfa_0(9, 4);
      }
   }

   private final int jjMoveStringLiteralDfa5_0(long old0, long active0, long old1, long active1) {
      if (((active0 &= old0) | (active1 &= old1)) == 0L) {
         return this.jjMoveNfa_0(9, 4);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            return this.jjMoveNfa_0(9, 4);
         }

         switch (this.curChar) {
            case 'E':
               if ((active0 & 2147483648L) != 0L) {
                  this.jjmatchedKind = 31;
                  this.jjmatchedPos = 5;
               } else if ((active0 & 2251799813685248L) != 0L) {
                  this.jjmatchedKind = 51;
                  this.jjmatchedPos = 5;
               } else if ((active0 & 4611686018427387904L) != 0L) {
                  this.jjmatchedKind = 62;
                  this.jjmatchedPos = 5;
               } else if ((active0 & Long.MIN_VALUE) != 0L) {
                  this.jjmatchedKind = 63;
                  this.jjmatchedPos = 5;
               }

               return this.jjMoveStringLiteralDfa6_0(active0, 4294967296L, active1, 0L);
            case 'F':
            case 'J':
            case 'K':
            case 'L':
            case 'M':
            case 'O':
            case 'P':
            case 'Q':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            case 'Z':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'a':
            case 'b':
            case 'c':
            case 'd':
            case 'f':
            case 'j':
            case 'k':
            case 'l':
            case 'm':
            case 'o':
            case 'p':
            case 'q':
            default:
               break;
            case 'G':
               if ((active1 & 4L) != 0L) {
                  this.jjmatchedKind = 66;
                  this.jjmatchedPos = 5;
               }
               break;
            case 'H':
               if ((active0 & 1125899906842624L) != 0L) {
                  this.jjmatchedKind = 50;
                  this.jjmatchedPos = 5;
               }
               break;
            case 'I':
               return this.jjMoveStringLiteralDfa6_0(active0, 281474976710656L, active1, 0L);
            case 'N':
               return this.jjMoveStringLiteralDfa6_0(active0, 1657465400360697856L, active1, 0L);
            case 'R':
               if ((active0 & 268435456L) != 0L) {
                  this.jjmatchedKind = 28;
                  this.jjmatchedPos = 5;
               }

               return this.jjMoveStringLiteralDfa6_0(active0, 8796093022208L, active1, 0L);
            case 'S':
               if ((active0 & 1048576L) != 0L) {
                  this.jjmatchedKind = 20;
                  this.jjmatchedPos = 5;
               }
               break;
            case 'T':
               if ((active0 & 4398046511104L) != 0L) {
                  this.jjmatchedKind = 42;
                  this.jjmatchedPos = 5;
               } else if ((active0 & 576460752303423488L) != 0L) {
                  this.jjmatchedKind = 59;
                  this.jjmatchedPos = 5;
               } else if ((active1 & 2048L) != 0L) {
                  this.jjmatchedKind = 75;
                  this.jjmatchedPos = 5;
               }
               break;
            case 'e':
               if ((active0 & 2147483648L) != 0L) {
                  this.jjmatchedKind = 31;
                  this.jjmatchedPos = 5;
               } else if ((active0 & 2251799813685248L) != 0L) {
                  this.jjmatchedKind = 51;
                  this.jjmatchedPos = 5;
               } else if ((active0 & 4611686018427387904L) != 0L) {
                  this.jjmatchedKind = 62;
                  this.jjmatchedPos = 5;
               } else if ((active0 & Long.MIN_VALUE) != 0L) {
                  this.jjmatchedKind = 63;
                  this.jjmatchedPos = 5;
               }

               return this.jjMoveStringLiteralDfa6_0(active0, 4294967296L, active1, 0L);
            case 'g':
               if ((active1 & 4L) != 0L) {
                  this.jjmatchedKind = 66;
                  this.jjmatchedPos = 5;
               }
               break;
            case 'h':
               if ((active0 & 1125899906842624L) != 0L) {
                  this.jjmatchedKind = 50;
                  this.jjmatchedPos = 5;
               }
               break;
            case 'i':
               return this.jjMoveStringLiteralDfa6_0(active0, 281474976710656L, active1, 0L);
            case 'n':
               return this.jjMoveStringLiteralDfa6_0(active0, 1657465400360697856L, active1, 0L);
            case 'r':
               if ((active0 & 268435456L) != 0L) {
                  this.jjmatchedKind = 28;
                  this.jjmatchedPos = 5;
               }

               return this.jjMoveStringLiteralDfa6_0(active0, 8796093022208L, active1, 0L);
            case 's':
               if ((active0 & 1048576L) != 0L) {
                  this.jjmatchedKind = 20;
                  this.jjmatchedPos = 5;
               }
               break;
            case 't':
               if ((active0 & 4398046511104L) != 0L) {
                  this.jjmatchedKind = 42;
                  this.jjmatchedPos = 5;
               } else if ((active0 & 576460752303423488L) != 0L) {
                  this.jjmatchedKind = 59;
                  this.jjmatchedPos = 5;
               } else if ((active1 & 2048L) != 0L) {
                  this.jjmatchedKind = 75;
                  this.jjmatchedPos = 5;
               }
         }

         return this.jjMoveNfa_0(9, 5);
      }
   }

   private final int jjMoveStringLiteralDfa6_0(long old0, long active0, long old1, long active1) {
      if (((active0 &= old0) | active1 & old1) == 0L) {
         return this.jjMoveNfa_0(9, 5);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var10) {
            return this.jjMoveNfa_0(9, 5);
         }

         switch (this.curChar) {
            case 'C':
               return this.jjMoveStringLiteralDfa7_0(active0, 1152921504606846976L);
            case 'G':
               if ((active0 & 140737488355328L) != 0L) {
                  this.jjmatchedKind = 47;
                  this.jjmatchedPos = 6;
               }
               break;
            case 'I':
               return this.jjMoveStringLiteralDfa7_0(active0, 8796093022208L);
            case 'N':
               if ((active0 & 4294967296L) != 0L) {
                  this.jjmatchedKind = 32;
                  this.jjmatchedPos = 6;
               }

               return this.jjMoveStringLiteralDfa7_0(active0, 281474976710656L);
            case 'T':
               return this.jjMoveStringLiteralDfa7_0(active0, 504403158265495552L);
            case 'c':
               return this.jjMoveStringLiteralDfa7_0(active0, 1152921504606846976L);
            case 'g':
               if ((active0 & 140737488355328L) != 0L) {
                  this.jjmatchedKind = 47;
                  this.jjmatchedPos = 6;
               }
               break;
            case 'i':
               return this.jjMoveStringLiteralDfa7_0(active0, 8796093022208L);
            case 'n':
               if ((active0 & 4294967296L) != 0L) {
                  this.jjmatchedKind = 32;
                  this.jjmatchedPos = 6;
               }

               return this.jjMoveStringLiteralDfa7_0(active0, 281474976710656L);
            case 't':
               return this.jjMoveStringLiteralDfa7_0(active0, 504403158265495552L);
         }

         return this.jjMoveNfa_0(9, 6);
      }
   }

   private final int jjMoveStringLiteralDfa7_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 6);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 6);
         }

         switch (this.curChar) {
            case 'G':
               if ((active0 & 281474976710656L) != 0L) {
                  this.jjmatchedKind = 48;
                  this.jjmatchedPos = 7;
               }
               break;
            case 'N':
               return this.jjMoveStringLiteralDfa8_0(active0, 8796093022208L);
            case 'T':
               if ((active0 & 1152921504606846976L) != 0L) {
                  this.jjmatchedKind = 60;
                  this.jjmatchedPos = 7;
               }
               break;
            case '_':
               return this.jjMoveStringLiteralDfa8_0(active0, 504403158265495552L);
            case 'g':
               if ((active0 & 281474976710656L) != 0L) {
                  this.jjmatchedKind = 48;
                  this.jjmatchedPos = 7;
               }
               break;
            case 'n':
               return this.jjMoveStringLiteralDfa8_0(active0, 8796093022208L);
            case 't':
               if ((active0 & 1152921504606846976L) != 0L) {
                  this.jjmatchedKind = 60;
                  this.jjmatchedPos = 7;
               }
         }

         return this.jjMoveNfa_0(9, 7);
      }
   }

   private final int jjMoveStringLiteralDfa8_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 7);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 7);
         }

         switch (this.curChar) {
            case 'D':
               return this.jjMoveStringLiteralDfa9_0(active0, 72057594037927936L);
            case 'G':
               if ((active0 & 8796093022208L) != 0L) {
                  this.jjmatchedKind = 43;
                  this.jjmatchedPos = 8;
               }
               break;
            case 'T':
               return this.jjMoveStringLiteralDfa9_0(active0, 432345564227567616L);
            case 'd':
               return this.jjMoveStringLiteralDfa9_0(active0, 72057594037927936L);
            case 'g':
               if ((active0 & 8796093022208L) != 0L) {
                  this.jjmatchedKind = 43;
                  this.jjmatchedPos = 8;
               }
               break;
            case 't':
               return this.jjMoveStringLiteralDfa9_0(active0, 432345564227567616L);
         }

         return this.jjMoveNfa_0(9, 8);
      }
   }

   private final int jjMoveStringLiteralDfa9_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 8);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 8);
         }

         switch (this.curChar) {
            case 'A':
               return this.jjMoveStringLiteralDfa10_0(active0, 72057594037927936L);
            case 'I':
               return this.jjMoveStringLiteralDfa10_0(active0, 432345564227567616L);
            case 'a':
               return this.jjMoveStringLiteralDfa10_0(active0, 72057594037927936L);
            case 'i':
               return this.jjMoveStringLiteralDfa10_0(active0, 432345564227567616L);
            default:
               return this.jjMoveNfa_0(9, 9);
         }
      }
   }

   private final int jjMoveStringLiteralDfa10_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 9);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 9);
         }

         switch (this.curChar) {
            case 'M':
               return this.jjMoveStringLiteralDfa11_0(active0, 432345564227567616L);
            case 'T':
               return this.jjMoveStringLiteralDfa11_0(active0, 72057594037927936L);
            case 'm':
               return this.jjMoveStringLiteralDfa11_0(active0, 432345564227567616L);
            case 't':
               return this.jjMoveStringLiteralDfa11_0(active0, 72057594037927936L);
            default:
               return this.jjMoveNfa_0(9, 10);
         }
      }
   }

   private final int jjMoveStringLiteralDfa11_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 10);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 10);
         }

         switch (this.curChar) {
            case 'E':
               if ((active0 & 72057594037927936L) != 0L) {
                  this.jjmatchedKind = 56;
                  this.jjmatchedPos = 11;
               } else if ((active0 & 144115188075855872L) != 0L) {
                  this.jjmatchedKind = 57;
                  this.jjmatchedPos = 11;
               }

               return this.jjMoveStringLiteralDfa12_0(active0, 288230376151711744L);
            case 'e':
               if ((active0 & 72057594037927936L) != 0L) {
                  this.jjmatchedKind = 56;
                  this.jjmatchedPos = 11;
               } else if ((active0 & 144115188075855872L) != 0L) {
                  this.jjmatchedKind = 57;
                  this.jjmatchedPos = 11;
               }

               return this.jjMoveStringLiteralDfa12_0(active0, 288230376151711744L);
            default:
               return this.jjMoveNfa_0(9, 11);
         }
      }
   }

   private final int jjMoveStringLiteralDfa12_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 11);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 11);
         }

         switch (this.curChar) {
            case 'S':
               return this.jjMoveStringLiteralDfa13_0(active0, 288230376151711744L);
            case 's':
               return this.jjMoveStringLiteralDfa13_0(active0, 288230376151711744L);
            default:
               return this.jjMoveNfa_0(9, 12);
         }
      }
   }

   private final int jjMoveStringLiteralDfa13_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 12);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 12);
         }

         switch (this.curChar) {
            case 'T':
               return this.jjMoveStringLiteralDfa14_0(active0, 288230376151711744L);
            case 't':
               return this.jjMoveStringLiteralDfa14_0(active0, 288230376151711744L);
            default:
               return this.jjMoveNfa_0(9, 13);
         }
      }
   }

   private final int jjMoveStringLiteralDfa14_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 13);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 13);
         }

         switch (this.curChar) {
            case 'A':
               return this.jjMoveStringLiteralDfa15_0(active0, 288230376151711744L);
            case 'a':
               return this.jjMoveStringLiteralDfa15_0(active0, 288230376151711744L);
            default:
               return this.jjMoveNfa_0(9, 14);
         }
      }
   }

   private final int jjMoveStringLiteralDfa15_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 14);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 14);
         }

         switch (this.curChar) {
            case 'M':
               return this.jjMoveStringLiteralDfa16_0(active0, 288230376151711744L);
            case 'm':
               return this.jjMoveStringLiteralDfa16_0(active0, 288230376151711744L);
            default:
               return this.jjMoveNfa_0(9, 15);
         }
      }
   }

   private final int jjMoveStringLiteralDfa16_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjMoveNfa_0(9, 15);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            return this.jjMoveNfa_0(9, 15);
         }

         switch (this.curChar) {
            case 'P':
               if ((active0 & 288230376151711744L) != 0L) {
                  this.jjmatchedKind = 58;
                  this.jjmatchedPos = 16;
               }
               break;
            case 'p':
               if ((active0 & 288230376151711744L) != 0L) {
                  this.jjmatchedKind = 58;
                  this.jjmatchedPos = 16;
               }
         }

         return this.jjMoveNfa_0(9, 16);
      }
   }

   private final void jjCheckNAdd(int state) {
      if (this.jjrounds[state] != this.jjround) {
         this.jjstateSet[this.jjnewStateCnt++] = state;
         this.jjrounds[state] = this.jjround;
      }

   }

   private final void jjAddStates(int start, int end) {
      do {
         this.jjstateSet[this.jjnewStateCnt++] = jjnextStates[start];
      } while(start++ != end);

   }

   private final void jjCheckNAddTwoStates(int state1, int state2) {
      this.jjCheckNAdd(state1);
      this.jjCheckNAdd(state2);
   }

   private final void jjCheckNAddStates(int start, int end) {
      do {
         this.jjCheckNAdd(jjnextStates[start]);
      } while(start++ != end);

   }

   private final void jjCheckNAddStates(int start) {
      this.jjCheckNAdd(jjnextStates[start]);
      this.jjCheckNAdd(jjnextStates[start + 1]);
   }

   private final int jjMoveNfa_0(int startState, int curPos) {
      int strKind = this.jjmatchedKind;
      int strPos = this.jjmatchedPos;
      int seenUpto;
      this.input_stream.backup(seenUpto = curPos + 1);

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var19) {
         throw new Error("Internal Error");
      }

      curPos = 0;
      int startsAt = 0;
      this.jjnewStateCnt = 46;
      int i = 1;
      this.jjstateSet[0] = startState;
      int kind = Integer.MAX_VALUE;

      int toRet;
      while(true) {
         if (++this.jjround == Integer.MAX_VALUE) {
            this.ReInitRounds();
         }

         long l;
         if (this.curChar < '@') {
            l = 1L << this.curChar;

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if (this.curChar == '.') {
                        this.jjCheckNAdd(1);
                     }
                     break;
                  case 1:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddStates(16, 18);
                     }
                  case 2:
                  case 5:
                  case 6:
                  case 7:
                  case 8:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 18:
                  case 22:
                  case 26:
                  case 30:
                  case 40:
                  default:
                     break;
                  case 3:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(4);
                     }
                     break;
                  case 4:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddTwoStates(4, 5);
                     }
                     break;
                  case 9:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 76) {
                           kind = 76;
                        }

                        this.jjCheckNAddStates(0, 10);
                     } else if (this.curChar == '\'') {
                        this.jjCheckNAddStates(11, 15);
                     } else if (this.curChar == '$') {
                        if (kind > 82) {
                           kind = 82;
                        }

                        this.jjCheckNAdd(15);
                     } else if (this.curChar == '.') {
                        this.jjCheckNAdd(1);
                     }
                     break;
                  case 14:
                     if (this.curChar == '$') {
                        if (kind > 82) {
                           kind = 82;
                        }

                        this.jjCheckNAdd(15);
                     }
                     break;
                  case 15:
                     if ((287948969894477824L & l) != 0L) {
                        if (kind > 82) {
                           kind = 82;
                        }

                        this.jjCheckNAdd(15);
                     }
                     break;
                  case 16:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 76) {
                           kind = 76;
                        }

                        this.jjCheckNAddStates(0, 10);
                     }
                     break;
                  case 17:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 76) {
                           kind = 76;
                        }

                        this.jjCheckNAddTwoStates(17, 18);
                     }
                     break;
                  case 19:
                     if ((287948901175001088L & l) != 0L) {
                        this.jjCheckNAddTwoStates(19, 0);
                     }
                     break;
                  case 20:
                     if ((287948901175001088L & l) != 0L) {
                        this.jjCheckNAddTwoStates(20, 21);
                     }
                     break;
                  case 21:
                     if (this.curChar == '.') {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddTwoStates(22, 5);
                     }
                     break;
                  case 23:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(24);
                     }
                     break;
                  case 24:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddTwoStates(24, 5);
                     }
                     break;
                  case 25:
                     if ((287948901175001088L & l) != 0L) {
                        this.jjCheckNAddTwoStates(25, 26);
                     }
                     break;
                  case 27:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(28);
                     }
                     break;
                  case 28:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddTwoStates(28, 5);
                     }
                     break;
                  case 29:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddStates(19, 21);
                     }
                     break;
                  case 31:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(32);
                     }
                     break;
                  case 32:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 77) {
                           kind = 77;
                        }

                        this.jjCheckNAddTwoStates(32, 5);
                     }
                     break;
                  case 33:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(11, 15);
                     }
                     break;
                  case 34:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(22, 24);
                     }
                     break;
                  case 35:
                     if (this.curChar == '\'') {
                        this.jjstateSet[this.jjnewStateCnt++] = 34;
                     }
                     break;
                  case 36:
                     if ((-549755813889L & l) != 0L) {
                        this.jjCheckNAddStates(22, 24);
                     }
                     break;
                  case 37:
                     if (this.curChar == '\'' && kind > 79) {
                        kind = 79;
                     }
                     break;
                  case 38:
                     if ((-549755823105L & l) != 0L) {
                        this.jjCheckNAdd(39);
                     }
                     break;
                  case 39:
                     if (this.curChar == '\'' && kind > 80) {
                        kind = 80;
                     }
                     break;
                  case 41:
                     if (this.curChar == '\'') {
                        this.jjCheckNAdd(39);
                     }
                     break;
                  case 42:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAddTwoStates(43, 39);
                     }
                     break;
                  case 43:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAdd(39);
                     }
                     break;
                  case 44:
                     if ((4222124650659840L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 45;
                     }
                     break;
                  case 45:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAdd(43);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 2:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(25, 26);
                     }
                  case 3:
                  case 4:
                  case 16:
                  case 17:
                  case 19:
                  case 20:
                  case 21:
                  case 23:
                  case 24:
                  case 25:
                  case 27:
                  case 28:
                  case 29:
                  case 31:
                  case 32:
                  case 33:
                  case 34:
                  case 35:
                  case 37:
                  case 39:
                  default:
                     break;
                  case 5:
                     if ((343597383760L & l) != 0L && kind > 77) {
                        kind = 77;
                     }
                     break;
                  case 6:
                     if ((137438953504L & l) != 0L && kind > 81) {
                        kind = 81;
                     }
                     break;
                  case 7:
                     if ((9007199256838144L & l) != 0L) {
                        this.jjCheckNAdd(6);
                     }
                     break;
                  case 8:
                     if ((1125899907104768L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     }
                     break;
                  case 9:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 82) {
                           kind = 82;
                        }

                        this.jjCheckNAdd(15);
                     }

                     if ((274877907008L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 12;
                     } else if ((4503599628419072L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 8;
                     }
                     break;
                  case 10:
                     if ((2251799814209536L & l) != 0L) {
                        this.jjCheckNAdd(6);
                     }
                     break;
                  case 11:
                     if ((17592186048512L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 10;
                     }
                     break;
                  case 12:
                     if ((8589934594L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 11;
                     }
                     break;
                  case 13:
                     if ((274877907008L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 12;
                     }
                     break;
                  case 14:
                  case 15:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 82) {
                           kind = 82;
                        }

                        this.jjCheckNAdd(15);
                     }
                     break;
                  case 18:
                     if ((17592186048512L & l) != 0L && kind > 76) {
                        kind = 76;
                     }
                     break;
                  case 22:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(27, 28);
                     }
                     break;
                  case 26:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(29, 30);
                     }
                     break;
                  case 30:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(31, 32);
                     }
                     break;
                  case 36:
                     this.jjAddStates(22, 24);
                     break;
                  case 38:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAdd(39);
                     }
                     break;
                  case 40:
                     if (this.curChar == '\\') {
                        this.jjAddStates(33, 35);
                     }
                     break;
                  case 41:
                     if ((5700160604602368L & l) != 0L) {
                        this.jjCheckNAdd(39);
                     }
               }
            } while(i != startsAt);
         } else {
            toRet = this.curChar >> 8;
            int i1 = toRet >> 6;
            long l1 = 1L << (toRet & 63);
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 9:
                  case 15:
                     if (jjCanMove_0(toRet, i1, i2, l1, l2)) {
                        if (kind > 82) {
                           kind = 82;
                        }

                        this.jjCheckNAdd(15);
                     }
                     break;
                  case 36:
                     if (jjCanMove_1(toRet, i1, i2, l1, l2)) {
                        this.jjAddStates(22, 24);
                     }
                     break;
                  case 38:
                     if (jjCanMove_1(toRet, i1, i2, l1, l2)) {
                        this.jjstateSet[this.jjnewStateCnt++] = 39;
                     }
               }
            } while(i != startsAt);
         }

         if (kind != Integer.MAX_VALUE) {
            this.jjmatchedKind = kind;
            this.jjmatchedPos = curPos;
            kind = Integer.MAX_VALUE;
         }

         ++curPos;
         if ((i = this.jjnewStateCnt) == (startsAt = 46 - (this.jjnewStateCnt = startsAt))) {
            break;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var20) {
            break;
         }
      }

      if (this.jjmatchedPos > strPos) {
         return curPos;
      } else {
         toRet = Math.max(curPos, seenUpto);
         if (curPos < toRet) {
            i = toRet - Math.min(curPos, seenUpto);

            while(i-- > 0) {
               try {
                  this.curChar = this.input_stream.readChar();
               } catch (IOException var18) {
                  throw new Error("Internal Error : Please send a bug report.");
               }
            }
         }

         if (this.jjmatchedPos < strPos) {
            this.jjmatchedKind = strKind;
            this.jjmatchedPos = strPos;
         } else if (this.jjmatchedPos == strPos && this.jjmatchedKind > strKind) {
            this.jjmatchedKind = strKind;
         }

         return toRet;
      }
   }

   private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
      switch (hiByte) {
         case 0:
            return (jjbitVec2[i2] & l2) != 0L;
         case 48:
            return (jjbitVec3[i2] & l2) != 0L;
         case 49:
            return (jjbitVec4[i2] & l2) != 0L;
         case 51:
            return (jjbitVec5[i2] & l2) != 0L;
         case 61:
            return (jjbitVec6[i2] & l2) != 0L;
         default:
            return (jjbitVec0[i1] & l1) != 0L;
      }
   }

   private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
      switch (hiByte) {
         case 0:
            return (jjbitVec8[i2] & l2) != 0L;
         default:
            return (jjbitVec7[i1] & l1) != 0L;
      }
   }

   public JPQLTokenManager(JavaCharStream stream) {
      this.debugStream = System.out;
      this.jjrounds = new int[46];
      this.jjstateSet = new int[92];
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = stream;
   }

   public JPQLTokenManager(JavaCharStream stream, int lexState) {
      this(stream);
      this.SwitchTo(lexState);
   }

   public void ReInit(JavaCharStream stream) {
      this.jjmatchedPos = this.jjnewStateCnt = 0;
      this.curLexState = this.defaultLexState;
      this.input_stream = stream;
      this.ReInitRounds();
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;

      for(int i = 46; i-- > 0; this.jjrounds[i] = Integer.MIN_VALUE) {
      }

   }

   public void ReInit(JavaCharStream stream, int lexState) {
      this.ReInit(stream);
      this.SwitchTo(lexState);
   }

   public void SwitchTo(int lexState) {
      if (lexState < 1 && lexState >= 0) {
         this.curLexState = lexState;
      } else {
         throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
      }
   }

   protected Token jjFillToken() {
      Token t = Token.newToken(this.jjmatchedKind);
      t.kind = this.jjmatchedKind;
      String im = jjstrLiteralImages[this.jjmatchedKind];
      t.image = im == null ? this.input_stream.GetImage() : im;
      t.beginLine = this.input_stream.getBeginLine();
      t.beginColumn = this.input_stream.getBeginColumn();
      t.endLine = this.input_stream.getEndLine();
      t.endColumn = this.input_stream.getEndColumn();
      return t;
   }

   public Token getNextToken() {
      Token specialToken = null;
      int curPos = false;

      Token matchedToken;
      do {
         try {
            this.curChar = this.input_stream.BeginToken();
         } catch (IOException var10) {
            this.jjmatchedKind = 0;
            matchedToken = this.jjFillToken();
            return matchedToken;
         }

         this.jjmatchedKind = Integer.MAX_VALUE;
         this.jjmatchedPos = 0;
         int curPos = this.jjMoveStringLiteralDfa0_0();
         if (this.jjmatchedKind == Integer.MAX_VALUE) {
            int error_line = this.input_stream.getEndLine();
            int error_column = this.input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;

            try {
               this.input_stream.readChar();
               this.input_stream.backup(1);
            } catch (IOException var11) {
               EOFSeen = true;
               error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
               if (this.curChar != '\n' && this.curChar != '\r') {
                  ++error_column;
               } else {
                  ++error_line;
                  error_column = 0;
               }
            }

            if (!EOFSeen) {
               this.input_stream.backup(1);
               error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
            }

            throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
         }

         if (this.jjmatchedPos + 1 < curPos) {
            this.input_stream.backup(curPos - this.jjmatchedPos - 1);
         }
      } while((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) == 0L);

      matchedToken = this.jjFillToken();
      return matchedToken;
   }
}
