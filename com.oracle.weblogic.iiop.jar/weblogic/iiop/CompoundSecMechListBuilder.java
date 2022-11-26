package weblogic.iiop;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;
import weblogic.iiop.ior.ASContextSec;
import weblogic.iiop.ior.CompoundSecMech;
import weblogic.iiop.ior.CompoundSecMechList;
import weblogic.iiop.ior.RequirementType;
import weblogic.iiop.ior.SASContextSec;
import weblogic.iiop.ior.TLSSecTransComponent;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.kernel.Kernel;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;

public class CompoundSecMechListBuilder {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static CompoundSecMechList createCompoundSecMechList(String host, ServerIdentity target, RuntimeDescriptor runtimeDescriptor) {
      return new CompoundSecMechList(useStatefulAuthentication(runtimeDescriptor), createCompSecMechs(host, target, runtimeDescriptor));
   }

   private static boolean useStatefulAuthentication(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor != null ? runtimeDescriptor.getStatefulAuthentication() : useStatefulAuthenticationForIiop();
   }

   private static boolean useStatefulAuthenticationForIiop() {
      return Kernel.getConfig().getIIOP().getUseStatefulAuthentication();
   }

   private static CompoundSecMech[] createCompSecMechs(String host, ServerIdentity target, RuntimeDescriptor runtimeDescriptor) {
      List compoundSecMechs = new ArrayList();
      if (!isPlainPortDisabled(runtimeDescriptor)) {
         compoundSecMechs.add(createPlainPortSecurityMechanism(runtimeDescriptor));
      }

      if (isSslEnabled(runtimeDescriptor)) {
         compoundSecMechs.add(createSslSecurityMechanism(host, target, runtimeDescriptor));
      }

      return (CompoundSecMech[])compoundSecMechs.toArray(new CompoundSecMech[compoundSecMechs.size()]);
   }

   private static boolean isPlainPortDisabled(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor != null && "required".equals(runtimeDescriptor.getIntegrity());
   }

   private static boolean isSslEnabled(RuntimeDescriptor runtimeDescriptor) {
      return !sslDisabledForInstance(runtimeDescriptor) && IiopConfigurationFacade.isSslChannelEnabled();
   }

   private static boolean sslDisabledForInstance(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor != null && "none".equals(runtimeDescriptor.getIntegrity());
   }

   private static CompoundSecMech createSslSecurityMechanism(String host, ServerIdentity target, RuntimeDescriptor runtimeDescriptor) {
      CompoundSecMech csm = createPlainPortSecurityMechanism(runtimeDescriptor);
      csm.addTransportMech(createTLSSecTransComponent(host, target, runtimeDescriptor), isPlainPortDisabled(runtimeDescriptor));
      return csm;
   }

   private static CompoundSecMech createPlainPortSecurityMechanism(RuntimeDescriptor runtimeDescriptor) {
      return new CompoundSecMech(createASContextSec(getAuthenticationRequirement(runtimeDescriptor)), createSASContextSec(getIdentityAssertionRequirement(runtimeDescriptor)));
   }

   private static RequirementType getAuthenticationRequirement(RuntimeDescriptor runtimeDescriptor) {
      String authenticationSetting = getClientAuthenticationSetting(runtimeDescriptor);
      if ("none".equals(authenticationSetting)) {
         return RequirementType.NONE;
      } else {
         return "required".equals(authenticationSetting) ? RequirementType.REQUIRED : RequirementType.SUPPORTED;
      }
   }

   private static String getClientAuthenticationSetting(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor == null ? null : runtimeDescriptor.getClientAuthentication();
   }

   private static RequirementType getIdentityAssertionRequirement(RuntimeDescriptor runtimeDescriptor) {
      return "none".equals(getIdentityAssertionSetting(runtimeDescriptor)) ? RequirementType.NONE : RequirementType.SUPPORTED;
   }

   private static String getIdentityAssertionSetting(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor == null ? null : runtimeDescriptor.getIdentityAssertion();
   }

   static ASContextSec createASContextSec(RequirementType authenticationRequirement) {
      return new ASContextSec(authenticationRequirement, RmiSecurityFacade.getSecurityRealmName(KERNEL_ID));
   }

