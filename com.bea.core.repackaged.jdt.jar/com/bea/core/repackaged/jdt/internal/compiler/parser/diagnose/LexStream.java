package com.bea.core.repackaged.jdt.internal.compiler.parser.diagnose;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.core.compiler.InvalidInputException;
import com.bea.core.repackaged.jdt.internal.compiler.parser.Scanner;
import com.bea.core.repackaged.jdt.internal.compiler.parser.TerminalTokens;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class LexStream implements TerminalTokens {
   public static final int IS_AFTER_JUMP = 1;
   public static final int LBRACE_MISSING = 2;
   private int tokenCacheIndex;
   private int tokenCacheEOFIndex;
   private Token[] tokenCache;
   private int currentIndex = -1;
   private Scanner scanner;
   private int[] intervalStartToSkip;
   private int[] intervalEndToSkip;
   private int[] intervalFlagsToSkip;
   private int previousInterval = -1;
   private int currentInterval = -1;
   private boolean awaitingColonColon;

   public LexStream(int size, Scanner scanner, int[] intervalStartToSkip, int[] intervalEndToSkip, int[] intervalFlagsToSkip, int firstToken, int init, int eof) {
      this.tokenCache = new Token[size];
      this.tokenCacheIndex = 0;
      this.tokenCacheEOFIndex = Integer.MAX_VALUE;
      this.tokenCache[0] = new Token();
      this.tokenCache[0].kind = firstToken;
      this.tokenCache[0].name = CharOperation.NO_CHAR;
      this.tokenCache[0].start = init;
      this.tokenCache[0].end = init;
      this.tokenCache[0].line = 0;
      this.intervalStartToSkip = intervalStartToSkip;
      this.intervalEndToSkip = intervalEndToSkip;
      this.intervalFlagsToSkip = intervalFlagsToSkip;
      this.awaitingColonColon = false;
      scanner.resetTo(init, eof);
      this.scanner = scanner;
   }

   private void readTokenFromScanner() {
      int length = this.tokenCache.length;
      boolean tokenNotFound = true;

      while(tokenNotFound) {
         try {
            int tokenKind = this.scanner.getNextToken();
            if (tokenKind == 84) {
               this.awaitingColonColon = true;
            } else if (tokenKind == 7) {
               this.awaitingColonColon = false;
            }

            int start;
            int end;
            if (tokenKind != 61) {
               start = this.scanner.getCurrentTokenStartPosition();
               end = this.scanner.getCurrentTokenEndPosition();
               int nextInterval = this.currentInterval + 1;
               if (this.intervalStartToSkip.length != 0 && nextInterval < this.intervalStartToSkip.length && start >= this.intervalStartToSkip[nextInterval]) {
                  this.scanner.resetTo(this.intervalEndToSkip[++this.currentInterval] + 1, this.scanner.eofPosition - 1);
               } else {
                  Token token = new Token();
                  token.kind = tokenKind;
                  token.name = this.scanner.getCurrentTokenSource();
                  token.start = start;
                  token.end = end;
                  token.line = Util.getLineNumber(end, this.scanner.lineEnds, 0, this.scanner.linePtr);
                  if (this.currentInterval != this.previousInterval && (this.intervalFlagsToSkip[this.currentInterval] & 2) == 0) {
                     token.flags = 1;
                     if ((this.intervalFlagsToSkip[this.currentInterval] & 1) != 0) {
                        token.flags |= 2;
                     }
                  }

                  this.previousInterval = this.currentInterval;
                  this.tokenCache[++this.tokenCacheIndex % length] = token;
                  tokenNotFound = false;
               }
            } else {
               start = this.scanner.getCurrentTokenStartPosition();
               end = this.scanner.getCurrentTokenEndPosition();
               Token token = new Token();
               token.kind = tokenKind;
               token.name = CharOperation.NO_CHAR;
               token.start = start;
               token.end = end;
               token.line = Util.getLineNumber(end, this.scanner.lineEnds, 0, this.scanner.linePtr);
               this.tokenCache[++this.tokenCacheIndex % length] = token;
               this.tokenCacheEOFIndex = this.tokenCacheIndex;
               tokenNotFound = false;
            }
         } catch (InvalidInputException var8) {
         }
      }

   }

   public Token token(int index) {
      if (index < 0) {
         Token eofToken = new Token();
         eofToken.kind = 61;
         eofToken.name = CharOperation.NO_CHAR;
         return eofToken;
      } else if (this.tokenCacheEOFIndex >= 0 && index > this.tokenCacheEOFIndex) {
         return this.token(this.tokenCacheEOFIndex);
      } else {
         int length = this.tokenCache.length;
         if (index > this.tokenCacheIndex) {
            int tokensToRead = index - this.tokenCacheIndex;

            while(tokensToRead-- != 0) {
               this.readTokenFromScanner();
            }
         } else if (this.tokenCacheIndex - length >= index) {
            return null;
         }

         return this.tokenCache[index % length];
      }
   }

   public int getToken() {
      return this.currentIndex = this.next(this.currentIndex);
   }

   public int previous(int tokenIndex) {
      return tokenIndex > 0 ? tokenIndex - 1 : 0;
   }

   public int next(int tokenIndex) {
      return tokenIndex < this.tokenCacheEOFIndex ? tokenIndex + 1 : this.tokenCacheEOFIndex;
   }

   public boolean afterEol(int i) {
      return i < 1 ? true : this.line(i - 1) < this.line(i);
   }

   public void reset() {
      this.currentIndex = -1;
   }

   public void reset(int i) {
      this.currentIndex = this.previous(i);
   }

   public int badtoken() {
      return 0;
   }

   public int kind(int tokenIndex) {
      return this.token(tokenIndex).kind;
   }

   public char[] name(int tokenIndex) {
      return this.token(tokenIndex).name;
   }

   public int line(int tokenIndex) {
      return this.token(tokenIndex).line;
   }

   public int start(int tokenIndex) {
      return this.token(tokenIndex).start;
   }

   public int end(int tokenIndex) {
      return this.token(tokenIndex).end;
   }

   public int flags(int tokenIndex) {
      return this.token(tokenIndex).flags;
   }

   public boolean isInsideStream(int index) {
      if (this.tokenCacheEOFIndex >= 0 && index > this.tokenCacheEOFIndex) {
         return false;
      } else if (index > this.tokenCacheIndex) {
         return true;
      } else {
         return this.tokenCacheIndex - this.tokenCache.length < index;
      }
   }

   public String toString() {
      StringBuffer res = new StringBuffer();
      String source = new String(this.scanner.source);
      int curtokKind;
      int curtokStart;
      int curtokEnd;
      if (this.currentIndex < 0) {
         int previousEnd = -1;

         for(curtokKind = 0; curtokKind < this.intervalStartToSkip.length; ++curtokKind) {
            curtokStart = this.intervalStartToSkip[curtokKind];
            curtokEnd = this.intervalEndToSkip[curtokKind];
            res.append(source.substring(previousEnd + 1, curtokStart));
            res.append('<');
            res.append('@');
            res.append(source.substring(curtokStart, curtokEnd + 1));
            res.append('@');
            res.append('>');
            previousEnd = curtokEnd;
         }

         res.append(source.substring(previousEnd + 1));
      } else {
         Token token = this.token(this.currentIndex);
         curtokKind = token.kind;
         curtokStart = token.start;
         curtokEnd = token.end;
         int previousEnd = -1;

         for(int i = 0; i < this.intervalStartToSkip.length; ++i) {
            int intervalStart = this.intervalStartToSkip[i];
            int intervalEnd = this.intervalEndToSkip[i];
            if (curtokStart >= previousEnd && curtokEnd <= intervalStart) {
               res.append(source.substring(previousEnd + 1, curtokStart));
               res.append('<');
               res.append('#');
               res.append(source.substring(curtokStart, curtokEnd + 1));
               res.append('#');
               res.append('>');
               res.append(source.substring(curtokEnd + 1, intervalStart));
            } else {
               res.append(source.substring(previousEnd + 1, intervalStart));
            }

            res.append('<');
            res.append('@');
            res.append(source.substring(intervalStart, intervalEnd + 1));
            res.append('@');
            res.append('>');
            previousEnd = intervalEnd;
         }

         if (curtokStart >= previousEnd) {
            res.append(source.substring(previousEnd + 1, curtokStart));
            res.append('<');
            res.append('#');
            if (curtokKind == 61) {
               res.append("EOF#>");
            } else {
               res.append(source.substring(curtokStart, curtokEnd + 1));
               res.append('#');
               res.append('>');
               res.append(source.substring(curtokEnd + 1));
            }
         } else {
            res.append(source.substring(previousEnd + 1));
         }
      }

      return res.toString();
   }

   public boolean awaitingColonColon() {
      return this.awaitingColonColon;
   }

   public static class Token {
      int kind;
      char[] name;
      int start;
      int end;
      int line;
      int flags;

      public String toString() {
         StringBuffer buffer = new StringBuffer();
         buffer.append(this.name).append('[').append(this.kind).append(']');
         buffer.append('{').append(this.start).append(',').append(this.end).append('}').append(this.line);
         return buffer.toString();
      }
   }
}
