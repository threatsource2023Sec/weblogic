package weblogic.xml.xpath.xmlnode.axes;

import java.util.Iterator;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.iterators.SingleObjectIterator;
import weblogic.xml.xpath.xmlnode.XMLNodeContext;

public final class DocumentRootAxis implements Axis {
   public static final Axis INSTANCE = new DocumentRootAxis();
   private static final boolean DEBUG = false;

   private DocumentRootAxis() {
   }

   public Iterator createIterator(Context ctx) {
      return new SingleObjectIterator(((XMLNodeContext)ctx).documentRoot);
   }
}
