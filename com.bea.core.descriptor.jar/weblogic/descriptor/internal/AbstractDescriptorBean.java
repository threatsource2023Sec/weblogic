package weblogic.descriptor.internal;

import com.bea.staxb.runtime.ObjectFactory;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.SecurityService;
import weblogic.descriptor.Visitor;
import weblogic.utils.Debug;

public abstract class AbstractDescriptorBean implements DescriptorBean, Cloneable, ObjectFactory, Serializable {
   private static final long serialVersionUID = 3058716929963432994L;
   private transient DescriptorImpl descriptor;
   private AbstractDescriptorBean parentBean;
   private int parentProperty = -1;
   private transient Reference helperRef;
   private PropertyChangeSupport changeSupport;
   private BitSet setProperties;
   private boolean destroyed = false;
   private Map xmlComments;
   private transient Map metaData;
   private int instanceId = 0;
   private boolean transientBean = false;
   private boolean isClone = false;
   private boolean syntheticBean = false;
   private transient SecurityService securityService;
   private String _elementName;
   public static final String msg_nullvalue = "A non-null value expected";
   public static final String msg_nullelement_array = "Array has at least one null element";

   protected boolean _isProductionModeEnabled() {
      return this.descriptor.getProductionMode();
   }

   protected boolean _isSecureModeEnabled() {
      return this.descriptor.getSecureMode();
   }

   public AbstractDescriptorBean(DescriptorBean parentBean, int parentProperty) {
      Debug.assertion(parentBean != null);
      this._initialize(parentBean.getDescriptor(), parentBean, parentProperty);
   }

   public AbstractDescriptorBean() {
      this._initialize(_getDescriptorFromThread(), (DescriptorBean)null, -1);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.descriptor = (DescriptorImpl)_getDescriptorFromThread();
      this.securityService = this.descriptor.getSecurityService();
   }

   private static Descriptor _getDescriptorFromThread() {
      Descriptor descriptor = DescriptorImpl.getThreadLocal();
      if (descriptor == null) {
         throw new AssertionError("descriptor must be set on thread prior to parsing or cloning using DescriptorImpl.beginConstruction()");
      } else {
         return descriptor;
      }
   }

   public Object createObject(Class beanClass) {
      DescriptorBean child = this._createChildBean(beanClass, -1);
      return this.descriptor.callBeanCreationInterceptor(child, this);
   }

   protected final void _initializeRootBean(Descriptor descriptor) {
      this.securityService = ((DescriptorImpl)descriptor).getSecurityService();
      ((DescriptorImpl)descriptor).initializeRootBean(this);
      if (descriptor.getRootBean() == this && this.getSchemaLocation() != null && this.getTargetNamespace() != null) {
         ((DescriptorImpl)descriptor).addSchemaLocation(this.getTargetNamespace(), this.getSchemaLocation());
      }

   }

   private void _initialize(Descriptor descriptor, DescriptorBean parentBean, int parentProperty) {
      Debug.assertion(descriptor != null);
      this.descriptor = (DescriptorImpl)descriptor;
      this.securityService = ((DescriptorImpl)descriptor).getSecurityService();
      this._setParent(parentBean, parentProperty);
      this.helperRef = null;
      this.changeSupport = null;
      this.setProperties = new BitSet();
   }

   public AbstractDescriptorBean findByQualifiedName(AbstractDescriptorBean bean) {
      String qualifiedName = bean._getQualifiedName();
      Queue queue = new LinkedList();
      queue.add(this);

      AbstractDescriptorBean b;
      do {
         if (queue.isEmpty()) {
            return null;
         }

         b = (AbstractDescriptorBean)queue.poll();
         Iterator it = b._getHelper().getChildren();

         while(it.hasNext()) {
            AbstractDescriptorBean next = (AbstractDescriptorBean)it.next();
            if (qualifiedName.startsWith(next._getQualifiedName())) {
               queue.add(next);
            }
         }
      } while(!b._getQualifiedName().equals(bean._getQualifiedName()));

      return b;
   }

   private boolean _setParent(DescriptorBean parentBean, int parentProperty) {
      if (parentBean != null && this.parentBean != null && this.parentBean != parentBean) {
         throw new IllegalArgumentException("Cannot set a bean as a child of a different parent than it's existing parent");
      } else if (parentBean != null && this.parentBean != null && parentBean.getDescriptor() != this.descriptor) {
         throw new IllegalArgumentException("Cannot set a bean as a child of a parent in a different descriptor than it's own descriptor");
      } else if (parentBean == this.parentBean && parentProperty == this.parentProperty) {
         return false;
      } else {
         this.parentBean = (AbstractDescriptorBean)parentBean;
         this.parentProperty = parentBean == null ? -1 : parentProperty;
         if (this.getSchemaLocation() != null && this.getTargetNamespace() != null) {
            this.descriptor.addSchemaLocation(this.getTargetNamespace(), this.getSchemaLocation());
         }

         return true;
      }
   }

