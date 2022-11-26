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
import weblogic.management.DistributedManagementException;
import weblogic.management.ManagementException;
import weblogic.management.mbeans.custom.Component;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class ComponentMBeanImpl extends DeploymentMBeanImpl implements ComponentMBean, Serializable {
   private TargetMBean[] _ActivatedTargets;
   private ApplicationMBean _Application;
   private boolean _DynamicallyCreated;
   private String _Name;
   private String[] _Tags;
   private TargetMBean[] _Targets;
   private String _URI;
   private transient Component _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ComponentMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ComponentMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ComponentMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ComponentMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ComponentMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ComponentMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

      if (this._Application instanceof ApplicationMBeanImpl) {
         if (oldDelegate != null && oldDelegate.getApplication() != null) {
            this._getReferenceManager().unregisterBean((ApplicationMBeanImpl)oldDelegate.getApplication());
         }

         if (delegate != null && delegate.getApplication() != null) {
            this._getReferenceManager().registerBean((ApplicationMBeanImpl)delegate.getApplication(), false);
         }

         ((ApplicationMBeanImpl)this._Application)._setDelegateBean((ApplicationMBeanImpl)((ApplicationMBeanImpl)(delegate == null ? null : delegate.getApplication())));
      }

   }

   public ComponentMBeanImpl() {
      try {
         this._customizer = new Component(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ComponentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Component(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ComponentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Component(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ApplicationMBean getApplication() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getApplication() : this._customizer.getApplication();
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

   public TargetMBean[] getTargets() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getTargets() : this._customizer.getTargets();
   }

   public String getTargetsAsString() {
      return this._getHelper()._serializeKeyList(this.getTargets());
   }

   public boolean isApplicationInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isApplicationSet() {
      return this._isSet(12);
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public boolean isTargetsInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isTargetsSet() {
      return this._isSet(10);
   }

   public void setTargetsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._Targets);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, TargetMBean.class, new ReferenceManager.Resolver(this, 10, param0) {
                  public void resolveReference(Object value) {
                     try {
                        ComponentMBeanImpl.this.addTarget((TargetMBean)value);
                        ComponentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])ComponentMBeanImpl.this._Targets, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               TargetMBean[] var6 = this._Targets;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  TargetMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeTarget(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         TargetMBean[] _oldVal = this._Targets;
         this._initializeProperty(10);
         this._postSet(10, _oldVal, this._Targets);
      }
   }

   public void setApplication(ApplicationMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._customizer.setApplication(param0);
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
         ComponentMBeanImpl source = (ComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setTargets(TargetMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      param0 = (TargetMBean[])((TargetMBean[])this._getHelper()._cleanAndValidateArray(param0, TargetMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 10, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return ComponentMBeanImpl.this.getTargets();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      boolean wasSet = this._isSet(10);
      TargetMBean[] _oldVal = this.getTargets();
      this._customizer.setTargets(param0);
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ComponentMBeanImpl source = (ComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean addTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getTargets(), TargetMBean.class, param0));

         try {
            this.setTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public String getURI() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getURI(), this) : this._URI;
   }

   public boolean isURIInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isURISet() {
      return this._isSet(13);
   }

   public boolean removeTarget(TargetMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      TargetMBean[] _old = this.getTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setTargets(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void setURI(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._URI;
      this._URI = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         ComponentMBeanImpl source = (ComponentMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public TargetMBean[] getActivatedTargets() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().getActivatedTargets() : this._customizer.getActivatedTargets();
   }

   public boolean isActivatedTargetsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isActivatedTargetsSet() {
      return this._isSet(14);
   }

   public void addActivatedTarget(TargetMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._extendArray(this.getActivatedTargets(), TargetMBean.class, param0));

         try {
            this.setActivatedTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public void removeActivatedTarget(TargetMBean param0) {
      TargetMBean[] _old = this.getActivatedTargets();
      TargetMBean[] _new = (TargetMBean[])((TargetMBean[])this._getHelper()._removeElement(_old, TargetMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setActivatedTargets(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setActivatedTargets(TargetMBean[] param0) {
      TargetMBean[] param0 = param0 == null ? new TargetMBeanImpl[0] : param0;
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this._ActivatedTargets = (TargetMBean[])param0;
   }

   public boolean activated(TargetMBean param0) {
      return this._customizer.activated(param0);
   }

   public void refreshDDsIfNeeded(String[] param0) {
      throw new AssertionError("Method not implemented");
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
         ComponentMBeanImpl source = (ComponentMBeanImpl)var4.next();
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._ActivatedTargets = new TargetMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._customizer.setApplication((ApplicationMBean)null);
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
            case 10:
               this._customizer.setTargets(new TargetMBean[0]);
               if (initOne) {
                  break;
               }
            case 13:
               this._URI = null;
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
            case 11:
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
      return "Component";
   }

   public void putValue(String name, Object v) {
      TargetMBean[] oldVal;
      if (name.equals("ActivatedTargets")) {
         oldVal = this._ActivatedTargets;
         this._ActivatedTargets = (TargetMBean[])((TargetMBean[])v);
         this._postSet(14, oldVal, this._ActivatedTargets);
      } else if (name.equals("Application")) {
         ApplicationMBean oldVal = this._Application;
         this._Application = (ApplicationMBean)v;
         this._postSet(12, oldVal, this._Application);
      } else if (name.equals("DynamicallyCreated")) {
         boolean oldVal = this._DynamicallyCreated;
         this._DynamicallyCreated = (Boolean)v;
         this._postSet(7, oldVal, this._DynamicallyCreated);
      } else {
         String oldVal;
         if (name.equals("Name")) {
            oldVal = this._Name;
            this._Name = (String)v;
            this._postSet(2, oldVal, this._Name);
         } else if (name.equals("Tags")) {
            String[] oldVal = this._Tags;
            this._Tags = (String[])((String[])v);
            this._postSet(9, oldVal, this._Tags);
         } else if (name.equals("Targets")) {
            oldVal = this._Targets;
            this._Targets = (TargetMBean[])((TargetMBean[])v);
            this._postSet(10, oldVal, this._Targets);
         } else if (name.equals("URI")) {
            oldVal = this._URI;
            this._URI = (String)v;
            this._postSet(13, oldVal, this._URI);
         } else if (name.equals("customizer")) {
            Component oldVal = this._customizer;
            this._customizer = (Component)v;
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ActivatedTargets")) {
         return this._ActivatedTargets;
      } else if (name.equals("Application")) {
         return this._Application;
      } else if (name.equals("DynamicallyCreated")) {
         return new Boolean(this._DynamicallyCreated);
      } else if (name.equals("Name")) {
         return this._Name;
      } else if (name.equals("Tags")) {
         return this._Tags;
      } else if (name.equals("Targets")) {
         return this._Targets;
      } else if (name.equals("URI")) {
         return this._URI;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 3:
               if (s.equals("tag")) {
                  return 9;
               }

               if (s.equals("uri")) {
                  return 13;
               }
               break;
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 18:
            default:
               break;
            case 6:
               if (s.equals("target")) {
                  return 10;
               }
               break;
            case 11:
               if (s.equals("application")) {
                  return 12;
               }
               break;
            case 16:
               if (s.equals("activated-target")) {
                  return 14;
               }
               break;
            case 19:
               if (s.equals("dynamically-created")) {
                  return 7;
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
            case 11:
            default:
               return super.getElementName(propIndex);
            case 7:
               return "dynamically-created";
            case 9:
               return "tag";
            case 10:
               return "target";
            case 12:
               return "application";
            case 13:
               return "uri";
            case 14:
               return "activated-target";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 14:
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private ComponentMBeanImpl bean;

      protected Helper(ComponentMBeanImpl bean) {
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
            case 11:
            default:
               return super.getPropertyName(propIndex);
            case 7:
               return "DynamicallyCreated";
            case 9:
               return "Tags";
            case 10:
               return "Targets";
            case 12:
               return "Application";
            case 13:
               return "URI";
            case 14:
               return "ActivatedTargets";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ActivatedTargets")) {
            return 14;
         } else if (propName.equals("Application")) {
            return 12;
         } else if (propName.equals("Name")) {
            return 2;
         } else if (propName.equals("Tags")) {
            return 9;
         } else if (propName.equals("Targets")) {
            return 10;
         } else if (propName.equals("URI")) {
            return 13;
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
            if (this.bean.isActivatedTargetsSet()) {
               buf.append("ActivatedTargets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getActivatedTargets())));
            }

            if (this.bean.isApplicationSet()) {
               buf.append("Application");
               buf.append(String.valueOf(this.bean.getApplication()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isTagsSet()) {
               buf.append("Tags");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTags())));
            }

            if (this.bean.isTargetsSet()) {
               buf.append("Targets");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTargets())));
            }

            if (this.bean.isURISet()) {
               buf.append("URI");
               buf.append(String.valueOf(this.bean.getURI()));
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
            ComponentMBeanImpl otherTyped = (ComponentMBeanImpl)other;
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Tags", this.bean.getTags(), otherTyped.getTags(), true);
            this.computeDiff("Targets", this.bean.getTargets(), otherTyped.getTargets(), true);
            this.computeDiff("URI", this.bean.getURI(), otherTyped.getURI(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ComponentMBeanImpl original = (ComponentMBeanImpl)event.getSourceBean();
            ComponentMBeanImpl proposed = (ComponentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("ActivatedTargets") && !prop.equals("Application")) {
                  if (prop.equals("Name")) {
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
                  } else if (prop.equals("Targets")) {
                     original.setTargetsAsString(proposed.getTargetsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  } else if (prop.equals("URI")) {
                     original.setURI(proposed.getURI());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            ComponentMBeanImpl copy = (ComponentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Tags")) && this.bean.isTagsSet()) {
               Object o = this.bean.getTags();
               copy.setTags(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Targets")) && this.bean.isTargetsSet()) {
               copy._unSet(copy, 10);
               copy.setTargetsAsString(this.bean.getTargetsAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("URI")) && this.bean.isURISet()) {
               copy.setURI(this.bean.getURI());
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
         this.inferSubTree(this.bean.getActivatedTargets(), clazz, annotation);
         this.inferSubTree(this.bean.getApplication(), clazz, annotation);
         this.inferSubTree(this.bean.getTargets(), clazz, annotation);
      }
   }
}
