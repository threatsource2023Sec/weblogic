package weblogic.iiop.security;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.corba.rmi.Stub;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.security.acl.SecurityService;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedUser;
import weblogic.transaction.TransactionHelper;

public final class SecurityServiceImpl_IIOP_WLStub extends Stub implements StubInfoIntf, SecurityService {
   // $FF: synthetic field
   private static Class class$weblogic$security$acl$SecurityService;
   private static boolean initialized;
   private static Method[] m;
   private static RuntimeMethodDescriptor md0;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public SecurityServiceImpl_IIOP_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final AuthenticatedUser authenticate(UserInfo var1) throws RemoteException {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      AuthenticatedUser var16;
      try {
         Object[] var3 = new Object[]{var1};
         var16 = (AuthenticatedUser)this.ror.invoke(this, md0, var3, m[0]);
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
         md0 = new MethodDescriptor(m[0], class$weblogic$security$acl$SecurityService == null ? (class$weblogic$security$acl$SecurityService = class$("weblogic.security.acl.SecurityService")) : class$weblogic$security$acl$SecurityService, false, false, false, false, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }
}
