package weblogic.diagnostics.instrumentation.engine.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;

class ClassAnalyzer implements InstrumentationEngineConstants {
   private int magic;
   private int[] cpEntryOffsets;
   private char[] tempCharBuffer;
   private int classHeaderOffset;
   private int fieldsTableOffset;
   private int[] methodRefOffsets;
   private int methodRefCount;
   private ClassInfo classInfo;
   private boolean classAnalyzed;
   private Map eligibleMonitorsForMethods;
   private Map eligibleMonitorsForCallsites;
   private Map eligibleExceptionsMap;
   private Map methodInfoMap;
   private Map fieldMap = new HashMap();
   private int majorVersion = 0;
   private int minorVersion = 0;
   private int firstAvailableJoinpointIndex;
   private ClassReader delegateClassReader;
   private AnalyzingClassVisitor analyzingClassVisitor;
   private boolean hasFrames;
   private boolean cantInstrumentClass = false;
   private static final String[] eligibleAnnotationTypes = new String[]{"RuntimeVisibleAnnotations", "RuntimeInvisibleAnnotations"};

   int getMajorVersion() {
      return this.majorVersion;
   }

   int getMinorVersion() {
      return this.minorVersion;
   }

   public boolean getCantInstrumentClass() {
      return this.cantInstrumentClass;
   }

   boolean usesFrames() {
      return this.hasFrames;
   }

   public static boolean isMagicOk(byte[] b) {
      if (b != null && b.length >= 4) {
         return peekInt(0, b) == -889275714;
      } else {
         return false;
      }
   }

   private static int peekInt(int index, byte[] b) {
      return (b[index] & 255) << 24 | (b[index + 1] & 255) << 16 | (b[index + 2] & 255) << 8 | b[index + 3] & 255;
   }

