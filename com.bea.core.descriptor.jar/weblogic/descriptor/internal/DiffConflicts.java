package weblogic.descriptor.internal;

import com.google.common.base.Function;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.conflict.AddSameBeanDiffConflict;
import weblogic.descriptor.conflict.AddSameExternalTreeDiffConflict;
import weblogic.descriptor.conflict.ConflictDescriptorDiff;
import weblogic.descriptor.conflict.DiffConflict;
import weblogic.descriptor.conflict.EditRemovedBeanDiffConflict;
import weblogic.descriptor.conflict.EditRemovedExternalTreeDiffConflict;
import weblogic.descriptor.conflict.ExceptionDiffConflict;
import weblogic.descriptor.conflict.NonResolvableDiffConflictException;
import weblogic.descriptor.conflict.PropertyValueChangeDiffConflict;
import weblogic.descriptor.conflict.ReferenceToRemoveInAddedDiffConflict;
import weblogic.descriptor.conflict.ReferenceToRemovedDiffConflict;
import weblogic.descriptor.conflict.RemoveEditedExternalTreeDiffConflict;
import weblogic.descriptor.conflict.RemovedEditedBeanDiffConflict;

public class DiffConflicts {
   private static final ConflictDescriptorDiff EMPTY_DIFF = new ConflictDescriptorDiff((Iterator)null) {
      public int size() {
         return 0;
      }

      public Iterator iterator() {
         return Collections.emptyIterator();
      }
   };
   private final Descriptor editTree;
   private final DescriptorDiff orig2EditDiff;
   private final DescriptorDiff orig2CurrentDiff;
   private final Map removedFromCurrent = new HashMap();
   private final List conflicts = new ArrayList();
   private final List nonResolvableConflicts = new ArrayList();

   public DiffConflicts(Descriptor currentTree, Descriptor originalTree, Descriptor editTree, String treeName) {
      this.editTree = editTree;
      if (currentTree != null && originalTree != null && editTree != null) {
         this.orig2EditDiff = originalTree.computeDiff(editTree);
         this.orig2CurrentDiff = originalTree.computeDiff(currentTree);
         this.findConflicts();
      } else {
         this.handleMissingExternalTree(currentTree, originalTree, editTree, treeName);
         this.orig2EditDiff = EMPTY_DIFF;
         this.orig2CurrentDiff = EMPTY_DIFF;
      }
   }

   private void handleMissingExternalTree(Descriptor currentTree, Descriptor originalTree, Descriptor editTree, String treeName) {
      DescriptorDiff activatedChanges;
      if (currentTree != null && originalTree != null && editTree == null) {
         activatedChanges = originalTree.computeDiff(currentTree);
         if (activatedChanges.size() != 0) {
            this.conflicts.add(new RemoveEditedExternalTreeDiffConflict(treeName));
         }

      } else if (currentTree == null && originalTree != null && editTree != null) {
         activatedChanges = originalTree.computeDiff(editTree);
         if (activatedChanges.size() != 0) {
            this.conflicts.add(new EditRemovedExternalTreeDiffConflict(treeName));
         }

      } else {
         if (currentTree != null && originalTree == null && editTree != null) {
            this.nonResolvableConflicts.add(new AddSameExternalTreeDiffConflict(treeName));
         }

      }
   }

