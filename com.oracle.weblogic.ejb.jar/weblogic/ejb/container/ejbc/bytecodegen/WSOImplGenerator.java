package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.util.Map;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.internal.BaseWSLocalObject;
import weblogic.ejb.container.internal.EJBContextHandler;
import weblogic.ejb.container.internal.WSOMethodInvoker;
import weblogic.ejb.container.utils.EJBMethodsUtil;
import weblogic.ejb.spi.BaseWSObjectIntf;
import weblogic.ejb.spi.MethodUtils;
import weblogic.utils.Debug;

class WSOImplGenerator implements Generator {
   private static final String WSO_INVOKER = BCUtil.binName(WSOMethodInvoker.class);
   private static final String INVOKE_METHOD_DESC = "(Lweblogic/ejb/container/internal/BaseWSLocalObject;Lweblogic/ejb/container/internal/MethodDescriptor;[Ljava/lang/Object;I)Ljava/lang/Object;";
   private static final String WL_WEBLOGIC_EJB_CONTEXT_HANDLER_CLS = BCUtil.binName(EJBContextHandler.class);
   private static final String WL_UTILS_DEBUG_CLS = BCUtil.binName(Debug.class);
   private static final String SUPER_PRE_INVOKE_METHOD_DESC = "(Lweblogic/ejb/container/internal/MethodDescriptor;Lweblogic/security/service/ContextHandler;Lweblogic/security/service/ContextHandler;Lweblogic/security/acl/internal/AuthenticatedSubject;)Lweblogic/ejb/container/internal/InvocationWrapper;";
   private static final String EJB_CTX_HANDLER_METHOD_DESC = "(Lweblogic/ejb/container/internal/MethodDescriptor;[Ljava/lang/Object;)V";
   private final NamingConvention nc;
   private final SessionBeanInfo sbi;
   private final String clsName;
   private final String superClsName;

   WSOImplGenerator(NamingConvention nc, SessionBeanInfo sbi) {
      this.nc = nc;
      this.sbi = sbi;
      this.clsName = BCUtil.binName(nc.getWsObjectClassName());
      this.superClsName = BCUtil.binName(BaseWSLocalObject.class);
   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      BCUtil.addDefInit(cw, this.superClsName);
      Method[] methods = EJBMethodsUtil.getWebserviceMethods(this.sbi);
      Map sigsMap = EJBMethodsUtil.getMethodSigs(methods);
      String mdPrefix = EJBMethodsUtil.methodDescriptorPrefix((short)0);
      BCUtil.addDistinctMDFields(cw, mdPrefix, sigsMap.values(), true);

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         String md = mdPrefix + (String)sigsMap.get(m);
         String methodDesc = BCUtil.methodDesc(m);
         this.addPreInvokeMethod(cw, m, md);
         this.addBusMethod(cw, m, md, methodDesc, i);
         this.addDummyMethod(cw, m, methodDesc);
      }

      this.addPostInvoke(cw);
      BCUtil.addInvoke(cw, methods, BCUtil.binName(this.nc.getGeneratedBeanInterfaceName()), this.clsName);
      BCUtil.addHandleExceptionAssertMethod(cw);
      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private String[] getInterfaces() {
      return new String[]{BCUtil.binName(BaseWSObjectIntf.class), "java/io/Serializable", BCUtil.WL_INVOKABLE_CLS};
   }

   private void addPostInvoke(ClassWriter cw) {
      MethodVisitor postInvoke = cw.visitMethod(1, "__WL__WS_postInvoke", "()V", (String)null, new String[]{"java/lang/Exception"});
      postInvoke.visitCode();
      postInvoke.visitVarInsn(25, 0);
      postInvoke.visitMethodInsn(183, this.superClsName, "__WL_wsPostInvoke", "()V", false);
      postInvoke.visitInsn(177);
      postInvoke.visitMaxs(1, 1);
      postInvoke.visitEnd();
   }

