package weblogic.application.utils.annotation;

import java.io.IOException;
import java.io.InputStream;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;

public final class AnnotationDetector extends ClassVisitor {
   private static final String[] EXCLUDES = new String[]{Type.getDescriptor(Deprecated.class).intern(), Type.getDescriptor(Override.class).intern(), Type.getDescriptor(SuppressWarnings.class).intern()};
   private boolean annotationPresent;
   private String superName;
   private String[] interfaces;

   private AnnotationDetector() {
      super(458752, (ClassVisitor)null);
   }

   public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
      if ((version & 255) >= 49) {
         this.superName = superName;
         this.interfaces = interfaces;
      }
   }

   public static boolean hasAnnotation(String className, ClassFinder finder) {
      Source source = finder.getClassSource(className);
      if (source == null) {
         return false;
      } else {
         InputStream is = null;

         boolean var4;
         try {
            is = source.getInputStream();
            if (is.available() != 0) {
               ClassReader cr = new ClassReader(is);
               AnnotationDetector annotationDetector = new AnnotationDetector();
               cr.accept(annotationDetector, 7);
               if (annotationDetector.annotationPresent) {
                  boolean var30 = true;
                  return var30;
               }

               if (annotationDetector.superName != null) {
                  String superClassName = annotationDetector.superName.replace('/', '.');
                  if (hasAnnotation(superClassName, finder)) {
                     boolean var31 = true;
                     return var31;
                  }
               }

               if (annotationDetector.interfaces == null) {
                  return false;
               }

               String[] var29 = annotationDetector.interfaces;
               int var7 = var29.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  String intf = var29[var8];
                  String interfaceName = intf.replace('/', '.');
                  if (hasAnnotation(interfaceName, finder)) {
                     boolean var11 = true;
                     return var11;
                  }
               }

               return false;
            }

            var4 = false;
         } catch (IOException var25) {
            boolean var5 = false;
            return var5;
         } finally {
            if (is != null) {
               try {
                  is.close();
               } catch (IOException var24) {
               }
            }

         }

         return var4;
      }
   }

   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
      if (!visible) {
         return null;
      } else {
         String[] var3 = EXCLUDES;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String exclude = var3[var5];
            if (desc.intern() == exclude) {
               return null;
            }
         }

         this.annotationPresent = true;
         return null;
      }
   }

   public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
      return new FV();
   }

   public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
      return new MV();
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return null;
   }

   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
      return null;
   }

   private final class MV extends MethodVisitor {
      MV() {
         super(458752);
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (!visible) {
            return null;
         } else {
            String[] var3 = AnnotationDetector.EXCLUDES;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String exclude = var3[var5];
               if (desc.intern() == exclude) {
                  return null;
               }
            }

            AnnotationDetector.this.annotationPresent = true;
            return null;
         }
      }

      public AnnotationVisitor visitAnnotationDefault() {
         return null;
      }

      public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
         return null;
      }
   }

   private final class FV extends FieldVisitor {
      FV() {
         super(458752);
      }

      public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
         if (!visible) {
            return null;
         } else {
            String[] var3 = AnnotationDetector.EXCLUDES;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String exclude = var3[var5];
               if (desc.intern() == exclude) {
                  return null;
               }
            }

            AnnotationDetector.this.annotationPresent = true;
            return null;
         }
      }
   }
}
