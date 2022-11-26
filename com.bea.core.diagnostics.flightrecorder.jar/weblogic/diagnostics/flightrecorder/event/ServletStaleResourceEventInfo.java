package weblogic.diagnostics.flightrecorder.event;

public interface ServletStaleResourceEventInfo extends Throttleable {
   String getResource();

   void setResource(String var1);
}
