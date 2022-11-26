package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.CallContextCache;
import com.kenai.jffi.Type;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import jnr.ffi.CallingConvention;
import jnr.ffi.LibraryOption;
import jnr.ffi.NativeType;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.StdCall;
import jnr.ffi.mapper.DefaultSignatureType;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.mapper.FromNativeType;
import jnr.ffi.mapper.MethodParameterContext;
import jnr.ffi.mapper.SignatureType;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.mapper.ToNativeType;
import jnr.ffi.provider.ParameterType;
import jnr.ffi.provider.ResultType;
import jnr.ffi.provider.SigType;
import jnr.ffi.util.Annotations;

final class InvokerUtil {
   static final Map jffiTypes;

   public static CallingConvention getCallingConvention(Map libraryOptions) {
      Object convention = libraryOptions.get(LibraryOption.CallingConvention);
      if (convention instanceof com.kenai.jffi.CallingConvention) {
         return com.kenai.jffi.CallingConvention.DEFAULT.equals(convention) ? CallingConvention.DEFAULT : CallingConvention.STDCALL;
      } else {
         if (convention instanceof CallingConvention) {
            switch ((CallingConvention)convention) {
               case DEFAULT:
                  return CallingConvention.DEFAULT;
               case STDCALL:
                  return CallingConvention.STDCALL;
            }
         } else if (convention != null) {
            throw new IllegalArgumentException("unknown calling convention: " + convention);
         }

         return CallingConvention.DEFAULT;
      }
   }

   public static CallingConvention getCallingConvention(Class interfaceClass, Map options) {
      return interfaceClass.isAnnotationPresent(StdCall.class) ? CallingConvention.STDCALL : getCallingConvention(options);
   }

   public static boolean hasAnnotation(Collection annotations, Class annotationClass) {
      Iterator var2 = annotations.iterator();

      Annotation a;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         a = (Annotation)var2.next();
      } while(!annotationClass.isInstance(a));

