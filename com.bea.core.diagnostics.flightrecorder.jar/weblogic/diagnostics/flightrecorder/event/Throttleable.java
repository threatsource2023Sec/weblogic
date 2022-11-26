package weblogic.diagnostics.flightrecorder.event;

public interface Throttleable {
   boolean getThrottled();

   void setThrottled(boolean var1);
}
