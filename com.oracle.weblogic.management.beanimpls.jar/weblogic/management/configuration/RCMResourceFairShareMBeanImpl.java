package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class RCMResourceFairShareMBeanImpl extends ConfigurationMBeanImpl implements RCMResourceFairShareMBean, Serializable {
   private FairShareConstraintMBean _FairShareConstraint;
   private static SchemaHelper2 _schemaHelper;

   public RCMResourceFairShareMBeanImpl() {
      this._initializeProperty(-1);
   }

   public RCMResourceFairShareMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RCMResourceFairShareMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public FairShareConstraintMBean getFairShareConstraint() {
      return this._FairShareConstraint;
   }

   public boolean isFairShareConstraintInherited() {
      return false;
   }

   public boolean isFairShareConstraintSet() {
      return this._isSet(10);
   }

   public void setFairShareConstraint(FairShareConstraintMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFairShareConstraint() != null && param0 != this.getFairShareConstraint()) {
         throw new BeanAlreadyExistsException(this.getFairShareConstraint() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 10)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         FairShareConstraintMBean _oldVal = this._FairShareConstraint;
         this._FairShareConstraint = param0;
         this._postSet(10, _oldVal, param0);
      }
   }

   public FairShareConstraintMBean createFairShareConstraint(String param0) {
      FairShareConstraintMBeanImpl _val = new FairShareConstraintMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setFairShareConstraint(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public FairShareConstraintMBean createFairShareConstraint(String param0, int param1) {
      FairShareConstraintMBeanImpl _val = new FairShareConstraintMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         _val.setValue(param1);
         this.setFairShareConstraint(_val);
         return _val;
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         } else {
            throw new UndeclaredThrowableException(var5);
         }
      }
   }

   public void destroyFairShareConstraint(FairShareConstraintMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FairShareConstraint;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFairShareConstraint((FairShareConstraintMBean)null);
               this._unSet(10);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._FairShareConstraint = null;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "RCMResourceFairShare";
   }

   public void putValue(String name, Object v) {
      if (name.equals("FairShareConstraint")) {
         FairShareConstraintMBean oldVal = this._FairShareConstraint;
         this._FairShareConstraint = (FairShareConstraintMBean)v;
         this._postSet(10, oldVal, this._FairShareConstraint);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      return name.equals("FairShareConstraint") ? this._FairShareConstraint : super.getValue(name);
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 21:
               if (s.equals("fair-share-constraint")) {
                  return 10;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new FairShareConstraintMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "fair-share-constraint";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private RCMResourceFairShareMBeanImpl bean;

      protected Helper(RCMResourceFairShareMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "FairShareConstraint";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("FairShareConstraint") ? 10 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getFairShareConstraint() != null) {
            iterators.add(new ArrayIterator(new FairShareConstraintMBean[]{this.bean.getFairShareConstraint()}));
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
            childValue = this.computeChildHashValue(this.bean.getFairShareConstraint());
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
            RCMResourceFairShareMBeanImpl otherTyped = (RCMResourceFairShareMBeanImpl)other;
            this.computeChildDiff("FairShareConstraint", this.bean.getFairShareConstraint(), otherTyped.getFairShareConstraint(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RCMResourceFairShareMBeanImpl original = (RCMResourceFairShareMBeanImpl)event.getSourceBean();
            RCMResourceFairShareMBeanImpl proposed = (RCMResourceFairShareMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FairShareConstraint")) {
                  if (type == 2) {
                     original.setFairShareConstraint((FairShareConstraintMBean)this.createCopy((AbstractDescriptorBean)proposed.getFairShareConstraint()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FairShareConstraint", original.getFairShareConstraint());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
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
            RCMResourceFairShareMBeanImpl copy = (RCMResourceFairShareMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FairShareConstraint")) && this.bean.isFairShareConstraintSet() && !copy._isSet(10)) {
               Object o = this.bean.getFairShareConstraint();
               copy.setFairShareConstraint((FairShareConstraintMBean)null);
               copy.setFairShareConstraint(o == null ? null : (FairShareConstraintMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getFairShareConstraint(), clazz, annotation);
      }
   }
}
