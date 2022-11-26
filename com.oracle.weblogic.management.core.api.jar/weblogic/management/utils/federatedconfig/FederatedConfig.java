package weblogic.management.utils.federatedconfig;

import java.io.InputStream;

public interface FederatedConfig {
   InputStream combine(InputStream var1) throws Exception;

   InputStream combine(InputStream var1, String var2, Validator var3) throws Exception;

   InputStream combine(InputStream var1, ExclusiveFederatedConfigLocator var2, Validator var3) throws Exception;

   boolean registerUpdateListener(UpdateListener var1);

   boolean unregisterUpdateListener(UpdateListener var1);

   long getExpirationPeriod();

   public interface Validator {
      void validate(InputStream var1) throws Exception;
   }

   public interface UpdateListener {
      void reportUpdateNeeded();
   }
}
