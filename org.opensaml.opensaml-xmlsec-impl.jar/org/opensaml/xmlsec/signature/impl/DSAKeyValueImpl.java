package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.xmlsec.signature.DSAKeyValue;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.J;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.PgenCounter;
import org.opensaml.xmlsec.signature.Q;
import org.opensaml.xmlsec.signature.Seed;
import org.opensaml.xmlsec.signature.Y;

public class DSAKeyValueImpl extends AbstractXMLObject implements DSAKeyValue {
   private P p;
   private Q q;
   private G g;
   private Y y;
   private J j;
   private Seed seed;
   private PgenCounter pgenCounter;

   protected DSAKeyValueImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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

   public G getG() {
      return this.g;
   }

   public void setG(G newG) {
      this.g = (G)this.prepareForAssignment(this.g, newG);
   }

   public Y getY() {
      return this.y;
   }

   public void setY(Y newY) {
      this.y = (Y)this.prepareForAssignment(this.y, newY);
   }

   public J getJ() {
      return this.j;
   }

   public void setJ(J newJ) {
      this.j = (J)this.prepareForAssignment(this.j, newJ);
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

      if (this.g != null) {
         children.add(this.g);
      }

      if (this.y != null) {
         children.add(this.y);
      }

      if (this.j != null) {
         children.add(this.j);
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
