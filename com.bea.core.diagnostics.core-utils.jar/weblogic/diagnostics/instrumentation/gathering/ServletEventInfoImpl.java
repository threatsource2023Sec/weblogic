package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.ServletEventInfo;

public class ServletEventInfoImpl implements ServletEventInfo {
   private String uri = null;
   private String servletName = null;
   private boolean hasServletName = false;

   public ServletEventInfoImpl(String uri) {
      this.uri = uri;
   }

   public ServletEventInfoImpl(String uri, String servletName) {
      this.uri = uri;
      this.servletName = servletName;
      this.hasServletName = true;
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public String getServletName() {
      return this.servletName;
   }

   public void setServletName(String servletName) {
      this.servletName = servletName;
      this.hasServletName = true;
   }

   public boolean hasServletName() {
      return this.hasServletName;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("URI=");
      sb.append(this.uri);
      if (this.hasServletName) {
         sb.append(",ServletName=");
         sb.append(this.servletName);
      }

      return sb.toString();
   }
}
