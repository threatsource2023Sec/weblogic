package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.Label;

class DelegatingMonitorCodeGenerator extends MonitorCodeGeneratorAdapter {
   private void emitBeforeJoinPoint(boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
      if (this.monitor.getLocation() == 2) {
         if (!this.registers.getLocalHolderRequired() && this.throwableCaptured && this.registers.getExceptionRegister() != -1) {
            this.codeVisitor.visitInsn(1);
            this.codeVisitor.visitVarInsn(58, this.registers.getExceptionRegister());
         }

      } else {
         this.emitTrace();
         Label label_1 = null;
         int enabledIndex;
         if (this.registers.getLocalHolderRequired()) {
            if (!singleMonitorOptimization) {
               label_1 = new Label();
               this.codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
               this.codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "monitorHolder", WLDF_LOCALHOLDER_MONITORHOLDER_TYPE);
               this.codeVisitor.visitIntInsn(16, monitorIndex);
               this.codeVisitor.visitInsn(50);
               this.codeVisitor.visitJumpInsn(198, label_1);
            }

            enabledIndex = this.registers.getLocalHolderRegister();
            this.loadJoinpointToLocalHolder(true, monitorIndex, singleMonitorOptimization);
            this.codeVisitor.visitVarInsn(25, enabledIndex);
            if (this.monitor.getLocation() == 3) {
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "preProcess", WLDF_SUPPORT_PREPROCESS_HOLDER_DESC);
            } else {
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "process", WLDF_SUPPORT_PROCESS_HOLDER_DESC);
            }
         } else {
            label_1 = new Label();
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitMethodInsn(185, this.monClassType, "isEnabledAndNotDyeFiltered", "()Z");
            enabledIndex = this.registers.getEnabledFlagRegister();
            if (enabledIndex >= 0) {
               this.codeVisitor.visitInsn(89);
               this.codeVisitor.visitVarInsn(54, enabledIndex);
               this.codeVisitor.visitInsn(1);
               this.codeVisitor.visitVarInsn(58, enabledIndex + 1);
               this.codeVisitor.visitInsn(1);
               this.codeVisitor.visitVarInsn(58, enabledIndex + 2);
               if (this.registers.getExceptionRegister() != -1) {
                  this.codeVisitor.visitInsn(1);
                  this.codeVisitor.visitVarInsn(58, this.registers.getExceptionRegister());
               }
            }

            this.codeVisitor.visitJumpInsn(153, label_1);
            this.loadJoinpoint(true, argsOnStack, isNewCall);
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            this.codeVisitor.visitInsn(89);
            this.codeVisitor.visitMethodInsn(185, this.monClassType, "getActions", WLDF_GETACTIONS_DESC);
            if (enabledIndex >= 0) {
               this.codeVisitor.visitInsn(89);
               this.codeVisitor.visitVarInsn(58, enabledIndex + 1);
               this.codeVisitor.visitInsn(89);
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "getActionStates", WLDF_SUPPORT_GETACTIONSTATES_DESC);
               this.codeVisitor.visitInsn(89);
               this.codeVisitor.visitVarInsn(58, enabledIndex + 2);
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "preProcess", WLDF_SUPPORT_PREPROCESS_DESC);
            } else {
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "process", WLDF_SUPPORT_PROCESS_DESC);
            }
         }

         if (!this.registers.getLocalHolderRequired() || !singleMonitorOptimization) {
            this.codeVisitor.visitLabel(label_1);
         }

      }
   }

   private void emitAfterJoinPoint(int monitorIndex, boolean singleMonitorOptimization) {
      if (this.monitor.getLocation() != 1) {
         Label label_1 = null;
         int enabledIndex;
         if (this.registers.getLocalHolderRequired()) {
            enabledIndex = this.registers.getLocalHolderRegister();
            if (this.monitor.getLocation() != 3) {
               this.emitTrace();
            }

            if (!singleMonitorOptimization) {
               label_1 = new Label();
               this.codeVisitor.visitVarInsn(25, enabledIndex);
               this.codeVisitor.visitFieldInsn(180, "weblogic/diagnostics/instrumentation/LocalHolder", "monitorHolder", WLDF_LOCALHOLDER_MONITORHOLDER_TYPE);
               this.codeVisitor.visitIntInsn(16, monitorIndex);
               this.codeVisitor.visitInsn(50);
               this.codeVisitor.visitJumpInsn(198, label_1);
            }

            this.loadJoinpointToLocalHolder(false, monitorIndex, singleMonitorOptimization);
            this.codeVisitor.visitVarInsn(25, enabledIndex);
            if (this.monitor.getLocation() == 3) {
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "postProcess", WLDF_SUPPORT_POSTPROCESS_HOLDER_DESC);
            } else {
               this.codeVisitor.visitMethodInsn(184, this.supportClassName, "process", WLDF_SUPPORT_PROCESS_HOLDER_DESC);
            }
         } else {
            label_1 = new Label();
            enabledIndex = this.registers.getEnabledFlagRegister();
            if (enabledIndex >= 0) {
               this.codeVisitor.visitVarInsn(21, enabledIndex);
            } else {
               this.emitTrace();
               this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
               this.codeVisitor.visitMethodInsn(185, this.monClassType, "isEnabledAndNotDyeFiltered", "()Z");
            }

            this.codeVisitor.visitJumpInsn(153, label_1);
            this.loadJoinpoint(false, false, false);
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
            int exceptionRegister;
            if (enabledIndex >= 0) {
               this.codeVisitor.visitVarInsn(25, enabledIndex + 1);
               this.codeVisitor.visitVarInsn(25, enabledIndex + 2);
               if (this.throwableCaptured) {
                  exceptionRegister = this.registers.getExceptionRegister();
                  if (exceptionRegister != -1) {
                     this.codeVisitor.visitVarInsn(25, exceptionRegister);
                  } else {
                     this.codeVisitor.visitVarInsn(25, 1);
                  }

                  this.codeVisitor.visitMethodInsn(184, this.supportClassName, "postProcess", WLDF_SUPPORT_POSTPROCESS_EXC_DESC);
               } else {
                  this.codeVisitor.visitMethodInsn(184, this.supportClassName, "postProcess", WLDF_SUPPORT_POSTPROCESS_DESC);
               }
            } else {
               this.codeVisitor.visitInsn(89);
               this.codeVisitor.visitMethodInsn(185, this.monClassType, "getActions", WLDF_GETACTIONS_DESC);
               if (this.throwableCaptured) {
                  exceptionRegister = this.registers.getExceptionRegister();
                  if (exceptionRegister != -1) {
                     this.codeVisitor.visitVarInsn(25, exceptionRegister);
                  } else {
                     this.codeVisitor.visitInsn(1);
                  }

                  this.codeVisitor.visitMethodInsn(184, this.supportClassName, "process", WLDF_SUPPORT_PROCESS_EXC_DESC);
               } else {
                  this.codeVisitor.visitMethodInsn(184, this.supportClassName, "process", WLDF_SUPPORT_PROCESS_DESC);
               }
            }
         }

         if (!this.registers.getLocalHolderRequired() || !singleMonitorOptimization) {
            this.codeVisitor.visitLabel(label_1);
         }

      }
   }

   public void emitBeforeExec(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitBeforeJoinPoint(false, monitorIndex, singleMonitorOptimization, false);
   }

   public void emitAfterExec(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitAfterJoinPoint(monitorIndex, singleMonitorOptimization);
   }

   public void emitBeforeCall(boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
      this.emitBeforeJoinPoint(argsOnStack, monitorIndex, singleMonitorOptimization, isNewCall);
   }

   public void emitAfterCall(int monitorIndex, boolean singleMonitorOptimization) {
      this.emitAfterJoinPoint(monitorIndex, singleMonitorOptimization);
   }
}
