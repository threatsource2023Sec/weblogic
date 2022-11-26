package weblogic.ejb.container.deployer;

import com.oracle.injection.integration.CDIUtils;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import org.jboss.weld.bootstrap.spi.BeanDiscoveryMode;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.Extensible;
import weblogic.application.ModuleContext;
import weblogic.application.naming.Environment;
import weblogic.application.utils.BaseModuleExtensionContext;
import weblogic.application.utils.annotation.ClassInfoFinder;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.utils.classloaders.ClassFinder;

public final class ModuleExtensionContextImpl extends BaseModuleExtensionContext {
   private final ClassFinder classFinder;
   private final ModuleContext moduleContext;
   private final EjbJarArchive archive;
   private final Set annosSet;
   private EnvironmentManager envManager;

   public ModuleExtensionContextImpl(ModuleContext mc, ClassFinder cf, EjbJarArchive archive) {
      super((ApplicationContextInternal)null, mc, (Extensible)null, (Environment)null);
      this.moduleContext = mc;
      this.classFinder = cf;
      this.archive = archive;
      this.annosSet = new HashSet(DDConstants.TOP_LEVEL_ANNOS);
      this.annosSet.addAll(mc.getExtensionAnnotationClasses());
   }

   public void setEnvironmentManager(EnvironmentManager envManager) {
      this.envManager = envManager;
   }

   public Set getAnnotatedClasses(boolean useTempClassLoader, Class... annos) throws AnnotationProcessingException {
      if (!useTempClassLoader) {
         throw new UnsupportedOperationException("NYI");
      } else {
         return this.archive.getAnnotatedClasses(annos);
      }
   }

   public List getSources(String relativeURI) {
      List sources = new LinkedList();
      Enumeration e = this.classFinder.getSources(relativeURI);

      while(e.hasMoreElements()) {
         sources.add(e.nextElement());
      }

      return sources;
   }

   public Collection getBeanClassNames() {
      BeanDiscoveryMode bdm = CDIUtils.getBeanDiscoveryMode(this.moduleContext.getRegistry());
      if (bdm == null) {
         bdm = BeanDiscoveryMode.ALL;
      }

      Collection classNames = new LinkedList();
      Iterator it;
      if (BeanDiscoveryMode.ALL.equals(bdm)) {
         it = this.moduleContext.getVirtualJarFile().entries();

         while(it.hasNext()) {
            String name = ((ZipEntry)it.next()).getName();
            if (name.endsWith(".class")) {
               classNames.add(name.substring(0, name.length() - 6).replace('/', '.'));
            }
         }
      } else if (BeanDiscoveryMode.ANNOTATED.equals(bdm) && this.moduleContext.getVirtualJarFile() != null) {
         it = null;
         Collection cdiAnnotatedClasses = CDIUtils.getCDIAnnotatedClassNames(it, this.getClassInfoFinder(), this.getTemporaryClassLoader());
         if (cdiAnnotatedClasses != null) {
            classNames.addAll(cdiAnnotatedClasses);
         }
      }

      return classNames;
   }

   public Environment getEnvironment(String compName) {
      if (this.envManager == null) {
         throw new IllegalStateException("Environment Manager is not available");
      } else {
         return this.envManager.getEnvironmentFor(compName);
      }
   }

   public Collection getEnvironments() {
      return this.envManager.getEnvironments();
   }

   public ClassInfoFinder getClassInfoFinder() {
      return this.archive.getClassInfoFinder();
   }
}
