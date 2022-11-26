package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class InstanceMetricBeanImpl extends AbstractDescriptorBean implements InstanceMetricBean, Serializable {
   private String _InstanceName;
   private MetricBean[] _MBeanMetrics;
   private static SchemaHelper2 _schemaHelper;

   public InstanceMetricBeanImpl() {
      this._initializeProperty(-1);
   }

   public InstanceMetricBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InstanceMetricBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getInstanceName() {
      return this._InstanceName;
   }

   public boolean isInstanceNameInherited() {
      return false;
   }

   public boolean isInstanceNameSet() {
      return this._isSet(0);
   }

   public void setInstanceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InstanceName;
      this._InstanceName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addMBeanMetric(MetricBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         MetricBean[] _new;
         if (this._isSet(1)) {
            _new = (MetricBean[])((MetricBean[])this._getHelper()._extendArray(this.getMBeanMetrics(), MetricBean.class, param0));
         } else {
            _new = new MetricBean[]{param0};
         }

         try {
            this.setMBeanMetrics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MetricBean[] getMBeanMetrics() {
      return this._MBeanMetrics;
   }

   public boolean isMBeanMetricsInherited() {
      return false;
   }

   public boolean isMBeanMetricsSet() {
      return this._isSet(1);
   }

   public void removeMBeanMetric(MetricBean param0) {
      MetricBean[] _old = this.getMBeanMetrics();
      MetricBean[] _new = (MetricBean[])((MetricBean[])this._getHelper()._removeElement(_old, MetricBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMBeanMetrics(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMBeanMetrics(MetricBean[] param0) throws InvalidAttributeValueException {
      MetricBean[] param0 = param0 == null ? new MetricBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MetricBean[] _oldVal = this._MBeanMetrics;
      this._MBeanMetrics = (MetricBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public MetricBean createMBeanMetric() {
      MetricBeanImpl _val = new MetricBeanImpl(this, -1);

      try {
         this.addMBeanMetric(_val);
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
               this._InstanceName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MBeanMetrics = new MetricBean[0];
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
            case 13:
               if (s.equals("instance-name")) {
                  return 0;
               } else if (s.equals("m-bean-metric")) {
                  return 1;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new MetricBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "instance-name";
            case 1:
               return "m-bean-metric";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private InstanceMetricBeanImpl bean;

      protected Helper(InstanceMetricBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InstanceName";
            case 1:
               return "MBeanMetrics";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("InstanceName")) {
            return 0;
         } else {
            return propName.equals("MBeanMetrics") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getMBeanMetrics()));
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
            if (this.bean.isInstanceNameSet()) {
               buf.append("InstanceName");
               buf.append(String.valueOf(this.bean.getInstanceName()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getMBeanMetrics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMBeanMetrics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            InstanceMetricBeanImpl otherTyped = (InstanceMetricBeanImpl)other;
            this.computeDiff("InstanceName", this.bean.getInstanceName(), otherTyped.getInstanceName(), false);
            this.computeChildDiff("MBeanMetrics", this.bean.getMBeanMetrics(), otherTyped.getMBeanMetrics(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InstanceMetricBeanImpl original = (InstanceMetricBeanImpl)event.getSourceBean();
            InstanceMetricBeanImpl proposed = (InstanceMetricBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InstanceName")) {
                  original.setInstanceName(proposed.getInstanceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MBeanMetrics")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMBeanMetric((MetricBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMBeanMetric((MetricBean)update.getRemovedObject());
                  }

                  if (original.getMBeanMetrics() == null || original.getMBeanMetrics().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
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
            InstanceMetricBeanImpl copy = (InstanceMetricBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("InstanceName")) && this.bean.isInstanceNameSet()) {
               copy.setInstanceName(this.bean.getInstanceName());
            }

            if ((excludeProps == null || !excludeProps.contains("MBeanMetrics")) && this.bean.isMBeanMetricsSet() && !copy._isSet(1)) {
               MetricBean[] oldMBeanMetrics = this.bean.getMBeanMetrics();
               MetricBean[] newMBeanMetrics = new MetricBean[oldMBeanMetrics.length];

               for(int i = 0; i < newMBeanMetrics.length; ++i) {
                  newMBeanMetrics[i] = (MetricBean)((MetricBean)this.createCopy((AbstractDescriptorBean)oldMBeanMetrics[i], includeObsolete));
               }

               copy.setMBeanMetrics(newMBeanMetrics);
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getMBeanMetrics(), clazz, annotation);
      }
   }
}
