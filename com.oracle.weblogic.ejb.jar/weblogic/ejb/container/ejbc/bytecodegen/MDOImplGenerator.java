package weblogic.ejb.container.ejbc.bytecodegen;

import java.lang.reflect.Method;
import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJBException;
import javax.resource.spi.endpoint.MessageEndpoint;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import weblogic.ejb.container.deployer.NamingConvention;
import weblogic.ejb.container.interfaces.Invokable;
import weblogic.ejb.container.interfaces.MessageDrivenBeanInfo;
import weblogic.ejb.container.internal.MessageDrivenLocalObject;
import weblogic.ejb.container.internal.MessageEndpointRemote;
import weblogic.ejb.container.utils.EJBMethodsUtil;

class MDOImplGenerator implements Generator {
   private static final String NON_BUS_METHOD_ERROR_MSG = "Illegal attempt to invoke a non public method. Only public methods are exposed as business methods through the no-interface view.";
   private static final String INVOKE_METHOD_DESC = "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/MethodDescriptor;[Ljava/lang/Object;ILjava/lang/String;)Ljava/lang/Object;";
   private final MessageDrivenBeanInfo mdbi;
   private final String clsName;
   private final String superClsName;

   MDOImplGenerator(NamingConvention nc, MessageDrivenBeanInfo mdbi) {
      this.mdbi = mdbi;
      this.clsName = BCUtil.binName(nc.getMdLocalObjectClassName());
      this.superClsName = BCUtil.binName(mdbi.exposesNoInterfaceClientView() ? mdbi.getBeanClass() : Object.class);
   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(1);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, this.getInterfaces());
      String mdloBinName = BCUtil.binName(MessageDrivenLocalObject.class);
      FieldInfo mdoImplField = new FieldInfo("__WL_localObject", MessageDrivenLocalObject.class);
      BCUtil.addCtrInitedFinalFields(cw, this.clsName, this.superClsName, mdoImplField);
      Method[] methods;
      if (this.mdbi.exposesNoInterfaceClientView()) {
         Map map = EJBMethodsUtil.categorizeNoInterfaceMethods(this.mdbi.getBeanClass());
         methods = (Method[])map.get(1);
         BCUtil.addAssertMethods(cw, (Method[])map.get(-4), EJBException.class, "Illegal attempt to invoke a non public method. Only public methods are exposed as business methods through the no-interface view.");
      } else {
         methods = EJBMethodsUtil.getMethods(this.mdbi.getMessagingTypeInterfaceClass(), false);
      }

      Map exceptionMap = new HashMap();
      methods = BCUtil.filterDuplicatedMethods(methods, exceptionMap);
      Map sigsMap = EJBMethodsUtil.getMethodSigs(methods);
      String mdPrefix = EJBMethodsUtil.methodDescriptorPrefix((short)0);
      BCUtil.addDistinctMDFields(cw, mdPrefix, sigsMap.values(), true);

      for(int i = 0; i < methods.length; ++i) {
         Method m = methods[i];
         String md = mdPrefix + (String)sigsMap.get(m);
         String methodDesc = BCUtil.methodDesc(m);
         String[] exceptionsDesc = (String[])exceptionMap.get(m);
         MethodVisitor mv = cw.visitMethod(1, m.getName(), methodDesc, (String)null, exceptionsDesc);
         mv.visitCode();
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(180, this.clsName, mdoImplField.fieldName(), mdoImplField.fieldDesc());
         mv.visitVarInsn(25, 0);
         mv.visitFieldInsn(178, this.clsName, md, BCUtil.WL_MD_FIELD_DESCRIPTOR);
         BCUtil.boxArgs(mv, m);
         BCUtil.pushInsn(mv, i);
         mv.visitLdcInsn(this.mdbi.getBeanClass().getName() + "." + m.getName());
         mv.visitMethodInsn(182, mdloBinName, "invoke", "(Lweblogic/ejb/container/interfaces/Invokable;Lweblogic/ejb/container/internal/MethodDescriptor;[Ljava/lang/Object;ILjava/lang/String;)Ljava/lang/Object;", false);
         BCUtil.unboxReturn(mv, m.getReturnType());
         mv.visitMaxs(0, 0);
         mv.visitEnd();
      }

      String messType = BCUtil.binName(this.mdbi.exposesNoInterfaceClientView() ? this.mdbi.getGeneratedBeanInterface() : this.mdbi.getMessagingTypeInterfaceClass());
      BCUtil.addInvoke(cw, methods, messType, this.clsName);
      BCUtil.addHandleException(cw, methods, this.clsName);
      Method[] var16 = MessageEndpoint.class.getMethods();
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         Method m = var16[var18];
         BCUtil.addDelegatingImplementation(cw, this.clsName, m, mdoImplField);
      }

      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }

   private String[] getInterfaces() {
      Class messType = this.mdbi.getMessagingTypeInterfaceClass();
      return !this.mdbi.exposesNoInterfaceClientView() && Remote.class.isAssignableFrom(messType) ? new String[]{BCUtil.binName(MessageEndpointRemote.class), BCUtil.binName(messType), BCUtil.binName(Invokable.class)} : new String[]{BCUtil.binName(MessageEndpoint.class), BCUtil.binName(messType), BCUtil.binName(Invokable.class)};
   }
}
