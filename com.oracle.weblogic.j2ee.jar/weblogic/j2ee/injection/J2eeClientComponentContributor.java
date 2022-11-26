package weblogic.j2ee.injection;

import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.ApplicationClientBean;

public class J2eeClientComponentContributor extends BaseComponentContributor {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppClient");
   private final Class mainClass;
   private final ApplicationClientBean clientBean;

   public J2eeClientComponentContributor(Class mainClass, ApplicationClientBean clientBean, PitchforkContext pitchforkContext) {
      super(pitchforkContext);
      this.mainClass = mainClass;
      this.clientBean = clientBean;
   }

   public void contribute(EnricherI enricher) {
      Jsr250MetadataI jsr250Metadata = this.buildJsr250MetaData(enricher, "main", this.mainClass.getName());
      this.buildInjectionMetadata(jsr250Metadata, this.clientBean);
      enricher.attach(jsr250Metadata);
      if (jsr250Metadata != null) {
         jsr250Metadata.inject((Object)null);
      }

   }

   public Jsr250MetadataI newJsr250Metadata(String componentName, Class componentClass, DeploymentUnitMetadataI dum) {
      return this.pitchforkContext.getPitchforkUtils().createJ2eeClientInjectionMetadata(componentName, componentClass, dum);
   }

   public Jsr250MetadataI getMetadata(String componentName) {
      return null;
   }

   protected void debug(String s) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[J2eeClientComponentContributor] " + s);
      }

   }
}
