package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import javax.enterprise.context.spi.Context;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;

public class ContextLogger_$logger extends DelegatingBasicLogger implements ContextLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ContextLogger_$logger.class.getName();
   private static final String contextualInstanceFound = "WELD-000200: Looked for {0} and got {1} in {2}";
   private static final String contextCleared = "WELD-000201: Context {0} cleared";
   private static final String contextualInstanceAdded = "WELD-000202: Added {0} with key {1} to {2}";
   private static final String contextualInstanceRemoved = "WELD-000203: Removed {0} from {1}";
   private static final String delimiterInPrefix = "WELD-000211: The delimiter \"{0}\" should not be in the prefix \"{1}\"";
   private static final String contextualIsNull = "WELD-000212: No contextual specified to retrieve (null)";
   private static final String noBeanStoreAvailable = "WELD-000213: No bean store available for {0}";
   private static final String beanStoreDetached = "WELD-000215: Bean store {0} is detached";
   private static final String updatingStoreWithContextualUnderId = "WELD-000216: Updating underlying store with contextual {0} under ID {1}";
   private static final String addingDetachedContextualUnderId = "WELD-000217: Adding detached contextual {0} under ID {1}";
   private static final String removedKeyFromSession = "WELD-000218: Removed {0} from session {1}";
   private static final String unableToRemoveKeyFromSession = "WELD-000219: Unable to remove {0} from non-existent session";
   private static final String addedKeyToSession = "WELD-000220: Added {0} to session {1}";
   private static final String unableToAddKeyToSession = "WELD-000221: Unable to add {0} to session as no session could be obtained";
   private static final String loadingBeanStoreMapFromSession = "WELD-000222: Loading bean store {0} map from session {1}";
   private static final String contextHasNullScope = "WELD-000223: Context.getScope() returned null for {0}";
   private static final String unableToClearBeanStore = "WELD-000224: Unable to clear the bean store {0}.";
   private static final String beanStoreLeakDuringAssociation = "WELD-000225: Bean store leak detected during {0} association: {1}";
   private static final String cannotRegisterContext = "WELD-000226: Cannot register additional context for scope: {0}, {1}";
   private static final String beanIdentifierIndexInconsistencyDetected = "WELD-000227: Bean identifier index inconsistency detected - the distributed container probably does not work with identical applications\nExpected hash: {0}\nCurrent index: {1}";
   private static final String beanStoreLeakAffectedBeanIdentifiers = "WELD-000228: Bean store leak detected during {0} association - instances of beans with the following identifiers might not be destroyed correctly: {1}";
   private static final String contextualReferenceNotValidAfterShutdown = "WELD-000229: Contextual reference of {0} is not valid after container {1} shutdown";
   private static final String catchingDebug = "Catching";

   public ContextLogger_$logger(Logger log) {
      super(log);
   }

   public final void contextualInstanceFound(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.contextualInstanceFound$str(), param1, param2, param3);
   }

   protected String contextualInstanceFound$str() {
      return "WELD-000200: Looked for {0} and got {1} in {2}";
   }

   public final void contextCleared(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.contextCleared$str(), param1);
   }

   protected String contextCleared$str() {
      return "WELD-000201: Context {0} cleared";
   }

   public final void contextualInstanceAdded(Object param1, Object param2, Object param3) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.contextualInstanceAdded$str(), param1, param2, param3);
   }

   protected String contextualInstanceAdded$str() {
      return "WELD-000202: Added {0} with key {1} to {2}";
   }

   public final void contextualInstanceRemoved(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.contextualInstanceRemoved$str(), param1, param2);
   }

   protected String contextualInstanceRemoved$str() {
      return "WELD-000203: Removed {0} from {1}";
   }

   protected String delimiterInPrefix$str() {
      return "WELD-000211: The delimiter \"{0}\" should not be in the prefix \"{1}\"";
   }

   public final IllegalArgumentException delimiterInPrefix(Object param1, Object param2) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.delimiterInPrefix$str(), param1, param2));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String contextualIsNull$str() {
      return "WELD-000212: No contextual specified to retrieve (null)";
   }

   public final IllegalArgumentException contextualIsNull() {
      IllegalArgumentException result = new IllegalArgumentException(String.format(this.contextualIsNull$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String noBeanStoreAvailable$str() {
      return "WELD-000213: No bean store available for {0}";
   }

   public final IllegalStateException noBeanStoreAvailable(Object param1) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.noBeanStoreAvailable$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void beanStoreDetached(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.beanStoreDetached$str(), param1);
   }

   protected String beanStoreDetached$str() {
      return "WELD-000215: Bean store {0} is detached";
   }

   public final void updatingStoreWithContextualUnderId(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.updatingStoreWithContextualUnderId$str(), param1, param2);
   }

   protected String updatingStoreWithContextualUnderId$str() {
      return "WELD-000216: Updating underlying store with contextual {0} under ID {1}";
   }

   public final void addingDetachedContextualUnderId(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.addingDetachedContextualUnderId$str(), param1, param2);
   }

   protected String addingDetachedContextualUnderId$str() {
      return "WELD-000217: Adding detached contextual {0} under ID {1}";
   }

   public final void removedKeyFromSession(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.removedKeyFromSession$str(), param1, param2);
   }

   protected String removedKeyFromSession$str() {
      return "WELD-000218: Removed {0} from session {1}";
   }

   public final void unableToRemoveKeyFromSession(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.unableToRemoveKeyFromSession$str(), param1);
   }

   protected String unableToRemoveKeyFromSession$str() {
      return "WELD-000219: Unable to remove {0} from non-existent session";
   }

   public final void addedKeyToSession(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.addedKeyToSession$str(), param1, param2);
   }

   protected String addedKeyToSession$str() {
      return "WELD-000220: Added {0} to session {1}";
   }

   public final void unableToAddKeyToSession(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.unableToAddKeyToSession$str(), param1);
   }

   protected String unableToAddKeyToSession$str() {
      return "WELD-000221: Unable to add {0} to session as no session could be obtained";
   }

   public final void loadingBeanStoreMapFromSession(Object param1, Object param2) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.loadingBeanStoreMapFromSession$str(), param1, param2);
   }

   protected String loadingBeanStoreMapFromSession$str() {
      return "WELD-000222: Loading bean store {0} map from session {1}";
   }

   protected String contextHasNullScope$str() {
      return "WELD-000223: Context.getScope() returned null for {0}";
   }

   public final DefinitionException contextHasNullScope(Object param1) {
      DefinitionException result = new DefinitionException(MessageFormat.format(this.contextHasNullScope$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void unableToClearBeanStore(Object beanStore) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.unableToClearBeanStore$str(), beanStore);
   }

   protected String unableToClearBeanStore$str() {
      return "WELD-000224: Unable to clear the bean store {0}.";
   }

   public final void beanStoreLeakDuringAssociation(Object context, Object info) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.beanStoreLeakDuringAssociation$str(), context, info);
   }

   protected String beanStoreLeakDuringAssociation$str() {
      return "WELD-000225: Bean store leak detected during {0} association: {1}";
   }

   protected String cannotRegisterContext$str() {
      return "WELD-000226: Cannot register additional context for scope: {0}, {1}";
   }

   public final DeploymentException cannotRegisterContext(Class scope, Context context) {
      DeploymentException result = new DeploymentException(MessageFormat.format(this.cannotRegisterContext$str(), scope, context));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String beanIdentifierIndexInconsistencyDetected$str() {
      return "WELD-000227: Bean identifier index inconsistency detected - the distributed container probably does not work with identical applications\nExpected hash: {0}\nCurrent index: {1}";
   }

   public final IllegalStateException beanIdentifierIndexInconsistencyDetected(Object hash, Object index) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.beanIdentifierIndexInconsistencyDetected$str(), hash, index));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void beanStoreLeakAffectedBeanIdentifiers(Object context, Object identifiers) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.beanStoreLeakAffectedBeanIdentifiers$str(), context, identifiers);
   }

   protected String beanStoreLeakAffectedBeanIdentifiers$str() {
      return "WELD-000228: Bean store leak detected during {0} association - instances of beans with the following identifiers might not be destroyed correctly: {1}";
   }

   protected String contextualReferenceNotValidAfterShutdown$str() {
      return "WELD-000229: Contextual reference of {0} is not valid after container {1} shutdown";
   }

   public final IllegalStateException contextualReferenceNotValidAfterShutdown(Object bean, Object contextId) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.contextualReferenceNotValidAfterShutdown$str(), bean, contextId));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void catchingDebug(Throwable throwable) {
      super.log.logf(FQCN, Level.DEBUG, throwable, this.catchingDebug$str(), new Object[0]);
   }

   protected String catchingDebug$str() {
      return "Catching";
   }
}
