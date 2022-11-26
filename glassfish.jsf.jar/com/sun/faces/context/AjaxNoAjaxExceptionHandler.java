package com.sun.faces.context;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;

public class AjaxNoAjaxExceptionHandler extends ExceptionHandlerWrapper {
   private AjaxExceptionHandlerImpl ajaxExceptionHandlerImpl;

   public AjaxNoAjaxExceptionHandler(AjaxExceptionHandlerImpl ajaxExceptionHandlerImpl, ExceptionHandlerImpl exceptionHandlerImpl) {
      super(exceptionHandlerImpl);
      this.ajaxExceptionHandlerImpl = ajaxExceptionHandlerImpl;
   }

   public ExceptionHandler getWrapped() {
      FacesContext fc = FacesContext.getCurrentInstance();
      return (ExceptionHandler)(null != fc && fc.getPartialViewContext().isAjaxRequest() ? this.ajaxExceptionHandlerImpl : super.getWrapped());
   }
}
