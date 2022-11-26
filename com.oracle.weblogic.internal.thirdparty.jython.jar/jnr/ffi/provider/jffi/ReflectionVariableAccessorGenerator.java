package jnr.ffi.provider.jffi;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Variable;
import jnr.ffi.mapper.DataConverter;
import jnr.ffi.mapper.DefaultSignatureType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.ToNativeType;

class ReflectionVariableAccessorGenerator {
   static Variable createVariableAccessor(Runtime runtime, Method method, long symbolAddress, SignatureTypeMapper typeMapper, Collection annotations) {
      Type variableType = ((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments()[0];
      if (!(variableType instanceof Class)) {
         throw new IllegalArgumentException("unsupported variable class: " + variableType);
      } else {
         Class javaType = (Class)variableType;
         SimpleNativeContext context = new SimpleNativeContext(runtime, annotations);
         SignatureType signatureType = DefaultSignatureType.create(javaType, (FromNativeContext)context);
         FromNativeType mappedFromNativeType = typeMapper.getFromNativeType(signatureType, context);
         FromNativeConverter fromNativeConverter = mappedFromNativeType != null ? mappedFromNativeType.getFromNativeConverter() : null;
         ToNativeType mappedToNativeType = typeMapper.getToNativeType(signatureType, context);
         ToNativeConverter toNativeConverter = mappedToNativeType != null ? mappedToNativeType.getToNativeConverter() : null;
         Class boxedType = toNativeConverter != null ? toNativeConverter.nativeType() : javaType;
         NativeType nativeType = Types.getType(runtime, boxedType, annotations).getNativeType();
         jnr.ffi.provider.ToNativeType toNativeType = new jnr.ffi.provider.ToNativeType(javaType, nativeType, annotations, toNativeConverter, (ToNativeContext)null);
         jnr.ffi.provider.FromNativeType fromNativeType = new jnr.ffi.provider.FromNativeType(javaType, nativeType, annotations, fromNativeConverter, (FromNativeContext)null);
         Pointer memory = MemoryUtil.newPointer(runtime, symbolAddress);
         Variable variable = getNativeVariableAccessor(memory, toNativeType, fromNativeType);
         return toNativeType.getToNativeConverter() != null ? getConvertingVariable(variable, toNativeType.getToNativeConverter(), fromNativeType.getFromNativeConverter()) : variable;
      }
   }

   static Variable getConvertingVariable(Variable nativeVariable, ToNativeConverter toNativeConverter, FromNativeConverter fromNativeConverter) {
      if ((toNativeConverter == null || fromNativeConverter != null) && (toNativeConverter != null || fromNativeConverter == null)) {
         return new ConvertingVariable(nativeVariable, toNativeConverter, fromNativeConverter);
      } else {
         throw new UnsupportedOperationException("convertible types must have both a ToNativeConverter and a FromNativeConverter");
      }
   }

   static Variable getNativeVariableAccessor(Pointer memory, jnr.ffi.provider.ToNativeType toNativeType, jnr.ffi.provider.FromNativeType fromNativeType) {
      if (Pointer.class == toNativeType.effectiveJavaType()) {
         return new PointerVariable(memory);
      } else if (Number.class.isAssignableFrom(toNativeType.effectiveJavaType())) {
         return new NumberVariable(memory, getPointerOp(toNativeType.getNativeType()), DefaultInvokerFactory.getNumberDataConverter(toNativeType.getNativeType()), DefaultInvokerFactory.getNumberResultConverter(fromNativeType));
      } else {
         throw new UnsupportedOperationException("unsupported variable type: " + toNativeType.effectiveJavaType());
      }
   }

   private static PointerOp getPointerOp(NativeType nativeType) {
      switch (nativeType) {
         case SCHAR:
         case UCHAR:
            return ReflectionVariableAccessorGenerator.Int8PointerOp.INSTANCE;
         case SSHORT:
         case USHORT:
            return ReflectionVariableAccessorGenerator.Int16PointerOp.INSTANCE;
         case SINT:
         case UINT:
            return ReflectionVariableAccessorGenerator.Int32PointerOp.INSTANCE;
         case SLONGLONG:
         case ULONGLONG:
            return ReflectionVariableAccessorGenerator.Int64PointerOp.INSTANCE;
         case SLONG:
         case ULONG:
         case ADDRESS:
            return NumberUtil.sizeof(nativeType) == 4 ? ReflectionVariableAccessorGenerator.Int32PointerOp.INSTANCE : ReflectionVariableAccessorGenerator.Int64PointerOp.INSTANCE;
         case FLOAT:
            return ReflectionVariableAccessorGenerator.FloatPointerOp.INSTANCE;
         case DOUBLE:
            return ReflectionVariableAccessorGenerator.DoublePointerOp.INSTANCE;
         default:
            throw new UnsupportedOperationException("cannot convert " + nativeType);
      }
   }

   private static final class DoublePointerOp implements PointerOp {
      static final PointerOp INSTANCE = new DoublePointerOp();

      public Number get(Pointer memory) {
         return memory.getFloat(0L);
      }

      public void put(Pointer memory, Number value) {
         memory.putFloat(0L, value.floatValue());
      }
   }

   private static final class FloatPointerOp implements PointerOp {
      static final PointerOp INSTANCE = new FloatPointerOp();

      public Number get(Pointer memory) {
         return memory.getFloat(0L);
      }

      public void put(Pointer memory, Number value) {
         memory.putFloat(0L, value.floatValue());
      }
   }

   private static final class Int64PointerOp implements PointerOp {
      static final PointerOp INSTANCE = new Int64PointerOp();

      public Number get(Pointer memory) {
         return memory.getLongLong(0L);
      }

      public void put(Pointer memory, Number value) {
         memory.putLongLong(0L, value.longValue());
      }
   }

   private static final class Int32PointerOp implements PointerOp {
      static final PointerOp INSTANCE = new Int32PointerOp();

      public Number get(Pointer memory) {
         return memory.getInt(0L);
      }

      public void put(Pointer memory, Number value) {
         memory.putInt(0L, value.intValue());
      }
   }

   private static final class Int16PointerOp implements PointerOp {
      static final PointerOp INSTANCE = new Int16PointerOp();

      public Number get(Pointer memory) {
         return memory.getShort(0L);
      }

      public void put(Pointer memory, Number value) {
         memory.putShort(0L, value.shortValue());
      }
   }

   private static final class Int8PointerOp implements PointerOp {
      static final PointerOp INSTANCE = new Int8PointerOp();

      public Number get(Pointer memory) {
         return memory.getByte(0L);
      }

      public void put(Pointer memory, Number value) {
         memory.putByte(0L, value.byteValue());
      }
   }

   private interface PointerOp {
      Object get(Pointer var1);

      void put(Pointer var1, Object var2);
   }

   private static final class PointerVariable extends AbstractVariable {
      private PointerVariable(Pointer memory) {
         super(memory);
      }

      public Pointer get() {
         return this.memory.getPointer(0L);
      }

      public void set(Pointer value) {
         if (value != null) {
            this.memory.putPointer(0L, value);
         } else {
            this.memory.putAddress(0L, 0L);
         }

      }

      // $FF: synthetic method
      PointerVariable(Pointer x0, Object x1) {
         this(x0);
      }
   }

   private static final class NumberVariable extends AbstractVariable {
      private final DataConverter dataConverter;
      private final DefaultInvokerFactory.ResultConverter resultConverter;
      private final PointerOp pointerOp;

      private NumberVariable(Pointer memory, PointerOp pointerOp, DataConverter dataConverter, DefaultInvokerFactory.ResultConverter resultConverter) {
         super(memory);
         this.pointerOp = pointerOp;
         this.dataConverter = dataConverter;
         this.resultConverter = resultConverter;
      }

      public Number get() {
         return (Number)this.resultConverter.fromNative(this.dataConverter.fromNative(this.pointerOp.get(this.memory), (FromNativeContext)null), (FromNativeContext)null);
      }

      public void set(Number value) {
         this.pointerOp.put(this.memory, this.dataConverter.toNative(value, (ToNativeContext)null));
      }

      // $FF: synthetic method
      NumberVariable(Pointer x0, PointerOp x1, DataConverter x2, DefaultInvokerFactory.ResultConverter x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static final class ConvertingVariable implements Variable {
      private final Variable variable;
      private final ToNativeConverter toNativeConverter;
      private final FromNativeConverter fromNativeConverter;

      private ConvertingVariable(Variable variable, ToNativeConverter toNativeConverter, FromNativeConverter fromNativeConverter) {
         this.variable = variable;
         this.toNativeConverter = toNativeConverter;
         this.fromNativeConverter = fromNativeConverter;
      }

      public Object get() {
         return this.fromNativeConverter.fromNative(this.variable.get(), (FromNativeContext)null);
      }

      public void set(Object value) {
         this.variable.set(this.toNativeConverter.toNative(value, (ToNativeContext)null));
      }

      // $FF: synthetic method
      ConvertingVariable(Variable x0, ToNativeConverter x1, FromNativeConverter x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   private abstract static class AbstractVariable implements Variable {
      protected final Pointer memory;

      protected AbstractVariable(Pointer memory) {
         this.memory = memory;
      }
   }
}
