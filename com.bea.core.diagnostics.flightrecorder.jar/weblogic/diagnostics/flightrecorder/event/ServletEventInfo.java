package weblogic.diagnostics.flightrecorder.event;

public interface ServletEventInfo {
   boolean hasServletName();

   String getUri();

   void setUri(String var1);

   String getServletName();

   void setServletName(String var1);
}
