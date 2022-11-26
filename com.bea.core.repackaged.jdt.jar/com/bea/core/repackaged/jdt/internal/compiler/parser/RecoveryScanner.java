package com.bea.core.repackaged.jdt.internal.compiler.parser;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.core.compiler.InvalidInputException;

public class RecoveryScanner extends Scanner {
   public static final char[] FAKE_IDENTIFIER = "$missing$".toCharArray();
   private RecoveryScannerData data;
   private int[] pendingTokens;
   private int pendingTokensPtr = -1;
   private char[] fakeTokenSource = null;
   private boolean isInserted = true;
   private boolean precededByRemoved = false;
   private int skipNextInsertedTokens = -1;
   public boolean record = true;

   public RecoveryScanner(Scanner scanner, RecoveryScannerData data) {
      super(false, scanner.tokenizeWhiteSpace, scanner.checkNonExternalizedStringLiterals, scanner.sourceLevel, scanner.complianceLevel, scanner.taskTags, scanner.taskPriorities, scanner.isTaskCaseSensitive);
      this.setData(data);
   }

   public RecoveryScanner(boolean tokenizeWhiteSpace, boolean checkNonExternalizedStringLiterals, long sourceLevel, long complianceLevel, char[][] taskTags, char[][] taskPriorities, boolean isTaskCaseSensitive, RecoveryScannerData data) {
      super(false, tokenizeWhiteSpace, checkNonExternalizedStringLiterals, sourceLevel, complianceLevel, taskTags, taskPriorities, isTaskCaseSensitive);
      this.setData(data);
   }

   public void insertToken(int token, int completedToken, int position) {
      this.insertTokens(new int[]{token}, completedToken, position);
   }

   private int[] reverse(int[] tokens) {
      int length = tokens.length;
      int i = 0;

      for(int max = length / 2; i < max; ++i) {
         int tmp = tokens[i];
         tokens[i] = tokens[length - i - 1];
         tokens[length - i - 1] = tmp;
      }

      return tokens;
   }

   public void insertTokens(int[] tokens, int completedToken, int position) {
      if (this.record) {
         if (completedToken <= -1 || Parser.statements_recovery_filter[completedToken] == 0) {
            ++this.data.insertedTokensPtr;
            if (this.data.insertedTokens == null) {
               this.data.insertedTokens = new int[10][];
               this.data.insertedTokensPosition = new int[10];
               this.data.insertedTokenUsed = new boolean[10];
            } else if (this.data.insertedTokens.length == this.data.insertedTokensPtr) {
               int length = this.data.insertedTokens.length;
               System.arraycopy(this.data.insertedTokens, 0, this.data.insertedTokens = new int[length * 2][], 0, length);
               System.arraycopy(this.data.insertedTokensPosition, 0, this.data.insertedTokensPosition = new int[length * 2], 0, length);
               System.arraycopy(this.data.insertedTokenUsed, 0, this.data.insertedTokenUsed = new boolean[length * 2], 0, length);
            }

            this.data.insertedTokens[this.data.insertedTokensPtr] = this.reverse(tokens);
            this.data.insertedTokensPosition[this.data.insertedTokensPtr] = position;
            this.data.insertedTokenUsed[this.data.insertedTokensPtr] = false;
         }
      }
   }

   public void insertTokenAhead(int token, int index) {
      if (this.record) {
         int length = this.data.insertedTokens[index].length;
         int[] tokens = new int[length + 1];
         System.arraycopy(this.data.insertedTokens[index], 0, tokens, 1, length);
         tokens[0] = token;
         this.data.insertedTokens[index] = tokens;
      }
   }

   public void replaceTokens(int token, int start, int end) {
      this.replaceTokens(new int[]{token}, start, end);
   }

   public void replaceTokens(int[] tokens, int start, int end) {
      if (this.record) {
         ++this.data.replacedTokensPtr;
         if (this.data.replacedTokensStart == null) {
            this.data.replacedTokens = new int[10][];
            this.data.replacedTokensStart = new int[10];
            this.data.replacedTokensEnd = new int[10];
            this.data.replacedTokenUsed = new boolean[10];
         } else if (this.data.replacedTokensStart.length == this.data.replacedTokensPtr) {
            int length = this.data.replacedTokensStart.length;
            System.arraycopy(this.data.replacedTokens, 0, this.data.replacedTokens = new int[length * 2][], 0, length);
            System.arraycopy(this.data.replacedTokensStart, 0, this.data.replacedTokensStart = new int[length * 2], 0, length);
            System.arraycopy(this.data.replacedTokensEnd, 0, this.data.replacedTokensEnd = new int[length * 2], 0, length);
            System.arraycopy(this.data.replacedTokenUsed, 0, this.data.replacedTokenUsed = new boolean[length * 2], 0, length);
         }

         this.data.replacedTokens[this.data.replacedTokensPtr] = this.reverse(tokens);
         this.data.replacedTokensStart[this.data.replacedTokensPtr] = start;
         this.data.replacedTokensEnd[this.data.replacedTokensPtr] = end;
         this.data.replacedTokenUsed[this.data.replacedTokensPtr] = false;
      }
   }

