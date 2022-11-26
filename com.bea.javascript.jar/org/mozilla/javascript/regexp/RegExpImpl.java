package org.mozilla.javascript.regexp;

import java.util.Vector;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.JavaScriptException;
import org.mozilla.javascript.RegExpProxy;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Undefined;

public class RegExpImpl implements RegExpProxy {
   String input;
   boolean multiline;
   Vector parens = new Vector(9);
   SubString lastMatch;
   SubString lastParen;
   SubString leftContext;
   SubString rightContext;

   public int find_split(Scriptable var1, String var2, String var3, Object var4, int[] var5, int[] var6, boolean[] var7, String[][] var8) {
      int var9 = var5[0];
      int var10 = var2.length();
      Context var12 = Context.getCurrentContext();
      int var13 = var12.getLanguageVersion();
      NativeRegExp var14 = (NativeRegExp)var4;

      int var11;
      int var15;
      SubString var17;
      while(true) {
         var15 = var5[0];
         var5[0] = var9;
         Object var16 = var14.executeRegExp(var12, var1, this, var2, var5, 0);
         if (var16 != Boolean.TRUE) {
            var5[0] = var15;
            var6[0] = 1;
            var7[0] = false;
            return var10;
         }

         var9 = var5[0];
         var5[0] = var15;
         var7[0] = true;
         var17 = this.lastMatch;
         var6[0] = var17.length;
         if (var6[0] != 0 || var9 != var5[0]) {
            var11 = var9 - var6[0];
            break;
         }

         if (var9 == var10) {
            if (var13 == 120) {
               var6[0] = 1;
               var11 = var9;
            } else {
               var11 = -1;
            }
            break;
         }

         ++var9;
      }

      var15 = this.parens.size();
      var8[0] = new String[var15];

      for(int var18 = 0; var18 < var15; ++var18) {
         var17 = this.getParenSubString(var18);
         var8[0][var18] = var17.toString();
      }

      return var11;
   }

   SubString getParenSubString(int var1) {
      return var1 >= this.parens.size() ? SubString.emptySubString : (SubString)this.parens.elementAt(var1);
   }

   public boolean isRegExp(Object var1) {
      return var1 instanceof NativeRegExp;
   }

   public Object match(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      MatchData var5 = new MatchData();
      var5.optarg = 1;
      var5.mode = 1;
      var5.parent = ScriptableObject.getTopLevelScope(var2);
      Object var6 = matchOrReplace(var1, var2, var3, var4, this, var5, false);
      return var5.arrayobj == null ? var6 : var5.arrayobj;
   }

   private static Object matchOrReplace(Context var0, Scriptable var1, Scriptable var2, Object[] var3, RegExpImpl var4, GlobData var5, boolean var6) throws JavaScriptException {
      String var8 = ScriptRuntime.toString(var2);
      var5.str = var8;
      Scriptable var9 = ScriptableObject.getTopLevelScope(var1);
      NativeRegExp var7;
      if (var3.length == 0) {
         var7 = new NativeRegExp(var0, var9, "", "", false);
      } else if (var3[0] instanceof NativeRegExp) {
         var7 = (NativeRegExp)var3[0];
      } else {
         String var10 = ScriptRuntime.toString(var3[0]);
         String var11;
         if (var5.optarg < var3.length) {
            var3[0] = var10;
            var11 = ScriptRuntime.toString(var3[var5.optarg]);
         } else {
            var11 = null;
         }

         var7 = new NativeRegExp(var0, var9, var10, var11, var6);
      }

      var5.regexp = var7;
      var5.global = (var7.getFlags() & 1) != 0;
      int[] var13 = new int[1];
      Object var14 = null;
      if (var5.mode == 3) {
         var14 = var7.executeRegExp(var0, var1, var4, var8, var13, 0);
         if (var14 != null && var14.equals(Boolean.TRUE)) {
            var14 = new Integer(var4.leftContext.length);
         } else {
            var14 = new Integer(-1);
         }
      } else if (var5.global) {
         var7.setLastIndex(0);

         for(int var12 = 0; var13[0] <= var8.length(); ++var12) {
            var14 = var7.executeRegExp(var0, var1, var4, var8, var13, 0);
            if (var14 == null || !var14.equals(Boolean.TRUE)) {
               break;
            }

            var5.doGlobal(var0, var1, var12, var4);
            if (var4.lastMatch.length == 0) {
               if (var13[0] == var8.length()) {
                  break;
               }

               int var10002 = var13[0]++;
            }
         }
      } else {
         var14 = var7.executeRegExp(var0, var1, var4, var8, var13, var5.mode == 2 ? 0 : 1);
      }

      return var14;
   }

   public Object newRegExp(Context var1, Scriptable var2, String var3, String var4, boolean var5) {
      return new NativeRegExp(var1, var2, var3, var4, var5);
   }

   public Object replace(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      Object var5 = var4.length < 2 ? Undefined.instance : var4[1];
      String var6 = null;
      Function var7 = null;
      if (var5 instanceof Function) {
         var7 = (Function)var5;
      } else {
         var6 = ScriptRuntime.toString(var5);
      }

      ReplaceData var8 = new ReplaceData();
      var8.optarg = 2;
      var8.mode = 2;
      var8.lambda = var7;
      var8.repstr = var6 == null ? null : var6.toCharArray();
      var8.dollar = var6 == null ? -1 : var6.indexOf(36);
      var8.charArray = null;
      var8.length = 0;
      var8.index = 0;
      var8.leftIndex = 0;
      Object var9 = matchOrReplace(var1, var2, var3, var4, this, var8, true);
      char[] var10;
      int var12;
      if (var8.charArray == null) {
         if (var8.global || var9 == null || !var9.equals(Boolean.TRUE)) {
            return var8.str;
         }

         int var11 = this.leftContext.length;
         var12 = var11 + var8.findReplen(var1, this);
         var10 = new char[var12];
         SubString var13 = this.leftContext;
         System.arraycopy(var13.charArray, var13.index, var10, 0, var11);
         var8.doReplace(var1, this, var10, var11);
         var8.charArray = var10;
         var8.length = var12;
      }

      SubString var14 = this.rightContext;
      var12 = var14.length;
      int var15 = var8.length + var12;
      var10 = new char[var15];
      System.arraycopy(var8.charArray, 0, var10, 0, var8.charArray.length);
      System.arraycopy(var14.charArray, var14.index, var10, var8.length, var12);
      return new String(var10, 0, var15);
   }

   public Object search(Context var1, Scriptable var2, Scriptable var3, Object[] var4) throws JavaScriptException {
      MatchData var5 = new MatchData();
      var5.optarg = 1;
      var5.mode = 3;
      var5.parent = ScriptableObject.getTopLevelScope(var2);
      return matchOrReplace(var1, var2, var3, var4, this, var5, false);
   }
}
