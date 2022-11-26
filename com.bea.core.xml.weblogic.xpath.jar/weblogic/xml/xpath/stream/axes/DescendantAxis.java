package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class DescendantAxis implements Axis {
   public static final Axis INSTANCE = new DescendantAxis();

   private DescendantAxis() {
   }

   public int matchNew(StreamContext ctx) {
      return 202;
   }

   public int match(StreamContext ctx) {
      return ctx.mDepth > ctx.mStep.getContextNodeDepth() ? 200 : 203;
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
      return "descendant";
   }
}
