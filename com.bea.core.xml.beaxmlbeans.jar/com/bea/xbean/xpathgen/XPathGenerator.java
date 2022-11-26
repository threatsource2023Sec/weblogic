package com.bea.xbean.xpathgen;

import com.bea.xml.XmlCursor;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

public class XPathGenerator {
   public static String generateXPath(XmlCursor node, XmlCursor context, NamespaceContext nsctx) throws XPathGenerationException {
      if (node == null) {
         throw new IllegalArgumentException("Null node");
      } else if (nsctx == null) {
         throw new IllegalArgumentException("Null namespace context");
      } else {
         XmlCursor.TokenType tt = node.currentTokenType();
         if (context != null && node.isAtSamePositionAs(context)) {
            return ".";
         } else {
            QName name;
            String pathToParent;
            switch (tt.intValue()) {
               case 1:
               case 3:
                  return generateInternal(node, context, nsctx);
               case 2:
               case 4:
               default:
                  throw new XPathGenerationException("Cannot generate XPath for cursor position: " + tt.toString());
               case 5:
                  int nrOfTextTokens = countTextTokens(node);
                  node.toParent();
                  pathToParent = generateInternal(node, context, nsctx);
                  if (nrOfTextTokens == 0) {
                     return pathToParent + "/text()";
                  }

                  return pathToParent + "/text()[position()=" + nrOfTextTokens + ']';
               case 6:
                  name = node.getName();
                  node.toParent();
                  pathToParent = generateInternal(node, context, nsctx);
                  return pathToParent + '/' + '@' + qnameToString(name, nsctx);
               case 7:
                  name = node.getName();
                  node.toParent();
                  pathToParent = generateInternal(node, context, nsctx);
                  String prefix = name.getLocalPart();
                  return prefix.length() == 0 ? pathToParent + "/@xmlns" : pathToParent + "/@xmlns:" + prefix;
            }
         }
      }
   }

   private static String generateInternal(XmlCursor node, XmlCursor context, NamespaceContext nsctx) throws XPathGenerationException {
      if (node.isStartdoc()) {
         return "";
      } else if (context != null && node.isAtSamePositionAs(context)) {
         return ".";
      } else {
         assert node.isStart();

         QName name = node.getName();
         XmlCursor d = node.newCursor();
         if (!node.toParent()) {
            return "/" + name;
         } else {
            int elemIndex = 0;
            int i = 1;
            node.push();
            if (!node.toChild(name)) {
               throw new IllegalStateException("Must have at least one child with name: " + name);
            } else {
               do {
                  if (node.isAtSamePositionAs(d)) {
                     elemIndex = i;
                  } else {
                     ++i;
                  }
               } while(node.toNextSibling(name));

               node.pop();
               d.dispose();
               String pathToParent = generateInternal(node, context, nsctx);
               return i == 1 ? pathToParent + '/' + qnameToString(name, nsctx) : pathToParent + '/' + qnameToString(name, nsctx) + '[' + elemIndex + ']';
            }
         }
      }
   }

   private static String qnameToString(QName qname, NamespaceContext ctx) throws XPathGenerationException {
      String localName = qname.getLocalPart();
      String uri = qname.getNamespaceURI();
      if (uri.length() == 0) {
         return localName;
      } else {
         String prefix = qname.getPrefix();
         if (prefix != null && prefix.length() > 0) {
            String mappedUri = ctx.getNamespaceURI(prefix);
            if (uri.equals(mappedUri)) {
               return prefix + ':' + localName;
            }
         }

         prefix = ctx.getPrefix(uri);
         if (prefix == null) {
            throw new XPathGenerationException("Could not obtain a prefix for URI: " + uri);
         } else if (prefix.length() == 0) {
            throw new XPathGenerationException("Can not use default prefix in XPath for URI: " + uri);
         } else {
            return prefix + ':' + localName;
         }
      }
   }

   private static int countTextTokens(XmlCursor c) {
      int k = 0;
      int l = 0;
      XmlCursor d = c.newCursor();
      c.push();
      c.toParent();

      for(XmlCursor.TokenType tt = c.toFirstContentToken(); !tt.isEnd(); tt = c.toNextToken()) {
         if (tt.isText()) {
            if (c.comparePosition(d) > 0) {
               ++l;
            } else {
               ++k;
            }
         } else if (tt.isStart()) {
            c.toEndToken();
         }
      }

      c.pop();
      return l == 0 ? 0 : k;
   }

   public static void main(String[] args) throws XmlException {
      String xml = "<root>\n<ns:a xmlns:ns=\"http://a.com\"><b foo=\"value\">text1<c/>text2<c/>text3<c>text</c>text4</b></ns:a>\n</root>";
      NamespaceContext ns = new NamespaceContext() {
         public String getNamespaceURI(String prefix) {
            return "ns".equals(prefix) ? "http://a.com" : null;
         }

         public String getPrefix(String namespaceUri) {
            return null;
         }

         public Iterator getPrefixes(String namespaceUri) {
            return null;
         }
      };
      XmlCursor c = XmlObject.Factory.parse(xml).newCursor();
      c.toFirstContentToken();
      c.toFirstContentToken();
      c.toFirstChild();
      c.toFirstChild();
      c.push();
      System.out.println(generateXPath(c, (XmlCursor)null, ns));
      c.pop();
      c.toNextSibling();
      c.toNextSibling();
      c.push();
      System.out.println(generateXPath(c, (XmlCursor)null, ns));
      c.pop();
      XmlCursor d = c.newCursor();
      d.toParent();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      d.toParent();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      c.toFirstContentToken();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      c.toParent();
      c.toPrevToken();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      c.toParent();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      c.toFirstAttribute();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      c.toParent();
      c.toParent();
      c.toNextToken();
      c.push();
      System.out.println(generateXPath(c, d, ns));
      c.pop();
      c.push();
      System.out.println(generateXPath(c, (XmlCursor)null, ns));
      c.pop();
   }
}
