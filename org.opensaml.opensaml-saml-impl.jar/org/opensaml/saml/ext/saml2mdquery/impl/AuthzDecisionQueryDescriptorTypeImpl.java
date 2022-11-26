package org.opensaml.saml.ext.saml2mdquery.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.ext.saml2mdquery.AuthzDecisionQueryDescriptorType;

public class AuthzDecisionQueryDescriptorTypeImpl extends QueryDescriptorTypeImpl implements AuthzDecisionQueryDescriptorType {
   private XMLObjectChildrenList actionNamespaces = new XMLObjectChildrenList(this);

   protected AuthzDecisionQueryDescriptorTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getActionNamespaces() {
      return this.actionNamespaces;
   }

   public List getEndpoints() {
      return new ArrayList();
   }

   public List getEndpoints(QName type) {
      return null;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(super.getOrderedChildren());
      children.addAll(this.actionNamespaces);
      return Collections.unmodifiableList(children);
   }
}
