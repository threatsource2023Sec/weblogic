package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;

public final class ChildAxis implements Axis {
   private Interrogator mInterrogator;

   public ChildAxis(Interrogator i) {
      this.mInterrogator = i;
   }

   public Iterator createIterator(Context ctx) {
      return this.mInterrogator.getChildren(ctx.node);
   }
}
