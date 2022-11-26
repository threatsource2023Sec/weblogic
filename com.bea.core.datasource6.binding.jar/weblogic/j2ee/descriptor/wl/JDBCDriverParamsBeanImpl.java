package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.jdbc.module.JDBCDriverParamsCustomizer;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class JDBCDriverParamsBeanImpl extends SettableBeanImpl implements JDBCDriverParamsBean, Serializable {
   private String _DriverName;
   private String _Password;
   private byte[] _PasswordEncrypted;
   private JDBCPropertiesBean _Properties;
   private String _Url;
   private boolean _UsePasswordIndirection;
   private boolean _UseXaDataSourceInterface;
   private transient JDBCDriverParamsCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public JDBCDriverParamsBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jdbc.module.JDBCDriverParamsBeanCustomizerFactory");
         this._customizer = (JDBCDriverParamsCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public JDBCDriverParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jdbc.module.JDBCDriverParamsBeanCustomizerFactory");
         this._customizer = (JDBCDriverParamsCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public JDBCDriverParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.jdbc.module.JDBCDriverParamsBeanCustomizerFactory");
         this._customizer = (JDBCDriverParamsCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getUrl() {
      return this._Url;
   }

   public boolean isUrlInherited() {
      return false;
   }

   public boolean isUrlSet() {
      return this._isSet(0);
   }

   public void setUrl(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Url;
      this._Url = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getDriverName() {
      return this._DriverName;
   }

   public boolean isDriverNameInherited() {
      return false;
   }

   public boolean isDriverNameSet() {
      return this._isSet(1);
   }

   public void setDriverName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DriverName;
      this._DriverName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public JDBCPropertiesBean getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getProperties());
   }

   public void setProperties(JDBCPropertiesBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      JDBCPropertiesBean _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(2, _oldVal, param0);
   }

   public byte[] getPasswordEncrypted() {
      return this._getHelper()._cloneArray(this._PasswordEncrypted);
   }

   public String getPasswordEncryptedAsString() {
      byte[] obj = this.getPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isPasswordEncryptedInherited() {
      return false;
   }

   public boolean isPasswordEncryptedSet() {
      return this._isSet(3);
   }

   public void setPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isUseXaDataSourceInterface() {
      return this._UseXaDataSourceInterface;
   }

   public boolean isUseXaDataSourceInterfaceInherited() {
      return false;
   }

   public boolean isUseXaDataSourceInterfaceSet() {
      return this._isSet(4);
   }

   public void setUseXaDataSourceInterface(boolean param0) {
      boolean _oldVal = this._UseXaDataSourceInterface;
      this._UseXaDataSourceInterface = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getPassword() {
      byte[] bEncrypted = this.getPasswordEncrypted();
      return bEncrypted == null ? null : this._decrypt("Password", bEncrypted);
   }

   public boolean isPasswordInherited() {
      return false;
   }

   public boolean isPasswordSet() {
      return this.isPasswordEncryptedSet();
   }

   public void setPassword(String param0) {
      param0 = param0 == null ? null : param0.trim();
      this.setPasswordEncrypted(param0 == null ? null : this._encrypt("Password", param0));
   }

   public boolean isUsePasswordIndirection() {
      return this._UsePasswordIndirection;
   }

   public boolean isUsePasswordIndirectionInherited() {
      return false;
   }

   public boolean isUsePasswordIndirectionSet() {
      return this._isSet(6);
   }

   public void setUsePasswordIndirection(boolean param0) {
      boolean _oldVal = this._UsePasswordIndirection;
      this._UsePasswordIndirection = param0;
      this._postSet(6, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setPasswordEncrypted(byte[] param0) {
      byte[] _oldVal = this._PasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: PasswordEncrypted of JDBCDriverParamsBean");
      } else {
         this._getHelper()._clearArray(this._PasswordEncrypted);
         this._PasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(3, _oldVal, param0);
      }
   }

   protected void _postCreate() {
      this._customizer._postCreate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 5) {
            this._markSet(3, false);
         }
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet() || this.isPropertiesSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._DriverName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Properties = new JDBCPropertiesBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._Properties);
               if (initOne) {
                  break;
               }
            case 0:
               this._Url = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._UsePasswordIndirection = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._UseXaDataSourceInterface = true;
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("url")) {
                  return 0;
               }
               break;
            case 8:
               if (s.equals("password")) {
                  return 5;
               }
               break;
            case 10:
               if (s.equals("properties")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("driver-name")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("password-encrypted")) {
                  return 3;
               }
               break;
            case 24:
               if (s.equals("use-password-indirection")) {
                  return 6;
               }
               break;
            case 28:
               if (s.equals("use-xa-data-source-interface")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new JDBCPropertiesBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "url";
            case 1:
               return "driver-name";
            case 2:
               return "properties";
            case 3:
               return "password-encrypted";
            case 4:
               return "use-xa-data-source-interface";
            case 5:
               return "password";
            case 6:
               return "use-password-indirection";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            case 6:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private JDBCDriverParamsBeanImpl bean;

      protected Helper(JDBCDriverParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Url";
            case 1:
               return "DriverName";
            case 2:
               return "Properties";
            case 3:
               return "PasswordEncrypted";
            case 4:
               return "UseXaDataSourceInterface";
            case 5:
               return "Password";
            case 6:
               return "UsePasswordIndirection";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DriverName")) {
            return 1;
         } else if (propName.equals("Password")) {
            return 5;
         } else if (propName.equals("PasswordEncrypted")) {
            return 3;
         } else if (propName.equals("Properties")) {
            return 2;
         } else if (propName.equals("Url")) {
            return 0;
         } else if (propName.equals("UsePasswordIndirection")) {
            return 6;
         } else {
            return propName.equals("UseXaDataSourceInterface") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getProperties() != null) {
            iterators.add(new ArrayIterator(new JDBCPropertiesBean[]{this.bean.getProperties()}));
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
            if (this.bean.isDriverNameSet()) {
               buf.append("DriverName");
               buf.append(String.valueOf(this.bean.getDriverName()));
            }

            if (this.bean.isPasswordSet()) {
               buf.append("Password");
               buf.append(String.valueOf(this.bean.getPassword()));
            }

            if (this.bean.isPasswordEncryptedSet()) {
               buf.append("PasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getPasswordEncrypted())));
            }

            childValue = this.computeChildHashValue(this.bean.getProperties());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isUrlSet()) {
               buf.append("Url");
               buf.append(String.valueOf(this.bean.getUrl()));
            }

            if (this.bean.isUsePasswordIndirectionSet()) {
               buf.append("UsePasswordIndirection");
               buf.append(String.valueOf(this.bean.isUsePasswordIndirection()));
            }

            if (this.bean.isUseXaDataSourceInterfaceSet()) {
               buf.append("UseXaDataSourceInterface");
               buf.append(String.valueOf(this.bean.isUseXaDataSourceInterface()));
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
            JDBCDriverParamsBeanImpl otherTyped = (JDBCDriverParamsBeanImpl)other;
            this.computeDiff("DriverName", this.bean.getDriverName(), otherTyped.getDriverName(), false);
            this.computeDiff("PasswordEncrypted", this.bean.getPasswordEncrypted(), otherTyped.getPasswordEncrypted(), true);
            this.computeSubDiff("Properties", this.bean.getProperties(), otherTyped.getProperties());
            this.computeDiff("Url", this.bean.getUrl(), otherTyped.getUrl(), false);
            this.computeDiff("UsePasswordIndirection", this.bean.isUsePasswordIndirection(), otherTyped.isUsePasswordIndirection(), false);
            this.computeDiff("UseXaDataSourceInterface", this.bean.isUseXaDataSourceInterface(), otherTyped.isUseXaDataSourceInterface(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JDBCDriverParamsBeanImpl original = (JDBCDriverParamsBeanImpl)event.getSourceBean();
            JDBCDriverParamsBeanImpl proposed = (JDBCDriverParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("DriverName")) {
                  original.setDriverName(proposed.getDriverName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (!prop.equals("Password")) {
                  if (prop.equals("PasswordEncrypted")) {
                     original.setPasswordEncrypted(proposed.getPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  } else if (prop.equals("Properties")) {
                     if (type == 2) {
                        original.setProperties((JDBCPropertiesBean)this.createCopy((AbstractDescriptorBean)proposed.getProperties()));
                     } else {
                        if (type != 3) {
                           throw new AssertionError("Invalid type: " + type);
                        }

                        original._destroySingleton("Properties", (DescriptorBean)original.getProperties());
                     }

                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  } else if (prop.equals("Url")) {
                     original.setUrl(proposed.getUrl());
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  } else if (prop.equals("UsePasswordIndirection")) {
                     original.setUsePasswordIndirection(proposed.isUsePasswordIndirection());
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  } else if (prop.equals("UseXaDataSourceInterface")) {
                     original.setUseXaDataSourceInterface(proposed.isUseXaDataSourceInterface());
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  } else {
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
            JDBCDriverParamsBeanImpl copy = (JDBCDriverParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("DriverName")) && this.bean.isDriverNameSet()) {
               copy.setDriverName(this.bean.getDriverName());
            }

            if ((excludeProps == null || !excludeProps.contains("PasswordEncrypted")) && this.bean.isPasswordEncryptedSet()) {
               Object o = this.bean.getPasswordEncrypted();
               copy.setPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet() && !copy._isSet(2)) {
               Object o = this.bean.getProperties();
               copy.setProperties((JDBCPropertiesBean)null);
               copy.setProperties(o == null ? null : (JDBCPropertiesBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Url")) && this.bean.isUrlSet()) {
               copy.setUrl(this.bean.getUrl());
            }

            if ((excludeProps == null || !excludeProps.contains("UsePasswordIndirection")) && this.bean.isUsePasswordIndirectionSet()) {
               copy.setUsePasswordIndirection(this.bean.isUsePasswordIndirection());
            }

            if ((excludeProps == null || !excludeProps.contains("UseXaDataSourceInterface")) && this.bean.isUseXaDataSourceInterfaceSet()) {
               copy.setUseXaDataSourceInterface(this.bean.isUseXaDataSourceInterface());
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
         this.inferSubTree(this.bean.getProperties(), clazz, annotation);
      }
   }
}
