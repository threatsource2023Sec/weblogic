package weblogic.management.workflow;

import java.io.Serializable;
import java.util.Date;
import weblogic.management.workflow.internal.OrchestrationLogger;

public class CommandFailedException extends WorkflowException implements Serializable {
   private static final long serialVersionUID = 1L;
   private final String workflowFullId;
   private final String operationName;
   private final Date operationStartTimestamp;

   public CommandFailedException(String message) {
      super(message);
      this.workflowFullId = null;
      this.operationName = null;
      this.operationStartTimestamp = null;
   }

   public CommandFailedException(String message, Throwable t) {
      super(message, t);
      this.workflowFullId = null;
      this.operationName = null;
      this.operationStartTimestamp = null;
   }

   public CommandFailedException(Date operationStartTimestamp, String workflowFullId, String operationName) {
      super(buildMessage(workflowFullId, operationName, (Throwable)null));
      this.workflowFullId = workflowFullId;
      this.operationName = operationName;
      this.operationStartTimestamp = operationStartTimestamp;
   }

   public CommandFailedException(Date operationStartTimestamp, String workflowFullId, String operationName, Throwable cause) {
      super(buildMessage(workflowFullId, operationName, cause), cause);
      this.workflowFullId = workflowFullId;
      this.operationName = operationName;
      this.operationStartTimestamp = operationStartTimestamp;
   }

   public String getWorkflowFullId() {
      return this.workflowFullId;
   }

   public String getOperationName() {
      return this.operationName;
   }

   public Date getOperationStartTimestamp() {
      return this.operationStartTimestamp;
   }

   protected static String buildMessage(String workflowFullId, String operationName, Throwable cause) {
      String message = null;
      String cleanOperationName = stripMsgcatId(operationName);
      Throwable rootCause = cause;

      for(int i = false; rootCause != null && rootCause.getCause() != null; rootCause = rootCause.getCause()) {
      }

      String causeMessage = null;
      if (rootCause != null) {
         causeMessage = rootCause.getMessage();
         if (causeMessage == null || causeMessage.isEmpty()) {
            causeMessage = rootCause.getClass().toString();
         }
      }

      if (causeMessage != null && !causeMessage.isEmpty()) {
         message = OrchestrationLogger.getCommandFailedWithCauseExceptionMessage(workflowFullId, cleanOperationName, causeMessage);
      } else {
         message = OrchestrationLogger.getCommandFailedExceptionMessage(workflowFullId, cleanOperationName);
      }

      String cleanMessage = stripMsgcatId(message);
      return cleanMessage;
   }

   private static String stripMsgcatId(String message) {
      if (message != null && !message.isEmpty()) {
         String cleanMessage = null;
         int index = message.indexOf(93);
         if (index > 0) {
            ++index;
            cleanMessage = message.substring(index);
         } else {
            cleanMessage = message;
         }

         return cleanMessage;
      } else {
         return message;
      }
   }
}
