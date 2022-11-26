package weblogic.jms.bridge;

import javax.jms.Message;
import javax.resource.ResourceException;

public interface TargetConnection extends AdapterConnection {
   void send(Message var1) throws ResourceException;

   void send(GenericMessage var1) throws ResourceException;

   Message createMessage(Message var1) throws ResourceException;

   Message createMessage(GenericMessage var1) throws ResourceException;

   GenericMessage createGenericMessage(Message var1) throws ResourceException;

   GenericMessage createGenericMessage(GenericMessage var1) throws ResourceException;
}
