package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.dom.iterators.DescendantOrSelfIterator;

public final class DescendantOrSelfAxis implements Axis {
   public static final Axis INSTANCE = new DescendantOrSelfAxis();

   private DescendantOrSelfAxis() {
   }

   public Iterator createIterator(Context ctx) {
      return new DescendantOrSelfIterator((Node)ctx.node);
   }
}
