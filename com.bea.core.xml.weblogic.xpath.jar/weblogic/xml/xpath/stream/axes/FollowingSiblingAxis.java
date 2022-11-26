package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class FollowingSiblingAxis implements Axis {
   public static final Axis INSTANCE = new FollowingSiblingAxis();
   private static final boolean DEBUG = false;
   private static final int NOT_FOLLOWING = -1;
   private static final int FOLLOWING = 1;

   private FollowingSiblingAxis() {
   }

   public int matchNew(StreamContext ctx) {
      ctx.mStep.setFollowingCeiling(-1);
      return 202;
   }

   public int match(StreamContext ctx) {
      int f = ctx.mStep.getContextNodeDepth();
      int d = ctx.mDepth;
      if (d < f) {
         return 203;
      } else if (d > f) {
         return 202;
      } else {
         switch (ctx.getNodeType()) {
            case 4:
               if (ctx.mStep.getFollowingCeiling() == -1) {
                  ctx.mStep.setFollowingCeiling(1);
                  return 202;
               }
            default:
               return 200;
         }
      }
   }

   public List getNodeset(StreamContext ctx) {
      throw new IllegalStateException();
   }

   public boolean isAllowedInRoot() {
      return true;
   }

   public boolean isAllowedInPredicate() {
      return false;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return "following";
   }
}
