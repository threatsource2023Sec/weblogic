package weblogic.diagnostics.instrumentation.engine.base;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.TryCatchBlockSorter;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;

class InstrumentingClassVisitor extends ClassVisitor implements InstrumentationEngineConstants {
   private ClassInstrumentor classInstrumentor;
   private String className;
   private int access;
   private String[] interfaces;
   private boolean hasStaticInitializer;
   private boolean hasSUID;
   private List suidFields = new ArrayList();
   private List suidConstructors = new ArrayList();
   private List suidMethods = new ArrayList();
   private int methodCount;

   InstrumentingClassVisitor(ClassInstrumentor classInstrumentor, ClassVisitor cv) {
      super(458752, cv);
      this.classInstrumentor = classInstrumentor;
   }

   ClassInstrumentor getClassInstrumentor() {
      return this.classInstrumentor;
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      this.className = name;
      this.access = access;
      this.interfaces = interfaces;
      super.visit(version, access, name, signature, superName, interfaces);
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      if (name.equals("serialVersionUID")) {
         this.classInstrumentor.setSUIDMark(true);
         this.hasSUID = true;
      }

      int fieldMods = access & 223;
      if ((fieldMods & 2) == 0 || (fieldMods & 136) == 0) {
         this.suidFields.add(new Item(name, fieldMods, desc));
      }

      return super.visitField(access, name, desc, signature, value);
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      if (name.equals("<clinit>")) {
         this.hasStaticInitializer = true;
         this.classInstrumentor.setStaticInitializerMark(true);
         return null;
      } else {
         int methodMods = access & 3391;
         if ((methodMods & 2) == 0) {
            if (name.equals("<init>")) {
               this.suidConstructors.add(new Item(name, methodMods, desc));
            } else {
               this.suidMethods.add(new Item(name, methodMods, desc));
            }
         }

         if (!name.equals("<init>")) {
            ++this.methodCount;
         }

         MethodVisitor mv = this.cv.visitMethod(access, name, desc, signature, exceptions);
         if (mv == null) {
            return null;
         } else if (this.classInstrumentor.isOverflowedMethod(name, desc)) {
            return mv;
         } else {
            MethodVisitor mv = new TryCatchBlockSorter(mv, access, name, desc, signature, exceptions);
            MethodVisitor mv = new InlinedCodeTransformer(this.classInstrumentor, this.cv, mv, access, this.className, name, desc, exceptions);
            return mv;
         }
      }
   }

   public void visitEnd() {
      this.generateSUID();
   }

   private void generateSUID() {
      if (!this.hasSUID && this.classInstrumentor.isModified()) {
         if (!this.classInstrumentor.getInstrumentorEngine().isHotswapAllowed()) {
            ByteArrayOutputStream bos = null;
            DataOutputStream dos = null;
            boolean success = false;

            try {
               bos = new ByteArrayOutputStream();
               dos = new DataOutputStream(bos);
               dos.writeUTF(this.className.replace('/', '.'));
               int classMods = this.access & 1553;
               if ((classMods & 512) != 0) {
                  classMods = this.methodCount > 0 ? classMods | 1024 : classMods & -1025;
               }

               dos.writeInt(classMods);
               Arrays.sort(this.interfaces);

               for(int i = 0; i < this.interfaces.length; ++i) {
                  String ifs = this.interfaces[i].replace('/', '.');
                  dos.writeUTF(ifs);
               }

               this.writeItems(this.suidFields, dos, false);
               if (this.hasStaticInitializer) {
                  dos.writeUTF("<clinit>");
                  dos.writeInt(8);
                  dos.writeUTF("()V");
               }

               this.writeItems(this.suidConstructors, dos, true);
               this.writeItems(this.suidMethods, dos, true);
               dos.flush();
               MessageDigest md = MessageDigest.getInstance("SHA");
               byte[] hashBytes = md.digest(bos.toByteArray());
               long suid = 0L;

               for(int i = Math.min(hashBytes.length, 8) - 1; i >= 0; --i) {
                  suid = suid << 8 | (long)(hashBytes[i] & 255);
               }

               if (InstrumentationDebug.DEBUG_WEAVING.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_WEAVING.debug("serialVersionUID for " + this.className + " fixed to " + suid);
               }

               this.cv.visitField(24, "serialVersionUID", "J", (String)null, suid);
               success = true;
            } catch (IOException var20) {
               UnexpectedExceptionHandler.handle("Unexpected io exception while creating suid for " + this.className, var20);
            } catch (NoSuchAlgorithmException var21) {
               UnexpectedExceptionHandler.handle("Algorith SHA not found", var21);
            } finally {
               if (dos != null) {
                  try {
                     dos.close();
                  } catch (IOException var19) {
                     UnexpectedExceptionHandler.handle("Unexpected exception closing output stream ", var19);
                  }
               }

            }

            if (!success) {
               DiagnosticsLogger.logErrorCreatingSUID(this.className);
               this.classInstrumentor.reportError();
            }

         }
      }
   }

   private void writeItems(List itemList, DataOutputStream dos, boolean dotted) throws IOException {
      int size = itemList.size();
      Item[] items = new Item[size];
      items = (Item[])((Item[])itemList.toArray(items));
      Arrays.sort(items);

      for(int i = 0; i < size; ++i) {
         items[i].write(dos, dotted);
      }

   }
}
