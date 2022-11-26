package weblogic.xml.stream.util;

import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLEvent;

/** @deprecated */
@Deprecated
public class TypeFilter implements ElementFilter {
   protected int type;

   public TypeFilter(int type) {
      this.type = type;
   }

   public boolean accept(XMLEvent e) {
      return (e.getType() & this.type) != 0;
   }
}
