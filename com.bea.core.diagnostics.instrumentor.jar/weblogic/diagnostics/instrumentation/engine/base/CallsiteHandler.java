package weblogic.diagnostics.instrumentation.engine.base;

import java.util.Iterator;
import java.util.List;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

class CallsiteHandler implements InstrumentationEngineConstants {
   private Type[] argsTypes;
   private Type retType;
   private List codeGenerators;
   private RegisterFile registers;
   private Label beforeLabel;
   private Label finallylabel;
   private String supportClassName = null;
   private boolean allArgsSensitive = false;
   private boolean captureArgs = false;
   private MonitorSpecificationBase[] applicableMonitors;

   CallsiteHandler(Type[] argTypes, Type retType, List codeGenerators, boolean needsBefore, boolean needsAfter, RegisterFile registers, String supportClassName, boolean allArgsSensitive, boolean captureArgs, MonitorSpecificationBase[] applicableMonitors) {
      this.argsTypes = argTypes;
      this.retType = retType;
      this.codeGenerators = codeGenerators;
      this.registers = registers;
      this.supportClassName = supportClassName;
      this.allArgsSensitive = allArgsSensitive;
      this.captureArgs = captureArgs;
      this.applicableMonitors = applicableMonitors;
      if (needsBefore) {
         this.beforeLabel = new Label();
      }

      if (needsAfter) {
         this.finallylabel = new Label();
      }

   }

   MonitorSpecificationBase[] getApplicableMonitors() {
      return this.applicableMonitors;
   }

   void handleCallsite(MethodVisitor codeVisitor, int opcode, String owner, String name, String desc, boolean isNewCall) {
      if (this.beforeLabel != null) {
         this.emitDiagnosticCodeBeforeJoinpoint(codeVisitor, isNewCall);
      } else if (this.registers.getLocalHolderRequired()) {
         codeVisitor.visitVarInsn(58, this.registers.getLocalHolderRegister());
      }

      Label fromLabel = null;
      Label toLabel = null;
      Label skipLabel = new Label();
      Label targetLabel = null;
      if (this.finallylabel != null) {
         fromLabel = new Label();
         toLabel = new Label();
         targetLabel = new Label();
         codeVisitor.visitTryCatchBlock(fromLabel, toLabel, targetLabel, (String)null);
         codeVisitor.visitLabel(fromLabel);
      }

      codeVisitor.visitMethodInsn(opcode, owner, name, desc);
      if (this.finallylabel != null) {
         int exceptionIndex = true;
         int holderIndex = this.registers.getLocalHolderRequired() ? this.registers.getLocalHolderRegister() : -1;
         if (this.registers.getLocalHolderRequired()) {
            codeVisitor.visitVarInsn(25, holderIndex);
            codeVisitor.visitJumpInsn(198, skipLabel);
         }

         this.emitDiagnosticCodeAfterJoinpoint(codeVisitor, false);
         codeVisitor.visitLabel(toLabel);
         codeVisitor.visitJumpInsn(167, skipLabel);
         codeVisitor.visitLabel(targetLabel);
         if (this.registers.getLocalHolderRequired()) {
            Label label_1 = new Label();
            codeVisitor.visitVarInsn(25, holderIndex);
            codeVisitor.visitJumpInsn(198, label_1);
            codeVisitor.visitInsn(89);
            holderIndex = this.registers.getLocalHolderRegister();
            codeVisitor.visitVarInsn(25, holderIndex);
            codeVisitor.visitInsn(95);
            codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "th", "Ljava/lang/Throwable;");
            this.emitDiagnosticCodeAfterJoinpoint(codeVisitor, true);
            codeVisitor.visitLabel(label_1);
         } else {
            int exceptionIndex = this.registers.getExceptionRegister();
            codeVisitor.visitVarInsn(58, exceptionIndex);
            this.emitDiagnosticCodeAfterJoinpoint(codeVisitor, true);
            codeVisitor.visitVarInsn(25, exceptionIndex);
         }

         codeVisitor.visitInsn(191);
      } else {
         codeVisitor.visitJumpInsn(167, skipLabel);
      }

      codeVisitor.visitLabel(skipLabel);
   }

   private void emitDiagnosticCodeBeforeJoinpoint(MethodVisitor codeVisitor, boolean isNewCall) {
      Label label_1 = null;
      if (this.registers.getLocalHolderRequired()) {
         label_1 = new Label();
         codeVisitor.visitInsn(89);
         codeVisitor.visitVarInsn(58, this.registers.getLocalHolderRegister());
         codeVisitor.visitJumpInsn(198, label_1);
         if (this.captureArgs) {
            if (this.allArgsSensitive) {
               CodeUtils.captureSensitiveArguments(codeVisitor, this.registers, this.supportClassName, this.argsTypes == null ? 0 : this.argsTypes.length);
            } else {
               CodeUtils.captureArguments(codeVisitor, true, this.registers, this.supportClassName, this.argsTypes, isNewCall, this.captureArgs, false);
            }
         }
      }

      boolean singleMonitorOptimization = this.codeGenerators.size() == 1;
      int i = 0;
      Iterator it = this.codeGenerators.iterator();

      while(it.hasNext()) {
         MonitorCodeGenerator generator = (MonitorCodeGenerator)it.next();
         generator.emitBeforeCall(true, i++, singleMonitorOptimization, isNewCall);
      }

      if (this.registers.getLocalHolderRequired()) {
         if (this.captureArgs) {
            codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
            codeVisitor.visitMethodInsn(182, "weblogic/diagnostics/instrumentation/LocalHolder", "resetPostBegin", "()V");
         }

         codeVisitor.visitLabel(label_1);
      }

   }

   private void emitDiagnosticCodeAfterJoinpoint(MethodVisitor codeVisitor, boolean exceptionCase) {
      int retValIndex;
      if (this.registers.getLocalHolderRequired()) {
         retValIndex = this.registers.getLocalHolderRegister();
         if (!this.retType.equals(Type.VOID_TYPE) && retValIndex >= 0) {
            if (exceptionCase) {
               codeVisitor.visitInsn(89);
               codeVisitor.visitVarInsn(25, retValIndex);
               codeVisitor.visitInsn(95);
               codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "ret", "Ljava/lang/Object;");
            } else {
               codeVisitor.visitInsn(CodeUtils.getDUPInstruction(this.retType));
               CodeUtils.objectifyArg(codeVisitor, this.retType, this.supportClassName);
               codeVisitor.visitVarInsn(25, retValIndex);
               codeVisitor.visitInsn(95);
               codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "ret", "Ljava/lang/Object;");
            }
         }
      } else {
         retValIndex = this.registers.getReturnValueRegister();
         if (!this.retType.equals(Type.VOID_TYPE) && retValIndex >= 0) {
            if (exceptionCase) {
               codeVisitor.visitVarInsn(25, this.registers.getExceptionRegister());
            } else {
               codeVisitor.visitInsn(CodeUtils.getDUPInstruction(this.retType));
               CodeUtils.objectifyArg(codeVisitor, this.retType, this.supportClassName);
            }

            codeVisitor.visitVarInsn(58, retValIndex);
         }
      }

      retValIndex = this.codeGenerators != null ? this.codeGenerators.size() : 0;
      boolean singleMonitorOptimization = retValIndex == 1;

      for(int i = retValIndex - 1; i >= 0; --i) {
         MonitorCodeGenerator generator = (MonitorCodeGenerator)this.codeGenerators.get(i);
         generator.emitAfterCall(i, singleMonitorOptimization);
      }

   }

   RegisterFile getRegisters() {
      return this.registers;
   }
}
