package weblogic.iiop.ior;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;
import weblogic.iiop.protocol.ListenPoint;
import weblogic.protocol.ServerIdentity;
import weblogic.utils.Hex;

public final class IOPProfile extends Profile {
   public static final int PORT_DISABLED = 0;
   private byte major;
   private byte minor;
   private String host;
   private InetAddress canonicalHost;
   private transient ListenPoint address = null;
   private int port;
   private boolean readSecurely = false;
   private boolean clusterable = false;
   private int securePort = -1;
   private byte[] key;
   private transient Object objectKey;
   private List taggedComponents;

   public void disablePlainPort() {
      this.port = 0;
   }

   private boolean taggedComponentsSupported() {
      return this.major > 1 || this.major == 1 && this.minor > 0;
   }

   public void addComponent(TaggedComponent component) {
      if (this.taggedComponents == null) {
         this.taggedComponents = new ArrayList();
      }

      this.taggedComponents.add(component);
   }

   public IOPProfile(String host, int port, byte[] key, byte major, byte minor) {
      super(0);
      this.host = host;
      this.port = port;
      this.key = key;
      this.major = major;
      this.minor = minor;
   }

   IOPProfile() {
      super(0);
   }

   public IOPProfile(IOPProfile other) {
      super(0);
      this.major = other.major;
      this.minor = other.minor;
      this.host = other.host;
      this.port = other.port;
      this.readSecurely = other.readSecurely;
      this.securePort = other.securePort;
      this.key = other.key;
      this.taggedComponents = other.taggedComponents == null ? null : new ArrayList(other.taggedComponents);
   }

   public void setClusterComponent(ClusterComponent clusterComponent) {
      this.removeExistingClusterComponents();
      if (clusterComponent != null) {
         this.addComponent(clusterComponent);
      }

   }

   private void removeExistingClusterComponents() {
      Iterator each = this.taggedComponents.iterator();

      while(each.hasNext()) {
         if (each.next() instanceof ClusterComponent) {
            each.remove();
         }
      }

   }

   public final String getHost() {
      return this.host;
   }

   public final InetAddress getHostAddress() throws UnknownHostException {
      if (this.canonicalHost == null) {
         this.canonicalHost = InetAddressHelper.getByName(this.host);
      }

      return this.canonicalHost;
   }

   final boolean isSecure() {
      return (this.readSecurely() || this.getPort() <= 0) && this.getSecurePort() > 0;
   }

   public final void makeSecure() {
      if (this.getSecurePort() > 0 && this.getPort() > 0) {
         this.port = 0;
      }

   }

   final ListenPoint getListenPoint() {
      if (this.address == null) {
         try {
            if (this.isSecure()) {
               String host = this.getSecureHost() == null ? this.getHost() : this.getSecureHost();
               this.address = new ListenPoint(InetAddressHelper.getByName(host).getHostAddress(), this.getSecurePort());
            } else {
               this.address = new ListenPoint(this.getHostAddress().getHostAddress(), this.getPort());
            }
         } catch (UnknownHostException var3) {
            if (this.isSecure()) {
               String host = this.getSecureHost() == null ? this.getHost() : this.getSecureHost();
               this.address = new ListenPoint(host, this.getSecurePort());
            } else {
               this.address = new ListenPoint(this.getHost(), this.getPort());
            }
         }
      }

      return this.address;
   }

   public final int getPort() {
      return this.port;
   }

   public final int getSecurePort() {
      if (this.securePort < 0) {
         TaggedComponent tc = this.getComponent(33);
         if (tc != null) {
            this.securePort = ((CompoundSecMechList)tc).getSecurePort();
         }

         if (this.securePort < 0) {
            SSLSecTransComponent ssl = (SSLSecTransComponent)this.getComponent(20);
            if (ssl != null) {
               this.securePort = ssl.getPort();
            }
         }
      }

      return this.securePort;
   }

   public void setSecurePort(int securePort) {
      this.securePort = securePort;
   }

   public final String getSecureHost() {
      TaggedComponent tc = this.getComponent(33);
      return tc != null ? ((CompoundSecMechList)tc).getSecureHost() : null;
   }

   private boolean readSecurely() {
      return this.readSecurely;
   }

   public final byte getMinorVersion() {
      return this.minor;
   }

   public byte[] getKey() {
      return this.key;
   }

   public void setObjectKey(Object objectKey) {
      this.objectKey = objectKey;
   }

   public final Object getObjectKey() {
      if (this.objectKey == null) {
         synchronized(this) {
            if (this.objectKey == null) {
               this.objectKey = IiopProtocolFacade.toObjectKey(this.key);
            }
         }
      }

      return this.objectKey;
   }

