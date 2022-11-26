package weblogic.jndi.remote;

import java.lang.reflect.Method;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.transaction.Transaction;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class AttributesWrapper_14110_WLStub extends Stub implements StubInfoIntf, RemoteAttributes {
   // $FF: synthetic field
   private static Class class$weblogic$jndi$remote$RemoteAttributes;
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
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public AttributesWrapper_14110_WLStub(StubInfo var1) {
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

   public final Object clone() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (Object)this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$jndi$remote$RemoteAttributes == null ? (class$weblogic$jndi$remote$RemoteAttributes = class$("weblogic.jndi.remote.RemoteAttributes")) : class$weblogic$jndi$remote$RemoteAttributes, false, false, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         initialized = true;
      }
   }

   public final Attribute get(String var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Attribute var14;
      try {
         Object[] var3 = new Object[]{var1};
         var14 = (Attribute)this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var14;
   }

   public final NamingEnumeration getAll() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingEnumeration var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (NamingEnumeration)this.ror.invoke(this, md2, var2, m[2]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   public final NamingEnumeration getIDs() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingEnumeration var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (NamingEnumeration)this.ror.invoke(this, md3, var2, m[3]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final boolean isCaseIgnored() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (Boolean)this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }

   public final Attribute put(String var1, Object var2) {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Attribute var15;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var15 = (Attribute)this.ror.invoke(this, md5, var4, m[5]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var15;
   }

   public final Attribute put(Attribute var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Attribute var14;
      try {
         Object[] var3 = new Object[]{var1};
         var14 = (Attribute)this.ror.invoke(this, md6, var3, m[6]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var14;
   }

   public final Attribute remove(String var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Attribute var14;
      try {
         Object[] var3 = new Object[]{var1};
         var14 = (Attribute)this.ror.invoke(this, md7, var3, m[7]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var14;
   }

   public final int size() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      int var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (Integer)this.ror.invoke(this, md8, var2, m[8]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var13;
   }
}
