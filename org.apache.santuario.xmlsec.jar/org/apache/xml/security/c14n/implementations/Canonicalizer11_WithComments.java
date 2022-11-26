package org.apache.xml.security.c14n.implementations;

public class Canonicalizer11_WithComments extends Canonicalizer20010315 {
   public Canonicalizer11_WithComments() {
      super(true, true);
   }

   public final String engineGetURI() {
      return "http://www.w3.org/2006/12/xml-c14n11#WithComments";
   }

   public final boolean engineGetIncludeComments() {
      return true;
   }
}
