package org.opensaml.soap.soap11.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.soap.common.AbstractExtensibleSOAPObject;
import org.opensaml.soap.soap11.Body;
import org.opensaml.soap.soap11.Envelope;
import org.opensaml.soap.soap11.Header;

public class EnvelopeImpl extends AbstractExtensibleSOAPObject implements Envelope {
   private Header header;
   private Body body;

   protected EnvelopeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Header getHeader() {
      return this.header;
   }

   public void setHeader(Header newHeader) {
      this.header = (Header)this.prepareForAssignment(this.header, newHeader);
   }

   public Body getBody() {
      return this.body;
   }

   public void setBody(Body newBody) {
      this.body = (Body)this.prepareForAssignment(this.body, newBody);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      children.add(this.header);
      children.add(this.body);
      children.addAll(super.getOrderedChildren());
      return Collections.unmodifiableList(children);
   }
}
