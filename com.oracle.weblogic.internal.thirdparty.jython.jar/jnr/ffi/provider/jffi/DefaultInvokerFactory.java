package jnr.ffi.provider.jffi;

import com.kenai.jffi.Function;
import com.kenai.jffi.HeapInvocationBuffer;
import com.kenai.jffi.ObjectParameterStrategy;
import com.kenai.jffi.ObjectParameterType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jnr.ffi.Address;
import jnr.ffi.CallingConvention;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.Meta;
import jnr.ffi.annotations.StdCall;
import jnr.ffi.annotations.Synchronized;
import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.DefaultSignatureType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.FunctionMapper;
import jnr.ffi.mapper.MethodResultContext;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.ToNativeType;
import jnr.ffi.provider.InvocationSession;
import jnr.ffi.provider.Invoker;
import jnr.ffi.provider.NativeFunction;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.provider.SigType;
import jnr.ffi.util.AnnotationProxy;
import jnr.ffi.util.Annotations;

final class DefaultInvokerFactory {
   private final Runtime runtime;
   private final NativeLibrary library;
   private final SignatureTypeMapper typeMapper;
   private final FunctionMapper functionMapper;
   private final CallingConvention libraryCallingConvention;
   private final boolean libraryIsSynchronized;
   private final Map libraryOptions;

   public DefaultInvokerFactory(Runtime runtime, NativeLibrary library, SignatureTypeMapper typeMapper, FunctionMapper functionMapper, CallingConvention libraryCallingConvention, Map libraryOptions, boolean libraryIsSynchronized) {
      this.runtime = runtime;
      this.library = library;
      this.typeMapper = typeMapper;
      this.functionMapper = functionMapper;
      this.libraryCallingConvention = libraryCallingConvention;
      this.libraryIsSynchronized = libraryIsSynchronized;
      this.libraryOptions = libraryOptions;
   }

   public Invoker createInvoker(Method method) {
      Collection annotations = Annotations.sortedAnnotationCollection(method.getAnnotations());
      String functionName = this.functionMapper.mapFunctionName(method.getName(), new NativeFunctionMapperContext(this.library, annotations));
      long functionAddress = this.library.getSymbolAddress(functionName);
      if (functionAddress == 0L) {
         return new FunctionNotFoundInvoker(method, functionName);
      } else {
         FromNativeContext resultContext = new MethodResultContext(NativeRuntime.getInstance(), method);
         SignatureType signatureType = DefaultSignatureType.create(method.getReturnType(), (FromNativeContext)resultContext);
         ResultType resultType = InvokerUtil.getResultType(this.runtime, method.getReturnType(), resultContext.getAnnotations(), (FromNativeType)this.typeMapper.getFromNativeType(signatureType, resultContext), resultContext);
         FunctionInvoker functionInvoker = getFunctionInvoker(resultType);
         if (resultType.getFromNativeConverter() != null) {
            functionInvoker = new ConvertingInvoker(resultType.getFromNativeConverter(), resultType.getFromNativeContext(), (FunctionInvoker)functionInvoker);
         }

         ParameterType[] parameterTypes = InvokerUtil.getParameterTypes(this.runtime, this.typeMapper, method);
         CallingConvention callingConvention = method.isAnnotationPresent(StdCall.class) ? CallingConvention.STDCALL : this.libraryCallingConvention;
         boolean saveError = jnr.ffi.LibraryLoader.saveError(this.libraryOptions, NativeFunction.hasSaveError(method), NativeFunction.hasIgnoreError(method));
         if (method.isVarArgs()) {
            Invoker invoker = new VariadicInvoker(this.runtime, (FunctionInvoker)functionInvoker, this.typeMapper, parameterTypes, functionAddress, resultType, saveError, callingConvention);
            return (Invoker)(!this.libraryIsSynchronized && !method.isAnnotationPresent(Synchronized.class) ? invoker : new SynchronizedInvoker(invoker));
         } else {
            Function function = new Function(functionAddress, InvokerUtil.getCallContext(resultType, parameterTypes, callingConvention, saveError));
            Marshaller[] marshallers = new Marshaller[parameterTypes.length];

            for(int i = 0; i < marshallers.length; ++i) {
               marshallers[i] = getMarshaller(parameterTypes[i]);
            }

            return new DefaultInvoker(this.runtime, this.library, function, (FunctionInvoker)functionInvoker, marshallers);
         }
      }
   }

