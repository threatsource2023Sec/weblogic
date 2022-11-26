package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.utils.EJBMethodsUtil;

abstract class AbstractHomeImplGenerator implements Generator {
   private static final String CLINIT_ASEERTION_ERROR_MSG = "Unable to find expected methods.  Please check your classpath for stale versions of your ejb classes and re-run weblogic.appc.\n If this is a java.io.FilePermission exception and you are running under JACC security, then check your security policy file.\n  Exception: ";
   final NamingConvention nc;
   final SessionBeanInfo sbi;
   final String clsName;
   final String superClsName;
   private static final String STATEFUL_BASE_CREATE_SIG = "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/reflect/Method;[Ljava/lang/Object;)";
   private static final String STATELESS_BASE_CREATE_SIG = "(Lweblogic/ejb/container/internal/MethodDescriptor;)";

   AbstractHomeImplGenerator(NamingConvention nc, SessionBeanInfo sbi, String cls, String superCls) {
      this.nc = nc;
      this.sbi = sbi;
      this.clsName = cls;
      this.superClsName = superCls;
   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      Map sigs = EJBMethodsUtil.getHomeMethodSigs(this.getCreateMethods());
      Iterator var4;
      if (this.needsClInit(sigs.keySet())) {
         int psfAccess = 26;
         var4 = sigs.values().iterator();

         while(var4.hasNext()) {
            String sig = (String)var4.next();
            cw.visitField(psfAccess, "mth_" + sig, BCUtil.METHOD_FIELD_DESC, (String)null, (Object)null).visitEnd();
         }

         this.addClInit(cw, sigs);
      }

      this.addInit(cw);
      String mdPrefix = EJBMethodsUtil.methodDescriptorPrefix((short)1);
      BCUtil.addDistinctMDFields(cw, mdPrefix, sigs.values(), false);
      var4 = sigs.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry e = (Map.Entry)var4.next();
         String md = mdPrefix + (String)e.getValue();
         String methodField = "mth_" + (String)e.getValue();
         this.addCreateMethod(cw, (Method)e.getKey(), md, methodField);
      }

      this.addCustomMembers(cw);
      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   abstract String[] getInterfaces();

   abstract Class getComponentInterface();

   abstract String getComponentImplName();

   abstract Method[] getCreateMethods();

   abstract void addCustomMembers(ClassWriter var1);

   abstract Class getDefExceptionForCreate();

   abstract String getSupersCreateReturnType();

   private void addInit(ClassWriter cw) {
      MethodVisitor ctr = cw.visitMethod(1, "<init>", "()V", (String)null, (String[])null);
      ctr.visitCode();
      ctr.visitVarInsn(25, 0);
      ctr.visitLdcInsn(Type.getType("L" + this.getComponentImplName() + ";"));
      ctr.visitMethodInsn(183, this.superClsName, "<init>", "(Ljava/lang/Class;)V", false);
      ctr.visitInsn(177);
      ctr.visitMaxs(2, 1);
      ctr.visitEnd();
   }

   private boolean needsClInit(Set methods) {
      boolean addClInit = true;
      if (this.sbi.isStateless() && this.sbi.isEJB30()) {
         try {
            if (!methods.isEmpty()) {
               Method m = (Method)methods.iterator().next();
               String name = this.sbi.getEjbCreateInitMethodName(m);
               this.sbi.getBeanClass().getMethod(name, m.getParameterTypes());
            } else {
               addClInit = false;
            }
         } catch (NoSuchMethodException var5) {
            addClInit = false;
         }
      }

      return addClInit;
   }

