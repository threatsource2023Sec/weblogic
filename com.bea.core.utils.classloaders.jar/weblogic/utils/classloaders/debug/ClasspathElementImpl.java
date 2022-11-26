package weblogic.utils.classloaders.debug;

import java.util.ArrayList;
import java.util.List;
import weblogic.utils.PlatformConstants;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;

class ClasspathElementImpl implements ClasspathElement {
   private String path = null;
   private List children;
   private final ClasspathElement parent;

   public ClasspathElementImpl(ClassFinder finder, ClasspathElement parent) {
      if (finder instanceof JarClassFinder) {
         this.path = ((JarClassFinder)finder).getDelegate().getClassPath();
         this.initAndGetChildren().add(new ClasspathElementImpl(finder.getManifestFinder(), this));
      } else if (finder instanceof MultiClassFinder) {
         ClassFinder[] var3 = ((MultiClassFinder)finder).getClassFinders();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ClassFinder f = var3[var5];
            this.initAndGetChildren().add(new ClasspathElementImpl(f, this));
         }
      } else {
         String classpath = finder.getClassPath();
         if (classpath != null && !classpath.isEmpty()) {
            String[] pathElements = StringUtils.splitCompletely(finder.getClassPath(), PlatformConstants.PATH_SEP);
            String[] var11 = pathElements;
            int var12 = pathElements.length;

            for(int var7 = 0; var7 < var12; ++var7) {
               String pathElement = var11[var7];
               this.initAndGetChildren().add(new ClasspathElementImpl(pathElement, (ClasspathElement)null, this));
            }
         }
      }

      this.parent = parent;
   }

   private ClasspathElementImpl(String path, ClasspathElement node, ClasspathElement parent) {
      this.path = path;
      if (node != null) {
         this.initAndGetChildren().add(node);
      }

      this.parent = parent;
   }

   private List initAndGetChildren() {
      if (this.children == null) {
         this.children = new ArrayList();
      }

      return this.children;
   }

   public String getPath() {
      return this.path;
   }

   public List getChildren() {
      return this.children;
   }

   public ClasspathElement getParent() {
      return this.parent;
   }
}
