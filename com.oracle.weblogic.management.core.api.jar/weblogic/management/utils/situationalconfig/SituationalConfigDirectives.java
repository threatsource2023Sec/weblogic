package weblogic.management.utils.situationalconfig;

public interface SituationalConfigDirectives {
   String LOADING_POLICY_LAX = "lax";
   String LOADING_POLICY_STRICT = "strict";

   long getExpiration();

   String getLoadingPolicy();
}
