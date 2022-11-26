package weblogic.diagnostics.context;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.utils.PropertyHelper;

class RID {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
   private int children = 0;
   private int[] components = null;
   private String cachedRidStr = null;
   private static final int MAX_UNROOTED_RID_ROOT = 1073741823;
   private static AtomicInteger unrootedRIDCount = new AtomicInteger(1);
   static final int MAX_RID_LENGTH = PropertyHelper.getInteger("weblogic.diagnostics.context.MaxRIDLength", 1024);
   private static int EXTREME_RID_DEPTH = PropertyHelper.getInteger("weblogic.diagnostics.context.ExtremeRIDDepth", 100);
   private static final boolean CHECKING_DEPTH;
   static volatile boolean extremeRIDGrowthSeen;
   static volatile boolean maxRIDGrowthSeen;
   static volatile int deepestRIDSeen;
   static volatile int previouslyReportedDeepestRID;
   private static volatile Object JFRDebugContributor;

   RID(boolean isRooted) {
      if (isRooted) {
         this.components = new int[1];
         this.components[0] = 0;
      } else {
         this.components = new int[2];
         this.components[0] = 1;
         this.components[1] = unrootedRIDCount.getAndIncrement();
         if (this.components[1] > 1073741823) {
            unrootedRIDCount.set(1);
         }
      }

   }

   RID(int[] components) {
      this.components = components;
   }

   RID(int[] components, int children) {
      this.components = components;
      this.children = children;
      if (CHECKING_DEPTH && components.length > EXTREME_RID_DEPTH) {
         extremeRIDGrowthSeen(components.length);
      }

   }

   private static void extremeRIDGrowthSeen(int depth) {
      if (!extremeRIDGrowthSeen) {
         extremeRIDGrowthSeen = true;
         DiagnosticsLogger.logLongRIDValueCreated(depth);
         previouslyReportedDeepestRID = depth;
      }

      if (depth > deepestRIDSeen) {
         deepestRIDSeen = depth;
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("CorrelationManager", "LongRIDSeen", (Throwable)null, JFRDebugContributor);
      }

      maxRIDGrowthSeen();
   }

   private static void maxRIDGrowthSeen() {
      if (!maxRIDGrowthSeen && deepestRIDSeen >= MAX_RID_LENGTH) {
         maxRIDGrowthSeen = true;
         DiagnosticsLogger.logLongMaxRIDLengthValueExceeded(deepestRIDSeen, MAX_RID_LENGTH);
      }

   }

   RID produceChild() {
      int newLength = this.components.length + 1;
      int[] childComponents = Arrays.copyOfRange(this.components, 0, newLength);
      ++this.children;
      if (CHECKING_DEPTH && newLength > EXTREME_RID_DEPTH) {
         extremeRIDGrowthSeen(newLength);
      }

      childComponents[childComponents.length - 1] = this.children;
      return new RID(childComponents);
   }

   int[] produceChildComponents() {
      int newLength = this.components.length + 1;
      int[] childComponents = Arrays.copyOfRange(this.components, 0, newLength);
      ++this.children;
      if (CHECKING_DEPTH && newLength > EXTREME_RID_DEPTH) {
         extremeRIDGrowthSeen(newLength);
      }

      childComponents[childComponents.length - 1] = this.children;
      return childComponents;
   }

   int[] getComponents() {
      return this.components;
   }

   int getChildCount() {
      return this.children;
   }

   int incAndGetChildRIDCount() {
      ++this.children;
      return this.children;
   }

   public String toString() {
      if (this.cachedRidStr != null) {
         return this.cachedRidStr;
      } else if (this.components != null && this.components.length != 0) {
         StringBuffer sb = new StringBuffer();
         int index = false;

         int index;
         for(index = 0; index < this.components.length - 1; ++index) {
            sb.append(this.components[index]);
            sb.append(":");
         }

         sb.append(this.components[index]);
         this.cachedRidStr = sb.toString();
         return this.cachedRidStr;
      } else {
         return null;
      }
   }

   static void setJFRDebugContributor(Object contributor) {
      JFRDebugContributor = contributor;
   }

   static {
      CHECKING_DEPTH = EXTREME_RID_DEPTH > 0;
      extremeRIDGrowthSeen = false;
      maxRIDGrowthSeen = false;
      deepestRIDSeen = 0;
      previouslyReportedDeepestRID = 0;
      JFRDebugContributor = null;
   }
}
