package org.python.apache.xerces.dom;

import java.io.Serializable;

class NodeListCache implements Serializable {
   private static final long serialVersionUID = -7927529254918631002L;
   int fLength = -1;
   int fChildIndex = -1;
   ChildNode fChild;
   ParentNode fOwner;
   NodeListCache next;

   NodeListCache(ParentNode var1) {
      this.fOwner = var1;
   }
}
