package weblogic.diagnostics.instrumentation.engine.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.JoinPointImpl;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;

class InlinedCodeTransformer extends CodeTransformer {
   private int access;
   private String className;
   private String auxClassName;
   private String methodName;
   private String methodDesc;
   private Label lastLabel;
   private int firstAvailableIndex;
   private JoinPointInfo executionJoinpointInfo;
   private MonitorSpecificationBase[] applicableMonitorsForMethod;
   private boolean needsCodeAtEntry;
   private boolean needsCodeAtExit;
   private List methodCodeGenerators;
   private Label entryLabel;
   private Label exceptionLabel;
   private Label afterLabel;
   private List afterLabelsNormalReturn;
   private int savedReturnOpcode = -2;
   private RegisterFile templateRegisterFile;
   private RegisterFile methodRegisters;
   private boolean deferEntryCode = false;
   private boolean isInitializer = false;
   private boolean pastSuperOrThis;
   private Map instrumentableHandlerLabels = new HashMap();
   private String instrumentableExceptionClass;
   private static final String WLDF_JOINPOINT_DESC = "L" + JoinPoint.class.getName().replace('.', '/') + ";";

   InlinedCodeTransformer(ClassInstrumentor classInstrumentor, ClassVisitor classVisitor, MethodVisitor codeVisitor, int access, String className, String methodName, String methodDesc, String[] exceptions) {
      super(classInstrumentor, classVisitor, codeVisitor);
      this.access = access;
      this.className = className;
      this.auxClassName = classInstrumentor.getAuxClassName();
      this.methodName = methodName;
      this.methodDesc = methodDesc;
      this.applicableMonitorsForMethod = classInstrumentor.getMonitorsForMethod(methodName, methodDesc);
      this.firstAvailableIndex = classInstrumentor.getMaxLocalsForMethod(methodName, methodDesc);
      this.isInitializer = methodName.equals("<init>");
      this.needsCodeAtEntry = this.needsBeforeBlock(this.applicableMonitorsForMethod);
      this.needsCodeAtExit = this.needsAfterBlock(this.applicableMonitorsForMethod);
      if (!this.isInitializer) {
         this.pastSuperOrThis = true;
      }

      this.templateRegisterFile = new RegisterFile(classInstrumentor.getStackmapFrameGeneration());
      this.firstAvailableIndex = this.templateRegisterFile.assignSharedRegisters(this.firstAvailableIndex);
      if (this.needsCodeAtEntry || this.needsCodeAtExit) {
         if (this.isInitializer) {
            this.deferEntryCode = true;
         } else {
            classInstrumentor.incrementExecutionJoinpointCount(1);
            this.generateCodeBeforeMethod();
            this.deferEntryCode = false;
         }
      }

   }

   private boolean isStaticAccess() {
      return (this.access & 8) != 0;
   }

