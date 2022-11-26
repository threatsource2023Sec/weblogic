package com.bea.core.repackaged.jdt.internal.compiler.parser;

public class RecoveryScannerData {
   public int insertedTokensPtr = -1;
   public int[][] insertedTokens;
   public int[] insertedTokensPosition;
   public boolean[] insertedTokenUsed;
   public int replacedTokensPtr = -1;
   public int[][] replacedTokens;
   public int[] replacedTokensStart;
   public int[] replacedTokensEnd;
   public boolean[] replacedTokenUsed;
   public int removedTokensPtr = -1;
   public int[] removedTokensStart;
   public int[] removedTokensEnd;
   public boolean[] removedTokenUsed;

   public RecoveryScannerData removeUnused() {
      int newRemovedTokensPtr;
      int i;
      if (this.insertedTokens != null) {
         newRemovedTokensPtr = -1;

         for(i = 0; i <= this.insertedTokensPtr; ++i) {
            if (this.insertedTokenUsed[i]) {
               ++newRemovedTokensPtr;
               this.insertedTokens[newRemovedTokensPtr] = this.insertedTokens[i];
               this.insertedTokensPosition[newRemovedTokensPtr] = this.insertedTokensPosition[i];
               this.insertedTokenUsed[newRemovedTokensPtr] = this.insertedTokenUsed[i];
            }
         }

         this.insertedTokensPtr = newRemovedTokensPtr;
      }

      if (this.replacedTokens != null) {
         newRemovedTokensPtr = -1;

         for(i = 0; i <= this.replacedTokensPtr; ++i) {
            if (this.replacedTokenUsed[i]) {
               ++newRemovedTokensPtr;
               this.replacedTokens[newRemovedTokensPtr] = this.replacedTokens[i];
               this.replacedTokensStart[newRemovedTokensPtr] = this.replacedTokensStart[i];
               this.replacedTokensEnd[newRemovedTokensPtr] = this.replacedTokensEnd[i];
               this.replacedTokenUsed[newRemovedTokensPtr] = this.replacedTokenUsed[i];
            }
         }

         this.replacedTokensPtr = newRemovedTokensPtr;
      }

      if (this.removedTokensStart != null) {
         newRemovedTokensPtr = -1;

         for(i = 0; i <= this.removedTokensPtr; ++i) {
            if (this.removedTokenUsed[i]) {
               ++newRemovedTokensPtr;
               this.removedTokensStart[newRemovedTokensPtr] = this.removedTokensStart[i];
               this.removedTokensEnd[newRemovedTokensPtr] = this.removedTokensEnd[i];
               this.removedTokenUsed[newRemovedTokensPtr] = this.removedTokenUsed[i];
            }
         }

         this.removedTokensPtr = newRemovedTokensPtr;
      }

      return this;
   }
}
