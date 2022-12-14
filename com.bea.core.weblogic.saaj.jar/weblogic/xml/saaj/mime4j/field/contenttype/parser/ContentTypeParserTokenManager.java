package weblogic.xml.saaj.mime4j.field.contenttype.parser;

import java.io.IOException;
import java.io.PrintStream;

public class ContentTypeParserTokenManager implements ContentTypeParserConstants {
   static int commentNest;
   public PrintStream debugStream;
   static final long[] jjbitVec0 = new long[]{0L, 0L, -1L, -1L};
   static final int[] jjnextStates = new int[0];
   public static final String[] jjstrLiteralImages = new String[]{"", "\r", "\n", "/", ";", "=", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
   public static final String[] lexStateNames = new String[]{"DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING"};
   public static final int[] jjnewLexState = new int[]{-1, -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, 3, -1, -1, 0, -1, -1, -1, -1};
   static final long[] jjtoToken = new long[]{3670079L};
   static final long[] jjtoSkip = new long[]{320L};
   static final long[] jjtoSpecial = new long[]{64L};
   static final long[] jjtoMore = new long[]{523904L};
   protected SimpleCharStream input_stream;
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

   public void setDebugStream(PrintStream ds) {
      this.debugStream = ds;
   }

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
         case '\n':
            return this.jjStartNfaWithStates_0(0, 2, 2);
         case '\r':
            return this.jjStartNfaWithStates_0(0, 1, 2);
         case '"':
            return this.jjStopAtPos(0, 16);
         case '(':
            return this.jjStopAtPos(0, 7);
         case '/':
            return this.jjStopAtPos(0, 3);
         case ';':
            return this.jjStopAtPos(0, 4);
         case '=':
            return this.jjStopAtPos(0, 5);
         default:
            return this.jjMoveNfa_0(3, 0);
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
                     if ((4294967808L & l) != 0L) {
                        kind = 6;
                        this.jjCheckNAdd(0);
                     }
                     break;
                  case 1:
                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 20) {
                           kind = 20;
                        }

