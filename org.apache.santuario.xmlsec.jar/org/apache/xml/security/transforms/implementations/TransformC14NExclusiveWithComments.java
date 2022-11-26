package org.apache.xml.security.transforms.implementations;

import java.io.OutputStream;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315ExclWithComments;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.params.InclusiveNamespaces;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;

public class TransformC14NExclusiveWithComments extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";

   protected String engineGetURI() {
      return "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws CanonicalizationException {
      try {
         String inclusiveNamespaces = null;
         if (transformObject.length("http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces") == 1) {
            Element inclusiveElement = XMLUtils.selectNode(transformObject.getElement().getFirstChild(), "http://www.w3.org/2001/10/xml-exc-c14n#", "InclusiveNamespaces", 0);
            inclusiveNamespaces = (new InclusiveNamespaces(inclusiveElement, transformObject.getBaseURI())).getInclusiveNamespaces();
         }

         Canonicalizer20010315ExclWithComments c14n = new Canonicalizer20010315ExclWithComments();
         c14n.setSecureValidation(this.secureValidation);
         if (os != null) {
            c14n.setWriter(os);
         }

         byte[] result = c14n.engineCanonicalize(input, inclusiveNamespaces);
         XMLSignatureInput output = new XMLSignatureInput(result);
         output.setSecureValidation(this.secureValidation);
         return output;
      } catch (XMLSecurityException var8) {
         throw new CanonicalizationException(var8);
      }
   }
}
