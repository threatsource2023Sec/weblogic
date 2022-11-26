package weblogic.management;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanInfo;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.internal.mbean.AbstractDynamicMBean;
import weblogic.management.mbeans.custom.WebLogic;
import weblogic.utils.collections.CombinedIterator;

public class WebLogicMBeanImpl extends AbstractDynamicMBean implements WebLogicMBean, Serializable {
   private boolean _CachingDisabled;
   private MBeanInfo _MBeanInfo;
   private String _Name;
   private WebLogicObjectName _ObjectName;
   private WebLogicMBean _Parent;
   private boolean _Registered;
   private String _Type;
   private transient WebLogic _customizer;
   private static SchemaHelper2 _schemaHelper;

   public WebLogicMBeanImpl() {
      try {
         this._customizer = new WebLogic(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WebLogicMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WebLogic(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WebLogicMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WebLogic(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      return this._customizer.getName();
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(1, _oldVal, param0);
   }

   public String getType() {
      return this._Type;
   }

   public boolean isTypeInherited() {
      return false;
   }

   public boolean isTypeSet() {
      return this._isSet(2);
   }

   public void setType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      this._Type = param0;
   }

   public WebLogicObjectName getObjectName() {
      return this._customizer.getObjectName();
   }

   public boolean isObjectNameInherited() {
      return false;
   }

   public boolean isObjectNameSet() {
      return this._isSet(3);
   }

   public void setObjectName(WebLogicObjectName param0) throws InvalidAttributeValueException {
      this._ObjectName = param0;
   }

   public MBeanInfo getMBeanInfo() {
      return this._MBeanInfo;
   }

   public boolean isCachingDisabled() {
      return this._CachingDisabled;
   }

   public boolean isCachingDisabledInherited() {
      return false;
   }

   public boolean isCachingDisabledSet() {
      return this._isSet(4);
   }

   public boolean isMBeanInfoInherited() {
      return false;
   }

   public boolean isMBeanInfoSet() {
      return this._isSet(0);
   }

   public void setCachingDisabled(boolean param0) throws InvalidAttributeValueException {
      this._CachingDisabled = param0;
   }

   public void setMBeanInfo(MBeanInfo param0) throws InvalidAttributeValueException {
      this._MBeanInfo = param0;
   }

   public WebLogicMBean getParent() {
      return this._customizer.getParent();
   }

   public boolean isParentInherited() {
      return false;
   }

   public boolean isParentSet() {
      return this._isSet(5);
   }

   public void setParent(WebLogicMBean param0) throws ConfigurationException {
      this._customizer.setParent(param0);
   }

   public boolean isRegistered() {
      return this._customizer.isRegistered();
   }

   public boolean isRegisteredInherited() {
      return false;
   }

   public boolean isRegisteredSet() {
      return this._isSet(6);
   }

   public void setRegistered(boolean param0) throws InvalidAttributeValueException {
      this._Registered = param0;
   }

   public Object _getKey() {
      return this.getName();
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
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._MBeanInfo = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 3:
               this._ObjectName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._customizer.setParent((WebLogicMBean)null);
               if (initOne) {
                  break;
               }
            case 2:
               this._Type = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._CachingDisabled = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._Registered = false;
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

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("CachingDisabled")) {
         oldVal = this._CachingDisabled;
         this._CachingDisabled = (Boolean)v;
         this._postSet(4, oldVal, this._CachingDisabled);
      } else if (name.equals("MBeanInfo")) {
         MBeanInfo oldVal = this._MBeanInfo;
         this._MBeanInfo = (MBeanInfo)v;
         this._postSet(0, oldVal, this._MBeanInfo);
      } else {
         String oldVal;
         if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(1, oldVal, this._Name);
         } else if (name.equals("ObjectName")) {
            WebLogicObjectName oldVal = this._ObjectName;
            this._ObjectName = (WebLogicObjectName)v;
            this._postSet(3, oldVal, this._ObjectName);
         } else if (name.equals("Parent")) {
            WebLogicMBean oldVal = this._Parent;
            this._Parent = (WebLogicMBean)v;
            this._postSet(5, oldVal, this._Parent);
         } else if (name.equals("Registered")) {
            oldVal = this._Registered;
            this._Registered = (Boolean)v;
            this._postSet(6, oldVal, this._Registered);
         } else if (name.equals("Type")) {
            oldVal = this._Type;
            this._Type = (String)v;
            this._postSet(2, oldVal, this._Type);
         } else if (name.equals("customizer")) {
            WebLogic oldVal = this._customizer;
            this._customizer = (WebLogic)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("CachingDisabled")) {
         return new Boolean(this._CachingDisabled);
      } else if (name.equals("MBeanInfo")) {
         return this._MBeanInfo;
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("ObjectName")) {
         return this._ObjectName;
      } else if (name.equals("Parent")) {
         return this._Parent;
      } else if (name.equals("Registered")) {
         return new Boolean(this._Registered);
      } else if (name.equals("Type")) {
         return this._Type;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 1;
               }

               if (s.equals("type")) {
                  return 2;
               }
            case 5:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
            case 14:
            case 15:
            default:
               break;
            case 6:
               if (s.equals("parent")) {
                  return 5;
               }
               break;
            case 10:
               if (s.equals("registered")) {
                  return 6;
               }
               break;
            case 11:
               if (s.equals("m-bean-info")) {
                  return 0;
               }

               if (s.equals("object-name")) {
                  return 3;
               }
               break;
            case 16:
               if (s.equals("caching-disabled")) {
                  return 4;
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
               return "m-bean-info";
            case 1:
               return "name";
            case 2:
               return "type";
            case 3:
               return "object-name";
            case 4:
               return "caching-disabled";
            case 5:
               return "parent";
            case 6:
               return "registered";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDynamicMBean.Helper {
      private WebLogicMBeanImpl bean;

      protected Helper(WebLogicMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MBeanInfo";
            case 1:
               return "Name";
            case 2:
               return "Type";
            case 3:
               return "ObjectName";
            case 4:
               return "CachingDisabled";
            case 5:
               return "Parent";
            case 6:
               return "Registered";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MBeanInfo")) {
            return 0;
         } else if (propName.equals("Name")) {
            return 1;
         } else if (propName.equals("ObjectName")) {
            return 3;
         } else if (propName.equals("Parent")) {
            return 5;
         } else if (propName.equals("Type")) {
            return 2;
         } else if (propName.equals("CachingDisabled")) {
            return 4;
         } else {
            return propName.equals("Registered") ? 6 : super.getPropertyIndex(propName);
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
            if (this.bean.isMBeanInfoSet()) {
               buf.append("MBeanInfo");
               buf.append(String.valueOf(this.bean.getMBeanInfo()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isObjectNameSet()) {
               buf.append("ObjectName");
               buf.append(String.valueOf(this.bean.getObjectName()));
            }

            if (this.bean.isParentSet()) {
               buf.append("Parent");
               buf.append(String.valueOf(this.bean.getParent()));
            }

            if (this.bean.isTypeSet()) {
               buf.append("Type");
               buf.append(String.valueOf(this.bean.getType()));
            }

            if (this.bean.isCachingDisabledSet()) {
               buf.append("CachingDisabled");
               buf.append(String.valueOf(this.bean.isCachingDisabled()));
            }

            if (this.bean.isRegisteredSet()) {
               buf.append("Registered");
               buf.append(String.valueOf(this.bean.isRegistered()));
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
            WebLogicMBeanImpl otherTyped = (WebLogicMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebLogicMBeanImpl original = (WebLogicMBeanImpl)event.getSourceBean();
            WebLogicMBeanImpl proposed = (WebLogicMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("MBeanInfo")) {
                  if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  } else if (!prop.equals("ObjectName") && !prop.equals("Parent") && !prop.equals("Type") && !prop.equals("CachingDisabled") && !prop.equals("Registered")) {
                     super.applyPropertyUpdate(event, update);
                  }
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
            WebLogicMBeanImpl copy = (WebLogicMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
         this.inferSubTree(this.bean.getParent(), clazz, annotation);
      }
   }
}
