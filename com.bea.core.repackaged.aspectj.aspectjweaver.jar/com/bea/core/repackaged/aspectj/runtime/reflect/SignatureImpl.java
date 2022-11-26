package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.Signature;
import java.lang.ref.SoftReference;
import java.util.StringTokenizer;

abstract class SignatureImpl implements Signature {
   private static boolean useCache = true;
   int modifiers = -1;
   String name;
   String declaringTypeName;
   Class declaringType;
   Cache stringCache;
   private String stringRep;
   ClassLoader lookupClassLoader = null;
   static final char SEP = '-';
   static String[] EMPTY_STRING_ARRAY = new String[0];
   static Class[] EMPTY_CLASS_ARRAY = new Class[0];
   static final String INNER_SEP = ":";

   SignatureImpl(int modifiers, String name, Class declaringType) {
      this.modifiers = modifiers;
      this.name = name;
      this.declaringType = declaringType;
   }

   protected abstract String createToString(StringMaker var1);

   String toString(StringMaker sm) {
      String result = null;
      if (useCache) {
         if (this.stringCache == null) {
            try {
               this.stringCache = new CacheImpl();
            } catch (Throwable var4) {
               useCache = false;
            }
         } else {
            result = this.stringCache.get(sm.cacheOffset);
         }
      }

      if (result == null) {
         result = this.createToString(sm);
      }

      if (useCache) {
         this.stringCache.set(sm.cacheOffset, result);
      }

      return result;
   }

   public final String toString() {
      return this.toString(StringMaker.middleStringMaker);
   }

   public final String toShortString() {
      return this.toString(StringMaker.shortStringMaker);
   }

   public final String toLongString() {
      return this.toString(StringMaker.longStringMaker);
   }

   public int getModifiers() {
      if (this.modifiers == -1) {
         this.modifiers = this.extractInt(0);
      }

      return this.modifiers;
   }

   public String getName() {
      if (this.name == null) {
         this.name = this.extractString(1);
      }

      return this.name;
   }

   public Class getDeclaringType() {
      if (this.declaringType == null) {
         this.declaringType = this.extractType(2);
      }

      return this.declaringType;
   }

   public String getDeclaringTypeName() {
      if (this.declaringTypeName == null) {
         this.declaringTypeName = this.getDeclaringType().getName();
      }

      return this.declaringTypeName;
   }

   String fullTypeName(Class type) {
      if (type == null) {
         return "ANONYMOUS";
      } else {
         return type.isArray() ? this.fullTypeName(type.getComponentType()) + "[]" : type.getName().replace('$', '.');
      }
   }

   String stripPackageName(String name) {
      int dot = name.lastIndexOf(46);
      return dot == -1 ? name : name.substring(dot + 1);
   }

   String shortTypeName(Class type) {
      if (type == null) {
         return "ANONYMOUS";
      } else {
         return type.isArray() ? this.shortTypeName(type.getComponentType()) + "[]" : this.stripPackageName(type.getName()).replace('$', '.');
      }
   }

   void addFullTypeNames(StringBuffer buf, Class[] types) {
      for(int i = 0; i < types.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.fullTypeName(types[i]));
      }

   }

   void addShortTypeNames(StringBuffer buf, Class[] types) {
      for(int i = 0; i < types.length; ++i) {
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(this.shortTypeName(types[i]));
      }

   }

   void addTypeArray(StringBuffer buf, Class[] types) {
      this.addFullTypeNames(buf, types);
   }

   public void setLookupClassLoader(ClassLoader loader) {
      this.lookupClassLoader = loader;
   }

   private ClassLoader getLookupClassLoader() {
      if (this.lookupClassLoader == null) {
         this.lookupClassLoader = this.getClass().getClassLoader();
      }

      return this.lookupClassLoader;
   }

   public SignatureImpl(String stringRep) {
      this.stringRep = stringRep;
   }

   String extractString(int n) {
      int startIndex = 0;

      int endIndex;
      for(endIndex = this.stringRep.indexOf(45); n-- > 0; endIndex = this.stringRep.indexOf(45, startIndex)) {
         startIndex = endIndex + 1;
      }

      if (endIndex == -1) {
         endIndex = this.stringRep.length();
      }

      return this.stringRep.substring(startIndex, endIndex);
   }

   int extractInt(int n) {
      String s = this.extractString(n);
      return Integer.parseInt(s, 16);
   }

   Class extractType(int n) {
      String s = this.extractString(n);
      return Factory.makeClass(s, this.getLookupClassLoader());
   }

   String[] extractStrings(int n) {
      String s = this.extractString(n);
      StringTokenizer st = new StringTokenizer(s, ":");
      int N = st.countTokens();
      String[] ret = new String[N];

      for(int i = 0; i < N; ++i) {
         ret[i] = st.nextToken();
      }

      return ret;
   }

   Class[] extractTypes(int n) {
      String s = this.extractString(n);
      StringTokenizer st = new StringTokenizer(s, ":");
      int N = st.countTokens();
      Class[] ret = new Class[N];

      for(int i = 0; i < N; ++i) {
         ret[i] = Factory.makeClass(st.nextToken(), this.getLookupClassLoader());
      }

      return ret;
   }

   static void setUseCache(boolean b) {
      useCache = b;
   }

   static boolean getUseCache() {
      return useCache;
   }

   private static final class CacheImpl implements Cache {
      private SoftReference toStringCacheRef;

      public CacheImpl() {
         this.makeCache();
      }

      public String get(int cacheOffset) {
         String[] cachedArray = this.array();
         return cachedArray == null ? null : cachedArray[cacheOffset];
      }

      public void set(int cacheOffset, String result) {
         String[] cachedArray = this.array();
         if (cachedArray == null) {
            cachedArray = this.makeCache();
         }

         cachedArray[cacheOffset] = result;
      }

      private String[] array() {
         return (String[])((String[])this.toStringCacheRef.get());
      }

      private String[] makeCache() {
         String[] array = new String[3];
         this.toStringCacheRef = new SoftReference(array);
         return array;
      }
   }

   private interface Cache {
      String get(int var1);

      void set(int var1, String var2);
   }
}
