package weblogic.descriptor.internal;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BootstrapProperties;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.StringUtils;

public abstract class AbstractDescriptorBeanHelper {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugDescriptor");
   private final AbstractDescriptorBean bean;
   private DiffData diffData;
   private long hashValue = 0L;
   private static final int LOOKAHEAD = 3;

   protected AbstractDescriptorBeanHelper(AbstractDescriptorBean bean) {
      this.bean = bean;
   }

   public abstract Iterator getChildren();

   protected long computeHashValue(CRC32 crc) {
      return this.hashValue;
   }

   protected final long computeChildHashValue(Object v1) {
      if (v1 instanceof DescriptorBean) {
         AbstractDescriptorBean d1 = (AbstractDescriptorBean)v1;
         return d1._getHelper().getHashValue();
      } else {
         return 0L;
      }
   }

   protected void computeDiff(AbstractDescriptorBean proposed) {
   }

   protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
      return initialCopy;
   }

   public String getPropertyName(int propIndex) {
      throw new AssertionError("Unrecognized property index: " + propIndex);
   }

   public int getPropertyIndex(String propName) {
      return -1;
   }

   protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate proposed) {
      throw new AssertionError("Update fell through: " + proposed);
   }

   public final void validateSubTree() throws DescriptorValidateException {
      DescriptorValidateException dve = null;
      boolean exceptionOccurred = false;

      try {
         this.bean._validate();
      } catch (IllegalArgumentException var7) {
         exceptionOccurred = true;
         if (dve == null) {
            dve = new DescriptorValidateException("");
         }

         dve.addException(var7);
      }

      Iterator it = this.getChildren();

      while(it.hasNext()) {
         AbstractDescriptorBean bean = (AbstractDescriptorBean)it.next();

         try {
            bean._getHelper().validateSubTree();
         } catch (DescriptorValidateException var6) {
            if (dve == null) {
               dve = new DescriptorValidateException("");
            }

            exceptionOccurred = true;
            dve.addException(var6);
         }
      }

      if (exceptionOccurred) {
         throw dve;
      }
   }

   public final void applyUpdate(BeanUpdateEvent event) {
      BeanUpdateEvent.PropertyUpdate[] updates = this.reorderUpdateSet(event);

      for(int i = 0; i < updates.length; ++i) {
         this.applyPropertyUpdate(event, updates[i]);
      }

   }

   private BeanUpdateEvent.PropertyUpdate[] reorderUpdateSet(BeanUpdateEvent event) {
      Set reorderedList = new LinkedHashSet();
      BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
      List remainingPropertyUpdates = new ArrayList();
      BeanUpdateEvent.PropertyUpdate[] var5 = updates;
      int var6 = updates.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         BeanUpdateEvent.PropertyUpdate update = var5[var7];
         remainingPropertyUpdates.add(update);
      }

      while(true) {
         while(remainingPropertyUpdates.size() > 0) {
            BeanUpdateEvent.PropertyUpdate update = (BeanUpdateEvent.PropertyUpdate)remainingPropertyUpdates.get(0);
            if (update.isRemoveUpdate() && update.getRemovedObject() instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean child = (AbstractDescriptorBean)update.getRemovedObject();
               Set cycleDetectionSet = new HashSet();
               Set references = this.getFlattenedReferenceTree(child, cycleDetectionSet);
               reorderedList.addAll(this.putDeletesInOrder(references, remainingPropertyUpdates));
            } else {
               reorderedList.add(update);
               remainingPropertyUpdates.remove(update);
            }
         }

         return (BeanUpdateEvent.PropertyUpdate[])reorderedList.toArray(new BeanUpdateEvent.PropertyUpdate[reorderedList.size()]);
      }
   }

   private Set getFlattenedReferenceTree(AbstractDescriptorBean bean, Set cycleDetectionSet) {
      LinkedHashSet references = new LinkedHashSet();
      if (cycleDetectionSet.contains(bean)) {
         return references;
      } else {
         cycleDetectionSet.add(bean);

         try {
            ReferenceManager referenceManager = bean._getReferenceManager();
            List directReferences = referenceManager.getResolvedReferences(bean);
            if (directReferences != null && directReferences.size() > 0) {
               Iterator iter = directReferences.iterator();

               while(iter.hasNext()) {
                  AbstractDescriptorBean directReference = (AbstractDescriptorBean)((AbstractDescriptorBean)((ResolvedReference)iter.next()).getBean());
                  references.addAll(this.getFlattenedReferenceTree(directReference, cycleDetectionSet));
               }
            }

            references.add(bean);
         } finally {
            cycleDetectionSet.remove(bean);
         }

         return references;
      }
   }

   private List putDeletesInOrder(Set beans, List propertyUpdates) {
      List deleteUpdateList = new ArrayList();
      Iterator var4 = beans.iterator();

      while(var4.hasNext()) {
         AbstractDescriptorBean bean = (AbstractDescriptorBean)var4.next();
         BeanUpdateEvent.PropertyUpdate correspondingUpdate = this.findRemovalPropertyUpdateAndRemoveItFromList(bean, propertyUpdates);
         if (correspondingUpdate != null) {
            deleteUpdateList.add(correspondingUpdate);
         }
      }

      return deleteUpdateList;
   }

   private BeanUpdateEvent.PropertyUpdate findRemovalPropertyUpdateAndRemoveItFromList(AbstractDescriptorBean bean, List propertyUpdates) {
      Iterator var3 = propertyUpdates.iterator();

      while(var3.hasNext()) {
         BeanUpdateEvent.PropertyUpdate propertyUpdate = (BeanUpdateEvent.PropertyUpdate)var3.next();
         if (propertyUpdate.isRemoveUpdate()) {
            Object removedBean = propertyUpdate.getRemovedObject();
            if (removedBean != null && removedBean.equals(bean)) {
               propertyUpdates.remove(propertyUpdate);
               return propertyUpdate;
            }
         }
      }

      return null;
   }

   public final long getHashValue() {
      if (this.hashValue == 0L) {
         CRC32 crc = new CRC32();
         this.hashValue = this.computeHashValue(crc);
      }

      return this.hashValue;
   }

   public final synchronized void computeDiff(DescriptorBean proposed, DescriptorDiffImpl diff) {
      BeanDiff beanDiff = diff.newBeanDiff(this.bean, proposed);
      this.diffData = new DiffData(diff, beanDiff);
      this.computeDiff((AbstractDescriptorBean)proposed);
      diff.addBeanDiff(this.bean, beanDiff);
   }

   protected final void addRestartElements(String prop, Set restartElements) {
      this.diffData.getBeanDiff().addRestartElements(prop, restartElements);
   }

   protected final void computeDiff(String prop, int v1, int v2, boolean isDynamic) {
      if (v1 != v2) {
         this.diffData.getBeanDiff().recordChange(prop, isDynamic);
      }

   }

   protected final void computeDiff(String prop, long v1, long v2, boolean isDynamic) {
      if (v1 != v2) {
         this.diffData.getBeanDiff().recordChange(prop, isDynamic);
      }

   }

   protected final void computeDiff(String prop, double v1, double v2, boolean isDynamic) {
      if (v1 != v2) {
         this.diffData.getBeanDiff().recordChange(prop, isDynamic);
      }

   }

   protected final void computeDiff(String prop, boolean v1, boolean v2, boolean isDynamic) {
      if (v1 != v2) {
         this.diffData.getBeanDiff().recordChange(prop, isDynamic);
      }

   }

   protected final void computeDiff(String prop, byte[] v1, byte[] v2, boolean isDynamic) {
      if (!Arrays.equals(v1, v2)) {
         this.diffData.getBeanDiff().recordChange(prop, isDynamic);
      }

   }

   protected final void computeDiff(String prop, Object v1, Object v2, boolean isDynamic) {
      this.computeObjectDiff(prop, v1, v2, false, isDynamic);
   }

   protected final void computeDiff(String prop, Object[] v1, Object[] v2, boolean isDynamic) {
      this.computeObjectDiff(prop, v1, v2, false, isDynamic, false);
   }

   protected final void computeChildDiff(String prop, Object v1, Object v2) {
      this.computeObjectDiff(prop, v1, v2, true, true);
   }

   protected final void computeChildDiff(String prop, Object v1, Object v2, boolean isDynamic) {
      this.computeObjectDiff(prop, v1, v2, true, isDynamic);
   }

   protected final void computeChildDiff(String prop, Object[] v1, Object[] v2, boolean isDynamic) {
      this.computeObjectDiff(prop, v1, v2, true, isDynamic, false);
   }

   protected final void computeChildDiff(String prop, Object[] v1, Object[] v2) {
      this.computeObjectDiff(prop, v1, v2, true, true, false);
   }

   protected final void computeDiff(String prop, Object[] v1, Object[] v2, boolean isDynamic, boolean orderSensitive) {
      this.computeObjectDiff(prop, v1, v2, false, isDynamic, orderSensitive);
   }

   protected final void computeChildDiff(String prop, Object[] v1, Object[] v2, boolean isDynamic, boolean orderSensitive) {
      this.computeObjectDiff(prop, v1, v2, true, isDynamic, orderSensitive);
   }

   private void computeObjectDiff(String prop, Object v1, Object v2, boolean isChild, boolean isDynamic) {
      if (v1 != v2) {
         if (v1 != null && v2 != null) {
            if (!this.haveSameKey(v1, v2)) {
               if (v1 instanceof DescriptorBean && v2 instanceof DescriptorBean && isChild) {
                  this.diffData.getBeanDiff().recordRemoval(prop, v1, isDynamic);
                  this.diffData.getBeanDiff().recordAddition(prop, v2, isDynamic);
               } else {
                  this.diffData.getBeanDiff().recordChange(prop, isDynamic);
               }
            } else if (isChild) {
               this.computeSubDiff(prop, v1, v2);
            }
         } else if (v1 == null && v2 instanceof DescriptorBean && isChild) {
            this.diffData.getBeanDiff().recordAddition(prop, v2, isDynamic);
         } else if (v2 == null && v1 instanceof DescriptorBean && isChild) {
            this.diffData.getBeanDiff().recordRemoval(prop, v1, isDynamic);
         } else {
            this.diffData.getBeanDiff().recordChange(prop, isDynamic);
         }
      }

   }

   private final void computeObjectDiff(String prop, Object[] v1, Object[] v2, boolean computeSubDiff, boolean isDynamic, boolean orderSensitive) {
      boolean orderMatched = true;
      List addCandidates = new ArrayList();
      Map removeCandidates = new HashMap();
      int i1 = 0;
      int i2 = 0;
      if (v1 == null) {
         v1 = new Object[0];
      }

      if (v2 == null) {
         v2 = new Object[0];
      }

      while(i1 < v1.length && i2 < v2.length) {
         if (this.haveSameKey(v1[i1], v2[i2])) {
            if (computeSubDiff) {
               this.computeSubDiff(prop, v1[i1], v2[i2]);
            }

            ++i1;
            ++i2;
         } else {
            orderMatched = false;
            boolean matchFound = false;
            int lookahead = Math.min(3, v2.length - i2);
            List potentialAdds = new ArrayList(lookahead);
            this.trace("adding potential addition " + v2[i2]);
            potentialAdds.add(v2[i2]);

            for(int i = 1; !matchFound && i < lookahead; ++i) {
               if (this.haveSameKey(v1[i1], v2[i2 + i])) {
                  if (computeSubDiff) {
                     this.computeSubDiff(prop, v1[i1], v2[i2 + i]);
                  }

                  matchFound = true;
                  addCandidates.addAll(potentialAdds);
                  ++i1;
                  i2 += i + 1;
               } else {
                  this.trace("adding potential addition " + v2[i2 + i]);
                  potentialAdds.add(v2[i2 + i]);
               }
            }

            if (!matchFound) {
               lookahead = Math.min(3, v1.length - i1);
               List potentialRemoves = new ArrayList(lookahead);
               this.trace("adding potential removal " + v1[i1]);
               potentialRemoves.add(v1[i1]);

               for(int i = 1; !matchFound && i < lookahead; ++i) {
                  if (!this.haveSameKey(v1[i1 + i], v2[i2])) {
                     this.trace("adding potential removal " + v1[i1 + i]);
                     potentialRemoves.add(v1[i1 + i]);
                  } else {
                     if (computeSubDiff) {
                        this.computeSubDiff(prop, v1[i1 + i], v2[i2]);
                     }

                     matchFound = true;
                     Iterator it = potentialRemoves.iterator();

                     while(it.hasNext()) {
                        Object cur = it.next();
                        removeCandidates.put(getKey(cur), cur);
                     }

                     i1 += i + 1;
                     ++i2;
                     if (i1 >= v1.length) {
                        while(i2 < v2.length) {
                           addCandidates.add(v2[i2]);
                           ++i2;
                        }
                     }
                  }
               }
            }

            if (!matchFound) {
               removeCandidates.put(getKey(v1[i1]), v1[i1]);
               addCandidates.add(v2[i2]);
               ++i1;
               ++i2;
            }
         }
      }

      int i;
      if (i1 < v1.length) {
         for(i = i1; i < v1.length; ++i) {
            this.trace("adding potential removal " + v1[i]);
            removeCandidates.put(getKey(v1[i]), v1[i]);
         }
      } else if (i2 < v2.length) {
         for(i = i2; i < v2.length; ++i) {
            this.trace("adding potential addition " + v2[i]);
            addCandidates.add(v2[i]);
         }
      }

      Iterator it;
      if (addCandidates.size() > 0 && removeCandidates.size() > 0) {
         it = addCandidates.iterator();

         while(it.hasNext()) {
            Object addCandidate = it.next();
            Object matchingRemove = removeCandidates.get(getKey(addCandidate));
            if (matchingRemove != null) {
               removeCandidates.remove(getKey(matchingRemove));
               it.remove();
               if (computeSubDiff) {
                  this.computeSubDiff(prop, matchingRemove, addCandidate);
               }
            }
         }
      }

      if (orderSensitive && addCandidates.isEmpty() && removeCandidates.isEmpty() && !orderMatched) {
         this.diffData.getBeanDiff().recordChange(prop, isDynamic);
      }

      it = addCandidates.iterator();

      while(it.hasNext()) {
         this.diffData.getBeanDiff().recordAddition(prop, it.next(), isDynamic);
      }

      it = removeCandidates.values().iterator();

      while(it.hasNext()) {
         this.diffData.getBeanDiff().recordRemoval(prop, it.next(), isDynamic);
      }

   }

   protected final void computeSubDiff(String prop, Object v1, Object v2) {
      if (v1 instanceof DescriptorBean) {
         AbstractDescriptorBean d1 = (AbstractDescriptorBean)v1;
         DescriptorBean d2 = (DescriptorBean)v2;
         d1._getHelper().computeDiff(d2, this.diffData.getDescriptorDiff());
      }

   }

   protected final void initializeRootBean(AbstractDescriptorBean bean) {
      bean._initializeRootBean(bean.getDescriptor());
   }

   protected final AbstractDescriptorBean createCopy(AbstractDescriptorBean bean, boolean includeObsolete) {
      return bean == null ? null : bean._createCopy(includeObsolete, (List)null);
   }

   protected final AbstractDescriptorBean createCopy(AbstractDescriptorBean bean) {
      return this.createCopy(bean, BootstrapProperties.getIncludeObsoletePropsInDiff());
   }

   private static Object getKey(Object o) {
      return !(o instanceof AbstractDescriptorBean) ? o : new DescriptorBeanKey((AbstractDescriptorBean)o);
   }

   private boolean haveSameKey(Object o1, Object o2) {
      return getKey(o1).equals(getKey(o2));
   }

   private void trace(String s) {
      if (debug.isDebugEnabled()) {
         debug.debug(s);
      }

   }

   private void validateArrayObject(Object array) {
      if (!array.getClass().isArray()) {
         throw new IllegalArgumentException("Object not an array");
      }
   }

   private Object extendArray(Object array, Class componentType) {
      this.validateArrayObject(array);
      int sourceLength = Array.getLength(array);
      Object newArray = Array.newInstance(componentType, sourceLength + 1);
      System.arraycopy(array, 0, newArray, 0, sourceLength);
      return newArray;
   }

   public Object _extendArray(Object array, Class componentType, Object newComponent) {
      Object newArray = this.extendArray(array, componentType);
      Array.set(newArray, Array.getLength(array), newComponent);
      return newArray;
   }

   public Object _removeElement(Object array, Class componentType, Object component) {
      this.validateArrayObject(array);
      int sourceLength = Array.getLength(array);

      for(int count = 0; count < sourceLength; ++count) {
         String compType = componentType.getName();
         boolean isElementEqual = false;
         if (compType.equals("java.lang.String")) {
            isElementEqual = Array.get(array, count).equals(component);
         } else {
            isElementEqual = Array.get(array, count) == component;
         }

         if (isElementEqual) {
            int newLength = sourceLength - 1;
            Object newArray = Array.newInstance(componentType, newLength);
            if (newLength != 0) {
               if (count != 0) {
                  System.arraycopy(array, 0, newArray, 0, count);
               }

               if (count != newLength) {
                  System.arraycopy(array, count + 1, newArray, count, newLength - count);
               }
            }

            return newArray;
         }
      }

      return array;
   }

   public String[] _trimElements(String[] array) {
      String[] newArray = new String[array.length];
      String message = "Array has at least one null element";

      for(int count = 0; count < array.length; ++count) {
         this._ensureNonNull(array[count], message);
         newArray[count] = array[count].trim();
      }

      return newArray;
   }

   public void _ensureNonNullElements(String[] array) {
      String message = "Array has at least one null element";

      for(int count = 0; count < array.length; ++count) {
         this._ensureNonNull(array[count], message);
      }

   }

   public void _ensureNonNull(Object obj) {
      this._ensureNonNull(obj, "A non-null value expected");
   }

   public void _ensureNonNull(Object obj, String message) {
      if (obj == null) {
         throw new IllegalArgumentException(message);
      }
   }

   public List _getKeyList(Object array) {
      List result = new ArrayList();
      if (array != null) {
         this.validateArrayObject(array);
         int sourceLength = Array.getLength(array);

         for(int count = 0; count < sourceLength; ++count) {
            AbstractDescriptorBean bean = (AbstractDescriptorBean)Array.get(array, count);
            if (bean != null) {
               result.add(bean._getKey().toString());
            }
         }
      }

      return result;
   }

   public String _serializeKeyList(Object array) {
      this.validateArrayObject(array);
      StringBuffer buf = new StringBuffer();
      int sourceLength = Array.getLength(array);

      for(int count = 0; count < sourceLength; ++count) {
         AbstractDescriptorBean bean = (AbstractDescriptorBean)Array.get(array, count);
         if (bean != null) {
            buf.append(bean._getKey());
         }

         if (count + 1 < sourceLength) {
            buf.append(",");
         }
      }

      return buf.toString();
   }

   public String[] _splitKeyList(String keys) {
      return keys.indexOf("|") != -1 ? StringUtils.splitCompletely(keys, "|") : keys.split(",");
   }

   public Object _cleanAndValidateArray(Object array, Class componentType) {
      this.validateArrayObject(array);
      Set _uniqueElements = new LinkedHashSet(Arrays.asList((Object[])((Object[])array)));
      if (_uniqueElements.contains((Object)null)) {
         throw new IllegalArgumentException("Array has at least one null element");
      } else {
         return _uniqueElements.toArray((Object[])((Object[])Array.newInstance(componentType, 0)));
      }
   }

   public void _clearArray(byte[] array) {
      if (array != null) {
         for(int count = 0; count < array.length; ++count) {
            array[count] = 0;
         }
      }

   }

   public byte[] _cloneArray(byte[] array) {
      byte[] newArray = null;
      if (array != null) {
         newArray = new byte[array.length];
         System.arraycopy(array, 0, newArray, 0, array.length);
      }

      return newArray;
   }

   protected void inferSubTree(Class clazz, Object annotation) {
   }

   protected void inferSubTree(Object child, Class clazz, Object annotation) {
      if (child != null) {
         ((AbstractDescriptorBean)child)._getHelper().inferSubTree(clazz, annotation);
      }

   }

   protected void inferSubTree(Object[] children, Class clazz, Object annotation) {
      if (children != null) {
         for(int count = 0; count < children.length; ++count) {
            if (children[count] != null) {
               ((AbstractDescriptorBean)children[count])._getHelper().inferSubTree(clazz, annotation);
            }
         }
      }

   }

   protected Object attrVal(Object annotation, String attributeName) {
      try {
         Class clazz = annotation.getClass();
         Method annotationTypeMethod = clazz.getMethod("annotationType");
         Class annotationType = (Class)annotationTypeMethod.invoke(annotation);
         Method attribute = annotationType.getMethod(attributeName);
         return attribute.invoke(annotation);
      } catch (SecurityException var7) {
         var7.printStackTrace();
      } catch (NoSuchMethodException var8) {
         var8.printStackTrace();
      } catch (IllegalArgumentException var9) {
         var9.printStackTrace();
      } catch (IllegalAccessException var10) {
         var10.printStackTrace();
      } catch (InvocationTargetException var11) {
         var11.printStackTrace();
      }

      return null;
   }

   protected String strAttrVal(Object annotation, String attributeName) {
      return (String)this.attrVal(annotation, attributeName);
   }

   protected String strAttrValOrDef(Object annotation, String attributeName, String defaultValue) {
      String val = this.strAttrVal(annotation, attributeName);
      return val != null && !val.equals("") ? val : defaultValue;
   }

   protected void reorderArrayObjects(Object[] original, Object[] proposed) {
      if (original != null && proposed != null) {
         for(int propCounter = 0; propCounter < proposed.length; ++propCounter) {
            for(int origCounter = 0; origCounter < original.length; ++origCounter) {
               if (origCounter != propCounter && this.haveSameKey(original[origCounter], proposed[propCounter])) {
                  Object victim = original[propCounter];
                  original[propCounter] = original[origCounter];
                  original[origCounter] = victim;
               }
            }
         }
      }

   }

   public void reorderArrayObjects(Object[] original, Object proposedOrder) {
      if (original != null && proposedOrder != null && proposedOrder instanceof String) {
         Object[] localCopy = new Object[original.length];
         System.arraycopy(original, 0, localCopy, 0, original.length);
         StringTokenizer st = new StringTokenizer((String)proposedOrder, ",");
         if (st.countTokens() == original.length) {
            int j = 0;
            String token = null;

            while(true) {
               while(st.hasMoreTokens()) {
                  token = st.nextToken().trim();

                  for(int i = 0; i < localCopy.length; ++i) {
                     if (((AbstractDescriptorBean)localCopy[i])._getKey().toString().equals(token)) {
                        original[j] = localCopy[i];
                        ++j;
                        break;
                     }
                  }
               }

               return;
            }
         }
      }
   }

   private static class DiffData {
      private final DescriptorDiffImpl descriptorDiff;
      private final BeanDiff beanDiff;

      private DiffData(DescriptorDiffImpl descriptorDiff, BeanDiff beanDiff) {
         this.descriptorDiff = descriptorDiff;
         this.beanDiff = beanDiff;
      }

      private DescriptorDiffImpl getDescriptorDiff() {
         return this.descriptorDiff;
      }

      private BeanDiff getBeanDiff() {
         return this.beanDiff;
      }

      // $FF: synthetic method
      DiffData(DescriptorDiffImpl x0, BeanDiff x1, Object x2) {
         this(x0, x1);
      }
   }
}
