package org.apache.oro.text;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.util.Cache;

public abstract class GenericPatternCache implements PatternCache {
   PatternCompiler _compiler;
   Cache _cache;
   public static final int DEFAULT_CAPACITY = 20;

   GenericPatternCache(Cache var1, PatternCompiler var2) {
      this._cache = var1;
      this._compiler = var2;
   }

   public final synchronized Pattern addPattern(String var1, int var2) throws MalformedPatternException {
      Object var3 = this._cache.getElement(var1);
      Pattern var4;
      if (var3 != null) {
         var4 = (Pattern)var3;
         if (var4.getOptions() == var2) {
            return var4;
         }
      }

      var4 = this._compiler.compile(var1, var2);
      this._cache.addElement(var1, var4);
      return var4;
   }

   public final synchronized Pattern addPattern(String var1) throws MalformedPatternException {
      return this.addPattern(var1, 0);
   }

   public final synchronized Pattern getPattern(String var1, int var2) throws MalformedCachePatternException {
      Pattern var3 = null;

      try {
         var3 = this.addPattern(var1, var2);
         return var3;
      } catch (MalformedPatternException var5) {
         throw new MalformedCachePatternException("Invalid expression: " + var1 + "\n" + var5.getMessage());
      }
   }

   public final synchronized Pattern getPattern(String var1) throws MalformedCachePatternException {
      return this.getPattern(var1, 0);
   }

   public final int size() {
      return this._cache.size();
   }

   public final int capacity() {
      return this._cache.capacity();
   }
}
