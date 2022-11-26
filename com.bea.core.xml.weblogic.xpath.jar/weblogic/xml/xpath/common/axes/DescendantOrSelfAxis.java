package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.iterators.DescendantOrSelfIterator;

public final class DescendantOrSelfAxis implements Axis {
   private Interrogator mInterrogator;

   public DescendantOrSelfAxis(Interrogator i) {
      this.mInterrogator = i;
   }

   public Iterator createIterator(Context ctx) {
      return new DescendantOrSelfIterator(this.mInterrogator, ctx.node);
   }
}
