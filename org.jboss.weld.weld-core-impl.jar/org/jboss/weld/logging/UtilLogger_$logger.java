package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;
import org.jboss.weld.exceptions.WeldException;

public class UtilLogger_$logger extends DelegatingBasicLogger implements UtilLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = UtilLogger_$logger.class.getName();
   private static final String classNotEnum = "WELD-000804: {0} is not an enum";
   private static final String tooManyPostConstructMethods = "WELD-000805: Cannot have more than one post construct method annotated with @PostConstruct for {0}";
   private static final String tooManyPreDestroyMethods = "WELD-000806: Cannot have more than one pre destroy method annotated @PreDestroy for {0}";
   private static final String initializerCannotBeProducer = "WELD-000807: Initializer method cannot be annotated @Produces {0}\n\tat {1}\n  StackTrace:";
   private static final String initializerCannotBeDisposalMethod = "WELD-000808: Initializer method cannot have parameters annotated @Disposes: {0}\n\tat {1}\n  StackTrace:";
   private static final String qualifierOnFinalField = "WELD-000810: Cannot place qualifiers on final fields:  {0}";
   private static final String ambiguousConstructor = "WELD-000812: Cannot determine constructor to use for {0}. Possible constructors {1}";
   private static final String invalidQuantityInjectableFieldsAndInitializerMethods = "WELD-000813: injectableFields and initializerMethods must have the same size.\n\nInjectable Fields:  {0}\nInitializerMethods:  {1}";
   private static final String annotationNotQualifier = "WELD-000814: Annotation {0} is not a qualifier";
   private static final String redundantQualifier = "WELD-000815: Qualifier {0} is already present in the set {1}";
   private static final String unableToFindConstructor = "WELD-000816: Cannot determine constructor to use for {0}";
   private static final String unableToFindBeanDeploymentArchive = "WELD-000817: Unable to find Bean Deployment Archive for {0}";
   private static final String eventTypeNotAllowed = "WELD-000818: Event type {0} is not allowed";
   private static final String typeParameterNotAllowedInEventType = "WELD-000819: Cannot provide an event type parameterized with a type parameter {0}";
   private static final String cannotProxyNonClassType = "WELD-000820: Cannot proxy non-Class Type {0}";
   private static final String accessErrorOnField = "WELD-000824: Error getting field {0} on {1}";
   private static final String annotationValuesInaccessible = "WELD-000826: Cannot access values() on annotation";
   private static final String initializerMethodIsGeneric = "WELD-000827: Initializer method may not be a generic method: {0}\n\tat {1}\n  StackTrace:";
   private static final String resourceSetterInjectionNotAJavabean = "WELD-000833: Resource injection point represents a method which doesn't follow JavaBean conventions {0}";
   private static final String unableToInjectResource = "WELD-000834: Unable to inject resource - most probably incorrect InjectionServices SPI implementation: {0}\n\tat {1}";
   private static final String catchingDebug = "Catching";

   public UtilLogger_$logger(Logger log) {
      super(log);
   }

   protected String classNotEnum$str() {
      return "WELD-000804: {0} is not an enum";
   }

   public final IllegalArgumentException classNotEnum(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.classNotEnum$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String tooManyPostConstructMethods$str() {
      return "WELD-000805: Cannot have more than one post construct method annotated with @PostConstruct for {0}";
   }

   public final DefinitionException tooManyPostConstructMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.tooManyPostConstructMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String tooManyPreDestroyMethods$str() {
      return "WELD-000806: Cannot have more than one pre destroy method annotated @PreDestroy for {0}";
   }

   public final DefinitionException tooManyPreDestroyMethods(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.tooManyPreDestroyMethods$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String initializerCannotBeProducer$str() {
      return "WELD-000807: Initializer method cannot be annotated @Produces {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException initializerCannotBeProducer(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.initializerCannotBeProducer$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String initializerCannotBeDisposalMethod$str() {
      return "WELD-000808: Initializer method cannot have parameters annotated @Disposes: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException initializerCannotBeDisposalMethod(Object param1, Object stackElement) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.initializerCannotBeDisposalMethod$str(), param1, stackElement));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String qualifierOnFinalField$str() {
      return "WELD-000810: Cannot place qualifiers on final fields:  {0}";
   }

   public final DefinitionException qualifierOnFinalField(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.qualifierOnFinalField$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String ambiguousConstructor$str() {
      return "WELD-000812: Cannot determine constructor to use for {0}. Possible constructors {1}";
   }

   public final DefinitionException ambiguousConstructor(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.ambiguousConstructor$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String invalidQuantityInjectableFieldsAndInitializerMethods$str() {
      return "WELD-000813: injectableFields and initializerMethods must have the same size.\n\nInjectable Fields:  {0}\nInitializerMethods:  {1}";
   }

   public final IllegalArgumentException invalidQuantityInjectableFieldsAndInitializerMethods(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.invalidQuantityInjectableFieldsAndInitializerMethods$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String annotationNotQualifier$str() {
      return "WELD-000814: Annotation {0} is not a qualifier";
   }

   public final IllegalArgumentException annotationNotQualifier(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.annotationNotQualifier$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String redundantQualifier$str() {
      return "WELD-000815: Qualifier {0} is already present in the set {1}";
   }

   public final IllegalArgumentException redundantQualifier(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.redundantQualifier$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToFindConstructor$str() {
      return "WELD-000816: Cannot determine constructor to use for {0}";
   }

   public final DefinitionException unableToFindConstructor(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.unableToFindConstructor$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToFindBeanDeploymentArchive$str() {
      return "WELD-000817: Unable to find Bean Deployment Archive for {0}";
   }

   public final IllegalStateException unableToFindBeanDeploymentArchive(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToFindBeanDeploymentArchive$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String eventTypeNotAllowed$str() {
      return "WELD-000818: Event type {0} is not allowed";
   }

   public final IllegalArgumentException eventTypeNotAllowed(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.eventTypeNotAllowed$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String typeParameterNotAllowedInEventType$str() {
      return "WELD-000819: Cannot provide an event type parameterized with a type parameter {0}";
   }

   public final IllegalArgumentException typeParameterNotAllowedInEventType(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.typeParameterNotAllowedInEventType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String cannotProxyNonClassType$str() {
      return "WELD-000820: Cannot proxy non-Class Type {0}";
   }

   public final IllegalArgumentException cannotProxyNonClassType(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.cannotProxyNonClassType$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String accessErrorOnField$str() {
      return "WELD-000824: Error getting field {0} on {1}";
   }

   public final WeldException accessErrorOnField(Object param1, Object param2, Throwable cause) {
      WeldException result = new WeldException(MessageFormat.format(this.accessErrorOnField$str(), param1, param2), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String annotationValuesInaccessible$str() {
      return "WELD-000826: Cannot access values() on annotation";
   }

   public final DeploymentException annotationValuesInaccessible(Throwable cause) {
      DeploymentException result = new DeploymentException(String.format(this.annotationValuesInaccessible$str()), cause);
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String initializerMethodIsGeneric$str() {
      return "WELD-000827: Initializer method may not be a generic method: {0}\n\tat {1}\n  StackTrace:";
   }

   public final DefinitionException initializerMethodIsGeneric(Object param1, Object param2) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.initializerMethodIsGeneric$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String resourceSetterInjectionNotAJavabean$str() {
      return "WELD-000833: Resource injection point represents a method which doesn't follow JavaBean conventions {0}";
   }

   public final DefinitionException resourceSetterInjectionNotAJavabean(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.resourceSetterInjectionNotAJavabean$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void unableToInjectResource(Object member, Object stackElement) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.unableToInjectResource$str(), member, stackElement);
   }

   protected String unableToInjectResource$str() {
      return "WELD-000834: Unable to inject resource - most probably incorrect InjectionServices SPI implementation: {0}\n\tat {1}";
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
