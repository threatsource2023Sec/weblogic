package weblogic.diagnostics.debug;

import weblogic.diagnostics.context.Correlation;
import weblogic.diagnostics.context.CorrelationFactory;

final class DebugContextImpl implements DebugContext {
   private static final long UNAVAILABLE = 0L;

   public long getDyeVector() {
      Correlation ctx = CorrelationFactory.findCorrelation();
      return ctx != null ? ctx.getDyeVector() : 0L;
   }
}
