package weblogic.xml.util;

import java.io.IOException;
import java.io.PrintWriter;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class DocumentWriter {
   public static void write(Document document, PrintWriter out) throws IOException {
      printNode(document, out, 0);
   }

   public static void write(Node node, PrintWriter out) throws IOException {
      printNode(node, out, 0);
   }

   private static void printNode(Node node, PrintWriter out, int level) {
      indent(level, out);
      if (node.getNodeType() == 1) {
         out.print("<" + node.getNodeName());
      } else if (node.getNodeType() == 3) {
         out.print(((Text)node).getData());
      } else if (node.getNodeType() == 8) {
         out.print("<!--" + ((Comment)node).getData() + "-->");
         out.print("\n");
      }

      if (node.getNodeType() != 9) {
         NamedNodeMap attrs = node.getAttributes();
         if (attrs != null) {
            int attrLen = attrs.getLength();

            for(int i = 0; i < attrLen; ++i) {
               Node attr = attrs.item(i);
               indent(level + 2, out);
               out.print(attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
            }
         }

         if (node.hasChildNodes() && attrs.getLength() != 0) {
            indent(level, out);
            out.print(">");
         } else if (node.hasChildNodes() && attrs.getLength() == 0) {
            out.print(">");
         }
      }

      NodeList childNodes = node.getChildNodes();
      if (childNodes.getLength() > 0) {
         printNodeList(childNodes, out, level + 2);
         indent(level, out);
         if (node.getNodeType() != 8 && node.getNodeType() != 9) {
            out.print("</" + node.getNodeName() + ">");
         }
      } else {
         indent(level, out);
         if (node.getNodeType() == 1) {
            out.print("/>");
         }
      }

   }

   private static void printNodeList(NodeList list, PrintWriter out, int level) {
      int len = list.getLength();

      int i;
      for(i = 0; i < len; ++i) {
         if (list.item(i).getNodeType() == 8) {
            printNode(list.item(i), out, level);
         }
      }

      for(i = 0; i < len; ++i) {
         if (list.item(i).getNodeType() != 8) {
            printNode(list.item(i), out, level);
         }
      }

   }

   private static void indent(int level, PrintWriter out) {
      out.print("\n");

      for(int i = 0; i < level; ++i) {
         out.print(" ");
      }

   }
}
