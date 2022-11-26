package weblogic.connector.configuration.meta;

import javax.resource.spi.AuthenticationMechanism;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.SecurityPermission;
import javax.resource.spi.TransactionSupport;
import javax.resource.spi.AuthenticationMechanism.CredentialInterface;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.exception.RAException;
import weblogic.connector.utils.ArrayUtils;
import weblogic.connector.utils.ValidationMessage;
import weblogic.j2ee.descriptor.AuthenticationMechanismBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.IconBean;
import weblogic.j2ee.descriptor.LicenseBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.j2ee.descriptor.SecurityPermissionBean;

@AnnotationProcessorDescription(
   targetAnnotation = Connector.class
)
class ConnectorProcessor implements TypeAnnotationProcessor {
   private final ConnectorBean connectorBean;
   private final ConnectorBeanNavigator beanNavigator;
   private String resourceAdapterClassInDD = null;
   private boolean connectorProcessed = false;

   ConnectorProcessor(ConnectorBeanNavigator connectorBeanNav) {
      this.beanNavigator = connectorBeanNav;
      this.connectorBean = connectorBeanNav.getConnectorBean();
      this.initAdapterBeanIfSpecifiedInDD();
   }

   private void initAdapterBeanIfSpecifiedInDD() {
      ResourceAdapterBean resourceAdapter = this.beanNavigator.getOrCreateResourceAdapter();
      this.resourceAdapterClassInDD = resourceAdapter.getResourceAdapterClass();
   }

   public void processClass(Class clz, Connector connectorMeta) throws RAException {
      if (this.resourceAdapterClassInDD != null && !"".equals(this.resourceAdapterClassInDD) && !clz.getName().equals(this.resourceAdapterClassInDD)) {
         ConnectorLogger.logConnectorAnnotationIgnored(clz.getCanonicalName(), this.resourceAdapterClassInDD);
      } else {
         if (!ResourceAdapter.class.isAssignableFrom(clz)) {
            this.beanNavigator.context.warning(ValidationMessage.RAComplianceTextMsg.CONNECTOR_MUST_ON_ADATERBEN(clz.getName()));
         }

         if (this.connectorProcessed) {
            String adapterbeanPreFound = this.beanNavigator.getOrCreateResourceAdapter().getResourceAdapterClass();
            this.beanNavigator.context.error(ValidationMessage.RAComplianceTextMsg.MUST_SPECIFY_ADAPTERBEN_INDD(clz.getName() + "," + adapterbeanPreFound));
            return;
         }

         this.connectorProcessed = true;
         this.readDescription(connectorMeta);
         this.readDisplayName(connectorMeta);
         this.readIcons(connectorMeta);
         this.readVendorName(connectorMeta);
         this.readAdapterVersion(connectorMeta);
         this.readEisType(connectorMeta);
         this.connectorBean.setVersion("1.6");
         this.readLicense(connectorMeta);
         this.readAdapterBeanAttribute(clz, connectorMeta);
      }

   }

   private void readLicense(Connector connectorMeta) {
      if (!MetaUtils.isPropertySet(this.connectorBean, "License")) {
         LicenseBean license = this.connectorBean.createLicense();
         license.setDescriptions(connectorMeta.licenseDescription());
         license.setLicenseRequired(connectorMeta.licenseRequired());
      }

   }

   private void readVendorName(Connector connectorMeta) {
      String vendorName = connectorMeta.vendorName();
      if (vendorName != null && !MetaUtils.isPropertySet(this.connectorBean, "VendorName")) {
         this.connectorBean.setVendorName(vendorName);
      }

   }

   private void readAdapterBeanAttribute(Class clz, Connector connectorMeta) {
      ResourceAdapterBean adapterBean = this.beanNavigator.getOrCreateResourceAdapter();
      this.readInterface(clz, adapterBean);
      this.readAuthentication(connectorMeta);
      this.readReauthenticationSupport(connectorMeta);
      this.readTransaction(connectorMeta);
      this.readSecurityPermission(connectorMeta.securityPermissions());
      this.readRequiredWorkContexts(connectorMeta);
   }

