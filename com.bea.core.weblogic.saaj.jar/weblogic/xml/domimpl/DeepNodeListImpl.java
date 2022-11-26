package weblogic.xml.domimpl;

import java.util.Vector;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class DeepNodeListImpl implements NodeList {
   private final NodeImpl rootNode;
   private final String tagName;
   private Vector nodes;
   private String nsName;
   private boolean enableNS;

   public DeepNodeListImpl(NodeImpl rootNode, String tagName) {
      this.enableNS = false;
      this.rootNode = rootNode;
      this.tagName = tagName;
      this.nodes = new Vector();
   }

   public DeepNodeListImpl(NodeImpl rootNode, String nsName, String localName) {
      this(rootNode, localName);
      this.nsName = nsName != null && !nsName.equals("") ? nsName : null;
      this.enableNS = true;
   }

   public int getLength() {
      this.item(Integer.MAX_VALUE);
      return this.nodes.size();
   }

   public Node item(int index) {
      this.nodes = new Vector();
      if (index < this.nodes.size()) {
         return (Node)this.nodes.elementAt(index);
      } else {
         Object thisNode;
         if (this.nodes.size() == 0) {
            thisNode = this.rootNode;
         } else {
            thisNode = (NodeImpl)((NodeImpl)this.nodes.lastElement());
         }

         while(thisNode != null && index >= this.nodes.size()) {
            thisNode = this.nextMatchingElementAfter((Node)thisNode);
            if (thisNode != null) {
               this.nodes.addElement(thisNode);
            }
         }

         return (Node)thisNode;
      }
   }

   private Node nextMatchingElementAfter(Node current) {
      while(current != null) {
         if (current.hasChildNodes()) {
            current = current.getFirstChild();
         } else {
            Node next;
            if (current != this.rootNode && null != (next = current.getNextSibling())) {
               current = next;
            } else {
               for(next = null; current != this.rootNode; current = current.getParentNode()) {
                  next = current.getNextSibling();
                  if (next != null) {
                     break;
                  }
               }

               current = next;
            }
         }

         if (current != this.rootNode && current != null && current.getNodeType() == 1) {
            if (!this.enableNS) {
               if (this.tagName.equals("*") || ((ElementBase)current).getTagName().equals(this.tagName)) {
                  return current;
               }
            } else {
               ElementBase el;
               if (this.tagName.equals("*")) {
                  if (this.nsName != null && this.nsName.equals("*")) {
                     return current;
                  }

                  el = (ElementBase)current;
                  if (this.nsName == null && el.getNamespaceURI() == null || this.nsName != null && this.nsName.equals(el.getNamespaceURI())) {
                     return current;
                  }
               } else {
                  el = (ElementBase)current;
                  if (el.getLocalName() != null && el.getLocalName().equals(this.tagName)) {
                     if (this.nsName != null && this.nsName.equals("*")) {
                        return current;
                     }

                     if (this.nsName == null && el.getNamespaceURI() == null || this.nsName != null && this.nsName.equals(el.getNamespaceURI())) {
                        return current;
                     }
                  }
               }
            }
         }
      }

      return null;
   }
}
