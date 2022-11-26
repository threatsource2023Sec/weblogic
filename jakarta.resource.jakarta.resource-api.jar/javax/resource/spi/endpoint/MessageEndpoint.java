package javax.resource.spi.endpoint;

import java.lang.reflect.Method;
import javax.resource.ResourceException;

public interface MessageEndpoint {
   void beforeDelivery(Method var1) throws NoSuchMethodException, ResourceException;

   void afterDelivery() throws ResourceException;

   void release();
}