   private static FunctionInvoker getFunctionInvoker(ResultType resultType) {
      Class returnType = resultType.effectiveJavaType();
      if (!Void.class.isAssignableFrom(returnType) && Void.TYPE != returnType) {
         if (!Boolean.class.isAssignableFrom(returnType) && Boolean.TYPE != returnType) {
            if (!Number.class.isAssignableFrom(returnType) && !returnType.isPrimitive()) {
               if (Pointer.class.isAssignableFrom(returnType)) {
                  return DefaultInvokerFactory.PointerInvoker.INSTANCE;
               } else {
                  throw new IllegalArgumentException("Unknown return type: " + returnType);
               }
            } else {
               return new ConvertingInvoker(getNumberResultConverter(resultType), (FromNativeContext)null, new ConvertingInvoker(getNumberDataConverter(resultType.getNativeType()), (FromNativeContext)null, getNumberFunctionInvoker(resultType.getNativeType())));
            }
         } else {
            return DefaultInvokerFactory.BooleanInvoker.INSTANCE;
         }
      } else {
         return DefaultInvokerFactory.VoidInvoker.INSTANCE;
      }
   }

   private static FunctionInvoker getNumberFunctionInvoker(NativeType nativeType) {
      switch (nativeType) {
         case SCHAR:
         case UCHAR:
         case SSHORT:
         case USHORT:
         case SINT:
         case UINT:
         case SLONG:
         case ULONG:
         case SLONGLONG:
         case ULONGLONG:
         case ADDRESS:
            return NumberUtil.sizeof(nativeType) <= 4 ? DefaultInvokerFactory.IntInvoker.INSTANCE : DefaultInvokerFactory.LongInvoker.INSTANCE;
         case FLOAT:
            return DefaultInvokerFactory.Float32Invoker.INSTANCE;
         case DOUBLE:
            return DefaultInvokerFactory.Float64Invoker.INSTANCE;
         default:
            throw new UnsupportedOperationException("unsupported numeric type: " + nativeType);
      }
   }

   static Marshaller getMarshaller(ParameterType parameterType) {
      Marshaller marshaller = getMarshaller(parameterType.effectiveJavaType(), parameterType.getNativeType(), parameterType.getAnnotations());
      return (Marshaller)(parameterType.getToNativeConverter() != null ? new ToNativeConverterMarshaller(parameterType.getToNativeConverter(), parameterType.getToNativeContext(), marshaller) : marshaller);
   }

