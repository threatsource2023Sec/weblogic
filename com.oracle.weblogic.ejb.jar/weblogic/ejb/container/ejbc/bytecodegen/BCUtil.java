package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.ejb.container.interfaces.Invokable;
import weblogic.ejb.container.interfaces.WLLocalEJBObject;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.MethodDescriptor;
import weblogic.ejb.container.utils.Serializer;
import weblogic.kernel.KernelStatus;

final class BCUtil {
   public static final String METHOD_FIELD_DESC = fieldDesc(Method.class);
   public static final String WL_INVOKABLE_CLS = binName(Invokable.class);
   public static final String WL_INVOCATION_WRAPPER_CLS = binName(InvocationWrapper.class);
   public static final String WL_MD_FIELD_DESCRIPTOR = fieldDesc(MethodDescriptor.class);
   public static final String WL_EJB_LOCAL_OBJECT_CLS = binName(WLLocalEJBObject.class);
   public static final String INV_WRAP_NEW_INSTANCE_METHOD_DESC = "(Lweblogic/ejb/container/internal/MethodDescriptor;)Lweblogic/ejb/container/internal/InvocationWrapper;";
   public static final String INV_WRAP_NEW_INSTANCE_PK_METHOD_DESC = "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)Lweblogic/ejb/container/internal/InvocationWrapper;";
   public static final int PFS_CLS = 49;
   private static final int DEFAULT_CHAINING_THRESHOLD = 100;
   private static final String CHAINING_THRESHOLD_PROPERTY = "weblogic.ejb.bytecodegen.methodchainingthreshold";
   private static final int METHOD_CHAINING_THRESHOLD = KernelStatus.isApplet() ? 100 : Integer.getInteger("weblogic.ejb.bytecodegen.methodchainingthreshold", 100);
   private static final String INVOKE_METHOD_DESC = "(Ljava/lang/Object;[Ljava/lang/Object;I)Ljava/lang/Object;";
   private static final String HANDLE_EX_METHOD_DESC = "(ILjava/lang/Throwable;)V";

   private BCUtil() {
   }

   public static void boxReturn(MethodVisitor mv, Class returnType) {
      if (returnType.isPrimitive()) {
         if (returnType == Void.TYPE) {
            mv.visitInsn(1);
         } else if (returnType == Boolean.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
         } else if (returnType == Byte.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
         } else if (returnType == Character.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
         } else if (returnType == Double.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
         } else if (returnType == Float.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
         } else if (returnType == Integer.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
         } else if (returnType == Long.TYPE) {
            mv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
         } else {
            if (returnType != Short.TYPE) {
               throw new AssertionError("Unknown primitive type : " + returnType);
            }

            mv.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
         }
      }

   }

   public static void unboxReturn(MethodVisitor mv, Class returnType) {
      if (!returnType.isPrimitive()) {
         mv.visitTypeInsn(192, binName(returnType));
         mv.visitInsn(176);
      } else if (returnType == Void.TYPE) {
         mv.visitInsn(87);
         mv.visitInsn(177);
      } else if (returnType == Boolean.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Boolean");
         mv.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
         mv.visitInsn(172);
      } else if (returnType == Byte.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Byte");
         mv.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B", false);
         mv.visitInsn(172);
      } else if (returnType == Character.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Character");
         mv.visitMethodInsn(182, "java/lang/Character", "charValue", "()C", false);
         mv.visitInsn(172);
      } else if (returnType == Double.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Double");
         mv.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D", false);
         mv.visitInsn(175);
      } else if (returnType == Float.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Float");
         mv.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F", false);
         mv.visitInsn(174);
      } else if (returnType == Integer.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Integer");
         mv.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
         mv.visitInsn(172);
      } else if (returnType == Long.TYPE) {
         mv.visitTypeInsn(192, "java/lang/Long");
         mv.visitMethodInsn(182, "java/lang/Long", "longValue", "()J", false);
         mv.visitInsn(173);
      } else {
         if (returnType != Short.TYPE) {
            throw new AssertionError("Unknown primitive type : " + returnType);
         }

         mv.visitTypeInsn(192, "java/lang/Short");
         mv.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S", false);
         mv.visitInsn(172);
      }

   }

   public static void boxArgs(MethodVisitor mv, Method method) {
      int argsIndex = Modifier.isStatic(method.getModifiers()) ? 0 : 1;
      boxArgs(mv, method, argsIndex);
   }

