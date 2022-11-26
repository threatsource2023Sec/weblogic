package weblogic.diagnostics.context;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;
import weblogic.net.http.HttpContributorRegistrar;
import weblogic.net.http.HttpRequestHeaderContributor;

final class CorrelationHttpRequestHeaderContributor implements HttpRequestHeaderContributor {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
   private static final String DMS_HTTP_REQUEST_HEADER = "ECID-Context";
   private static CorrelationHttpRequestHeaderContributor SINGLETON = null;
   private static Map emptyMap = new HashMap(0);

   private CorrelationHttpRequestHeaderContributor() {
   }

   static synchronized void initialize() {
      if (SINGLETON == null) {
         SINGLETON = new CorrelationHttpRequestHeaderContributor();
         HttpContributorRegistrar.getHttpContributorRegistrar().registerRequestHeaderContributor(SINGLETON);
      }
   }

   public Map getHeadersForOutgoingRequest() {
      CorrelationImpl parent = (CorrelationImpl)CorrelationFactory.findCorrelation();
      if (parent != null && parent.getInheritable()) {
         String wrapString = WrapUtils.wrap(parent);
         HashMap theMap = new HashMap(1);
         theMap.put("ECID-Context", wrapString);
         if (DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("CorrelationHttpRequestHeaderContributor", "contributed header: " + wrapString, (Throwable)null, CorrelationImpl.getDCDebugContributor(parent.getECID(), parent.getRID()));
         }

         return theMap;
      } else {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            JFRDebug.generateDebugEvent("CorrelationHttpRequestHeaderContributor", "no contribution", (Throwable)null, CorrelationImpl.getDCDebugContributor("", ""));
         }

         return emptyMap;
      }
   }
}
