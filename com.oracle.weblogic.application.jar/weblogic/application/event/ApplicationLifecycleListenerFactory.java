package weblogic.application.event;

import weblogic.application.ApplicationContext;
import weblogic.application.ApplicationLifecycleListener;

public interface ApplicationLifecycleListenerFactory {
   ApplicationLifecycleListener createListener(ApplicationContext var1);
}
