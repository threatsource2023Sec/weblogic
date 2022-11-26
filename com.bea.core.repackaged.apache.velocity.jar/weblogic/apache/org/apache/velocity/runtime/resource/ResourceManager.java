package weblogic.apache.org.apache.velocity.runtime.resource;

import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;

public interface ResourceManager {
   int RESOURCE_TEMPLATE = 1;
   int RESOURCE_CONTENT = 2;

   void initialize(RuntimeServices var1) throws Exception;

   Resource getResource(String var1, int var2, String var3) throws ResourceNotFoundException, ParseErrorException, Exception;

   String getLoaderNameForResource(String var1);
}
