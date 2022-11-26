package javax.resource.spi;

import javax.resource.ResourceException;

public interface ResourceAdapterAssociation {
   ResourceAdapter getResourceAdapter();

   void setResourceAdapter(ResourceAdapter var1) throws ResourceException;
}
