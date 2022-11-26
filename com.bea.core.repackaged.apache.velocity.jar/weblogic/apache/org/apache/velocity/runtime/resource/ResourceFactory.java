package weblogic.apache.org.apache.velocity.runtime.resource;

import weblogic.apache.org.apache.velocity.Template;

public class ResourceFactory {
   public static Resource getResource(String resourceName, int resourceType) {
      Resource resource = null;
      switch (resourceType) {
         case 1:
            resource = new Template();
            break;
         case 2:
            resource = new ContentResource();
      }

      return (Resource)resource;
   }
}
