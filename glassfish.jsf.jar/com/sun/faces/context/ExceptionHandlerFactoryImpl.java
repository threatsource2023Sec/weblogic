package com.sun.faces.context;

import com.sun.faces.application.ApplicationAssociate;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import javax.faces.context.FacesContext;

public class ExceptionHandlerFactoryImpl extends ExceptionHandlerFactory {
   private ApplicationAssociate associate;

   public ExceptionHandlerFactoryImpl() {
      super((ExceptionHandlerFactory)null);
   }

   public ExceptionHandler getExceptionHandler() {
      FacesContext fc = FacesContext.getCurrentInstance();
      ApplicationAssociate myAssociate = this.getAssociate(fc);
      ExceptionHandler result = new AjaxNoAjaxExceptionHandler(new AjaxExceptionHandlerImpl(new ExceptionHandlerImpl(Boolean.TRUE)), new ExceptionHandlerImpl(myAssociate != null ? myAssociate.isErrorPagePresent() : Boolean.TRUE));
      return result;
   }

   private ApplicationAssociate getAssociate(FacesContext ctx) {
      if (this.associate == null) {
         this.associate = ApplicationAssociate.getCurrentInstance();
         if (this.associate == null) {
            this.associate = ApplicationAssociate.getInstance(ctx.getExternalContext());
         }
      }

      return this.associate;
   }
}
