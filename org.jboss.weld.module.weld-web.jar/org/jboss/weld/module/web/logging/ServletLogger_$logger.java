package org.jboss.weld.module.web.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.logging.WeldLogger;
import org.jboss.weld.module.web.servlet.ServletContextService;

public class ServletLogger_$logger extends DelegatingBasicLogger implements ServletLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ServletLogger_$logger.class.getName();
   private static final String onlyHttpServletLifecycleDefined = "WELD-000707: Non Http-Servlet lifecycle not defined";
   private static final String requestInitialized = "WELD-000708: Initializing request {0}";
   private static final String requestDestroyed = "WELD-000709: Destroying request {0}";
   private static final String cannotInjectObjectOutsideOfServletRequest = "WELD-000710: Cannot inject {0} outside of a Servlet request";
   private static final String webXmlMappingPatternIgnored = "WELD-000711: Context activation pattern {0} ignored as it is overriden by the integrator.";
   private static final String unableToDissociateContext = "WELD-000712: Unable to dissociate context {0} from the storage {1}";
   private static final String cannotInjectServletContext = "WELD-000713: Unable to inject ServletContext. None is associated with {0}, {1}";
   private static final String guardLeak = "WELD-000714: HttpContextLifecycle guard leak detected. The Servlet container is not fully compliant. The value was {0}";
   private static final String guardNotSet = "WELD-000715: HttpContextLifecycle guard not set. The Servlet container is not fully compliant.";
   private static final String servlet2Environment = "WELD-000716: Running in Servlet 2.x environment. Asynchronous request support is disabled.";
   private static final String unableToDeactivateContext = "WELD-000717: Unable to deactivate context {0} when destroying request {1}";
   private static final String noEeModuleDescriptor = "WELD-000718: No EEModuleDescriptor defined for bean archive with ID: {0}. @Initialized and @Destroyed events for ApplicationScoped may be fired twice.";
   private static final String catchingDebug = "Catching";

   public ServletLogger_$logger(Logger log) {
      super(log);
   }

   protected String onlyHttpServletLifecycleDefined$str() {
      return "WELD-000707: Non Http-Servlet lifecycle not defined";
   }

   public final IllegalStateException onlyHttpServletLifecycleDefined() {
      IllegalStateException result = new IllegalStateException(String.format(this.onlyHttpServletLifecycleDefined$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void requestInitialized(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.requestInitialized$str(), param1);
   }

   protected String requestInitialized$str() {
      return "WELD-000708: Initializing request {0}";
   }

   public final void requestDestroyed(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.requestDestroyed$str(), param1);
   }

   protected String requestDestroyed$str() {
      return "WELD-000709: Destroying request {0}";
   }

   protected String cannotInjectObjectOutsideOfServletRequest$str() {
      return "WELD-000710: Cannot inject {0} outside of a Servlet request";
   }

   public final IllegalStateException cannotInjectObjectOutsideOfServletRequest(Object param1, Throwable cause) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.cannotInjectObjectOutsideOfServletRequest$str(), param1), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void webXmlMappingPatternIgnored(String pattern) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.webXmlMappingPatternIgnored$str(), pattern);
   }

   protected String webXmlMappingPatternIgnored$str() {
      return "WELD-000711: Context activation pattern {0} ignored as it is overriden by the integrator.";
   }

   public final void unableToDissociateContext(Object context, Object storage) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.unableToDissociateContext$str(), context, storage);
   }

   protected String unableToDissociateContext$str() {
      return "WELD-000712: Unable to dissociate context {0} from the storage {1}";
   }

   protected String cannotInjectServletContext$str() {
      return "WELD-000713: Unable to inject ServletContext. None is associated with {0}, {1}";
   }

   public final IllegalStateException cannotInjectServletContext(ClassLoader classLoader, ServletContextService service) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.cannotInjectServletContext$str(), classLoader, service));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void guardLeak(int value) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.guardLeak$str(), value);
   }

   protected String guardLeak$str() {
      return "WELD-000714: HttpContextLifecycle guard leak detected. The Servlet container is not fully compliant. The value was {0}";
   }

   public final void guardNotSet() {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.guardNotSet$str(), new Object[0]);
   }

   protected String guardNotSet$str() {
      return "WELD-000715: HttpContextLifecycle guard not set. The Servlet container is not fully compliant.";
   }

   public final void servlet2Environment() {
      super.log.logf(FQCN, Level.INFO, (Throwable)null, this.servlet2Environment$str(), new Object[0]);
   }

   protected String servlet2Environment$str() {
      return "WELD-000716: Running in Servlet 2.x environment. Asynchronous request support is disabled.";
   }

   public final void unableToDeactivateContext(Object context, Object request) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.unableToDeactivateContext$str(), context, request);
   }

   protected String unableToDeactivateContext$str() {
      return "WELD-000717: Unable to deactivate context {0} when destroying request {1}";
   }

   public final void noEeModuleDescriptor(Object beanArchiveId) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.noEeModuleDescriptor$str(), beanArchiveId);
   }

   protected String noEeModuleDescriptor$str() {
      return "WELD-000718: No EEModuleDescriptor defined for bean archive with ID: {0}. @Initialized and @Destroyed events for ApplicationScoped may be fired twice.";
   }

   public final void catchingDebug(Throwable arg0) {
      super.log.logf(FQCN, Level.DEBUG, arg0, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
