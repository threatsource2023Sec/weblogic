package com.sun.faces.context;

import com.sun.faces.util.Util;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ExternalContextFactoryImpl extends ExternalContextFactory {
   public static final String DEFAULT_EXTERNAL_CONTEXT_KEY = ExternalContextFactoryImpl.class.getName() + "_KEY";

   public ExternalContextFactoryImpl() {
      super((ExternalContextFactory)null);
   }

   public ExternalContext getExternalContext(Object servletContext, Object request, Object response) throws FacesException {
      Util.notNull("servletContext", servletContext);
      Util.notNull("request", request);
      Util.notNull("response", response);
      ExternalContext extContext = new ExternalContextImpl((ServletContext)servletContext, (ServletRequest)request, (ServletResponse)response);
      if (request instanceof ServletRequest) {
         ((ServletRequest)request).setAttribute(DEFAULT_EXTERNAL_CONTEXT_KEY, extContext);
      }

      return extContext;
   }
}