   static SASContextSec createSASContextSec(RequirementType assertionRequirement) {
      return new SASContextSec(assertionRequirement, getSupportedIdentityTypes());
   }

   private static int getSupportedIdentityTypes() {
      int supportedTypes = 0;
      PrincipalAuthenticator pa = RmiSecurityFacade.getPrincipalAuthenticator(KERNEL_ID, RmiSecurityFacade.getDefaultRealm());
      if (pa.isTokenTypeSupported("CSI.ITTAnonymous")) {
         supportedTypes |= 1;
      }

      if (pa.isTokenTypeSupported("CSI.PrincipalName")) {
         supportedTypes |= 2;
      }

      if (pa.isTokenTypeSupported("CSI.X509CertChain")) {
         supportedTypes |= 4;
      }

      if (pa.isTokenTypeSupported("CSI.DistinguishedName")) {
         supportedTypes |= 8;
      }

      return supportedTypes;
   }

   static TLSSecTransComponent createTLSSecTransComponent(String host, ServerIdentity target, RuntimeDescriptor runtimeDescriptor) {
      ListenPoint listenPoint = createTLSListenPoint(host, IiopConfigurationFacade.getLocalServerChannel(ProtocolHandlerIIOPS.PROTOCOL_IIOPS));
      RequirementType certificateAuthentication = getCertificateAuthenticationType(runtimeDescriptor, IiopConfigurationFacade.getLocalServerChannel(ProtocolHandlerIIOPS.PROTOCOL_IIOPS));
      String[] cipherSuites = getCipherSuiteNames();
      boolean confidentialityRequired = isConfidentialityRequired(runtimeDescriptor);
      return new TLSSecTransComponent(listenPoint, target, certificateAuthentication, confidentialityRequired, cipherSuites);
   }

   private static String[] getCipherSuiteNames() {
      ServerChannel sc = IiopConfigurationFacade.getLocalServerChannel(ProtocolHandlerIIOPS.PROTOCOL_IIOPS);
      return sc != null ? sc.getCiphersuites() : IiopConfigurationFacade.getCipherSuites();
   }

   private static boolean isConfidentialityRequired(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor != null && "required".equals(runtimeDescriptor.getConfidentiality());
   }

   private static RequirementType getCertificateAuthenticationType(RuntimeDescriptor runtimeDescriptor, ServerChannel sc) {
      RequirementType certificateAuthentication;
      label23: {
         certificateAuthentication = RequirementType.NONE;
         if (sc != null) {
            if (!sc.isClientCertificateEnforced()) {
               break label23;
            }
         } else if (!IiopConfigurationFacade.isClientCertificateEnforced()) {
            break label23;
         }

         certificateAuthentication = RequirementType.REQUIRED;
      }

      if (runtimeDescriptor != null) {
         String clientCert = runtimeDescriptor.getClientCertAuthentication();
         if ("supported".equals(clientCert)) {
            certificateAuthentication = RequirementType.SUPPORTED;
         } else if ("required".equals(clientCert)) {
            certificateAuthentication = RequirementType.REQUIRED;
         }
      }

      return certificateAuthentication;
   }

   private static ListenPoint createTLSListenPoint(String host, ServerChannel sc) {
      if (host == null) {
         return createListenPoint(sc.getPublicAddress(), sc.getPublicPort());
      } else {
         return sc == null ? createListenPoint(host, IiopConfigurationFacade.getSslListenPort()) : createListenPoint(host, sc.getPublicPort());
      }
   }

   private static ListenPoint createListenPoint(String address, int port) {
      return new ListenPoint(address, port);
   }

   static CompoundSecMech createCompoundSecMech(boolean haveSSL, String host, ServerIdentity target, RuntimeDescriptor runtimeDescriptor) {
      CompoundSecMech csm = createPlainPortSecurityMechanism(runtimeDescriptor);
      if (haveSSL) {
         csm.addTransportMech(createTLSSecTransComponent(host, target, runtimeDescriptor), isPlainPortDisabled(runtimeDescriptor));
      }

      return csm;
   }
}
