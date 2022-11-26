package weblogic.ejb.container.deployer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeSet;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import weblogic.utils.classloaders.ClassPreProcessor;

/** @deprecated */
@Deprecated
public class RemoteBizIntfClassPreProcessor extends EJBClassEnhancer implements ClassPreProcessor {
   private String remoteBiName;
   private String biName;
   private ClassWriter ricw = null;
   private byte[] result = null;
   private List neededMethods = new ArrayList();

   public RemoteBizIntfClassPreProcessor(String remoteBiName, String biName) {
      this.remoteBiName = remoteBiName;
      this.biName = biName;
   }

   public void initialize(Hashtable params) {
   }

   public boolean isContainMethod(String method) {
      return this.neededMethods.contains(method);
   }

   public void addMethod(String method) {
      this.neededMethods.add(method);
   }

   public byte[] preProcess(String className, byte[] classBytes) {
      if (className.indexOf("_WLStub") > 0) {
         return classBytes;
      } else {
         ClassReader cr = new ClassReader(classBytes);
         ClassWriter cw = new ClassWriter(cr, 0);
         boolean IsBizIntf = false;
         if (className.equals(this.biName)) {
            this.ricw = new ClassWriter(cr, 0);
            IsBizIntf = true;
         }

         Local2RemoteTransformer transformer;
         if (IsBizIntf) {
            transformer = new Local2RemoteTransformer(cw, this.ricw, false);
         } else {
            transformer = new Local2RemoteTransformer(cw, this.ricw, true);
         }

         cr.accept(transformer, 0);
         return cw.toByteArray();
      }
   }

   public byte[] postProcess() {
      if (this.result != null) {
         return this.result;
      } else {
         this.ricw.visitEnd();
         this.result = this.ricw.toByteArray();
         this.writeEnhancedClassBack(this.remoteBiName, this.result);
         return this.result;
      }
   }

   /** @deprecated */
   @Deprecated
   public class Local2RemoteTransformer extends ClassVisitor {
      private static final String REMOTE = "java/rmi/Remote";
      private static final String REMOTE_EXCEPTION = "java/rmi/RemoteException";
      private final String[] EXCEPTIONS = new String[]{"java/rmi/RemoteException"};
      private final ClassVisitor riClassVisitor;
      private final boolean onlyTransferMethod;
      private String currentCName;

      public Local2RemoteTransformer(ClassVisitor classVisitor, ClassVisitor riClassVisitor, boolean b) {
         super(458752, classVisitor);
         this.riClassVisitor = riClassVisitor;
         this.onlyTransferMethod = b;
      }

      public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
         this.currentCName = name;
         if (this.onlyTransferMethod) {
            this.cv.visit(version, access, name, signature, superName, interfaces);
         }

         if (!this.onlyTransferMethod) {
            ArrayList list = new ArrayList(1);
            list.add("java/rmi/Remote");
            String[] result = new String[list.size()];
            result = (String[])list.toArray(result);
            this.riClassVisitor.visit(version, access, RemoteBizIntfClassPreProcessor.this.remoteBiName.replace('.', '/'), signature, superName, result);
            this.currentCName = "_pseudo_" + RemoteBizIntfClassPreProcessor.this.remoteBiName.replace('.', '/');
            this.cv.visit(version, access, this.currentCName, signature, superName, interfaces);
         }

      }

      public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
         String method = name + desc;
         if (!name.equals("<init>") && !name.equals("<clinit>") && !RemoteBizIntfClassPreProcessor.this.isContainMethod(method)) {
            RemoteBizIntfClassPreProcessor.this.addMethod(method);
            return this.riClassVisitor.visitMethod(access, name, desc, signature, this.getExceptions(exceptions));
         } else {
            return this.cv.visitMethod(access, name, desc, signature, exceptions);
         }
      }

      public void visitEnd() {
         super.visitEnd();
         RemoteBizIntfClassPreProcessor.this.writeEnhancedClassBack(this.currentCName, ((ClassWriter)this.cv).toByteArray());
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

            if (!set.contains("java/rmi/RemoteException")) {
               set.add("java/rmi/RemoteException");
            }

            newExceptions = new String[set.size()];
            set.toArray(newExceptions);
            return newExceptions;
         } else {
            return this.EXCEPTIONS;
         }
      }
   }
}
