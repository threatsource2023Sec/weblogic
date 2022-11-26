package org.glassfish.hk2.configuration.hub.internal;

import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.configuration.hub.api.Change;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;

public class ChangeImpl implements Change {
   private final Change.ChangeCategory changeCategory;
   private final Type changeType;
   private final String instanceKey;
   private final Instance instanceValue;
   private final Instance originalInstanceValue;
   private final List propertyChanges;

   ChangeImpl(Change.ChangeCategory changeCategory, Type changeType, String instanceKey, Instance instanceValue, Instance originalInstanceValue, List propertyChanges) {
      this.changeCategory = changeCategory;
      this.changeType = changeType;
      this.instanceKey = instanceKey;
      this.instanceValue = instanceValue;
      this.originalInstanceValue = originalInstanceValue;
      this.propertyChanges = propertyChanges;
   }

   public Change.ChangeCategory getChangeCategory() {
      return this.changeCategory;
   }

   public Type getChangeType() {
      return this.changeType;
   }

   public String getInstanceKey() {
      return this.instanceKey;
   }

   public Instance getInstanceValue() {
      return this.instanceValue;
   }

   public Instance getOriginalInstanceValue() {
      return this.originalInstanceValue;
   }

   public List getModifiedProperties() {
      return this.propertyChanges == null ? null : Collections.unmodifiableList(this.propertyChanges);
   }

   public String toString() {
      StringBuffer propChanges = new StringBuffer(",propChanges=[");
      if (this.propertyChanges != null) {
         boolean firstTime = true;
         Iterator var3 = this.propertyChanges.iterator();

         while(var3.hasNext()) {
            PropertyChangeEvent pce = (PropertyChangeEvent)var3.next();
            if (firstTime) {
               propChanges.append(pce.getPropertyName());
               firstTime = false;
            } else {
               propChanges.append("," + pce.getPropertyName());
            }
         }
      }

      propChanges.append("]");
      return "ChangeImpl(" + this.changeCategory + ",type=" + this.changeType + ",instanceKey=" + this.instanceKey + propChanges.toString() + ",sid=" + System.identityHashCode(this) + ")";
   }
}
