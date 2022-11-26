package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.AsyncExecutor;
import weblogic.ejb.container.internal.AsyncInvocationManager;
import weblogic.ejb.container.internal.BaseLocalObject;
import weblogic.ejb.container.internal.SessionLocalMethodInvoker;
import weblogic.ejb.container.internal.SingletonLocalObject;
import weblogic.ejb.container.internal.StatefulLocalObject;
import weblogic.ejb.container.internal.StatelessLocalObject;
import weblogic.ejb.container.utils.EJBMethodsUtil;

class LocalBusImplGenerator implements Generator {
   private static final String SLO_INVOKER = BCUtil.binName(SessionLocalMethodInvoker.class);
   private static final String ASYNC_INV_MANAGER = BCUtil.binName(AsyncInvocationManager.class);
   private static final String ASYNC_EXECUTOR_DESC = BCUtil.fieldDesc(AsyncExecutor.class);
   private static final String INVOKE_ARGS = "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)";
   private static final String SERIALIZATION_ERROR_MSG = "Attempt to pass a reference to an EJBLocalObject to a remote client. A local EJB component may only be accessed by clients co-located in the same ear or standalone jar file.";
   private static final String NON_BUS_METHOD_ERROR_MSG = "Illegal attempt to invoke a non public method. Only public methods are exposed as business methods through the no-interface view.";
   private final NamingConvention nc;
   private final SessionBeanInfo sbi;
   private final String clsName;
   private final String superClsName;
   private final Class viewClass;
   private final Class loClass;

   LocalBusImplGenerator(NamingConvention nc, SessionBeanInfo sbi, Class viewClass) {
      this.nc = nc;
      this.sbi = sbi;
      this.viewClass = viewClass;
      if (!viewClass.isInterface()) {
         this.clsName = BCUtil.binName(nc.getNoIntfViewImplClassName());
      } else {
         this.clsName = BCUtil.binName(nc.getLocalBusinessImplClassName(viewClass));
      }

      if (viewClass.isInterface()) {
         this.superClsName = "java/lang/Object";
      } else {
         this.superClsName = BCUtil.binName(sbi.getBeanClassName());
      }

      if (sbi.isStateful()) {
         this.loClass = StatefulLocalObject.class;
      } else if (sbi.isStateless()) {
         this.loClass = StatelessLocalObject.class;
      } else {
         this.loClass = SingletonLocalObject.class;
      }

   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      FieldInfo asyncMgrField = null;
      FieldInfo pkField = null;
      FieldInfo loField = new FieldInfo("__WL_localObject", this.loClass);
      if (this.sbi.hasAsyncMethods()) {
         asyncMgrField = new FieldInfo("__WL_asyncInvManager", AsyncInvocationManager.class);
      }

      if (this.sbi.isStateful()) {
         pkField = new FieldInfo("__WL_primaryKey", Object.class);
      }

      BCUtil.addCtrInitedFinalFields(cw, this.clsName, this.superClsName, loField, asyncMgrField, pkField);
      Method[] methods = null;
      Map exceptionMap = null;
      Map sigsMap;
      if (!this.viewClass.isInterface()) {
         sigsMap = EJBMethodsUtil.categorizeNoInterfaceMethods(this.sbi.getBeanClass());
         methods = (Method[])sigsMap.get(1);
         BCUtil.addAssertMethods(cw, (Method[])sigsMap.get(-4), EJBException.class, "Illegal attempt to invoke a non public method. Only public methods are exposed as business methods through the no-interface view.");
      } else {
         methods = EJBMethodsUtil.getMethods(this.viewClass, false);
         exceptionMap = new HashMap();
         methods = BCUtil.filterDuplicatedMethods(methods, exceptionMap);
      }

      sigsMap = EJBMethodsUtil.getMethodSigs(methods);
      String mdPrefix = EJBMethodsUtil.methodDescriptorPrefix((short)0);
      BCUtil.addDistinctMDFields(cw, mdPrefix, sigsMap.values(), true);

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         boolean isAsyncMethod = this.sbi.hasAsyncMethods() && this.sbi.isAsyncMethod(m);
         String md = mdPrefix + (String)sigsMap.get(m);
         String[] exsDesc = exceptionMap != null ? (String[])exceptionMap.get(m) : BCUtil.exceptionsDesc(m.getExceptionTypes());
         MethodVisitor mv = cw.visitMethod(1, m.getName(), BCUtil.methodDesc(m), (String)null, exsDesc);
         mv.visitCode();
         if (isAsyncMethod) {
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, this.clsName, asyncMgrField.fieldName(), asyncMgrField.fieldDesc());
         }

         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(180, this.clsName, loField.fieldName(), loField.fieldDesc());
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
            if (m.getReturnType() == Void.TYPE) {
               mv.visitMethodInsn(182, ASYNC_INV_MANAGER, "scheduleOneway", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)V", false);
               mv.visitInsn(177);
            } else {
               mv.visitMethodInsn(182, ASYNC_INV_MANAGER, "scheduleAsync", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)" + ASYNC_EXECUTOR_DESC, false);
               mv.visitInsn(176);
            }
         } else {
            mv.visitMethodInsn(184, SLO_INVOKER, "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;", false);
            BCUtil.unboxReturn(mv, m.getReturnType());
         }

         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

      BCUtil.addInvoke(cw, methods, BCUtil.binName(this.nc.getGeneratedBeanInterfaceName()), this.clsName);
      BCUtil.addHandleException(cw, methods, this.clsName);
      BCUtil.addGetterWithReturnClass(cw, this.clsName, "getWLLocalObject", loField, BaseLocalObject.class);
      BCUtil.addGetterWithReturnClass(cw, this.clsName, "getWLPrimaryKey", pkField, Object.class);
      if (pkField != null) {
         BCUtil.addEqualsAndHashCode(cw, this.clsName, pkField);
      }

      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private String[] getInterfaces() {
      return this.viewClass.isInterface() ? new String[]{BCUtil.binName(this.viewClass), BCUtil.WL_INVOKABLE_CLS, BCUtil.WL_EJB_LOCAL_OBJECT_CLS} : new String[]{BCUtil.WL_INVOKABLE_CLS, BCUtil.WL_EJB_LOCAL_OBJECT_CLS};
   }
}
