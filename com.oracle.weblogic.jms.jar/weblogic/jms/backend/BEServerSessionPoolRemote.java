package weblogic.jms.backend;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jms.JMSException;
import javax.jms.ServerSession;
import weblogic.messaging.dispatcher.DispatcherId;

public interface BEServerSessionPoolRemote extends Remote {
   ServerSession getServerSession(DispatcherId var1) throws JMSException, RemoteException;

   void close() throws JMSException, RemoteException;
}
