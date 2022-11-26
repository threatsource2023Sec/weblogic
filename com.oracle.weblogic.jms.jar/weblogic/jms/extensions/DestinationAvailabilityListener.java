package weblogic.jms.extensions;

import java.util.List;

public interface DestinationAvailabilityListener {
   void onDestinationsAvailable(String var1, List var2);

   void onDestinationsUnavailable(String var1, List var2);

   void onFailure(String var1, Exception var2);
}
