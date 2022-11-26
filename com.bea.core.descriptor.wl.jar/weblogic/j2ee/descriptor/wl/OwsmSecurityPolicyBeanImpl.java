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
import weblogic.j2ee.descriptor.wl.validators.WseePolicyBeanValidator;
import weblogic.utils.collections.CombinedIterator;

public class OwsmSecurityPolicyBeanImpl extends AbstractDescriptorBean implements OwsmSecurityPolicyBean, Serializable {
   private String _Status;
   private String _Uri;
   private static SchemaHelper2 _schemaHelper;

   public OwsmSecurityPolicyBeanImpl() {
      this._initializeProperty(-1);
   }

   public OwsmSecurityPolicyBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public OwsmSecurityPolicyBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseePolicyBeanValidator.validateUri(param0);
      String _oldVal = this._Uri;
      this._Uri = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getUri() {
      return this._Uri;
   }

   public boolean isUriInherited() {
      return false;
   }

   public boolean isUriSet() {
      return this._isSet(0);
   }

   public void setStatus(String param0) {
      param0 = param0 == null ? null : param0.trim();
      WseePolicyBeanValidator.validateStatus(param0);
      String _oldVal = this._Status;
      this._Status = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getStatus() {
      return this._Status;
   }

   public boolean isStatusInherited() {
      return false;
   }

   public boolean isStatusSet() {
      return this._isSet(1);
   }

   public Object _getKey() {
      return this.getUri();
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
         case 3:
            if (s.equals("uri")) {
               return info.compareXpaths(this._getPropertyXpath("uri"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._Status = "enabled";
               if (initOne) {
                  break;
               }
            case 0:
               this._Uri = null;
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
            case 3:
               if (s.equals("uri")) {
                  return 0;
               }
               break;
            case 6:
               if (s.equals("status")) {
                  return 1;
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
               return "uri";
            case 1:
               return "status";
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
         indices.add("uri");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private OwsmSecurityPolicyBeanImpl bean;

      protected Helper(OwsmSecurityPolicyBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Uri";
            case 1:
               return "Status";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Status")) {
            return 1;
         } else {
            return propName.equals("Uri") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isStatusSet()) {
               buf.append("Status");
               buf.append(String.valueOf(this.bean.getStatus()));
            }

            if (this.bean.isUriSet()) {
               buf.append("Uri");
               buf.append(String.valueOf(this.bean.getUri()));
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
            OwsmSecurityPolicyBeanImpl otherTyped = (OwsmSecurityPolicyBeanImpl)other;
            this.computeDiff("Status", this.bean.getStatus(), otherTyped.getStatus(), false);
            this.computeDiff("Uri", this.bean.getUri(), otherTyped.getUri(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            OwsmSecurityPolicyBeanImpl original = (OwsmSecurityPolicyBeanImpl)event.getSourceBean();
            OwsmSecurityPolicyBeanImpl proposed = (OwsmSecurityPolicyBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Status")) {
                  original.setStatus(proposed.getStatus());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Uri")) {
                  original.setUri(proposed.getUri());
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
            OwsmSecurityPolicyBeanImpl copy = (OwsmSecurityPolicyBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Status")) && this.bean.isStatusSet()) {
               copy.setStatus(this.bean.getStatus());
            }

            if ((excludeProps == null || !excludeProps.contains("Uri")) && this.bean.isUriSet()) {
               copy.setUri(this.bean.getUri());
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
