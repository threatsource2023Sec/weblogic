package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Invoker;
import com.kenai.jffi.ObjectParameterInfo;
import com.kenai.jffi.ObjectParameterStrategy;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import jnr.ffi.Pointer;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import org.python.objectweb.asm.Label;

abstract class AbstractFastNumericMethodGenerator extends BaseMethodGenerator {
   static final Map STRATEGY_ADDRESS_METHODS;
   static final Map STRATEGY_PARAMETER_TYPES;

   public void generate(AsmBuilder builder, SkinnyMethodAdapter mv, LocalVariableAllocator localVariableAllocator, CallContext callContext, ResultType resultType, ParameterType[] parameterTypes, boolean ignoreError) {
      Class nativeIntType = this.getInvokerType();
      LocalVariable objCount = localVariableAllocator.allocate(Integer.TYPE);
      LocalVariable[] parameters = AsmUtil.getParameterVariables(parameterTypes);
      LocalVariable[] converted = new LocalVariable[parameterTypes.length];
      int pointerCount = 0;

      for(int i = 0; i < parameterTypes.length; ++i) {
         converted[i] = loadAndConvertParameter(builder, mv, localVariableAllocator, parameters[i], parameterTypes[i]);
         Class javaParameterType = parameterTypes[i].effectiveJavaType();
         ToNativeOp op = ToNativeOp.get(parameterTypes[i]);
         if (op != null && op.isPrimitive()) {
            op.emitPrimitive(mv, this.getInvokerType(), parameterTypes[i].getNativeType());
         } else {
            if (!hasPointerParameterStrategy(javaParameterType)) {
               throw new IllegalArgumentException("unsupported parameter type " + parameterTypes[i].getDeclaredType());
            }

            pointerCount = emitDirectCheck(mv, javaParameterType, nativeIntType, converted[i], objCount, pointerCount);
         }
      }

      Label hasObjects = new Label();
      Label convertResult = new Label();
      if (pointerCount > 0) {
         mv.iload(objCount);
         mv.ifne(hasObjects);
      }

      mv.invokevirtual(CodegenUtils.p(Invoker.class), this.getInvokerMethodName(resultType, parameterTypes, ignoreError), this.getInvokerSignature(parameterTypes.length, nativeIntType));
      if (pointerCount > 0) {
         mv.label(convertResult);
      }

      Class javaReturnType = resultType.effectiveJavaType();
      Class nativeReturnType = nativeIntType;
      if (Float.class != javaReturnType && Float.TYPE != javaReturnType) {
         if (Double.class == javaReturnType || Double.TYPE == javaReturnType) {
            NumberUtil.widen(mv, nativeIntType, Long.TYPE);
            mv.invokestatic(Double.class, "longBitsToDouble", Double.TYPE, Long.TYPE);
            nativeReturnType = Double.TYPE;
         }
      } else {
         NumberUtil.narrow(mv, nativeIntType, Integer.TYPE);
         mv.invokestatic(Float.class, "intBitsToFloat", Float.TYPE, Integer.TYPE);
         nativeReturnType = Float.TYPE;
      }

      Class unboxedResultType = AsmUtil.unboxedReturnType(javaReturnType);
      NumberUtil.convertPrimitive(mv, nativeReturnType, unboxedResultType, resultType.getNativeType());
      emitEpilogue(builder, mv, resultType, parameterTypes, parameters, converted, (Runnable)null);
      if (pointerCount > 0) {
         mv.label(hasObjects);
         LocalVariable[] tmp;
         int i;
         if (Integer.TYPE == nativeIntType) {
            tmp = new LocalVariable[parameterTypes.length];

            for(i = parameterTypes.length - 1; i > 0; --i) {
               tmp[i] = localVariableAllocator.allocate(Integer.TYPE);
               mv.istore(tmp[i]);
            }

            if (parameterTypes.length > 0) {
               mv.i2l();
            }

            for(i = 1; i < parameterTypes.length; ++i) {
               mv.iload(tmp[i]);
               mv.i2l();
            }
         }

         mv.iload(objCount);
         tmp = new LocalVariable[parameterTypes.length];

         for(i = 0; i < parameterTypes.length; ++i) {
            Class javaParameterType = parameterTypes[i].effectiveJavaType();
            if (hasPointerParameterStrategy(javaParameterType)) {
               mv.aload(converted[i]);
               emitParameterStrategyLookup(mv, javaParameterType);
               mv.astore(tmp[i] = localVariableAllocator.allocate(ParameterStrategy.class));
               mv.aload(converted[i]);
               mv.aload(tmp[i]);
               mv.aload(0);
               ObjectParameterInfo info = ObjectParameterInfo.create(i, AsmUtil.getNativeArrayFlags(parameterTypes[i].annotations()));
               mv.getfield(builder.getClassNamePath(), builder.getObjectParameterInfoName(info), CodegenUtils.ci(ObjectParameterInfo.class));
            }
         }

         mv.invokevirtual(CodegenUtils.p(Invoker.class), getObjectParameterMethodName(parameterTypes.length), getObjectParameterMethodSignature(parameterTypes.length, pointerCount));
         NumberUtil.narrow(mv, Long.TYPE, nativeIntType);
         mv.go_to(convertResult);
      }

   }

   private static void addStrategyParameterType(Map map, Class strategyClass, Class parameterType) {
      try {
         Method addressMethod = strategyClass.getDeclaredMethod("address", parameterType);
         if (Modifier.isPublic(addressMethod.getModifiers()) && Modifier.isPublic(addressMethod.getDeclaringClass().getModifiers())) {
            map.put(strategyClass, addressMethod);
         }
      } catch (NoSuchMethodException var4) {
      }

   }

