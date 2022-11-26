package weblogic.xml.xpath.xmlnode.axes;

import java.util.Iterator;
import weblogic.xml.xmlnode.XMLNode;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;
import weblogic.xml.xpath.xmlnode.XMLNodeContext;

public final class ParentAxis implements Axis {
   public static final Axis INSTANCE = new ParentAxis();

   private ParentAxis() {
   }

   public Iterator createIterator(Context ctx) {
      XMLNode p = ((XMLNode)ctx.node).getParent();
      if (p == null && ctx.node != ((XMLNodeContext)ctx).documentRoot) {
         p = ((XMLNodeContext)ctx).documentRoot;
      }

      return new SingleObjectIterator(p);
   }
}
