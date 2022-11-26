package org.apache.taglibs.standard.extra.spath;

import java.io.IOException;

public class SPathParserTokenManager implements SPathParserConstants {
   static final long[] jjbitVec0 = new long[]{-2L, -1L, -1L, -1L};
   static final long[] jjbitVec2 = new long[]{0L, 0L, -1L, -1L};
   static final long[] jjbitVec3 = new long[]{0L, -16384L, -17590038560769L, 8388607L};
   static final long[] jjbitVec4 = new long[]{0L, 0L, 0L, -36028797027352577L};
   static final long[] jjbitVec5 = new long[]{9219994337134247935L, 9223372036854775294L, -1L, -274156627316187121L};
   static final long[] jjbitVec6 = new long[]{16777215L, -65536L, -576458553280167937L, 3L};
   static final long[] jjbitVec7 = new long[]{0L, 0L, -17179879616L, 4503588160110591L};
   static final long[] jjbitVec8 = new long[]{-8194L, -536936449L, -65533L, 234134404065073567L};
   static final long[] jjbitVec9 = new long[]{-562949953421312L, -8547991553L, 127L, 1979120929931264L};
   static final long[] jjbitVec10 = new long[]{576460743713488896L, -562949953419266L, 9007199254740991999L, 412319973375L};
   static final long[] jjbitVec11 = new long[]{2594073385365405664L, 17163091968L, 271902628478820320L, 844440767823872L};
   static final long[] jjbitVec12 = new long[]{247132830528276448L, 7881300924956672L, 2589004636761075680L, 4294967296L};
   static final long[] jjbitVec13 = new long[]{2579997437506199520L, 15837691904L, 270153412153034720L, 0L};
   static final long[] jjbitVec14 = new long[]{283724577500946400L, 12884901888L, 283724577500946400L, 13958643712L};
   static final long[] jjbitVec15 = new long[]{288228177128316896L, 12884901888L, 0L, 0L};
   static final long[] jjbitVec16 = new long[]{3799912185593854L, 63L, 2309621682768192918L, 31L};
   static final long[] jjbitVec17 = new long[]{0L, 4398046510847L, 0L, 0L};
   static final long[] jjbitVec18 = new long[]{0L, 0L, -4294967296L, 36028797018898495L};
   static final long[] jjbitVec19 = new long[]{5764607523034749677L, 12493387738468353L, -756383734487318528L, 144405459145588743L};
   static final long[] jjbitVec20 = new long[]{-1L, -1L, -4026531841L, 288230376151711743L};
   static final long[] jjbitVec21 = new long[]{-3233808385L, 4611686017001275199L, 6908521828386340863L, 2295745090394464220L};
   static final long[] jjbitVec22 = new long[]{83837761617920L, 0L, 7L, 0L};
   static final long[] jjbitVec23 = new long[]{4389456576640L, -2L, -8587837441L, 576460752303423487L};
   static final long[] jjbitVec24 = new long[]{35184372088800L, 0L, 0L, 0L};
   static final long[] jjbitVec25 = new long[]{-1L, -1L, 274877906943L, 0L};
   static final long[] jjbitVec26 = new long[]{-1L, -1L, 68719476735L, 0L};
   static final long[] jjbitVec27 = new long[]{0L, 0L, 36028797018963968L, -36028797027352577L};
   static final long[] jjbitVec28 = new long[]{16777215L, -65536L, -576458553280167937L, 196611L};
   static final long[] jjbitVec29 = new long[]{-1L, 12884901951L, -17179879488L, 4503588160110591L};
   static final long[] jjbitVec30 = new long[]{-8194L, -536936449L, -65413L, 234134404065073567L};
   static final long[] jjbitVec31 = new long[]{-562949953421312L, -8547991553L, -4899916411759099777L, 1979120929931286L};
   static final long[] jjbitVec32 = new long[]{576460743713488896L, -277081224642561L, 9007199254740991999L, 288017070894841855L};
   static final long[] jjbitVec33 = new long[]{-864691128455135250L, 281268803485695L, -3186861885341720594L, 1125692414638495L};
   static final long[] jjbitVec34 = new long[]{-3211631683292264476L, 9006925953907079L, -869759877059465234L, 281204393786303L};
   static final long[] jjbitVec35 = new long[]{-878767076314341394L, 281215949093263L, -4341532606274353172L, 280925229301191L};
   static final long[] jjbitVec36 = new long[]{-4327961440926441490L, 281212990012895L, -4327961440926441492L, 281214063754719L};
   static final long[] jjbitVec37 = new long[]{-4323457841299070996L, 281212992110031L, 0L, 0L};
   static final long[] jjbitVec38 = new long[]{576320014815068158L, 67076095L, 4323293666156225942L, 67059551L};
   static final long[] jjbitVec39 = new long[]{-4422530440275951616L, -558551906910465L, 215680200883507167L, 0L};
   static final long[] jjbitVec40 = new long[]{0L, 0L, 0L, 9126739968L};
   static final long[] jjbitVec41 = new long[]{17732914942836896L, -2L, -6876561409L, 8646911284551352319L};
   static final int[] jjnextStates = new int[]{6, 7, 9, 1, 2, 4, 11, 12, 16, 17, 11, 12, 14, 15, 16, 17};
   public static final String[] jjstrLiteralImages = new String[]{"", null, null, null, null, null, null, null, null, null, null, null, null, "/", "*", ":", "[", "]", "@", "="};
   public static final String[] lexStateNames = new String[]{"DEFAULT"};
   private ASCII_UCodeESC_CharStream input_stream;
   private final int[] jjrounds;
   private final int[] jjstateSet;
   protected char curChar;
   int curLexState;
   int defaultLexState;
   int jjnewStateCnt;
   int jjround;
   int jjmatchedPos;
   int jjmatchedKind;

