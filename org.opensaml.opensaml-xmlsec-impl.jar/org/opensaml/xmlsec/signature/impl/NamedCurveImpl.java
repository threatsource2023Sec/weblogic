package org.opensaml.xmlsec.signature.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.NamedCurve;

public class NamedCurveImpl extends AbstractXMLObject implements NamedCurve {
   private String uri;

   protected NamedCurveImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public String getURI() {
      return this.uri;
   }

   public void setURI(String newURI) {
      this.uri = this.prepareForAssignment(this.uri, newURI);
   }

   public List getOrderedChildren() {
      return null;
   }
}
