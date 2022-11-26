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

public class SingleSignOnServicesMBeanImpl extends ConfigurationMBeanImpl implements SingleSignOnServicesMBean, Serializable {
   private String[] _AllowedTargetHosts;
   private int _ArtifactMaxCacheSize;
   private int _ArtifactTimeout;
   private String _AssertionEncryptionDecryptionKeyAlias;
   private String _AssertionEncryptionDecryptionKeyPassPhrase;
   private byte[] _AssertionEncryptionDecryptionKeyPassPhraseEncrypted;
   private boolean _AssertionEncryptionEnabled;
   private int _AuthnRequestMaxCacheSize;
   private int _AuthnRequestTimeout;
   private String _BasicAuthPassword;
   private byte[] _BasicAuthPasswordEncrypted;
   private String _BasicAuthUsername;
   private String _ContactPersonCompany;
   private String _ContactPersonEmailAddress;
   private String _ContactPersonGivenName;
   private String _ContactPersonSurName;
   private String _ContactPersonTelephoneNumber;
   private String _ContactPersonType;
   private String _DataEncryptionAlgorithm;
   private String _DefaultURL;
   private String _EntityID;
   private String _ErrorPath;
   private boolean _ForceAuthn;
   private boolean _IdentityProviderArtifactBindingEnabled;
   private boolean _IdentityProviderEnabled;
   private boolean _IdentityProviderPOSTBindingEnabled;
   private String _IdentityProviderPreferredBinding;
   private boolean _IdentityProviderRedirectBindingEnabled;
   private String _KeyEncryptionAlgorithm;
   private String _LoginReturnQueryParameter;
   private String _LoginURL;
   private String[] _MetadataEncryptionAlgorithms;
   private String _OrganizationName;
   private String _OrganizationURL;
   private boolean _POSTOneUseCheckEnabled;
   private boolean _Passive;
   private String _PublishedSiteURL;
   private boolean _RecipientCheckEnabled;
   private boolean _ReplicatedCacheEnabled;
   private String _SSOSigningKeyAlias;
   private String _SSOSigningKeyPassPhrase;
   private byte[] _SSOSigningKeyPassPhraseEncrypted;
   private boolean _ServiceProviderArtifactBindingEnabled;
   private boolean _ServiceProviderEnabled;
   private boolean _ServiceProviderPOSTBindingEnabled;
   private String _ServiceProviderPreferredBinding;
   private boolean _SignAuthnRequests;
   private String _TransportLayerSecurityKeyAlias;
   private String _TransportLayerSecurityKeyPassPhrase;
   private byte[] _TransportLayerSecurityKeyPassPhraseEncrypted;
   private boolean _WantArtifactRequestsSigned;
   private boolean _WantAssertionsSigned;
   private boolean _WantAuthnRequestsSigned;
   private boolean _WantBasicAuthClientAuthentication;
   private boolean _WantTransportLayerSecurityClientAuthentication;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private SingleSignOnServicesMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(SingleSignOnServicesMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(SingleSignOnServicesMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public SingleSignOnServicesMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(SingleSignOnServicesMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      SingleSignOnServicesMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public SingleSignOnServicesMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SingleSignOnServicesMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SingleSignOnServicesMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getContactPersonGivenName() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getContactPersonGivenName(), this) : this._ContactPersonGivenName;
   }

   public boolean isContactPersonGivenNameInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isContactPersonGivenNameSet() {
      return this._isSet(10);
   }

   public void setContactPersonGivenName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._ContactPersonGivenName;
      this._ContactPersonGivenName = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getContactPersonSurName() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getContactPersonSurName(), this) : this._ContactPersonSurName;
   }

   public boolean isContactPersonSurNameInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isContactPersonSurNameSet() {
      return this._isSet(11);
   }

   public void setContactPersonSurName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._ContactPersonSurName;
      this._ContactPersonSurName = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getContactPersonType() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getContactPersonType(), this) : this._ContactPersonType;
   }

   public boolean isContactPersonTypeInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isContactPersonTypeSet() {
      return this._isSet(12);
   }

   public void setContactPersonType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      if (param0 != null && param0.trim().length() > 0) {
         LegalChecks.checkInEnum("ContactPersonType", param0, new String[]{"technical", "support", "administrative", "billing", "other"});
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._ContactPersonType;
      this._ContactPersonType = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getContactPersonCompany() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getContactPersonCompany(), this) : this._ContactPersonCompany;
   }

   public boolean isContactPersonCompanyInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isContactPersonCompanySet() {
      return this._isSet(13);
   }

   public void setContactPersonCompany(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      String _oldVal = this._ContactPersonCompany;
      this._ContactPersonCompany = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public String getContactPersonTelephoneNumber() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._performMacroSubstitution(this._getDelegateBean().getContactPersonTelephoneNumber(), this) : this._ContactPersonTelephoneNumber;
   }

   public boolean isContactPersonTelephoneNumberInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isContactPersonTelephoneNumberSet() {
      return this._isSet(14);
   }

   public void setContactPersonTelephoneNumber(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      String _oldVal = this._ContactPersonTelephoneNumber;
      this._ContactPersonTelephoneNumber = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public String getContactPersonEmailAddress() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getContactPersonEmailAddress(), this) : this._ContactPersonEmailAddress;
   }

   public boolean isContactPersonEmailAddressInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isContactPersonEmailAddressSet() {
      return this._isSet(15);
   }

   public void setContactPersonEmailAddress(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      String _oldVal = this._ContactPersonEmailAddress;
      this._ContactPersonEmailAddress = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOrganizationName() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getOrganizationName(), this) : this._OrganizationName;
   }

   public boolean isOrganizationNameInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isOrganizationNameSet() {
      return this._isSet(16);
   }

   public void setOrganizationName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String _oldVal = this._OrganizationName;
      this._OrganizationName = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getOrganizationURL() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getOrganizationURL(), this) : this._OrganizationURL;
   }

   public boolean isOrganizationURLInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isOrganizationURLSet() {
      return this._isSet(17);
   }

   public void setOrganizationURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._OrganizationURL;
      this._OrganizationURL = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getPublishedSiteURL() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getPublishedSiteURL(), this) : this._PublishedSiteURL;
   }

   public boolean isPublishedSiteURLInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isPublishedSiteURLSet() {
      return this._isSet(18);
   }

   public void setPublishedSiteURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(18);
      String _oldVal = this._PublishedSiteURL;
      this._PublishedSiteURL = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getEntityID() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getEntityID(), this) : this._EntityID;
   }

   public boolean isEntityIDInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isEntityIDSet() {
      return this._isSet(19);
   }

   public void setEntityID(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(19);
      String _oldVal = this._EntityID;
      this._EntityID = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public String getErrorPath() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20) ? this._performMacroSubstitution(this._getDelegateBean().getErrorPath(), this) : this._ErrorPath;
   }

   public boolean isErrorPathInherited() {
      return !this._isSet(20) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(20);
   }

   public boolean isErrorPathSet() {
      return this._isSet(20);
   }

   public void setErrorPath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(20);
      String _oldVal = this._ErrorPath;
      this._ErrorPath = param0;
      this._postSet(20, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(20)) {
            source._postSetFirePropertyChange(20, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isServiceProviderEnabled() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21) ? this._getDelegateBean().isServiceProviderEnabled() : this._ServiceProviderEnabled;
   }

   public boolean isServiceProviderEnabledInherited() {
      return !this._isSet(21) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(21);
   }

   public boolean isServiceProviderEnabledSet() {
      return this._isSet(21);
   }

   public void setServiceProviderEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(21);
      boolean _oldVal = this._ServiceProviderEnabled;
      this._ServiceProviderEnabled = param0;
      this._postSet(21, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(21)) {
            source._postSetFirePropertyChange(21, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDefaultURL() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22) ? this._performMacroSubstitution(this._getDelegateBean().getDefaultURL(), this) : this._DefaultURL;
   }

   public boolean isDefaultURLInherited() {
      return !this._isSet(22) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(22);
   }

   public boolean isDefaultURLSet() {
      return this._isSet(22);
   }

   public void setDefaultURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(22);
      String _oldVal = this._DefaultURL;
      this._DefaultURL = param0;
      this._postSet(22, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(22)) {
            source._postSetFirePropertyChange(22, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isServiceProviderArtifactBindingEnabled() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23) ? this._getDelegateBean().isServiceProviderArtifactBindingEnabled() : this._ServiceProviderArtifactBindingEnabled;
   }

   public boolean isServiceProviderArtifactBindingEnabledInherited() {
      return !this._isSet(23) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(23);
   }

   public boolean isServiceProviderArtifactBindingEnabledSet() {
      return this._isSet(23);
   }

   public void setServiceProviderArtifactBindingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(23);
      boolean _oldVal = this._ServiceProviderArtifactBindingEnabled;
      this._ServiceProviderArtifactBindingEnabled = param0;
      this._postSet(23, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(23)) {
            source._postSetFirePropertyChange(23, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isServiceProviderPOSTBindingEnabled() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24) ? this._getDelegateBean().isServiceProviderPOSTBindingEnabled() : this._ServiceProviderPOSTBindingEnabled;
   }

   public boolean isServiceProviderPOSTBindingEnabledInherited() {
      return !this._isSet(24) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(24);
   }

   public boolean isServiceProviderPOSTBindingEnabledSet() {
      return this._isSet(24);
   }

   public void setServiceProviderPOSTBindingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(24);
      boolean _oldVal = this._ServiceProviderPOSTBindingEnabled;
      this._ServiceProviderPOSTBindingEnabled = param0;
      this._postSet(24, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(24)) {
            source._postSetFirePropertyChange(24, wasSet, _oldVal, param0);
         }
      }

   }

   public String getServiceProviderPreferredBinding() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25) ? this._performMacroSubstitution(this._getDelegateBean().getServiceProviderPreferredBinding(), this) : this._ServiceProviderPreferredBinding;
   }

   public boolean isServiceProviderPreferredBindingInherited() {
      return !this._isSet(25) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(25);
   }

   public boolean isServiceProviderPreferredBindingSet() {
      return this._isSet(25);
   }

   public void setServiceProviderPreferredBinding(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"None", "HTTP/POST", "HTTP/Artifact"};
      param0 = LegalChecks.checkInEnum("ServiceProviderPreferredBinding", param0, _set);
      LegalChecks.checkNonNull("ServiceProviderPreferredBinding", param0);
      boolean wasSet = this._isSet(25);
      String _oldVal = this._ServiceProviderPreferredBinding;
      this._ServiceProviderPreferredBinding = param0;
      this._postSet(25, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var5.next();
         if (source != null && !source._isSet(25)) {
            source._postSetFirePropertyChange(25, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isSignAuthnRequests() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26) ? this._getDelegateBean().isSignAuthnRequests() : this._SignAuthnRequests;
   }

   public boolean isSignAuthnRequestsInherited() {
      return !this._isSet(26) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(26);
   }

   public boolean isSignAuthnRequestsSet() {
      return this._isSet(26);
   }

   public void setSignAuthnRequests(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(26);
      boolean _oldVal = this._SignAuthnRequests;
      this._SignAuthnRequests = param0;
      this._postSet(26, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(26)) {
            source._postSetFirePropertyChange(26, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isWantAssertionsSigned() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27) ? this._getDelegateBean().isWantAssertionsSigned() : this._WantAssertionsSigned;
   }

   public boolean isWantAssertionsSignedInherited() {
      return !this._isSet(27) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(27);
   }

   public boolean isWantAssertionsSignedSet() {
      return this._isSet(27);
   }

   public void setWantAssertionsSigned(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(27);
      boolean _oldVal = this._WantAssertionsSigned;
      this._WantAssertionsSigned = param0;
      this._postSet(27, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(27)) {
            source._postSetFirePropertyChange(27, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSSOSigningKeyAlias() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28) ? this._performMacroSubstitution(this._getDelegateBean().getSSOSigningKeyAlias(), this) : this._SSOSigningKeyAlias;
   }

   public boolean isSSOSigningKeyAliasInherited() {
      return !this._isSet(28) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(28);
   }

   public boolean isSSOSigningKeyAliasSet() {
      return this._isSet(28);
   }

   public void setSSOSigningKeyAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(28);
      String _oldVal = this._SSOSigningKeyAlias;
      this._SSOSigningKeyAlias = param0;
      this._postSet(28, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(28)) {
            source._postSetFirePropertyChange(28, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSSOSigningKeyPassPhrase() {
      if (!this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29)) {
         return this._performMacroSubstitution(this._getDelegateBean().getSSOSigningKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getSSOSigningKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("SSOSigningKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isSSOSigningKeyPassPhraseInherited() {
      return !this._isSet(29) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(29);
   }

   public boolean isSSOSigningKeyPassPhraseSet() {
      return this.isSSOSigningKeyPassPhraseEncryptedSet();
   }

   public void setSSOSigningKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setSSOSigningKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("SSOSigningKeyPassPhrase", param0));
   }

   public byte[] getSSOSigningKeyPassPhraseEncrypted() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30) ? this._getDelegateBean().getSSOSigningKeyPassPhraseEncrypted() : this._getHelper()._cloneArray(this._SSOSigningKeyPassPhraseEncrypted);
   }

   public String getSSOSigningKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getSSOSigningKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isSSOSigningKeyPassPhraseEncryptedInherited() {
      return !this._isSet(30) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(30);
   }

   public boolean isSSOSigningKeyPassPhraseEncryptedSet() {
      return this._isSet(30);
   }

   public void setSSOSigningKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setSSOSigningKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isForceAuthn() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31) ? this._getDelegateBean().isForceAuthn() : this._ForceAuthn;
   }

   public boolean isForceAuthnInherited() {
      return !this._isSet(31) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(31);
   }

   public boolean isForceAuthnSet() {
      return this._isSet(31);
   }

   public void setForceAuthn(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(31);
      boolean _oldVal = this._ForceAuthn;
      this._ForceAuthn = param0;
      this._postSet(31, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(31)) {
            source._postSetFirePropertyChange(31, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPassive() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32) ? this._getDelegateBean().isPassive() : this._Passive;
   }

   public boolean isPassiveInherited() {
      return !this._isSet(32) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(32);
   }

   public boolean isPassiveSet() {
      return this._isSet(32);
   }

   public void setPassive(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(32);
      boolean _oldVal = this._Passive;
      this._Passive = param0;
      this._postSet(32, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(32)) {
            source._postSetFirePropertyChange(32, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIdentityProviderEnabled() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33) ? this._getDelegateBean().isIdentityProviderEnabled() : this._IdentityProviderEnabled;
   }

   public boolean isIdentityProviderEnabledInherited() {
      return !this._isSet(33) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(33);
   }

   public boolean isIdentityProviderEnabledSet() {
      return this._isSet(33);
   }

   public void setIdentityProviderEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(33);
      boolean _oldVal = this._IdentityProviderEnabled;
      this._IdentityProviderEnabled = param0;
      this._postSet(33, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(33)) {
            source._postSetFirePropertyChange(33, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIdentityProviderArtifactBindingEnabled() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34) ? this._getDelegateBean().isIdentityProviderArtifactBindingEnabled() : this._IdentityProviderArtifactBindingEnabled;
   }

   public boolean isIdentityProviderArtifactBindingEnabledInherited() {
      return !this._isSet(34) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(34);
   }

   public boolean isIdentityProviderArtifactBindingEnabledSet() {
      return this._isSet(34);
   }

   public void setIdentityProviderArtifactBindingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(34);
      boolean _oldVal = this._IdentityProviderArtifactBindingEnabled;
      this._IdentityProviderArtifactBindingEnabled = param0;
      this._postSet(34, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(34)) {
            source._postSetFirePropertyChange(34, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIdentityProviderPOSTBindingEnabled() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35) ? this._getDelegateBean().isIdentityProviderPOSTBindingEnabled() : this._IdentityProviderPOSTBindingEnabled;
   }

   public boolean isIdentityProviderPOSTBindingEnabledInherited() {
      return !this._isSet(35) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(35);
   }

   public boolean isIdentityProviderPOSTBindingEnabledSet() {
      return this._isSet(35);
   }

   public void setIdentityProviderPOSTBindingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(35);
      boolean _oldVal = this._IdentityProviderPOSTBindingEnabled;
      this._IdentityProviderPOSTBindingEnabled = param0;
      this._postSet(35, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(35)) {
            source._postSetFirePropertyChange(35, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isIdentityProviderRedirectBindingEnabled() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36) ? this._getDelegateBean().isIdentityProviderRedirectBindingEnabled() : this._IdentityProviderRedirectBindingEnabled;
   }

   public boolean isIdentityProviderRedirectBindingEnabledInherited() {
      return !this._isSet(36) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(36);
   }

   public boolean isIdentityProviderRedirectBindingEnabledSet() {
      return this._isSet(36);
   }

   public void setIdentityProviderRedirectBindingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(36);
      boolean _oldVal = this._IdentityProviderRedirectBindingEnabled;
      this._IdentityProviderRedirectBindingEnabled = param0;
      this._postSet(36, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(36)) {
            source._postSetFirePropertyChange(36, wasSet, _oldVal, param0);
         }
      }

   }

   public String getIdentityProviderPreferredBinding() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37) ? this._performMacroSubstitution(this._getDelegateBean().getIdentityProviderPreferredBinding(), this) : this._IdentityProviderPreferredBinding;
   }

   public boolean isIdentityProviderPreferredBindingInherited() {
      return !this._isSet(37) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(37);
   }

   public boolean isIdentityProviderPreferredBindingSet() {
      return this._isSet(37);
   }

   public void setIdentityProviderPreferredBinding(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"None", "HTTP/POST", "HTTP/Artifact", "HTTP/Redirect"};
      param0 = LegalChecks.checkInEnum("IdentityProviderPreferredBinding", param0, _set);
      LegalChecks.checkNonNull("IdentityProviderPreferredBinding", param0);
      boolean wasSet = this._isSet(37);
      String _oldVal = this._IdentityProviderPreferredBinding;
      this._IdentityProviderPreferredBinding = param0;
      this._postSet(37, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var5.next();
         if (source != null && !source._isSet(37)) {
            source._postSetFirePropertyChange(37, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLoginURL() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38) ? this._performMacroSubstitution(this._getDelegateBean().getLoginURL(), this) : this._LoginURL;
   }

   public boolean isLoginURLInherited() {
      return !this._isSet(38) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(38);
   }

   public boolean isLoginURLSet() {
      return this._isSet(38);
   }

   public void setLoginURL(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkNonEmptyString("LoginURL", param0);
      LegalChecks.checkNonNull("LoginURL", param0);
      boolean wasSet = this._isSet(38);
      String _oldVal = this._LoginURL;
      this._LoginURL = param0;
      this._postSet(38, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(38)) {
            source._postSetFirePropertyChange(38, wasSet, _oldVal, param0);
         }
      }

   }

   public String getLoginReturnQueryParameter() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39) ? this._performMacroSubstitution(this._getDelegateBean().getLoginReturnQueryParameter(), this) : this._LoginReturnQueryParameter;
   }

   public boolean isLoginReturnQueryParameterInherited() {
      return !this._isSet(39) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(39);
   }

   public boolean isLoginReturnQueryParameterSet() {
      return this._isSet(39);
   }

   public void setLoginReturnQueryParameter(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(39);
      String _oldVal = this._LoginReturnQueryParameter;
      this._LoginReturnQueryParameter = param0;
      this._postSet(39, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(39)) {
            source._postSetFirePropertyChange(39, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isWantAuthnRequestsSigned() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40) ? this._getDelegateBean().isWantAuthnRequestsSigned() : this._WantAuthnRequestsSigned;
   }

   public boolean isWantAuthnRequestsSignedInherited() {
      return !this._isSet(40) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(40);
   }

   public boolean isWantAuthnRequestsSignedSet() {
      return this._isSet(40);
   }

   public void setWantAuthnRequestsSigned(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(40);
      boolean _oldVal = this._WantAuthnRequestsSigned;
      this._WantAuthnRequestsSigned = param0;
      this._postSet(40, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(40)) {
            source._postSetFirePropertyChange(40, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isRecipientCheckEnabled() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41) ? this._getDelegateBean().isRecipientCheckEnabled() : this._RecipientCheckEnabled;
   }

   public boolean isRecipientCheckEnabledInherited() {
      return !this._isSet(41) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(41);
   }

   public boolean isRecipientCheckEnabledSet() {
      return this._isSet(41);
   }

   public void setRecipientCheckEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(41);
      boolean _oldVal = this._RecipientCheckEnabled;
      this._RecipientCheckEnabled = param0;
      this._postSet(41, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(41)) {
            source._postSetFirePropertyChange(41, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPOSTOneUseCheckEnabled() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42) ? this._getDelegateBean().isPOSTOneUseCheckEnabled() : this._POSTOneUseCheckEnabled;
   }

   public boolean isPOSTOneUseCheckEnabledInherited() {
      return !this._isSet(42) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(42);
   }

   public boolean isPOSTOneUseCheckEnabledSet() {
      return this._isSet(42);
   }

   public void setPOSTOneUseCheckEnabled(boolean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(42);
      boolean _oldVal = this._POSTOneUseCheckEnabled;
      this._POSTOneUseCheckEnabled = param0;
      this._postSet(42, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(42)) {
            source._postSetFirePropertyChange(42, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransportLayerSecurityKeyAlias() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43) ? this._performMacroSubstitution(this._getDelegateBean().getTransportLayerSecurityKeyAlias(), this) : this._TransportLayerSecurityKeyAlias;
   }

   public boolean isTransportLayerSecurityKeyAliasInherited() {
      return !this._isSet(43) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(43);
   }

   public boolean isTransportLayerSecurityKeyAliasSet() {
      return this._isSet(43);
   }

   public void setTransportLayerSecurityKeyAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(43);
      String _oldVal = this._TransportLayerSecurityKeyAlias;
      this._TransportLayerSecurityKeyAlias = param0;
      this._postSet(43, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(43)) {
            source._postSetFirePropertyChange(43, wasSet, _oldVal, param0);
         }
      }

   }

   public String getTransportLayerSecurityKeyPassPhrase() {
      if (!this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44)) {
         return this._performMacroSubstitution(this._getDelegateBean().getTransportLayerSecurityKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getTransportLayerSecurityKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("TransportLayerSecurityKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isTransportLayerSecurityKeyPassPhraseInherited() {
      return !this._isSet(44) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(44);
   }

   public boolean isTransportLayerSecurityKeyPassPhraseSet() {
      return this.isTransportLayerSecurityKeyPassPhraseEncryptedSet();
   }

   public void setTransportLayerSecurityKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setTransportLayerSecurityKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("TransportLayerSecurityKeyPassPhrase", param0));
   }

   public byte[] getTransportLayerSecurityKeyPassPhraseEncrypted() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45) ? this._getDelegateBean().getTransportLayerSecurityKeyPassPhraseEncrypted() : this._getHelper()._cloneArray(this._TransportLayerSecurityKeyPassPhraseEncrypted);
   }

   public String getTransportLayerSecurityKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getTransportLayerSecurityKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isTransportLayerSecurityKeyPassPhraseEncryptedInherited() {
      return !this._isSet(45) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(45);
   }

   public boolean isTransportLayerSecurityKeyPassPhraseEncryptedSet() {
      return this._isSet(45);
   }

   public void setTransportLayerSecurityKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setTransportLayerSecurityKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getBasicAuthUsername() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46) ? this._performMacroSubstitution(this._getDelegateBean().getBasicAuthUsername(), this) : this._BasicAuthUsername;
   }

   public boolean isBasicAuthUsernameInherited() {
      return !this._isSet(46) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(46);
   }

   public boolean isBasicAuthUsernameSet() {
      return this._isSet(46);
   }

   public void setBasicAuthUsername(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(46);
      String _oldVal = this._BasicAuthUsername;
      this._BasicAuthUsername = param0;
      this._postSet(46, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(46)) {
            source._postSetFirePropertyChange(46, wasSet, _oldVal, param0);
         }
      }

   }

   public String getBasicAuthPassword() {
      if (!this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47)) {
         return this._performMacroSubstitution(this._getDelegateBean().getBasicAuthPassword(), this);
      } else {
         byte[] bEncrypted = this.getBasicAuthPasswordEncrypted();
         return bEncrypted == null ? null : this._decrypt("BasicAuthPassword", bEncrypted);
      }
   }

   public boolean isBasicAuthPasswordInherited() {
      return !this._isSet(47) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(47);
   }

   public boolean isBasicAuthPasswordSet() {
      return this.isBasicAuthPasswordEncryptedSet();
   }

   public void setBasicAuthPassword(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setBasicAuthPasswordEncrypted(param0 == null ? null : this._encrypt("BasicAuthPassword", param0));
   }

   public byte[] getBasicAuthPasswordEncrypted() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48) ? this._getDelegateBean().getBasicAuthPasswordEncrypted() : this._getHelper()._cloneArray(this._BasicAuthPasswordEncrypted);
   }

   public String getBasicAuthPasswordEncryptedAsString() {
      byte[] obj = this.getBasicAuthPasswordEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isBasicAuthPasswordEncryptedInherited() {
      return !this._isSet(48) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(48);
   }

   public boolean isBasicAuthPasswordEncryptedSet() {
      return this._isSet(48);
   }

   public void setBasicAuthPasswordEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setBasicAuthPasswordEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public boolean isWantArtifactRequestsSigned() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49) ? this._getDelegateBean().isWantArtifactRequestsSigned() : this._WantArtifactRequestsSigned;
   }

   public boolean isWantArtifactRequestsSignedInherited() {
      return !this._isSet(49) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(49);
   }

   public boolean isWantArtifactRequestsSignedSet() {
      return this._isSet(49);
   }

   public void setWantArtifactRequestsSigned(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(49);
      boolean _oldVal = this._WantArtifactRequestsSigned;
      this._WantArtifactRequestsSigned = param0;
      this._postSet(49, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(49)) {
            source._postSetFirePropertyChange(49, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isWantTransportLayerSecurityClientAuthentication() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50) ? this._getDelegateBean().isWantTransportLayerSecurityClientAuthentication() : this._WantTransportLayerSecurityClientAuthentication;
   }

   public boolean isWantTransportLayerSecurityClientAuthenticationInherited() {
      return !this._isSet(50) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(50);
   }

   public boolean isWantTransportLayerSecurityClientAuthenticationSet() {
      return this._isSet(50);
   }

   public void setWantTransportLayerSecurityClientAuthentication(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(50);
      boolean _oldVal = this._WantTransportLayerSecurityClientAuthentication;
      this._WantTransportLayerSecurityClientAuthentication = param0;
      this._postSet(50, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(50)) {
            source._postSetFirePropertyChange(50, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isWantBasicAuthClientAuthentication() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51) ? this._getDelegateBean().isWantBasicAuthClientAuthentication() : this._WantBasicAuthClientAuthentication;
   }

   public boolean isWantBasicAuthClientAuthenticationInherited() {
      return !this._isSet(51) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(51);
   }

   public boolean isWantBasicAuthClientAuthenticationSet() {
      return this._isSet(51);
   }

   public void setWantBasicAuthClientAuthentication(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(51);
      boolean _oldVal = this._WantBasicAuthClientAuthentication;
      this._WantBasicAuthClientAuthentication = param0;
      this._postSet(51, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(51)) {
            source._postSetFirePropertyChange(51, wasSet, _oldVal, param0);
         }
      }

   }

   public int getAuthnRequestMaxCacheSize() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52) ? this._getDelegateBean().getAuthnRequestMaxCacheSize() : this._AuthnRequestMaxCacheSize;
   }

   public boolean isAuthnRequestMaxCacheSizeInherited() {
      return !this._isSet(52) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(52);
   }

   public boolean isAuthnRequestMaxCacheSizeSet() {
      return this._isSet(52);
   }

   public void setAuthnRequestMaxCacheSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(52);
      int _oldVal = this._AuthnRequestMaxCacheSize;
      this._AuthnRequestMaxCacheSize = param0;
      this._postSet(52, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(52)) {
            source._postSetFirePropertyChange(52, wasSet, _oldVal, param0);
         }
      }

   }

   public int getAuthnRequestTimeout() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53) ? this._getDelegateBean().getAuthnRequestTimeout() : this._AuthnRequestTimeout;
   }

   public boolean isAuthnRequestTimeoutInherited() {
      return !this._isSet(53) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(53);
   }

   public boolean isAuthnRequestTimeoutSet() {
      return this._isSet(53);
   }

   public void setAuthnRequestTimeout(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(53);
      int _oldVal = this._AuthnRequestTimeout;
      this._AuthnRequestTimeout = param0;
      this._postSet(53, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(53)) {
            source._postSetFirePropertyChange(53, wasSet, _oldVal, param0);
         }
      }

   }

   public int getArtifactMaxCacheSize() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54) ? this._getDelegateBean().getArtifactMaxCacheSize() : this._ArtifactMaxCacheSize;
   }

   public boolean isArtifactMaxCacheSizeInherited() {
      return !this._isSet(54) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(54);
   }

   public boolean isArtifactMaxCacheSizeSet() {
      return this._isSet(54);
   }

   public void setArtifactMaxCacheSize(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(54);
      int _oldVal = this._ArtifactMaxCacheSize;
      this._ArtifactMaxCacheSize = param0;
      this._postSet(54, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(54)) {
            source._postSetFirePropertyChange(54, wasSet, _oldVal, param0);
         }
      }

   }

   public int getArtifactTimeout() {
      return !this._isSet(55) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(55) ? this._getDelegateBean().getArtifactTimeout() : this._ArtifactTimeout;
   }

   public boolean isArtifactTimeoutInherited() {
      return !this._isSet(55) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(55);
   }

   public boolean isArtifactTimeoutSet() {
      return this._isSet(55);
   }

   public void setArtifactTimeout(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(55);
      int _oldVal = this._ArtifactTimeout;
      this._ArtifactTimeout = param0;
      this._postSet(55, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(55)) {
            source._postSetFirePropertyChange(55, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isReplicatedCacheEnabled() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56) ? this._getDelegateBean().isReplicatedCacheEnabled() : this._ReplicatedCacheEnabled;
   }

   public boolean isReplicatedCacheEnabledInherited() {
      return !this._isSet(56) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(56);
   }

   public boolean isReplicatedCacheEnabledSet() {
      return this._isSet(56);
   }

   public void setReplicatedCacheEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(56);
      boolean _oldVal = this._ReplicatedCacheEnabled;
      this._ReplicatedCacheEnabled = param0;
      this._postSet(56, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(56)) {
            source._postSetFirePropertyChange(56, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isAssertionEncryptionEnabled() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57) ? this._getDelegateBean().isAssertionEncryptionEnabled() : this._AssertionEncryptionEnabled;
   }

   public boolean isAssertionEncryptionEnabledInherited() {
      return !this._isSet(57) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(57);
   }

   public boolean isAssertionEncryptionEnabledSet() {
      return this._isSet(57);
   }

   public void setAssertionEncryptionEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(57);
      boolean _oldVal = this._AssertionEncryptionEnabled;
      this._AssertionEncryptionEnabled = param0;
      this._postSet(57, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(57)) {
            source._postSetFirePropertyChange(57, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDataEncryptionAlgorithm() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58) ? this._performMacroSubstitution(this._getDelegateBean().getDataEncryptionAlgorithm(), this) : this._DataEncryptionAlgorithm;
   }

   public boolean isDataEncryptionAlgorithmInherited() {
      return !this._isSet(58) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(58);
   }

   public boolean isDataEncryptionAlgorithmSet() {
      return this._isSet(58);
   }

   public void setDataEncryptionAlgorithm(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      ServerLegalHelper.validateSAMLDataEncryptionAlgorithm(param0);
      boolean wasSet = this._isSet(58);
      String _oldVal = this._DataEncryptionAlgorithm;
      this._DataEncryptionAlgorithm = param0;
      this._postSet(58, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(58)) {
            source._postSetFirePropertyChange(58, wasSet, _oldVal, param0);
         }
      }

   }

   public String getKeyEncryptionAlgorithm() {
      return !this._isSet(59) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(59) ? this._performMacroSubstitution(this._getDelegateBean().getKeyEncryptionAlgorithm(), this) : this._KeyEncryptionAlgorithm;
   }

   public boolean isKeyEncryptionAlgorithmInherited() {
      return !this._isSet(59) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(59);
   }

   public boolean isKeyEncryptionAlgorithmSet() {
      return this._isSet(59);
   }

   public void setKeyEncryptionAlgorithm(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      ServerLegalHelper.validateSAMLKeyEncryptionAlgorithm(param0);
      boolean wasSet = this._isSet(59);
      String _oldVal = this._KeyEncryptionAlgorithm;
      this._KeyEncryptionAlgorithm = param0;
      this._postSet(59, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(59)) {
            source._postSetFirePropertyChange(59, wasSet, _oldVal, param0);
         }
      }

   }

   public String[] getMetadataEncryptionAlgorithms() {
      return !this._isSet(60) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(60) ? this._getDelegateBean().getMetadataEncryptionAlgorithms() : this._MetadataEncryptionAlgorithms;
   }

   public boolean isMetadataEncryptionAlgorithmsInherited() {
      return !this._isSet(60) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(60);
   }

   public boolean isMetadataEncryptionAlgorithmsSet() {
      return this._isSet(60);
   }

   public void setMetadataEncryptionAlgorithms(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      ServerLegalHelper.validateAlgorithms(param0);
      boolean wasSet = this._isSet(60);
      String[] _oldVal = this._MetadataEncryptionAlgorithms;
      this._MetadataEncryptionAlgorithms = param0;
      this._postSet(60, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(60)) {
            source._postSetFirePropertyChange(60, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAssertionEncryptionDecryptionKeyAlias() {
      return !this._isSet(61) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(61) ? this._performMacroSubstitution(this._getDelegateBean().getAssertionEncryptionDecryptionKeyAlias(), this) : this._AssertionEncryptionDecryptionKeyAlias;
   }

   public boolean isAssertionEncryptionDecryptionKeyAliasInherited() {
      return !this._isSet(61) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(61);
   }

   public boolean isAssertionEncryptionDecryptionKeyAliasSet() {
      return this._isSet(61);
   }

   public void setAssertionEncryptionDecryptionKeyAlias(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(61);
      String _oldVal = this._AssertionEncryptionDecryptionKeyAlias;
      this._AssertionEncryptionDecryptionKeyAlias = param0;
      this._postSet(61, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(61)) {
            source._postSetFirePropertyChange(61, wasSet, _oldVal, param0);
         }
      }

   }

   public String getAssertionEncryptionDecryptionKeyPassPhrase() {
      if (!this._isSet(62) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(62)) {
         return this._performMacroSubstitution(this._getDelegateBean().getAssertionEncryptionDecryptionKeyPassPhrase(), this);
      } else {
         byte[] bEncrypted = this.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted();
         return bEncrypted == null ? null : this._decrypt("AssertionEncryptionDecryptionKeyPassPhrase", bEncrypted);
      }
   }

   public boolean isAssertionEncryptionDecryptionKeyPassPhraseInherited() {
      return !this._isSet(62) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(62);
   }

   public boolean isAssertionEncryptionDecryptionKeyPassPhraseSet() {
      return this.isAssertionEncryptionDecryptionKeyPassPhraseEncryptedSet();
   }

   public void setAssertionEncryptionDecryptionKeyPassPhrase(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      this.setAssertionEncryptionDecryptionKeyPassPhraseEncrypted(param0 == null ? null : this._encrypt("AssertionEncryptionDecryptionKeyPassPhrase", param0));
   }

   public byte[] getAssertionEncryptionDecryptionKeyPassPhraseEncrypted() {
      return !this._isSet(63) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(63) ? this._getDelegateBean().getAssertionEncryptionDecryptionKeyPassPhraseEncrypted() : this._getHelper()._cloneArray(this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted);
   }

   public String getAssertionEncryptionDecryptionKeyPassPhraseEncryptedAsString() {
      byte[] obj = this.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted();
      return obj == null ? null : new String(obj);
   }

   public boolean isAssertionEncryptionDecryptionKeyPassPhraseEncryptedInherited() {
      return !this._isSet(63) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(63);
   }

   public boolean isAssertionEncryptionDecryptionKeyPassPhraseEncryptedSet() {
      return this._isSet(63);
   }

   public void setAssertionEncryptionDecryptionKeyPassPhraseEncryptedAsString(String param0) {
      try {
         byte[] encryptedBytes = param0 == null ? null : param0.getBytes();
         this.setAssertionEncryptionDecryptionKeyPassPhraseEncrypted(encryptedBytes);
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String[] getAllowedTargetHosts() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64) ? this._getDelegateBean().getAllowedTargetHosts() : this._AllowedTargetHosts;
   }

   public boolean isAllowedTargetHostsInherited() {
      return !this._isSet(64) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(64);
   }

   public boolean isAllowedTargetHostsSet() {
      return this._isSet(64);
   }

   public void setAllowedTargetHosts(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(64);
      String[] _oldVal = this._AllowedTargetHosts;
      this._AllowedTargetHosts = param0;
      this._postSet(64, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
         if (source != null && !source._isSet(64)) {
            source._postSetFirePropertyChange(64, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      ServerLegalHelper.validateSingleSignOnServices(this);
   }

   public void setAssertionEncryptionDecryptionKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(63);
      byte[] _oldVal = this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: AssertionEncryptionDecryptionKeyPassPhraseEncrypted of SingleSignOnServicesMBean");
      } else {
         this._getHelper()._clearArray(this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted);
         this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(63, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
            if (source != null && !source._isSet(63)) {
               source._postSetFirePropertyChange(63, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setBasicAuthPasswordEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(48);
      byte[] _oldVal = this._BasicAuthPasswordEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: BasicAuthPasswordEncrypted of SingleSignOnServicesMBean");
      } else {
         this._getHelper()._clearArray(this._BasicAuthPasswordEncrypted);
         this._BasicAuthPasswordEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(48, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
            if (source != null && !source._isSet(48)) {
               source._postSetFirePropertyChange(48, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setSSOSigningKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(30);
      byte[] _oldVal = this._SSOSigningKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: SSOSigningKeyPassPhraseEncrypted of SingleSignOnServicesMBean");
      } else {
         this._getHelper()._clearArray(this._SSOSigningKeyPassPhraseEncrypted);
         this._SSOSigningKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(30, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
            if (source != null && !source._isSet(30)) {
               source._postSetFirePropertyChange(30, wasSet, _oldVal, param0);
            }
         }

      }
   }

   public void setTransportLayerSecurityKeyPassPhraseEncrypted(byte[] param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(45);
      byte[] _oldVal = this._TransportLayerSecurityKeyPassPhraseEncrypted;
      if (this._isProductionModeEnabled() && param0 != null && !this._isEncrypted(param0)) {
         throw new IllegalArgumentException("In production mode, it's not allowed to set a clear text value to the property: TransportLayerSecurityKeyPassPhraseEncrypted of SingleSignOnServicesMBean");
      } else {
         this._getHelper()._clearArray(this._TransportLayerSecurityKeyPassPhraseEncrypted);
         this._TransportLayerSecurityKeyPassPhraseEncrypted = this._getHelper()._cloneArray(param0);
         this._postSet(45, _oldVal, param0);
         Iterator var4 = this._DelegateSources.iterator();

         while(var4.hasNext()) {
            SingleSignOnServicesMBeanImpl source = (SingleSignOnServicesMBeanImpl)var4.next();
            if (source != null && !source._isSet(45)) {
               source._postSetFirePropertyChange(45, wasSet, _oldVal, param0);
            }
         }

      }
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
         if (idx == 62) {
            this._markSet(63, false);
         }

         if (idx == 47) {
            this._markSet(48, false);
         }

         if (idx == 29) {
            this._markSet(30, false);
         }

         if (idx == 44) {
            this._markSet(45, false);
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
         idx = 64;
      }

      try {
         switch (idx) {
            case 64:
               this._AllowedTargetHosts = new String[0];
               if (initOne) {
                  break;
               }
            case 54:
               this._ArtifactMaxCacheSize = 10000;
               if (initOne) {
                  break;
               }
            case 55:
               this._ArtifactTimeout = 300;
               if (initOne) {
                  break;
               }
            case 61:
               this._AssertionEncryptionDecryptionKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 62:
               this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 63:
               this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 52:
               this._AuthnRequestMaxCacheSize = 10000;
               if (initOne) {
                  break;
               }
            case 53:
               this._AuthnRequestTimeout = 300;
               if (initOne) {
                  break;
               }
            case 47:
               this._BasicAuthPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 48:
               this._BasicAuthPasswordEncrypted = null;
               if (initOne) {
                  break;
               }
            case 46:
               this._BasicAuthUsername = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._ContactPersonCompany = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._ContactPersonEmailAddress = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._ContactPersonGivenName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._ContactPersonSurName = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._ContactPersonTelephoneNumber = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._ContactPersonType = null;
               if (initOne) {
                  break;
               }
            case 58:
               this._DataEncryptionAlgorithm = "aes128-gcm";
               if (initOne) {
                  break;
               }
            case 22:
               this._DefaultURL = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._EntityID = null;
               if (initOne) {
                  break;
               }
            case 20:
               this._ErrorPath = null;
               if (initOne) {
                  break;
               }
            case 37:
               this._IdentityProviderPreferredBinding = "None";
               if (initOne) {
                  break;
               }
            case 59:
               this._KeyEncryptionAlgorithm = "rsa-oaep";
               if (initOne) {
                  break;
               }
            case 39:
               this._LoginReturnQueryParameter = null;
               if (initOne) {
                  break;
               }
            case 38:
               this._LoginURL = "/saml2/idp/login";
               if (initOne) {
                  break;
               }
            case 60:
               this._MetadataEncryptionAlgorithms = new String[]{"aes128-gcm", "aes192-gcm", "aes256-gcm", "aes128-cbc", "aes192-cbc", "aes256-cbc", "rsa-oaep", "rsa-oaep-mgf1p"};
               if (initOne) {
                  break;
               }
            case 16:
               this._OrganizationName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._OrganizationURL = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._PublishedSiteURL = null;
               if (initOne) {
                  break;
               }
            case 28:
               this._SSOSigningKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 29:
               this._SSOSigningKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 30:
               this._SSOSigningKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 25:
               this._ServiceProviderPreferredBinding = "None";
               if (initOne) {
                  break;
               }
            case 43:
               this._TransportLayerSecurityKeyAlias = null;
               if (initOne) {
                  break;
               }
            case 44:
               this._TransportLayerSecurityKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 45:
               this._TransportLayerSecurityKeyPassPhraseEncrypted = null;
               if (initOne) {
                  break;
               }
            case 57:
               this._AssertionEncryptionEnabled = false;
               if (initOne) {
                  break;
               }
            case 31:
               this._ForceAuthn = false;
               if (initOne) {
                  break;
               }
            case 34:
               this._IdentityProviderArtifactBindingEnabled = true;
               if (initOne) {
                  break;
               }
            case 33:
               this._IdentityProviderEnabled = false;
               if (initOne) {
                  break;
               }
            case 35:
               this._IdentityProviderPOSTBindingEnabled = true;
               if (initOne) {
                  break;
               }
            case 36:
               this._IdentityProviderRedirectBindingEnabled = true;
               if (initOne) {
                  break;
               }
            case 42:
               this._POSTOneUseCheckEnabled = true;
               if (initOne) {
                  break;
               }
            case 32:
               this._Passive = false;
               if (initOne) {
                  break;
               }
            case 41:
               this._RecipientCheckEnabled = true;
               if (initOne) {
                  break;
               }
            case 56:
               this._ReplicatedCacheEnabled = false;
               if (initOne) {
                  break;
               }
            case 23:
               this._ServiceProviderArtifactBindingEnabled = true;
               if (initOne) {
                  break;
               }
            case 21:
               this._ServiceProviderEnabled = false;
               if (initOne) {
                  break;
               }
            case 24:
               this._ServiceProviderPOSTBindingEnabled = true;
               if (initOne) {
                  break;
               }
            case 26:
               this._SignAuthnRequests = false;
               if (initOne) {
                  break;
               }
            case 49:
               this._WantArtifactRequestsSigned = false;
               if (initOne) {
                  break;
               }
            case 27:
               this._WantAssertionsSigned = true;
               if (initOne) {
                  break;
               }
            case 40:
               this._WantAuthnRequestsSigned = false;
               if (initOne) {
                  break;
               }
            case 51:
               this._WantBasicAuthClientAuthentication = false;
               if (initOne) {
                  break;
               }
            case 50:
               this._WantTransportLayerSecurityClientAuthentication = false;
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
      return "SingleSignOnServices";
   }

   public void putValue(String name, Object v) {
      String[] oldVal;
      if (name.equals("AllowedTargetHosts")) {
         oldVal = this._AllowedTargetHosts;
         this._AllowedTargetHosts = (String[])((String[])v);
         this._postSet(64, oldVal, this._AllowedTargetHosts);
      } else {
         int oldVal;
         if (name.equals("ArtifactMaxCacheSize")) {
            oldVal = this._ArtifactMaxCacheSize;
            this._ArtifactMaxCacheSize = (Integer)v;
            this._postSet(54, oldVal, this._ArtifactMaxCacheSize);
         } else if (name.equals("ArtifactTimeout")) {
            oldVal = this._ArtifactTimeout;
            this._ArtifactTimeout = (Integer)v;
            this._postSet(55, oldVal, this._ArtifactTimeout);
         } else {
            String oldVal;
            if (name.equals("AssertionEncryptionDecryptionKeyAlias")) {
               oldVal = this._AssertionEncryptionDecryptionKeyAlias;
               this._AssertionEncryptionDecryptionKeyAlias = (String)v;
               this._postSet(61, oldVal, this._AssertionEncryptionDecryptionKeyAlias);
            } else if (name.equals("AssertionEncryptionDecryptionKeyPassPhrase")) {
               oldVal = this._AssertionEncryptionDecryptionKeyPassPhrase;
               this._AssertionEncryptionDecryptionKeyPassPhrase = (String)v;
               this._postSet(62, oldVal, this._AssertionEncryptionDecryptionKeyPassPhrase);
            } else {
               byte[] oldVal;
               if (name.equals("AssertionEncryptionDecryptionKeyPassPhraseEncrypted")) {
                  oldVal = this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted;
                  this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted = (byte[])((byte[])v);
                  this._postSet(63, oldVal, this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted);
               } else {
                  boolean oldVal;
                  if (name.equals("AssertionEncryptionEnabled")) {
                     oldVal = this._AssertionEncryptionEnabled;
                     this._AssertionEncryptionEnabled = (Boolean)v;
                     this._postSet(57, oldVal, this._AssertionEncryptionEnabled);
                  } else if (name.equals("AuthnRequestMaxCacheSize")) {
                     oldVal = this._AuthnRequestMaxCacheSize;
                     this._AuthnRequestMaxCacheSize = (Integer)v;
                     this._postSet(52, oldVal, this._AuthnRequestMaxCacheSize);
                  } else if (name.equals("AuthnRequestTimeout")) {
                     oldVal = this._AuthnRequestTimeout;
                     this._AuthnRequestTimeout = (Integer)v;
                     this._postSet(53, oldVal, this._AuthnRequestTimeout);
                  } else if (name.equals("BasicAuthPassword")) {
                     oldVal = this._BasicAuthPassword;
                     this._BasicAuthPassword = (String)v;
                     this._postSet(47, oldVal, this._BasicAuthPassword);
                  } else if (name.equals("BasicAuthPasswordEncrypted")) {
                     oldVal = this._BasicAuthPasswordEncrypted;
                     this._BasicAuthPasswordEncrypted = (byte[])((byte[])v);
                     this._postSet(48, oldVal, this._BasicAuthPasswordEncrypted);
                  } else if (name.equals("BasicAuthUsername")) {
                     oldVal = this._BasicAuthUsername;
                     this._BasicAuthUsername = (String)v;
                     this._postSet(46, oldVal, this._BasicAuthUsername);
                  } else if (name.equals("ContactPersonCompany")) {
                     oldVal = this._ContactPersonCompany;
                     this._ContactPersonCompany = (String)v;
                     this._postSet(13, oldVal, this._ContactPersonCompany);
                  } else if (name.equals("ContactPersonEmailAddress")) {
                     oldVal = this._ContactPersonEmailAddress;
                     this._ContactPersonEmailAddress = (String)v;
                     this._postSet(15, oldVal, this._ContactPersonEmailAddress);
                  } else if (name.equals("ContactPersonGivenName")) {
                     oldVal = this._ContactPersonGivenName;
                     this._ContactPersonGivenName = (String)v;
                     this._postSet(10, oldVal, this._ContactPersonGivenName);
                  } else if (name.equals("ContactPersonSurName")) {
                     oldVal = this._ContactPersonSurName;
                     this._ContactPersonSurName = (String)v;
                     this._postSet(11, oldVal, this._ContactPersonSurName);
                  } else if (name.equals("ContactPersonTelephoneNumber")) {
                     oldVal = this._ContactPersonTelephoneNumber;
                     this._ContactPersonTelephoneNumber = (String)v;
                     this._postSet(14, oldVal, this._ContactPersonTelephoneNumber);
                  } else if (name.equals("ContactPersonType")) {
                     oldVal = this._ContactPersonType;
                     this._ContactPersonType = (String)v;
                     this._postSet(12, oldVal, this._ContactPersonType);
                  } else if (name.equals("DataEncryptionAlgorithm")) {
                     oldVal = this._DataEncryptionAlgorithm;
                     this._DataEncryptionAlgorithm = (String)v;
                     this._postSet(58, oldVal, this._DataEncryptionAlgorithm);
                  } else if (name.equals("DefaultURL")) {
                     oldVal = this._DefaultURL;
                     this._DefaultURL = (String)v;
                     this._postSet(22, oldVal, this._DefaultURL);
                  } else if (name.equals("EntityID")) {
                     oldVal = this._EntityID;
                     this._EntityID = (String)v;
                     this._postSet(19, oldVal, this._EntityID);
                  } else if (name.equals("ErrorPath")) {
                     oldVal = this._ErrorPath;
                     this._ErrorPath = (String)v;
                     this._postSet(20, oldVal, this._ErrorPath);
                  } else if (name.equals("ForceAuthn")) {
                     oldVal = this._ForceAuthn;
                     this._ForceAuthn = (Boolean)v;
                     this._postSet(31, oldVal, this._ForceAuthn);
                  } else if (name.equals("IdentityProviderArtifactBindingEnabled")) {
                     oldVal = this._IdentityProviderArtifactBindingEnabled;
                     this._IdentityProviderArtifactBindingEnabled = (Boolean)v;
                     this._postSet(34, oldVal, this._IdentityProviderArtifactBindingEnabled);
                  } else if (name.equals("IdentityProviderEnabled")) {
                     oldVal = this._IdentityProviderEnabled;
                     this._IdentityProviderEnabled = (Boolean)v;
                     this._postSet(33, oldVal, this._IdentityProviderEnabled);
                  } else if (name.equals("IdentityProviderPOSTBindingEnabled")) {
                     oldVal = this._IdentityProviderPOSTBindingEnabled;
                     this._IdentityProviderPOSTBindingEnabled = (Boolean)v;
                     this._postSet(35, oldVal, this._IdentityProviderPOSTBindingEnabled);
                  } else if (name.equals("IdentityProviderPreferredBinding")) {
                     oldVal = this._IdentityProviderPreferredBinding;
                     this._IdentityProviderPreferredBinding = (String)v;
                     this._postSet(37, oldVal, this._IdentityProviderPreferredBinding);
                  } else if (name.equals("IdentityProviderRedirectBindingEnabled")) {
                     oldVal = this._IdentityProviderRedirectBindingEnabled;
                     this._IdentityProviderRedirectBindingEnabled = (Boolean)v;
                     this._postSet(36, oldVal, this._IdentityProviderRedirectBindingEnabled);
                  } else if (name.equals("KeyEncryptionAlgorithm")) {
                     oldVal = this._KeyEncryptionAlgorithm;
                     this._KeyEncryptionAlgorithm = (String)v;
                     this._postSet(59, oldVal, this._KeyEncryptionAlgorithm);
                  } else if (name.equals("LoginReturnQueryParameter")) {
                     oldVal = this._LoginReturnQueryParameter;
                     this._LoginReturnQueryParameter = (String)v;
                     this._postSet(39, oldVal, this._LoginReturnQueryParameter);
                  } else if (name.equals("LoginURL")) {
                     oldVal = this._LoginURL;
                     this._LoginURL = (String)v;
                     this._postSet(38, oldVal, this._LoginURL);
                  } else if (name.equals("MetadataEncryptionAlgorithms")) {
                     oldVal = this._MetadataEncryptionAlgorithms;
                     this._MetadataEncryptionAlgorithms = (String[])((String[])v);
                     this._postSet(60, oldVal, this._MetadataEncryptionAlgorithms);
                  } else if (name.equals("OrganizationName")) {
                     oldVal = this._OrganizationName;
                     this._OrganizationName = (String)v;
                     this._postSet(16, oldVal, this._OrganizationName);
                  } else if (name.equals("OrganizationURL")) {
                     oldVal = this._OrganizationURL;
                     this._OrganizationURL = (String)v;
                     this._postSet(17, oldVal, this._OrganizationURL);
                  } else if (name.equals("POSTOneUseCheckEnabled")) {
                     oldVal = this._POSTOneUseCheckEnabled;
                     this._POSTOneUseCheckEnabled = (Boolean)v;
                     this._postSet(42, oldVal, this._POSTOneUseCheckEnabled);
                  } else if (name.equals("Passive")) {
                     oldVal = this._Passive;
                     this._Passive = (Boolean)v;
                     this._postSet(32, oldVal, this._Passive);
                  } else if (name.equals("PublishedSiteURL")) {
                     oldVal = this._PublishedSiteURL;
                     this._PublishedSiteURL = (String)v;
                     this._postSet(18, oldVal, this._PublishedSiteURL);
                  } else if (name.equals("RecipientCheckEnabled")) {
                     oldVal = this._RecipientCheckEnabled;
                     this._RecipientCheckEnabled = (Boolean)v;
                     this._postSet(41, oldVal, this._RecipientCheckEnabled);
                  } else if (name.equals("ReplicatedCacheEnabled")) {
                     oldVal = this._ReplicatedCacheEnabled;
                     this._ReplicatedCacheEnabled = (Boolean)v;
                     this._postSet(56, oldVal, this._ReplicatedCacheEnabled);
                  } else if (name.equals("SSOSigningKeyAlias")) {
                     oldVal = this._SSOSigningKeyAlias;
                     this._SSOSigningKeyAlias = (String)v;
                     this._postSet(28, oldVal, this._SSOSigningKeyAlias);
                  } else if (name.equals("SSOSigningKeyPassPhrase")) {
                     oldVal = this._SSOSigningKeyPassPhrase;
                     this._SSOSigningKeyPassPhrase = (String)v;
                     this._postSet(29, oldVal, this._SSOSigningKeyPassPhrase);
                  } else if (name.equals("SSOSigningKeyPassPhraseEncrypted")) {
                     oldVal = this._SSOSigningKeyPassPhraseEncrypted;
                     this._SSOSigningKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(30, oldVal, this._SSOSigningKeyPassPhraseEncrypted);
                  } else if (name.equals("ServiceProviderArtifactBindingEnabled")) {
                     oldVal = this._ServiceProviderArtifactBindingEnabled;
                     this._ServiceProviderArtifactBindingEnabled = (Boolean)v;
                     this._postSet(23, oldVal, this._ServiceProviderArtifactBindingEnabled);
                  } else if (name.equals("ServiceProviderEnabled")) {
                     oldVal = this._ServiceProviderEnabled;
                     this._ServiceProviderEnabled = (Boolean)v;
                     this._postSet(21, oldVal, this._ServiceProviderEnabled);
                  } else if (name.equals("ServiceProviderPOSTBindingEnabled")) {
                     oldVal = this._ServiceProviderPOSTBindingEnabled;
                     this._ServiceProviderPOSTBindingEnabled = (Boolean)v;
                     this._postSet(24, oldVal, this._ServiceProviderPOSTBindingEnabled);
                  } else if (name.equals("ServiceProviderPreferredBinding")) {
                     oldVal = this._ServiceProviderPreferredBinding;
                     this._ServiceProviderPreferredBinding = (String)v;
                     this._postSet(25, oldVal, this._ServiceProviderPreferredBinding);
                  } else if (name.equals("SignAuthnRequests")) {
                     oldVal = this._SignAuthnRequests;
                     this._SignAuthnRequests = (Boolean)v;
                     this._postSet(26, oldVal, this._SignAuthnRequests);
                  } else if (name.equals("TransportLayerSecurityKeyAlias")) {
                     oldVal = this._TransportLayerSecurityKeyAlias;
                     this._TransportLayerSecurityKeyAlias = (String)v;
                     this._postSet(43, oldVal, this._TransportLayerSecurityKeyAlias);
                  } else if (name.equals("TransportLayerSecurityKeyPassPhrase")) {
                     oldVal = this._TransportLayerSecurityKeyPassPhrase;
                     this._TransportLayerSecurityKeyPassPhrase = (String)v;
                     this._postSet(44, oldVal, this._TransportLayerSecurityKeyPassPhrase);
                  } else if (name.equals("TransportLayerSecurityKeyPassPhraseEncrypted")) {
                     oldVal = this._TransportLayerSecurityKeyPassPhraseEncrypted;
                     this._TransportLayerSecurityKeyPassPhraseEncrypted = (byte[])((byte[])v);
                     this._postSet(45, oldVal, this._TransportLayerSecurityKeyPassPhraseEncrypted);
                  } else if (name.equals("WantArtifactRequestsSigned")) {
                     oldVal = this._WantArtifactRequestsSigned;
                     this._WantArtifactRequestsSigned = (Boolean)v;
                     this._postSet(49, oldVal, this._WantArtifactRequestsSigned);
                  } else if (name.equals("WantAssertionsSigned")) {
                     oldVal = this._WantAssertionsSigned;
                     this._WantAssertionsSigned = (Boolean)v;
                     this._postSet(27, oldVal, this._WantAssertionsSigned);
                  } else if (name.equals("WantAuthnRequestsSigned")) {
                     oldVal = this._WantAuthnRequestsSigned;
                     this._WantAuthnRequestsSigned = (Boolean)v;
                     this._postSet(40, oldVal, this._WantAuthnRequestsSigned);
                  } else if (name.equals("WantBasicAuthClientAuthentication")) {
                     oldVal = this._WantBasicAuthClientAuthentication;
                     this._WantBasicAuthClientAuthentication = (Boolean)v;
                     this._postSet(51, oldVal, this._WantBasicAuthClientAuthentication);
                  } else if (name.equals("WantTransportLayerSecurityClientAuthentication")) {
                     oldVal = this._WantTransportLayerSecurityClientAuthentication;
                     this._WantTransportLayerSecurityClientAuthentication = (Boolean)v;
                     this._postSet(50, oldVal, this._WantTransportLayerSecurityClientAuthentication);
                  } else {
                     super.putValue(name, v);
                  }
               }
            }
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("AllowedTargetHosts")) {
         return this._AllowedTargetHosts;
      } else if (name.equals("ArtifactMaxCacheSize")) {
         return new Integer(this._ArtifactMaxCacheSize);
      } else if (name.equals("ArtifactTimeout")) {
         return new Integer(this._ArtifactTimeout);
      } else if (name.equals("AssertionEncryptionDecryptionKeyAlias")) {
         return this._AssertionEncryptionDecryptionKeyAlias;
      } else if (name.equals("AssertionEncryptionDecryptionKeyPassPhrase")) {
         return this._AssertionEncryptionDecryptionKeyPassPhrase;
      } else if (name.equals("AssertionEncryptionDecryptionKeyPassPhraseEncrypted")) {
         return this._AssertionEncryptionDecryptionKeyPassPhraseEncrypted;
      } else if (name.equals("AssertionEncryptionEnabled")) {
         return new Boolean(this._AssertionEncryptionEnabled);
      } else if (name.equals("AuthnRequestMaxCacheSize")) {
         return new Integer(this._AuthnRequestMaxCacheSize);
      } else if (name.equals("AuthnRequestTimeout")) {
         return new Integer(this._AuthnRequestTimeout);
      } else if (name.equals("BasicAuthPassword")) {
         return this._BasicAuthPassword;
      } else if (name.equals("BasicAuthPasswordEncrypted")) {
         return this._BasicAuthPasswordEncrypted;
      } else if (name.equals("BasicAuthUsername")) {
         return this._BasicAuthUsername;
      } else if (name.equals("ContactPersonCompany")) {
         return this._ContactPersonCompany;
      } else if (name.equals("ContactPersonEmailAddress")) {
         return this._ContactPersonEmailAddress;
      } else if (name.equals("ContactPersonGivenName")) {
         return this._ContactPersonGivenName;
      } else if (name.equals("ContactPersonSurName")) {
         return this._ContactPersonSurName;
      } else if (name.equals("ContactPersonTelephoneNumber")) {
         return this._ContactPersonTelephoneNumber;
      } else if (name.equals("ContactPersonType")) {
         return this._ContactPersonType;
      } else if (name.equals("DataEncryptionAlgorithm")) {
         return this._DataEncryptionAlgorithm;
      } else if (name.equals("DefaultURL")) {
         return this._DefaultURL;
      } else if (name.equals("EntityID")) {
         return this._EntityID;
      } else if (name.equals("ErrorPath")) {
         return this._ErrorPath;
      } else if (name.equals("ForceAuthn")) {
         return new Boolean(this._ForceAuthn);
      } else if (name.equals("IdentityProviderArtifactBindingEnabled")) {
         return new Boolean(this._IdentityProviderArtifactBindingEnabled);
      } else if (name.equals("IdentityProviderEnabled")) {
         return new Boolean(this._IdentityProviderEnabled);
      } else if (name.equals("IdentityProviderPOSTBindingEnabled")) {
         return new Boolean(this._IdentityProviderPOSTBindingEnabled);
      } else if (name.equals("IdentityProviderPreferredBinding")) {
         return this._IdentityProviderPreferredBinding;
      } else if (name.equals("IdentityProviderRedirectBindingEnabled")) {
         return new Boolean(this._IdentityProviderRedirectBindingEnabled);
      } else if (name.equals("KeyEncryptionAlgorithm")) {
         return this._KeyEncryptionAlgorithm;
      } else if (name.equals("LoginReturnQueryParameter")) {
         return this._LoginReturnQueryParameter;
      } else if (name.equals("LoginURL")) {
         return this._LoginURL;
      } else if (name.equals("MetadataEncryptionAlgorithms")) {
         return this._MetadataEncryptionAlgorithms;
      } else if (name.equals("OrganizationName")) {
         return this._OrganizationName;
      } else if (name.equals("OrganizationURL")) {
         return this._OrganizationURL;
      } else if (name.equals("POSTOneUseCheckEnabled")) {
         return new Boolean(this._POSTOneUseCheckEnabled);
      } else if (name.equals("Passive")) {
         return new Boolean(this._Passive);
      } else if (name.equals("PublishedSiteURL")) {
         return this._PublishedSiteURL;
      } else if (name.equals("RecipientCheckEnabled")) {
         return new Boolean(this._RecipientCheckEnabled);
      } else if (name.equals("ReplicatedCacheEnabled")) {
         return new Boolean(this._ReplicatedCacheEnabled);
      } else if (name.equals("SSOSigningKeyAlias")) {
         return this._SSOSigningKeyAlias;
      } else if (name.equals("SSOSigningKeyPassPhrase")) {
         return this._SSOSigningKeyPassPhrase;
      } else if (name.equals("SSOSigningKeyPassPhraseEncrypted")) {
         return this._SSOSigningKeyPassPhraseEncrypted;
      } else if (name.equals("ServiceProviderArtifactBindingEnabled")) {
         return new Boolean(this._ServiceProviderArtifactBindingEnabled);
      } else if (name.equals("ServiceProviderEnabled")) {
         return new Boolean(this._ServiceProviderEnabled);
      } else if (name.equals("ServiceProviderPOSTBindingEnabled")) {
         return new Boolean(this._ServiceProviderPOSTBindingEnabled);
      } else if (name.equals("ServiceProviderPreferredBinding")) {
         return this._ServiceProviderPreferredBinding;
      } else if (name.equals("SignAuthnRequests")) {
         return new Boolean(this._SignAuthnRequests);
      } else if (name.equals("TransportLayerSecurityKeyAlias")) {
         return this._TransportLayerSecurityKeyAlias;
      } else if (name.equals("TransportLayerSecurityKeyPassPhrase")) {
         return this._TransportLayerSecurityKeyPassPhrase;
      } else if (name.equals("TransportLayerSecurityKeyPassPhraseEncrypted")) {
         return this._TransportLayerSecurityKeyPassPhraseEncrypted;
      } else if (name.equals("WantArtifactRequestsSigned")) {
         return new Boolean(this._WantArtifactRequestsSigned);
      } else if (name.equals("WantAssertionsSigned")) {
         return new Boolean(this._WantAssertionsSigned);
      } else if (name.equals("WantAuthnRequestsSigned")) {
         return new Boolean(this._WantAuthnRequestsSigned);
      } else if (name.equals("WantBasicAuthClientAuthentication")) {
         return new Boolean(this._WantBasicAuthClientAuthentication);
      } else {
         return name.equals("WantTransportLayerSecurityClientAuthentication") ? new Boolean(this._WantTransportLayerSecurityClientAuthentication) : super.getValue(name);
      }
   }

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("IdentityProviderPreferredBinding", "None");
      } catch (IllegalArgumentException var4) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property IdentityProviderPreferredBinding in SingleSignOnServicesMBean" + var4.getMessage());
      }

      try {
         LegalChecks.checkNonNull("LoginURL", "/saml2/idp/login");
      } catch (IllegalArgumentException var3) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property LoginURL in SingleSignOnServicesMBean" + var3.getMessage());
      }

      try {
         LegalChecks.checkNonEmptyString("LoginURL", "/saml2/idp/login");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is zero-length. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-zero-length value on @default annotation. Refer annotation legalZeroLength on property LoginURL in SingleSignOnServicesMBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("ServiceProviderPreferredBinding", "None");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property ServiceProviderPreferredBinding in SingleSignOnServicesMBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("passive")) {
                  return 32;
               }
               break;
            case 8:
               if (s.equals("entityid")) {
                  return 19;
               }
               break;
            case 9:
               if (s.equals("login-url")) {
                  return 38;
               }
               break;
            case 10:
               if (s.equals("error-path")) {
                  return 20;
               }
               break;
            case 11:
               if (s.equals("default-url")) {
                  return 22;
               }

               if (s.equals("force-authn")) {
                  return 31;
               }
            case 12:
            case 13:
            case 14:
            case 15:
            case 20:
            case 30:
            case 32:
            case 33:
            case 38:
            case 39:
            case 43:
            case 44:
            case 45:
            case 46:
            case 48:
            case 49:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            default:
               break;
            case 16:
               if (s.equals("artifact-timeout")) {
                  return 55;
               }

               if (s.equals("organization-url")) {
                  return 17;
               }
               break;
            case 17:
               if (s.equals("organization-name")) {
                  return 16;
               }
               break;
            case 18:
               if (s.equals("published-site-url")) {
                  return 18;
               }
               break;
            case 19:
               if (s.equals("allowed-target-host")) {
                  return 64;
               }

               if (s.equals("basic-auth-password")) {
                  return 47;
               }

               if (s.equals("basic-auth-username")) {
                  return 46;
               }

               if (s.equals("contact-person-type")) {
                  return 12;
               }

               if (s.equals("sign-authn-requests")) {
                  return 26;
               }
               break;
            case 21:
               if (s.equals("authn-request-timeout")) {
                  return 53;
               }

               if (s.equals("sso-signing-key-alias")) {
                  return 28;
               }
               break;
            case 22:
               if (s.equals("contact-person-company")) {
                  return 13;
               }

               if (s.equals("want-assertions-signed")) {
                  return 27;
               }
               break;
            case 23:
               if (s.equals("artifact-max-cache-size")) {
                  return 54;
               }

               if (s.equals("contact-person-sur-name")) {
                  return 11;
               }

               if (s.equals("recipient-check-enabled")) {
                  return 41;
               }
               break;
            case 24:
               if (s.equals("key-encryption-algorithm")) {
                  return 59;
               }

               if (s.equals("replicated-cache-enabled")) {
                  return 56;
               }

               if (s.equals("service-provider-enabled")) {
                  return 21;
               }
               break;
            case 25:
               if (s.equals("contact-person-given-name")) {
                  return 10;
               }

               if (s.equals("data-encryption-algorithm")) {
                  return 58;
               }

               if (s.equals("identity-provider-enabled")) {
                  return 33;
               }
               break;
            case 26:
               if (s.equals("post-one-use-check-enabled")) {
                  return 42;
               }

               if (s.equals("want-authn-requests-signed")) {
                  return 40;
               }
               break;
            case 27:
               if (s.equals("sso-signing-key-pass-phrase")) {
                  return 29;
               }
               break;
            case 28:
               if (s.equals("authn-request-max-cache-size")) {
                  return 52;
               }

               if (s.equals("contact-person-email-address")) {
                  return 15;
               }

               if (s.equals("login-return-query-parameter")) {
                  return 39;
               }

               if (s.equals("assertion-encryption-enabled")) {
                  return 57;
               }
               break;
            case 29:
               if (s.equals("basic-auth-password-encrypted")) {
                  return 48;
               }

               if (s.equals("metadata-encryption-algorithm")) {
                  return 60;
               }

               if (s.equals("want-artifact-requests-signed")) {
                  return 49;
               }
               break;
            case 31:
               if (s.equals("contact-person-telephone-number")) {
                  return 14;
               }
               break;
            case 34:
               if (s.equals("service-provider-preferred-binding")) {
                  return 25;
               }

               if (s.equals("transport-layer-security-key-alias")) {
                  return 43;
               }
               break;
            case 35:
               if (s.equals("identity-provider-preferred-binding")) {
                  return 37;
               }
               break;
            case 36:
               if (s.equals("service-providerpost-binding-enabled")) {
                  return 24;
               }
               break;
            case 37:
               if (s.equals("sso-signing-key-pass-phrase-encrypted")) {
                  return 30;
               }

               if (s.equals("identity-providerpost-binding-enabled")) {
                  return 35;
               }

               if (s.equals("want-basic-auth-client-authentication")) {
                  return 51;
               }
               break;
            case 40:
               if (s.equals("transport-layer-security-key-pass-phrase")) {
                  return 44;
               }
               break;
            case 41:
               if (s.equals("assertion-encryption-decryption-key-alias")) {
                  return 61;
               }

               if (s.equals("service-provider-artifact-binding-enabled")) {
                  return 23;
               }
               break;
            case 42:
               if (s.equals("identity-provider-artifact-binding-enabled")) {
                  return 34;
               }

               if (s.equals("identity-provider-redirect-binding-enabled")) {
                  return 36;
               }
               break;
            case 47:
               if (s.equals("assertion-encryption-decryption-key-pass-phrase")) {
                  return 62;
               }
               break;
            case 50:
               if (s.equals("transport-layer-security-key-pass-phrase-encrypted")) {
                  return 45;
               }
               break;
            case 51:
               if (s.equals("want-transport-layer-security-client-authentication")) {
                  return 50;
               }
               break;
            case 57:
               if (s.equals("assertion-encryption-decryption-key-pass-phrase-encrypted")) {
                  return 63;
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
               return "contact-person-given-name";
            case 11:
               return "contact-person-sur-name";
            case 12:
               return "contact-person-type";
            case 13:
               return "contact-person-company";
            case 14:
               return "contact-person-telephone-number";
            case 15:
               return "contact-person-email-address";
            case 16:
               return "organization-name";
            case 17:
               return "organization-url";
            case 18:
               return "published-site-url";
            case 19:
               return "entityid";
            case 20:
               return "error-path";
            case 21:
               return "service-provider-enabled";
            case 22:
               return "default-url";
            case 23:
               return "service-provider-artifact-binding-enabled";
            case 24:
               return "service-providerpost-binding-enabled";
            case 25:
               return "service-provider-preferred-binding";
            case 26:
               return "sign-authn-requests";
            case 27:
               return "want-assertions-signed";
            case 28:
               return "sso-signing-key-alias";
            case 29:
               return "sso-signing-key-pass-phrase";
            case 30:
               return "sso-signing-key-pass-phrase-encrypted";
            case 31:
               return "force-authn";
            case 32:
               return "passive";
            case 33:
               return "identity-provider-enabled";
            case 34:
               return "identity-provider-artifact-binding-enabled";
            case 35:
               return "identity-providerpost-binding-enabled";
            case 36:
               return "identity-provider-redirect-binding-enabled";
            case 37:
               return "identity-provider-preferred-binding";
            case 38:
               return "login-url";
            case 39:
               return "login-return-query-parameter";
            case 40:
               return "want-authn-requests-signed";
            case 41:
               return "recipient-check-enabled";
            case 42:
               return "post-one-use-check-enabled";
            case 43:
               return "transport-layer-security-key-alias";
            case 44:
               return "transport-layer-security-key-pass-phrase";
            case 45:
               return "transport-layer-security-key-pass-phrase-encrypted";
            case 46:
               return "basic-auth-username";
            case 47:
               return "basic-auth-password";
            case 48:
               return "basic-auth-password-encrypted";
            case 49:
               return "want-artifact-requests-signed";
            case 50:
               return "want-transport-layer-security-client-authentication";
            case 51:
               return "want-basic-auth-client-authentication";
            case 52:
               return "authn-request-max-cache-size";
            case 53:
               return "authn-request-timeout";
            case 54:
               return "artifact-max-cache-size";
            case 55:
               return "artifact-timeout";
            case 56:
               return "replicated-cache-enabled";
            case 57:
               return "assertion-encryption-enabled";
            case 58:
               return "data-encryption-algorithm";
            case 59:
               return "key-encryption-algorithm";
            case 60:
               return "metadata-encryption-algorithm";
            case 61:
               return "assertion-encryption-decryption-key-alias";
            case 62:
               return "assertion-encryption-decryption-key-pass-phrase";
            case 63:
               return "assertion-encryption-decryption-key-pass-phrase-encrypted";
            case 64:
               return "allowed-target-host";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 60:
               return true;
            case 64:
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
      private SingleSignOnServicesMBeanImpl bean;

      protected Helper(SingleSignOnServicesMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "ContactPersonGivenName";
            case 11:
               return "ContactPersonSurName";
            case 12:
               return "ContactPersonType";
            case 13:
               return "ContactPersonCompany";
            case 14:
               return "ContactPersonTelephoneNumber";
            case 15:
               return "ContactPersonEmailAddress";
            case 16:
               return "OrganizationName";
            case 17:
               return "OrganizationURL";
            case 18:
               return "PublishedSiteURL";
            case 19:
               return "EntityID";
            case 20:
               return "ErrorPath";
            case 21:
               return "ServiceProviderEnabled";
            case 22:
               return "DefaultURL";
            case 23:
               return "ServiceProviderArtifactBindingEnabled";
            case 24:
               return "ServiceProviderPOSTBindingEnabled";
            case 25:
               return "ServiceProviderPreferredBinding";
            case 26:
               return "SignAuthnRequests";
            case 27:
               return "WantAssertionsSigned";
            case 28:
               return "SSOSigningKeyAlias";
            case 29:
               return "SSOSigningKeyPassPhrase";
            case 30:
               return "SSOSigningKeyPassPhraseEncrypted";
            case 31:
               return "ForceAuthn";
            case 32:
               return "Passive";
            case 33:
               return "IdentityProviderEnabled";
            case 34:
               return "IdentityProviderArtifactBindingEnabled";
            case 35:
               return "IdentityProviderPOSTBindingEnabled";
            case 36:
               return "IdentityProviderRedirectBindingEnabled";
            case 37:
               return "IdentityProviderPreferredBinding";
            case 38:
               return "LoginURL";
            case 39:
               return "LoginReturnQueryParameter";
            case 40:
               return "WantAuthnRequestsSigned";
            case 41:
               return "RecipientCheckEnabled";
            case 42:
               return "POSTOneUseCheckEnabled";
            case 43:
               return "TransportLayerSecurityKeyAlias";
            case 44:
               return "TransportLayerSecurityKeyPassPhrase";
            case 45:
               return "TransportLayerSecurityKeyPassPhraseEncrypted";
            case 46:
               return "BasicAuthUsername";
            case 47:
               return "BasicAuthPassword";
            case 48:
               return "BasicAuthPasswordEncrypted";
            case 49:
               return "WantArtifactRequestsSigned";
            case 50:
               return "WantTransportLayerSecurityClientAuthentication";
            case 51:
               return "WantBasicAuthClientAuthentication";
            case 52:
               return "AuthnRequestMaxCacheSize";
            case 53:
               return "AuthnRequestTimeout";
            case 54:
               return "ArtifactMaxCacheSize";
            case 55:
               return "ArtifactTimeout";
            case 56:
               return "ReplicatedCacheEnabled";
            case 57:
               return "AssertionEncryptionEnabled";
            case 58:
               return "DataEncryptionAlgorithm";
            case 59:
               return "KeyEncryptionAlgorithm";
            case 60:
               return "MetadataEncryptionAlgorithms";
            case 61:
               return "AssertionEncryptionDecryptionKeyAlias";
            case 62:
               return "AssertionEncryptionDecryptionKeyPassPhrase";
            case 63:
               return "AssertionEncryptionDecryptionKeyPassPhraseEncrypted";
            case 64:
               return "AllowedTargetHosts";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AllowedTargetHosts")) {
            return 64;
         } else if (propName.equals("ArtifactMaxCacheSize")) {
            return 54;
         } else if (propName.equals("ArtifactTimeout")) {
            return 55;
         } else if (propName.equals("AssertionEncryptionDecryptionKeyAlias")) {
            return 61;
         } else if (propName.equals("AssertionEncryptionDecryptionKeyPassPhrase")) {
            return 62;
         } else if (propName.equals("AssertionEncryptionDecryptionKeyPassPhraseEncrypted")) {
            return 63;
         } else if (propName.equals("AuthnRequestMaxCacheSize")) {
            return 52;
         } else if (propName.equals("AuthnRequestTimeout")) {
            return 53;
         } else if (propName.equals("BasicAuthPassword")) {
            return 47;
         } else if (propName.equals("BasicAuthPasswordEncrypted")) {
            return 48;
         } else if (propName.equals("BasicAuthUsername")) {
            return 46;
         } else if (propName.equals("ContactPersonCompany")) {
            return 13;
         } else if (propName.equals("ContactPersonEmailAddress")) {
            return 15;
         } else if (propName.equals("ContactPersonGivenName")) {
            return 10;
         } else if (propName.equals("ContactPersonSurName")) {
            return 11;
         } else if (propName.equals("ContactPersonTelephoneNumber")) {
            return 14;
         } else if (propName.equals("ContactPersonType")) {
            return 12;
         } else if (propName.equals("DataEncryptionAlgorithm")) {
            return 58;
         } else if (propName.equals("DefaultURL")) {
            return 22;
         } else if (propName.equals("EntityID")) {
            return 19;
         } else if (propName.equals("ErrorPath")) {
            return 20;
         } else if (propName.equals("IdentityProviderPreferredBinding")) {
            return 37;
         } else if (propName.equals("KeyEncryptionAlgorithm")) {
            return 59;
         } else if (propName.equals("LoginReturnQueryParameter")) {
            return 39;
         } else if (propName.equals("LoginURL")) {
            return 38;
         } else if (propName.equals("MetadataEncryptionAlgorithms")) {
            return 60;
         } else if (propName.equals("OrganizationName")) {
            return 16;
         } else if (propName.equals("OrganizationURL")) {
            return 17;
         } else if (propName.equals("PublishedSiteURL")) {
            return 18;
         } else if (propName.equals("SSOSigningKeyAlias")) {
            return 28;
         } else if (propName.equals("SSOSigningKeyPassPhrase")) {
            return 29;
         } else if (propName.equals("SSOSigningKeyPassPhraseEncrypted")) {
            return 30;
         } else if (propName.equals("ServiceProviderPreferredBinding")) {
            return 25;
         } else if (propName.equals("TransportLayerSecurityKeyAlias")) {
            return 43;
         } else if (propName.equals("TransportLayerSecurityKeyPassPhrase")) {
            return 44;
         } else if (propName.equals("TransportLayerSecurityKeyPassPhraseEncrypted")) {
            return 45;
         } else if (propName.equals("AssertionEncryptionEnabled")) {
            return 57;
         } else if (propName.equals("ForceAuthn")) {
            return 31;
         } else if (propName.equals("IdentityProviderArtifactBindingEnabled")) {
            return 34;
         } else if (propName.equals("IdentityProviderEnabled")) {
            return 33;
         } else if (propName.equals("IdentityProviderPOSTBindingEnabled")) {
            return 35;
         } else if (propName.equals("IdentityProviderRedirectBindingEnabled")) {
            return 36;
         } else if (propName.equals("POSTOneUseCheckEnabled")) {
            return 42;
         } else if (propName.equals("Passive")) {
            return 32;
         } else if (propName.equals("RecipientCheckEnabled")) {
            return 41;
         } else if (propName.equals("ReplicatedCacheEnabled")) {
            return 56;
         } else if (propName.equals("ServiceProviderArtifactBindingEnabled")) {
            return 23;
         } else if (propName.equals("ServiceProviderEnabled")) {
            return 21;
         } else if (propName.equals("ServiceProviderPOSTBindingEnabled")) {
            return 24;
         } else if (propName.equals("SignAuthnRequests")) {
            return 26;
         } else if (propName.equals("WantArtifactRequestsSigned")) {
            return 49;
         } else if (propName.equals("WantAssertionsSigned")) {
            return 27;
         } else if (propName.equals("WantAuthnRequestsSigned")) {
            return 40;
         } else if (propName.equals("WantBasicAuthClientAuthentication")) {
            return 51;
         } else {
            return propName.equals("WantTransportLayerSecurityClientAuthentication") ? 50 : super.getPropertyIndex(propName);
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
            if (this.bean.isAllowedTargetHostsSet()) {
               buf.append("AllowedTargetHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAllowedTargetHosts())));
            }

            if (this.bean.isArtifactMaxCacheSizeSet()) {
               buf.append("ArtifactMaxCacheSize");
               buf.append(String.valueOf(this.bean.getArtifactMaxCacheSize()));
            }

            if (this.bean.isArtifactTimeoutSet()) {
               buf.append("ArtifactTimeout");
               buf.append(String.valueOf(this.bean.getArtifactTimeout()));
            }

            if (this.bean.isAssertionEncryptionDecryptionKeyAliasSet()) {
               buf.append("AssertionEncryptionDecryptionKeyAlias");
               buf.append(String.valueOf(this.bean.getAssertionEncryptionDecryptionKeyAlias()));
            }

            if (this.bean.isAssertionEncryptionDecryptionKeyPassPhraseSet()) {
               buf.append("AssertionEncryptionDecryptionKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getAssertionEncryptionDecryptionKeyPassPhrase()));
            }

            if (this.bean.isAssertionEncryptionDecryptionKeyPassPhraseEncryptedSet()) {
               buf.append("AssertionEncryptionDecryptionKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted())));
            }

            if (this.bean.isAuthnRequestMaxCacheSizeSet()) {
               buf.append("AuthnRequestMaxCacheSize");
               buf.append(String.valueOf(this.bean.getAuthnRequestMaxCacheSize()));
            }

            if (this.bean.isAuthnRequestTimeoutSet()) {
               buf.append("AuthnRequestTimeout");
               buf.append(String.valueOf(this.bean.getAuthnRequestTimeout()));
            }

            if (this.bean.isBasicAuthPasswordSet()) {
               buf.append("BasicAuthPassword");
               buf.append(String.valueOf(this.bean.getBasicAuthPassword()));
            }

            if (this.bean.isBasicAuthPasswordEncryptedSet()) {
               buf.append("BasicAuthPasswordEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getBasicAuthPasswordEncrypted())));
            }

            if (this.bean.isBasicAuthUsernameSet()) {
               buf.append("BasicAuthUsername");
               buf.append(String.valueOf(this.bean.getBasicAuthUsername()));
            }

            if (this.bean.isContactPersonCompanySet()) {
               buf.append("ContactPersonCompany");
               buf.append(String.valueOf(this.bean.getContactPersonCompany()));
            }

            if (this.bean.isContactPersonEmailAddressSet()) {
               buf.append("ContactPersonEmailAddress");
               buf.append(String.valueOf(this.bean.getContactPersonEmailAddress()));
            }

            if (this.bean.isContactPersonGivenNameSet()) {
               buf.append("ContactPersonGivenName");
               buf.append(String.valueOf(this.bean.getContactPersonGivenName()));
            }

            if (this.bean.isContactPersonSurNameSet()) {
               buf.append("ContactPersonSurName");
               buf.append(String.valueOf(this.bean.getContactPersonSurName()));
            }

            if (this.bean.isContactPersonTelephoneNumberSet()) {
               buf.append("ContactPersonTelephoneNumber");
               buf.append(String.valueOf(this.bean.getContactPersonTelephoneNumber()));
            }

            if (this.bean.isContactPersonTypeSet()) {
               buf.append("ContactPersonType");
               buf.append(String.valueOf(this.bean.getContactPersonType()));
            }

            if (this.bean.isDataEncryptionAlgorithmSet()) {
               buf.append("DataEncryptionAlgorithm");
               buf.append(String.valueOf(this.bean.getDataEncryptionAlgorithm()));
            }

            if (this.bean.isDefaultURLSet()) {
               buf.append("DefaultURL");
               buf.append(String.valueOf(this.bean.getDefaultURL()));
            }

            if (this.bean.isEntityIDSet()) {
               buf.append("EntityID");
               buf.append(String.valueOf(this.bean.getEntityID()));
            }

            if (this.bean.isErrorPathSet()) {
               buf.append("ErrorPath");
               buf.append(String.valueOf(this.bean.getErrorPath()));
            }

            if (this.bean.isIdentityProviderPreferredBindingSet()) {
               buf.append("IdentityProviderPreferredBinding");
               buf.append(String.valueOf(this.bean.getIdentityProviderPreferredBinding()));
            }

            if (this.bean.isKeyEncryptionAlgorithmSet()) {
               buf.append("KeyEncryptionAlgorithm");
               buf.append(String.valueOf(this.bean.getKeyEncryptionAlgorithm()));
            }

            if (this.bean.isLoginReturnQueryParameterSet()) {
               buf.append("LoginReturnQueryParameter");
               buf.append(String.valueOf(this.bean.getLoginReturnQueryParameter()));
            }

            if (this.bean.isLoginURLSet()) {
               buf.append("LoginURL");
               buf.append(String.valueOf(this.bean.getLoginURL()));
            }

            if (this.bean.isMetadataEncryptionAlgorithmsSet()) {
               buf.append("MetadataEncryptionAlgorithms");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMetadataEncryptionAlgorithms())));
            }

            if (this.bean.isOrganizationNameSet()) {
               buf.append("OrganizationName");
               buf.append(String.valueOf(this.bean.getOrganizationName()));
            }

            if (this.bean.isOrganizationURLSet()) {
               buf.append("OrganizationURL");
               buf.append(String.valueOf(this.bean.getOrganizationURL()));
            }

            if (this.bean.isPublishedSiteURLSet()) {
               buf.append("PublishedSiteURL");
               buf.append(String.valueOf(this.bean.getPublishedSiteURL()));
            }

            if (this.bean.isSSOSigningKeyAliasSet()) {
               buf.append("SSOSigningKeyAlias");
               buf.append(String.valueOf(this.bean.getSSOSigningKeyAlias()));
            }

            if (this.bean.isSSOSigningKeyPassPhraseSet()) {
               buf.append("SSOSigningKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getSSOSigningKeyPassPhrase()));
            }

            if (this.bean.isSSOSigningKeyPassPhraseEncryptedSet()) {
               buf.append("SSOSigningKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSSOSigningKeyPassPhraseEncrypted())));
            }

            if (this.bean.isServiceProviderPreferredBindingSet()) {
               buf.append("ServiceProviderPreferredBinding");
               buf.append(String.valueOf(this.bean.getServiceProviderPreferredBinding()));
            }

            if (this.bean.isTransportLayerSecurityKeyAliasSet()) {
               buf.append("TransportLayerSecurityKeyAlias");
               buf.append(String.valueOf(this.bean.getTransportLayerSecurityKeyAlias()));
            }

            if (this.bean.isTransportLayerSecurityKeyPassPhraseSet()) {
               buf.append("TransportLayerSecurityKeyPassPhrase");
               buf.append(String.valueOf(this.bean.getTransportLayerSecurityKeyPassPhrase()));
            }

            if (this.bean.isTransportLayerSecurityKeyPassPhraseEncryptedSet()) {
               buf.append("TransportLayerSecurityKeyPassPhraseEncrypted");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getTransportLayerSecurityKeyPassPhraseEncrypted())));
            }

            if (this.bean.isAssertionEncryptionEnabledSet()) {
               buf.append("AssertionEncryptionEnabled");
               buf.append(String.valueOf(this.bean.isAssertionEncryptionEnabled()));
            }

            if (this.bean.isForceAuthnSet()) {
               buf.append("ForceAuthn");
               buf.append(String.valueOf(this.bean.isForceAuthn()));
            }

            if (this.bean.isIdentityProviderArtifactBindingEnabledSet()) {
               buf.append("IdentityProviderArtifactBindingEnabled");
               buf.append(String.valueOf(this.bean.isIdentityProviderArtifactBindingEnabled()));
            }

            if (this.bean.isIdentityProviderEnabledSet()) {
               buf.append("IdentityProviderEnabled");
               buf.append(String.valueOf(this.bean.isIdentityProviderEnabled()));
            }

            if (this.bean.isIdentityProviderPOSTBindingEnabledSet()) {
               buf.append("IdentityProviderPOSTBindingEnabled");
               buf.append(String.valueOf(this.bean.isIdentityProviderPOSTBindingEnabled()));
            }

            if (this.bean.isIdentityProviderRedirectBindingEnabledSet()) {
               buf.append("IdentityProviderRedirectBindingEnabled");
               buf.append(String.valueOf(this.bean.isIdentityProviderRedirectBindingEnabled()));
            }

            if (this.bean.isPOSTOneUseCheckEnabledSet()) {
               buf.append("POSTOneUseCheckEnabled");
               buf.append(String.valueOf(this.bean.isPOSTOneUseCheckEnabled()));
            }

            if (this.bean.isPassiveSet()) {
               buf.append("Passive");
               buf.append(String.valueOf(this.bean.isPassive()));
            }

            if (this.bean.isRecipientCheckEnabledSet()) {
               buf.append("RecipientCheckEnabled");
               buf.append(String.valueOf(this.bean.isRecipientCheckEnabled()));
            }

            if (this.bean.isReplicatedCacheEnabledSet()) {
               buf.append("ReplicatedCacheEnabled");
               buf.append(String.valueOf(this.bean.isReplicatedCacheEnabled()));
            }

            if (this.bean.isServiceProviderArtifactBindingEnabledSet()) {
               buf.append("ServiceProviderArtifactBindingEnabled");
               buf.append(String.valueOf(this.bean.isServiceProviderArtifactBindingEnabled()));
            }

            if (this.bean.isServiceProviderEnabledSet()) {
               buf.append("ServiceProviderEnabled");
               buf.append(String.valueOf(this.bean.isServiceProviderEnabled()));
            }

            if (this.bean.isServiceProviderPOSTBindingEnabledSet()) {
               buf.append("ServiceProviderPOSTBindingEnabled");
               buf.append(String.valueOf(this.bean.isServiceProviderPOSTBindingEnabled()));
            }

            if (this.bean.isSignAuthnRequestsSet()) {
               buf.append("SignAuthnRequests");
               buf.append(String.valueOf(this.bean.isSignAuthnRequests()));
            }

            if (this.bean.isWantArtifactRequestsSignedSet()) {
               buf.append("WantArtifactRequestsSigned");
               buf.append(String.valueOf(this.bean.isWantArtifactRequestsSigned()));
            }

            if (this.bean.isWantAssertionsSignedSet()) {
               buf.append("WantAssertionsSigned");
               buf.append(String.valueOf(this.bean.isWantAssertionsSigned()));
            }

            if (this.bean.isWantAuthnRequestsSignedSet()) {
               buf.append("WantAuthnRequestsSigned");
               buf.append(String.valueOf(this.bean.isWantAuthnRequestsSigned()));
            }

            if (this.bean.isWantBasicAuthClientAuthenticationSet()) {
               buf.append("WantBasicAuthClientAuthentication");
               buf.append(String.valueOf(this.bean.isWantBasicAuthClientAuthentication()));
            }

            if (this.bean.isWantTransportLayerSecurityClientAuthenticationSet()) {
               buf.append("WantTransportLayerSecurityClientAuthentication");
               buf.append(String.valueOf(this.bean.isWantTransportLayerSecurityClientAuthentication()));
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
            SingleSignOnServicesMBeanImpl otherTyped = (SingleSignOnServicesMBeanImpl)other;
            this.computeDiff("AllowedTargetHosts", this.bean.getAllowedTargetHosts(), otherTyped.getAllowedTargetHosts(), true);
            this.computeDiff("ArtifactMaxCacheSize", this.bean.getArtifactMaxCacheSize(), otherTyped.getArtifactMaxCacheSize(), true);
            this.computeDiff("ArtifactTimeout", this.bean.getArtifactTimeout(), otherTyped.getArtifactTimeout(), true);
            this.computeDiff("AssertionEncryptionDecryptionKeyAlias", this.bean.getAssertionEncryptionDecryptionKeyAlias(), otherTyped.getAssertionEncryptionDecryptionKeyAlias(), true);
            this.computeDiff("AssertionEncryptionDecryptionKeyPassPhraseEncrypted", this.bean.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted(), otherTyped.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted(), true);
            this.computeDiff("AuthnRequestMaxCacheSize", this.bean.getAuthnRequestMaxCacheSize(), otherTyped.getAuthnRequestMaxCacheSize(), true);
            this.computeDiff("AuthnRequestTimeout", this.bean.getAuthnRequestTimeout(), otherTyped.getAuthnRequestTimeout(), true);
            this.computeDiff("BasicAuthPasswordEncrypted", this.bean.getBasicAuthPasswordEncrypted(), otherTyped.getBasicAuthPasswordEncrypted(), true);
            this.computeDiff("BasicAuthUsername", this.bean.getBasicAuthUsername(), otherTyped.getBasicAuthUsername(), true);
            this.computeDiff("ContactPersonCompany", this.bean.getContactPersonCompany(), otherTyped.getContactPersonCompany(), true);
            this.computeDiff("ContactPersonEmailAddress", this.bean.getContactPersonEmailAddress(), otherTyped.getContactPersonEmailAddress(), true);
            this.computeDiff("ContactPersonGivenName", this.bean.getContactPersonGivenName(), otherTyped.getContactPersonGivenName(), true);
            this.computeDiff("ContactPersonSurName", this.bean.getContactPersonSurName(), otherTyped.getContactPersonSurName(), true);
            this.computeDiff("ContactPersonTelephoneNumber", this.bean.getContactPersonTelephoneNumber(), otherTyped.getContactPersonTelephoneNumber(), true);
            this.computeDiff("ContactPersonType", this.bean.getContactPersonType(), otherTyped.getContactPersonType(), true);
            this.computeDiff("DataEncryptionAlgorithm", this.bean.getDataEncryptionAlgorithm(), otherTyped.getDataEncryptionAlgorithm(), true);
            this.computeDiff("DefaultURL", this.bean.getDefaultURL(), otherTyped.getDefaultURL(), true);
            this.computeDiff("EntityID", this.bean.getEntityID(), otherTyped.getEntityID(), true);
            this.computeDiff("ErrorPath", this.bean.getErrorPath(), otherTyped.getErrorPath(), true);
            this.computeDiff("IdentityProviderPreferredBinding", this.bean.getIdentityProviderPreferredBinding(), otherTyped.getIdentityProviderPreferredBinding(), true);
            this.computeDiff("KeyEncryptionAlgorithm", this.bean.getKeyEncryptionAlgorithm(), otherTyped.getKeyEncryptionAlgorithm(), true);
            this.computeDiff("LoginReturnQueryParameter", this.bean.getLoginReturnQueryParameter(), otherTyped.getLoginReturnQueryParameter(), true);
            this.computeDiff("LoginURL", this.bean.getLoginURL(), otherTyped.getLoginURL(), true);
            this.computeDiff("MetadataEncryptionAlgorithms", this.bean.getMetadataEncryptionAlgorithms(), otherTyped.getMetadataEncryptionAlgorithms(), true);
            this.computeDiff("OrganizationName", this.bean.getOrganizationName(), otherTyped.getOrganizationName(), true);
            this.computeDiff("OrganizationURL", this.bean.getOrganizationURL(), otherTyped.getOrganizationURL(), true);
            this.computeDiff("PublishedSiteURL", this.bean.getPublishedSiteURL(), otherTyped.getPublishedSiteURL(), true);
            this.computeDiff("SSOSigningKeyAlias", this.bean.getSSOSigningKeyAlias(), otherTyped.getSSOSigningKeyAlias(), true);
            this.computeDiff("SSOSigningKeyPassPhraseEncrypted", this.bean.getSSOSigningKeyPassPhraseEncrypted(), otherTyped.getSSOSigningKeyPassPhraseEncrypted(), true);
            this.computeDiff("ServiceProviderPreferredBinding", this.bean.getServiceProviderPreferredBinding(), otherTyped.getServiceProviderPreferredBinding(), true);
            this.computeDiff("TransportLayerSecurityKeyAlias", this.bean.getTransportLayerSecurityKeyAlias(), otherTyped.getTransportLayerSecurityKeyAlias(), true);
            this.computeDiff("TransportLayerSecurityKeyPassPhraseEncrypted", this.bean.getTransportLayerSecurityKeyPassPhraseEncrypted(), otherTyped.getTransportLayerSecurityKeyPassPhraseEncrypted(), true);
            this.computeDiff("AssertionEncryptionEnabled", this.bean.isAssertionEncryptionEnabled(), otherTyped.isAssertionEncryptionEnabled(), true);
            this.computeDiff("ForceAuthn", this.bean.isForceAuthn(), otherTyped.isForceAuthn(), true);
            this.computeDiff("IdentityProviderArtifactBindingEnabled", this.bean.isIdentityProviderArtifactBindingEnabled(), otherTyped.isIdentityProviderArtifactBindingEnabled(), true);
            this.computeDiff("IdentityProviderEnabled", this.bean.isIdentityProviderEnabled(), otherTyped.isIdentityProviderEnabled(), true);
            this.computeDiff("IdentityProviderPOSTBindingEnabled", this.bean.isIdentityProviderPOSTBindingEnabled(), otherTyped.isIdentityProviderPOSTBindingEnabled(), true);
            this.computeDiff("IdentityProviderRedirectBindingEnabled", this.bean.isIdentityProviderRedirectBindingEnabled(), otherTyped.isIdentityProviderRedirectBindingEnabled(), true);
            this.computeDiff("POSTOneUseCheckEnabled", this.bean.isPOSTOneUseCheckEnabled(), otherTyped.isPOSTOneUseCheckEnabled(), true);
            this.computeDiff("Passive", this.bean.isPassive(), otherTyped.isPassive(), true);
            this.computeDiff("RecipientCheckEnabled", this.bean.isRecipientCheckEnabled(), otherTyped.isRecipientCheckEnabled(), true);
            this.computeDiff("ReplicatedCacheEnabled", this.bean.isReplicatedCacheEnabled(), otherTyped.isReplicatedCacheEnabled(), false);
            this.computeDiff("ServiceProviderArtifactBindingEnabled", this.bean.isServiceProviderArtifactBindingEnabled(), otherTyped.isServiceProviderArtifactBindingEnabled(), true);
            this.computeDiff("ServiceProviderEnabled", this.bean.isServiceProviderEnabled(), otherTyped.isServiceProviderEnabled(), true);
            this.computeDiff("ServiceProviderPOSTBindingEnabled", this.bean.isServiceProviderPOSTBindingEnabled(), otherTyped.isServiceProviderPOSTBindingEnabled(), true);
            this.computeDiff("SignAuthnRequests", this.bean.isSignAuthnRequests(), otherTyped.isSignAuthnRequests(), true);
            this.computeDiff("WantArtifactRequestsSigned", this.bean.isWantArtifactRequestsSigned(), otherTyped.isWantArtifactRequestsSigned(), true);
            this.computeDiff("WantAssertionsSigned", this.bean.isWantAssertionsSigned(), otherTyped.isWantAssertionsSigned(), true);
            this.computeDiff("WantAuthnRequestsSigned", this.bean.isWantAuthnRequestsSigned(), otherTyped.isWantAuthnRequestsSigned(), true);
            this.computeDiff("WantBasicAuthClientAuthentication", this.bean.isWantBasicAuthClientAuthentication(), otherTyped.isWantBasicAuthClientAuthentication(), true);
            this.computeDiff("WantTransportLayerSecurityClientAuthentication", this.bean.isWantTransportLayerSecurityClientAuthentication(), otherTyped.isWantTransportLayerSecurityClientAuthentication(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SingleSignOnServicesMBeanImpl original = (SingleSignOnServicesMBeanImpl)event.getSourceBean();
            SingleSignOnServicesMBeanImpl proposed = (SingleSignOnServicesMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AllowedTargetHosts")) {
                  original.setAllowedTargetHosts(proposed.getAllowedTargetHosts());
                  original._conditionalUnset(update.isUnsetUpdate(), 64);
               } else if (prop.equals("ArtifactMaxCacheSize")) {
                  original.setArtifactMaxCacheSize(proposed.getArtifactMaxCacheSize());
                  original._conditionalUnset(update.isUnsetUpdate(), 54);
               } else if (prop.equals("ArtifactTimeout")) {
                  original.setArtifactTimeout(proposed.getArtifactTimeout());
                  original._conditionalUnset(update.isUnsetUpdate(), 55);
               } else if (prop.equals("AssertionEncryptionDecryptionKeyAlias")) {
                  original.setAssertionEncryptionDecryptionKeyAlias(proposed.getAssertionEncryptionDecryptionKeyAlias());
                  original._conditionalUnset(update.isUnsetUpdate(), 61);
               } else if (!prop.equals("AssertionEncryptionDecryptionKeyPassPhrase")) {
                  if (prop.equals("AssertionEncryptionDecryptionKeyPassPhraseEncrypted")) {
                     original.setAssertionEncryptionDecryptionKeyPassPhraseEncrypted(proposed.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted());
                     original._conditionalUnset(update.isUnsetUpdate(), 63);
                  } else if (prop.equals("AuthnRequestMaxCacheSize")) {
                     original.setAuthnRequestMaxCacheSize(proposed.getAuthnRequestMaxCacheSize());
                     original._conditionalUnset(update.isUnsetUpdate(), 52);
                  } else if (prop.equals("AuthnRequestTimeout")) {
                     original.setAuthnRequestTimeout(proposed.getAuthnRequestTimeout());
                     original._conditionalUnset(update.isUnsetUpdate(), 53);
                  } else if (!prop.equals("BasicAuthPassword")) {
                     if (prop.equals("BasicAuthPasswordEncrypted")) {
                        original.setBasicAuthPasswordEncrypted(proposed.getBasicAuthPasswordEncrypted());
                        original._conditionalUnset(update.isUnsetUpdate(), 48);
                     } else if (prop.equals("BasicAuthUsername")) {
                        original.setBasicAuthUsername(proposed.getBasicAuthUsername());
                        original._conditionalUnset(update.isUnsetUpdate(), 46);
                     } else if (prop.equals("ContactPersonCompany")) {
                        original.setContactPersonCompany(proposed.getContactPersonCompany());
                        original._conditionalUnset(update.isUnsetUpdate(), 13);
                     } else if (prop.equals("ContactPersonEmailAddress")) {
                        original.setContactPersonEmailAddress(proposed.getContactPersonEmailAddress());
                        original._conditionalUnset(update.isUnsetUpdate(), 15);
                     } else if (prop.equals("ContactPersonGivenName")) {
                        original.setContactPersonGivenName(proposed.getContactPersonGivenName());
                        original._conditionalUnset(update.isUnsetUpdate(), 10);
                     } else if (prop.equals("ContactPersonSurName")) {
                        original.setContactPersonSurName(proposed.getContactPersonSurName());
                        original._conditionalUnset(update.isUnsetUpdate(), 11);
                     } else if (prop.equals("ContactPersonTelephoneNumber")) {
                        original.setContactPersonTelephoneNumber(proposed.getContactPersonTelephoneNumber());
                        original._conditionalUnset(update.isUnsetUpdate(), 14);
                     } else if (prop.equals("ContactPersonType")) {
                        original.setContactPersonType(proposed.getContactPersonType());
                        original._conditionalUnset(update.isUnsetUpdate(), 12);
                     } else if (prop.equals("DataEncryptionAlgorithm")) {
                        original.setDataEncryptionAlgorithm(proposed.getDataEncryptionAlgorithm());
                        original._conditionalUnset(update.isUnsetUpdate(), 58);
                     } else if (prop.equals("DefaultURL")) {
                        original.setDefaultURL(proposed.getDefaultURL());
                        original._conditionalUnset(update.isUnsetUpdate(), 22);
                     } else if (prop.equals("EntityID")) {
                        original.setEntityID(proposed.getEntityID());
                        original._conditionalUnset(update.isUnsetUpdate(), 19);
                     } else if (prop.equals("ErrorPath")) {
                        original.setErrorPath(proposed.getErrorPath());
                        original._conditionalUnset(update.isUnsetUpdate(), 20);
                     } else if (prop.equals("IdentityProviderPreferredBinding")) {
                        original.setIdentityProviderPreferredBinding(proposed.getIdentityProviderPreferredBinding());
                        original._conditionalUnset(update.isUnsetUpdate(), 37);
                     } else if (prop.equals("KeyEncryptionAlgorithm")) {
                        original.setKeyEncryptionAlgorithm(proposed.getKeyEncryptionAlgorithm());
                        original._conditionalUnset(update.isUnsetUpdate(), 59);
                     } else if (prop.equals("LoginReturnQueryParameter")) {
                        original.setLoginReturnQueryParameter(proposed.getLoginReturnQueryParameter());
                        original._conditionalUnset(update.isUnsetUpdate(), 39);
                     } else if (prop.equals("LoginURL")) {
                        original.setLoginURL(proposed.getLoginURL());
                        original._conditionalUnset(update.isUnsetUpdate(), 38);
                     } else if (prop.equals("MetadataEncryptionAlgorithms")) {
                        original.setMetadataEncryptionAlgorithms(proposed.getMetadataEncryptionAlgorithms());
                        original._conditionalUnset(update.isUnsetUpdate(), 60);
                     } else if (prop.equals("OrganizationName")) {
                        original.setOrganizationName(proposed.getOrganizationName());
                        original._conditionalUnset(update.isUnsetUpdate(), 16);
                     } else if (prop.equals("OrganizationURL")) {
                        original.setOrganizationURL(proposed.getOrganizationURL());
                        original._conditionalUnset(update.isUnsetUpdate(), 17);
                     } else if (prop.equals("PublishedSiteURL")) {
                        original.setPublishedSiteURL(proposed.getPublishedSiteURL());
                        original._conditionalUnset(update.isUnsetUpdate(), 18);
                     } else if (prop.equals("SSOSigningKeyAlias")) {
                        original.setSSOSigningKeyAlias(proposed.getSSOSigningKeyAlias());
                        original._conditionalUnset(update.isUnsetUpdate(), 28);
                     } else if (!prop.equals("SSOSigningKeyPassPhrase")) {
                        if (prop.equals("SSOSigningKeyPassPhraseEncrypted")) {
                           original.setSSOSigningKeyPassPhraseEncrypted(proposed.getSSOSigningKeyPassPhraseEncrypted());
                           original._conditionalUnset(update.isUnsetUpdate(), 30);
                        } else if (prop.equals("ServiceProviderPreferredBinding")) {
                           original.setServiceProviderPreferredBinding(proposed.getServiceProviderPreferredBinding());
                           original._conditionalUnset(update.isUnsetUpdate(), 25);
                        } else if (prop.equals("TransportLayerSecurityKeyAlias")) {
                           original.setTransportLayerSecurityKeyAlias(proposed.getTransportLayerSecurityKeyAlias());
                           original._conditionalUnset(update.isUnsetUpdate(), 43);
                        } else if (!prop.equals("TransportLayerSecurityKeyPassPhrase")) {
                           if (prop.equals("TransportLayerSecurityKeyPassPhraseEncrypted")) {
                              original.setTransportLayerSecurityKeyPassPhraseEncrypted(proposed.getTransportLayerSecurityKeyPassPhraseEncrypted());
                              original._conditionalUnset(update.isUnsetUpdate(), 45);
                           } else if (prop.equals("AssertionEncryptionEnabled")) {
                              original.setAssertionEncryptionEnabled(proposed.isAssertionEncryptionEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 57);
                           } else if (prop.equals("ForceAuthn")) {
                              original.setForceAuthn(proposed.isForceAuthn());
                              original._conditionalUnset(update.isUnsetUpdate(), 31);
                           } else if (prop.equals("IdentityProviderArtifactBindingEnabled")) {
                              original.setIdentityProviderArtifactBindingEnabled(proposed.isIdentityProviderArtifactBindingEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 34);
                           } else if (prop.equals("IdentityProviderEnabled")) {
                              original.setIdentityProviderEnabled(proposed.isIdentityProviderEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 33);
                           } else if (prop.equals("IdentityProviderPOSTBindingEnabled")) {
                              original.setIdentityProviderPOSTBindingEnabled(proposed.isIdentityProviderPOSTBindingEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 35);
                           } else if (prop.equals("IdentityProviderRedirectBindingEnabled")) {
                              original.setIdentityProviderRedirectBindingEnabled(proposed.isIdentityProviderRedirectBindingEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 36);
                           } else if (prop.equals("POSTOneUseCheckEnabled")) {
                              original.setPOSTOneUseCheckEnabled(proposed.isPOSTOneUseCheckEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 42);
                           } else if (prop.equals("Passive")) {
                              original.setPassive(proposed.isPassive());
                              original._conditionalUnset(update.isUnsetUpdate(), 32);
                           } else if (prop.equals("RecipientCheckEnabled")) {
                              original.setRecipientCheckEnabled(proposed.isRecipientCheckEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 41);
                           } else if (prop.equals("ReplicatedCacheEnabled")) {
                              original.setReplicatedCacheEnabled(proposed.isReplicatedCacheEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 56);
                           } else if (prop.equals("ServiceProviderArtifactBindingEnabled")) {
                              original.setServiceProviderArtifactBindingEnabled(proposed.isServiceProviderArtifactBindingEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 23);
                           } else if (prop.equals("ServiceProviderEnabled")) {
                              original.setServiceProviderEnabled(proposed.isServiceProviderEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 21);
                           } else if (prop.equals("ServiceProviderPOSTBindingEnabled")) {
                              original.setServiceProviderPOSTBindingEnabled(proposed.isServiceProviderPOSTBindingEnabled());
                              original._conditionalUnset(update.isUnsetUpdate(), 24);
                           } else if (prop.equals("SignAuthnRequests")) {
                              original.setSignAuthnRequests(proposed.isSignAuthnRequests());
                              original._conditionalUnset(update.isUnsetUpdate(), 26);
                           } else if (prop.equals("WantArtifactRequestsSigned")) {
                              original.setWantArtifactRequestsSigned(proposed.isWantArtifactRequestsSigned());
                              original._conditionalUnset(update.isUnsetUpdate(), 49);
                           } else if (prop.equals("WantAssertionsSigned")) {
                              original.setWantAssertionsSigned(proposed.isWantAssertionsSigned());
                              original._conditionalUnset(update.isUnsetUpdate(), 27);
                           } else if (prop.equals("WantAuthnRequestsSigned")) {
                              original.setWantAuthnRequestsSigned(proposed.isWantAuthnRequestsSigned());
                              original._conditionalUnset(update.isUnsetUpdate(), 40);
                           } else if (prop.equals("WantBasicAuthClientAuthentication")) {
                              original.setWantBasicAuthClientAuthentication(proposed.isWantBasicAuthClientAuthentication());
                              original._conditionalUnset(update.isUnsetUpdate(), 51);
                           } else if (prop.equals("WantTransportLayerSecurityClientAuthentication")) {
                              original.setWantTransportLayerSecurityClientAuthentication(proposed.isWantTransportLayerSecurityClientAuthentication());
                              original._conditionalUnset(update.isUnsetUpdate(), 50);
                           } else {
                              super.applyPropertyUpdate(event, update);
                           }
                        }
                     }
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
            SingleSignOnServicesMBeanImpl copy = (SingleSignOnServicesMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("AllowedTargetHosts")) && this.bean.isAllowedTargetHostsSet()) {
               o = this.bean.getAllowedTargetHosts();
               copy.setAllowedTargetHosts(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ArtifactMaxCacheSize")) && this.bean.isArtifactMaxCacheSizeSet()) {
               copy.setArtifactMaxCacheSize(this.bean.getArtifactMaxCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("ArtifactTimeout")) && this.bean.isArtifactTimeoutSet()) {
               copy.setArtifactTimeout(this.bean.getArtifactTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("AssertionEncryptionDecryptionKeyAlias")) && this.bean.isAssertionEncryptionDecryptionKeyAliasSet()) {
               copy.setAssertionEncryptionDecryptionKeyAlias(this.bean.getAssertionEncryptionDecryptionKeyAlias());
            }

            byte[] o;
            if ((excludeProps == null || !excludeProps.contains("AssertionEncryptionDecryptionKeyPassPhraseEncrypted")) && this.bean.isAssertionEncryptionDecryptionKeyPassPhraseEncryptedSet()) {
               o = this.bean.getAssertionEncryptionDecryptionKeyPassPhraseEncrypted();
               copy.setAssertionEncryptionDecryptionKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("AuthnRequestMaxCacheSize")) && this.bean.isAuthnRequestMaxCacheSizeSet()) {
               copy.setAuthnRequestMaxCacheSize(this.bean.getAuthnRequestMaxCacheSize());
            }

            if ((excludeProps == null || !excludeProps.contains("AuthnRequestTimeout")) && this.bean.isAuthnRequestTimeoutSet()) {
               copy.setAuthnRequestTimeout(this.bean.getAuthnRequestTimeout());
            }

            if ((excludeProps == null || !excludeProps.contains("BasicAuthPasswordEncrypted")) && this.bean.isBasicAuthPasswordEncryptedSet()) {
               o = this.bean.getBasicAuthPasswordEncrypted();
               copy.setBasicAuthPasswordEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("BasicAuthUsername")) && this.bean.isBasicAuthUsernameSet()) {
               copy.setBasicAuthUsername(this.bean.getBasicAuthUsername());
            }

            if ((excludeProps == null || !excludeProps.contains("ContactPersonCompany")) && this.bean.isContactPersonCompanySet()) {
               copy.setContactPersonCompany(this.bean.getContactPersonCompany());
            }

            if ((excludeProps == null || !excludeProps.contains("ContactPersonEmailAddress")) && this.bean.isContactPersonEmailAddressSet()) {
               copy.setContactPersonEmailAddress(this.bean.getContactPersonEmailAddress());
            }

            if ((excludeProps == null || !excludeProps.contains("ContactPersonGivenName")) && this.bean.isContactPersonGivenNameSet()) {
               copy.setContactPersonGivenName(this.bean.getContactPersonGivenName());
            }

            if ((excludeProps == null || !excludeProps.contains("ContactPersonSurName")) && this.bean.isContactPersonSurNameSet()) {
               copy.setContactPersonSurName(this.bean.getContactPersonSurName());
            }

            if ((excludeProps == null || !excludeProps.contains("ContactPersonTelephoneNumber")) && this.bean.isContactPersonTelephoneNumberSet()) {
               copy.setContactPersonTelephoneNumber(this.bean.getContactPersonTelephoneNumber());
            }

            if ((excludeProps == null || !excludeProps.contains("ContactPersonType")) && this.bean.isContactPersonTypeSet()) {
               copy.setContactPersonType(this.bean.getContactPersonType());
            }

            if ((excludeProps == null || !excludeProps.contains("DataEncryptionAlgorithm")) && this.bean.isDataEncryptionAlgorithmSet()) {
               copy.setDataEncryptionAlgorithm(this.bean.getDataEncryptionAlgorithm());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultURL")) && this.bean.isDefaultURLSet()) {
               copy.setDefaultURL(this.bean.getDefaultURL());
            }

            if ((excludeProps == null || !excludeProps.contains("EntityID")) && this.bean.isEntityIDSet()) {
               copy.setEntityID(this.bean.getEntityID());
            }

            if ((excludeProps == null || !excludeProps.contains("ErrorPath")) && this.bean.isErrorPathSet()) {
               copy.setErrorPath(this.bean.getErrorPath());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityProviderPreferredBinding")) && this.bean.isIdentityProviderPreferredBindingSet()) {
               copy.setIdentityProviderPreferredBinding(this.bean.getIdentityProviderPreferredBinding());
            }

            if ((excludeProps == null || !excludeProps.contains("KeyEncryptionAlgorithm")) && this.bean.isKeyEncryptionAlgorithmSet()) {
               copy.setKeyEncryptionAlgorithm(this.bean.getKeyEncryptionAlgorithm());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginReturnQueryParameter")) && this.bean.isLoginReturnQueryParameterSet()) {
               copy.setLoginReturnQueryParameter(this.bean.getLoginReturnQueryParameter());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginURL")) && this.bean.isLoginURLSet()) {
               copy.setLoginURL(this.bean.getLoginURL());
            }

            if ((excludeProps == null || !excludeProps.contains("MetadataEncryptionAlgorithms")) && this.bean.isMetadataEncryptionAlgorithmsSet()) {
               o = this.bean.getMetadataEncryptionAlgorithms();
               copy.setMetadataEncryptionAlgorithms(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("OrganizationName")) && this.bean.isOrganizationNameSet()) {
               copy.setOrganizationName(this.bean.getOrganizationName());
            }

            if ((excludeProps == null || !excludeProps.contains("OrganizationURL")) && this.bean.isOrganizationURLSet()) {
               copy.setOrganizationURL(this.bean.getOrganizationURL());
            }

            if ((excludeProps == null || !excludeProps.contains("PublishedSiteURL")) && this.bean.isPublishedSiteURLSet()) {
               copy.setPublishedSiteURL(this.bean.getPublishedSiteURL());
            }

            if ((excludeProps == null || !excludeProps.contains("SSOSigningKeyAlias")) && this.bean.isSSOSigningKeyAliasSet()) {
               copy.setSSOSigningKeyAlias(this.bean.getSSOSigningKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("SSOSigningKeyPassPhraseEncrypted")) && this.bean.isSSOSigningKeyPassPhraseEncryptedSet()) {
               o = this.bean.getSSOSigningKeyPassPhraseEncrypted();
               copy.setSSOSigningKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceProviderPreferredBinding")) && this.bean.isServiceProviderPreferredBindingSet()) {
               copy.setServiceProviderPreferredBinding(this.bean.getServiceProviderPreferredBinding());
            }

            if ((excludeProps == null || !excludeProps.contains("TransportLayerSecurityKeyAlias")) && this.bean.isTransportLayerSecurityKeyAliasSet()) {
               copy.setTransportLayerSecurityKeyAlias(this.bean.getTransportLayerSecurityKeyAlias());
            }

            if ((excludeProps == null || !excludeProps.contains("TransportLayerSecurityKeyPassPhraseEncrypted")) && this.bean.isTransportLayerSecurityKeyPassPhraseEncryptedSet()) {
               o = this.bean.getTransportLayerSecurityKeyPassPhraseEncrypted();
               copy.setTransportLayerSecurityKeyPassPhraseEncrypted(o == null ? null : (byte[])((byte[])((byte[])((byte[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("AssertionEncryptionEnabled")) && this.bean.isAssertionEncryptionEnabledSet()) {
               copy.setAssertionEncryptionEnabled(this.bean.isAssertionEncryptionEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ForceAuthn")) && this.bean.isForceAuthnSet()) {
               copy.setForceAuthn(this.bean.isForceAuthn());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityProviderArtifactBindingEnabled")) && this.bean.isIdentityProviderArtifactBindingEnabledSet()) {
               copy.setIdentityProviderArtifactBindingEnabled(this.bean.isIdentityProviderArtifactBindingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityProviderEnabled")) && this.bean.isIdentityProviderEnabledSet()) {
               copy.setIdentityProviderEnabled(this.bean.isIdentityProviderEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityProviderPOSTBindingEnabled")) && this.bean.isIdentityProviderPOSTBindingEnabledSet()) {
               copy.setIdentityProviderPOSTBindingEnabled(this.bean.isIdentityProviderPOSTBindingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("IdentityProviderRedirectBindingEnabled")) && this.bean.isIdentityProviderRedirectBindingEnabledSet()) {
               copy.setIdentityProviderRedirectBindingEnabled(this.bean.isIdentityProviderRedirectBindingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("POSTOneUseCheckEnabled")) && this.bean.isPOSTOneUseCheckEnabledSet()) {
               copy.setPOSTOneUseCheckEnabled(this.bean.isPOSTOneUseCheckEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("Passive")) && this.bean.isPassiveSet()) {
               copy.setPassive(this.bean.isPassive());
            }

            if ((excludeProps == null || !excludeProps.contains("RecipientCheckEnabled")) && this.bean.isRecipientCheckEnabledSet()) {
               copy.setRecipientCheckEnabled(this.bean.isRecipientCheckEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ReplicatedCacheEnabled")) && this.bean.isReplicatedCacheEnabledSet()) {
               copy.setReplicatedCacheEnabled(this.bean.isReplicatedCacheEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceProviderArtifactBindingEnabled")) && this.bean.isServiceProviderArtifactBindingEnabledSet()) {
               copy.setServiceProviderArtifactBindingEnabled(this.bean.isServiceProviderArtifactBindingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceProviderEnabled")) && this.bean.isServiceProviderEnabledSet()) {
               copy.setServiceProviderEnabled(this.bean.isServiceProviderEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceProviderPOSTBindingEnabled")) && this.bean.isServiceProviderPOSTBindingEnabledSet()) {
               copy.setServiceProviderPOSTBindingEnabled(this.bean.isServiceProviderPOSTBindingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("SignAuthnRequests")) && this.bean.isSignAuthnRequestsSet()) {
               copy.setSignAuthnRequests(this.bean.isSignAuthnRequests());
            }

            if ((excludeProps == null || !excludeProps.contains("WantArtifactRequestsSigned")) && this.bean.isWantArtifactRequestsSignedSet()) {
               copy.setWantArtifactRequestsSigned(this.bean.isWantArtifactRequestsSigned());
            }

            if ((excludeProps == null || !excludeProps.contains("WantAssertionsSigned")) && this.bean.isWantAssertionsSignedSet()) {
               copy.setWantAssertionsSigned(this.bean.isWantAssertionsSigned());
            }

            if ((excludeProps == null || !excludeProps.contains("WantAuthnRequestsSigned")) && this.bean.isWantAuthnRequestsSignedSet()) {
               copy.setWantAuthnRequestsSigned(this.bean.isWantAuthnRequestsSigned());
            }

            if ((excludeProps == null || !excludeProps.contains("WantBasicAuthClientAuthentication")) && this.bean.isWantBasicAuthClientAuthenticationSet()) {
               copy.setWantBasicAuthClientAuthentication(this.bean.isWantBasicAuthClientAuthentication());
            }

            if ((excludeProps == null || !excludeProps.contains("WantTransportLayerSecurityClientAuthentication")) && this.bean.isWantTransportLayerSecurityClientAuthenticationSet()) {
               copy.setWantTransportLayerSecurityClientAuthentication(this.bean.isWantTransportLayerSecurityClientAuthentication());
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
