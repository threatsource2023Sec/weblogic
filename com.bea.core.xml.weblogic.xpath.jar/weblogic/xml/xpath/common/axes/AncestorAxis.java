package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.iterators.AncestorIterator;

public final class AncestorAxis implements Axis {
   private Interrogator mInterrogator;

   public AncestorAxis(Interrogator i) {
      this.mInterrogator = i;
   }

   public Iterator createIterator(Context ctx) {
      return new AncestorIterator(this.mInterrogator, this.mInterrogator.getParent(ctx.node));
   }
}
