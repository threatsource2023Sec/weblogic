package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class OrderingBeanImpl extends AbstractDescriptorBean implements OrderingBean, Serializable {
   private OrderingOrderingBean _After;
   private OrderingOrderingBean _Before;
   private static SchemaHelper2 _schemaHelper;

   public OrderingBeanImpl() {
      this._initializeProperty(-1);
   }

   public OrderingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OrderingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public OrderingOrderingBean getAfter() {
      return this._After;
   }

   public boolean isAfterInherited() {
      return false;
   }

   public boolean isAfterSet() {
      return this._isSet(0);
   }

   public void setAfter(OrderingOrderingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAfter() != null && param0 != this.getAfter()) {
         throw new BeanAlreadyExistsException(this.getAfter() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OrderingOrderingBean _oldVal = this._After;
         this._After = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public OrderingOrderingBean createAfter() {
      OrderingOrderingBeanImpl _val = new OrderingOrderingBeanImpl(this, -1);

      try {
         this.setAfter(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public OrderingOrderingBean getBefore() {
      return this._Before;
   }

   public boolean isBeforeInherited() {
      return false;
   }

   public boolean isBeforeSet() {
      return this._isSet(1);
   }

   public void setBefore(OrderingOrderingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getBefore() != null && param0 != this.getBefore()) {
         throw new BeanAlreadyExistsException(this.getBefore() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         OrderingOrderingBean _oldVal = this._Before;
         this._Before = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public OrderingOrderingBean createBefore() {
      OrderingOrderingBeanImpl _val = new OrderingOrderingBeanImpl(this, -1);

      try {
         this.setBefore(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
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
               this._After = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Before = null;
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
            case 5:
               if (s.equals("after")) {
                  return 0;
               }
               break;
            case 6:
               if (s.equals("before")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new OrderingOrderingBeanImpl.SchemaHelper2();
            case 1:
               return new OrderingOrderingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "after";
            case 1:
               return "before";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private OrderingBeanImpl bean;

      protected Helper(OrderingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "After";
            case 1:
               return "Before";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("After")) {
            return 0;
         } else {
            return propName.equals("Before") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAfter() != null) {
            iterators.add(new ArrayIterator(new OrderingOrderingBean[]{this.bean.getAfter()}));
         }

         if (this.bean.getBefore() != null) {
            iterators.add(new ArrayIterator(new OrderingOrderingBean[]{this.bean.getBefore()}));
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
            childValue = this.computeChildHashValue(this.bean.getAfter());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getBefore());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            OrderingBeanImpl otherTyped = (OrderingBeanImpl)other;
            this.computeChildDiff("After", this.bean.getAfter(), otherTyped.getAfter(), false);
            this.computeChildDiff("Before", this.bean.getBefore(), otherTyped.getBefore(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OrderingBeanImpl original = (OrderingBeanImpl)event.getSourceBean();
            OrderingBeanImpl proposed = (OrderingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("After")) {
                  if (type == 2) {
                     original.setAfter((OrderingOrderingBean)this.createCopy((AbstractDescriptorBean)proposed.getAfter()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("After", (DescriptorBean)original.getAfter());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Before")) {
                  if (type == 2) {
                     original.setBefore((OrderingOrderingBean)this.createCopy((AbstractDescriptorBean)proposed.getBefore()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Before", (DescriptorBean)original.getBefore());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            OrderingBeanImpl copy = (OrderingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            OrderingOrderingBean o;
            if ((excludeProps == null || !excludeProps.contains("After")) && this.bean.isAfterSet() && !copy._isSet(0)) {
               o = this.bean.getAfter();
               copy.setAfter((OrderingOrderingBean)null);
               copy.setAfter(o == null ? null : (OrderingOrderingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Before")) && this.bean.isBeforeSet() && !copy._isSet(1)) {
               o = this.bean.getBefore();
               copy.setBefore((OrderingOrderingBean)null);
               copy.setBefore(o == null ? null : (OrderingOrderingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getAfter(), clazz, annotation);
         this.inferSubTree(this.bean.getBefore(), clazz, annotation);
      }
   }
}
