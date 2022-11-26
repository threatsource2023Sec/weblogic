package org.apache.xml.security.utils;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JDKXPathAPI implements XPathAPI {
   private javax.xml.xpath.XPathFactory xpf;
   private String xpathStr;
   private XPathExpression xpathExpression;

   public NodeList selectNodeList(Node contextNode, Node xpathnode, String str, Node namespaceNode) throws TransformerException {
      if (!str.equals(this.xpathStr) || this.xpathExpression == null) {
         if (this.xpf == null) {
            this.xpf = javax.xml.xpath.XPathFactory.newInstance();

            try {
               this.xpf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            } catch (XPathFactoryConfigurationException var9) {
               throw new TransformerException(var9);
            }
         }

         XPath xpath = this.xpf.newXPath();
         xpath.setNamespaceContext(new DOMNamespaceContext(namespaceNode));
         this.xpathStr = str;

         try {
            this.xpathExpression = xpath.compile(this.xpathStr);
         } catch (XPathExpressionException var8) {
            throw new TransformerException(var8);
         }
      }

      try {
         return (NodeList)this.xpathExpression.evaluate(contextNode, XPathConstants.NODESET);
      } catch (XPathExpressionException var7) {
         throw new TransformerException(var7);
      }
   }

   public boolean evaluate(Node contextNode, Node xpathnode, String str, Node namespaceNode) throws TransformerException {
      if (!str.equals(this.xpathStr) || this.xpathExpression == null) {
         if (this.xpf == null) {
            this.xpf = javax.xml.xpath.XPathFactory.newInstance();

            try {
               this.xpf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
            } catch (XPathFactoryConfigurationException var9) {
               throw new TransformerException(var9);
            }
         }

         XPath xpath = this.xpf.newXPath();
         xpath.setNamespaceContext(new DOMNamespaceContext(namespaceNode));
         this.xpathStr = str;

         try {
            this.xpathExpression = xpath.compile(this.xpathStr);
         } catch (XPathExpressionException var8) {
            throw new TransformerException(var8);
         }
      }

      try {
         return (Boolean)this.xpathExpression.evaluate(contextNode, XPathConstants.BOOLEAN);
      } catch (XPathExpressionException var7) {
         throw new TransformerException(var7);
      }
   }

   public void clear() {
      this.xpathStr = null;
      this.xpathExpression = null;
      this.xpf = null;
   }
}
