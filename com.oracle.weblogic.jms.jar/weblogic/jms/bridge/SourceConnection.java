package weblogic.jms.bridge;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.ResourceException;

public interface SourceConnection extends AdapterConnection {
   int AUTO_ACKNOWLEDGE = 1;
   int CLIENT_ACKNOWLEDGE = 2;
   int DUPS_OKAY = 3;

   Message receive() throws ResourceException;

   GenericMessage receiveGenericMessage() throws ResourceException;

   Message receive(long var1) throws ResourceException;

   GenericMessage receiveGenericMessage(long var1) throws ResourceException;

   void setMessageListener(MessageListener var1) throws ResourceException;

   void setAcknowledgeMode(int var1) throws ResourceException;

   void recover() throws ResourceException;

   void acknowledge(Message var1) throws ResourceException;
}
