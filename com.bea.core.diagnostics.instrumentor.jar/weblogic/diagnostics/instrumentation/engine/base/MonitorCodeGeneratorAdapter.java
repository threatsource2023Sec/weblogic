package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.utils.PropertyHelper;

class MonitorCodeGeneratorAdapter implements MonitorCodeGenerator, InstrumentationEngineConstants {
   protected ClassInstrumentor classInstrumentor;
   protected ClassVisitor classVisitor;
   protected MethodVisitor codeVisitor;
   protected MonitorSpecificationBase monitor;
   protected Type[] argTypes;
   protected Type retType;
   protected RegisterFile registers;
   protected String jpName;
   protected boolean argsSensitive;
   protected boolean throwableCaptured;
   protected String fieldPrefix;
   protected String className;
   protected String auxClassName;
   protected String supportClassName;
   protected String monFieldName;
   protected String monClassType;
   private static final boolean EMIT_TRACE = PropertyHelper.getBoolean("weblogic.diagnostics.instrumentation.emit_trace");

   protected void emitTrace() {
      if (EMIT_TRACE) {
         String msg = "Calling " + this.auxClassName.replace('/', '.') + "." + this.monFieldName + ".isEnabled()";
         this.codeVisitor.visitFieldInsn(178, "java/lang/System", "out", "Ljava/io/PrintStream;");
         this.codeVisitor.visitLdcInsn(msg);
         this.codeVisitor.visitMethodInsn(182, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
      }

   }

   public void init(ClassInstrumentor classInstrumentor, ClassVisitor classVisitor, MethodVisitor codeVisitor, MonitorSpecificationBase monitor, Type[] argTypes, Type retType, RegisterFile registers, String jpName, boolean argsSensitive, boolean throwableCaptured) {
      this.classInstrumentor = classInstrumentor;
      this.classVisitor = classVisitor;
      this.codeVisitor = codeVisitor;
      this.monitor = monitor;
      this.argTypes = argTypes;
      this.retType = retType;
      this.registers = registers;
      this.jpName = jpName;
      this.argsSensitive = argsSensitive;
      this.throwableCaptured = throwableCaptured;
      this.fieldPrefix = "_WLDF$INST_FLD_";
      this.className = classInstrumentor.getClassName();
      this.auxClassName = classInstrumentor.getAuxClassName();
      this.supportClassName = classInstrumentor.getInstrumentationSupportClassName();
      this.monFieldName = this.fieldPrefix + monitor.getType();
      this.monFieldName = this.monFieldName.replace('/', '$').replace('.', '$');
      this.monClassType = monitor.isStandardMonitor() ? "weblogic/diagnostics/instrumentation/StandardMonitor" : "weblogic/diagnostics/instrumentation/DelegatingMonitor";
   }

   public void emitBeforeExec(int monitorIndex, boolean singleMonitorOptimization) {
   }

   public void emitAfterExec(int monitorIndex, boolean singleMonitorOptimization) {
   }

   public void emitBeforeCall(boolean argsOnStack, int monitorIndex, boolean singleMonitorOptimization, boolean isNewCall) {
   }

   public void emitAfterCall(int monitorIndex, boolean singleMonitorOptimization) {
   }

   protected void loadJoinpoint(boolean before, boolean argsOnStack, boolean isNewCall) {
      if (this.registers.getLocalHolderRequired()) {
         throw new RuntimeException("loadJoinpoint should not be called when using LocalHolder");
      } else {
         boolean captureArgs = false;
         boolean captureReturn = false;
         int argsIndex = this.registers.getArgumentsRegister();
         int retValIndex = this.registers.getReturnValueRegister();
         int scratchIndex = this.registers.getScratchRegister();
         int jpIndex = this.registers.getJoinpointRegister();
         if (before && this.monitor.allowCaptureArguments() && argsIndex >= 0) {
            captureArgs = true;
            if (argsOnStack && scratchIndex < 0) {
               captureArgs = false;
            }
         }

         if (captureArgs) {
            if (this.argsSensitive && this.classInstrumentor.getSensitiveOptimize()) {
               this.captureSensitiveArguments(this.codeVisitor);
            } else {
               this.captureArguments(this.codeVisitor, argsOnStack, isNewCall);
            }
         }

         if (!before && this.monitor.allowCaptureReturnValue() && !this.retType.equals(Type.VOID_TYPE) && retValIndex >= 0) {
            captureReturn = true;
         }

         if (this.jpName != null) {
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, this.jpName, "L" + JoinPoint.class.getName().replace('.', '/') + ";");
         } else if (jpIndex >= 0) {
            this.codeVisitor.visitVarInsn(25, jpIndex);
         } else {
            this.codeVisitor.visitInsn(1);
         }

         if (captureArgs || captureReturn) {
            if (captureArgs) {
               this.codeVisitor.visitVarInsn(25, argsIndex);
            } else {
               this.codeVisitor.visitInsn(1);
            }

            if (captureReturn) {
               this.codeVisitor.visitVarInsn(25, retValIndex);
            } else {
               this.codeVisitor.visitInsn(1);
            }

            this.codeVisitor.visitMethodInsn(184, this.supportClassName, "createDynamicJoinPoint", WLDF_CREATE_DYNAMIC_JOINPOINT_DESC);
            if (before && captureArgs && jpIndex >= 0) {
               this.codeVisitor.visitInsn(89);
               this.codeVisitor.visitVarInsn(58, jpIndex);
            }
         }

      }
   }

