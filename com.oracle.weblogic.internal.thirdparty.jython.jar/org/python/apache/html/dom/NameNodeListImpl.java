package org.python.apache.html.dom;

import org.python.apache.xerces.dom.DeepNodeListImpl;
import org.python.apache.xerces.dom.ElementImpl;
import org.python.apache.xerces.dom.NodeImpl;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class NameNodeListImpl extends DeepNodeListImpl implements NodeList {
   public NameNodeListImpl(NodeImpl var1, String var2) {
      super(var1, var2);
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
            String var3 = ((ElementImpl)var1).getAttribute("name");
            if (var3.equals("*") || var3.equals(this.tagName)) {
               return var1;
            }
         }
      }

      return null;
   }
}
