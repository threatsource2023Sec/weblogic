package weblogic.application.internal.flow;

import java.io.File;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.internal.Controls;
import weblogic.application.utils.annotation.AnnotationMappingsImpl;
import weblogic.management.DeploymentException;

public class ParseAnnotationsFlow extends BaseFlow {
   private AnnotationMappingsImpl mappings;

   public ParseAnnotationsFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      this.mappings = new AnnotationMappingsImpl(this.appCtx.getAppClassLoader(), Controls.annoscancache.enabled, this.appCtx.getAppAnnotationScanningClassPath(), new File(this.appCtx.getOutputPath()), this.appCtx.getCacheDir(), this.appCtx.getLibraryClassInfoFinders(), this.appCtx.isPojoAnnotationEnabled());
      this.appCtx.setAnnotationMappings(this.mappings);
   }
}
