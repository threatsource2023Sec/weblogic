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

public class WTCExportMBeanImpl extends ConfigurationMBeanImpl implements WTCExportMBean, Serializable {
   private String _EJBName;
   private String _LocalAccessPoint;
   private String _RemoteName;
   private String _ResourceName;
   private String _TargetClass;
   private String _TargetJar;
   private static SchemaHelper2 _schemaHelper;

   public WTCExportMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WTCExportMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WTCExportMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public void setEJBName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EJBName;
      this._EJBName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getEJBName() {
      return this._EJBName;
   }

   public boolean isEJBNameInherited() {
      return false;
   }

   public boolean isEJBNameSet() {
      return this._isSet(12);
   }

   public void setTargetClass(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TargetClass;
      this._TargetClass = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getTargetClass() {
      return this._TargetClass;
   }

   public boolean isTargetClassInherited() {
      return false;
   }

   public boolean isTargetClassSet() {
      return this._isSet(13);
   }

   public void setTargetJar(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._TargetJar;
      this._TargetJar = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getTargetJar() {
      return this._TargetJar;
   }

   public boolean isTargetJarInherited() {
      return false;
   }

   public boolean isTargetJarSet() {
      return this._isSet(14);
   }

   public void setRemoteName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoteName;
      this._RemoteName = param0;
      this._postSet(15, _oldVal, param0);
   }

   public String getRemoteName() {
      return this._RemoteName;
   }

   public boolean isRemoteNameInherited() {
      return false;
   }

   public boolean isRemoteNameSet() {
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._EJBName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._LocalAccessPoint = "myLAP";
               if (initOne) {
                  break;
               }
            case 15:
               this._RemoteName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._ResourceName = "myExport";
               if (initOne) {
                  break;
               }
            case 13:
               this._TargetClass = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._TargetJar = null;
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
      return "WTCExport";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("EJBName")) {
         oldVal = this._EJBName;
         this._EJBName = (String)v;
         this._postSet(12, oldVal, this._EJBName);
      } else if (name.equals("LocalAccessPoint")) {
         oldVal = this._LocalAccessPoint;
         this._LocalAccessPoint = (String)v;
         this._postSet(11, oldVal, this._LocalAccessPoint);
      } else if (name.equals("RemoteName")) {
         oldVal = this._RemoteName;
         this._RemoteName = (String)v;
         this._postSet(15, oldVal, this._RemoteName);
      } else if (name.equals("ResourceName")) {
         oldVal = this._ResourceName;
         this._ResourceName = (String)v;
         this._postSet(10, oldVal, this._ResourceName);
      } else if (name.equals("TargetClass")) {
         oldVal = this._TargetClass;
         this._TargetClass = (String)v;
         this._postSet(13, oldVal, this._TargetClass);
      } else if (name.equals("TargetJar")) {
         oldVal = this._TargetJar;
         this._TargetJar = (String)v;
         this._postSet(14, oldVal, this._TargetJar);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("EJBName")) {
         return this._EJBName;
      } else if (name.equals("LocalAccessPoint")) {
         return this._LocalAccessPoint;
      } else if (name.equals("RemoteName")) {
         return this._RemoteName;
      } else if (name.equals("ResourceName")) {
         return this._ResourceName;
      } else if (name.equals("TargetClass")) {
         return this._TargetClass;
      } else {
         return name.equals("TargetJar") ? this._TargetJar : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("LocalAccessPoint", "myLAP");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LocalAccessPoint in WTCExportMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("ResourceName", "myExport");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property ResourceName in WTCExportMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("ejb-name")) {
                  return 12;
               }
            case 9:
            case 14:
            case 15:
            case 16:
            case 17:
            default:
               break;
            case 10:
               if (s.equals("target-jar")) {
                  return 14;
               }
               break;
            case 11:
               if (s.equals("remote-name")) {
                  return 15;
               }
               break;
            case 12:
               if (s.equals("target-class")) {
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
               return "ejb-name";
            case 13:
               return "target-class";
            case 14:
               return "target-jar";
            case 15:
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
      private WTCExportMBeanImpl bean;

      protected Helper(WTCExportMBeanImpl bean) {
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
               return "EJBName";
            case 13:
               return "TargetClass";
            case 14:
               return "TargetJar";
            case 15:
               return "RemoteName";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EJBName")) {
            return 12;
         } else if (propName.equals("LocalAccessPoint")) {
            return 11;
         } else if (propName.equals("RemoteName")) {
            return 15;
         } else if (propName.equals("ResourceName")) {
            return 10;
         } else if (propName.equals("TargetClass")) {
            return 13;
         } else {
            return propName.equals("TargetJar") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isEJBNameSet()) {
               buf.append("EJBName");
               buf.append(String.valueOf(this.bean.getEJBName()));
            }

            if (this.bean.isLocalAccessPointSet()) {
               buf.append("LocalAccessPoint");
               buf.append(String.valueOf(this.bean.getLocalAccessPoint()));
            }

            if (this.bean.isRemoteNameSet()) {
               buf.append("RemoteName");
               buf.append(String.valueOf(this.bean.getRemoteName()));
            }

            if (this.bean.isResourceNameSet()) {
               buf.append("ResourceName");
               buf.append(String.valueOf(this.bean.getResourceName()));
            }

            if (this.bean.isTargetClassSet()) {
               buf.append("TargetClass");
               buf.append(String.valueOf(this.bean.getTargetClass()));
            }

            if (this.bean.isTargetJarSet()) {
               buf.append("TargetJar");
               buf.append(String.valueOf(this.bean.getTargetJar()));
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
            WTCExportMBeanImpl otherTyped = (WTCExportMBeanImpl)other;
            this.computeDiff("EJBName", this.bean.getEJBName(), otherTyped.getEJBName(), true);
            this.computeDiff("LocalAccessPoint", this.bean.getLocalAccessPoint(), otherTyped.getLocalAccessPoint(), true);
            this.computeDiff("RemoteName", this.bean.getRemoteName(), otherTyped.getRemoteName(), true);
            this.computeDiff("ResourceName", this.bean.getResourceName(), otherTyped.getResourceName(), true);
            this.computeDiff("TargetClass", this.bean.getTargetClass(), otherTyped.getTargetClass(), true);
            this.computeDiff("TargetJar", this.bean.getTargetJar(), otherTyped.getTargetJar(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WTCExportMBeanImpl original = (WTCExportMBeanImpl)event.getSourceBean();
            WTCExportMBeanImpl proposed = (WTCExportMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EJBName")) {
                  original.setEJBName(proposed.getEJBName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("LocalAccessPoint")) {
                  original.setLocalAccessPoint(proposed.getLocalAccessPoint());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RemoteName")) {
                  original.setRemoteName(proposed.getRemoteName());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ResourceName")) {
                  original.setResourceName(proposed.getResourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("TargetClass")) {
                  original.setTargetClass(proposed.getTargetClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("TargetJar")) {
                  original.setTargetJar(proposed.getTargetJar());
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
            WTCExportMBeanImpl copy = (WTCExportMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EJBName")) && this.bean.isEJBNameSet()) {
               copy.setEJBName(this.bean.getEJBName());
            }

            if ((excludeProps == null || !excludeProps.contains("LocalAccessPoint")) && this.bean.isLocalAccessPointSet()) {
               copy.setLocalAccessPoint(this.bean.getLocalAccessPoint());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteName")) && this.bean.isRemoteNameSet()) {
               copy.setRemoteName(this.bean.getRemoteName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceName")) && this.bean.isResourceNameSet()) {
               copy.setResourceName(this.bean.getResourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetClass")) && this.bean.isTargetClassSet()) {
               copy.setTargetClass(this.bean.getTargetClass());
            }

            if ((excludeProps == null || !excludeProps.contains("TargetJar")) && this.bean.isTargetJarSet()) {
               copy.setTargetJar(this.bean.getTargetJar());
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
