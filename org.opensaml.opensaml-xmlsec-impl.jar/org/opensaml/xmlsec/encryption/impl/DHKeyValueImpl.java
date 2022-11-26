package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.encryption.DHKeyValue;
import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.encryption.Seed;

public class DHKeyValueImpl extends AbstractXMLObject implements DHKeyValue {
   private P p;
   private Q q;
   private Generator generator;
   private Public publicChild;
   private Seed seed;
   private PgenCounter pgenCounter;

   protected DHKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public P getP() {
      return this.p;
   }

   public void setP(P newP) {
      this.p = (P)this.prepareForAssignment(this.p, newP);
   }

   public Q getQ() {
      return this.q;
   }

   public void setQ(Q newQ) {
      this.q = (Q)this.prepareForAssignment(this.q, newQ);
   }

   public Generator getGenerator() {
      return this.generator;
   }

   public void setGenerator(Generator newGenerator) {
      this.generator = (Generator)this.prepareForAssignment(this.generator, newGenerator);
   }

   public Public getPublic() {
      return this.publicChild;
   }

   public void setPublic(Public newPublic) {
      this.publicChild = (Public)this.prepareForAssignment(this.publicChild, newPublic);
   }

   public Seed getSeed() {
      return this.seed;
   }

   public void setSeed(Seed newSeed) {
      this.seed = (Seed)this.prepareForAssignment(this.seed, newSeed);
   }

   public PgenCounter getPgenCounter() {
      return this.pgenCounter;
   }

   public void setPgenCounter(PgenCounter newPgenCounter) {
      this.pgenCounter = (PgenCounter)this.prepareForAssignment(this.pgenCounter, newPgenCounter);
   }

   public List getOrderedChildren() {
      ArrayList children = new ArrayList();
      if (this.p != null) {
         children.add(this.p);
      }

      if (this.q != null) {
         children.add(this.q);
      }

      if (this.generator != null) {
         children.add(this.generator);
      }

      if (this.publicChild != null) {
         children.add(this.publicChild);
      }

      if (this.seed != null) {
         children.add(this.seed);
      }

      if (this.pgenCounter != null) {
         children.add(this.pgenCounter);
      }

      return children.size() == 0 ? null : Collections.unmodifiableList(children);
   }
}
