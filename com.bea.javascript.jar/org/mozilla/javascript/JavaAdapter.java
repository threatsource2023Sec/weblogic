package org.mozilla.javascript;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import org.mozilla.classfile.ClassFileWriter;
import org.mozilla.classfile.DefiningClassLoader;

public class JavaAdapter extends ScriptableObject {
   private static int serial;
   private static DefiningClassLoader classLoader;
   private static Hashtable generatedClasses = new Hashtable(7);
   // $FF: synthetic field
   static Class class$java$lang$Object;
   // $FF: synthetic field
   static Class class$org$mozilla$javascript$Scriptable;

   private static StringBuffer appendTypeString(StringBuffer var0, Class var1) {
      while(var1.isArray()) {
         var0.append('[');
         var1 = var1.getComponentType();
      }

      if (var1.isPrimitive()) {
         if (var1.equals(Boolean.TYPE)) {
            var0.append('Z');
         } else if (var1.equals(Long.TYPE)) {
            var0.append('J');
         } else {
            String var2 = var1.getName();
            var0.append(Character.toUpperCase(var2.charAt(0)));
         }
      } else {
         var0.append('L');
         var0.append(var1.getName().replace('.', '/'));
         var0.append(';');
      }

      return var0;
   }

   public static Object callMethod(Scriptable var0, Object var1, String var2, Object[] var3) {
      Scriptable var4;
      try {
         Context var7 = Context.enter();
         Object var8 = ScriptableObject.getProperty(var0, var2);
         if (var8 != Scriptable.NOT_FOUND) {
            Object var13 = ScriptRuntime.call(var7, var8, var1, var3, var0);
            return var13;
         }

         var4 = Undefined.instance;
      } catch (JavaScriptException var11) {
         throw WrappedException.wrapException(var11);
      } finally {
         Context.exit();
      }

      return var4;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public static Object convertResult(Object var0, String var1) throws ClassNotFoundException {
      Class var2 = ScriptRuntime.loadClassName(var1);
      return var0 == Undefined.instance && var2 != ScriptRuntime.ObjectClass && var2 != ScriptRuntime.StringClass ? null : NativeJavaObject.coerceType(var2, var0);
   }

   public static Class createAdapterClass(Context var0, Scriptable var1, String var2, Class var3, Class[] var4, String var5, ClassNameHelper var6) throws ClassNotFoundException {
      ClassFileWriter var7 = new ClassFileWriter(var2, var3.getName(), "<adapter>");
      var7.addField("delegee", "Lorg/mozilla/javascript/Scriptable;", (short)17);
      var7.addField("self", "Lorg/mozilla/javascript/Scriptable;", (short)17);
      int var8 = var4 == null ? 0 : var4.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         if (var4[var9] != null) {
            var7.addInterface(var4[var9].getName());
         }
      }

      String var10 = var3.getName().replace('.', '/');
      generateCtor(var7, var2, var10);
      if (var5 != null) {
         generateEmptyCtor(var7, var2, var10, var5);
      }

      Hashtable var11 = new Hashtable();
      Hashtable var12 = new Hashtable();

      Method[] var14;
      int var15;
      Method var16;
      int var17;
      String var19;
      for(int var13 = 0; var13 < var8; ++var13) {
         var14 = var4[var13].getMethods();

         for(var15 = 0; var15 < var14.length; ++var15) {
            var16 = var14[var15];
            var17 = var16.getModifiers();
            if (!Modifier.isStatic(var17) && !Modifier.isFinal(var17) && var1 != null) {
               if (!ScriptableObject.hasProperty(var1, var16.getName())) {
                  try {
                     var3.getMethod(var16.getName(), var16.getParameterTypes());
                     continue;
                  } catch (NoSuchMethodException var27) {
                  }
               }

               String var18 = var16.getName();
               var19 = var18 + getMethodSignature(var16);
               if (!var11.containsKey(var19)) {
                  generateMethod(var7, var2, var18, var16.getParameterTypes(), var16.getReturnType());
                  var11.put(var19, Boolean.TRUE);
                  var12.put(var18, Boolean.TRUE);
               }
            }
         }
      }

      var14 = var3.getMethods();

      String var20;
      for(var15 = 0; var15 < var14.length; ++var15) {
         var16 = var14[var15];
         var17 = var16.getModifiers();
         if (!Modifier.isStatic(var17) && !Modifier.isFinal(var17)) {
            boolean var31 = Modifier.isAbstract(var17);
            if (var31 || var1 != null && ScriptableObject.hasProperty(var1, var16.getName())) {
               var19 = var16.getName();
               var20 = getMethodSignature(var16);
               String var21 = var19 + var20;
               if (!var11.containsKey(var21)) {
                  generateMethod(var7, var2, var19, var16.getParameterTypes(), var16.getReturnType());
                  var11.put(var21, Boolean.TRUE);
                  var12.put(var19, Boolean.TRUE);
               }

               if (!var31) {
                  generateSuper(var7, var2, var10, var19, var20, var16.getParameterTypes(), var16.getReturnType());
               }
            }
         }
      }

      Object var36;
      for(Scriptable var28 = var1; var28 != null; var28 = var28.getPrototype()) {
         Object[] var29 = var1.getIds();

         for(int var32 = 0; var32 < var29.length; ++var32) {
            if (var29[var32] instanceof String) {
               var19 = (String)var29[var32];
               if (!var12.containsKey(var19)) {
                  var36 = var28.get(var19, var28);
                  int var39;
                  if (var36 instanceof Scriptable) {
                     Scriptable var22 = (Scriptable)var36;
                     if (!(var22 instanceof Function)) {
                        continue;
                     }

                     var39 = (int)Context.toNumber(ScriptableObject.getProperty(var22, "length"));
                  } else {
                     if (!(var36 instanceof FunctionNode)) {
                        continue;
                     }

                     var39 = ((FunctionNode)var36).getVariableTable().getParameterCount();
                  }

                  Class[] var41 = new Class[var39];

                  for(int var23 = 0; var23 < var39; ++var23) {
                     var41[var23] = class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object"));
                  }

                  generateMethod(var7, var2, var19, var41, class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object")));
               }
            }
         }
      }

      ByteArrayOutputStream var30 = new ByteArrayOutputStream(512);

      try {
         var7.write(var30);
      } catch (IOException var26) {
         throw new RuntimeException("unexpected IOException");
      }

      byte[] var33 = var30.toByteArray();
      if (var6 != null) {
         if (var6.getGeneratingDirectory() != null) {
            try {
               int var37 = var2.lastIndexOf(46);
               if (var37 != -1) {
                  var2 = var2.substring(var37 + 1);
               }

               var20 = var6.getTargetClassFileName(var2);
               FileOutputStream var42 = new FileOutputStream(var20);
               var42.write(var33);
               var42.close();
               return null;
            } catch (IOException var24) {
               throw WrappedException.wrapException(var24);
            }
         }

         try {
            ClassOutput var34 = var6.getClassOutput();
            if (var34 != null) {
               OutputStream var38 = var34.getOutputStream(var2, true);
               var38.write(var33);
               var38.close();
            }
         } catch (IOException var25) {
            throw WrappedException.wrapException(var25);
         }
      }

      SecuritySupport var35 = var0.getSecuritySupport();
      if (var35 != null) {
         var36 = var0.getSecurityDomainForStackDepth(-1);
         Class var40 = var35.defineClass(var2, var33, var36);
         if (var40 != null) {
            return var40;
         }
      }

      if (classLoader == null) {
         classLoader = new DefiningClassLoader();
      }

      classLoader.defineClass(var2, var33);
      return classLoader.loadClass(var2, true);
   }

   public boolean equals(Object var1) {
      return super.equals(var1);
   }

   private static void generateCtor(ClassFileWriter var0, String var1, String var2) {
      var0.startMethod("<init>", "(Lorg/mozilla/javascript/Scriptable;)V", (short)1);
      var0.add((byte)42);
      var0.add((byte)-73, var2, "<init>", "()", "V");
      var0.add((byte)42);
      var0.add((byte)43);
      var0.add((byte)-75, var1, "delegee", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)43);
      var0.add((byte)42);
      var0.add((byte)-72, "org/mozilla/javascript/JavaAdapter", "setAdapterProto", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;)", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)76);
      var0.add((byte)42);
      var0.add((byte)43);
      var0.add((byte)-75, var1, "self", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)-79);
      var0.stopMethod((short)20, (VariableTable)null);
   }

   private static void generateEmptyCtor(ClassFileWriter var0, String var1, String var2, String var3) {
      var0.startMethod("<init>", "()V", (short)1);
      var0.add((byte)42);
      var0.add((byte)-73, var2, "<init>", "()", "V");
      var0.add((byte)-69, var3);
      var0.add((byte)89);
      var0.add((byte)-73, var3, "<init>", "()", "V");
      var0.add((byte)-72, "org/mozilla/javascript/ScriptRuntime", "runScript", "(Lorg/mozilla/javascript/Script;)", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)76);
      var0.add((byte)42);
      var0.add((byte)43);
      var0.add((byte)-75, var1, "delegee", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)43);
      var0.add((byte)42);
      var0.add((byte)-72, "org/mozilla/javascript/JavaAdapter", "setAdapterProto", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;)", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)76);
      var0.add((byte)42);
      var0.add((byte)43);
      var0.add((byte)-75, var1, "self", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)-79);
      var0.stopMethod((short)20, (VariableTable)null);
   }

   private static void generateMethod(ClassFileWriter var0, String var1, String var2, Class[] var3, Class var4) {
      StringBuffer var5 = new StringBuffer();
      var5.append('(');
      short var6 = 1;

      for(int var7 = 0; var7 < var3.length; ++var7) {
         Class var8 = var3[var7];
         appendTypeString(var5, var8);
         if (!var8.equals(Long.TYPE) && !var8.equals(Double.TYPE)) {
            ++var6;
         } else {
            var6 = (short)(var6 + 2);
         }
      }

      var5.append(')');
      appendTypeString(var5, var4);
      String var13 = var5.toString();
      var0.startMethod(var2, var13, (short)1);
      var0.add((byte)16, (byte)var3.length);
      var0.add((byte)-67, "java/lang/Object");
      var0.add((byte)58, var6);
      short var9 = (short)(var6 + 1);
      boolean var10 = false;
      int var11 = 1;

      for(int var12 = 0; var12 < var3.length; ++var12) {
         var0.add((byte)25, var6);
         var0.add((byte)16, var12);
         if (var3[var12].isPrimitive()) {
            var11 = generateWrapParam(var0, var11, var3[var12]);
         } else {
            var0.add((byte)25, var11++);
            if (!var10) {
               var0.add((byte)42);
               var0.add((byte)-76, var1, "delegee", "Lorg/mozilla/javascript/Scriptable;");
               var0.add((byte)58, var9);
               var10 = true;
            }

            var0.add((byte)25, var9);
            var0.addLoadConstant(var3[var12].getName());
            var0.add((byte)-72, "org/mozilla/javascript/ScriptRuntime", "loadClassName", "(Ljava/lang/String;)", "Ljava/lang/Class;");
            var0.add((byte)-72, "org/mozilla/javascript/JavaAdapter", "toObject", "(Ljava/lang/Object;Lorg/mozilla/javascript/Scriptable;Ljava/lang/Class;)", "Lorg/mozilla/javascript/Scriptable;");
         }

         var0.add((byte)83);
      }

      var0.add((byte)42);
      var0.add((byte)-76, var1, "delegee", "Lorg/mozilla/javascript/Scriptable;");
      var0.add((byte)42);
      var0.add((byte)-76, var1, "self", "Lorg/mozilla/javascript/Scriptable;");
      var0.addLoadConstant(var2);
      var0.add((byte)25, var6);
      var0.add((byte)-72, "org/mozilla/javascript/JavaAdapter", "callMethod", "(Lorg/mozilla/javascript/Scriptable;Ljava/lang/Object;Ljava/lang/String;[Ljava/lang/Object;)", "Ljava/lang/Object;");
      if (var4.equals(Void.TYPE)) {
         var0.add((byte)87);
         var0.add((byte)-79);
      } else {
         generateReturnResult(var0, var4);
      }

      var0.stopMethod((short)(var9 + 3), (VariableTable)null);
   }

   private static void generatePopResult(ClassFileWriter var0, Class var1) {
      if (var1.isPrimitive()) {
         String var2 = var1.getName();
         switch (var2.charAt(0)) {
            case 'b':
            case 'c':
            case 'i':
            case 's':
            case 'z':
               var0.add((byte)-84);
               break;
            case 'd':
               var0.add((byte)-81);
               break;
            case 'f':
               var0.add((byte)-82);
               break;
            case 'l':
               var0.add((byte)-83);
         }
      } else {
         var0.add((byte)-80);
      }

   }

   private static int generatePushParam(ClassFileWriter var0, int var1, Class var2) {
      String var3 = var2.getName();
      switch (var3.charAt(0)) {
         case 'b':
         case 'c':
         case 'i':
         case 's':
         case 'z':
            var0.add((byte)21, var1++);
            break;
         case 'd':
            var0.add((byte)24, var1);
            var1 += 2;
            break;
         case 'f':
            var0.add((byte)23, var1++);
            break;
         case 'l':
            var0.add((byte)22, var1);
            var1 += 2;
      }

      return var1;
   }

   private static void generateReturnResult(ClassFileWriter var0, Class var1) {
      if (var1.equals(Boolean.TYPE)) {
         var0.add((byte)-72, "org/mozilla/javascript/Context", "toBoolean", "(Ljava/lang/Object;)", "Z");
         var0.add((byte)-84);
      } else if (var1.equals(Character.TYPE)) {
         var0.add((byte)-72, "org/mozilla/javascript/Context", "toString", "(Ljava/lang/Object;)", "Ljava/lang/String;");
         var0.add((byte)3);
         var0.add((byte)-74, "java/lang/String", "charAt", "(I)", "C");
         var0.add((byte)-84);
      } else {
         String var2;
         if (var1.isPrimitive()) {
            var0.add((byte)-72, "org/mozilla/javascript/Context", "toNumber", "(Ljava/lang/Object;)", "D");
            var2 = var1.getName();
            switch (var2.charAt(0)) {
               case 'b':
               case 'i':
               case 's':
                  var0.add((byte)-114);
                  var0.add((byte)-84);
                  break;
               case 'c':
               case 'e':
               case 'g':
               case 'h':
               case 'j':
               case 'k':
               case 'm':
               case 'n':
               case 'o':
               case 'p':
               case 'q':
               case 'r':
               default:
                  throw new RuntimeException("Unexpected return type " + var1.toString());
               case 'd':
                  var0.add((byte)-81);
                  break;
               case 'f':
                  var0.add((byte)-112);
                  var0.add((byte)-82);
                  break;
               case 'l':
                  var0.add((byte)-113);
                  var0.add((byte)-83);
            }
         } else {
            var2 = var1.getName();
            var0.addLoadConstant(var2);
            var0.add((byte)-72, "org/mozilla/javascript/JavaAdapter", "convertResult", "(Ljava/lang/Object;Ljava/lang/String;)", "Ljava/lang/Object;");
            var0.add((byte)-64, var2.replace('.', '/'));
            var0.add((byte)-80);
         }
      }

   }

   private static void generateSuper(ClassFileWriter var0, String var1, String var2, String var3, String var4, Class[] var5, Class var6) {
      var0.startMethod("super$" + var3, var4, (short)1);
      var0.add((byte)25, 0);
      int var7 = 1;

      for(int var8 = 0; var8 < var5.length; ++var8) {
         if (var5[var8].isPrimitive()) {
            var7 = generatePushParam(var0, var7, var5[var8]);
         } else {
            var0.add((byte)25, var7++);
         }
      }

      int var9 = var4.indexOf(41);
      var0.add((byte)-73, var2, var3, var4.substring(0, var9 + 1), var4.substring(var9 + 1));
      if (!var6.equals(Void.TYPE)) {
         generatePopResult(var0, var6);
      } else {
         var0.add((byte)-79);
      }

      var0.stopMethod((short)(var7 + 1), (VariableTable)null);
   }

   private static int generateWrapParam(ClassFileWriter var0, int var1, Class var2) {
      if (var2.equals(Boolean.TYPE)) {
         var0.add((byte)-69, "java/lang/Boolean");
         var0.add((byte)89);
         var0.add((byte)21, var1++);
         var0.add((byte)-73, "java/lang/Boolean", "<init>", "(Z)", "V");
      } else if (var2.equals(Character.TYPE)) {
         var0.add((byte)-69, "java/lang/String");
         var0.add((byte)89);
         var0.add((byte)4);
         var0.add((byte)-68, 5);
         var0.add((byte)89);
         var0.add((byte)3);
         var0.add((byte)21, var1++);
         var0.add((byte)85);
         var0.add((byte)-73, "java/lang/String", "<init>", "([C)", "V");
      } else {
         var0.add((byte)-69, "java/lang/Double");
         var0.add((byte)89);
         String var3 = var2.getName();
         switch (var3.charAt(0)) {
            case 'b':
            case 'i':
            case 's':
               var0.add((byte)21, var1++);
               var0.add((byte)-121);
            case 'c':
            case 'e':
            case 'g':
            case 'h':
            case 'j':
            case 'k':
            case 'm':
            case 'n':
            case 'o':
            case 'p':
            case 'q':
            case 'r':
            default:
               break;
            case 'd':
               var0.add((byte)24, var1);
               var1 += 2;
               break;
            case 'f':
               var0.add((byte)23, var1++);
               var0.add((byte)-115);
               break;
            case 'l':
               var0.add((byte)22, var1);
               var0.add((byte)-118);
               var1 += 2;
         }

         var0.add((byte)-73, "java/lang/Double", "<init>", "(D)", "V");
      }

      return var1;
   }

   public static Object getAdapterSelf(Class var0, Object var1) throws NoSuchFieldException, IllegalAccessException {
      Field var2 = var0.getDeclaredField("self");
      return var2.get(var1);
   }

   public String getClassName() {
      return "JavaAdapter";
   }

   private static String getMethodSignature(Method var0) {
      Class[] var1 = var0.getParameterTypes();
      StringBuffer var2 = new StringBuffer();
      var2.append('(');

      for(int var3 = 0; var3 < var1.length; ++var3) {
         Class var4 = var1[var3];
         appendTypeString(var2, var4);
      }

      var2.append(')');
      appendTypeString(var2, var0.getReturnType());
      return var2.toString();
   }

   public static Object jsConstructor(Context var0, Object[] var1, Function var2, boolean var3) throws InstantiationException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
      Class var4 = null;
      Class[] var5 = new Class[var1.length - 1];
      int var6 = 0;

      for(int var7 = 0; var7 < var1.length - 1; ++var7) {
         if (!(var1[var7] instanceof NativeJavaClass)) {
            throw NativeGlobal.constructError(var0, "TypeError", "expected java class object", var2);
         }

         Class var8 = ((NativeJavaClass)var1[var7]).getClassObject();
         if (!var8.isInterface()) {
            if (var4 != null) {
               String var9 = "Only one class may be extended by a JavaAdapter. Had " + var4.getName() + " and " + var8.getName();
               throw NativeGlobal.constructError(var0, "TypeError", var9, var2);
            }

            var4 = var8;
         } else {
            var5[var6++] = var8;
         }
      }

      if (var4 == null) {
         var4 = class$java$lang$Object != null ? class$java$lang$Object : (class$java$lang$Object = class$("java.lang.Object"));
      }

      Class[] var15 = new Class[var6];
      System.arraycopy(var5, 0, var15, 0, var6);
      Scriptable var16 = (Scriptable)var1[var1.length - 1];
      ClassSignature var10 = new ClassSignature(var4, var15, var16);
      Class var11 = (Class)generatedClasses.get(var10);
      if (var11 == null) {
         String var12 = "adapter" + serial++;
         var11 = createAdapterClass(var0, var16, var12, var4, var15, (String)null, (ClassNameHelper)null);
         generatedClasses.put(var10, var11);
      }

      Class[] var17 = new Class[]{class$org$mozilla$javascript$Scriptable != null ? class$org$mozilla$javascript$Scriptable : (class$org$mozilla$javascript$Scriptable = class$("org.mozilla.javascript.Scriptable"))};
      Object[] var13 = new Object[]{var16};
      Object var14 = var11.getConstructor(var17).newInstance(var13);
      return getAdapterSelf(var11, var14);
   }

   public static Scriptable setAdapterProto(Scriptable var0, Object var1) {
      Scriptable var2 = ScriptRuntime.toObject(ScriptableObject.getTopLevelScope(var0), var1);
      var2.setPrototype(var0);
      return var2;
   }

   public static Scriptable toObject(Object var0, Scriptable var1, Class var2) {
      Context.enter();

      Scriptable var3;
      try {
         var3 = Context.toObject(var0, var1, var2);
      } finally {
         Context.exit();
      }

      return var3;
   }

   static class ClassSignature {
      Class mSuperClass;
      Class[] mInterfaces;
      Object[] mProperties;

      ClassSignature(Class var1, Class[] var2, Scriptable var3) {
         this.mSuperClass = var1;
         this.mInterfaces = var2;
         this.mProperties = ScriptableObject.getPropertyIds(var3);
      }

      public boolean equals(Object var1) {
         if (var1 instanceof ClassSignature) {
            ClassSignature var2 = (ClassSignature)var1;
            if (this.mSuperClass == var2.mSuperClass) {
               Class[] var3 = var2.mInterfaces;
               int var4;
               if (this.mInterfaces != var3) {
                  if (this.mInterfaces == null || var3 == null) {
                     return false;
                  }

                  if (this.mInterfaces.length != var3.length) {
                     return false;
                  }

                  for(var4 = 0; var4 < var3.length; ++var4) {
                     if (this.mInterfaces[var4] != var3[var4]) {
                        return false;
                     }
                  }
               }

               if (this.mProperties.length != var2.mProperties.length) {
                  return false;
               }

               for(var4 = 0; var4 < this.mProperties.length; ++var4) {
                  if (!this.mProperties[var4].equals(var2.mProperties[var4])) {
                     return false;
                  }
               }

               return true;
            }
         }

         return false;
      }

      public int hashCode() {
         return this.mSuperClass.hashCode();
      }
   }
}
