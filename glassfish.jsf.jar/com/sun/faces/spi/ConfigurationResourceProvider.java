package com.sun.faces.spi;

import java.net.URI;
import java.util.Collection;
import javax.servlet.ServletContext;

public interface ConfigurationResourceProvider {
   Collection getResources(ServletContext var1);

   default boolean validateXml(URI uri, boolean globalValidateXml) {
      return globalValidateXml;
   }
}
