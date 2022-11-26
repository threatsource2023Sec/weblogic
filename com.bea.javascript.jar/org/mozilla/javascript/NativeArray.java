package org.mozilla.javascript;

import java.util.Hashtable;

public class NativeArray extends IdScriptable {
   private static final int Id_length = 1;
   private static final int MAX_INSTANCE_ID = 1;
   private static final int Id_constructor = 2;
   private static final int Id_toString = 3;
   private static final int Id_toLocaleString = 4;
   private static final int Id_join = 5;
   private static final int Id_reverse = 6;
   private static final int Id_sort = 7;
   private static final int Id_push = 8;
   private static final int Id_pop = 9;
   private static final int Id_shift = 10;
   private static final int Id_unshift = 11;
   private static final int Id_splice = 12;
   private static final int Id_concat = 13;
   private static final int Id_slice = 14;
   private static final int MAX_PROTOTYPE_ID = 14;
   private long length;
   private Object[] dense;
   private static final int maximumDenseLength = 10000;
   private boolean prototypeFlag;

   public NativeArray() {
      this.dense = null;
      this.length = 0L;
   }

   public NativeArray(long var1) {
      int var3 = (int)var1;
      if ((long)var3 == var1 && var3 > 0) {
         if (var3 > 10000) {
            var3 = 10000;
         }

         this.dense = new Object[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            this.dense[var4] = Scriptable.NOT_FOUND;
         }
      }

      this.length = var1;
   }

   public NativeArray(Object[] var1) {
      this.dense = var1;
      this.length = (long)var1.length;
   }

   public void delete(int var1) {
      if (!this.isSealed() && this.dense != null && var1 >= 0 && var1 < this.dense.length) {
         this.dense[var1] = Scriptable.NOT_FOUND;
      } else {
         super.delete(var1);
      }
   }

   public Object execMethod(int var1, IdFunction var2, Context var3, Scriptable var4, Scriptable var5, Object[] var6) throws JavaScriptException {
      if (this.prototypeFlag) {
         switch (var1) {
            case 2:
               return jsConstructor(var3, var4, var6, var2, var5 == null);
            case 3:
               return jsFunction_toString(var3, var5, var6);
            case 4:
               return jsFunction_toLocaleString(var3, var5, var6);
            case 5:
               return jsFunction_join(var3, var5, var6);
            case 6:
               return jsFunction_reverse(var3, var5, var6);
            case 7:
               return jsFunction_sort(var3, var4, var5, var6);
            case 8:
               return jsFunction_push(var3, var5, var6);
            case 9:
               return jsFunction_pop(var3, var5, var6);
            case 10:
               return jsFunction_shift(var3, var5, var6);
            case 11:
               return jsFunction_unshift(var3, var5, var6);
            case 12:
               return jsFunction_splice(var3, var4, var5, var6);
            case 13:
               return jsFunction_concat(var3, var4, var5, var6);
            case 14:
               return this.jsFunction_slice(var3, var5, var6);
         }
      }

      return super.execMethod(var1, var2, var3, var4, var5, var6);
   }

   public Object get(int var1, Scriptable var2) {
      return this.dense != null && var1 >= 0 && var1 < this.dense.length ? this.dense[var1] : super.get(var1, var2);
   }

   public String getClassName() {
      return "Array";
   }

   public Object getDefaultValue(Class var1) {
      if (var1 == ScriptRuntime.NumberClass) {
         Context var2 = Context.getContext();
         if (var2.getLanguageVersion() == 120) {
            return new Long(this.length);
         }
      }

      return super.getDefaultValue(var1);
   }

   private static Object getElem(Scriptable var0, long var1) {
      if (var1 > 2147483647L) {
         String var3 = Long.toString(var1);
         return ScriptRuntime.getElem(var0, var3, var0);
      } else {
         return ScriptRuntime.getElem(var0, (int)var1);
      }
   }

   protected int getIdDefaultAttributes(int var1) {
      return var1 == 1 ? 6 : super.getIdDefaultAttributes(var1);
   }

