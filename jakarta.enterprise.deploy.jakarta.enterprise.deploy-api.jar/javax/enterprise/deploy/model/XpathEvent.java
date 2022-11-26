package javax.enterprise.deploy.model;

import java.beans.PropertyChangeEvent;

public final class XpathEvent {
   private final DDBean bean;
   private final Object typ;
   private PropertyChangeEvent changeEvent;
   public static final Object BEAN_ADDED = new Object();
   public static final Object BEAN_REMOVED = new Object();
   public static final Object BEAN_CHANGED = new Object();

   public XpathEvent(DDBean bean, Object typ) {
      this.bean = bean;
      this.typ = typ;
   }

   public PropertyChangeEvent getChangeEvent() {
      return this.typ == BEAN_CHANGED ? this.changeEvent : null;
   }

   public void setChangeEvent(PropertyChangeEvent pce) {
      this.changeEvent = pce;
   }

   public DDBean getBean() {
      return this.bean;
   }

   public boolean isAddEvent() {
      return this.typ == BEAN_ADDED;
   }

   public boolean isRemoveEvent() {
      return this.typ == BEAN_REMOVED;
   }

   public boolean isChangeEvent() {
      return this.typ == BEAN_CHANGED;
   }
}
