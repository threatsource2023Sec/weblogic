package weblogic.utils.classloaders.debug;

import java.util.Iterator;
import weblogic.utils.classloaders.ByteArraySource;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.FileSource;
import weblogic.utils.classloaders.JarSource;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;

public class ClasspathHelper {
   public static ClasspathElement getClasspathElements(ClassFinder finder) {
      return new ClasspathElementImpl(finder, (ClasspathElement)null);
   }

   public static void printFormattedClasspath(ClassFinder finder, StringBuilder builder) {
      printFormattedClasspath(getClasspathElements(finder), builder, "  ");
   }

   private static void printFormattedClasspath(ClasspathElement root, StringBuilder builder, String indent) {
      printFormattedClasspath(root, builder, indent, 0);
   }

   private static void printFormattedClasspath(ClasspathElement root, StringBuilder builder, String indent, int level) {
      ClasspathElement child;
      Iterator var6;
      if (root.getPath() != null) {
         for(int index = 0; index < level; ++index) {
            builder.append(indent);
         }

         builder.append(root.getPath()).append('\n');
         if (root.getChildren() != null) {
            var6 = root.getChildren().iterator();

            while(var6.hasNext()) {
               child = (ClasspathElement)var6.next();
               printFormattedClasspath(child, builder, indent, level + 1);
            }
         }
      } else if (root.getChildren() != null) {
         var6 = root.getChildren().iterator();

         while(var6.hasNext()) {
            child = (ClasspathElement)var6.next();
            printFormattedClasspath(child, builder, indent, level);
         }
      }

   }

   public static String getDeploymentLocation(String className, ClassFinder cf) {
      Source s = cf.getClassSource(className);
      if (s instanceof FileSource) {
         return ((FileSource)s).getCodeBase();
      } else if (s instanceof JarSource) {
         return ((JarSource)s).getFile().getName();
      } else if (s instanceof URLSource) {
         return ((URLSource)s).getURL().getFile();
      } else {
         return s instanceof ByteArraySource ? null : s.getURL().getFile();
      }
   }
}
