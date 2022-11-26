package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.Label;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;

class DyeInjectionMonitorCodeGenerator extends MonitorCodeGeneratorAdapter {
   private static final String SUPPORT_CLASSNAME = "weblogic/diagnostics/instrumentation/support/DyeInjectionMonitorSupport";
   private static final String SUPPORT_METHOD_DESC = "(Ljava/lang/Object;L" + DiagnosticMonitor.class.getName().replace('.', '/') + ";)V";
   private static final SupportDsc[] supportDescriptors = new SupportDsc[]{new SupportDsc("weblogic/servlet/internal/WebAppServletContext", 1, "dyeWebAppRequest"), new SupportDsc("weblogic/rmi/internal/BasicServerRef", 1, "dyeRMIRequest")};

   private static SupportDsc findSupportDescriptor(String className) {
      for(int i = 0; i < supportDescriptors.length; ++i) {
         SupportDsc dsc = supportDescriptors[i];
         if (className.equals(dsc.getClassName())) {
            return dsc;
         }
      }

      return null;
   }

   public void emitBeforeExec(int monitorIndex, boolean singleMonitorOptimization) {
      SupportDsc supportDesc = findSupportDescriptor(this.className);
      if (supportDesc != null) {
         this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
         this.codeVisitor.visitMethodInsn(185, this.monClassType, "isEnabled", "()Z");
         Label label_1 = new Label();
         this.codeVisitor.visitJumpInsn(153, label_1);
         String supportMethodName = supportDesc.getSupportMethodName();
         this.codeVisitor.visitVarInsn(25, supportDesc.getArgumentIndex());
         this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
         this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/support/DyeInjectionMonitorSupport", supportMethodName, SUPPORT_METHOD_DESC);
         this.codeVisitor.visitLabel(label_1);
      }

   }

   static class SupportDsc {
      private String className;
      private String supportMethodName;
      private int argIndex;

      SupportDsc(String className, int argIndex, String supportMethodName) {
         this.className = className;
         this.supportMethodName = supportMethodName;
         this.argIndex = argIndex;
      }

      String getClassName() {
         return this.className;
      }

      int getArgumentIndex() {
         return this.argIndex;
      }

      String getSupportMethodName() {
         return this.supportMethodName;
      }
   }
}
