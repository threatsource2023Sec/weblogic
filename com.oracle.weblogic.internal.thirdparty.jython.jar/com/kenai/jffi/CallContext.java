package com.kenai.jffi;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CallContext {
   final long contextAddress;
   private final int parameterCount;
   private final int rawParameterSize;
   final Type returnType;
   final Type[] parameterTypes;
   final long[] parameterTypeHandles;
   final int flags;
   volatile int disposed;
   final AtomicIntegerFieldUpdater UPDATER;
   private final Foreign foreign;

   public static CallContext getCallContext(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno) {
      return CallContextCache.getInstance().getCallContext(returnType, parameterTypes, convention, saveErrno);
   }

   public static CallContext getCallContext(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno, boolean faultProtect) {
      return CallContextCache.getInstance().getCallContext(returnType, parameterTypes, convention, saveErrno, faultProtect);
   }

   public CallContext(Type returnType, Type... parameterTypes) {
      this(returnType, parameterTypes, CallingConvention.DEFAULT, true);
   }

   public CallContext(Type returnType, Type[] parameterTypes, CallingConvention convention) {
      this(returnType, parameterTypes, convention, true);
   }

   public CallContext(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno) {
      this(returnType, parameterTypes, convention, saveErrno, false);
   }

   CallContext(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno, boolean faultProtect) {
      this.UPDATER = AtomicIntegerFieldUpdater.newUpdater(CallContext.class, "disposed");
      this.foreign = Foreign.getInstance();
      int flags = (!saveErrno ? 2 : 0) | (convention == CallingConvention.STDCALL ? 1 : 0) | (faultProtect ? 4 : 0);
      long h = this.foreign.newCallContext(returnType.handle(), Type.nativeHandles(parameterTypes), flags);
      if (h == 0L) {
         throw new RuntimeException("Failed to create native function");
      } else {
         this.contextAddress = h;
         this.returnType = returnType;
         this.parameterTypes = (Type[])parameterTypes.clone();
         this.parameterCount = parameterTypes.length;
         this.rawParameterSize = this.foreign.getCallContextRawParameterSize(h);
         this.parameterTypeHandles = Type.nativeHandles(parameterTypes);
         this.flags = flags;
      }
   }

   public final int getParameterCount() {
      return this.parameterCount;
   }

   public final int getRawParameterSize() {
      return this.rawParameterSize;
   }

   final long getAddress() {
      return this.contextAddress;
   }

   public final Type getReturnType() {
      return this.returnType;
   }

   public final Type getParameterType(int index) {
      return this.parameterTypes[index];
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         CallContext that = (CallContext)o;
         if (this.flags != that.flags) {
            return false;
         } else if (this.parameterCount != that.parameterCount) {
            return false;
         } else if (this.rawParameterSize != that.rawParameterSize) {
            return false;
         } else if (!Arrays.equals(this.parameterTypes, that.parameterTypes)) {
            return false;
         } else {
            return this.returnType.equals(that.returnType);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = this.parameterCount;
      result = 31 * result + this.returnType.hashCode();
      result = 31 * result + Arrays.hashCode(this.parameterTypes);
      result = 31 * result + this.flags;
      return result;
   }

   /** @deprecated */
   @Deprecated
   public final void dispose() {
   }

   protected void finalize() throws Throwable {
      try {
         int disposed = this.UPDATER.getAndSet(this, 1);
         if (disposed == 0 && this.contextAddress != 0L) {
            this.foreign.freeCallContext(this.contextAddress);
         }
      } catch (Throwable var5) {
         Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "exception when freeing " + this.getClass() + ": %s", var5.getLocalizedMessage());
      } finally {
         super.finalize();
      }

   }
}
