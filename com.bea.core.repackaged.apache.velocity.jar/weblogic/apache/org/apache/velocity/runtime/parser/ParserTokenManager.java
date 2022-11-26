package weblogic.apache.org.apache.velocity.runtime.parser;

import java.io.IOException;
import java.io.PrintStream;
import java.util.EmptyStackException;
import java.util.Hashtable;
import java.util.Stack;

public class ParserTokenManager implements ParserConstants {
   private int fileDepth;
   private int lparen;
   private int rparen;
   Stack stateStack;
   public boolean debugPrint;
   private boolean inReference;
   public boolean inDirective;
   private boolean inComment;
   public boolean inSet;
   public PrintStream debugStream;
   static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
   static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
   static final int[] jjnextStates = new int[]{22, 23, 26, 11, 12, 13, 1, 2, 4, 11, 16, 12, 13, 19, 20, 24, 25, 35, 36, 37, 38, 14, 15, 17, 19, 20, 39, 40, 5, 6, 7, 8, 9, 10, 24, 25, 27, 18, 19, 21, 9, 10, 11, 12, 22, 29, 13, 14, 2, 3, 18, 19, 20, 21, 22, 23, 8, 9, 10, 11, 12, 13, 17, 18, 21, 6, 7, 8, 6, 11, 7, 8, 14, 15, 29, 30, 31, 32, 9, 10, 12, 14, 15, 33, 34};
   public static final String[] jjstrLiteralImages = new String[]{null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
   public static final String[] lexStateNames = new String[]{"DIRECTIVE", "REFMOD2", "REFMODIFIER", "DEFAULT", "PRE_DIRECTIVE", "REFERENCE", "IN_MULTI_LINE_COMMENT", "IN_FORMAL_COMMENT", "IN_SINGLE_LINE_COMMENT"};
   public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
   static final long[] jjtoToken = new long[]{1086211935122162687L};
   static final long[] jjtoSkip = new long[]{3458764513820540928L};
   static final long[] jjtoSpecial = new long[]{3458764513820540928L};
   static final long[] jjtoMore = new long[]{4258816L};
   private CharStream input_stream;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   StringBuffer image;
   int jjimageLen;
   int lengthOfMatch;
   protected char curChar;
   int curLexState;
   int defaultLexState;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;

   public boolean stateStackPop() {
      Hashtable h;
      try {
         h = (Hashtable)this.stateStack.pop();
      } catch (EmptyStackException var3) {
         this.lparen = 0;
         this.SwitchTo(3);
         return false;
      }

      if (this.debugPrint) {
         System.out.println(" stack pop (" + this.stateStack.size() + ") : lparen=" + (Integer)h.get("lparen") + " newstate=" + (Integer)h.get("lexstate"));
      }

      this.lparen = (Integer)h.get("lparen");
      this.rparen = (Integer)h.get("rparen");
      this.SwitchTo((Integer)h.get("lexstate"));
      return true;
   }

   public boolean stateStackPush() {
      if (this.debugPrint) {
         System.out.println(" (" + this.stateStack.size() + ") pushing cur state : " + this.curLexState);
      }

      Hashtable h = new Hashtable();
      h.put("lexstate", new Integer(this.curLexState));
      h.put("lparen", new Integer(this.lparen));
      h.put("rparen", new Integer(this.rparen));
      this.lparen = 0;
      this.stateStack.push(h);
      return true;
   }

   public void clearStateVars() {
      this.stateStack.clear();
      this.lparen = 0;
      this.rparen = 0;
      this.inReference = false;
      this.inDirective = false;
      this.inComment = false;
      this.inSet = false;
   }

   private void RPARENHandler() {
      boolean closed = false;
      if (this.inComment) {
         closed = true;
      }

      while(!closed) {
         if (this.lparen > 0) {
            if (this.lparen == this.rparen + 1) {
               this.stateStackPop();
            } else {
               ++this.rparen;
            }

            closed = true;
         } else if (!this.stateStackPop()) {
            break;
         }
      }

   }

   public void setDebugStream(PrintStream ds) {
      this.debugStream = ds;
   }

   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 52;
               return 33;
            } else if ((active0 & 268435456L) != 0L) {
               return 31;
            } else {
               if ((active0 & 53248L) != 0L) {
                  return 7;
               }

               return -1;
            }
         case 1:
            if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 1;
               return 33;
            } else {
               if ((active0 & 16384L) != 0L) {
                  return 5;
               }

               return -1;
            }
         case 2:
            if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 2;
               return 33;
            }

            return -1;
         case 3:
            if ((active0 & 67108864L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 3;
               return 33;
            } else {
               if ((active0 & 33554432L) != 0L) {
                  return 33;
               }

               return -1;
            }
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
         case '!':
            this.jjmatchedKind = 41;
            return this.jjMoveStringLiteralDfa1_0(1099511627776L);
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_0(20480L);
         case '%':
            return this.jjStopAtPos(0, 32);
         case '&':
            return this.jjMoveStringLiteralDfa1_0(8589934592L);
         case '(':
            return this.jjStopAtPos(0, 5);
         case '*':
            return this.jjStopAtPos(0, 30);
         case '+':
            return this.jjStopAtPos(0, 29);
         case ',':
            return this.jjStopAtPos(0, 3);
         case '-':
            return this.jjStartNfaWithStates_0(0, 28, 31);
         case '.':
            return this.jjMoveStringLiteralDfa1_0(16L);
         case '/':
            return this.jjStopAtPos(0, 31);
         case '<':
            this.jjmatchedKind = 35;
            return this.jjMoveStringLiteralDfa1_0(68719476736L);
         case '=':
            this.jjmatchedKind = 42;
            return this.jjMoveStringLiteralDfa1_0(549755813888L);
         case '>':
            this.jjmatchedKind = 37;
            return this.jjMoveStringLiteralDfa1_0(274877906944L);
         case '[':
            return this.jjStopAtPos(0, 1);
         case ']':
            return this.jjStopAtPos(0, 2);
         case 'f':
            return this.jjMoveStringLiteralDfa1_0(67108864L);
         case 't':
            return this.jjMoveStringLiteralDfa1_0(33554432L);
         case '|':
            return this.jjMoveStringLiteralDfa1_0(17179869184L);
         default:
            return this.jjMoveNfa_0(0, 0);
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
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '&':
            if ((active0 & 8589934592L) != 0L) {
               return this.jjStopAtPos(1, 33);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_0(1, 14, 5);
            }
            break;
         case '.':
            if ((active0 & 16L) != 0L) {
               return this.jjStopAtPos(1, 4);
            }
            break;
         case '=':
            if ((active0 & 68719476736L) != 0L) {
               return this.jjStopAtPos(1, 36);
            }

            if ((active0 & 274877906944L) != 0L) {
               return this.jjStopAtPos(1, 38);
            }

            if ((active0 & 549755813888L) != 0L) {
               return this.jjStopAtPos(1, 39);
            }

            if ((active0 & 1099511627776L) != 0L) {
               return this.jjStopAtPos(1, 40);
            }
            break;
         case 'a':
            return this.jjMoveStringLiteralDfa2_0(active0, 67108864L);
         case 'r':
            return this.jjMoveStringLiteralDfa2_0(active0, 33554432L);
         case '|':
            if ((active0 & 17179869184L) != 0L) {
               return this.jjStopAtPos(1, 34);
            }
      }

      return this.jjStartNfa_0(0, active0);
   }

   private final int jjMoveStringLiteralDfa2_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_0(0, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(1, active0);
            return 2;
         }

         switch (this.curChar) {
            case 'l':
               return this.jjMoveStringLiteralDfa3_0(active0, 67108864L);
            case 'u':
               return this.jjMoveStringLiteralDfa3_0(active0, 33554432L);
            default:
               return this.jjStartNfa_0(1, active0);
         }
      }
   }

   private final int jjMoveStringLiteralDfa3_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_0(1, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(2, active0);
            return 3;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 33554432L) != 0L) {
                  return this.jjStartNfaWithStates_0(3, 25, 33);
               }
            default:
               return this.jjStartNfa_0(2, active0);
            case 's':
               return this.jjMoveStringLiteralDfa4_0(active0, 67108864L);
         }
      }
   }

   private final int jjMoveStringLiteralDfa4_0(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_0(2, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_0(3, active0);
            return 4;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 67108864L) != 0L) {
                  return this.jjStartNfaWithStates_0(4, 26, 33);
               }
            default:
               return this.jjStartNfa_0(3, active0);
         }
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
      this.jjnewStateCnt = 42;
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
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(31);
                     } else if ((9216L & l) != 0L) {
                        if (kind > 27) {
                           kind = 27;
                        }
                     } else if ((4294967808L & l) != 0L) {
                        if (kind > 23) {
                           kind = 23;
                        }

                        this.jjCheckNAdd(9);
                     } else if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(39, 40);
                     } else if (this.curChar == '-') {
                        this.jjCheckNAdd(31);
                     } else if (this.curChar == '\'') {
                        this.jjCheckNAddStates(0, 2);
                     } else if (this.curChar == '"') {
                        this.jjCheckNAddStates(3, 5);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     } else if (this.curChar == ')') {
                        if (kind > 6) {
                           kind = 6;
                        }

                        this.jjCheckNAddStates(6, 8);
                     }

                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 28;
                     }
                     break;
                  case 1:
                     if ((4294967808L & l) != 0L) {
                        this.jjCheckNAddStates(6, 8);
                     }
                     break;
                  case 2:
                     if ((9216L & l) != 0L && kind > 6) {
                        kind = 6;
                     }
                     break;
                  case 3:
                     if (this.curChar == '\n' && kind > 6) {
                        kind = 6;
                     }
                     break;
                  case 4:
                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 3;
                     }
                     break;
                  case 5:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 6;
                     }
                     break;
                  case 6:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 7:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 5;
                     }
                     break;
                  case 8:
                     if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     }
                     break;
                  case 9:
                     if ((4294967808L & l) != 0L) {
                        if (kind > 23) {
                           kind = 23;
                        }

                        this.jjCheckNAdd(9);
                     }
                     break;
                  case 10:
                     if (this.curChar == '"') {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 11:
                     if ((-17179878401L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 12:
                     if (this.curChar == '"' && kind > 24) {
                        kind = 24;
                     }
                  case 13:
                  case 23:
                  case 32:
                  case 34:
                  case 35:
                  case 37:
                  case 39:
                  default:
                     break;
                  case 14:
                     if ((566935683072L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 15:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAddStates(9, 12);
                     }
                     break;
                  case 16:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 17:
                     if ((4222124650659840L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 18;
                     }
                     break;
                  case 18:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAdd(16);
                     }
                     break;
                  case 19:
                     if (this.curChar == ' ') {
                        this.jjAddStates(13, 14);
                     }
                     break;
                  case 20:
                     if (this.curChar == '\n') {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 21:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 22:
                     if ((-549755823105L & l) != 0L) {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 24:
                     if (this.curChar == ' ') {
                        this.jjAddStates(15, 16);
                     }
                     break;
                  case 25:
                     if (this.curChar == '\n') {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 26:
                     if (this.curChar == '\'' && kind > 24) {
                        kind = 24;
                     }
                     break;
                  case 27:
                     if ((9216L & l) != 0L && kind > 27) {
                        kind = 27;
                     }
                     break;
                  case 28:
                     if (this.curChar == '\n' && kind > 27) {
                        kind = 27;
                     }
                     break;
                  case 29:
                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 28;
                     }
                     break;
                  case 30:
                     if (this.curChar == '-') {
                        this.jjCheckNAdd(31);
                     }
                     break;
                  case 31:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(31);
                     }
                     break;
                  case 33:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 33;
                     }
                     break;
                  case 36:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 38:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(39, 40);
                     }
                     break;
                  case 40:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 41:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(39, 40);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(33);
                     } else if (this.curChar == '\\') {
                        this.jjCheckNAddStates(17, 20);
                     }
                  case 1:
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                  case 12:
                  case 15:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 24:
                  case 25:
                  case 26:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 36:
                  case 38:
                  default:
                     break;
                  case 6:
                     if (kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 11:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 13:
                     if (this.curChar == '\\') {
                        this.jjAddStates(21, 25);
                     }
                     break;
                  case 14:
                     if ((5700160604602368L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 22:
                     this.jjAddStates(0, 2);
                     break;
                  case 23:
                     if (this.curChar == '\\') {
                        this.jjAddStates(15, 16);
                     }
                     break;
                  case 32:
                  case 33:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(33);
                     }
                     break;
                  case 34:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(17, 20);
                     }
                     break;
                  case 35:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(35, 36);
                     }
                     break;
                  case 37:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(37, 38);
                     }
                     break;
                  case 39:
                     if (this.curChar == '\\') {
                        this.jjAddStates(26, 27);
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
                  case 6:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 11:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(3, 5);
                     }
                     break;
                  case 22:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(0, 2);
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
         if ((i = this.jjnewStateCnt) == (startsAt = 42 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_6(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 53248L) != 0L) {
               return 2;
            }

            return -1;
         default:
            return -1;
      }
   }

   private final int jjStartNfa_6(int pos, long active0) {
      return this.jjMoveNfa_6(this.jjStopStringLiteralDfa_6(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_6(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_6(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_6() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_6(20480L);
         case '*':
            return this.jjMoveStringLiteralDfa1_6(2097152L);
         default:
            return this.jjMoveNfa_6(3, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_6(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_6(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }

            if ((active0 & 2097152L) != 0L) {
               return this.jjStopAtPos(1, 21);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_6(1, 14, 0);
            }
      }

      return this.jjStartNfa_6(0, active0);
   }

   private final int jjMoveNfa_6(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 12;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(9, 10);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
                  case 4:
                  case 5:
                  case 7:
                  case 9:
                  default:
                     break;
                  case 6:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 8:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(9, 10);
                     }
                     break;
                  case 10:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 11:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(9, 10);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 4:
                  case 6:
                  case 8:
                  default:
                     break;
                  case 3:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(28, 31);
                     }
                     break;
                  case 5:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(5, 6);
                     }
                     break;
                  case 7:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(7, 8);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\\') {
                        this.jjAddStates(32, 33);
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 12 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_4(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 53248L) != 0L) {
               return 2;
            } else if ((active0 & 35184372088832L) != 0L) {
               this.jjmatchedKind = 52;
               return 22;
            } else {
               if ((active0 & 158329674399744L) != 0L) {
                  this.jjmatchedKind = 52;
                  return 7;
               }

               return -1;
            }
         case 1:
            if ((active0 & 16384L) != 0L) {
               return 0;
            } else if ((active0 & 35184372088832L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 1;
               return 28;
            } else if ((active0 & 140737488355328L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 1;
               return 7;
            } else {
               if ((active0 & 17592186044416L) != 0L) {
                  return 7;
               }

               return -1;
            }
         case 2:
            if ((active0 & 35184372088832L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 2;
               return 23;
            } else {
               if ((active0 & 140737488355328L) != 0L) {
                  this.jjmatchedKind = 52;
                  this.jjmatchedPos = 2;
                  return 7;
               }

               return -1;
            }
         case 3:
            if ((active0 & 140737488355328L) != 0L) {
               return 7;
            } else {
               if ((active0 & 35184372088832L) != 0L) {
                  this.jjmatchedKind = 46;
                  this.jjmatchedPos = 3;
                  return 30;
               }

               return -1;
            }
         case 4:
            if ((active0 & 35184372088832L) != 0L) {
               this.jjmatchedKind = 52;
               this.jjmatchedPos = 4;
               return 7;
            }

            return -1;
         default:
            return -1;
      }
   }

   private final int jjStartNfa_4(int pos, long active0) {
      return this.jjMoveNfa_4(this.jjStopStringLiteralDfa_4(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_4(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_4(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_4() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_4(20480L);
         case 'e':
            return this.jjMoveStringLiteralDfa1_4(35184372088832L);
         case 'i':
            return this.jjMoveStringLiteralDfa1_4(17592186044416L);
         case 's':
            return this.jjMoveStringLiteralDfa1_4(140737488355328L);
         default:
            return this.jjMoveNfa_4(3, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_4(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_4(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_4(1, 14, 0);
            }
            break;
         case 'f':
            if ((active0 & 17592186044416L) != 0L) {
               return this.jjStartNfaWithStates_4(1, 44, 7);
            }
            break;
         case 'l':
            return this.jjMoveStringLiteralDfa2_4(active0, 35184372088832L);
         case 't':
            return this.jjMoveStringLiteralDfa2_4(active0, 140737488355328L);
      }

      return this.jjStartNfa_4(0, active0);
   }

   private final int jjMoveStringLiteralDfa2_4(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_4(0, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_4(1, active0);
            return 2;
         }

         switch (this.curChar) {
            case 'o':
               return this.jjMoveStringLiteralDfa3_4(active0, 140737488355328L);
            case 's':
               return this.jjMoveStringLiteralDfa3_4(active0, 35184372088832L);
            default:
               return this.jjStartNfa_4(1, active0);
         }
      }
   }

   private final int jjMoveStringLiteralDfa3_4(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_4(1, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_4(2, active0);
            return 3;
         }

         switch (this.curChar) {
            case 'e':
               return this.jjMoveStringLiteralDfa4_4(active0, 35184372088832L);
            case 'p':
               if ((active0 & 140737488355328L) != 0L) {
                  return this.jjStartNfaWithStates_4(3, 47, 7);
               }
            default:
               return this.jjStartNfa_4(2, active0);
         }
      }
   }

   private final int jjMoveStringLiteralDfa4_4(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_4(2, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_4(3, active0);
            return 4;
         }

         switch (this.curChar) {
            case 'i':
               return this.jjMoveStringLiteralDfa5_4(active0, 35184372088832L);
            default:
               return this.jjStartNfa_4(3, active0);
         }
      }
   }

   private final int jjMoveStringLiteralDfa5_4(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_4(3, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_4(4, active0);
            return 5;
         }

         switch (this.curChar) {
            case 'f':
               if ((active0 & 35184372088832L) != 0L) {
                  return this.jjStartNfaWithStates_4(5, 45, 7);
               }
            default:
               return this.jjStartNfa_4(4, active0);
         }
      }
   }

   private final int jjMoveNfa_4(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 30;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(5);
                     } else if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(13, 14);
                     } else if (this.curChar == '-') {
                        this.jjCheckNAdd(5);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
                     break;
                  case 4:
                     if (this.curChar == '-') {
                        this.jjCheckNAdd(5);
                     }
                     break;
                  case 5:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(5);
                     }
                  case 6:
                  case 8:
                  case 9:
                  case 11:
                  case 13:
                  case 16:
                  case 17:
                  case 29:
                  default:
                     break;
                  case 7:
                  case 22:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 10:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 12:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(13, 14);
                     }
                     break;
                  case 14:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 15:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(13, 14);
                     }
                     break;
                  case 18:
                     if ((4294967808L & l) != 0L) {
                        this.jjAddStates(37, 39);
                     }
                     break;
                  case 19:
                     if ((9216L & l) != 0L && kind > 43) {
                        kind = 43;
                     }
                     break;
                  case 20:
                     if (this.curChar == '\n' && kind > 43) {
                        kind = 43;
                     }
                     break;
                  case 21:
                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 20;
                     }
                     break;
                  case 23:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 24:
                     if ((4294967808L & l) != 0L) {
                        this.jjCheckNAddStates(34, 36);
                     }
                     break;
                  case 25:
                     if ((9216L & l) != 0L && kind > 46) {
                        kind = 46;
                     }
                     break;
                  case 26:
                     if (this.curChar == '\n' && kind > 46) {
                        kind = 46;
                     }
                     break;
                  case 27:
                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 26;
                     }
                     break;
                  case 28:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 30:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     } else if ((9216L & l) != 0L) {
                        if (kind > 46) {
                           kind = 46;
                        }
                     } else if ((4294967808L & l) != 0L) {
                        this.jjCheckNAddStates(34, 36);
                     }

                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 26;
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 4:
                  case 5:
                  case 10:
                  case 12:
                  case 14:
                  case 15:
                  case 18:
                  case 19:
                  case 20:
                  case 21:
                  case 24:
                  case 25:
                  case 26:
                  case 27:
                  default:
                     break;
                  case 3:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     } else if (this.curChar == '\\') {
                        this.jjCheckNAddStates(40, 43);
                     }

                     if (this.curChar == 'e') {
                        this.jjAddStates(44, 45);
                     }
                     break;
                  case 6:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 7:
                  case 30:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(40, 43);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(9, 10);
                     }
                     break;
                  case 11:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 13:
                     if (this.curChar == '\\') {
                        this.jjAddStates(46, 47);
                     }
                     break;
                  case 16:
                     if (this.curChar == 'e') {
                        this.jjAddStates(44, 45);
                     }
                     break;
                  case 17:
                     if (this.curChar == 'd') {
                        if (kind > 43) {
                           kind = 43;
                        }

                        this.jjAddStates(37, 39);
                     }
                     break;
                  case 22:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }

                     if (this.curChar == 'l') {
                        this.jjstateSet[this.jjnewStateCnt++] = 28;
                     } else if (this.curChar == 'n') {
                        this.jjstateSet[this.jjnewStateCnt++] = 17;
                     }
                     break;
                  case 23:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }

                     if (this.curChar == 'e') {
                        if (kind > 46) {
                           kind = 46;
                        }

                        this.jjAddStates(34, 36);
                     }
                     break;
                  case 28:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 52) {
                           kind = 52;
                        }

                        this.jjCheckNAdd(7);
                     }

                     if (this.curChar == 's') {
                        this.jjstateSet[this.jjnewStateCnt++] = 23;
                     }
                     break;
                  case 29:
                     if (this.curChar == 'l') {
                        this.jjstateSet[this.jjnewStateCnt++] = 28;
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 30 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_3(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 196608L) != 0L) {
               return 9;
            } else {
               if ((active0 & 53248L) != 0L) {
                  return 16;
               }

               return -1;
            }
         default:
            return -1;
      }
   }

   private final int jjStartNfa_3(int pos, long active0) {
      return this.jjMoveNfa_3(this.jjStopStringLiteralDfa_3(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_3(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_3(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_3() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_3(20480L);
         case '\\':
            this.jjmatchedKind = 17;
            return this.jjMoveStringLiteralDfa1_3(65536L);
         default:
            return this.jjMoveNfa_3(13, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_3(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_3(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_3(1, 14, 14);
            }
            break;
         case '\\':
            if ((active0 & 65536L) != 0L) {
               return this.jjStartNfaWithStates_3(1, 16, 25);
            }
      }

      return this.jjStartNfa_3(0, active0);
   }

   private final int jjMoveNfa_3(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 25;
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
                     if ((4294967808L & l) != 0L) {
                        this.jjCheckNAddTwoStates(0, 6);
                     }
                  case 1:
                  case 4:
                  case 5:
                  case 8:
                  case 11:
                  case 17:
                  case 18:
                  case 20:
                  case 22:
                  default:
                     break;
                  case 2:
                     if (this.curChar == ' ') {
                        this.jjAddStates(48, 49);
                     }
                     break;
                  case 3:
                     if (this.curChar == '(' && kind > 9) {
                        kind = 9;
                     }
                     break;
                  case 6:
                     if (this.curChar == '#') {
                        this.jjCheckNAdd(5);
                     }
                     break;
                  case 7:
                     if ((-103079215105L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 9:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(22, 23);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 11;
                     }

                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 10:
                     if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 11;
                     }
                     break;
                  case 12:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 12;
                     }
                     break;
                  case 13:
                     if ((-103079215105L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(7);
                     } else if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(22, 23);
                     } else if (this.curChar == '#') {
                        this.jjCheckNAddTwoStates(5, 16);
                     }

                     if ((4294967808L & l) != 0L) {
                        this.jjCheckNAddTwoStates(0, 6);
                     }
                     break;
                  case 14:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 15;
                     }
                     break;
                  case 15:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 16:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 14;
                     }
                     break;
                  case 19:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 21:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(22, 23);
                     }
                     break;
                  case 23:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 24:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(22, 23);
                     }
                     break;
                  case 25:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(22, 23);
                     }

                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (this.curChar == 't') {
                        this.jjAddStates(48, 49);
                     }
                  case 2:
                  case 3:
                  case 6:
                  case 10:
                  case 14:
                  case 19:
                  case 21:
                  case 23:
                  case 24:
                  default:
                     break;
                  case 4:
                     if (this.curChar == 'e') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 5:
                  case 16:
                     if (this.curChar == 's') {
                        this.jjstateSet[this.jjnewStateCnt++] = 4;
                     }
                     break;
                  case 7:
                     if ((-268435457L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjAddStates(32, 33);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(20, 21);
                     }

                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(18, 19);
                     }

                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 8;
                     }
                     break;
                  case 11:
                  case 12:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 8) {
                           kind = 8;
                        }

                        this.jjCheckNAdd(12);
                     }
                     break;
                  case 13:
                     if ((-268435457L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(7);
                     } else if (this.curChar == '\\') {
                        this.jjCheckNAddStates(50, 53);
                     }

                     if (this.curChar == '\\') {
                        this.jjAddStates(32, 33);
                     }
                     break;
                  case 15:
                     if (kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 17:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(50, 53);
                     }
                     break;
                  case 18:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(18, 19);
                     }
                     break;
                  case 20:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(20, 21);
                     }
                     break;
                  case 22:
                     if (this.curChar == '\\') {
                        this.jjAddStates(54, 55);
                     }
                     break;
                  case 25:
                     if (this.curChar == '\\') {
                        this.jjAddStates(32, 33);
                     }

                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(20, 21);
                     }

                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(18, 19);
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
                  case 7:
                  case 13:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(7);
                     }
                     break;
                  case 15:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 25 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_7(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 53248L) != 0L) {
               return 2;
            }

            return -1;
         default:
            return -1;
      }
   }

   private final int jjStartNfa_7(int pos, long active0) {
      return this.jjMoveNfa_7(this.jjStopStringLiteralDfa_7(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_7(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_7(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_7() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_7(20480L);
         case '*':
            return this.jjMoveStringLiteralDfa1_7(1048576L);
         default:
            return this.jjMoveNfa_7(3, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_7(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_7(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }

            if ((active0 & 1048576L) != 0L) {
               return this.jjStopAtPos(1, 20);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_7(1, 14, 0);
            }
      }

      return this.jjStartNfa_7(0, active0);
   }

   private final int jjMoveNfa_7(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 12;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(9, 10);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
                  case 4:
                  case 5:
                  case 7:
                  case 9:
                  default:
                     break;
                  case 6:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 8:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(9, 10);
                     }
                     break;
                  case 10:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 11:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(9, 10);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 4:
                  case 6:
                  case 8:
                  default:
                     break;
                  case 3:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(28, 31);
                     }
                     break;
                  case 5:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(5, 6);
                     }
                     break;
                  case 7:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(7, 8);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\\') {
                        this.jjAddStates(32, 33);
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 12 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_8(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 53248L) != 0L) {
               return 2;
            }

            return -1;
         default:
            return -1;
      }
   }

   private final int jjStartNfa_8(int pos, long active0) {
      return this.jjMoveNfa_8(this.jjStopStringLiteralDfa_8(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_8(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_8(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_8() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_8(20480L);
         default:
            return this.jjMoveNfa_8(3, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_8(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_8(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_8(1, 14, 0);
            }
      }

      return this.jjStartNfa_8(0, active0);
   }

   private final int jjMoveNfa_8(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 15;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if ((9216L & l) != 0L) {
                        if (kind > 19) {
                           kind = 19;
                        }
                     } else if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(12, 13);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }

                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 5;
                     }
                     break;
                  case 4:
                     if ((9216L & l) != 0L && kind > 19) {
                        kind = 19;
                     }
                     break;
                  case 5:
                     if (this.curChar == '\n' && kind > 19) {
                        kind = 19;
                     }
                     break;
                  case 6:
                     if (this.curChar == '\r') {
                        this.jjstateSet[this.jjnewStateCnt++] = 5;
                     }
                  case 7:
                  case 8:
                  case 10:
                  case 12:
                  default:
                     break;
                  case 9:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 11:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(12, 13);
                     }
                     break;
                  case 13:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 14:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(12, 13);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 4:
                  case 5:
                  case 6:
                  case 7:
                  case 9:
                  case 11:
                  default:
                     break;
                  case 3:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(56, 59);
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(8, 9);
                     }
                     break;
                  case 10:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(10, 11);
                     }
                     break;
                  case 12:
                     if (this.curChar == '\\') {
                        this.jjAddStates(60, 61);
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 15 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_5(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 53248L) != 0L) {
               return 2;
            } else {
               if ((active0 & 100663296L) != 0L) {
                  this.jjmatchedKind = 56;
                  return 5;
               }

               return -1;
            }
         case 1:
            if ((active0 & 16384L) != 0L) {
               return 0;
            } else {
               if ((active0 & 100663296L) != 0L) {
                  this.jjmatchedKind = 56;
                  this.jjmatchedPos = 1;
                  return 5;
               }

               return -1;
            }
         case 2:
            if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 56;
               this.jjmatchedPos = 2;
               return 5;
            }

            return -1;
         case 3:
            if ((active0 & 67108864L) != 0L) {
               this.jjmatchedKind = 56;
               this.jjmatchedPos = 3;
               return 5;
            } else {
               if ((active0 & 33554432L) != 0L) {
                  return 5;
               }

               return -1;
            }
         default:
            return -1;
      }
   }

   private final int jjStartNfa_5(int pos, long active0) {
      return this.jjMoveNfa_5(this.jjStopStringLiteralDfa_5(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_5(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_5(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_5() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_5(20480L);
         case 'f':
            return this.jjMoveStringLiteralDfa1_5(67108864L);
         case 't':
            return this.jjMoveStringLiteralDfa1_5(33554432L);
         case '{':
            return this.jjStopAtPos(0, 58);
         case '}':
            return this.jjStopAtPos(0, 59);
         default:
            return this.jjMoveNfa_5(3, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_5(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_5(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_5(1, 14, 0);
            }
            break;
         case 'a':
            return this.jjMoveStringLiteralDfa2_5(active0, 67108864L);
         case 'r':
            return this.jjMoveStringLiteralDfa2_5(active0, 33554432L);
      }

      return this.jjStartNfa_5(0, active0);
   }

   private final int jjMoveStringLiteralDfa2_5(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_5(0, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_5(1, active0);
            return 2;
         }

         switch (this.curChar) {
            case 'l':
               return this.jjMoveStringLiteralDfa3_5(active0, 67108864L);
            case 'u':
               return this.jjMoveStringLiteralDfa3_5(active0, 33554432L);
            default:
               return this.jjStartNfa_5(1, active0);
         }
      }
   }

   private final int jjMoveStringLiteralDfa3_5(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_5(1, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_5(2, active0);
            return 3;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 33554432L) != 0L) {
                  return this.jjStartNfaWithStates_5(3, 25, 5);
               }
            default:
               return this.jjStartNfa_5(2, active0);
            case 's':
               return this.jjMoveStringLiteralDfa4_5(active0, 67108864L);
         }
      }
   }

   private final int jjMoveStringLiteralDfa4_5(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_5(2, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_5(3, active0);
            return 4;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 67108864L) != 0L) {
                  return this.jjStartNfaWithStates_5(4, 26, 5);
               }
            default:
               return this.jjStartNfa_5(3, active0);
         }
      }
   }

   private final int jjMoveNfa_5(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 16;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(13, 14);
                     } else if (this.curChar == '.') {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
                  case 4:
                  case 7:
                  case 8:
                  case 9:
                  case 11:
                  case 13:
                  default:
                     break;
                  case 5:
                     if ((287984085547089920L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 5;
                     }
                     break;
                  case 6:
                     if (this.curChar == '.') {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     }
                     break;
                  case 10:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 12:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(13, 14);
                     }
                     break;
                  case 14:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 15:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(13, 14);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 6:
                  case 10:
                  case 12:
                  default:
                     break;
                  case 3:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjCheckNAdd(5);
                     } else if (this.curChar == '\\') {
                        this.jjCheckNAddStates(40, 43);
                     }
                     break;
                  case 4:
                  case 5:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjCheckNAdd(5);
                     }
                     break;
                  case 7:
                     if ((576460743847706622L & l) != 0L && kind > 57) {
                        kind = 57;
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(40, 43);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(9, 10);
                     }
                     break;
                  case 11:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 13:
                     if (this.curChar == '\\') {
                        this.jjAddStates(46, 47);
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 16 - (this.jjnewStateCnt = startsAt))) {
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
            if ((active0 & 53248L) != 0L) {
               return 2;
            } else if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 56;
               return 25;
            } else {
               if ((active0 & 16L) != 0L) {
                  return 27;
               }

               return -1;
            }
         case 1:
            if ((active0 & 16384L) != 0L) {
               return 0;
            } else {
               if ((active0 & 100663296L) != 0L) {
                  this.jjmatchedKind = 56;
                  this.jjmatchedPos = 1;
                  return 25;
               }

               return -1;
            }
         case 2:
            if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 56;
               this.jjmatchedPos = 2;
               return 25;
            }

            return -1;
         case 3:
            if ((active0 & 67108864L) != 0L) {
               this.jjmatchedKind = 56;
               this.jjmatchedPos = 3;
               return 25;
            } else {
               if ((active0 & 33554432L) != 0L) {
                  return 25;
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
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_1(20480L);
         case ')':
            return this.jjStopAtPos(0, 7);
         case ',':
            return this.jjStopAtPos(0, 3);
         case '.':
            return this.jjMoveStringLiteralDfa1_1(16L);
         case '[':
            return this.jjStopAtPos(0, 1);
         case ']':
            return this.jjStopAtPos(0, 2);
         case 'f':
            return this.jjMoveStringLiteralDfa1_1(67108864L);
         case 't':
            return this.jjMoveStringLiteralDfa1_1(33554432L);
         case '{':
            return this.jjStopAtPos(0, 58);
         case '}':
            return this.jjStopAtPos(0, 59);
         default:
            return this.jjMoveNfa_1(3, 0);
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
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_1(1, 14, 0);
            }
            break;
         case '.':
            if ((active0 & 16L) != 0L) {
               return this.jjStopAtPos(1, 4);
            }
            break;
         case 'a':
            return this.jjMoveStringLiteralDfa2_1(active0, 67108864L);
         case 'r':
            return this.jjMoveStringLiteralDfa2_1(active0, 33554432L);
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
            case 'l':
               return this.jjMoveStringLiteralDfa3_1(active0, 67108864L);
            case 'u':
               return this.jjMoveStringLiteralDfa3_1(active0, 33554432L);
            default:
               return this.jjStartNfa_1(1, active0);
         }
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
               if ((active0 & 33554432L) != 0L) {
                  return this.jjStartNfaWithStates_1(3, 25, 25);
               }
            default:
               return this.jjStartNfa_1(2, active0);
            case 's':
               return this.jjMoveStringLiteralDfa4_1(active0, 67108864L);
         }
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
               if ((active0 & 67108864L) != 0L) {
                  return this.jjStartNfaWithStates_1(4, 26, 25);
               }
            default:
               return this.jjStartNfa_1(3, active0);
         }
      }
   }

   private final int jjMoveNfa_1(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 36;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(23);
                     } else if ((4294967808L & l) != 0L) {
                        if (kind > 23) {
                           kind = 23;
                        }

                        this.jjCheckNAdd(4);
                     } else if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(33, 34);
                     } else if (this.curChar == '.') {
                        this.jjstateSet[this.jjnewStateCnt++] = 27;
                     } else if (this.curChar == '-') {
                        this.jjCheckNAdd(23);
                     } else if (this.curChar == '\'') {
                        this.jjCheckNAddStates(62, 64);
                     } else if (this.curChar == '"') {
                        this.jjCheckNAddStates(65, 67);
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
                     break;
                  case 4:
                     if ((4294967808L & l) != 0L) {
                        if (kind > 23) {
                           kind = 23;
                        }

                        this.jjCheckNAdd(4);
                     }
                     break;
                  case 5:
                     if (this.curChar == '"') {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 6:
                     if ((-17179878401L & l) != 0L) {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 7:
                     if (this.curChar == '"' && kind > 24) {
                        kind = 24;
                     }
                  case 8:
                  case 18:
                  case 24:
                  case 27:
                  case 28:
                  case 29:
                  case 31:
                  case 33:
                  default:
                     break;
                  case 9:
                     if ((566935683072L & l) != 0L) {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 10:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAddStates(68, 71);
                     }
                     break;
                  case 11:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 12:
                     if ((4222124650659840L & l) != 0L) {
                        this.jjstateSet[this.jjnewStateCnt++] = 13;
                     }
                     break;
                  case 13:
                     if ((71776119061217280L & l) != 0L) {
                        this.jjCheckNAdd(11);
                     }
                     break;
                  case 14:
                     if (this.curChar == ' ') {
                        this.jjAddStates(72, 73);
                     }
                     break;
                  case 15:
                     if (this.curChar == '\n') {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 16:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(62, 64);
                     }
                     break;
                  case 17:
                     if ((-549755823105L & l) != 0L) {
                        this.jjCheckNAddStates(62, 64);
                     }
                     break;
                  case 19:
                     if (this.curChar == ' ') {
                        this.jjAddStates(13, 14);
                     }
                     break;
                  case 20:
                     if (this.curChar == '\n') {
                        this.jjCheckNAddStates(62, 64);
                     }
                     break;
                  case 21:
                     if (this.curChar == '\'' && kind > 24) {
                        kind = 24;
                     }
                     break;
                  case 22:
                     if (this.curChar == '-') {
                        this.jjCheckNAdd(23);
                     }
                     break;
                  case 23:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 49) {
                           kind = 49;
                        }

                        this.jjCheckNAdd(23);
                     }
                     break;
                  case 25:
                     if ((287984085547089920L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 25;
                     }
                     break;
                  case 26:
                     if (this.curChar == '.') {
                        this.jjstateSet[this.jjnewStateCnt++] = 27;
                     }
                     break;
                  case 30:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 32:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(33, 34);
                     }
                     break;
                  case 34:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 35:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(33, 34);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 4:
                  case 5:
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 14:
                  case 15:
                  case 16:
                  case 19:
                  case 20:
                  case 21:
                  case 22:
                  case 23:
                  case 26:
                  case 30:
                  case 32:
                  default:
                     break;
                  case 3:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjCheckNAdd(25);
                     } else if (this.curChar == '\\') {
                        this.jjCheckNAddStates(74, 77);
                     }
                     break;
                  case 6:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjAddStates(78, 82);
                     }
                     break;
                  case 9:
                     if ((5700160604602368L & l) != 0L) {
                        this.jjCheckNAddStates(65, 67);
                     }
                     break;
                  case 17:
                     this.jjAddStates(62, 64);
                     break;
                  case 18:
                     if (this.curChar == '\\') {
                        this.jjAddStates(13, 14);
                     }
                     break;
                  case 24:
                  case 25:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjCheckNAdd(25);
                     }
                     break;
                  case 27:
                     if ((576460743847706622L & l) != 0L && kind > 57) {
                        kind = 57;
                     }
                     break;
                  case 28:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(74, 77);
                     }
                     break;
                  case 29:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(29, 30);
                     }
                     break;
                  case 31:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(31, 32);
                     }
                     break;
                  case 33:
                     if (this.curChar == '\\') {
                        this.jjAddStates(83, 84);
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 6:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(65, 67);
                     }
                     break;
                  case 17:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(62, 64);
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
         if ((i = this.jjnewStateCnt) == (startsAt = 36 - (this.jjnewStateCnt = startsAt))) {
            return curPos;
         }

         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var15) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_2(int pos, long active0) {
      switch (pos) {
         case 0:
            if ((active0 & 53248L) != 0L) {
               return 2;
            } else {
               if ((active0 & 100663296L) != 0L) {
                  this.jjmatchedKind = 56;
                  return 5;
               }

               return -1;
            }
         case 1:
            if ((active0 & 16384L) != 0L) {
               return 0;
            } else {
               if ((active0 & 100663296L) != 0L) {
                  this.jjmatchedKind = 56;
                  this.jjmatchedPos = 1;
                  return 5;
               }

               return -1;
            }
         case 2:
            if ((active0 & 100663296L) != 0L) {
               this.jjmatchedKind = 56;
               this.jjmatchedPos = 2;
               return 5;
            }

            return -1;
         case 3:
            if ((active0 & 67108864L) != 0L) {
               this.jjmatchedKind = 56;
               this.jjmatchedPos = 3;
               return 5;
            } else {
               if ((active0 & 33554432L) != 0L) {
                  return 5;
               }

               return -1;
            }
         default:
            return -1;
      }
   }

   private final int jjStartNfa_2(int pos, long active0) {
      return this.jjMoveNfa_2(this.jjStopStringLiteralDfa_2(pos, active0), pos + 1);
   }

   private final int jjStartNfaWithStates_2(int pos, int kind, int state) {
      this.jjmatchedKind = kind;
      this.jjmatchedPos = pos;

      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var5) {
         return pos + 1;
      }

      return this.jjMoveNfa_2(state, pos + 1);
   }

   private final int jjMoveStringLiteralDfa0_2() {
      switch (this.curChar) {
         case '#':
            this.jjmatchedKind = 15;
            return this.jjMoveStringLiteralDfa1_2(20480L);
         case '(':
            return this.jjStopAtPos(0, 5);
         case 'f':
            return this.jjMoveStringLiteralDfa1_2(67108864L);
         case 't':
            return this.jjMoveStringLiteralDfa1_2(33554432L);
         case '{':
            return this.jjStopAtPos(0, 58);
         case '}':
            return this.jjStopAtPos(0, 59);
         default:
            return this.jjMoveNfa_2(3, 0);
      }
   }

   private final int jjMoveStringLiteralDfa1_2(long active0) {
      try {
         this.curChar = this.input_stream.readChar();
      } catch (IOException var4) {
         this.jjStopStringLiteralDfa_2(0, active0);
         return 1;
      }

      switch (this.curChar) {
         case '#':
            if ((active0 & 4096L) != 0L) {
               return this.jjStopAtPos(1, 12);
            }
            break;
         case '*':
            if ((active0 & 16384L) != 0L) {
               return this.jjStartNfaWithStates_2(1, 14, 0);
            }
            break;
         case 'a':
            return this.jjMoveStringLiteralDfa2_2(active0, 67108864L);
         case 'r':
            return this.jjMoveStringLiteralDfa2_2(active0, 33554432L);
      }

      return this.jjStartNfa_2(0, active0);
   }

   private final int jjMoveStringLiteralDfa2_2(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_2(0, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_2(1, active0);
            return 2;
         }

         switch (this.curChar) {
            case 'l':
               return this.jjMoveStringLiteralDfa3_2(active0, 67108864L);
            case 'u':
               return this.jjMoveStringLiteralDfa3_2(active0, 33554432L);
            default:
               return this.jjStartNfa_2(1, active0);
         }
      }
   }

   private final int jjMoveStringLiteralDfa3_2(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_2(1, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_2(2, active0);
            return 3;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 33554432L) != 0L) {
                  return this.jjStartNfaWithStates_2(3, 25, 5);
               }
            default:
               return this.jjStartNfa_2(2, active0);
            case 's':
               return this.jjMoveStringLiteralDfa4_2(active0, 67108864L);
         }
      }
   }

   private final int jjMoveStringLiteralDfa4_2(long old0, long active0) {
      if ((active0 &= old0) == 0L) {
         return this.jjStartNfa_2(2, old0);
      } else {
         try {
            this.curChar = this.input_stream.readChar();
         } catch (IOException var6) {
            this.jjStopStringLiteralDfa_2(3, active0);
            return 4;
         }

         switch (this.curChar) {
            case 'e':
               if ((active0 & 67108864L) != 0L) {
                  return this.jjStartNfaWithStates_2(4, 26, 5);
               }
            default:
               return this.jjStartNfa_2(3, active0);
         }
      }
   }

   private final int jjMoveNfa_2(int startState, int curPos) {
      int startsAt = 0;
      this.jjnewStateCnt = 16;
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
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if ((-34359738369L & l) != 0L && kind > 13) {
                        kind = 13;
                     }
                     break;
                  case 2:
                     if (this.curChar == '*') {
                        this.jjstateSet[this.jjnewStateCnt++] = 0;
                     }
                     break;
                  case 3:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(13, 14);
                     } else if (this.curChar == '.') {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     } else if (this.curChar == '#') {
                        this.jjstateSet[this.jjnewStateCnt++] = 2;
                     }
                  case 4:
                  case 7:
                  case 8:
                  case 9:
                  case 11:
                  case 13:
                  default:
                     break;
                  case 5:
                     if ((287984085547089920L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 5;
                     }
                     break;
                  case 6:
                     if (this.curChar == '.') {
                        this.jjstateSet[this.jjnewStateCnt++] = 7;
                     }
                     break;
                  case 10:
                     if (this.curChar == '$' && kind > 10) {
                        kind = 10;
                     }
                     break;
                  case 12:
                     if (this.curChar == '$') {
                        this.jjCheckNAddTwoStates(13, 14);
                     }
                     break;
                  case 14:
                     if (this.curChar == '!' && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 15:
                     if (this.curChar == '$') {
                        if (kind > 10) {
                           kind = 10;
                        }

                        this.jjCheckNAddTwoStates(13, 14);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 1:
                     if (kind > 13) {
                        kind = 13;
                     }
                  case 2:
                  case 6:
                  case 10:
                  case 12:
                  default:
                     break;
                  case 3:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjCheckNAdd(5);
                     } else if (this.curChar == '\\') {
                        this.jjCheckNAddStates(40, 43);
                     }
                     break;
                  case 4:
                  case 5:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 56) {
                           kind = 56;
                        }

                        this.jjCheckNAdd(5);
                     }
                     break;
                  case 7:
                     if ((576460743847706622L & l) != 0L && kind > 57) {
                        kind = 57;
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(40, 43);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(9, 10);
                     }
                     break;
                  case 11:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 13:
                     if (this.curChar == '\\') {
                        this.jjAddStates(46, 47);
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
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 13) {
                        kind = 13;
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
         if ((i = this.jjnewStateCnt) == (startsAt = 16 - (this.jjnewStateCnt = startsAt))) {
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

   public ParserTokenManager(CharStream stream) {
      this.fileDepth = 0;
      this.lparen = 0;
      this.rparen = 0;
      this.stateStack = new Stack();
      this.debugPrint = false;
      this.debugStream = System.out;
      this.jjrounds = new int[42];
      this.jjstateSet = new int[84];
      this.curLexState = 3;
      this.defaultLexState = 3;
      this.input_stream = stream;
   }

   public ParserTokenManager(CharStream stream, int lexState) {
      this(stream);
      this.SwitchTo(lexState);
   }

   public void ReInit(CharStream stream) {
      this.jjmatchedPos = this.jjnewStateCnt = 0;
      this.curLexState = this.defaultLexState;
      this.input_stream = stream;
      this.ReInitRounds();
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;

      for(int i = 42; i-- > 0; this.jjrounds[i] = Integer.MIN_VALUE) {
      }

   }

   public void ReInit(CharStream stream, int lexState) {
      this.ReInit(stream);
      this.SwitchTo(lexState);
   }

   public void SwitchTo(int lexState) {
      if (lexState < 9 && lexState >= 0) {
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

      label131:
      while(true) {
         Token matchedToken;
         try {
            this.curChar = this.input_stream.BeginToken();
         } catch (IOException var10) {
            this.jjmatchedKind = 0;
            matchedToken = this.jjFillToken();
            matchedToken.specialToken = specialToken;
            return matchedToken;
         }

         this.image = null;
         this.jjimageLen = 0;

         while(true) {
            switch (this.curLexState) {
               case 0:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_0();
                  break;
               case 1:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_1();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 60) {
                     this.jjmatchedKind = 60;
                  }
                  break;
               case 2:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_2();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 60) {
                     this.jjmatchedKind = 60;
                  }
                  break;
               case 3:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_3();
                  break;
               case 4:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_4();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 61) {
                     this.jjmatchedKind = 61;
                  }
                  break;
               case 5:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_5();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 60) {
                     this.jjmatchedKind = 60;
                  }
                  break;
               case 6:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_6();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 22) {
                     this.jjmatchedKind = 22;
                  }
                  break;
               case 7:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_7();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 22) {
                     this.jjmatchedKind = 22;
                  }
                  break;
               case 8:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_8();
                  if (this.jjmatchedPos == 0 && this.jjmatchedKind > 22) {
                     this.jjmatchedKind = 22;
                  }
            }

            if (this.jjmatchedKind == Integer.MAX_VALUE) {
               break label131;
            }

            if (this.jjmatchedPos + 1 < curPos) {
               this.input_stream.backup(curPos - this.jjmatchedPos - 1);
            }

            if ((jjtoToken[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
               matchedToken = this.jjFillToken();
               matchedToken.specialToken = specialToken;
               this.TokenLexicalActions(matchedToken);
               if (jjnewLexState[this.jjmatchedKind] != -1) {
                  this.curLexState = jjnewLexState[this.jjmatchedKind];
               }

               return matchedToken;
            }

            if ((jjtoSkip[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
               if ((jjtoSpecial[this.jjmatchedKind >> 6] & 1L << (this.jjmatchedKind & 63)) != 0L) {
                  matchedToken = this.jjFillToken();
                  if (specialToken == null) {
                     specialToken = matchedToken;
                  } else {
                     matchedToken.specialToken = specialToken;
                     specialToken = specialToken.next = matchedToken;
                  }

                  this.SkipLexicalActions(matchedToken);
               } else {
                  this.SkipLexicalActions((Token)null);
               }

               if (jjnewLexState[this.jjmatchedKind] != -1) {
                  this.curLexState = jjnewLexState[this.jjmatchedKind];
               }
               break;
            }

            this.MoreLexicalActions();
            if (jjnewLexState[this.jjmatchedKind] != -1) {
               this.curLexState = jjnewLexState[this.jjmatchedKind];
            }

            curPos = 0;
            this.jjmatchedKind = Integer.MAX_VALUE;

            try {
               this.curChar = this.input_stream.readChar();
            } catch (IOException var12) {
               break label131;
            }
         }
      }

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

   final void SkipLexicalActions(Token matchedToken) {
      switch (this.jjmatchedKind) {
         case 60:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.input_stream.backup(1);
            this.inReference = false;
            if (this.debugPrint) {
               System.out.print("REF_TERM :");
            }

            this.stateStackPop();
            break;
         case 61:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            if (this.debugPrint) {
               System.out.print("DIRECTIVE_TERM :");
            }

            this.input_stream.backup(1);
            this.inDirective = false;
            this.stateStackPop();
      }

   }

   final void MoreLexicalActions() {
      this.jjimageLen += this.lengthOfMatch = this.jjmatchedPos + 1;
      switch (this.jjmatchedKind) {
         case 10:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
            }

            this.jjimageLen = 0;
            if (!this.inComment) {
               if (this.curLexState == 5) {
                  this.inReference = false;
                  this.stateStackPop();
               }

               this.inReference = true;
               if (this.debugPrint) {
                  System.out.print("$  : going to 5");
               }

               this.stateStackPush();
               this.SwitchTo(5);
            }
            break;
         case 11:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
            }

            this.jjimageLen = 0;
            if (!this.inComment) {
               if (this.curLexState == 5) {
                  this.inReference = false;
                  this.stateStackPop();
               }

               this.inReference = true;
               if (this.debugPrint) {
                  System.out.print("$!  : going to 5");
               }

               this.stateStackPush();
               this.SwitchTo(5);
            }
            break;
         case 12:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
            }

            this.jjimageLen = 0;
            if (!this.inComment) {
               if (this.curLexState == 5) {
                  this.inReference = false;
                  this.stateStackPop();
               }

               this.inComment = true;
               this.stateStackPush();
               this.SwitchTo(8);
            }
            break;
         case 13:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
            }

            this.jjimageLen = 0;
            this.input_stream.backup(1);
            this.inComment = true;
            this.stateStackPush();
            this.SwitchTo(7);
            break;
         case 14:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
            }

            this.jjimageLen = 0;
            this.inComment = true;
            this.stateStackPush();
            this.SwitchTo(6);
            break;
         case 15:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
            }

            this.jjimageLen = 0;
            if (!this.inComment) {
               if (this.curLexState == 5 || this.curLexState == 2) {
                  this.inReference = false;
                  this.stateStackPop();
               }

               this.inDirective = true;
               if (this.debugPrint) {
                  System.out.print("# :  going to 0");
               }

               this.stateStackPush();
               this.SwitchTo(4);
            }
      }

   }

   final void TokenLexicalActions(Token matchedToken) {
      switch (this.jjmatchedKind) {
         case 5:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            if (!this.inComment) {
               ++this.lparen;
            }

            if (this.curLexState == 2) {
               this.SwitchTo(1);
            }
            break;
         case 6:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.RPARENHandler();
            break;
         case 7:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.SwitchTo(5);
         case 8:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 22:
         case 23:
         case 25:
         case 26:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 48:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 58:
         default:
            break;
         case 9:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            if (!this.inComment) {
               this.inDirective = true;
               if (this.debugPrint) {
                  System.out.print("#set :  going to 0");
               }

               this.stateStackPush();
               this.inSet = true;
               this.SwitchTo(0);
            }

            if (!this.inComment) {
               ++this.lparen;
               if (this.curLexState == 2) {
                  this.SwitchTo(1);
               }
            }
            break;
         case 19:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.inComment = false;
            this.stateStackPop();
            break;
         case 20:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.inComment = false;
            this.stateStackPop();
            break;
         case 21:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.inComment = false;
            this.stateStackPop();
            break;
         case 24:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            if (this.curLexState == 0 && !this.inSet && this.lparen == 0) {
               this.stateStackPop();
            }
            break;
         case 27:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            if (this.debugPrint) {
               System.out.println(" NEWLINE :");
            }

            this.stateStackPop();
            if (this.inSet) {
               this.inSet = false;
            }

            if (this.inDirective) {
               this.inDirective = false;
            }
            break;
         case 43:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.inDirective = false;
            this.stateStackPop();
            break;
         case 44:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.SwitchTo(0);
            break;
         case 45:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.SwitchTo(0);
            break;
         case 46:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.inDirective = false;
            this.stateStackPop();
            break;
         case 47:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            matchedToken.kind = 0;
            this.fileDepth = 0;
            break;
         case 49:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            if (this.lparen == 0 && !this.inSet && this.curLexState != 1) {
               this.stateStackPop();
            }
            break;
         case 57:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.input_stream.backup(1);
            matchedToken.image = ".";
            if (this.debugPrint) {
               System.out.print("DOT : switching to 2");
            }

            this.SwitchTo(2);
            break;
         case 59:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
            }

            this.stateStackPop();
      }

   }
}
