package jnr.ffi.provider.jffi;

import java.lang.reflect.Method;
import java.util.EnumSet;
import java.util.Set;
import jnr.ffi.NativeLong;
import jnr.ffi.Pointer;
import jnr.ffi.Struct;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.byref.ByReference;
import jnr.ffi.mapper.AbstractSignatureTypeMapper;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.FromNativeTypes;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.ToNativeType;
import jnr.ffi.mapper.ToNativeTypes;
import jnr.ffi.provider.ParameterFlags;
import jnr.ffi.provider.converters.BoxedBooleanArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedByteArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedDoubleArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedFloatArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedIntegerArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedLong32ArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedLong64ArrayParameterConverter;
import jnr.ffi.provider.converters.BoxedShortArrayParameterConverter;
import jnr.ffi.provider.converters.ByReferenceParameterConverter;
import jnr.ffi.provider.converters.CharSequenceArrayParameterConverter;
import jnr.ffi.provider.converters.CharSequenceParameterConverter;
import jnr.ffi.provider.converters.EnumConverter;
import jnr.ffi.provider.converters.EnumSetConverter;
import jnr.ffi.provider.converters.Long32ArrayParameterConverter;
import jnr.ffi.provider.converters.NativeLong32ArrayParameterConverter;
import jnr.ffi.provider.converters.NativeLong64ArrayParameterConverter;
import jnr.ffi.provider.converters.NativeLongConverter;
import jnr.ffi.provider.converters.Pointer32ArrayParameterConverter;
import jnr.ffi.provider.converters.Pointer64ArrayParameterConverter;
import jnr.ffi.provider.converters.StringBufferParameterConverter;
import jnr.ffi.provider.converters.StringBuilderParameterConverter;
import jnr.ffi.provider.converters.StringResultConverter;
import jnr.ffi.provider.converters.StructArrayParameterConverter;
import jnr.ffi.provider.converters.StructByReferenceToNativeConverter;

final class InvokerTypeMapper extends AbstractSignatureTypeMapper implements SignatureTypeMapper {
   private final NativeClosureManager closureManager;
   private final AsmClassLoader classLoader;
   private final StructByReferenceResultConverterFactory structResultConverterFactory;

   public InvokerTypeMapper(NativeClosureManager closureManager, AsmClassLoader classLoader, boolean asmEnabled) {
      this.closureManager = closureManager;
      this.classLoader = classLoader;
      this.structResultConverterFactory = new StructByReferenceResultConverterFactory(classLoader, asmEnabled);
   }

   public FromNativeConverter getFromNativeConverter(SignatureType signatureType, FromNativeContext fromNativeContext) {
      if (Enum.class.isAssignableFrom(signatureType.getDeclaredType())) {
         return EnumConverter.getInstance(signatureType.getDeclaredType().asSubclass(Enum.class));
      } else if (Struct.class.isAssignableFrom(signatureType.getDeclaredType())) {
         return this.structResultConverterFactory.get(signatureType.getDeclaredType().asSubclass(Struct.class), fromNativeContext);
      } else if (this.closureManager != null && isDelegate(signatureType.getDeclaredType())) {
         return ClosureFromNativeConverter.getInstance(fromNativeContext.getRuntime(), signatureType, this.classLoader, this);
      } else if (NativeLong.class == signatureType.getDeclaredType()) {
         return NativeLongConverter.getInstance();
      } else if (String.class != signatureType.getDeclaredType() && CharSequence.class != signatureType.getDeclaredType()) {
         FromNativeConverter converter;
         return (Set.class == signatureType.getDeclaredType() || EnumSet.class == signatureType.getDeclaredType()) && (converter = EnumSetConverter.getFromNativeConverter(signatureType, fromNativeContext)) != null ? converter : null;
      } else {
         return StringResultConverter.getInstance(fromNativeContext);
      }
   }

