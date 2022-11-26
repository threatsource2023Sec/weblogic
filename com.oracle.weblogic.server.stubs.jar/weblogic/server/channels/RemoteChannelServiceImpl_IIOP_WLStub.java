package weblogic.server.channels;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.corba.rmi.Stub;
import weblogic.protocol.ChannelList;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class RemoteChannelServiceImpl_IIOP_WLStub extends Stub implements StubInfoIntf, RemoteChannelService {
   // $FF: synthetic field
   private static Class class$weblogic$server$channels$RemoteChannelService;
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

   public RemoteChannelServiceImpl_IIOP_WLStub(StubInfo var1) {
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

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, false, false, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$server$channels$RemoteChannelService == null ? (class$weblogic$server$channels$RemoteChannelService = class$("weblogic.server.channels.RemoteChannelService")) : class$weblogic$server$channels$RemoteChannelService, true, false, false, false, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         initialized = true;
      }
   }

   public final String getAdministrationURL() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String)this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var15;
   }

   public final ChannelList getChannelList(ServerIdentity var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ChannelList var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (ChannelList)this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final String[] getConnectedServers() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String[] var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String[])this.ror.invoke(this, md2, var2, m[2]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var15;
   }

   public final String getDefaultURL() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String)this.ror.invoke(this, md3, var2, m[3]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var15;
   }

   public final ServerChannel getServerChannel(String var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ServerChannel var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (ServerChannel)this.ror.invoke(this, md4, var3, m[4]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final ServerIdentity getServerIdentity() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      ServerIdentity var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (ServerIdentity)this.ror.invoke(this, md5, var2, m[5]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

      return var15;
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final String getURL(String var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (String)this.ror.invoke(this, md6, var3, m[6]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final String registerServer(String var1, ChannelList var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var17;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var17 = (String)this.ror.invoke(this, md7, var4, m[7]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (RemoteException var14) {
         throw var14;
      } catch (Throwable var15) {
         throw new RemoteRuntimeException("Unexpected Exception", var15);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var17;
   }

   public final void removeChannelList(ServerIdentity var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md8, var3, m[8]);
      } catch (Error var10) {
         throw var10;
      } catch (RuntimeException var11) {
         throw var11;
      } catch (RemoteException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }

   public final void updateServer(String var1, ChannelList var2) throws RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md9, var4, m[9]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (RemoteException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }
}
