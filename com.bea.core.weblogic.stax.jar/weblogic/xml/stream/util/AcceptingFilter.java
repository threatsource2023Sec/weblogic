package weblogic.xml.stream.util;

import weblogic.xml.stream.ElementFilter;
import weblogic.xml.stream.XMLEvent;

/** @deprecated */
@Deprecated
public class AcceptingFilter implements ElementFilter {
   public boolean accept(XMLEvent e) {
      return true;
   }
}
