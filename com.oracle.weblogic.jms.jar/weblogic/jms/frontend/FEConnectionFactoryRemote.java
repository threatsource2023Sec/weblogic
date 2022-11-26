package weblogic.jms.frontend;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jms.JMSException;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.dispatcher.DispatcherWrapper;

public interface FEConnectionFactoryRemote extends Remote {
   JMSConnection connectionCreate(DispatcherWrapper var1, String var2, String var3) throws JMSException, RemoteException;

   JMSConnection connectionCreate(DispatcherWrapper var1) throws JMSException, RemoteException;

   JMSConnection connectionCreateRequest(FEConnectionCreateRequest var1) throws JMSException, RemoteException;
}