   private void addPreInvokeMethod(ClassWriter cw, Method m, String mdName) {
      String methodName = MethodUtils.getWSOPreInvokeMethodName(m);
      StringBuilder methodDesc = new StringBuilder("(Lweblogic/security/acl/internal/AuthenticatedSubject;Lweblogic/security/service/ContextHandler;");
      Class[] var6 = m.getParameterTypes();
      int loc = var6.length;

      for(int var8 = 0; var8 < loc; ++var8) {
         Class p = var6[var8];
         methodDesc.append(BCUtil.fieldDesc(p));
      }

      methodDesc.append(")V");
      MethodVisitor mv = cw.visitMethod(1, methodName, methodDesc.toString(), (String)null, (String[])null);
      mv.visitCode();
      mv.visitFieldInsn(178, this.clsName, mdName, BCUtil.WL_MD_FIELD_DESCRIPTOR);
      loc = BCUtil.sizeOf(m.getParameterTypes()) + 3;
      mv.visitVarInsn(58, loc);
      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(25, loc);
      mv.visitTypeInsn(187, WL_WEBLOGIC_EJB_CONTEXT_HANDLER_CLS);
      mv.visitInsn(89);
      mv.visitVarInsn(25, loc);
      if (m.getParameterTypes().length == 0) {
         mv.visitInsn(3);
         mv.visitTypeInsn(189, "java/lang/Object");
      } else {
         BCUtil.boxArgs(mv, m, 3);
      }

      mv.visitMethodInsn(183, WL_WEBLOGIC_EJB_CONTEXT_HANDLER_CLS, "<init>", "(Lweblogic/ejb/container/internal/MethodDescriptor;[Ljava/lang/Object;)V", false);
      mv.visitVarInsn(25, 2);
      mv.visitVarInsn(25, 1);
      mv.visitMethodInsn(183, this.superClsName, "__WL_preInvoke", "(Lweblogic/ejb/container/internal/MethodDescriptor;Lweblogic/security/service/ContextHandler;Lweblogic/security/service/ContextHandler;Lweblogic/security/acl/internal/AuthenticatedSubject;)Lweblogic/ejb/container/internal/InvocationWrapper;", false);
      mv.visitInsn(87);
      mv.visitInsn(177);
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   private void addBusMethod(ClassWriter cw, Method m, String mdName, String methodDesc, int index) {
      String name = MethodUtils.getWSOBusinessMethodName(m);
      MethodVisitor busMethod = cw.visitMethod(1, name, methodDesc, (String)null, new String[]{"java/lang/Throwable"});
      busMethod.visitCode();
      busMethod.visitVarInsn(25, 0);
      busMethod.visitFieldInsn(178, this.clsName, mdName, BCUtil.WL_MD_FIELD_DESCRIPTOR);
      BCUtil.boxArgs(busMethod, m);
      BCUtil.pushInsn(busMethod, index);
      busMethod.visitMethodInsn(184, WSO_INVOKER, "invoke", "(Lweblogic/ejb/container/internal/BaseWSLocalObject;Lweblogic/ejb/container/internal/MethodDescriptor;[Ljava/lang/Object;I)Ljava/lang/Object;", false);
      BCUtil.unboxReturn(busMethod, m.getReturnType());
      busMethod.visitMaxs(0, 0);
      busMethod.visitEnd();
   }

   private void addDummyMethod(ClassWriter cw, Method m, String methodDesc) {
      String[] exceptionsDesc = BCUtil.exceptionsDesc(m.getExceptionTypes());
      MethodVisitor mv = cw.visitMethod(1, m.getName(), methodDesc, (String)null, exceptionsDesc);
      mv.visitCode();
      mv.visitInsn(3);
      mv.visitLdcInsn(" invalid call on " + m.getName() + ".");
      mv.visitMethodInsn(184, WL_UTILS_DEBUG_CLS, "assertion", "(ZLjava/lang/String;)V", false);
      BCUtil.addReturnDefaultValue(mv, m.getReturnType());
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }
}
