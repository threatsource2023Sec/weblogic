package org.apache.xml.security.c14n.implementations;

import org.w3c.dom.Attr;

class NameSpaceSymbEntry implements Cloneable {
   String prefix;
   String uri;
   String lastrendered = null;
   boolean rendered = false;
   Attr n;

   NameSpaceSymbEntry(String name, Attr n, boolean rendered, String prefix) {
      this.uri = name;
      this.rendered = rendered;
      this.n = n;
      this.prefix = prefix;
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }
}
