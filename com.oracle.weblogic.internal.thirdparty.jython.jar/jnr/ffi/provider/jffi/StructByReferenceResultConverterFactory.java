package jnr.ffi.provider.jffi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;
import jnr.ffi.provider.converters.StructByReferenceFromNativeConverter;

final class StructByReferenceResultConverterFactory {
   private final Map converters = new ConcurrentHashMap();
   private final AsmClassLoader classLoader;
   private final boolean asmEnabled;

   public StructByReferenceResultConverterFactory(AsmClassLoader classLoader, boolean asmEnabled) {
      this.classLoader = classLoader;
      this.asmEnabled = asmEnabled;
   }

   public final FromNativeConverter get(Class structClass, FromNativeContext fromNativeContext) {
      FromNativeConverter converter = (FromNativeConverter)this.converters.get(structClass);
      if (converter == null) {
         synchronized(this.converters) {
            if ((converter = (FromNativeConverter)this.converters.get(structClass)) == null) {
               this.converters.put(structClass, converter = this.createConverter(fromNativeContext.getRuntime(), structClass, fromNativeContext));
            }
         }
      }

      return converter;
   }

   private FromNativeConverter createConverter(Runtime runtime, Class structClass, FromNativeContext fromNativeContext) {
      return (FromNativeConverter)(this.asmEnabled ? AsmStructByReferenceFromNativeConverter.newStructByReferenceConverter(runtime, structClass, 0, this.classLoader) : StructByReferenceFromNativeConverter.getInstance(structClass, fromNativeContext));
   }
}
