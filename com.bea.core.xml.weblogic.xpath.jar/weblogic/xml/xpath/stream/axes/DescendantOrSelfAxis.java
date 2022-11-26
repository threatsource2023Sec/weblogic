package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class DescendantOrSelfAxis implements Axis {
   private static final boolean DEBUG = false;
   public static final Axis INSTANCE = new DescendantOrSelfAxis();

   private DescendantOrSelfAxis() {
   }

   public int matchNew(StreamContext ctx) {
      return 200;
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
      return "descendant-or-self";
   }
}
