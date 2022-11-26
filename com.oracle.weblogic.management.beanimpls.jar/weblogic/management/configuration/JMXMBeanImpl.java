package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JMXMBeanImpl extends ConfigurationMBeanImpl implements JMXMBean, Serializable {
   private boolean _CompatibilityMBeanServerEnabled;
   private boolean _DomainMBeanServerEnabled;
   private boolean _EditMBeanServerEnabled;
   private int _InvocationTimeoutSeconds;
   private boolean _ManagedServerNotificationsEnabled;
   private boolean _ManagementAppletCreateEnabled;
   private boolean _ManagementEJBEnabled;
   private boolean _PlatformMBeanServerEnabled;
   private boolean _PlatformMBeanServerUsed;
   private boolean _RuntimeMBeanServerEnabled;
   private static SchemaHelper2 _schemaHelper;

   public JMXMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMXMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMXMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isRuntimeMBeanServerEnabled() {
      return this._RuntimeMBeanServerEnabled;
   }

   public boolean isRuntimeMBeanServerEnabledInherited() {
      return false;
   }

   public boolean isRuntimeMBeanServerEnabledSet() {
      return this._isSet(10);
   }

   public void setRuntimeMBeanServerEnabled(boolean param0) {
      boolean _oldVal = this._RuntimeMBeanServerEnabled;
      this._RuntimeMBeanServerEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isDomainMBeanServerEnabled() {
      return this._DomainMBeanServerEnabled;
   }

   public boolean isDomainMBeanServerEnabledInherited() {
      return false;
   }

   public boolean isDomainMBeanServerEnabledSet() {
      return this._isSet(11);
   }

   public void setDomainMBeanServerEnabled(boolean param0) {
      boolean _oldVal = this._DomainMBeanServerEnabled;
      this._DomainMBeanServerEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isEditMBeanServerEnabled() {
      return this._EditMBeanServerEnabled;
   }

   public boolean isEditMBeanServerEnabledInherited() {
      return false;
   }

   public boolean isEditMBeanServerEnabledSet() {
      return this._isSet(12);
   }

   public void setEditMBeanServerEnabled(boolean param0) {
      boolean _oldVal = this._EditMBeanServerEnabled;
      this._EditMBeanServerEnabled = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isCompatibilityMBeanServerEnabled() {
      return this._CompatibilityMBeanServerEnabled;
   }

   public boolean isCompatibilityMBeanServerEnabledInherited() {
      return false;
   }

   public boolean isCompatibilityMBeanServerEnabledSet() {
      return this._isSet(13);
   }

   public void setCompatibilityMBeanServerEnabled(boolean param0) {
      boolean _oldVal = this._CompatibilityMBeanServerEnabled;
      this._CompatibilityMBeanServerEnabled = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isManagementEJBEnabled() {
      return this._ManagementEJBEnabled;
   }

   public boolean isManagementEJBEnabledInherited() {
      return false;
   }

   public boolean isManagementEJBEnabledSet() {
      return this._isSet(14);
   }

   public void setManagementEJBEnabled(boolean param0) {
      boolean _oldVal = this._ManagementEJBEnabled;
      this._ManagementEJBEnabled = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean isPlatformMBeanServerEnabled() {
      return this._PlatformMBeanServerEnabled;
   }

   public boolean isPlatformMBeanServerEnabledInherited() {
      return false;
   }

   public boolean isPlatformMBeanServerEnabledSet() {
      return this._isSet(15);
   }

   public void setPlatformMBeanServerEnabled(boolean param0) {
      boolean _oldVal = this._PlatformMBeanServerEnabled;
      this._PlatformMBeanServerEnabled = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getInvocationTimeoutSeconds() {
      return this._InvocationTimeoutSeconds;
   }

   public boolean isInvocationTimeoutSecondsInherited() {
      return false;
   }

   public boolean isInvocationTimeoutSecondsSet() {
      return this._isSet(16);
   }

   public void setInvocationTimeoutSeconds(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("InvocationTimeoutSeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._InvocationTimeoutSeconds;
      this._InvocationTimeoutSeconds = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isPlatformMBeanServerUsed() {
      if (!this._isSet(17)) {
         try {
            return !LegalHelper.versionEarlierThan(((DomainMBean)this.getParent()).getDomainVersion(), "10.3.3.0");
         } catch (NullPointerException var2) {
         }
      }

      return this._PlatformMBeanServerUsed;
   }

   public boolean isPlatformMBeanServerUsedInherited() {
      return false;
   }

   public boolean isPlatformMBeanServerUsedSet() {
      return this._isSet(17);
   }

   public void setPlatformMBeanServerUsed(boolean param0) {
      boolean _oldVal = this._PlatformMBeanServerUsed;
      this._PlatformMBeanServerUsed = param0;
      this._postSet(17, _oldVal, param0);
   }

   public boolean isManagedServerNotificationsEnabled() {
      return this._ManagedServerNotificationsEnabled;
   }

   public boolean isManagedServerNotificationsEnabledInherited() {
      return false;
   }

   public boolean isManagedServerNotificationsEnabledSet() {
      return this._isSet(18);
   }

   public void setManagedServerNotificationsEnabled(boolean param0) {
      boolean _oldVal = this._ManagedServerNotificationsEnabled;
      this._ManagedServerNotificationsEnabled = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isManagementAppletCreateEnabled() {
      return this._ManagementAppletCreateEnabled;
   }

   public boolean isManagementAppletCreateEnabledInherited() {
      return false;
   }

   public boolean isManagementAppletCreateEnabledSet() {
      return this._isSet(19);
   }

   public void setManagementAppletCreateEnabled(boolean param0) {
      boolean _oldVal = this._ManagementAppletCreateEnabled;
      this._ManagementAppletCreateEnabled = param0;
      this._postSet(19, _oldVal, param0);
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
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._InvocationTimeoutSeconds = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._CompatibilityMBeanServerEnabled = true;
               if (initOne) {
                  break;
               }
            case 11:
               this._DomainMBeanServerEnabled = true;
               if (initOne) {
                  break;
               }
            case 12:
               this._EditMBeanServerEnabled = true;
               if (initOne) {
                  break;
               }
            case 18:
               this._ManagedServerNotificationsEnabled = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._ManagementAppletCreateEnabled = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._ManagementEJBEnabled = true;
               if (initOne) {
                  break;
               }
            case 15:
               this._PlatformMBeanServerEnabled = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._PlatformMBeanServerUsed = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._RuntimeMBeanServerEnabled = true;
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
      return "JMX";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CompatibilityMBeanServerEnabled")) {
         oldVal = this._CompatibilityMBeanServerEnabled;
         this._CompatibilityMBeanServerEnabled = (Boolean)v;
         this._postSet(13, oldVal, this._CompatibilityMBeanServerEnabled);
      } else if (name.equals("DomainMBeanServerEnabled")) {
         oldVal = this._DomainMBeanServerEnabled;
         this._DomainMBeanServerEnabled = (Boolean)v;
         this._postSet(11, oldVal, this._DomainMBeanServerEnabled);
      } else if (name.equals("EditMBeanServerEnabled")) {
         oldVal = this._EditMBeanServerEnabled;
         this._EditMBeanServerEnabled = (Boolean)v;
         this._postSet(12, oldVal, this._EditMBeanServerEnabled);
      } else if (name.equals("InvocationTimeoutSeconds")) {
         int oldVal = this._InvocationTimeoutSeconds;
         this._InvocationTimeoutSeconds = (Integer)v;
         this._postSet(16, oldVal, this._InvocationTimeoutSeconds);
      } else if (name.equals("ManagedServerNotificationsEnabled")) {
         oldVal = this._ManagedServerNotificationsEnabled;
         this._ManagedServerNotificationsEnabled = (Boolean)v;
         this._postSet(18, oldVal, this._ManagedServerNotificationsEnabled);
      } else if (name.equals("ManagementAppletCreateEnabled")) {
         oldVal = this._ManagementAppletCreateEnabled;
         this._ManagementAppletCreateEnabled = (Boolean)v;
         this._postSet(19, oldVal, this._ManagementAppletCreateEnabled);
      } else if (name.equals("ManagementEJBEnabled")) {
         oldVal = this._ManagementEJBEnabled;
         this._ManagementEJBEnabled = (Boolean)v;
         this._postSet(14, oldVal, this._ManagementEJBEnabled);
      } else if (name.equals("PlatformMBeanServerEnabled")) {
         oldVal = this._PlatformMBeanServerEnabled;
         this._PlatformMBeanServerEnabled = (Boolean)v;
         this._postSet(15, oldVal, this._PlatformMBeanServerEnabled);
      } else if (name.equals("PlatformMBeanServerUsed")) {
         oldVal = this._PlatformMBeanServerUsed;
         this._PlatformMBeanServerUsed = (Boolean)v;
         this._postSet(17, oldVal, this._PlatformMBeanServerUsed);
      } else if (name.equals("RuntimeMBeanServerEnabled")) {
         oldVal = this._RuntimeMBeanServerEnabled;
         this._RuntimeMBeanServerEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._RuntimeMBeanServerEnabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CompatibilityMBeanServerEnabled")) {
         return new Boolean(this._CompatibilityMBeanServerEnabled);
      } else if (name.equals("DomainMBeanServerEnabled")) {
         return new Boolean(this._DomainMBeanServerEnabled);
      } else if (name.equals("EditMBeanServerEnabled")) {
         return new Boolean(this._EditMBeanServerEnabled);
      } else if (name.equals("InvocationTimeoutSeconds")) {
         return new Integer(this._InvocationTimeoutSeconds);
      } else if (name.equals("ManagedServerNotificationsEnabled")) {
         return new Boolean(this._ManagedServerNotificationsEnabled);
      } else if (name.equals("ManagementAppletCreateEnabled")) {
         return new Boolean(this._ManagementAppletCreateEnabled);
      } else if (name.equals("ManagementEJBEnabled")) {
         return new Boolean(this._ManagementEJBEnabled);
      } else if (name.equals("PlatformMBeanServerEnabled")) {
         return new Boolean(this._PlatformMBeanServerEnabled);
      } else if (name.equals("PlatformMBeanServerUsed")) {
         return new Boolean(this._PlatformMBeanServerUsed);
      } else {
         return name.equals("RuntimeMBeanServerEnabled") ? new Boolean(this._RuntimeMBeanServerEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 21:
               if (s.equals("managementejb-enabled")) {
                  return 14;
               }
            case 22:
            case 23:
            case 24:
            case 30:
            case 31:
            case 33:
            case 35:
            default:
               break;
            case 25:
               if (s.equals("editm-bean-server-enabled")) {
                  return 12;
               }
               break;
            case 26:
               if (s.equals("invocation-timeout-seconds")) {
                  return 16;
               }

               if (s.equals("platformm-bean-server-used")) {
                  return 17;
               }
               break;
            case 27:
               if (s.equals("domainm-bean-server-enabled")) {
                  return 11;
               }
               break;
            case 28:
               if (s.equals("runtimem-bean-server-enabled")) {
                  return 10;
               }
               break;
            case 29:
               if (s.equals("platformm-bean-server-enabled")) {
                  return 15;
               }
               break;
            case 32:
               if (s.equals("management-applet-create-enabled")) {
                  return 19;
               }
               break;
            case 34:
               if (s.equals("compatibilitym-bean-server-enabled")) {
                  return 13;
               }
               break;
            case 36:
               if (s.equals("managed-server-notifications-enabled")) {
                  return 18;
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
               return "runtimem-bean-server-enabled";
            case 11:
               return "domainm-bean-server-enabled";
            case 12:
               return "editm-bean-server-enabled";
            case 13:
               return "compatibilitym-bean-server-enabled";
            case 14:
               return "managementejb-enabled";
            case 15:
               return "platformm-bean-server-enabled";
            case 16:
               return "invocation-timeout-seconds";
            case 17:
               return "platformm-bean-server-used";
            case 18:
               return "managed-server-notifications-enabled";
            case 19:
               return "management-applet-create-enabled";
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
      private JMXMBeanImpl bean;

      protected Helper(JMXMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "RuntimeMBeanServerEnabled";
            case 11:
               return "DomainMBeanServerEnabled";
            case 12:
               return "EditMBeanServerEnabled";
            case 13:
               return "CompatibilityMBeanServerEnabled";
            case 14:
               return "ManagementEJBEnabled";
            case 15:
               return "PlatformMBeanServerEnabled";
            case 16:
               return "InvocationTimeoutSeconds";
            case 17:
               return "PlatformMBeanServerUsed";
            case 18:
               return "ManagedServerNotificationsEnabled";
            case 19:
               return "ManagementAppletCreateEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("InvocationTimeoutSeconds")) {
            return 16;
         } else if (propName.equals("CompatibilityMBeanServerEnabled")) {
            return 13;
         } else if (propName.equals("DomainMBeanServerEnabled")) {
            return 11;
         } else if (propName.equals("EditMBeanServerEnabled")) {
            return 12;
         } else if (propName.equals("ManagedServerNotificationsEnabled")) {
            return 18;
         } else if (propName.equals("ManagementAppletCreateEnabled")) {
            return 19;
         } else if (propName.equals("ManagementEJBEnabled")) {
            return 14;
         } else if (propName.equals("PlatformMBeanServerEnabled")) {
            return 15;
         } else if (propName.equals("PlatformMBeanServerUsed")) {
            return 17;
         } else {
            return propName.equals("RuntimeMBeanServerEnabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isInvocationTimeoutSecondsSet()) {
               buf.append("InvocationTimeoutSeconds");
               buf.append(String.valueOf(this.bean.getInvocationTimeoutSeconds()));
            }

            if (this.bean.isCompatibilityMBeanServerEnabledSet()) {
               buf.append("CompatibilityMBeanServerEnabled");
               buf.append(String.valueOf(this.bean.isCompatibilityMBeanServerEnabled()));
            }

            if (this.bean.isDomainMBeanServerEnabledSet()) {
               buf.append("DomainMBeanServerEnabled");
               buf.append(String.valueOf(this.bean.isDomainMBeanServerEnabled()));
            }

            if (this.bean.isEditMBeanServerEnabledSet()) {
               buf.append("EditMBeanServerEnabled");
               buf.append(String.valueOf(this.bean.isEditMBeanServerEnabled()));
            }

            if (this.bean.isManagedServerNotificationsEnabledSet()) {
               buf.append("ManagedServerNotificationsEnabled");
               buf.append(String.valueOf(this.bean.isManagedServerNotificationsEnabled()));
            }

            if (this.bean.isManagementAppletCreateEnabledSet()) {
               buf.append("ManagementAppletCreateEnabled");
               buf.append(String.valueOf(this.bean.isManagementAppletCreateEnabled()));
            }

            if (this.bean.isManagementEJBEnabledSet()) {
               buf.append("ManagementEJBEnabled");
               buf.append(String.valueOf(this.bean.isManagementEJBEnabled()));
            }

            if (this.bean.isPlatformMBeanServerEnabledSet()) {
               buf.append("PlatformMBeanServerEnabled");
               buf.append(String.valueOf(this.bean.isPlatformMBeanServerEnabled()));
            }

            if (this.bean.isPlatformMBeanServerUsedSet()) {
               buf.append("PlatformMBeanServerUsed");
               buf.append(String.valueOf(this.bean.isPlatformMBeanServerUsed()));
            }

            if (this.bean.isRuntimeMBeanServerEnabledSet()) {
               buf.append("RuntimeMBeanServerEnabled");
               buf.append(String.valueOf(this.bean.isRuntimeMBeanServerEnabled()));
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
            JMXMBeanImpl otherTyped = (JMXMBeanImpl)other;
            this.computeDiff("InvocationTimeoutSeconds", this.bean.getInvocationTimeoutSeconds(), otherTyped.getInvocationTimeoutSeconds(), false);
            this.computeDiff("CompatibilityMBeanServerEnabled", this.bean.isCompatibilityMBeanServerEnabled(), otherTyped.isCompatibilityMBeanServerEnabled(), true);
            this.computeDiff("DomainMBeanServerEnabled", this.bean.isDomainMBeanServerEnabled(), otherTyped.isDomainMBeanServerEnabled(), true);
            this.computeDiff("EditMBeanServerEnabled", this.bean.isEditMBeanServerEnabled(), otherTyped.isEditMBeanServerEnabled(), true);
            this.computeDiff("ManagedServerNotificationsEnabled", this.bean.isManagedServerNotificationsEnabled(), otherTyped.isManagedServerNotificationsEnabled(), false);
            this.computeDiff("ManagementAppletCreateEnabled", this.bean.isManagementAppletCreateEnabled(), otherTyped.isManagementAppletCreateEnabled(), true);
            this.computeDiff("ManagementEJBEnabled", this.bean.isManagementEJBEnabled(), otherTyped.isManagementEJBEnabled(), true);
            this.computeDiff("PlatformMBeanServerEnabled", this.bean.isPlatformMBeanServerEnabled(), otherTyped.isPlatformMBeanServerEnabled(), false);
            this.computeDiff("PlatformMBeanServerUsed", this.bean.isPlatformMBeanServerUsed(), otherTyped.isPlatformMBeanServerUsed(), false);
            this.computeDiff("RuntimeMBeanServerEnabled", this.bean.isRuntimeMBeanServerEnabled(), otherTyped.isRuntimeMBeanServerEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMXMBeanImpl original = (JMXMBeanImpl)event.getSourceBean();
            JMXMBeanImpl proposed = (JMXMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InvocationTimeoutSeconds")) {
                  original.setInvocationTimeoutSeconds(proposed.getInvocationTimeoutSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("CompatibilityMBeanServerEnabled")) {
                  original.setCompatibilityMBeanServerEnabled(proposed.isCompatibilityMBeanServerEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("DomainMBeanServerEnabled")) {
                  original.setDomainMBeanServerEnabled(proposed.isDomainMBeanServerEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("EditMBeanServerEnabled")) {
                  original.setEditMBeanServerEnabled(proposed.isEditMBeanServerEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("ManagedServerNotificationsEnabled")) {
                  original.setManagedServerNotificationsEnabled(proposed.isManagedServerNotificationsEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("ManagementAppletCreateEnabled")) {
                  original.setManagementAppletCreateEnabled(proposed.isManagementAppletCreateEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("ManagementEJBEnabled")) {
                  original.setManagementEJBEnabled(proposed.isManagementEJBEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("PlatformMBeanServerEnabled")) {
                  original.setPlatformMBeanServerEnabled(proposed.isPlatformMBeanServerEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PlatformMBeanServerUsed")) {
                  original.setPlatformMBeanServerUsed(proposed.isPlatformMBeanServerUsed());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("RuntimeMBeanServerEnabled")) {
                  original.setRuntimeMBeanServerEnabled(proposed.isRuntimeMBeanServerEnabled());
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
            JMXMBeanImpl copy = (JMXMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("InvocationTimeoutSeconds")) && this.bean.isInvocationTimeoutSecondsSet()) {
               copy.setInvocationTimeoutSeconds(this.bean.getInvocationTimeoutSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("CompatibilityMBeanServerEnabled")) && this.bean.isCompatibilityMBeanServerEnabledSet()) {
               copy.setCompatibilityMBeanServerEnabled(this.bean.isCompatibilityMBeanServerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("DomainMBeanServerEnabled")) && this.bean.isDomainMBeanServerEnabledSet()) {
               copy.setDomainMBeanServerEnabled(this.bean.isDomainMBeanServerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("EditMBeanServerEnabled")) && this.bean.isEditMBeanServerEnabledSet()) {
               copy.setEditMBeanServerEnabled(this.bean.isEditMBeanServerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagedServerNotificationsEnabled")) && this.bean.isManagedServerNotificationsEnabledSet()) {
               copy.setManagedServerNotificationsEnabled(this.bean.isManagedServerNotificationsEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagementAppletCreateEnabled")) && this.bean.isManagementAppletCreateEnabledSet()) {
               copy.setManagementAppletCreateEnabled(this.bean.isManagementAppletCreateEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ManagementEJBEnabled")) && this.bean.isManagementEJBEnabledSet()) {
               copy.setManagementEJBEnabled(this.bean.isManagementEJBEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PlatformMBeanServerEnabled")) && this.bean.isPlatformMBeanServerEnabledSet()) {
               copy.setPlatformMBeanServerEnabled(this.bean.isPlatformMBeanServerEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PlatformMBeanServerUsed")) && this.bean.isPlatformMBeanServerUsedSet()) {
               copy.setPlatformMBeanServerUsed(this.bean.isPlatformMBeanServerUsed());
            }

            if ((excludeProps == null || !excludeProps.contains("RuntimeMBeanServerEnabled")) && this.bean.isRuntimeMBeanServerEnabledSet()) {
               copy.setRuntimeMBeanServerEnabled(this.bean.isRuntimeMBeanServerEnabled());
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
