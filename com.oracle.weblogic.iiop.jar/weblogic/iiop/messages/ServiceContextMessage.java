package weblogic.iiop.messages;

import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.ServiceContextList;

public interface ServiceContextMessage {
   ServiceContext getServiceContext(int var1);

   ServiceContextList getServiceContexts();

   void removeServiceContext(int var1);

   void addServiceContext(ServiceContext var1);
}
