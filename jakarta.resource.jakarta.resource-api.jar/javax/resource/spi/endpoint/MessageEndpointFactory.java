package javax.resource.spi.endpoint;

import java.lang.reflect.Method;
import javax.resource.spi.UnavailableException;
import javax.transaction.xa.XAResource;

public interface MessageEndpointFactory {
   MessageEndpoint createEndpoint(XAResource var1) throws UnavailableException;

   MessageEndpoint createEndpoint(XAResource var1, long var2) throws UnavailableException;

   boolean isDeliveryTransacted(Method var1) throws NoSuchMethodException;

   String getActivationName();

   Class getEndpointClass();
}
