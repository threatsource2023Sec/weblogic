package org.apache.xml.security.transforms.implementations;

import java.io.OutputStream;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315OmitComments;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;

public class TransformC14N extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";

   protected String engineGetURI() {
      return "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws CanonicalizationException {
      Canonicalizer20010315OmitComments c14n = new Canonicalizer20010315OmitComments();
      c14n.setSecureValidation(this.secureValidation);
      if (os != null) {
         c14n.setWriter(os);
      }

      byte[] result = null;
      byte[] result = c14n.engineCanonicalize(input);
      XMLSignatureInput output = new XMLSignatureInput(result);
      output.setSecureValidation(this.secureValidation);
      if (os != null) {
         output.setOutputStream(os);
      }

      return output;
   }
}
