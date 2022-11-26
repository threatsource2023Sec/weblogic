package org.opensaml.saml.ext.saml2aslo.impl;

import java.util.List;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2aslo.Asynchronous;

public class AsynchronousImpl extends AbstractSAMLObject implements Asynchronous {
   protected AsynchronousImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }
}