   public static void boxArgs(MethodVisitor mv, Method method, int localsOffset) {
      Class[] paramTypes = method.getParameterTypes();
      if (paramTypes.length == 0) {
         mv.visitInsn(1);
      } else {
         pushInsn(mv, paramTypes.length);
         mv.visitTypeInsn(189, "java/lang/Object");
         int curLocalIndex = localsOffset;

         for(int i = 0; i < paramTypes.length; ++i) {
            mv.visitInsn(89);
            pushInsn(mv, i);
            Class curParam = paramTypes[i];
            if (!curParam.isPrimitive()) {
               mv.visitVarInsn(25, curLocalIndex);
            } else if (curParam == Boolean.TYPE) {
               mv.visitVarInsn(21, curLocalIndex);
               mv.visitMethodInsn(184, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
            } else if (curParam == Byte.TYPE) {
               mv.visitVarInsn(21, curLocalIndex);
               mv.visitMethodInsn(184, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
            } else if (curParam == Character.TYPE) {
               mv.visitVarInsn(21, curLocalIndex);
               mv.visitMethodInsn(184, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
            } else if (curParam == Double.TYPE) {
               mv.visitVarInsn(24, curLocalIndex);
               ++curLocalIndex;
               mv.visitMethodInsn(184, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
            } else if (curParam == Float.TYPE) {
               mv.visitVarInsn(23, curLocalIndex);
               mv.visitMethodInsn(184, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
            } else if (curParam == Integer.TYPE) {
               mv.visitVarInsn(21, curLocalIndex);
               mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            } else if (curParam == Long.TYPE) {
               mv.visitVarInsn(22, curLocalIndex);
               ++curLocalIndex;
               mv.visitMethodInsn(184, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
            } else {
               if (curParam != Short.TYPE) {
                  throw new AssertionError("Unknown primitive type : " + curParam);
               }

               mv.visitVarInsn(21, curLocalIndex);
               mv.visitMethodInsn(184, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
            }

            ++curLocalIndex;
            mv.visitInsn(83);
         }

      }
   }

   public static void loadArgs(MethodVisitor mv, Method method) {
      loadArgs(mv, method.getParameterTypes(), Modifier.isStatic(method.getModifiers()));
   }

   public static void loadArgs(MethodVisitor mv, Class[] paramTypes, boolean isStatic) {
      int idx = isStatic ? 0 : 1;
      Class[] var4 = paramTypes;
      int var5 = paramTypes.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class c = var4[var6];
         if (!c.isPrimitive()) {
            mv.visitVarInsn(25, idx++);
         } else if (c == Double.TYPE) {
            mv.visitVarInsn(24, idx);
            idx += 2;
         } else if (c == Long.TYPE) {
            mv.visitVarInsn(22, idx);
            idx += 2;
         } else if (c == Float.TYPE) {
            mv.visitVarInsn(23, idx++);
         } else {
            mv.visitVarInsn(21, idx++);
         }
      }

   }

   private static void unboxArgs(MethodVisitor mv, Method method) {
      Class[] paramTypes = method.getParameterTypes();
      if (paramTypes.length != 0) {
         for(int i = 0; i < paramTypes.length; ++i) {
            mv.visitVarInsn(25, 2);
            pushInsn(mv, i);
            mv.visitInsn(50);
            if (!paramTypes[i].isPrimitive()) {
               mv.visitTypeInsn(192, binName(paramTypes[i]));
            } else {
               Class curParam = paramTypes[i];
               if (curParam == Boolean.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Boolean");
                  mv.visitMethodInsn(182, "java/lang/Boolean", "booleanValue", "()Z", false);
               } else if (curParam == Byte.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Byte");
                  mv.visitMethodInsn(182, "java/lang/Byte", "byteValue", "()B", false);
               } else if (curParam == Character.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Character");
                  mv.visitMethodInsn(182, "java/lang/Character", "charValue", "()C", false);
               } else if (curParam == Double.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Double");
                  mv.visitMethodInsn(182, "java/lang/Double", "doubleValue", "()D", false);
               } else if (curParam == Float.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Float");
                  mv.visitMethodInsn(182, "java/lang/Float", "floatValue", "()F", false);
               } else if (curParam == Integer.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Integer");
                  mv.visitMethodInsn(182, "java/lang/Integer", "intValue", "()I", false);
               } else if (curParam == Long.TYPE) {
                  mv.visitTypeInsn(192, "java/lang/Long");
                  mv.visitMethodInsn(182, "java/lang/Long", "longValue", "()J", false);
               } else {
                  if (curParam != Short.TYPE) {
                     throw new AssertionError("Unknown primitive type : " + curParam);
                  }

                  mv.visitTypeInsn(192, "java/lang/Short");
                  mv.visitMethodInsn(182, "java/lang/Short", "shortValue", "()S", false);
               }
            }
         }
      }

   }

   public static String getBoxedClassBinName(Class c) {
      if (!c.isPrimitive()) {
         throw new AssertionError("Invoked for a NON primitive class : " + c);
      } else if (c == Void.TYPE) {
         return "java/lang/Void";
      } else if (c == Boolean.TYPE) {
         return "java/lang/Boolean";
      } else if (c == Byte.TYPE) {
         return "java/lang/Byte";
      } else if (c == Character.TYPE) {
         return "java/lang/Character";
      } else if (c == Double.TYPE) {
         return "java/lang/Double";
      } else if (c == Float.TYPE) {
         return "java/lang/Float";
      } else if (c == Integer.TYPE) {
         return "java/lang/Integer";
      } else if (c == Long.TYPE) {
         return "java/lang/Long";
      } else if (c == Short.TYPE) {
         return "java/lang/Short";
      } else {
         throw new AssertionError("Unknown primitive type : " + c);
      }
   }

   public static void addReturnDefaultValue(MethodVisitor mv, Class retType) {
      if (!retType.isPrimitive()) {
         mv.visitInsn(1);
         mv.visitInsn(176);
      } else if (retType == Void.TYPE) {
         mv.visitInsn(177);
      } else if (retType != Boolean.TYPE && retType != Byte.TYPE && retType != Character.TYPE && retType != Integer.TYPE && retType != Short.TYPE) {
         if (retType == Double.TYPE) {
            mv.visitInsn(14);
            mv.visitInsn(175);
         } else if (retType == Float.TYPE) {
            mv.visitInsn(11);
            mv.visitInsn(174);
         } else {
            if (retType != Long.TYPE) {
               throw new AssertionError("Unknown primitive type : " + retType);
            }

            mv.visitInsn(9);
            mv.visitInsn(173);
         }
      } else {
         mv.visitInsn(3);
         mv.visitInsn(172);
      }

   }

   public static void addInvoke(ClassWriter cw, Method[] methods, String targetCls, String thisCls) {
      if (methods != null && methods.length != 0) {
         chainedInvokeAdder(cw, methods, targetCls, thisCls, 0);
      }
   }

   private static void chainedInvokeAdder(ClassWriter cw, Method[] methods, String targetCls, String thisCls, int startIdx) {
      int methodIdx = startIdx / METHOD_CHAINING_THRESHOLD;
      String name = "__WL_invoke" + (methodIdx == 0 ? "" : "_" + methodIdx);
      int endIdx = Math.min(startIdx + METHOD_CHAINING_THRESHOLD, methods.length);
      MethodVisitor mv = cw.visitMethod(methodIdx == 0 ? 1 : 2, name, "(Ljava/lang/Object;[Ljava/lang/Object;I)Ljava/lang/Object;", (String)null, new String[]{"java/lang/Throwable"});
      mv.visitCode();
      Label[] caseLabels = new Label[endIdx - startIdx];

      for(int i = 0; i < caseLabels.length; ++i) {
         caseLabels[i] = new Label();
      }

      Label defaultLabel = new Label();
      mv.visitVarInsn(21, 3);
      mv.visitTableSwitchInsn(startIdx, endIdx - 1, defaultLabel, caseLabels);

      for(int i = startIdx; i < endIdx; ++i) {
         Method m = methods[i];
         mv.visitLabel(caseLabels[i - startIdx]);
         mv.visitVarInsn(25, 1);
         mv.visitTypeInsn(192, targetCls);
         unboxArgs(mv, m);
         mv.visitMethodInsn(185, targetCls, m.getName(), methodDesc(m), true);
         boxReturn(mv, m.getReturnType());
         mv.visitInsn(176);
      }

      mv.visitLabel(defaultLabel);
      if (endIdx == methods.length) {
         mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
         mv.visitInsn(89);
         mv.visitTypeInsn(187, "java/lang/StringBuilder");
         mv.visitInsn(89);
         mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "()V", false);
         mv.visitLdcInsn("No method found for index : ");
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
         mv.visitVarInsn(21, 3);
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
         mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
         mv.visitInsn(191);
      } else {
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(25, 1);
         mv.visitVarInsn(25, 2);
         mv.visitVarInsn(21, 3);
         mv.visitMethodInsn(183, thisCls, "__WL_invoke_" + (methodIdx + 1), "(Ljava/lang/Object;[Ljava/lang/Object;I)Ljava/lang/Object;", false);
         mv.visitInsn(176);
      }

      mv.visitMaxs(0, 0);
      mv.visitEnd();
      if (endIdx != methods.length) {
         chainedInvokeAdder(cw, methods, targetCls, thisCls, endIdx);
      }

   }

   public static void addHandleException(ClassWriter cw, Method[] methods, String thisCls) {
      if (methods != null && methods.length != 0) {
         chainedHandleExceptionAdder(cw, methods, thisCls, 0);
      }
   }

   private static void chainedHandleExceptionAdder(ClassWriter cw, Method[] methods, String thisCls, int startIdx) {
      int methodIdx = startIdx / METHOD_CHAINING_THRESHOLD;
      String name = "__WL_handleException" + (methodIdx == 0 ? "" : "_" + methodIdx);
      int endIdx = Math.min(startIdx + METHOD_CHAINING_THRESHOLD, methods.length);
      MethodVisitor mv = cw.visitMethod(methodIdx == 0 ? 1 : 2, name, "(ILjava/lang/Throwable;)V", (String)null, new String[]{"java/lang/Throwable"});
      mv.visitCode();
      Label retLabel = new Label();
      Label[] exCaseLabels = new Label[endIdx - startIdx];

      for(int i = 0; i < exCaseLabels.length; ++i) {
         exCaseLabels[i] = new Label();
      }

      Label exDefaultLabel = new Label();
      mv.visitVarInsn(21, 1);
      mv.visitTableSwitchInsn(startIdx, endIdx - 1, exDefaultLabel, exCaseLabels);

      for(int i = startIdx; i < endIdx; ++i) {
         Method m = methods[i];
         mv.visitLabel(exCaseLabels[i - startIdx]);
         List exs = filterExs(m);
         if (exs.isEmpty()) {
            mv.visitJumpInsn(167, retLabel);
         } else {
            Label throwLabel = new Label();
            Iterator li = exs.iterator();

            while(li.hasNext()) {
               mv.visitVarInsn(25, 2);
               mv.visitTypeInsn(193, binName((Class)li.next()));
               if (li.hasNext()) {
                  mv.visitJumpInsn(154, throwLabel);
               } else {
                  mv.visitJumpInsn(153, retLabel);
                  mv.visitLabel(throwLabel);
                  mv.visitVarInsn(25, 2);
                  mv.visitInsn(191);
               }
            }
         }
      }

      mv.visitLabel(exDefaultLabel);
      if (endIdx == methods.length) {
         mv.visitTypeInsn(187, "java/lang/IllegalArgumentException");
         mv.visitInsn(89);
         mv.visitTypeInsn(187, "java/lang/StringBuilder");
         mv.visitInsn(89);
         mv.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "()V", false);
         mv.visitLdcInsn("No method found for index : ");
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
         mv.visitVarInsn(21, 1);
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;", false);
         mv.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
         mv.visitMethodInsn(183, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V", false);
         mv.visitInsn(191);
      } else {
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(21, 1);
         mv.visitVarInsn(25, 2);
         mv.visitMethodInsn(183, thisCls, "__WL_handleException_" + (methodIdx + 1), "(ILjava/lang/Throwable;)V", false);
      }

      mv.visitLabel(retLabel);
      mv.visitInsn(177);
      mv.visitMaxs(0, 0);
      if (endIdx != methods.length) {
         chainedHandleExceptionAdder(cw, methods, thisCls, endIdx);
      }

   }

   public static void addHandleExceptionAssertMethod(ClassWriter cw) {
      MethodVisitor mv = cw.visitMethod(1, "__WL_handleException", "(ILjava/lang/Throwable;)V", (String)null, new String[]{"java/lang/Throwable"});
      mv.visitCode();
      mv.visitTypeInsn(187, "java/lang/AssertionError");
      mv.visitInsn(89);
      mv.visitLdcInsn("This method should NOT get called");
      mv.visitMethodInsn(183, "java/lang/AssertionError", "<init>", "(Ljava/lang/Object;)V", false);
      mv.visitInsn(191);
      mv.visitMaxs(3, 3);
      mv.visitEnd();
   }

   public static String binName(Class cls) {
      return cls.getName().replace('.', '/');
   }

   public static String binName(String fqnClsName) {
      return fqnClsName.replace('.', '/');
   }

   public static String fieldDesc(Class c) {
      if (c.isPrimitive()) {
         if (c == Byte.TYPE) {
            return "B";
         } else if (c == Character.TYPE) {
            return "C";
         } else if (c == Double.TYPE) {
            return "D";
         } else if (c == Float.TYPE) {
            return "F";
         } else if (c == Integer.TYPE) {
            return "I";
         } else if (c == Long.TYPE) {
            return "J";
         } else if (c == Short.TYPE) {
            return "S";
         } else if (c == Boolean.TYPE) {
            return "Z";
         } else if (c == Void.TYPE) {
            return "V";
         } else {
            throw new AssertionError("Unknown primitive type : " + c);
         }
      } else {
         return c.isArray() ? binName(c) : "L" + binName(c) + ";";
      }
   }

   public static String methodDesc(Method m) {
      return methodDesc(m.getReturnType(), m.getParameterTypes());
   }

   public static String methodDesc(Class retType, Class... params) {
      StringBuilder sb = new StringBuilder("(");
      if (params != null) {
         Class[] var3 = params;
         int var4 = params.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class p = var3[var5];
            sb.append(fieldDesc(p));
         }
      }

      sb.append(")");
      sb.append(fieldDesc(retType));
      return sb.toString();
   }

   public static String[] exceptionsDesc(Class[] exs) {
      if (exs == null) {
         return new String[0];
      } else {
         String[] exDescs = new String[exs.length];

         for(int i = 0; i < exs.length; ++i) {
            exDescs[i] = binName(exs[i]);
         }

         return exDescs;
      }
   }

   public static int loadOpcode(Class c) {
      if (c == Void.TYPE) {
         throw new IllegalArgumentException("Invalid type " + c);
      } else if (!c.isPrimitive()) {
         return 25;
      } else if (c == Float.TYPE) {
         return 23;
      } else if (c == Double.TYPE) {
         return 24;
      } else {
         return c == Long.TYPE ? 22 : 21;
      }
   }

   public static int storeOpcode(Class c) {
      if (c == Void.TYPE) {
         throw new IllegalArgumentException("Invalid type " + c);
      } else if (!c.isPrimitive()) {
         return 58;
      } else if (c == Float.TYPE) {
         return 56;
      } else if (c == Double.TYPE) {
         return 57;
      } else {
         return c == Long.TYPE ? 55 : 54;
      }
   }

   public static int returnOpcode(Class retType) {
      if (!retType.isPrimitive()) {
         return 176;
      } else if (retType == Void.TYPE) {
         return 177;
      } else if (retType == Double.TYPE) {
         return 175;
      } else if (retType == Float.TYPE) {
         return 174;
      } else {
         return retType == Long.TYPE ? 173 : 172;
      }
   }

   public static void pushInsn(MethodVisitor mv, int val) {
      if (val < -1) {
         mv.visitLdcInsn(val);
      } else if (val <= 5) {
         switch (val) {
            case -1:
               mv.visitInsn(2);
               break;
            case 0:
               mv.visitInsn(3);
               break;
            case 1:
               mv.visitInsn(4);
               break;
            case 2:
               mv.visitInsn(5);
               break;
            case 3:
               mv.visitInsn(6);
               break;
            case 4:
               mv.visitInsn(7);
               break;
            case 5:
               mv.visitInsn(8);
         }
      } else if (val <= 127) {
         mv.visitIntInsn(16, val);
      } else if (val <= 32767) {
         mv.visitIntInsn(17, val);
      } else {
         mv.visitLdcInsn(val);
      }

   }

   public static int sizeOf(Class... types) {
      if (types == null) {
         return 0;
      } else {
         int count = 0;
         Class[] var2 = types;
         int var3 = types.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class type = var2[var4];
            if (type != Long.TYPE && type != Double.TYPE) {
               ++count;
            } else {
               count += 2;
            }
         }

         return count;
      }
   }

   public static void addDefInit(ClassWriter cw, String superClsName) {
      MethodVisitor mv = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, superClsName, "<init>", "()V", false);
      mv.visitInsn(177);
      mv.visitMaxs(1, 1);
      mv.visitEnd();
   }

   public static void addGetter(ClassWriter cw, String clsName, String methodName, FieldInfo fi) {
      MethodVisitor mv = cw.visitMethod(1, methodName, "()" + fi.fieldDesc(), (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, clsName, fi.fieldName(), fi.fieldDesc());
      mv.visitInsn(returnOpcode(fi.fieldType()));
      mv.visitMaxs(sizeOf(fi.fieldType()), 1);
      mv.visitEnd();
   }

   public static void addGetterWithReturnClass(ClassWriter cw, String clsName, String methodName, FieldInfo fi, Class returnClass) {
      MethodVisitor mv = cw.visitMethod(1, methodName, "()" + fieldDesc(returnClass), (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      if (fi != null) {
         mv.visitFieldInsn(180, clsName, fi.fieldName(), fi.fieldDesc());
         mv.visitInsn(returnOpcode(returnClass));
         mv.visitMaxs(sizeOf(fi.fieldType()), 1);
      } else {
         addReturnDefaultValue(mv, returnClass);
         mv.visitMaxs(0, 0);
      }

      mv.visitEnd();
   }

   public static void addSetter(ClassWriter cw, String clsName, String methodName, FieldInfo fi) {
      MethodVisitor mv = cw.visitMethod(1, methodName, "(" + fi.fieldDesc() + ")V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(loadOpcode(fi.fieldType()), 1);
      mv.visitFieldInsn(181, clsName, fi.fieldName(), fi.fieldDesc());
      mv.visitInsn(177);
      int size = 1 + sizeOf(fi.fieldType());
      mv.visitMaxs(size, size);
      mv.visitEnd();
   }

   public static void addDummyImplementation(ClassWriter cw, Method intfMethod) {
      MethodVisitor mv = cw.visitMethod(1, intfMethod.getName(), methodDesc(intfMethod), (String)null, (String[])null);
      mv.visitCode();
      addReturnDefaultValue(mv, intfMethod.getReturnType());
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   public static void addDelegatingImplementation(ClassWriter cw, String clsName, Method m, FieldInfo delegateField) {
      String methodDesc = methodDesc(m);
      MethodVisitor mv = cw.visitMethod(1, m.getName(), methodDesc, (String)null, exceptionsDesc(m.getExceptionTypes()));
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, clsName, delegateField.fieldName(), delegateField.fieldDesc());
      loadArgs(mv, m);
      mv.visitMethodInsn(182, delegateField.binName(), m.getName(), methodDesc, false);
      mv.visitInsn(returnOpcode(m.getReturnType()));
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   public static void addCtrInitedFinalFields(ClassWriter cw, String clsName, String superClsName, FieldInfo... fields) {
      StringBuilder sb = new StringBuilder("(");
      FieldInfo[] var5 = fields;
      int idx = fields.length;

      for(int var7 = 0; var7 < idx; ++var7) {
         FieldInfo f = var5[var7];
         if (f != null) {
            sb.append(f.fieldDesc());
         }
      }

      sb.append(")V");
      MethodVisitor mv = cw.visitMethod(1, "<init>", sb.toString(), (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitMethodInsn(183, superClsName, "<init>", "()V", false);
      idx = 1;
      FieldInfo[] var12 = fields;
      int var13 = fields.length;

      for(int var9 = 0; var9 < var13; ++var9) {
         FieldInfo f = var12[var9];
         if (f != null) {
            addInstanceField(cw, f, true);
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(loadOpcode(f.fieldType()), idx);
            idx += sizeOf(f.fieldType());
            mv.visitFieldInsn(181, clsName, f.fieldName(), f.fieldDesc());
         }
      }

      mv.visitInsn(177);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   public static void addEqualsAndHashCode(ClassWriter cw, String clsName, FieldInfo fi) {
      assert !fi.fieldType().isPrimitive();

      addEquals(cw, clsName, fi);
      addHashCode(cw, clsName, fi);
   }

   private static void addEquals(ClassWriter cw, String clsName, FieldInfo fi) {
      assert !fi.fieldType().isPrimitive();

      MethodVisitor mv = cw.visitMethod(1, "equals", "(Ljava/lang/Object;)Z", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 1);
      mv.visitTypeInsn(193, clsName);
      Label typeMatched = new Label();
      mv.visitJumpInsn(154, typeMatched);
      mv.visitInsn(3);
      mv.visitInsn(172);
      mv.visitLabel(typeMatched);
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, clsName, fi.fieldName(), fi.fieldDesc());
      mv.visitVarInsn(25, 1);
      mv.visitTypeInsn(192, clsName);
      mv.visitFieldInsn(180, clsName, fi.fieldName(), fi.fieldDesc());
      mv.visitMethodInsn(182, "java/lang/Object", "equals", "(Ljava/lang/Object;)Z", false);
      mv.visitInsn(172);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   private static void addHashCode(ClassWriter cw, String clsName, FieldInfo fi) {
      assert !fi.fieldType().isPrimitive();

      MethodVisitor mv = cw.visitMethod(1, "hashCode", "()I", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, clsName, fi.fieldName(), fi.fieldDesc());
      Label fieldNotNull = new Label();
      mv.visitJumpInsn(199, fieldNotNull);
      mv.visitInsn(3);
      mv.visitInsn(172);
      mv.visitLabel(fieldNotNull);
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, clsName, fi.fieldName(), fi.fieldDesc());
      mv.visitMethodInsn(182, "java/lang/Object", "hashCode", "()I", false);
      mv.visitInsn(172);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   public static void addInstanceField(ClassWriter cw, FieldInfo fi, boolean isFinal) {
      int mods = 2;
      if (isFinal) {
         mods += 16;
      }

      cw.visitField(mods, fi.fieldName(), fi.fieldDesc(), (String)null, (Object)null).visitEnd();
   }

   public static void addSerializationAssertMethods(ClassWriter cw, String exceptionCls, String errorMessage) {
      MethodVisitor wo = cw.visitMethod(2, "writeObject", "(Ljava/io/ObjectOutputStream;)V", (String)null, new String[]{"java/io/IOException"});
      wo.visitCode();
      wo.visitTypeInsn(187, exceptionCls);
      wo.visitInsn(89);
      wo.visitLdcInsn(errorMessage);
      wo.visitMethodInsn(183, exceptionCls, "<init>", "(Ljava/lang/String;)V", false);
      wo.visitInsn(191);
      wo.visitMaxs(3, 2);
      wo.visitEnd();
      MethodVisitor ro = cw.visitMethod(2, "readObject", "(Ljava/io/ObjectInputStream;)V", (String)null, new String[]{"java/io/IOException", "java/lang/ClassNotFoundException"});
      ro.visitCode();
      ro.visitTypeInsn(187, exceptionCls);
      ro.visitInsn(89);
      ro.visitLdcInsn(errorMessage);
      ro.visitMethodInsn(183, exceptionCls, "<init>", "(Ljava/lang/String;)V", false);
      ro.visitInsn(191);
      ro.visitMaxs(3, 2);
      ro.visitEnd();
   }

   public static void addAssertMethods(ClassWriter cw, Method[] methods, Class ex, String exceptionMsg) {
      String exceptionClass = binName(ex);
      Method[] var5 = methods;
      int var6 = methods.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Method m = var5[var7];
         int mods = 0;
         if (Modifier.isPublic(m.getModifiers())) {
            mods = 1;
         } else if (Modifier.isProtected(m.getModifiers())) {
            mods = 4;
         }

         MethodVisitor mv = cw.visitMethod(mods, m.getName(), methodDesc(m), (String)null, exceptionsDesc(m.getExceptionTypes()));
         mv.visitCode();
         mv.visitTypeInsn(187, exceptionClass);
         mv.visitInsn(89);
         mv.visitLdcInsn(exceptionMsg);
         mv.visitMethodInsn(183, exceptionClass, "<init>", "(Ljava/lang/String;)V", false);
         mv.visitInsn(191);
         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

   }

   public static List filterExs(Method m) {
      List allExs = new ArrayList();
      Class[] var2 = m.getExceptionTypes();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class c = var2[var4];
         if (!RemoteException.class.isAssignableFrom(c)) {
            allExs.add(c);
         }
      }

      return allExs;
   }

   public static void addSerializationSupport(String clsName, ClassWriter cw, MethodVisitor clInitMV) {
      String serializerClass = binName(Serializer.class);
      String serializerDesc = fieldDesc(Serializer.class);
      String serField = "__WL_Serializer";
      int serFieldModifiers = 154;
      cw.visitField(serFieldModifiers, serField, serializerDesc, (String)null, (Object)null).visitEnd();
      MethodVisitor clInit = clInitMV;
      if (clInitMV == null) {
         clInit = cw.visitMethod(8, "<clinit>", "()V", (String)null, (String[])null);
         clInit.visitCode();
      }

      clInit.visitTypeInsn(187, serializerClass);
      clInit.visitInsn(89);
      clInit.visitLdcInsn(Type.getType("L" + clsName + ";"));
      clInit.visitMethodInsn(183, serializerClass, "<init>", "(Ljava/lang/Class;)V", false);
      clInit.visitFieldInsn(179, clsName, serField, serializerDesc);
      if (clInitMV == null) {
         clInit.visitInsn(177);
         clInit.visitMaxs(3, 0);
         clInit.visitEnd();
      }

      MethodVisitor wo = cw.visitMethod(2, "writeObject", "(Ljava/io/ObjectOutputStream;)V", (String)null, new String[]{"java/io/IOException", "java/lang/IllegalAccessException"});
      wo.visitCode();
      wo.visitFieldInsn(178, clsName, serField, serializerDesc);
      wo.visitVarInsn(25, 1);
      wo.visitVarInsn(25, 0);
      wo.visitMethodInsn(182, serializerClass, "writeObject", "(Ljava/io/ObjectOutputStream;Ljava/lang/Object;)V", false);
      wo.visitInsn(177);
      wo.visitMaxs(3, 2);
      wo.visitEnd();
      MethodVisitor ro = cw.visitMethod(2, "readObject", "(Ljava/io/ObjectInputStream;)V", (String)null, new String[]{"java/io/IOException", "java/lang/ClassNotFoundException", "java/lang/IllegalAccessException"});
      ro.visitCode();
      ro.visitFieldInsn(178, clsName, serField, serializerDesc);
      ro.visitVarInsn(25, 1);
      ro.visitVarInsn(25, 0);
      ro.visitMethodInsn(182, serializerClass, "readObject", "(Ljava/io/ObjectInputStream;Ljava/lang/Object;)V", false);
      ro.visitInsn(177);
      ro.visitMaxs(3, 2);
      ro.visitEnd();
   }

   public static void addDistinctMDFields(ClassWriter cw, String mdPrefix, Collection sigs, boolean isStatic) {
      Set withoutDups = new HashSet(sigs);
      Iterator var5 = withoutDups.iterator();

      while(var5.hasNext()) {
         String sig = (String)var5.next();
         addMDField(cw, mdPrefix + sig, isStatic);
      }

   }

   public static void addMDField(ClassWriter cw, String name, boolean isStatic) {
      int mods = 1;
      if (isStatic) {
         mods += 8;
      }

      cw.visitField(mods, name, WL_MD_FIELD_DESCRIPTOR, (String)null, (Object)null).visitEnd();
   }

   public static void addEOMembers(ClassWriter cw, String cls, String superCls, MethInfo... mis) {
      addMembers(cw, cls, superCls, true, mis);
   }

   public static void addHomeMembers(ClassWriter cw, String cls, String superCls, MethInfo... mis) {
      addMembers(cw, cls, superCls, false, mis);
   }

   private static void addMembers(ClassWriter cw, String cls, String superCls, boolean staticMD, MethInfo... mis) {
      if (mis != null) {
         MethInfo[] var5 = mis;
         int var6 = mis.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            MethInfo mi = var5[var7];
            addMDField(cw, mi.getMdName(), staticMD);
            StringBuilder argsDesc = new StringBuilder();
            if (mi.getArgs() != null) {
               Class[] var10 = mi.getArgs();
               int var11 = var10.length;

               for(int var12 = 0; var12 < var11; ++var12) {
                  Class c = var10[var12];
                  argsDesc.append(fieldDesc(c));
               }
            }

            String returnDesc = fieldDesc(mi.getRetType());
            String methodDesc = "(" + argsDesc + ")" + returnDesc;
            String[] exDesc = exceptionsDesc(mi.getExs());
            MethodVisitor mv = cw.visitMethod(1, mi.getMethodName(), methodDesc, (String)null, exDesc);
            mv.visitCode();
            mv.visitVarInsn(25, 0);
            if (staticMD) {
               mv.visitFieldInsn(178, cls, mi.getMdName(), WL_MD_FIELD_DESCRIPTOR);
            } else {
               mv.visitVarInsn(25, 0);
               mv.visitFieldInsn(180, cls, mi.getMdName(), WL_MD_FIELD_DESCRIPTOR);
            }

            if (mi.getArgs() != null) {
               int curIndex = 1;
               Class[] var15 = mi.getArgs();
               int var16 = var15.length;

               for(int var17 = 0; var17 < var16; ++var17) {
                  Class c = var15[var17];
                  mv.visitVarInsn(loadOpcode(c), curIndex);
                  curIndex += sizeOf(c);
               }
            }

            mv.visitMethodInsn(183, superCls, mi.getMethodName(), "(" + WL_MD_FIELD_DESCRIPTOR + argsDesc + ")" + returnDesc, false);
            mv.visitInsn(returnOpcode(mi.getRetType()));
            mv.visitMaxs(0, 0);
            mv.visitEnd();
         }

      }
   }

   public static Method[] filterDuplicatedMethods(Method[] methods, Map exceptionMapResult) {
      Map methodMap = new TreeMap();
      Map exceptionMap = new HashMap();
      Method[] var4 = methods;
      int var5 = methods.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Method m = var4[var6];
         String methodDesc = m.getName() + ":" + methodDesc(m);
         if (!methodMap.containsKey(methodDesc)) {
            methodMap.put(methodDesc, m);
            exceptionMap.put(methodDesc, m.getExceptionTypes());
         } else {
            Class[] exceptions1 = (Class[])exceptionMap.get(methodDesc);
            Class[] exceptions2 = m.getExceptionTypes();
            Set resultExceptions = new HashSet();
            mergeExceptions(exceptions1, exceptions2, resultExceptions);
            mergeExceptions(exceptions2, exceptions1, resultExceptions);
            exceptionMap.put(methodDesc, resultExceptions.toArray(new Class[resultExceptions.size()]));
         }
      }

      Iterator var12 = methodMap.entrySet().iterator();

      while(var12.hasNext()) {
         Map.Entry entry = (Map.Entry)var12.next();
         String key = (String)entry.getKey();
         exceptionMapResult.put(entry.getValue(), exceptionsDesc((Class[])exceptionMap.get(key)));
      }

      return (Method[])methodMap.values().toArray(new Method[0]);
   }

   public static void mergeExceptions(Class[] fromExceptions, Class[] withExceptions, Set resultExceptions) {
      Class[] var3 = fromExceptions;
      int var4 = fromExceptions.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class fromException = var3[var5];
         if (!resultExceptions.contains(fromException)) {
            Class[] var7 = withExceptions;
            int var8 = withExceptions.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               Class withException = var7[var9];
               if (withException.isAssignableFrom(fromException)) {
                  resultExceptions.add(fromException);
                  break;
               }
            }
         }
      }

   }
}
