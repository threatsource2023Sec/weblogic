package com.oracle.weblogic.lifecycle.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditListener implements VetoableChangeListener {
   public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
      String propName = evt.getPropertyName();
      if (propName != null) {
         if (!propName.equals("created-on") && !propName.equals("updated-on")) {
            Object source = evt.getSource();
            if (source != null && source instanceof Auditable) {
               Auditable auditable = (Auditable)source;
               Object oldValue = evt.getOldValue();
               Object newValue = evt.getNewValue();
               if (oldValue == null && newValue != null && auditable.equals(newValue)) {
                  auditable.setCreatedOn(getDate());
               } else {
                  auditable.setUpdatedOn(getDate());
               }
            }
         }
      }
   }

   private static String getDate() {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
      return dateFormat.format(new Date());
   }
}
