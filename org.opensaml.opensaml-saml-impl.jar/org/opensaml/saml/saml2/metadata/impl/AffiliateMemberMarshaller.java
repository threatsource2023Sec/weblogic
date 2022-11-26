package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.AffiliateMember;
import org.w3c.dom.Element;

public class AffiliateMemberMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallElementContent(XMLObject samlObject, Element domElement) throws MarshallingException {
      super.marshallElementContent(samlObject, domElement);
      AffiliateMember member = (AffiliateMember)samlObject;
      if (member.getID() != null) {
         domElement.appendChild(domElement.getOwnerDocument().createTextNode(member.getID()));
      }

   }
}