   private void addClInit(ClassWriter cw, Map methodSigs) {
      String beanIntfName = BCUtil.binName(this.nc.getGeneratedBeanInterfaceName());
      Type beanIntfType = Type.getType("L" + beanIntfName + ";");
      MethodVisitor clInit = cw.visitMethod(8, "<clinit>", "()V", (String)null, (String[])null);
      clInit.visitCode();
      Label l0 = new Label();
      Label l1 = new Label();
      Label l2 = new Label();
      clInit.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
      clInit.visitLabel(l0);
      Iterator var9 = methodSigs.entrySet().iterator();

      while(var9.hasNext()) {
         Map.Entry entry = (Map.Entry)var9.next();
         Method method = (Method)entry.getKey();
         String sig = (String)entry.getValue();
         clInit.visitLdcInsn(beanIntfType);
         String mName = this.sbi.getEjbCreateInitMethodName(method);
         clInit.visitLdcInsn(mName);
         Class[] args = method.getParameterTypes();
         BCUtil.pushInsn(clInit, args.length);
         clInit.visitTypeInsn(189, "java/lang/Class");

         for(int j = 0; j < args.length; ++j) {
            clInit.visitInsn(89);
            BCUtil.pushInsn(clInit, j);
            if (args[j].isPrimitive()) {
               clInit.visitFieldInsn(178, BCUtil.getBoxedClassBinName(args[j]), "TYPE", "Ljava/lang/Class;");
            } else {
               clInit.visitLdcInsn(Type.getType(BCUtil.fieldDesc(args[j])));
            }

            clInit.visitInsn(83);
         }

         clInit.visitMethodInsn(182, "java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
         clInit.visitFieldInsn(179, this.clsName, "mth_" + sig, BCUtil.METHOD_FIELD_DESC);
      }

      clInit.visitLabel(l1);
      Label l3 = new Label();
      clInit.visitJumpInsn(167, l3);
      clInit.visitLabel(l2);
      clInit.visitVarInsn(58, 0);
      clInit.visitTypeInsn(187, "java/lang/AssertionError");
      clInit.visitInsn(89);
      clInit.visitTypeInsn(187, "java/lang/StringBuilder");
      clInit.visitInsn(89);
      clInit.visitLdcInsn("Unable to find expected methods.  Please check your classpath for stale versions of your ejb classes and re-run weblogic.appc.\n If this is a java.io.FilePermission exception and you are running under JACC security, then check your security policy file.\n  Exception: '");
      clInit.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
      clInit.visitVarInsn(25, 0);
      clInit.visitMethodInsn(182, "java/lang/Exception", "getMessage", "()Ljava/lang/String;", false);
      clInit.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
      clInit.visitLdcInsn("'");
      clInit.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
      clInit.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
      clInit.visitMethodInsn(183, "java/lang/AssertionError", "<init>", "(Ljava/lang/Object;)V", false);
      clInit.visitInsn(191);
      clInit.visitLabel(l3);
      clInit.visitInsn(177);
      clInit.visitMaxs(6, 1);
      clInit.visitEnd();
   }

   private void addCreateMethod(ClassWriter cw, Method m, String mdForCreate, String methodField) {
      Class[] params = m.getParameterTypes();
      String methodDesc = BCUtil.methodDesc(this.getComponentInterface(), params);
      String[] exs = BCUtil.exceptionsDesc(m.getExceptionTypes());
      MethodVisitor createMeth = cw.visitMethod(1, m.getName(), methodDesc, (String)null, exs);
      createMeth.visitCode();
      Label l0 = new Label();
      Label l1 = new Label();
      Label l2 = new Label();
      createMeth.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
      createMeth.visitLabel(l0);
      createMeth.visitVarInsn(25, 0);
      createMeth.visitVarInsn(25, 0);
      createMeth.visitFieldInsn(180, this.clsName, mdForCreate, BCUtil.WL_MD_FIELD_DESCRIPTOR);
      String mDesc;
      if (this.sbi.isStateful()) {
         createMeth.visitFieldInsn(178, this.clsName, methodField, BCUtil.METHOD_FIELD_DESC);
         if (params.length == 0) {
            createMeth.visitInsn(3);
            createMeth.visitTypeInsn(189, "java/lang/Object");
         } else {
            BCUtil.boxArgs(createMeth, m);
         }

         mDesc = "(Lweblogic/ejb/container/internal/MethodDescriptor;Ljava/lang/reflect/Method;[Ljava/lang/Object;)" + this.getSupersCreateReturnType();
         createMeth.visitMethodInsn(183, this.superClsName, "create", mDesc, false);
      } else {
         mDesc = "(Lweblogic/ejb/container/internal/MethodDescriptor;)" + this.getSupersCreateReturnType();
         createMeth.visitMethodInsn(183, this.superClsName, "create", mDesc, false);
      }

      createMeth.visitTypeInsn(192, BCUtil.binName(m.getReturnType()));
      createMeth.visitLabel(l1);
      createMeth.visitInsn(176);
      createMeth.visitLabel(l2);
      int exLoc = BCUtil.sizeOf(params) + 1;
      createMeth.visitVarInsn(58, exLoc);
      List allExs = BCUtil.filterExs(m);
      allExs.add(0, this.getDefExceptionForCreate());
      Label nextLabel = null;
      Iterator var15 = allExs.iterator();

      while(var15.hasNext()) {
         Class exception = (Class)var15.next();
         if (nextLabel != null) {
            createMeth.visitLabel(nextLabel);
         }

         createMeth.visitVarInsn(25, exLoc);
         createMeth.visitTypeInsn(193, BCUtil.binName(exception));
         nextLabel = new Label();
         createMeth.visitJumpInsn(153, nextLabel);
         createMeth.visitVarInsn(25, exLoc);
         createMeth.visitTypeInsn(192, BCUtil.binName(exception));
         createMeth.visitInsn(191);
      }

      createMeth.visitLabel(nextLabel);
      createMeth.visitVarInsn(25, exLoc);
      createMeth.visitTypeInsn(193, "java/lang/RuntimeException");
      nextLabel = new Label();
      createMeth.visitJumpInsn(153, nextLabel);
      createMeth.visitVarInsn(25, 0);
      createMeth.visitFieldInsn(180, this.clsName, "deploymentInfo", "Lweblogic/ejb/container/interfaces/DeploymentInfo;");
      createMeth.visitVarInsn(25, 0);
      createMeth.visitFieldInsn(180, this.clsName, mdForCreate, BCUtil.WL_MD_FIELD_DESCRIPTOR);
      createMeth.visitMethodInsn(182, "weblogic/ejb/container/internal/MethodDescriptor", "getMethod", "()Ljava/lang/reflect/Method;", false);
      createMeth.visitVarInsn(25, exLoc);
      createMeth.visitMethodInsn(185, "weblogic/ejb/container/interfaces/DeploymentInfo", "getExceptionInfo", "(Ljava/lang/reflect/Method;Ljava/lang/Throwable;)Lweblogic/ejb/container/interfaces/ExceptionInfo;", true);
      createMeth.visitMethodInsn(185, "weblogic/ejb/container/interfaces/ExceptionInfo", "isAppException", "()Z", true);
      createMeth.visitJumpInsn(153, nextLabel);
      createMeth.visitVarInsn(25, exLoc);
      createMeth.visitTypeInsn(192, "java/lang/RuntimeException");
      createMeth.visitInsn(191);
      createMeth.visitLabel(nextLabel);
      createMeth.visitTypeInsn(187, "javax/ejb/CreateException");
      createMeth.visitInsn(89);
      createMeth.visitTypeInsn(187, "java/lang/StringBuilder");
      createMeth.visitInsn(89);
      createMeth.visitLdcInsn("Error while creating bean: ");
      createMeth.visitMethodInsn(183, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V", false);
      createMeth.visitVarInsn(25, exLoc);
      createMeth.visitMethodInsn(182, "java/lang/Exception", "toString", "()Ljava/lang/String;", false);
      createMeth.visitMethodInsn(182, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;", false);
      createMeth.visitMethodInsn(182, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
      createMeth.visitMethodInsn(183, "javax/ejb/CreateException", "<init>", "(Ljava/lang/String;)V", false);
      createMeth.visitInsn(191);
      createMeth.visitMaxs(7, 4);
      createMeth.visitEnd();
   }
}
