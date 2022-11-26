package weblogic.server;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ServerService extends ServerStates {
   String EJB = "EJB";
   String JMS = "JMS";
   String CONNECTOR = "CONNECTOR";

   String getName();

   String getVersion();

   void start() throws ServiceFailureException;

   void stop() throws ServiceFailureException;

   void halt() throws ServiceFailureException;
}