   private void generateCodeBeforeMethod() {
      String sourceFileName = this.classInstrumentor.getSourceFileName();
      Map phiMap = this.classInstrumentor.getPointcutHandlingInfoMapForMethod(this.methodName, this.methodDesc);
      boolean argsSensitive = this.allArgumentsSensitive(phiMap, this.isStaticAccess());
      this.executionJoinpointInfo = new JoinPointInfo(new JoinPointImpl(sourceFileName, this.className, this.methodName, this.methodDesc, 0, (String)null, (String)null, (String)null, phiMap, this.isStaticAccess()), this.lastLabel);
      boolean isStatic = (this.access & 8) != 0;
      boolean captureArgs = this.needsArgumentCapture(this.applicableMonitorsForMethod);
      boolean captureReturnVal = this.needsReturnCapture(this.applicableMonitorsForMethod);
      boolean captureThisOnly = isStatic ? false : this.needsThisOnlyCapture(this.applicableMonitorsForMethod);
      this.methodRegisters = this.createJoinpointRegisters(this.templateRegisterFile, captureArgs, captureReturnVal);
      if (this.methodRegisters.getLocalHolderRequired()) {
         this.templateRegisterFile.setWithinExecuteAdvice();
         this.executionJoinpointInfo.setMonitors(this.applicableMonitorsForMethod);
      }

      String jpName = this.classInstrumentor.registerJoinPoint(this.executionJoinpointInfo);
      Type retType = Type.getReturnType(this.methodDesc);
      Type[] argTypes = this.getArgumentTypes(this.access, this.className, this.methodDesc);
      this.methodCodeGenerators = this.getMonitorCodeGenerators(this.codeVisitor, this.applicableMonitorsForMethod, argTypes, retType, this.firstAvailableIndex, this.methodRegisters, jpName, argsSensitive, this.classInstrumentor.getInstrumentorEngine().isThrowableCaptured());
      if (!this.methodRegisters.getLocalHolderRequired()) {
         this.firstAvailableIndex += 3 * this.getAroundMonitorCount(this.applicableMonitorsForMethod);
      }

      int i;
      if (this.methodRegisters.getLocalHolderRequired()) {
         int holderIndex = this.methodRegisters.getLocalHolderRegister();
         if (this.classInstrumentor.isHotSwapWithNoAux()) {
            i = this.classInstrumentor.getAuxHolderClassIndex();
            int jpIndex = LocalHolder.addAuxJPMons(i, this.classInstrumentor.generateJoinPointAuxInfo(jpName));
            this.codeVisitor.visitLdcInsn(new Integer(i));
            this.codeVisitor.visitLdcInsn(new Integer(jpIndex));
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/LocalHolder", "getInstance", WLDF_LOCALHOLDER_GETINSTANCE_DESC_AUXKEY);
         } else {
            String jpMonsFieldName = CodeUtils.getJPMonitorsFieldName(jpName);
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, jpName, WLDF_JOINPOINT_DESC);
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, jpMonsFieldName, "[L" + DiagnosticMonitor.class.getName().replace('.', '/') + ";");
            this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/LocalHolder", "getInstance", WLDF_LOCALHOLDER_GETINSTANCE_DESC);
         }

         if (this.needsCodeAtEntry) {
            this.codeVisitor.visitInsn(89);
            this.codeVisitor.visitVarInsn(58, holderIndex);
            Label label_1 = new Label();
            this.codeVisitor.visitJumpInsn(198, label_1);
            if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_WEAVING.debug("generateCodeBeforeMethod " + this.className + " " + this.methodName + " " + this.methodDesc);
            }

            if (captureArgs || captureThisOnly) {
               if (argsSensitive && this.classInstrumentor.getSensitiveOptimize()) {
                  CodeUtils.captureSensitiveArguments(this.codeVisitor, this.methodRegisters, this.supportClassName, argTypes == null ? 0 : argTypes.length);
               } else {
                  CodeUtils.captureArguments(this.codeVisitor, false, this.methodRegisters, this.supportClassName, argTypes, false, captureArgs, captureThisOnly);
               }
            }

            boolean singleMonitorOptimization = this.methodCodeGenerators.size() == 1;
            int i = 0;
            Iterator it = this.methodCodeGenerators.iterator();

            while(it.hasNext()) {
               MonitorCodeGenerator generator = (MonitorCodeGenerator)it.next();
               generator.emitBeforeExec(i++, singleMonitorOptimization);
            }

            if (captureArgs || captureThisOnly) {
               this.codeVisitor.visitVarInsn(25, holderIndex);
               this.codeVisitor.visitMethodInsn(182, "weblogic/diagnostics/instrumentation/LocalHolder", "resetPostBegin", "()V");
            }

