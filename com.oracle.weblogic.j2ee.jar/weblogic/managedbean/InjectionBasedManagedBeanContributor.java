package weblogic.managedbean;

import com.oracle.injection.InjectionContainer;
import com.oracle.injection.spi.ContainerIntegrationService;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.j2ee.descriptor.wl.ManagedBeansBean;
import weblogic.j2ee.injection.PitchforkContext;

public class InjectionBasedManagedBeanContributor extends ManagedBeanContributor {
   private final ContainerIntegrationService cis;

   public InjectionBasedManagedBeanContributor(InjectionContainer injectionContainer, ManagedBeansBean bean, ClassLoader classLoader, PitchforkContext pitchforkContext) {
      super(bean, classLoader, pitchforkContext);
      this.cis = injectionContainer.getIntegrationService();
   }

   protected void contribute(Jsr250MetadataI jsr250Metadata, J2eeEnvironmentBean envBean) {
      this.cis.addInjectionMetaData(jsr250Metadata.getComponentClass(), jsr250Metadata);
   }
}
