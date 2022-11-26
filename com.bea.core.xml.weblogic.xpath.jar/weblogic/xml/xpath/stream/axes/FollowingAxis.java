package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class FollowingAxis implements Axis {
   public static final Axis INSTANCE = new FollowingAxis();
   private static final boolean DEBUG = false;
   private static final int NOT_FOLLOWING = -99;

   private FollowingAxis() {
   }

   public int matchNew(StreamContext ctx) {
      ctx.mStep.setFollowingCeiling(-99);
      return 202;
   }

   public int match(StreamContext ctx) {
      int f = ctx.mStep.getFollowingCeiling();
      if (f == -99) {
         if (ctx.mDepth == ctx.mStep.getContextNodeDepth()) {
            ctx.mStep.setFollowingCeiling(ctx.mStep.getContextNodeDepth());
         }

         return 202;
      } else {
         switch (ctx.getNodeType()) {
            case 2:
               if (ctx.mDepth < f) {
                  ctx.mStep.setFollowingCeiling(ctx.mDepth);
               }
            case 8:
            case 16:
            case 32:
               return 200;
            case 4:
               return ctx.mDepth < f ? 202 : 200;
            default:
               return 202;
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
