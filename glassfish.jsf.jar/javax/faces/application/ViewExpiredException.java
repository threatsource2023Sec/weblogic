package javax.faces.application;

import javax.faces.FacesException;

public class ViewExpiredException extends FacesException {
   private static final long serialVersionUID = 5175808310270035833L;
   private String viewId;

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
