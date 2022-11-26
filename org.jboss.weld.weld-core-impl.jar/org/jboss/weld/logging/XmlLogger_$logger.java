package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.IllegalStateException;

public class XmlLogger_$logger extends DelegatingBasicLogger implements XmlLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = XmlLogger_$logger.class.getName();
   private static final String configurationError = "WELD-001200: Error configuring XML parser";
   private static final String loadError = "WELD-001201: Error loading beans.xml {0}";
   private static final String parsingError = "WELD-001202: Error parsing {0}";
   private static final String multipleAlternatives = "WELD-001203: <alternatives> can only be specified once, but appears multiple times:  {0}";
   private static final String multipleDecorators = "WELD-001204: <decorators> can only be specified once, but is specified multiple times:  {0}";
   private static final String multipleInterceptors = "WELD-001205: <interceptors> can only be specified once, but it is specified multiple times:  {0}";
   private static final String multipleScanning = "WELD-001207: <scan> can only be specified once, but it is specified multiple times:  {0}";
   private static final String xsdValidationError = "WELD-001208: Error when validating {0}@{1} against xsd. {2}";
   private static final String xsdValidationWarning = "WELD-001210: Warning when validating {0}@{1} against xsd. {2}";
   private static final String catchingDebug = "Catching";

   public XmlLogger_$logger(Logger log) {
      super(log);
   }

   protected String configurationError$str() {
      return "WELD-001200: Error configuring XML parser";
   }

   public final IllegalStateException configurationError(Throwable cause) {
      IllegalStateException result = new IllegalStateException(String.format(this.configurationError$str()), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String loadError$str() {
      return "WELD-001201: Error loading beans.xml {0}";
   }

   public final IllegalStateException loadError(Object param1, Throwable cause) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.loadError$str(), param1), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String parsingError$str() {
      return "WELD-001202: Error parsing {0}";
   }

   public final IllegalStateException parsingError(Object param1, Throwable cause) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.parsingError$str(), param1), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleAlternatives$str() {
      return "WELD-001203: <alternatives> can only be specified once, but appears multiple times:  {0}";
   }

   public final DefinitionException multipleAlternatives(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleAlternatives$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleDecorators$str() {
      return "WELD-001204: <decorators> can only be specified once, but is specified multiple times:  {0}";
   }

   public final DefinitionException multipleDecorators(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleDecorators$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleInterceptors$str() {
      return "WELD-001205: <interceptors> can only be specified once, but it is specified multiple times:  {0}";
   }

   public final DefinitionException multipleInterceptors(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleInterceptors$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String multipleScanning$str() {
      return "WELD-001207: <scan> can only be specified once, but it is specified multiple times:  {0}";
   }

   public final DefinitionException multipleScanning(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.multipleScanning$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void xsdValidationError(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.xsdValidationError$str(), param1, param2, param3);
   }

   protected String xsdValidationError$str() {
      return "WELD-001208: Error when validating {0}@{1} against xsd. {2}";
   }

   public final void xsdValidationWarning(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.xsdValidationWarning$str(), param1, param2, param3);
   }

   protected String xsdValidationWarning$str() {
      return "WELD-001210: Warning when validating {0}@{1} against xsd. {2}";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
