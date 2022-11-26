package weblogic.xml.stream.util;

import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLEvent;

/** @deprecated */
@Deprecated
public class NamespaceTypeFilter implements ElementFilter {
   protected int type;
   protected String uri;

   public NamespaceTypeFilter(String uri, int type) {
      this.type = type;
      this.uri = uri;
   }

   public boolean accept(XMLEvent e) {
      if ((e.getType() & this.type) == 0) {
         return false;
      } else if (!e.hasName()) {
         return true;
      } else {
         return this.uri.equals(e.getName().getNamespaceUri());
      }
   }
}
