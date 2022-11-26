package weblogic.corba.cos.naming;

import java.lang.reflect.Method;
import javax.transaction.Transaction;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtPackage.InvalidAddress;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import weblogic.corba.cos.naming.NamingContextAnyPackage.AppException;
import weblogic.corba.cos.naming.NamingContextAnyPackage.TypeNotSupported;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;
import weblogic.transaction.TransactionHelper;

public final class NamingContextImpl_14110_WLStub extends Stub implements StubInfoIntf, NamingContextAny, NamingContextExt, NamingContext, Object {
   // $FF: synthetic field
   private static Class class$weblogic$corba$cos$naming$NamingContextAny;
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

   public NamingContextImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final void bind(NameComponent[] var1, Object var2) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md0, var4, m[0]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NotFound var16) {
         throw var16;
      } catch (CannotProceed var17) {
         throw var17;
      } catch (InvalidName var18) {
         throw var18;
      } catch (AlreadyBound var19) {
         throw var19;
      } catch (Throwable var20) {
         throw new RemoteRuntimeException("Unexpected Exception", var20);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void bind_any(WNameComponent[] var1, Any var2) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, AppException, InvalidName, AlreadyBound, TypeNotSupported {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md1, var4, m[1]);
      } catch (Error var16) {
         throw var16;
      } catch (RuntimeException var17) {
         throw var17;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound var18) {
         throw var18;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed var19) {
         throw var19;
      } catch (AppException var20) {
         throw var20;
      } catch (InvalidName var21) {
         throw var21;
      } catch (AlreadyBound var22) {
         throw var22;
      } catch (TypeNotSupported var23) {
         throw var23;
      } catch (Throwable var24) {
         throw new RemoteRuntimeException("Unexpected Exception", var24);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void bind_context(NameComponent[] var1, NamingContext var2) throws NotFound, CannotProceed, InvalidName, AlreadyBound {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md2, var4, m[2]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NotFound var16) {
         throw var16;
      } catch (CannotProceed var17) {
         throw var17;
      } catch (InvalidName var18) {
         throw var18;
      } catch (AlreadyBound var19) {
         throw var19;
      } catch (Throwable var20) {
         throw new RemoteRuntimeException("Unexpected Exception", var20);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final NamingContext bind_new_context(NameComponent[] var1) throws NotFound, AlreadyBound, CannotProceed, InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingContext var22;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var22 = (NamingContext)this.ror.invoke(this, md3, var3, m[3]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (NotFound var16) {
         throw var16;
      } catch (AlreadyBound var17) {
         throw var17;
      } catch (CannotProceed var18) {
         throw var18;
      } catch (InvalidName var19) {
         throw var19;
      } catch (Throwable var20) {
         throw new RemoteRuntimeException("Unexpected Exception", var20);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var22;
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   public final void destroy() throws NotEmpty {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw var10;
      } catch (NotEmpty var11) {
         throw var11;
      } catch (Throwable var12) {
         throw new RemoteRuntimeException("Unexpected Exception", var12);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var1);
      }

   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         md10 = new MethodDescriptor(m[10], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[10]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[10]));
         md11 = new MethodDescriptor(m[11], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[11]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[11]));
         md12 = new MethodDescriptor(m[12], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[12]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[12]));
         md13 = new MethodDescriptor(m[13], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[13]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[13]));
         md14 = new MethodDescriptor(m[14], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[14]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[14]));
         md15 = new MethodDescriptor(m[15], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[15]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[15]));
         md16 = new MethodDescriptor(m[16], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[16]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[16]));
         md17 = new MethodDescriptor(m[17], class$weblogic$corba$cos$naming$NamingContextAny == null ? (class$weblogic$corba$cos$naming$NamingContextAny = class$("weblogic.corba.cos.naming.NamingContextAny")) : class$weblogic$corba$cos$naming$NamingContextAny, false, false, false, true, var0.getTimeOut(m[17]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[17]));
         initialized = true;
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final void list(int var1, BindingListHolder var2, BindingIteratorHolder var3) {
      Transaction var4 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var5 = new java.lang.Object[]{new Integer(var1), var2, var3};
         this.ror.invoke(this, md5, var5, m[5]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (Throwable var13) {
         throw new RemoteRuntimeException("Unexpected Exception", var13);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var4);
      }

   }

   public final NamingContext new_context() {
      Transaction var1 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NamingContext var13;
      try {
         java.lang.Object[] var2 = new java.lang.Object[0];
         var13 = (NamingContext)this.ror.invoke(this, md6, var2, m[6]);
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

   public final void rebind(NameComponent[] var1, Object var2) throws NotFound, CannotProceed, InvalidName {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md7, var4, m[7]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NotFound var15) {
         throw var15;
      } catch (CannotProceed var16) {
         throw var16;
      } catch (InvalidName var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void rebind_any(WNameComponent[] var1, Any var2) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, AppException, InvalidName, TypeNotSupported {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md8, var4, m[8]);
      } catch (Error var15) {
         throw var15;
      } catch (RuntimeException var16) {
         throw var16;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound var17) {
         throw var17;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed var18) {
         throw var18;
      } catch (AppException var19) {
         throw var19;
      } catch (InvalidName var20) {
         throw var20;
      } catch (TypeNotSupported var21) {
         throw var21;
      } catch (Throwable var22) {
         throw new RemoteRuntimeException("Unexpected Exception", var22);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final void rebind_context(NameComponent[] var1, NamingContext var2) throws NotFound, CannotProceed, InvalidName {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md9, var4, m[9]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NotFound var15) {
         throw var15;
      } catch (CannotProceed var16) {
         throw var16;
      } catch (InvalidName var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

   }

   public final Object resolve(NameComponent[] var1) throws NotFound, CannotProceed, InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var20;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var20 = (Object)this.ror.invoke(this, md10, var3, m[10]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NotFound var15) {
         throw var15;
      } catch (CannotProceed var16) {
         throw var16;
      } catch (InvalidName var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var20;
   }

   public final Any resolve_any(WNameComponent[] var1) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, AppException, InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Any var22;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var22 = (Any)this.ror.invoke(this, md11, var3, m[11]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound var16) {
         throw var16;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed var17) {
         throw var17;
      } catch (AppException var18) {
         throw var18;
      } catch (InvalidName var19) {
         throw var19;
      } catch (Throwable var20) {
         throw new RemoteRuntimeException("Unexpected Exception", var20);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var22;
   }

   public final Object resolve_str(String var1) throws NotFound, CannotProceed, InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Object var20;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var20 = (Object)this.ror.invoke(this, md12, var3, m[12]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (NotFound var15) {
         throw var15;
      } catch (CannotProceed var16) {
         throw var16;
      } catch (InvalidName var17) {
         throw var17;
      } catch (Throwable var18) {
         throw new RemoteRuntimeException("Unexpected Exception", var18);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var20;
   }

   public final Any resolve_str_any(String var1) throws weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound, weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed, AppException, InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      Any var22;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var22 = (Any)this.ror.invoke(this, md13, var3, m[13]);
      } catch (Error var14) {
         throw var14;
      } catch (RuntimeException var15) {
         throw var15;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound var16) {
         throw var16;
      } catch (weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed var17) {
         throw var17;
      } catch (AppException var18) {
         throw var18;
      } catch (InvalidName var19) {
         throw var19;
      } catch (Throwable var20) {
         throw new RemoteRuntimeException("Unexpected Exception", var20);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var22;
   }

   public final NameComponent[] to_name(String var1) throws InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      NameComponent[] var16;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var16 = (NameComponent[])this.ror.invoke(this, md14, var3, m[14]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (InvalidName var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final String to_string(NameComponent[] var1) throws InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var16;
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         var16 = (String)this.ror.invoke(this, md15, var3, m[15]);
      } catch (Error var11) {
         throw var11;
      } catch (RuntimeException var12) {
         throw var12;
      } catch (InvalidName var13) {
         throw var13;
      } catch (Throwable var14) {
         throw new RemoteRuntimeException("Unexpected Exception", var14);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

      return var16;
   }

   public final String to_url(String var1, String var2) throws InvalidAddress, InvalidName {
      Transaction var3 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      String var19;
      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2};
         var19 = (String)this.ror.invoke(this, md16, var4, m[16]);
      } catch (Error var13) {
         throw var13;
      } catch (RuntimeException var14) {
         throw var14;
      } catch (InvalidAddress var15) {
         throw var15;
      } catch (InvalidName var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var3);
      }

      return var19;
   }

   public final void unbind(NameComponent[] var1) throws NotFound, CannotProceed, InvalidName {
      Transaction var2 = TransactionHelper.getTransactionHelper().getTransactionManager().forceSuspend();

      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1};
         this.ror.invoke(this, md17, var3, m[17]);
      } catch (Error var12) {
         throw var12;
      } catch (RuntimeException var13) {
         throw var13;
      } catch (NotFound var14) {
         throw var14;
      } catch (CannotProceed var15) {
         throw var15;
      } catch (InvalidName var16) {
         throw var16;
      } catch (Throwable var17) {
         throw new RemoteRuntimeException("Unexpected Exception", var17);
      } finally {
         TransactionHelper.getTransactionHelper().getTransactionManager().forceResume(var2);
      }

   }
}
