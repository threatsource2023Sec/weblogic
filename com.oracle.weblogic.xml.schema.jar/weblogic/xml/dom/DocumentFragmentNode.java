package weblogic.xml.dom;

import org.w3c.dom.DocumentFragment;

public final class DocumentFragmentNode extends NodeImpl implements DocumentFragment {
   public DocumentFragmentNode() {
      this.setNodeType((short)11);
   }

   public String getNodeName() {
      return "#document-fragment";
   }

   public static void main(String[] args) throws Exception {
      DocumentFragmentNode n = new DocumentFragmentNode();
      n.appendChild(new TextNode("a text\n"));
      n.appendChild(new TextNode("b text\n"));
      n.appendChild(new TextNode("c text\n"));
      ElementNode e = new ElementNode();
      e.setLocalName("parent");
      e.appendChild(n);
      System.out.println(e);
      DocumentFragmentNode n2 = new DocumentFragmentNode();
      n2.appendChild(new ElementNode((String)null, "a", (String)null));
      n2.appendChild(new ElementNode((String)null, "b", (String)null));
      n2.appendChild(new ElementNode((String)null, "c", (String)null));
      e.appendChild(n2);
      System.out.println(e);
   }
}
