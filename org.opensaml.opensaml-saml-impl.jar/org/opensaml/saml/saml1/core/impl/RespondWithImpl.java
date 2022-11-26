package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.RespondWith;

public class RespondWithImpl extends AbstractSAMLObject implements RespondWith {
   private QName value;

   protected RespondWithImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public QName getValue() {
      return this.value;
   }

   public void setValue(QName newValue) {
      this.value = this.prepareElementContentForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return null;
   }
}
