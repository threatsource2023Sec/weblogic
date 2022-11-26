package org.apache.xml.security.transforms.implementations;

import java.io.OutputStream;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.implementations.Canonicalizer11_OmitComments;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;

public class TransformC14N11 extends TransformSpi {
   protected String engineGetURI() {
      return "http://www.w3.org/2006/12/xml-c14n11";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transform) throws CanonicalizationException {
      Canonicalizer11_OmitComments c14n = new Canonicalizer11_OmitComments();
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
