package org.opensaml.saml.saml2.core.impl;

import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.Action;
import org.w3c.dom.Element;

public class ActionMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
      Action action = (Action)samlObject;
      if (action.getNamespace() != null) {
         domElement.setAttributeNS((String)null, "Namespace", action.getNamespace());
      }

   }

   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      Action action = (Action)samlObject;
      ElementSupport.appendTextContent(domElement, action.getAction());
   }
}
