package org.python.modules.jffi;

import com.kenai.jffi.CallingConvention;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import org.python.core.PyObject;

class JITCompiler {
   private final Map handles = new HashMap();
   private final Map classes = Collections.synchronizedMap(new WeakHashMap());
   private final ReferenceQueue referenceQueue = new ReferenceQueue();
   private final JITHandle failedHandle;

   JITCompiler() {
      this.failedHandle = new JITHandle(this, new JITSignature(NativeType.VOID, new NativeType[0], false, new boolean[0], CallingConvention.DEFAULT, false), true);
   }

   public static JITCompiler getInstance() {
      return JITCompiler.SingletonHolder.INSTANCE;
   }

   private void cleanup() {
      HandleRef ref;
      while((ref = (HandleRef)this.referenceQueue.poll()) != null) {
         this.handles.remove(ref.signature);
      }

   }

   JITHandle getHandle(PyObject resultType, PyObject[] parameterTypes, CallingConvention convention, boolean ignoreErrno) {
      boolean hasResultConverter = !(resultType instanceof CType.Builtin);
      if (resultType instanceof CType.Builtin) {
         NativeType nativeResultType = ((CType)resultType).getNativeType();
         NativeType[] nativeParameterTypes = new NativeType[parameterTypes.length];
         boolean[] hasParameterConverter = new boolean[parameterTypes.length];

         for(int i = 0; i < hasParameterConverter.length; ++i) {
            CType parameterType = CType.typeOf(parameterTypes[i]);
            if (!(parameterType instanceof CType.Builtin)) {
               return this.failedHandle;
            }

            nativeParameterTypes[i] = parameterType.getNativeType();
            hasParameterConverter[i] = !(parameterType instanceof CType.Builtin);
         }

         JITSignature jitSignature = new JITSignature(nativeResultType, nativeParameterTypes, hasResultConverter, hasParameterConverter, convention, ignoreErrno);
         synchronized(this) {
            this.cleanup();
            HandleRef ref = (HandleRef)this.handles.get(jitSignature);
            JITHandle handle = ref != null ? (JITHandle)ref.get() : null;
            if (handle == null) {
               handle = new JITHandle(this, jitSignature, false);
               this.handles.put(jitSignature, new HandleRef(handle, jitSignature, this.referenceQueue));
            }

            return handle;
         }
      } else {
         return this.failedHandle;
      }
   }

   void registerClass(JITHandle handle, Class klass) {
      this.classes.put(klass, handle);
   }

   private static final class HandleRef extends WeakReference {
      JITSignature signature;

      public HandleRef(JITHandle handle, JITSignature signature, ReferenceQueue refqueue) {
         super(handle, refqueue);
         this.signature = signature;
      }
   }

   private static class SingletonHolder {
      private static final JITCompiler INSTANCE = new JITCompiler();
   }
}
