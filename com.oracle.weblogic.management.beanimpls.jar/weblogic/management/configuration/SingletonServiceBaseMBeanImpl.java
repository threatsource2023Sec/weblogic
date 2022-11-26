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
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.SingletonService;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SingletonServiceBaseMBeanImpl extends ConfigurationMBeanImpl implements SingletonServiceBaseMBean, Serializable {
   private int _AdditionalMigrationAttempts;
   private boolean _DynamicallyCreated;
   private ServerMBean _HostingServer;
   private int _MillisToSleepBetweenAttempts;
   private String _Name;
   private String[] _Tags;
   private ServerMBean _UserPreferredServer;
   private transient SingletonService _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private SingletonServiceBaseMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(SingletonServiceBaseMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(SingletonServiceBaseMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public SingletonServiceBaseMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(SingletonServiceBaseMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      SingletonServiceBaseMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._HostingServer instanceof ServerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getHostingServer() != null) {
            this._getReferenceManager().unregisterBean((ServerMBeanImpl)oldDelegate.getHostingServer());
         }

         if (delegate != null && delegate.getHostingServer() != null) {
            this._getReferenceManager().registerBean((ServerMBeanImpl)delegate.getHostingServer(), false);
         }

         ((ServerMBeanImpl)this._HostingServer)._setDelegateBean((ServerMBeanImpl)((ServerMBeanImpl)(delegate == null ? null : delegate.getHostingServer())));
      }

      if (this._UserPreferredServer instanceof ServerMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getUserPreferredServer() != null) {
            this._getReferenceManager().unregisterBean((ServerMBeanImpl)oldDelegate.getUserPreferredServer());
         }

         if (delegate != null && delegate.getUserPreferredServer() != null) {
            this._getReferenceManager().registerBean((ServerMBeanImpl)delegate.getUserPreferredServer(), false);
         }

         ((ServerMBeanImpl)this._UserPreferredServer)._setDelegateBean((ServerMBeanImpl)((ServerMBeanImpl)(delegate == null ? null : delegate.getUserPreferredServer())));
      }

   }

   public SingletonServiceBaseMBeanImpl() {
      try {
         this._customizer = new SingletonService(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public SingletonServiceBaseMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new SingletonService(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public SingletonServiceBaseMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new SingletonService(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ServerMBean getHostingServer() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getHostingServer() : this._customizer.getHostingServer();
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._customizer.getName();
      }
   }

   public boolean isHostingServerInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isHostingServerSet() {
      return this._isSet(10);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public void setHostingServer(ServerMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._HostingServer = param0;
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this.getName();
      this._customizer.setName(param0);
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingletonServiceBaseMBeanImpl source = (SingletonServiceBaseMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public ServerMBean getUserPreferredServer() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().getUserPreferredServer() : this._customizer.getUserPreferredServer();
   }

   public String getUserPreferredServerAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getUserPreferredServer();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isUserPreferredServerInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isUserPreferredServerSet() {
      return this._isSet(11);
   }

   public void setUserPreferredServerAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerMBean.class, new ReferenceManager.Resolver(this, 11) {
            public void resolveReference(Object value) {
               try {
                  SingletonServiceBaseMBeanImpl.this.setUserPreferredServer((ServerMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ServerMBean _oldVal = this._UserPreferredServer;
         this._initializeProperty(11);
         this._postSet(11, _oldVal, this._UserPreferredServer);
      }

   }

   public void setUserPreferredServer(ServerMBean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 11, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SingletonServiceBaseMBeanImpl.this.getUserPreferredServer();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      boolean wasSet = this._isSet(11);
      ServerMBean _oldVal = this.getUserPreferredServer();
      this._customizer.setUserPreferredServer(param0);
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingletonServiceBaseMBeanImpl source = (SingletonServiceBaseMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public int getAdditionalMigrationAttempts() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getAdditionalMigrationAttempts() : this._AdditionalMigrationAttempts;
   }

   public boolean isAdditionalMigrationAttemptsInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isAdditionalMigrationAttemptsSet() {
      return this._isSet(12);
   }

   public void setAdditionalMigrationAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      int _oldVal = this._AdditionalMigrationAttempts;
      this._AdditionalMigrationAttempts = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingletonServiceBaseMBeanImpl source = (SingletonServiceBaseMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getMillisToSleepBetweenAttempts() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getMillisToSleepBetweenAttempts() : this._MillisToSleepBetweenAttempts;
   }

   public boolean isMillisToSleepBetweenAttemptsInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isMillisToSleepBetweenAttemptsSet() {
      return this._isSet(13);
   }

   public void setMillisToSleepBetweenAttempts(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      int _oldVal = this._MillisToSleepBetweenAttempts;
      this._MillisToSleepBetweenAttempts = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingletonServiceBaseMBeanImpl source = (SingletonServiceBaseMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public void touch() throws ConfigurationException {
      this._customizer.touch();
   }

   public void freezeCurrentValue(String param0) throws AttributeNotFoundException, MBeanException {
      this._customizer.freezeCurrentValue(param0);
   }

   public void restoreDefaultValue(String param0) throws AttributeNotFoundException {
      this._customizer.restoreDefaultValue(param0);
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
         SingletonServiceBaseMBeanImpl source = (SingletonServiceBaseMBeanImpl)var4.next();
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._AdditionalMigrationAttempts = 2;
               if (initOne) {
                  break;
               }
            case 10:
               this._HostingServer = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._MillisToSleepBetweenAttempts = 300000;
               if (initOne) {
                  break;
               }
            case 2:
               this._customizer.setName((String)null);
               if (initOne) {
                  break;
               }
            case 9:
               this._customizer.setTags(new String[0]);
               if (initOne) {
                  break;
               }
            case 11:
               this._customizer.setUserPreferredServer((ServerMBean)null);
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
      return "SingletonServiceBase";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("AdditionalMigrationAttempts")) {
         oldVal = this._AdditionalMigrationAttempts;
         this._AdditionalMigrationAttempts = (Integer)v;
         this._postSet(12, oldVal, this._AdditionalMigrationAttempts);
      } else if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         ServerMBean oldVal;
         if (name.equals("HostingServer")) {
            oldVal = this._HostingServer;
            this._HostingServer = (ServerMBean)v;
            this._postSet(10, oldVal, this._HostingServer);
         } else if (name.equals("MillisToSleepBetweenAttempts")) {
            oldVal = this._MillisToSleepBetweenAttempts;
            this._MillisToSleepBetweenAttempts = (Integer)v;
            this._postSet(13, oldVal, this._MillisToSleepBetweenAttempts);
         } else if (name.equals("Name")) {
            String oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("Tags")) {
            String[] oldVal = this._Tags;
            this._Tags = (String[])((String[])v);
            this._postSet(9, oldVal, this._Tags);
         } else if (name.equals("UserPreferredServer")) {
            oldVal = this._UserPreferredServer;
            this._UserPreferredServer = (ServerMBean)v;
            this._postSet(11, oldVal, this._UserPreferredServer);
         } else if (name.equals("customizer")) {
            SingletonService oldVal = this._customizer;
            this._customizer = (SingletonService)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AdditionalMigrationAttempts")) {
         return new Integer(this._AdditionalMigrationAttempts);
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("HostingServer")) {
         return this._HostingServer;
      } else if (name.equals("MillisToSleepBetweenAttempts")) {
         return new Integer(this._MillisToSleepBetweenAttempts);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("UserPreferredServer")) {
         return this._UserPreferredServer;
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
            case 14:
               if (s.equals("hosting-server")) {
                  return 10;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
               }
               break;
            case 21:
               if (s.equals("user-preferred-server")) {
                  return 11;
               }
               break;
            case 29:
               if (s.equals("additional-migration-attempts")) {
                  return 12;
               }
               break;
            case 32:
               if (s.equals("millis-to-sleep-between-attempts")) {
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
               return "hosting-server";
            case 11:
               return "user-preferred-server";
            case 12:
               return "additional-migration-attempts";
            case 13:
               return "millis-to-sleep-between-attempts";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            default:
               return super.isBean(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private SingletonServiceBaseMBeanImpl bean;

      protected Helper(SingletonServiceBaseMBeanImpl bean) {
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
               return "HostingServer";
            case 11:
               return "UserPreferredServer";
            case 12:
               return "AdditionalMigrationAttempts";
            case 13:
               return "MillisToSleepBetweenAttempts";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AdditionalMigrationAttempts")) {
            return 12;
         } else if (propName.equals("HostingServer")) {
            return 10;
         } else if (propName.equals("MillisToSleepBetweenAttempts")) {
            return 13;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("UserPreferredServer")) {
            return 11;
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
            if (this.bean.isAdditionalMigrationAttemptsSet()) {
               buf.append("AdditionalMigrationAttempts");
               buf.append(String.valueOf(this.bean.getAdditionalMigrationAttempts()));
            }

            if (this.bean.isHostingServerSet()) {
               buf.append("HostingServer");
               buf.append(String.valueOf(this.bean.getHostingServer()));
            }

            if (this.bean.isMillisToSleepBetweenAttemptsSet()) {
               buf.append("MillisToSleepBetweenAttempts");
               buf.append(String.valueOf(this.bean.getMillisToSleepBetweenAttempts()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isUserPreferredServerSet()) {
               buf.append("UserPreferredServer");
               buf.append(String.valueOf(this.bean.getUserPreferredServer()));
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
            SingletonServiceBaseMBeanImpl otherTyped = (SingletonServiceBaseMBeanImpl)other;
            this.computeDiff("AdditionalMigrationAttempts", this.bean.getAdditionalMigrationAttempts(), otherTyped.getAdditionalMigrationAttempts(), false);
            this.computeDiff("MillisToSleepBetweenAttempts", this.bean.getMillisToSleepBetweenAttempts(), otherTyped.getMillisToSleepBetweenAttempts(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("UserPreferredServer", this.bean.getUserPreferredServer(), otherTyped.getUserPreferredServer(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SingletonServiceBaseMBeanImpl original = (SingletonServiceBaseMBeanImpl)event.getSourceBean();
            SingletonServiceBaseMBeanImpl proposed = (SingletonServiceBaseMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AdditionalMigrationAttempts")) {
                  original.setAdditionalMigrationAttempts(proposed.getAdditionalMigrationAttempts());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (!prop.equals("HostingServer")) {
                  if (prop.equals("MillisToSleepBetweenAttempts")) {
                     original.setMillisToSleepBetweenAttempts(proposed.getMillisToSleepBetweenAttempts());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (prop.equals("Name")) {
                     original.setName(proposed.getName());
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
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
                  } else if (prop.equals("UserPreferredServer")) {
                     original.setUserPreferredServerAsString(proposed.getUserPreferredServerAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  } else if (!prop.equals("DynamicallyCreated")) {
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
            SingletonServiceBaseMBeanImpl copy = (SingletonServiceBaseMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AdditionalMigrationAttempts")) && this.bean.isAdditionalMigrationAttemptsSet()) {
               copy.setAdditionalMigrationAttempts(this.bean.getAdditionalMigrationAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("MillisToSleepBetweenAttempts")) && this.bean.isMillisToSleepBetweenAttemptsSet()) {
               copy.setMillisToSleepBetweenAttempts(this.bean.getMillisToSleepBetweenAttempts());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserPreferredServer")) && this.bean.isUserPreferredServerSet()) {
               copy._unSet(copy, 11);
               copy.setUserPreferredServerAsString(this.bean.getUserPreferredServerAsString());
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
         this.inferSubTree(this.bean.getHostingServer(), clazz, annotation);
         this.inferSubTree(this.bean.getUserPreferredServer(), clazz, annotation);
      }
   }
}
