package jnr.ffi.provider.jffi;

import java.util.IdentityHashMap;
import java.util.Map;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.CachingTypeMapper;
import jnr.ffi.mapper.CompositeTypeMapper;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ClosureManager;

final class NativeClosureManager implements ClosureManager {
   private volatile Map factories = new IdentityHashMap();
   private final Runtime runtime;
   private final SignatureTypeMapper typeMapper;
   private final AsmClassLoader classLoader;

   NativeClosureManager(Runtime runtime, SignatureTypeMapper typeMapper, AsmClassLoader classLoader) {
      this.runtime = runtime;
      this.typeMapper = new CompositeTypeMapper(new SignatureTypeMapper[]{typeMapper, new CachingTypeMapper(new ClosureTypeMapper())});
      this.classLoader = classLoader;
   }

   NativeClosureFactory getClosureFactory(Class closureClass) {
      NativeClosureFactory factory = (NativeClosureFactory)this.factories.get(closureClass);
      return factory != null ? factory : this.initClosureFactory(closureClass);
   }

   public Object newClosure(Class closureClass, Object instance) {
      NativeClosureFactory factory = (NativeClosureFactory)this.factories.get(closureClass);
      if (factory != null) {
      }

      return null;
   }

   public final Pointer getClosurePointer(Class closureClass, Object instance) {
      return this.getClosureFactory(closureClass).getClosureReference(instance).getPointer();
   }

   synchronized NativeClosureFactory initClosureFactory(Class closureClass) {
      NativeClosureFactory factory = (NativeClosureFactory)this.factories.get(closureClass);
      if (factory != null) {
         return factory;
      } else {
         factory = NativeClosureFactory.newClosureFactory(this.runtime, closureClass, this.typeMapper, this.classLoader);
         Map factories = new IdentityHashMap();
         factories.putAll(this.factories);
         factories.put(closureClass, factory);
         this.factories = factories;
         return factory;
      }
   }

   ToNativeConverter newClosureSite(Class closureClass) {
      return new ClosureSite(this.getClosureFactory(closureClass));
   }

   @ToNativeConverter.NoContext
   public static final class ClosureSite implements ToNativeConverter {
      private final NativeClosureFactory factory;
      private NativeClosureFactory.ClosureReference closureReference;

      private ClosureSite(NativeClosureFactory factory) {
         this.closureReference = null;
         this.factory = factory;
      }

      public Pointer toNative(Object value, ToNativeContext context) {
         if (value == null) {
            return null;
         } else if (value instanceof ClosureFromNativeConverter.AbstractClosurePointer) {
            return (ClosureFromNativeConverter.AbstractClosurePointer)value;
         } else {
            NativeClosureFactory.ClosureReference ref = this.closureReference;
            if (ref != null && ref.getCallable() == value) {
               return ref.getPointer();
            } else {
               ref = this.factory.getClosureReference(value);
               if (this.closureReference == null || this.closureReference.get() == null) {
                  this.closureReference = ref;
               }

               return ref.getPointer();
            }
         }
      }

      public Class nativeType() {
         return Pointer.class;
      }

      // $FF: synthetic method
      ClosureSite(NativeClosureFactory x0, Object x1) {
         this(x0);
      }
   }
}
