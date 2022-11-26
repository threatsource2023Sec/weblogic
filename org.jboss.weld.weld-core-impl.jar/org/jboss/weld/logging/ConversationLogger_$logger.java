package org.jboss.weld.logging;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.weld.contexts.BusyConversationException;
import org.jboss.weld.contexts.NonexistentConversationException;

public class ConversationLogger_$logger extends DelegatingBasicLogger implements ConversationLogger, WeldLogger, BasicLogger, Serializable {
   private static final long serialVersionUID = 1L;
   private static final String FQCN = ConversationLogger_$logger.class.getName();
   private static final String cleaningUpConversation = "WELD-000304: Cleaning up conversation {0}";
   private static final String conversationLocked = "WELD-000313: Lock acquired on conversation {0}";
   private static final String conversationUnlocked = "WELD-000314: Lock released on conversation {0}";
   private static final String conversationUnavailable = "WELD-000315: Failed to acquire conversation lock in {0} ms for {1}";
   private static final String illegalConversationUnlockAttempt = "WELD-000316: Attempt to release lock on conversation {0} failed because {1}";
   private static final String promotedTransientConversation = "WELD-000317: Promoted conversation {0} to long-running";
   private static final String demotedLongRunningConversation = "WELD-000318: Returned long-running conversation {0} to transient";
   private static final String cleaningUpTransientConversation = "WELD-000320: Cleaning up transient conversation";
   private static final String noConversationFoundToRestore = "WELD-000321: No conversation found to restore for id {0}";
   private static final String conversationLockTimedout = "WELD-000322: Conversation lock timed out: {0}";
   private static final String foundConversationFromRequest = "WELD-000326: Found conversation id {0} in request parameter";
   private static final String resumingConversation = "WELD-000327: Resuming conversation with id {0}";
   private static final String beginCalledOnLongRunningConversation = "WELD-000328: Attempt to call begin() on a long-running conversation";
   private static final String endCalledOnTransientConversation = "WELD-000329: Attempt to call end() on a transient conversation";
   private static final String conversationIdAlreadyInUse = "WELD-000332: Conversation ID {0} is already in use";
   private static final String mustCallAssociateBeforeActivate = "WELD-000333: Must call associate() before calling activate()";
   private static final String mustCallAssociateBeforeDeactivate = "WELD-000334: Must call associate() before calling deactivate()";
   private static final String contextAlreadyActive = "WELD-000335: Conversation context is already active, most likely it was not cleaned up properly during previous request processing: {0}";
   private static final String contextNotActive = "WELD-000336: Conversation context is not active";
   private static final String conversationNamingSchemeNotFound = "WELD-000337: Unable to find ConversationNamingScheme in the request, this conversation wasn't transient at the start of the request";
   private static final String conversationIdGeneratorNotFound = "WELD-000338: Unable to locate ConversationIdGenerator";
   private static final String mustCallAssociateBeforeGeneratingId = "WELD-000339: A request must be associated with the context in order to generate a conversation id";
   private static final String mustCallAssociateBeforeLoadingKnownConversations = "WELD-000340: A request must be associated with the context in order to load the known conversations";
   private static final String unableToLoadConversations = "WELD-000341: Unable to load the conversations from the associated request - {0}: {1}, request: {2}";
   private static final String endLockedConversation = "WELD-000342: Going to end a locked conversation with id {0}";
   private static final String unableToLoadCurrentConversation = "WELD-000343: Unable to load the current conversation from the associated request - {0}: {1}, request: {2}";
   private static final String catchingDebug = "Catching";

   public ConversationLogger_$logger(Logger log) {
      super(log);
   }

