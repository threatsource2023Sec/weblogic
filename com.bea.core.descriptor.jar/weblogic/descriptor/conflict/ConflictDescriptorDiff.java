package weblogic.descriptor.conflict;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.BeanDiff;

public class ConflictDescriptorDiff implements DescriptorDiff {
   protected static final BeangenDescriptorTextFormatter textFormatter = new BeangenDescriptorTextFormatter();
   private final List beanUpdateEvents;
   private final List resolveUpdateEvents;

   public ConflictDescriptorDiff(Iterator iterator) {
      if (iterator != null) {
         this.beanUpdateEvents = Lists.newArrayList(iterator);
      } else {
         this.beanUpdateEvents = new ArrayList();
      }

      this.resolveUpdateEvents = new ArrayList();
   }

   public int size() {
      return this.beanUpdateEvents.size() + this.resolveUpdateEvents.size();
   }

   public Iterator iterator() {
      return this.beanUpdateEvents.iterator();
   }

   public void addBeanDiff(BeanDiff beanDiff) {
      BeanDiff removeRefDiff = beanDiff.getReferenceRemovalOnlyBeanDiff();
      BeanDiff otherDiff = beanDiff.getReferenceRemovalExcludeBeanDiff();
      if (removeRefDiff != null) {
         this.beanUpdateEvents.add(0, removeRefDiff);
      }

      if (otherDiff != null) {
         this.beanUpdateEvents.add(otherDiff);
      }

   }

   public void addResolveUpdateEvent(ResolveUpdateEvent event) {
      this.resolveUpdateEvents.add(event);
   }

   public List getResolveUpdateEvents() {
      return Collections.unmodifiableList(this.resolveUpdateEvents);
   }

   public static String constructMessage(List patches) {
      List beanUpdateEvents = new ArrayList();
      List resolveUpdateEvents = new ArrayList();
      Iterator var3 = patches.iterator();

      while(var3.hasNext()) {
         ConflictDescriptorDiff patch = (ConflictDescriptorDiff)var3.next();
         beanUpdateEvents.addAll(patch.beanUpdateEvents);
         resolveUpdateEvents.addAll(patch.resolveUpdateEvents);
      }

      if (beanUpdateEvents.size() + resolveUpdateEvents.size() == 0) {
         return textFormatter.getNoDiff();
      } else {
         StringBuilder result = new StringBuilder();
         Iterator var13 = resolveUpdateEvents.iterator();

         while(var13.hasNext()) {
            ResolveUpdateEvent event = (ResolveUpdateEvent)var13.next();
            if (event instanceof RemoveReferenceUpdateEvent) {
               result.append("    ");
               RemoveReferenceUpdateEvent removeReferenceUpdateEvent = (RemoveReferenceUpdateEvent)event;
               result.append(textFormatter.getRemoveReferenceChange(removeReferenceUpdateEvent.getModifiedBeanName(), removeReferenceUpdateEvent.getUnsetPropertyName(), removeReferenceUpdateEvent.getRemovedBeanName()));
               result.append('\n');
            }
         }

         var13 = beanUpdateEvents.iterator();

         while(var13.hasNext()) {
            BeanUpdateEvent beanUpdate = (BeanUpdateEvent)var13.next();
            DescriptorBean bean = beanUpdate.getSourceBean();
            if (bean != null && bean instanceof AbstractDescriptorBean) {
               result.append(((AbstractDescriptorBean)bean)._getQualifiedName());
            } else {
               result.append(bean);
            }

            result.append('\n');
            BeanUpdateEvent.PropertyUpdate[] var8 = beanUpdate.getUpdateList();
            int var9 = var8.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               BeanUpdateEvent.PropertyUpdate propUpdate = var8[var10];
               result.append("    ");
               String childName;
               switch (propUpdate.getUpdateType()) {
                  case 1:
                     result.append(textFormatter.getChangeDiff(propUpdate.getPropertyName()));
                     break;
                  case 2:
                     if (propUpdate.getAddedObject() instanceof AbstractDescriptorBean) {
                        childName = ((AbstractDescriptorBean)propUpdate.getAddedObject())._getQualifiedName();
                     } else {
                        childName = String.valueOf(propUpdate.getAddedObject());
                     }

                     result.append(textFormatter.getAddDiff(childName));
                     break;
                  case 3:
                     if (propUpdate.getRemovedObject() instanceof AbstractDescriptorBean) {
                        childName = ((AbstractDescriptorBean)propUpdate.getRemovedObject())._getQualifiedName();
                     } else {
                        childName = String.valueOf(propUpdate.getRemovedObject());
                     }

                     result.append(textFormatter.getDestroyDiff(childName));
                     break;
                  default:
                     result.append("???");
               }

               result.append('\n');
            }
         }

         if (result.length() == 0) {
            return textFormatter.getNoDiff();
         } else {
            return result.toString();
         }
      }
   }

   public abstract static class RemoveReferenceUpdateEvent extends ResolveUpdateEvent {
      public abstract String getModifiedBeanName();

      public abstract String getUnsetPropertyName();

      public abstract String getRemovedBeanName();
   }

   public abstract static class ResolveUpdateEvent {
      public abstract void apply();
   }
}
