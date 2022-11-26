package weblogic.iiop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.HashMap;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosTransactions.TransactionFactoryHelper;
import org.omg.SendingContext.CodeBaseHelper;
import weblogic.corba.utils.MarshaledString;
import weblogic.corba.utils.RepositoryId;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.TaggedComponent;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.server.InitialReferences;
import weblogic.kernel.Kernel;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.Identity;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerIdentityManager;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RemoteType;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.Hex;
import weblogic.utils.StringUtils;

public class ObjectKey {
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static final boolean USE_BIG_ENDIAN = false;
   private static final int CHAR_SET = 83951617;
   private static final int WCHAR_SET = 65801;
   private static final int VENDOR_OFFSET_1 = 1;
   private static final int VENDOR_OFFSET_2 = 2;
   private static final int VENDOR_OFFSET_3 = 3;
   private static final int ICEBERG_OFFSET = 4;
   private static final int HEADER_SIZE = 4;
   private static final int VERSION_MAJOR_OFFSET = 5;
   private int keyType;
   private int oid;
   private int keyLength;
   private transient int numComponents;
   private Object activationID;
   private IIOPInputStream activationData;
   private boolean foreignKey;
   private byte[] key_data;
   private ServerIdentity target;
   private Identity identity;
   private static final String BOOTSTRAP_NAME_SERVICE = "INIT";
   static final String NAME_SERVICE = "NameService";
   private static final String BEA_NAME_SERVICE = "BEA:NameService:Root";
   static final ObjectKey BOOTSTRAP_KEY = new ObjectKey("INIT");
   static final ObjectKey NAME_SERVICE_KEY = new ObjectKey("NameService");
   private byte[] preMarshalled;
   private static HashMap typeIdMap = new HashMap();
   private static final int ENCAPKEYHEADERSIZE = 8;
   private static final int KEYTYPE_ICEBERG = 8;
   private static final int TAG_OBJKEY_HASH = 1111834886;
   private static final int TAG_CLNT_ROUTE_INFO = 1111834884;
   private static final int TAG_WLS_INITIAL_REF = 1111834888;
   private static final int TAG_WLS_TRANSIENT_REF_51 = 1111834890;
   private static final int TAG_WLS_TRANSIENT_REF_61 = 1111834887;
   private static final int TAG_WLS_OBJ_INFO = 1111834891;
   private static final int TAG_WLS_ACTIVATABLE_REF = 1111834897;
   private static final int TAG_WLS_ACTIVATABLE_REF_81 = 1111834896;
   private static final int TAG_WLS_TRANSIENT_REF = 1111834926;
   private static final int KEY_MAJOR_VERSION = 1;
   private static final int KEY_MINOR_VERSION = 3;
   private static final int WLS_OA_ID = 0;
   private static MarshaledString localDomainId;
   private int wleVersionMajor;
   private int wleVersionMinor;
   private RepositoryId interfaceName;
   private MarshaledString wleDomainId;
   private int wleGroupId;
   private String wleObjectId;
   private int wleObjectAdapter;
   private int wleScaInterfaceBucket;
   private transient int numForeignComponents;
   private TaggedComponent[] foreignComponents;
   private String[] remoteInterfaces;
   private boolean isRepIdAnInterface;
   private transient boolean writeObjInfo;
   private transient boolean computeRepId;

   public static ObjectKey create(byte[] representation) {
      ObjectKey key = new ObjectKey();
      key.parse(representation);
      return key;
   }

   public ObjectKey() {
      this.keyType = 0;
      this.oid = 0;
      this.keyLength = 0;
      this.numComponents = 0;
      this.foreignKey = false;
      this.wleVersionMajor = 1;
      this.wleVersionMinor = 3;
      this.interfaceName = RepositoryId.EMPTY;
      this.wleGroupId = 0;
      this.wleObjectId = "";
      this.wleObjectAdapter = 0;
      this.wleScaInterfaceBucket = -1;
      this.numForeignComponents = 0;
      this.remoteInterfaces = null;
      this.isRepIdAnInterface = true;
      this.writeObjInfo = false;
      this.computeRepId = true;
   }

