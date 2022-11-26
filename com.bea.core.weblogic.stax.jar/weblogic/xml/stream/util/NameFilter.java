package weblogic.xml.stream.util;

import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLEvent;

/** @deprecated */
@Deprecated
public class NameFilter implements ElementFilter {
   protected String name;

   public NameFilter(String name) {
      this.name = name;
   }

   public boolean accept(XMLEvent e) {
      if (!e.hasName()) {
         return false;
      } else {
         return this.name.equals(e.getName().getLocalName());
      }
   }
}
