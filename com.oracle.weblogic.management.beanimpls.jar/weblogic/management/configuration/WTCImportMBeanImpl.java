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

public class WTCImportMBeanImpl extends ConfigurationMBeanImpl implements WTCImportMBean, Serializable {
   private String _LocalAccessPoint;
   private String _RemoteAccessPointList;
   private String _RemoteName;
   private String _ResourceName;
   private static SchemaHelper2 _schemaHelper;

   public WTCImportMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCImportMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCImportMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void setResourceName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("ResourceName", param0);
      String _oldVal = this._ResourceName;
      this._ResourceName = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String getResourceName() {
      return this._ResourceName;
   }

   public boolean isResourceNameInherited() {
      return false;
   }

   public boolean isResourceNameSet() {
      return this._isSet(10);
   }

   public void setLocalAccessPoint(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("LocalAccessPoint", param0);
      String _oldVal = this._LocalAccessPoint;
      this._LocalAccessPoint = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getLocalAccessPoint() {
      return this._LocalAccessPoint;
   }

   public boolean isLocalAccessPointInherited() {
      return false;
   }

   public boolean isLocalAccessPointSet() {
      return this._isSet(11);
   }

   public void setRemoteAccessPointList(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("RemoteAccessPointList", param0);
      String _oldVal = this._RemoteAccessPointList;
      this._RemoteAccessPointList = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getRemoteAccessPointList() {
      return this._RemoteAccessPointList;
   }

   public boolean isRemoteAccessPointListInherited() {
      return false;
   }

   public boolean isRemoteAccessPointListSet() {
      return this._isSet(12);
   }

   public void setRemoteName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoteName;
      this._RemoteName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getRemoteName() {
      return this._RemoteName;
   }

   public boolean isRemoteNameInherited() {
      return false;
   }

   public boolean isRemoteNameSet() {
      return this._isSet(13);
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
               this._LocalAccessPoint = "myLAP";
               if (initOne) {
                  break;
               }
            case 12:
               this._RemoteAccessPointList = "myRAP";
               if (initOne) {
                  break;
               }
            case 13:
               this._RemoteName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._ResourceName = "myImport";
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
      return "WTCImport";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("LocalAccessPoint")) {
         oldVal = this._LocalAccessPoint;
         this._LocalAccessPoint = (String)v;
         this._postSet(11, oldVal, this._LocalAccessPoint);
      } else if (name.equals("RemoteAccessPointList")) {
         oldVal = this._RemoteAccessPointList;
         this._RemoteAccessPointList = (String)v;
         this._postSet(12, oldVal, this._RemoteAccessPointList);
      } else if (name.equals("RemoteName")) {
         oldVal = this._RemoteName;
         this._RemoteName = (String)v;
         this._postSet(13, oldVal, this._RemoteName);
      } else if (name.equals("ResourceName")) {
         oldVal = this._ResourceName;
         this._ResourceName = (String)v;
         this._postSet(10, oldVal, this._ResourceName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("LocalAccessPoint")) {
         return this._LocalAccessPoint;
      } else if (name.equals("RemoteAccessPointList")) {
         return this._RemoteAccessPointList;
      } else if (name.equals("RemoteName")) {
         return this._RemoteName;
      } else {
         return name.equals("ResourceName") ? this._ResourceName : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("LocalAccessPoint", "myLAP");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LocalAccessPoint in WTCImportMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonNull("RemoteAccessPointList", "myRAP");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property RemoteAccessPointList in WTCImportMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("ResourceName", "myImport");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property ResourceName in WTCImportMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("remote-name")) {
                  return 13;
               }
               break;
            case 13:
               if (s.equals("resource-name")) {
                  return 10;
               }
               break;
            case 18:
               if (s.equals("local-access-point")) {
                  return 11;
               }
               break;
            case 24:
               if (s.equals("remote-access-point-list")) {
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
               return "resource-name";
            case 11:
               return "local-access-point";
            case 12:
               return "remote-access-point-list";
            case 13:
               return "remote-name";
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
      private WTCImportMBeanImpl bean;

      protected Helper(WTCImportMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ResourceName";
            case 11:
               return "LocalAccessPoint";
            case 12:
               return "RemoteAccessPointList";
            case 13:
               return "RemoteName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LocalAccessPoint")) {
            return 11;
         } else if (propName.equals("RemoteAccessPointList")) {
            return 12;
         } else if (propName.equals("RemoteName")) {
            return 13;
         } else {
            return propName.equals("ResourceName") ? 10 : super.getPropertyIndex(propName);
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

            if (this.bean.isRemoteAccessPointListSet()) {
               buf.append("RemoteAccessPointList");
               buf.append(String.valueOf(this.bean.getRemoteAccessPointList()));
            }

            if (this.bean.isRemoteNameSet()) {
               buf.append("RemoteName");
               buf.append(String.valueOf(this.bean.getRemoteName()));
            }

            if (this.bean.isResourceNameSet()) {
               buf.append("ResourceName");
               buf.append(String.valueOf(this.bean.getResourceName()));
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
            WTCImportMBeanImpl otherTyped = (WTCImportMBeanImpl)other;
            this.computeDiff("LocalAccessPoint", this.bean.getLocalAccessPoint(), otherTyped.getLocalAccessPoint(), true);
            this.computeDiff("RemoteAccessPointList", this.bean.getRemoteAccessPointList(), otherTyped.getRemoteAccessPointList(), true);
            this.computeDiff("RemoteName", this.bean.getRemoteName(), otherTyped.getRemoteName(), true);
            this.computeDiff("ResourceName", this.bean.getResourceName(), otherTyped.getResourceName(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCImportMBeanImpl original = (WTCImportMBeanImpl)event.getSourceBean();
            WTCImportMBeanImpl proposed = (WTCImportMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LocalAccessPoint")) {
                  original.setLocalAccessPoint(proposed.getLocalAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RemoteAccessPointList")) {
                  original.setRemoteAccessPointList(proposed.getRemoteAccessPointList());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RemoteName")) {
                  original.setRemoteName(proposed.getRemoteName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ResourceName")) {
                  original.setResourceName(proposed.getResourceName());
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
            WTCImportMBeanImpl copy = (WTCImportMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LocalAccessPoint")) && this.bean.isLocalAccessPointSet()) {
               copy.setLocalAccessPoint(this.bean.getLocalAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteAccessPointList")) && this.bean.isRemoteAccessPointListSet()) {
               copy.setRemoteAccessPointList(this.bean.getRemoteAccessPointList());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteName")) && this.bean.isRemoteNameSet()) {
               copy.setRemoteName(this.bean.getRemoteName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceName")) && this.bean.isResourceNameSet()) {
               copy.setResourceName(this.bean.getResourceName());
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