   public ObjectKey(CorbaInputStream is) {
      this.keyType = 0;
      this.oid = 0;
      this.keyLength = 0;
      this.numComponents = 0;
      this.foreignKey = false;
      this.wleVersionMajor = 1;
      this.wleVersionMinor = 3;
      this.interfaceName = RepositoryId.EMPTY;
      this.wleGroupId = 0;
      this.wleObjectId = "";
      this.wleObjectAdapter = 0;
      this.wleScaInterfaceBucket = -1;
      this.numForeignComponents = 0;
      this.remoteInterfaces = null;
      this.isRepIdAnInterface = true;
      this.writeObjInfo = false;
      this.computeRepId = true;
      this.read((IIOPInputStream)is);
   }

   private void read(IIOPInputStream in) {
      byte[] bytes = in.read_octet_sequence(1048576);
      this.parse(bytes);
   }

   public ObjectKey(byte[] foreign) {
      this.keyType = 0;
      this.oid = 0;
      this.keyLength = 0;
      this.numComponents = 0;
      this.foreignKey = false;
      this.wleVersionMajor = 1;
      this.wleVersionMinor = 3;
      this.interfaceName = RepositoryId.EMPTY;
      this.wleGroupId = 0;
      this.wleObjectId = "";
      this.wleObjectAdapter = 0;
      this.wleScaInterfaceBucket = -1;
      this.numForeignComponents = 0;
      this.remoteInterfaces = null;
      this.isRepIdAnInterface = true;
      this.writeObjInfo = false;
      this.computeRepId = true;
      this.key_data = foreign;
      this.foreignKey = true;
      this.keyLength = this.key_data.length;
   }

   private ObjectKey(String bootstrap) {
      this.keyType = 0;
      this.oid = 0;
      this.keyLength = 0;
      this.numComponents = 0;
      this.foreignKey = false;
      this.wleVersionMajor = 1;
      this.wleVersionMinor = 3;
      this.interfaceName = RepositoryId.EMPTY;
      this.wleGroupId = 0;
      this.wleObjectId = "";
      this.wleObjectAdapter = 0;
      this.wleScaInterfaceBucket = -1;
      this.numForeignComponents = 0;
      this.remoteInterfaces = null;
      this.isRepIdAnInterface = true;
      this.writeObjInfo = false;
      this.computeRepId = true;
      this.keyLength = bootstrap.length();
      this.key_data = new byte[this.keyLength];
      bootstrap.getBytes(0, this.keyLength, this.key_data, 0);
      this.foreignKey = true;
   }

   private ObjectKey(String interfaceName, int oid, ServerIdentity target) {
      this.keyType = 0;
      this.oid = 0;
      this.keyLength = 0;
      this.numComponents = 0;
      this.foreignKey = false;
      this.wleVersionMajor = 1;
      this.wleVersionMinor = 3;
      this.interfaceName = RepositoryId.EMPTY;
      this.wleGroupId = 0;
      this.wleObjectId = "";
      this.wleObjectAdapter = 0;
      this.wleScaInterfaceBucket = -1;
      this.numForeignComponents = 0;
      this.remoteInterfaces = null;
      this.isRepIdAnInterface = true;
      this.writeObjInfo = false;
      this.computeRepId = true;
      this.interfaceName = new RepositoryId(interfaceName);
      this.oid = oid;
      this.keyType = 1111834926;
      this.target = target;
      this.identity = target.getTransientIdentity();
      if (target.getServerName() == null) {
         this.wleDomainId = MarshaledString.EMPTY;
      } else if (target.isLocal()) {
         this.wleDomainId = localDomainId;
      } else {
         this.wleDomainId = new MarshaledString(target.getServerName());
      }

      this.wleGroupId = 0;
      this.numComponents = 1;
      this.checkObjInfoRequiredToBeMarshalled();
   }

   private ObjectKey(String interfaceName, int oid) {
      this(interfaceName, oid, LocalServerIdentity.getIdentity());
   }

