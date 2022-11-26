package com.sun.faces.context;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.Util;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.ExternalContext;
import javax.faces.context.ExternalContextFactory;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;

public class FacesContextFactoryImpl extends FacesContextFactory {
   private final ExceptionHandlerFactory exceptionHandlerFactory = (ExceptionHandlerFactory)FactoryFinder.getFactory("javax.faces.context.ExceptionHandlerFactory");
   private final ExternalContextFactory externalContextFactory = (ExternalContextFactory)FactoryFinder.getFactory("javax.faces.context.ExternalContextFactory");

   public FacesContextFactoryImpl() {
      super((FacesContextFactory)null);
   }

   public FacesContext getFacesContext(Object sc, Object request, Object response, Lifecycle lifecycle) throws FacesException {
      Util.notNull("sc", sc);
      Util.notNull("request", request);
      Util.notNull("response", response);
      Util.notNull("lifecycle", lifecycle);
      ExternalContext extContext;
      FacesContext ctx = new FacesContextImpl(extContext = this.externalContextFactory.getExternalContext(sc, request, response), lifecycle);
      ctx.setExceptionHandler(this.exceptionHandlerFactory.getExceptionHandler());
      WebConfiguration webConfig = WebConfiguration.getInstance(extContext);
      this.savePerRequestInitParams(ctx, webConfig);
      return ctx;
   }

   private void savePerRequestInitParams(FacesContext context, WebConfiguration webConfig) {
      ExternalContext extContext = context.getExternalContext();
      Map appMap = extContext.getApplicationMap();
      String val = extContext.getInitParameter("javax.faces.HONOR_CURRENT_COMPONENT_ATTRIBUTES");
      boolean setCurrentComponent = Boolean.valueOf(val);
      Map attrs = context.getAttributes();
      attrs.put("javax.faces.ALWAYS_PERFORM_VALIDATION_WHEN_REQUIRED_IS_TRUE", webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.AlwaysPerformValidationWhenRequiredTrue) ? Boolean.TRUE : Boolean.FALSE);
      attrs.put("javax.faces.HONOR_CURRENT_COMPONENT_ATTRIBUTES", setCurrentComponent ? Boolean.TRUE : Boolean.FALSE);
      attrs.put(WebConfiguration.BooleanWebContextInitParameter.PartialStateSaving, webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.PartialStateSaving) ? Boolean.TRUE : Boolean.FALSE);
      attrs.put(WebConfiguration.BooleanWebContextInitParameter.ForceAlwaysWriteFlashCookie, webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ForceAlwaysWriteFlashCookie) ? Boolean.TRUE : Boolean.FALSE);
      attrs.put(WebConfiguration.BooleanWebContextInitParameter.ViewRootPhaseListenerQueuesException.getQualifiedName(), webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ViewRootPhaseListenerQueuesException) ? Boolean.TRUE : Boolean.FALSE);
      attrs.put(WebConfiguration.BooleanWebContextInitParameter.EnableValidateWholeBean.getQualifiedName(), webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableValidateWholeBean) ? Boolean.TRUE : Boolean.FALSE);
      Object nonDefaultResourceResolver = extContext.getApplicationMap().get("com.sun.faces.NDRRPN");
      if (null != nonDefaultResourceResolver) {
         attrs.put("com.sun.faces.NDRRPN", nonDefaultResourceResolver);
      }

      String facesConfigVersion = "" + appMap.get("com.sun.faces.facesConfigVersion");
      attrs.put("com.sun.faces.facesConfigVersion", facesConfigVersion);
   }
}
