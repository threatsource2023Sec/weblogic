package org.apache.xml.security.transforms.implementations;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.params.XPath2FilterContainer;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.XPathAPI;
import org.apache.xml.security.utils.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TransformXPath2Filter extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/2002/06/xmldsig-filter2";

   protected String engineGetURI() {
      return "http://www.w3.org/2002/06/xmldsig-filter2";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws TransformationException {
      try {
         List unionNodes = new ArrayList();
         List subtractNodes = new ArrayList();
         List intersectNodes = new ArrayList();
         Element[] xpathElements = XMLUtils.selectNodes(transformObject.getElement().getFirstChild(), "http://www.w3.org/2002/06/xmldsig-filter2", "XPath");
         if (xpathElements.length == 0) {
            Object[] exArgs = new Object[]{"http://www.w3.org/2002/06/xmldsig-filter2", "XPath"};
            throw new TransformationException("xml.WrongContent", exArgs);
         } else {
            Document inputDoc = null;
            if (input.getSubNode() != null) {
               inputDoc = XMLUtils.getOwnerDocument(input.getSubNode());
            } else {
               inputDoc = XMLUtils.getOwnerDocument(input.getNodeSet());
            }

            for(int i = 0; i < xpathElements.length; ++i) {
               Element xpathElement = xpathElements[i];
               XPath2FilterContainer xpathContainer = XPath2FilterContainer.newInstance(xpathElement, input.getSourceURI());
               String str = XMLUtils.getStrFromNode(xpathContainer.getXPathFilterTextNode());
               XPathFactory xpathFactory = XPathFactory.newInstance();
               XPathAPI xpathAPIInstance = xpathFactory.newXPathAPI();
               NodeList subtreeRoots = xpathAPIInstance.selectNodeList(inputDoc, xpathContainer.getXPathFilterTextNode(), str, xpathContainer.getElement());
               if (xpathContainer.isIntersect()) {
                  intersectNodes.add(subtreeRoots);
               } else if (xpathContainer.isSubtract()) {
                  subtractNodes.add(subtreeRoots);
               } else if (xpathContainer.isUnion()) {
                  unionNodes.add(subtreeRoots);
               }
            }

            input.addNodeFilter(new XPath2NodeFilter(unionNodes, subtractNodes, intersectNodes));
            input.setNodeSet(true);
            return input;
         }
      } catch (TransformerException var16) {
         throw new TransformationException(var16);
      } catch (DOMException var17) {
         throw new TransformationException(var17);
      } catch (CanonicalizationException var18) {
         throw new TransformationException(var18);
      } catch (InvalidCanonicalizerException var19) {
         throw new TransformationException(var19);
      } catch (XMLSecurityException var20) {
         throw new TransformationException(var20);
      } catch (SAXException var21) {
         throw new TransformationException(var21);
      } catch (IOException var22) {
         throw new TransformationException(var22);
      } catch (ParserConfigurationException var23) {
         throw new TransformationException(var23);
      }
   }
}
