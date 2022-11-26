package weblogic.servlet.internal.fragment;

import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;

public class GeneralRuleMerger extends AbstractMerger {
   public boolean accept(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      return true;
   }

   protected void handleAddEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      String property = update.getPropertyName();
      Object updatedValue = update.getAddedObject();
      property = singularizeProperty(property);
      if (updatedValue instanceof DescriptorBean) {
         try {
            addChildBean(targetBean, property, updatedValue);
         } catch (BeanAlreadyExistsException var13) {
            throw new MergeException("Conflict found while merging web fragment, " + updatedValue);
         }
      } else {
         Object value = getProperty(targetBean, update.getPropertyName());
         Object[] values = (Object[])((Object[])value);
         Object[] var9 = values;
         int var10 = values.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Object v = var9[var11];
            if (updatedValue.equals(v)) {
               return;
            }
         }

         addProperty(targetBean, property, updatedValue);
      }

   }

   protected void handleChangeEvent(DescriptorBean targetBean, DescriptorBean sourceBean, DescriptorBean proposedBean, BeanUpdateEvent.PropertyUpdate update) throws MergeException {
      String property = update.getPropertyName();
      if (!isPropertySet(sourceBean, property)) {
         if (!isPropertySet(targetBean, property)) {
            Object value = getProperty(proposedBean, property);
            if (value != null) {
               setProperty(targetBean, property, value);
            }
         } else if (!getProperty(targetBean, property).equals(getProperty(proposedBean, property))) {
            throw new MergeException("Conflict found while merging web fragment, " + proposedBean + " : " + property);
         }

      }
   }
}
