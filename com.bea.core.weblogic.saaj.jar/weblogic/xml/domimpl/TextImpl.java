package weblogic.xml.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class TextImpl extends CharacterDataImpl implements Text {
   public TextImpl(DocumentImpl owner_doc, String data) {
      super(owner_doc, data);
   }

   public short getNodeType() {
      return 3;
   }

   public String getNodeName() {
      return "#text";
   }

   public boolean isElementContentWhitespace() {
      throw new AssertionError("UNIMP");
   }

   public String getWholeText() {
      StringBuffer fBufferStr = new StringBuffer();

      Object node;
      for(node = this; isTextNode(((Node)node).getPreviousSibling()); node = ((Node)node).getPreviousSibling()) {
      }

      for(; isTextNode((Node)node); node = ((Node)node).getNextSibling()) {
         if (((Node)node).getNodeValue() != null) {
            fBufferStr.append(((Node)node).getNodeValue());
         }
      }

      return fBufferStr.toString();
   }

   private static boolean isTextNode(Node node) {
      if (node == null) {
         return false;
      } else {
         short nodeTYpe = node.getNodeType();
         return nodeTYpe == 3 || nodeTYpe == 4;
      }
   }

   public Text replaceWholeText(String content) throws DOMException {
      throw new AssertionError("UNIMP");
   }

   public Text splitText(int offset) throws DOMException {
      throw new AssertionError("UNIMP");
   }
}
