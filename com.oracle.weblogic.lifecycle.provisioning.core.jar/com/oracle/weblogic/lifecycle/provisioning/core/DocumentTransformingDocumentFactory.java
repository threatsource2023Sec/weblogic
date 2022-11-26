package com.oracle.weblogic.lifecycle.provisioning.core;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentRepository;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.Transformer;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.xml.transform.URIResolver;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;

@Service
@Singleton
public class DocumentTransformingDocumentFactory extends DocumentFactory {
   final Provider provisioningContextProvider;
   private final Provider provisioningComponentRepositoryProvider;
   final ConfigurableAttributeValueProvider configurableAttributeValueProvider;

   public DocumentTransformingDocumentFactory() {
      this((ProvisioningContext)null, (ConfigurableAttributeValueProvider)null);
   }

   public DocumentTransformingDocumentFactory(@Optional final ProvisioningContext provisioningContext, @Optional ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      this(new Provider() {
         public final ProvisioningContext get() {
            return provisioningContext;
         }
      }, (Provider)null, configurableAttributeValueProvider);
   }

   @Inject
   public DocumentTransformingDocumentFactory(Provider provisioningContextProvider, @Optional Provider provisioningComponentRepositoryProvider, @Optional ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      Objects.requireNonNull(provisioningContextProvider);
      this.provisioningContextProvider = provisioningContextProvider;
      this.provisioningComponentRepositoryProvider = provisioningComponentRepositoryProvider;
      this.configurableAttributeValueProvider = configurableAttributeValueProvider;
   }

   protected final Transformer getTransformerFor(Document document) {
      DocumentTransformer returnValue;
      if (document == null) {
         returnValue = null;
      } else {
         returnValue = new DocumentTransformer() {
            protected final URIResolver createURIResolver(URIResolver potentialDelegate) {
               return new ConfigurableAttributeURIResolver(potentialDelegate, "config", true, (ProvisioningContext)DocumentTransformingDocumentFactory.this.provisioningContextProvider.get(), DocumentTransformingDocumentFactory.this.provisioningComponentRepositoryProvider == null ? null : (ProvisioningComponentRepository)DocumentTransformingDocumentFactory.this.provisioningComponentRepositoryProvider.get(), DocumentTransformingDocumentFactory.this.configurableAttributeValueProvider);
            }
         };
      }

      return returnValue;
   }
}
