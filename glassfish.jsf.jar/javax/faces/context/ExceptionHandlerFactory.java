package javax.faces.context;

import javax.faces.FacesWrapper;

public abstract class ExceptionHandlerFactory implements FacesWrapper {
   private ExceptionHandlerFactory wrapped;

   /** @deprecated */
   @Deprecated
   public ExceptionHandlerFactory() {
   }

   public ExceptionHandlerFactory(ExceptionHandlerFactory wrapped) {
      this.wrapped = wrapped;
   }

   public ExceptionHandlerFactory getWrapped() {
      return this.wrapped;
   }

   public abstract ExceptionHandler getExceptionHandler();
}
