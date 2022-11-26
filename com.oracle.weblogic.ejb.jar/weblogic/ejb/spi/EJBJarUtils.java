package weblogic.ejb.spi;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.shared.ModuleType;
import org.apache.tools.ant.taskdefs.Javac;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.j2ee.J2EEUtils;
import weblogic.logging.Loggable;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public final class EJBJarUtils {
   public static boolean hasEJBSources(Javac javac, File f, String encoding) throws IOException {
      return f.isDirectory() && J2EEUtils.EJB_ANNOTATION_DETECTOR.hasAnnotatedSources(javac, f, encoding);
   }

   public static boolean isEJBBasic(File f) throws IOException {
      if (f.isDirectory()) {
         File ejbJar = new File(f, J2EEUtils.EJB_DD_PATH);
         return ejbJar.exists() && ejbJar.length() > 0L;
      } else if (f.getName().endsWith(".jar") && f.exists()) {
         JarFile jf = null;

         boolean var3;
         try {
            jf = new JarFile(f);
            ZipEntry ze = jf.getEntry("META-INF/ejb-jar.xml");
            var3 = ze != null && ze.getSize() != 0L;
         } finally {
            if (jf != null) {
               jf.close();
            }

         }

         return var3;
      } else {
         return false;
      }
   }

   public static boolean hasEJBAnnotations(File f) throws IOException {
      if (f.isDirectory()) {
         return J2EEUtils.EJB_ANNOTATION_DETECTOR.isAnnotated(f);
      } else if (f.getName().endsWith(".jar") && f.exists()) {
         JarFile jf = null;

         boolean var2;
         try {
            jf = new JarFile(f);
            var2 = J2EEUtils.EJB_ANNOTATION_DETECTOR.isAnnotated(jf);
         } finally {
            if (jf != null) {
               jf.close();
            }

         }

         return var2;
      } else {
         return false;
      }
   }

   public static Set getIdentityAnnotatedClasses(VirtualJarFile vjf, GenericClassLoader cl) throws AnnotationProcessingException {
      Set allAnnotatedClasses = new HashSet();
      Map m = getIdentityAnnotatedClasses(vjf, cl, (Class[])DDConstants.TOP_LEVEL_ANNOS.toArray(new Class[0]));
      Iterator var4 = m.values().iterator();

      while(var4.hasNext()) {
         Set annotatedClasses = (Set)var4.next();
         if (annotatedClasses != null) {
            allAnnotatedClasses.addAll(annotatedClasses);
         }
      }

      return allAnnotatedClasses;
   }

   public static Map getIdentityAnnotatedClasses(VirtualJarFile vjf, GenericClassLoader cl, Class... annos) throws AnnotationProcessingException {
      Map classes = new HashMap();
      String[] annoClassNames = new String[annos.length];

      for(int i = 0; i < annos.length; ++i) {
         annoClassNames[i] = annos[i].getName();
      }

      Map map;
      if (annoClassNames != null && annoClassNames.length != 0) {
         map = ClassInfoFinderFactory.FACTORY.newInstance(ClassInfoFinderFactory.FACTORY.createParams(vjf).setModuleType(ModuleType.EJB)).getAnnotatedClasses(annoClassNames);
      } else {
         map = Collections.emptyMap();
      }

      for(int i = 0; i < annos.length; ++i) {
         Set classNames = (Set)map.get(annos[i].getName());
         if (classNames != null && !classNames.isEmpty()) {
            Set annotatedSet = new HashSet();
            classes.put(annos[i], annotatedSet);
            Iterator var9 = classNames.iterator();

            while(var9.hasNext()) {
               String name = (String)var9.next();

               Loggable l;
               try {
                  Class clazz = cl.loadClass(name.replace('/', '.'));
                  clazz.getAnnotations();
                  annotatedSet.add(clazz);
               } catch (LinkageError var13) {
                  l = EJBLogger.logUnableLinkClassLoggable(name, vjf.toString(), var13.toString());
                  throw new AnnotationProcessingException(l.getMessage());
               } catch (ClassNotFoundException var14) {
                  l = EJBLogger.logUnableLoadClassLoggable(name, vjf.toString(), var14.toString());
                  throw new AnnotationProcessingException(l.getMessage());
               } catch (ArrayStoreException var15) {
                  l = EJBLogger.logUnableLoadClassLoggable(name, vjf.toString(), var15.toString());
                  throw new AnnotationProcessingException(l.getMessage());
               }
            }
         }
      }

      return classes;
   }
}
