package org.opensaml.saml.saml1.core.impl;

import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.w3c.dom.Element;

public class AuthorityBindingMarshaller extends AbstractSAMLObjectMarshaller {
   public void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
      AuthorityBinding authorityBinding = (AuthorityBinding)samlElement;
      if (authorityBinding.getAuthorityKind() != null) {
         QName authKind = authorityBinding.getAuthorityKind();
         domElement.setAttributeNS((String)null, "AuthorityKind", QNameSupport.qnameToContentString(authKind));
      }

      if (authorityBinding.getBinding() != null) {
         domElement.setAttributeNS((String)null, "Binding", authorityBinding.getBinding());
      }

      if (authorityBinding.getLocation() != null) {
         domElement.setAttributeNS((String)null, "Location", authorityBinding.getLocation());
      }

   }
}
