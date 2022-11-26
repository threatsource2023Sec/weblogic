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

public class CdiContainerMBeanImpl extends ConfigurationMBeanImpl implements CdiContainerMBean, Serializable {
   private boolean _ImplicitBeanDiscoveryEnabled;
   private String _Policy;
   private static SchemaHelper2 _schemaHelper;

   public CdiContainerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public CdiContainerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CdiContainerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isImplicitBeanDiscoveryEnabled() {
      return this._ImplicitBeanDiscoveryEnabled;
   }

   public boolean isImplicitBeanDiscoveryEnabledInherited() {
      return false;
   }

   public boolean isImplicitBeanDiscoveryEnabledSet() {
      return this._isSet(10);
   }

   public void setImplicitBeanDiscoveryEnabled(boolean param0) {
      boolean _oldVal = this._ImplicitBeanDiscoveryEnabled;
      this._ImplicitBeanDiscoveryEnabled = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getPolicy() {
      return this._Policy;
   }

   public boolean isPolicyInherited() {
      return false;
   }

   public boolean isPolicySet() {
      return this._isSet(11);
   }

   public void setPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Enabled", "Disabled"};
      param0 = LegalChecks.checkInEnum("Policy", param0, _set);
      String _oldVal = this._Policy;
      this._Policy = param0;
      this._postSet(11, _oldVal, param0);
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
               this._Policy = "Enabled";
               if (initOne) {
                  break;
               }
            case 10:
               this._ImplicitBeanDiscoveryEnabled = true;
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
      return "CdiContainer";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ImplicitBeanDiscoveryEnabled")) {
         boolean oldVal = this._ImplicitBeanDiscoveryEnabled;
         this._ImplicitBeanDiscoveryEnabled = (Boolean)v;
         this._postSet(10, oldVal, this._ImplicitBeanDiscoveryEnabled);
      } else if (name.equals("Policy")) {
         String oldVal = this._Policy;
         this._Policy = (String)v;
         this._postSet(11, oldVal, this._Policy);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ImplicitBeanDiscoveryEnabled")) {
         return new Boolean(this._ImplicitBeanDiscoveryEnabled);
      } else {
         return name.equals("Policy") ? this._Policy : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("policy")) {
                  return 11;
               }
               break;
            case 31:
               if (s.equals("implicit-bean-discovery-enabled")) {
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
               return "implicit-bean-discovery-enabled";
            case 11:
               return "policy";
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
      private CdiContainerMBeanImpl bean;

      protected Helper(CdiContainerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ImplicitBeanDiscoveryEnabled";
            case 11:
               return "Policy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Policy")) {
            return 11;
         } else {
            return propName.equals("ImplicitBeanDiscoveryEnabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isPolicySet()) {
               buf.append("Policy");
               buf.append(String.valueOf(this.bean.getPolicy()));
            }

            if (this.bean.isImplicitBeanDiscoveryEnabledSet()) {
               buf.append("ImplicitBeanDiscoveryEnabled");
               buf.append(String.valueOf(this.bean.isImplicitBeanDiscoveryEnabled()));
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
            CdiContainerMBeanImpl otherTyped = (CdiContainerMBeanImpl)other;
            this.computeDiff("Policy", this.bean.getPolicy(), otherTyped.getPolicy(), false);
            this.computeDiff("ImplicitBeanDiscoveryEnabled", this.bean.isImplicitBeanDiscoveryEnabled(), otherTyped.isImplicitBeanDiscoveryEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CdiContainerMBeanImpl original = (CdiContainerMBeanImpl)event.getSourceBean();
            CdiContainerMBeanImpl proposed = (CdiContainerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Policy")) {
                  original.setPolicy(proposed.getPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("ImplicitBeanDiscoveryEnabled")) {
                  original.setImplicitBeanDiscoveryEnabled(proposed.isImplicitBeanDiscoveryEnabled());
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
            CdiContainerMBeanImpl copy = (CdiContainerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Policy")) && this.bean.isPolicySet()) {
               copy.setPolicy(this.bean.getPolicy());
            }

            if ((excludeProps == null || !excludeProps.contains("ImplicitBeanDiscoveryEnabled")) && this.bean.isImplicitBeanDiscoveryEnabledSet()) {
               copy.setImplicitBeanDiscoveryEnabled(this.bean.isImplicitBeanDiscoveryEnabled());
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
