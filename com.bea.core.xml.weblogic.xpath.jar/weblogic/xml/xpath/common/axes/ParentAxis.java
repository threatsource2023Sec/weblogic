package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;

public final class ParentAxis implements Axis {
   private Interrogator mInterrogator;

   public ParentAxis(Interrogator i) {
      this.mInterrogator = i;
   }

   public Iterator createIterator(Context ctx) {
      return new SingleObjectIterator(this.mInterrogator.getParent(ctx.node));
   }
}
