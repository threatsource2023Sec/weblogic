package org.mozilla.javascript.optimizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Hashtable;
import org.mozilla.classfile.ClassFileWriter;
import org.mozilla.classfile.DefiningClassLoader;
import org.mozilla.javascript.Invoker;
import org.mozilla.javascript.VariableTable;

public class InvokerImpl extends Invoker {
   int classNumber;
   Hashtable invokersCache = new Hashtable();
   DefiningClassLoader classLoader = new DefiningClassLoader();

   public Invoker createInvoker(Method var1, Class[] var2) {
      Invoker var3 = (Invoker)this.invokersCache.get(var1);
      if (var3 != null) {
         return var3;
      } else {
         synchronized(this){}

         try {
            ++this.classNumber;
         } catch (Throwable var25) {
            throw var25;
         }

         String var4 = "inv" + this.classNumber;
         ClassFileWriter var5 = new ClassFileWriter(var4, "org.mozilla.javascript.Invoker", "");
         var5.setFlags((short)17);
         var5.startMethod("<init>", "()V", (short)1);
         var5.add((byte)42);
         var5.add((byte)-73, "org.mozilla.javascript.Invoker", "<init>", "()", "V");
         var5.add((byte)-79);
         var5.stopMethod((short)1, (VariableTable)null);
         var5.startMethod("invoke", "(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;", (short)17);
         String var6 = var1.getDeclaringClass().getName().replace('.', '/');
         Class var7 = var1.getReturnType();
         String var8 = null;
         String var9 = null;
         boolean var10 = false;
         boolean var11 = false;
         if (var7.isPrimitive()) {
            if (var7 == Boolean.TYPE) {
               var11 = true;
               var9 = "(Z)";
            } else if (var7 == Void.TYPE) {
               var10 = true;
               var9 = "(V)";
            } else if (var7 == Integer.TYPE) {
               var8 = "java/lang/Integer";
               var5.add((byte)-69, "java/lang/Integer");
               var5.add((byte)89);
               var9 = "(I)";
            } else if (var7 == Long.TYPE) {
               var8 = "java/lang/Long";
               var5.add((byte)-69, "java/lang/Long");
               var5.add((byte)89);
               var9 = "(J)";
            } else if (var7 == Short.TYPE) {
               var8 = "java/lang/Short";
               var5.add((byte)-69, "java/lang/Short");
               var5.add((byte)89);
               var9 = "(S)";
            } else if (var7 == Float.TYPE) {
               var8 = "java/lang/Float";
               var5.add((byte)-69, "java/lang/Float");
               var5.add((byte)89);
               var9 = "(F)";
            } else if (var7 == Double.TYPE) {
               var8 = "java/lang/Double";
               var5.add((byte)-69, "java/lang/Double");
               var5.add((byte)89);
               var9 = "(D)";
            } else if (var7 == Byte.TYPE) {
               var8 = "java/lang/Byte";
               var5.add((byte)-69, "java/lang/Byte");
               var5.add((byte)89);
               var9 = "(B)";
            } else if (var7 == Character.TYPE) {
               var8 = "java/lang/Character";
               var5.add((byte)-69, "java/lang/Character");
               var5.add((byte)89);
               var9 = "(C)";
            }
         }

         if (!Modifier.isStatic(var1.getModifiers())) {
            var5.add((byte)43);
            var5.add((byte)-64, var6);
         }

         StringBuffer var12 = new StringBuffer(2 + (var2 != null ? 20 * var2.length : 0));
         var12.append('(');
         if (var2 != null) {
            for(int var13 = 0; var13 < var2.length; ++var13) {
               Class var14 = var2[var13];
               var5.add((byte)44);
               if (var13 <= 5) {
                  var5.add((byte)(3 + var13));
               } else if (var13 <= 127) {
                  var5.add((byte)16, var13);
               } else if (var13 <= 32767) {
                  var5.add((byte)17, var13);
               } else {
                  var5.addLoadConstant(var13);
               }

               var5.add((byte)50);
               if (var14.isPrimitive()) {
                  if (var14 == Boolean.TYPE) {
                     var5.add((byte)-64, "java/lang/Boolean");
                     var5.add((byte)-74, "java/lang/Boolean", "booleanValue", "()", "Z");
                     var12.append('Z');
                  } else if (var14 == Integer.TYPE) {
                     var5.add((byte)-64, "java/lang/Number");
                     var5.add((byte)-74, "java/lang/Number", "intValue", "()", "I");
                     var12.append('I');
                  } else if (var14 == Short.TYPE) {
                     var5.add((byte)-64, "java/lang/Number");
                     var5.add((byte)-74, "java/lang/Number", "shortValue", "()", "S");
                     var12.append('S');
                  } else if (var14 == Character.TYPE) {
                     var5.add((byte)-64, "java/lang/Character");
                     var5.add((byte)-74, "java/lang/Character", "charValue", "()", "C");
                     var12.append('C');
                  } else if (var14 == Double.TYPE) {
                     var5.add((byte)-64, "java/lang/Number");
                     var5.add((byte)-74, "java/lang/Number", "doubleValue", "()", "D");
                     var12.append('D');
                  } else if (var14 == Float.TYPE) {
                     var5.add((byte)-64, "java/lang/Number");
                     var5.add((byte)-74, "java/lang/Number", "floatValue", "()", "F");
                     var12.append('F');
                  } else if (var14 == Byte.TYPE) {
                     var5.add((byte)-64, "java/lang/Byte");
                     var5.add((byte)-74, "java/lang/Byte", "byteValue", "()", "B");
                     var12.append('B');
                  }
               } else {
                  String var15 = var14.getName().replace('.', '/');
                  var5.add((byte)-64, var15);
                  if (!var14.isArray()) {
                     var12.append('L');
                  }

                  var12.append(var15);
                  if (!var14.isArray()) {
                     var12.append(';');
                  }
               }
            }
         }

         var12.append(')');
         if (!Modifier.isStatic(var1.getModifiers())) {
            var5.add((byte)-74, var6, var1.getName(), var12.toString(), var9 != null ? var9.substring(1, 2) : (var7.isArray() ? var7.getName().replace('.', '/') : "L".concat(var7.getName().replace('.', '/').concat(";"))));
         } else {
            var5.add((byte)-72, var6, var1.getName(), var12.toString(), var9 != null ? var9.substring(1, 2) : (var7.isArray() ? var7.getName().replace('.', '/') : "L".concat(var7.getName().replace('.', '/').concat(";"))));
         }

         if (var10) {
            var5.add((byte)1);
            var5.add((byte)-80);
         } else if (var11) {
            var5.add((byte)-103, 7);
            var5.add((byte)-78, "java/lang/Boolean", "TRUE", "Ljava/lang/Boolean;");
            var5.add((byte)-80);
            var5.add((byte)-78, "java/lang/Boolean", "FALSE", "Ljava/lang/Boolean;");
            var5.add((byte)-80);
         } else if (var8 != null) {
            var5.add((byte)-73, var8, "<init>", var9, "V");
            var5.add((byte)-80);
         } else {
            var5.add((byte)-80);
         }

         var5.stopMethod((short)3, (VariableTable)null);
         ByteArrayOutputStream var26 = new ByteArrayOutputStream(550);

         try {
            var5.write(var26);
         } catch (IOException var24) {
            throw new RuntimeException("unexpected IOException" + var24.toString());
         }

         try {
            byte[] var27 = var26.toByteArray();
            this.classLoader.defineClass(var4, var27);
            Class var28 = this.classLoader.loadClass(var4, true);
            var3 = (Invoker)var28.newInstance();
         } catch (ClassNotFoundException var21) {
            throw new RuntimeException("unexpected " + var21.toString());
         } catch (InstantiationException var22) {
            throw new RuntimeException("unexpected " + var22.toString());
         } catch (IllegalAccessException var23) {
            throw new RuntimeException("unexpected " + var23.toString());
         }

         this.invokersCache.put(var1, var3);
         return var3;
      }
   }

   public Object invoke(Object var1, Object[] var2) {
      return null;
   }
}
