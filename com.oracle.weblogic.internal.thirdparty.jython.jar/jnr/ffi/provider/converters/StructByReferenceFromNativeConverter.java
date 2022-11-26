package jnr.ffi.provider.converters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.mapper.FromNativeContext;
import jnr.ffi.mapper.FromNativeConverter;

public class StructByReferenceFromNativeConverter implements FromNativeConverter {
   private final Constructor constructor;

   public static FromNativeConverter getInstance(Class structClass, FromNativeContext toNativeContext) {
      try {
         return new StructByReferenceFromNativeConverter(structClass.getConstructor(Runtime.class));
      } catch (NoSuchMethodException var3) {
         throw new RuntimeException(structClass.getName() + " has no constructor that accepts jnr.ffi.Runtime");
      } catch (Throwable var4) {
         throw new RuntimeException(var4);
      }
   }

   StructByReferenceFromNativeConverter(Constructor constructor) {
      this.constructor = constructor;
   }

   public Struct fromNative(Pointer nativeValue, FromNativeContext context) {
      try {
         Struct s = (Struct)this.constructor.newInstance(context.getRuntime());
         s.useMemory(nativeValue);
         return s;
      } catch (InstantiationException var4) {
         throw new RuntimeException(var4);
      } catch (IllegalAccessException var5) {
         throw new RuntimeException(var5);
      } catch (InvocationTargetException var6) {
         throw new RuntimeException(var6);
      }
   }

   public Class nativeType() {
      return Pointer.class;
   }
}
