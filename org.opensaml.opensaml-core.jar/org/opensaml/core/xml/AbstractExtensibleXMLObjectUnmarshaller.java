package org.opensaml.core.xml;

import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.w3c.dom.Attr;

public abstract class AbstractExtensibleXMLObjectUnmarshaller extends AbstractElementExtensibleXMLObjectUnmarshaller {
   protected void processAttribute(@Nonnull XMLObject xmlObject, @Nonnull Attr attribute) throws UnmarshallingException {
      AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject)xmlObject;
      QName attribQName = QNameSupport.constructQName(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getPrefix());
      if (attribute.isId()) {
         anyAttribute.getUnknownAttributes().registerID(attribQName);
      }

      anyAttribute.getUnknownAttributes().put(attribQName, attribute.getValue());
   }
}
