package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.Label;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;

class HttpSessionDebugMonitorCodeGenerator extends MonitorCodeGeneratorAdapter {
   private static final String SUPPORT_CLASSNAME = "weblogic/diagnostics/instrumentation/support/HttpSessionDebugMonitorSupport";
   private static final String HTTPDEBUG_SUPPORT_METHOD_DESC = "(L" + JoinPoint.class.getName().replace('.', '/') + ";L" + DiagnosticMonitor.class.getName().replace('.', '/') + ";)V";
   private static final String HTTPDEBUG_SUPPORT_METHOD_LOCALHOLDER_DESC = "(L" + LocalHolder.class.getName().replace('.', '/') + ";)V";

   private void emitBeforeJoinPoint(boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
      if (this.monitor.getLocation() != 2) {
         Label label_1 = null;
         if (this.registers.getLocalHolderRequired()) {
            if (this.monitor.getLocation() != 3) {
               return;
            }

            if (!singleMonitorOptimization) {
               this.codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
               this.codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "monitorHolder", WLDF_LOCALHOLDER_MONITORHOLDER_TYPE);
               this.codeVisitor.visitIntInsn(16, monitorIndex);
               this.codeVisitor.visitInsn(50);
               label_1 = new Label();
               this.codeVisitor.visitJumpInsn(198, label_1);
            }

            this.loadJoinpointToLocalHolder(true, monitorIndex, singleMonitorOptimization);
            this.codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/support/HttpSessionDebugMonitorSupport", "preProcess", HTTPDEBUG_SUPPORT_METHOD_LOCALHOLDER_DESC);
         } else {
            int enabledIndex = this.registers.getEnabledFlagRegister();
            if (enabledIndex < 0) {
               return;
            }

            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitMethodInsn(185, this.monClassType, "isEnabled", "()Z");
            this.codeVisitor.visitInsn(89);
            this.codeVisitor.visitVarInsn(54, enabledIndex);
            this.codeVisitor.visitInsn(1);
            this.codeVisitor.visitVarInsn(58, enabledIndex + 1);
            this.codeVisitor.visitInsn(1);
            this.codeVisitor.visitVarInsn(58, enabledIndex + 2);
            label_1 = new Label();
            this.codeVisitor.visitJumpInsn(153, label_1);
            this.loadJoinpoint(true, argsOnStack, isNewCall);
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/support/HttpSessionDebugMonitorSupport", "preProcess", HTTPDEBUG_SUPPORT_METHOD_DESC);
         }

         if (!this.registers.getLocalHolderRequired() || !singleMonitorOptimization) {
            this.codeVisitor.visitLabel(label_1);
         }

      }
   }

   private void emitAfterJoinPoint(int monitorIndex, boolean singleMonitorOptimization) {
      if (this.monitor.getLocation() != 1) {
         Label label_1 = null;
         if (this.registers.getLocalHolderRequired()) {
            if (this.monitor.getLocation() != 3) {
               return;
            }

            if (!singleMonitorOptimization) {
               this.codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
               this.codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "monitorHolder", WLDF_LOCALHOLDER_MONITORHOLDER_TYPE);
               this.codeVisitor.visitIntInsn(16, monitorIndex);
               this.codeVisitor.visitInsn(50);
               label_1 = new Label();
               this.codeVisitor.visitJumpInsn(198, label_1);
            }

            this.loadJoinpointToLocalHolder(false, monitorIndex, singleMonitorOptimization);
            this.codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/support/HttpSessionDebugMonitorSupport", "postProcess", HTTPDEBUG_SUPPORT_METHOD_LOCALHOLDER_DESC);
         } else {
            int enabledIndex = this.registers.getEnabledFlagRegister();
            if (enabledIndex < 0) {
               return;
            }

            this.codeVisitor.visitVarInsn(21, enabledIndex);
            label_1 = new Label();
            this.codeVisitor.visitJumpInsn(153, label_1);
            this.loadJoinpoint(false, false, false);
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/support/HttpSessionDebugMonitorSupport", "postProcess", HTTPDEBUG_SUPPORT_METHOD_DESC);
         }

         if (!this.registers.getLocalHolderRequired() || !singleMonitorOptimization) {
            this.codeVisitor.visitLabel(label_1);
         }

      }
   }

   public void emitBeforeCall(boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
      this.emitBeforeJoinPoint(argsOnStack, monitorIndex, singleMonitorOptimization, isNewCall);
   }

   public void emitAfterCall(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitAfterJoinPoint(monitorIndex, singleMonitorOptimization);
   }
}
