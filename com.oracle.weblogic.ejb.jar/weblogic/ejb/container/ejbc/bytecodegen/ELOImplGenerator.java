package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.RemoveException;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import weblogic.ejb.EJBLocalObject;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.SessionLocalMethodInvoker;
import weblogic.ejb.container.internal.StatefulEJBLocalObject;
import weblogic.ejb.container.internal.StatelessEJBLocalObject;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.ejb20.interfaces.LocalHandle;

class ELOImplGenerator implements Generator {
   private static final MethInfo REMOVE_MI = MethInfo.of("remove", "md_eo_remove").exceps(RemoveException.class).create();
   private static final MethInfo GET_EJB_LOCAL_HOME_MI = MethInfo.of("getEJBLocalHome", "md_eo_getEJBLocalHome").returns(EJBLocalHome.class).exceps(EJBException.class).create();
   private static final MethInfo GET_LOCAL_HANDLE_MI = MethInfo.of("getLocalHandle", "md_eo_getLocalHandle").returns(LocalHandle.class).exceps(EJBException.class).create();
   private static final MethInfo GET_PRIMARY_KEY_MI = MethInfo.of("getPrimaryKey", "md_eo_getPrimaryKey").returns(Object.class).exceps(EJBException.class).create();
   private static final MethInfo IS_IDENTICAL_MI;
   private static final String SLO_INVOKER;
   private static final String INVOKE_METHOD_DESC = "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;";
   private static final String SERIALIZATION_ERROR_MSG = "Attempt to pass a reference to an EJBLocalObject to a remote client. A local EJB component may only be accessed by clients co-located in the same ear or standalone jar file.";
   private final NamingConvention nc;
   private final SessionBeanInfo sbi;
   private final String clsName;
   private final String superClsName;

   ELOImplGenerator(NamingConvention nc, SessionBeanInfo sbi) {
      this.nc = nc;
      this.sbi = sbi;
      this.clsName = BCUtil.binName(nc.getEJBLocalObjectClassName());
      if (sbi.isStateful()) {
         this.superClsName = BCUtil.binName(StatefulEJBLocalObject.class);
      } else {
         this.superClsName = BCUtil.binName(StatelessEJBLocalObject.class);
      }

   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      BCUtil.addDefInit(cw, this.superClsName);
      Method[] methods = EJBMethodsUtil.getLocalMethods(this.sbi.getLocalInterfaceClass(), false);
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
         mv.visitMethodInsn(184, SLO_INVOKER, "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/BaseLocalObject;Lweblogic/ejb/container/internal/InvocationWrapper;[Ljava/lang/Object;I)Ljava/lang/Object;", false);
         BCUtil.unboxReturn(mv, m.getReturnType());
         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

      BCUtil.addInvoke(cw, methods, BCUtil.binName(this.nc.getGeneratedBeanInterfaceName()), this.clsName);
      BCUtil.addHandleException(cw, methods, this.clsName);
      BCUtil.addEOMembers(cw, this.clsName, this.superClsName, REMOVE_MI, GET_EJB_LOCAL_HOME_MI, GET_LOCAL_HANDLE_MI, GET_PRIMARY_KEY_MI, IS_IDENTICAL_MI);
      this.addOperationsComplete(cw);
      BCUtil.addSerializationAssertMethods(cw, "javax/ejb/EJBException", "Attempt to pass a reference to an EJBLocalObject to a remote client. A local EJB component may only be accessed by clients co-located in the same ear or standalone jar file.");
      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private String[] getInterfaces() {
      return new String[]{BCUtil.binName(this.sbi.getLocalInterfaceClass()), "java/io/Serializable", BCUtil.binName(EJBLocalObject.class), BCUtil.WL_INVOKABLE_CLS};
   }

   private void addOperationsComplete(ClassWriter cw) {
      MethodVisitor mv = cw.visitMethod(1, "operationsComplete", "()V", (String)null, (String[])null);
      mv.visitCode();
      mv.visitInsn(177);
      mv.visitMaxs(0, 1);
      mv.visitEnd();
   }

   static {
      IS_IDENTICAL_MI = MethInfo.of("isIdentical", "md_eo_isIdentical_javax_ejb_EJBLocalObject").args(javax.ejb.EJBLocalObject.class).returns(Boolean.TYPE).exceps(EJBException.class).create();
      SLO_INVOKER = BCUtil.binName(SessionLocalMethodInvoker.class);
   }
}
