package com.kenai.jffi;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CallContextCache {
   private final Map contextCache;
   private final ReferenceQueue contextReferenceQueue;

   public static CallContextCache getInstance() {
      return CallContextCache.SingletonHolder.INSTANCE;
   }

   private CallContextCache() {
      this.contextCache = new ConcurrentHashMap();
      this.contextReferenceQueue = new ReferenceQueue();
   }

   public final CallContext getCallContext(Type returnType, Type[] parameterTypes, CallingConvention convention) {
      return this.getCallContext(returnType, parameterTypes, convention, true, false);
   }

   public final CallContext getCallContext(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno) {
      return this.getCallContext(returnType, parameterTypes, convention, saveErrno, false);
   }

   public final CallContext getCallContext(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno, boolean faultProtect) {
      Signature signature = new Signature(returnType, parameterTypes, convention, saveErrno, faultProtect);
      CallContextRef ref = (CallContextRef)this.contextCache.get(signature);
      CallContext ctx;
      if (ref != null && (ctx = (CallContext)ref.get()) != null) {
         return ctx;
      } else {
         while((ref = (CallContextRef)this.contextReferenceQueue.poll()) != null) {
            this.contextCache.remove(ref.signature);
         }

         ctx = new CallContext(returnType, (Type[])parameterTypes.clone(), convention, saveErrno, faultProtect);
         this.contextCache.put(signature, new CallContextRef(signature, ctx, this.contextReferenceQueue));
         return ctx;
      }
   }

   // $FF: synthetic method
   CallContextCache(Object x0) {
      this();
   }

   private static final class Signature {
      private final Type returnType;
      private final Type[] parameterTypes;
      private final CallingConvention convention;
      private final boolean saveErrno;
      private final boolean faultProtect;
      private int hashCode = 0;

      public Signature(Type returnType, Type[] parameterTypes, CallingConvention convention, boolean saveErrno, boolean faultProtect) {
         if (returnType != null && parameterTypes != null) {
            this.returnType = returnType;
            this.parameterTypes = parameterTypes;
            this.convention = convention;
            this.saveErrno = saveErrno;
            this.faultProtect = faultProtect;
         } else {
            throw new NullPointerException("null return type or parameter types array");
         }
      }

      public boolean equals(Object obj) {
         if (obj != null && this.getClass() == obj.getClass()) {
            Signature other = (Signature)obj;
            if (this.convention == other.convention && this.saveErrno == other.saveErrno && this.faultProtect == other.faultProtect) {
               if (this.returnType != other.returnType && !this.returnType.equals(other.returnType)) {
                  return false;
               } else if (this.parameterTypes.length != other.parameterTypes.length) {
                  return false;
               } else {
                  for(int i = 0; i < this.parameterTypes.length; ++i) {
                     if (this.parameterTypes[i] != other.parameterTypes[i] && (this.parameterTypes[i] == null || !this.parameterTypes[i].equals(other.parameterTypes[i]))) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      private final int calculateHashCode() {
         int hash = 7;
         hash = 53 * hash + (this.returnType != null ? this.returnType.hashCode() : 0);
         int paramHash = 1;

         for(int i = 0; i < this.parameterTypes.length; ++i) {
            paramHash = 31 * paramHash + this.parameterTypes[i].hashCode();
         }

         hash = 53 * hash + paramHash;
         hash = 53 * hash + this.convention.hashCode();
         hash = 53 * hash + (this.saveErrno ? 1 : 0);
         hash = 53 * hash + (this.faultProtect ? 1 : 0);
         return hash;
      }

      public int hashCode() {
         return this.hashCode != 0 ? this.hashCode : (this.hashCode = this.calculateHashCode());
      }
   }

   private static final class CallContextRef extends SoftReference {
      final Signature signature;

      public CallContextRef(Signature signature, CallContext ctx, ReferenceQueue queue) {
         super(ctx, queue);
         this.signature = signature;
      }
   }

   private static final class SingletonHolder {
      static final CallContextCache INSTANCE = new CallContextCache();
   }
}
