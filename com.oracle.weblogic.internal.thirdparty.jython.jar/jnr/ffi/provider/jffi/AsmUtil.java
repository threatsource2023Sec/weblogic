package jnr.ffi.provider.jffi;

import com.kenai.jffi.Platform;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import jnr.ffi.Address;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.FromNativeType;
import jnr.ffi.provider.ParameterFlags;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.SigType;
import jnr.ffi.provider.ToNativeType;
import org.python.objectweb.asm.ClassVisitor;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;

final class AsmUtil {
   private AsmUtil() {
   }

   public static MethodVisitor newTraceMethodVisitor(MethodVisitor mv) {
      try {
         Class tmvClass = Class.forName("org.python.objectweb.asm.util.TraceMethodVisitor").asSubclass(MethodVisitor.class);
         Constructor c = tmvClass.getDeclaredConstructor(MethodVisitor.class);
         return (MethodVisitor)c.newInstance(mv);
      } catch (Throwable var3) {
         return mv;
      }
   }

   public static ClassVisitor newTraceClassVisitor(ClassVisitor cv, OutputStream out) {
      return newTraceClassVisitor(cv, new PrintWriter(out, true));
   }

   public static ClassVisitor newTraceClassVisitor(ClassVisitor cv, PrintWriter out) {
      try {
         Class tmvClass = Class.forName("org.python.objectweb.asm.util.TraceClassVisitor").asSubclass(ClassVisitor.class);
         Constructor c = tmvClass.getDeclaredConstructor(ClassVisitor.class, PrintWriter.class);
         return (ClassVisitor)c.newInstance(cv, out);
      } catch (Throwable var4) {
         return cv;
      }
   }

   public static ClassVisitor newTraceClassVisitor(PrintWriter out) {
      try {
         Class tmvClass = Class.forName("org.python.objectweb.asm.util.TraceClassVisitor").asSubclass(ClassVisitor.class);
         Constructor c = tmvClass.getDeclaredConstructor(PrintWriter.class);
         return (ClassVisitor)c.newInstance(out);
      } catch (Throwable var3) {
         throw new RuntimeException(var3);
      }
   }

   public static ClassVisitor newCheckClassAdapter(ClassVisitor cv) {
      try {
         Class tmvClass = Class.forName("org.python.objectweb.asm.util.CheckClassAdapter").asSubclass(ClassVisitor.class);
         Constructor c = tmvClass.getDeclaredConstructor(ClassVisitor.class);
         return (ClassVisitor)c.newInstance(cv);
      } catch (Throwable var3) {
         return cv;
      }
   }

   public static Class unboxedReturnType(Class type) {
      return unboxedType(type);
   }

   public static Class unboxedType(Class boxedType) {
      if (boxedType == Byte.class) {
         return Byte.TYPE;
      } else if (boxedType == Short.class) {
         return Short.TYPE;
      } else if (boxedType == Integer.class) {
         return Integer.TYPE;
      } else if (boxedType == Long.class) {
         return Long.TYPE;
      } else if (boxedType == Float.class) {
         return Float.TYPE;
      } else if (boxedType == Double.class) {
         return Double.TYPE;
      } else if (boxedType == Boolean.class) {
         return Boolean.TYPE;
      } else if (Pointer.class.isAssignableFrom(boxedType)) {
         return Platform.getPlatform().addressSize() == 32 ? Integer.TYPE : Long.TYPE;
      } else if (Address.class == boxedType) {
         return Platform.getPlatform().addressSize() == 32 ? Integer.TYPE : Long.TYPE;
      } else {
         return boxedType;
      }
   }

   public static Class boxedType(Class type) {
      if (type == Byte.TYPE) {
         return Byte.class;
      } else if (type == Short.TYPE) {
         return Short.class;
      } else if (type == Integer.TYPE) {
         return Integer.class;
      } else if (type == Long.TYPE) {
         return Long.class;
      } else if (type == Float.TYPE) {
         return Float.class;
      } else if (type == Double.TYPE) {
         return Double.class;
      } else {
         return type == Boolean.TYPE ? Boolean.class : type;
      }
   }

   static void emitReturnOp(SkinnyMethodAdapter mv, Class returnType) {
      if (!returnType.isPrimitive()) {
         mv.areturn();
      } else if (Long.TYPE == returnType) {
         mv.lreturn();
      } else if (Float.TYPE == returnType) {
         mv.freturn();
      } else if (Double.TYPE == returnType) {
         mv.dreturn();
      } else if (Void.TYPE == returnType) {
         mv.voidreturn();
      } else {
         mv.ireturn();
      }

   }

