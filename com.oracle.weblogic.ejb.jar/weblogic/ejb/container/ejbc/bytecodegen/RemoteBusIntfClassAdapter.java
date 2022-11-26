package weblogic.ejb.container.ejbc.bytecodegen;

import java.io.IOException;
import java.io.InputStream;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

public final class RemoteBusIntfClassAdapter extends ClassVisitor {
   private final String rbiBinName;

   private RemoteBusIntfClassAdapter(ClassVisitor cv, String rbiBinName) {
      super(458752, cv);
      this.rbiBinName = rbiBinName;
   }

   public static byte[] getRBIBytes(Class bi, String rbiBinName) throws IOException {
      ClassReader cr = getReader(bi);
      ClassWriter cw = new ClassWriter(cr, 0);
      RemoteBusIntfClassAdapter adapter = new RemoteBusIntfClassAdapter(cw, rbiBinName);
      cr.accept(adapter, 0);
      return cw.toByteArray();
   }

   private static ClassReader getReader(Class bi) throws IOException {
      InputStream is = bi.getClassLoader().getResourceAsStream(getResourceName(bi));
      Throwable var2 = null;

      ClassReader var3;
      try {
         var3 = new ClassReader(is);
      } catch (Throwable var12) {
         var2 = var12;
         throw var12;
      } finally {
         if (is != null) {
            if (var2 != null) {
               try {
                  is.close();
               } catch (Throwable var11) {
                  var2.addSuppressed(var11);
               }
            } else {
               is.close();
            }
         }

      }

      return var3;
   }

   private static String getResourceName(Class intf) {
      String name = "";

      Class curCls;
      for(curCls = intf; curCls.getEnclosingClass() != null; curCls = curCls.getEnclosingClass()) {
         name = "$" + curCls.getSimpleName() + name;
      }

      return (curCls.getName() + name).replace('.', '/') + ".class";
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] intfs) {
      String[] allIntfs = new String[intfs.length + 1];

      for(int i = 0; i < intfs.length; ++i) {
         allIntfs[i] = intfs[i];
      }

      allIntfs[intfs.length] = "java/rmi/Remote";
      this.cv.visit(version, access, this.rbiBinName, signature, superName, allIntfs);
   }

   public void visitSource(String source, String debug) {
   }

   public void visitOuterClass(String owner, String name, String desc) {
      throw new AssertionError("EnclosingMethod NOT allowed for interfaces");
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      return null;
   }

   public void visitInnerClass(String name, String outerName, String innerName, int access) {
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      return null;
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      return name.equals("<clinit>") ? null : this.cv.visitMethod(access, name, desc, signature, exceptions);
   }

   public void visitEnd() {
      this.cv.visitEnd();
   }
}
