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

public class WTCPasswordMBeanImpl extends ConfigurationMBeanImpl implements WTCPasswordMBean, Serializable {
   private String _LocalAccessPoint;
   private String _LocalPassword;
   private String _LocalPasswordIV;
   private String _RemoteAccessPoint;
   private String _RemotePassword;
   private String _RemotePasswordIV;
   private static SchemaHelper2 _schemaHelper;

   public WTCPasswordMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCPasswordMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCPasswordMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setLocalAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalAccessPoint", param0);
      String _oldVal = this._LocalAccessPoint;
      this._LocalAccessPoint = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getLocalAccessPoint() {
      return this._LocalAccessPoint;
   }

   public boolean isLocalAccessPointInherited() {
      return false;
   }

   public boolean isLocalAccessPointSet() {
      return this._isSet(10);
   }

   public void setRemoteAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("RemoteAccessPoint", param0);
      String _oldVal = this._RemoteAccessPoint;
      this._RemoteAccessPoint = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getRemoteAccessPoint() {
      return this._RemoteAccessPoint;
   }

   public boolean isRemoteAccessPointInherited() {
      return false;
   }

   public boolean isRemoteAccessPointSet() {
      return this._isSet(11);
   }

   public void setLocalPasswordIV(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalPasswordIV", param0);
      String _oldVal = this._LocalPasswordIV;
      this._LocalPasswordIV = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getLocalPasswordIV() {
      return this._LocalPasswordIV;
   }

   public boolean isLocalPasswordIVInherited() {
      return false;
   }

   public boolean isLocalPasswordIVSet() {
      return this._isSet(12);
   }

   public void setLocalPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalPassword", param0);
      String _oldVal = this._LocalPassword;
      this._LocalPassword = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getLocalPassword() {
      return this._LocalPassword;
   }

   public boolean isLocalPasswordInherited() {
      return false;
   }

   public boolean isLocalPasswordSet() {
      return this._isSet(13);
   }

   public void setRemotePasswordIV(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("RemotePasswordIV", param0);
      String _oldVal = this._RemotePasswordIV;
      this._RemotePasswordIV = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getRemotePasswordIV() {
      return this._RemotePasswordIV;
   }

   public boolean isRemotePasswordIVInherited() {
      return false;
   }

   public boolean isRemotePasswordIVSet() {
      return this._isSet(14);
   }

   public void setRemotePassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("RemotePassword", param0);
      String _oldVal = this._RemotePassword;
      this._RemotePassword = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getRemotePassword() {
      return this._RemotePassword;
   }

   public boolean isRemotePasswordInherited() {
      return false;
   }

   public boolean isRemotePasswordSet() {
      return this._isSet(15);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._LocalAccessPoint = "myLAP";
               if (initOne) {
                  break;
               }
            case 13:
               this._LocalPassword = "myLPWD";
               if (initOne) {
                  break;
               }
            case 12:
               this._LocalPasswordIV = "myLPWDIV";
               if (initOne) {
                  break;
               }
            case 11:
               this._RemoteAccessPoint = "myRAP";
               if (initOne) {
                  break;
               }
            case 15:
               this._RemotePassword = "myRPWD";
               if (initOne) {
                  break;
               }
            case 14:
               this._RemotePasswordIV = "myRPWDIV";
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
      return "WTCPassword";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("LocalAccessPoint")) {
         oldVal = this._LocalAccessPoint;
         this._LocalAccessPoint = (String)v;
         this._postSet(10, oldVal, this._LocalAccessPoint);
      } else if (name.equals("LocalPassword")) {
         oldVal = this._LocalPassword;
         this._LocalPassword = (String)v;
         this._postSet(13, oldVal, this._LocalPassword);
      } else if (name.equals("LocalPasswordIV")) {
         oldVal = this._LocalPasswordIV;
         this._LocalPasswordIV = (String)v;
         this._postSet(12, oldVal, this._LocalPasswordIV);
      } else if (name.equals("RemoteAccessPoint")) {
         oldVal = this._RemoteAccessPoint;
         this._RemoteAccessPoint = (String)v;
         this._postSet(11, oldVal, this._RemoteAccessPoint);
      } else if (name.equals("RemotePassword")) {
         oldVal = this._RemotePassword;
         this._RemotePassword = (String)v;
         this._postSet(15, oldVal, this._RemotePassword);
      } else if (name.equals("RemotePasswordIV")) {
         oldVal = this._RemotePasswordIV;
         this._RemotePasswordIV = (String)v;
         this._postSet(14, oldVal, this._RemotePasswordIV);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("LocalAccessPoint")) {
         return this._LocalAccessPoint;
      } else if (name.equals("LocalPassword")) {
         return this._LocalPassword;
      } else if (name.equals("LocalPasswordIV")) {
         return this._LocalPasswordIV;
      } else if (name.equals("RemoteAccessPoint")) {
         return this._RemoteAccessPoint;
      } else if (name.equals("RemotePassword")) {
         return this._RemotePassword;
      } else {
         return name.equals("RemotePasswordIV") ? this._RemotePasswordIV : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("LocalAccessPoint", "myLAP");
      } catch (IllegalArgumentException var6) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LocalAccessPoint in WTCPasswordMBean" + var6.getMessage());
      }

      try {
         LegalChecks.checkNonNull("LocalPassword", "myLPWD");
      } catch (IllegalArgumentException var5) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LocalPassword in WTCPasswordMBean" + var5.getMessage());
      }

