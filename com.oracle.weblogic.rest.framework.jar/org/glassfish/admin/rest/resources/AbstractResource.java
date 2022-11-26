package org.glassfish.admin.rest.resources;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

public abstract class AbstractResource {
   @Context
   protected HttpHeaders requestHeaders;
   @Context
   protected UriInfo uriInfo;
   @Context
   protected SecurityContext securityContext;

   protected UriInfo getUriInfo() {
      return this.uriInfo;
   }
}
