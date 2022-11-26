package weblogic.descriptor.conflict;

import java.util.Collection;
import java.util.Iterator;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.BeanDiff;

public abstract class DiffConflict {
   protected static final BeangenDescriptorTextFormatter textFormatter = new BeangenDescriptorTextFormatter();
   protected final BeanUpdateEvent orig2CurrBeanUpdate;
   protected final BeanUpdateEvent.PropertyUpdate orig2CurrPropertyUpdate;
   protected final DescriptorBean editBean;

   protected DiffConflict(BeanUpdateEvent orig2CurrBeanUpdate, BeanUpdateEvent.PropertyUpdate orig2CurrPropertyUpdate, DescriptorBean editBean) {
      this.orig2CurrBeanUpdate = orig2CurrBeanUpdate;
      this.orig2CurrPropertyUpdate = orig2CurrPropertyUpdate;
      this.editBean = editBean;
   }

   protected String getBeanFullName() {
      return this.editBean instanceof AbstractDescriptorBean ? ((AbstractDescriptorBean)this.editBean)._getQualifiedName() : String.valueOf(this.editBean);
   }

   public boolean isResolvable() {
      return true;
   }

   public abstract String toString();

   public abstract ConflictDescriptorDiff resolve(ConflictDescriptorDiff var1);

   public abstract String getResolveDescription();

   protected ConflictDescriptorDiff removePropertyUpdateFromOrig2CurrDiff(ConflictDescriptorDiff orig2CurrDiff) {
      Iterator iterator = orig2CurrDiff.iterator();

      BeanUpdateEvent bue;
      do {
         if (!iterator.hasNext()) {
            return orig2CurrDiff;
         }

         bue = (BeanUpdateEvent)iterator.next();
      } while(bue.getSourceBean() != this.orig2CurrBeanUpdate.getSourceBean() || bue.getProposedBean() != this.orig2CurrBeanUpdate.getProposedBean());

      iterator.remove();
      if (this.orig2CurrBeanUpdate.getUpdateList().length != 1 && this.orig2CurrPropertyUpdate != null) {
         BeanDiff beanDiff = new BeanDiff(this.orig2CurrBeanUpdate.getSourceBean(), this.orig2CurrBeanUpdate.getProposedBean(), 0, 0);
         BeanUpdateEvent.PropertyUpdate[] var5 = this.orig2CurrBeanUpdate.getUpdateList();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            BeanUpdateEvent.PropertyUpdate propertyUpdate = var5[var7];
            if (!propertyUpdate.equals(this.orig2CurrPropertyUpdate)) {
               switch (propertyUpdate.getUpdateType()) {
                  case 1:
                     beanDiff.recordChange(propertyUpdate.getPropertyName(), propertyUpdate.isDynamic());
                     break;
                  case 2:
                     beanDiff.recordAddition(propertyUpdate.getPropertyName(), propertyUpdate.getAddedObject(), propertyUpdate.isDynamic());
                     break;
                  case 3:
                     beanDiff.recordRemoval(propertyUpdate.getPropertyName(), propertyUpdate.getRemovedObject(), propertyUpdate.isDynamic());
               }
            }
         }

         orig2CurrDiff.addBeanDiff(beanDiff);
         return orig2CurrDiff;
      } else {
         return orig2CurrDiff;
      }
   }

   public static String constructMessage(Collection conflicts) {
      StringBuilder result = new StringBuilder();
      if (conflicts != null && !conflicts.isEmpty()) {
         if (conflicts.size() == 1) {
            result.append(textFormatter.getOneConflict()).append('\n');
         } else {
            result.append(textFormatter.getNConflicts(conflicts.size())).append('\n');
         }

         int ind = 1;
         Iterator var3 = conflicts.iterator();

         while(var3.hasNext()) {
            DiffConflict conflict = (DiffConflict)var3.next();
            result.append(textFormatter.getConflictFullMessage(ind++, conflict.toString(), conflict.getResolveDescription())).append('\n');
         }
      } else {
         result.append(textFormatter.getNoConflicts()).append('\n');
      }

      return result.toString();
   }
}
