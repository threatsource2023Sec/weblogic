package org.apache.xml.security.transforms.implementations;

import java.io.OutputStream;
import javax.xml.transform.TransformerException;
import org.apache.xml.security.exceptions.XMLSecurityRuntimeException;
import org.apache.xml.security.signature.NodeFilter;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.transforms.Transform;
import org.apache.xml.security.transforms.TransformSpi;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.XPathAPI;
import org.apache.xml.security.utils.XPathFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class TransformXPath extends TransformSpi {
   public static final String implementedTransformURI = "http://www.w3.org/TR/1999/REC-xpath-19991116";

   protected String engineGetURI() {
      return "http://www.w3.org/TR/1999/REC-xpath-19991116";
   }

   protected XMLSignatureInput enginePerformTransform(XMLSignatureInput input, OutputStream os, Transform transformObject) throws TransformationException {
      try {
         Element xpathElement = XMLUtils.selectDsNode(transformObject.getElement().getFirstChild(), "XPath", 0);
         if (xpathElement == null) {
            Object[] exArgs = new Object[]{"ds:XPath", "Transform"};
            throw new TransformationException("xml.WrongContent", exArgs);
         } else {
            Node xpathnode = xpathElement.getFirstChild();
            if (xpathnode == null) {
               throw new DOMException((short)3, "Text must be in ds:Xpath");
            } else {
               String str = XMLUtils.getStrFromNode(xpathnode);
               input.setNeedsToBeExpanded(this.needsCircumvent(str));
               XPathFactory xpathFactory = XPathFactory.newInstance();
               XPathAPI xpathAPIInstance = xpathFactory.newXPathAPI();
               input.addNodeFilter(new XPathNodeFilter(xpathElement, xpathnode, str, xpathAPIInstance));
               input.setNodeSet(true);
               return input;
            }
         }
      } catch (DOMException var9) {
         throw new TransformationException(var9);
      }
   }

   private boolean needsCircumvent(String str) {
      return str.indexOf("namespace") != -1 || str.indexOf("name()") != -1;
   }

   static class XPathNodeFilter implements NodeFilter {
      XPathAPI xPathAPI;
      Node xpathnode;
      Element xpathElement;
      String str;

      XPathNodeFilter(Element xpathElement, Node xpathnode, String str, XPathAPI xPathAPI) {
         this.xpathnode = xpathnode;
         this.str = str;
         this.xpathElement = xpathElement;
         this.xPathAPI = xPathAPI;
      }

      public int isNodeInclude(Node currentNode) {
         Object[] eArgs;
         try {
            boolean include = this.xPathAPI.evaluate(currentNode, this.xpathnode, this.str, this.xpathElement);
            return include ? 1 : 0;
         } catch (TransformerException var4) {
            eArgs = new Object[]{currentNode};
            throw new XMLSecurityRuntimeException("signature.Transform.node", eArgs, var4);
         } catch (Exception var5) {
            eArgs = new Object[]{currentNode, currentNode.getNodeType()};
            throw new XMLSecurityRuntimeException("signature.Transform.nodeAndType", eArgs, var5);
         }
      }

      public int isNodeIncludeDO(Node n, int level) {
         return this.isNodeInclude(n);
      }
   }
}