   protected void loadJoinpointToLocalHolder(boolean before, int monitorIndex, boolean singleMonitorOptimization) {
      if (!this.registers.getLocalHolderRequired()) {
         throw new RuntimeException("loadJoinpointToLocalHolder should not be called when using locals");
      } else {
         if (!singleMonitorOptimization) {
            this.codeVisitor.visitVarInsn(25, this.registers.getLocalHolderRegister());
            this.codeVisitor.visitIntInsn(16, monitorIndex);
            this.codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "monitorIndex", "I");
         }

         boolean captureArgs = false;
         boolean captureReturn = false;
         int holderIndex = this.registers.getLocalHolderRegister();
         if (before && this.monitor.allowCaptureArguments() && holderIndex >= 0) {
            captureArgs = true;
         }

         if (!before && this.monitor.allowCaptureReturnValue() && !this.retType.equals(Type.VOID_TYPE) && holderIndex >= 0) {
            captureReturn = true;
         }

         if (captureArgs || captureReturn) {
            this.codeVisitor.visitVarInsn(25, holderIndex);
            this.codeVisitor.visitMethodInsn(184, this.supportClassName, "createDynamicJoinPoint", WLDF_CREATE_DYNAMIC_JOINPOINT_HOLDER_DESC);
         }

      }
   }

   private void captureArguments(MethodVisitor codeVisitor, boolean argsOnStack, boolean isNewCall) {
      int argCnt = this.argTypes.length;
      int argsIndex = true;
      int scratchIndex = true;
      int argsIndex = this.registers.getArgumentsRegister();
      int scratchIndex = this.registers.getScratchRegister();
      codeVisitor.visitInsn(1);
      codeVisitor.visitVarInsn(58, argsIndex);
      codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
      codeVisitor.visitMethodInsn(185, this.monClassType, "isArgumentsCaptureNeeded", "()Z");
      Label l1 = new Label();
      codeVisitor.visitJumpInsn(153, l1);
      codeVisitor.visitIntInsn(16, argCnt);
      codeVisitor.visitTypeInsn(189, "java/lang/Object");
      codeVisitor.visitVarInsn(58, argsIndex);
      int i;
      if (argsOnStack) {
         for(i = argCnt - 1; i >= 0; --i) {
            if (i == 0 && isNewCall) {
               codeVisitor.visitVarInsn(25, argsIndex);
               codeVisitor.visitIntInsn(16, i);
               codeVisitor.visitInsn(1);
               codeVisitor.visitInsn(83);
            } else {
               CodeUtils.objectifyArg(codeVisitor, this.argTypes[i], this.supportClassName);
               codeVisitor.visitVarInsn(58, scratchIndex);
               codeVisitor.visitVarInsn(25, argsIndex);
               codeVisitor.visitIntInsn(16, i);
               codeVisitor.visitVarInsn(25, scratchIndex);
               codeVisitor.visitInsn(83);
            }
         }

         for(i = isNewCall ? 1 : 0; i < argCnt; ++i) {
            codeVisitor.visitVarInsn(25, argsIndex);
            codeVisitor.visitIntInsn(16, i);
            codeVisitor.visitInsn(50);
            CodeUtils.deObjectifyArg(codeVisitor, this.argTypes[i], this.supportClassName);
         }
      } else {
         i = 0;

         for(int i = 0; i < argCnt; ++i) {
            Type aType = this.argTypes[i];
            codeVisitor.visitVarInsn(25, argsIndex);
            codeVisitor.visitIntInsn(16, i);
            codeVisitor.visitVarInsn(aType.getOpcode(21), i);
            CodeUtils.objectifyArg(codeVisitor, aType, this.supportClassName);
            codeVisitor.visitInsn(83);
            i += aType.getSize();
         }
      }

      codeVisitor.visitLabel(l1);
   }

   private void captureSensitiveArguments(MethodVisitor codeVisitor) {
      int argsIndex = this.registers.getArgumentsRegister();
      codeVisitor.visitInsn(1);
      codeVisitor.visitVarInsn(58, argsIndex);
      codeVisitor.visitFieldInsn(178, this.auxClassName, this.monFieldName, "L" + this.monClassType + ";");
      codeVisitor.visitMethodInsn(185, this.monClassType, "isArgumentsCaptureNeeded", "()Z");
      Label l1 = new Label();
      codeVisitor.visitJumpInsn(153, l1);
      codeVisitor.visitIntInsn(16, this.argTypes.length);
      codeVisitor.visitMethodInsn(184, this.supportClassName, "toSensitive", "(I)[Ljava/lang/Object;");
      codeVisitor.visitVarInsn(58, argsIndex);
      codeVisitor.visitLabel(l1);
   }
}