   protected String getIdName(int var1) {
      if (var1 == 1) {
         return "length";
      } else {
         if (this.prototypeFlag) {
            switch (var1) {
               case 2:
                  return "constructor";
               case 3:
                  return "toString";
               case 4:
                  return "toLocaleString";
               case 5:
                  return "join";
               case 6:
                  return "reverse";
               case 7:
                  return "sort";
               case 8:
                  return "push";
               case 9:
                  return "pop";
               case 10:
                  return "shift";
               case 11:
                  return "unshift";
               case 12:
                  return "splice";
               case 13:
                  return "concat";
               case 14:
                  return "slice";
            }
         }

         return null;
      }
   }

   protected Object getIdValue(int var1) {
      return var1 == 1 ? this.wrap_double((double)this.length) : super.getIdValue(var1);
   }

   public Object[] getIds() {
      Object[] var1 = super.getIds();
      if (this.dense == null) {
         return var1;
      } else {
         int var2 = 0;
         int var3 = this.dense.length;
         if ((long)var3 > this.length) {
            var3 = (int)this.length;
         }

         for(int var4 = var3 - 1; var4 >= 0; --var4) {
            if (this.dense[var4] != Scriptable.NOT_FOUND) {
               ++var2;
            }
         }

         var2 += var1.length;
         Object[] var5 = new Object[var2];
         System.arraycopy(var1, 0, var5, 0, var1.length);

         for(int var6 = var3 - 1; var6 >= 0; --var6) {
            if (this.dense[var6] != Scriptable.NOT_FOUND) {
               --var2;
               var5[var2] = new Integer(var6);
            }
         }

         return var5;
      }
   }

   static double getLengthProperty(Scriptable var0) {
      if (var0 instanceof NativeString) {
         return ((NativeString)var0).jsGet_length();
      } else {
         return var0 instanceof NativeArray ? (double)((NativeArray)var0).jsGet_length() : (double)ScriptRuntime.toUint32(ScriptRuntime.getProp(var0, "length", var0));
      }
   }

   public boolean has(int var1, Scriptable var2) {
      if (this.dense != null && var1 >= 0 && var1 < this.dense.length) {
         return this.dense[var1] != Scriptable.NOT_FOUND;
      } else {
         return super.has(var1, var2);
      }
   }

   private static boolean hasElem(Scriptable var0, long var1) {
      return var1 > 2147483647L ? var0.has(Long.toString(var1), var0) : var0.has((int)var1, var0);
   }

