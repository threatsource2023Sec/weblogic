package weblogic.management.provider.internal.federatedconfig;

import java.util.ArrayList;
import weblogic.management.utils.federatedconfig.FederatedConfigLocator;

public class FederatedConfigLocatorManager {
   private static final FederatedConfigLocatorManager SINGLETON = new FederatedConfigLocatorManager();
   private ArrayList locators = new ArrayList();

   private FederatedConfigLocatorManager() {
   }

   public static FederatedConfigLocatorManager getSingleton() {
      return SINGLETON;
   }

   public void addFederatedConfigLocator(FederatedConfigLocator locator) {
      if (locator != null) {
         synchronized(this.locators) {
            this.locators.add(locator);
         }
      }

   }

   public boolean removeFederatedConfigLocator(FederatedConfigLocator locator) {
      if (locator != null) {
         synchronized(this.locators) {
            return this.locators.remove(locator);
         }
      } else {
         return false;
      }
   }

   public Iterable getFederatedConfigLocators() {
      synchronized(this.locators) {
         return (ArrayList)this.locators.clone();
      }
   }
}
