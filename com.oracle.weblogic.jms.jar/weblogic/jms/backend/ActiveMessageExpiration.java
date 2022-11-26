package weblogic.jms.backend;

import java.util.List;

public interface ActiveMessageExpiration {
   int EXPIRATION_POLICY_IGNORE = 0;
   int EXPIRATION_POLICY_DISCARD = 1;
   int EXPIRATION_POLICY_LOGGING = 2;
   int EXPIRATION_POLICY_REDIRECT = 4;

   List getExpirationLoggingJMSHeaders();

   List getExpirationLoggingUserProperties();
}
