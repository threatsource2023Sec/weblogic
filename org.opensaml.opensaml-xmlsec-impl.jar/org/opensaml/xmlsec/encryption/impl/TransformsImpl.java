package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xmlsec.encryption.Transforms;

public class TransformsImpl extends AbstractXMLObject implements Transforms {
   private final XMLObjectChildrenList transforms = new XMLObjectChildrenList(this);

   protected TransformsImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getTransforms() {
      return this.transforms;
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.addAll(this.transforms);
      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
