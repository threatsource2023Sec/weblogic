package org.opensaml.core.xml.schema.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.schema.XSQName;

public class XSQNameImpl extends AbstractXMLObject implements XSQName {
   private QName value;

   protected XSQNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public QName getValue() {
      return this.value;
   }

   public void setValue(QName newValue) {
      this.value = this.prepareElementContentForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return Collections.unmodifiableList(new LinkedList());
   }
}
