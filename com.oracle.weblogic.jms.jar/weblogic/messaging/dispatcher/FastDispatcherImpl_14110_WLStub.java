package weblogic.messaging.dispatcher;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class FastDispatcherImpl_14110_WLStub extends Stub implements StubInfoIntf, DispatcherRemote, DispatcherOneWay {
   // $FF: synthetic field
   private static Class class$weblogic$messaging$dispatcher$DispatcherRemote;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private static RuntimeMethodDescriptor md6;
   private static RuntimeMethodDescriptor md7;
   private static RuntimeMethodDescriptor md8;
   private static RuntimeMethodDescriptor md9;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public FastDispatcherImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final void dispatchAsyncFuture(Request var1, AsyncResult var2) throws DispatcherException, RemoteException {
      try {
         Object[] var3 = new Object[]{var1, var2};
         this.ror.invoke(this, md0, var3, m[0]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (DispatcherException var6) {
         throw var6;
      } catch (RemoteException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RemoteRuntimeException("Unexpected Exception", var8);
      }
   }

   public final void dispatchAsyncFutureWithId(Request var1, AsyncResult var2, int var3) throws DispatcherException, RemoteException {
      try {
         Object[] var4 = new Object[]{var1, var2, new Integer(var3)};
         this.ror.invoke(this, md1, var4, m[1]);
      } catch (Error var5) {
         throw var5;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (DispatcherException var7) {
         throw var7;
      } catch (RemoteException var8) {
         throw var8;
      } catch (Throwable var9) {
         throw new RemoteRuntimeException("Unexpected Exception", var9);
      }
   }

   public final void dispatchAsyncTranFuture(Request var1, AsyncResult var2) throws DispatcherException, RemoteException {
      try {
         Object[] var3 = new Object[]{var1, var2};
         this.ror.invoke(this, md2, var3, m[2]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (DispatcherException var6) {
         throw var6;
      } catch (RemoteException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RemoteRuntimeException("Unexpected Exception", var8);
      }
   }

   public final void dispatchAsyncTranFutureWithId(Request var1, AsyncResult var2, int var3) throws DispatcherException, RemoteException {
      try {
         Object[] var4 = new Object[]{var1, var2, new Integer(var3)};
         this.ror.invoke(this, md3, var4, m[3]);
      } catch (Error var5) {
         throw var5;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (DispatcherException var7) {
         throw var7;
      } catch (RemoteException var8) {
         throw var8;
      } catch (Throwable var9) {
         throw new RemoteRuntimeException("Unexpected Exception", var9);
      }
   }

   public final void dispatchOneWay(Request var1) throws RemoteException {
      try {
         Object[] var2 = new Object[]{var1};
         this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (RemoteException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final void dispatchOneWayWithId(Request var1, int var2) throws RemoteException {
      try {
         Object[] var3 = new Object[]{var1, new Integer(var2)};
         this.ror.invoke(this, md5, var3, m[5]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (RemoteException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Response dispatchSyncFuture(Request var1) throws DispatcherException, RemoteException {
      try {
         Object[] var2 = new Object[]{var1};
         return (Response)this.ror.invoke(this, md6, var2, m[6]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (DispatcherException var5) {
         throw var5;
      } catch (RemoteException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Response dispatchSyncNoTranFuture(Request var1) throws DispatcherException, RemoteException {
      try {
         Object[] var2 = new Object[]{var1};
         return (Response)this.ror.invoke(this, md7, var2, m[7]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (DispatcherException var5) {
         throw var5;
      } catch (RemoteException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Response dispatchSyncTranFuture(Request var1) throws DispatcherException, RemoteException {
      try {
         Object[] var2 = new Object[]{var1};
         return (Response)this.ror.invoke(this, md8, var2, m[8]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (DispatcherException var5) {
         throw var5;
      } catch (RemoteException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Response dispatchSyncTranFutureWithId(Request var1, int var2) throws DispatcherException, RemoteException {
      try {
         Object[] var3 = new Object[]{var1, new Integer(var2)};
         return (Response)this.ror.invoke(this, md9, var3, m[9]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (DispatcherException var6) {
         throw var6;
      } catch (RemoteException var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RemoteRuntimeException("Unexpected Exception", var8);
      }
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, true, true, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$messaging$dispatcher$DispatcherRemote == null ? (class$weblogic$messaging$dispatcher$DispatcherRemote = class$("weblogic.messaging.dispatcher.DispatcherRemote")) : class$weblogic$messaging$dispatcher$DispatcherRemote, false, true, false, false, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }
}