   private ObjectKey(String interfaceName, int oid, Object aid) {
      this.keyType = 0;
      this.oid = 0;
      this.keyLength = 0;
      this.numComponents = 0;
      this.foreignKey = false;
      this.wleVersionMajor = 1;
      this.wleVersionMinor = 3;
      this.interfaceName = RepositoryId.EMPTY;
      this.wleGroupId = 0;
      this.wleObjectId = "";
      this.wleObjectAdapter = 0;
      this.wleScaInterfaceBucket = -1;
      this.numForeignComponents = 0;
      this.remoteInterfaces = null;
      this.isRepIdAnInterface = true;
      this.writeObjInfo = false;
      this.computeRepId = true;
      this.interfaceName = new RepositoryId(interfaceName);
      this.oid = oid;
      this.activationID = aid;
      Debug.assertion(aid != null);
      this.keyType = 1111834897;
      this.target = LocalServerIdentity.getIdentity();
      this.identity = this.target.getPersistentIdentity();
      this.wleDomainId = localDomainId;
      this.wleGroupId = 0;
      this.numComponents = 1;
      this.checkObjInfoRequiredToBeMarshalled();
   }

   public static ObjectKey getObjectKey(IOR ior) {
      return (ObjectKey)ior.getProfile().getObjectKey();
   }

   public static MarshaledString getLocalDomainID() {
      return localDomainId;
   }

   private static MarshaledString getLocalDomainIDInternal() {
      if (Kernel.isServer()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         return new MarshaledString(ManagementService.getRuntimeAccess(kernelId).getServerName());
      } else {
         return MarshaledString.EMPTY;
      }
   }

   static void p(String s) {
      System.err.println("<ObjectKey> " + s);
   }

   public static ObjectKey createActivatableObjectKey(String interfaceName, int oid, Object aid) {
      return new ObjectKey(interfaceName, oid, aid);
   }

   public static ObjectKey createTransientObjectKey(String interfaceName, int oid) {
      return new ObjectKey(interfaceName, oid);
   }

   public static ObjectKey createTransientObjectKey(String interfaceName, int oid, ServerIdentity target) {
      return new ObjectKey(interfaceName, oid, target);
   }

   public final String getInterfaceName() {
      return this.interfaceName.toString();
   }

   protected final void setInterfaceName(String interfaceName) {
      this.preMarshalled = null;
      this.interfaceName = new RepositoryId(interfaceName);
   }

   public final int getObjectID() {
      return this.oid;
   }

   public MarshaledString getWLEDomainId() {
      return this.wleDomainId;
   }

   protected final void setWLEDomainId(String did) {
      this.preMarshalled = null;
      this.wleDomainId = new MarshaledString(did);
   }

   protected final void setWLEObjectId(String wleObjectId) {
      this.preMarshalled = null;
      this.wleObjectId = wleObjectId;
   }

   protected final void setWLEObjectAdapter(int wleObjectAdapter) {
      this.preMarshalled = null;
      this.wleObjectAdapter = wleObjectAdapter;
   }

   public boolean isWLEKey() {
      return this.target == null && !this.foreignKey && this.wleObjectAdapter >= 0;
   }

   public final boolean isWLSKey() {
      return this.identity != null && this.target != null;
   }

   private boolean isForeignKey() {
      return this.foreignKey || this.identity == null && this.wleObjectAdapter == 0;
   }

   public final boolean isBootstrapKey() {
      return this.foreignKey && this.key_data != null && this.key_data.length == 4 && (this.key_data[0] == 73 && this.key_data[1] == 78 && this.key_data[2] == 73 && this.key_data[3] == 84 || this.key_data[0] == 84 && this.key_data[1] == 73 && this.key_data[2] == 78 && this.key_data[3] == 73);
   }

   public final boolean isNamingKey() {
      return this.key_data != null && this.key_data.length < 128 && (new String(this.key_data)).equals("NameService") || this.oid == 8;
   }

   public final IOR getInitialReference() {
      return this.key_data != null && this.key_data.length < 128 ? InitialReferences.getInitialReference(new String(this.key_data)) : null;
   }

   public final boolean isLocalKey() {
      return this.isWLSKey() && this.target != null && this.target.isLocal();
   }

   public final Object getActivationID() {
      if (this.activationID == null && this.activationData != null) {
         synchronized(this) {
            if (this.activationData != null) {
               this.activationID = this.activationData.read_value();
               this.activationData.close();
               this.activationData = null;
            }
         }
      }

      return this.activationID;
   }

   public final ServerIdentity getTarget() {
      return this.target;
   }

   public final Identity getIdentity() {
      return this.identity;
   }

   public static String getTypeId(Object internalId) {
      String[] id = (String[])((String[])typeIdMap.get(internalId));
      return id != null ? id[0] : null;
   }