   public final boolean isTransactional() {
      TransactionPolicyComponent tx = (TransactionPolicyComponent)this.getComponent(31);
      return tx != null && tx.getPolicy() != 2;
   }

   public final boolean isClusterable() {
      if (!this.clusterable && this.getComponent(1111834883) != null) {
         this.clusterable = true;
      }

      return this.clusterable;
   }

   public final TaggedComponent getComponent(int tag) {
      if (this.taggedComponents != null) {
         Iterator var2 = this.taggedComponents.iterator();

         while(var2.hasNext()) {
            TaggedComponent component = (TaggedComponent)var2.next();
            if (component.tag == tag) {
               return component;
            }
         }
      }

      return null;
   }

   public final boolean useSAS() {
      TaggedComponent tc = this.getComponent(33);
      return tc != null && ((CompoundSecMechList)tc).useSAS();
   }

   public byte getMaxStreamFormatVersion() {
      SFVComponent sfv = (SFVComponent)this.getComponent(38);
      return sfv == null ? 1 : sfv.getMaxFormatVersion();
   }

   public void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      if (in.isSecure()) {
         this.readSecurely = true;
      }

      this.major = in.read_octet();
      this.minor = in.read_octet();
      ListenPoint addr = new ListenPoint(in);
      this.key = in.read_octet_sequence(1048576);
      this.objectKey = IiopProtocolFacade.toObjectKey(this.key);
      if (IiopProtocolFacade.mustReplaceAddress(this.objectKey)) {
         addr = addr.replaceFromChannel(in);
      }

      this.host = addr.getAddress();
      this.port = addr.getPort();
      if (this.taggedComponentsSupported()) {
         ServerIdentity target = IiopProtocolFacade.getTargetForRead(this.objectKey);
         long size = (long)in.read_long();
         this.taggedComponents = new ArrayList();

         for(int i = 0; (long)i < size; ++i) {
            TaggedComponent taggedComponent = TaggedComponent.readComponent(in, target);
            this.taggedComponents.add(taggedComponent);
            if (taggedComponent.getTag() == 1111834883) {
               this.clusterable = true;
            }
         }
      }

      in.endEncapsulation(handle);
   }

   public void write(CorbaOutputStream out) {
      out.write_long(0);
      long handle = out.startEncapsulation();
      out.write_octet(this.major);
      out.write_octet(this.minor);
      IiopProtocolFacade.writeListenPoint(out, this.getObjectKey(), new ListenPoint(this.host, this.port));
      out.write_octet_sequence(this.key);
      if (this.taggedComponentsSupported()) {
         if (this.needToAddSSLSecTransComponent(out)) {
            out.write_long(this.taggedComponents.size() + 1);
         } else {
            out.write_long(this.taggedComponents.size());
         }

         Iterator var4 = this.taggedComponents.iterator();

         while(var4.hasNext()) {
            TaggedComponent taggedComponent = (TaggedComponent)var4.next();
            taggedComponent.write(out);
         }

         if (this.needToAddSSLSecTransComponent(out)) {
            SSLSecTransComponent.getSingleton().write(out);
         }
      }

      out.endEncapsulation(handle);
   }

   private boolean needToAddSSLSecTransComponent(CorbaOutputStream out) {
      return out.isSecure() && IiopProtocolFacade.isServerLocalObject(this.getObjectKey());
   }

   public final int hashCode() {
      return this.port ^ this.host.hashCode() ^ Arrays.hashCode(this.key);
   }

   public final boolean equals(Object o) {
      return this == o || o instanceof IOPProfile && this.equals((IOPProfile)o);
   }

   private boolean equals(IOPProfile other) {
      return IiopProtocolFacade.fastEquals(this.getObjectKey(), other.getObjectKey()) || this.standardEquals(other);
   }

   private boolean standardEquals(IOPProfile other) {
      return this.port == other.port && Objects.equals(this.host, other.host) && Objects.deepEquals(this.key, other.key);
   }

   public String toString() {
      String str = "IOP Profile (ver = " + this.major + "." + this.minor + ", host = " + this.host + ",port = " + this.port + ",key = " + Hex.asHex(this.key) + ", \ntaggedComponents = ";
      TaggedComponent taggedComponent;
      if (this.taggedComponents != null) {
         for(Iterator var2 = this.taggedComponents.iterator(); var2.hasNext(); str = str + '\n' + taggedComponent) {
            taggedComponent = (TaggedComponent)var2.next();
         }
      }

      return str + ")";
   }
}
