package weblogic.management.security;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.util.Vector;
import javax.management.InvalidAttributeValueException;
import javax.management.JMException;
import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.management.configuration.ConfigurationValidator;
import weblogic.management.provider.beaninfo.BeanInfoAccessFactory;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authentication.PasswordValidatorMBean;
import weblogic.management.security.authorization.AdjudicatorMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.management.utils.ErrorCollectionException;
import weblogic.security.SecurityMessagesTextFormatter;
import weblogic.security.internal.RealmValidatorImpl;

public class RealmImpl extends BaseMBeanImpl {
   private boolean isInConstructor = true;
   private static final String MBEAN_SUFFIX = "MBean";

   public RealmImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected RealmImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }

   public void validate() throws ErrorCollectionException {
      (new RealmValidatorImpl()).validate(this.getRealm());
   }

   private boolean constructed() {
      return !this.isInConstructor;
   }

   public void _postCreate() {
      this.isInConstructor = false;
   }

   private RealmMBean getRealm() {
      try {
         return (RealmMBean)this.getProxy();
      } catch (MBeanException var2) {
         throw new AssertionError(var2);
      }
   }

   private RealmContainer getRealmContainer() {
      return (RealmContainer)((RealmContainer)this.getRealm().getParentBean());
   }

   private boolean amDefaultRealm() {
      RealmMBean realm1 = this.getRealm();
      if (realm1 == null) {
         throw new AssertionError("Realm customizer cannot get its RealmMBean");
      } else {
         String name1 = realm1.getName();
         if (name1 != null && name1.length() >= 1) {
            RealmMBean realm2 = this.getRealmContainer().getDefaultRealmInternal();
            return realm2 == null ? false : name1.equals(realm2.getName());
         } else {
            throw new AssertionError("RealmMBean has a null or empty name");
         }
      }
   }

   public boolean isDefaultRealm() {
      return this.constructed() ? this.amDefaultRealm() : false;
   }

   public void setDefaultRealm(boolean isDefault) throws InvalidAttributeValueException {
      if (this.constructed()) {
         if (this.amDefaultRealm()) {
            if (!isDefault) {
               this.getRealmContainer().setDefaultRealmInternal((RealmMBean)null);
            }
         } else if (isDefault) {
            this.getRealmContainer().setDefaultRealmInternal(this.getRealm());
         }
      }

   }

   public String getCompatibilityObjectName() {
      String prefix = "Security:Name=";
      String result = prefix + this.getRealm().getName();
      return result;
   }

   private String[] getProviderTypes(String baseProviderType) {
      String[] candidateTypes = BeanInfoAccessFactory.getBeanInfoAccess().getSubtypes(baseProviderType + "MBean");
      Vector v = new Vector();

      for(int i = 0; candidateTypes != null && i < candidateTypes.length; ++i) {
         String candidateType = candidateTypes[i];
         BeanInfo beanInfo = BeanInfoAccessFactory.getBeanInfoAccess().getBeanInfoForInterface(candidateType, false, (String)null);
         if (beanInfo != null) {
            BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
            if (beanDescriptor == null) {
               throw new AssertionError("Could not get BeanDescriptor for provider type " + candidateType);
            }

            Boolean isAbs = (Boolean)beanDescriptor.getValue("abstract");
            boolean isAbstract = isAbs != null ? isAbs : false;
            Boolean isExc = (Boolean)beanDescriptor.getValue("exclude");
            boolean isExcluded = isExc != null ? isExc : false;
            if (!isAbstract && !isExcluded) {
               if (!candidateType.endsWith("MBean")) {
                  throw new AssertionError("Provider type " + candidateType + " should have ended with " + "MBean");
               }

               String providerType = candidateType.substring(0, candidateType.length() - "MBean".length());
               v.add(providerType);
            }
         }
      }

      return v.size() > 0 ? (String[])((String[])v.toArray(new String[0])) : null;
   }

   public String[] getPasswordValidatorTypes() {
      return this.getProviderTypes("weblogic.management.security.authentication.PasswordValidator");
   }

   public String[] getAuditorTypes() {
      return this.getProviderTypes("weblogic.management.security.audit.Auditor");
   }

   public String[] getAuthenticationProviderTypes() {
      return this.getProviderTypes("weblogic.management.security.authentication.AuthenticationProvider");
   }

   public String[] getRoleMapperTypes() {
      return this.getProviderTypes("weblogic.management.security.authorization.RoleMapper");
   }

   public String[] getAuthorizerTypes() {
      return this.getProviderTypes("weblogic.management.security.authorization.Authorizer");
   }

   public String[] getAdjudicatorTypes() {
      return this.getProviderTypes("weblogic.management.security.authorization.Adjudicator");
   }

   public String[] getCredentialMapperTypes() {
      return this.getProviderTypes("weblogic.management.security.credentials.CredentialMapper");
   }

   public String[] getCertPathProviderTypes() {
      return this.getProviderTypes("weblogic.management.security.pk.CertPathProvider");
   }

   private ProviderMBean createProvider(String providerType, String[] legalTypes, String type, boolean setName, String name, ProviderCreator creator) throws JMException, ClassNotFoundException {
      boolean found = false;

      for(int i = 0; !found && legalTypes != null && i < legalTypes.length; ++i) {
         if (legalTypes[i].equals(type)) {
            found = true;
         }
      }

      if (found) {
         Class intf = Class.forName(type + "MBean", true, DescriptorClassLoader.getClassLoader());
         ClassLoader cl = Thread.currentThread().getContextClassLoader();

         ProviderMBean var10;
         try {
            Thread.currentThread().setContextClassLoader(DescriptorClassLoader.getClassLoader());
            var10 = setName ? creator.createProvider(intf, name) : creator.createProvider(intf);
         } finally {
            Thread.currentThread().setContextClassLoader(cl);
         }

         return var10;
      } else {
         String types = "";

         for(int i = 0; legalTypes != null && i < legalTypes.length; ++i) {
            if (i > 0) {
               types = types + ",";
            }

            types = types + legalTypes[i];
         }

         throw new IllegalArgumentException(SecurityMessagesTextFormatter.getInstance().getUnknownSecurityProviderTypeError(type, providerType, types));
      }
   }

   private AuditorMBean createAuditor(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (AuditorMBean)this.createProvider("Auditor", this.getRealm().getAuditorTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAuditor(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAuditor(intf, name);
         }
      });
   }

   public AuthenticationProviderMBean createAuthenticationProvider(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (AuthenticationProviderMBean)this.createProvider("AutenticationProvider", this.getRealm().getAuthenticationProviderTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAuthenticationProvider(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAuthenticationProvider(intf, name);
         }
      });
   }

   public RoleMapperMBean createRoleMapper(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (RoleMapperMBean)this.createProvider("RoleMapper", this.getRealm().getRoleMapperTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createRoleMapper(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createRoleMapper(intf, name);
         }
      });
   }

   public AuthorizerMBean createAuthorizer(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (AuthorizerMBean)this.createProvider("Authorizer", this.getRealm().getAuthorizerTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAuthorizer(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAuthorizer(intf, name);
         }
      });
   }

   public AdjudicatorMBean createAdjudicator(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (AdjudicatorMBean)this.createProvider("Adjudicator", this.getRealm().getAdjudicatorTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAdjudicator(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createAdjudicator(intf, name);
         }
      });
   }

   public CredentialMapperMBean createCredentialMapper(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (CredentialMapperMBean)this.createProvider("CredentialMapper", this.getRealm().getCredentialMapperTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createCredentialMapper(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createCredentialMapper(intf, name);
         }
      });
   }

   public CertPathProviderMBean createCertPathProvider(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (CertPathProviderMBean)this.createProvider("CertPathProvider", this.getRealm().getCertPathProviderTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createCertPathProvider(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createCertPathProvider(intf, name);
         }
      });
   }

   public PasswordValidatorMBean createPasswordValidator(String type, boolean setName, String name) throws JMException, ClassNotFoundException {
      return (PasswordValidatorMBean)this.createProvider("PasswordValidator", this.getRealm().getPasswordValidatorTypes(), type, setName, name, new ProviderCreator() {
         public ProviderMBean createProvider(Class intf) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createPasswordValidator(intf);
         }

         public ProviderMBean createProvider(Class intf, String name) throws JMException, ClassNotFoundException {
            return RealmImpl.this.getRealm().createPasswordValidator(intf, name);
         }
      });
   }

   public AuditorMBean createAuditor(String name, String type) throws JMException, ClassNotFoundException {
      return this.createAuditor(type, true, name);
   }

   public AuthenticationProviderMBean createAuthenticationProvider(String name, String type) throws JMException, ClassNotFoundException {
      ConfigurationValidator.validateName(name);
      return this.createAuthenticationProvider(type, true, name);
   }

   public RoleMapperMBean createRoleMapper(String name, String type) throws JMException, ClassNotFoundException {
      return this.createRoleMapper(type, true, name);
   }

   public AuthorizerMBean createAuthorizer(String name, String type) throws JMException, ClassNotFoundException {
      return this.createAuthorizer(type, true, name);
   }

   public AdjudicatorMBean createAdjudicator(String name, String type) throws JMException, ClassNotFoundException {
      return this.createAdjudicator(type, true, name);
   }

   public CredentialMapperMBean createCredentialMapper(String name, String type) throws JMException, ClassNotFoundException {
      return this.createCredentialMapper(type, true, name);
   }

   public CertPathProviderMBean createCertPathProvider(String name, String type) throws JMException, ClassNotFoundException {
      return this.createCertPathProvider(type, true, name);
   }

   public AuditorMBean createAuditor(String type) throws JMException, ClassNotFoundException {
      return this.createAuditor(type, false, (String)null);
   }

   public AuthenticationProviderMBean createAuthenticationProvider(String type) throws JMException, ClassNotFoundException {
      return this.createAuthenticationProvider(type, false, (String)null);
   }

   public RoleMapperMBean createRoleMapper(String type) throws JMException, ClassNotFoundException {
      return this.createRoleMapper(type, false, (String)null);
   }

   public AuthorizerMBean createAuthorizer(String type) throws JMException, ClassNotFoundException {
      return this.createAuthorizer(type, false, (String)null);
   }

   public AdjudicatorMBean createAdjudicator(String type) throws JMException, ClassNotFoundException {
      return this.createAdjudicator(type, false, (String)null);
   }

   public CredentialMapperMBean createCredentialMapper(String type) throws JMException, ClassNotFoundException {
      return this.createCredentialMapper(type, false, (String)null);
   }

   public CertPathProviderMBean createCertPathProvider(String type) throws JMException, ClassNotFoundException {
      return this.createCertPathProvider(type, false, (String)null);
   }

   public PasswordValidatorMBean createPasswordValidator(String name, String type) throws JMException, ClassNotFoundException {
      return this.createPasswordValidator(type, true, name);
   }

   public PasswordValidatorMBean createPasswordValidator(String type) throws JMException, ClassNotFoundException {
      return this.createPasswordValidator(type, false, (String)null);
   }

   private interface ProviderCreator {
      ProviderMBean createProvider(Class var1) throws JMException, ClassNotFoundException;

      ProviderMBean createProvider(Class var1, String var2) throws JMException, ClassNotFoundException;
   }
}
