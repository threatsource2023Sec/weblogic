package weblogic.xml.domimpl.util;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class EmptyNodeList implements NodeList {
   private static EmptyNodeList INSTANCE = new EmptyNodeList();

   public static EmptyNodeList getInstance() {
      return INSTANCE;
   }

   private EmptyNodeList() {
   }

   public Node item(int index) {
      return null;
   }

   public int getLength() {
      return 0;
   }
}
