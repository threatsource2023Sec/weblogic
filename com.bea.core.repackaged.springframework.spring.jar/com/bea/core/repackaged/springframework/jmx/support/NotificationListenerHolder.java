package com.bea.core.repackaged.springframework.jmx.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

public class NotificationListenerHolder {
   @Nullable
   private NotificationListener notificationListener;
   @Nullable
   private NotificationFilter notificationFilter;
   @Nullable
   private Object handback;
   @Nullable
   protected Set mappedObjectNames;

   public void setNotificationListener(@Nullable NotificationListener notificationListener) {
      this.notificationListener = notificationListener;
   }

   @Nullable
   public NotificationListener getNotificationListener() {
      return this.notificationListener;
   }

   public void setNotificationFilter(@Nullable NotificationFilter notificationFilter) {
      this.notificationFilter = notificationFilter;
   }

   @Nullable
   public NotificationFilter getNotificationFilter() {
      return this.notificationFilter;
   }

   public void setHandback(@Nullable Object handback) {
      this.handback = handback;
   }

   @Nullable
   public Object getHandback() {
      return this.handback;
   }

   public void setMappedObjectName(@Nullable Object mappedObjectName) {
      this.mappedObjectNames = mappedObjectName != null ? new LinkedHashSet(Collections.singleton(mappedObjectName)) : null;
   }

   public void setMappedObjectNames(Object... mappedObjectNames) {
      this.mappedObjectNames = new LinkedHashSet(Arrays.asList(mappedObjectNames));
   }

   @Nullable
   public ObjectName[] getResolvedObjectNames() throws MalformedObjectNameException {
      if (this.mappedObjectNames == null) {
         return null;
      } else {
         ObjectName[] resolved = new ObjectName[this.mappedObjectNames.size()];
         int i = 0;

         for(Iterator var3 = this.mappedObjectNames.iterator(); var3.hasNext(); ++i) {
            Object objectName = var3.next();
            resolved[i] = ObjectNameManager.getInstance(objectName);
         }

         return resolved;
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof NotificationListenerHolder)) {
         return false;
      } else {
         NotificationListenerHolder otherNlh = (NotificationListenerHolder)other;
         return ObjectUtils.nullSafeEquals(this.notificationListener, otherNlh.notificationListener) && ObjectUtils.nullSafeEquals(this.notificationFilter, otherNlh.notificationFilter) && ObjectUtils.nullSafeEquals(this.handback, otherNlh.handback) && ObjectUtils.nullSafeEquals(this.mappedObjectNames, otherNlh.mappedObjectNames);
      }
   }

   public int hashCode() {
      int hashCode = ObjectUtils.nullSafeHashCode((Object)this.notificationListener);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.notificationFilter);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.handback);
      hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode((Object)this.mappedObjectNames);
      return hashCode;
   }
}
