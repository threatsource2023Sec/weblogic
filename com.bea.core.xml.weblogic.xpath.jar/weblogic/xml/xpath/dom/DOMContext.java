package weblogic.xml.xpath.dom;

import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Context;

public final class DOMContext extends Context {
   public DOMContext(Node n) {
      super(n);
   }

   public DOMContext(Node n, int p, int s) {
      super(n, p, s);
   }
}
