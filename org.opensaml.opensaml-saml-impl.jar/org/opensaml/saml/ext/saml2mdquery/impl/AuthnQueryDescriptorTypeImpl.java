package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.ext.saml2mdquery.AuthnQueryDescriptorType;

public class AuthnQueryDescriptorTypeImpl extends QueryDescriptorTypeImpl implements AuthnQueryDescriptorType {
   protected AuthnQueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getEndpoints() {
      return new ArrayList();
   }

   public List getEndpoints(QName type) {
      return null;
   }
}
