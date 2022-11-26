package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.iterators.AncestorIterator;

public final class AncestorOrSelfAxis implements Axis {
   private Interrogator mInterrogator;

   public AncestorOrSelfAxis(Interrogator i) {
      this.mInterrogator = i;
   }

   public Iterator createIterator(Context ctx) {
      return new AncestorIterator(this.mInterrogator, ctx.node);
   }
}