   public void removeTokens(int start, int end) {
      if (this.record) {
         ++this.data.removedTokensPtr;
         if (this.data.removedTokensStart == null) {
            this.data.removedTokensStart = new int[10];
            this.data.removedTokensEnd = new int[10];
            this.data.removedTokenUsed = new boolean[10];
         } else if (this.data.removedTokensStart.length == this.data.removedTokensPtr) {
            int length = this.data.removedTokensStart.length;
            System.arraycopy(this.data.removedTokensStart, 0, this.data.removedTokensStart = new int[length * 2], 0, length);
            System.arraycopy(this.data.removedTokensEnd, 0, this.data.removedTokensEnd = new int[length * 2], 0, length);
            System.arraycopy(this.data.removedTokenUsed, 0, this.data.removedTokenUsed = new boolean[length * 2], 0, length);
         }

         this.data.removedTokensStart[this.data.removedTokensPtr] = start;
         this.data.removedTokensEnd[this.data.removedTokensPtr] = end;
         this.data.removedTokenUsed[this.data.removedTokensPtr] = false;
      }
   }

   protected int getNextToken0() throws InvalidInputException {
      int previousLocation;
      if (this.pendingTokensPtr > -1) {
         previousLocation = this.pendingTokens[this.pendingTokensPtr--];
         if (previousLocation == 22) {
            this.fakeTokenSource = FAKE_IDENTIFIER;
         } else {
            this.fakeTokenSource = CharOperation.NO_CHAR;
         }

         return previousLocation;
      } else {
         this.fakeTokenSource = null;
         this.precededByRemoved = false;
         int pendingToken;
         if (this.data.insertedTokens != null) {
            for(previousLocation = 0; previousLocation <= this.data.insertedTokensPtr; ++previousLocation) {
               if (this.data.insertedTokensPosition[previousLocation] == this.currentPosition - 1 && previousLocation > this.skipNextInsertedTokens) {
                  this.data.insertedTokenUsed[previousLocation] = true;
                  this.pendingTokens = this.data.insertedTokens[previousLocation];
                  this.pendingTokensPtr = this.data.insertedTokens[previousLocation].length - 1;
                  this.isInserted = true;
                  this.startPosition = this.currentPosition;
                  this.skipNextInsertedTokens = previousLocation;
                  pendingToken = this.pendingTokens[this.pendingTokensPtr--];
                  if (pendingToken == 22) {
                     this.fakeTokenSource = FAKE_IDENTIFIER;
                  } else {
                     this.fakeTokenSource = CharOperation.NO_CHAR;
                  }

                  return pendingToken;
               }
            }

            this.skipNextInsertedTokens = -1;
         }

         previousLocation = this.currentPosition;
         pendingToken = super.getNextToken0();
         int i;
         if (this.data.replacedTokens != null) {
            for(i = 0; i <= this.data.replacedTokensPtr; ++i) {
               if (this.data.replacedTokensStart[i] >= previousLocation && this.data.replacedTokensStart[i] <= this.startPosition && this.data.replacedTokensEnd[i] >= this.currentPosition - 1) {
                  this.data.replacedTokenUsed[i] = true;
                  this.pendingTokens = this.data.replacedTokens[i];
                  this.pendingTokensPtr = this.data.replacedTokens[i].length - 1;
                  this.fakeTokenSource = FAKE_IDENTIFIER;
                  this.isInserted = false;
                  this.currentPosition = this.data.replacedTokensEnd[i] + 1;
                  int pendingToken = this.pendingTokens[this.pendingTokensPtr--];
                  if (pendingToken == 22) {
                     this.fakeTokenSource = FAKE_IDENTIFIER;
                  } else {
                     this.fakeTokenSource = CharOperation.NO_CHAR;
                  }

                  return pendingToken;
               }
            }
         }

         if (this.data.removedTokensStart != null) {
            for(i = 0; i <= this.data.removedTokensPtr; ++i) {
               if (this.data.removedTokensStart[i] >= previousLocation && this.data.removedTokensStart[i] <= this.startPosition && this.data.removedTokensEnd[i] >= this.currentPosition - 1) {
                  this.data.removedTokenUsed[i] = true;
                  this.currentPosition = this.data.removedTokensEnd[i] + 1;
                  this.precededByRemoved = false;
                  return this.getNextToken0();
               }
            }
         }

         return pendingToken;
      }
   }

   public char[] getCurrentIdentifierSource() {
      return this.fakeTokenSource != null ? this.fakeTokenSource : super.getCurrentIdentifierSource();
   }

   public char[] getCurrentTokenSourceString() {
      return this.fakeTokenSource != null ? this.fakeTokenSource : super.getCurrentTokenSourceString();
   }

   public char[] getCurrentTokenSource() {
      return this.fakeTokenSource != null ? this.fakeTokenSource : super.getCurrentTokenSource();
   }

   public RecoveryScannerData getData() {
      return this.data;
   }

   public boolean isFakeToken() {
      return this.fakeTokenSource != null;
   }

   public boolean isInsertedToken() {
      return this.fakeTokenSource != null && this.isInserted;
   }

   public boolean isReplacedToken() {
      return this.fakeTokenSource != null && !this.isInserted;
   }

   public boolean isPrecededByRemovedToken() {
      return this.precededByRemoved;
   }

   public void setData(RecoveryScannerData data) {
      if (data == null) {
         this.data = new RecoveryScannerData();
      } else {
         this.data = data;
      }

   }

   public void setPendingTokens(int[] pendingTokens) {
      this.pendingTokens = pendingTokens;
      this.pendingTokensPtr = pendingTokens.length - 1;
   }
}
