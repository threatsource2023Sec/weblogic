package org.opensaml.saml.ext.saml2delrestrict.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;

public class DelegationRestrictionTypeBuilder extends AbstractSAMLObjectBuilder {
   public DelegationRestrictionType buildObject() {
      return (DelegationRestrictionType)this.buildObject(DelegationRestrictionType.DEFAULT_ELEMENT_NAME, DelegationRestrictionType.TYPE_NAME);
   }

   public DelegationRestrictionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DelegationRestrictionTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
