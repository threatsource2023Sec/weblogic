package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class EverythingAxis implements Axis {
   private static final boolean DEBUG = false;
   public static final Axis INSTANCE = new EverythingAxis();

   private EverythingAxis() {
   }

   public int matchNew(StreamContext ctx) {
      AttributeAxis.INSTANCE.matchNew(ctx);
      NamespaceAxis.INSTANCE.matchNew(ctx);
      return 200;
   }

   public int match(StreamContext ctx) {
      AttributeAxis.INSTANCE.matchNew(ctx);
      NamespaceAxis.INSTANCE.matchNew(ctx);
      return 200;
   }

   public List getNodeset(StreamContext ctx) {
      throw new IllegalStateException();
   }

   public boolean isAllowedInRoot() {
      return true;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public boolean isAllowedInPredicate() {
      return false;
   }

   public String toString() {
      return "everything";
   }
}
