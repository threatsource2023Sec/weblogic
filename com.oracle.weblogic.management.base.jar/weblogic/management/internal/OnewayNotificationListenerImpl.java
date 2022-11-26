package weblogic.management.internal;

import javax.management.NotificationListener;

class OnewayNotificationListenerImpl extends BaseNotificationListenerImpl implements OnewayNotificationListener {
   OnewayNotificationListenerImpl(MBeanProxy proxyArg, NotificationListener listenerArg) {
      super(proxyArg, listenerArg);
   }
}
