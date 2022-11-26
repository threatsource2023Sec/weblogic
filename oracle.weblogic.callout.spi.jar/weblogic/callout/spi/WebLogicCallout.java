package weblogic.callout.spi;

import java.util.Map;

public interface WebLogicCallout {
   String HOOK_POINT_CHANGE_RECEIVED = "HOOK_POINT_CHANGE_RECEIVED";
   String HOOK_POINT_EXPECTED_CHANGE = "HOOK_POINT_EXPECTED_CHANGE";
   String RESPONSE_IGNORE = "ignore";
   String RESPONSE_ACCEPT = "accept";
   String RESPONSE_CONTINUE = "continue";
   String LOCATION_BOOT = "boot";
   String LOCATION_POLL = "poll";
   String LOCATION_COMMIT = "commit";
   String VALUES_PENDING = "pending";
   String VALUES_ACTIVE = "active";
   String VALUES_CHANGES = "changes";

   String callout(String var1, String var2, Map var3);

   void init(String var1);
}
