package weblogic.servlet.internal;

import java.util.List;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;

public abstract class WebComponentBeanUpdateListener implements BeanUpdateListener {
   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
      DescriptorBean newBean = event.getProposedBean();
      BeanUpdateEvent.PropertyUpdate[] var4 = list;
      int var5 = list.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BeanUpdateEvent.PropertyUpdate prop = var4[var6];
         Object changed;
         switch (prop.getUpdateType()) {
            case 1:
               this.handlePropertyChange(prop, newBean);
               break;
            case 2:
               changed = prop.getAddedObject();
               if (changed instanceof DescriptorBean) {
                  this.handleBeanAdd(prop, (DescriptorBean)changed);
               } else {
                  this.handlePropertyAdd(prop, newBean);
               }
               break;
            case 3:
               changed = prop.getRemovedObject();
               if (changed instanceof DescriptorBean) {
                  this.handleBeanRemove(prop);
               } else {
                  this.handlePropertyRemove(prop);
               }
         }
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
      BeanUpdateEvent.PropertyUpdate[] var3 = list;
      int var4 = list.length;
      int var5 = 0;

      while(var5 < var4) {
         BeanUpdateEvent.PropertyUpdate prop = var3[var5];
         switch (prop.getUpdateType()) {
            case 2:
               Object newBean = prop.getAddedObject();
               if (newBean instanceof DescriptorBean) {
                  this.prepareBeanAdd(prop, (DescriptorBean)newBean);
               }
            case 1:
            case 3:
            default:
               ++var5;
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   protected abstract void prepareBeanAdd(BeanUpdateEvent.PropertyUpdate var1, DescriptorBean var2) throws BeanUpdateRejectedException;

   protected void handlePropertyAdd(BeanUpdateEvent.PropertyUpdate prop, DescriptorBean newBean) {
      this.handlePropertyChange(prop, newBean);
   }

   protected abstract void handlePropertyChange(BeanUpdateEvent.PropertyUpdate var1, DescriptorBean var2);

   protected abstract void handlePropertyRemove(BeanUpdateEvent.PropertyUpdate var1);

   protected abstract void handleBeanAdd(BeanUpdateEvent.PropertyUpdate var1, DescriptorBean var2);

   protected abstract void handleBeanRemove(BeanUpdateEvent.PropertyUpdate var1);

   protected static boolean computeChange(String propertyName, boolean cur, boolean prev, List changedPropertyNames) {
      boolean changed = prev != cur;
      if (changed) {
         changedPropertyNames.add(propertyName);
      }

      return changed;
   }

   protected static boolean computeChange(String propertyName, int cur, int prev, List changedPropertyNames) {
      boolean changed = prev != cur;
      if (changed) {
         changedPropertyNames.add(propertyName);
      }

      return changed;
   }

   protected static boolean computeChange(String propertyName, Object cur, Object prev, List changedPropertyNames) {
      boolean changed = prev == null && cur != null || prev != null && !prev.equals(cur);
      if (changed) {
         changedPropertyNames.add(propertyName);
      }

      return changed;
   }

   protected static String getChangedPropertyNames(List changedNames) {
      if (changedNames != null && !changedNames.isEmpty()) {
         StringBuilder buf = new StringBuilder();
         int i = 0;

         for(int size = changedNames.size(); i < size; ++i) {
            if (i > 0) {
               buf.append(", ");
            }

            buf.append((String)changedNames.get(i));
         }

         return buf.toString();
      } else {
         return "No change is found.";
      }
   }
}
