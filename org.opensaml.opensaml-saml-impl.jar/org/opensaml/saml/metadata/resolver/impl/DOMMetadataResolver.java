package org.opensaml.saml.metadata.resolver.impl;

import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class DOMMetadataResolver extends AbstractBatchMetadataResolver {
   private final Logger log = LoggerFactory.getLogger(DOMMetadataResolver.class);
   private Element metadataElement;

   public DOMMetadataResolver(Element mdElement) {
      this.metadataElement = mdElement;
   }

   protected void doDestroy() {
      this.metadataElement = null;
      super.doDestroy();
   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      super.initMetadataResolver();

      String errorMsg;
      try {
         Unmarshaller unmarshaller = this.getUnmarshallerFactory().getUnmarshaller(this.metadataElement);
         XMLObject metadataTemp = unmarshaller.unmarshall(this.metadataElement);
         AbstractBatchMetadataResolver.BatchEntityBackingStore newBackingStore = this.preProcessNewMetadata(metadataTemp);
         this.releaseMetadataDOM(metadataTemp);
         this.setBackingStore(newBackingStore);
      } catch (UnmarshallingException var4) {
         errorMsg = "Unable to unmarshall metadata element";
         this.log.error("{} Unable to unmarshall metadata element", this.getLogPrefix(), var4);
         throw new ComponentInitializationException("Unable to unmarshall metadata element", var4);
      } catch (FilterException var5) {
         errorMsg = "Unable to filter metadata";
         this.log.error("{} Unable to filter metadata", this.getLogPrefix(), var5);
         throw new ComponentInitializationException("Unable to filter metadata", var5);
      }
   }
}
