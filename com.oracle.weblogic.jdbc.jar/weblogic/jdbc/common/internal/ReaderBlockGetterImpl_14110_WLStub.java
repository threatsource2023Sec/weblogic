package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class ReaderBlockGetterImpl_14110_WLStub extends Stub implements StubInfoIntf, ReaderBlockGetter {
   // $FF: synthetic field
   private static Class class$weblogic$jdbc$common$internal$ReaderBlockGetter;
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

   public ReaderBlockGetterImpl_14110_WLStub(StubInfo var1) {
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

   public final void close() {
      try {
         Object[] var1 = new Object[0];
         this.ror.invoke(this, md0, var1, m[0]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final void close(int var1) {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         this.ror.invoke(this, md1, var2, m[1]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$jdbc$common$internal$ReaderBlockGetter == null ? (class$weblogic$jdbc$common$internal$ReaderBlockGetter = class$("weblogic.jdbc.common.internal.ReaderBlockGetter")) : class$weblogic$jdbc$common$internal$ReaderBlockGetter, false, true, false, false, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         initialized = true;
      }
   }

   public final char[] getBlock(int var1) throws RemoteException {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         return (char[])this.ror.invoke(this, md2, var2, m[2]);
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

   public final int getBlockSize() {
      try {
         Object[] var1 = new Object[0];
         return (Integer)this.ror.invoke(this, md3, var1, m[3]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final Reader getReader(int var1) {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         return (Reader)this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void mark(int var1, int var2) throws IOException {
      try {
         Object[] var3 = new Object[]{new Integer(var1), new Integer(var2)};
         this.ror.invoke(this, md5, var3, m[5]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (IOException var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final boolean markSupported(int var1) {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         return (Boolean)this.ror.invoke(this, md6, var2, m[6]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final boolean ready(int var1) throws IOException {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         return (Boolean)this.ror.invoke(this, md7, var2, m[7]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (IOException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final int register(Reader var1, int var2) {
      try {
         Object[] var3 = new Object[]{var1, new Integer(var2)};
         return (Integer)this.ror.invoke(this, md8, var3, m[8]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final void reset(int var1) throws IOException {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         this.ror.invoke(this, md9, var2, m[9]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (IOException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }
}
