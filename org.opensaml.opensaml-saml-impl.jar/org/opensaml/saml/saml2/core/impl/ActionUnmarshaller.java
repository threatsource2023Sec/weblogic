package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.Action;
import org.w3c.dom.Attr;

public class ActionUnmarshaller extends AbstractSAMLObjectUnmarshaller {
   protected void processElementContent(XMLObject samlObject, String elementContent) {
      Action action = (Action)samlObject;
      action.setAction(elementContent);
   }

   protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
      Action action = (Action)samlObject;
      if (attribute.getLocalName().equals("Namespace") && attribute.getNamespaceURI() == null) {
         action.setNamespace(attribute.getValue());
      } else {
         super.processAttribute(samlObject, attribute);
      }

   }
}
