package org.apache.xml.security.transforms.implementations;

import java.io.IOException;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class TransformBase64Decode extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/2000/09/xmldsig#base64";

   protected String engineGetURI() {
      return "http://www.w3.org/2000/09/xmldsig#base64";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, Transform transformObject) throws IOException, CanonicalizationException, TransformationException {
      return this.enginePerformTransform(input, (OutputStream)null, transformObject);
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws IOException, CanonicalizationException, TransformationException {
      if (input.isElement()) {
         Node el = input.getSubNode();
         if (input.getSubNode().getNodeType() == 3) {
            el = el.getParentNode();
         }

         StringBuilder sb = new StringBuilder();
         this.traverseElement((Element)el, sb);
         byte[] bytes;
         XMLSignatureInput output;
         if (os == null) {
            bytes = XMLUtils.decode(sb.toString());
            output = new XMLSignatureInput(bytes);
            output.setSecureValidation(this.secureValidation);
            return output;
         } else {
            bytes = XMLUtils.decode(sb.toString());
            os.write(bytes);
            output = new XMLSignatureInput((byte[])null);
            output.setSecureValidation(this.secureValidation);
            output.setOutputStream(os);
            return output;
         }
      } else if (!input.isOctetStream() && !input.isNodeSet()) {
         try {
            Document doc = XMLUtils.createDocumentBuilder(false, this.secureValidation).parse(input.getOctetStream());
            Element rootNode = doc.getDocumentElement();
            StringBuilder sb = new StringBuilder();
            this.traverseElement(rootNode, sb);
            byte[] decodedBytes = XMLUtils.decode(sb.toString());
            XMLSignatureInput output = new XMLSignatureInput(decodedBytes);
            output.setSecureValidation(this.secureValidation);
            return output;
         } catch (ParserConfigurationException var9) {
            throw new TransformationException(var9, "c14n.Canonicalizer.Exception");
         } catch (SAXException var10) {
            throw new TransformationException(var10, "SAX exception");
         }
      } else {
         byte[] bytes;
         byte[] bytes;
         if (os == null) {
            bytes = input.getBytes();
            bytes = XMLUtils.decode(bytes);
            XMLSignatureInput output = new XMLSignatureInput(bytes);
            output.setSecureValidation(this.secureValidation);
            return output;
         } else {
            if (!input.isByteArray() && !input.isNodeSet()) {
               bytes = JavaUtils.getBytesFromStream(input.getOctetStreamReal());
               bytes = XMLUtils.decode(bytes);
               os.write(bytes);
            } else {
               bytes = XMLUtils.decode(input.getBytes());
               os.write(bytes);
            }

            XMLSignatureInput output = new XMLSignatureInput((byte[])null);
            output.setSecureValidation(this.secureValidation);
            output.setOutputStream(os);
            return output;
         }
      }
   }

   void traverseElement(Element node, StringBuilder sb) {
      for(Node sibling = node.getFirstChild(); sibling != null; sibling = sibling.getNextSibling()) {
         switch (sibling.getNodeType()) {
            case 1:
               this.traverseElement((Element)sibling, sb);
               break;
            case 3:
               sb.append(((Text)sibling).getData());
         }
      }

   }
}