                        this.jjCheckNAdd(1);
                     }
                     break;
                  case 2:
                     if ((288068726467591679L & l) != 0L) {
                        if (kind > 21) {
                           kind = 21;
                        }

                        this.jjCheckNAdd(2);
                     }
                     break;
                  case 3:
                     if ((288068726467591679L & l) != 0L) {
                        if (kind > 21) {
                           kind = 21;
                        }

                        this.jjCheckNAdd(2);
                     } else if ((4294967808L & l) != 0L) {
                        if (kind > 6) {
                           kind = 6;
                        }

                        this.jjCheckNAdd(0);
                     }

                     if ((287948901175001088L & l) != 0L) {
                        if (kind > 20) {
                           kind = 20;
                        }

                        this.jjCheckNAdd(1);
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 2:
                  case 3:
                     if ((-939524098L & l) != 0L) {
                        kind = 21;
                        this.jjCheckNAdd(2);
                     }
               }
            } while(i != startsAt);
         } else {
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 2:
                  case 3:
                     if ((jjbitVec0[i2] & l2) != 0L) {
                        if (kind > 21) {
                           kind = 21;
                        }

                        this.jjCheckNAdd(2);
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
         } catch (IOException var11) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_1(int pos, long active0) {
      switch (pos) {
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
         case '(':
            return this.jjStopAtPos(0, 10);
         case ')':
            return this.jjStopAtPos(0, 8);
         default:
            return this.jjMoveNfa_1(0, 0);
      }
   }

   private final int jjMoveNfa_1(int startState, int curPos) {
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
                     if (kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 1:
                     if (kind > 9) {
                        kind = 9;
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if (kind > 11) {
                        kind = 11;
                     }

                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if (kind > 9) {
                        kind = 9;
                     }
                     break;
                  case 2:
                     if (kind > 11) {
                        kind = 11;
                     }
               }
            } while(i != startsAt);
         } else {
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if ((jjbitVec0[i2] & l2) != 0L && kind > 11) {
                        kind = 11;
                     }
                     break;
                  case 1:
                     if ((jjbitVec0[i2] & l2) != 0L && kind > 9) {
                        kind = 9;
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
         } catch (IOException var11) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_3(int pos, long active0) {
      switch (pos) {
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
         case '"':
            return this.jjStopAtPos(0, 19);
         default:
            return this.jjMoveNfa_3(0, 0);
      }
   }

   private final int jjMoveNfa_3(int startState, int curPos) {
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
                  case 2:
                     if ((-17179869185L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(2);
                     }
                     break;
                  case 1:
                     if (kind > 17) {
                        kind = 17;
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if ((-268435457L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(2);
                     } else if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if (kind > 17) {
                        kind = 17;
                     }
                     break;
                  case 2:
                     if ((-268435457L & l) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(2);
                     }
               }
            } while(i != startsAt);
         } else {
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                  case 2:
                     if ((jjbitVec0[i2] & l2) != 0L) {
                        if (kind > 18) {
                           kind = 18;
                        }

                        this.jjCheckNAdd(2);
                     }
                     break;
                  case 1:
                     if ((jjbitVec0[i2] & l2) != 0L && kind > 17) {
                        kind = 17;
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
         } catch (IOException var11) {
            return curPos;
         }
      }
   }

   private final int jjStopStringLiteralDfa_2(int pos, long active0) {
      switch (pos) {
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
         case '(':
            return this.jjStopAtPos(0, 13);
         case ')':
            return this.jjStopAtPos(0, 14);
         default:
            return this.jjMoveNfa_2(0, 0);
      }
   }

   private final int jjMoveNfa_2(int startState, int curPos) {
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
                     if (kind > 15) {
                        kind = 15;
                     }
                     break;
                  case 1:
                     if (kind > 12) {
                        kind = 12;
                     }
               }
            } while(i != startsAt);
         } else if (this.curChar < 128) {
            l = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if (kind > 15) {
                        kind = 15;
                     }

                     if (this.curChar == '\\') {
                        this.jjstateSet[this.jjnewStateCnt++] = 1;
                     }
                     break;
                  case 1:
                     if (kind > 12) {
                        kind = 12;
                     }
                     break;
                  case 2:
                     if (kind > 15) {
                        kind = 15;
                     }
               }
            } while(i != startsAt);
         } else {
            int i2 = (this.curChar & 255) >> 6;
            long l2 = 1L << (this.curChar & 63);

            do {
               --i;
               switch (this.jjstateSet[i]) {
                  case 0:
                     if ((jjbitVec0[i2] & l2) != 0L && kind > 15) {
                        kind = 15;
                     }
                     break;
                  case 1:
                     if ((jjbitVec0[i2] & l2) != 0L && kind > 12) {
                        kind = 12;
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
         } catch (IOException var11) {
            return curPos;
         }
      }
   }

   public ContentTypeParserTokenManager(SimpleCharStream stream) {
      this.debugStream = System.out;
      this.jjrounds = new int[3];
      this.jjstateSet = new int[6];
      this.curLexState = 0;
      this.defaultLexState = 0;
      this.input_stream = stream;
   }

   public ContentTypeParserTokenManager(SimpleCharStream stream, int lexState) {
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

      for(int i = 3; i-- > 0; this.jjrounds[i] = Integer.MIN_VALUE) {
      }

   }

   public void ReInit(SimpleCharStream stream, int lexState) {
      this.ReInit(stream);
      this.SwitchTo(lexState);
   }

   public void SwitchTo(int lexState) {
      if (lexState < 4 && lexState >= 0) {
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
      int curPos = 0;

      label96:
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
                  break;
               case 2:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_2();
                  break;
               case 3:
                  this.jjmatchedKind = Integer.MAX_VALUE;
                  this.jjmatchedPos = 0;
                  curPos = this.jjMoveStringLiteralDfa0_3();
            }

            if (this.jjmatchedKind == Integer.MAX_VALUE) {
               break label96;
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
               break label96;
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

   void MoreLexicalActions() {
      this.jjimageLen += this.lengthOfMatch = this.jjmatchedPos + 1;
      switch (this.jjmatchedKind) {
         case 9:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            this.image.deleteCharAt(this.image.length() - 2);
            break;
         case 10:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            commentNest = 1;
         case 11:
         case 15:
         default:
            break;
         case 12:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            this.image.deleteCharAt(this.image.length() - 2);
            break;
         case 13:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            ++commentNest;
            break;
         case 14:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            --commentNest;
            if (commentNest == 0) {
               this.SwitchTo(1);
            }
            break;
         case 16:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            this.image.deleteCharAt(this.image.length() - 1);
            break;
         case 17:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen)));
            }

            this.jjimageLen = 0;
            this.image.deleteCharAt(this.image.length() - 2);
      }

   }

   void TokenLexicalActions(Token matchedToken) {
      switch (this.jjmatchedKind) {
         case 19:
            if (this.image == null) {
               this.image = new StringBuffer(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            } else {
               this.image.append(new String(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1))));
            }

            matchedToken.image = this.image.substring(0, this.image.length() - 1);
         default:
      }
   }
}
