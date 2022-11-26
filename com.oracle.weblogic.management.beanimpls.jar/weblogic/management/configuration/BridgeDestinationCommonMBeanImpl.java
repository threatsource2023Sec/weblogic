package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class BridgeDestinationCommonMBeanImpl extends ConfigurationMBeanImpl implements BridgeDestinationCommonMBean, Serializable {
   private String _AdapterJNDIName;
   private String _Classpath;
   private String _UserName;
   private String _UserPassword;
   private byte[] _UserPasswordEncrypted;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private BridgeDestinationCommonMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(BridgeDestinationCommonMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(BridgeDestinationCommonMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public BridgeDestinationCommonMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(BridgeDestinationCommonMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      BridgeDestinationCommonMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public BridgeDestinationCommonMBeanImpl() {
      this._initializeProperty(-1);
   }

   public BridgeDestinationCommonMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public BridgeDestinationCommonMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getAdapterJNDIName() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getAdapterJNDIName(), this) : this._AdapterJNDIName;
   }

   public boolean isAdapterJNDINameInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isAdapterJNDINameSet() {
      return this._isSet(10);
   }

   public void setAdapterJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkNonEmptyString("AdapterJNDIName", param0);
      LegalChecks.checkNonNull("AdapterJNDIName", param0);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._AdapterJNDIName;
      this._AdapterJNDIName = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BridgeDestinationCommonMBeanImpl source = (BridgeDestinationCommonMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getUserName() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getUserName(), this) : this._UserName;
   }

   public boolean isUserNameInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isUserNameSet() {
      return this._isSet(11);
   }

   public void setUserName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BridgeDestinationCommonMBeanImpl source = (BridgeDestinationCommonMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getUserPassword() {
      if (!this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12)) {
         return this._performMacroSubstitution(this._getDelegateBean().getUserPassword(), this);
      } else {
         byte[] bEncrypted = this.getUserPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("UserPassword", bEncrypted);
      }
   }

   public boolean isUserPasswordInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isUserPasswordSet() {
      return this.isUserPasswordEncryptedSet();
   }

   public void setUserPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setUserPasswordEncrypted(param0 == null ? null : this._encrypt("UserPassword", param0));
   }

   public byte[] getUserPasswordEncrypted() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getUserPasswordEncrypted() : this._getHelper()._cloneArray(this._UserPasswordEncrypted);
   }

   public String getUserPasswordEncryptedAsString() {
      byte[] obj = this.getUserPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isUserPasswordEncryptedInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isUserPasswordEncryptedSet() {
      return this._isSet(13);
   }

   public void setUserPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setUserPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getClasspath() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getClasspath(), this) : this._Classpath;
   }

   public boolean isClasspathInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isClasspathSet() {
      return this._isSet(14);
   }

   public void setClasspath(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String _oldVal = this._Classpath;
      this._Classpath = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         BridgeDestinationCommonMBeanImpl source = (BridgeDestinationCommonMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public void setUserPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      byte[] _oldVal = this._UserPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: UserPasswordEncrypted of BridgeDestinationCommonMBean");
      } else {
         this._getHelper()._clearArray(this._UserPasswordEncrypted);
         this._UserPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(13, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            BridgeDestinationCommonMBeanImpl source = (BridgeDestinationCommonMBeanImpl)var4.next();
            if (source != null && !source._isSet(13)) {
               source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
            }
         }

      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 12) {
            this._markSet(13, false);
         }
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
               this._AdapterJNDIName = "eis.jms.WLSConnectionFactoryJNDIXA";
               if (initOne) {
                  break;
               }
            case 14:
               this._Classpath = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._UserName = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._UserPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._UserPasswordEncrypted = null;
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
      return "BridgeDestinationCommon";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("AdapterJNDIName")) {
         oldVal = this._AdapterJNDIName;
         this._AdapterJNDIName = (String)v;
         this._postSet(10, oldVal, this._AdapterJNDIName);
      } else if (name.equals("Classpath")) {
         oldVal = this._Classpath;
         this._Classpath = (String)v;
         this._postSet(14, oldVal, this._Classpath);
      } else if (name.equals("UserName")) {
         oldVal = this._UserName;
         this._UserName = (String)v;
         this._postSet(11, oldVal, this._UserName);
      } else if (name.equals("UserPassword")) {
         oldVal = this._UserPassword;
         this._UserPassword = (String)v;
         this._postSet(12, oldVal, this._UserPassword);
      } else if (name.equals("UserPasswordEncrypted")) {
         byte[] oldVal = this._UserPasswordEncrypted;
         this._UserPasswordEncrypted = (byte[])((byte[])v);
         this._postSet(13, oldVal, this._UserPasswordEncrypted);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("AdapterJNDIName")) {
         return this._AdapterJNDIName;
      } else if (name.equals("Classpath")) {
         return this._Classpath;
      } else if (name.equals("UserName")) {
         return this._UserName;
      } else if (name.equals("UserPassword")) {
         return this._UserPassword;
      } else {
         return name.equals("UserPasswordEncrypted") ? this._UserPasswordEncrypted : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("AdapterJNDIName", "eis.jms.WLSConnectionFactoryJNDIXA");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property AdapterJNDIName in BridgeDestinationCommonMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("AdapterJNDIName", "eis.jms.WLSConnectionFactoryJNDIXA");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property AdapterJNDIName in BridgeDestinationCommonMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("classpath")) {
                  return 14;
               }

               if (s.equals("user-name")) {
                  return 11;
               }
               break;
            case 13:
               if (s.equals("user-password")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("adapter-jndi-name")) {
                  return 10;
               }
               break;
            case 23:
               if (s.equals("user-password-encrypted")) {
                  return 13;
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
               return "adapter-jndi-name";
            case 11:
               return "user-name";
            case 12:
               return "user-password";
            case 13:
               return "user-password-encrypted";
            case 14:
               return "classpath";
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
      private BridgeDestinationCommonMBeanImpl bean;

      protected Helper(BridgeDestinationCommonMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "AdapterJNDIName";
            case 11:
               return "UserName";
            case 12:
               return "UserPassword";
            case 13:
               return "UserPasswordEncrypted";
            case 14:
               return "Classpath";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdapterJNDIName")) {
            return 10;
         } else if (propName.equals("Classpath")) {
            return 14;
         } else if (propName.equals("UserName")) {
            return 11;
         } else if (propName.equals("UserPassword")) {
            return 12;
         } else {
            return propName.equals("UserPasswordEncrypted") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isAdapterJNDINameSet()) {
               buf.append("AdapterJNDIName");
               buf.append(String.valueOf(this.bean.getAdapterJNDIName()));
            }

            if (this.bean.isClasspathSet()) {
               buf.append("Classpath");
               buf.append(String.valueOf(this.bean.getClasspath()));
            }

            if (this.bean.isUserNameSet()) {
               buf.append("UserName");
               buf.append(String.valueOf(this.bean.getUserName()));
            }

            if (this.bean.isUserPasswordSet()) {
               buf.append("UserPassword");
               buf.append(String.valueOf(this.bean.getUserPassword()));
            }

            if (this.bean.isUserPasswordEncryptedSet()) {
               buf.append("UserPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUserPasswordEncrypted())));
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
            BridgeDestinationCommonMBeanImpl otherTyped = (BridgeDestinationCommonMBeanImpl)other;
            this.computeDiff("AdapterJNDIName", this.bean.getAdapterJNDIName(), otherTyped.getAdapterJNDIName(), false);
            this.computeDiff("Classpath", this.bean.getClasspath(), otherTyped.getClasspath(), false);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), false);
            this.computeDiff("UserPasswordEncrypted", this.bean.getUserPasswordEncrypted(), otherTyped.getUserPasswordEncrypted(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            BridgeDestinationCommonMBeanImpl original = (BridgeDestinationCommonMBeanImpl)event.getSourceBean();
            BridgeDestinationCommonMBeanImpl proposed = (BridgeDestinationCommonMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdapterJNDIName")) {
                  original.setAdapterJNDIName(proposed.getAdapterJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Classpath")) {
                  original.setClasspath(proposed.getClasspath());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("UserName")) {
                  original.setUserName(proposed.getUserName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (!prop.equals("UserPassword")) {
                  if (prop.equals("UserPasswordEncrypted")) {
                     original.setUserPasswordEncrypted(proposed.getUserPasswordEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            BridgeDestinationCommonMBeanImpl copy = (BridgeDestinationCommonMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdapterJNDIName")) && this.bean.isAdapterJNDINameSet()) {
               copy.setAdapterJNDIName(this.bean.getAdapterJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("Classpath")) && this.bean.isClasspathSet()) {
               copy.setClasspath(this.bean.getClasspath());
            }

            if ((excludeProps == null || !excludeProps.contains("UserName")) && this.bean.isUserNameSet()) {
               copy.setUserName(this.bean.getUserName());
            }

            if ((excludeProps == null || !excludeProps.contains("UserPasswordEncrypted")) && this.bean.isUserPasswordEncryptedSet()) {
               Object o = this.bean.getUserPasswordEncrypted();
               copy.setUserPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
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
