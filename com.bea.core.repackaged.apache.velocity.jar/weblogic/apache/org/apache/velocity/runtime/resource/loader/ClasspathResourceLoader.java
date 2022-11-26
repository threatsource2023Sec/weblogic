package weblogic.apache.org.apache.velocity.runtime.resource.loader;

import java.io.InputStream;
import org.apache.commons.collections.ExtendedProperties;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.resource.Resource;

public class ClasspathResourceLoader extends ResourceLoader {
   public void init(ExtendedProperties configuration) {
      super.rsvc.info("ClasspathResourceLoader : initialization starting.");
      super.rsvc.info("ClasspathResourceLoader : initialization complete.");
   }

   public synchronized InputStream getResourceStream(String name) throws ResourceNotFoundException {
      InputStream result = null;
      if (name != null && name.length() != 0) {
         try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            result = classLoader.getResourceAsStream(name);
            return result;
         } catch (Exception var4) {
            throw new ResourceNotFoundException(var4.getMessage());
         }
      } else {
         throw new ResourceNotFoundException("No template name provided");
      }
   }

   public boolean isSourceModified(Resource resource) {
      return false;
   }

   public long getLastModified(Resource resource) {
      return 0L;
   }
}
