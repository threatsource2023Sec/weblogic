package weblogic.diagnostics.instrumentation.engine.base;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.diagnostics.i18n.DiagnosticsLogger;

class CodeTransformer extends MethodVisitor implements InstrumentationEngineConstants {
   protected ClassInstrumentor classInstrumentor;
   protected ClassVisitor classVisitor;
   protected MethodVisitor codeVisitor;
   protected String supportClassName;

   CodeTransformer(ClassInstrumentor classInstrumentor, ClassVisitor classVisitor, MethodVisitor codeVisitor) {
      super(458752, codeVisitor);
      this.classInstrumentor = classInstrumentor;
      this.classVisitor = classVisitor;
      this.codeVisitor = codeVisitor;
      this.supportClassName = classInstrumentor.getInstrumentationSupportClassName();
   }

   protected List identifyAroundMonitors(MonitorSpecificationBase[] applicableMonitors) {
      int size = applicableMonitors != null ? applicableMonitors.length : 0;
      List list = new ArrayList();

      for(int i = 0; i < size; ++i) {
         MonitorSpecificationBase mSpec = applicableMonitors[i];
         if (mSpec.getLocation() == 3) {
            list.add(mSpec);
         }
      }

      return list;
   }

   protected int getAroundMonitorCount(MonitorSpecificationBase[] applicableMonitors) {
      List aroundList = this.identifyAroundMonitors(applicableMonitors);
      return aroundList.size();
   }

   protected int getArgumentsSize(Type[] argTypes) {
      int size = 0;

      for(int i = 0; i < argTypes.length; ++i) {
         size += argTypes[i].getSize();
      }

      return size;
   }

   protected boolean needsBeforeBlock(MonitorSpecificationBase[] applicableMonitors) {
      int size = applicableMonitors != null ? applicableMonitors.length : 0;
      boolean throwableCaptured = this.classInstrumentor.getInstrumentorEngine().isThrowableCaptured();

      for(int i = 0; i < size; ++i) {
         if (this.classInstrumentor.getStackmapFrameGeneration()) {
            return true;
         }

         int location = applicableMonitors[i].getLocation();
         if (location == 3 || location == 1) {
            return true;
         }

         if (throwableCaptured && location == 2) {
            return true;
         }
      }

      return false;
   }

   protected boolean needsAfterBlock(MonitorSpecificationBase[] applicableMonitors) {
      int size = applicableMonitors != null ? applicableMonitors.length : 0;

      for(int i = 0; i < size; ++i) {
         int location = applicableMonitors[i].getLocation();
         if (location == 3 || location == 2) {
            return true;
         }
      }

      return false;
   }

   protected boolean needsArgumentCapture(MonitorSpecificationBase[] applicableMonitors) {
      int size = applicableMonitors != null ? applicableMonitors.length : 0;

      for(int i = 0; i < size; ++i) {
         if (applicableMonitors[i].allowCaptureArguments()) {
            return true;
         }
      }

      return false;
   }

   protected boolean needsThisOnlyCapture(MonitorSpecificationBase[] applicableMonitors) {
      int size = applicableMonitors != null ? applicableMonitors.length : 0;

      for(int i = 0; i < size; ++i) {
         if (applicableMonitors[i].allowCaptureThisOnly()) {
            return true;
         }
      }

      return false;
   }

   protected boolean needsReturnCapture(MonitorSpecificationBase[] applicableMonitors) {
      int size = applicableMonitors != null ? applicableMonitors.length : 0;

      for(int i = 0; i < size; ++i) {
         if (applicableMonitors[i].allowCaptureReturnValue()) {
            return true;
         }
      }

      return false;
   }

   protected Type[] getArgumentTypes(int access, String className, String methodDesc) {
      Type[] argTypes = Type.getArgumentTypes(methodDesc);
      if ((access & 8) != 0) {
         return argTypes;
      } else {
         int argCnt = argTypes != null ? argTypes.length : 0;
         Type[] newArgTypes = new Type[argCnt + 1];
         newArgTypes[0] = Type.getType("L" + className.replace('.', '/') + ";");

         for(int i = 0; i < argCnt; ++i) {
            newArgTypes[i + 1] = argTypes[i];
         }

         return newArgTypes;
      }
   }

   protected List getMonitorCodeGenerators(MethodVisitor codeVisitor, MonitorSpecificationBase[] applicableMonitors, Type[] argTypes, Type retType, int firstAvailableIndex, RegisterFile registerFile, String jpName, boolean argsSensitive, boolean throwableCaptured) {
      List list = new ArrayList();
      int len = applicableMonitors != null ? applicableMonitors.length : 0;

      for(int i = 0; i < len; ++i) {
         MonitorSpecificationBase mSpec = applicableMonitors[i];
         MonitorCodeGenerator generator = null;
         if (mSpec.isStandardMonitor()) {
            String codegenClassName = mSpec.getCodeGenerator();

            try {
               generator = (MonitorCodeGenerator)Class.forName(codegenClassName).newInstance();
            } catch (ClassNotFoundException var17) {
               DiagnosticsLogger.logStdMonClassNotFound(mSpec.getType(), codegenClassName);
            } catch (InstantiationException var18) {
               DiagnosticsLogger.logStdMonCodegenNotInstantiated(mSpec.getType(), codegenClassName);
            } catch (IllegalAccessException var19) {
               DiagnosticsLogger.logStdMonCodegenNotAccessed(mSpec.getType(), codegenClassName);
            }
         } else {
            generator = new DelegatingMonitorCodeGenerator();
         }

         if (generator != null) {
            RegisterFile registers = registerFile.duplicate();
            if (mSpec.getLocation() == 3) {
               firstAvailableIndex = registers.assignAroundRegisters(firstAvailableIndex, true);
            }

            ((MonitorCodeGenerator)generator).init(this.classInstrumentor, this.classVisitor, codeVisitor, mSpec, argTypes, retType, registers, jpName, argsSensitive, throwableCaptured);
            list.add(generator);
         }
      }

      return list;
   }

   protected RegisterFile createJoinpointRegisters(RegisterFile template, boolean captureArgs, boolean captureReturnVal) {
      RegisterFile registers = template.duplicate();
      if (!registers.getLocalHolderRequired()) {
         if (!captureArgs) {
            registers.resetArgumentsRegister();
         }

         if (!captureReturnVal) {
            registers.resetReturnValueRegister();
         }
      }

      return registers;
   }
}
