package weblogic.servlet.jsp;

import java.util.Enumeration;
import weblogic.utils.classloaders.ChangeAwareClassLoader;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.enumerations.EmptyEnumerator;

public class TagFileClassLoader extends ChangeAwareClassLoader {
   private static final boolean debug = false;
   private final long creationTime = this.getLastChecked();

   public TagFileClassLoader(ClassFinder finder, ClassLoader parent) {
      super(finder, false, parent);
   }

   public Class loadClass(String name) throws ClassNotFoundException {
      return this.findClass(name);
   }

   public Class findClass(String name) throws ClassNotFoundException {
      if (this.isTagFileClass(name)) {
         synchronized(this) {
            return super.findClass(name);
         }
      } else {
         return this.getParent().loadClass(name);
      }
   }

   public Enumeration findResources(String resourceName) {
      return new EmptyEnumerator();
   }

   public final long getCreationTime() {
      return this.creationTime;
   }

   private boolean isTagFileClass(String name) {
      return name.indexOf("_tags") > 0 && (name.endsWith("_tag") || name.endsWith("_tagx") || name.indexOf("_tag$") > 0 || name.indexOf("_tagx$") > 0);
   }
}
