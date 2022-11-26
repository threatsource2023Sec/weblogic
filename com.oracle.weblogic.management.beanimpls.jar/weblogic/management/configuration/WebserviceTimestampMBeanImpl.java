package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class WebserviceTimestampMBeanImpl extends ConfigurationMBeanImpl implements WebserviceTimestampMBean, Serializable {
   private long _ClockSkew;
   private boolean _ClockSynchronized;
   private long _MaxProcessingDelay;
   private int _ValidityPeriod;
   private static SchemaHelper2 _schemaHelper;

   public WebserviceTimestampMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebserviceTimestampMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebserviceTimestampMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setClockSynchronized(boolean param0) {
      boolean _oldVal = this._ClockSynchronized;
      this._ClockSynchronized = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isClockSynchronized() {
      return this._ClockSynchronized;
   }

   public boolean isClockSynchronizedInherited() {
      return false;
   }

   public boolean isClockSynchronizedSet() {
      return this._isSet(10);
   }

   public void setClockSkew(long param0) {
      LegalChecks.checkMin("ClockSkew", param0, 0L);
      long _oldVal = this._ClockSkew;
      this._ClockSkew = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getClockSkew() {
      return this._ClockSkew;
   }

   public boolean isClockSkewInherited() {
      return false;
   }

   public boolean isClockSkewSet() {
      return this._isSet(11);
   }

   public void setMaxProcessingDelay(long param0) {
      long _oldVal = this._MaxProcessingDelay;
      this._MaxProcessingDelay = param0;
      this._postSet(12, _oldVal, param0);
   }

   public long getMaxProcessingDelay() {
      return this._MaxProcessingDelay;
   }

   public boolean isMaxProcessingDelayInherited() {
      return false;
   }

   public boolean isMaxProcessingDelaySet() {
      return this._isSet(12);
   }

   public void setValidityPeriod(int param0) {
      LegalChecks.checkMin("ValidityPeriod", param0, 1);
      int _oldVal = this._ValidityPeriod;
      this._ValidityPeriod = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getValidityPeriod() {
      return this._ValidityPeriod;
   }

   public boolean isValidityPeriodInherited() {
      return false;
   }

   public boolean isValidityPeriodSet() {
      return this._isSet(13);
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
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._ClockSkew = 60000L;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxProcessingDelay = -1L;
               if (initOne) {
                  break;
               }
            case 13:
               this._ValidityPeriod = 60;
               if (initOne) {
                  break;
               }
            case 10:
               this._ClockSynchronized = true;
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
      return "WebserviceTimestamp";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("ClockSkew")) {
         oldVal = this._ClockSkew;
         this._ClockSkew = (Long)v;
         this._postSet(11, oldVal, this._ClockSkew);
      } else if (name.equals("ClockSynchronized")) {
         boolean oldVal = this._ClockSynchronized;
         this._ClockSynchronized = (Boolean)v;
         this._postSet(10, oldVal, this._ClockSynchronized);
      } else if (name.equals("MaxProcessingDelay")) {
         oldVal = this._MaxProcessingDelay;
         this._MaxProcessingDelay = (Long)v;
         this._postSet(12, oldVal, this._MaxProcessingDelay);
      } else if (name.equals("ValidityPeriod")) {
         int oldVal = this._ValidityPeriod;
         this._ValidityPeriod = (Integer)v;
         this._postSet(13, oldVal, this._ValidityPeriod);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ClockSkew")) {
         return new Long(this._ClockSkew);
      } else if (name.equals("ClockSynchronized")) {
         return new Boolean(this._ClockSynchronized);
      } else if (name.equals("MaxProcessingDelay")) {
         return new Long(this._MaxProcessingDelay);
      } else {
         return name.equals("ValidityPeriod") ? new Integer(this._ValidityPeriod) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("clock-skew")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("validity-period")) {
                  return 13;
               }
               break;
            case 18:
               if (s.equals("clock-synchronized")) {
                  return 10;
               }
               break;
            case 20:
               if (s.equals("max-processing-delay")) {
                  return 12;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "clock-synchronized";
            case 11:
               return "clock-skew";
            case 12:
               return "max-processing-delay";
            case 13:
               return "validity-period";
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
      private WebserviceTimestampMBeanImpl bean;

      protected Helper(WebserviceTimestampMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ClockSynchronized";
            case 11:
               return "ClockSkew";
            case 12:
               return "MaxProcessingDelay";
            case 13:
               return "ValidityPeriod";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClockSkew")) {
            return 11;
         } else if (propName.equals("MaxProcessingDelay")) {
            return 12;
         } else if (propName.equals("ValidityPeriod")) {
            return 13;
         } else {
            return propName.equals("ClockSynchronized") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isClockSkewSet()) {
               buf.append("ClockSkew");
               buf.append(String.valueOf(this.bean.getClockSkew()));
            }

            if (this.bean.isMaxProcessingDelaySet()) {
               buf.append("MaxProcessingDelay");
               buf.append(String.valueOf(this.bean.getMaxProcessingDelay()));
            }

            if (this.bean.isValidityPeriodSet()) {
               buf.append("ValidityPeriod");
               buf.append(String.valueOf(this.bean.getValidityPeriod()));
            }

            if (this.bean.isClockSynchronizedSet()) {
               buf.append("ClockSynchronized");
               buf.append(String.valueOf(this.bean.isClockSynchronized()));
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
            WebserviceTimestampMBeanImpl otherTyped = (WebserviceTimestampMBeanImpl)other;
            this.computeDiff("ClockSkew", this.bean.getClockSkew(), otherTyped.getClockSkew(), true);
            this.computeDiff("MaxProcessingDelay", this.bean.getMaxProcessingDelay(), otherTyped.getMaxProcessingDelay(), true);
            this.computeDiff("ValidityPeriod", this.bean.getValidityPeriod(), otherTyped.getValidityPeriod(), true);
            this.computeDiff("ClockSynchronized", this.bean.isClockSynchronized(), otherTyped.isClockSynchronized(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebserviceTimestampMBeanImpl original = (WebserviceTimestampMBeanImpl)event.getSourceBean();
            WebserviceTimestampMBeanImpl proposed = (WebserviceTimestampMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClockSkew")) {
                  original.setClockSkew(proposed.getClockSkew());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MaxProcessingDelay")) {
                  original.setMaxProcessingDelay(proposed.getMaxProcessingDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ValidityPeriod")) {
                  original.setValidityPeriod(proposed.getValidityPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ClockSynchronized")) {
                  original.setClockSynchronized(proposed.isClockSynchronized());
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
            WebserviceTimestampMBeanImpl copy = (WebserviceTimestampMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClockSkew")) && this.bean.isClockSkewSet()) {
               copy.setClockSkew(this.bean.getClockSkew());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxProcessingDelay")) && this.bean.isMaxProcessingDelaySet()) {
               copy.setMaxProcessingDelay(this.bean.getMaxProcessingDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("ValidityPeriod")) && this.bean.isValidityPeriodSet()) {
               copy.setValidityPeriod(this.bean.getValidityPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("ClockSynchronized")) && this.bean.isClockSynchronizedSet()) {
               copy.setClockSynchronized(this.bean.isClockSynchronized());
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
      }
   }
}
