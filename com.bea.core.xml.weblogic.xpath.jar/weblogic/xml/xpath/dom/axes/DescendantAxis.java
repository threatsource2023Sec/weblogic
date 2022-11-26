package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.dom.iterators.DescendantOrSelfIterator;

public final class DescendantAxis implements Axis {
   public static final Axis INSTANCE = new DescendantAxis();

   private DescendantAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Iterator out = new DescendantOrSelfIterator((Node)ctx.node);
      out.next();
      return out;
   }
}