   public final void cleaningUpConversation(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.cleaningUpConversation$str(), param1);
   }

   protected String cleaningUpConversation$str() {
      return "WELD-000304: Cleaning up conversation {0}";
   }

   public final void conversationLocked(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.conversationLocked$str(), param1);
   }

   protected String conversationLocked$str() {
      return "WELD-000313: Lock acquired on conversation {0}";
   }

   public final void conversationUnlocked(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.conversationUnlocked$str(), param1);
   }

   protected String conversationUnlocked$str() {
      return "WELD-000314: Lock released on conversation {0}";
   }

   public final void conversationUnavailable(Object param1, Object param2) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.conversationUnavailable$str(), param1, param2);
   }

   protected String conversationUnavailable$str() {
      return "WELD-000315: Failed to acquire conversation lock in {0} ms for {1}";
   }

   public final void illegalConversationUnlockAttempt(Object param1, Object param2) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.illegalConversationUnlockAttempt$str(), param1, param2);
   }

   protected String illegalConversationUnlockAttempt$str() {
      return "WELD-000316: Attempt to release lock on conversation {0} failed because {1}";
   }

   public final void promotedTransientConversation(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.promotedTransientConversation$str(), param1);
   }

   protected String promotedTransientConversation$str() {
      return "WELD-000317: Promoted conversation {0} to long-running";
   }

   public final void demotedLongRunningConversation(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.demotedLongRunningConversation$str(), param1);
   }

   protected String demotedLongRunningConversation$str() {
      return "WELD-000318: Returned long-running conversation {0} to transient";
   }

   public final void cleaningUpTransientConversation() {
      super.log.logf(FQCN, Level.TRACE, (Throwable)null, this.cleaningUpTransientConversation$str(), new Object[0]);
   }

   protected String cleaningUpTransientConversation$str() {
      return "WELD-000320: Cleaning up transient conversation";
   }

   protected String noConversationFoundToRestore$str() {
      return "WELD-000321: No conversation found to restore for id {0}";
   }

   public final NonexistentConversationException noConversationFoundToRestore(Object param1) {
      NonexistentConversationException result = new NonexistentConversationException(MessageFormat.format(this.noConversationFoundToRestore$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String conversationLockTimedout$str() {
      return "WELD-000322: Conversation lock timed out: {0}";
   }

   public final BusyConversationException conversationLockTimedout(Object param1) {
      BusyConversationException result = new BusyConversationException(MessageFormat.format(this.conversationLockTimedout$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void foundConversationFromRequest(Object param1) {
      super.log.logv(FQCN, Level.TRACE, (Throwable)null, this.foundConversationFromRequest$str(), param1);
   }

   protected String foundConversationFromRequest$str() {
      return "WELD-000326: Found conversation id {0} in request parameter";
   }

   public final void resumingConversation(Object param1) {
      super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.resumingConversation$str(), param1);
   }

   protected String resumingConversation$str() {
      return "WELD-000327: Resuming conversation with id {0}";
   }

   protected String beginCalledOnLongRunningConversation$str() {
      return "WELD-000328: Attempt to call begin() on a long-running conversation";
   }

   public final IllegalStateException beginCalledOnLongRunningConversation() {
      IllegalStateException result = new IllegalStateException(String.format(this.beginCalledOnLongRunningConversation$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String endCalledOnTransientConversation$str() {
      return "WELD-000329: Attempt to call end() on a transient conversation";
   }

   public final IllegalStateException endCalledOnTransientConversation() {
      IllegalStateException result = new IllegalStateException(String.format(this.endCalledOnTransientConversation$str()));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String conversationIdAlreadyInUse$str() {
      return "WELD-000332: Conversation ID {0} is already in use";
   }

   public final IllegalArgumentException conversationIdAlreadyInUse(Object param1) {
      IllegalArgumentException result = new IllegalArgumentException(MessageFormat.format(this.conversationIdAlreadyInUse$str(), param1));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String mustCallAssociateBeforeActivate$str() {
      return "WELD-000333: Must call associate() before calling activate()";
   }

   public final IllegalStateException mustCallAssociateBeforeActivate() {
      IllegalStateException result = new IllegalStateException(this.mustCallAssociateBeforeActivate$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String mustCallAssociateBeforeDeactivate$str() {
      return "WELD-000334: Must call associate() before calling deactivate()";
   }

   public final IllegalStateException mustCallAssociateBeforeDeactivate() {
      IllegalStateException result = new IllegalStateException(this.mustCallAssociateBeforeDeactivate$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void contextAlreadyActive(Object request) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.contextAlreadyActive$str(), request);
   }

   protected String contextAlreadyActive$str() {
      return "WELD-000335: Conversation context is already active, most likely it was not cleaned up properly during previous request processing: {0}";
   }

   protected String contextNotActive$str() {
      return "WELD-000336: Conversation context is not active";
   }

   public final IllegalStateException contextNotActive() {
      IllegalStateException result = new IllegalStateException(this.contextNotActive$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String conversationNamingSchemeNotFound$str() {
      return "WELD-000337: Unable to find ConversationNamingScheme in the request, this conversation wasn't transient at the start of the request";
   }

   public final IllegalStateException conversationNamingSchemeNotFound() {
      IllegalStateException result = new IllegalStateException(this.conversationNamingSchemeNotFound$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String conversationIdGeneratorNotFound$str() {
      return "WELD-000338: Unable to locate ConversationIdGenerator";
   }

   public final IllegalStateException conversationIdGeneratorNotFound() {
      IllegalStateException result = new IllegalStateException(this.conversationIdGeneratorNotFound$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String mustCallAssociateBeforeGeneratingId$str() {
      return "WELD-000339: A request must be associated with the context in order to generate a conversation id";
   }

   public final IllegalStateException mustCallAssociateBeforeGeneratingId() {
      IllegalStateException result = new IllegalStateException(this.mustCallAssociateBeforeGeneratingId$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String mustCallAssociateBeforeLoadingKnownConversations$str() {
      return "WELD-000340: A request must be associated with the context in order to load the known conversations";
   }

   public final IllegalStateException mustCallAssociateBeforeLoadingKnownConversations() {
      IllegalStateException result = new IllegalStateException(this.mustCallAssociateBeforeLoadingKnownConversations$str());
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   protected String unableToLoadConversations$str() {
      return "WELD-000341: Unable to load the conversations from the associated request - {0}: {1}, request: {2}";
   }

   public final IllegalStateException unableToLoadConversations(String attributeName, Object attributeValue, Object request) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToLoadConversations$str(), attributeName, attributeValue, request));
      StackTraceElement[] st = result.getStackTrace();
      result.setStackTrace((StackTraceElement[])Arrays.copyOfRange(st, 1, st.length));
      return result;
   }

   public final void endLockedConversation(String cid) {
      super.log.logv(FQCN, Level.WARN, (Throwable)null, this.endLockedConversation$str(), cid);
   }

   protected String endLockedConversation$str() {
      return "WELD-000342: Going to end a locked conversation with id {0}";
   }

   protected String unableToLoadCurrentConversation$str() {
      return "WELD-000343: Unable to load the current conversation from the associated request - {0}: {1}, request: {2}";
   }

   public final IllegalStateException unableToLoadCurrentConversation(String attributeName, Object attributeValue, Object request) {
      IllegalStateException result = new IllegalStateException(MessageFormat.format(this.unableToLoadCurrentConversation$str(), attributeName, attributeValue, request));
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
