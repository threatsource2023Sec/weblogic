package org.apache.taglibs.standard.lang.jstl.parser;

import java.io.IOException;
import java.io.PrintStream;

public class ELParserTokenManager implements ELParserConstants {
   public PrintStream debugStream;
   static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
   static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
   static final long[] jjbitVec3 = new long[]{2301339413881290750L, -16384L, 4294967295L, 432345564227567616L};
   static final long[] jjbitVec4 = new long[]{0L, 0L, 0L, -36028797027352577L};
   static final long[] jjbitVec5 = new long[]{0L, -1L, -1L, -1L};
   static final long[] jjbitVec6 = new long[]{-1L, -1L, 65535L, 0L};
   static final long[] jjbitVec7 = new long[]{-1L, -1L, 0L, 0L};
   static final long[] jjbitVec8 = new long[]{70368744177663L, 0L, 0L, 0L};
   static final int[] jjnextStates = new int[]{8, 9, 10, 15, 16, 28, 29, 31, 32, 33, 20, 21, 23, 24, 25, 20, 21, 23, 28, 29, 31, 3, 4, 13, 14, 17, 18, 24, 25, 32, 33};
   public static final String[] jjstrLiteralImages = new String[]{"", null, "${", null, null, null, null, null, null, null, null, null, "true", "false", "null", "}", ".", ">", "gt", "<", "lt", "==", "eq", "<=", "le", ">=", "ge", "!=", "ne", "(", ")", ",", ":", "[", "]", "+", "-", "*", "/", "div", "%", "mod", "not", "!", "and", "&&", "or", "||", "empty", null, null, null, null, null};
   public static final String[] lexStateNames = new String[]{"DEFAULT", "IN_EXPRESSION"};
   public static final int[] jjnewLexState = new int[]{-1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
   static final long[] jjtoToken = new long[]{10133099161582983L};
   static final long[] jjtoSkip = new long[]{120L};
   private SimpleCharStream input_stream;
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

   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 4L) != 0L) {
               this.jjmatchedKind = 1;
               return 2;
            }

            return -1;
         default:
            return -1;
      }
   }

   private final int jjStartNfa_0(int pos, long active0) {
      return this.jjMoveNfa_0(this.jjStopStringLiteralDfa_0(pos, active0), pos + 1);
   }

   private final int jjStopAtPos(int pos, int kind) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;
      return pos + 1;
   }

   private final int jjStartNfaWithStates_0(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_0(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_0() {
      switch (this.curChar) {
         case '$':
            return this.jjMoveStringLiteralDfa1_0(4L);
         default:
            return this.jjMoveNfa_0(1, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_0(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_0(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '{':
            if ((active0 & 4L) != 0L) {
               return this.jjStopAtPos(1, 2);
            }
         default:
            return this.jjStartNfa_0(0, active0);
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
      int startsAt = 0;
      this.jjnewStateCnt = 3;
      int i = 1;
      this.jjstateSet[0] = startState;
      int kind = Integer.MAX_VALUE;

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
                     if ((-68719476737L & l) != 0L) {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjCheckNAdd(0);
                     }
                     break;
                  case 1:
                     if ((-68719476737L & l) != 0L) {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjCheckNAdd(0);
                     } else if (this.curChar == '$') {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjCheckNAdd(2);
                     }
                     break;
                  case 2:
                     if ((-68719476737L & l) != 0L) {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjCheckNAdd(2);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                  case 1:
                     if (kind > 1) {
                        kind = 1;
                     }

                     this.jjCheckNAdd(0);
                     break;
                  case 2:
                     if ((-576460752303423489L & l) != 0L) {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
               }
            } while(i != startsAt);
         } else {
            int hiByte = this.curChar >> 8;
            int i1 = hiByte >> 6;
            long l1 = 1L << (hiByte & 63);
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjCheckNAdd(0);
                     }
                     break;
                  case 2:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        if (kind > 1) {
                           kind = 1;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 2;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_1(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 376583090368512L) != 0L) {
               this.jjmatchedKind = 49;
               return 6;
            } else {
               if ((active0 & 65536L) != 0L) {
                  return 1;
               }

               return -1;
            }
         case 1:
            if ((active0 & 70369102004224L) != 0L) {
               return 6;
            } else {
               if ((active0 & 306213988364288L) != 0L) {
                  this.jjmatchedKind = 49;
                  this.jjmatchedPos = 1;
                  return 6;
               }

               return -1;
            }
         case 2:
            if ((active0 & 24739011624960L) != 0L) {
               return 6;
            } else {
               if ((active0 & 281474976739328L) != 0L) {
                  this.jjmatchedKind = 49;
                  this.jjmatchedPos = 2;
                  return 6;
               }

               return -1;
            }
         case 3:
            if ((active0 & 20480L) != 0L) {
               return 6;
            } else {
               if ((active0 & 281474976718848L) != 0L) {
                  this.jjmatchedKind = 49;
                  this.jjmatchedPos = 3;
                  return 6;
               }

               return -1;
            }
         default:
            return -1;
      }
   }

   private final int jjStartNfa_1(int pos, long active0) {
      return this.jjMoveNfa_1(this.jjStopStringLiteralDfa_1(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_1(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_1(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_1() {
      switch (this.curChar) {
         case '!':
            this.jjmatchedKind = 43;
            return this.jjMoveStringLiteralDfa1_1(134217728L);
         case '"':
         case '#':
         case '$':
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
         case '?':
         case '@':
         case 'A':
         case 'B':
         case 'C':
         case 'D':
         case 'E':
         case 'F':
         case 'G':
         case 'H':
         case 'I':
         case 'J':
         case 'K':
         case 'L':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'S':
         case 'T':
         case 'U':
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         case 'Z':
         case '\\':
         case '^':
         case '_':
         case '`':
         case 'b':
         case 'c':
         case 'h':
         case 'i':
         case 'j':
         case 'k':
         case 'p':
         case 'q':
         case 'r':
         case 's':
         case 'u':
         case 'v':
         case 'w':
         case 'x':
         case 'y':
         case 'z':
         case '{':
         default:
            return this.jjMoveNfa_1(0, 0);
         case '%':
            return this.jjStopAtPos(0, 40);
         case '&':
            return this.jjMoveStringLiteralDfa1_1(35184372088832L);
         case '(':
            return this.jjStopAtPos(0, 29);
         case ')':
            return this.jjStopAtPos(0, 30);
         case '*':
            return this.jjStopAtPos(0, 37);
         case '+':
            return this.jjStopAtPos(0, 35);
         case ',':
            return this.jjStopAtPos(0, 31);
         case '-':
            return this.jjStopAtPos(0, 36);
         case '.':
            return this.jjStartNfaWithStates_1(0, 16, 1);
         case '/':
            return this.jjStopAtPos(0, 38);
         case ':':
            return this.jjStopAtPos(0, 32);
         case '<':
            this.jjmatchedKind = 19;
            return this.jjMoveStringLiteralDfa1_1(8388608L);
         case '=':
            return this.jjMoveStringLiteralDfa1_1(2097152L);
         case '>':
            this.jjmatchedKind = 17;
            return this.jjMoveStringLiteralDfa1_1(33554432L);
         case '[':
            return this.jjStopAtPos(0, 33);
         case ']':
            return this.jjStopAtPos(0, 34);
         case 'a':
            return this.jjMoveStringLiteralDfa1_1(17592186044416L);
         case 'd':
            return this.jjMoveStringLiteralDfa1_1(549755813888L);
         case 'e':
            return this.jjMoveStringLiteralDfa1_1(281474980904960L);
         case 'f':
            return this.jjMoveStringLiteralDfa1_1(8192L);
         case 'g':
            return this.jjMoveStringLiteralDfa1_1(67371008L);
         case 'l':
            return this.jjMoveStringLiteralDfa1_1(17825792L);
         case 'm':
            return this.jjMoveStringLiteralDfa1_1(2199023255552L);
         case 'n':
            return this.jjMoveStringLiteralDfa1_1(4398314962944L);
         case 'o':
            return this.jjMoveStringLiteralDfa1_1(70368744177664L);
         case 't':
            return this.jjMoveStringLiteralDfa1_1(4096L);
         case '|':
            return this.jjMoveStringLiteralDfa1_1(140737488355328L);
         case '}':
            return this.jjStopAtPos(0, 15);
      }
   }

   private final int jjMoveStringLiteralDfa1_1(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_1(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '&':
            if ((active0 & 35184372088832L) != 0L) {
               return this.jjStopAtPos(1, 45);
            }
            break;
         case '=':
            if ((active0 & 2097152L) != 0L) {
               return this.jjStopAtPos(1, 21);
            }

            if ((active0 & 8388608L) != 0L) {
               return this.jjStopAtPos(1, 23);
            }

            if ((active0 & 33554432L) != 0L) {
               return this.jjStopAtPos(1, 25);
            }

            if ((active0 & 134217728L) != 0L) {
               return this.jjStopAtPos(1, 27);
            }
            break;
         case 'a':
            return this.jjMoveStringLiteralDfa2_1(active0, 8192L);
         case 'e':
            if ((active0 & 16777216L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 24, 6);
            }

            if ((active0 & 67108864L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 26, 6);
            }

            if ((active0 & 268435456L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 28, 6);
            }
            break;
         case 'i':
            return this.jjMoveStringLiteralDfa2_1(active0, 549755813888L);
         case 'm':
            return this.jjMoveStringLiteralDfa2_1(active0, 281474976710656L);
         case 'n':
            return this.jjMoveStringLiteralDfa2_1(active0, 17592186044416L);
         case 'o':
            return this.jjMoveStringLiteralDfa2_1(active0, 6597069766656L);
         case 'q':
            if ((active0 & 4194304L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 22, 6);
            }
            break;
         case 'r':
            if ((active0 & 70368744177664L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 46, 6);
            }

            return this.jjMoveStringLiteralDfa2_1(active0, 4096L);
         case 't':
            if ((active0 & 262144L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 18, 6);
            }

            if ((active0 & 1048576L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 20, 6);
            }
            break;
         case 'u':
            return this.jjMoveStringLiteralDfa2_1(active0, 16384L);
         case '|':
            if ((active0 & 140737488355328L) != 0L) {
               return this.jjStopAtPos(1, 47);
            }
      }

      return this.jjStartNfa_1(0, active0);
   }

   private final int jjMoveStringLiteralDfa2_1(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_1(0, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_1(1, active0);
            return 2;
         }

         switch (this.curChar) {
            case 'd':
               if ((active0 & 2199023255552L) != 0L) {
                  return this.jjStartNfaWithStates_1(2, 41, 6);
               }

               if ((active0 & 17592186044416L) != 0L) {
                  return this.jjStartNfaWithStates_1(2, 44, 6);
               }
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'j':
            case 'k':
            case 'm':
            case 'n':
            case 'o':
            case 'q':
            case 'r':
            case 's':
            default:
               break;
            case 'l':
               return this.jjMoveStringLiteralDfa3_1(active0, 24576L);
            case 'p':
               return this.jjMoveStringLiteralDfa3_1(active0, 281474976710656L);
            case 't':
               if ((active0 & 4398046511104L) != 0L) {
                  return this.jjStartNfaWithStates_1(2, 42, 6);
               }
               break;
            case 'u':
               return this.jjMoveStringLiteralDfa3_1(active0, 4096L);
            case 'v':
               if ((active0 & 549755813888L) != 0L) {
                  return this.jjStartNfaWithStates_1(2, 39, 6);
               }
         }

         return this.jjStartNfa_1(1, active0);
      }
   }

   private final int jjMoveStringLiteralDfa3_1(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_1(1, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_1(2, active0);
            return 3;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 4096L) != 0L) {
                  return this.jjStartNfaWithStates_1(3, 12, 6);
               }
               break;
            case 'l':
               if ((active0 & 16384L) != 0L) {
                  return this.jjStartNfaWithStates_1(3, 14, 6);
               }
               break;
            case 's':
               return this.jjMoveStringLiteralDfa4_1(active0, 8192L);
            case 't':
               return this.jjMoveStringLiteralDfa4_1(active0, 281474976710656L);
         }

         return this.jjStartNfa_1(2, active0);
      }
   }

   private final int jjMoveStringLiteralDfa4_1(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_1(2, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_1(3, active0);
            return 4;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 8192L) != 0L) {
                  return this.jjStartNfaWithStates_1(4, 13, 6);
               }
               break;
            case 'y':
               if ((active0 & 281474976710656L) != 0L) {
                  return this.jjStartNfaWithStates_1(4, 48, 6);
               }
         }

         return this.jjStartNfa_1(3, active0);
      }
   }

   private final int jjMoveNfa_1(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 35;
      int i = 1;
      this.jjstateSet[0] = startState;
      int kind = Integer.MAX_VALUE;

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
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 7) {
                           kind = 7;
                        }

                        this.jjCheckNAddStates(0, 4);
                     } else if ((103079215104L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(6);
                     } else if (this.curChar == '\'') {
                        this.jjCheckNAddStates(5, 9);
                     } else if (this.curChar == '"') {
                        this.jjCheckNAddStates(10, 14);
                     } else if (this.curChar == '.') {
                        this.jjCheckNAdd(1);
                     }
                     break;
                  case 1:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAddTwoStates(1, 2);
                     }
                  case 2:
                  case 12:
                  case 16:
                  case 21:
                  case 25:
                  case 29:
                  case 33:
                  default:
                     break;
                  case 3:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(4);
                     }
                     break;
                  case 4:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAdd(4);
                     }
                     break;
                  case 5:
                     if ((103079215104L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(6);
                     }
                     break;
                  case 6:
                     if ((287948969894477824L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(6);
                     }
                     break;
                  case 7:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 7) {
                           kind = 7;
                        }

                        this.jjCheckNAddStates(0, 4);
                     }
                     break;
                  case 8:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 7) {
                           kind = 7;
                        }

                        this.jjCheckNAdd(8);
                     }
                     break;
                  case 9:
                     if ((287948901175001088L & l) != 0L) {
                        this.jjCheckNAddTwoStates(9, 10);
                     }
                     break;
                  case 10:
                     if (this.curChar == '.') {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 11:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 13:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(14);
                     }
                     break;
                  case 14:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAdd(14);
                     }
                     break;
                  case 15:
                     if ((287948901175001088L & l) != 0L) {
                        this.jjCheckNAddTwoStates(15, 16);
                     }
                     break;
                  case 17:
                     if ((43980465111040L & l) != 0L) {
                        this.jjCheckNAdd(18);
                     }
                     break;
                  case 18:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAdd(18);
                     }
                     break;
                  case 19:
                     if (this.curChar == '"') {
                        this.jjCheckNAddStates(10, 14);
                     }
                     break;
                  case 20:
                     if ((-17179869185L & l) != 0L) {
                        this.jjCheckNAddStates(15, 17);
                     }
                     break;
                  case 22:
                     if (this.curChar == '"') {
                        this.jjCheckNAddStates(15, 17);
                     }
                     break;
                  case 23:
                     if (this.curChar == '"' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 24:
                     if ((-17179869185L & l) != 0L) {
                        this.jjCheckNAddTwoStates(24, 25);
                     }
                     break;
                  case 26:
                     if ((-17179869185L & l) != 0L && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 27:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(5, 9);
                     }
                     break;
                  case 28:
                     if ((-549755813889L & l) != 0L) {
                        this.jjCheckNAddStates(18, 20);
                     }
                     break;
                  case 30:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(18, 20);
                     }
                     break;
                  case 31:
                     if (this.curChar == '\'' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 32:
                     if ((-549755813889L & l) != 0L) {
                        this.jjCheckNAddTwoStates(32, 33);
                     }
                     break;
                  case 34:
                     if ((-549755813889L & l) != 0L && kind > 11) {
                        kind = 11;
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                  case 6:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(6);
                     }
                  case 1:
                  case 3:
                  case 4:
                  case 5:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                  case 11:
                  case 13:
                  case 14:
                  case 15:
                  case 17:
                  case 18:
                  case 19:
                  case 23:
                  case 27:
                  case 31:
                  default:
                     break;
                  case 2:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(21, 22);
                     }
                     break;
                  case 12:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(23, 24);
                     }
                     break;
                  case 16:
                     if ((137438953504L & l) != 0L) {
                        this.jjAddStates(25, 26);
                     }
                     break;
                  case 20:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAddStates(15, 17);
                     }
                     break;
                  case 21:
                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 22;
                     }
                     break;
                  case 22:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(15, 17);
                     }
                     break;
                  case 24:
                     if ((-268435457L & l) != 0L) {
                        this.jjAddStates(27, 28);
                     }
                     break;
                  case 25:
                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 26;
                     }
                     break;
                  case 26:
                  case 34:
                     if ((-268435457L & l) != 0L && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 28:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAddStates(18, 20);
                     }
                     break;
                  case 29:
                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 30;
                     }
                     break;
                  case 30:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(18, 20);
                     }
                     break;
                  case 32:
                     if ((-268435457L & l) != 0L) {
                        this.jjAddStates(29, 30);
                     }
                     break;
                  case 33:
                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 34;
                     }
               }
            } while(i != startsAt);
         } else {
            int hiByte = this.curChar >> 8;
            int i1 = hiByte >> 6;
            long l1 = 1L << (hiByte & 63);
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                  case 6:
                     if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(6);
                     }
                     break;
                  case 20:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(15, 17);
                     }
                     break;
                  case 24:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(27, 28);
                     }
                     break;
                  case 26:
                  case 34:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 28:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(18, 20);
                     }
                     break;
                  case 32:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(29, 30);
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
         if ((i = this.jjnewStateCnt) == (startsAt = 35 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
      switch (hiByte) {
         case 0:
            return (jjbitVec2[i2] & l2) != 0L;
         default:
            return (jjbitVec0[i1] & l1) != 0L;
      }
   }

   private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
      switch (hiByte) {
         case 0:
            return (jjbitVec4[i2] & l2) != 0L;
         case 48:
            return (jjbitVec5[i2] & l2) != 0L;
         case 49:
            return (jjbitVec6[i2] & l2) != 0L;
         case 51:
            return (jjbitVec7[i2] & l2) != 0L;
         case 61:
            return (jjbitVec8[i2] & l2) != 0L;
         default:
            return (jjbitVec3[i1] & l1) != 0L;
      }
   }

   public ELParserTokenManager(SimpleCharStream stream) {
      this.debugStream = System.out;
      this.jjrounds = new int[35];
      this.jjstateSet = new int[70];
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = stream;
   }

   public ELParserTokenManager(SimpleCharStream stream, int lexState) {
      this(stream);
      this.SwitchTo(lexState);
   }

   public void ReInit(SimpleCharStream stream) {
      this.jjmatchedPos = this.jjnewStateCnt = 0;
      this.curLexState = this.defaultLexState;
      this.input_stream = stream;
      this.ReInitRounds();
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;

      for(int i = 35; i-- > 0; this.jjrounds[i] = Integer.MIN_VALUE) {
      }

   }

   public void ReInit(SimpleCharStream stream, int lexState) {
      this.ReInit(stream);
      this.SwitchTo(lexState);
   }

   public void SwitchTo(int lexState) {
      if (lexState < 2 && lexState >= 0) {
         this.curLexState = lexState;
      } else {
         throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
      }
   }

   private final Token jjFillToken() {
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

   public final Token getNextToken() {
      Token specialToken = null;
      int curPos = 0;

      while(true) {
         Token matchedToken;
         label89:
         while(true) {
            try {
               this.curChar = this.input_stream.BeginToken();
            } catch (IOException var9) {
               this.jjmatchedKind = 0;
               matchedToken = this.jjFillToken();
               return matchedToken;
            }

            switch (this.curLexState) {
               case 0:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_0();
                  break label89;
               case 1:
                  try {
                     this.input_stream.backup(0);

                     while(this.curChar <= ' ' && (4294977024L & 1L << this.curChar) != 0L) {
                        this.curChar = this.input_stream.BeginToken();
                     }
                  } catch (IOException var11) {
                     break;
                  }

                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_1();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 53) {
                     this.jjmatchedKind = 53;
                  }
               default:
                  break label89;
            }
         }

         if (this.jjmatchedKind == Integer.MAX_VALUE) {
            int error_line = this.input_stream.getEndLine();
            int error_column = this.input_stream.getEndColumn();
            String error_after = null;
            boolean EOFSeen = false;

            try {
               this.input_stream.readChar();
               this.input_stream.backup(1);
            } catch (IOException var10) {
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

         if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
            matchedToken = this.jjFillToken();
            if (jjnewLexState[this.jjmatchedKind] != -1) {
               this.curLexState = jjnewLexState[this.jjmatchedKind];
            }

            return matchedToken;
         }

         if (jjnewLexState[this.jjmatchedKind] != -1) {
            this.curLexState = jjnewLexState[this.jjmatchedKind];
         }
      }
   }
}
