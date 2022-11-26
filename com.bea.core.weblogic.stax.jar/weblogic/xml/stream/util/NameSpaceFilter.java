package weblogic.xml.stream.util;

import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLEvent;

/** @deprecated */
@Deprecated
public class NameSpaceFilter implements ElementFilter {
   protected String uri;

   public NameSpaceFilter(String uri) {
      this.uri = uri;
   }

   public boolean accept(XMLEvent e) {
      if (!e.hasName()) {
         return false;
      } else {
         return this.uri.equals(e.getName().getNamespaceUri());
      }
   }
}
