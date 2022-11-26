package org.apache.xml.security.transforms.implementations;

import java.io.OutputStream;
import org.apache.xml.security.signature.NodeFilter;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TransformEnvelopedSignature extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/2000/09/xmldsig#enveloped-signature";

   protected String engineGetURI() {
      return "http://www.w3.org/2000/09/xmldsig#enveloped-signature";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws TransformationException {
      Node signatureElement = transformObject.getElement();
      Node signatureElement = searchSignatureElement(signatureElement);
      input.setExcludeNode(signatureElement);
      input.addNodeFilter(new EnvelopedNodeFilter(signatureElement));
      return input;
   }

   private static Node searchSignatureElement(Node signatureElement) throws TransformationException {
      boolean found;
      for(found = false; signatureElement != null && signatureElement.getNodeType() != 9; signatureElement = signatureElement.getParentNode()) {
         Element el = (Element)signatureElement;
         if (el.getNamespaceURI().equals("http://www.w3.org/2000/09/xmldsig#") && el.getLocalName().equals("Signature")) {
            found = true;
            break;
         }
      }

      if (!found) {
         throw new TransformationException("transform.envelopedSignatureTransformNotInSignatureElement");
      } else {
         return signatureElement;
      }
   }

   static class EnvelopedNodeFilter implements NodeFilter {
      Node exclude;

      EnvelopedNodeFilter(Node n) {
         this.exclude = n;
      }

      public int isNodeIncludeDO(Node n, int level) {
         return n == this.exclude ? -1 : 1;
      }

      public int isNodeInclude(Node n) {
         return n != this.exclude && !XMLUtils.isDescendantOrSelf(this.exclude, n) ? 1 : -1;
      }
   }
}
