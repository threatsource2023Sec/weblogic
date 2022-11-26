package org.python.antlr.runtime.tree;

import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.MismatchedTokenException;
import org.python.antlr.runtime.MissingTokenException;
import org.python.antlr.runtime.NoViableAltException;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.Token;
import org.python.antlr.runtime.TokenStream;
import org.python.antlr.runtime.UnwantedTokenException;

public class CommonErrorNode extends CommonTree {
   public IntStream input;
   public Token start;
   public Token stop;
   public RecognitionException trappedException;

   public CommonErrorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
      if (stop == null || stop.getTokenIndex() < start.getTokenIndex() && stop.getType() != -1) {
         stop = start;
      }

      this.input = input;
      this.start = start;
      this.stop = stop;
      this.trappedException = e;
   }

   public boolean isNil() {
      return false;
   }

   public int getType() {
      return 0;
   }

   public String getText() {
      String badText = null;
      if (this.start instanceof Token) {
         int i = this.start.getTokenIndex();
         int j = this.stop.getTokenIndex();
         if (this.stop.getType() == -1) {
            j = ((TokenStream)this.input).size();
         }

         badText = ((TokenStream)this.input).toString(i, j);
      } else if (this.start instanceof Tree) {
         badText = ((TreeNodeStream)this.input).toString(this.start, this.stop);
      } else {
         badText = "<unknown>";
      }

      return badText;
   }

   public String toString() {
      if (this.trappedException instanceof MissingTokenException) {
         return "<missing type: " + ((MissingTokenException)this.trappedException).getMissingType() + ">";
      } else if (this.trappedException instanceof UnwantedTokenException) {
         return "<extraneous: " + ((UnwantedTokenException)this.trappedException).getUnexpectedToken() + ", resync=" + this.getText() + ">";
      } else if (this.trappedException instanceof MismatchedTokenException) {
         return "<mismatched token: " + this.trappedException.token + ", resync=" + this.getText() + ">";
      } else {
         return this.trappedException instanceof NoViableAltException ? "<unexpected: " + this.trappedException.token + ", resync=" + this.getText() + ">" : "<error: " + this.getText() + ">";
      }
   }
}
