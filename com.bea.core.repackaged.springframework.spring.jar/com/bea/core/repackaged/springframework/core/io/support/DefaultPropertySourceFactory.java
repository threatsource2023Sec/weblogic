package com.bea.core.repackaged.springframework.core.io.support;

import com.bea.core.repackaged.springframework.core.env.PropertySource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public class DefaultPropertySourceFactory implements PropertySourceFactory {
   public PropertySource createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
      return name != null ? new ResourcePropertySource(name, resource) : new ResourcePropertySource(resource);
   }
}