   protected final void setKeyType(int keyType) {
      this.preMarshalled = null;
      this.keyType = keyType;
   }

   public static ObjectKey getBootstrapKey(String name) {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("getBootstrapKey(" + name + ")");
      }

      String[] id = (String[])((String[])typeIdMap.get(name));
      byte[] data;
      if (id != null) {
         data = new byte[id[1].length()];
         id[1].getBytes(0, id[1].length(), data, 0);
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
            p("getBootstrapKey(" + name + ") = " + id[1]);
         }

         return new ObjectKey(data);
      } else {
         data = new byte[name.length()];
         name.getBytes(0, name.length(), data, 0);
         return new ObjectKey(data);
      }
   }

   public Identity getTransientIdentity() {
      return this.identity;
   }

   private void writeVendor(IIOPOutputStream out) {
      byte[] vendor = new byte[]{66, 69, 65};
      out.write_octet_array(vendor, 0, vendor.length);
   }

   private void parse(byte[] bytes) {
      if (!this.isWlsKeyRepresentation(bytes)) {
         this.foreignKey = true;
         this.key_data = bytes;
      } else {
         IIOPInputStream in = new IIOPInputStream(bytes);
         in.setCodeSets(83951617, 65801);
         this.readWlsKey(in);
      }

   }

   private void readWlsKey(IIOPInputStream in) {
      this.preMarshalled = null;
      in.consumeEndian();
      in.skip(4L);
      this.wleVersionMajor = in.read_octet();
      this.wleVersionMinor = in.read_octet();
      this.wleObjectAdapter = in.read_octet();
      this.wleDomainId = new MarshaledString(in);
      this.wleGroupId = in.read_long();
      this.interfaceName = in.read_repository_id();

      try {
         this.oid = in.read_numeric_string();
      } catch (NumberFormatException var16) {
         this.oid = 0;
         this.wleObjectId = var16.getMessage();
         String[] mappedid = (String[])((String[])typeIdMap.get(this.wleObjectId));
         if (mappedid != null && mappedid[1].equals("NameService")) {
            this.oid = 8;
         } else {
            this.oid = 0;
         }
      }

      if (this.wleVersionMinor > 1 && this.wleVersionMajor == 1) {
         this.numComponents = in.read_long();

         for(int ncomp = 0; ncomp < this.numComponents; ++ncomp) {
            int keycompid = in.read_long();
            long encaphandle;
            switch (keycompid) {
               case 1111834884:
               case 1111834886:
               default:
                  if (this.foreignComponents == null) {
                     this.foreignComponents = new TaggedComponent[this.numComponents];
                  }

                  this.foreignComponents[this.numForeignComponents++] = new TaggedComponent(keycompid, in);
                  break;
               case 1111834888:
                  this.keyType = 1111834888;
                  in.read_long();
                  this.target = LocalServerIdentity.getIdentity();
                  break;
               case 1111834891:
                  encaphandle = in.startEncapsulation();
                  ByteArrayInputStream bais = null;
                  DataInputStream dis = null;

                  try {
                     byte[] data = in.read_octet_sequence();
                     bais = new ByteArrayInputStream(data);
                     dis = new DataInputStream(bais);
                     this.isRepIdAnInterface = dis.readBoolean();
                     String interfaces = dis.readUTF();
                     if (interfaces.length() > 0) {
                        this.remoteInterfaces = StringUtils.splitCompletely(interfaces, ":");
                     }
                  } catch (Exception var14) {
                  } finally {
                     if (dis != null) {
                        this.close(dis);
                     }

                  }

                  in.endEncapsulation(encaphandle);
                  this.writeObjInfo = true;
                  this.computeRepId = false;
                  break;
               case 1111834897:
                  this.keyType = 1111834897;
                  this.activationData = new IIOPInputStream(in);
                  this.identity = Identity.read(this.activationData);
                  this.target = ServerIdentityManager.findServerIdentityFromPersistent(this.identity);
                  break;
               case 1111834926:
                  this.keyType = 1111834926;
                  encaphandle = in.startEncapsulation();
                  this.identity = Identity.read(in);
                  this.target = ServerIdentityManager.findServerIdentityFromTransient(this.identity);
                  in.endEncapsulation(encaphandle);
            }
         }
      }

   }

   private boolean isWlsKeyRepresentation(byte[] bytes) {
      return bytes.length >= 8 && this.hasHeader(bytes) && bytes[5] <= 1;
   }

   private boolean hasHeader(byte[] bytes) {
      return bytes[1] == 66 && bytes[2] == 69 && bytes[3] == 65 && bytes[4] == 8;
   }

   public byte[] getBytes() {
      if (this.foreignKey) {
         return this.key_data;
      } else {
         if (this.preMarshalled == null) {
            IIOPOutputStream tmp = new IIOPOutputStream(false, (EndPoint)null);
            tmp.setCodeSets(83951617, 65801);
            this.writeEncapsulation(tmp);
            this.preMarshalled = tmp.getBuffer();
            tmp.close();
         }

         return this.preMarshalled;
      }
   }

   private void writeEncapsulation(IIOPOutputStream out) {
      out.putEndian();
      this.writeVendor(out);
      out.write_octet((byte)8);
      out.write_octet((byte)this.wleVersionMajor);
      out.write_octet((byte)this.wleVersionMinor);
      out.write_octet((byte)this.wleObjectAdapter);
      this.wleDomainId.write(out);
      out.write_long(this.wleGroupId);
      out.write_repository_id(this.interfaceName);
      if (this.oid == 0 || this.wleObjectId != null && this.wleObjectId.length() != 0) {
         out.write_string(this.wleObjectId);
      } else {
         out.write_string(Integer.toString(this.oid));
      }

      if (this.wleVersionMinor > 1 && this.wleVersionMajor == 1) {
         out.write_long(this.numComponents);
         long encaphandle;
         switch (this.keyType) {
            case 1111834888:
               out.write_long(1111834888);
               out.write_long(0);
               break;
            case 1111834897:
               out.write_long(1111834897);
               encaphandle = out.startEncapsulationNoNesting();
               this.identity.write(out);
               out.write_value((Serializable)this.getActivationID());
               out.endEncapsulation(encaphandle);
               break;
            case 1111834926:
               out.write_long(1111834926);
               encaphandle = out.startEncapsulation();
               this.identity.write(out);
               out.endEncapsulation(encaphandle);
         }

         if (this.writeObjInfo) {
            out.write_long(1111834891);
            encaphandle = out.startEncapsulation();
            out.write_octet_sequence(this.getObjInfo());
            out.endEncapsulation(encaphandle);
         }

         for(int ncomps = 0; ncomps < this.numForeignComponents; ++ncomps) {
            this.foreignComponents[ncomps].write(out);
         }
      } else {
         out.write_long(0);
      }

   }

   private void close(java.io.Closeable os) {
      try {
         os.close();
      } catch (IOException var3) {
      }

   }

   public final int hashCode() {
      int hash = this.foreignKey ? 0 : -1;
      if (this.foreignKey) {
         if (this.key_data != null) {
            byte[] var2 = this.key_data;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               byte aKey_data = var2[var4];
               hash ^= aKey_data;
            }
         }
      } else {
         hash ^= this.wleVersionMajor ^ this.wleVersionMinor ^ this.wleObjectAdapter ^ this.wleGroupId ^ this.oid ^ this.wleDomainId.hashCode();
      }

      return hash;
   }

   public final boolean equals(Object o) {
      if (o instanceof ObjectKey) {
         ObjectKey other = (ObjectKey)o;
         if (this.foreignKey) {
            if (!other.foreignKey) {
               return false;
            } else if (this.key_data == null) {
               return other.key_data == null;
            } else if (this.key_data.length != other.key_data.length) {
               return false;
            } else {
               for(int i = 0; i < this.key_data.length; ++i) {
                  if (this.key_data[i] != other.key_data[i]) {
                     return false;
                  }
               }

               return true;
            }
         } else if (other.foreignKey) {
            return false;
         } else if (this.wleVersionMajor != other.wleVersionMajor) {
            return false;
         } else if (this.wleVersionMinor != other.wleVersionMinor) {
            return false;
         } else if (this.wleObjectAdapter != other.wleObjectAdapter) {
            return false;
         } else if (this.wleGroupId != other.wleGroupId) {
            return false;
         } else if (this.oid != other.oid) {
            return false;
         } else if (!this.wleDomainId.equals(other.wleDomainId)) {
            return false;
         } else if (!this.interfaceName.equals((Object)other.interfaceName)) {
            return false;
         } else if (!this.wleObjectId.equals(other.wleObjectId)) {
            return false;
         } else if (this.wleScaInterfaceBucket != other.wleScaInterfaceBucket) {
            return false;
         } else if (this.target != null && this.identity != other.identity && !this.identity.equals(other.identity)) {
            return false;
         } else {
            return this.keyType == other.keyType;
         }
      } else {
         return false;
      }
   }

   public final String toString() {
      return !this.foreignKey ? "type: " + ServiceContext.VMCIDToString(this.keyType) + ", interface: " + this.interfaceName + ", oid: " + this.oid + ", target: " + this.target + ", identity: " + this.identity : Hex.dump(this.key_data, 0, this.key_data.length);
   }

   public boolean isRepositoryIdAnInterface() {
      return this.isRepIdAnInterface;
   }

   private String[] getRemoteInterfacesForOid(int oid) {
      String[] remoteInterfaces = null;

      try {
         ServerReference sRef = OIDManager.getInstance().findServerReference(oid);
         if (sRef != null) {
            RuntimeDescriptor runtimeDescriptor = sRef.getDescriptor();
            if (runtimeDescriptor != null) {
               RemoteType remoteType = runtimeDescriptor.getRemoteType();
               if (remoteType != null) {
                  remoteInterfaces = remoteType.getInterfaces();
               }
            }
         }
      } catch (Exception var6) {
      }

      return remoteInterfaces;
   }

   private boolean isRepositoryIdAnInterfaceForOid(int oid, RepositoryId repId) {
      boolean result = true;

      try {
         ServerReference sRef = OIDManager.getInstance().findServerReference(oid);
         if (sRef != null) {
            Object implementation = sRef.getImplementation();
            if (implementation == null && sRef instanceof ActivatableServerReference) {
               try {
                  implementation = ((ActivatableServerReference)sRef).getImplementation(this.activationID);
               } catch (RemoteException var7) {
               }
            }

            if (implementation != null && repId != null && implementation.getClass().getName().equals(repId.getClassName())) {
               result = false;
            }
         }
      } catch (Exception var8) {
      }

      return result;
   }

   private byte[] getObjInfo() {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      DataOutputStream outputStream = null;

      try {
         outputStream = new DataOutputStream(baos);
         boolean isRepAnIntf;
         if (this.computeRepId) {
            isRepAnIntf = this.isRepositoryIdAnInterfaceForOid(this.oid, this.interfaceName);
         } else {
            isRepAnIntf = this.isRepIdAnInterface;
         }

         outputStream.writeBoolean(isRepAnIntf);
         String interfaces = "";
         if (this.remoteInterfaces != null) {
            interfaces = StringUtils.join(this.remoteInterfaces, ":");
         }

         outputStream.writeUTF(interfaces);
         outputStream.flush();
      } catch (Exception var8) {
      } finally {
         if (outputStream != null) {
            this.close(outputStream);
         }

      }

      return baos.toByteArray();
   }

   private boolean checkObjInfoRequiredToBeMarshalled() {
      boolean result = false;
      if (!this.interfaceName.isIDLType()) {
         this.remoteInterfaces = this.getRemoteInterfacesForOid(this.oid);
         if (this.remoteInterfaces != null && this.remoteInterfaces.length > 1) {
            this.writeObjInfo = true;
            ++this.numComponents;
            result = true;
         }
      }

      return result;
   }

   static {
      Kernel.ensureInitialized();
      typeIdMap.put(new Integer(8), new String[]{NamingContextHelper.id(), "NameService"});
      typeIdMap.put("NameService", new String[]{NamingContextHelper.id(), "NameService"});
      typeIdMap.put("INIT", new String[]{NamingContextHelper.id(), "NameService"});
      typeIdMap.put("BEA:NameService:Root", new String[]{NamingContextHelper.id(), "NameService"});
      typeIdMap.put("TransactionFactory", new String[]{TransactionFactoryHelper.id(), "TransactionFactory"});
      typeIdMap.put("CodeBase", new String[]{CodeBaseHelper.id(), "CodeBase"});
      localDomainId = getLocalDomainIDInternal();
   }
}
