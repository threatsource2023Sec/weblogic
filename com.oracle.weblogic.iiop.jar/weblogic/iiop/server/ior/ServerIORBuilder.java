package weblogic.iiop.server.ior;

import weblogic.corba.policies.PolicyImpl;
import weblogic.iiop.CompoundSecMechListBuilder;
import weblogic.iiop.IIOPClientService;
import weblogic.iiop.IiopConfigurationFacade;
import weblogic.iiop.ObjectKey;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOPProfile;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORBuilder;
import weblogic.iiop.ior.SFVComponent;
import weblogic.iiop.ior.SSLSecTransComponent;
import weblogic.iiop.ior.TransactionPolicyComponent;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.kernel.Kernel;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.utils.collections.NumericKeyHashMap;

public class ServerIORBuilder extends IORBuilder {
   private String applicationName;
   private ObjectKey objectKey;

   public static ServerIORBuilder createBuilder(String typeId, String host, int plainTextPort) {
      return new ServerIORBuilder(typeId, host, plainTextPort, -1);
   }

   public static ServerIORBuilder createSecureBuilder(String typeId, String host, int securePort) {
      return new ServerIORBuilder(typeId, host, -1, securePort);
   }

   private ServerIORBuilder(String repositoryId, String host, int plainTextPort, int securePort) {
      super(repositoryId, host, plainTextPort, securePort);
   }

   protected void addStandardComponents(IOPProfile iopProfile) {
      super.addStandardComponents(iopProfile);
      this.addCodebaseComponent(iopProfile);
      this.advertiseSerializationVersion(iopProfile);
   }

   public void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public ServerIORBuilder setKey(ObjectKey objectKey) {
      this.objectKey = objectKey;
      return (ServerIORBuilder)this.setKey(objectKey.getBytes());
   }

   public ObjectKey getObjectKey() {
      return this.objectKey;
   }

   protected IOPProfile createIOPProfile() {
      IOPProfile iopProfile = super.createIOPProfile();
      iopProfile.setObjectKey(this.objectKey);
      return iopProfile;
   }

   public ServerIORBuilder addClusterComponent(ClusterComponent clusterComponent) {
      if (clusterComponent != null && this.useCustomComponents()) {
         this.preAddComponent(clusterComponent);
      }

      return this;
   }

   public IOR createWithRuntimeDescriptor(RuntimeDescriptor runtimeDescriptor) {
      IOPProfile iopProfile = this.createProfile();
      if (this.taggedComponentsSupported() && this.useCustomComponents()) {
         addAsyncComponent(iopProfile, runtimeDescriptor);
         addTransactionComponents(iopProfile, runtimeDescriptor);
         this.addCSIv2Component(iopProfile, runtimeDescriptor);
         if (this.isUnprotectedAccessDisabled(runtimeDescriptor)) {
            iopProfile.disablePlainPort();
         }
      }

      return this.createIOR(iopProfile);
   }

   private boolean useCustomComponents() {
      return Kernel.isServer() && this.getObjectKey() != null && this.getObjectKey().isWLSKey();
   }

   private void addCodebaseComponent(IOPProfile iopProfile) {
      if (this.getObjectKey() != null && this.useCustomComponents()) {
         iopProfile.addComponent(new ServerCodebaseComponent(this.getObjectKey().getTarget(), this.applicationName));
      }

   }

   private void advertiseSerializationVersion(IOPProfile iopProfile) {
      if (IIOPClientService.useSerialFormatVersion2) {
         iopProfile.addComponent(SFVComponent.VERSION_2);
      }

   }

   private boolean isUnprotectedAccessDisabled(RuntimeDescriptor runtimeDescriptor) {
      return runtimeDescriptor != null && runtimeDescriptor.getIntegrity() != null && "required".equals(runtimeDescriptor.getIntegrity());
   }

   protected void setSecure(IOPProfile iopProfile) {
      super.setSecure(iopProfile);
      if (IiopProtocolFacade.isServerLocalObject(this.objectKey)) {
         SSLSecTransComponent.initialize(IiopConfigurationFacade.getSslListenPort(), IiopConfigurationFacade.isClientCertificateEnforced(), IiopConfigurationFacade.getCipherSuites());
      }

   }

   private void addCSIv2Component(IOPProfile iopProfile, RuntimeDescriptor runtimeDescriptor) {
      if (OIDManager.requiresAuthentication(this.getObjectKey().getObjectID())) {
         if (RmiSecurityFacade.isSecurityServiceInitialized()) {
            iopProfile.addComponent(CompoundSecMechListBuilder.createCompoundSecMechList(this.getHost(), this.getObjectKey().getTarget(), runtimeDescriptor));
         }
      }
   }

   public IOR createWithPoaPolicies(NumericKeyHashMap poaPolicies) {
      IOPProfile iopProfile = this.createProfile();
      if (this.taggedComponentsSupported() && this.useCustomComponents()) {
         this.addTransactionComponents(iopProfile, poaPolicies);
         this.addCSIv2Component(iopProfile, (RuntimeDescriptor)null);
      }

      return this.createIOR(iopProfile);
   }

   private void addTransactionComponents(IOPProfile iopProfile, NumericKeyHashMap poaPolicies) {
      if (poaPolicies == null) {
         iopProfile.addComponent(TransactionPolicyComponent.EJB_INV_POLICY);
      } else {
         if (poaPolicies.get(55L) != null) {
            iopProfile.addComponent(TransactionPolicyComponent.getInvocationPolicy(this.getPolicyId(poaPolicies, 55)));
         }

         if (poaPolicies.get(56L) != null) {
            iopProfile.addComponent(TransactionPolicyComponent.getOTSPolicy(this.getPolicyId(poaPolicies, 56)));
         }
      }

   }

   private int getPolicyId(NumericKeyHashMap poaPolicies, int poaPolicyKey) {
      return ((PolicyImpl)poaPolicies.get((long)poaPolicyKey)).policy_value();
   }
}
