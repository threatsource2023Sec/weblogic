package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;

public final class DocumentRootAxis implements Axis {
   public static final Axis INSTANCE = new DocumentRootAxis();

   private DocumentRootAxis() {
   }

   public Iterator createIterator(Context ctx) {
      Node n = (Node)ctx.node;
      if (((Node)n).getNodeType() != 9) {
         n = ((Node)n).getOwnerDocument();
      }

      return new SingleObjectIterator(n);
   }
}
