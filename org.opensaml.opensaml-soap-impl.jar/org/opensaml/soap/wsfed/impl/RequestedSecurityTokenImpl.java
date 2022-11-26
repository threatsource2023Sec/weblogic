package org.opensaml.soap.wsfed.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.soap.wsfed.RequestedSecurityToken;

public class RequestedSecurityTokenImpl extends AbstractXMLObject implements RequestedSecurityToken {
   private final XMLObjectChildrenList tokens = new XMLObjectChildrenList(this);

   public RequestedSecurityTokenImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getSecurityTokens() {
      return this.tokens;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList(1 + this.tokens.size());
      children.addAll(this.tokens);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
