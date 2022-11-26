package weblogic.jms.common;

import java.io.IOException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.security.subject.AbstractSubject;

public interface CDSListListener {
   void listChange(DDMemberInformation[] var1);

   void distributedDestinationGone(DispatcherId var1);

   DistributedDestinationImpl getDistributedDestinationImpl();

   Context getContext() throws NamingException;

   String getJNDIName();

   String getProviderURL();

   String getConfigName();

   AbstractSubject getSubject();

   void setForeign(Hashtable var1) throws NamingException, IOException;

   AbstractSubject getForeignSubject() throws NamingException;

   boolean isLocal();
}
