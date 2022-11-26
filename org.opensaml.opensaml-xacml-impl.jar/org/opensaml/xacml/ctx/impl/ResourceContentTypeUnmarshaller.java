package org.opensaml.xacml.ctx.impl;

import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.impl.AbstractXACMLObjectUnmarshaller;
import org.w3c.dom.Attr;

public class ResourceContentTypeUnmarshaller extends AbstractXACMLObjectUnmarshaller {
   protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
      ResourceContentType resourceContent = (ResourceContentType)xmlObject;
      this.processUnknownAttribute(resourceContent, attribute);
   }

   protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
      ResourceContentType resourceContent = (ResourceContentType)parentXMLObject;
      resourceContent.getUnknownXMLObjects().add(childXMLObject);
   }

   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      ResourceContentType resourceContent = (ResourceContentType)xmlObject;
      resourceContent.setValue(StringSupport.trimOrNull(elementContent));
   }
}
