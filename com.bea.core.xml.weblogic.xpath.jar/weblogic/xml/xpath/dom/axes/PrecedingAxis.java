package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.dom.iterators.PrecedingIterator;

public final class PrecedingAxis implements Axis {
   public static final Axis INSTANCE = new PrecedingAxis();

   private PrecedingAxis() {
   }

   public Iterator createIterator(Context ctx) {
      return new PrecedingIterator((Node)ctx.node);
   }
}
