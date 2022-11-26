package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;

public final class SelfAxis implements Axis {
   public static final Axis INSTANCE = new SelfAxis();

   private SelfAxis() {
   }

   public Iterator createIterator(Context ctx) {
      return new SingleObjectIterator(ctx.node);
   }
}
