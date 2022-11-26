package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.Label;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.JoinPoint;

class TraceMonitorCodeGenerator extends MonitorCodeGeneratorAdapter {
   private static final String TRACE_CLASSNAME = "weblogic/diagnostics/instrumentation/support/Trace";
   private static final String TRACE_METHOD = "trace";
   private static final String TRACE_METHOD_DESC = "(L" + JoinPoint.class.getName().replace('.', '/') + ";L" + DiagnosticMonitor.class.getName().replace('.', '/') + ";)V";

   private void emitAtJoinPoint(boolean before, boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
      if (!before || this.monitor.getLocation() != 2) {
         if (before || this.monitor.getLocation() != 1) {
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitMethodInsn(185, this.monClassType, "isEnabled", "()Z");
            Label label_1 = new Label();
            this.codeVisitor.visitJumpInsn(153, label_1);
            if (this.registers.getLocalHolderRequired()) {
               int holderIndex = this.registers.getLocalHolderRegister();
               if (this.jpName != null) {
                  this.codeVisitor.visitTypeInsn(187, "weblogic/diagnostics/instrumentation/LocalHolder");
                  this.codeVisitor.visitInsn(89);
                  this.codeVisitor.visitMethodInsn(183, "weblogic/diagnostics/instrumentation/LocalHolder", "<init>", "()V");
                  this.codeVisitor.visitVarInsn(58, holderIndex);
               }

               this.loadJoinpointToLocalHolder(before, monitorIndex, singleMonitorOptimization);
               this.codeVisitor.visitVarInsn(25, holderIndex);
               this.codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "jp", WLDF_LOCALHOLDER_JOINPOINT_TYPE);
            } else {
               this.loadJoinpoint(before, argsOnStack, isNewCall);
            }

            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/support/Trace", "trace", TRACE_METHOD_DESC);
            this.codeVisitor.visitLabel(label_1);
         }
      }
   }

   public void emitBeforeExec(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitAtJoinPoint(true, false, monitorIndex, singleMonitorOptimization, false);
   }

   public void emitAfterExec(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitAtJoinPoint(false, false, monitorIndex, singleMonitorOptimization, false);
   }

   public void emitBeforeCall(boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
      this.emitAtJoinPoint(true, argsOnStack, monitorIndex, singleMonitorOptimization, isNewCall);
   }

   public void emitAfterCall(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitAtJoinPoint(false, false, monitorIndex, singleMonitorOptimization, false);
   }
}
