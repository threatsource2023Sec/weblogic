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
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class ManagedExternalServerMBeanImpl extends ConfigurationMBeanImpl implements ManagedExternalServerMBean, Serializable {
   private boolean _AutoRestart;
   private MachineMBean _Machine;
   private ManagedExternalServerStartMBean _ManagedExternalServerStart;
   private String _ManagedExternalType;
   private int _NMSocketCreateTimeoutInMillis;
   private String _Name;
   private int _RestartDelaySeconds;
   private int _RestartIntervalSeconds;
   private int _RestartMax;
   private static SchemaHelper2 _schemaHelper;

   public ManagedExternalServerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ManagedExternalServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ManagedExternalServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      ConfigurationValidator.validateNameUsedInDirectory(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
   }

   public MachineMBean getMachine() {
      return this._Machine;
   }

   public String getMachineAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getMachine();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isMachineInherited() {
      return false;
   }

   public boolean isMachineSet() {
      return this._isSet(10);
   }

   public void setMachineAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, MachineMBean.class, new ReferenceManager.Resolver(this, 10) {
            public void resolveReference(Object value) {
               try {
                  ManagedExternalServerMBeanImpl.this.setMachine((MachineMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         MachineMBean _oldVal = this._Machine;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Machine);
      }

   }

   public void setMachine(MachineMBean param0) throws InvalidAttributeValueException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ManagedExternalServerMBeanImpl.this.getMachine();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      MachineMBean _oldVal = this._Machine;
      this._Machine = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean getAutoRestart() {
      return this._AutoRestart;
   }

   public boolean isAutoRestartInherited() {
      return false;
   }

   public boolean isAutoRestartSet() {
      return this._isSet(11);
   }

   public void setAutoRestart(boolean param0) {
      boolean _oldVal = this._AutoRestart;
      this._AutoRestart = param0;
      this._postSet(11, _oldVal, param0);
   }

   public int getRestartIntervalSeconds() {
      return this._RestartIntervalSeconds;
   }

   public boolean isRestartIntervalSecondsInherited() {
      return false;
   }

   public boolean isRestartIntervalSecondsSet() {
      return this._isSet(12);
   }

   public void setRestartIntervalSeconds(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RestartIntervalSeconds", (long)param0, 300L, 2147483647L);
      int _oldVal = this._RestartIntervalSeconds;
      this._RestartIntervalSeconds = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getRestartMax() {
      return this._RestartMax;
   }

   public boolean isRestartMaxInherited() {
      return false;
   }

   public boolean isRestartMaxSet() {
      return this._isSet(13);
   }

   public void setRestartMax(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RestartMax", (long)param0, 0L, 2147483647L);
      int _oldVal = this._RestartMax;
      this._RestartMax = param0;
      this._postSet(13, _oldVal, param0);
   }

   public int getRestartDelaySeconds() {
      return this._RestartDelaySeconds;
   }

   public boolean isRestartDelaySecondsInherited() {
      return false;
   }

   public boolean isRestartDelaySecondsSet() {
      return this._isSet(14);
   }

   public void setRestartDelaySeconds(int param0) throws InvalidAttributeValueException {
      LegalChecks.checkInRange("RestartDelaySeconds", (long)param0, 0L, 2147483647L);
      int _oldVal = this._RestartDelaySeconds;
      this._RestartDelaySeconds = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getNMSocketCreateTimeoutInMillis() {
      return this._NMSocketCreateTimeoutInMillis;
   }

   public boolean isNMSocketCreateTimeoutInMillisInherited() {
      return false;
   }

   public boolean isNMSocketCreateTimeoutInMillisSet() {
      return this._isSet(15);
   }

   public void setNMSocketCreateTimeoutInMillis(int param0) throws InvalidAttributeValueException, DistributedManagementException {
      LegalChecks.checkMin("NMSocketCreateTimeoutInMillis", param0, 0);
      int _oldVal = this._NMSocketCreateTimeoutInMillis;
      this._NMSocketCreateTimeoutInMillis = param0;
      this._postSet(15, _oldVal, param0);
   }

   public ManagedExternalServerStartMBean getManagedExternalServerStart() {
      return this._ManagedExternalServerStart;
   }

   public boolean isManagedExternalServerStartInherited() {
      return false;
   }

   public boolean isManagedExternalServerStartSet() {
      return this._isSet(16);
   }

   public void setManagedExternalServerStart(ManagedExternalServerStartMBean param0) throws InvalidAttributeValueException {
      this._ManagedExternalServerStart = param0;
   }

   public String getManagedExternalType() {
      return this._ManagedExternalType;
   }

   public boolean isManagedExternalTypeInherited() {
      return false;
   }

   public boolean isManagedExternalTypeSet() {
      return this._isSet(17);
   }

   public void setManagedExternalType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._ManagedExternalType = param0;
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
               this._AutoRestart = true;
               if (initOne) {
                  break;
               }
            case 10:
               this._Machine = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._ManagedExternalServerStart = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._ManagedExternalType = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._NMSocketCreateTimeoutInMillis = 180000;
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._RestartDelaySeconds = 0;
               if (initOne) {
                  break;
               }
            case 12:
               this._RestartIntervalSeconds = 3600;
               if (initOne) {
                  break;
               }
            case 13:
               this._RestartMax = 2;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
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
      return "ManagedExternalServer";
   }

   public void putValue(String name, Object v) {
      if (name.equals("AutoRestart")) {
         boolean oldVal = this._AutoRestart;
         this._AutoRestart = (Boolean)v;
         this._postSet(11, oldVal, this._AutoRestart);
      } else if (name.equals("Machine")) {
         MachineMBean oldVal = this._Machine;
         this._Machine = (MachineMBean)v;
         this._postSet(10, oldVal, this._Machine);
      } else if (name.equals("ManagedExternalServerStart")) {
         ManagedExternalServerStartMBean oldVal = this._ManagedExternalServerStart;
         this._ManagedExternalServerStart = (ManagedExternalServerStartMBean)v;
         this._postSet(16, oldVal, this._ManagedExternalServerStart);
      } else {
         String oldVal;
         if (name.equals("ManagedExternalType")) {
            oldVal = this._ManagedExternalType;
            this._ManagedExternalType = (String)v;
            this._postSet(17, oldVal, this._ManagedExternalType);
         } else {
            int oldVal;
            if (name.equals("NMSocketCreateTimeoutInMillis")) {
               oldVal = this._NMSocketCreateTimeoutInMillis;
               this._NMSocketCreateTimeoutInMillis = (Integer)v;
               this._postSet(15, oldVal, this._NMSocketCreateTimeoutInMillis);
            } else if (name.equals("Name")) {
               oldVal = this._Name;
               this._Name = (String)v;
               this._postSet(2, oldVal, this._Name);
            } else if (name.equals("RestartDelaySeconds")) {
               oldVal = this._RestartDelaySeconds;
               this._RestartDelaySeconds = (Integer)v;
               this._postSet(14, oldVal, this._RestartDelaySeconds);
            } else if (name.equals("RestartIntervalSeconds")) {
               oldVal = this._RestartIntervalSeconds;
               this._RestartIntervalSeconds = (Integer)v;
               this._postSet(12, oldVal, this._RestartIntervalSeconds);
            } else if (name.equals("RestartMax")) {
               oldVal = this._RestartMax;
               this._RestartMax = (Integer)v;
               this._postSet(13, oldVal, this._RestartMax);
            } else {
               super.putValue(name, v);
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AutoRestart")) {
         return new Boolean(this._AutoRestart);
      } else if (name.equals("Machine")) {
         return this._Machine;
      } else if (name.equals("ManagedExternalServerStart")) {
         return this._ManagedExternalServerStart;
      } else if (name.equals("ManagedExternalType")) {
         return this._ManagedExternalType;
      } else if (name.equals("NMSocketCreateTimeoutInMillis")) {
         return new Integer(this._NMSocketCreateTimeoutInMillis);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("RestartDelaySeconds")) {
         return new Integer(this._RestartDelaySeconds);
      } else if (name.equals("RestartIntervalSeconds")) {
         return new Integer(this._RestartIntervalSeconds);
      } else {
         return name.equals("RestartMax") ? new Integer(this._RestartMax) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 7:
               if (s.equals("machine")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("restart-max")) {
                  return 13;
               }
               break;
            case 12:
               if (s.equals("auto-restart")) {
                  return 11;
               }
               break;
            case 21:
               if (s.equals("managed-external-type")) {
                  return 17;
               }

               if (s.equals("restart-delay-seconds")) {
                  return 14;
               }
               break;
            case 24:
               if (s.equals("restart-interval-seconds")) {
                  return 12;
               }
               break;
            case 29:
               if (s.equals("managed-external-server-start")) {
                  return 16;
               }
               break;
            case 34:
               if (s.equals("nm-socket-create-timeout-in-millis")) {
                  return 15;
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
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
               return super.getElementName(propIndex);
            case 10:
               return "machine";
            case 11:
               return "auto-restart";
            case 12:
               return "restart-interval-seconds";
            case 13:
               return "restart-max";
            case 14:
               return "restart-delay-seconds";
            case 15:
               return "nm-socket-create-timeout-in-millis";
            case 16:
               return "managed-external-server-start";
            case 17:
               return "managed-external-type";
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
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private ManagedExternalServerMBeanImpl bean;

      protected Helper(ManagedExternalServerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            default:
               return super.getPropertyName(propIndex);
            case 10:
               return "Machine";
            case 11:
               return "AutoRestart";
            case 12:
               return "RestartIntervalSeconds";
            case 13:
               return "RestartMax";
            case 14:
               return "RestartDelaySeconds";
            case 15:
               return "NMSocketCreateTimeoutInMillis";
            case 16:
               return "ManagedExternalServerStart";
            case 17:
               return "ManagedExternalType";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AutoRestart")) {
            return 11;
         } else if (propName.equals("Machine")) {
            return 10;
         } else if (propName.equals("ManagedExternalServerStart")) {
            return 16;
         } else if (propName.equals("ManagedExternalType")) {
            return 17;
         } else if (propName.equals("NMSocketCreateTimeoutInMillis")) {
            return 15;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("RestartDelaySeconds")) {
            return 14;
         } else if (propName.equals("RestartIntervalSeconds")) {
            return 12;
         } else {
            return propName.equals("RestartMax") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isAutoRestartSet()) {
               buf.append("AutoRestart");
               buf.append(String.valueOf(this.bean.getAutoRestart()));
            }

            if (this.bean.isMachineSet()) {
               buf.append("Machine");
               buf.append(String.valueOf(this.bean.getMachine()));
            }

            if (this.bean.isManagedExternalServerStartSet()) {
               buf.append("ManagedExternalServerStart");
               buf.append(String.valueOf(this.bean.getManagedExternalServerStart()));
            }

            if (this.bean.isManagedExternalTypeSet()) {
               buf.append("ManagedExternalType");
               buf.append(String.valueOf(this.bean.getManagedExternalType()));
            }

            if (this.bean.isNMSocketCreateTimeoutInMillisSet()) {
               buf.append("NMSocketCreateTimeoutInMillis");
               buf.append(String.valueOf(this.bean.getNMSocketCreateTimeoutInMillis()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isRestartDelaySecondsSet()) {
               buf.append("RestartDelaySeconds");
               buf.append(String.valueOf(this.bean.getRestartDelaySeconds()));
            }

            if (this.bean.isRestartIntervalSecondsSet()) {
               buf.append("RestartIntervalSeconds");
               buf.append(String.valueOf(this.bean.getRestartIntervalSeconds()));
            }

            if (this.bean.isRestartMaxSet()) {
               buf.append("RestartMax");
               buf.append(String.valueOf(this.bean.getRestartMax()));
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
            ManagedExternalServerMBeanImpl otherTyped = (ManagedExternalServerMBeanImpl)other;
            this.computeDiff("AutoRestart", this.bean.getAutoRestart(), otherTyped.getAutoRestart(), true);
            this.computeDiff("Machine", this.bean.getMachine(), otherTyped.getMachine(), false);
            this.computeDiff("NMSocketCreateTimeoutInMillis", this.bean.getNMSocketCreateTimeoutInMillis(), otherTyped.getNMSocketCreateTimeoutInMillis(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("RestartDelaySeconds", this.bean.getRestartDelaySeconds(), otherTyped.getRestartDelaySeconds(), true);
            this.computeDiff("RestartIntervalSeconds", this.bean.getRestartIntervalSeconds(), otherTyped.getRestartIntervalSeconds(), true);
            this.computeDiff("RestartMax", this.bean.getRestartMax(), otherTyped.getRestartMax(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ManagedExternalServerMBeanImpl original = (ManagedExternalServerMBeanImpl)event.getSourceBean();
            ManagedExternalServerMBeanImpl proposed = (ManagedExternalServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AutoRestart")) {
                  original.setAutoRestart(proposed.getAutoRestart());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Machine")) {
                  original.setMachineAsString(proposed.getMachineAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (!prop.equals("ManagedExternalServerStart") && !prop.equals("ManagedExternalType")) {
                  if (prop.equals("NMSocketCreateTimeoutInMillis")) {
                     original.setNMSocketCreateTimeoutInMillis(proposed.getNMSocketCreateTimeoutInMillis());
                     original._conditionalUnset(update.isUnsetUpdate(), 15);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("RestartDelaySeconds")) {
                     original.setRestartDelaySeconds(proposed.getRestartDelaySeconds());
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  } else if (prop.equals("RestartIntervalSeconds")) {
                     original.setRestartIntervalSeconds(proposed.getRestartIntervalSeconds());
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  } else if (prop.equals("RestartMax")) {
                     original.setRestartMax(proposed.getRestartMax());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else {
                     super.applyPropertyUpdate(event, update);
                  }
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
            ManagedExternalServerMBeanImpl copy = (ManagedExternalServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AutoRestart")) && this.bean.isAutoRestartSet()) {
               copy.setAutoRestart(this.bean.getAutoRestart());
            }

            if ((excludeProps == null || !excludeProps.contains("Machine")) && this.bean.isMachineSet()) {
               copy._unSet(copy, 10);
               copy.setMachineAsString(this.bean.getMachineAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("NMSocketCreateTimeoutInMillis")) && this.bean.isNMSocketCreateTimeoutInMillisSet()) {
               copy.setNMSocketCreateTimeoutInMillis(this.bean.getNMSocketCreateTimeoutInMillis());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartDelaySeconds")) && this.bean.isRestartDelaySecondsSet()) {
               copy.setRestartDelaySeconds(this.bean.getRestartDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartIntervalSeconds")) && this.bean.isRestartIntervalSecondsSet()) {
               copy.setRestartIntervalSeconds(this.bean.getRestartIntervalSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("RestartMax")) && this.bean.isRestartMaxSet()) {
               copy.setRestartMax(this.bean.getRestartMax());
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
         this.inferSubTree(this.bean.getMachine(), clazz, annotation);
         this.inferSubTree(this.bean.getManagedExternalServerStart(), clazz, annotation);
      }
   }
}