   static int calculateLocalVariableSpace(Class type) {
      return Long.TYPE != type && Double.TYPE != type ? 1 : 2;
   }

   static int calculateLocalVariableSpace(SigType type) {
      return calculateLocalVariableSpace(type.getDeclaredType());
   }

   static int calculateLocalVariableSpace(Class... types) {
      int size = 0;

      for(int i = 0; i < types.length; ++i) {
         size += calculateLocalVariableSpace(types[i]);
      }

      return size;
   }

   static int calculateLocalVariableSpace(SigType... types) {
      int size = 0;
      SigType[] var2 = types;
      int var3 = types.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         SigType type = var2[var4];
         size += calculateLocalVariableSpace(type);
      }

      return size;
   }

   private static void unboxPointerOrStruct(SkinnyMethodAdapter mv, Class type, Class nativeType) {
      mv.invokestatic(CodegenUtils.p(AsmRuntime.class), Long.TYPE == nativeType ? "longValue" : "intValue", CodegenUtils.sig(nativeType, type));
   }

   static void unboxPointer(SkinnyMethodAdapter mv, Class nativeType) {
      unboxPointerOrStruct(mv, Pointer.class, nativeType);
   }

   static void unboxBoolean(SkinnyMethodAdapter mv, Class boxedType, Class nativeType) {
      mv.invokevirtual(CodegenUtils.p(boxedType), "booleanValue", "()Z");
      NumberUtil.widen(mv, Boolean.TYPE, nativeType);
   }

   static void unboxBoolean(SkinnyMethodAdapter mv, Class nativeType) {
      unboxBoolean(mv, Boolean.class, nativeType);
   }

   static void unboxNumber(SkinnyMethodAdapter mv, Class boxedType, Class unboxedType, NativeType nativeType) {
      if (Number.class.isAssignableFrom(boxedType)) {
         switch (nativeType) {
            case SCHAR:
            case UCHAR:
               mv.invokevirtual(CodegenUtils.p(boxedType), "byteValue", "()B");
               NumberUtil.convertPrimitive(mv, Byte.TYPE, unboxedType, nativeType);
               break;
            case SSHORT:
            case USHORT:
               mv.invokevirtual(CodegenUtils.p(boxedType), "shortValue", "()S");
               NumberUtil.convertPrimitive(mv, Short.TYPE, unboxedType, nativeType);
               break;
            case SINT:
            case UINT:
            case SLONG:
            case ULONG:
            case ADDRESS:
               if (NumberUtil.sizeof(nativeType) == 4) {
                  mv.invokevirtual(CodegenUtils.p(boxedType), "intValue", "()I");
                  NumberUtil.convertPrimitive(mv, Integer.TYPE, unboxedType, nativeType);
               } else {
                  mv.invokevirtual(CodegenUtils.p(boxedType), "longValue", "()J");
                  NumberUtil.convertPrimitive(mv, Long.TYPE, unboxedType, nativeType);
               }
               break;
            case SLONGLONG:
            case ULONGLONG:
               mv.invokevirtual(CodegenUtils.p(boxedType), "longValue", "()J");
               NumberUtil.narrow(mv, Long.TYPE, unboxedType);
               break;
            case FLOAT:
               mv.invokevirtual(CodegenUtils.p(boxedType), "floatValue", "()F");
               break;
            case DOUBLE:
               mv.invokevirtual(CodegenUtils.p(boxedType), "doubleValue", "()D");
         }
      } else {
         if (!Boolean.class.isAssignableFrom(boxedType)) {
            throw new IllegalArgumentException("unsupported boxed type: " + boxedType);
         }

         unboxBoolean(mv, unboxedType);
      }

   }

   static void unboxNumber(SkinnyMethodAdapter mv, Class boxedType, Class nativeType) {
      if (Number.class.isAssignableFrom(boxedType)) {
         if (Byte.TYPE == nativeType) {
            mv.invokevirtual(CodegenUtils.p(boxedType), "byteValue", "()B");
         } else if (Short.TYPE == nativeType) {
            mv.invokevirtual(CodegenUtils.p(boxedType), "shortValue", "()S");
         } else if (Integer.TYPE == nativeType) {
            mv.invokevirtual(CodegenUtils.p(boxedType), "intValue", "()I");
         } else if (Long.TYPE == nativeType) {
            mv.invokevirtual(CodegenUtils.p(boxedType), "longValue", "()J");
         } else if (Float.TYPE == nativeType) {
            mv.invokevirtual(CodegenUtils.p(boxedType), "floatValue", "()F");
         } else {
            if (Double.TYPE != nativeType) {
               throw new IllegalArgumentException("unsupported Number subclass: " + boxedType);
            }

            mv.invokevirtual(CodegenUtils.p(boxedType), "doubleValue", "()D");
         }
      } else {
         if (!Boolean.class.isAssignableFrom(boxedType)) {
            throw new IllegalArgumentException("unsupported boxed type: " + boxedType);
         }

         unboxBoolean(mv, nativeType);
      }

   }

   static void boxValue(AsmBuilder builder, SkinnyMethodAdapter mv, Class boxedType, Class unboxedType) {
      if (boxedType != unboxedType && !boxedType.isPrimitive()) {
         if (Boolean.class.isAssignableFrom(boxedType)) {
            NumberUtil.narrow(mv, unboxedType, Boolean.TYPE);
            mv.invokestatic(Boolean.class, "valueOf", Boolean.class, Boolean.TYPE);
         } else if (Pointer.class.isAssignableFrom(boxedType)) {
            getfield(mv, builder, builder.getRuntimeField());
            mv.invokestatic(AsmRuntime.class, "pointerValue", Pointer.class, unboxedType, Runtime.class);
         } else if (Address.class == boxedType) {
            mv.invokestatic(boxedType, "valueOf", boxedType, unboxedType);
         } else {
            if (!Number.class.isAssignableFrom(boxedType) || boxedType(unboxedType) != boxedType) {
               throw new IllegalArgumentException("cannot box value of type " + unboxedType + " to " + boxedType);
            }

            mv.invokestatic(boxedType, "valueOf", boxedType, unboxedType);
         }
      }

   }

   static int getNativeArrayFlags(int flags) {
      int nflags = 0;
      nflags |= ParameterFlags.isIn(flags) ? 1 : 0;
      nflags |= ParameterFlags.isOut(flags) ? 2 : 0;
      nflags |= !ParameterFlags.isNulTerminate(flags) && !ParameterFlags.isIn(flags) ? 0 : 4;
      return nflags;
   }

   static int getNativeArrayFlags(Collection annotations) {
      return getNativeArrayFlags(ParameterFlags.parse(annotations));
   }

   static LocalVariable[] getParameterVariables(ParameterType[] parameterTypes) {
      LocalVariable[] lvars = new LocalVariable[parameterTypes.length];
      int lvar = 1;

      for(int i = 0; i < parameterTypes.length; ++i) {
         lvars[i] = new LocalVariable(parameterTypes[i].getDeclaredType(), lvar);
         lvar += calculateLocalVariableSpace((SigType)parameterTypes[i]);
      }

      return lvars;
   }

   static LocalVariable[] getParameterVariables(Class[] parameterTypes) {
      LocalVariable[] lvars = new LocalVariable[parameterTypes.length];
      int idx = 1;

      for(int i = 0; i < parameterTypes.length; ++i) {
         lvars[i] = new LocalVariable(parameterTypes[i], idx);
         idx += calculateLocalVariableSpace(parameterTypes[i]);
      }

      return lvars;
   }

   static void load(SkinnyMethodAdapter mv, Class parameterType, LocalVariable parameter) {
      if (!parameterType.isPrimitive()) {
         mv.aload(parameter);
      } else if (Long.TYPE == parameterType) {
         mv.lload(parameter);
      } else if (Float.TYPE == parameterType) {
         mv.fload(parameter);
      } else if (Double.TYPE == parameterType) {
         mv.dload(parameter);
      } else {
         mv.iload(parameter);
      }

   }

   static void store(SkinnyMethodAdapter mv, Class type, LocalVariable var) {
      if (!type.isPrimitive()) {
         mv.astore(var);
      } else if (Long.TYPE == type) {
         mv.lstore(var);
      } else if (Double.TYPE == type) {
         mv.dstore(var);
      } else if (Float.TYPE == type) {
         mv.fstore(var);
      } else {
         mv.istore(var);
      }

   }

   static void emitReturn(AsmBuilder builder, SkinnyMethodAdapter mv, Class returnType, Class nativeIntType) {
      if (returnType.isPrimitive()) {
         if (Long.TYPE == returnType) {
            mv.lreturn();
         } else if (Float.TYPE == returnType) {
            mv.freturn();
         } else if (Double.TYPE == returnType) {
            mv.dreturn();
         } else if (Void.TYPE == returnType) {
            mv.voidreturn();
         } else {
            mv.ireturn();
         }
      } else {
         boxValue(builder, mv, returnType, nativeIntType);
         mv.areturn();
      }

   }

   static void getfield(SkinnyMethodAdapter mv, AsmBuilder builder, AsmBuilder.ObjectField field) {
      mv.aload(0);
      mv.getfield(builder.getClassNamePath(), field.name, CodegenUtils.ci(field.klass));
   }

   static void tryfinally(SkinnyMethodAdapter mv, Runnable codeBlock, Runnable finallyBlock) {
      Label before = new Label();
      Label after = new Label();
      Label ensure = new Label();
      Label done = new Label();
      mv.trycatch(before, after, ensure, (String)null);
      mv.label(before);
      codeBlock.run();
      mv.label(after);
      if (finallyBlock != null) {
         finallyBlock.run();
      }

      mv.go_to(done);
      if (finallyBlock != null) {
         mv.label(ensure);
         finallyBlock.run();
         mv.athrow();
      }

      mv.label(done);
   }

   static void emitToNativeConversion(AsmBuilder builder, SkinnyMethodAdapter mv, ToNativeType toNativeType) {
      ToNativeConverter parameterConverter = toNativeType.getToNativeConverter();
      if (parameterConverter != null) {
         Method toNativeMethod = getToNativeMethod(toNativeType, builder.getClassLoader());
         if (toNativeType.getDeclaredType().isPrimitive()) {
            boxValue(builder, mv, NumberUtil.getBoxedClass(toNativeType.getDeclaredType()), toNativeType.getDeclaredType());
         }

         if (!toNativeMethod.getParameterTypes()[0].isAssignableFrom(NumberUtil.getBoxedClass(toNativeType.getDeclaredType()))) {
            mv.checkcast(toNativeMethod.getParameterTypes()[0]);
         }

         mv.aload(0);
         AsmBuilder.ObjectField toNativeConverterField = builder.getToNativeConverterField(parameterConverter);
         mv.getfield(builder.getClassNamePath(), toNativeConverterField.name, CodegenUtils.ci(toNativeConverterField.klass));
         if (!toNativeMethod.getDeclaringClass().equals(toNativeConverterField.klass)) {
            mv.checkcast(toNativeMethod.getDeclaringClass());
         }

         mv.swap();
         if (toNativeType.getToNativeContext() != null) {
            getfield(mv, builder, builder.getToNativeContextField(toNativeType.getToNativeContext()));
         } else {
            mv.aconst_null();
         }

         if (toNativeMethod.getDeclaringClass().isInterface()) {
            mv.invokeinterface(toNativeMethod.getDeclaringClass(), toNativeMethod.getName(), toNativeMethod.getReturnType(), toNativeMethod.getParameterTypes());
         } else {
            mv.invokevirtual(toNativeMethod.getDeclaringClass(), toNativeMethod.getName(), toNativeMethod.getReturnType(), toNativeMethod.getParameterTypes());
         }

         if (!parameterConverter.nativeType().isAssignableFrom(toNativeMethod.getReturnType())) {
            mv.checkcast(CodegenUtils.p(parameterConverter.nativeType()));
         }
      }

   }

   static void emitFromNativeConversion(AsmBuilder builder, SkinnyMethodAdapter mv, FromNativeType fromNativeType, Class nativeClass) {
      FromNativeConverter fromNativeConverter = fromNativeType.getFromNativeConverter();
      if (fromNativeConverter != null) {
         NumberUtil.convertPrimitive(mv, nativeClass, unboxedType(fromNativeConverter.nativeType()), fromNativeType.getNativeType());
         boxValue(builder, mv, fromNativeConverter.nativeType(), nativeClass);
         Method fromNativeMethod = getFromNativeMethod(fromNativeType, builder.getClassLoader());
         getfield(mv, builder, builder.getFromNativeConverterField(fromNativeConverter));
         mv.swap();
         if (fromNativeType.getFromNativeContext() != null) {
            getfield(mv, builder, builder.getFromNativeContextField(fromNativeType.getFromNativeContext()));
         } else {
            mv.aconst_null();
         }

         if (fromNativeMethod.getDeclaringClass().isInterface()) {
            mv.invokeinterface(fromNativeMethod.getDeclaringClass(), fromNativeMethod.getName(), fromNativeMethod.getReturnType(), fromNativeMethod.getParameterTypes());
         } else {
            mv.invokevirtual(fromNativeMethod.getDeclaringClass(), fromNativeMethod.getName(), fromNativeMethod.getReturnType(), fromNativeMethod.getParameterTypes());
         }

         if (fromNativeType.getDeclaredType().isPrimitive()) {
            Class boxedType = NumberUtil.getBoxedClass(fromNativeType.getDeclaredType());
            if (!boxedType.isAssignableFrom(fromNativeMethod.getReturnType())) {
               mv.checkcast(CodegenUtils.p(boxedType));
            }

            unboxNumber(mv, boxedType, fromNativeType.getDeclaredType(), fromNativeType.getNativeType());
         } else if (!fromNativeType.getDeclaredType().isAssignableFrom(fromNativeMethod.getReturnType())) {
            mv.checkcast(CodegenUtils.p(fromNativeType.getDeclaredType()));
         }
      } else if (!fromNativeType.getDeclaredType().isPrimitive()) {
         Class unboxedType = unboxedType(fromNativeType.getDeclaredType());
         NumberUtil.convertPrimitive(mv, nativeClass, unboxedType, fromNativeType.getNativeType());
         boxValue(builder, mv, fromNativeType.getDeclaredType(), unboxedType);
      }

   }

   static Method getToNativeMethod(ToNativeType toNativeType, AsmClassLoader classLoader) {
      ToNativeConverter toNativeConverter = toNativeType.getToNativeConverter();
      if (toNativeConverter == null) {
         return null;
      } else {
         try {
            Class toNativeConverterClass = toNativeConverter.getClass();
            if (Modifier.isPublic(toNativeConverterClass.getModifiers())) {
               Method[] var4 = toNativeConverterClass.getMethods();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Method method = var4[var6];
                  if (method.getName().equals("toNative")) {
                     Class[] methodParameterTypes = method.getParameterTypes();
                     if (toNativeConverter.nativeType().isAssignableFrom(method.getReturnType()) && methodParameterTypes.length == 2 && methodParameterTypes[0].isAssignableFrom(toNativeType.getDeclaredType()) && methodParameterTypes[1] == ToNativeContext.class && methodIsAccessible(method) && classIsVisible(classLoader, method.getDeclaringClass())) {
                        return method;
                     }
                  }
               }
            }

            Method method = toNativeConverterClass.getMethod("toNative", Object.class, ToNativeContext.class);
            return methodIsAccessible(method) && classIsVisible(classLoader, method.getDeclaringClass()) ? method : ToNativeConverter.class.getDeclaredMethod("toNative", Object.class, ToNativeContext.class);
         } catch (NoSuchMethodException var10) {
            try {
               return ToNativeConverter.class.getDeclaredMethod("toNative", Object.class, ToNativeContext.class);
            } catch (NoSuchMethodException var9) {
               throw new RuntimeException("internal error. " + ToNativeConverter.class + " has no toNative() method");
            }
         }
      }
   }

   static Method getFromNativeMethod(FromNativeType fromNativeType, AsmClassLoader classLoader) {
      FromNativeConverter fromNativeConverter = fromNativeType.getFromNativeConverter();
      if (fromNativeConverter == null) {
         return null;
      } else {
         try {
            Class fromNativeConverterClass = fromNativeConverter.getClass();
            if (Modifier.isPublic(fromNativeConverterClass.getModifiers())) {
               Method[] var4 = fromNativeConverterClass.getMethods();
               int var5 = var4.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Method method = var4[var6];
                  if (method.getName().equals("fromNative")) {
                     Class[] methodParameterTypes = method.getParameterTypes();
                     Class javaType = fromNativeType.getDeclaredType().isPrimitive() ? boxedType(fromNativeType.getDeclaredType()) : fromNativeType.getDeclaredType();
                     if (javaType.isAssignableFrom(method.getReturnType()) && methodParameterTypes.length == 2 && methodParameterTypes[0].isAssignableFrom(fromNativeConverter.nativeType()) && methodParameterTypes[1] == FromNativeContext.class && methodIsAccessible(method) && classIsVisible(classLoader, method.getDeclaringClass())) {
                        return method;
                     }
                  }
               }
            }

            Method method = fromNativeConverterClass.getMethod("fromNative", Object.class, FromNativeContext.class);
            return methodIsAccessible(method) && classIsVisible(classLoader, method.getDeclaringClass()) ? method : FromNativeConverter.class.getDeclaredMethod("fromNative", Object.class, FromNativeContext.class);
         } catch (NoSuchMethodException var11) {
            try {
               return FromNativeConverter.class.getDeclaredMethod("fromNative", Object.class, FromNativeContext.class);
            } catch (NoSuchMethodException var10) {
               throw new RuntimeException("internal error. " + FromNativeConverter.class + " has no fromNative() method");
            }
         }
      }
   }

   static boolean methodIsAccessible(Method method) {
      return Modifier.isPublic(method.getModifiers()) && Modifier.isPublic(method.getDeclaringClass().getModifiers());
   }

   private static boolean classIsVisible(ClassLoader classLoader, Class klass) {
      try {
         return classLoader.loadClass(klass.getName()) == klass;
      } catch (ClassNotFoundException var3) {
         return false;
      }
   }
}
