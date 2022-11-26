package weblogic.jms.bridge;

import javax.jms.ExceptionListener;
import javax.jms.Message;
import javax.resource.ResourceException;
import javax.transaction.xa.XAResource;

public interface AdapterConnection {
   int GENERIC_CONNECTION = 0;
   int SOURCE_CONNECTION = 1;
   int TARGET_CONNECTION = 2;
   int NOTIFICATION_ALL = 0;
   int NOTIFICATION_CONNETION_FAILURE = 1;
   int NOTIFICATION_CONNETION_RECOVERED = 2;
   int NOTIFICATION_DESTINATION_FULL = 3;
   int NOTIFICATION_DESTINATION_AVAILABLE = 4;

   void start() throws ResourceException;

   void close() throws ResourceException;

   void pause() throws ResourceException;

   void resume() throws ResourceException;

   LocalTransaction getLocalTransaction() throws ResourceException;

   void addNotificationListener(NotificationListener var1, int var2) throws ResourceException;

   void removeNotificationListener(NotificationListener var1, int var2) throws ResourceException;

   void associateTransaction(Message var1) throws ResourceException;

   void associateTransaction(GenericMessage var1) throws ResourceException;

   XAResource getXAResource() throws ResourceException;

   AdapterConnectionMetaData getMetaData() throws ResourceException;

   void setExceptionListener(ExceptionListener var1) throws ResourceException;
}
