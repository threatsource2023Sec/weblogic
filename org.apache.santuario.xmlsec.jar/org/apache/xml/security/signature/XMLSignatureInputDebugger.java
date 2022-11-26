package org.apache.xml.security.signature;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Set;
import org.apache.xml.security.c14n.helper.AttrCompare;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;

public class XMLSignatureInputDebugger {
   private Set xpathNodeSet;
   private Set inclusiveNamespaces;
   private Writer writer;
   static final String HTMLPrefix = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n";
   static final String HTMLSuffix = "</pre></body></html>";
   static final String HTMLExcludePrefix = "<span class=\"EXCLUDED\">";
   static final String HTMLIncludePrefix = "<span class=\"INCLUDED\">";
   static final String HTMLIncludeOrExcludeSuffix = "</span>";
   static final String HTMLIncludedInclusiveNamespacePrefix = "<span class=\"INCLUDEDINCLUSIVENAMESPACE\">";
   static final String HTMLExcludedInclusiveNamespacePrefix = "<span class=\"EXCLUDEDINCLUSIVENAMESPACE\">";
   private static final int NODE_BEFORE_DOCUMENT_ELEMENT = -1;
   private static final int NODE_NOT_BEFORE_OR_AFTER_DOCUMENT_ELEMENT = 0;
   private static final int NODE_AFTER_DOCUMENT_ELEMENT = 1;
   static final AttrCompare ATTR_COMPARE = new AttrCompare();

   public XMLSignatureInputDebugger(XMLSignatureInput xmlSignatureInput) {
      if (!xmlSignatureInput.isNodeSet()) {
         this.xpathNodeSet = null;
      } else {
         this.xpathNodeSet = xmlSignatureInput.getInputNodeSet();
      }

   }

   public XMLSignatureInputDebugger(XMLSignatureInput xmlSignatureInput, Set inclusiveNamespace) {
      this(xmlSignatureInput);
      this.inclusiveNamespaces = inclusiveNamespace;
   }

   public String getHTMLRepresentation() throws XMLSignatureException {
      if (this.xpathNodeSet != null && !this.xpathNodeSet.isEmpty()) {
         Node n = (Node)this.xpathNodeSet.iterator().next();
         Document doc = XMLUtils.getOwnerDocument(n);

         String var3;
         try {
            this.writer = new StringWriter();
            this.canonicalizeXPathNodeSet(doc);
            this.writer.close();
            var3 = this.writer.toString();
         } catch (IOException var7) {
            throw new XMLSignatureException(var7);
         } finally {
            this.xpathNodeSet = null;
            this.writer = null;
         }

         return var3;
      } else {
         return "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n<blink>no node set, sorry</blink></pre></body></html>";
      }
   }

