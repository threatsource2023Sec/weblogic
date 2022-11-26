package weblogic.management.security.authentication;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.security.IdentityDomainAwareProviderMBeanImpl;
import weblogic.utils.collections.CombinedIterator;

public class AnyIdentityDomainAuthenticatorMBeanImpl extends IdentityDomainAwareProviderMBeanImpl implements AnyIdentityDomainAuthenticatorMBean, Serializable {
   private boolean _AnyIdentityDomainEnabled;
   private static SchemaHelper2 _schemaHelper;

   public AnyIdentityDomainAuthenticatorMBeanImpl() {
      this._initializeProperty(-1);
   }

   public AnyIdentityDomainAuthenticatorMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AnyIdentityDomainAuthenticatorMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isAnyIdentityDomainEnabled() {
      return this._AnyIdentityDomainEnabled;
   }

   public boolean isAnyIdentityDomainEnabledInherited() {
      return false;
   }

   public boolean isAnyIdentityDomainEnabledSet() {
      return this._isSet(2);
   }

   public void setAnyIdentityDomainEnabled(boolean param0) throws InvalidAttributeValueException {
      boolean _oldVal = this._AnyIdentityDomainEnabled;
      this._AnyIdentityDomainEnabled = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._AnyIdentityDomainEnabled = false;
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
      return "http://xmlns.oracle.com/weblogic/1.0/security.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/security";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String wls_getInterfaceClassName() {
      return "weblogic.management.security.authentication.AnyIdentityDomainAuthenticatorMBean";
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 27:
               if (s.equals("any-identity-domain-enabled")) {
                  return 2;
               }
            default:
               return super.getPropertyIndex(s);
         }
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
               return "any-identity-domain-enabled";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends IdentityDomainAwareProviderMBeanImpl.Helper {
      private AnyIdentityDomainAuthenticatorMBeanImpl bean;

      protected Helper(AnyIdentityDomainAuthenticatorMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "AnyIdentityDomainEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("AnyIdentityDomainEnabled") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isAnyIdentityDomainEnabledSet()) {
               buf.append("AnyIdentityDomainEnabled");
               buf.append(String.valueOf(this.bean.isAnyIdentityDomainEnabled()));
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
            AnyIdentityDomainAuthenticatorMBeanImpl otherTyped = (AnyIdentityDomainAuthenticatorMBeanImpl)other;
            this.computeDiff("AnyIdentityDomainEnabled", this.bean.isAnyIdentityDomainEnabled(), otherTyped.isAnyIdentityDomainEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnyIdentityDomainAuthenticatorMBeanImpl original = (AnyIdentityDomainAuthenticatorMBeanImpl)event.getSourceBean();
            AnyIdentityDomainAuthenticatorMBeanImpl proposed = (AnyIdentityDomainAuthenticatorMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AnyIdentityDomainEnabled")) {
                  original.setAnyIdentityDomainEnabled(proposed.isAnyIdentityDomainEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            AnyIdentityDomainAuthenticatorMBeanImpl copy = (AnyIdentityDomainAuthenticatorMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AnyIdentityDomainEnabled")) && this.bean.isAnyIdentityDomainEnabledSet()) {
               copy.setAnyIdentityDomainEnabled(this.bean.isAnyIdentityDomainEnabled());
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
