package org.apache.xml.security.utils;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HelperNodeList implements NodeList {
   List nodes;
   boolean allNodesMustHaveSameParent;

   public HelperNodeList() {
      this(false);
   }

   public HelperNodeList(boolean allNodesMustHaveSameParent) {
      this.nodes = new ArrayList();
      this.allNodesMustHaveSameParent = false;
      this.allNodesMustHaveSameParent = allNodesMustHaveSameParent;
   }

   public Node item(int index) {
      return (Node)this.nodes.get(index);
   }

   public int getLength() {
      return this.nodes.size();
   }

   public void appendChild(Node node) throws IllegalArgumentException {
      if (this.allNodesMustHaveSameParent && this.getLength() > 0 && this.item(0).getParentNode() != node.getParentNode()) {
         throw new IllegalArgumentException("Nodes have not the same Parent");
      } else {
         this.nodes.add(node);
      }
   }

   public Document getOwnerDocument() {
      return this.getLength() == 0 ? null : XMLUtils.getOwnerDocument(this.item(0));
   }
}
