package weblogic.xml.dom;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NodeListImpl implements NodeList {
   private ArrayList list = new ArrayList();

   public void add(Node n) {
      this.list.add(n);
   }

   public int getLength() {
      return this.list.size();
   }

   public Node item(int item) {
      return (Node)this.list.get(item);
   }
}