   private final int jjStopStringLiteralDfa_0(int pos, long active0) {
      switch (pos) {
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
         case '*':
            return this.jjStopAtPos(0, 14);
         case '/':
            return this.jjStopAtPos(0, 13);
         case ':':
            return this.jjStopAtPos(0, 15);
         case '=':
            return this.jjStopAtPos(0, 19);
         case '@':
            return this.jjStopAtPos(0, 18);
         case '[':
            return this.jjStopAtPos(0, 16);
         case ']':
            return this.jjStopAtPos(0, 17);
         default:
            return this.jjMoveNfa_0(0, 0);
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
      this.jjnewStateCnt = 19;
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
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(0, 2);
                     } else if (this.curChar == '"') {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 1:
                     if ((-17179869185L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                  case 2:
                  case 7:
                  case 10:
                  case 13:
                  default:
                     break;
                  case 3:
                     if (this.curChar == '"') {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 4:
                     if (this.curChar == '"' && kind > 1) {
                        kind = 1;
                     }
                     break;
                  case 5:
                  case 8:
                     if (this.curChar == '\'') {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 6:
                     if ((-549755813889L & l) != 0L) {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 9:
                     if (this.curChar == '\'' && kind > 1) {
                        kind = 1;
                     }
                     break;
                  case 11:
                     if ((288054454291267584L & l) != 0L) {
                        this.jjAddStates(6, 7);
                     }
                     break;
                  case 12:
                     if (this.curChar == ':') {
                        this.jjstateSet[this.jjnewStateCnt++] = 13;
                     }
                     break;
                  case 14:
                     if ((288054454291267584L & l) != 0L) {
                        if (kind > 2) {
                           kind = 2;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 14;
                     }
                     break;
                  case 15:
                     if ((288054454291267584L & l) != 0L) {
                        if (kind > 3) {
                           kind = 3;
                        }

                        this.jjstateSet[this.jjnewStateCnt++] = 15;
                     }
                     break;
                  case 16:
                     if ((288054454291267584L & l) != 0L) {
                        this.jjAddStates(8, 9);
                     }
                     break;
                  case 17:
                     if (this.curChar == ':') {
                        this.jjstateSet[this.jjnewStateCnt++] = 18;
                     }
                     break;
                  case 18:
                     if (this.curChar == '*' && kind > 4) {
                        kind = 4;
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
                        if (kind > 2) {
                           kind = 2;
                        }

                        this.jjCheckNAddStates(10, 15);
                     }
                     break;
                  case 1:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAddStates(3, 5);
                     }
                     break;
                  case 2:
                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 3;
                     }
                     break;
                  case 3:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(3, 5);
                     }
                  case 4:
                  case 5:
                  case 9:
                  case 10:
                  case 12:
                  default:
                     break;
                  case 6:
                     if ((-268435457L & l) != 0L) {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 7:
                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 8;
                     }
                     break;
                  case 8:
                     if (this.curChar == '\\') {
                        this.jjCheckNAddStates(0, 2);
                     }
                     break;
                  case 11:
                     if ((576460745995190270L & l) != 0L) {
                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 13:
                  case 14:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 2) {
                           kind = 2;
                        }

                        this.jjCheckNAdd(14);
                     }
                     break;
                  case 15:
                     if ((576460745995190270L & l) != 0L) {
                        if (kind > 3) {
                           kind = 3;
                        }

                        this.jjCheckNAdd(15);
                     }
                     break;
                  case 16:
                     if ((576460745995190270L & l) != 0L) {
                        this.jjCheckNAddTwoStates(16, 17);
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
                     if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
                        if (kind > 2) {
                           kind = 2;
                        }

                        this.jjCheckNAddStates(10, 15);
                     }
                     break;
                  case 1:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(3, 5);
                     }
                  case 2:
                  case 3:
                  case 4:
                  case 5:
                  case 7:
                  case 8:
                  case 9:
                  case 10:
                  case 12:
                  default:
                     break;
                  case 6:
                     if (jjCanMove_0(hiByte, i1, i2, l1, l2)) {
                        this.jjAddStates(0, 2);
                     }
                     break;
                  case 11:
                     if (jjCanMove_2(hiByte, i1, i2, l1, l2)) {
                        this.jjCheckNAddTwoStates(11, 12);
                     }
                     break;
                  case 13:
                     if (jjCanMove_1(hiByte, i1, i2, l1, l2)) {
                        if (kind > 2) {
                           kind = 2;
                        }

                        this.jjCheckNAdd(14);
                     }
                     break;
                  case 14:
                     if (jjCanMove_2(hiByte, i1, i2, l1, l2)) {
                        if (kind > 2) {
                           kind = 2;
                        }

                        this.jjCheckNAdd(14);
                     }
                     break;
                  case 15:
                     if (jjCanMove_2(hiByte, i1, i2, l1, l2)) {
                        if (kind > 3) {
                           kind = 3;
                        }

                        this.jjCheckNAdd(15);
                     }
                     break;
                  case 16:
                     if (jjCanMove_2(hiByte, i1, i2, l1, l2)) {
                        this.jjCheckNAddTwoStates(16, 17);
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
         if ((i = this.jjnewStateCnt) == (startsAt = 19 - (this.jjnewStateCnt = startsAt))) {
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
         case 1:
            return (jjbitVec5[i2] & l2) != 0L;
         case 2:
            return (jjbitVec6[i2] & l2) != 0L;
         case 3:
            return (jjbitVec7[i2] & l2) != 0L;
         case 4:
            return (jjbitVec8[i2] & l2) != 0L;
         case 5:
            return (jjbitVec9[i2] & l2) != 0L;
         case 6:
            return (jjbitVec10[i2] & l2) != 0L;
         case 9:
            return (jjbitVec11[i2] & l2) != 0L;
         case 10:
            return (jjbitVec12[i2] & l2) != 0L;
         case 11:
            return (jjbitVec13[i2] & l2) != 0L;
         case 12:
            return (jjbitVec14[i2] & l2) != 0L;
         case 13:
            return (jjbitVec15[i2] & l2) != 0L;
         case 14:
            return (jjbitVec16[i2] & l2) != 0L;
         case 15:
            return (jjbitVec17[i2] & l2) != 0L;
         case 16:
            return (jjbitVec18[i2] & l2) != 0L;
         case 17:
            return (jjbitVec19[i2] & l2) != 0L;
         case 30:
            return (jjbitVec20[i2] & l2) != 0L;
         case 31:
            return (jjbitVec21[i2] & l2) != 0L;
         case 33:
            return (jjbitVec22[i2] & l2) != 0L;
         case 48:
            return (jjbitVec23[i2] & l2) != 0L;
         case 49:
            return (jjbitVec24[i2] & l2) != 0L;
         case 159:
            return (jjbitVec25[i2] & l2) != 0L;
         case 215:
            return (jjbitVec26[i2] & l2) != 0L;
         default:
            return (jjbitVec3[i1] & l1) != 0L;
      }
   }

   private static final boolean jjCanMove_2(int hiByte, int i1, int i2, long l1, long l2) {
      switch (hiByte) {
         case 0:
            return (jjbitVec27[i2] & l2) != 0L;
         case 1:
            return (jjbitVec5[i2] & l2) != 0L;
         case 2:
            return (jjbitVec28[i2] & l2) != 0L;
         case 3:
            return (jjbitVec29[i2] & l2) != 0L;
         case 4:
            return (jjbitVec30[i2] & l2) != 0L;
         case 5:
            return (jjbitVec31[i2] & l2) != 0L;
         case 6:
            return (jjbitVec32[i2] & l2) != 0L;
         case 9:
            return (jjbitVec33[i2] & l2) != 0L;
         case 10:
            return (jjbitVec34[i2] & l2) != 0L;
         case 11:
            return (jjbitVec35[i2] & l2) != 0L;
         case 12:
            return (jjbitVec36[i2] & l2) != 0L;
         case 13:
            return (jjbitVec37[i2] & l2) != 0L;
         case 14:
            return (jjbitVec38[i2] & l2) != 0L;
         case 15:
            return (jjbitVec39[i2] & l2) != 0L;
         case 16:
            return (jjbitVec18[i2] & l2) != 0L;
         case 17:
            return (jjbitVec19[i2] & l2) != 0L;
         case 30:
            return (jjbitVec20[i2] & l2) != 0L;
         case 31:
            return (jjbitVec21[i2] & l2) != 0L;
         case 32:
            return (jjbitVec40[i2] & l2) != 0L;
         case 33:
            return (jjbitVec22[i2] & l2) != 0L;
         case 48:
            return (jjbitVec41[i2] & l2) != 0L;
         case 49:
            return (jjbitVec24[i2] & l2) != 0L;
         case 159:
            return (jjbitVec25[i2] & l2) != 0L;
         case 215:
            return (jjbitVec26[i2] & l2) != 0L;
         default:
            return (jjbitVec3[i1] & l1) != 0L;
      }
   }

   public SPathParserTokenManager(ASCII_UCodeESC_CharStream stream) {
      this.jjrounds = new int[19];
      this.jjstateSet = new int[38];
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = stream;
   }

   public SPathParserTokenManager(ASCII_UCodeESC_CharStream stream, int lexState) {
      this(stream);
      this.SwitchTo(lexState);
   }

   public void ReInit(ASCII_UCodeESC_CharStream stream) {
      this.jjmatchedPos = this.jjnewStateCnt = 0;
      this.curLexState = this.defaultLexState;
      this.input_stream = stream;
      this.ReInitRounds();
   }

   private final void ReInitRounds() {
      this.jjround = -2147483647;

      for(int i = 19; i-- > 0; this.jjrounds[i] = Integer.MIN_VALUE) {
      }

   }

   public void ReInit(ASCII_UCodeESC_CharStream stream, int lexState) {
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
      int curPos = false;

      Token matchedToken;
      try {
         this.curChar = this.input_stream.BeginToken();
      } catch (IOException var9) {
         this.jjmatchedKind = 0;
         matchedToken = this.jjFillToken();
         return matchedToken;
      }

      this.jjmatchedKind = Integer.MAX_VALUE;
      this.jjmatchedPos = 0;
      int curPos = this.jjMoveStringLiteralDfa0_0();
      if (this.jjmatchedKind != Integer.MAX_VALUE) {
         if (this.jjmatchedPos + 1 < curPos) {
            this.input_stream.backup(curPos - this.jjmatchedPos - 1);
         }

         matchedToken = this.jjFillToken();
         return matchedToken;
      } else {
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
   }
}
