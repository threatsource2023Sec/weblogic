package weblogic.application.naming;

import java.util.Collection;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;

public interface MessageDestinationInfoRegistry {
   MessageDestinationInfo get(String var1);

   Collection getAll();

   void register(MessageDestinationBean[] var1, MessageDestinationDescriptorBean[] var2) throws EnvironmentException;

   public interface MessageDestinationInfo {
      String getMessageDestinationName();

      MessageDestinationBean getMessageDestination();

      MessageDestinationDescriptorBean getMessageDestinationDescriptor();
   }
}
