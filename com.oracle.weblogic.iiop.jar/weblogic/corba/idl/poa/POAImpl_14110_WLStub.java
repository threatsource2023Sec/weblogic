package weblogic.corba.idl.poa;

import java.lang.reflect.Method;
import org.omg.CORBA.Any;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyError;
import org.omg.PortableInterceptor.PolicyFactory;
import org.omg.PortableServer.AdapterActivator;
import org.omg.PortableServer.IdAssignmentPolicy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicy;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicy;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicy;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAManager;
import org.omg.PortableServer.RequestProcessingPolicy;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantRetentionPolicy;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ThreadPolicy;
import org.omg.PortableServer.ThreadPolicyValue;
import org.omg.PortableServer.POAPackage.AdapterAlreadyExists;
import org.omg.PortableServer.POAPackage.AdapterNonExistent;
import org.omg.PortableServer.POAPackage.InvalidPolicy;
import org.omg.PortableServer.POAPackage.NoServant;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongAdapter;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.MethodDescriptor;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.Utilities;

public final class POAImpl_14110_WLStub extends Stub implements StubInfoIntf, POA, Object, PolicyFactory {
   // $FF: synthetic field
   private static Class class$org$omg$PortableServer$POA;
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
   private static RuntimeMethodDescriptor md24;
   private static RuntimeMethodDescriptor md25;
   private static RuntimeMethodDescriptor md26;
   private static RuntimeMethodDescriptor md27;
   private static RuntimeMethodDescriptor md28;
   private static RuntimeMethodDescriptor md29;
   private static RuntimeMethodDescriptor md3;
   private static RuntimeMethodDescriptor md30;
   private static RuntimeMethodDescriptor md31;
   private static RuntimeMethodDescriptor md32;
   private static RuntimeMethodDescriptor md4;
   private static RuntimeMethodDescriptor md5;
   private static RuntimeMethodDescriptor md6;
   private static RuntimeMethodDescriptor md7;
   private static RuntimeMethodDescriptor md8;
   private static RuntimeMethodDescriptor md9;
   private final RemoteReference ror;
   private final StubInfo stubinfo;

   public POAImpl_14110_WLStub(StubInfo var1) {
      super(var1);
      this.stubinfo = var1;
      this.ror = this.stubinfo.getRemoteRef();
      ensureInitialized(this.stubinfo);
   }

