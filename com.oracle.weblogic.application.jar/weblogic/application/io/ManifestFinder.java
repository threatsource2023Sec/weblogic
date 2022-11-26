package weblogic.application.io;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipFile;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.AbstractClassFinder;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClassFinderUtils;
import weblogic.utils.classloaders.NullClassFinder;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.ClassFinderUtils.Attr;

public class ManifestFinder extends AbstractClassFinder {
   private final ClassFinder manifestFinder;

   public ManifestFinder(File root) {
      this(root, (ClassFinderUtils.Attr)null);
   }

   private ManifestFinder(File root, ClassFinderUtils.Attr mfAttr) {
      ClassFinder cf = null;
      if (root.exists()) {
         if (root.isFile()) {
            ZipFile zf = null;

            try {
               zf = new ZipFile(root);
               cf = ClassFinderUtils.getManifestFinder(zf, new HashSet(), mfAttr);
            } catch (IOException var16) {
            } finally {
               try {
                  if (zf != null) {
                     zf.close();
                  }
               } catch (IOException var14) {
               }

            }
         } else {
            try {
               cf = ClassFinderUtils.getManifestFinder(root, new HashSet(), mfAttr);
            } catch (IOException var15) {
            }
         }
      }

      if (cf == null) {
         this.manifestFinder = NullClassFinder.NULL_FINDER;
      } else {
         this.manifestFinder = cf;
      }

   }

   public String getClassPath() {
      return this.manifestFinder.getClassPath();
   }

   public Source getSource(String name) {
      return this.manifestFinder.getSource(name);
   }

   public Enumeration getSources(String name) {
      return this.manifestFinder.getSources(name);
   }

   public ClassFinder getManifestFinder() {
      return this.manifestFinder;
   }

   public Enumeration entries() {
      return this.manifestFinder.entries();
   }

   public void close() {
      this.manifestFinder.close();
   }

   public String[] getPathElements() {
      return StringUtils.splitCompletely(this.getClassPath(), File.pathSeparator);
   }

   // $FF: synthetic method
   ManifestFinder(File x0, ClassFinderUtils.Attr x1, Object x2) {
      this(x0, x1);
   }

   public static final class ExtensionListFinder extends ManifestFinder {
      public ExtensionListFinder(File root) {
         super(root, Attr.EXTENSION_LIST, null);
      }
   }

   public static final class ClassPathFinder extends ManifestFinder {
      public ClassPathFinder(File root) {
         super(root, Attr.CLASS_PATH, null);
      }
   }
}
