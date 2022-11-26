package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.WebServiceLogicalStore;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WebServiceLogicalStoreMBeanImpl extends ConfigurationMBeanImpl implements WebServiceLogicalStoreMBean, Serializable {
   private String _CleanerInterval;
   private String _DefaultMaximumObjectLifetime;
   private boolean _DynamicallyCreated;
   private String _Name;
   private String _PersistenceStrategy;
   private String _PhysicalStoreName;
   private String _RequestBufferingQueueJndiName;
   private String _ResponseBufferingQueueJndiName;
   private String[] _Tags;
   private transient WebServiceLogicalStore _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServiceLogicalStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServiceLogicalStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServiceLogicalStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServiceLogicalStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServiceLogicalStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServiceLogicalStoreMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WebServiceLogicalStoreMBeanImpl() {
      try {
         this._customizer = new WebServiceLogicalStore(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public WebServiceLogicalStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new WebServiceLogicalStore(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public WebServiceLogicalStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new WebServiceLogicalStore(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getName() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2) ? this._performMacroSubstitution(this._getDelegateBean().getName(), this) : this._customizer.getName();
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      WseeConfigBeanValidator.validateLogicalStoreName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPersistenceStrategy() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getPersistenceStrategy(), this) : this._PersistenceStrategy;
   }

   public boolean isPersistenceStrategyInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isPersistenceStrategySet() {
      return this._isSet(10);
   }

   public void setPersistenceStrategy(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"LOCAL_ACCESS_ONLY", "IN_MEMORY"};
      param0 = LegalChecks.checkInEnum("PersistenceStrategy", param0, _set);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._PersistenceStrategy;
      this._PersistenceStrategy = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var5.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public void setCleanerInterval(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateCleanerInterval(param0);
      boolean wasSet = this._isSet(11);
      String _oldVal = this._CleanerInterval;
      this._CleanerInterval = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCleanerInterval() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getCleanerInterval(), this) : this._CleanerInterval;
   }

   public boolean isCleanerIntervalInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isCleanerIntervalSet() {
      return this._isSet(11);
   }

   public void setDefaultMaximumObjectLifetime(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateDefaultMaximumObjectLifetime(param0);
      boolean wasSet = this._isSet(12);
      String _oldVal = this._DefaultMaximumObjectLifetime;
      this._DefaultMaximumObjectLifetime = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultMaximumObjectLifetime() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultMaximumObjectLifetime(), this) : this._DefaultMaximumObjectLifetime;
   }

   public boolean isDefaultMaximumObjectLifetimeInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isDefaultMaximumObjectLifetimeSet() {
      return this._isSet(12);
   }

   public void setRequestBufferingQueueJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateRequestBufferingQueueJndiName(param0);
      boolean wasSet = this._isSet(13);
      String _oldVal = this._RequestBufferingQueueJndiName;
      this._RequestBufferingQueueJndiName = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRequestBufferingQueueJndiName() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getRequestBufferingQueueJndiName(), this) : this._RequestBufferingQueueJndiName;
   }

   public boolean isRequestBufferingQueueJndiNameInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isRequestBufferingQueueJndiNameSet() {
      return this._isSet(13);
   }

   public void setResponseBufferingQueueJndiName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateResponseBufferingQueueJndiName(param0);
      boolean wasSet = this._isSet(14);
      String _oldVal = this._ResponseBufferingQueueJndiName;
      this._ResponseBufferingQueueJndiName = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public String getResponseBufferingQueueJndiName() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getResponseBufferingQueueJndiName(), this) : this._ResponseBufferingQueueJndiName;
   }

   public boolean isResponseBufferingQueueJndiNameInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isResponseBufferingQueueJndiNameSet() {
      return this._isSet(14);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
   }

   public void setPhysicalStoreName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      String _oldVal = this._PhysicalStoreName;
      this._PhysicalStoreName = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPhysicalStoreName() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getPhysicalStoreName(), this) : this._PhysicalStoreName;
   }

   public boolean isPhysicalStoreNameInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isPhysicalStoreNameSet() {
      return this._isSet(15);
   }

   public boolean isDynamicallyCreated() {
      return this._customizer.isDynamicallyCreated();
   }

   public boolean isDynamicallyCreatedInherited() {
      return false;
   }

   public boolean isDynamicallyCreatedSet() {
      return this._isSet(7);
   }

   public void setDynamicallyCreated(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._DynamicallyCreated = param0;
   }

   public String[] getTags() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9) ? this._getDelegateBean().getTags() : this._customizer.getTags();
   }

   public boolean isTagsInherited() {
      return !this._isSet(9) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(9);
   }

   public boolean isTagsSet() {
      return this._isSet(9);
   }

   public void setTags(String[] param0) throws IllegalArgumentException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(9);
      String[] _oldVal = this.getTags();
      this._customizer.setTags(param0);
      this._postSet(9, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceLogicalStoreMBeanImpl source = (WebServiceLogicalStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(9)) {
            source._postSetFirePropertyChange(9, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTag(String param0) throws IllegalArgumentException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new = (String[])((String[])this._getHelper()._extendArray(this.getTags(), String.class, param0));

      try {
         this.setTags(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeTag(String param0) throws IllegalArgumentException {
      String[] _old = this.getTags();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTags(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof IllegalArgumentException) {
               throw (IllegalArgumentException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _preDestroy() {
      this._customizer._preDestroy();
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
               this._CleanerInterval = "PT10M";
               if (initOne) {
                  break;
               }
            case 12:
               this._DefaultMaximumObjectLifetime = "P1D";
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 10:
               this._PersistenceStrategy = "LOCAL_ACCESS_ONLY";
               if (initOne) {
                  break;
               }
            case 15:
               this._PhysicalStoreName = "";
               if (initOne) {
                  break;
               }
            case 13:
               this._RequestBufferingQueueJndiName = "";
               if (initOne) {
                  break;
               }
            case 14:
               this._ResponseBufferingQueueJndiName = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 7:
               this._DynamicallyCreated = false;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
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
      return "WebServiceLogicalStore";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("CleanerInterval")) {
         oldVal = this._CleanerInterval;
         this._CleanerInterval = (String)v;
         this._postSet(11, oldVal, this._CleanerInterval);
      } else if (name.equals("DefaultMaximumObjectLifetime")) {
         oldVal = this._DefaultMaximumObjectLifetime;
         this._DefaultMaximumObjectLifetime = (String)v;
         this._postSet(12, oldVal, this._DefaultMaximumObjectLifetime);
      } else if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else if (name.equals("PersistenceStrategy")) {
         oldVal = this._PersistenceStrategy;
         this._PersistenceStrategy = (String)v;
         this._postSet(10, oldVal, this._PersistenceStrategy);
      } else if (name.equals("PhysicalStoreName")) {
         oldVal = this._PhysicalStoreName;
         this._PhysicalStoreName = (String)v;
         this._postSet(15, oldVal, this._PhysicalStoreName);
      } else if (name.equals("RequestBufferingQueueJndiName")) {
         oldVal = this._RequestBufferingQueueJndiName;
         this._RequestBufferingQueueJndiName = (String)v;
         this._postSet(13, oldVal, this._RequestBufferingQueueJndiName);
      } else if (name.equals("ResponseBufferingQueueJndiName")) {
         oldVal = this._ResponseBufferingQueueJndiName;
         this._ResponseBufferingQueueJndiName = (String)v;
         this._postSet(14, oldVal, this._ResponseBufferingQueueJndiName);
      } else if (name.equals("Tags")) {
         String[] oldVal = this._Tags;
         this._Tags = (String[])((String[])v);
         this._postSet(9, oldVal, this._Tags);
      } else if (name.equals("customizer")) {
         WebServiceLogicalStore oldVal = this._customizer;
         this._customizer = (WebServiceLogicalStore)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CleanerInterval")) {
         return this._CleanerInterval;
      } else if (name.equals("DefaultMaximumObjectLifetime")) {
         return this._DefaultMaximumObjectLifetime;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("PersistenceStrategy")) {
         return this._PersistenceStrategy;
      } else if (name.equals("PhysicalStoreName")) {
         return this._PhysicalStoreName;
      } else if (name.equals("RequestBufferingQueueJndiName")) {
         return this._RequestBufferingQueueJndiName;
      } else if (name.equals("ResponseBufferingQueueJndiName")) {
         return this._ResponseBufferingQueueJndiName;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 16:
               if (s.equals("cleaner-interval")) {
                  return 11;
               }
               break;
            case 19:
               if (s.equals("physical-store-name")) {
                  return 15;
               }

               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("persistence-strategy")) {
                  return 10;
               }
               break;
            case 31:
               if (s.equals("default-maximum-object-lifetime")) {
                  return 12;
               }
               break;
            case 33:
               if (s.equals("request-buffering-queue-jndi-name")) {
                  return 13;
               }
               break;
            case 34:
               if (s.equals("response-buffering-queue-jndi-name")) {
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
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "persistence-strategy";
            case 11:
               return "cleaner-interval";
            case 12:
               return "default-maximum-object-lifetime";
            case 13:
               return "request-buffering-queue-jndi-name";
            case 14:
               return "response-buffering-queue-jndi-name";
            case 15:
               return "physical-store-name";
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
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServiceLogicalStoreMBeanImpl bean;

      protected Helper(WebServiceLogicalStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "PersistenceStrategy";
            case 11:
               return "CleanerInterval";
            case 12:
               return "DefaultMaximumObjectLifetime";
            case 13:
               return "RequestBufferingQueueJndiName";
            case 14:
               return "ResponseBufferingQueueJndiName";
            case 15:
               return "PhysicalStoreName";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CleanerInterval")) {
            return 11;
         } else if (propName.equals("DefaultMaximumObjectLifetime")) {
            return 12;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("PersistenceStrategy")) {
            return 10;
         } else if (propName.equals("PhysicalStoreName")) {
            return 15;
         } else if (propName.equals("RequestBufferingQueueJndiName")) {
            return 13;
         } else if (propName.equals("ResponseBufferingQueueJndiName")) {
            return 14;
         } else if (propName.equals("Tags")) {
            return 9;
         } else {
            return propName.equals("DynamicallyCreated") ? 7 : super.getPropertyIndex(propName);
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
            if (this.bean.isCleanerIntervalSet()) {
               buf.append("CleanerInterval");
               buf.append(String.valueOf(this.bean.getCleanerInterval()));
            }

            if (this.bean.isDefaultMaximumObjectLifetimeSet()) {
               buf.append("DefaultMaximumObjectLifetime");
               buf.append(String.valueOf(this.bean.getDefaultMaximumObjectLifetime()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPersistenceStrategySet()) {
               buf.append("PersistenceStrategy");
               buf.append(String.valueOf(this.bean.getPersistenceStrategy()));
            }

            if (this.bean.isPhysicalStoreNameSet()) {
               buf.append("PhysicalStoreName");
               buf.append(String.valueOf(this.bean.getPhysicalStoreName()));
            }

            if (this.bean.isRequestBufferingQueueJndiNameSet()) {
               buf.append("RequestBufferingQueueJndiName");
               buf.append(String.valueOf(this.bean.getRequestBufferingQueueJndiName()));
            }

            if (this.bean.isResponseBufferingQueueJndiNameSet()) {
               buf.append("ResponseBufferingQueueJndiName");
               buf.append(String.valueOf(this.bean.getResponseBufferingQueueJndiName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isDynamicallyCreatedSet()) {
               buf.append("DynamicallyCreated");
               buf.append(String.valueOf(this.bean.isDynamicallyCreated()));
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
            WebServiceLogicalStoreMBeanImpl otherTyped = (WebServiceLogicalStoreMBeanImpl)other;
            this.computeDiff("CleanerInterval", this.bean.getCleanerInterval(), otherTyped.getCleanerInterval(), false);
            this.computeDiff("DefaultMaximumObjectLifetime", this.bean.getDefaultMaximumObjectLifetime(), otherTyped.getDefaultMaximumObjectLifetime(), true);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PersistenceStrategy", this.bean.getPersistenceStrategy(), otherTyped.getPersistenceStrategy(), false);
            this.computeDiff("PhysicalStoreName", this.bean.getPhysicalStoreName(), otherTyped.getPhysicalStoreName(), true);
            this.computeDiff("RequestBufferingQueueJndiName", this.bean.getRequestBufferingQueueJndiName(), otherTyped.getRequestBufferingQueueJndiName(), false);
            this.computeDiff("ResponseBufferingQueueJndiName", this.bean.getResponseBufferingQueueJndiName(), otherTyped.getResponseBufferingQueueJndiName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServiceLogicalStoreMBeanImpl original = (WebServiceLogicalStoreMBeanImpl)event.getSourceBean();
            WebServiceLogicalStoreMBeanImpl proposed = (WebServiceLogicalStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CleanerInterval")) {
                  original.setCleanerInterval(proposed.getCleanerInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("DefaultMaximumObjectLifetime")) {
                  original.setDefaultMaximumObjectLifetime(proposed.getDefaultMaximumObjectLifetime());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("PersistenceStrategy")) {
                  original.setPersistenceStrategy(proposed.getPersistenceStrategy());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("PhysicalStoreName")) {
                  original.setPhysicalStoreName(proposed.getPhysicalStoreName());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("RequestBufferingQueueJndiName")) {
                  original.setRequestBufferingQueueJndiName(proposed.getRequestBufferingQueueJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("ResponseBufferingQueueJndiName")) {
                  original.setResponseBufferingQueueJndiName(proposed.getResponseBufferingQueueJndiName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("Tags")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addTag((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTag((String)update.getRemovedObject());
                  }

                  if (original.getTags() == null || original.getTags().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 9);
                  }
               } else if (!prop.equals("DynamicallyCreated")) {
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
            WebServiceLogicalStoreMBeanImpl copy = (WebServiceLogicalStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CleanerInterval")) && this.bean.isCleanerIntervalSet()) {
               copy.setCleanerInterval(this.bean.getCleanerInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultMaximumObjectLifetime")) && this.bean.isDefaultMaximumObjectLifetimeSet()) {
               copy.setDefaultMaximumObjectLifetime(this.bean.getDefaultMaximumObjectLifetime());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceStrategy")) && this.bean.isPersistenceStrategySet()) {
               copy.setPersistenceStrategy(this.bean.getPersistenceStrategy());
            }

            if ((excludeProps == null || !excludeProps.contains("PhysicalStoreName")) && this.bean.isPhysicalStoreNameSet()) {
               copy.setPhysicalStoreName(this.bean.getPhysicalStoreName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequestBufferingQueueJndiName")) && this.bean.isRequestBufferingQueueJndiNameSet()) {
               copy.setRequestBufferingQueueJndiName(this.bean.getRequestBufferingQueueJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseBufferingQueueJndiName")) && this.bean.isResponseBufferingQueueJndiNameSet()) {
               copy.setResponseBufferingQueueJndiName(this.bean.getResponseBufferingQueueJndiName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
