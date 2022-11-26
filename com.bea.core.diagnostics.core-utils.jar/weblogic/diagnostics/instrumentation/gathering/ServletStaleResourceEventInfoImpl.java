package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.ServletStaleResourceEventInfo;

public class ServletStaleResourceEventInfoImpl implements ServletStaleResourceEventInfo {
   private String resource = null;

   public ServletStaleResourceEventInfoImpl(String resource) {
      this.resource = resource;
   }

   public String getResource() {
      return this.resource;
   }

   public void setResource(String resource) {
      this.resource = resource;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("resource=");
      sb.append(this.resource);
      return sb.toString();
   }

   public boolean getThrottled() {
      return false;
   }

   public void setThrottled(boolean throttled) {
   }
}
