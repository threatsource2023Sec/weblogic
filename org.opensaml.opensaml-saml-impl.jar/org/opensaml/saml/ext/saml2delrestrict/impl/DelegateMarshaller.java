package org.opensaml.saml.ext.saml2delrestrict.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.w3c.dom.Element;

public class DelegateMarshaller extends AbstractSAMLObjectMarshaller {
   protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
      Delegate delegate = (Delegate)xmlObject;
      if (delegate.getDelegationInstant() != null) {
         String delInstant = SAMLConfigurationSupport.getSAMLDateFormatter().print(delegate.getDelegationInstant());
         domElement.setAttributeNS((String)null, "DelegationInstant", delInstant);
      }

      if (!Strings.isNullOrEmpty(delegate.getConfirmationMethod())) {
         domElement.setAttributeNS((String)null, "ConfirmationMethod", delegate.getConfirmationMethod());
      }

      super.marshallAttributes(xmlObject, domElement);
   }
}
