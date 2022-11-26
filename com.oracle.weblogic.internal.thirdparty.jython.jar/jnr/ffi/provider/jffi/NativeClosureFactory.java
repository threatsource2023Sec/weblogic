package jnr.ffi.provider.jffi;

import com.kenai.jffi.CallContext;
import com.kenai.jffi.Closure;
import com.kenai.jffi.ClosureMagazine;
import com.kenai.jffi.ClosureManager;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.Delegate;
import jnr.ffi.mapper.SignatureTypeMapper;
import jnr.ffi.provider.FromNativeType;
import jnr.ffi.provider.ToNativeType;
import jnr.ffi.util.ref.FinalizableWeakReference;

public final class NativeClosureFactory {
   private final Runtime runtime;
   private final ConcurrentMap closures = new ConcurrentHashMap();
   private final CallContext callContext;
   private final NativeClosureProxy.Factory closureProxyFactory;
   private final ConcurrentLinkedQueue freeQueue = new ConcurrentLinkedQueue();
   private ClosureMagazine currentMagazine;

   protected NativeClosureFactory(Runtime runtime, CallContext callContext, NativeClosureProxy.Factory closureProxyFactory) {
      this.runtime = runtime;
      this.closureProxyFactory = closureProxyFactory;
      this.callContext = callContext;
   }

   static NativeClosureFactory newClosureFactory(Runtime runtime, Class closureClass, SignatureTypeMapper typeMapper, AsmClassLoader classLoader) {
      Method callMethod = null;
      Method[] var5 = closureClass.getMethods();
      int var6 = var5.length;

      int i;
      for(i = 0; i < var6; ++i) {
         Method m = var5[i];
         if (m.isAnnotationPresent(Delegate.class) && Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())) {
            callMethod = m;
            break;
         }
      }

      if (callMethod == null) {
         throw new NoSuchMethodError("no public non-static delegate method defined in " + closureClass.getName());
      } else {
         Class[] parameterTypes = callMethod.getParameterTypes();
         FromNativeType[] parameterSigTypes = new FromNativeType[parameterTypes.length];

         for(i = 0; i < parameterTypes.length; ++i) {
            parameterSigTypes[i] = ClosureUtil.getParameterType(runtime, callMethod, i, typeMapper);
         }

         ToNativeType resultType = ClosureUtil.getResultType(runtime, callMethod, typeMapper);
         return new NativeClosureFactory(runtime, InvokerUtil.getCallContext(resultType, parameterSigTypes, InvokerUtil.getNativeCallingConvention(callMethod), false), NativeClosureProxy.newProxyFactory(runtime, callMethod, resultType, parameterSigTypes, classLoader));
      }
   }

   private void expunge(ClosureReference ref, Integer key) {
      if (ref.next != null || !this.closures.remove(key, ref)) {
         synchronized(this.closures) {
            ClosureReference clref = (ClosureReference)this.closures.get(key);

            for(ClosureReference prev = clref; clref != null; clref = clref.next) {
               if (clref == ref) {
                  if (prev != clref) {
                     prev.next = clref.next;
                  } else if (clref.next != null) {
                     this.closures.replace(key, clref, clref.next);
                  } else {
                     this.closures.remove(key, clref);
                  }
                  break;
               }

               prev = clref;
            }

         }
      }
   }

   private void recycle(NativeClosurePointer ptr) {
      this.freeQueue.add(ptr);
   }

   NativeClosurePointer allocateClosurePointer() {
      NativeClosurePointer closurePointer = (NativeClosurePointer)this.freeQueue.poll();
      if (closurePointer != null) {
         return closurePointer;
      } else {
         NativeClosureProxy proxy = this.closureProxyFactory.newClosureProxy();
         Closure.Handle closureHandle = null;
         synchronized(this) {
            do {
               if (this.currentMagazine == null || (closureHandle = this.currentMagazine.allocate(proxy)) == null) {
                  this.currentMagazine = ClosureManager.getInstance().newClosureMagazine(this.callContext, this.closureProxyFactory.getInvokeMethod());
               }
            } while(closureHandle == null);
         }

         return new NativeClosurePointer(this.runtime, closureHandle, proxy);
      }
   }

   NativeClosurePointer newClosure(Object callable, Integer key) {
      return this.newClosureReference(callable, key).pointer;
   }

   ClosureReference newClosureReference(Object callable, Integer key) {
      NativeClosurePointer ptr = this.allocateClosurePointer();
      ClosureReference ref = new ClosureReference(callable, key, this, ptr);
      ptr.proxy.closureReference = ref;
      if (this.closures.putIfAbsent(key, ref) == null) {
         return ref;
      } else {
         synchronized(this.closures) {
            do {
               ref.next = (ClosureReference)this.closures.get(key);
            } while((ref.next != null || this.closures.putIfAbsent(key, ref) != null) && !this.closures.replace(key, ref.next, ref));

            return ref;
         }
      }
   }

   ClosureReference getClosureReference(Object callable) {
      Integer key = System.identityHashCode(callable);
      ClosureReference ref = (ClosureReference)this.closures.get(key);
      if (ref != null) {
         if (ref.getCallable() == callable) {
            return ref;
         }

         synchronized(this.closures) {
            while((ref = ref.next) != null) {
               if (ref.getCallable() == callable) {
                  return ref;
               }
            }
         }
      }

      return this.newClosureReference(callable, key);
   }

   final class ClosureReference extends FinalizableWeakReference {
      volatile ClosureReference next;
      private final NativeClosureFactory factory;
      private final NativeClosurePointer pointer;
      private final Integer key;

      private ClosureReference(Object referent, Integer key, NativeClosureFactory factory, NativeClosurePointer pointer) {
         super(referent, NativeFinalizer.getInstance().getFinalizerQueue());
         this.factory = factory;
         this.key = key;
         this.pointer = pointer;
      }

      public void finalizeReferent() {
         this.clear();
         this.factory.expunge(this, this.key);
         this.factory.recycle(this.pointer);
      }

      Object getCallable() {
         return this.get();
      }

      Pointer getPointer() {
         return this.pointer;
      }

      // $FF: synthetic method
      ClosureReference(Object x1, Integer x2, NativeClosureFactory x3, NativeClosurePointer x4, Object x5) {
         this(x1, x2, x3, x4);
      }
   }
}
