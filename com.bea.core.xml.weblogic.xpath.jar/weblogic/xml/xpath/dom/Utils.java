package weblogic.xml.xpath.dom;

import java.io.StringWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import weblogic.xml.xpath.dom.iterators.DescendantOrSelfIterator;

public abstract class Utils {
   public static String getNodeStringValue(Node node) {
      if (!(node instanceof Document) && !(node instanceof Element)) {
         if (node instanceof Attr) {
            return ((Attr)node).getValue();
         } else if (node instanceof ProcessingInstruction) {
            return ((ProcessingInstruction)node).getTarget();
         } else if (node instanceof Comment) {
            return ((Comment)node).getData();
         } else if (node instanceof Text) {
            return ((Text)node).getData();
         } else {
            throw new IllegalArgumentException("Unknown node: " + node.getClass() + " " + node);
         }
      } else {
         StringWriter out = new StringWriter();
         DescendantOrSelfIterator iter = new DescendantOrSelfIterator(node);
         iter.next();

         while(iter.hasNext()) {
            Node next = (Node)iter.next();
            switch (next.getNodeType()) {
               case 3:
               case 4:
                  out.write(((CharacterData)next).getData());
            }
         }

         return out.toString();
      }
   }

   public static final boolean isUsable(Node node) {
      if (node == null) {
         throw new IllegalArgumentException("null node");
      } else {
         switch (node.getNodeType()) {
            case 5:
            case 6:
            case 10:
            case 11:
            case 12:
               return false;
            case 7:
            case 8:
            case 9:
            default:
               return true;
         }
      }
   }

   public static final String node2string(Node node) {
      StringWriter out = new StringWriter();
      if (node.getNodeType() != 3) {
         out.write(node.getNodeName());
         if (node.getAttributes() != null) {
            Node name = node.getAttributes().getNamedItem("name");
            if (name != null) {
               out.write(" '" + name.getNodeValue() + "'");
            }
         }
      }

      return out.toString();
   }
}
