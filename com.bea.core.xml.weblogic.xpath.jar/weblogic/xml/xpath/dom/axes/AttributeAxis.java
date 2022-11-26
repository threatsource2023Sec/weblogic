package weblogic.xml.xpath.dom.axes;

import java.util.Iterator;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.EmptyIterator;
import weblogic.xml.xpath.dom.iterators.AttributeIterator;

public final class AttributeAxis implements Axis {
   public static final Axis INSTANCE = new AttributeAxis();

   private AttributeAxis() {
   }

   public Iterator createIterator(Context ctx) {
      NamedNodeMap atts = ((Node)ctx.node).getAttributes();
      return (Iterator)(atts == null ? EmptyIterator.getInstance() : new AttributeIterator(atts));
   }
}
