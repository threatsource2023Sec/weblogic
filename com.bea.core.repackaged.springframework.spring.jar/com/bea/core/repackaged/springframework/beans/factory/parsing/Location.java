package com.bea.core.repackaged.springframework.beans.factory.parsing;

import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class Location {
   private final Resource resource;
   @Nullable
   private final Object source;

   public Location(Resource resource) {
      this(resource, (Object)null);
   }

   public Location(Resource resource, @Nullable Object source) {
      Assert.notNull(resource, (String)"Resource must not be null");
      this.resource = resource;
      this.source = source;
   }

   public Resource getResource() {
      return this.resource;
   }

   @Nullable
   public Object getSource() {
      return this.source;
   }
}
