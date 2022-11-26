package org.hibernate.validator.internal.engine.messageinterpolation.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TokenIterator {
   private final List tokenList;
   private int currentPosition;
   private Token currentToken;
   private boolean allInterpolationTermsProcessed;
   private boolean currentTokenAvailable;

   public TokenIterator(List tokens) {
      this.tokenList = new ArrayList(tokens);
   }

   public boolean hasMoreInterpolationTerms() throws MessageDescriptorFormatException {
      while(true) {
         if (this.currentPosition < this.tokenList.size()) {
            this.currentToken = (Token)this.tokenList.get(this.currentPosition);
            ++this.currentPosition;
            if (!this.currentToken.isParameter()) {
               continue;
            }

            this.currentTokenAvailable = true;
            return true;
         }

         this.allInterpolationTermsProcessed = true;
         return false;
      }
   }

   public String nextInterpolationTerm() {
      if (!this.currentTokenAvailable) {
         throw new IllegalStateException("Trying to call #nextInterpolationTerm without calling #hasMoreInterpolationTerms");
      } else {
         this.currentTokenAvailable = false;
         return this.currentToken.getTokenValue();
      }
   }

   public void replaceCurrentInterpolationTerm(String replacement) {
      Token token = new Token(replacement);
      token.terminate();
      this.tokenList.set(this.currentPosition - 1, token);
   }

   public String getInterpolatedMessage() {
      if (!this.allInterpolationTermsProcessed) {
         throw new IllegalStateException("Not all interpolation terms have been processed yet.");
      } else {
         StringBuilder messageBuilder = new StringBuilder();
         Iterator var2 = this.tokenList.iterator();

         while(var2.hasNext()) {
            Token token = (Token)var2.next();
            messageBuilder.append(token.getTokenValue());
         }

         return messageBuilder.toString();
      }
   }
}