   ClassAnalyzer(byte[] classBytes) {
      this.magic = peekInt(0, classBytes);
      if (this.magic != -889275714) {
         if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_LOGGER.debug("Invalid magic: " + this.magic + ", no further analysis done");
         }

      } else {
         this.delegateClassReader = new ClassReader(classBytes);
         this.minorVersion = this.readUnsignedShort(4);
         this.majorVersion = this.readUnsignedShort(6);
         int offset = 8;
         int constPoolEntryCount = this.readUnsignedShort(offset);
         offset += 2;
         this.cpEntryOffsets = new int[constPoolEntryCount];
         this.methodRefOffsets = new int[constPoolEntryCount];
         this.methodRefCount = 0;
         int maxStringSize = 0;

         for(int i = 1; i < constPoolEntryCount; ++i) {
            int cpType = classBytes[offset];
            this.cpEntryOffsets[i] = offset + 1;
            int entrySize = true;
            int entrySize;
            switch (cpType) {
               case 1:
                  entrySize = 3 + this.readUnsignedShort(offset + 1);
                  maxStringSize = entrySize > maxStringSize ? entrySize : maxStringSize;
                  break;
               case 2:
               case 13:
               case 14:
               case 17:
               default:
                  if (InstrumentationDebug.DEBUG_LOGGER.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_LOGGER.debug("Unhandled Constant Pool Tag Seen: " + cpType + "!!!, check the current Java Class Spec and add support for this type");
                  }

                  entrySize = 3;
                  break;
               case 3:
               case 4:
               case 9:
                  entrySize = 5;
                  break;
               case 5:
               case 6:
                  entrySize = 9;
                  ++i;
                  break;
               case 7:
               case 8:
               case 16:
                  entrySize = 3;
                  break;
               case 10:
               case 11:
                  this.methodRefOffsets[this.methodRefCount++] = offset + 1;
                  entrySize = 5;
                  break;
               case 12:
                  entrySize = 5;
                  break;
               case 15:
                  entrySize = 4;
                  break;
               case 18:
                  entrySize = 5;
            }

            offset += entrySize;
         }

         this.tempCharBuffer = new char[maxStringSize];
         this.classHeaderOffset = offset;
      }
   }

   ClassInfo getClassInfo() {
      if (this.classInfo != null) {
         return this.classInfo;
      } else {
         this.classInfo = new ClassInfo();
         if (this.magic == -889275714) {
            int access = this.readUnsignedShort(this.classHeaderOffset);
            this.classInfo.setInterface((access & 512) != 0);
            this.classInfo.setClassName(this.readClassName(this.classHeaderOffset + 2));
            this.classInfo.setSuperClassName(this.readClassName(this.classHeaderOffset + 4));
            this.classInfo.setInterfaceNames(this.getInterfaces());
            NestAnalyzingClassVisitor nestAnalyzer = new NestAnalyzingClassVisitor(this.classInfo);
            this.delegateClassReader.accept(nestAnalyzer, 1);
            this.classInfo.setValid(true);
         }

         return this.classInfo;
      }
   }

   private String[] getInterfaces() {
      int interfaceCount = this.readUnsignedShort(this.classHeaderOffset + 6);
      String[] interfaces = new String[interfaceCount];
      int offset = this.classHeaderOffset + 8;

      for(int i = 0; i < interfaceCount; ++i) {
         interfaces[i] = this.readClassName(offset);
         offset += 2;
      }

      this.fieldsTableOffset = offset;
      return interfaces;
   }

   int getMaxLocalsForMethod(String name, String desc) {
      int retVal = -1;
      if (this.methodInfoMap != null) {
         MethodInfo methodInfo = (MethodInfo)this.methodInfoMap.get(name + ":" + desc);
         if (methodInfo != null) {
            retVal = methodInfo.getMaxLocals();
         }
      }

      return retVal;
   }

   MethodInfo getMethodInfo(String name, String desc) {
      return this.methodInfoMap != null ? (MethodInfo)this.methodInfoMap.get(name + ":" + desc) : null;
   }

   private void analyzeClass(ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      this.computeCallsiteInfo(classInstrumentor, monitors);
      this.computeMethodInfo(classInstrumentor, monitors);
      int size = monitors != null ? monitors.length : 0;

      for(int i = 0; i < size; ++i) {
         String monitorType = monitors[i].getType();
         this.removePrewovenMonitors(classInstrumentor, this.eligibleMonitorsForCallsites, monitorType);
         this.removePrewovenMonitors(classInstrumentor, this.eligibleMonitorsForMethods, monitorType);
      }

      this.classAnalyzed = true;
   }

   private void removePrewovenMonitors(ClassInstrumentor classInstrumentor, Map map, String monitorType) {
      String monFieldName = "_WLDF$INST_FLD_" + monitorType;
      monFieldName = monFieldName.replace('/', '$').replace('.', '$');
      if (this.hasField(monFieldName)) {
         ArrayList removalCandidates = new ArrayList();
         Iterator it = map.entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Object key = entry.getKey();
            MonitorSpecificationBase[] mArr = (MonitorSpecificationBase[])((MonitorSpecificationBase[])entry.getValue());
            int size = mArr != null ? mArr.length : 0;
            ArrayList list = new ArrayList();

            for(int i = 0; i < size; ++i) {
               MonitorSpecificationBase monitor = mArr[i];
               if (!monitor.getType().equals(monitorType)) {
                  list.add(monitor);
               }
            }

            if (list.size() > 0) {
               mArr = new MonitorSpecificationBase[list.size()];
               mArr = (MonitorSpecificationBase[])((MonitorSpecificationBase[])list.toArray(mArr));
               map.put(key, mArr);
            } else {
               removalCandidates.add(key);
            }
         }

         if (classInstrumentor.getInstrumentorEngine().getTrackMatchedMonitors() && !map.isEmpty()) {
            classInstrumentor.getInstrumentorEngine().addMatchedMonitor(monitorType);
         }

         it = removalCandidates.iterator();

         while(it.hasNext()) {
            Object key = it.next();
            map.remove(key);
         }
      }

   }

   Map getEligibleCallsites(ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      if (!this.classAnalyzed) {
         this.analyzeClass(classInstrumentor, monitors);
      }

      return this.eligibleMonitorsForCallsites;
   }

   private void computeCallsiteInfo(ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      this.eligibleMonitorsForCallsites = new HashMap();
      int monCnt = monitors != null ? monitors.length : 0;
      if (monCnt != 0) {
         for(int i = 0; i < this.methodRefCount; ++i) {
            int offset = this.methodRefOffsets[i];
            int classIndex = this.readUnsignedShort(offset);
            int name_type_index = this.readUnsignedShort(offset + 2);
            String className = this.readUTF8(this.cpEntryOffsets[classIndex]);
            String methodName = this.readUTF8(this.cpEntryOffsets[name_type_index]);
            String methodDesc = this.readUTF8(this.cpEntryOffsets[name_type_index] + 2);
            if (!className.startsWith("[")) {
               ArrayList list = new ArrayList();

               for(int j = 0; j < monCnt; ++j) {
                  MonitorSpecificationBase mSpec = monitors[j];
                  if (mSpec.isEligibleCallsite(classInstrumentor, "L" + className + ";", methodName, methodDesc, (MethodInfo)null)) {
                     list.add(mSpec);
                  }
               }

               if (list.size() > 0) {
                  String key = className + ":" + methodName + ":" + methodDesc;
                  MonitorSpecificationBase[] mArr = new MonitorSpecificationBase[list.size()];
                  mArr = (MonitorSpecificationBase[])((MonitorSpecificationBase[])list.toArray(mArr));
                  this.eligibleMonitorsForCallsites.put(key, mArr);
               }

               if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_WEAVING.debug("getEligibleCallsites: " + className + ":" + methodName + ":" + methodDesc + " found " + list.size() + " monitors");
               }
            }
         }

      }
   }

   boolean hasField(String fieldName) {
      return this.fieldMap.get(fieldName) != null;
   }

   int getFirstAvailableJoinpointIndex() {
      return this.firstAvailableJoinpointIndex;
   }

   Map getEligibleMethods(ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      if (!this.classAnalyzed) {
         this.analyzeClass(classInstrumentor, monitors);
      }

      return this.eligibleMonitorsForMethods;
   }

   Map getEligibleExceptions(ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      if (!this.classAnalyzed) {
         this.analyzeClass(classInstrumentor, monitors);
      }

      return this.eligibleExceptionsMap;
   }

   Map findOverflowedMethods() {
      Map map = new HashMap();
      ClassInfo info = this.getClassInfo();
      if (info.isValid() && !info.isInterface()) {
         int fieldCount = this.readUnsignedShort(this.fieldsTableOffset);
         int offset = this.fieldsTableOffset + 2;

         int methodsCount;
         int i;
         for(methodsCount = 0; methodsCount < fieldCount; ++methodsCount) {
            i = this.readUnsignedShort(offset + 6);
            offset += 8;

            for(int j = 0; j < i; ++j) {
               int attributeLength = this.readInt(offset + 2);
               offset += 6 + attributeLength;
            }
         }

         methodsCount = this.readUnsignedShort(offset);
         offset += 2;

         for(i = 0; i < methodsCount; ++i) {
            String methodName = this.readUTF8(offset + 2);
            String methodDesc = this.readUTF8(offset + 4);
            int attributeCnt = this.readUnsignedShort(offset + 6);
            offset += 8;

            for(int j = 0; j < attributeCnt; ++j) {
               String attributeName = this.readUTF8(offset);
               int attributeLength = this.readInt(offset + 2);
               if (attributeName.equals("Code")) {
                  String key = methodName + "/" + methodDesc;
                  int codeLength = this.readInt(offset + 10);
                  if (codeLength >= 65536) {
                     map.put(key, codeLength);
                     DiagnosticsLogger.logInstrumentedMethodOevrflowError(info.getClassName(), methodName, methodDesc);
                     if ("<clinit>".equals(methodName)) {
                        this.cantInstrumentClass = true;
                     }
                  }

                  if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_WEAVING.debug("Method " + key + " code-length=" + codeLength + ", overall code-attribute length = " + attributeLength);
                  }
               }

               offset += 6 + attributeLength;
            }
         }

         return map;
      } else {
         return map;
      }
   }

   private void computeMethodInfo(ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      this.eligibleMonitorsForMethods = new HashMap();
      this.eligibleExceptionsMap = new HashMap();
      int monCnt = monitors != null ? monitors.length : 0;
      if (monCnt != 0) {
         ClassInfo info = this.getClassInfo();
         if (info.isValid() && !info.isInterface()) {
            int fieldCount = this.readUnsignedShort(this.fieldsTableOffset);
            int offset = this.fieldsTableOffset + 2;

            int jpInd;
            int methodAccess;
            for(int i = 0; i < fieldCount; ++i) {
               String fieldName = this.readUTF8(offset + 2);
               this.fieldMap.put(fieldName, fieldName);
               if (fieldName.startsWith("_WLDF$INST_JPFLD_")) {
                  try {
                     jpInd = Integer.parseInt(fieldName.substring("_WLDF$INST_JPFLD_".length(), fieldName.length()));
                     if (jpInd >= this.firstAvailableJoinpointIndex) {
                        this.firstAvailableJoinpointIndex = jpInd + 1;
                     }
                  } catch (Exception var18) {
                  }
               }

               jpInd = this.readUnsignedShort(offset + 6);
               offset += 8;

               for(int j = 0; j < jpInd; ++j) {
                  methodAccess = this.readInt(offset + 2);
                  offset += 6 + methodAccess;
               }
            }

            String className = info.getClassName();
            int methodsCount = this.readUnsignedShort(offset);
            offset += 2;
            this.methodInfoMap = new HashMap();

            for(jpInd = 0; jpInd < methodsCount; ++jpInd) {
               MethodInfo methodInfo = new MethodInfo();
               methodAccess = this.readUnsignedShort(offset);
               String methodName = this.readUTF8(offset + 2);
               String methodDesc = this.readUTF8(offset + 4);
               methodInfo.setClassName(className);
               methodInfo.setMethodAccess(methodAccess);
               methodInfo.setMethodName(methodName);
               methodInfo.setMethodDesc(methodDesc);
               methodInfo.setAnnotations(new ArrayList());
               int attributeCnt = this.readUnsignedShort(offset + 6);
               offset += 8;

               for(int j = 0; j < attributeCnt; ++j) {
                  String attributeName = this.readUTF8(offset);
                  int attributeLength = this.readInt(offset + 2);
                  if (attributeName.equals("Code")) {
                     methodInfo.setMaxStack(this.readUnsignedShort(offset + 6));
                     methodInfo.setMaxLocals(this.readUnsignedShort(offset + 8));
                     this.methodInfoMap.put(methodName + ":" + methodDesc, methodInfo);
                  }

                  offset += 6 + attributeLength;
               }

               if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_WEAVING.debug("Found method: access=" + methodAccess + " name=" + methodName + " desc=" + methodDesc);
               }
            }

            this.analyzingClassVisitor = new AnalyzingClassVisitor();
            jpInd = classInstrumentor.getInstrumentorEngine().getCatchPointcutCount() == 0 ? 1 : 0;
            this.delegateClassReader.accept(this.analyzingClassVisitor, jpInd);
            this.identifyEligibleMethods(className, classInstrumentor, monitors);
         }
      }
   }

   private void identifyEligibleMethods(String className, ClassInstrumentor classInstrumentor, MonitorSpecificationBase[] monitors) throws InvalidPointcutException {
      Iterator it = this.methodInfoMap.values().iterator();

      while(true) {
         MethodInfo methodInfo;
         int methodAccess;
         String methodName;
         String methodDesc;
         do {
            do {
               do {
                  if (!it.hasNext()) {
                     return;
                  }

                  methodInfo = (MethodInfo)it.next();
                  methodAccess = methodInfo.getMethodAccess();
                  methodName = methodInfo.getMethodName();
                  methodDesc = methodInfo.getMethodDesc();
               } while((methodAccess & 1024) != 0);
            } while((methodAccess & 256) != 0);
         } while(methodName.equals("<clinit>"));

         ArrayList list = new ArrayList();
         int matchingCatchBlocksCount = 0;

         for(int j = 0; j < monitors.length; ++j) {
            MonitorSpecificationBase mSpec = monitors[j];
            if (mSpec.isEligibleMethod(classInstrumentor, "L" + className + ";", methodInfo)) {
               if (InstrumentationDebug.DEBUG_WEAVING_MATCHES.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_WEAVING_MATCHES.debug("Matched monitor " + mSpec.getType() + " with method: " + className + "::" + methodInfo.getMethodName() + methodInfo.getMethodDesc());
               }

               list.add(mSpec);
            }

            List caughtExceptions = methodInfo.getCaughtExceptions();
            if (caughtExceptions != null) {
               Iterator var14 = caughtExceptions.iterator();

               while(var14.hasNext()) {
                  String exceptionClassName = (String)var14.next();
                  String key = methodName + ":" + methodDesc + ":" + exceptionClassName;
                  if (mSpec.isEligibleCatchBlock(classInstrumentor, exceptionClassName, methodInfo)) {
                     if (InstrumentationDebug.DEBUG_WEAVING_MATCHES.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_WEAVING_MATCHES.debug("Matched monitor " + mSpec.getType() + " with caught exception=" + exceptionClassName + " in method: " + className + "::" + methodInfo.getMethodName() + methodInfo.getMethodDesc());
                     }

                     this.eligibleExceptionsMap.put(key, exceptionClassName);
                     ++matchingCatchBlocksCount;
                  }
               }
            }
         }

         if (list.size() > 0) {
            String key = methodName + ":" + methodDesc;
            MonitorSpecificationBase[] mArr = new MonitorSpecificationBase[list.size()];
            mArr = (MonitorSpecificationBase[])((MonitorSpecificationBase[])list.toArray(mArr));
            this.eligibleMonitorsForMethods.put(key, mArr);
         }

         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("getEligibleMethods: " + methodName + ":" + methodDesc + " found " + list.size() + " monitors matching methods and " + matchingCatchBlocksCount + " matching catch blocks");
         }
      }
   }

   String getSourceFileName() {
      return this.classInfo != null ? this.classInfo.getSourceFileName() : null;
   }

   private int readUnsignedShort(int offset) {
      return this.delegateClassReader.readUnsignedShort(offset);
   }

   private int readInt(int offset) {
      return this.delegateClassReader.readInt(offset);
   }

   private String readClassName(int offset) {
      int nameIndex = this.readUnsignedShort(offset);
      return nameIndex == 0 ? null : this.readUTF8(this.cpEntryOffsets[nameIndex]);
   }

   private String readUTF8(int offset) {
      return this.delegateClassReader.readUTF8(offset, this.tempCharBuffer);
   }

   private class AnalyzingMethodVistor extends NullMethodAdapter {
      private MethodInfo methodInfo;

      AnalyzingMethodVistor(String name, String desc) {
         String key = name + ":" + desc;
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("Analyzing method: " + key);
         }

         this.methodInfo = (MethodInfo)ClassAnalyzer.this.methodInfoMap.get(key);
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("Found method annotation: " + desc);
         }

         if (this.methodInfo != null) {
            if (this.methodInfo.getAnnotations() == null) {
               this.methodInfo.setAnnotations(new ArrayList());
            }

            this.methodInfo.getAnnotations().add(desc);
         }

         return null;
      }

      public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
         if (type != null) {
            if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_WEAVING.debug("Found method try-catch for " + type);
            }

            if (this.methodInfo != null) {
               List list = this.methodInfo.getCaughtExceptions();
               if (list == null) {
                  list = new ArrayList();
                  this.methodInfo.setCaughtExceptions((List)list);
               }

               if (!((List)list).contains(type)) {
                  ((List)list).add(type);
               }
            }

         }
      }

      public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
         ClassAnalyzer.this.hasFrames = true;
      }
   }

   private class AnalyzingClassVisitor extends NullClassAdapter {
      private AnalyzingClassVisitor() {
      }

      public void visitSource(String source, String debug) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("Class source file: " + source);
         }

         if (ClassAnalyzer.this.classInfo != null) {
            ClassAnalyzer.this.classInfo.setSourceFileName(source);
         }

      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("Class annotation: " + desc);
         }

         if (ClassAnalyzer.this.classInfo != null) {
            List annotations = ClassAnalyzer.this.classInfo.getClassAnnotations();
            if (annotations == null) {
               annotations = new ArrayList();
               ClassAnalyzer.this.classInfo.setClassAnnotations((List)annotations);
            }

            ((List)annotations).add(desc);
         }

         return null;
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         return ClassAnalyzer.this.new AnalyzingMethodVistor(name, desc);
      }

      // $FF: synthetic method
      AnalyzingClassVisitor(Object x1) {
         this();
      }
   }

   private static class NestAnalyzingClassVisitor extends NullClassAdapter {
      private ClassInfo info;

      NestAnalyzingClassVisitor(ClassInfo info) {
         this.info = info;
      }

      public void visitInnerClass(String name, String outerName, String innerName, int access) {
         if (name.equals(this.info.getClassName()) && outerName != null) {
            this.info.setOuterClassName(outerName);
         }

      }

      public void visitOuterClass(String owner, String name, String desc) {
         this.info.setOuterClassName(owner);
         this.info.setOuterMethodName(name);
         this.info.setOuterMethodDesc(desc);
      }
   }
}