   static Marshaller getMarshaller(Class type, NativeType nativeType, Collection annotations) {
      if (Number.class.isAssignableFrom(type) || type.isPrimitive() && Number.class.isAssignableFrom(NumberUtil.getBoxedClass(type))) {
         switch (nativeType) {
            case SCHAR:
               return new Int8Marshaller(DefaultInvokerFactory.Signed8Converter.INSTANCE);
            case UCHAR:
               return new Int8Marshaller(DefaultInvokerFactory.Unsigned8Converter.INSTANCE);
            case SSHORT:
               return new Int16Marshaller(DefaultInvokerFactory.Signed16Converter.INSTANCE);
            case USHORT:
               return new Int16Marshaller(DefaultInvokerFactory.Unsigned16Converter.INSTANCE);
            case SINT:
               return new Int32Marshaller(DefaultInvokerFactory.Signed32Converter.INSTANCE);
            case UINT:
               return new Int32Marshaller(DefaultInvokerFactory.Unsigned32Converter.INSTANCE);
            case SLONG:
            case ULONG:
            case ADDRESS:
               return (Marshaller)(NumberUtil.sizeof(nativeType) == 4 ? new Int32Marshaller(getNumberDataConverter(nativeType)) : DefaultInvokerFactory.Int64Marshaller.INSTANCE);
            case SLONGLONG:
            case ULONGLONG:
               return DefaultInvokerFactory.Int64Marshaller.INSTANCE;
            case FLOAT:
               return DefaultInvokerFactory.Float32Marshaller.INSTANCE;
            case DOUBLE:
               return DefaultInvokerFactory.Float64Marshaller.INSTANCE;
            default:
               throw new IllegalArgumentException("Unsupported parameter type: " + type);
         }
      } else if (!Boolean.class.isAssignableFrom(type) && Boolean.TYPE != type) {
         if (Pointer.class.isAssignableFrom(type)) {
            return new PointerMarshaller(annotations);
         } else if (ByteBuffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller(ObjectParameterType.ComponentType.BYTE, annotations);
         } else if (ShortBuffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller(ObjectParameterType.ComponentType.SHORT, annotations);
         } else if (IntBuffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller(ObjectParameterType.ComponentType.INT, annotations);
         } else if (LongBuffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller(ObjectParameterType.ComponentType.LONG, annotations);
         } else if (FloatBuffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller(ObjectParameterType.ComponentType.FLOAT, annotations);
         } else if (DoubleBuffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller(ObjectParameterType.ComponentType.DOUBLE, annotations);
         } else if (Buffer.class.isAssignableFrom(type)) {
            return new BufferMarshaller((ObjectParameterType.ComponentType)null, annotations);
         } else if (type.isArray() && type.getComponentType() == Byte.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.BYTE, annotations);
         } else if (type.isArray() && type.getComponentType() == Short.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.SHORT, annotations);
         } else if (type.isArray() && type.getComponentType() == Integer.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.INT, annotations);
         } else if (type.isArray() && type.getComponentType() == Long.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.LONG, annotations);
         } else if (type.isArray() && type.getComponentType() == Float.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.FLOAT, annotations);
         } else if (type.isArray() && type.getComponentType() == Double.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.DOUBLE, annotations);
         } else if (type.isArray() && type.getComponentType() == Boolean.TYPE) {
            return new PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy.BOOLEAN, annotations);
         } else {
            throw new IllegalArgumentException("Unsupported parameter type: " + type);
         }
      } else {
         return DefaultInvokerFactory.BooleanMarshaller.INSTANCE;
      }
   }

   private static boolean isUnsigned(NativeType nativeType) {
      switch (nativeType) {
         case UCHAR:
         case USHORT:
         case UINT:
         case ULONG:
            return true;
         case SSHORT:
         case SINT:
         case SLONG:
         default:
            return false;
      }
   }

   static DataConverter getNumberDataConverter(NativeType nativeType) {
      switch (nativeType) {
         case SCHAR:
            return DefaultInvokerFactory.Signed8Converter.INSTANCE;
         case UCHAR:
            return DefaultInvokerFactory.Unsigned8Converter.INSTANCE;
         case SSHORT:
            return DefaultInvokerFactory.Signed16Converter.INSTANCE;
         case USHORT:
            return DefaultInvokerFactory.Unsigned16Converter.INSTANCE;
         case SINT:
            return DefaultInvokerFactory.Signed32Converter.INSTANCE;
         case UINT:
            return DefaultInvokerFactory.Unsigned32Converter.INSTANCE;
         case SLONG:
            return NumberUtil.sizeof(nativeType) == 4 ? DefaultInvokerFactory.Signed32Converter.INSTANCE : DefaultInvokerFactory.LongLongConverter.INSTANCE;
         case ULONG:
         case ADDRESS:
            return NumberUtil.sizeof(nativeType) == 4 ? DefaultInvokerFactory.Unsigned32Converter.INSTANCE : DefaultInvokerFactory.LongLongConverter.INSTANCE;
         case SLONGLONG:
         case ULONGLONG:
            return DefaultInvokerFactory.LongLongConverter.INSTANCE;
         case FLOAT:
            return DefaultInvokerFactory.FloatConverter.INSTANCE;
         case DOUBLE:
            return DefaultInvokerFactory.DoubleConverter.INSTANCE;
         default:
            throw new UnsupportedOperationException("cannot convert " + nativeType);
      }
   }

   static ResultConverter getNumberResultConverter(jnr.ffi.provider.FromNativeType fromNativeType) {
      if (Byte.class != fromNativeType.effectiveJavaType() && Byte.TYPE != fromNativeType.effectiveJavaType()) {
         if (Short.class != fromNativeType.effectiveJavaType() && Short.TYPE != fromNativeType.effectiveJavaType()) {
            if (Integer.class != fromNativeType.effectiveJavaType() && Integer.TYPE != fromNativeType.effectiveJavaType()) {
               if (Long.class != fromNativeType.effectiveJavaType() && Long.TYPE != fromNativeType.effectiveJavaType()) {
                  if (Float.class != fromNativeType.effectiveJavaType() && Float.TYPE != fromNativeType.effectiveJavaType()) {
                     if (Double.class != fromNativeType.effectiveJavaType() && Double.TYPE != fromNativeType.effectiveJavaType()) {
                        if (Address.class == fromNativeType.effectiveJavaType()) {
                           return DefaultInvokerFactory.AddressResultConverter.INSTANCE;
                        } else {
                           throw new UnsupportedOperationException("cannot convert to " + fromNativeType.effectiveJavaType());
                        }
                     } else {
                        return DefaultInvokerFactory.DoubleResultConverter.INSTANCE;
                     }
                  } else {
                     return DefaultInvokerFactory.FloatResultConverter.INSTANCE;
                  }
               } else {
                  return DefaultInvokerFactory.LongResultConverter.INSTANCE;
               }
            } else {
               return DefaultInvokerFactory.IntegerResultConverter.INSTANCE;
            }
         } else {
            return DefaultInvokerFactory.ShortResultConverter.INSTANCE;
         }
      } else {
         return DefaultInvokerFactory.ByteResultConverter.INSTANCE;
      }
   }

   static final class AddressResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new AddressResultConverter();

      public Address fromNative(Number value, FromNativeContext fromNativeContext) {
         return Address.valueOf(value.longValue());
      }
   }

   static final class DoubleResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new DoubleResultConverter();

      public Double fromNative(Number value, FromNativeContext fromNativeContext) {
         return value.doubleValue();
      }
   }

   static final class FloatResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new FloatResultConverter();

      public Float fromNative(Number value, FromNativeContext fromNativeContext) {
         return value.floatValue();
      }
   }

   static final class LongResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new LongResultConverter();

      public Long fromNative(Number value, FromNativeContext fromNativeContext) {
         return value.longValue();
      }
   }

   static final class IntegerResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new IntegerResultConverter();

      public Integer fromNative(Number value, FromNativeContext fromNativeContext) {
         return value.intValue();
      }
   }

   static final class ShortResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new ShortResultConverter();

      public Short fromNative(Number value, FromNativeContext fromNativeContext) {
         return value.shortValue();
      }
   }

   static final class ByteResultConverter extends AbstractNumberResultConverter {
      static final ResultConverter INSTANCE = new ByteResultConverter();

      public Byte fromNative(Number value, FromNativeContext fromNativeContext) {
         return value.byteValue();
      }
   }

   abstract static class AbstractNumberResultConverter implements ResultConverter {
      public final Class nativeType() {
         return Number.class;
      }
   }

   interface ResultConverter extends FromNativeConverter {
      Object fromNative(Object var1, FromNativeContext var2);
   }

   static final class BooleanConverter implements DataConverter {
      static final DataConverter INSTANCE = new BooleanConverter();

      public Boolean fromNative(Number nativeValue, FromNativeContext context) {
         return (nativeValue.intValue() & 1) != 0;
      }

      public Number toNative(Boolean value, ToNativeContext context) {
         return value ? 1 : 0;
      }

      public Class nativeType() {
         return Number.class;
      }
   }

   static final class DoubleConverter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new DoubleConverter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         return nativeValue.doubleValue();
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.doubleValue();
      }
   }

   static final class FloatConverter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new FloatConverter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         return nativeValue.floatValue();
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.floatValue();
      }
   }

   static final class LongLongConverter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new LongLongConverter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         return nativeValue.longValue();
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.longValue();
      }
   }

   static final class Unsigned32Converter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new Unsigned32Converter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         long value = (long)nativeValue.intValue();
         return value < 0L ? (value & 2147483647L) + 2147483648L : value;
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.longValue() & 4294967295L;
      }
   }

   static final class Signed32Converter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new Signed32Converter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         return nativeValue.intValue();
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.intValue();
      }
   }

   static final class Unsigned16Converter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new Unsigned16Converter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         int value = nativeValue.shortValue();
         return value < 0 ? (value & 32767) + '耀' : value;
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.intValue() & '\uffff';
      }
   }

   static final class Signed16Converter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new Signed16Converter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         return nativeValue.shortValue();
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.shortValue();
      }
   }

   static final class Unsigned8Converter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new Unsigned8Converter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         int value = nativeValue.byteValue();
         return value < 0 ? (value & 127) + 128 : value;
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.intValue() & '\uffff';
      }
   }

   static final class Signed8Converter extends NumberDataConverter {
      static final NumberDataConverter INSTANCE = new Signed8Converter();

      public Number fromNative(Number nativeValue, FromNativeContext context) {
         return nativeValue.byteValue();
      }

      public Number toNative(Number value, ToNativeContext context) {
         return value.byteValue();
      }
   }

   abstract static class NumberDataConverter implements DataConverter {
      public final Class nativeType() {
         return Number.class;
      }
   }

   static class ToNativeConverterMarshaller implements Marshaller {
      private final ToNativeConverter converter;
      private final ToNativeContext context;
      private final Marshaller marshaller;
      private final boolean isPostInvokeRequired;

      public ToNativeConverterMarshaller(ToNativeConverter toNativeConverter, ToNativeContext toNativeContext, Marshaller marshaller) {
         this.converter = toNativeConverter;
         this.context = toNativeContext;
         this.marshaller = marshaller;
         this.isPostInvokeRequired = this.converter instanceof ToNativeConverter.PostInvocation;
      }

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, final Object parameter) {
         final Object nativeValue = this.converter.toNative(parameter, this.context);
         this.marshaller.marshal(session, buffer, nativeValue);
         if (this.isPostInvokeRequired) {
            session.addPostInvoke(new InvocationSession.PostInvoke() {
               public void postInvoke() {
                  ((ToNativeConverter.PostInvocation)ToNativeConverterMarshaller.this.converter).postInvoke(parameter, nativeValue, ToNativeConverterMarshaller.this.context);
               }
            });
         } else {
            session.keepAlive(nativeValue);
         }

      }
   }

   static class BufferMarshaller implements Marshaller {
      private final ObjectParameterType.ComponentType componentType;
      private final int flags;

      BufferMarshaller(ObjectParameterType.ComponentType componentType, Collection annotations) {
         this.componentType = componentType;
         this.flags = AsmUtil.getNativeArrayFlags(annotations);
      }

      public final void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         ObjectParameterStrategy strategy = this.componentType != null ? AsmRuntime.bufferParameterStrategy((Buffer)parameter, this.componentType) : AsmRuntime.pointerParameterStrategy((Buffer)parameter);
         buffer.putObject(parameter, strategy, this.flags);
      }
   }

   static class PrimitiveArrayMarshaller implements Marshaller {
      private final PrimitiveArrayParameterStrategy strategy;
      private final int flags;

      protected PrimitiveArrayMarshaller(PrimitiveArrayParameterStrategy strategy, Collection annotations) {
         this.strategy = strategy;
         this.flags = AsmUtil.getNativeArrayFlags(annotations);
      }

      public final void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putObject(parameter, (ObjectParameterStrategy)(parameter != null ? this.strategy : NullObjectParameterStrategy.NULL), this.flags);
      }
   }

   static class PointerMarshaller implements Marshaller {
      private final int flags;

      PointerMarshaller(Collection annotations) {
         this.flags = AsmUtil.getNativeArrayFlags(annotations);
      }

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putObject(parameter, AsmRuntime.pointerParameterStrategy((Pointer)parameter), this.flags);
      }
   }

   static class Float64Marshaller implements Marshaller {
      static final Marshaller INSTANCE = new Float64Marshaller();

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putDouble(((Number)parameter).doubleValue());
      }
   }

   static class Float32Marshaller implements Marshaller {
      static final Marshaller INSTANCE = new Float32Marshaller();

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putFloat(((Number)parameter).floatValue());
      }
   }

   static class Int64Marshaller implements Marshaller {
      static final Marshaller INSTANCE = new Int64Marshaller();

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putLong(((Number)parameter).longValue());
      }
   }

   static class Int32Marshaller implements Marshaller {
      private final ToNativeConverter toNativeConverter;

      Int32Marshaller(ToNativeConverter toNativeConverter) {
         this.toNativeConverter = toNativeConverter;
      }

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putInt(((Number)this.toNativeConverter.toNative((Number)parameter, (ToNativeContext)null)).intValue());
      }
   }

   static class Int16Marshaller implements Marshaller {
      private final ToNativeConverter toNativeConverter;

      Int16Marshaller(ToNativeConverter toNativeConverter) {
         this.toNativeConverter = toNativeConverter;
      }

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putShort(((Number)this.toNativeConverter.toNative((Number)parameter, (ToNativeContext)null)).intValue());
      }
   }

   static class Int8Marshaller implements Marshaller {
      private final ToNativeConverter toNativeConverter;

      Int8Marshaller(ToNativeConverter toNativeConverter) {
         this.toNativeConverter = toNativeConverter;
      }

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putByte(((Number)this.toNativeConverter.toNative((Number)parameter, (ToNativeContext)null)).intValue());
      }
   }

   static class BooleanMarshaller implements Marshaller {
      static final Marshaller INSTANCE = new BooleanMarshaller();

      public void marshal(InvocationSession session, HeapInvocationBuffer buffer, Object parameter) {
         buffer.putInt((Boolean)parameter ? 1 : 0);
      }
   }

   static class PointerInvoker extends BaseInvoker {
      static final FunctionInvoker INSTANCE = new PointerInvoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return MemoryUtil.newPointer(runtime, invoker.invokeAddress(function, buffer));
      }
   }

   static class Float64Invoker extends BaseInvoker {
      static final FunctionInvoker INSTANCE = new Float64Invoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return invoker.invokeDouble(function, buffer);
      }
   }

   static class Float32Invoker extends BaseInvoker {
      static final FunctionInvoker INSTANCE = new Float32Invoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return invoker.invokeFloat(function, buffer);
      }
   }

   static class LongInvoker extends BaseInvoker {
      static final FunctionInvoker INSTANCE = new LongInvoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return invoker.invokeLong(function, buffer);
      }
   }

   static class IntInvoker extends BaseInvoker {
      static final FunctionInvoker INSTANCE = new IntInvoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return invoker.invokeInt(function, buffer);
      }
   }

   static class BooleanInvoker extends BaseInvoker {
      static FunctionInvoker INSTANCE = new BooleanInvoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return invoker.invokeInt(function, buffer) != 0;
      }
   }

   static class VoidInvoker extends BaseInvoker {
      static FunctionInvoker INSTANCE = new VoidInvoker();

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         invoker.invokeInt(function, buffer);
         return null;
      }
   }

   static class ConvertingInvoker extends BaseInvoker {
      private final FromNativeConverter fromNativeConverter;
      private final FromNativeContext fromNativeContext;
      private final FunctionInvoker nativeInvoker;

      public ConvertingInvoker(FromNativeConverter converter, FromNativeContext context, FunctionInvoker nativeInvoker) {
         this.fromNativeConverter = converter;
         this.fromNativeContext = context;
         this.nativeInvoker = nativeInvoker;
      }

      public final Object invoke(Runtime runtime, Function function, HeapInvocationBuffer buffer) {
         return this.fromNativeConverter.fromNative(this.nativeInvoker.invoke(runtime, function, buffer), this.fromNativeContext);
      }
   }

   abstract static class BaseInvoker implements FunctionInvoker {
      static com.kenai.jffi.Invoker invoker = com.kenai.jffi.Invoker.getInstance();
   }

   interface FunctionInvoker {
      Object invoke(Runtime var1, Function var2, HeapInvocationBuffer var3);
   }

   interface Marshaller {
      void marshal(InvocationSession var1, HeapInvocationBuffer var2, Object var3);
   }

   private static final class FunctionNotFoundInvoker implements Invoker {
      private final Method method;
      private final String functionName;

      private FunctionNotFoundInvoker(Method method, String functionName) {
         this.method = method;
         this.functionName = functionName;
      }

      public Object invoke(Object self, Object[] parameters) {
         throw new UnsatisfiedLinkError(String.format("native method '%s' not found for method %s", this.functionName, this.method));
      }

      // $FF: synthetic method
      FunctionNotFoundInvoker(Method x0, String x1, Object x2) {
         this(x0, x1);
      }
   }

   private static final class SynchronizedInvoker implements Invoker {
      private final Invoker invoker;

      public SynchronizedInvoker(Invoker invoker) {
         this.invoker = invoker;
      }

      public Object invoke(Object self, Object[] parameters) {
         synchronized(self) {
            return this.invoker.invoke(self, parameters);
         }
      }
   }

   static class DefaultInvoker implements Invoker {
      protected final Runtime runtime;
      final Function function;
      final FunctionInvoker functionInvoker;
      final Marshaller[] marshallers;
      final NativeLibrary nativeLibrary;

      DefaultInvoker(Runtime runtime, NativeLibrary nativeLibrary, Function function, FunctionInvoker invoker, Marshaller[] marshallers) {
         this.runtime = runtime;
         this.nativeLibrary = nativeLibrary;
         this.function = function;
         this.functionInvoker = invoker;
         this.marshallers = marshallers;
      }

      public final Object invoke(Object self, Object[] parameters) {
         InvocationSession session = new InvocationSession();
         HeapInvocationBuffer buffer = new HeapInvocationBuffer(this.function.getCallContext());

         Object var9;
         try {
            if (parameters != null) {
               for(int i = 0; i < parameters.length; ++i) {
                  this.marshallers[i].marshal(session, buffer, parameters[i]);
               }
            }

            var9 = this.functionInvoker.invoke(this.runtime, this.function, buffer);
         } finally {
            session.finish();
         }

         return var9;
      }
   }

   static class VariadicInvoker implements Invoker {
      private final Runtime runtime;
      private final FunctionInvoker functionInvoker;
      private final SignatureTypeMapper typeMapper;
      private final ParameterType[] fixedParameterTypes;
      private final long functionAddress;
      private final SigType resultType;
      private final boolean requiresErrno;
      private final CallingConvention callingConvention;

      VariadicInvoker(Runtime runtime, FunctionInvoker functionInvoker, SignatureTypeMapper typeMapper, ParameterType[] fixedParameterTypes, long functionAddress, SigType resultType, boolean requiresErrno, CallingConvention callingConvention) {
         this.runtime = runtime;
         this.functionInvoker = functionInvoker;
         this.typeMapper = typeMapper;
         this.fixedParameterTypes = fixedParameterTypes;
         this.functionAddress = functionAddress;
         this.resultType = resultType;
         this.requiresErrno = requiresErrno;
         this.callingConvention = callingConvention;
      }

      public final Object invoke(Object self, Object[] parameters) {
         Object[] varParam = (Object[])((Object[])parameters[parameters.length - 1]);
         ParameterType[] argTypes = new ParameterType[this.fixedParameterTypes.length + varParam.length];
         System.arraycopy(this.fixedParameterTypes, 0, argTypes, 0, this.fixedParameterTypes.length - 1);
         Object[] variableArgs = new Object[varParam.length + 1];
         int variableArgsCount = 0;
         List paramAnnotations = new ArrayList();
         Object[] var8 = varParam;
         int var9 = varParam.length;

         Object arg;
         for(int var10 = 0; var10 < var9; ++var10) {
            arg = var8[var10];
            if (arg instanceof Class && Annotation.class.isAssignableFrom((Class)arg)) {
               paramAnnotations.add((Class)arg);
            } else {
               ToNativeConverter toNativeConverter = null;
               Collection annos = getAnnotations(paramAnnotations);
               paramAnnotations.clear();
               ToNativeContext toNativeContext = new SimpleNativeContext(this.runtime, annos);
               Class argClass;
               if (arg != null) {
                  ToNativeType toNativeType = this.typeMapper.getToNativeType(DefaultSignatureType.create(arg.getClass(), (ToNativeContext)toNativeContext), toNativeContext);
                  toNativeConverter = toNativeType == null ? null : toNativeType.getToNativeConverter();
                  argClass = toNativeConverter == null ? arg.getClass() : toNativeConverter.nativeType();
                  variableArgs[variableArgsCount] = arg;
               } else {
                  argClass = Pointer.class;
                  variableArgs[variableArgsCount] = arg;
               }

               argTypes[this.fixedParameterTypes.length + variableArgsCount - 1] = new ParameterType(argClass, Types.getType(this.runtime, argClass, annos).getNativeType(), annos, toNativeConverter, new SimpleNativeContext(this.runtime, annos));
               ++variableArgsCount;
            }
         }

         argTypes[this.fixedParameterTypes.length + variableArgsCount - 1] = new ParameterType(Pointer.class, Types.getType(this.runtime, Pointer.class, Collections.emptyList()).getNativeType(), Collections.emptyList(), (ToNativeConverter)null, new SimpleNativeContext(this.runtime, Collections.emptyList()));
         variableArgs[variableArgsCount] = null;
         ++variableArgsCount;
         Function function = new Function(this.functionAddress, InvokerUtil.getCallContext(this.resultType, argTypes, variableArgsCount + this.fixedParameterTypes.length - 1, this.callingConvention, this.requiresErrno));
         HeapInvocationBuffer buffer = new HeapInvocationBuffer(function.getCallContext());
         InvocationSession session = new InvocationSession();

         try {
            int i;
            if (parameters != null) {
               for(i = 0; i < parameters.length - 1; ++i) {
                  DefaultInvokerFactory.getMarshaller(argTypes[i]).marshal(session, buffer, parameters[i]);
               }
            }

            for(i = 0; i < variableArgsCount; ++i) {
               DefaultInvokerFactory.getMarshaller(argTypes[i + this.fixedParameterTypes.length - 1]).marshal(session, buffer, variableArgs[i]);
            }

            arg = this.functionInvoker.invoke(this.runtime, function, buffer);
            return arg;
         } finally {
            session.finish();
         }
      }

      private static Collection getAnnotations(Collection klasses) {
         List ret = new ArrayList();
         Iterator var2 = klasses.iterator();

         while(true) {
            while(var2.hasNext()) {
               Class klass = (Class)var2.next();
               if (klass.getAnnotation(Meta.class) != null) {
                  Annotation[] var4 = klass.getAnnotations();
                  int var5 = var4.length;

                  for(int var6 = 0; var6 < var5; ++var6) {
                     Annotation anno = var4[var6];
                     if (!anno.annotationType().getName().startsWith("java") && !Meta.class.equals(anno.annotationType())) {
                        ret.add(anno);
                     }
                  }
               } else {
                  ret.add(AnnotationProxy.newProxy(klass));
               }
            }

            return ret;
         }
      }
   }
}
