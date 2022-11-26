package weblogic.factories.tgiop;

import weblogic.factories.iiop.iiopEnvironmentFactory;

public class tgiopEnvironmentFactory extends iiopEnvironmentFactory {
   protected boolean isLocalUrl(String providerUrl) {
      return false;
   }
}
