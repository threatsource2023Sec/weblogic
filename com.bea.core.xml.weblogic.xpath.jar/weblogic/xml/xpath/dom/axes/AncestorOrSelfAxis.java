package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.dom.iterators.BaseNodeIterator;

public final class AncestorOrSelfAxis implements Axis {
   public static final Axis INSTANCE = new AncestorOrSelfAxis();

   private AncestorOrSelfAxis() {
   }

   public Iterator createIterator(Context ctx) {
      return new BaseNodeIterator((Node)ctx.node) {
         public Node nextNode() {
            return this.mCurrent == null ? null : this.mCurrent.getParentNode();
         }
      };
   }
}
