package org.apache.xml.security.transforms.implementations;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.xml.security.signature.NodeFilter;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class XPath2NodeFilter implements NodeFilter {
   boolean hasUnionFilter;
   boolean hasSubtractFilter;
   boolean hasIntersectFilter;
   Set unionNodes;
   Set subtractNodes;
   Set intersectNodes;
   int inSubtract = -1;
   int inIntersect = -1;
   int inUnion = -1;

   XPath2NodeFilter(List unionNodes, List subtractNodes, List intersectNodes) {
      this.hasUnionFilter = !unionNodes.isEmpty();
      this.unionNodes = convertNodeListToSet(unionNodes);
      this.hasSubtractFilter = !subtractNodes.isEmpty();
      this.subtractNodes = convertNodeListToSet(subtractNodes);
      this.hasIntersectFilter = !intersectNodes.isEmpty();
      this.intersectNodes = convertNodeListToSet(intersectNodes);
   }

   public int isNodeInclude(Node currentNode) {
      int result = 1;
      if (this.hasSubtractFilter && rooted(currentNode, this.subtractNodes)) {
         result = -1;
      } else if (this.hasIntersectFilter && !rooted(currentNode, this.intersectNodes)) {
         result = 0;
      }

      if (result == 1) {
         return 1;
      } else {
         if (this.hasUnionFilter) {
            if (rooted(currentNode, this.unionNodes)) {
               return 1;
            }

            result = 0;
         }

         return result;
      }
   }

   public int isNodeIncludeDO(Node n, int level) {
      int result = 1;
      if (this.hasSubtractFilter) {
         if (this.inSubtract == -1 || level <= this.inSubtract) {
            if (inList(n, this.subtractNodes)) {
               this.inSubtract = level;
            } else {
               this.inSubtract = -1;
            }
         }

         if (this.inSubtract != -1) {
            result = -1;
         }
      }

      if (result != -1 && this.hasIntersectFilter && (this.inIntersect == -1 || level <= this.inIntersect)) {
         if (!inList(n, this.intersectNodes)) {
            this.inIntersect = -1;
            result = 0;
         } else {
            this.inIntersect = level;
         }
      }

      if (level <= this.inUnion) {
         this.inUnion = -1;
      }

      if (result == 1) {
         return 1;
      } else {
         if (this.hasUnionFilter) {
            if (this.inUnion == -1 && inList(n, this.unionNodes)) {
               this.inUnion = level;
            }

            if (this.inUnion != -1) {
               return 1;
            }

            result = 0;
         }

         return result;
      }
   }

   static boolean rooted(Node currentNode, Set nodeList) {
      if (nodeList.isEmpty()) {
         return false;
      } else if (nodeList.contains(currentNode)) {
         return true;
      } else {
         Iterator var2 = nodeList.iterator();

         Node rootNode;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            rootNode = (Node)var2.next();
         } while(!XMLUtils.isDescendantOrSelf(rootNode, currentNode));

         return true;
      }
   }

   static boolean inList(Node currentNode, Set nodeList) {
      return nodeList.contains(currentNode);
   }

   private static Set convertNodeListToSet(List l) {
      Set result = new HashSet();
      Iterator var2 = l.iterator();

      while(var2.hasNext()) {
         NodeList rootNodes = (NodeList)var2.next();
         int length = rootNodes.getLength();

         for(int i = 0; i < length; ++i) {
            Node rootNode = rootNodes.item(i);
            result.add(rootNode);
         }
      }

      return result;
   }
}
