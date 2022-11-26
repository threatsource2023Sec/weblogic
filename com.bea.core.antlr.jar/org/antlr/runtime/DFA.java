package org.antlr.runtime;

public class DFA {
   protected short[] eot;
   protected short[] eof;
   protected char[] min;
   protected char[] max;
   protected short[] accept;
   protected short[] special;
   protected short[][] transition;
   protected int decisionNumber;
   protected BaseRecognizer recognizer;
   public static final boolean debug = false;

   public int predict(IntStream input) throws RecognitionException {
      int mark = input.mark();
      int s = 0;

      try {
         while(true) {
            int specialState = this.special[s];
            if (specialState >= 0) {
               s = this.specialStateTransition(specialState, input);
               if (s == -1) {
                  this.noViableAlt(s, input);
                  byte var11 = 0;
                  return var11;
               }

               input.consume();
            } else {
               short c;
               if (this.accept[s] >= 1) {
                  c = this.accept[s];
                  return c;
               }

               c = (short)((char)input.LA(1));
               short snext;
               if (c >= this.min[s] && c <= this.max[s]) {
                  snext = this.transition[s][c - this.min[s]];
                  if (snext >= 0) {
                     s = snext;
                     input.consume();
                  } else if (this.eot[s] >= 0) {
                     s = this.eot[s];
                     input.consume();
                  } else {
                     this.noViableAlt(s, input);
                     byte var7 = 0;
                     return var7;
                  }
               } else {
                  if (this.eot[s] < 0) {
                     if (c == '\uffff' && this.eof[s] >= 0) {
                        snext = this.accept[this.eof[s]];
                        return snext;
                     }

                     this.noViableAlt(s, input);
                     snext = 0;
                     return snext;
                  }

                  s = this.eot[s];
                  input.consume();
               }
            }
         }
      } finally {
         input.rewind(mark);
      }
   }

   protected void noViableAlt(int s, IntStream input) throws NoViableAltException {
      if (this.recognizer.state.backtracking > 0) {
         this.recognizer.state.failed = true;
      } else {
         NoViableAltException nvae = new NoViableAltException(this.getDescription(), this.decisionNumber, s, input);
         this.error(nvae);
         throw nvae;
      }
   }

   protected void error(NoViableAltException nvae) {
   }

   public int specialStateTransition(int s, IntStream input) throws NoViableAltException {
      return -1;
   }

   public String getDescription() {
      return "n/a";
   }

   public static short[] unpackEncodedString(String encodedString) {
      int size = 0;

      for(int i = 0; i < encodedString.length(); i += 2) {
         size += encodedString.charAt(i);
      }

      short[] data = new short[size];
      int di = 0;

      for(int i = 0; i < encodedString.length(); i += 2) {
         char n = encodedString.charAt(i);
         char v = encodedString.charAt(i + 1);

         for(int j = 1; j <= n; ++j) {
            data[di++] = (short)v;
         }
      }

      return data;
   }

   public static char[] unpackEncodedStringToUnsignedChars(String encodedString) {
      int size = 0;

      for(int i = 0; i < encodedString.length(); i += 2) {
         size += encodedString.charAt(i);
      }

      char[] data = new char[size];
      int di = 0;

      for(int i = 0; i < encodedString.length(); i += 2) {
         char n = encodedString.charAt(i);
         char v = encodedString.charAt(i + 1);

         for(int j = 1; j <= n; ++j) {
            data[di++] = v;
         }
      }

      return data;
   }
}
