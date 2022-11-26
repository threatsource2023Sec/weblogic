package weblogic.iiop.ior;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.RuntimeDescriptor;

public class IORBuilder {
   private static byte defaultGIOPMinorVersion = 2;
   private final String repositoryId;
   private final String host;
   private final int plainTextPort;
   private final int securePort;
   private byte[] key;
   private byte major = 1;
   private byte minor;
   private List taggedComponents;

   public static void setDefaultGIOPMinorVersion(byte defaultGIOPMinorVersion) {
      IORBuilder.defaultGIOPMinorVersion = defaultGIOPMinorVersion;
   }

   public static IORBuilder createBuilder(String repositoryId, String host, int port) {
      return new IORBuilder(repositoryId, host, port, -1);
   }

   public static IORBuilder createSecureBuilder(String repositoryId, String host, int port) {
      return new IORBuilder(repositoryId, host, -1, port);
   }

   protected IORBuilder(String repositoryId, String host, int plainTextPort, int securePort) {
      this.minor = defaultGIOPMinorVersion;
      this.taggedComponents = new ArrayList();
      this.host = host;
      this.repositoryId = repositoryId;
      this.plainTextPort = plainTextPort;
      this.securePort = securePort;
      this.addCodeSetComponent();
   }

   protected static void addAsyncComponent(IOPProfile iopProfile, RuntimeDescriptor runtimeDescriptor) {
      AsyncComponent component = createAsyncComponent(runtimeDescriptor);
      if (component != null) {
         iopProfile.addComponent(component);
      }

   }

   public IORBuilder addAsyncComponent(RuntimeDescriptor runtimeDescriptor) {
      AsyncComponent component = createAsyncComponent(runtimeDescriptor);
      if (component != null) {
         this.preAddComponent(component);
      }

      return this;
   }

   private static AsyncComponent createAsyncComponent(RuntimeDescriptor runtimeDescriptor) {
      if (runtimeDescriptor != null && runtimeDescriptor.getClientMethodDescriptors() != null) {
         AsyncComponent component = new AsyncComponent();
         Iterator var2 = runtimeDescriptor.getClientMethodDescriptors().values().iterator();

         while(var2.hasNext()) {
            ClientMethodDescriptor descriptor = (ClientMethodDescriptor)var2.next();
            if (descriptor.getAsynchronousResult()) {
               component.addAsyncSignature(descriptor.getSignature());
            }
         }

         return component.hasSignatures() ? component : null;
      } else {
         return null;
      }
   }

   public static void addTransactionComponents(IOPProfile iopProfile, RuntimeDescriptor runtimeDescriptor) {
      iopProfile.addComponent(TransactionPolicyComponent.EJB_INV_POLICY);
      if (runtimeDescriptor != null && runtimeDescriptor.getMethodsAreTransactional()) {
         iopProfile.addComponent(TransactionPolicyComponent.EJB_OTS_POLICY);
      }

   }

   private void addCodeSetComponent() {
      this.preAddComponent(CodeSetsComponent.getDefault());
   }

   public IORBuilder addClusterComponent(ClusterComponent clusterComponent) {
      if (clusterComponent != null) {
         this.preAddComponent(clusterComponent);
      }

      return this;
   }

   protected void preAddComponent(TaggedComponent component) {
      this.taggedComponents.add(component);
   }

   protected String getHost() {
      return this.host;
   }

   public IORBuilder setGiopVersion(byte major, byte minor) {
      this.major = major;
      this.minor = minor;
      return this;
   }

   public IORBuilder withMinorVersion(int minor) {
      this.minor = (byte)minor;
      return this;
   }

   protected boolean taggedComponentsSupported() {
      return this.major > 1 || this.major == 1 && this.minor > 0;
   }

   public IORBuilder setKey(byte[] key) {
      this.key = key;
      return this;
   }

   protected IOPProfile createProfile() {
      IOPProfile iopProfile = this.createIOPProfile();
      if (this.taggedComponentsSupported()) {
         this.addStandardComponents(iopProfile);
      }

      return iopProfile;
   }

   protected IOPProfile createIOPProfile() {
      return new IOPProfile(this.host, this.plainTextPort, this.key, this.major, this.minor);
   }

   protected void addStandardComponents(IOPProfile iopProfile) {
      this.setTaggedComponents(iopProfile);
   }

   private void setTaggedComponents(IOPProfile iopProfile) {
      Iterator var2 = this.taggedComponents.iterator();

      while(var2.hasNext()) {
         TaggedComponent taggedComponent = (TaggedComponent)var2.next();
         iopProfile.addComponent(taggedComponent);
      }

   }

   protected IOR createIOR(IOPProfile iopProfile) {
      if (this.securePort > -1) {
         this.setSecure(iopProfile);
      }

      return new IOR(this.repositoryId, iopProfile);
   }

   protected void setSecure(IOPProfile iopProfile) {
      iopProfile.setSecurePort(this.securePort);
   }

   public IOR createIOR() {
      return this.createIOR(this.createProfile());
   }
}
