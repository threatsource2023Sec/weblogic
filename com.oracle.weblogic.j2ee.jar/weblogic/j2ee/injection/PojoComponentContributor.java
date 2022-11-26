package weblogic.j2ee.injection;

import com.oracle.pitchfork.inject.Jsr250Metadata;
import com.oracle.pitchfork.interfaces.inject.DeploymentUnitMetadataI;
import com.oracle.pitchfork.interfaces.inject.EnricherI;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import java.util.logging.Level;
import java.util.logging.Logger;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.wl.PojoEnvironmentBean;

public class PojoComponentContributor extends J2eeComponentContributor {
   private static Logger logger = Logger.getLogger(PojoComponentContributor.class.getName());
   private J2eeEnvironmentBean pojoEnvironmentBean;
   private String pojoClassName;
   private Jsr250MetadataI jsr250Metadata;

   public PojoComponentContributor(String pojoClassName, PojoEnvironmentBean pojoEnvironmentBean, PitchforkContext pitchforkContext, ClassLoader componentClassLoader) {
      super(pitchforkContext);
      this.pojoEnvironmentBean = pojoEnvironmentBean;
      this.pojoClassName = pojoClassName;
      this.classLoader = componentClassLoader;
   }

   protected void contribute(Jsr250MetadataI jsr250Metadata, J2eeEnvironmentBean environmentGroupBean) {
   }

   protected void debug(String s) {
   }

   public void contribute(EnricherI enricher) {
      this.contribute(enricher, this.pojoClassName, this.pojoClassName, this.pojoEnvironmentBean);
   }

   public Jsr250MetadataI newJsr250Metadata(String componentName, Class componentClass, DeploymentUnitMetadataI metadata) {
      this.jsr250Metadata = new Jsr250Metadata(metadata, componentName, componentClass);
      return this.jsr250Metadata;
   }

   public Jsr250MetadataI getMetadata(String componentName) {
      return this.jsr250Metadata;
   }

   protected boolean createInjectionWhenNoLookupValueFound(EnvEntryBean envEntry, Jsr250MetadataI jsr250Metadata) {
      if (logger.isLoggable(Level.FINE)) {
         String msg = "[PojoComponentContributor] Environment entry: " + envEntry.getEnvEntryName() + " on the POJO class " + this.pojoClassName + " does not have a Value or LookupName specified. Processing the injection metadata for the POJO, with the expectation that there is another definition of the environment entry with the pertinent information, contributing to the same component environment";
         logger.log(Level.FINE, msg);
      }

      return true;
   }
}
