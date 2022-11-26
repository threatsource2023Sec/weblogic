package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.cluster.GroupMessage;
import weblogic.rmi.spi.HostID;
import weblogic.server.GlobalServiceLocator;

public class QuerySessionRequestMessageWrapper implements GroupMessage, Externalizable {
   QuerySessionRequestMessage request;

   public QuerySessionRequestMessageWrapper() {
   }

   public QuerySessionRequestMessageWrapper(QuerySessionRequestMessage request) {
      this.request = request;
   }

   public void execute(HostID sender) {
      ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
      ((ReplicationManager)serviceLocator.getService(ReplicationManager.class, new Annotation[0])).handleQuerySessionRequest(sender, this.request);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.request);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.request = (QuerySessionRequestMessage)in.readObject();
   }
}
