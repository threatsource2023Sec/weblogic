package org.apache.oro.text.regex;

import java.util.ArrayList;
import java.util.Iterator;

public class Perl5Substitution extends StringSubstitution {
   public static final int INTERPOLATE_ALL = 0;
   public static final int INTERPOLATE_NONE = -1;
   int _numInterpolations;
   ArrayList _substitutions;
   transient String _lastInterpolation;

   public Perl5Substitution() {
      this("", 0);
   }

   public Perl5Substitution(String var1) {
      this(var1, 0);
   }

   public Perl5Substitution(String var1, int var2) {
      this.setSubstitution(var1, var2);
   }

   void _calcSub(StringBuffer var1, MatchResult var2) {
      Iterator var8 = this._substitutions.iterator();

      while(true) {
         while(var8.hasNext()) {
            Object var5 = var8.next();
            if (var5 instanceof String) {
               var1.append(var5);
            } else {
               Integer var6 = (Integer)var5;
               int var4 = var6;
               if (var4 > 0 && var4 < var2.groups()) {
                  String var7 = var2.group(var4);
                  if (var7 != null) {
                     var1.append(var7);
                  }
               } else {
                  var1.append('$');
                  var1.append(var4);
               }
            }
         }

         return;
      }
   }

   String _finalInterpolatedSub(MatchResult var1) {
      StringBuffer var2 = new StringBuffer(10);
      this._calcSub(var2, var1);
      return var2.toString();
   }

   static ArrayList _parseSubs(String var0) {
      ArrayList var5 = new ArrayList(5);
      StringBuffer var6 = new StringBuffer(5);
      StringBuffer var7 = new StringBuffer(10);
      char[] var4 = var0.toCharArray();
      int var3 = 0;
      boolean var1 = false;

      boolean var2;
      for(var2 = false; var3 < var4.length; ++var3) {
         if (var1 && Character.isDigit(var4[var3])) {
            var6.append(var4[var3]);
            if (var7.length() > 0) {
               var5.add(var7.toString());
               var7.setLength(0);
            }
         } else {
            if (var1) {
               try {
                  var5.add(new Integer(var6.toString()));
                  var2 = true;
               } catch (NumberFormatException var9) {
                  var5.add(var6.toString());
               }

               var6.setLength(0);
               var1 = false;
            }

            if (var4[var3] == '$' && var3 + 1 < var4.length && var4[var3 + 1] != '0' && Character.isDigit(var4[var3 + 1])) {
               var1 = true;
            } else {
               var7.append(var4[var3]);
            }
         }
      }

      if (var1) {
         try {
            var5.add(new Integer(var6.toString()));
            var2 = true;
         } catch (NumberFormatException var8) {
            var5.add(var6.toString());
         }
      } else if (var7.length() > 0) {
         var5.add(var7.toString());
      }

      return var2 ? var5 : null;
   }

   public void appendSubstitution(StringBuffer var1, MatchResult var2, int var3, String var4, PatternMatcher var5, Pattern var6) {
      if (this._substitutions == null) {
         super.appendSubstitution(var1, var2, var3, var4, var5, var6);
      } else {
         if (this._numInterpolations >= 1 && var3 >= this._numInterpolations) {
            if (var3 == this._numInterpolations) {
               this._lastInterpolation = this._finalInterpolatedSub(var2);
            }

            var1.append(this._lastInterpolation);
         } else {
            this._calcSub(var1, var2);
         }

      }
   }

   public void setSubstitution(String var1) {
      this.setSubstitution(var1, 0);
   }

   public void setSubstitution(String var1, int var2) {
      super.setSubstitution(var1);
      this._numInterpolations = var2;
      if (var2 != -1 && var1.indexOf(36) != -1) {
         this._substitutions = _parseSubs(var1);
      } else {
         this._substitutions = null;
      }

      this._lastInterpolation = null;
   }
}
