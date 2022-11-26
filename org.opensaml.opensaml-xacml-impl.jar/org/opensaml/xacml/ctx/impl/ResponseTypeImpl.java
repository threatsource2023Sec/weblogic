package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class ResponseTypeImpl extends AbstractXACMLObject implements ResponseType {
   private final XMLObjectChildrenList results = new XMLObjectChildrenList(this);

   protected ResponseTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.results);
      return Collections.unmodifiableList(children);
   }

   public List getResults() {
      return this.results;
   }
}
