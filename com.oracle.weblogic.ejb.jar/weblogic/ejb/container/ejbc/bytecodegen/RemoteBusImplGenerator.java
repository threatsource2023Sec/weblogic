package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.internal.AsyncInvocationManager;
import weblogic.ejb.container.internal.RMIAsyncInvState;
import weblogic.ejb.container.internal.SessionRemoteMethodInvoker;
import weblogic.ejb.container.internal.SingletonRemoteObject;
import weblogic.ejb.container.internal.StatefulRemoteObject;
import weblogic.ejb.container.internal.StatelessRemoteObject;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.ejb.spi.BusinessObject;
import weblogic.rmi.extensions.activation.Activatable;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.internal.FutureResultHandle;

class RemoteBusImplGenerator implements Generator {
   private static final String SRO_INVOKER = BCUtil.binName(SessionRemoteMethodInvoker.class);
   private static final String ASYNC_INV_MANAGER = BCUtil.binName(AsyncInvocationManager.class);
   private static final String INVOKE_ARGS = "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I";
   private final NamingConvention nc;
   private final SessionBeanInfo sbi;
   private final String clsName;
   private final String superClsName;
   private final Class busIntf;
   private final Class roClass;

   RemoteBusImplGenerator(NamingConvention nc, SessionBeanInfo sbi, Class intf) {
      this.nc = nc;
      this.sbi = sbi;
      this.busIntf = intf;
      this.clsName = BCUtil.binName(nc.getRemoteBusinessImplClassName(this.busIntf));
      this.superClsName = "java/lang/Object";
      if (sbi.isStateful()) {
         this.roClass = StatefulRemoteObject.class;
      } else if (sbi.isStateless()) {
         this.roClass = StatelessRemoteObject.class;
      } else {
         this.roClass = SingletonRemoteObject.class;
      }

   }

   public Generator.Output generate() {
      boolean isActivatable = this.sbi.isStateful() && !((StatefulSessionBeanInfo)this.sbi).isReplicated();
      Method[] methods = EJBMethodsUtil.getMethods(this.busIntf, false);
      Map exceptionMap = new HashMap();
      methods = BCUtil.filterDuplicatedMethods(methods, exceptionMap);
      boolean hasAsyncMethods = this.viewHasAsyncMethods(methods);
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces(isActivatable, hasAsyncMethods));
      FieldInfo asyncMgrField = null;
      FieldInfo rmiStateField = null;
      FieldInfo pkField = null;
      FieldInfo activatorField = null;
      FieldInfo roField = new FieldInfo("__WL_remoteObject", this.roClass);
      if (this.sbi.hasAsyncMethods()) {
         asyncMgrField = new FieldInfo("__WL_asyncInvManager", AsyncInvocationManager.class);
         rmiStateField = new FieldInfo("__WL_rmiAsyncInvState", RMIAsyncInvState.class);
      }

      if (this.sbi.isStateful()) {
         pkField = new FieldInfo("__WL_primaryKey", Object.class);
         activatorField = new FieldInfo("__WL_activator", Activator.class);
      }

