package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class InterceptorBindingBeanImpl extends AbstractDescriptorBean implements InterceptorBindingBean, Serializable {
   private String[] _Descriptions;
   private String _EjbName;
   private boolean _ExcludeClassInterceptors;
   private boolean _ExcludeDefaultInterceptors;
   private String _Id;
   private String[] _InterceptorClasses;
   private InterceptorOrderBean _InterceptorOrder;
   private NamedMethodBean _Method;
   private static SchemaHelper2 _schemaHelper;

   public InterceptorBindingBeanImpl() {
      this._initializeProperty(-1);
   }

   public InterceptorBindingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InterceptorBindingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(1);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String[] getInterceptorClasses() {
      return this._InterceptorClasses;
   }

   public boolean isInterceptorClassesInherited() {
      return false;
   }

   public boolean isInterceptorClassesSet() {
      return this._isSet(2);
   }

   public void addInterceptorClass(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(2)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getInterceptorClasses(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setInterceptorClasses(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeInterceptorClass(String param0) {
      String[] _old = this.getInterceptorClasses();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setInterceptorClasses(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setInterceptorClasses(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._InterceptorClasses;
      this._InterceptorClasses = param0;
      this._postSet(2, _oldVal, param0);
   }

   public InterceptorOrderBean getInterceptorOrder() {
      return this._InterceptorOrder;
   }

   public boolean isInterceptorOrderInherited() {
      return false;
   }

   public boolean isInterceptorOrderSet() {
      return this._isSet(3);
   }

   public void setInterceptorOrder(InterceptorOrderBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getInterceptorOrder() != null && param0 != this.getInterceptorOrder()) {
         throw new BeanAlreadyExistsException(this.getInterceptorOrder() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 3)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         InterceptorOrderBean _oldVal = this._InterceptorOrder;
         this._InterceptorOrder = param0;
         this._postSet(3, _oldVal, param0);
      }
   }

   public InterceptorOrderBean createInterceptorOrder() {
      InterceptorOrderBeanImpl _val = new InterceptorOrderBeanImpl(this, -1);

      try {
         this.setInterceptorOrder(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInterceptorOrder(InterceptorOrderBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._InterceptorOrder;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setInterceptorOrder((InterceptorOrderBean)null);
               this._unSet(3);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean isExcludeDefaultInterceptors() {
      return this._ExcludeDefaultInterceptors;
   }

   public boolean isExcludeDefaultInterceptorsInherited() {
      return false;
   }

   public boolean isExcludeDefaultInterceptorsSet() {
      return this._isSet(4);
   }

   public void setExcludeDefaultInterceptors(boolean param0) {
      boolean _oldVal = this._ExcludeDefaultInterceptors;
      this._ExcludeDefaultInterceptors = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isExcludeClassInterceptors() {
      return this._ExcludeClassInterceptors;
   }

   public boolean isExcludeClassInterceptorsInherited() {
      return false;
   }

   public boolean isExcludeClassInterceptorsSet() {
      return this._isSet(5);
   }

   public void setExcludeClassInterceptors(boolean param0) {
      boolean _oldVal = this._ExcludeClassInterceptors;
      this._ExcludeClassInterceptors = param0;
      this._postSet(5, _oldVal, param0);
   }

   public NamedMethodBean getMethod() {
      return this._Method;
   }

   public boolean isMethodInherited() {
      return false;
   }

   public boolean isMethodSet() {
      return this._isSet(6);
   }

   public void setMethod(NamedMethodBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMethod() != null && param0 != this.getMethod()) {
         throw new BeanAlreadyExistsException(this.getMethod() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 6)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         NamedMethodBean _oldVal = this._Method;
         this._Method = param0;
         this._postSet(6, _oldVal, param0);
      }
   }

   public NamedMethodBean createMethod() {
      NamedMethodBeanImpl _val = new NamedMethodBeanImpl(this, -1);

      try {
         this.setMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMethod(NamedMethodBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._Method;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMethod((NamedMethodBean)null);
               this._unSet(6);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._InterceptorClasses = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._InterceptorOrder = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Method = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._ExcludeClassInterceptors = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._ExcludeDefaultInterceptors = false;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
               break;
            case 6:
               if (s.equals("method")) {
                  return 6;
               }
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }
               break;
            case 17:
               if (s.equals("interceptor-class")) {
                  return 2;
               }

               if (s.equals("interceptor-order")) {
                  return 3;
               }
               break;
            case 26:
               if (s.equals("exclude-class-interceptors")) {
                  return 5;
               }
               break;
            case 28:
               if (s.equals("exclude-default-interceptors")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new InterceptorOrderBeanImpl.SchemaHelper2();
            case 6:
               return new NamedMethodBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "ejb-name";
            case 2:
               return "interceptor-class";
            case 3:
               return "interceptor-order";
            case 4:
               return "exclude-default-interceptors";
            case 5:
               return "exclude-class-interceptors";
            case 6:
               return "method";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 6:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private InterceptorBindingBeanImpl bean;

      protected Helper(InterceptorBindingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "EjbName";
            case 2:
               return "InterceptorClasses";
            case 3:
               return "InterceptorOrder";
            case 4:
               return "ExcludeDefaultInterceptors";
            case 5:
               return "ExcludeClassInterceptors";
            case 6:
               return "Method";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("EjbName")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("InterceptorClasses")) {
            return 2;
         } else if (propName.equals("InterceptorOrder")) {
            return 3;
         } else if (propName.equals("Method")) {
            return 6;
         } else if (propName.equals("ExcludeClassInterceptors")) {
            return 5;
         } else {
            return propName.equals("ExcludeDefaultInterceptors") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getInterceptorOrder() != null) {
            iterators.add(new ArrayIterator(new InterceptorOrderBean[]{this.bean.getInterceptorOrder()}));
         }

         if (this.bean.getMethod() != null) {
            iterators.add(new ArrayIterator(new NamedMethodBean[]{this.bean.getMethod()}));
         }

         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInterceptorClassesSet()) {
               buf.append("InterceptorClasses");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getInterceptorClasses())));
            }

            childValue = this.computeChildHashValue(this.bean.getInterceptorOrder());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getMethod());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isExcludeClassInterceptorsSet()) {
               buf.append("ExcludeClassInterceptors");
               buf.append(String.valueOf(this.bean.isExcludeClassInterceptors()));
            }

            if (this.bean.isExcludeDefaultInterceptorsSet()) {
               buf.append("ExcludeDefaultInterceptors");
               buf.append(String.valueOf(this.bean.isExcludeDefaultInterceptors()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            InterceptorBindingBeanImpl otherTyped = (InterceptorBindingBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InterceptorClasses", this.bean.getInterceptorClasses(), otherTyped.getInterceptorClasses(), false);
            this.computeChildDiff("InterceptorOrder", this.bean.getInterceptorOrder(), otherTyped.getInterceptorOrder(), false);
            this.computeChildDiff("Method", this.bean.getMethod(), otherTyped.getMethod(), false);
            this.computeDiff("ExcludeClassInterceptors", this.bean.isExcludeClassInterceptors(), otherTyped.isExcludeClassInterceptors(), false);
            this.computeDiff("ExcludeDefaultInterceptors", this.bean.isExcludeDefaultInterceptors(), otherTyped.isExcludeDefaultInterceptors(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InterceptorBindingBeanImpl original = (InterceptorBindingBeanImpl)event.getSourceBean();
            InterceptorBindingBeanImpl proposed = (InterceptorBindingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("InterceptorClasses")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addInterceptorClass((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInterceptorClass((String)update.getRemovedObject());
                  }

                  if (original.getInterceptorClasses() == null || original.getInterceptorClasses().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("InterceptorOrder")) {
                  if (type == 2) {
                     original.setInterceptorOrder((InterceptorOrderBean)this.createCopy((AbstractDescriptorBean)proposed.getInterceptorOrder()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("InterceptorOrder", (DescriptorBean)original.getInterceptorOrder());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Method")) {
                  if (type == 2) {
                     original.setMethod((NamedMethodBean)this.createCopy((AbstractDescriptorBean)proposed.getMethod()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Method", (DescriptorBean)original.getMethod());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("ExcludeClassInterceptors")) {
                  original.setExcludeClassInterceptors(proposed.isExcludeClassInterceptors());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ExcludeDefaultInterceptors")) {
                  original.setExcludeDefaultInterceptors(proposed.isExcludeDefaultInterceptors());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            InterceptorBindingBeanImpl copy = (InterceptorBindingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptorClasses")) && this.bean.isInterceptorClassesSet()) {
               o = this.bean.getInterceptorClasses();
               copy.setInterceptorClasses(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("InterceptorOrder")) && this.bean.isInterceptorOrderSet() && !copy._isSet(3)) {
               Object o = this.bean.getInterceptorOrder();
               copy.setInterceptorOrder((InterceptorOrderBean)null);
               copy.setInterceptorOrder(o == null ? null : (InterceptorOrderBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Method")) && this.bean.isMethodSet() && !copy._isSet(6)) {
               Object o = this.bean.getMethod();
               copy.setMethod((NamedMethodBean)null);
               copy.setMethod(o == null ? null : (NamedMethodBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludeClassInterceptors")) && this.bean.isExcludeClassInterceptorsSet()) {
               copy.setExcludeClassInterceptors(this.bean.isExcludeClassInterceptors());
            }

            if ((excludeProps == null || !excludeProps.contains("ExcludeDefaultInterceptors")) && this.bean.isExcludeDefaultInterceptorsSet()) {
               copy.setExcludeDefaultInterceptors(this.bean.isExcludeDefaultInterceptors());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getInterceptorOrder(), clazz, annotation);
         this.inferSubTree(this.bean.getMethod(), clazz, annotation);
      }
   }
}
