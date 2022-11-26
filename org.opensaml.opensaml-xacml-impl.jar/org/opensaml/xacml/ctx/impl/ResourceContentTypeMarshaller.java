package org.opensaml.xacml.ctx.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.ResourceContentType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class ResourceContentTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ResourceContentType resourceContent = (ResourceContentType)xmlObject;
      this.marshallUnknownAttributes(resourceContent, domElement);
   }

   protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
      ResourceContentType resourceContent = (ResourceContentType)xmlObject;
      if (resourceContent.getValue() != null) {
         ElementSupport.appendTextContent(domElement, resourceContent.getValue());
      }

   }
}
