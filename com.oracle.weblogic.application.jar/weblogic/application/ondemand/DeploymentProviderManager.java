package weblogic.application.ondemand;

import java.util.ArrayList;
import java.util.List;

public enum DeploymentProviderManager {
   instance;

   private List providers = new ArrayList();

   public void addProvider(DeploymentProvider provider) {
      this.providers.add(provider);
   }

   public List getProviders() {
      return this.providers;
   }
}
