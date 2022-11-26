package com.bea.core.repackaged.aspectj.bridge;

import java.util.List;

public interface IMessageHolder extends IMessageHandler {
   boolean ORGREATER = true;
   boolean EQUAL = false;

   boolean hasAnyMessage(IMessage.Kind var1, boolean var2);

   int numMessages(IMessage.Kind var1, boolean var2);

   IMessage[] getMessages(IMessage.Kind var1, boolean var2);

   List getUnmodifiableListView();

   void clearMessages() throws UnsupportedOperationException;
}
