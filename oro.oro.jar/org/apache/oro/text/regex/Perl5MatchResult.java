package org.apache.oro.text.regex;

final class Perl5MatchResult implements MatchResult {
   int _matchBeginOffset;
   int[] _beginGroupOffset;
   int[] _endGroupOffset;
   String _match;

   Perl5MatchResult(int var1) {
      this._beginGroupOffset = new int[var1];
      this._endGroupOffset = new int[var1];
   }

   public int length() {
      int var1 = this._endGroupOffset[0] - this._beginGroupOffset[0];
      return var1 > 0 ? var1 : 0;
   }

   public int groups() {
      return this._beginGroupOffset.length;
   }

   public String group(int var1) {
      if (var1 < this._beginGroupOffset.length) {
         int var2 = this._beginGroupOffset[var1];
         int var3 = this._endGroupOffset[var1];
         int var4 = this._match.length();
         if (var2 >= 0 && var3 >= 0) {
            if (var2 < var4 && var3 <= var4 && var3 > var2) {
               return this._match.substring(var2, var3);
            }

            if (var2 <= var3) {
               return "";
            }
         }
      }

      return null;
   }

   public int begin(int var1) {
      if (var1 < this._beginGroupOffset.length) {
         int var2 = this._beginGroupOffset[var1];
         int var3 = this._endGroupOffset[var1];
         if (var2 >= 0 && var3 >= 0) {
            return var2;
         }
      }

      return -1;
   }

   public int end(int var1) {
      if (var1 < this._beginGroupOffset.length) {
         int var2 = this._beginGroupOffset[var1];
         int var3 = this._endGroupOffset[var1];
         if (var2 >= 0 && var3 >= 0) {
            return var3;
         }
      }

      return -1;
   }

   public int beginOffset(int var1) {
      if (var1 < this._beginGroupOffset.length) {
         int var2 = this._beginGroupOffset[var1];
         int var3 = this._endGroupOffset[var1];
         if (var2 >= 0 && var3 >= 0) {
            return this._matchBeginOffset + var2;
         }
      }

      return -1;
   }

   public int endOffset(int var1) {
      if (var1 < this._endGroupOffset.length) {
         int var2 = this._beginGroupOffset[var1];
         int var3 = this._endGroupOffset[var1];
         if (var2 >= 0 && var3 >= 0) {
            return this._matchBeginOffset + var3;
         }
      }

      return -1;
   }

   public String toString() {
      return this.group(0);
   }
}
