package org.opensaml;

import java.io.InputStream;
import org.w3c.dom.Element;

public abstract class SAMLSubjectQuery extends SAMLQuery implements Cloneable {
   protected SAMLSubject subject = null;

   public SAMLSubjectQuery() {
   }

   public SAMLSubjectQuery(SAMLSubject var1) throws SAMLException {
      this.subject = var1;
   }

   public SAMLSubjectQuery(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLSubjectQuery(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      this.subject = new SAMLSubject(XML.getFirstChildElement(var1));
   }

   public SAMLSubject getSubject() {
      return this.subject;
   }

   public void setSubject(SAMLSubject var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            this.root.replaceChild(var1.toDOM(this.root.getOwnerDocument()), this.subject.root);
         }

         this.subject = var1;
      } else {
         throw new IllegalArgumentException("subject cannot be null");
      }
   }

   public void checkValidity() throws SAMLException {
      if (this.subject == null) {
         throw new MalformedException(SAMLException.RESPONDER, "SubjectQuery invalid, requires subject");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLSubjectQuery var1 = (SAMLSubjectQuery)super.clone();
      var1.subject = (SAMLSubject)this.subject.clone();
      return var1;
   }
}
