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

public class ServerRuntimeStatisticsBeanImpl extends AbstractDescriptorBean implements ServerRuntimeStatisticsBean, Serializable {
   private JMXDomainStatisticsBean[] _MBeanServerStatistics;
   private int _TotalRegisteredMBeansCount;
   private static SchemaHelper2 _schemaHelper;

   public ServerRuntimeStatisticsBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServerRuntimeStatisticsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServerRuntimeStatisticsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getTotalRegisteredMBeansCount() {
      return this._TotalRegisteredMBeansCount;
   }

   public boolean isTotalRegisteredMBeansCountInherited() {
      return false;
   }

   public boolean isTotalRegisteredMBeansCountSet() {
      return this._isSet(0);
   }

   public void setTotalRegisteredMBeansCount(int param0) {
      int _oldVal = this._TotalRegisteredMBeansCount;
      this._TotalRegisteredMBeansCount = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addMBeanServerStatistic(JMXDomainStatisticsBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         JMXDomainStatisticsBean[] _new;
         if (this._isSet(1)) {
            _new = (JMXDomainStatisticsBean[])((JMXDomainStatisticsBean[])this._getHelper()._extendArray(this.getMBeanServerStatistics(), JMXDomainStatisticsBean.class, param0));
         } else {
            _new = new JMXDomainStatisticsBean[]{param0};
         }

         try {
            this.setMBeanServerStatistics(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JMXDomainStatisticsBean[] getMBeanServerStatistics() {
      return this._MBeanServerStatistics;
   }

   public boolean isMBeanServerStatisticsInherited() {
      return false;
   }

   public boolean isMBeanServerStatisticsSet() {
      return this._isSet(1);
   }

   public void removeMBeanServerStatistic(JMXDomainStatisticsBean param0) {
      JMXDomainStatisticsBean[] _old = this.getMBeanServerStatistics();
      JMXDomainStatisticsBean[] _new = (JMXDomainStatisticsBean[])((JMXDomainStatisticsBean[])this._getHelper()._removeElement(_old, JMXDomainStatisticsBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMBeanServerStatistics(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMBeanServerStatistics(JMXDomainStatisticsBean[] param0) throws InvalidAttributeValueException {
      JMXDomainStatisticsBean[] param0 = param0 == null ? new JMXDomainStatisticsBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JMXDomainStatisticsBean[] _oldVal = this._MBeanServerStatistics;
      this._MBeanServerStatistics = (JMXDomainStatisticsBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public JMXDomainStatisticsBean createJMXDomainStatistic() {
      JMXDomainStatisticsBeanImpl _val = new JMXDomainStatisticsBeanImpl(this, -1);

      try {
         this.addMBeanServerStatistic(_val);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._MBeanServerStatistics = new JMXDomainStatisticsBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._TotalRegisteredMBeansCount = 0;
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
            case 23:
               if (s.equals("m-bean-server-statistic")) {
                  return 1;
               }
               break;
            case 29:
               if (s.equals("total-registeredm-beans-count")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new JMXDomainStatisticsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "total-registeredm-beans-count";
            case 1:
               return "m-bean-server-statistic";
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
      private ServerRuntimeStatisticsBeanImpl bean;

      protected Helper(ServerRuntimeStatisticsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TotalRegisteredMBeansCount";
            case 1:
               return "MBeanServerStatistics";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MBeanServerStatistics")) {
            return 1;
         } else {
            return propName.equals("TotalRegisteredMBeansCount") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getMBeanServerStatistics()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getMBeanServerStatistics().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMBeanServerStatistics()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isTotalRegisteredMBeansCountSet()) {
               buf.append("TotalRegisteredMBeansCount");
               buf.append(String.valueOf(this.bean.getTotalRegisteredMBeansCount()));
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
            ServerRuntimeStatisticsBeanImpl otherTyped = (ServerRuntimeStatisticsBeanImpl)other;
            this.computeChildDiff("MBeanServerStatistics", this.bean.getMBeanServerStatistics(), otherTyped.getMBeanServerStatistics(), false);
            this.computeDiff("TotalRegisteredMBeansCount", this.bean.getTotalRegisteredMBeansCount(), otherTyped.getTotalRegisteredMBeansCount(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServerRuntimeStatisticsBeanImpl original = (ServerRuntimeStatisticsBeanImpl)event.getSourceBean();
            ServerRuntimeStatisticsBeanImpl proposed = (ServerRuntimeStatisticsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MBeanServerStatistics")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMBeanServerStatistic((JMXDomainStatisticsBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMBeanServerStatistic((JMXDomainStatisticsBean)update.getRemovedObject());
                  }

                  if (original.getMBeanServerStatistics() == null || original.getMBeanServerStatistics().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("TotalRegisteredMBeansCount")) {
                  original.setTotalRegisteredMBeansCount(proposed.getTotalRegisteredMBeansCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            ServerRuntimeStatisticsBeanImpl copy = (ServerRuntimeStatisticsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MBeanServerStatistics")) && this.bean.isMBeanServerStatisticsSet() && !copy._isSet(1)) {
               JMXDomainStatisticsBean[] oldMBeanServerStatistics = this.bean.getMBeanServerStatistics();
               JMXDomainStatisticsBean[] newMBeanServerStatistics = new JMXDomainStatisticsBean[oldMBeanServerStatistics.length];

               for(int i = 0; i < newMBeanServerStatistics.length; ++i) {
                  newMBeanServerStatistics[i] = (JMXDomainStatisticsBean)((JMXDomainStatisticsBean)this.createCopy((AbstractDescriptorBean)oldMBeanServerStatistics[i], includeObsolete));
               }

               copy.setMBeanServerStatistics(newMBeanServerStatistics);
            }

            if ((excludeProps == null || !excludeProps.contains("TotalRegisteredMBeansCount")) && this.bean.isTotalRegisteredMBeansCountSet()) {
               copy.setTotalRegisteredMBeansCount(this.bean.getTotalRegisteredMBeansCount());
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
         this.inferSubTree(this.bean.getMBeanServerStatistics(), clazz, annotation);
      }
   }
}
