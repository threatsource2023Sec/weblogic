package weblogic.ejb.container.ejbc.bytecodegen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

class SerializableIcptrGenerator implements Generator {
   private final String clsName;
   private final String superClsName;

   SerializableIcptrGenerator(String interceptorImpl, String baseInterceptor) {
      this.clsName = BCUtil.binName(interceptorImpl);
      this.superClsName = BCUtil.binName(baseInterceptor);
   }

   public Generator.Output generate() {
      ClassWriter cw = new ClassWriter(0);
      cw.visit(49, 49, this.clsName, (String)null, this.superClsName, new String[]{"java/io/Serializable"});
      BCUtil.addDefInit(cw, this.superClsName);
      BCUtil.addSerializationSupport(this.clsName, cw, (MethodVisitor)null);
      cw.visitEnd();
      return new ClassFileOutput(this.clsName, cw.toByteArray());
   }
}
