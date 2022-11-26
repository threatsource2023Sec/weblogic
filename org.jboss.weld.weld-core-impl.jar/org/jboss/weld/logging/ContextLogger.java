package org.jboss.weld.logging;

import javax.enterprise.context.spi.Context;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.logging.annotations.LogMessage;
import org.jboss.logging.annotations.Message;
import org.jboss.logging.annotations.MessageLogger;
import org.jboss.logging.annotations.Message.Format;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.exceptions.DeploymentException;
import org.jboss.weld.exceptions.IllegalArgumentException;
import org.jboss.weld.exceptions.IllegalStateException;

@MessageLogger(
   projectCode = "WELD-"
)
public interface ContextLogger extends WeldLogger {
   ContextLogger LOG = (ContextLogger)Logger.getMessageLogger(ContextLogger.class, Category.CONTEXT.getName());

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 200,
      value = "Looked for {0} and got {1} in {2}",
      format = Format.MESSAGE_FORMAT
   )
   void contextualInstanceFound(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 201,
      value = "Context {0} cleared",
      format = Format.MESSAGE_FORMAT
   )
   void contextCleared(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 202,
      value = "Added {0} with key {1} to {2}",
      format = Format.MESSAGE_FORMAT
   )
   void contextualInstanceAdded(Object var1, Object var2, Object var3);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 203,
      value = "Removed {0} from {1}",
      format = Format.MESSAGE_FORMAT
   )
   void contextualInstanceRemoved(Object var1, Object var2);

   @Message(
      id = 211,
      value = "The delimiter \"{0}\" should not be in the prefix \"{1}\"",
      format = Format.MESSAGE_FORMAT
   )
   IllegalArgumentException delimiterInPrefix(Object var1, Object var2);

   @Message(
      id = 212,
      value = "No contextual specified to retrieve (null)"
   )
   IllegalArgumentException contextualIsNull();

   @Message(
      id = 213,
      value = "No bean store available for {0}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException noBeanStoreAvailable(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 215,
      value = "Bean store {0} is detached",
      format = Format.MESSAGE_FORMAT
   )
   void beanStoreDetached(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 216,
      value = "Updating underlying store with contextual {0} under ID {1}",
      format = Format.MESSAGE_FORMAT
   )
   void updatingStoreWithContextualUnderId(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 217,
      value = "Adding detached contextual {0} under ID {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addingDetachedContextualUnderId(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 218,
      value = "Removed {0} from session {1}",
      format = Format.MESSAGE_FORMAT
   )
   void removedKeyFromSession(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 219,
      value = "Unable to remove {0} from non-existent session",
      format = Format.MESSAGE_FORMAT
   )
   void unableToRemoveKeyFromSession(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 220,
      value = "Added {0} to session {1}",
      format = Format.MESSAGE_FORMAT
   )
   void addedKeyToSession(Object var1, Object var2);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 221,
      value = "Unable to add {0} to session as no session could be obtained",
      format = Format.MESSAGE_FORMAT
   )
   void unableToAddKeyToSession(Object var1);

   @LogMessage(
      level = Level.TRACE
   )
   @Message(
      id = 222,
      value = "Loading bean store {0} map from session {1}",
      format = Format.MESSAGE_FORMAT
   )
   void loadingBeanStoreMapFromSession(Object var1, Object var2);

   @Message(
      id = 223,
      value = "Context.getScope() returned null for {0}",
      format = Format.MESSAGE_FORMAT
   )
   DefinitionException contextHasNullScope(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 224,
      value = "Unable to clear the bean store {0}.",
      format = Format.MESSAGE_FORMAT
   )
   void unableToClearBeanStore(Object var1);

   @LogMessage(
      level = Level.WARN
   )
   @Message(
      id = 225,
      value = "Bean store leak detected during {0} association: {1}",
      format = Format.MESSAGE_FORMAT
   )
   void beanStoreLeakDuringAssociation(Object var1, Object var2);

   @Message(
      id = 226,
      value = "Cannot register additional context for scope: {0}, {1}",
      format = Format.MESSAGE_FORMAT
   )
   DeploymentException cannotRegisterContext(Class var1, Context var2);

   @Message(
      id = 227,
      value = "Bean identifier index inconsistency detected - the distributed container probably does not work with identical applications\nExpected hash: {0}\nCurrent index: {1}",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException beanIdentifierIndexInconsistencyDetected(Object var1, Object var2);

   @LogMessage(
      level = Level.DEBUG
   )
   @Message(
      id = 228,
      value = "Bean store leak detected during {0} association - instances of beans with the following identifiers might not be destroyed correctly: {1}",
      format = Format.MESSAGE_FORMAT
   )
   void beanStoreLeakAffectedBeanIdentifiers(Object var1, Object var2);

   @Message(
      id = 229,
      value = "Contextual reference of {0} is not valid after container {1} shutdown",
      format = Format.MESSAGE_FORMAT
   )
   IllegalStateException contextualReferenceNotValidAfterShutdown(Object var1, Object var2);
}