   protected boolean _setParent(DescriptorBean bean, DescriptorBean parentBean, int parentProperty) {
      return bean == null ? false : ((AbstractDescriptorBean)bean)._setParent(parentBean, parentProperty);
   }

   public final DescriptorBean getParentBean() {
      return this.parentBean;
   }

   final int _getParentProperty() {
      return this.parentProperty;
   }

   public boolean isChildProperty(DescriptorBean parent, int childPropertyIndex) {
      return this._getParentProperty() != -1 && this.getParentBean() != null && this.getParentBean() == parent && this._getParentProperty() == childPropertyIndex;
   }

   public Descriptor getDescriptor() {
      return this.descriptor;
   }

   public final void addBeanUpdateListener(BeanUpdateListener listener) {
      ((DescriptorImpl)this.getDescriptor()).addBeanUpdateListener(this, listener);
   }

   public final void removeBeanUpdateListener(BeanUpdateListener listener) {
      ((DescriptorImpl)this.getDescriptor()).removeBeanUpdateListener(this, listener);
   }

   public final void addPropertyChangeListener(PropertyChangeListener listener) {
      synchronized(this) {
         if (this.changeSupport == null) {
            this.changeSupport = new PropertyChangeSupport(this);
         }
      }

      this.changeSupport.addPropertyChangeListener(listener);
   }

   public final void removePropertyChangeListener(PropertyChangeListener listener) {
      if (this.changeSupport != null) {
         this.changeSupport.removePropertyChangeListener(listener);
      }

   }

   public final PropertyChangeListener[] listPropertyChangeListeners() {
      synchronized(this) {
         if (this.changeSupport != null) {
            return this.changeSupport.getPropertyChangeListeners();
         }
      }

      return new PropertyChangeListener[0];
   }

   public boolean isSet(String propertyName) throws IllegalArgumentException {
      try {
         Method m = this.getClass().getMethod("is" + propertyName + "Set");
         return (Boolean)m.invoke(this);
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      } catch (InvocationTargetException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new IllegalArgumentException(propertyName + " is a not a recognized property on " + this);
      }
   }

   public boolean isInherited(String propertyName) throws IllegalArgumentException {
      try {
         Method m = this.getClass().getMethod("is" + propertyName + "Inherited");
         return (Boolean)m.invoke(this);
      } catch (IllegalAccessException var3) {
         throw new AssertionError(var3);
      } catch (InvocationTargetException var4) {
         throw new AssertionError(var4);
      } catch (NoSuchMethodException var5) {
         throw new IllegalArgumentException(propertyName + " is a not a recognized property on " + this);
      }
   }

   public String[] getInheritedProperties(String[] propertyNames) {
      ArrayList result = new ArrayList();
      String[] var3 = propertyNames;
      int var4 = propertyNames.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String propertyName = var3[var5];

         try {
            if (this.isInherited(propertyName)) {
               result.add(propertyName);
            }
         } catch (Exception var8) {
         }
      }

