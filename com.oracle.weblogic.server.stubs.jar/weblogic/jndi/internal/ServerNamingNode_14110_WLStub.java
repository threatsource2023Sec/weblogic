package weblogic.jndi.internal;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.event.NamingListener;
import javax.transaction.Transaction;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.transaction.TransactionHelper;

public final class ServerNamingNode_14110_WLStub extends Stub implements StubInfoIntf, NamingNode {
   // $FF: synthetic field
   private static Class class$weblogic$jndi$internal$NamingNode;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private static RuntimeMethodDescriptor md1;
   private static RuntimeMethodDescriptor md10;
   private static RuntimeMethodDescriptor md11;
   private static RuntimeMethodDescriptor md12;
   private static RuntimeMethodDescriptor md13;
   private static RuntimeMethodDescriptor md14;
   private static RuntimeMethodDescriptor md15;
   private static RuntimeMethodDescriptor md16;
   private static RuntimeMethodDescriptor md17;
   private static RuntimeMethodDescriptor md18;
   private static RuntimeMethodDescriptor md19;
   private static RuntimeMethodDescriptor md2;
   private static RuntimeMethodDescriptor md20;
   private static RuntimeMethodDescriptor md21;
   private static RuntimeMethodDescriptor md22;
   private static RuntimeMethodDescriptor md23;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private static RuntimeMethodDescriptor md6;
   private static RuntimeMethodDescriptor md7;
   private static RuntimeMethodDescriptor md8;
   private static RuntimeMethodDescriptor md9;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public ServerNamingNode_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final void addNamingListener(String var1, int var2, NamingListener var3, Hashtable var4) throws NamingException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, new Integer(var2), var3, var4};
         this.ror.invoke(this, md0, var6, m[0]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void addOneLevelScopeNamingListener(NamingListener var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var3 = new Object[]{var1};
         this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Throwable var11) {
         throw new RemoteRuntimeException("Unexpected Exception", var11);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }

   public final Object authenticatedLookup(String var1, Hashtable var2, AuthenticatedSubject var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var20;
      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         var20 = (Object)this.ror.invoke(this, md2, var5, m[2]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NamingException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var20;
   }

   public final void bind(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md3, var5, m[3]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final Context createSubcontext(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Context var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (Context)this.ror.invoke(this, md4, var4, m[4]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final void destroySubcontext(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md5, var4, m[5]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (NamingException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         md10 = new MethodDescriptor(m[10], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[10]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[10]));
         md11 = new MethodDescriptor(m[11], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[11]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[11]));
         md12 = new MethodDescriptor(m[12], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[12]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[12]));
         md13 = new MethodDescriptor(m[13], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[13]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[13]));
         md14 = new MethodDescriptor(m[14], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[14]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[14]));
         md15 = new MethodDescriptor(m[15], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[15]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[15]));
         md16 = new MethodDescriptor(m[16], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[16]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[16]));
         md17 = new MethodDescriptor(m[17], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[17]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[17]));
         md18 = new MethodDescriptor(m[18], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[18]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[18]));
         md19 = new MethodDescriptor(m[19], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[19]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[19]));
         md20 = new MethodDescriptor(m[20], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[20]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[20]));
         md21 = new MethodDescriptor(m[21], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[21]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[21]));
         md22 = new MethodDescriptor(m[22], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[22]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[22]));
         md23 = new MethodDescriptor(m[23], class$weblogic$jndi$internal$NamingNode == null ? (class$weblogic$jndi$internal$NamingNode = class$("weblogic.jndi.internal.NamingNode")) : class$weblogic$jndi$internal$NamingNode, false, false, false, false, var0.getTimeOut(m[23]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[23]));
         initialized = true;
      }
   }

   public final Context getContext(Hashtable var1) {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Context var14;
      try {
         Object[] var3 = new Object[]{var1};
         var14 = (Context)this.ror.invoke(this, md6, var3, m[6]);
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

   public final String getNameInNamespace() throws RemoteException {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var15;
      try {
         Object[] var2 = new Object[0];
         var15 = (String)this.ror.invoke(this, md7, var2, m[7]);
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

   public final String getNameInNamespace(String var1) throws NamingException, RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var18;
      try {
         Object[] var3 = new Object[]{var1};
         var18 = (String)this.ror.invoke(this, md8, var3, m[8]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (NamingException var14) {
         throw var14;
      } catch (RemoteException var15) {
         throw var15;
      } catch (Throwable var16) {
         throw new RemoteRuntimeException("Unexpected Exception", var16);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var18;
   }

   public final NameParser getNameParser(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NameParser var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (NameParser)this.ror.invoke(this, md9, var4, m[9]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final List getOneLevelScopeNamingListeners() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      List var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (List)this.ror.invoke(this, md10, var2, m[10]);
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

   public final NamingNode getParent() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingNode var13;
      try {
         Object[] var2 = new Object[0];
         var13 = (NamingNode)this.ror.invoke(this, md11, var2, m[11]);
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

   public final boolean isBindable(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var20;
      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         var20 = (Boolean)this.ror.invoke(this, md12, var5, m[12]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NamingException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var20;
   }

   public final boolean isBindable(String var1, boolean var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      boolean var20;
      try {
         Object[] var5 = new Object[]{var1, new Boolean(var2), var3};
         var20 = (Boolean)this.ror.invoke(this, md13, var5, m[13]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NamingException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

      return var20;
   }

   public final NamingEnumeration list(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingEnumeration var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (NamingEnumeration)this.ror.invoke(this, md14, var4, m[14]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final NamingEnumeration listBindings(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingEnumeration var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (NamingEnumeration)this.ror.invoke(this, md15, var4, m[15]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final Object lookup(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (Object)this.ror.invoke(this, md16, var4, m[16]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final Object lookupLink(String var1, Hashtable var2) throws NamingException, RemoteException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var19;
      try {
         Object[] var4 = new Object[]{var1, var2};
         var19 = (Object)this.ror.invoke(this, md17, var4, m[17]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final void rebind(String var1, Object var2, Object var3, Hashtable var4) throws NamingException, RemoteException {
      Transaction var5 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var6 = new Object[]{var1, var2, var3, var4};
         this.ror.invoke(this, md18, var6, m[18]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NamingException var16) {
         throw var16;
      } catch (RemoteException var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var5);
      }

   }

   public final void rebind(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md19, var5, m[19]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void rebind(Name var1, Object var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md20, var5, m[20]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void removeNamingListener(NamingListener var1, Hashtable var2) throws NamingException {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var4 = new Object[]{var1, var2};
         this.ror.invoke(this, md21, var4, m[21]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (NamingException var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void rename(String var1, String var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md22, var5, m[22]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final void unbind(String var1, Object var2, Hashtable var3) throws NamingException, RemoteException {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         Object[] var5 = new Object[]{var1, var2, var3};
         this.ror.invoke(this, md23, var5, m[23]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NamingException var15) {
         throw var15;
      } catch (RemoteException var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }
}