      BCUtil.addCtrInitedFinalFields(cw, this.clsName, this.superClsName, roField, asyncMgrField, rmiStateField, pkField, activatorField);
      Map sigsMap = EJBMethodsUtil.getMethodSigs(methods);
      String mdPrefix = EJBMethodsUtil.methodDescriptorPrefix((short)0);
      BCUtil.addDistinctMDFields(cw, mdPrefix, sigsMap.values(), true);

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         boolean isAsyncMethod = hasAsyncMethods && this.sbi.isAsyncMethod(m);
         String md = mdPrefix + (String)sigsMap.get(m);
         String methodDesc = BCUtil.methodDesc(m);
         String[] exsDesc = (String[])exceptionMap.get(m);
         MethodVisitor mv = cw.visitMethod(1, m.getName(), methodDesc, (String)null, exsDesc);
         mv.visitCode();
         if (isAsyncMethod) {
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, this.clsName, asyncMgrField.fieldName(), asyncMgrField.fieldDesc());
         }

         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(180, this.clsName, roField.fieldName(), roField.fieldDesc());
         mv.visitFieldInsn(178, this.clsName, md, BCUtil.WL_MD_FIELD_DESCRIPTOR);
         if (pkField != null) {
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, this.clsName, pkField.fieldName(), pkField.fieldDesc());
            mv.visitMethodInsn(184, BCUtil.WL_INVOCATION_WRAPPER_CLS, "newInstance", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Object;)Lweblogic/ejb/container/internal/InvocationWrapper;", false);
         } else {
            mv.visitMethodInsn(184, BCUtil.WL_INVOCATION_WRAPPER_CLS, "newInstance", "(Lweblogic/ejb/container/internal/MethodDescriptor;)Lweblogic/ejb/container/internal/InvocationWrapper;", false);
         }

         BCUtil.boxArgs(mv, m);
         BCUtil.pushInsn(mv, i);
         if (isAsyncMethod) {
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, this.clsName, rmiStateField.fieldName(), rmiStateField.fieldDesc());
            if (m.getReturnType() == Void.TYPE) {
               mv.visitMethodInsn(182, ASYNC_INV_MANAGER, "handleOneway", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I" + rmiStateField.fieldDesc() + ")V", false);
               mv.visitInsn(177);
            } else {
               mv.visitMethodInsn(182, ASYNC_INV_MANAGER, "handleAsync", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I" + rmiStateField.fieldDesc() + ")Ljava/util/concurrent/Future;", false);
               mv.visitInsn(176);
            }
         } else {
            mv.visitMethodInsn(184, SRO_INVOKER, "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;", false);
            BCUtil.unboxReturn(mv, m.getReturnType());
         }

         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

      BCUtil.addInvoke(cw, methods, BCUtil.binName(this.nc.getGeneratedBeanInterfaceName()), this.clsName);
      BCUtil.addHandleException(cw, methods, this.clsName);
      this.addGetBusinessObjectHandle(cw, roField, pkField);
      if (pkField != null) {
         BCUtil.addEqualsAndHashCode(cw, this.clsName, pkField);
      }

      if (isActivatable) {
         BCUtil.addGetter(cw, this.clsName, "getActivationID", pkField);
         BCUtil.addGetter(cw, this.clsName, "getActivator", activatorField);
      }

      if (hasAsyncMethods) {
         this.addWLCancel(cw, rmiStateField);
         this.addWLSetFutureId(cw, rmiStateField);
      }

      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private boolean viewHasAsyncMethods(Method[] methods) {
      if (this.sbi.hasAsyncMethods()) {
         Method[] var2 = methods;
         int var3 = methods.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Method m = var2[var4];
            if (this.sbi.isAsyncMethod(m)) {
               return true;
            }
         }
      }

      return false;
   }

   private String[] getInterfaces(boolean isActivatable, boolean hasAsyncMethods) {
      List intfs = new ArrayList(5);
      intfs.add(BCUtil.WL_INVOKABLE_CLS);
      intfs.add(BCUtil.binName(BusinessObject.class));
      if (isActivatable) {
         intfs.add(BCUtil.binName(Activatable.class));
      }

      if (hasAsyncMethods) {
         intfs.add(BCUtil.binName(FutureResultHandle.class));
      }

      if (Remote.class.isAssignableFrom(this.busIntf)) {
         intfs.add(BCUtil.binName(this.busIntf));
      } else {
         intfs.add(BCUtil.binName(this.nc.getRemoteBusinessIntfClassName(this.busIntf)));
      }

      return (String[])intfs.toArray(new String[intfs.size()]);
   }

   private void addGetBusinessObjectHandle(ClassWriter cw, FieldInfo roField, FieldInfo pkField) {
      BCUtil.addMDField(cw, "md_eo__WL_getBusinessObjectHandle", true);
      MethodVisitor mv = cw.visitMethod(1, "_WL_getBusinessObjectHandle", "()Lweblogic/ejb/spi/BusinessHandle;", (String)null, new String[]{"java/rmi/RemoteException"});
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, this.clsName, roField.fieldName(), roField.fieldDesc());
      mv.visitFieldInsn(178, this.clsName, "md_eo__WL_getBusinessObjectHandle", BCUtil.WL_MD_FIELD_DESCRIPTOR);
      if (pkField != null) {
         mv.visitLdcInsn(Type.getType(this.busIntf));
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(180, this.clsName, pkField.fieldName(), pkField.fieldDesc());
         mv.visitMethodInsn(182, roField.binName(), "__WL_getBusinessObjectHandle", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Class;Ljava/lang/Object;)Lweblogic/ejb/spi/BusinessHandle;", false);
      } else {
         mv.visitLdcInsn(Type.getType(this.busIntf));
         mv.visitMethodInsn(182, roField.binName(), "__WL_getBusinessObjectHandle", "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/Class;)Lweblogic/ejb/spi/BusinessHandle;", false);
      }

      mv.visitInsn(176);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   private void addWLCancel(ClassWriter cw, FieldInfo rmiStateField) {
      MethodVisitor mv = cw.visitMethod(1, "__WL_cancel", "(Lweblogic/rmi/internal/FutureResultID;Z)Z", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, this.clsName, rmiStateField.fieldName(), rmiStateField.fieldDesc());
      mv.visitVarInsn(25, 1);
      mv.visitVarInsn(21, 2);
      mv.visitMethodInsn(182, rmiStateField.binName(), "updateCancelFlag", "(Lweblogic/rmi/internal/FutureResultID;Z)Z", false);
      mv.visitInsn(172);
      mv.visitMaxs(4, 3);
      mv.visitEnd();
   }

   private void addWLSetFutureId(ClassWriter cw, FieldInfo rmiStateField) {
      MethodVisitor mv = cw.visitMethod(1, "__WL_setFutureResultID", "(Lweblogic/rmi/internal/FutureResultID;Lweblogic/security/acl/internal/AuthenticatedSubject;)V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitVarInsn(25, 0);
      mv.visitFieldInsn(180, this.clsName, rmiStateField.fieldName(), rmiStateField.fieldDesc());
      mv.visitVarInsn(25, 1);
      mv.visitVarInsn(25, 2);
      mv.visitMethodInsn(182, rmiStateField.binName(), "setFutureResultID", "(Lweblogic/rmi/internal/FutureResultID;Lweblogic/security/acl/internal/AuthenticatedSubject;)V", false);
      mv.visitInsn(177);
      mv.visitMaxs(4, 3);
      mv.visitEnd();
   }
}
