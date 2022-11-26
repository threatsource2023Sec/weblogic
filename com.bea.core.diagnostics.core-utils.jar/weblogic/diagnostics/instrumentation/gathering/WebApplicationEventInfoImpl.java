package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.WebApplicationEventInfo;

public class WebApplicationEventInfoImpl implements WebApplicationEventInfo {
   private String moduleName = null;

   public WebApplicationEventInfoImpl(String moduleName) {
      this.moduleName = moduleName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("moduleName=");
      sb.append(this.moduleName);
      return sb.toString();
   }
}
