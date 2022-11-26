package org.opensaml.xacml.policy.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.DescriptionType;
import org.w3c.dom.Element;

public class DescriptionTypeMarshaller extends AbstractXACMLObjectMarshaller {
   protected void marshallElementContent(XMLObject xmlobject, Element domElement) throws MarshallingException {
      DescriptionType message = (DescriptionType)xmlobject;
      if (message.getValue() != null) {
         ElementSupport.appendTextContent(domElement, message.getValue());
      }

   }
}
