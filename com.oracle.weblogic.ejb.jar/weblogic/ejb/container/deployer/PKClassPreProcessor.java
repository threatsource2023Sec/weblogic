package weblogic.ejb.container.deployer;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.tree.FieldNode;
import weblogic.utils.classloaders.ClassPreProcessor;

class PKClassPreProcessor extends EJBClassEnhancer implements ClassPreProcessor {
   private static final String _WL_HASHCODE_NAME = "_WL_HASHCODE_";
   private final Map pkClassNames = new ConcurrentHashMap();

   PKClassPreProcessor(List pkClassNames) {
      Iterator var2 = pkClassNames.iterator();

      while(var2.hasNext()) {
         String pkCLassName = (String)var2.next();
         this.pkClassNames.put(pkCLassName, Boolean.FALSE);
      }

   }

   public void initialize(Hashtable params) {
   }

   public byte[] preProcess(String className, byte[] classBytes) {
      if (this.pkClassNames.containsKey(className) && !(Boolean)this.pkClassNames.get(className)) {
         FieldNode fn = new FieldNode(130, "_WL_HASHCODE_", Type.INT_TYPE.getDescriptor(), (String)null, 0);
         ClassReader cr = new ClassReader(classBytes);
         int majorVersion = (classBytes[6] & 255) << 8 | classBytes[7] & 255;
         ClassWriter cw;
         if (majorVersion > 50) {
            cw = new ClassWriter(cr, 2);
         } else {
            cw = new ClassWriter(cr, 0);
         }

         cr.accept(new HashCodeClassEnhancer(cw, fn), 4);
         byte[] bytes = cw.toByteArray();
         this.pkClassNames.put(className, Boolean.TRUE);
         this.writeEnhancedClassBack(className, bytes);
         return bytes;
      } else {
         return classBytes;
      }
   }

   private static final class HashCodeMethodEnhancer extends AdviceAdapter {
      private final String owner;
      private final Label label = new Label();

      protected HashCodeMethodEnhancer(MethodVisitor mv, int access, String name, String desc, String owner) {
         super(458752, mv, access, name, desc);
         this.owner = owner;
      }

      protected void onMethodEnter() {
         this.visitVarInsn(25, 0);
         this.visitFieldInsn(180, this.owner, "_WL_HASHCODE_", Type.INT_TYPE.getDescriptor());
         this.visitJumpInsn(154, this.label);
         this.visitVarInsn(25, 0);
      }

      protected void onMethodExit(int opcode) {
         this.visitFieldInsn(181, this.owner, "_WL_HASHCODE_", Type.INT_TYPE.getDescriptor());
         this.visitLabel(this.label);
         this.visitVarInsn(25, 0);
         this.visitFieldInsn(180, this.owner, "_WL_HASHCODE_", Type.INT_TYPE.getDescriptor());
      }

      public void visitMaxs(int maxStack, int maxLocals) {
         super.visitMaxs(maxStack + 1, maxLocals);
      }
   }

   private static final class InitMethodAdapter extends AdviceAdapter {
      private final String owner;

      public InitMethodAdapter(MethodVisitor mv, int access, String name, String desc, String owner) {
         super(458752, mv, access, name, desc);
         this.owner = owner;
      }

      protected void onMethodEnter() {
      }

      protected void onMethodExit(int opcode) {
         this.visitVarInsn(25, 0);
         this.visitInsn(3);
         this.visitFieldInsn(181, this.owner, "_WL_HASHCODE_", Type.INT_TYPE.getDescriptor());
      }

      public void visitMaxs(int maxStack, int maxLocals) {
         super.visitMaxs(maxStack + 1, maxLocals);
      }
   }

   private static final class HashCodeClassEnhancer extends ClassVisitor {
      private final FieldNode fn;
      private boolean isFieldPresent = false;
      private String owner;
      private ClassVisitor cv;
      private boolean noNeedEnhance;

      public HashCodeClassEnhancer(ClassVisitor classVisitor, FieldNode fn) {
         super(458752, classVisitor);
         this.fn = fn;
         this.cv = classVisitor;
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         if ((access & 512) != 0) {
            this.cv.visit(version, access, name, signature, superName, interfaces);
            this.noNeedEnhance = true;
         } else {
            this.owner = name;
            this.cv.visit(version, access, name, signature, superName, interfaces);
         }
      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         MethodVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions);
         if (this.noNeedEnhance) {
            return (MethodVisitor)mv;
         } else {
            if ("<init>".equals(name)) {
               mv = new InitMethodAdapter((MethodVisitor)mv, access, name, desc, this.owner);
            }

            if ("hashCode".equals(name) && "()I".equals(desc)) {
               mv = new HashCodeMethodEnhancer((MethodVisitor)mv, access, name, desc, this.owner);
            }

            return (MethodVisitor)mv;
         }
      }

      public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
         if (name.equals("_WL_HASHCODE_")) {
            this.isFieldPresent = true;
         }

         return this.cv.visitField(access, name, desc, signature, value);
      }

      public void visitEnd() {
         if (!this.isFieldPresent && !this.noNeedEnhance) {
            this.fn.accept(this.cv);
         }

         super.visitEnd();
      }
   }
}
