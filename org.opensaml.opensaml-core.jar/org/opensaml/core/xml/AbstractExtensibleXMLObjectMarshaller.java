package org.opensaml.core.xml;

import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractExtensibleXMLObjectMarshaller extends AbstractElementExtensibleXMLObjectMarshaller {
   protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
      AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject)xmlObject;
      Document document = domElement.getOwnerDocument();
      Iterator var6 = anyAttribute.getUnknownAttributes().entrySet().iterator();

      while(true) {
         Attr attribute;
         Map.Entry entry;
         do {
            if (!var6.hasNext()) {
               return;
            }

            entry = (Map.Entry)var6.next();
            attribute = AttributeSupport.constructAttribute(document, (QName)entry.getKey());
            attribute.setValue((String)entry.getValue());
            domElement.setAttributeNodeNS(attribute);
         } while(!XMLObjectProviderRegistrySupport.isIDAttribute((QName)entry.getKey()) && !anyAttribute.getUnknownAttributes().isIDAttribute((QName)entry.getKey()));

         attribute.getOwnerElement().setIdAttributeNode(attribute, true);
      }
   }
}
