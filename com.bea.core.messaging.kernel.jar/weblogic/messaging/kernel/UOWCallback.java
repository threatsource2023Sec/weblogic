package weblogic.messaging.kernel;

import weblogic.messaging.Message;

public interface UOWCallback {
   Message newVisibleMessage(Message var1);

   boolean removeMessage(Message var1);

   boolean sendMessage(Message var1) throws KernelException;

   void recoverMessage(Message var1) throws KernelException;

   void recoveryComplete();

   Message getOneBigMessageReplacee();

   void checkReplacement(Message var1, Message var2);

   void adminDeletedMessage(Message var1);
}
