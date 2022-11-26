package org.jboss.weld.logging;

import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.contexts.BusyConversationException;
import org.jboss.weld.contexts.NonexistentConversationException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface ConversationLogger extends WeldLogger {
   ConversationLogger LOG = (ConversationLogger)Logger.getMessageLogger(ConversationLogger.class, Category.CONVERSATION.getName());

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 304,
      value = "Cleaning up conversation {0}",
      format = Format.MESSAGE_FORMAT
   )
   void cleaningUpConversation(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 313,
      value = "Lock acquired on conversation {0}",
      format = Format.MESSAGE_FORMAT
   )
   void conversationLocked(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 314,
      value = "Lock released on conversation {0}",
      format = Format.MESSAGE_FORMAT
   )
   void conversationUnlocked(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 315,
      value = "Failed to acquire conversation lock in {0} ms for {1}",
      format = Format.MESSAGE_FORMAT
   )
   void conversationUnavailable(Object var1, Object var2);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 316,
      value = "Attempt to release lock on conversation {0} failed because {1}",
      format = Format.MESSAGE_FORMAT
   )
   void illegalConversationUnlockAttempt(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 317,
      value = "Promoted conversation {0} to long-running",
      format = Format.MESSAGE_FORMAT
   )
   void promotedTransientConversation(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 318,
      value = "Returned long-running conversation {0} to transient",
      format = Format.MESSAGE_FORMAT
   )
   void demotedLongRunningConversation(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 320,
      value = "Cleaning up transient conversation"
   )
   void cleaningUpTransientConversation();

   @Message(
      id = 321,
      value = "No conversation found to restore for id {0}",
      format = Format.MESSAGE_FORMAT
   )
   NonexistentConversationException noConversationFoundToRestore(Object var1);

   @Message(
      id = 322,
      value = "Conversation lock timed out: {0}",
      format = Format.MESSAGE_FORMAT
   )
   BusyConversationException conversationLockTimedout(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 326,
      value = "Found conversation id {0} in request parameter",
      format = Format.MESSAGE_FORMAT
   )
   void foundConversationFromRequest(Object var1);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 327,
      value = "Resuming conversation with id {0}",
      format = Format.MESSAGE_FORMAT
   )
   void resumingConversation(Object var1);

   @Message(
      id = 328,
      value = "Attempt to call begin() on a long-running conversation"
   )
   IllegalStateException beginCalledOnLongRunningConversation();

   @Message(
      id = 329,
      value = "Attempt to call end() on a transient conversation"
   )
   IllegalStateException endCalledOnTransientConversation();

   @Message(
      id = 332,
      value = "Conversation ID {0} is already in use",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException conversationIdAlreadyInUse(Object var1);

   @Message(
      id = 333,
      value = "Must call associate() before calling activate()",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException mustCallAssociateBeforeActivate();

   @Message(
      id = 334,
      value = "Must call associate() before calling deactivate()",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException mustCallAssociateBeforeDeactivate();

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 335,
      value = "Conversation context is already active, most likely it was not cleaned up properly during previous request processing: {0}",
      format = Format.MESSAGE_FORMAT
   )
   void contextAlreadyActive(Object var1);

   @Message(
      id = 336,
      value = "Conversation context is not active",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException contextNotActive();

   @Message(
      id = 337,
      value = "Unable to find ConversationNamingScheme in the request, this conversation wasn't transient at the start of the request",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException conversationNamingSchemeNotFound();

   @Message(
      id = 338,
      value = "Unable to locate ConversationIdGenerator",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException conversationIdGeneratorNotFound();

   @Message(
      id = 339,
      value = "A request must be associated with the context in order to generate a conversation id",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException mustCallAssociateBeforeGeneratingId();

   @Message(
      id = 340,
      value = "A request must be associated with the context in order to load the known conversations",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException mustCallAssociateBeforeLoadingKnownConversations();

   @Message(
      id = 341,
      value = "Unable to load the conversations from the associated request - {0}: {1}, request: {2}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToLoadConversations(String var1, Object var2, Object var3);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 342,
      value = "Going to end a locked conversation with id {0}",
      format = Format.MESSAGE_FORMAT
   )
   void endLockedConversation(String var1);

   @Message(
      id = 343,
      value = "Unable to load the current conversation from the associated request - {0}: {1}, request: {2}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException unableToLoadCurrentConversation(String var1, Object var2, Object var3);
}
