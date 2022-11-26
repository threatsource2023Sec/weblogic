package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.dom.iterators.FollowingIterator;

public final class FollowingAxis implements Axis {
   public static final Axis INSTANCE = new FollowingAxis();

   private FollowingAxis() {
   }

   public Iterator createIterator(Context ctx) {
      return new FollowingIterator((Node)ctx.node);
   }
}
