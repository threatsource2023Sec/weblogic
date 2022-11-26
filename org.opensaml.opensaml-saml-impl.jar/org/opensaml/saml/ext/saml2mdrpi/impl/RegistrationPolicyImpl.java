package org.opensaml.saml.ext.saml2mdrpi.impl;

import org.opensaml.saml.ext.saml2mdrpi.RegistrationPolicy;
import org.opensaml.saml.saml2.metadata.impl.LocalizedURIImpl;

public class RegistrationPolicyImpl extends LocalizedURIImpl implements RegistrationPolicy {
   protected RegistrationPolicyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
