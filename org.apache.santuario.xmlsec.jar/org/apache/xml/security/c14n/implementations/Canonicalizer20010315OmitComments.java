package org.apache.xml.security.c14n.implementations;

public class Canonicalizer20010315OmitComments extends Canonicalizer20010315 {
   public Canonicalizer20010315OmitComments() {
      super(false);
   }

   public final String engineGetURI() {
      return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
   }

   public final boolean engineGetIncludeComments() {
      return false;
   }
}
