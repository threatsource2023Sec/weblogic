package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class DocumentLocatorImpl extends AbstractDocumentLocator {
   public DocumentLocatorImpl() {
   }

   @Inject
   public DocumentLocatorImpl(ProvisioningContext provisioningContext, ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      super(new DocumentTransformingDocumentFactory(provisioningContext, configurableAttributeValueProvider));
   }

   DocumentLocatorImpl(DocumentFactory factory) {
      super(factory);
   }
}
