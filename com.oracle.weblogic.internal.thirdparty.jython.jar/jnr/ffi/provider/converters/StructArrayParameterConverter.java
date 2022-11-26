package jnr.ffi.provider.converters;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.Struct;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.DelegatingMemoryIO;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class StructArrayParameterConverter implements ToNativeConverter {
   protected final Runtime runtime;
   protected final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext, Class structClass) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return (ToNativeConverter)(!ParameterFlags.isOut(parameterFlags) ? new StructArrayParameterConverter(toNativeContext.getRuntime(), parameterFlags) : new Out(toNativeContext.getRuntime(), structClass.asSubclass(Struct.class), parameterFlags));
   }

   StructArrayParameterConverter(Runtime runtime, int parameterFlags) {
      this.runtime = runtime;
      this.parameterFlags = parameterFlags;
   }

   public Class nativeType() {
      return Pointer.class;
   }

   public Pointer toNative(Struct[] structs, ToNativeContext context) {
      if (structs == null) {
         return null;
      } else {
         Pointer memory = Struct.getMemory(structs[0], this.parameterFlags);
         if (!(memory instanceof DelegatingMemoryIO)) {
            throw new RuntimeException("Struct array must be backed by contiguous array");
         } else {
            return ((DelegatingMemoryIO)memory).getDelegatedMemoryIO();
         }
      }
   }

   private static int align(int offset, int align) {
      return offset + align - 1 & ~(align - 1);
   }

   public static final class Out extends StructArrayParameterConverter implements ToNativeConverter.PostInvocation {
      private final Constructor constructor;

      Out(Runtime runtime, Class structClass, int parameterFlags) {
         super(runtime, parameterFlags);

         Constructor cons;
         try {
            cons = structClass.getConstructor(Runtime.class);
         } catch (NoSuchMethodException var6) {
            throw new RuntimeException(structClass.getName() + " has no constructor that accepts jnr.ffi.Runtime");
         } catch (Throwable var7) {
            throw new RuntimeException(var7);
         }

         this.constructor = cons;
      }

      public void postInvoke(Struct[] structs, Pointer primitive, ToNativeContext context) {
         if (structs != null && primitive != null) {
            try {
               int off = 0;

               for(int i = 0; i < structs.length; ++i) {
                  structs[i] = (Struct)this.constructor.newInstance(this.runtime);
                  int structSize = StructArrayParameterConverter.align(Struct.size(structs[i]), Struct.alignment(structs[i]));
                  structs[i].useMemory(primitive.slice((long)(off = StructArrayParameterConverter.align(off, Struct.alignment(structs[i]))), (long)structSize));
                  off += structSize;
               }
            } catch (InstantiationException var7) {
               throw new RuntimeException(var7);
            } catch (IllegalAccessException var8) {
               throw new RuntimeException(var8);
            } catch (InvocationTargetException var9) {
               throw new RuntimeException(var9);
            }
         }

      }
   }
}
