package weblogic.xml.xpath.common.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;

public final class DocumentRootAxis implements Axis {
   private static final boolean DEBUG = false;
   private Interrogator mInterrogator;

   public DocumentRootAxis(Interrogator i) {
      this.mInterrogator = i;
   }

   public Iterator createIterator(Context ctx) {
      Object look = ctx.node;

      Object out;
      do {
         out = look;
         look = this.mInterrogator.getParent(look);
      } while(look != null);

      return new SingleObjectIterator(out);
   }
}