            this.codeVisitor.visitLabel(label_1);
         } else {
            this.codeVisitor.visitVarInsn(58, holderIndex);
         }
      } else if (this.needsCodeAtEntry) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("generateCodeBeforeMethod " + this.className + " " + this.methodName + " " + this.methodDesc);
         }

         boolean singleMonitorOptimization = this.methodCodeGenerators.size() == 1;
         i = 0;
         Iterator it = this.methodCodeGenerators.iterator();

         while(it.hasNext()) {
            MonitorCodeGenerator generator = (MonitorCodeGenerator)it.next();
            generator.emitBeforeExec(i++, singleMonitorOptimization);
         }
      }

      if (this.needsCodeAtExit) {
         this.entryLabel = new Label();
         this.exceptionLabel = new Label();
         this.afterLabel = new Label();
         if (this.methodRegisters.getLocalHolderRequired()) {
            this.afterLabelsNormalReturn = new ArrayList();
         }

         this.codeVisitor.visitTryCatchBlock(this.entryLabel, this.exceptionLabel, this.exceptionLabel, (String)null);
         this.codeVisitor.visitLabel(this.entryLabel);
      }

      this.classInstrumentor.setModified(true);
   }

   private void generateCodeAfterMethod() {
      if (this.needsCodeAtExit) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("generateCodeAfterMethod " + this.className + " " + this.methodName + " " + this.methodDesc);
         }

         Type retType = Type.getReturnType(this.methodDesc);
         if (this.methodRegisters.getLocalHolderRequired()) {
            this.emitExceptionBlock(retType);
            Iterator var2 = this.afterLabelsNormalReturn.iterator();

            while(var2.hasNext()) {
               Label label = (Label)var2.next();
               this.emitNormalReturnBlock(retType, label);
            }
         } else {
            CodeUtils.generateCatchAll(this.codeVisitor, this.methodRegisters.getExceptionRegister(), retType, this.exceptionLabel, this.afterLabel);
            this.emitFinallyBlock(retType);
         }

      }
   }

   private void emitFinallyBlock(Type retType) {
      int returnValueRegister = this.methodRegisters.getReturnValueRegister();
      int returnAddressRegister = this.methodRegisters.getReturnAddressRegister();
      this.codeVisitor.visitLabel(this.afterLabel);
      this.codeVisitor.visitVarInsn(58, returnAddressRegister);
      if (!retType.equals(Type.VOID_TYPE) && returnValueRegister >= 0) {
         this.codeVisitor.visitInsn(CodeUtils.getDUPInstruction(retType));
         CodeUtils.objectifyArg(this.codeVisitor, retType, this.supportClassName);
         this.codeVisitor.visitVarInsn(58, returnValueRegister);
      }

      int size = this.methodCodeGenerators != null ? this.methodCodeGenerators.size() : 0;
      boolean singleMonitorOptimization = size == 1;

      for(int i = size - 1; i >= 0; --i) {
         MonitorCodeGenerator generator = (MonitorCodeGenerator)this.methodCodeGenerators.get(i);
         generator.emitAfterExec(i, singleMonitorOptimization);
      }

      this.codeVisitor.visitVarInsn(169, returnAddressRegister);
   }

   private void emitExceptionBlock(Type retType) {
      int holderIndex = this.methodRegisters.getLocalHolderRegister();
      this.codeVisitor.visitLabel(this.exceptionLabel);
      this.codeVisitor.visitVarInsn(25, holderIndex);
      Label label_1 = new Label();
      this.codeVisitor.visitJumpInsn(198, label_1);
      this.codeVisitor.visitInsn(89);
      this.codeVisitor.visitVarInsn(25, holderIndex);
      this.codeVisitor.visitInsn(95);
      this.codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "th", "Ljava/lang/Throwable;");
      if (!retType.equals(Type.VOID_TYPE) && holderIndex >= 0) {
         this.codeVisitor.visitVarInsn(25, holderIndex);
         CodeUtils.pushDummyReturnValue(this.codeVisitor, retType);
         CodeUtils.objectifyArg(this.codeVisitor, retType, this.supportClassName);
         this.codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "ret", "Ljava/lang/Object;");
      }

      int size = this.methodCodeGenerators != null ? this.methodCodeGenerators.size() : 0;
      boolean singleMonitorOptimization = size == 1;

      for(int i = size - 1; i >= 0; --i) {
         MonitorCodeGenerator generator = (MonitorCodeGenerator)this.methodCodeGenerators.get(i);
         generator.emitAfterExec(i, singleMonitorOptimization);
      }

      this.codeVisitor.visitLabel(label_1);
      this.codeVisitor.visitInsn(191);
   }

   private void emitNormalReturnBlock(Type retType, Label afterLabelNormalReturn) {
      if (this.savedReturnOpcode != -2) {
         int holderIndex = this.methodRegisters.getLocalHolderRegister();
         this.codeVisitor.visitLabel(afterLabelNormalReturn);
         this.codeVisitor.visitVarInsn(25, holderIndex);
         Label label_1 = new Label();
         this.codeVisitor.visitJumpInsn(198, label_1);
         if (!retType.equals(Type.VOID_TYPE)) {
            this.codeVisitor.visitInsn(CodeUtils.getDUPInstruction(retType));
            CodeUtils.objectifyArg(this.codeVisitor, retType, this.supportClassName);
            this.codeVisitor.visitVarInsn(25, holderIndex);
            this.codeVisitor.visitInsn(95);
            this.codeVisitor.visitFieldInsn(181, "weblogic/diagnostics/instrumentation/LocalHolder", "ret", "Ljava/lang/Object;");
         }

         int size = this.methodCodeGenerators != null ? this.methodCodeGenerators.size() : 0;
         boolean singleMonitorOptimization = size == 1;

         for(int i = size - 1; i >= 0; --i) {
            MonitorCodeGenerator generator = (MonitorCodeGenerator)this.methodCodeGenerators.get(i);
            generator.emitAfterExec(i, singleMonitorOptimization);
         }

         this.codeVisitor.visitLabel(label_1);
         super.visitInsn(this.savedReturnOpcode);
      }
   }

   public void visitInsn(int opcode) {
      if (this.deferEntryCode) {
         if (this.pastSuperOrThis) {
            this.classInstrumentor.incrementExecutionJoinpointCount(1);
            this.generateCodeBeforeMethod();
            this.deferEntryCode = false;
         } else if (opcode == 183) {
            this.pastSuperOrThis = true;
         }
      }

      boolean skipReturnEmit = false;
      if (this.needsCodeAtExit && this.isReturnInsn(opcode)) {
         if (this.methodRegisters.getLocalHolderRequired()) {
            Label afterLabelNormal = new Label();
            this.afterLabelsNormalReturn.add(afterLabelNormal);
            this.codeVisitor.visitJumpInsn(167, afterLabelNormal);
            this.savedReturnOpcode = opcode;
            skipReturnEmit = true;
         } else {
            this.codeVisitor.visitJumpInsn(168, this.afterLabel);
         }
      }

      if (!skipReturnEmit) {
         super.visitInsn(opcode);
      }

   }

   private void emitCatchThrowable() {
      if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_WEAVING.debug("Emitting code to capture exception of type " + this.instrumentableExceptionClass);
      }

      this.codeVisitor.visitInsn(89);
      this.codeVisitor.visitMethodInsn(184, this.supportClassName, "catchThrowable", "(Ljava/lang/Throwable;)V");
      this.classInstrumentor.incrementCatchJoinpointCount(1);
      this.classInstrumentor.setModified(true);
      this.instrumentableExceptionClass = null;
   }

   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
      super.visitFrame(type, nLocal, local, nStack, stack);
      if (this.instrumentableExceptionClass != null) {
         this.emitCatchThrowable();
      }

   }

   private boolean isReturnInsn(int opcode) {
      switch (opcode) {
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
            return true;
         default:
            return false;
      }
   }

   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
      boolean methodOwnerIsIntf = opcode == 185;
      this.visitMethodInsn(opcode, owner, name, desc, methodOwnerIsIntf);
   }

   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean intf) {
      CallsiteHandler callHandler = this.getCallsiteHandler(opcode, owner, name, desc);
      if (callHandler != null) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("generateInlinedForCall " + owner + " " + name + " " + desc);
         }

         boolean isStaticCall = opcode == 184;
         this.classInstrumentor.incrementCallJoinpointCount(1);
         String sourceFileName = this.classInstrumentor.getSourceFileName();
         JoinPointInfo jpInfo = new JoinPointInfo(new JoinPointImpl(sourceFileName, owner, name, desc, 0, this.className, this.methodName, this.methodDesc, this.classInstrumentor.getPointcutHandlingInfoMapForCallsite(owner, name, desc), isStaticCall), this.lastLabel);
         if (this.templateRegisterFile.getLocalHolderRequired()) {
            jpInfo.setMonitors(this.classInstrumentor.getMonitorsForCallsite(owner, name, desc, this.className, this.methodName, this.methodDesc, opcode));
         }

         String jpName = this.classInstrumentor.registerJoinPoint(jpInfo);
         if (this.templateRegisterFile.getLocalHolderRequired()) {
            if (this.classInstrumentor.isHotSwapWithNoAux()) {
               int classIndex = this.classInstrumentor.getAuxHolderClassIndex();
               int jpIndex = LocalHolder.addAuxJPMons(classIndex, this.classInstrumentor.generateJoinPointAuxInfo(jpName));
               this.codeVisitor.visitLdcInsn(new Integer(classIndex));
               this.codeVisitor.visitLdcInsn(new Integer(jpIndex));
               this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/LocalHolder", "getInstance", WLDF_LOCALHOLDER_GETINSTANCE_DESC_AUXKEY);
            } else {
               String jpMonsFieldName = CodeUtils.getJPMonitorsFieldName(jpName);
               this.codeVisitor.visitFieldInsn(178, this.auxClassName, jpName, WLDF_JOINPOINT_DESC);
               this.codeVisitor.visitFieldInsn(178, this.auxClassName, jpMonsFieldName, "[L" + DiagnosticMonitor.class.getName().replace('.', '/') + ";");
               this.codeVisitor.visitMethodInsn(184, "weblogic/diagnostics/instrumentation/LocalHolder", "getInstance", WLDF_LOCALHOLDER_GETINSTANCE_DESC);
            }
         } else {
            this.codeVisitor.visitFieldInsn(178, this.auxClassName, jpName, WLDF_JOINPOINT_DESC);
            this.codeVisitor.visitVarInsn(58, this.templateRegisterFile.getJoinpointRegister());
         }

         callHandler.handleCallsite(this.codeVisitor, opcode, owner, name, desc, opcode == 183 && "<init>".equals(name));
      } else {
         super.visitMethodInsn(opcode, owner, name, desc, intf);
      }

   }

   private CallsiteHandler getCallsiteHandler(int opcode, String owner, String name, String desc) {
      ClassInfo classInfo;
      if (!this.pastSuperOrThis) {
         if (opcode == 183 && name.equals("<init>")) {
            if (owner.equals(this.className)) {
               this.pastSuperOrThis = true;
            } else {
               classInfo = this.classInstrumentor.getClassInfo();
               if (classInfo != null && owner.equals(classInfo.getSuperClassName())) {
                  this.pastSuperOrThis = true;
               }
            }
         }

         return null;
      } else {
         if (opcode == 183) {
            classInfo = this.classInstrumentor.getClassInfo();
            if (classInfo != null && !owner.equals(classInfo.getClassName())) {
               return null;
            }
         }

         boolean isStaticCall = opcode == 184;
         CallsiteHandler handler = null;
         MonitorSpecificationBase[] applicableMonitors = null;
         if (this.classInstrumentor.hasEligibleMonitorsForCallsites()) {
            applicableMonitors = this.classInstrumentor.getMonitorsForCallsite(owner, name, desc, this.className, this.methodName, this.methodDesc, opcode);
         }

         int cnt = applicableMonitors != null ? applicableMonitors.length : 0;
         if (cnt == 0) {
            return null;
         } else {
            Type retType = Type.getReturnType(desc);
            Type[] argTypes = this.getArgumentTypes(opcode == 184 ? 8 : 0, owner, desc);
            boolean needsBefore = this.needsBeforeBlock(applicableMonitors);
            boolean needsAfter = this.needsAfterBlock(applicableMonitors);
            boolean captureArgs = this.needsArgumentCapture(applicableMonitors);
            boolean captureReturnVal = this.needsReturnCapture(applicableMonitors);
            RegisterFile registers = this.createJoinpointRegisters(this.templateRegisterFile, captureArgs, captureReturnVal);
            if (registers.getLocalHolderRequired() && registers.getWithinExecuteAdvice()) {
               this.firstAvailableIndex = registers.reassignLocalHolderRegister(this.firstAvailableIndex);
            }

            boolean allArgsSensitive = this.allArgumentsSensitive(this.classInstrumentor.getPointcutHandlingInfoMapForCallsite(owner, name, desc), isStaticCall);
            List codeGenerators = this.getMonitorCodeGenerators(this.codeVisitor, applicableMonitors, argTypes, retType, this.firstAvailableIndex, registers, (String)null, allArgsSensitive, this.classInstrumentor.getInstrumentorEngine().isThrowableCaptured());
            if (!registers.getLocalHolderRequired()) {
               this.firstAvailableIndex += 3 * this.getAroundMonitorCount(applicableMonitors);
            }

            handler = new CallsiteHandler(argTypes, retType, codeGenerators, needsBefore, needsAfter, registers, this.supportClassName, allArgsSensitive && this.classInstrumentor.getSensitiveOptimize(), captureArgs, applicableMonitors);
            this.classInstrumentor.setModified(true);
            return handler;
         }
      }
   }

   public void visitLineNumber(int line, Label start) {
      this.classInstrumentor.registerLabel(start, line);
      super.visitLineNumber(line, start);
   }

   public void visitLabel(Label label) {
      if (this.executionJoinpointInfo != null && this.executionJoinpointInfo.getLabel() == null) {
         this.executionJoinpointInfo.setLabel(label);
      }

      this.lastLabel = label;
      super.visitLabel(label);
      String exceptionClassName = (String)this.instrumentableHandlerLabels.get(label);
      if (exceptionClassName != null) {
         if (this.classInstrumentor.usesFrames()) {
            this.instrumentableExceptionClass = exceptionClassName;
         } else {
            this.emitCatchThrowable();
         }
      }

   }

   public void visitMaxs(int maxStack, int maxLocals) {
      this.generateCodeAfterMethod();
      super.visitMaxs(maxStack, maxLocals);
   }

   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
      super.visitTryCatchBlock(start, end, handler, type);
      if (type != null && this.classInstrumentor.isEligibleCatchBlockInMethod(this.methodName, this.methodDesc, type)) {
         this.instrumentableHandlerLabels.put(handler, type);
      }

   }

   private boolean allArgumentsSensitive(Map phiMap, boolean isStatic) {
      if (phiMap == null) {
         return true;
      } else {
         Iterator iter = phiMap.values().iterator();

         while(iter.hasNext()) {
            PointcutHandlingInfo phi = (PointcutHandlingInfo)iter.next();
            if (phi != null) {
               if (phi.getArgumentValueHandlingInfo() != null) {
                  return false;
               }

               if (!isStatic && phi.getClassValueHandlingInfo() != null) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static class TryCatchBlockStruct {
      Label start;
      Label end;
      Label handler;
      String type;

      TryCatchBlockStruct(Label start, Label end, Label handler, String type) {
         this.start = start;
         this.end = end;
         this.handler = handler;
         this.type = type;
      }
   }
}
