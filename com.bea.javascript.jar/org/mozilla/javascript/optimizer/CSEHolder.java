package org.mozilla.javascript.optimizer;

import org.mozilla.javascript.Node;

class CSEHolder {
   Node getPropParent;
   Node getPropChild;

   CSEHolder(Node var1, Node var2) {
      this.getPropParent = var1;
      this.getPropChild = var2;
   }
}
