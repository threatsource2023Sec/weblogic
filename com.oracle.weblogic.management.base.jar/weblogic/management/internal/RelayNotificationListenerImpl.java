package weblogic.management.internal;

import javax.management.NotificationListener;

class RelayNotificationListenerImpl extends BaseNotificationListenerImpl implements RelayNotificationListener {
   RelayNotificationListenerImpl(MBeanProxy proxyArg, NotificationListener listenerArg) {
      super(proxyArg, listenerArg);
   }
}
