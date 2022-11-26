package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.EmptyIterator;
import weblogic.xml.xpath.dom.iterators.BaseNodeIterator;

public final class AncestorAxis implements Axis {
   public static final Axis INSTANCE = new AncestorAxis();

   private AncestorAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Node parent = ((Node)ctx.node).getParentNode();
      return (Iterator)(parent == null ? EmptyIterator.getInstance() : new BaseNodeIterator(parent) {
         public Node nextNode() {
            return this.mCurrent == null ? null : this.mCurrent.getParentNode();
         }
      });
   }
}
