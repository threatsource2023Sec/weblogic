package org.jboss.weld.resources.spi.helpers;

import java.net.URL;
import java.util.Collection;
import org.jboss.weld.resources.spi.ResourceLoader;

public abstract class ForwardingResourceLoader implements ResourceLoader {
   protected abstract ResourceLoader delegate();

   public Class classForName(String name) {
      return this.delegate().classForName(name);
   }

   public URL getResource(String name) {
      return this.delegate().getResource(name);
   }

   public Collection getResources(String name) {
      return this.delegate().getResources(name);
   }

   public boolean equals(Object obj) {
      return this == obj || this.delegate().equals(obj);
   }

   public String toString() {
      return this.delegate().toString();
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }
}