   public final byte[] activate_object(Servant var1) throws ServantAlreadyActive, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (byte[])this.ror.invoke(this, md0, var2, m[0]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ServantAlreadyActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final void activate_object_with_id(byte[] var1, Servant var2) throws ServantAlreadyActive, ObjectAlreadyActive, WrongPolicy {
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1, var2};
         this.ror.invoke(this, md1, var3, m[1]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (ServantAlreadyActive var6) {
         throw var6;
      } catch (ObjectAlreadyActive var7) {
         throw var7;
      } catch (WrongPolicy var8) {
         throw var8;
      } catch (Throwable var9) {
         throw new RemoteRuntimeException("Unexpected Exception", var9);
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

   public final POA create_POA(String var1, POAManager var2, Policy[] var3) throws AdapterAlreadyExists, InvalidPolicy {
      try {
         java.lang.Object[] var4 = new java.lang.Object[]{var1, var2, var3};
         return (POA)this.ror.invoke(this, md2, var4, m[2]);
      } catch (Error var5) {
         throw var5;
      } catch (RuntimeException var6) {
         throw var6;
      } catch (AdapterAlreadyExists var7) {
         throw var7;
      } catch (InvalidPolicy var8) {
         throw var8;
      } catch (Throwable var9) {
         throw new RemoteRuntimeException("Unexpected Exception", var9);
      }
   }

   public final IdAssignmentPolicy create_id_assignment_policy(IdAssignmentPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (IdAssignmentPolicy)this.ror.invoke(this, md3, var2, m[3]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final IdUniquenessPolicy create_id_uniqueness_policy(IdUniquenessPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (IdUniquenessPolicy)this.ror.invoke(this, md4, var2, m[4]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final ImplicitActivationPolicy create_implicit_activation_policy(ImplicitActivationPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (ImplicitActivationPolicy)this.ror.invoke(this, md5, var2, m[5]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final LifespanPolicy create_lifespan_policy(LifespanPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (LifespanPolicy)this.ror.invoke(this, md6, var2, m[6]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final Policy create_policy(int var1, Any var2) throws PolicyError {
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{new Integer(var1), var2};
         return (Policy)this.ror.invoke(this, md7, var3, m[7]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (PolicyError var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Object create_reference(String var1) throws WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (Object)this.ror.invoke(this, md8, var2, m[8]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (WrongPolicy var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final Object create_reference_with_id(byte[] var1, String var2) {
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1, var2};
         return (Object)this.ror.invoke(this, md9, var3, m[9]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final RequestProcessingPolicy create_request_processing_policy(RequestProcessingPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (RequestProcessingPolicy)this.ror.invoke(this, md10, var2, m[10]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final ServantRetentionPolicy create_servant_retention_policy(ServantRetentionPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (ServantRetentionPolicy)this.ror.invoke(this, md11, var2, m[11]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final ThreadPolicy create_thread_policy(ThreadPolicyValue var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (ThreadPolicy)this.ror.invoke(this, md12, var2, m[12]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final void deactivate_object(byte[] var1) throws ObjectNotActive, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         this.ror.invoke(this, md13, var2, m[13]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ObjectNotActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final void destroy(boolean var1, boolean var2) {
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{new Boolean(var1), new Boolean(var2)};
         this.ror.invoke(this, md14, var3, m[14]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   private static synchronized void ensureInitialized(StubInfo var0) {
      if (!initialized) {
         m = Utilities.getRemoteRMIMethods(var0.getInterfaces());
         md0 = new MethodDescriptor(m[0], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[0]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[0]));
         md1 = new MethodDescriptor(m[1], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[1]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[1]));
         md2 = new MethodDescriptor(m[2], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[2]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[2]));
         md3 = new MethodDescriptor(m[3], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[3]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[3]));
         md4 = new MethodDescriptor(m[4], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[4]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[4]));
         md5 = new MethodDescriptor(m[5], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[5]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[5]));
         md6 = new MethodDescriptor(m[6], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[6]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[6]));
         md7 = new MethodDescriptor(m[7], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[7]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[7]));
         md8 = new MethodDescriptor(m[8], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[8]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[8]));
         md9 = new MethodDescriptor(m[9], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[9]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[9]));
         md10 = new MethodDescriptor(m[10], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[10]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[10]));
         md11 = new MethodDescriptor(m[11], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[11]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[11]));
         md12 = new MethodDescriptor(m[12], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[12]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[12]));
         md13 = new MethodDescriptor(m[13], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[13]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[13]));
         md14 = new MethodDescriptor(m[14], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[14]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[14]));
         md15 = new MethodDescriptor(m[15], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[15]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[15]));
         md16 = new MethodDescriptor(m[16], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[16]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[16]));
         md17 = new MethodDescriptor(m[17], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[17]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[17]));
         md18 = new MethodDescriptor(m[18], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[18]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[18]));
         md19 = new MethodDescriptor(m[19], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[19]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[19]));
         md20 = new MethodDescriptor(m[20], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[20]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[20]));
         md21 = new MethodDescriptor(m[21], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[21]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[21]));
         md22 = new MethodDescriptor(m[22], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[22]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[22]));
         md23 = new MethodDescriptor(m[23], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[23]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[23]));
         md24 = new MethodDescriptor(m[24], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[24]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[24]));
         md25 = new MethodDescriptor(m[25], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[25]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[25]));
         md26 = new MethodDescriptor(m[26], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[26]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[26]));
         md27 = new MethodDescriptor(m[27], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[27]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[27]));
         md28 = new MethodDescriptor(m[28], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[28]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[28]));
         md29 = new MethodDescriptor(m[29], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[29]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[29]));
         md30 = new MethodDescriptor(m[30], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[30]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[30]));
         md31 = new MethodDescriptor(m[31], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[31]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[31]));
         md32 = new MethodDescriptor(m[32], class$org$omg$PortableServer$POA == null ? (class$org$omg$PortableServer$POA = class$("org.omg.PortableServer.POA")) : class$org$omg$PortableServer$POA, false, true, false, true, var0.getTimeOut(m[32]), var0.getRemoteRef().getObjectID(), false, var0.getRemoteExceptionWrapperClassName(m[32]));
         initialized = true;
      }
   }

   public final POA find_POA(String var1, boolean var2) throws AdapterNonExistent {
      try {
         java.lang.Object[] var3 = new java.lang.Object[]{var1, new Boolean(var2)};
         return (POA)this.ror.invoke(this, md15, var3, m[15]);
      } catch (Error var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw var5;
      } catch (AdapterNonExistent var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public StubInfo getStubInfo() {
      return this.stubinfo;
   }

   public final Servant get_servant() throws NoServant, WrongPolicy {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (Servant)this.ror.invoke(this, md16, var1, m[16]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (NoServant var4) {
         throw var4;
      } catch (WrongPolicy var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final ServantManager get_servant_manager() throws WrongPolicy {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (ServantManager)this.ror.invoke(this, md17, var1, m[17]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (WrongPolicy var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final byte[] id() {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (byte[])this.ror.invoke(this, md18, var1, m[18]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final Object id_to_reference(byte[] var1) throws ObjectNotActive, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (Object)this.ror.invoke(this, md19, var2, m[19]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ObjectNotActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Servant id_to_servant(byte[] var1) throws ObjectNotActive, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (Servant)this.ror.invoke(this, md20, var2, m[20]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ObjectNotActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final byte[] reference_to_id(Object var1) throws WrongAdapter, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (byte[])this.ror.invoke(this, md21, var2, m[21]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (WrongAdapter var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Servant reference_to_servant(Object var1) throws ObjectNotActive, WrongPolicy, WrongAdapter {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (Servant)this.ror.invoke(this, md22, var2, m[22]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ObjectNotActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (WrongAdapter var7) {
         throw var7;
      } catch (Throwable var8) {
         throw new RemoteRuntimeException("Unexpected Exception", var8);
      }
   }

   public final byte[] servant_to_id(Servant var1) throws ServantNotActive, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (byte[])this.ror.invoke(this, md23, var2, m[23]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ServantNotActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final Object servant_to_reference(Servant var1) throws ServantNotActive, WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         return (Object)this.ror.invoke(this, md24, var2, m[24]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (ServantNotActive var5) {
         throw var5;
      } catch (WrongPolicy var6) {
         throw var6;
      } catch (Throwable var7) {
         throw new RemoteRuntimeException("Unexpected Exception", var7);
      }
   }

   public final void set_servant(Servant var1) throws WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         this.ror.invoke(this, md25, var2, m[25]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (WrongPolicy var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final void set_servant_manager(ServantManager var1) throws WrongPolicy {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         this.ror.invoke(this, md26, var2, m[26]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (WrongPolicy var5) {
         throw var5;
      } catch (Throwable var6) {
         throw new RemoteRuntimeException("Unexpected Exception", var6);
      }
   }

   public final POAManager the_POAManager() {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (POAManager)this.ror.invoke(this, md27, var1, m[27]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final AdapterActivator the_activator() {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (AdapterActivator)this.ror.invoke(this, md28, var1, m[28]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final void the_activator(AdapterActivator var1) {
      try {
         java.lang.Object[] var2 = new java.lang.Object[]{var1};
         this.ror.invoke(this, md29, var2, m[29]);
      } catch (Error var3) {
         throw var3;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Throwable var5) {
         throw new RemoteRuntimeException("Unexpected Exception", var5);
      }
   }

   public final POA[] the_children() {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (POA[])this.ror.invoke(this, md30, var1, m[30]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final String the_name() {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (String)this.ror.invoke(this, md31, var1, m[31]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }

   public final POA the_parent() {
      try {
         java.lang.Object[] var1 = new java.lang.Object[0];
         return (POA)this.ror.invoke(this, md32, var1, m[32]);
      } catch (Error var2) {
         throw var2;
      } catch (RuntimeException var3) {
         throw var3;
      } catch (Throwable var4) {
         throw new RemoteRuntimeException("Unexpected Exception", var4);
      }
   }
}
