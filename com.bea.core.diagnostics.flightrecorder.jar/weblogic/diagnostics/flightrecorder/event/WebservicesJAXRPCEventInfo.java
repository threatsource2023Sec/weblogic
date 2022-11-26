package weblogic.diagnostics.flightrecorder.event;

import java.util.ArrayList;

public interface WebservicesJAXRPCEventInfo {
   boolean isSending();

   String getAction();

   void setAction(String var1);

   String getCurrentParty();

   void setCurrentParty(String var1);

   ArrayList getDeferredArguments();

   void setDeferredArguments(ArrayList var1);
}
