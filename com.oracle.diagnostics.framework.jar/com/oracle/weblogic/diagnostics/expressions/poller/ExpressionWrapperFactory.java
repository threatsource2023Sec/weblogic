package com.oracle.weblogic.diagnostics.expressions.poller;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.inject.Inject;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;

@Service
@PerLookup
public class ExpressionWrapperFactory {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionPoller");
   @Inject
   private ServiceLocator locator;
   private Map wrappersMap = new HashMap();

   public ExpressionWrapper findOrCreateWrapper(String subExpression, Annotation... qualifiers) {
      ExpressionWrapper wrapper = (ExpressionWrapper)this.wrappersMap.get(subExpression);
      if (wrapper == null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Wrapper(" + this + "): Creating a NEW ExpressionWrapper for " + subExpression + " with qualifiers " + Arrays.toString(qualifiers));
         }

         wrapper = new ExpressionWrapper(subExpression, qualifiers);
         this.locator.inject(wrapper);
         this.locator.postConstruct(wrapper);
         this.wrappersMap.put(subExpression, wrapper);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Wrapper(" + this + "): Using EXISTING ExpressionWrapper for " + subExpression);
      }

      return wrapper;
   }

   public void destroy() {
      Iterator wrappersIt = this.wrappersMap.entrySet().iterator();

      while(wrappersIt.hasNext()) {
         Map.Entry entry = (Map.Entry)wrappersIt.next();
         ExpressionWrapper wrapper = (ExpressionWrapper)entry.getValue();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Destroying expression wrapper for " + wrapper.getKey());
         }

         this.locator.preDestroy(wrapper);
         wrappersIt.remove();
      }

   }
}
