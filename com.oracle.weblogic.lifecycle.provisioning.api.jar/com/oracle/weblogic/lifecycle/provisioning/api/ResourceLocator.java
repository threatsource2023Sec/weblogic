package com.oracle.weblogic.lifecycle.provisioning.api;

import java.net.URI;
import java.net.URL;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ResourceLocator {
   URL getResourceRelativeTo(URI var1, URI var2) throws ResourceLocatorException;

   URL getResourceRelativeTo(URI var1, URI var2, URIResolver var3) throws ResourceLocatorException;

   public interface URIResolver {
      URI resolve(URI var1, URI var2) throws ResourceLocatorException;
   }
}
