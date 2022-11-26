package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class SNMPTrapDestinationMBeanImpl extends ConfigurationMBeanImpl implements SNMPTrapDestinationMBean, Serializable {
   private String _Community;
   private String _Host;
   private int _Port;
   private String _SecurityLevel;
   private String _SecurityName;
   private static SchemaHelper2 _schemaHelper;

   public SNMPTrapDestinationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPTrapDestinationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPTrapDestinationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getHost() {
      return this._Host;
   }

   public boolean isHostInherited() {
      return false;
   }

   public boolean isHostSet() {
      return this._isSet(10);
   }

   public void setHost(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Host", param0);
      String _oldVal = this._Host;
      this._Host = param0;
      this._postSet(10, _oldVal, param0);
   }

   public int getPort() {
      return this._Port;
   }

   public boolean isPortInherited() {
      return false;
   }

   public boolean isPortSet() {
      return this._isSet(11);
   }

   public void setPort(int param0) throws InvalidAttributeValueException, ConfigurationException {
      LegalChecks.checkInRange("Port", (long)param0, 1L, 65535L);
      int _oldVal = this._Port;
      this._Port = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getCommunity() {
      return this._Community;
   }

   public boolean isCommunityInherited() {
      return false;
   }

   public boolean isCommunitySet() {
      return this._isSet(12);
   }

   public void setCommunity(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonEmptyString("Community", param0);
      String _oldVal = this._Community;
      this._Community = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getSecurityName() {
      return this._SecurityName;
   }

   public boolean isSecurityNameInherited() {
      return false;
   }

   public boolean isSecurityNameSet() {
      return this._isSet(13);
   }

   public void setSecurityName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SecurityName;
      this._SecurityName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getSecurityLevel() {
      return this._SecurityLevel;
   }

   public boolean isSecurityLevelInherited() {
      return false;
   }

   public boolean isSecurityLevelSet() {
      return this._isSet(14);
   }

   public void setSecurityLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"noAuthNoPriv", "authNoPriv", "authPriv"};
      param0 = LegalChecks.checkInEnum("SecurityLevel", param0, _set);
      LegalChecks.checkNonNull("SecurityLevel", param0);
      String _oldVal = this._SecurityLevel;
      this._SecurityLevel = param0;
      this._postSet(14, _oldVal, param0);
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
               this._Community = "public";
               if (initOne) {
                  break;
               }
            case 10:
               this._Host = "localhost";
               if (initOne) {
                  break;
               }
            case 11:
               this._Port = 162;
               if (initOne) {
                  break;
               }
            case 14:
               this._SecurityLevel = "authNoPriv";
               if (initOne) {
                  break;
               }
            case 13:
               this._SecurityName = null;
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
      return "SNMPTrapDestination";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Community")) {
         oldVal = this._Community;
         this._Community = (String)v;
         this._postSet(12, oldVal, this._Community);
      } else if (name.equals("Host")) {
         oldVal = this._Host;
         this._Host = (String)v;
         this._postSet(10, oldVal, this._Host);
      } else if (name.equals("Port")) {
         int oldVal = this._Port;
         this._Port = (Integer)v;
         this._postSet(11, oldVal, this._Port);
      } else if (name.equals("SecurityLevel")) {
         oldVal = this._SecurityLevel;
         this._SecurityLevel = (String)v;
         this._postSet(14, oldVal, this._SecurityLevel);
      } else if (name.equals("SecurityName")) {
         oldVal = this._SecurityName;
         this._SecurityName = (String)v;
         this._postSet(13, oldVal, this._SecurityName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Community")) {
         return this._Community;
      } else if (name.equals("Host")) {
         return this._Host;
      } else if (name.equals("Port")) {
         return new Integer(this._Port);
      } else if (name.equals("SecurityLevel")) {
         return this._SecurityLevel;
      } else {
         return name.equals("SecurityName") ? this._SecurityName : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonEmptyString("Community", "public");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property Community in SNMPTrapDestinationMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("Host", "localhost");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property Host in SNMPTrapDestinationMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("SecurityLevel", "authNoPriv");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property SecurityLevel in SNMPTrapDestinationMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("host")) {
                  return 10;
               }

               if (s.equals("port")) {
                  return 11;
               }
               break;
            case 9:
               if (s.equals("community")) {
                  return 12;
               }
               break;
            case 13:
               if (s.equals("security-name")) {
                  return 13;
               }
               break;
            case 14:
               if (s.equals("security-level")) {
                  return 14;
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
               return "host";
            case 11:
               return "port";
            case 12:
               return "community";
            case 13:
               return "security-name";
            case 14:
               return "security-level";
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
      private SNMPTrapDestinationMBeanImpl bean;

      protected Helper(SNMPTrapDestinationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Host";
            case 11:
               return "Port";
            case 12:
               return "Community";
            case 13:
               return "SecurityName";
            case 14:
               return "SecurityLevel";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Community")) {
            return 12;
         } else if (propName.equals("Host")) {
            return 10;
         } else if (propName.equals("Port")) {
            return 11;
         } else if (propName.equals("SecurityLevel")) {
            return 14;
         } else {
            return propName.equals("SecurityName") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isCommunitySet()) {
               buf.append("Community");
               buf.append(String.valueOf(this.bean.getCommunity()));
            }

            if (this.bean.isHostSet()) {
               buf.append("Host");
               buf.append(String.valueOf(this.bean.getHost()));
            }

            if (this.bean.isPortSet()) {
               buf.append("Port");
               buf.append(String.valueOf(this.bean.getPort()));
            }

            if (this.bean.isSecurityLevelSet()) {
               buf.append("SecurityLevel");
               buf.append(String.valueOf(this.bean.getSecurityLevel()));
            }

            if (this.bean.isSecurityNameSet()) {
               buf.append("SecurityName");
               buf.append(String.valueOf(this.bean.getSecurityName()));
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
            SNMPTrapDestinationMBeanImpl otherTyped = (SNMPTrapDestinationMBeanImpl)other;
            this.computeDiff("Community", this.bean.getCommunity(), otherTyped.getCommunity(), true);
            this.computeDiff("Host", this.bean.getHost(), otherTyped.getHost(), true);
            this.computeDiff("Port", this.bean.getPort(), otherTyped.getPort(), true);
            this.computeDiff("SecurityLevel", this.bean.getSecurityLevel(), otherTyped.getSecurityLevel(), true);
            this.computeDiff("SecurityName", this.bean.getSecurityName(), otherTyped.getSecurityName(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPTrapDestinationMBeanImpl original = (SNMPTrapDestinationMBeanImpl)event.getSourceBean();
            SNMPTrapDestinationMBeanImpl proposed = (SNMPTrapDestinationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Community")) {
                  original.setCommunity(proposed.getCommunity());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Host")) {
                  original.setHost(proposed.getHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Port")) {
                  original.setPort(proposed.getPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("SecurityLevel")) {
                  original.setSecurityLevel(proposed.getSecurityLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("SecurityName")) {
                  original.setSecurityName(proposed.getSecurityName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            SNMPTrapDestinationMBeanImpl copy = (SNMPTrapDestinationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Community")) && this.bean.isCommunitySet()) {
               copy.setCommunity(this.bean.getCommunity());
            }

            if ((excludeProps == null || !excludeProps.contains("Host")) && this.bean.isHostSet()) {
               copy.setHost(this.bean.getHost());
            }

            if ((excludeProps == null || !excludeProps.contains("Port")) && this.bean.isPortSet()) {
               copy.setPort(this.bean.getPort());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityLevel")) && this.bean.isSecurityLevelSet()) {
               copy.setSecurityLevel(this.bean.getSecurityLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("SecurityName")) && this.bean.isSecurityNameSet()) {
               copy.setSecurityName(this.bean.getSecurityName());
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
