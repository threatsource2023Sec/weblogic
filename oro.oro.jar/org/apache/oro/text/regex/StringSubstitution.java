package org.apache.oro.text.regex;

public class StringSubstitution implements Substitution {
   int _subLength;
   String _substitution;

   public StringSubstitution() {
      this("");
   }

   public StringSubstitution(String var1) {
      this.setSubstitution(var1);
   }

   public void setSubstitution(String var1) {
      this._substitution = var1;
      this._subLength = var1.length();
   }

   public String getSubstitution() {
      return this._substitution;
   }

   public String toString() {
      return this.getSubstitution();
   }

   public void appendSubstitution(StringBuffer var1, MatchResult var2, int var3, PatternMatcherInput var4, PatternMatcher var5, Pattern var6) {
      if (this._subLength != 0) {
         var1.append(this._substitution);
      }
   }
}
