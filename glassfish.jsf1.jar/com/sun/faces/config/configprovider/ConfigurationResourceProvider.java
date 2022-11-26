package com.sun.faces.config.configprovider;

import java.util.Collection;
import javax.servlet.ServletContext;

public interface ConfigurationResourceProvider {
   Collection getResources(ServletContext var1);
}
