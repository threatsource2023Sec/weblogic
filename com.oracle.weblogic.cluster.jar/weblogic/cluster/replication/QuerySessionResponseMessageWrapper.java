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

public class QuerySessionResponseMessageWrapper implements GroupMessage, Externalizable {
   private QuerySessionResponseMessage response;
   private HostID target;

   public QuerySessionResponseMessageWrapper() {
   }

   public QuerySessionResponseMessageWrapper(QuerySessionResponseMessage response, HostID target) {
      this.response = response;
      this.target = target;
   }

   public void execute(HostID sender) {
      if (this.target.isLocal()) {
         ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
         ((ReplicationManager)serviceLocator.getService(ReplicationManager.class, new Annotation[0])).handleQuerySessionResponse(sender, this.response);
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.response);
      out.writeObject(this.target);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.response = (QuerySessionResponseMessage)in.readObject();
      this.target = (HostID)in.readObject();
   }
}
