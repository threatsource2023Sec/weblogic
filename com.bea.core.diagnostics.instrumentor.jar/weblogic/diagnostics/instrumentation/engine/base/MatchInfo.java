package weblogic.diagnostics.instrumentation.engine.base;

import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfoImpl;

public final class MatchInfo {
   public static final MatchInfo NO_MATCH = new MatchInfo(false, (PointcutHandlingInfo)null);
   public static final MatchInfo SIMPLE_MATCH = new MatchInfo(true, (PointcutHandlingInfo)null);
   public static final MatchInfo PROBABLE_MATCH = new MatchInfo(true, (PointcutHandlingInfo)null);
   private boolean match = false;
   private PointcutHandlingInfo info = null;
   private boolean infoSet = false;

   public MatchInfo() {
   }

   public MatchInfo(boolean match, PointcutHandlingInfo info) {
      this.setMatch(match);
      this.setPointcutHandlingInfo(info);
   }

   public void setMatch(boolean match) {
      this.match = match;
   }

   public boolean isMatch() {
      return this.match;
   }

   public boolean isPointcutHandlingInfoSet() {
      return this.infoSet;
   }

   public void setPointcutHandlingInfo(PointcutHandlingInfo info) {
      this.info = info;
      this.infoSet = true;
   }

   public PointcutHandlingInfo getPointcutHandlingInfo() {
      return this.info;
   }

   public static boolean compareInfo(MatchInfo match1, MatchInfo match2) {
      if (match1 == match2) {
         return true;
      } else if (match1 != null && match2 != null) {
         if (match1.isMatch() != match2.isMatch()) {
            return false;
         } else if (match1 != SIMPLE_MATCH && match2 != SIMPLE_MATCH) {
            return PointcutHandlingInfoImpl.compareInfo(match1.getPointcutHandlingInfo(), match2.getPointcutHandlingInfo());
         } else {
            return true;
         }
      } else {
         return false;
      }
   }
}
