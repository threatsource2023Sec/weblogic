package org.python.apache.xerces.dom;

import java.util.ArrayList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DeepNodeListImpl implements NodeList {
   protected NodeImpl rootNode;
   protected String tagName;
   protected int changes;
   protected ArrayList nodes;
   protected String nsName;
   protected boolean enableNS;

   public DeepNodeListImpl(NodeImpl var1, String var2) {
      this.changes = 0;
      this.enableNS = false;
      this.rootNode = var1;
      this.tagName = var2;
      this.nodes = new ArrayList();
   }

   public DeepNodeListImpl(NodeImpl var1, String var2, String var3) {
      this(var1, var3);
      this.nsName = var2 != null && var2.length() != 0 ? var2 : null;
      this.enableNS = true;
   }

   public int getLength() {
      this.item(Integer.MAX_VALUE);
      return this.nodes.size();
   }

   public Node item(int var1) {
      if (this.rootNode.changes() != this.changes) {
         this.nodes = new ArrayList();
         this.changes = this.rootNode.changes();
      }

      int var2 = this.nodes.size();
      if (var1 < var2) {
         return (Node)this.nodes.get(var1);
      } else {
         Object var3;
         if (var2 == 0) {
            var3 = this.rootNode;
         } else {
            var3 = (NodeImpl)this.nodes.get(var2 - 1);
         }

         while(var3 != null && var1 >= this.nodes.size()) {
            var3 = this.nextMatchingElementAfter((Node)var3);
            if (var3 != null) {
               this.nodes.add(var3);
            }
         }

         return (Node)var3;
      }
   }

   protected Node nextMatchingElementAfter(Node var1) {
      while(var1 != null) {
         if (var1.hasChildNodes()) {
            var1 = var1.getFirstChild();
         } else {
            Node var2;
            if (var1 != this.rootNode && null != (var2 = var1.getNextSibling())) {
               var1 = var2;
            } else {
               for(var2 = null; var1 != this.rootNode; var1 = var1.getParentNode()) {
                  var2 = var1.getNextSibling();
                  if (var2 != null) {
                     break;
                  }
               }

               var1 = var2;
            }
         }

         if (var1 != this.rootNode && var1 != null && var1.getNodeType() == 1) {
            if (!this.enableNS) {
               if (this.tagName.equals("*") || ((ElementImpl)var1).getTagName().equals(this.tagName)) {
                  return var1;
               }
            } else {
               ElementImpl var3;
               if (this.tagName.equals("*")) {
                  if (this.nsName != null && this.nsName.equals("*")) {
                     return var1;
                  }

                  var3 = (ElementImpl)var1;
                  if (this.nsName == null && var3.getNamespaceURI() == null || this.nsName != null && this.nsName.equals(var3.getNamespaceURI())) {
                     return var1;
                  }
               } else {
                  var3 = (ElementImpl)var1;
                  if (var3.getLocalName() != null && var3.getLocalName().equals(this.tagName)) {
                     if (this.nsName != null && this.nsName.equals("*")) {
                        return var1;
                     }

                     if (this.nsName == null && var3.getNamespaceURI() == null || this.nsName != null && this.nsName.equals(var3.getNamespaceURI())) {
                        return var1;
                     }
                  }
               }
            }
         }
      }

      return null;
   }
}
