package weblogic.connector.extensions;

import java.io.Serializable;
import java.util.Properties;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

public class SuspendableAdapter implements ResourceAdapter, Suspendable, Serializable {
   public void start(BootstrapContext context) throws ResourceAdapterInternalException {
   }

   public void stop() {
   }

   public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
      throw new NotSupportedException();
   }

   public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
   }

   public XAResource[] getXAResources(ActivationSpec[] spec) throws ResourceException {
      throw new NotSupportedException();
   }

   public void suspend(int type, Properties props) throws ResourceException {
   }

   public void suspendInbound(MessageEndpointFactory endptFactory, Properties props) throws ResourceException {
   }

   public void resumeInbound(MessageEndpointFactory endptFactory, Properties props) throws ResourceException {
   }

   public void resume(int type, Properties props) throws ResourceException {
   }

   public boolean supportsSuspend(int type) {
      return false;
   }

   public boolean isSuspended(int type) {
      return false;
   }

   public boolean isInboundSuspended(MessageEndpointFactory endptFactory) throws ResourceException {
      return false;
   }

   public boolean supportsInit() {
      return false;
   }

   public boolean supportsVersioning() {
      return false;
   }

   public void startVersioning(ResourceAdapter ra, Properties props) throws ResourceException {
   }

   public void init(ResourceAdapter ra, Properties props) throws ResourceException {
   }
}
