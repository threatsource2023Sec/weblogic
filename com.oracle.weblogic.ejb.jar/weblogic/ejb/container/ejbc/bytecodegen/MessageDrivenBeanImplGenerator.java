package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import org.objectweb.asm.ClassWriter;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.utils.EJBMethodsUtil;

class MessageDrivenBeanImplGenerator implements Generator {
   private final NamingConvention nc;
   private final MessageDrivenBeanInfo mdbi;
   private final String clsName;
   private final String superClsName;

   MessageDrivenBeanImplGenerator(NamingConvention nCon, MessageDrivenBeanInfo mdbi) {
      this.nc = nCon;
      this.mdbi = mdbi;
      this.clsName = BCUtil.binName(this.nc.getGeneratedBeanClassName());
      this.superClsName = BCUtil.binName(this.nc.getBeanClassName());
   }

   public Generator.Output generate() {
      String[] intfNames = this.mdbi.exposesNoInterfaceClientView() ? new String[]{BCUtil.binName(this.mdbi.getGeneratedBeanInterface()), BCUtil.binName(this.mdbi.getMessagingTypeInterfaceClass())} : new String[]{BCUtil.binName(this.mdbi.getMessagingTypeInterfaceClass())};
      ClassWriter cw = new ClassWriter(0);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, intfNames);
      BCUtil.addDefInit(cw, this.superClsName);
      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   public Generator.Output generateBeanIntf() {
      String generateBeanIntfName = BCUtil.binName(this.nc.getGeneratedBeanInterfaceName());
      ClassWriter cw = new ClassWriter(0);
      cw.visit(49, 1537, generateBeanIntfName, (String)null, "java/lang/Object", new String[0]);
      Method[] var3 = EJBMethodsUtil.getNoInterfaceViewBusinessMethods(this.mdbi.getBeanClass());
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         cw.visitMethod(1025, m.getName(), BCUtil.methodDesc(m), (String)null, BCUtil.exceptionsDesc(m.getExceptionTypes())).visitEnd();
      }

      cw.visitEnd();
      return new ClassFileOutput(generateBeanIntfName, cw.toByteArray());
   }
}
