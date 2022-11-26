package weblogic.descriptor.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;

public class DescriptorDiffImpl implements DescriptorDiff {
   private final Map diffMap = new HashMap();
   private final int diffID;
   private int size;
   private boolean hasNonDynamicUpdates = false;
   private final TreeMap beanDiffOrderMap = new TreeMap();
   private int nextBeanDiffID = 0;

   public DescriptorDiffImpl(Descriptor original, Descriptor update, int diffID) {
      this.diffID = diffID;
      AbstractDescriptorBean root = (AbstractDescriptorBean)original.getRootBean();
      root._getHelper().computeDiff(update.getRootBean(), this);
   }

   public Iterator iterator() {
      List allDiffs = new LinkedList();
      Iterator var2 = this.beanDiffOrderMap.values().iterator();

      while(var2.hasNext()) {
         DescriptorBean bean = (DescriptorBean)var2.next();
         BeanDiff diff = (BeanDiff)this.diffMap.get(bean);
         if (diff != null) {
            BeanDiff removeRefDiff = diff.getReferenceRemovalOnlyBeanDiff();
            BeanDiff otherDiff = diff.getReferenceRemovalExcludeBeanDiff();
            if (removeRefDiff != null) {
               allDiffs.add(0, removeRefDiff);
            }

            if (otherDiff != null) {
               allDiffs.add(otherDiff);
            }
         }
      }

      return allDiffs.iterator();
   }

   public int size() {
      return this.size;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.size() + " changes ------");
      Iterator var2 = this.diffMap.values().iterator();

      while(var2.hasNext()) {
         BeanDiff diff = (BeanDiff)var2.next();
         buf.append("\n " + diff.toString());
      }

      return buf.toString();
   }

   BeanUpdateEvent getBeanUpdateEvent(DescriptorBean bean) {
      BeanDiff beanDiff = (BeanDiff)this.diffMap.get(bean);
      return beanDiff == null ? null : beanDiff;
   }

   public BeanDiff newBeanDiff(DescriptorBean original, DescriptorBean proposed) {
      return new BeanDiff(original, proposed, this.diffID, this.nextBeanDiffID++);
   }

   public void addBeanDiff(DescriptorBean original, BeanDiff diff) {
      if (diff.size() > 0) {
         this.diffMap.put(original, diff);
         this.size += diff.size();
         this.beanDiffOrderMap.put(new Integer(diff.getBeanDiffID()), original);
      }

      if (diff.hasNonDynamicUpdates()) {
         this.hasNonDynamicUpdates = true;
      }

   }

   private String getPropertyValueAsString(Object bean, String propertyName) {
      Object value = null;

      try {
         Method getter = null;

         try {
            getter = bean.getClass().getMethod("get" + propertyName);
         } catch (NoSuchMethodException var8) {
            try {
               getter = bean.getClass().getMethod("is" + propertyName);
            } catch (NoSuchMethodException var7) {
               var8.printStackTrace();
               var7.printStackTrace();
            }
         }

         if (getter == null) {
            value = "<unknown value>";
         } else {
            value = getter.invoke(bean);
         }
      } catch (IllegalAccessException var9) {
         var9.printStackTrace();
      } catch (InvocationTargetException var10) {
         var10.printStackTrace();
      }

      return value == null ? null : value.toString();
   }

   String getNonDynamicUpdateMessage() {
      StringBuffer message = new StringBuffer("Non-dynamic properties were found to be updated\n");
      if (this.hasNonDynamicUpdates()) {
         Iterator var2 = this.diffMap.keySet().iterator();

         while(true) {
            BeanDiff diff;
            BeanUpdateEvent.PropertyUpdate[] updates;
            do {
               do {
                  DescriptorBean bean;
                  do {
                     if (!var2.hasNext()) {
                        return message.toString();
                     }

                     bean = (DescriptorBean)var2.next();
                     diff = (BeanDiff)this.diffMap.get(bean);
                  } while(!diff.hasNonDynamicUpdates());

                  message.append("Bean: " + bean.getClass().getName() + "\n");
                  updates = diff.getUpdateList();
               } while(updates == null);
            } while(updates.length <= 0);

            for(int count = 0; count < updates.length; ++count) {
               if (!updates[count].isDynamic()) {
                  message.append(updates[count].toString()).append("[Original Value: " + this.getPropertyValueAsString(diff.getSourceBean(), updates[count].getPropertyName())).append(", Proposed Value: " + this.getPropertyValueAsString(diff.getProposedBean(), updates[count].getPropertyName())).append("]\n");
               }
            }
         }
      } else {
         return message.toString();
      }
   }

   public boolean hasNonDynamicUpdates() {
      return this.hasNonDynamicUpdates;
   }
}
