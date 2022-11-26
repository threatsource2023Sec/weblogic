package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class TransportRequirementsBeanImpl extends AbstractDescriptorBean implements TransportRequirementsBean, Serializable {
   private String _ClientCertAuthentication;
   private String _Confidentiality;
   private String _Id;
   private String _Integrity;
   private static SchemaHelper2 _schemaHelper;

   public TransportRequirementsBeanImpl() {
      this._initializeProperty(-1);
   }

   public TransportRequirementsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public TransportRequirementsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getIntegrity() {
      return this._Integrity;
   }

   public boolean isIntegrityInherited() {
      return false;
   }

   public boolean isIntegritySet() {
      return this._isSet(0);
   }

   public void setIntegrity(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "supported", "required"};
      param0 = LegalChecks.checkInEnum("Integrity", param0, _set);
      String _oldVal = this._Integrity;
      this._Integrity = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getConfidentiality() {
      return this._Confidentiality;
   }

   public boolean isConfidentialityInherited() {
      return false;
   }

   public boolean isConfidentialitySet() {
      return this._isSet(1);
   }

   public void setConfidentiality(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "supported", "required"};
      param0 = LegalChecks.checkInEnum("Confidentiality", param0, _set);
      String _oldVal = this._Confidentiality;
      this._Confidentiality = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getClientCertAuthentication() {
      return this._ClientCertAuthentication;
   }

   public boolean isClientCertAuthenticationInherited() {
      return false;
   }

   public boolean isClientCertAuthenticationSet() {
      return this._isSet(2);
   }

   public void setClientCertAuthentication(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "supported", "required"};
      param0 = LegalChecks.checkInEnum("ClientCertAuthentication", param0, _set);
      String _oldVal = this._ClientCertAuthentication;
      this._ClientCertAuthentication = param0;
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
               this._ClientCertAuthentication = "supported";
               if (initOne) {
                  break;
               }
            case 1:
               this._Confidentiality = "supported";
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Integrity = "supported";
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
            case 9:
               if (s.equals("integrity")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("confidentiality")) {
                  return 1;
               }
               break;
            case 26:
               if (s.equals("client-cert-authentication")) {
                  return 2;
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
               return "integrity";
            case 1:
               return "confidentiality";
            case 2:
               return "client-cert-authentication";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private TransportRequirementsBeanImpl bean;

      protected Helper(TransportRequirementsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Integrity";
            case 1:
               return "Confidentiality";
            case 2:
               return "ClientCertAuthentication";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClientCertAuthentication")) {
            return 2;
         } else if (propName.equals("Confidentiality")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 3;
         } else {
            return propName.equals("Integrity") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isClientCertAuthenticationSet()) {
               buf.append("ClientCertAuthentication");
               buf.append(String.valueOf(this.bean.getClientCertAuthentication()));
            }

            if (this.bean.isConfidentialitySet()) {
               buf.append("Confidentiality");
               buf.append(String.valueOf(this.bean.getConfidentiality()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isIntegritySet()) {
               buf.append("Integrity");
               buf.append(String.valueOf(this.bean.getIntegrity()));
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
            TransportRequirementsBeanImpl otherTyped = (TransportRequirementsBeanImpl)other;
            this.computeDiff("ClientCertAuthentication", this.bean.getClientCertAuthentication(), otherTyped.getClientCertAuthentication(), false);
            this.computeDiff("Confidentiality", this.bean.getConfidentiality(), otherTyped.getConfidentiality(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Integrity", this.bean.getIntegrity(), otherTyped.getIntegrity(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            TransportRequirementsBeanImpl original = (TransportRequirementsBeanImpl)event.getSourceBean();
            TransportRequirementsBeanImpl proposed = (TransportRequirementsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClientCertAuthentication")) {
                  original.setClientCertAuthentication(proposed.getClientCertAuthentication());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Confidentiality")) {
                  original.setConfidentiality(proposed.getConfidentiality());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Integrity")) {
                  original.setIntegrity(proposed.getIntegrity());
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
            TransportRequirementsBeanImpl copy = (TransportRequirementsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClientCertAuthentication")) && this.bean.isClientCertAuthenticationSet()) {
               copy.setClientCertAuthentication(this.bean.getClientCertAuthentication());
            }

            if ((excludeProps == null || !excludeProps.contains("Confidentiality")) && this.bean.isConfidentialitySet()) {
               copy.setConfidentiality(this.bean.getConfidentiality());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Integrity")) && this.bean.isIntegritySet()) {
               copy.setIntegrity(this.bean.getIntegrity());
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
