package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceTierMBeanImpl extends ConfigurationMBeanImpl implements CoherenceTierMBean, Serializable {
   private boolean _CoherenceWebFederatedStorageEnabled;
   private boolean _CoherenceWebLocalStorageEnabled;
   private boolean _LocalStorageEnabled;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceTierMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceTierMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceTierMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isLocalStorageEnabled() {
      return this._LocalStorageEnabled;
   }

   public boolean isLocalStorageEnabledInherited() {
      return false;
   }

   public boolean isLocalStorageEnabledSet() {
      return this._isSet(10);
   }

   public void setLocalStorageEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._LocalStorageEnabled;
      this._LocalStorageEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isCoherenceWebLocalStorageEnabled() {
      return this._CoherenceWebLocalStorageEnabled;
   }

   public boolean isCoherenceWebLocalStorageEnabledInherited() {
      return false;
   }

   public boolean isCoherenceWebLocalStorageEnabledSet() {
      return this._isSet(11);
   }

   public void setCoherenceWebLocalStorageEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._CoherenceWebLocalStorageEnabled;
      this._CoherenceWebLocalStorageEnabled = param0;
      this._postSet(11, _oldVal, param0);
   }

   public boolean isCoherenceWebFederatedStorageEnabled() {
      return this._CoherenceWebFederatedStorageEnabled;
   }

   public boolean isCoherenceWebFederatedStorageEnabledInherited() {
      return false;
   }

   public boolean isCoherenceWebFederatedStorageEnabledSet() {
      return this._isSet(12);
   }

   public void setCoherenceWebFederatedStorageEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._CoherenceWebFederatedStorageEnabled;
      this._CoherenceWebFederatedStorageEnabled = param0;
      this._postSet(12, _oldVal, param0);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._CoherenceWebFederatedStorageEnabled = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._CoherenceWebLocalStorageEnabled = false;
               if (initOne) {
                  break;
               }
            case 10:
               this._LocalStorageEnabled = true;
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
      return "CoherenceTier";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CoherenceWebFederatedStorageEnabled")) {
         oldVal = this._CoherenceWebFederatedStorageEnabled;
         this._CoherenceWebFederatedStorageEnabled = (Boolean)v;
         this._postSet(12, oldVal, this._CoherenceWebFederatedStorageEnabled);
      } else if (name.equals("CoherenceWebLocalStorageEnabled")) {
         oldVal = this._CoherenceWebLocalStorageEnabled;
         this._CoherenceWebLocalStorageEnabled = (Boolean)v;
         this._postSet(11, oldVal, this._CoherenceWebLocalStorageEnabled);
      } else if (name.equals("LocalStorageEnabled")) {
         oldVal = this._LocalStorageEnabled;
         this._LocalStorageEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._LocalStorageEnabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CoherenceWebFederatedStorageEnabled")) {
         return new Boolean(this._CoherenceWebFederatedStorageEnabled);
      } else if (name.equals("CoherenceWebLocalStorageEnabled")) {
         return new Boolean(this._CoherenceWebLocalStorageEnabled);
      } else {
         return name.equals("LocalStorageEnabled") ? new Boolean(this._LocalStorageEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 21:
               if (s.equals("local-storage-enabled")) {
                  return 10;
               }
               break;
            case 35:
               if (s.equals("coherence-web-local-storage-enabled")) {
                  return 11;
               }
               break;
            case 39:
               if (s.equals("coherence-web-federated-storage-enabled")) {
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
               return "local-storage-enabled";
            case 11:
               return "coherence-web-local-storage-enabled";
            case 12:
               return "coherence-web-federated-storage-enabled";
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
      private CoherenceTierMBeanImpl bean;

      protected Helper(CoherenceTierMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "LocalStorageEnabled";
            case 11:
               return "CoherenceWebLocalStorageEnabled";
            case 12:
               return "CoherenceWebFederatedStorageEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CoherenceWebFederatedStorageEnabled")) {
            return 12;
         } else if (propName.equals("CoherenceWebLocalStorageEnabled")) {
            return 11;
         } else {
            return propName.equals("LocalStorageEnabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isCoherenceWebFederatedStorageEnabledSet()) {
               buf.append("CoherenceWebFederatedStorageEnabled");
               buf.append(String.valueOf(this.bean.isCoherenceWebFederatedStorageEnabled()));
            }

            if (this.bean.isCoherenceWebLocalStorageEnabledSet()) {
               buf.append("CoherenceWebLocalStorageEnabled");
               buf.append(String.valueOf(this.bean.isCoherenceWebLocalStorageEnabled()));
            }

            if (this.bean.isLocalStorageEnabledSet()) {
               buf.append("LocalStorageEnabled");
               buf.append(String.valueOf(this.bean.isLocalStorageEnabled()));
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
            CoherenceTierMBeanImpl otherTyped = (CoherenceTierMBeanImpl)other;
            this.computeDiff("CoherenceWebFederatedStorageEnabled", this.bean.isCoherenceWebFederatedStorageEnabled(), otherTyped.isCoherenceWebFederatedStorageEnabled(), false);
            this.computeDiff("CoherenceWebLocalStorageEnabled", this.bean.isCoherenceWebLocalStorageEnabled(), otherTyped.isCoherenceWebLocalStorageEnabled(), false);
            this.computeDiff("LocalStorageEnabled", this.bean.isLocalStorageEnabled(), otherTyped.isLocalStorageEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceTierMBeanImpl original = (CoherenceTierMBeanImpl)event.getSourceBean();
            CoherenceTierMBeanImpl proposed = (CoherenceTierMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CoherenceWebFederatedStorageEnabled")) {
                  original.setCoherenceWebFederatedStorageEnabled(proposed.isCoherenceWebFederatedStorageEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("CoherenceWebLocalStorageEnabled")) {
                  original.setCoherenceWebLocalStorageEnabled(proposed.isCoherenceWebLocalStorageEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("LocalStorageEnabled")) {
                  original.setLocalStorageEnabled(proposed.isLocalStorageEnabled());
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
            CoherenceTierMBeanImpl copy = (CoherenceTierMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CoherenceWebFederatedStorageEnabled")) && this.bean.isCoherenceWebFederatedStorageEnabledSet()) {
               copy.setCoherenceWebFederatedStorageEnabled(this.bean.isCoherenceWebFederatedStorageEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("CoherenceWebLocalStorageEnabled")) && this.bean.isCoherenceWebLocalStorageEnabledSet()) {
               copy.setCoherenceWebLocalStorageEnabled(this.bean.isCoherenceWebLocalStorageEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalStorageEnabled")) && this.bean.isLocalStorageEnabledSet()) {
               copy.setLocalStorageEnabled(this.bean.isLocalStorageEnabled());
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
