package weblogic.entitlement.engine;

import java.util.ArrayList;
import weblogic.security.spi.Resource;

public class ResourceNodeImpl implements ResourceNode {
   private String name;
   private String[] namePath;
   private Resource resource;

   public ResourceNodeImpl(Resource resource) {
      if (resource == null) {
         throw new NullPointerException("null resource");
      } else {
         this.resource = resource;
      }
   }

   public Resource getResource() {
      return this.resource;
   }

   public String getName() {
      if (this.name == null) {
         this.name = this.resource.toString();
      }

      return this.name;
   }

   public ResourceNode getParent() {
      Resource parent = this.resource.getParentResource();
      return parent == null ? null : new ResourceNodeImpl(parent);
   }

   public String[] getNamePathToRoot() {
      if (this.namePath == null) {
         ArrayList list = new ArrayList();
         list.add(this.getName());

         for(Resource r = this.resource.getParentResource(); r != null; r = r.getParentResource()) {
            list.add(r.toString());
         }

         this.namePath = (String[])((String[])list.toArray(new String[list.size()]));
      }

      return this.namePath;
   }
}