   private void findConflicts() {
      this.conflicts.clear();
      this.nonResolvableConflicts.clear();
      if (this.orig2EditDiff.size() != 0 && this.orig2CurrentDiff.size() != 0) {
         this.fillRemovedFromCurrent();
         final Map removedFromEdit = new HashMap();
         Iterator var2 = this.orig2EditDiff.iterator();

         while(true) {
            BeanUpdateEvent o2eBean;
            BeanUpdateEvent.PropertyUpdate[] var4;
            int var5;
            int var6;
            BeanUpdateEvent.PropertyUpdate o2eProperty;
            do {
               if (!var2.hasNext()) {
                  var2 = this.orig2CurrentDiff.iterator();

                  while(var2.hasNext()) {
                     o2eBean = (BeanUpdateEvent)var2.next();
                     var4 = o2eBean.getUpdateList();
                     var5 = var4.length;

                     for(var6 = 0; var6 < var5; ++var6) {
                        o2eProperty = var4[var6];

                        try {
                           if (o2eProperty.getUpdateType() == 2) {
                              this.assertAddedBeanContainsReferenceToRemoved(o2eProperty.getAddedObject(), o2eProperty.getAddedObject(), new ReferenceToRemovedFromAddedHelper() {
                                 public AbstractDescriptorBean getWhenRemoved(DescriptorBean suspectedBean) {
                                    return DiffConflicts.this.getIfWasRemoved(suspectedBean, removedFromEdit);
                                 }

                                 public DiffConflict createConflict(AbstractDescriptorBean addedBean, AbstractDescriptorBean rootOfRemovedSubTree, AbstractDescriptorBean beanWithReference, DescriptorBean referencedRemovedBean, String propertyName) {
                                    return new ReferenceToRemoveInAddedDiffConflict(false, addedBean, rootOfRemovedSubTree, beanWithReference, referencedRemovedBean, propertyName);
                                 }
                              });
                           }
                        } catch (Exception var9) {
                           this.addConflict(new ExceptionDiffConflict(o2eBean.getProposedBean(), var9));
                        }
                     }
                  }

                  return;
               }

               o2eBean = (BeanUpdateEvent)var2.next();
            } while(this.assertEditRemovedBean(o2eBean));

            var4 = o2eBean.getUpdateList();
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               o2eProperty = var4[var6];

               try {
                  switch (o2eProperty.getUpdateType()) {
                     case 1:
                        this.assertPropertyChangeConflict(o2eBean, o2eProperty);
                        break;
                     case 2:
                        this.assertAddSameBean(o2eBean, o2eProperty);
                        this.assertAddedBeanContainsReferenceToRemoved(o2eProperty.getAddedObject(), o2eProperty.getAddedObject(), new ReferenceToRemovedFromAddedHelper() {
                           public AbstractDescriptorBean getWhenRemoved(DescriptorBean suspectedBean) {
                              return DiffConflicts.this.getIfWasRemovedFromCurrent(suspectedBean);
                           }

                           public DiffConflict createConflict(AbstractDescriptorBean addedBean, AbstractDescriptorBean rootOfRemovedSubTree, AbstractDescriptorBean beanWithReference, DescriptorBean referencedRemovedBean, String propertyName) {
                              return new ReferenceToRemoveInAddedDiffConflict(true, addedBean, rootOfRemovedSubTree, beanWithReference, referencedRemovedBean, propertyName);
                           }
                        });
                        break;
                     case 3:
                        if (o2eProperty.getRemovedObject() instanceof AbstractDescriptorBean) {
                           AbstractDescriptorBean removedADB = (AbstractDescriptorBean)o2eProperty.getRemovedObject();
                           removedFromEdit.put(removedADB._getQualifiedKey(), removedADB);
                        }

                        this.assertRemoveEditedBean(o2eBean, o2eProperty);
                  }
               } catch (Exception var10) {
                  this.addConflict(new ExceptionDiffConflict(o2eBean.getProposedBean(), var10));
               }
            }
         }
      }
   }

   private void assertAddedBeanContainsReferenceToRemoved(Object originalAdded, Object added, ReferenceToRemovedFromAddedHelper ref2removedHelper) {
      if (added instanceof AbstractDescriptorBean) {
         if (originalAdded instanceof AbstractDescriptorBean) {
            AbstractDescriptorBean addedBean = (AbstractDescriptorBean)added;
            AbstractDescriptorBean originalAddedBean = (AbstractDescriptorBean)originalAdded;
            AbstractDescriptorBeanHelper helper = addedBean._getHelper();
            Method[] methods = addedBean.getClass().getMethods();
            Method[] var8 = methods;
            int var9 = methods.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               Method method = var8[var10];
               if (method.getName().startsWith("get") && !method.getReturnType().isPrimitive() && (!method.getReturnType().isArray() || !method.getReturnType().getComponentType().isPrimitive()) && method.getParameterTypes().length == 0) {
                  String propName = method.getName().substring(3);
                  int propIndex = helper.getPropertyIndex(propName);
                  if (propIndex >= 0) {
                     List values = new ArrayList();

                     try {
                        Object value = method.invoke(addedBean);
                        if (value != null) {
                           if (method.getReturnType().isArray()) {
                              Object[] array = (Object[])((Object[])value);
                              Object[] var17 = array;
                              int var18 = array.length;

                              for(int var19 = 0; var19 < var18; ++var19) {
                                 Object o = var17[var19];
                                 if (o instanceof DescriptorBean) {
                                    values.add((DescriptorBean)o);
                                 }
                              }
                           } else if (value instanceof DescriptorBean) {
                              values.add((DescriptorBean)value);
                           }
                        }
                     } catch (InvocationTargetException | IllegalAccessException var21) {
                        this.addConflict(new ExceptionDiffConflict(addedBean, new Exception("Cannot get value of property " + propName + " in " + addedBean._getQualifiedName())));
                     }

                     Iterator var22 = values.iterator();

                     while(var22.hasNext()) {
                        DescriptorBean value = (DescriptorBean)var22.next();
                        if (value.getParentBean() == addedBean) {
                           this.assertAddedBeanContainsReferenceToRemoved(originalAdded, value, ref2removedHelper);
                        } else if (!this.isParentOrSame(originalAddedBean, value, true)) {
                           AbstractDescriptorBean realRemovedBean = ref2removedHelper.getWhenRemoved(value);
                           if (realRemovedBean != null) {
                              this.addConflict(ref2removedHelper.createConflict(originalAddedBean, realRemovedBean, addedBean, value, propName));
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private void fillRemovedFromCurrent() {
      this.removedFromCurrent.clear();
      Iterator var1 = this.orig2CurrentDiff.iterator();

      while(var1.hasNext()) {
         BeanUpdateEvent o2cBean = (BeanUpdateEvent)var1.next();
         BeanUpdateEvent.PropertyUpdate[] var3 = o2cBean.getUpdateList();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BeanUpdateEvent.PropertyUpdate o2cProperty = var3[var5];
            if (o2cProperty.getUpdateType() == 3) {
               Object removedObject = o2cProperty.getRemovedObject();
               if (removedObject instanceof AbstractDescriptorBean) {
                  AbstractDescriptorBean adb = (AbstractDescriptorBean)removedObject;
                  this.removedFromCurrent.put(adb._getQualifiedKey(), adb);
               }
            }
         }
      }

   }

   private Method getGetter(Object bean, String propertyName) throws NoSuchMethodException {
      return this.getGetter(bean.getClass(), propertyName);
   }

   private Method getGetter(Class clazz, String propertyName) throws NoSuchMethodException {
      try {
         return clazz.getMethod("get" + propertyName);
      } catch (NoSuchMethodException var4) {
         return clazz.getMethod("is" + propertyName);
      }
   }

   private Object getValue(Object bean, String propertyName) throws ReflectiveOperationException, SecurityException {
      return this.getGetter(bean, propertyName).invoke(bean);
   }

   private boolean eequals(Object a, Object b) {
      if (a == null) {
         return null == b;
      } else {
         return a.equals(b);
      }
   }

   private void addConflict(DiffConflict conflict) {
      this.conflicts.add(conflict);
      if (!conflict.isResolvable()) {
         this.nonResolvableConflicts.add(conflict);
      }

   }

   private void assertPropertyChangeConflict(BeanUpdateEvent o2eBean, BeanUpdateEvent.PropertyUpdate o2eProperty) throws Exception {
      String propertyName = o2eProperty.getPropertyName();
      DescriptorBean editBean = o2eBean.getProposedBean();
      Object editValue = this.getValue(editBean, propertyName);
      Iterator var6 = this.orig2CurrentDiff.iterator();

      int var9;
      label56:
      while(var6.hasNext()) {
         BeanUpdateEvent o2cBean = (BeanUpdateEvent)var6.next();
         if (o2cBean.getSourceBean() == o2eBean.getSourceBean()) {
            BeanUpdateEvent.PropertyUpdate[] var8 = o2cBean.getUpdateList();
            var9 = var8.length;
            int var10 = 0;

            while(true) {
               if (var10 >= var9) {
                  break label56;
               }

               BeanUpdateEvent.PropertyUpdate o2cProperty = var8[var10];
               if (o2cProperty.isChangeUpdate() && o2cProperty.getPropertyName().equals(propertyName)) {
                  Object currentValue = this.getValue(o2cBean.getProposedBean(), propertyName);
                  if (!this.eequals(currentValue, editValue)) {
                     this.addConflict(new PropertyValueChangeDiffConflict(o2cBean, o2cProperty, editBean, propertyName, this.getValue(o2eBean.getSourceBean(), propertyName), currentValue, editValue));
                  }
               }

               ++var10;
            }
         }
      }

      if (editValue != null) {
         if (editValue.getClass().isArray() && DescriptorBean.class.isAssignableFrom(editValue.getClass())) {
            DescriptorBean[] arr = (DescriptorBean[])((DescriptorBean[])editValue);
            DescriptorBean[] var14 = arr;
            int var15 = arr.length;

            for(var9 = 0; var9 < var15; ++var9) {
               DescriptorBean descriptorBean = var14[var9];
               if (this.assertReferenceToRemovedBean(descriptorBean, o2eBean, propertyName)) {
                  break;
               }
            }
         } else if (editValue instanceof DescriptorBean) {
            this.assertReferenceToRemovedBean((DescriptorBean)editValue, o2eBean, propertyName);
         }
      }

   }

   private boolean assertReferenceToRemovedBean(DescriptorBean referredBean, BeanUpdateEvent o2eBean, String propertyName) throws Exception {
      AbstractDescriptorBean removedBean = this.getIfWasRemovedFromCurrent(referredBean);
      if (removedBean != null) {
         this.addConflict(new ReferenceToRemovedDiffConflict(o2eBean, propertyName, removedBean));
         return true;
      } else {
         return false;
      }
   }

   private AbstractDescriptorBean getIfWasRemovedFromCurrent(DescriptorBean bean) {
      return this.getIfWasRemoved(bean, this.removedFromCurrent);
   }

   private AbstractDescriptorBean getIfWasRemoved(DescriptorBean bean, Map mapOfRemoved) {
      if (bean != null && bean instanceof AbstractDescriptorBean) {
         QualifiedKey qualifiedKey = ((AbstractDescriptorBean)bean)._getQualifiedKey();
         AbstractDescriptorBean result = (AbstractDescriptorBean)mapOfRemoved.get(qualifiedKey);
         return result == null ? this.getIfWasRemoved(bean.getParentBean(), mapOfRemoved) : result;
      } else {
         return null;
      }
   }

   private boolean assertEditRemovedBean(BeanUpdateEvent o2eBean) {
      AbstractDescriptorBean removed = this.getIfWasRemovedFromCurrent(o2eBean.getProposedBean());
      if (removed != null) {
         this.addConflict(new EditRemovedBeanDiffConflict((AbstractDescriptorBean)o2eBean.getProposedBean(), removed));
         return true;
      } else {
         return false;
      }
   }

   private void assertRemoveEditedBean(BeanUpdateEvent o2eBean, BeanUpdateEvent.PropertyUpdate o2eProperty) {
      Object ro = o2eProperty.getRemovedObject();
      if (ro instanceof AbstractDescriptorBean) {
         AbstractDescriptorBean removedBean = (AbstractDescriptorBean)ro;
         Iterator var5 = this.orig2CurrentDiff.iterator();

         while(true) {
            while(true) {
               BeanUpdateEvent o2cBean;
               do {
                  if (!var5.hasNext()) {
                     return;
                  }

                  o2cBean = (BeanUpdateEvent)var5.next();
               } while(!(o2cBean.getProposedBean() instanceof AbstractDescriptorBean));

               for(AbstractDescriptorBean editedBean = (AbstractDescriptorBean)o2cBean.getProposedBean(); editedBean != null; editedBean = (AbstractDescriptorBean)editedBean.getParentBean()) {
                  if (editedBean._getQualifiedKey().equals(removedBean._getQualifiedKey())) {
                     this.addConflict(new RemovedEditedBeanDiffConflict(o2cBean, removedBean, editedBean));
                     break;
                  }

                  if (editedBean.getParentBean() == null || !(editedBean.getParentBean() instanceof AbstractDescriptorBean)) {
                     break;
                  }
               }
            }
         }
      }
   }

   private void assertAddSameBean(BeanUpdateEvent o2eBean, BeanUpdateEvent.PropertyUpdate o2eProperty) {
      if (o2eProperty.getAddedObject() instanceof AbstractDescriptorBean) {
         DescriptorBean editedBean = o2eBean.getProposedBean();
         AbstractDescriptorBean editAddBean = (AbstractDescriptorBean)o2eProperty.getAddedObject();
         Iterator var5 = this.orig2CurrentDiff.iterator();

         while(true) {
            while(var5.hasNext()) {
               BeanUpdateEvent o2cBean = (BeanUpdateEvent)var5.next();
               BeanUpdateEvent.PropertyUpdate[] var7 = o2cBean.getUpdateList();
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  BeanUpdateEvent.PropertyUpdate o2cProperty = var7[var9];
                  if (o2cProperty.getUpdateType() == 2 && o2cProperty.getAddedObject() instanceof AbstractDescriptorBean) {
                     AbstractDescriptorBean currAddBean = (AbstractDescriptorBean)o2cProperty.getAddedObject();
                     if (currAddBean._getQualifiedKey().equals(editAddBean._getQualifiedKey())) {
                        this.addConflict(new AddSameBeanDiffConflict(o2cBean, o2cProperty, editedBean, editAddBean, currAddBean));
                        break;
                     }
                  }
               }
            }

            return;
         }
      }
   }

   private boolean isParentOrSame(DescriptorBean parent, DescriptorBean child, boolean preferQualifiedNames) {
      if (parent != null && child != null) {
         if (parent == child) {
            return true;
         } else {
            return preferQualifiedNames && parent instanceof AbstractDescriptorBean && child instanceof AbstractDescriptorBean && ((AbstractDescriptorBean)parent)._getQualifiedKey().equals(((AbstractDescriptorBean)child)._getQualifiedKey()) ? true : this.isParentOrSame(parent, child.getParentBean(), preferQualifiedNames);
         }
      } else {
         return false;
      }
   }

   public int size() {
      return this.conflicts.size();
   }

   public List getConflicts() {
      return this.conflicts;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public static void checkNonResolvableConflicts(Collection diffConflicts) throws NonResolvableDiffConflictException {
      List nonResolvableConflicts = new ArrayList();
      Iterator var2 = diffConflicts.iterator();

      while(var2.hasNext()) {
         DiffConflicts diffConflict = (DiffConflicts)var2.next();
         nonResolvableConflicts.addAll(diffConflict.nonResolvableConflicts);
      }

      if (!nonResolvableConflicts.isEmpty()) {
         StringBuilder message = new StringBuilder();
         message.append(nonResolvableConflicts.size()).append(" conflicts:");
         Iterator var6 = nonResolvableConflicts.iterator();

         while(var6.hasNext()) {
            DiffConflict conflict = (DiffConflict)var6.next();
            message.append("\n@").append(conflict);
         }

         throw new NonResolvableDiffConflictException(message.toString(), nonResolvableConflicts);
      }
   }

   public String toString() {
      return DiffConflict.constructMessage(this.getConflicts());
   }

   public ConflictDescriptorDiff getPatch() {
      ConflictDescriptorDiff conflictDescriptorDiff = new ConflictDescriptorDiff(this.orig2CurrentDiff.iterator());

      DiffConflict conflict;
      for(Iterator var2 = this.conflicts.iterator(); var2.hasNext(); conflictDescriptorDiff = conflict.resolve(conflictDescriptorDiff)) {
         conflict = (DiffConflict)var2.next();
      }

      return (ConflictDescriptorDiff)(conflictDescriptorDiff.size() == 0 ? EMPTY_DIFF : new TransformedDescriptorDiff(conflictDescriptorDiff, this.editTree));
   }

   public Descriptor getEditTree() {
      return this.editTree;
   }

   private interface ReferenceToRemovedFromAddedHelper {
      AbstractDescriptorBean getWhenRemoved(DescriptorBean var1);

      DiffConflict createConflict(AbstractDescriptorBean var1, AbstractDescriptorBean var2, AbstractDescriptorBean var3, DescriptorBean var4, String var5);
   }

   private static class TransformedDescriptorDiff extends ConflictDescriptorDiff {
      private final ConflictDescriptorDiff diff;
      private final AbstractDescriptorBean descriptorBean;

      public TransformedDescriptorDiff(ConflictDescriptorDiff diff, Descriptor targetDescriptor) {
         super(diff.iterator());
         this.diff = diff;
         this.descriptorBean = (AbstractDescriptorBean)targetDescriptor.getRootBean();
      }

      public int size() {
         return this.diff.size();
      }

      public Iterator iterator() {
         return Iterators.transform(this.diff.iterator(), new Function() {
            public BeanUpdateEvent apply(final BeanUpdateEvent input) {
               DescriptorBean bean = input.getSourceBean();
               DescriptorBean sourceBean = TransformedDescriptorDiff.this.getCorrespondingMBean(bean);
               return new BeanUpdateEvent(sourceBean, input.getProposedBean(), input.getUpdateID()) {
                  public BeanUpdateEvent.PropertyUpdate[] getUpdateList() {
                     List transformed = Lists.transform(Arrays.asList(input.getUpdateList()), new Function() {
                        public BeanUpdateEvent.PropertyUpdate apply(BeanUpdateEvent.PropertyUpdate inputx) {
                           if (inputx.isRemoveUpdate()) {
                              Object removed = inputx.getRemovedObject();
                              if (removed instanceof AbstractDescriptorBean) {
                                 DescriptorBean removedBean = TransformedDescriptorDiff.this.getCorrespondingMBean((AbstractDescriptorBean)removed);
                                 inputx.resetRemovedObject(removedBean);
                              }
                           }

                           return inputx;
                        }
                     });
                     return (BeanUpdateEvent.PropertyUpdate[])transformed.toArray(new BeanUpdateEvent.PropertyUpdate[transformed.size()]);
                  }
               };
            }
         });
      }

      public void addResolveUpdateEvent(ConflictDescriptorDiff.ResolveUpdateEvent event) {
         this.diff.addResolveUpdateEvent(event);
      }

      public List getResolveUpdateEvents() {
         return this.diff.getResolveUpdateEvents();
      }

      private DescriptorBean getCorrespondingMBean(DescriptorBean bean) {
         return this.descriptorBean.findByQualifiedName((AbstractDescriptorBean)bean);
      }
   }
}
