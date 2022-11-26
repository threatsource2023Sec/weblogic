package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class CdiDescriptorBeanImpl extends AbstractDescriptorBean implements CdiDescriptorBean, Serializable {
   private boolean _ImplicitBeanDiscoveryEnabled;
   private String _Policy;
   private static SchemaHelper2 _schemaHelper;

   public CdiDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public CdiDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CdiDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
      return this._isSet(0);
   }

   public void setImplicitBeanDiscoveryEnabled(boolean param0) {
      boolean _oldVal = this._ImplicitBeanDiscoveryEnabled;
      this._ImplicitBeanDiscoveryEnabled = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPolicy() {
      return this._Policy;
   }

   public boolean isPolicyInherited() {
      return false;
   }

   public boolean isPolicySet() {
      return this._isSet(1);
   }

   public void setPolicy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Policy;
      this._Policy = param0;
      this._postSet(1, _oldVal, param0);
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
               this._Policy = "Enabled";
               if (initOne) {
                  break;
               }
            case 0:
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 6:
               if (s.equals("policy")) {
                  return 1;
               }
               break;
            case 31:
               if (s.equals("implicit-bean-discovery-enabled")) {
                  return 0;
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
            case 0:
               return "implicit-bean-discovery-enabled";
            case 1:
               return "policy";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private CdiDescriptorBeanImpl bean;

      protected Helper(CdiDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ImplicitBeanDiscoveryEnabled";
            case 1:
               return "Policy";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Policy")) {
            return 1;
         } else {
            return propName.equals("ImplicitBeanDiscoveryEnabled") ? 0 : super.getPropertyIndex(propName);
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
            CdiDescriptorBeanImpl otherTyped = (CdiDescriptorBeanImpl)other;
            this.computeDiff("Policy", this.bean.getPolicy(), otherTyped.getPolicy(), false);
            this.computeDiff("ImplicitBeanDiscoveryEnabled", this.bean.isImplicitBeanDiscoveryEnabled(), otherTyped.isImplicitBeanDiscoveryEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CdiDescriptorBeanImpl original = (CdiDescriptorBeanImpl)event.getSourceBean();
            CdiDescriptorBeanImpl proposed = (CdiDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Policy")) {
                  original.setPolicy(proposed.getPolicy());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ImplicitBeanDiscoveryEnabled")) {
                  original.setImplicitBeanDiscoveryEnabled(proposed.isImplicitBeanDiscoveryEnabled());
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
            CdiDescriptorBeanImpl copy = (CdiDescriptorBeanImpl)initialCopy;
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
