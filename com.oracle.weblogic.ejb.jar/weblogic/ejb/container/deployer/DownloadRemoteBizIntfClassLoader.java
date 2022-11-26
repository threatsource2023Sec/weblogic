package weblogic.ejb.container.deployer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.TreeSet;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

/** @deprecated */
@Deprecated
public final class DownloadRemoteBizIntfClassLoader extends ClassLoader {
   private String newName;
   private boolean IsBizIntf = false;

   public DownloadRemoteBizIntfClassLoader(String newName, ClassLoader parent) {
      super(parent);
      this.newName = newName;
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      Class c = super.loadClass(name);
      if (!Remote.class.isAssignableFrom(c) && !Object.class.equals(c)) {
         if (name != null && name.startsWith("java.")) {
            return c;
         } else {
            byte[] b = null;
            InputStream input = null;

            byte[] b;
            try {
               input = this.getResourceAsStream(name.replace('.', '/') + ".class");
               b = this.getClassBytes(input, name);
            } finally {
               if (input != null) {
                  try {
                     input.close();
                  } catch (IOException var11) {
                  }
               }

            }

            byte[] enb = this.preProcess(name, b);
            if (!this.IsBizIntf) {
               this.IsBizIntf = true;
               return this.defineClass(this.newName, enb, 0, enb.length);
            } else {
               return this.defineClass(name, enb, 0, enb.length);
            }
         }
      } else {
         return c;
      }
   }

   private byte[] getClassBytes(InputStream stream, String name) throws ClassNotFoundException {
      try {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] buffer = new byte[4096];

         int count;
         while((count = stream.read(buffer)) != -1) {
            baos.write(buffer, 0, count);
         }

         baos.close();
         byte[] array = baos.toByteArray();
         return array;
      } catch (Throwable var6) {
         throw new ClassNotFoundException(name);
      }
   }

   private byte[] preProcess(String className, byte[] classBytes) {
      ClassReader cr = new ClassReader(classBytes);
      ClassWriter cw = new ClassWriter(cr, 0);
      Local2RemoteTransformer transformer;
      if (!this.IsBizIntf) {
         transformer = new Local2RemoteTransformer(cw, false, this.newName);
      } else {
         transformer = new Local2RemoteTransformer(cw, true, this.newName);
      }

      cr.accept(transformer, 0);
      byte[] generated = cw.toByteArray();
      if (!this.IsBizIntf) {
         this.writeEnhancedClassBack(this.newName, generated);
      } else {
         this.writeEnhancedClassBack(className, generated);
      }

      return generated;
   }

   private void writeEnhancedClassBack(String cname, byte[] b) {
      if (b != null) {
         try {
            if (System.getProperty("weblogic.ejb.enhancement.debug") == null) {
               return;
            }
         } catch (Exception var20) {
            return;
         }

         File file = new File(System.getProperty("user.home") + File.separatorChar + "enhanced_classes", cname.replace('.', File.separatorChar) + ".class");
         file.getParentFile().mkdirs();
         FileOutputStream writer = null;

         try {
            if (!file.exists()) {
               file.createNewFile();
            }

            writer = new FileOutputStream(file);
            writer.write(b);
         } catch (FileNotFoundException var17) {
            var17.printStackTrace();
         } catch (IOException var18) {
            var18.printStackTrace();
         } finally {
            if (writer != null) {
               try {
                  writer.close();
               } catch (IOException var16) {
                  var16.printStackTrace();
               }
            }

         }

      }
   }

   public class Local2RemoteTransformer extends ClassVisitor {
      private static final String REMOTE = "java/rmi/Remote";
      private static final String REMOTE_EXCEPTION = "java/rmi/RemoteException";
      private final String[] EXCEPTIONS = new String[]{"java/rmi/RemoteException"};
      private final ClassVisitor classvisitor;
      private final boolean onlyTransferMethod;
      private final String generatedIntfName;

      public Local2RemoteTransformer(ClassVisitor classVisitor, boolean b, String newName) {
         super(458752, classVisitor);
         this.classvisitor = classVisitor;
         this.onlyTransferMethod = b;
         this.generatedIntfName = newName;
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         if (!this.onlyTransferMethod) {
            ArrayList list = new ArrayList(interfaces.length + 1);
            String[] result = interfaces;
            int var9 = interfaces.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               String intf = result[var10];
               list.add(intf);
            }

            if (!list.contains("java/rmi/Remote")) {
               list.add("java/rmi/Remote");
            }

            result = new String[list.size()];
            result = (String[])list.toArray(result);
            this.classvisitor.visit(version, access, this.generatedIntfName.replace('.', '/'), signature, superName, result);
         } else {
            this.classvisitor.visit(version, access, name, signature, superName, interfaces);
         }

      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         return this.classvisitor.visitMethod(access, name, desc, signature, this.getExceptions(exceptions));
      }

      private final String[] getExceptions(String[] existing) {
         if (existing != null && existing.length > 0) {
            TreeSet set = new TreeSet();
            String[] newExceptions = existing;
            int var4 = existing.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String exception = newExceptions[var5];
               set.add(exception);
            }

            set.add("java/rmi/RemoteException");
            newExceptions = new String[set.size()];
            set.toArray(newExceptions);
            return newExceptions;
         } else {
            return this.EXCEPTIONS;
         }
      }
   }
}