   private void readRequiredWorkContexts(Connector connectorMeta) {
      ConnectorBean bean = this.beanNavigator.getConnectorBean();
      if (!MetaUtils.isPropertySet(bean, "RequiredWorkContexts")) {
         Class[] var3 = connectorMeta.requiredWorkContexts();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Class wc = var3[var5];
            if (!ArrayUtils.contains(bean.getRequiredWorkContexts(), wc.getName())) {
               bean.addRequiredWorkContext(wc.getName());
            }
         }
      }

   }

   void readSecurityPermission(SecurityPermission[] securityPermissions) {
      if (securityPermissions != null) {
         ResourceAdapterBean adapterBean = this.beanNavigator.getOrCreateResourceAdapter();
         SecurityPermission[] var3 = securityPermissions;
         int var4 = securityPermissions.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            SecurityPermission securityPermission = var3[var5];
            String spec = securityPermission.permissionSpec();
            if (!this.hasSecurityPermission(adapterBean, spec)) {
               SecurityPermissionBean permissionBean = adapterBean.createSecurityPermission();
               permissionBean.setSecurityPermissionSpec(spec);
               permissionBean.setDescriptions(securityPermission.description());
            }
         }

      }
   }

   private boolean hasSecurityPermission(ResourceAdapterBean adapterBean, String spec) {
      assert spec != null;

      SecurityPermissionBean[] permissions = adapterBean.getSecurityPermissions();
      return ArrayUtils.contains(permissions, spec, new ArrayUtils.KeyLocator() {
         public String getKey(SecurityPermissionBean entry) {
            return entry.getSecurityPermissionSpec();
         }
      });
   }

   void readAuthentication(Connector connectorMeta) {
      OutboundResourceAdapterBean outbound = this.beanNavigator.getOrCreateOutboundResourceAdapter();
      AuthenticationMechanism[] authMechanisms = connectorMeta.authMechanisms();
      AuthenticationMechanism[] var4 = authMechanisms;
      int var5 = authMechanisms.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         AuthenticationMechanism authenticationMechanism = var4[var6];
         AuthenticationMechanism.CredentialInterface intf = authenticationMechanism.credentialInterface();
         if (intf != null && !this.hasAuthenticationMechanism(outbound, intf.toString())) {
            AuthenticationMechanismBean mechanism = outbound.createAuthenticationMechanism();
            mechanism.setAuthenticationMechanismType(authenticationMechanism.authMechanism());
            if (CredentialInterface.PasswordCredential.equals(intf)) {
               mechanism.setCredentialInterface("javax.resource.spi.security.PasswordCredential");
            } else if (CredentialInterface.GenericCredential.equals(intf)) {
               mechanism.setCredentialInterface("javax.resource.spi.security.GenericCredential");
            } else if (CredentialInterface.GSSCredential.equals(intf)) {
               mechanism.setCredentialInterface("org.ietf.jgss.GSSCredential");
            }

            mechanism.setDescriptions(authenticationMechanism.description());
         }
      }

   }

   void readReauthenticationSupport(Connector connectorMeta) {
      OutboundResourceAdapterBean outbound = this.beanNavigator.getOrCreateOutboundResourceAdapter();
      if (!MetaUtils.isPropertySet(outbound, "ReauthenticationSupport")) {
         outbound.setReauthenticationSupport(connectorMeta.reauthenticationSupport());
      }

   }

   private boolean hasAuthenticationMechanism(OutboundResourceAdapterBean outbound, String intf) {
      assert intf != null;

      AuthenticationMechanismBean[] mechanisms = outbound.getAuthenticationMechanisms();
      return ArrayUtils.contains(mechanisms, intf, new ArrayUtils.KeyLocator() {
         public String getKey(AuthenticationMechanismBean entry) {
            return entry.getCredentialInterface();
         }
      });
   }

   void readTransaction(Connector connectorMeta) {
      OutboundResourceAdapterBean outbound = this.beanNavigator.getOrCreateOutboundResourceAdapter();
      if (!MetaUtils.isPropertySet(outbound, "TransactionSupport")) {
         TransactionSupport.TransactionSupportLevel transaction = connectorMeta.transactionSupport();
         outbound.setTransactionSupport(transaction.toString());
      }

   }

   private void readInterface(Class clz, ResourceAdapterBean adapterBean) {
      adapterBean.setResourceAdapterClass(clz.getName());
      this.beanNavigator.context.readPath("resourceadapter-class");
   }

   private void readEisType(Connector connectorMeta) {
      String eisType = connectorMeta.eisType();
      if (eisType != null && !MetaUtils.isPropertySet(this.connectorBean, "EisType")) {
         this.connectorBean.setEisType(eisType);
      }

   }

   private void readIcons(Connector connectorMeta) {
      if (!MetaUtils.isPropertySet(this.connectorBean, "Icons")) {
         String[] smallIcons = connectorMeta.smallIcon();
         String[] largeIcons = connectorMeta.largeIcon();
         if (smallIcons != null) {
            for(int i = 0; i < smallIcons.length; ++i) {
               IconBean iconBean = this.connectorBean.createIcon();
               iconBean.setSmallIcon(smallIcons[i]);
               this.beanNavigator.context.readPath("smallicon", smallIcons[i]);
               if (largeIcons != null && i < largeIcons.length) {
                  iconBean.setLargeIcon(largeIcons[i]);
                  this.beanNavigator.context.readPath("largeicon", largeIcons[i]);
               }
            }
         }
      }

   }

   private void readDisplayName(Connector connectorMeta) {
      String[] displayNames = connectorMeta.displayName();
      if (displayNames != null && !MetaUtils.isPropertySet(this.connectorBean, "DisplayNames")) {
         String[] var3 = displayNames;
         int var4 = displayNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String displayName = var3[var5];
            this.connectorBean.addDisplayName(displayName);
         }
      }

   }

   void readDescription(Connector connectorMeta) {
      String[] descriptions = connectorMeta.description();
      String[] var3 = descriptions;
      int var4 = descriptions.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String description = var3[var5];
         if (!ArrayUtils.contains(this.connectorBean.getDescriptions(), description)) {
            this.connectorBean.addDescription(description);
         }
      }

   }

   private void readAdapterVersion(Connector connectorMeta) {
      String version = connectorMeta.version();
      if (version != null && !MetaUtils.isPropertySet(this.connectorBean, "ResourceAdapterVersion")) {
         this.connectorBean.setResourceAdapterVersion(version);
      }

   }
}