   static boolean hasLengthProperty(Object var0) {
      if (var0 instanceof Scriptable && var0 != Context.getUndefinedValue()) {
         if (!(var0 instanceof NativeString) && !(var0 instanceof NativeArray)) {
            Scriptable var1 = (Scriptable)var0;
            Object var2 = ScriptRuntime.getProp(var1, "length", var1);
            return var2 instanceof Number;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public static void init(Context var0, Scriptable var1, boolean var2) {
      NativeArray var3 = new NativeArray();
      var3.prototypeFlag = true;
      var3.addAsPrototype(14, var0, var1, var2);
   }

   private static Object jsConstructor(Context var0, Scriptable var1, Object[] var2, IdFunction var3, boolean var4) throws JavaScriptException {
      if (!var4) {
         return var3.construct(var0, var1, var2);
      } else if (var2.length == 0) {
         return new NativeArray();
      } else if (var0.getLanguageVersion() == 120) {
         return new NativeArray(var2);
      } else if (var2.length <= 1 && var2[0] instanceof Number) {
         long var5 = ScriptRuntime.toUint32(var2[0]);
         if ((double)var5 != ((Number)var2[0]).doubleValue()) {
            throw Context.reportRuntimeError0("msg.arraylength.bad");
         } else {
            return new NativeArray(var5);
         }
      } else {
         return new NativeArray(var2);
      }
   }

   private static Scriptable jsFunction_concat(Context var0, Scriptable var1, Scriptable var2, Object[] var3) {
      var1 = ScriptableObject.getTopLevelScope(var1);
      Scriptable var4 = ScriptRuntime.newObject(var0, (Scriptable)var1, (String)"Array", (Object[])null);
      long var7 = 0L;
      double var5;
      if (hasLengthProperty(var2)) {
         var5 = getLengthProperty(var2);

         for(var7 = 0L; (double)var7 < var5; ++var7) {
            Object var9 = getElem(var2, var7);
            setElem(var4, var7, var9);
         }
      } else {
         setElem(var4, var7++, var2);
      }

      for(int var14 = 0; var14 < var3.length; ++var14) {
         if (hasLengthProperty(var3[var14])) {
            Scriptable var10 = (Scriptable)var3[var14];
            var5 = getLengthProperty(var10);

            for(long var11 = 0L; (double)var11 < var5; ++var7) {
               Object var13 = getElem(var10, var11);
               setElem(var4, var7, var13);
               ++var11;
            }
         } else {
            setElem(var4, var7++, var3[var14]);
         }
      }

      return var4;
   }

   private static String jsFunction_join(Context var0, Scriptable var1, Object[] var2) {
      StringBuffer var3 = new StringBuffer();
      double var5 = getLengthProperty(var1);
      String var4;
      if (var2.length < 1) {
         var4 = ",";
      } else {
         var4 = ScriptRuntime.toString(var2[0]);
      }

      for(long var7 = 0L; (double)var7 < var5; ++var7) {
         if (var7 > 0L) {
            var3.append(var4);
         }

         Object var9 = getElem(var1, var7);
         if (var9 != null && var9 != Undefined.instance) {
            var3.append(ScriptRuntime.toString(var9));
         }
      }

      return var3.toString();
   }

   private static Object jsFunction_pop(Context var0, Scriptable var1, Object[] var2) {
      double var4 = getLengthProperty(var1);
      Object var3;
      if (var4 > 0.0) {
         --var4;
         var3 = getElem(var1, (long)var4);
      } else {
         var3 = Context.getUndefinedValue();
      }

      ScriptRuntime.setProp(var1, "length", new Double(var4), var1);
      return var3;
   }

   private static Object jsFunction_push(Context var0, Scriptable var1, Object[] var2) {
      double var3 = getLengthProperty(var1);

      for(int var5 = 0; var5 < var2.length; ++var5) {
         setElem(var1, (long)var3 + (long)var5, var2[var5]);
      }

      var3 += (double)var2.length;
      ScriptRuntime.setProp(var1, "length", new Double(var3), var1);
      if (var0.getLanguageVersion() == 120) {
         return var2.length == 0 ? Context.getUndefinedValue() : var2[var2.length - 1];
      } else {
         return new Long((long)var3);
      }
   }

   private static Scriptable jsFunction_reverse(Context var0, Scriptable var1, Object[] var2) {
      long var3 = (long)getLengthProperty(var1);
      long var5 = var3 / 2L;

      for(long var7 = 0L; var7 < var5; ++var7) {
         long var9 = var3 - var7 - 1L;
         Object var11 = getElem(var1, var7);
         Object var12 = getElem(var1, var9);
         setElem(var1, var7, var12);
         setElem(var1, var9, var11);
      }

      return var1;
   }

   private static Object jsFunction_shift(Context var0, Scriptable var1, Object[] var2) {
      double var4 = getLengthProperty(var1);
      Object var3;
      if (var4 > 0.0) {
         long var6 = 0L;
         --var4;
         var3 = getElem(var1, var6);
         if (var4 > 0.0) {
            for(var6 = 1L; (double)var6 <= var4; ++var6) {
               Object var8 = getElem(var1, var6);
               setElem(var1, var6 - 1L, var8);
            }
         }
      } else {
         var3 = Context.getUndefinedValue();
      }

      ScriptRuntime.setProp(var1, "length", new Double(var4), var1);
      return var3;
   }

   private Scriptable jsFunction_slice(Context var1, Scriptable var2, Object[] var3) {
      Scriptable var4 = ScriptableObject.getTopLevelScope(this);
      Scriptable var5 = ScriptRuntime.newObject(var1, (Scriptable)var4, (String)"Array", (Object[])null);
      double var6 = getLengthProperty(var2);
      double var8 = 0.0;
      double var10 = var6;
      if (var3.length > 0) {
         var8 = ScriptRuntime.toInteger(var3[0]);
         if (var8 < 0.0) {
            var8 += var6;
            if (var8 < 0.0) {
               var8 = 0.0;
            }
         } else if (var8 > var6) {
            var8 = var6;
         }

         if (var3.length > 1) {
            var10 = ScriptRuntime.toInteger(var3[1]);
            if (var10 < 0.0) {
               var10 += var6;
               if (var10 < 0.0) {
                  var10 = 0.0;
               }
            } else if (var10 > var6) {
               var10 = var6;
            }
         }
      }

      long var12 = (long)var8;
      long var14 = (long)var10;

      for(long var16 = var12; var16 < var14; ++var16) {
         Object var18 = getElem(var2, var16);
         setElem(var5, var16 - var12, var18);
      }

      return var5;
   }

   private static Scriptable jsFunction_sort(Context var0, Scriptable var1, Scriptable var2, Object[] var3) throws JavaScriptException {
      long var4 = (long)getLengthProperty(var2);
      Object var6;
      if (var3.length > 0 && Undefined.instance != var3[0]) {
         var6 = var3[0];
      } else {
         var6 = null;
      }

      if (var4 >= 2147483647L) {
         qsort_extended(var0, var6, var2, 0L, var4 - 1L);
      } else {
         Object[] var7 = new Object[(int)var4];

         for(int var8 = 0; (long)var8 < var4; ++var8) {
            var7[var8] = getElem(var2, (long)var8);
         }

         qsort(var0, var6, var7, 0, (int)var4 - 1, var1);

         for(int var9 = 0; (long)var9 < var4; ++var9) {
            setElem(var2, (long)var9, var7[var9]);
         }
      }

      return var2;
   }

   private static Object jsFunction_splice(Context var0, Scriptable var1, Scriptable var2, Object[] var3) {
      var1 = ScriptableObject.getTopLevelScope(var1);
      Object var4 = ScriptRuntime.newObject(var0, (Scriptable)var1, (String)"Array", (Object[])null);
      int var5 = var3.length;
      if (var5 == 0) {
         return var4;
      } else {
         double var6 = getLengthProperty(var2);
         double var8 = ScriptRuntime.toInteger(var3[0]);
         if (var8 < 0.0) {
            var8 += var6;
            if (var8 < 0.0) {
               var8 = 0.0;
            }
         } else if (var8 > var6) {
            var8 = var6;
         }

         --var5;
         double var12 = var6 - var8;
         double var10;
         double var14;
         if (var3.length == 1) {
            var14 = var12;
            var10 = var6;
         } else {
            var14 = ScriptRuntime.toInteger(var3[1]);
            if (var14 < 0.0) {
               var14 = 0.0;
            } else if (var14 > var12) {
               var14 = var12;
            }

            var10 = var8 + var14;
            --var5;
         }

         long var16 = (long)var8;
         long var18 = (long)var10;
         long var20;
         if (var14 > 0.0) {
            if (var14 == 1.0 && var0.getLanguageVersion() == 120) {
               var4 = getElem(var2, var16);
            } else {
               for(var20 = var16; var20 < var18; ++var20) {
                  Scriptable var22 = (Scriptable)var4;
                  Object var23 = getElem(var2, var20);
                  setElem(var22, var20 - var16, var23);
               }
            }
         } else if (var14 == 0.0 && var0.getLanguageVersion() == 120) {
            var4 = Context.getUndefinedValue();
         }

         var12 = (double)var5 - var14;
         Object var25;
         if (var12 > 0.0) {
            for(var20 = (long)var6 - 1L; var20 >= var18; --var20) {
               var25 = getElem(var2, var20);
               setElem(var2, var20 + (long)var12, var25);
            }
         } else if (var12 < 0.0) {
            for(var20 = var18; (double)var20 < var6; ++var20) {
               var25 = getElem(var2, var20);
               setElem(var2, var20 + (long)var12, var25);
            }
         }

         int var24 = var3.length - var5;

         for(int var21 = 0; var21 < var5; ++var21) {
            setElem(var2, var16 + (long)var21, var3[var21 + var24]);
         }

         ScriptRuntime.setProp(var2, "length", new Double(var6 + var12), var2);
         return var4;
      }
   }

   private static String jsFunction_toLocaleString(Context var0, Scriptable var1, Object[] var2) throws JavaScriptException {
      return toStringHelper(var0, var1, false, true);
   }

   private static String jsFunction_toString(Context var0, Scriptable var1, Object[] var2) throws JavaScriptException {
      return toStringHelper(var0, var1, var0.getLanguageVersion() == 120, false);
   }

   private static Object jsFunction_unshift(Context var0, Scriptable var1, Object[] var2) {
      double var4 = getLengthProperty(var1);
      int var6 = var2.length;
      if (var2.length > 0) {
         if (var4 > 0.0) {
            for(long var7 = (long)var4 - 1L; var7 >= 0L; --var7) {
               Object var9 = getElem(var1, var7);
               setElem(var1, var7 + (long)var6, var9);
            }
         }

         for(int var10 = 0; var10 < var2.length; ++var10) {
            setElem(var1, (long)var10, var2[var10]);
         }

         var4 += (double)var2.length;
         ScriptRuntime.setProp(var1, "length", new Double(var4), var1);
      }

      return new Long((long)var4);
   }

   public long jsGet_length() {
      return this.length;
   }

   private void jsSet_length(Object var1) {
      if (!(var1 instanceof Number)) {
         throw Context.reportRuntimeError0("msg.arraylength.bad");
      } else {
         long var2 = ScriptRuntime.toUint32(var1);
         if ((double)var2 != ((Number)var1).doubleValue()) {
            throw Context.reportRuntimeError0("msg.arraylength.bad");
         } else {
            if (var2 < this.length) {
               if (this.length - var2 > 4096L) {
                  Object[] var4 = this.getIds();

                  for(int var5 = 0; var5 < var4.length; ++var5) {
                     if (var4[var5] instanceof String) {
                        String var6 = (String)var4[var5];
                        double var7 = ScriptRuntime.toNumber(var6);
                        if (var7 == var7 && var7 < (double)this.length) {
                           this.delete(var6);
                        }
                     } else {
                        int var10 = ((Number)var4[var5]).intValue();
                        if ((long)var10 >= var2) {
                           this.delete(var10);
                        }
                     }
                  }
               } else {
                  for(long var9 = var2; var9 < this.length; ++var9) {
                     if (hasElem(this, var9)) {
                        ScriptRuntime.delete(this, new Long(var9));
                     }
                  }
               }
            }

            this.length = var2;
         }
      }
   }

   protected int mapNameToId(String var1) {
      if (var1.equals("length")) {
         return 1;
      } else {
         return this.prototypeFlag ? toPrototypeId(var1) : 0;
      }
   }

   protected int maxInstanceId() {
      return 1;
   }

   public int methodArity(int var1) {
      if (this.prototypeFlag) {
         switch (var1) {
            case 2:
               return 1;
            case 3:
               return 0;
            case 4:
               return 1;
            case 5:
               return 1;
            case 6:
               return 0;
            case 7:
               return 1;
            case 8:
               return 1;
            case 9:
               return 1;
            case 10:
               return 1;
            case 11:
               return 1;
            case 12:
               return 1;
            case 13:
               return 1;
            case 14:
               return 1;
         }
      }

      return super.methodArity(var1);
   }

   public void put(int var1, Scriptable var2, Object var3) {
      if (var2 == this) {
         if (this.length <= (long)var1) {
            this.length = (long)var1 + 1L;
         }

         if (this.dense != null && var1 >= 0 && var1 < this.dense.length) {
            this.dense[var1] = var3;
            return;
         }
      }

      super.put(var1, var2, var3);
   }

   public void put(String var1, Scriptable var2, Object var3) {
      if (var2 == this) {
         double var4 = ScriptRuntime.toNumber(var1);
         if ((double)ScriptRuntime.toUint32(var4) == var4 && ScriptRuntime.numberToString(var4, 10).equals(var1) && (double)this.length <= var4 && var4 != 4.294967295E9) {
            this.length = (long)var4 + 1L;
         }
      }

      super.put(var1, var2, var3);
   }

   private static void qsort(Context var0, Object var1, Object[] var2, int var3, int var4, Scriptable var5) throws JavaScriptException {
      while(var3 < var4) {
         int var7 = var3;
         int var8 = var4;
         int var9 = var3;

         Object var6;
         for(var6 = var2[var3]; var7 < var8; var2[var8] = var2[var9]) {
            while(!(qsortCompare(var0, var1, var2[var8], var6, var5) <= 0.0)) {
               --var8;
            }

            for(var2[var9] = var2[var8]; var7 < var8 && qsortCompare(var0, var1, var2[var9], var6, var5) <= 0.0; var9 = var7) {
               ++var7;
            }
         }

         var2[var9] = var6;
         if (var7 - var3 < var4 - var7) {
            qsort(var0, var1, var2, var3, var7 - 1, var5);
            var3 = var7 + 1;
         } else {
            qsort(var0, var1, var2, var7 + 1, var4, var5);
            var4 = var7 - 1;
         }
      }

   }

   private static double qsortCompare(Context var0, Object var1, Object var2, Object var3, Scriptable var4) throws JavaScriptException {
      Scriptable var5 = Undefined.instance;
      if (var5 != var2 && var5 != var3) {
         if (var1 == null) {
            String var9 = ScriptRuntime.toString(var2);
            String var10 = ScriptRuntime.toString(var3);
            return (double)var9.compareTo(var10);
         } else {
            Object[] var6 = new Object[]{var2, var3};
            double var7 = ScriptRuntime.toNumber(ScriptRuntime.call(var0, var1, (Object)null, var6, var4));
            return var7 == var7 ? var7 : 0.0;
         }
      } else if (var5 != var2) {
         return -1.0;
      } else {
         return var5 != var3 ? 1.0 : 0.0;
      }
   }

   private static void qsort_extended(Context var0, Object var1, Scriptable var2, long var3, long var5) throws JavaScriptException {
      while(var3 < var5) {
         long var8 = var3;
         long var10 = var5;
         long var12 = var3;
         Object var7 = getElem(var2, var3);

         while(var8 < var10) {
            while(!(qsortCompare(var0, var1, getElem(var2, var10), var7, var2) <= 0.0)) {
               --var10;
            }

            setElem(var2, var12, getElem(var2, var10));

            while(var8 < var10 && qsortCompare(var0, var1, getElem(var2, var12), var7, var2) <= 0.0) {
               ++var8;
               var12 = var8;
            }

            setElem(var2, var10, getElem(var2, var12));
         }

         setElem(var2, var12, var7);
         if (var8 - var3 < var5 - var8) {
            qsort_extended(var0, var1, var2, var3, var8 - 1L);
            var3 = var8 + 1L;
         } else {
            qsort_extended(var0, var1, var2, var8 + 1L, var5);
            var5 = var8 - 1L;
         }
      }

   }

   private static void setElem(Scriptable var0, long var1, Object var3) {
      if (var1 > 2147483647L) {
         String var4 = Long.toString(var1);
         ScriptRuntime.setElem(var0, var4, var3, var0);
      } else {
         ScriptRuntime.setElem(var0, (int)var1, var3);
      }

   }

   protected void setIdValue(int var1, Object var2) {
      if (var1 == 1) {
         this.jsSet_length(var2);
      } else {
         super.setIdValue(var1, var2);
      }
   }

   private static int toPrototypeId(String var0) {
      byte var1 = 0;
      String var2 = null;
      char var3;
      switch (var0.length()) {
         case 3:
            var2 = "pop";
            var1 = 9;
            break;
         case 4:
            var3 = var0.charAt(0);
            if (var3 == 'j') {
               var2 = "join";
               var1 = 5;
            } else if (var3 == 'p') {
               var2 = "push";
               var1 = 8;
            } else if (var3 == 's') {
               var2 = "sort";
               var1 = 7;
            }
            break;
         case 5:
            var3 = var0.charAt(1);
            if (var3 == 'h') {
               var2 = "shift";
               var1 = 10;
            } else if (var3 == 'l') {
               var2 = "slice";
               var1 = 14;
            }
            break;
         case 6:
            var3 = var0.charAt(0);
            if (var3 == 'c') {
               var2 = "concat";
               var1 = 13;
            } else if (var3 == 'l') {
               var2 = "length";
               var1 = 1;
            } else if (var3 == 's') {
               var2 = "splice";
               var1 = 12;
            }
            break;
         case 7:
            var3 = var0.charAt(0);
            if (var3 == 'r') {
               var2 = "reverse";
               var1 = 6;
            } else if (var3 == 'u') {
               var2 = "unshift";
               var1 = 11;
            }
            break;
         case 8:
            var2 = "toString";
            var1 = 3;
         case 9:
         case 10:
         case 12:
         case 13:
         default:
            break;
         case 11:
            var2 = "constructor";
            var1 = 2;
            break;
         case 14:
            var2 = "toLocaleString";
            var1 = 4;
      }

      if (var2 != null && var2 != var0 && !var2.equals(var0)) {
         var1 = 0;
      }

      return var1;
   }

   private static String toStringHelper(Context var0, Scriptable var1, boolean var2, boolean var3) throws JavaScriptException {
      long var4 = (long)getLengthProperty(var1);
      StringBuffer var6 = new StringBuffer();
      if (var0.iterating == null) {
         var0.iterating = new Hashtable(31);
      }

      boolean var7 = var0.iterating.get(var1) == Boolean.TRUE;
      String var8;
      if (var2) {
         var6.append('[');
         var8 = ", ";
      } else {
         var8 = ",";
      }

      boolean var9 = false;
      long var10 = 0L;
      if (!var7) {
         for(var10 = 0L; var10 < var4; ++var10) {
            if (var10 > 0L) {
               var6.append(var8);
            }

            Object var12 = getElem(var1, var10);
            if (var12 != null && var12 != Undefined.instance) {
               var9 = true;
               if (var12 instanceof String) {
                  if (var2) {
                     var6.append('"');
                     var6.append(ScriptRuntime.escapeString(ScriptRuntime.toString(var12)));
                     var6.append('"');
                  } else {
                     var6.append(ScriptRuntime.toString(var12));
                  }
               } else {
                  try {
                     var0.iterating.put(var1, Boolean.TRUE);
                     if (var3 && var12 != Undefined.instance && var12 != null) {
                        Scriptable var15 = Context.toObject(var12, var1);
                        Object var16 = ScriptRuntime.getProp(var15, "toLocaleString", var1);
                        var12 = ScriptRuntime.call(var0, var16, var12, ScriptRuntime.emptyArgs);
                     }

                     var6.append(ScriptRuntime.toString(var12));
                  } finally {
                     var0.iterating.remove(var1);
                  }
               }
            } else {
               var9 = false;
            }
         }
      }

      if (var2) {
         if (!var9 && var10 > 0L) {
            var6.append(", ]");
         } else {
            var6.append(']');
         }
      }

      return var6.toString();
   }
}
