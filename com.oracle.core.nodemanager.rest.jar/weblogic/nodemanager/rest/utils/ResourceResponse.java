package weblogic.nodemanager.rest.utils;

import javax.ws.rs.core.Response;

public class ResourceResponse {
   private Response.Status httpCode;
   private Object response;
   private boolean isSuccess = false;

   public ResourceResponse() {
   }

   public ResourceResponse(Response.Status httpCode, Object response, boolean isSuccess) {
      this.httpCode = httpCode;
      this.response = response;
      this.isSuccess = isSuccess;
   }

   public Response.Status getHttpCode() {
      return this.httpCode;
   }

   public void setHttpCode(Response.Status httpCode) {
      this.httpCode = httpCode;
   }

   public Object getResponse() {
      return this.response;
   }

   public void setMsg(Object response) {
      this.response = response;
   }

   public boolean isSuccess() {
      return this.isSuccess;
   }

   public void setSuccess(boolean isSuccess) {
      this.isSuccess = isSuccess;
   }
}