   private void canonicalizeXPathNodeSet(Node currentNode) throws XMLSignatureException, IOException {
      int currentNodeType = currentNode.getNodeType();
      int position;
      switch (currentNodeType) {
         case 1:
            Element currentElement = (Element)currentNode;
            if (this.xpathNodeSet.contains(currentNode)) {
               this.writer.write("<span class=\"INCLUDED\">");
            } else {
               this.writer.write("<span class=\"EXCLUDED\">");
            }

            this.writer.write("&lt;");
            this.writer.write(currentElement.getTagName());
            this.writer.write("</span>");
            NamedNodeMap attrs = currentElement.getAttributes();
            int attrsLength = attrs.getLength();
            Attr[] attrs2 = new Attr[attrsLength];

            for(int i = 0; i < attrsLength; ++i) {
               attrs2[i] = (Attr)attrs.item(i);
            }

            Arrays.sort(attrs2, ATTR_COMPARE);
            Object[] attrs3 = attrs2;

            for(int i = 0; i < attrsLength; ++i) {
               Attr a = (Attr)attrs3[i];
               boolean included = this.xpathNodeSet.contains(a);
               boolean inclusive = this.inclusiveNamespaces.contains(a.getName());
               if (included) {
                  if (inclusive) {
                     this.writer.write("<span class=\"INCLUDEDINCLUSIVENAMESPACE\">");
                  } else {
                     this.writer.write("<span class=\"INCLUDED\">");
                  }
               } else if (inclusive) {
                  this.writer.write("<span class=\"EXCLUDEDINCLUSIVENAMESPACE\">");
               } else {
                  this.writer.write("<span class=\"EXCLUDED\">");
               }

               this.outputAttrToWriter(a.getNodeName(), a.getNodeValue());
               this.writer.write("</span>");
            }

            if (this.xpathNodeSet.contains(currentNode)) {
               this.writer.write("<span class=\"INCLUDED\">");
            } else {
               this.writer.write("<span class=\"EXCLUDED\">");
            }

            this.writer.write("&gt;");
            this.writer.write("</span>");

            for(Node currentChild = currentNode.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
               this.canonicalizeXPathNodeSet(currentChild);
            }

            if (this.xpathNodeSet.contains(currentNode)) {
               this.writer.write("<span class=\"INCLUDED\">");
            } else {
               this.writer.write("<span class=\"EXCLUDED\">");
            }

            this.writer.write("&lt;/");
            this.writer.write(currentElement.getTagName());
            this.writer.write("&gt;");
            this.writer.write("</span>");
            break;
         case 2:
         case 6:
         case 11:
         case 12:
            throw new XMLSignatureException("empty", new Object[]{"An incorrect node was provided for c14n: " + currentNodeType});
         case 3:
         case 4:
            if (this.xpathNodeSet.contains(currentNode)) {
               this.writer.write("<span class=\"INCLUDED\">");
            } else {
               this.writer.write("<span class=\"EXCLUDED\">");
            }

            this.outputTextToWriter(currentNode.getNodeValue());

            for(Node nextSibling = currentNode.getNextSibling(); nextSibling != null && (nextSibling.getNodeType() == 3 || nextSibling.getNodeType() == 4); nextSibling = nextSibling.getNextSibling()) {
               this.outputTextToWriter(nextSibling.getNodeValue());
            }

            this.writer.write("</span>");
         case 5:
         case 10:
         default:
            break;
         case 7:
            if (this.xpathNodeSet.contains(currentNode)) {
               this.writer.write("<span class=\"INCLUDED\">");
            } else {
               this.writer.write("<span class=\"EXCLUDED\">");
            }

            position = this.getPositionRelativeToDocumentElement(currentNode);
            if (position == 1) {
               this.writer.write("\n");
            }

            this.outputPItoWriter((ProcessingInstruction)currentNode);
            if (position == -1) {
               this.writer.write("\n");
            }

            this.writer.write("</span>");
            break;
         case 8:
            if (this.xpathNodeSet.contains(currentNode)) {
               this.writer.write("<span class=\"INCLUDED\">");
            } else {
               this.writer.write("<span class=\"EXCLUDED\">");
            }

            position = this.getPositionRelativeToDocumentElement(currentNode);
            if (position == 1) {
               this.writer.write("\n");
            }

            this.outputCommentToWriter((Comment)currentNode);
            if (position == -1) {
               this.writer.write("\n");
            }

            this.writer.write("</span>");
            break;
         case 9:
            this.writer.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n<html>\n<head>\n<title>Caninical XML node set</title>\n<style type=\"text/css\">\n<!-- \n.INCLUDED { \n   color: #000000; \n   background-color: \n   #FFFFFF; \n   font-weight: bold; } \n.EXCLUDED { \n   color: #666666; \n   background-color: \n   #999999; } \n.INCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #FFFFFF; \n   font-weight: bold; \n   font-style: italic; } \n.EXCLUDEDINCLUSIVENAMESPACE { \n   color: #0000FF; \n   background-color: #999999; \n   font-style: italic; } \n--> \n</style> \n</head>\n<body bgcolor=\"#999999\">\n<h1>Explanation of the output</h1>\n<p>The following text contains the nodeset of the given Reference before it is canonicalized. There exist four different styles to indicate how a given node is treated.</p>\n<ul>\n<li class=\"INCLUDED\">A node which is in the node set is labeled using the INCLUDED style.</li>\n<li class=\"EXCLUDED\">A node which is <em>NOT</em> in the node set is labeled EXCLUDED style.</li>\n<li class=\"INCLUDEDINCLUSIVENAMESPACE\">A namespace which is in the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n<li class=\"EXCLUDEDINCLUSIVENAMESPACE\">A namespace which is in NOT the node set AND in the InclusiveNamespaces PrefixList is labeled using the INCLUDEDINCLUSIVENAMESPACE style.</li>\n</ul>\n<h1>Output</h1>\n<pre>\n");

            for(Node currentChild = currentNode.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
               this.canonicalizeXPathNodeSet(currentChild);
            }

            this.writer.write("</pre></body></html>");
      }

   }

   private int getPositionRelativeToDocumentElement(Node currentNode) {
      if (currentNode == null) {
         return 0;
      } else {
         Document doc = currentNode.getOwnerDocument();
         if (currentNode.getParentNode() != doc) {
            return 0;
         } else {
            Element documentElement = doc.getDocumentElement();
            if (documentElement == null) {
               return 0;
            } else if (documentElement == currentNode) {
               return 0;
            } else {
               for(Node x = currentNode; x != null; x = x.getNextSibling()) {
                  if (x == documentElement) {
                     return -1;
                  }
               }

               return 1;
            }
         }
      }
   }

   private void outputAttrToWriter(String name, String value) throws IOException {
      this.writer.write(" ");
      this.writer.write(name);
      this.writer.write("=\"");
      int length = value.length();

      for(int i = 0; i < length; ++i) {
         char c = value.charAt(i);
         switch (c) {
            case '\t':
               this.writer.write("&amp;#x9;");
               break;
            case '\n':
               this.writer.write("&amp;#xA;");
               break;
            case '\r':
               this.writer.write("&amp;#xD;");
               break;
            case '"':
               this.writer.write("&amp;quot;");
               break;
            case '&':
               this.writer.write("&amp;amp;");
               break;
            case '<':
               this.writer.write("&amp;lt;");
               break;
            default:
               this.writer.write(c);
         }
      }

      this.writer.write("\"");
   }

   private void outputPItoWriter(ProcessingInstruction currentPI) throws IOException {
      if (currentPI != null) {
         this.writer.write("&lt;?");
         String target = currentPI.getTarget();
         int length = target.length();

         int i;
         for(int i = 0; i < length; ++i) {
            i = target.charAt(i);
            switch (i) {
               case 10:
                  this.writer.write("&para;\n");
                  break;
               case 13:
                  this.writer.write("&amp;#xD;");
                  break;
               case 32:
                  this.writer.write("&middot;");
                  break;
               default:
                  this.writer.write(i);
            }
         }

         String data = currentPI.getData();
         length = data.length();
         if (length > 0) {
            this.writer.write(" ");

            for(i = 0; i < length; ++i) {
               char c = data.charAt(i);
               switch (c) {
                  case '\r':
                     this.writer.write("&amp;#xD;");
                     break;
                  default:
                     this.writer.write(c);
               }
            }
         }

         this.writer.write("?&gt;");
      }
   }

   private void outputCommentToWriter(Comment currentComment) throws IOException {
      if (currentComment != null) {
         this.writer.write("&lt;!--");
         String data = currentComment.getData();
         int length = data.length();

         for(int i = 0; i < length; ++i) {
            char c = data.charAt(i);
            switch (c) {
               case '\n':
                  this.writer.write("&para;\n");
                  break;
               case '\r':
                  this.writer.write("&amp;#xD;");
                  break;
               case ' ':
                  this.writer.write("&middot;");
                  break;
               default:
                  this.writer.write(c);
            }
         }

         this.writer.write("--&gt;");
      }
   }

   private void outputTextToWriter(String text) throws IOException {
      if (text != null) {
         int length = text.length();

         for(int i = 0; i < length; ++i) {
            char c = text.charAt(i);
            switch (c) {
               case '\n':
                  this.writer.write("&para;\n");
                  break;
               case '\r':
                  this.writer.write("&amp;#xD;");
                  break;
               case ' ':
                  this.writer.write("&middot;");
                  break;
               case '&':
                  this.writer.write("&amp;amp;");
                  break;
               case '<':
                  this.writer.write("&amp;lt;");
                  break;
               case '>':
                  this.writer.write("&amp;gt;");
                  break;
               default:
                  this.writer.write(c);
            }
         }

      }
   }
}
