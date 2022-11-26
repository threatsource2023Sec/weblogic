package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBHome;
import javax.ejb.Handle;
import javax.ejb.RemoveException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import weblogic.ejb.EJBObject;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.ejb.container.internal.SessionRemoteMethodInvoker;
import weblogic.ejb.container.internal.StatefulEJBObject;
import weblogic.ejb.container.internal.StatelessEJBObject;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.rmi.extensions.activation.Activatable;

class EOImplGenerator implements Generator {
   private static final MethInfo REMOVE_OBJ_MI = MethInfo.of("remove", "md_eo_remove").exceps(RemoveException.class, RemoteException.class).create();
   private static final MethInfo GET_EJB_HOME_MI = MethInfo.of("getEJBHome", "md_eo_getEJBHome").returns(EJBHome.class).exceps(RemoteException.class).create();
   private static final MethInfo GET_HANDLE_MI = MethInfo.of("getHandle", "md_eo_getHandle").returns(Handle.class).exceps(RemoteException.class).create();
   private static final MethInfo GET_PRIMARY_KEY_MI = MethInfo.of("getPrimaryKey", "md_eo_getPrimaryKey").returns(Object.class).exceps(RemoteException.class).create();
   private static final MethInfo IS_IDENTICAL_MI;
   private static final String SRO_INVOKER;
   private static final String INVOKE_METHOD_DESC = "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;";
   private final NamingConvention nc;
   private final SessionBeanInfo sbi;
   private final String clsName;
   private final String superClsName;

   EOImplGenerator(NamingConvention nc, SessionBeanInfo sbi) {
      this.nc = nc;
      this.sbi = sbi;
      this.clsName = BCUtil.binName(nc.getEJBObjectClassName());
      if (sbi.isStateful()) {
         this.superClsName = BCUtil.binName(StatefulEJBObject.class);
      } else {
         this.superClsName = BCUtil.binName(StatelessEJBObject.class);
      }

   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      BCUtil.addDefInit(cw, this.superClsName);
      Method[] methods = EJBMethodsUtil.getRemoteMethods(this.sbi.getRemoteInterfaceClass(), false);
      Map exceptionMap = new HashMap();
      methods = BCUtil.filterDuplicatedMethods(methods, exceptionMap);
      Map sigsMap = EJBMethodsUtil.getMethodSigs(methods);
      String mdPrefix = EJBMethodsUtil.methodDescriptorPrefix((short)0);
      BCUtil.addDistinctMDFields(cw, mdPrefix, sigsMap.values(), true);

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         String md = mdPrefix + (String)sigsMap.get(m);
         String methodDesc = BCUtil.methodDesc(m);
         String[] exsDesc = (String[])exceptionMap.get(m);
         MethodVisitor mv = cw.visitMethod(1, m.getName(), methodDesc, (String)null, exsDesc);
         mv.visitCode();
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(178, this.clsName, md, BCUtil.WL_MD_FIELD_DESCRIPTOR);
         mv.visitMethodInsn(184, BCUtil.WL_INVOCATION_WRAPPER_CLS, "newInstance", "(Lweblogic/ejb/container/internal/MethodDescriptor;)Lweblogic/ejb/container/internal/InvocationWrapper;", false);
         BCUtil.boxArgs(mv, m);
         BCUtil.pushInsn(mv, i);
         mv.visitMethodInsn(184, SRO_INVOKER, "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseRemoteObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;", false);
         BCUtil.unboxReturn(mv, m.getReturnType());
         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

      BCUtil.addInvoke(cw, methods, BCUtil.binName(this.nc.getGeneratedBeanInterfaceName()), this.clsName);
      BCUtil.addHandleException(cw, methods, this.clsName);
      BCUtil.addEOMembers(cw, this.clsName, this.superClsName, REMOVE_OBJ_MI, GET_EJB_HOME_MI, GET_HANDLE_MI, GET_PRIMARY_KEY_MI, IS_IDENTICAL_MI);
      this.addOperationsComplete(cw);
      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private String[] getInterfaces() {
      return this.sbi.isStateful() && !((StatefulSessionBeanInfo)this.sbi).isReplicated() ? new String[]{BCUtil.binName(this.sbi.getRemoteInterfaceClass()), BCUtil.binName(Activatable.class), BCUtil.binName(EJBObject.class), BCUtil.WL_INVOKABLE_CLS} : new String[]{BCUtil.binName(this.sbi.getRemoteInterfaceClass()), BCUtil.binName(EJBObject.class), BCUtil.WL_INVOKABLE_CLS};
   }

   private void addOperationsComplete(ClassWriter cw) {
      MethodVisitor mv = cw.visitMethod(1, "operationsComplete", "()V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitInsn(177);
      mv.visitMaxs(0, 1);
      mv.visitEnd();
   }

   static {
      IS_IDENTICAL_MI = MethInfo.of("isIdentical", "md_eo_isIdentical_javax_ejb_EJBObject").args(javax.ejb.EJBObject.class).returns(Boolean.TYPE).exceps(RemoteException.class).create();
      SRO_INVOKER = BCUtil.binName(SessionRemoteMethodInvoker.class);
   }
}
