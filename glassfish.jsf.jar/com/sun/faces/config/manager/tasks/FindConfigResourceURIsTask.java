package com.sun.faces.config.manager.tasks;

import com.sun.faces.spi.ConfigurationResourceProvider;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.Callable;
import javax.servlet.ServletContext;

public class FindConfigResourceURIsTask implements Callable {
   private ConfigurationResourceProvider provider;
   private ServletContext servletContext;

   public FindConfigResourceURIsTask(ConfigurationResourceProvider provider, ServletContext servletContext) {
      this.provider = provider;
      this.servletContext = servletContext;
   }

   public Collection call() throws Exception {
      Collection untypedCollection = this.provider.getResources(this.servletContext);
      Iterator untypedCollectionIterator = untypedCollection.iterator();
      Collection result = Collections.emptyList();
      if (untypedCollectionIterator.hasNext()) {
         Object cur = untypedCollectionIterator.next();
         if (cur instanceof URL) {
            result = new ArrayList(untypedCollection.size());
            ((Collection)result).add(new URI(((URL)cur).toExternalForm()));

            while(untypedCollectionIterator.hasNext()) {
               cur = untypedCollectionIterator.next();
               ((Collection)result).add(new URI(((URL)cur).toExternalForm()));
            }
         } else {
            result = untypedCollection;
         }
      }

      return (Collection)result;
   }
}
