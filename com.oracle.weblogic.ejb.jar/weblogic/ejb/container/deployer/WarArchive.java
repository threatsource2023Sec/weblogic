package weblogic.ejb.container.deployer;

import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.CDIUtils;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;

public class WarArchive implements Archive {
   private static Logger logger = Logger.getLogger(WarArchive.class.getName());
   private final ModuleExtensionContext extCtx;
   private final ModuleContext modCtx;
   private final ApplicationContextInternal appCtx;

   public WarArchive(ModuleExtensionContext extCtx, ModuleContext modCtx, ApplicationContextInternal appCtx) {
      this.extCtx = extCtx;
      this.modCtx = modCtx;
      this.appCtx = appCtx;
   }

   public WarArchive(ModuleExtensionContext extCtx, ModuleContext modCtx) {
      this(extCtx, modCtx, (ApplicationContextInternal)null);
   }

   public Source getSource(String relativeURI) throws IOException {
      List sources = this.extCtx.getSources(relativeURI);
      return sources.isEmpty() ? null : (Source)sources.iterator().next();
   }

   public Set getAnnotatedClasses(Class... annos) throws AnnotationProcessingException {
      return this.extCtx.getAnnotatedClasses(true, annos);
   }

   public GenericClassLoader getTemporaryClassLoader() {
      return this.extCtx.getTemporaryClassLoader();
   }

   public boolean isCdiEnabled() {
      try {
         return CDIUtils.isWebModuleCDIEnabled(this.modCtx, this.extCtx, this.appCtx);
      } catch (InjectionException var2) {
         logger.warning("Unable to determine if web module is cdi-enabled for " + this.modCtx.getName() + ".  Exception: " + var2.getMessage());
         return false;
      }
   }

   public String getStandardDescriptorRoot() {
      return "WEB-INF/";
   }
}
