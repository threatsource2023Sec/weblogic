package weblogic.management.descriptors;

import javax.management.Notification;
import weblogic.management.RemoteNotificationListener;
import weblogic.utils.Debug;

class EJBNotificationListener implements RemoteNotificationListener {
   public void handleNotification(Notification n, Object o) {
      Debug.say("@@@ RECEIVED NOTIFICATON " + n);
      Debug.say("@@@ msg:" + n.getMessage());
      Debug.say("@@@ source:" + n.getSource());
      Debug.say("@@@ type:" + n.getType());
      Debug.say("@@@ userdata:" + o);
   }
}
