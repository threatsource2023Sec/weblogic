package javax.faces.application;

import javax.faces.FacesException;

public class ViewExpiredException extends FacesException {
   private String viewId = null;

   public ViewExpiredException() {
   }

   public ViewExpiredException(String viewId) {
      this.viewId = viewId;
   }

   public ViewExpiredException(String message, String viewId) {
      super(message);
      this.viewId = viewId;
   }

   public ViewExpiredException(Throwable cause, String viewId) {
      super(cause);
      this.viewId = viewId;
   }

   public ViewExpiredException(String message, Throwable cause, String viewId) {
      super(message, cause);
      this.viewId = viewId;
   }

   public String getViewId() {
      return this.viewId;
   }

   public String getMessage() {
      return this.viewId != null ? "viewId:" + this.viewId + " - " + super.getMessage() : super.getMessage();
   }
}
