package weblogic.connector.extensions;

import java.util.Properties;
import javax.resource.ResourceException;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.endpoint.MessageEndpointFactory;

public interface Suspendable {
   int INBOUND = 1;
   int OUTBOUND = 2;
   int WORK = 4;
   int ALL = 7;
   int SUSPEND = 1;
   int RESUME = 2;

   void suspend(int var1, Properties var2) throws ResourceException;

   void suspendInbound(MessageEndpointFactory var1, Properties var2) throws ResourceException;

   void resumeInbound(MessageEndpointFactory var1, Properties var2) throws ResourceException;

   void resume(int var1, Properties var2) throws ResourceException;

   boolean supportsSuspend(int var1);

   boolean isSuspended(int var1);

   boolean isInboundSuspended(MessageEndpointFactory var1) throws ResourceException;

   boolean supportsInit();

   boolean supportsVersioning();

   void startVersioning(ResourceAdapter var1, Properties var2) throws ResourceException;

   void init(ResourceAdapter var1, Properties var2) throws ResourceException;
}