   public ToNativeConverter getToNativeConverter(SignatureType signatureType, ToNativeContext context) {
      Class javaType = signatureType.getDeclaredType();
      if (Enum.class.isAssignableFrom(javaType)) {
         return EnumConverter.getInstance(javaType.asSubclass(Enum.class));
      } else {
         ToNativeConverter converter;
         if (Set.class.isAssignableFrom(javaType) && (converter = EnumSetConverter.getToNativeConverter(signatureType, context)) != null) {
            return converter;
         } else if (isDelegate(javaType)) {
            return this.closureManager.newClosureSite(javaType);
         } else if (ByReference.class.isAssignableFrom(javaType)) {
            return ByReferenceParameterConverter.getInstance(context);
         } else if (Struct.class.isAssignableFrom(javaType)) {
            return StructByReferenceToNativeConverter.getInstance(context);
         } else if (NativeLong.class.isAssignableFrom(javaType)) {
            return NativeLongConverter.getInstance();
         } else if (StringBuilder.class.isAssignableFrom(javaType)) {
            return StringBuilderParameterConverter.getInstance(ParameterFlags.parse(context.getAnnotations()), context);
         } else if (StringBuffer.class.isAssignableFrom(javaType)) {
            return StringBufferParameterConverter.getInstance(ParameterFlags.parse(context.getAnnotations()), context);
         } else if (CharSequence.class.isAssignableFrom(javaType)) {
            return CharSequenceParameterConverter.getInstance(context);
         } else if (Byte[].class.isAssignableFrom(javaType)) {
            return BoxedByteArrayParameterConverter.getInstance(context);
         } else if (Short[].class.isAssignableFrom(javaType)) {
            return BoxedShortArrayParameterConverter.getInstance(context);
         } else if (Integer[].class.isAssignableFrom(javaType)) {
            return BoxedIntegerArrayParameterConverter.getInstance(context);
         } else if (Long[].class.isAssignableFrom(javaType)) {
            return Types.getType(context.getRuntime(), javaType.getComponentType(), context.getAnnotations()).size() == 4 ? BoxedLong32ArrayParameterConverter.getInstance(context) : BoxedLong64ArrayParameterConverter.getInstance(context);
         } else if (NativeLong[].class.isAssignableFrom(javaType)) {
            return Types.getType(context.getRuntime(), javaType.getComponentType(), context.getAnnotations()).size() == 4 ? NativeLong32ArrayParameterConverter.getInstance(context) : NativeLong64ArrayParameterConverter.getInstance(context);
         } else if (Float[].class.isAssignableFrom(javaType)) {
            return BoxedFloatArrayParameterConverter.getInstance(context);
         } else if (Double[].class.isAssignableFrom(javaType)) {
            return BoxedDoubleArrayParameterConverter.getInstance(context);
         } else if (Boolean[].class.isAssignableFrom(javaType)) {
            return BoxedBooleanArrayParameterConverter.getInstance(context);
         } else if (javaType.isArray() && Pointer.class.isAssignableFrom(javaType.getComponentType())) {
            return context.getRuntime().addressSize() == 4 ? Pointer32ArrayParameterConverter.getInstance(context) : Pointer64ArrayParameterConverter.getInstance(context);
         } else if (long[].class.isAssignableFrom(javaType) && Types.getType(context.getRuntime(), javaType.getComponentType(), context.getAnnotations()).size() == 4) {
            return Long32ArrayParameterConverter.getInstance(context);
         } else if (javaType.isArray() && Struct.class.isAssignableFrom(javaType.getComponentType())) {
            return StructArrayParameterConverter.getInstance(context, javaType.getComponentType());
         } else {
            return javaType.isArray() && CharSequence.class.isAssignableFrom(javaType.getComponentType()) ? CharSequenceArrayParameterConverter.getInstance(context) : null;
         }
      }
   }

   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      return FromNativeTypes.create(this.getFromNativeConverter(type, context));
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      return ToNativeTypes.create(this.getToNativeConverter(type, context));
   }

   private static boolean isDelegate(Class klass) {
      Method[] var1 = klass.getMethods();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Method m = var1[var3];
         if (m.isAnnotationPresent(Delegate.class)) {
            return true;
         }
      }

      return false;
   }
}
