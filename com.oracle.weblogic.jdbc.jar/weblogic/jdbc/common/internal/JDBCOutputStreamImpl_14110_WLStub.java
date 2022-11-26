package weblogic.jdbc.common.internal;

import java.io.IOException;
import java.lang.reflect.Method;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class JDBCOutputStreamImpl_14110_WLStub extends Stub implements StubInfoIntf, JDBCOutputStream {
   // $FF: synthetic field
   private static Class class$weblogic$jdbc$common$internal$JDBCOutputStream;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md3;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public JDBCOutputStreamImpl_14110_WLStub(StubInfo var1) {
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

   public final void close() throws IOException {
      try {
         Object[] var1 = new Object[0];
         this.ror.invoke(this, md0, var1, m[0]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$jdbc$common$internal$JDBCOutputStream == null ? (class$weblogic$jdbc$common$internal$JDBCOutputStream = class$("weblogic.jdbc.common.internal.JDBCOutputStream")) : class$weblogic$jdbc$common$internal$JDBCOutputStream, false, true, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$jdbc$common$internal$JDBCOutputStream == null ? (class$weblogic$jdbc$common$internal$JDBCOutputStream = class$("weblogic.jdbc.common.internal.JDBCOutputStream")) : class$weblogic$jdbc$common$internal$JDBCOutputStream, false, true, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$jdbc$common$internal$JDBCOutputStream == null ? (class$weblogic$jdbc$common$internal$JDBCOutputStream = class$("weblogic.jdbc.common.internal.JDBCOutputStream")) : class$weblogic$jdbc$common$internal$JDBCOutputStream, false, true, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$jdbc$common$internal$JDBCOutputStream == null ? (class$weblogic$jdbc$common$internal$JDBCOutputStream = class$("weblogic.jdbc.common.internal.JDBCOutputStream")) : class$weblogic$jdbc$common$internal$JDBCOutputStream, false, true, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         initialized = true;
      }
   }

   public final void flush() throws IOException {
      try {
         Object[] var1 = new Object[0];
         this.ror.invoke(this, md1, var1, m[1]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (IOException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void write(int var1) throws IOException {
      try {
         Object[] var2 = new Object[]{new Integer(var1)};
         this.ror.invoke(this, md2, var2, m[2]);
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

   public final void writeBlock(byte[] var1) throws IOException {
      try {
         Object[] var2 = new Object[]{var1};
         this.ror.invoke(this, md3, var2, m[3]);
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
