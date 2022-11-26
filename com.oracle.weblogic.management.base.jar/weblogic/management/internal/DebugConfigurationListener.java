package weblogic.management.internal;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import weblogic.management.RemoteNotificationListener;
import weblogic.utils.Debug;

public final class DebugConfigurationListener implements RemoteNotificationListener {
   public void handleNotification(Notification n, Object ignore) {
      if (n instanceof AttributeChangeNotification) {
         AttributeChangeNotification acn = (AttributeChangeNotification)n;
         String name = acn.getAttributeName();
         Debug.attributeChangeNotification("weblogic." + acn.getAttributeName(), acn.getOldValue(), acn.getNewValue());
      }

   }
}
