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

public class AnonPrincipalBeanImpl extends AbstractDescriptorBean implements AnonPrincipalBean, Serializable {
   private String _PrincipalName;
   private boolean _UseAnonymousIdentity;
   private static SchemaHelper2 _schemaHelper;

   public AnonPrincipalBeanImpl() {
      this._initializeProperty(-1);
   }

   public AnonPrincipalBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AnonPrincipalBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isUseAnonymousIdentity() {
      return this._UseAnonymousIdentity;
   }

   public boolean isUseAnonymousIdentityInherited() {
      return false;
   }

   public boolean isUseAnonymousIdentitySet() {
      return this._isSet(0);
   }

   public void setUseAnonymousIdentity(boolean param0) {
      boolean _oldVal = this._UseAnonymousIdentity;
      this._UseAnonymousIdentity = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPrincipalName() {
      return this._PrincipalName;
   }

   public boolean isPrincipalNameInherited() {
      return false;
   }

   public boolean isPrincipalNameSet() {
      return this._isSet(1);
   }

   public void setPrincipalName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PrincipalName;
      this._PrincipalName = param0;
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
               this._PrincipalName = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._UseAnonymousIdentity = false;
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
            case 14:
               if (s.equals("principal-name")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("use-anonymous-identity")) {
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
               return "use-anonymous-identity";
            case 1:
               return "principal-name";
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
      private AnonPrincipalBeanImpl bean;

      protected Helper(AnonPrincipalBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "UseAnonymousIdentity";
            case 1:
               return "PrincipalName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("PrincipalName")) {
            return 1;
         } else {
            return propName.equals("UseAnonymousIdentity") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isPrincipalNameSet()) {
               buf.append("PrincipalName");
               buf.append(String.valueOf(this.bean.getPrincipalName()));
            }

            if (this.bean.isUseAnonymousIdentitySet()) {
               buf.append("UseAnonymousIdentity");
               buf.append(String.valueOf(this.bean.isUseAnonymousIdentity()));
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
            AnonPrincipalBeanImpl otherTyped = (AnonPrincipalBeanImpl)other;
            this.computeDiff("PrincipalName", this.bean.getPrincipalName(), otherTyped.getPrincipalName(), false);
            this.computeDiff("UseAnonymousIdentity", this.bean.isUseAnonymousIdentity(), otherTyped.isUseAnonymousIdentity(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnonPrincipalBeanImpl original = (AnonPrincipalBeanImpl)event.getSourceBean();
            AnonPrincipalBeanImpl proposed = (AnonPrincipalBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("PrincipalName")) {
                  original.setPrincipalName(proposed.getPrincipalName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("UseAnonymousIdentity")) {
                  original.setUseAnonymousIdentity(proposed.isUseAnonymousIdentity());
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
            AnonPrincipalBeanImpl copy = (AnonPrincipalBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("PrincipalName")) && this.bean.isPrincipalNameSet()) {
               copy.setPrincipalName(this.bean.getPrincipalName());
            }

            if ((excludeProps == null || !excludeProps.contains("UseAnonymousIdentity")) && this.bean.isUseAnonymousIdentitySet()) {
               copy.setUseAnonymousIdentity(this.bean.isUseAnonymousIdentity());
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
