package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.EmptyIterator;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;

public final class ParentAxis implements Axis {
   public static final Axis INSTANCE = new ParentAxis();

   private ParentAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Node parent = ((Node)ctx.node).getParentNode();
      return (Iterator)(parent == null ? EmptyIterator.getInstance() : new SingleObjectIterator(parent));
   }
}
