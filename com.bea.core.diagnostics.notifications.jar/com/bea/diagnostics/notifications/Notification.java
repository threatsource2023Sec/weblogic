package com.bea.diagnostics.notifications;

import java.io.Serializable;
import java.util.List;

public interface Notification extends Serializable {
   NotificationSource getSource();

   void setSource(NotificationSource var1);

   List keyList();

   Object getValue(Object var1);
}
