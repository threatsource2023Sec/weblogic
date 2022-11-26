package javax.resource.spi;

import javax.resource.ResourceException;

public interface LazyEnlistableConnectionManager {
   void lazyEnlist(ManagedConnection var1) throws ResourceException;
}
