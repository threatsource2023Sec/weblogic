package weblogic.application.internal;

import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationContextFactory;

public final class ApplicationContextFactoryImpl extends ApplicationContextFactory {
   public ApplicationContext newApplicationContext(String appName) {
      return new ApplicationContextImpl(appName);
   }
}