      try {
         LegalChecks.checkNonNull("LocalPasswordIV", "myLPWDIV");
      } catch (IllegalArgumentException var4) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LocalPasswordIV in WTCPasswordMBean" + var4.getMessage());
      }

      try {
         LegalChecks.checkNonNull("RemoteAccessPoint", "myRAP");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property RemoteAccessPoint in WTCPasswordMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonNull("RemotePassword", "myRPWD");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property RemotePassword in WTCPasswordMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("RemotePasswordIV", "myRPWDIV");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property RemotePasswordIV in WTCPasswordMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("local-password")) {
                  return 13;
               }
               break;
            case 15:
               if (s.equals("remote-password")) {
                  return 15;
               }
               break;
            case 16:
               if (s.equals("local-passwordiv")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("remote-passwordiv")) {
                  return 14;
               }
               break;
            case 18:
               if (s.equals("local-access-point")) {
                  return 10;
               }
               break;
            case 19:
               if (s.equals("remote-access-point")) {
                  return 11;
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
               return "local-access-point";
            case 11:
               return "remote-access-point";
            case 12:
               return "local-passwordiv";
            case 13:
               return "local-password";
            case 14:
               return "remote-passwordiv";
            case 15:
               return "remote-password";
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
      private WTCPasswordMBeanImpl bean;

      protected Helper(WTCPasswordMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "LocalAccessPoint";
            case 11:
               return "RemoteAccessPoint";
            case 12:
               return "LocalPasswordIV";
            case 13:
               return "LocalPassword";
            case 14:
               return "RemotePasswordIV";
            case 15:
               return "RemotePassword";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LocalAccessPoint")) {
            return 10;
         } else if (propName.equals("LocalPassword")) {
            return 13;
         } else if (propName.equals("LocalPasswordIV")) {
            return 12;
         } else if (propName.equals("RemoteAccessPoint")) {
            return 11;
         } else if (propName.equals("RemotePassword")) {
            return 15;
         } else {
            return propName.equals("RemotePasswordIV") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isLocalAccessPointSet()) {
               buf.append("LocalAccessPoint");
               buf.append(String.valueOf(this.bean.getLocalAccessPoint()));
            }

            if (this.bean.isLocalPasswordSet()) {
               buf.append("LocalPassword");
               buf.append(String.valueOf(this.bean.getLocalPassword()));
            }

            if (this.bean.isLocalPasswordIVSet()) {
               buf.append("LocalPasswordIV");
               buf.append(String.valueOf(this.bean.getLocalPasswordIV()));
            }

            if (this.bean.isRemoteAccessPointSet()) {
               buf.append("RemoteAccessPoint");
               buf.append(String.valueOf(this.bean.getRemoteAccessPoint()));
            }

            if (this.bean.isRemotePasswordSet()) {
               buf.append("RemotePassword");
               buf.append(String.valueOf(this.bean.getRemotePassword()));
            }

            if (this.bean.isRemotePasswordIVSet()) {
               buf.append("RemotePasswordIV");
               buf.append(String.valueOf(this.bean.getRemotePasswordIV()));
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
            WTCPasswordMBeanImpl otherTyped = (WTCPasswordMBeanImpl)other;
            this.computeDiff("LocalAccessPoint", this.bean.getLocalAccessPoint(), otherTyped.getLocalAccessPoint(), true);
            this.computeDiff("LocalPassword", this.bean.getLocalPassword(), otherTyped.getLocalPassword(), true);
            this.computeDiff("LocalPasswordIV", this.bean.getLocalPasswordIV(), otherTyped.getLocalPasswordIV(), true);
            this.computeDiff("RemoteAccessPoint", this.bean.getRemoteAccessPoint(), otherTyped.getRemoteAccessPoint(), true);
            this.computeDiff("RemotePassword", this.bean.getRemotePassword(), otherTyped.getRemotePassword(), true);
            this.computeDiff("RemotePasswordIV", this.bean.getRemotePasswordIV(), otherTyped.getRemotePasswordIV(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCPasswordMBeanImpl original = (WTCPasswordMBeanImpl)event.getSourceBean();
            WTCPasswordMBeanImpl proposed = (WTCPasswordMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LocalAccessPoint")) {
                  original.setLocalAccessPoint(proposed.getLocalAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("LocalPassword")) {
                  original.setLocalPassword(proposed.getLocalPassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("LocalPasswordIV")) {
                  original.setLocalPasswordIV(proposed.getLocalPasswordIV());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RemoteAccessPoint")) {
                  original.setRemoteAccessPoint(proposed.getRemoteAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RemotePassword")) {
                  original.setRemotePassword(proposed.getRemotePassword());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("RemotePasswordIV")) {
                  original.setRemotePasswordIV(proposed.getRemotePasswordIV());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            WTCPasswordMBeanImpl copy = (WTCPasswordMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LocalAccessPoint")) && this.bean.isLocalAccessPointSet()) {
               copy.setLocalAccessPoint(this.bean.getLocalAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalPassword")) && this.bean.isLocalPasswordSet()) {
               copy.setLocalPassword(this.bean.getLocalPassword());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalPasswordIV")) && this.bean.isLocalPasswordIVSet()) {
               copy.setLocalPasswordIV(this.bean.getLocalPasswordIV());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteAccessPoint")) && this.bean.isRemoteAccessPointSet()) {
               copy.setRemoteAccessPoint(this.bean.getRemoteAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("RemotePassword")) && this.bean.isRemotePasswordSet()) {
               copy.setRemotePassword(this.bean.getRemotePassword());
            }

            if ((excludeProps == null || !excludeProps.contains("RemotePasswordIV")) && this.bean.isRemotePasswordIVSet()) {
               copy.setRemotePasswordIV(this.bean.getRemotePasswordIV());
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