      return true;
   }

   static Type jffiType(NativeType jnrType) {
      Type jffiType = (Type)jffiTypes.get(jnrType);
      if (jffiType != null) {
         return jffiType;
      } else {
         throw new IllegalArgumentException("unsupported parameter type: " + jnrType);
      }
   }

   static NativeType nativeType(jnr.ffi.Type jnrType) {
      return jnrType.getNativeType();
   }

   static Collection getAnnotations(FromNativeType fromNativeType) {
      return fromNativeType != null ? ConverterMetaData.getAnnotations(fromNativeType.getFromNativeConverter()) : Annotations.EMPTY_ANNOTATIONS;
   }

   static Collection getAnnotations(ToNativeType toNativeType) {
      return toNativeType != null ? ConverterMetaData.getAnnotations(toNativeType.getToNativeConverter()) : Annotations.EMPTY_ANNOTATIONS;
   }

   static ResultType getResultType(Runtime runtime, Class type, Collection annotations, FromNativeConverter fromNativeConverter, FromNativeContext fromNativeContext) {
      Collection converterAnnotations = ConverterMetaData.getAnnotations(fromNativeConverter);
      Collection allAnnotations = Annotations.mergeAnnotations(annotations, converterAnnotations);
      NativeType nativeType = getMethodResultNativeType(runtime, fromNativeConverter != null ? fromNativeConverter.nativeType() : type, allAnnotations);
      boolean useContext = fromNativeConverter != null && !hasAnnotation(converterAnnotations, FromNativeConverter.NoContext.class);
      return new ResultType(type, nativeType, allAnnotations, fromNativeConverter, useContext ? fromNativeContext : null);
   }

   static ResultType getResultType(Runtime runtime, Class type, Collection annotations, FromNativeType fromNativeType, FromNativeContext fromNativeContext) {
      Collection converterAnnotations = getAnnotations(fromNativeType);
      Collection allAnnotations = Annotations.mergeAnnotations(annotations, converterAnnotations);
      FromNativeConverter fromNativeConverter = fromNativeType != null ? fromNativeType.getFromNativeConverter() : null;
      NativeType nativeType = getMethodResultNativeType(runtime, fromNativeConverter != null ? fromNativeConverter.nativeType() : type, allAnnotations);
      boolean useContext = fromNativeConverter != null && !hasAnnotation(converterAnnotations, FromNativeConverter.NoContext.class);
      return new ResultType(type, nativeType, allAnnotations, fromNativeConverter, useContext ? fromNativeContext : null);
   }

   private static ParameterType getParameterType(Runtime runtime, Class type, Collection annotations, ToNativeConverter toNativeConverter, ToNativeContext toNativeContext) {
      NativeType nativeType = getMethodParameterNativeType(runtime, toNativeConverter != null ? toNativeConverter.nativeType() : type, annotations);
      return new ParameterType(type, nativeType, annotations, toNativeConverter, toNativeContext);
   }

   private static ParameterType getParameterType(Runtime runtime, Class type, Collection annotations, ToNativeType toNativeType, ToNativeContext toNativeContext) {
      ToNativeConverter toNativeConverter = toNativeType != null ? toNativeType.getToNativeConverter() : null;
      NativeType nativeType = getMethodParameterNativeType(runtime, toNativeConverter != null ? toNativeConverter.nativeType() : type, annotations);
      return new ParameterType(type, nativeType, annotations, toNativeConverter, toNativeContext);
   }

   static ParameterType[] getParameterTypes(Runtime runtime, SignatureTypeMapper typeMapper, Method m) {
      Class[] javaParameterTypes = m.getParameterTypes();
      Annotation[][] parameterAnnotations = m.getParameterAnnotations();
      ParameterType[] parameterTypes = new ParameterType[javaParameterTypes.length];

      for(int pidx = 0; pidx < javaParameterTypes.length; ++pidx) {
         Collection annotations = Annotations.sortedAnnotationCollection(parameterAnnotations[pidx]);
         ToNativeContext toNativeContext = new MethodParameterContext(runtime, m, pidx, annotations);
         SignatureType signatureType = DefaultSignatureType.create(javaParameterTypes[pidx], (ToNativeContext)toNativeContext);
         ToNativeType toNativeType = typeMapper.getToNativeType(signatureType, toNativeContext);
         ToNativeConverter toNativeConverter = toNativeType != null ? toNativeType.getToNativeConverter() : null;
         Collection converterAnnotations = ConverterMetaData.getAnnotations(toNativeConverter);
         Collection allAnnotations = Annotations.mergeAnnotations(annotations, converterAnnotations);
         boolean contextRequired = toNativeConverter != null && !hasAnnotation(converterAnnotations, ToNativeConverter.NoContext.class);
         parameterTypes[pidx] = getParameterType(runtime, javaParameterTypes[pidx], allAnnotations, (ToNativeConverter)toNativeConverter, contextRequired ? toNativeContext : null);
      }

      return parameterTypes;
   }

   static CallContext getCallContext(SigType resultType, SigType[] parameterTypes, CallingConvention convention, boolean requiresErrno) {
      return getCallContext(resultType, parameterTypes, parameterTypes.length, convention, requiresErrno);
   }

   static CallContext getCallContext(SigType resultType, SigType[] parameterTypes, int paramTypesLength, CallingConvention convention, boolean requiresErrno) {
      Type[] nativeParamTypes = new Type[paramTypesLength];

      for(int i = 0; i < nativeParamTypes.length; ++i) {
         nativeParamTypes[i] = jffiType(parameterTypes[i].getNativeType());
      }

      return CallContextCache.getInstance().getCallContext(jffiType(resultType.getNativeType()), nativeParamTypes, jffiConvention(convention), requiresErrno);
   }

   public static CallingConvention getNativeCallingConvention(Method m) {
      return !m.isAnnotationPresent(StdCall.class) && !m.getDeclaringClass().isAnnotationPresent(StdCall.class) ? CallingConvention.DEFAULT : CallingConvention.STDCALL;
   }

   static NativeType getMethodParameterNativeType(Runtime runtime, Class parameterClass, Collection annotations) {
      return Types.getType(runtime, parameterClass, annotations).getNativeType();
   }

   static NativeType getMethodResultNativeType(Runtime runtime, Class resultClass, Collection annotations) {
      return Types.getType(runtime, resultClass, annotations).getNativeType();
   }

   public static final com.kenai.jffi.CallingConvention jffiConvention(CallingConvention callingConvention) {
      return callingConvention == CallingConvention.DEFAULT ? com.kenai.jffi.CallingConvention.DEFAULT : com.kenai.jffi.CallingConvention.STDCALL;
   }

   static {
      Map m = new EnumMap(NativeType.class);
      m.put(NativeType.VOID, Type.VOID);
      m.put(NativeType.SCHAR, Type.SCHAR);
      m.put(NativeType.UCHAR, Type.UCHAR);
      m.put(NativeType.SSHORT, Type.SSHORT);
      m.put(NativeType.USHORT, Type.USHORT);
      m.put(NativeType.SINT, Type.SINT);
      m.put(NativeType.UINT, Type.UINT);
      m.put(NativeType.SLONG, Type.SLONG);
      m.put(NativeType.ULONG, Type.ULONG);
      m.put(NativeType.SLONGLONG, Type.SLONG_LONG);
      m.put(NativeType.ULONGLONG, Type.ULONG_LONG);
      m.put(NativeType.FLOAT, Type.FLOAT);
      m.put(NativeType.DOUBLE, Type.DOUBLE);
      m.put(NativeType.ADDRESS, Type.POINTER);
      jffiTypes = Collections.unmodifiableMap(m);
   }
}