   static boolean hasPointerParameterStrategy(Class javaType) {
      Iterator var1 = STRATEGY_PARAMETER_TYPES.keySet().iterator();

      Class c;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         c = (Class)var1.next();
      } while(!c.isAssignableFrom(javaType));

      return true;
   }

   static Class emitParameterStrategyLookup(SkinnyMethodAdapter mv, Class javaParameterType) {
      Iterator var2 = STRATEGY_PARAMETER_TYPES.entrySet().iterator();

      Map.Entry e;
      do {
         if (!var2.hasNext()) {
            throw new RuntimeException("no conversion strategy for: " + javaParameterType);
         }

         e = (Map.Entry)var2.next();
      } while(!((Class)e.getKey()).isAssignableFrom(javaParameterType));

      mv.invokestatic(AsmRuntime.class, "pointerParameterStrategy", (Class)e.getValue(), (Class)e.getKey());
      return (Class)e.getValue();
   }

   static void emitParameterStrategyAddress(SkinnyMethodAdapter mv, Class nativeIntType, Class strategyClass, LocalVariable strategy, LocalVariable parameter) {
      mv.aload(strategy);
      mv.aload(parameter);
      Method addressMethod = (Method)STRATEGY_ADDRESS_METHODS.get(strategyClass);
      if (addressMethod != null) {
         mv.invokevirtual(strategyClass, addressMethod.getName(), addressMethod.getReturnType(), addressMethod.getParameterTypes());
      } else {
         mv.invokevirtual(PointerParameterStrategy.class, "address", Long.TYPE, Object.class);
      }

      NumberUtil.narrow(mv, Long.TYPE, nativeIntType);
   }

   static int emitDirectCheck(SkinnyMethodAdapter mv, Class javaParameterClass, Class nativeIntType, LocalVariable parameter, LocalVariable objCount, int pointerCount) {
      if (pointerCount < 1) {
         mv.iconst_0();
         mv.istore(objCount);
      }

      Label next = new Label();
      Label nullPointer = new Label();
      mv.ifnull(nullPointer);
      if (Pointer.class.isAssignableFrom(javaParameterClass)) {
         mv.aload(parameter);
         mv.invokevirtual(Pointer.class, "address", Long.TYPE);
         NumberUtil.narrow(mv, Long.TYPE, nativeIntType);
         mv.aload(parameter);
         mv.invokevirtual(Pointer.class, "isDirect", Boolean.TYPE);
         mv.iftrue(next);
      } else if (Buffer.class.isAssignableFrom(javaParameterClass)) {
         mv.aload(parameter);
         mv.invokestatic(AsmRuntime.class, "longValue", Long.TYPE, Buffer.class);
         NumberUtil.narrow(mv, Long.TYPE, nativeIntType);
         mv.aload(parameter);
         mv.invokevirtual(Buffer.class, "isDirect", Boolean.TYPE);
         mv.iftrue(next);
      } else {
         if (!javaParameterClass.isArray() || !javaParameterClass.getComponentType().isPrimitive()) {
            throw new UnsupportedOperationException("unsupported parameter type: " + javaParameterClass);
         }

         if (Long.TYPE == nativeIntType) {
            mv.lconst_0();
         } else {
            mv.iconst_0();
         }
      }

      mv.iinc(objCount, 1);
      mv.go_to(next);
      mv.label(nullPointer);
      if (Long.TYPE == nativeIntType) {
         mv.lconst_0();
      } else {
         mv.iconst_0();
      }

      mv.label(next);
      ++pointerCount;
      return pointerCount;
   }

   static String getObjectParameterMethodName(int parameterCount) {
      return "invokeN" + parameterCount;
   }

   static String getObjectParameterMethodSignature(int parameterCount, int pointerCount) {
      StringBuilder sb = new StringBuilder();
      sb.append('(').append(CodegenUtils.ci(CallContext.class)).append(CodegenUtils.ci(Long.TYPE));

      int n;
      for(n = 0; n < parameterCount; ++n) {
         sb.append('J');
      }

      sb.append('I');

      for(n = 0; n < pointerCount; ++n) {
         sb.append(CodegenUtils.ci(Object.class));
         sb.append(CodegenUtils.ci(ObjectParameterStrategy.class));
         sb.append(CodegenUtils.ci(ObjectParameterInfo.class));
      }

      sb.append(")J");
      return sb.toString();
   }

   abstract String getInvokerMethodName(ResultType var1, ParameterType[] var2, boolean var3);

   abstract String getInvokerSignature(int var1, Class var2);

   abstract Class getInvokerType();

   static {
      Map strategies = new HashMap();
      addStrategyParameterType(strategies, BufferParameterStrategy.class, Buffer.class);
      addStrategyParameterType(strategies, PointerParameterStrategy.class, Pointer.class);
      STRATEGY_ADDRESS_METHODS = Collections.unmodifiableMap(strategies);
      Map types = new LinkedHashMap();
      types.put(Pointer.class, PointerParameterStrategy.class);
      Class[] var2 = new Class[]{ByteBuffer.class, CharBuffer.class, ShortBuffer.class, IntBuffer.class, LongBuffer.class, FloatBuffer.class, DoubleBuffer.class, Buffer.class};
      int var3 = var2.length;

      int var4;
      Class c;
      for(var4 = 0; var4 < var3; ++var4) {
         c = var2[var4];
         types.put(c, BufferParameterStrategy.class);
      }

      var2 = new Class[]{byte[].class, short[].class, char[].class, int[].class, long[].class, float[].class, double[].class, boolean[].class};
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         c = var2[var4];
         types.put(c, ParameterStrategy.class);
      }

      STRATEGY_PARAMETER_TYPES = Collections.unmodifiableMap(types);
   }
}
