package com.sun.faces.context;

import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FacesContextFactoryImpl extends FacesContextFactory {
   public FacesContext getFacesContext(Object sc, Object request, Object response, Lifecycle lifecycle) throws FacesException {
      try {
         Util.parameterNonNull(sc);
         Util.parameterNonNull(request);
         Util.parameterNonNull(response);
         Util.parameterNonNull(lifecycle);
      } catch (Exception var6) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.FACES_CONTEXT_CONSTRUCTION_ERROR"));
      }

      FacesContext ctx = new FacesContextImpl(new ExternalContextImpl((ServletContext)sc, (ServletRequest)request, (ServletResponse)response), lifecycle);
      RequestStateManager.set(ctx, "com.sun.faces.FacesContextImpl", ctx);
      RequestStateManager.set(ctx, "com.sun.faces.ExternalContextImpl", ctx.getExternalContext());
      return ctx;
   }
}
