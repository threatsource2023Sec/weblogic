package org.opensaml.soap.wstrust.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.soap.wssecurity.Created;
import org.opensaml.soap.wssecurity.Expires;
import org.opensaml.soap.wstrust.Lifetime;

public class LifetimeImpl extends AbstractWSTrustObject implements Lifetime {
   private Created created;
   private Expires expires;

   public LifetimeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public Created getCreated() {
      return this.created;
   }

   public Expires getExpires() {
      return this.expires;
   }

   public void setCreated(Created newCreated) {
      this.created = (Created)this.prepareForAssignment(this.created, newCreated);
   }

   public void setExpires(Expires newExpires) {
      this.expires = (Expires)this.prepareForAssignment(this.expires, newExpires);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.created != null) {
         children.add(this.created);
      }

      if (this.expires != null) {
         children.add(this.expires);
      }

      return Collections.unmodifiableList(children);
   }
}
