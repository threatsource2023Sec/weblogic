package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class IiopSecurityDescriptorBeanImpl extends AbstractDescriptorBean implements IiopSecurityDescriptorBean, Serializable {
   private String _ClientAuthentication;
   private String _Id;
   private String _IdentityAssertion;
   private TransportRequirementsBean _TransportRequirements;
   private static SchemaHelper2 _schemaHelper;

   public IiopSecurityDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public IiopSecurityDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public IiopSecurityDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public TransportRequirementsBean getTransportRequirements() {
      return this._TransportRequirements;
   }

   public boolean isTransportRequirementsInherited() {
      return false;
   }

   public boolean isTransportRequirementsSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getTransportRequirements());
   }

   public void setTransportRequirements(TransportRequirementsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      TransportRequirementsBean _oldVal = this._TransportRequirements;
      this._TransportRequirements = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getClientAuthentication() {
      return this._ClientAuthentication;
   }

   public boolean isClientAuthenticationInherited() {
      return false;
   }

   public boolean isClientAuthenticationSet() {
      return this._isSet(1);
   }

   public void setClientAuthentication(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "supported", "required"};
      param0 = LegalChecks.checkInEnum("ClientAuthentication", param0, _set);
      String _oldVal = this._ClientAuthentication;
      this._ClientAuthentication = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getIdentityAssertion() {
      return this._IdentityAssertion;
   }

   public boolean isIdentityAssertionInherited() {
      return false;
   }

   public boolean isIdentityAssertionSet() {
      return this._isSet(2);
   }

   public void setIdentityAssertion(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "supported", "required"};
      param0 = LegalChecks.checkInEnum("IdentityAssertion", param0, _set);
      String _oldVal = this._IdentityAssertion;
      this._IdentityAssertion = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
      return super._isAnythingSet() || this.isTransportRequirementsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._ClientAuthentication = "supported";
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._IdentityAssertion = "supported";
               if (initOne) {
                  break;
               }
            case 0:
               this._TransportRequirements = new TransportRequirementsBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._TransportRequirements);
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
            case 2:
               if (s.equals("id")) {
                  return 3;
               }
               break;
            case 18:
               if (s.equals("identity-assertion")) {
                  return 2;
               }
               break;
            case 21:
               if (s.equals("client-authentication")) {
                  return 1;
               }
               break;
            case 22:
               if (s.equals("transport-requirements")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new TransportRequirementsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "transport-requirements";
            case 1:
               return "client-authentication";
            case 2:
               return "identity-assertion";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private IiopSecurityDescriptorBeanImpl bean;

      protected Helper(IiopSecurityDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TransportRequirements";
            case 1:
               return "ClientAuthentication";
            case 2:
               return "IdentityAssertion";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClientAuthentication")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("IdentityAssertion")) {
            return 2;
         } else {
            return propName.equals("TransportRequirements") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getTransportRequirements() != null) {
            iterators.add(new ArrayIterator(new TransportRequirementsBean[]{this.bean.getTransportRequirements()}));
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
            if (this.bean.isClientAuthenticationSet()) {
               buf.append("ClientAuthentication");
               buf.append(String.valueOf(this.bean.getClientAuthentication()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIdentityAssertionSet()) {
               buf.append("IdentityAssertion");
               buf.append(String.valueOf(this.bean.getIdentityAssertion()));
            }

            childValue = this.computeChildHashValue(this.bean.getTransportRequirements());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            IiopSecurityDescriptorBeanImpl otherTyped = (IiopSecurityDescriptorBeanImpl)other;
            this.computeDiff("ClientAuthentication", this.bean.getClientAuthentication(), otherTyped.getClientAuthentication(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("IdentityAssertion", this.bean.getIdentityAssertion(), otherTyped.getIdentityAssertion(), false);
            this.computeSubDiff("TransportRequirements", this.bean.getTransportRequirements(), otherTyped.getTransportRequirements());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            IiopSecurityDescriptorBeanImpl original = (IiopSecurityDescriptorBeanImpl)event.getSourceBean();
            IiopSecurityDescriptorBeanImpl proposed = (IiopSecurityDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClientAuthentication")) {
                  original.setClientAuthentication(proposed.getClientAuthentication());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("IdentityAssertion")) {
                  original.setIdentityAssertion(proposed.getIdentityAssertion());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("TransportRequirements")) {
                  if (type == 2) {
                     original.setTransportRequirements((TransportRequirementsBean)this.createCopy((AbstractDescriptorBean)proposed.getTransportRequirements()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TransportRequirements", (DescriptorBean)original.getTransportRequirements());
                  }

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
            IiopSecurityDescriptorBeanImpl copy = (IiopSecurityDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClientAuthentication")) && this.bean.isClientAuthenticationSet()) {
               copy.setClientAuthentication(this.bean.getClientAuthentication());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityAssertion")) && this.bean.isIdentityAssertionSet()) {
               copy.setIdentityAssertion(this.bean.getIdentityAssertion());
            }

            if ((excludeProps == null || !excludeProps.contains("TransportRequirements")) && this.bean.isTransportRequirementsSet() && !copy._isSet(0)) {
               Object o = this.bean.getTransportRequirements();
               copy.setTransportRequirements((TransportRequirementsBean)null);
               copy.setTransportRequirements(o == null ? null : (TransportRequirementsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getTransportRequirements(), clazz, annotation);
      }
   }
}
