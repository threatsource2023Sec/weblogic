package org.jboss.weld.resources.spi;

import java.net.URL;
import java.util.Collection;
import org.jboss.weld.bootstrap.api.Service;

public interface ResourceLoader extends Service {
   String PROPERTY_NAME = ResourceLoader.class.getName();

   Class classForName(String var1);

   URL getResource(String var1);

   Collection getResources(String var1);
}
