package weblogic.diagnostics.instrumentation.engine.base;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.utils.PropertyHelper;

class FinalizingClassVisitor extends ClassVisitor implements InstrumentationEngineConstants {
   private static final int DEFAULT_CLINIT_LINENUMBER = PropertyHelper.getInteger("weblogic.diagnostics.instrumentor.constructor.lineno", 1);
   private ClassInstrumentor classInstrumentor;
   private String className;
   private boolean hasStaticInitializer;

   FinalizingClassVisitor(ClassInstrumentor classInstrumentor, ClassVisitor cv) {
      super(458752, cv);
      this.classInstrumentor = classInstrumentor;
   }

   ClassInstrumentor getClassInstrumentor() {
      return this.classInstrumentor;
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.className = name;
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      return null;
   }

   public void visitAttribute(Attribute attr) {
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      return null;
   }

   public void visitInnerClass(String name, String outerName, String innerName, int access) {
   }

   public void visitOuterClass(String owner, String name, String desc) {
   }

   public void visitNestMember(String nestMember) {
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      if (name.equals("<clinit>")) {
         this.hasStaticInitializer = true;
         this.classInstrumentor.setStaticInitializerMark(true);
         MethodVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions);
         if (mv == null) {
            return null;
         } else {
            if (!this.classInstrumentor.getInstrumentorEngine().isHotswapAllowed()) {
               this.classInstrumentor.emitInitializerCode(this.cv, mv);
            }

            return mv;
         }
      } else {
         return null;
      }
   }

   public void visitEnd() {
      if (this.classInstrumentor.getInstrumentorEngine().isHotswapAllowed()) {
         if (!this.classInstrumentor.isHotSwapWithNoAux()) {
            this.classInstrumentor.generateAuxClass();
         }
      } else if (!this.hasStaticInitializer) {
         if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_WEAVING.debug("FinalizingClassVisitor.visitEnd: Creating <clinit> for: " + this.className);
         }

         MethodVisitor mv = this.cv.visitMethod(8, "<clinit>", "()V", (String)null, (String[])null);
         Label label = new Label();
         mv.visitLabel(label);
         mv.visitLineNumber(DEFAULT_CLINIT_LINENUMBER, label);
         this.classInstrumentor.emitInitializerCode(this.cv, mv);
         mv.visitInsn(177);
         mv.visitMaxs(0, 0);
      }

      super.visitEnd();
   }

   public void visitSource(String source, String debug) {
   }
}
