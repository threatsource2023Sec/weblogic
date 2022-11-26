package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.WebservicesJAXWSEventInfo;

public class WebservicesJAXWSEventInfoImpl implements WebservicesJAXWSEventInfo {
   private String uri = null;

   public WebservicesJAXWSEventInfoImpl(String uri) {
      this.uri = uri;
   }

   public String getUri() {
      return this.uri;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("URI=");
      sb.append(this.uri);
      return sb.toString();
   }
}
