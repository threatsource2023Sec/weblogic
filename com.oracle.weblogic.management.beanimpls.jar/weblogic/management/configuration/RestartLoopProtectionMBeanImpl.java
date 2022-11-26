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

public class RestartLoopProtectionMBeanImpl extends ConfigurationMBeanImpl implements RestartLoopProtectionMBean, Serializable {
   private long _MaxRestartAllowed;
   private long _MaxRestartAllowedInterval;
   private long _RestartDelay;
   private boolean _RestartLoopProtectionEnabled;
   private static SchemaHelper2 _schemaHelper;

   public RestartLoopProtectionMBeanImpl() {
      this._initializeProperty(-1);
   }

   public RestartLoopProtectionMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RestartLoopProtectionMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isRestartLoopProtectionEnabled() {
      return this._RestartLoopProtectionEnabled;
   }

   public boolean isRestartLoopProtectionEnabledInherited() {
      return false;
   }

   public boolean isRestartLoopProtectionEnabledSet() {
      return this._isSet(10);
   }

   public void setRestartLoopProtectionEnabled(boolean param0) {
      boolean _oldVal = this._RestartLoopProtectionEnabled;
      this._RestartLoopProtectionEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public long getMaxRestartAllowed() {
      return this._MaxRestartAllowed;
   }

   public boolean isMaxRestartAllowedInherited() {
      return false;
   }

   public boolean isMaxRestartAllowedSet() {
      return this._isSet(11);
   }

   public void setMaxRestartAllowed(long param0) {
      LegalChecks.checkMin("MaxRestartAllowed", param0, 0L);
      long _oldVal = this._MaxRestartAllowed;
      this._MaxRestartAllowed = param0;
      this._postSet(11, _oldVal, param0);
   }

   public long getMaxRestartAllowedInterval() {
      return this._MaxRestartAllowedInterval;
   }

   public boolean isMaxRestartAllowedIntervalInherited() {
      return false;
   }

   public boolean isMaxRestartAllowedIntervalSet() {
      return this._isSet(12);
   }

   public void setMaxRestartAllowedInterval(long param0) {
      LegalChecks.checkMin("MaxRestartAllowedInterval", param0, 0L);
      long _oldVal = this._MaxRestartAllowedInterval;
      this._MaxRestartAllowedInterval = param0;
      this._postSet(12, _oldVal, param0);
   }

   public long getRestartDelay() {
      return this._RestartDelay;
   }

   public boolean isRestartDelayInherited() {
      return false;
   }

   public boolean isRestartDelaySet() {
      return this._isSet(13);
   }

   public void setRestartDelay(long param0) {
      LegalChecks.checkMin("RestartDelay", param0, 0L);
      long _oldVal = this._RestartDelay;
      this._RestartDelay = param0;
      this._postSet(13, _oldVal, param0);
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
               this._MaxRestartAllowed = 0L;
               if (initOne) {
                  break;
               }
            case 12:
               this._MaxRestartAllowedInterval = 0L;
               if (initOne) {
                  break;
               }
            case 13:
               this._RestartDelay = 0L;
               if (initOne) {
                  break;
               }
            case 10:
               this._RestartLoopProtectionEnabled = false;
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
      return "RestartLoopProtection";
   }

   public void putValue(String name, Object v) {
      long oldVal;
      if (name.equals("MaxRestartAllowed")) {
         oldVal = this._MaxRestartAllowed;
         this._MaxRestartAllowed = (Long)v;
         this._postSet(11, oldVal, this._MaxRestartAllowed);
      } else if (name.equals("MaxRestartAllowedInterval")) {
         oldVal = this._MaxRestartAllowedInterval;
         this._MaxRestartAllowedInterval = (Long)v;
         this._postSet(12, oldVal, this._MaxRestartAllowedInterval);
      } else if (name.equals("RestartDelay")) {
         oldVal = this._RestartDelay;
         this._RestartDelay = (Long)v;
         this._postSet(13, oldVal, this._RestartDelay);
      } else if (name.equals("RestartLoopProtectionEnabled")) {
         boolean oldVal = this._RestartLoopProtectionEnabled;
         this._RestartLoopProtectionEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._RestartLoopProtectionEnabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("MaxRestartAllowed")) {
         return new Long(this._MaxRestartAllowed);
      } else if (name.equals("MaxRestartAllowedInterval")) {
         return new Long(this._MaxRestartAllowedInterval);
      } else if (name.equals("RestartDelay")) {
         return new Long(this._RestartDelay);
      } else {
         return name.equals("RestartLoopProtectionEnabled") ? new Boolean(this._RestartLoopProtectionEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 13:
               if (s.equals("restart-delay")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("max-restart-allowed")) {
                  return 11;
               }
               break;
            case 28:
               if (s.equals("max-restart-allowed-interval")) {
                  return 12;
               }
               break;
            case 31:
               if (s.equals("restart-loop-protection-enabled")) {
                  return 10;
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
               return "restart-loop-protection-enabled";
            case 11:
               return "max-restart-allowed";
            case 12:
               return "max-restart-allowed-interval";
            case 13:
               return "restart-delay";
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
      private RestartLoopProtectionMBeanImpl bean;

      protected Helper(RestartLoopProtectionMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "RestartLoopProtectionEnabled";
            case 11:
               return "MaxRestartAllowed";
            case 12:
               return "MaxRestartAllowedInterval";
            case 13:
               return "RestartDelay";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MaxRestartAllowed")) {
            return 11;
         } else if (propName.equals("MaxRestartAllowedInterval")) {
            return 12;
         } else if (propName.equals("RestartDelay")) {
            return 13;
         } else {
            return propName.equals("RestartLoopProtectionEnabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isMaxRestartAllowedSet()) {
               buf.append("MaxRestartAllowed");
               buf.append(String.valueOf(this.bean.getMaxRestartAllowed()));
            }

            if (this.bean.isMaxRestartAllowedIntervalSet()) {
               buf.append("MaxRestartAllowedInterval");
               buf.append(String.valueOf(this.bean.getMaxRestartAllowedInterval()));
            }

            if (this.bean.isRestartDelaySet()) {
               buf.append("RestartDelay");
               buf.append(String.valueOf(this.bean.getRestartDelay()));
            }

            if (this.bean.isRestartLoopProtectionEnabledSet()) {
               buf.append("RestartLoopProtectionEnabled");
               buf.append(String.valueOf(this.bean.isRestartLoopProtectionEnabled()));
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
            RestartLoopProtectionMBeanImpl otherTyped = (RestartLoopProtectionMBeanImpl)other;
            this.computeDiff("MaxRestartAllowed", this.bean.getMaxRestartAllowed(), otherTyped.getMaxRestartAllowed(), true);
            this.computeDiff("MaxRestartAllowedInterval", this.bean.getMaxRestartAllowedInterval(), otherTyped.getMaxRestartAllowedInterval(), true);
            this.computeDiff("RestartDelay", this.bean.getRestartDelay(), otherTyped.getRestartDelay(), true);
            this.computeDiff("RestartLoopProtectionEnabled", this.bean.isRestartLoopProtectionEnabled(), otherTyped.isRestartLoopProtectionEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RestartLoopProtectionMBeanImpl original = (RestartLoopProtectionMBeanImpl)event.getSourceBean();
            RestartLoopProtectionMBeanImpl proposed = (RestartLoopProtectionMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MaxRestartAllowed")) {
                  original.setMaxRestartAllowed(proposed.getMaxRestartAllowed());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("MaxRestartAllowedInterval")) {
                  original.setMaxRestartAllowedInterval(proposed.getMaxRestartAllowedInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RestartDelay")) {
                  original.setRestartDelay(proposed.getRestartDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("RestartLoopProtectionEnabled")) {
                  original.setRestartLoopProtectionEnabled(proposed.isRestartLoopProtectionEnabled());
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
            RestartLoopProtectionMBeanImpl copy = (RestartLoopProtectionMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("MaxRestartAllowed")) && this.bean.isMaxRestartAllowedSet()) {
               copy.setMaxRestartAllowed(this.bean.getMaxRestartAllowed());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRestartAllowedInterval")) && this.bean.isMaxRestartAllowedIntervalSet()) {
               copy.setMaxRestartAllowedInterval(this.bean.getMaxRestartAllowedInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartDelay")) && this.bean.isRestartDelaySet()) {
               copy.setRestartDelay(this.bean.getRestartDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartLoopProtectionEnabled")) && this.bean.isRestartLoopProtectionEnabledSet()) {
               copy.setRestartLoopProtectionEnabled(this.bean.isRestartLoopProtectionEnabled());
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
