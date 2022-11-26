package org.apache.taglibs.standard.tag.common.xml;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathVariableResolver;
import org.apache.taglibs.standard.resources.Resources;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathUtil {
   private static final String PAGE_NS_URL = "http://java.sun.com/jstl/xpath/page";
   private static final String REQUEST_NS_URL = "http://java.sun.com/jstl/xpath/request";
   private static final String SESSION_NS_URL = "http://java.sun.com/jstl/xpath/session";
   private static final String APP_NS_URL = "http://java.sun.com/jstl/xpath/app";
   private static final String PARAM_NS_URL = "http://java.sun.com/jstl/xpath/param";
   private static final String INITPARAM_NS_URL = "http://java.sun.com/jstl/xpath/initParam";
   private static final String COOKIE_NS_URL = "http://java.sun.com/jstl/xpath/cookie";
   private static final String HEADER_NS_URL = "http://java.sun.com/jstl/xpath/header";
   private PageContext pageContext;
   private static final String XPATH_FACTORY_CLASS_NAME = "org.apache.taglibs.standard.tag.common.xml.JSTLXPathFactory";
   private static XPathFactory XPATH_FACTORY;
   private static JSTLXPathNamespaceContext jstlXPathNamespaceContext = null;
   private static DocumentBuilderFactory dbf = null;

   public XPathUtil(PageContext pc) {
      this.pageContext = pc;
   }

   private static void initXPathFactory() {
      if (System.getSecurityManager() != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               System.setProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom", "org.apache.taglibs.standard.tag.common.xml.JSTLXPathFactory");
               return null;
            }
         });
      } else {
         System.setProperty("javax.xml.xpath.XPathFactory:http://java.sun.com/jaxp/xpath/dom", "org.apache.taglibs.standard.tag.common.xml.JSTLXPathFactory");
      }

      try {
         XPATH_FACTORY = XPathFactory.newInstance("http://java.sun.com/jaxp/xpath/dom");
      } catch (XPathFactoryConfigurationException var1) {
         var1.printStackTrace();
      }

   }

   private static void initXPathNamespaceContext() {
      jstlXPathNamespaceContext = new JSTLXPathNamespaceContext();
      jstlXPathNamespaceContext.addNamespace("pageScope", "http://java.sun.com/jstl/xpath/page");
      jstlXPathNamespaceContext.addNamespace("requestScope", "http://java.sun.com/jstl/xpath/request");
      jstlXPathNamespaceContext.addNamespace("sessionScope", "http://java.sun.com/jstl/xpath/session");
      jstlXPathNamespaceContext.addNamespace("applicationScope", "http://java.sun.com/jstl/xpath/app");
      jstlXPathNamespaceContext.addNamespace("param", "http://java.sun.com/jstl/xpath/param");
      jstlXPathNamespaceContext.addNamespace("initParam", "http://java.sun.com/jstl/xpath/initParam");
      jstlXPathNamespaceContext.addNamespace("header", "http://java.sun.com/jstl/xpath/header");
      jstlXPathNamespaceContext.addNamespace("cookie", "http://java.sun.com/jstl/xpath/cookie");
   }

   private static void initDocumentBuilderFactory() {
      dbf = DocumentBuilderFactory.newInstance();
      dbf.setNamespaceAware(true);
      dbf.setValidating(false);
   }

   private static Document newEmptyDocument() {
      try {
         DocumentBuilder db = dbf.newDocumentBuilder();
         return db.newDocument();
      } catch (ParserConfigurationException var1) {
         throw new AssertionError();
      }
   }

   public String valueOf(Node contextNode, String xpathString) throws JspTagException {
      XPathVariableResolver jxvr = new JSTLXPathVariableResolver(this.pageContext);
      XPath xpath = XPATH_FACTORY.newXPath();
      xpath.setNamespaceContext(jstlXPathNamespaceContext);
      xpath.setXPathVariableResolver(jxvr);

      try {
         return xpath.evaluate(xpathString, contextNode);
      } catch (XPathExpressionException var6) {
         throw new JspTagException(var6.toString(), var6);
      }
   }

   public boolean booleanValueOf(Node contextNode, String xpathString) throws JspTagException {
      XPathVariableResolver jxvr = new JSTLXPathVariableResolver(this.pageContext);
      XPath xpath = XPATH_FACTORY.newXPath();
      xpath.setNamespaceContext(jstlXPathNamespaceContext);
      xpath.setXPathVariableResolver(jxvr);

      try {
         return (Boolean)xpath.evaluate(xpathString, contextNode, XPathConstants.BOOLEAN);
      } catch (XPathExpressionException var6) {
         throw new JspTagException(Resources.getMessage("XPATH_ERROR_XOBJECT", (Object)var6.toString()), var6);
      }
   }

   public List selectNodes(Node contextNode, String xpathString) throws JspTagException {
      XPathVariableResolver jxvr = new JSTLXPathVariableResolver(this.pageContext);

      try {
         XPath xpath = XPATH_FACTORY.newXPath();
         xpath.setNamespaceContext(jstlXPathNamespaceContext);
         xpath.setXPathVariableResolver(jxvr);
         Object nl = xpath.evaluate(xpathString, contextNode, JSTLXPathConstants.OBJECT);
         return new JSTLNodeList(nl);
      } catch (XPathExpressionException var6) {
         throw new JspTagException(var6.toString(), var6);
      }
   }

   public Node selectSingleNode(Node contextNode, String xpathString) throws JspTagException {
      XPathVariableResolver jxvr = new JSTLXPathVariableResolver(this.pageContext);

      try {
         XPath xpath = XPATH_FACTORY.newXPath();
         xpath.setNamespaceContext(jstlXPathNamespaceContext);
         xpath.setXPathVariableResolver(jxvr);
         return (Node)xpath.evaluate(xpathString, contextNode, XPathConstants.NODE);
      } catch (XPathExpressionException var5) {
         throw new JspTagException(var5.toString(), var5);
      }
   }

   public static Node getContext(Tag t) throws JspTagException {
      ForEachTag xt = (ForEachTag)TagSupport.findAncestorWithClass(t, ForEachTag.class);
      return (Node)(xt == null ? newEmptyDocument() : xt.getContext());
   }

   private static void p(String s) {
      System.out.println("[XPathUtil] " + s);
   }

   public static void printDetails(Node n) {
      p("\n\nDetails of Node = > " + n);
      p("Name:Type:Node Value = > " + n.getNodeName() + ":" + n.getNodeType() + ":" + n.getNodeValue());
      p("Namespace URI : Prefix : localName = > " + n.getNamespaceURI() + ":" + n.getPrefix() + ":" + n.getLocalName());
      p("\n Node has children => " + n.hasChildNodes());
      if (n.hasChildNodes()) {
         NodeList nl = n.getChildNodes();
         p("Number of Children => " + nl.getLength());

         for(int i = 0; i < nl.getLength(); ++i) {
            Node childNode = nl.item(i);
            printDetails(childNode);
         }
      }

   }

   static {
      initXPathFactory();
      initXPathNamespaceContext();
      initDocumentBuilderFactory();
   }
}
