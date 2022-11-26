package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebserviceSecurityMBeanImpl extends ConfigurationMBeanImpl implements WebserviceSecurityMBean, Serializable {
   private String _CompatibilityOrderingPreference;
   private String _CompatibilityPreference;
   private String _DefaultCredentialProviderSTSURI;
   private String _PolicySelectionPreference;
   private WebserviceCredentialProviderMBean[] _WebserviceCredentialProviders;
   private WebserviceSecurityTokenMBean[] _WebserviceSecurityTokens;
   private WebserviceTimestampMBean _WebserviceTimestamp;
   private WebserviceTokenHandlerMBean[] _WebserviceTokenHandlers;
   private static SchemaHelper2 _schemaHelper;

   public WebserviceSecurityMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebserviceSecurityMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebserviceSecurityMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addWebserviceTokenHandler(WebserviceTokenHandlerMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 10)) {
         WebserviceTokenHandlerMBean[] _new;
         if (this._isSet(10)) {
            _new = (WebserviceTokenHandlerMBean[])((WebserviceTokenHandlerMBean[])this._getHelper()._extendArray(this.getWebserviceTokenHandlers(), WebserviceTokenHandlerMBean.class, param0));
         } else {
            _new = new WebserviceTokenHandlerMBean[]{param0};
         }

         try {
            this.setWebserviceTokenHandlers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebserviceTokenHandlerMBean[] getWebserviceTokenHandlers() {
      return this._WebserviceTokenHandlers;
   }

   public boolean isWebserviceTokenHandlersInherited() {
      return false;
   }

   public boolean isWebserviceTokenHandlersSet() {
      return this._isSet(10);
   }

   public void removeWebserviceTokenHandler(WebserviceTokenHandlerMBean param0) {
      this.destroyWebserviceTokenHandler(param0);
   }

   public void setWebserviceTokenHandlers(WebserviceTokenHandlerMBean[] param0) throws InvalidAttributeValueException {
      WebserviceTokenHandlerMBean[] param0 = param0 == null ? new WebserviceTokenHandlerMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 10)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WebserviceTokenHandlerMBean[] _oldVal = this._WebserviceTokenHandlers;
      this._WebserviceTokenHandlers = (WebserviceTokenHandlerMBean[])param0;
      this._postSet(10, _oldVal, param0);
   }

   public WebserviceTokenHandlerMBean lookupWebserviceTokenHandler(String param0) {
      Object[] aary = (Object[])this._WebserviceTokenHandlers;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebserviceTokenHandlerMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebserviceTokenHandlerMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WebserviceTokenHandlerMBean createWebserviceTokenHandler(String param0) {
      WebserviceTokenHandlerMBeanImpl lookup = (WebserviceTokenHandlerMBeanImpl)this.lookupWebserviceTokenHandler(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebserviceTokenHandlerMBeanImpl _val = new WebserviceTokenHandlerMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebserviceTokenHandler(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWebserviceTokenHandler(WebserviceTokenHandlerMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 10);
         WebserviceTokenHandlerMBean[] _old = this.getWebserviceTokenHandlers();
         WebserviceTokenHandlerMBean[] _new = (WebserviceTokenHandlerMBean[])((WebserviceTokenHandlerMBean[])this._getHelper()._removeElement(_old, WebserviceTokenHandlerMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWebserviceTokenHandlers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addWebserviceCredentialProvider(WebserviceCredentialProviderMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 11)) {
         WebserviceCredentialProviderMBean[] _new;
         if (this._isSet(11)) {
            _new = (WebserviceCredentialProviderMBean[])((WebserviceCredentialProviderMBean[])this._getHelper()._extendArray(this.getWebserviceCredentialProviders(), WebserviceCredentialProviderMBean.class, param0));
         } else {
            _new = new WebserviceCredentialProviderMBean[]{param0};
         }

         try {
            this.setWebserviceCredentialProviders(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebserviceCredentialProviderMBean[] getWebserviceCredentialProviders() {
      return this._WebserviceCredentialProviders;
   }

   public boolean isWebserviceCredentialProvidersInherited() {
      return false;
   }

   public boolean isWebserviceCredentialProvidersSet() {
      return this._isSet(11);
   }

   public void removeWebserviceCredentialProvider(WebserviceCredentialProviderMBean param0) {
      this.destroyWebserviceCredentialProvider(param0);
   }

   public void setWebserviceCredentialProviders(WebserviceCredentialProviderMBean[] param0) throws InvalidAttributeValueException {
      WebserviceCredentialProviderMBean[] param0 = param0 == null ? new WebserviceCredentialProviderMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 11)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WebserviceCredentialProviderMBean[] _oldVal = this._WebserviceCredentialProviders;
      this._WebserviceCredentialProviders = (WebserviceCredentialProviderMBean[])param0;
      this._postSet(11, _oldVal, param0);
   }

   public WebserviceCredentialProviderMBean lookupWebserviceCredentialProvider(String param0) {
      Object[] aary = (Object[])this._WebserviceCredentialProviders;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebserviceCredentialProviderMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebserviceCredentialProviderMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WebserviceCredentialProviderMBean createWebserviceCredentialProvider(String param0) {
      WebserviceCredentialProviderMBeanImpl lookup = (WebserviceCredentialProviderMBeanImpl)this.lookupWebserviceCredentialProvider(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebserviceCredentialProviderMBeanImpl _val = new WebserviceCredentialProviderMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebserviceCredentialProvider(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWebserviceCredentialProvider(WebserviceCredentialProviderMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 11);
         WebserviceCredentialProviderMBean[] _old = this.getWebserviceCredentialProviders();
         WebserviceCredentialProviderMBean[] _new = (WebserviceCredentialProviderMBean[])((WebserviceCredentialProviderMBean[])this._getHelper()._removeElement(_old, WebserviceCredentialProviderMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWebserviceCredentialProviders(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public void addWebserviceSecurityToken(WebserviceSecurityTokenMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WebserviceSecurityTokenMBean[] _new;
         if (this._isSet(12)) {
            _new = (WebserviceSecurityTokenMBean[])((WebserviceSecurityTokenMBean[])this._getHelper()._extendArray(this.getWebserviceSecurityTokens(), WebserviceSecurityTokenMBean.class, param0));
         } else {
            _new = new WebserviceSecurityTokenMBean[]{param0};
         }

         try {
            this.setWebserviceSecurityTokens(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WebserviceSecurityTokenMBean[] getWebserviceSecurityTokens() {
      return this._WebserviceSecurityTokens;
   }

   public boolean isWebserviceSecurityTokensInherited() {
      return false;
   }

   public boolean isWebserviceSecurityTokensSet() {
      return this._isSet(12);
   }

   public void removeWebserviceSecurityToken(WebserviceSecurityTokenMBean param0) {
      this.destroyWebserviceSecurityToken(param0);
   }

   public void setWebserviceSecurityTokens(WebserviceSecurityTokenMBean[] param0) throws InvalidAttributeValueException {
      WebserviceSecurityTokenMBean[] param0 = param0 == null ? new WebserviceSecurityTokenMBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 12)) {
            this._getReferenceManager().registerBean(_child, true);
            this._postCreate(_child);
         }
      }

      WebserviceSecurityTokenMBean[] _oldVal = this._WebserviceSecurityTokens;
      this._WebserviceSecurityTokens = (WebserviceSecurityTokenMBean[])param0;
      this._postSet(12, _oldVal, param0);
   }

   public WebserviceSecurityTokenMBean lookupWebserviceSecurityToken(String param0) {
      Object[] aary = (Object[])this._WebserviceSecurityTokens;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WebserviceSecurityTokenMBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WebserviceSecurityTokenMBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
   }

   public WebserviceSecurityTokenMBean createWebserviceSecurityToken(String param0) {
      WebserviceSecurityTokenMBeanImpl lookup = (WebserviceSecurityTokenMBeanImpl)this.lookupWebserviceSecurityToken(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WebserviceSecurityTokenMBeanImpl _val = new WebserviceSecurityTokenMBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addWebserviceSecurityToken(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyWebserviceSecurityToken(WebserviceSecurityTokenMBean param0) {
      try {
         this._checkIsPotentialChild(param0, 12);
         WebserviceSecurityTokenMBean[] _old = this.getWebserviceSecurityTokens();
         WebserviceSecurityTokenMBean[] _new = (WebserviceSecurityTokenMBean[])((WebserviceSecurityTokenMBean[])this._getHelper()._removeElement(_old, WebserviceSecurityTokenMBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWebserviceSecurityTokens(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public WebserviceTimestampMBean getWebserviceTimestamp() {
      return this._WebserviceTimestamp;
   }

   public boolean isWebserviceTimestampInherited() {
      return false;
   }

   public boolean isWebserviceTimestampSet() {
      return this._isSet(13) || this._isAnythingSet((AbstractDescriptorBean)this.getWebserviceTimestamp());
   }

   public void setWebserviceTimestamp(WebserviceTimestampMBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 13)) {
         this._postCreate(_child);
      }

      WebserviceTimestampMBean _oldVal = this._WebserviceTimestamp;
      this._WebserviceTimestamp = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getDefaultCredentialProviderSTSURI() {
      return this._DefaultCredentialProviderSTSURI;
   }

   public boolean isDefaultCredentialProviderSTSURIInherited() {
      return false;
   }

   public boolean isDefaultCredentialProviderSTSURISet() {
      return this._isSet(14);
   }

   public void setDefaultCredentialProviderSTSURI(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultCredentialProviderSTSURI;
      this._DefaultCredentialProviderSTSURI = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getPolicySelectionPreference() {
      return this._PolicySelectionPreference;
   }

   public boolean isPolicySelectionPreferenceInherited() {
      return false;
   }

   public boolean isPolicySelectionPreferenceSet() {
      return this._isSet(15);
   }

   public void setPolicySelectionPreference(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"NONE", "SCP", "SPC", "CSP", "CPS", "PCS", "PSC"};
      param0 = LegalChecks.checkInEnum("PolicySelectionPreference", param0, _set);
      String _oldVal = this._PolicySelectionPreference;
      this._PolicySelectionPreference = param0;
      this._postSet(15, _oldVal, param0);
   }

   public void setCompatibilityPreference(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityPreference;
      this._CompatibilityPreference = param0;
      this._postSet(16, _oldVal, param0);
   }

   public String getCompatibilityPreference() {
      return this._CompatibilityPreference;
   }

   public boolean isCompatibilityPreferenceInherited() {
      return false;
   }

   public boolean isCompatibilityPreferenceSet() {
      return this._isSet(16);
   }

   public void setCompatibilityOrderingPreference(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompatibilityOrderingPreference;
      this._CompatibilityOrderingPreference = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getCompatibilityOrderingPreference() {
      return this._CompatibilityOrderingPreference;
   }

   public boolean isCompatibilityOrderingPreferenceInherited() {
      return false;
   }

   public boolean isCompatibilityOrderingPreferenceSet() {
      return this._isSet(17);
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
      return super._isAnythingSet() || this.isWebserviceTimestampSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 17;
      }

      try {
         switch (idx) {
            case 17:
               this._CompatibilityOrderingPreference = null;
               if (initOne) {
                  break;
               }
            case 16:
               this._CompatibilityPreference = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._DefaultCredentialProviderSTSURI = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._PolicySelectionPreference = "NONE";
               if (initOne) {
                  break;
               }
            case 11:
               this._WebserviceCredentialProviders = new WebserviceCredentialProviderMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._WebserviceSecurityTokens = new WebserviceSecurityTokenMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._WebserviceTimestamp = new WebserviceTimestampMBeanImpl(this, 13);
               this._postCreate((AbstractDescriptorBean)this._WebserviceTimestamp);
               if (initOne) {
                  break;
               }
            case 10:
               this._WebserviceTokenHandlers = new WebserviceTokenHandlerMBean[0];
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
      return "WebserviceSecurity";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("CompatibilityOrderingPreference")) {
         oldVal = this._CompatibilityOrderingPreference;
         this._CompatibilityOrderingPreference = (String)v;
         this._postSet(17, oldVal, this._CompatibilityOrderingPreference);
      } else if (name.equals("CompatibilityPreference")) {
         oldVal = this._CompatibilityPreference;
         this._CompatibilityPreference = (String)v;
         this._postSet(16, oldVal, this._CompatibilityPreference);
      } else if (name.equals("DefaultCredentialProviderSTSURI")) {
         oldVal = this._DefaultCredentialProviderSTSURI;
         this._DefaultCredentialProviderSTSURI = (String)v;
         this._postSet(14, oldVal, this._DefaultCredentialProviderSTSURI);
      } else if (name.equals("PolicySelectionPreference")) {
         oldVal = this._PolicySelectionPreference;
         this._PolicySelectionPreference = (String)v;
         this._postSet(15, oldVal, this._PolicySelectionPreference);
      } else if (name.equals("WebserviceCredentialProviders")) {
         WebserviceCredentialProviderMBean[] oldVal = this._WebserviceCredentialProviders;
         this._WebserviceCredentialProviders = (WebserviceCredentialProviderMBean[])((WebserviceCredentialProviderMBean[])v);
         this._postSet(11, oldVal, this._WebserviceCredentialProviders);
      } else if (name.equals("WebserviceSecurityTokens")) {
         WebserviceSecurityTokenMBean[] oldVal = this._WebserviceSecurityTokens;
         this._WebserviceSecurityTokens = (WebserviceSecurityTokenMBean[])((WebserviceSecurityTokenMBean[])v);
         this._postSet(12, oldVal, this._WebserviceSecurityTokens);
      } else if (name.equals("WebserviceTimestamp")) {
         WebserviceTimestampMBean oldVal = this._WebserviceTimestamp;
         this._WebserviceTimestamp = (WebserviceTimestampMBean)v;
         this._postSet(13, oldVal, this._WebserviceTimestamp);
      } else if (name.equals("WebserviceTokenHandlers")) {
         WebserviceTokenHandlerMBean[] oldVal = this._WebserviceTokenHandlers;
         this._WebserviceTokenHandlers = (WebserviceTokenHandlerMBean[])((WebserviceTokenHandlerMBean[])v);
         this._postSet(10, oldVal, this._WebserviceTokenHandlers);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CompatibilityOrderingPreference")) {
         return this._CompatibilityOrderingPreference;
      } else if (name.equals("CompatibilityPreference")) {
         return this._CompatibilityPreference;
      } else if (name.equals("DefaultCredentialProviderSTSURI")) {
         return this._DefaultCredentialProviderSTSURI;
      } else if (name.equals("PolicySelectionPreference")) {
         return this._PolicySelectionPreference;
      } else if (name.equals("WebserviceCredentialProviders")) {
         return this._WebserviceCredentialProviders;
      } else if (name.equals("WebserviceSecurityTokens")) {
         return this._WebserviceSecurityTokens;
      } else if (name.equals("WebserviceTimestamp")) {
         return this._WebserviceTimestamp;
      } else {
         return name.equals("WebserviceTokenHandlers") ? this._WebserviceTokenHandlers : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 20:
               if (s.equals("webservice-timestamp")) {
                  return 13;
               }
            case 21:
            case 22:
            case 23:
            case 26:
            case 28:
            case 29:
            case 31:
            case 32:
            default:
               break;
            case 24:
               if (s.equals("compatibility-preference")) {
                  return 16;
               }

               if (s.equals("webservice-token-handler")) {
                  return 10;
               }
               break;
            case 25:
               if (s.equals("webservice-security-token")) {
                  return 12;
               }
               break;
            case 27:
               if (s.equals("policy-selection-preference")) {
                  return 15;
               }
               break;
            case 30:
               if (s.equals("webservice-credential-provider")) {
                  return 11;
               }
               break;
            case 33:
               if (s.equals("compatibility-ordering-preference")) {
                  return 17;
               }
               break;
            case 34:
               if (s.equals("default-credential-providersts-uri")) {
                  return 14;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new WebserviceTokenHandlerMBeanImpl.SchemaHelper2();
            case 11:
               return new WebserviceCredentialProviderMBeanImpl.SchemaHelper2();
            case 12:
               return new WebserviceSecurityTokenMBeanImpl.SchemaHelper2();
            case 13:
               return new WebserviceTimestampMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "webservice-token-handler";
            case 11:
               return "webservice-credential-provider";
            case 12:
               return "webservice-security-token";
            case 13:
               return "webservice-timestamp";
            case 14:
               return "default-credential-providersts-uri";
            case 15:
               return "policy-selection-preference";
            case 16:
               return "compatibility-preference";
            case 17:
               return "compatibility-ordering-preference";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
               return true;
            case 12:
               return true;
            case 13:
               return true;
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebserviceSecurityMBeanImpl bean;

      protected Helper(WebserviceSecurityMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "WebserviceTokenHandlers";
            case 11:
               return "WebserviceCredentialProviders";
            case 12:
               return "WebserviceSecurityTokens";
            case 13:
               return "WebserviceTimestamp";
            case 14:
               return "DefaultCredentialProviderSTSURI";
            case 15:
               return "PolicySelectionPreference";
            case 16:
               return "CompatibilityPreference";
            case 17:
               return "CompatibilityOrderingPreference";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompatibilityOrderingPreference")) {
            return 17;
         } else if (propName.equals("CompatibilityPreference")) {
            return 16;
         } else if (propName.equals("DefaultCredentialProviderSTSURI")) {
            return 14;
         } else if (propName.equals("PolicySelectionPreference")) {
            return 15;
         } else if (propName.equals("WebserviceCredentialProviders")) {
            return 11;
         } else if (propName.equals("WebserviceSecurityTokens")) {
            return 12;
         } else if (propName.equals("WebserviceTimestamp")) {
            return 13;
         } else {
            return propName.equals("WebserviceTokenHandlers") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWebserviceCredentialProviders()));
         iterators.add(new ArrayIterator(this.bean.getWebserviceSecurityTokens()));
         if (this.bean.getWebserviceTimestamp() != null) {
            iterators.add(new ArrayIterator(new WebserviceTimestampMBean[]{this.bean.getWebserviceTimestamp()}));
         }

         iterators.add(new ArrayIterator(this.bean.getWebserviceTokenHandlers()));
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
            if (this.bean.isCompatibilityOrderingPreferenceSet()) {
               buf.append("CompatibilityOrderingPreference");
               buf.append(String.valueOf(this.bean.getCompatibilityOrderingPreference()));
            }

            if (this.bean.isCompatibilityPreferenceSet()) {
               buf.append("CompatibilityPreference");
               buf.append(String.valueOf(this.bean.getCompatibilityPreference()));
            }

            if (this.bean.isDefaultCredentialProviderSTSURISet()) {
               buf.append("DefaultCredentialProviderSTSURI");
               buf.append(String.valueOf(this.bean.getDefaultCredentialProviderSTSURI()));
            }

            if (this.bean.isPolicySelectionPreferenceSet()) {
               buf.append("PolicySelectionPreference");
               buf.append(String.valueOf(this.bean.getPolicySelectionPreference()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getWebserviceCredentialProviders().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebserviceCredentialProviders()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWebserviceSecurityTokens().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebserviceSecurityTokens()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebserviceTimestamp());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getWebserviceTokenHandlers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWebserviceTokenHandlers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WebserviceSecurityMBeanImpl otherTyped = (WebserviceSecurityMBeanImpl)other;
            this.computeDiff("CompatibilityOrderingPreference", this.bean.getCompatibilityOrderingPreference(), otherTyped.getCompatibilityOrderingPreference(), false);
            this.computeDiff("CompatibilityPreference", this.bean.getCompatibilityPreference(), otherTyped.getCompatibilityPreference(), false);
            this.computeDiff("DefaultCredentialProviderSTSURI", this.bean.getDefaultCredentialProviderSTSURI(), otherTyped.getDefaultCredentialProviderSTSURI(), false);
            this.computeDiff("PolicySelectionPreference", this.bean.getPolicySelectionPreference(), otherTyped.getPolicySelectionPreference(), true);
            this.computeChildDiff("WebserviceCredentialProviders", this.bean.getWebserviceCredentialProviders(), otherTyped.getWebserviceCredentialProviders(), true);
            this.computeChildDiff("WebserviceSecurityTokens", this.bean.getWebserviceSecurityTokens(), otherTyped.getWebserviceSecurityTokens(), true);
            this.computeSubDiff("WebserviceTimestamp", this.bean.getWebserviceTimestamp(), otherTyped.getWebserviceTimestamp());
            this.computeChildDiff("WebserviceTokenHandlers", this.bean.getWebserviceTokenHandlers(), otherTyped.getWebserviceTokenHandlers(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebserviceSecurityMBeanImpl original = (WebserviceSecurityMBeanImpl)event.getSourceBean();
            WebserviceSecurityMBeanImpl proposed = (WebserviceSecurityMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompatibilityOrderingPreference")) {
                  original.setCompatibilityOrderingPreference(proposed.getCompatibilityOrderingPreference());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("CompatibilityPreference")) {
                  original.setCompatibilityPreference(proposed.getCompatibilityPreference());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("DefaultCredentialProviderSTSURI")) {
                  original.setDefaultCredentialProviderSTSURI(proposed.getDefaultCredentialProviderSTSURI());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("PolicySelectionPreference")) {
                  original.setPolicySelectionPreference(proposed.getPolicySelectionPreference());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("WebserviceCredentialProviders")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebserviceCredentialProvider((WebserviceCredentialProviderMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebserviceCredentialProvider((WebserviceCredentialProviderMBean)update.getRemovedObject());
                  }

                  if (original.getWebserviceCredentialProviders() == null || original.getWebserviceCredentialProviders().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 11);
                  }
               } else if (prop.equals("WebserviceSecurityTokens")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebserviceSecurityToken((WebserviceSecurityTokenMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebserviceSecurityToken((WebserviceSecurityTokenMBean)update.getRemovedObject());
                  }

                  if (original.getWebserviceSecurityTokens() == null || original.getWebserviceSecurityTokens().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("WebserviceTimestamp")) {
                  if (type == 2) {
                     original.setWebserviceTimestamp((WebserviceTimestampMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebserviceTimestamp()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebserviceTimestamp", original.getWebserviceTimestamp());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("WebserviceTokenHandlers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWebserviceTokenHandler((WebserviceTokenHandlerMBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWebserviceTokenHandler((WebserviceTokenHandlerMBean)update.getRemovedObject());
                  }

                  if (original.getWebserviceTokenHandlers() == null || original.getWebserviceTokenHandlers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 10);
                  }
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
            WebserviceSecurityMBeanImpl copy = (WebserviceSecurityMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompatibilityOrderingPreference")) && this.bean.isCompatibilityOrderingPreferenceSet()) {
               copy.setCompatibilityOrderingPreference(this.bean.getCompatibilityOrderingPreference());
            }

            if ((excludeProps == null || !excludeProps.contains("CompatibilityPreference")) && this.bean.isCompatibilityPreferenceSet()) {
               copy.setCompatibilityPreference(this.bean.getCompatibilityPreference());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultCredentialProviderSTSURI")) && this.bean.isDefaultCredentialProviderSTSURISet()) {
               copy.setDefaultCredentialProviderSTSURI(this.bean.getDefaultCredentialProviderSTSURI());
            }

            if ((excludeProps == null || !excludeProps.contains("PolicySelectionPreference")) && this.bean.isPolicySelectionPreferenceSet()) {
               copy.setPolicySelectionPreference(this.bean.getPolicySelectionPreference());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("WebserviceCredentialProviders")) && this.bean.isWebserviceCredentialProvidersSet() && !copy._isSet(11)) {
               WebserviceCredentialProviderMBean[] oldWebserviceCredentialProviders = this.bean.getWebserviceCredentialProviders();
               WebserviceCredentialProviderMBean[] newWebserviceCredentialProviders = new WebserviceCredentialProviderMBean[oldWebserviceCredentialProviders.length];

               for(i = 0; i < newWebserviceCredentialProviders.length; ++i) {
                  newWebserviceCredentialProviders[i] = (WebserviceCredentialProviderMBean)((WebserviceCredentialProviderMBean)this.createCopy((AbstractDescriptorBean)oldWebserviceCredentialProviders[i], includeObsolete));
               }

               copy.setWebserviceCredentialProviders(newWebserviceCredentialProviders);
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceSecurityTokens")) && this.bean.isWebserviceSecurityTokensSet() && !copy._isSet(12)) {
               WebserviceSecurityTokenMBean[] oldWebserviceSecurityTokens = this.bean.getWebserviceSecurityTokens();
               WebserviceSecurityTokenMBean[] newWebserviceSecurityTokens = new WebserviceSecurityTokenMBean[oldWebserviceSecurityTokens.length];

               for(i = 0; i < newWebserviceSecurityTokens.length; ++i) {
                  newWebserviceSecurityTokens[i] = (WebserviceSecurityTokenMBean)((WebserviceSecurityTokenMBean)this.createCopy((AbstractDescriptorBean)oldWebserviceSecurityTokens[i], includeObsolete));
               }

               copy.setWebserviceSecurityTokens(newWebserviceSecurityTokens);
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceTimestamp")) && this.bean.isWebserviceTimestampSet() && !copy._isSet(13)) {
               Object o = this.bean.getWebserviceTimestamp();
               copy.setWebserviceTimestamp((WebserviceTimestampMBean)null);
               copy.setWebserviceTimestamp(o == null ? null : (WebserviceTimestampMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebserviceTokenHandlers")) && this.bean.isWebserviceTokenHandlersSet() && !copy._isSet(10)) {
               WebserviceTokenHandlerMBean[] oldWebserviceTokenHandlers = this.bean.getWebserviceTokenHandlers();
               WebserviceTokenHandlerMBean[] newWebserviceTokenHandlers = new WebserviceTokenHandlerMBean[oldWebserviceTokenHandlers.length];

               for(i = 0; i < newWebserviceTokenHandlers.length; ++i) {
                  newWebserviceTokenHandlers[i] = (WebserviceTokenHandlerMBean)((WebserviceTokenHandlerMBean)this.createCopy((AbstractDescriptorBean)oldWebserviceTokenHandlers[i], includeObsolete));
               }

               copy.setWebserviceTokenHandlers(newWebserviceTokenHandlers);
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getWebserviceCredentialProviders(), clazz, annotation);
         this.inferSubTree(this.bean.getWebserviceSecurityTokens(), clazz, annotation);
         this.inferSubTree(this.bean.getWebserviceTimestamp(), clazz, annotation);
         this.inferSubTree(this.bean.getWebserviceTokenHandlers(), clazz, annotation);
      }
   }
}
