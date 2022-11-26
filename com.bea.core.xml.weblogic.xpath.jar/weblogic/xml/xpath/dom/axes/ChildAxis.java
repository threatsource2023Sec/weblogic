package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.EmptyIterator;
import weblogic.xml.xpath.dom.iterators.BaseNodeIterator;

public final class ChildAxis implements Axis {
   public static final Axis INSTANCE = new ChildAxis();

   private ChildAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Node child = ((Node)ctx.node).getFirstChild();
      return (Iterator)(child == null ? EmptyIterator.getInstance() : new BaseNodeIterator(child) {
         public Node nextNode() {
            return this.mCurrent == null ? null : this.mCurrent.getNextSibling();
         }
      });
   }
}
