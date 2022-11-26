package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WeblogicCoherenceBeanImpl extends SettableBeanImpl implements WeblogicCoherenceBean, Serializable {
   private CoherenceAddressProvidersBean _CoherenceAddressProviders;
   private CoherenceClusterParamsBean _CoherenceClusterParams;
   private CoherenceFederationParamsBean _CoherenceFederationParams;
   private CoherenceLoggingParamsBean _CoherenceLoggingParams;
   private CoherencePersistenceParamsBean _CoherencePersistenceParams;
   private long _CustomClusterConfigurationFileLastUpdatedTimestamp;
   private String _CustomClusterConfigurationFileName;
   private String _Name;
   private String _Version;
   private static SchemaHelper2 _schemaHelper;

   public WeblogicCoherenceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicCoherenceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WeblogicCoherenceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public CoherenceClusterParamsBean getCoherenceClusterParams() {
      return this._CoherenceClusterParams;
   }

   public boolean isCoherenceClusterParamsInherited() {
      return false;
   }

   public boolean isCoherenceClusterParamsSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceClusterParams());
   }

   public void setCoherenceClusterParams(CoherenceClusterParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      CoherenceClusterParamsBean _oldVal = this._CoherenceClusterParams;
      this._CoherenceClusterParams = param0;
      this._postSet(1, _oldVal, param0);
   }

   public CoherenceLoggingParamsBean getCoherenceLoggingParams() {
      return this._CoherenceLoggingParams;
   }

   public boolean isCoherenceLoggingParamsInherited() {
      return false;
   }

   public boolean isCoherenceLoggingParamsSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceLoggingParams());
   }

   public void setCoherenceLoggingParams(CoherenceLoggingParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      CoherenceLoggingParamsBean _oldVal = this._CoherenceLoggingParams;
      this._CoherenceLoggingParams = param0;
      this._postSet(2, _oldVal, param0);
   }

   public CoherenceAddressProvidersBean getCoherenceAddressProviders() {
      return this._CoherenceAddressProviders;
   }

   public boolean isCoherenceAddressProvidersInherited() {
      return false;
   }

   public boolean isCoherenceAddressProvidersSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceAddressProviders());
   }

   public void setCoherenceAddressProviders(CoherenceAddressProvidersBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      CoherenceAddressProvidersBean _oldVal = this._CoherenceAddressProviders;
      this._CoherenceAddressProviders = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getCustomClusterConfigurationFileName() {
      return this._CustomClusterConfigurationFileName;
   }

   public boolean isCustomClusterConfigurationFileNameInherited() {
      return false;
   }

   public boolean isCustomClusterConfigurationFileNameSet() {
      return this._isSet(4);
   }

   public void setCustomClusterConfigurationFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CustomClusterConfigurationFileName;
      this._CustomClusterConfigurationFileName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public long getCustomClusterConfigurationFileLastUpdatedTimestamp() {
      return this._CustomClusterConfigurationFileLastUpdatedTimestamp;
   }

   public boolean isCustomClusterConfigurationFileLastUpdatedTimestampInherited() {
      return false;
   }

   public boolean isCustomClusterConfigurationFileLastUpdatedTimestampSet() {
      return this._isSet(5);
   }

   public void setCustomClusterConfigurationFileLastUpdatedTimestamp(long param0) {
      long _oldVal = this._CustomClusterConfigurationFileLastUpdatedTimestamp;
      this._CustomClusterConfigurationFileLastUpdatedTimestamp = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getVersion() {
      return this._Version;
   }

   public boolean isVersionInherited() {
      return false;
   }

   public boolean isVersionSet() {
      return this._isSet(6);
   }

   public void setVersion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Version;
      this._Version = param0;
      this._postSet(6, _oldVal, param0);
   }

   public CoherencePersistenceParamsBean getCoherencePersistenceParams() {
      return this._CoherencePersistenceParams;
   }

   public boolean isCoherencePersistenceParamsInherited() {
      return false;
   }

   public boolean isCoherencePersistenceParamsSet() {
      return this._isSet(7) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherencePersistenceParams());
   }

   public void setCoherencePersistenceParams(CoherencePersistenceParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 7)) {
         this._postCreate(_child);
      }

      CoherencePersistenceParamsBean _oldVal = this._CoherencePersistenceParams;
      this._CoherencePersistenceParams = param0;
      this._postSet(7, _oldVal, param0);
   }

   public CoherenceFederationParamsBean getCoherenceFederationParams() {
      return this._CoherenceFederationParams;
   }

   public boolean isCoherenceFederationParamsInherited() {
      return false;
   }

   public boolean isCoherenceFederationParamsSet() {
      return this._isSet(8) || this._isAnythingSet((AbstractDescriptorBean)this.getCoherenceFederationParams());
   }

   public void setCoherenceFederationParams(CoherenceFederationParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 8)) {
         this._postCreate(_child);
      }

      CoherenceFederationParamsBean _oldVal = this._CoherenceFederationParams;
      this._CoherenceFederationParams = param0;
      this._postSet(8, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      CoherenceClusterValidator.validateCoherenceCluster(this);
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
      return super._isAnythingSet() || this.isCoherenceAddressProvidersSet() || this.isCoherenceClusterParamsSet() || this.isCoherenceFederationParamsSet() || this.isCoherenceLoggingParamsSet() || this.isCoherencePersistenceParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._CoherenceAddressProviders = new CoherenceAddressProvidersBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._CoherenceAddressProviders);
               if (initOne) {
                  break;
               }
            case 1:
               this._CoherenceClusterParams = new CoherenceClusterParamsBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._CoherenceClusterParams);
               if (initOne) {
                  break;
               }
            case 8:
               this._CoherenceFederationParams = new CoherenceFederationParamsBeanImpl(this, 8);
               this._postCreate((AbstractDescriptorBean)this._CoherenceFederationParams);
               if (initOne) {
                  break;
               }
            case 2:
               this._CoherenceLoggingParams = new CoherenceLoggingParamsBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._CoherenceLoggingParams);
               if (initOne) {
                  break;
               }
            case 7:
               this._CoherencePersistenceParams = new CoherencePersistenceParamsBeanImpl(this, 7);
               this._postCreate((AbstractDescriptorBean)this._CoherencePersistenceParams);
               if (initOne) {
                  break;
               }
            case 5:
               this._CustomClusterConfigurationFileLastUpdatedTimestamp = 0L;
               if (initOne) {
                  break;
               }
            case 4:
               this._CustomClusterConfigurationFileName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Version = null;
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
      return "weblogic-coherence.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-coherence";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
               break;
            case 7:
               if (s.equals("version")) {
                  return 6;
               }
               break;
            case 24:
               if (s.equals("coherence-cluster-params")) {
                  return 1;
               }

               if (s.equals("coherence-logging-params")) {
                  return 2;
               }
               break;
            case 27:
               if (s.equals("coherence-address-providers")) {
                  return 3;
               }

               if (s.equals("coherence-federation-params")) {
                  return 8;
               }
               break;
            case 28:
               if (s.equals("coherence-persistence-params")) {
                  return 7;
               }
               break;
            case 38:
               if (s.equals("custom-cluster-configuration-file-name")) {
                  return 4;
               }
               break;
            case 56:
               if (s.equals("custom-cluster-configuration-file-last-updated-timestamp")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new CoherenceClusterParamsBeanImpl.SchemaHelper2();
            case 2:
               return new CoherenceLoggingParamsBeanImpl.SchemaHelper2();
            case 3:
               return new CoherenceAddressProvidersBeanImpl.SchemaHelper2();
            case 4:
            case 5:
            case 6:
            default:
               return super.getSchemaHelper(propIndex);
            case 7:
               return new CoherencePersistenceParamsBeanImpl.SchemaHelper2();
            case 8:
               return new CoherenceFederationParamsBeanImpl.SchemaHelper2();
         }
      }

      public String getRootElementName() {
         return "weblogic-coherence";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "coherence-cluster-params";
            case 2:
               return "coherence-logging-params";
            case 3:
               return "coherence-address-providers";
            case 4:
               return "custom-cluster-configuration-file-name";
            case 5:
               return "custom-cluster-configuration-file-last-updated-timestamp";
            case 6:
               return "version";
            case 7:
               return "coherence-persistence-params";
            case 8:
               return "coherence-federation-params";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
            case 5:
            case 6:
            default:
               return super.isBean(propIndex);
            case 7:
               return true;
            case 8:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends SettableBeanImpl.Helper {
      private WeblogicCoherenceBeanImpl bean;

      protected Helper(WeblogicCoherenceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "CoherenceClusterParams";
            case 2:
               return "CoherenceLoggingParams";
            case 3:
               return "CoherenceAddressProviders";
            case 4:
               return "CustomClusterConfigurationFileName";
            case 5:
               return "CustomClusterConfigurationFileLastUpdatedTimestamp";
            case 6:
               return "Version";
            case 7:
               return "CoherencePersistenceParams";
            case 8:
               return "CoherenceFederationParams";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CoherenceAddressProviders")) {
            return 3;
         } else if (propName.equals("CoherenceClusterParams")) {
            return 1;
         } else if (propName.equals("CoherenceFederationParams")) {
            return 8;
         } else if (propName.equals("CoherenceLoggingParams")) {
            return 2;
         } else if (propName.equals("CoherencePersistenceParams")) {
            return 7;
         } else if (propName.equals("CustomClusterConfigurationFileLastUpdatedTimestamp")) {
            return 5;
         } else if (propName.equals("CustomClusterConfigurationFileName")) {
            return 4;
         } else if (propName.equals("Name")) {
            return 0;
         } else {
            return propName.equals("Version") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCoherenceAddressProviders() != null) {
            iterators.add(new ArrayIterator(new CoherenceAddressProvidersBean[]{this.bean.getCoherenceAddressProviders()}));
         }

         if (this.bean.getCoherenceClusterParams() != null) {
            iterators.add(new ArrayIterator(new CoherenceClusterParamsBean[]{this.bean.getCoherenceClusterParams()}));
         }

         if (this.bean.getCoherenceFederationParams() != null) {
            iterators.add(new ArrayIterator(new CoherenceFederationParamsBean[]{this.bean.getCoherenceFederationParams()}));
         }

         if (this.bean.getCoherenceLoggingParams() != null) {
            iterators.add(new ArrayIterator(new CoherenceLoggingParamsBean[]{this.bean.getCoherenceLoggingParams()}));
         }

         if (this.bean.getCoherencePersistenceParams() != null) {
            iterators.add(new ArrayIterator(new CoherencePersistenceParamsBean[]{this.bean.getCoherencePersistenceParams()}));
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
            childValue = this.computeChildHashValue(this.bean.getCoherenceAddressProviders());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceClusterParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceFederationParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherenceLoggingParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getCoherencePersistenceParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCustomClusterConfigurationFileLastUpdatedTimestampSet()) {
               buf.append("CustomClusterConfigurationFileLastUpdatedTimestamp");
               buf.append(String.valueOf(this.bean.getCustomClusterConfigurationFileLastUpdatedTimestamp()));
            }

            if (this.bean.isCustomClusterConfigurationFileNameSet()) {
               buf.append("CustomClusterConfigurationFileName");
               buf.append(String.valueOf(this.bean.getCustomClusterConfigurationFileName()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isVersionSet()) {
               buf.append("Version");
               buf.append(String.valueOf(this.bean.getVersion()));
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
            WeblogicCoherenceBeanImpl otherTyped = (WeblogicCoherenceBeanImpl)other;
            this.computeSubDiff("CoherenceAddressProviders", this.bean.getCoherenceAddressProviders(), otherTyped.getCoherenceAddressProviders());
            this.computeSubDiff("CoherenceClusterParams", this.bean.getCoherenceClusterParams(), otherTyped.getCoherenceClusterParams());
            this.computeSubDiff("CoherenceFederationParams", this.bean.getCoherenceFederationParams(), otherTyped.getCoherenceFederationParams());
            this.computeSubDiff("CoherenceLoggingParams", this.bean.getCoherenceLoggingParams(), otherTyped.getCoherenceLoggingParams());
            this.computeSubDiff("CoherencePersistenceParams", this.bean.getCoherencePersistenceParams(), otherTyped.getCoherencePersistenceParams());
            this.computeDiff("CustomClusterConfigurationFileLastUpdatedTimestamp", this.bean.getCustomClusterConfigurationFileLastUpdatedTimestamp(), otherTyped.getCustomClusterConfigurationFileLastUpdatedTimestamp(), false);
            this.computeDiff("CustomClusterConfigurationFileName", this.bean.getCustomClusterConfigurationFileName(), otherTyped.getCustomClusterConfigurationFileName(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Version", this.bean.getVersion(), otherTyped.getVersion(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WeblogicCoherenceBeanImpl original = (WeblogicCoherenceBeanImpl)event.getSourceBean();
            WeblogicCoherenceBeanImpl proposed = (WeblogicCoherenceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceAddressProviders")) {
                  if (type == 2) {
                     original.setCoherenceAddressProviders((CoherenceAddressProvidersBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceAddressProviders()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceAddressProviders", (DescriptorBean)original.getCoherenceAddressProviders());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("CoherenceClusterParams")) {
                  if (type == 2) {
                     original.setCoherenceClusterParams((CoherenceClusterParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceClusterParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceClusterParams", (DescriptorBean)original.getCoherenceClusterParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CoherenceFederationParams")) {
                  if (type == 2) {
                     original.setCoherenceFederationParams((CoherenceFederationParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceFederationParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceFederationParams", (DescriptorBean)original.getCoherenceFederationParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("CoherenceLoggingParams")) {
                  if (type == 2) {
                     original.setCoherenceLoggingParams((CoherenceLoggingParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherenceLoggingParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherenceLoggingParams", (DescriptorBean)original.getCoherenceLoggingParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("CoherencePersistenceParams")) {
                  if (type == 2) {
                     original.setCoherencePersistenceParams((CoherencePersistenceParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getCoherencePersistenceParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CoherencePersistenceParams", (DescriptorBean)original.getCoherencePersistenceParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("CustomClusterConfigurationFileLastUpdatedTimestamp")) {
                  original.setCustomClusterConfigurationFileLastUpdatedTimestamp(proposed.getCustomClusterConfigurationFileLastUpdatedTimestamp());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("CustomClusterConfigurationFileName")) {
                  original.setCustomClusterConfigurationFileName(proposed.getCustomClusterConfigurationFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Version")) {
                  original.setVersion(proposed.getVersion());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            WeblogicCoherenceBeanImpl copy = (WeblogicCoherenceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceAddressProviders")) && this.bean.isCoherenceAddressProvidersSet() && !copy._isSet(3)) {
               Object o = this.bean.getCoherenceAddressProviders();
               copy.setCoherenceAddressProviders((CoherenceAddressProvidersBean)null);
               copy.setCoherenceAddressProviders(o == null ? null : (CoherenceAddressProvidersBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceClusterParams")) && this.bean.isCoherenceClusterParamsSet() && !copy._isSet(1)) {
               Object o = this.bean.getCoherenceClusterParams();
               copy.setCoherenceClusterParams((CoherenceClusterParamsBean)null);
               copy.setCoherenceClusterParams(o == null ? null : (CoherenceClusterParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceFederationParams")) && this.bean.isCoherenceFederationParamsSet() && !copy._isSet(8)) {
               Object o = this.bean.getCoherenceFederationParams();
               copy.setCoherenceFederationParams((CoherenceFederationParamsBean)null);
               copy.setCoherenceFederationParams(o == null ? null : (CoherenceFederationParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceLoggingParams")) && this.bean.isCoherenceLoggingParamsSet() && !copy._isSet(2)) {
               Object o = this.bean.getCoherenceLoggingParams();
               copy.setCoherenceLoggingParams((CoherenceLoggingParamsBean)null);
               copy.setCoherenceLoggingParams(o == null ? null : (CoherenceLoggingParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CoherencePersistenceParams")) && this.bean.isCoherencePersistenceParamsSet() && !copy._isSet(7)) {
               Object o = this.bean.getCoherencePersistenceParams();
               copy.setCoherencePersistenceParams((CoherencePersistenceParamsBean)null);
               copy.setCoherencePersistenceParams(o == null ? null : (CoherencePersistenceParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("CustomClusterConfigurationFileLastUpdatedTimestamp")) && this.bean.isCustomClusterConfigurationFileLastUpdatedTimestampSet()) {
               copy.setCustomClusterConfigurationFileLastUpdatedTimestamp(this.bean.getCustomClusterConfigurationFileLastUpdatedTimestamp());
            }

            if ((excludeProps == null || !excludeProps.contains("CustomClusterConfigurationFileName")) && this.bean.isCustomClusterConfigurationFileNameSet()) {
               copy.setCustomClusterConfigurationFileName(this.bean.getCustomClusterConfigurationFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Version")) && this.bean.isVersionSet()) {
               copy.setVersion(this.bean.getVersion());
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
         this.inferSubTree(this.bean.getCoherenceAddressProviders(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceClusterParams(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceFederationParams(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherenceLoggingParams(), clazz, annotation);
         this.inferSubTree(this.bean.getCoherencePersistenceParams(), clazz, annotation);
      }
   }
}
