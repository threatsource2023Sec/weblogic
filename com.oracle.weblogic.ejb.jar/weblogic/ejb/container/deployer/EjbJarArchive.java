package weblogic.ejb.container.deployer;

import com.oracle.injection.integration.CDIUtils;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.metadatacache.Cache;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.application.utils.annotation.ClassInfoFinderFactory;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.i18n.logging.Loggable;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.classloaders.URLSource;
import weblogic.utils.classloaders.ZipSource;
import weblogic.utils.jars.VirtualJarFile;

public class EjbJarArchive implements Archive {
   private final ModuleContext moduleContext;
   private final ApplicationContextInternal appCtx;
   private final Set annosSet;
   private ClassInfoFinder classInfoFinder;
   private ClassFinder classFinder;

   public EjbJarArchive(ModuleContext moduleContext, ApplicationContextInternal appCtx, ClassFinder classFinder) {
      this.moduleContext = moduleContext;
      this.appCtx = appCtx;
      this.classFinder = classFinder;
      this.annosSet = new HashSet(DDConstants.TOP_LEVEL_ANNOS);
      this.annosSet.addAll(moduleContext.getExtensionAnnotationClasses());
   }

   public ModuleContext getModuleContext() {
      return this.moduleContext;
   }

   public Source getSource(String relativeURI) throws IOException {
      VirtualJarFile vjar = this.moduleContext.getVirtualJarFile();
      if (vjar.isDirectory()) {
         URL url = vjar.getResource(relativeURI);
         if (url != null) {
            return new URLSource(url);
         }
      } else {
         ZipEntry ze = vjar.getEntry(relativeURI);
         if (ze != null) {
            return new ZipSource(vjar.getJarFile(), ze);
         }
      }

      return null;
   }

   public Set getAnnotatedClasses(Class... annos) throws AnnotationProcessingException {
      Class[] var2 = annos;
      int var3 = annos.length;

      int i;
      for(i = 0; i < var3; ++i) {
         Class anno = var2[i];
         if (!this.annosSet.contains(anno)) {
            throw new IllegalArgumentException("Unexpected annotation class");
         }
      }

      Set annotatedClasses = new HashSet();
      String[] annotationNames = new String[annos.length];

      for(i = 0; i < annos.length; ++i) {
         annotationNames[i] = annos[i].getName();
      }

      Set classNames = this.getClassInfoFinder().getClassNamesWithAnnotations(annotationNames);
      Iterator var13 = classNames.iterator();

      while(var13.hasNext()) {
         String clz = (String)var13.next();

         try {
            annotatedClasses.add(this.getTemporaryClassLoader().loadClass(clz));
         } catch (ClassNotFoundException var9) {
            Loggable l = EJBLogger.logUnableLoadClassLoggable(clz, this.moduleContext.getVirtualJarFile().toString(), var9.toString());
            throw new AnnotationProcessingException(l.getMessage());
         }
      }

      return annotatedClasses;
   }

   public GenericClassLoader getTemporaryClassLoader() {
      return this.moduleContext.getTemporaryClassLoader();
   }

   public boolean isCdiEnabled() {
      return CDIUtils.isVirtualJarCdiEnabled(this.moduleContext.getVirtualJarFile(), this.getClassInfoFinder(), this.getTemporaryClassLoader(), this.appCtx);
   }

   public void reset() {
      this.moduleContext.removeTemporaryClassLoader();
      ClassInfoFinderFactory.FACTORY.notifyRemoval(this.getParams());
      this.classInfoFinder = null;
   }

   public String getStandardDescriptorRoot() {
      return "META-INF/";
   }

   public ClassInfoFinder getClassInfoFinder() {
      if (this.classInfoFinder == null) {
         try {
            this.classInfoFinder = ClassInfoFinderFactory.FACTORY.newInstance(this.getParams());
         } catch (AnnotationProcessingException var2) {
            var2.printStackTrace();
         }
      }

      return this.classInfoFinder;
   }

   private ClassInfoFinderFactory.Params getParams() {
      return ClassInfoFinderFactory.FACTORY.createParams(this.classFinder).setModuleType(ModuleType.EJB).enableCaching(Cache.AppMetadataCache, this.moduleContext.getVirtualJarFile(), this.moduleContext.getCacheDir());
   }
}
