package javax.resource.spi;

import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

public interface ResourceAdapter {
   void start(BootstrapContext var1) throws ResourceAdapterInternalException;

   void stop();

   void endpointActivation(MessageEndpointFactory var1, ActivationSpec var2) throws ResourceException;

   void endpointDeactivation(MessageEndpointFactory var1, ActivationSpec var2);

   XAResource[] getXAResources(ActivationSpec[] var1) throws ResourceException;
}