      return (String[])result.toArray(new String[result.size()]);
   }

   public void unSet(String propertyName) throws IllegalArgumentException {
      int idx = this._getHelper().getPropertyIndex(propertyName);
      if (idx == -1) {
         throw new IllegalArgumentException(propertyName + " is not a property defined by " + this);
      } else if (!this._getHelper().getPropertyName(idx).equals(propertyName)) {
         throw new AssertionError("index for " + propertyName + " must map back instead of to " + this._getHelper().getPropertyName(idx));
      } else {
         this._unSet(idx);
      }
   }

   public void markSet(String propertyName) throws IllegalArgumentException {
      int idx = this._getHelper().getPropertyIndex(propertyName);
      if (idx == -1) {
         throw new IllegalArgumentException(propertyName + " is not a property defined by " + this);
      } else if (!this._getHelper().getPropertyName(idx).equals(propertyName)) {
         throw new AssertionError("index for " + propertyName + " must map back instead of to " + this._getHelper().getPropertyName(idx));
      } else {
         this._markSet(idx, true);
      }
   }

   protected void _unSet(int propertyIndex) {
   }

   protected void _unSet(AbstractDescriptorBean bean, int propertyIndex) {
      bean._unSet(propertyIndex);
   }

   public boolean isEditable() {
      return this.descriptor.isEditable();
   }

   public String toString() {
      return super.toString() + "(" + this._getQualifiedName() + ")";
   }

   private Method findCreateChildMethod(String propertyName, Class intf) {
      Class[] sig = new Class[]{intf};
      Method method = null;

      try {
         method = this.getClass().getMethod("add" + propertyName, sig);
      } catch (NoSuchMethodException var8) {
      }

      try {
         method = this.getClass().getMethod("set" + propertyName, sig);
      } catch (NoSuchMethodException var7) {
      }

      if (method == null) {
         Class[] intfs = intf.getInterfaces();

         for(int i = 0; intfs != null && method == null && i < intfs.length; ++i) {
            method = this.findCreateChildMethod(propertyName, intfs[i]);
         }
      }

      return method;
   }

   private Method findDestroyChildMethod(String propertyName, Class intf) {
      Method method = null;

      try {
         method = this.getClass().getMethod("destroy" + propertyName);
      } catch (NoSuchMethodException var7) {
      }

      if (method == null) {
         if (intf != null) {
            try {
               method = this.getClass().getMethod("destroy" + propertyName, intf);
            } catch (NoSuchMethodException var6) {
            }
         }

         if (method == null) {
            Class[] intfs = intf.getInterfaces();

            for(int i = 0; intfs != null && method == null && i < intfs.length; ++i) {
               method = this.findCreateChildMethod(propertyName, intfs[i]);
            }
         }
      }

      return method;
   }

   public DescriptorBean createChildCopy(String propertyName, DescriptorBean beanToCopy) throws IllegalArgumentException, BeanAlreadyExistsException {
      return this._createChildCopy(propertyName, beanToCopy, false);
   }

   public DescriptorBean createChildCopyIncludingObsolete(String propertyName, DescriptorBean beanToCopy) throws IllegalArgumentException, BeanAlreadyExistsException {
      return this._createChildCopy(propertyName, beanToCopy, true);
   }

   private DescriptorBean _createChildCopy(String propertyName, DescriptorBean beanToCopy, boolean includeObsolete) throws IllegalArgumentException, BeanAlreadyExistsException {
      Method method = this.findCreateChildMethod(propertyName, beanToCopy.getClass().getInterfaces()[0]);
      if (method == null) {
         throw new IllegalArgumentException(propertyName + " of type " + beanToCopy.getClass() + " does not exist on " + this.getClass().getName());
      } else {
         Stack descStack = (Stack)DescriptorImpl.THREAD_LOCAL.get();
         descStack.push(this.descriptor);

         AbstractDescriptorBean var15;
         try {
            DescriptorBean clone = ((AbstractDescriptorBean)beanToCopy)._createCopy(includeObsolete, (List)null);
            method.invoke(this, clone);
            this.descriptor.getReferenceManager().resolveReferences();
            var15 = clone;
         } catch (IllegalAccessException var12) {
            throw new AssertionError(var12);
         } catch (InvocationTargetException var13) {
            Throwable targetException = var13.getTargetException();
            if (targetException instanceof BeanAlreadyExistsException) {
               throw (BeanAlreadyExistsException)targetException;
            }

            throw new AssertionError(targetException);
         } finally {
            descStack.pop();
         }

         return var15;
      }
   }

   public Object clone() {
      return this._cloneInternal(this, false, (List)null);
   }

   public AbstractDescriptorBean clone(AbstractDescriptorBean parent) {
      AbstractDescriptorBean clone = (AbstractDescriptorBean)AbstractDescriptorBean.class.cast(this.clone());
      this._setParent(clone, parent, this._getParentProperty());
      return clone;
   }

   protected AbstractDescriptorBean _cloneInternal(AbstractDescriptorBean bean, boolean includeObsolete, List excludeProps) {
      Descriptor origDescriptor = DescriptorImpl.getThreadLocal();
      if (origDescriptor == null) {
         DescriptorImpl.pushThreadLocal(this.descriptor);
      }

      AbstractDescriptorBean var6;
      try {
         AbstractDescriptorBean result;
         if (bean == null) {
            result = null;
            return result;
         }

         result = bean._createCopy(includeObsolete, excludeProps);
         result.isClone = true;
         var6 = result;
      } finally {
         if (origDescriptor == null) {
            DescriptorImpl.popThreadLocal();
         }

      }

      return var6;
   }

   public AbstractDescriptorBean _cloneIncludingObsolete() {
      return this._cloneInternal(this, true, (List)null);
   }

   protected AbstractDescriptorBean _createCopy(boolean includeObsolete, List excludeProps) {
      AbstractDescriptorBean copy = null;
      Debug.assertion(this.getParentBean() != null || DescriptorImpl.getThreadLocal() != this.descriptor, "the root bean must always be cloned into a new descriptor");

      try {
         copy = (AbstractDescriptorBean)this.getClass().newInstance();
      } catch (InstantiationException var5) {
         throw (AssertionError)(new AssertionError("Impossible: " + var5)).initCause(var5);
      } catch (IllegalAccessException var6) {
         throw (AssertionError)(new AssertionError("Impossible: " + var6)).initCause(var6);
      }

      copy = this._getHelper().finishCopy(copy, includeObsolete, excludeProps);
      copy.setProperties = (BitSet)this.setProperties.clone();
      copy.setInstanceId(this.instanceId);
      copy._setTransient(this.transientBean);
      copy.isClone = this.isClone;
      this.copyDelegate(copy);
      return copy;
   }

   public Object _copyProperties(AbstractDescriptorBean copy, List excludeProps) {
      Descriptor origDescriptor = DescriptorImpl.getThreadLocal();
      if (origDescriptor == null) {
         DescriptorImpl.pushThreadLocal(this.descriptor);
      }

      AbstractDescriptorBean var4;
      try {
         Debug.assertion(this.getParentBean() != null || DescriptorImpl.getThreadLocal() != this.descriptor, "the root bean must always be cloned into a new descriptor");
         copy = this._getHelper().finishCopy(copy, false, excludeProps);
         copy.setProperties = (BitSet)this.setProperties.clone();
         var4 = copy;
      } finally {
         if (origDescriptor == null) {
            DescriptorImpl.popThreadLocal();
         }

      }

      return var4;
   }

   public Object _getKey() {
      return null;
   }

   public boolean _hasKey() {
      return false;
   }

   public final String _getQualifiedName() {
      return this._getQualifiedKey().toString(this);
   }

   public final String _getQualifiedName(int propIdx) {
      return this._getQualifiedName() + '/' + this._getHelper().getPropertyName(propIdx);
   }

   public final String _getPropertyName(int propIdx) {
      return this._getHelper().getPropertyName(propIdx);
   }

   public final int _getPropertyIndex(String propertyName) {
      return this._getHelper().getPropertyIndex(propertyName);
   }

   public String getHashValue() {
      return String.valueOf(this._getHelper().getHashValue());
   }

   public int hashCode() {
      Object key = this._getKey();
      return key == null ? System.identityHashCode(this) : System.identityHashCode(this.parentBean) ^ this.parentProperty ^ key.hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof AbstractDescriptorBean)) {
         return false;
      } else {
         AbstractDescriptorBean other = (AbstractDescriptorBean)o;
         if (this.parentBean != other.parentBean) {
            return false;
         } else if (this.parentProperty != other.parentProperty) {
            return false;
         } else {
            return this._hasKey() ? this._isKeyEqual(other) : false;
         }
      }
   }

   boolean _isKeyEqual(AbstractDescriptorBean other) {
      if (this == other) {
         return true;
      } else if (this.getClass() != other.getClass()) {
         return false;
      } else {
         Object key = this._getKey();
         Object otherKey = other._getKey();
         if (key == otherKey) {
            return true;
         } else if (key != null && otherKey != null) {
            return key.getClass() != otherKey.getClass() ? false : key.equals(otherKey);
         } else {
            return false;
         }
      }
   }

   public final QualifiedKey _getQualifiedKey() {
      return new QualifiedKey(this);
   }

   protected final Object _getBeanKey(AbstractDescriptorBean bean) {
      return bean == null ? null : new DescriptorBeanKey(bean);
   }

   protected void _validate() throws IllegalArgumentException {
      if (this.parentBean == null) {
         if (this.parentProperty != -1) {
            throw new AssertionError(this + ".parentProperty  must be -1 rather than " + this.parentProperty + " since this is the root bean");
         }

         if (this != this.getDescriptor().getRootBean()) {
            throw new AssertionError(this + ".getDescriptor().getRootBean() must be " + this + " rather than " + this.getDescriptor().getRootBean() + " since this is the root bean");
         }
      } else {
         if (this.parentProperty <= -1) {
            throw new AssertionError(this + ".parentProperty  must be a valid property index rather than " + this.parentProperty + " since this is not a root bean");
         }

         if (this.getDescriptor() != this.parentBean.getDescriptor()) {
            throw new AssertionError(this + ".getDescriptor() (" + this.getDescriptor() + ") must be the same as as parentBean.getDescriptor() (" + this.parentBean.getDescriptor() + ")");
         }
      }

   }

   protected final void _validate(AbstractDescriptorBean other) throws IllegalArgumentException {
      other._validate();
   }

   protected void _postCreate() {
   }

   protected final void _postCreate(AbstractDescriptorBean other) {
      other._postCreate();
   }

   protected void _preDestroy() {
   }

   protected void _preDestroy(AbstractDescriptorBean other) {
      other._preDestroy();
   }

   protected boolean _isAnythingSet() {
      return !this.setProperties.isEmpty();
   }

   protected boolean _isAnythingSet(AbstractDescriptorBean other) {
      return other._isAnythingSet();
   }

   protected boolean _isSet(int propIndex) {
      return this.setProperties.get(propIndex);
   }

   public final DescriptorBean _createChildBean(Class interfaceOrImpl, int parentIndex) {
      Class implClass;
      try {
         if (!interfaceOrImpl.isInterface()) {
            implClass = interfaceOrImpl;
         } else {
            String implName = DescriptorBeanClassName.toImpl(interfaceOrImpl.getName(), this.isEditable());
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            implClass = cl == null ? Class.forName(implName) : Class.forName(implName, true, cl);
         }
      } catch (ClassNotFoundException var10) {
         throw new AssertionError(var10);
      }

      try {
         Class[] signature = new Class[]{DescriptorBean.class, Integer.TYPE};
         Constructor constructor = implClass.getConstructor(signature);
         return (DescriptorBean)constructor.newInstance(this, new Integer(parentIndex));
      } catch (NoSuchMethodException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (InvocationTargetException var8) {
         throw new AssertionError(var8.getTargetException());
      } catch (InstantiationException var9) {
         throw new AssertionError(var9);
      }
   }

   protected final AbstractDescriptorBeanHelper _getHelper() {
      AbstractDescriptorBeanHelper helper = null;
      if (this.helperRef != null) {
         helper = (AbstractDescriptorBeanHelper)this.helperRef.get();
      }

      if (helper == null) {
         helper = this._createHelper();
         this.helperRef = new SoftReference(helper);
      }

      return helper;
   }

   public final ReferenceManager _getReferenceManager() {
      return ((DescriptorImpl)this.getDescriptor()).getReferenceManager();
   }

   protected void _markSet(int propIndex, boolean isSet) {
      this._markSetOnly(propIndex, isSet);
   }

   private boolean _markSetOnly(int propIndex, boolean isSet) {
      boolean wasSet = this._isSet(propIndex);
      if (isSet) {
         this.setProperties.set(propIndex);
      } else {
         this.setProperties.clear(propIndex);
      }

      this._markModified();
      return wasSet;
   }

   private void _markModified() {
      ((DescriptorImpl)this.getDescriptor()).setModified(true);
   }

   private boolean _isReallyEqual(int propIndex, Object oldVal, Object newVal) {
      boolean result = false;
      if (!this._isSet(propIndex)) {
         return false;
      } else {
         if (oldVal == null && newVal == null) {
            result = true;
         } else if (oldVal != null && newVal != null) {
            if (oldVal.equals(newVal)) {
               result = true;
            } else {
               Class oldValClass = oldVal.getClass();
               Class newValClass = newVal.getClass();
               if (oldValClass.isArray() && newValClass.isArray()) {
                  result = this._isReallyEqualArray(oldVal, newVal);
               }
            }
         }

         return result;
      }
   }

   private boolean _isReallyEqualArray(Object left, Object right) {
      boolean result = false;
      Class leftClass = left.getClass();
      Class rightClass = right.getClass();
      Class leftComponentType = leftClass.getComponentType();
      Class rightComponentType = rightClass.getComponentType();
      boolean leftIsPrimitive = leftComponentType.isPrimitive();
      boolean rightIsPrimitive = rightComponentType.isPrimitive();
      if (leftIsPrimitive == rightIsPrimitive) {
         if (!leftIsPrimitive) {
            result = Arrays.equals((Object[])((Object[])left), (Object[])((Object[])right));
         } else if (leftComponentType.equals(rightComponentType)) {
            if (Boolean.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((boolean[])((boolean[])left), (boolean[])((boolean[])right));
            } else if (Byte.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((byte[])((byte[])left), (byte[])((byte[])right));
            } else if (Character.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((char[])((char[])left), (char[])((char[])right));
            } else if (Double.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((double[])((double[])left), (double[])((double[])right));
            } else if (Float.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((float[])((float[])left), (float[])((float[])right));
            } else if (Integer.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((int[])((int[])left), (int[])((int[])right));
            } else if (Long.TYPE.equals(leftComponentType)) {
               result = Arrays.equals((long[])((long[])left), (long[])((long[])right));
            } else {
               if (!Short.TYPE.equals(leftComponentType)) {
                  throw new RuntimeException("This should not be reachable unless a new primitive type has been added.");
               }

               result = Arrays.equals((short[])((short[])left), (short[])((short[])right));
            }
         }
      }

      return result;
   }

   protected void _postSet(int propIndex, Object oldVal, Object newVal) {
      boolean wasSet = this._markSetOnly(propIndex, true);
      if (!this._isReallyEqual(propIndex, oldVal, newVal) || !wasSet) {
         this._markModified();
         this._postSetFirePropertyChange(propIndex, wasSet, oldVal, newVal);
      }
   }

   protected void _postSetFirePropertyChange(int propIndex, boolean wasSet, Object oldVal, Object newVal) {
      if (this.changeSupport != null) {
         this.changeSupport.firePropertyChange(this._getHelper().getPropertyName(propIndex), wasSet ? oldVal : chooseUnsetValue(oldVal, newVal), newVal);
      }

   }

   private static Object chooseUnsetValue(Object oldVal, Object newVal) {
      if (oldVal != null) {
         return chooseUnsetValue(oldVal);
      } else {
         return newVal != null ? chooseUnsetValue(newVal) : null;
      }
   }

   private static Object chooseUnsetValue(Object value) {
      return value.getClass().isArray() ? Array.newInstance(value.getClass().getComponentType(), 0) : null;
   }

   protected void _postSet(int propIndex, int oldVal, int newVal) {
      boolean wasSet = this._markSetOnly(propIndex, true);
      if (newVal != oldVal || !wasSet) {
         this._markModified();
         this._postSetFirePropertyChange(propIndex, wasSet, oldVal, newVal);
      }
   }

   protected void _postSetFirePropertyChange(int propIndex, boolean wasSet, int oldVal, int newVal) {
      if (this.changeSupport != null) {
         this.changeSupport.firePropertyChange(this._getHelper().getPropertyName(propIndex), wasSet ? oldVal : null, newVal);
      }

   }

   protected void _postSet(int propIndex, long oldVal, long newVal) {
      boolean wasSet = this._markSetOnly(propIndex, true);
      if (newVal != oldVal || !wasSet) {
         this._markModified();
         this._postSetFirePropertyChange(propIndex, wasSet, oldVal, newVal);
      }
   }

   protected void _postSetFirePropertyChange(int propIndex, boolean wasSet, long oldVal, long newVal) {
      if (this.changeSupport != null) {
         this.changeSupport.firePropertyChange(this._getHelper().getPropertyName(propIndex), wasSet ? new Long(oldVal) : null, new Long(newVal));
      }

   }

   protected void _postSet(int propIndex, double oldVal, double newVal) {
      boolean wasSet = this._markSetOnly(propIndex, true);
      if (newVal != oldVal || !wasSet) {
         this._markModified();
         this._postSetFirePropertyChange(propIndex, wasSet, oldVal, newVal);
      }
   }

   protected void _postSetFirePropertyChange(int propIndex, boolean wasSet, double oldVal, double newVal) {
      if (this.changeSupport != null) {
         this.changeSupport.firePropertyChange(this._getHelper().getPropertyName(propIndex), wasSet ? new Double(oldVal) : null, new Double(newVal));
      }

   }

   protected void _postSet(int propIndex, boolean oldVal, boolean newVal) {
      boolean wasSet = this._markSetOnly(propIndex, true);
      if (newVal != oldVal || !wasSet) {
         this._markModified();
         this._postSetFirePropertyChange(propIndex, wasSet, oldVal, newVal);
      }
   }

   protected void _postSetFirePropertyChange(int propIndex, boolean wasSet, boolean oldVal, boolean newVal) {
      if (this.changeSupport != null) {
         this.changeSupport.firePropertyChange(this._getHelper().getPropertyName(propIndex), wasSet ? oldVal : null, newVal);
      }

   }

   public final List _getAlreadySetPropertyNames() {
      if (!this._isAnythingSet()) {
         return Collections.EMPTY_LIST;
      } else {
         String str = this.setProperties.toString();
         str = str.substring(1, str.length() - 1);
         String[] elems = str.split(", ");
         List props = new ArrayList(elems.length);

         for(int i = 0; i < elems.length; ++i) {
            props.add(new Integer(Integer.parseInt(elems[i].trim())));
         }

         Collections.sort(props, new Comparator() {
            public int compare(Object o1, Object o2) {
               if (o1 instanceof Integer && o2 instanceof Integer) {
                  Integer prop = (Integer)o1;
                  return AbstractDescriptorBean.this._getSchemaHelper2().isKey(prop) ? -1 : 1;
               } else {
                  return -1;
               }
            }
         });
         List ret = new ArrayList(props.size());
         Iterator i = props.iterator();

         while(i.hasNext()) {
            Integer index = (Integer)i.next();
            ret.add(this._getHelper().getPropertyName(index));
         }

         return ret;
      }
   }

   protected void _checkIsPotentialChild(Object bean, int propIdx) throws IllegalArgumentException {
      try {
         AbstractDescriptorBean child = (AbstractDescriptorBean)bean;
         if (child == null) {
            return;
         }

         if (child.getParentBean() == this && child._getParentProperty() == propIdx) {
            return;
         }
      } catch (ClassCastException var4) {
      }

      throw new IllegalArgumentException(bean + " is not a member of " + this._getQualifiedName(propIdx));
   }

   void _markDestroyed() {
      this.destroyed = true;
   }

   protected void _markDestroyed(AbstractDescriptorBean bean) {
      bean._markDestroyed();
   }

   boolean _isDestroyed() {
      return !this.destroyed && this.parentBean != null ? this.parentBean._isDestroyed() : this.destroyed;
   }

   protected abstract AbstractDescriptorBeanHelper _createHelper();

   public abstract SchemaHelper _getSchemaHelper2();

   protected String getSchemaLocation() {
      return null;
   }

   protected String getTargetNamespace() {
      return null;
   }

   protected boolean _isEncrypted(byte[] bEncrypted) {
      if (this.securityService == null) {
         return true;
      } else {
         try {
            return this.securityService.isEncrypted(bEncrypted);
         } catch (DescriptorException var3) {
            throw new AssertionError("Encryption check failed: " + var3);
         }
      }
   }

   protected byte[] _encrypt(String propertyName, String sPassword) {
      if (this.securityService == null) {
         return (propertyName + sPassword).getBytes();
      } else {
         try {
            return this.securityService.encrypt(sPassword);
         } catch (DescriptorException var4) {
            throw new AssertionError("Encryption failed: " + var4);
         }
      }
   }

   protected String _decrypt(String propertyName, byte[] bEncrypted) {
      if (this.securityService == null) {
         return (new String(bEncrypted)).substring(propertyName.length());
      } else {
         try {
            return this.securityService.decrypt(bEncrypted);
         } catch (DescriptorException var4) {
            throw new AssertionError("Decryption failed: " + var4);
         }
      }
   }

   public boolean _destroySingleton(String propertyName, DescriptorBean singletonToDestroy) {
      Method destroyer = this.findDestroyChildMethod(propertyName, singletonToDestroy == null ? null : singletonToDestroy.getClass().getInterfaces()[0]);
      if (destroyer != null) {
         try {
            if (destroyer.getParameterTypes().length == 0) {
               destroyer.invoke(this);
               return true;
            } else {
               destroyer.invoke(this, singletonToDestroy);
               return true;
            }
         } catch (IllegalAccessException var5) {
            throw new AssertionError(var5);
         } catch (InvocationTargetException var6) {
            throw new AssertionError(var6);
         }
      } else {
         return false;
      }
   }

   public abstract Munger.SchemaHelper _getSchemaHelper();

   public String _getBeanElementName() {
      return this.parentBean == null ? this._elementName : this.parentBean._getElementName(this._getParentProperty());
   }

   public String _getElementName() {
      return this._elementName;
   }

   public void _setElementName(String _elementName) {
      this._elementName = _elementName;
   }

   public String _getElementName(int propIndex) {
      throw new AssertionError("Unrecognized property index: " + propIndex);
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      return false;
   }

   public boolean _isPropertyASingleton(Munger.ReaderEventInfo info) {
      return false;
   }

   public boolean _isPropertyAdditive(Munger.ReaderEventInfo info) {
      return false;
   }

   public Munger.ReaderEventInfo _getParentReaderEvent(Munger.ReaderEventInfo info) {
      return this._isPropertyAKey(info) && this.getParentBean() != null && ((AbstractDescriptorBean)this.getParentBean())._isChildPropertyAKey(this._getBeanElementName()) ? info.getParentReaderInfo().getParentReaderInfo() : info.getParentReaderInfo();
   }

   public boolean _isChildPropertyAKey(String s) {
      return false;
   }

   public String _getPropertyXpath(String propertyElementName) {
      if (this.parentProperty == -1) {
         return this._getElementName() == null ? propertyElementName : "/" + this._getElementName() + "/" + propertyElementName;
      } else {
         return this.parentBean._getPropertyXpath(this._getBeanElementName() + "/" + propertyElementName);
      }
   }

   protected void _conditionalUnset(boolean condition, int propertyIndex) {
      if (condition) {
         this._unSet(propertyIndex);
      }

   }

   public void addXMLComments(String propertyName, List comments) {
      if (this.xmlComments == null) {
         this.xmlComments = new HashMap();
      }

      List currList = (List)this.xmlComments.get(propertyName);
      if (currList == null) {
         currList = new ArrayList();
      }

      ((List)currList).addAll(comments);
      this.xmlComments.put(propertyName, currList);
   }

   public String[] getXMLComments(String propertyName) {
      return this.xmlComments == null ? null : (String[])((String[])((List)this.xmlComments.get(propertyName)).toArray(new String[0]));
   }

   public final void accept(Visitor visitor) {
      visitor.visit(this);
      Iterator i = this._getHelper().getChildren();

      while(i.hasNext()) {
         AbstractDescriptorBean child = (AbstractDescriptorBean)i.next();
         child.accept(visitor);
      }

   }

   public void inferSubTree(Class clazz) {
      this.inferSubTree(clazz, (Object)null);
   }

   public void inferSubTree(Class clazz, Object annotation) {
      this._getHelper().inferSubTree(clazz, annotation);
   }

   public Object getMetaData(Object key) {
      return this.metaData == null ? null : this.metaData.get(key);
   }

   public Object setMetaData(Object key, Object value) {
      if (this.metaData == null) {
         this.metaData = new HashMap();
      }

      return this.metaData.put(key, value);
   }

   public int getInstanceId() {
      return this.instanceId;
   }

   public int setInstanceId(int id) {
      int oldId = this.instanceId;
      this.instanceId = id;
      return oldId;
   }

   public boolean _isTransient() {
      return this.transientBean;
   }

   public boolean _isClone() {
      return this.isClone;
   }

   public void _untransient() {
      if (!this._isConfigurationExtension()) {
         boolean origValue = this.transientBean;
         this.transientBean = false;
         if (origValue && this.syntheticBean) {
            DescriptorBean parent = this.getParentBean();
            if (parent != null && parent instanceof AbstractDescriptorBean) {
               AbstractDescriptorBean aParent = (AbstractDescriptorBean)parent;
               boolean wasSet = aParent._markSetOnly(this.parentProperty, true);
               if (!wasSet) {
                  aParent._markModified();
               }

               if (aParent._isTransient() && aParent._isSynthetic()) {
                  aParent._untransient();
               }
            }
         }

      }
   }

   public void _setTransient(boolean transientVal) {
      this.transientBean = transientVal;
   }

   public boolean _isSynthetic() {
      return this.syntheticBean;
   }

   private boolean _isConfigurationExtension(Class aClass) {
      if (aClass == null) {
         return false;
      } else if (aClass == Objects.class) {
         return false;
      } else if (aClass.getName().equals("weblogic.management.configuration.ConfigurationExtensionMBean")) {
         return true;
      } else {
         Class[] var2 = aClass.getInterfaces();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Class iface = var2[var4];
            if (this._isConfigurationExtension(iface)) {
               return true;
            }
         }

         return this._isConfigurationExtension(aClass.getSuperclass());
      }
   }

   protected boolean _isConfigurationExtension() {
      return this._isConfigurationExtension(this.getClass());
   }

   public void _setSynthetic(boolean value) {
      this.syntheticBean = value;
   }

   protected final String _performMacroSubstitution(String inputValue, DescriptorBean bean) {
      DescriptorImpl desc = (DescriptorImpl)this.getDescriptor();
      return desc != null && desc.dm != null && desc.dm.getDescriptorMacroSubstitutor() != null ? desc.dm.getDescriptorMacroSubstitutor().performMacroSubstitution(inputValue, bean) : inputValue;
   }

   private void copyDelegate(AbstractDescriptorBean copy) {
      if (this._isTransient()) {
         try {
            Class clz = this.getClass();
            Method mthd = clz.getMethod("_getDelegateBean");
            Object delegate = mthd.invoke(this);
            if (delegate == null) {
               return;
            }

            AbstractDescriptorBean root = (AbstractDescriptorBean)this.getDescriptor().getRootBean();
            if (root == null) {
               return;
            }

            AbstractDescriptorBean newDelegate = root.findByQualifiedName((AbstractDescriptorBean)delegate);
            if (newDelegate == null) {
               return;
            }

            clz = copy.getClass();
            Class[] params = new Class[]{clz};
            mthd = clz.getMethod("_setDelegateBean", params);
            Object[] args1 = new Object[]{newDelegate};
            mthd.invoke(copy, args1);
         } catch (NoSuchMethodException var9) {
         } catch (IllegalAccessException var10) {
            throw new AssertionError(var10);
         } catch (InvocationTargetException var11) {
            throw new AssertionError(var11);
         }

      }
   }

   public void computeDiff(AbstractDescriptorBean other) {
      this._getHelper().computeDiff(other);
   }
}
