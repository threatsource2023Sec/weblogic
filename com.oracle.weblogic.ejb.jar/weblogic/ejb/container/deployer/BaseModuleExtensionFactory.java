package weblogic.ejb.container.deployer;

import java.util.Collection;
import weblogic.application.AnnotationProcessingException;
import weblogic.application.ModuleExtensionContext;
import weblogic.descriptor.Descriptor;
import weblogic.ejb.container.dd.DDConstants;
import weblogic.j2ee.descriptor.WebAppBean;

public abstract class BaseModuleExtensionFactory {
   protected boolean hasEJBDescriptor(ModuleExtensionContext ctx) throws IllegalArgumentException {
      Collection descs = ctx.getSources("WEB-INF/ejb-jar.xml");
      if (descs.isEmpty()) {
         return false;
      } else if (descs.size() == 1) {
         return true;
      } else {
         throw new IllegalArgumentException("Multiple WEB-INF/ejb-jar.xml found. " + descs);
      }
   }

   protected boolean hasAnnotatedEJBs(ModuleExtensionContext extCtx, Descriptor stdDD, boolean useTempLoader) throws AnnotationProcessingException {
      boolean metadataComplete;
      if (stdDD != null && stdDD.getRootBean() instanceof WebAppBean) {
         metadataComplete = ((WebAppBean)stdDD.getRootBean()).isMetadataComplete();
      } else {
         metadataComplete = false;
      }

      if (this.supportedWebAppBeanVersion(stdDD) && !metadataComplete) {
         Class[] annos = (Class[])DDConstants.COMPONENT_DEFINING_ANNOS.toArray(new Class[0]);
         return !extCtx.getAnnotatedClasses(useTempLoader, annos).isEmpty();
      } else {
         return false;
      }
   }

   private boolean supportedWebAppBeanVersion(Descriptor stdDD) {
      try {
         return stdDD == null || (double)Float.parseFloat(stdDD.getOriginalVersionInfo()) >= 2.5;
      } catch (NumberFormatException var3) {
         return false;
      }
   }

   public final Class[] getSupportedClassLevelAnnotations() {
      return (Class[])DDConstants.TOP_LEVEL_ANNOS.toArray(new Class[0]);
   }
}
