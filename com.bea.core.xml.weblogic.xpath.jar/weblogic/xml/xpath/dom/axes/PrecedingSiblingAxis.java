package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.EmptyIterator;
import weblogic.xml.xpath.dom.iterators.BaseNodeIterator;

public final class PrecedingSiblingAxis implements Axis {
   public static final Axis INSTANCE = new PrecedingSiblingAxis();

   private PrecedingSiblingAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Node sibling = ((Node)ctx.node).getPreviousSibling();
      return (Iterator)(sibling == null ? EmptyIterator.getInstance() : new BaseNodeIterator(sibling) {
         public Node nextNode() {
            return this.mCurrent == null ? null : this.mCurrent.getPreviousSibling();
         }
      });
   }
}
