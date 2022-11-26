package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class ChildAxis implements Axis {
   public static final Axis INSTANCE = new ChildAxis();
   private static final boolean DEBUG = false;

   private ChildAxis() {
   }

   public int matchNew(StreamContext ctx) {
      return 202;
   }

   public int match(StreamContext ctx) {
      int d = ctx.mStep.getContextNodeDepth();
      if (ctx.mDepth == d + 1) {
         return 200;
      } else {
         return ctx.mDepth > d + 1 ? 202 : 203;
      }
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
      return "child";
   }
}
