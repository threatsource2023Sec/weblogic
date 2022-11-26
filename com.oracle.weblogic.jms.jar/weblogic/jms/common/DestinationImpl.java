package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.Topic;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.deployment.jms.ForeignJMSServerAware;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.client.JMSConnection;
import weblogic.jms.extensions.WLDestination;
import weblogic.jndi.annotation.CrossPartitionAware;
import weblogic.messaging.common.MessagingUtilities;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.store.common.PartitionNameUtilsClient;
import weblogic.store.common.PersistentStoreOutputStream;

@CrossPartitionAware
public class DestinationImpl extends Destination implements Queue, Topic, TemporaryQueue, TemporaryTopic, Externalizable, WLDestination, Cloneable, ForeignJMSServerAware, PerJVMLBAwareDDMember {
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static final byte EXTVERSION3 = 3;
   private static final byte EXTVERSION4 = 4;
   private static final byte EXTVERSION5 = 5;
   private static final byte EXTVERSION6 = 6;
   private static final byte EXTVERSION7 = 7;
   private static final byte EXTVERSION8 = 8;
   private static final long serialVersionUID = 6099783323740404732L;
   public static final byte TYPE_ANONYMOUS = 0;
   public static final byte TYPE_QUEUE = 1;
   public static final byte TYPE_TOPIC = 2;
   public static final byte TYPE_TEMP_QUEUE = 4;
   public static final byte TYPE_TEMP_TOPIC = 8;
   private transient JMSConnection connection;
   private String name;
   private transient String nameForSort;
   private String jmsServerConfigName;
   private String jmsServerInstanceName;
   private String applicationName;
   private String moduleName;
   JMSServerId backEndId;
   private String multicastAddress;
   private int port;
   private boolean pre90;
   private boolean pre10_3_4;
   JMSID destinationId;
   byte type;
   DispatcherId dispatcherId;
   private long generation;
   private static final int FIRSTGENERATION = 1;
   private String[] safExportAllowed;
   private String referenceName;
   private String persistentStoreName;
   private transient boolean replicated;
   private transient boolean OneCopyPerServer;
   private String partitionName;
   private boolean isReferencedByFS;
   private static final int _VERSIONMASK = 3840;
   private static final int _VERSIONSHIFT = 8;
   private static final int _TYPEMASK = 15;
   private static final int _TYPESHIFT = 0;
   private static final int _HASREFERENCENAME = 16;
   private static final int _HASGENERATION = 32;
   private static final int _ISPARTOFAPP = 64;
   private static final int _ISPARTOFEAR = 128;
   protected static final int _HASIDS = 4096;
   private static final int _ISNOTREPLYTO = 8192;
   private static final int _HASMULTICASTADDR = 16384;
   private static final int _HASDISPID = 32768;
   private static final int _HASSTORENAME = 1;
   private static final int _HASCONFIGNAME = 2;
   private static final int _HASPARTITIONNAME = 4;
   private static final int _ISREFEDBYFS = 8;
   private static final int _HAS_MORE_FLAGS = 32768;

   public boolean isReferencedByFS() {
      return this.isReferencedByFS;
   }

   public void setReferencedByFS(boolean isReferencedByFS) {
      this.isReferencedByFS = isReferencedByFS;
   }

   public boolean isReplicated() {
      return this.replicated;
   }

   public void setReplicated(boolean replicated) {
      this.replicated = replicated;
   }

   public boolean isOneCopyPerServer() {
      return this.OneCopyPerServer;
   }

   public void setOneCopyPerServer(boolean oneCopyPerServer) {
      this.OneCopyPerServer = oneCopyPerServer;
   }

   public DestinationImpl() {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
   }

   public DestinationImpl(byte type, String jmsServerInstanceName, String name, String applicationName, String moduleName) {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.destinationImplInternal(type, jmsServerInstanceName, jmsServerInstanceName, name);
   }

   public void destinationImplInternal(byte type, String jmsServerInstanceName, String jmsServerConfigName, String name) {
      this.name = name;
      this.jmsServerInstanceName = jmsServerInstanceName;
      this.jmsServerConfigName = jmsServerConfigName;
      this.type = type;
   }

   public DestinationImpl(byte type) {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
      this.type = type;
   }

   public DestinationImpl(int type, String jmsServerInstanceName, String jmsServerConfigName, String persistentStoreName, String name, String applicationName, String moduleName, JMSServerId backEndId, JMSID destinationId, long creationTime, String safExportPolicy) {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.destinationImplInternalWithCreationTime(type, jmsServerInstanceName, jmsServerConfigName, name, backEndId, destinationId, creationTime);
      this.persistentStoreName = persistentStoreName;
      this.setSafExportAllowedArray(safExportPolicy);
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   private void destinationImplInternalWithCreationTime(int type, String jmsServerInstanceName, String jmsServerConfigName, String name, JMSServerId backEndId, JMSID destinationId, long creationTime) {
      this.type = (byte)type;
      this.jmsServerInstanceName = jmsServerInstanceName;
      this.jmsServerConfigName = jmsServerConfigName;
      this.name = name;
      this.backEndId = backEndId;
      this.destinationId = destinationId;
      this.generation = creationTime;
      this.dispatcherId = JMSEnvironment.getJMSEnvironment().getLocalDispatcherId();
      this.partitionName = PartitionUtils.getPartitionName();
   }

   public DestinationImpl(int type, String jmsServerInstanceName, String jmsServerConfigName, String persistentStoreName, String name, String applicationName, String moduleName, JMSServerId backEndId, JMSID destinationId, DispatcherId dispatcherId) {
      this(type, jmsServerInstanceName, jmsServerConfigName, persistentStoreName, name, applicationName, moduleName, backEndId, destinationId, dispatcherId, PartitionUtils.getPartitionName());
   }

   public DestinationImpl(int type, String jmsServerInstanceName, String jmsServerConfigName, String persistentStoreName, String name, String applicationName, String moduleName, JMSServerId backEndId, JMSID destinationId, DispatcherId dispatcherId, String partitionName) {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.destinationImplInternalWithoutCreationTime(type, jmsServerInstanceName, jmsServerConfigName, name, backEndId, destinationId, partitionName);
      this.dispatcherId = dispatcherId;
      this.persistentStoreName = persistentStoreName;
   }

   public DestinationImpl(int type, String jmsServerInstanceName, String jmsServerConfigName, String name, String applicationName, String moduleName, JMSServerId backEndId, JMSID destinationId) {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.destinationImplInternalWithoutCreationTime(type, jmsServerInstanceName, jmsServerConfigName, name, backEndId, destinationId, PartitionUtils.getPartitionName());
      this.dispatcherId = JMSEnvironment.getJMSEnvironment().getLocalDispatcherId();
   }

   public DestinationImpl(int type, String jmsServerInstanceName, String jmsServerConfigName, String name, String applicationName, String moduleName, JMSServerId backEndId, JMSID destinationId, String safExportPolicy, String persistentStoreName) {
      this(type, jmsServerInstanceName, jmsServerConfigName, name, applicationName, moduleName, backEndId, destinationId, safExportPolicy, persistentStoreName, PartitionUtils.getPartitionName());
   }

   public DestinationImpl(int type, String jmsServerInstanceName, String jmsServerConfigName, String name, String applicationName, String moduleName, JMSServerId backEndId, JMSID destinationId, String safExportPolicy, String persistentStoreName, String partitionName) {
      this.pre90 = false;
      this.pre10_3_4 = false;
      this.type = 1;
      this.generation = 1L;
      this.persistentStoreName = null;
      this.replicated = false;
      this.OneCopyPerServer = false;
      this.partitionName = null;
      this.isReferencedByFS = false;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.destinationImplInternalWithoutCreationTime(type, jmsServerInstanceName, jmsServerConfigName, name, backEndId, destinationId, partitionName);
      this.dispatcherId = JMSEnvironment.getJMSEnvironment().getLocalDispatcherId();
      this.setSafExportAllowedArray(safExportPolicy);
      this.persistentStoreName = persistentStoreName;
   }

   private void destinationImplInternalWithoutCreationTime(int type, String jmsServerInstanceName, String jmsServerConfigName, String name, JMSServerId backEndId, JMSID destinationId, String partitionName) {
      this.type = (byte)type;
      this.jmsServerInstanceName = jmsServerInstanceName;
      this.jmsServerConfigName = jmsServerConfigName;
      this.name = name;
      this.backEndId = backEndId;
      this.destinationId = destinationId;
      this.partitionName = partitionName;
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public DestinationImpl getClone() {
      try {
         return (DestinationImpl)this.clone();
      } catch (CloneNotSupportedException var2) {
         throw new AssertionError(var2);
      }
   }

   public void setReferenceName(String referenceName) {
      this.referenceName = referenceName;
   }

   public String getReferenceName() {
      return this.referenceName;
   }

   void setSafExportAllowedArray(String safExportPolicy) {
      if (safExportPolicy != null) {
         ArrayList tempArray = new ArrayList();
         String buffer = new String(safExportPolicy);

         int index;
         for(int index = false; (index = buffer.indexOf(",")) != -1; buffer = buffer.substring(index + 1)) {
            tempArray.add(buffer.substring(0, index - 1));
         }

         if (buffer != null) {
            tempArray.add(buffer);
         }

         if (tempArray.size() > 0) {
            this.safExportAllowed = new String[tempArray.size()];

            for(int i = 0; i < tempArray.size(); ++i) {
               this.safExportAllowed[i] = (String)tempArray.get(i);
            }
         }

      }
   }

   public String[] getSafAllowedArray() {
      return this.safExportAllowed;
   }

   public final boolean isQueue() {
      return this.type == 1 || this.type == 4;
   }

   public final boolean isTopic() {
      return this.type == 2 || this.type == 8;
   }

   public final boolean isAnonymous() {
      return this.type == 0;
   }

   String getDestinationName() {
      return this.name;
   }

   public String toString() {
      return this.getDestinationNameInternal();
   }

   public final String getQueueName() {
      return this.isTopic() ? null : this.getDestinationNameInternal();
   }

   public final String getTopicName() {
      return this.isQueue() ? null : this.getDestinationNameInternal();
   }

   public final String getDestinationNameInternal() {
      return this.getPartitionName() == null ? this.getDestinationName() : PartitionNameUtilsClient.stripDecoratedPartitionNamesFromCombinedName("!@", this.getDestinationName(), this.getPartitionName());
   }

   public byte getDestinationInstanceType() {
      return 1;
   }

   public final int getType() {
      return this.type;
   }

   public final void setBackEndID(JMSServerId backEndId) {
      this.backEndId = backEndId;
   }

   public final synchronized JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public final JMSID getId() {
      return this.destinationId;
   }

   public final synchronized JMSID getDestinationId() {
      return this.destinationId;
   }

   public final void setDestinationId(JMSID id) {
      this.destinationId = id;
   }

   public String getMemberName() {
      return this.name;
   }

   public String getCreateDestinationArgument() {
      String value = this.getServerName() + "/" + this.getMemberName();
      return value.intern();
   }

   protected void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.getDestinationName();
   }

   protected void setServerName(String jmsServerInstanceName) {
      this.jmsServerInstanceName = jmsServerInstanceName;
   }

   public final String getServerName() {
      return this.jmsServerInstanceName;
   }

   protected void setJMSServerConfigName(String jmsServerConfigName) {
      this.jmsServerConfigName = jmsServerConfigName;
   }

   public final String getJMSServerConfigName() {
      return this.jmsServerConfigName;
   }

   protected void setApplicationName(String applicationName) {
      this.applicationName = applicationName;
   }

   public final String getApplicationName() {
      return this.applicationName;
   }

   protected void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public final String getModuleName() {
      return this.moduleName;
   }

   public final String getDispatcherName() {
      return this.dispatcherId == null ? null : this.dispatcherId.getName();
   }

   public final DispatcherId getDispatcherId() {
      return this.dispatcherId;
   }

   public final void setDispatcherId(DispatcherId dispatcherId) {
      this.dispatcherId = dispatcherId;
   }

   private final boolean isOnUPS() {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("Check if onPreferredServer: memberName=" + this.getMemberName() + " dispatcher = " + this.getDispatcherName());
      }

      if (this.getDispatcherName() == null) {
         return false;
      } else {
         String decoratedJMSServerName = this.jmsServerConfigName + "@" + this.getDispatcherName();
         return this.jmsServerInstanceName.endsWith(decoratedJMSServerName) && this.getMemberName().contains(decoratedJMSServerName);
      }
   }

   public final boolean isPossiblyClusterTargeted() {
      return this.jmsServerInstanceName.contains(this.jmsServerConfigName + "@");
   }

   public boolean isOnPreferredServer() {
      return this.isPossiblyClusterTargeted() && this.isOnUPS();
   }

   public final synchronized String getMemberNameForSort() {
      if (this.nameForSort == null) {
         this.nameForSort = MessagingUtilities.getSortingString(this.name);
      }

      return this.nameForSort;
   }

   public synchronized boolean isStale() {
      return this.dispatcherId == null;
   }

   public synchronized void markStale() {
      this.dispatcherId = null;
   }

   public String getPersistentStoreName() {
      return this.persistentStoreName;
   }

   protected int getVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         int majorVer = pi.getMajor();
         if (majorVer < 6) {
            throw new IOException(JMSClientExceptionLogger.logIncompatibleVersion9Loggable((byte)1, (byte)2, (byte)3, (byte)4, pi.toString()).getMessage());
         }

         switch (majorVer) {
            case 6:
               return 2;
            case 7:
            case 8:
               return 3;
            case 9:
               return 4;
            case 11:
            default:
               break;
            case 12:
               if (pi.compareTo(PeerInfo.VERSION_1221) >= 0) {
                  return 8;
               }

               if (pi.compareTo(PeerInfo.VERSION_1212) >= 0) {
                  return 7;
               }
            case 10:
               if (pi.compareTo(PeerInfo.VERSION_1034) >= 0) {
                  return 6;
               }

               if (pi.compareTo(PeerInfo.VERSION_1033) >= 0) {
                  return 5;
               }

               return 4;
         }
      }

      return 8;
   }

   public final boolean isPre90() {
      return this.pre90;
   }

   public final boolean isPre10_3_4() {
      return this.pre10_3_4;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      this.writeDestinationImpl(out, this.name);
   }

   final void writeDestinationImpl(ObjectOutput out) throws IOException {
      this.writeDestinationImpl(out, this.name);
   }

   final void writeDestinationImpl(ObjectOutput out, String writeName) throws IOException {
      boolean isStoreStream = out instanceof PersistentStoreOutputStream;
      boolean isWLObjectOutput = out instanceof WLObjectOutput;
      short flagSet1 = 0;
      int peerVersion = this.getVersion(out);
      flagSet1 = (short)(flagSet1 | peerVersion << 8);
      if (!isStoreStream && peerVersion <= 3) {
         flagSet1 &= -3841;
         flagSet1 = (short)(flagSet1 | 512);
      }

      flagSet1 = (short)(flagSet1 | this.type);
      if (isStoreStream) {
         flagSet1 = (short)(flagSet1 | 32);
         if (this.applicationName != null) {
            flagSet1 = (short)(flagSet1 | 64);
         }

         if (this.moduleName != null) {
            flagSet1 = (short)(flagSet1 | 128);
         }

         out.writeShort(flagSet1);
         out.writeUTF(writeName);
         out.writeUTF(this.jmsServerInstanceName);
         out.writeLong(this.getGeneration());
         if (this.applicationName != null) {
            out.writeUTF(this.applicationName);
         }

         if (this.moduleName != null) {
            out.writeUTF(this.moduleName);
         }

      } else {
         flagSet1 = (short)(flagSet1 | 4096);
         if (this.multicastAddress != null) {
            flagSet1 = (short)(flagSet1 | 16384);
         }

         if (this.destinationId != null) {
            flagSet1 = (short)(flagSet1 | 8192);
         }

         JMSID destinationId = this.destinationId;
         if (this.dispatcherId != null) {
            flagSet1 = (short)(flagSet1 | '耀');
         }

         if (this.applicationName != null) {
            flagSet1 = (short)(flagSet1 | 64);
         }

         if (this.moduleName != null) {
            flagSet1 = (short)(flagSet1 | 128);
         }

         if (peerVersion >= 4 && this.referenceName != null) {
            flagSet1 = (short)(flagSet1 | 16);
         }

         out.writeShort(flagSet1);
         if (isWLObjectOutput) {
            ((WLObjectOutput)out).writeAbbrevString(this.name);
            ((WLObjectOutput)out).writeAbbrevString(this.jmsServerInstanceName);
         } else {
            out.writeUTF(this.name);
            out.writeUTF(this.jmsServerInstanceName);
         }

         if (peerVersion >= 4) {
            if (this.applicationName != null) {
               out.writeUTF(this.applicationName);
            }

            if (this.moduleName != null) {
               out.writeUTF(this.moduleName);
            }

            if (this.safExportAllowed == null) {
               out.writeByte(0);
            } else {
               out.writeByte(this.safExportAllowed.length);

               for(int i = 0; i < this.safExportAllowed.length; ++i) {
                  out.writeUTF(this.safExportAllowed[i]);
               }
            }
         }

         if (destinationId != null) {
            this.backEndId.writeExternal(out);
            destinationId.writeExternal(out);
         }

         if (this.multicastAddress != null) {
            out.writeUTF(this.multicastAddress);
            out.writeInt(this.port);
         }

         if (this.dispatcherId != null) {
            this.dispatcherId.writeExternal(out);
         }

         if ((flagSet1 & 16) != 0) {
            out.writeUTF(this.referenceName);
         }

         short flagSet2;
         if (peerVersion >= 5) {
            short tmp_flagSet2 = 0;
            if (this.persistentStoreName != null && this.persistentStoreName.length() != 0) {
               tmp_flagSet2 = (short)(tmp_flagSet2 | 1);
            }

            if (peerVersion >= 7 && this.jmsServerConfigName != null && this.jmsServerConfigName.length() != 0) {
               tmp_flagSet2 = (short)(tmp_flagSet2 | 2);
            }

            if (peerVersion >= 8) {
               if (this.partitionName != null && this.partitionName.length() != 0 && !this.partitionName.equals("DOMAIN")) {
                  tmp_flagSet2 = (short)(tmp_flagSet2 | 4);
               }

               if (this.isReferencedByFS) {
                  tmp_flagSet2 = (short)(tmp_flagSet2 | 8);
               }
            }

            flagSet2 = tmp_flagSet2;
         } else {
            flagSet2 = 0;
         }

         if (peerVersion >= 5) {
            out.writeShort(flagSet2);
         }

         if ((flagSet2 & 1) != 0) {
            out.writeUTF(this.persistentStoreName);
         }

         if ((flagSet2 & 2) != 0) {
            if (isWLObjectOutput) {
               ((WLObjectOutput)out).writeAbbrevString(this.jmsServerConfigName);
            } else {
               out.writeUTF(this.jmsServerConfigName);
            }
         }

         if ((flagSet2 & 4) != 0) {
            out.writeUTF(this.partitionName);
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flagSet1 = in.readShort();
      this.readDestinationImpl(in, flagSet1);
   }

   final void readDestinationImpl(ObjectInput in, int flagSet1) throws IOException, ClassNotFoundException {
      byte vrsn = (byte)((flagSet1 & 3840) >>> 8 & 255);
      if (vrsn == 1) {
         this.readExternalVersion1(in, (byte)(flagSet1 & 255));
      } else if (vrsn != 2 && vrsn != 3 && vrsn != 4 && vrsn != 5 && vrsn != 6 && vrsn != 7 && vrsn != 8) {
         throw JMSUtilities.versionIOException(vrsn, 1, 8);
      } else {
         if (vrsn < 4) {
            this.pre90 = true;
         }

         if (vrsn < 6) {
            this.pre10_3_4 = true;
         }

         this.type = (byte)((flagSet1 & 15) >>> 0 & 255);
         if ((flagSet1 & 4096) == 0) {
            this.name = in.readUTF();
            this.jmsServerInstanceName = in.readUTF();
            if ((flagSet1 & 32) != 0) {
               if (vrsn == 2) {
                  this.generation = (long)in.readInt();
               } else {
                  this.generation = in.readLong();
               }
            }

            if ((flagSet1 & 64) != 0) {
               this.applicationName = in.readUTF();
            }

            if ((flagSet1 & 128) != 0) {
               this.moduleName = in.readUTF();
            }

         } else {
            if (in instanceof WLObjectInput) {
               this.name = ((WLObjectInput)in).readAbbrevString();
               this.jmsServerInstanceName = ((WLObjectInput)in).readAbbrevString();
            } else {
               this.name = in.readUTF();
               this.jmsServerInstanceName = in.readUTF();
            }

            short flagSet2;
            if (vrsn >= 4) {
               if ((flagSet1 & 64) != 0) {
                  this.applicationName = in.readUTF();
               }

               if ((flagSet1 & 128) != 0) {
                  this.moduleName = in.readUTF();
               }

               flagSet2 = in.readByte();
               if (flagSet2 > 0) {
                  this.safExportAllowed = new String[flagSet2];

                  for(int i = 0; i < flagSet2; ++i) {
                     this.safExportAllowed[i] = in.readUTF();
                  }
               }
            }

            if ((flagSet1 & 8192) != 0) {
               this.backEndId = new JMSServerId();
               this.backEndId.readExternal(in);
               this.destinationId = new JMSID();
               this.destinationId.readExternal(in);
            }

            if ((flagSet1 & 16384) != 0) {
               this.multicastAddress = in.readUTF();
               this.port = in.readInt();
            }

            if ((flagSet1 & '耀') != 0) {
               this.dispatcherId = new DispatcherId();
               this.dispatcherId.readExternal(in);
            }

            if ((flagSet1 & 16) != 0) {
               this.referenceName = in.readUTF();
            }

            flagSet2 = 0;
            if (vrsn >= 5) {
               flagSet2 = in.readShort();
            }

            if ((flagSet2 & 1) != 0) {
               this.persistentStoreName = in.readUTF();
            }

            if ((flagSet2 & 2) != 0) {
               if (in instanceof WLObjectInput) {
                  this.jmsServerConfigName = ((WLObjectInput)in).readAbbrevString();
               } else {
                  this.jmsServerConfigName = in.readUTF();
               }
            }

            if ((flagSet2 & 4) != 0) {
               this.partitionName = in.readUTF();
            }

            if ((flagSet2 & 8) != 0) {
               this.isReferencedByFS = true;
            }

         }
      }
   }

   private void readExternalVersion1(ObjectInput in, byte type) throws IOException, ClassNotFoundException {
      this.type = type;
      this.generation = 1L;
      this.name = in.readUTF();
      this.jmsServerInstanceName = in.readUTF();
      if (in.readBoolean()) {
         this.backEndId = new JMSServerId();
         this.backEndId.readExternal(in);
      }

      if (in.readBoolean()) {
         this.destinationId = new JMSID();
         this.destinationId.readExternal(in);
      }

      if (in.readBoolean()) {
         this.multicastAddress = in.readUTF();
         this.port = in.readInt();
      }

   }

   public boolean equals(Object o) {
      if (o != null && o instanceof DestinationImpl) {
         DestinationImpl destination = (DestinationImpl)o;
         if (destination == this) {
            return true;
         } else if (this.type != destination.type) {
            return false;
         } else if (this.destinationId == null) {
            return false;
         } else {
            return !this.name.equals(destination.name) ? false : this.destinationId.equals(destination.destinationId);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.getId() != null ? this.getId().hashCode() : super.hashCode();
   }

   public final void delete() throws javax.jms.JMSException {
      if (this.connection == null) {
         throw new JMSException(JMSClientExceptionLogger.logInvalidTemporaryDestinationLoggable().getMessage());
      } else {
         this.connection.destroyTemporaryDestination(this.backEndId, this.destinationId);
      }
   }

   public final void setConnection(JMSConnection connection) {
      this.connection = connection;
   }

   public final JMSConnection getConnection() {
      return this.connection;
   }

   public final void setMulticastAddress(String multicastAddress) {
      this.multicastAddress = multicastAddress;
   }

   public final String getMulticastAddress() {
      return this.multicastAddress;
   }

   public final int getPort() {
      return this.port;
   }

   public final void setPort(int port) {
      this.port = port;
   }

   public final synchronized long getGeneration() {
      return this.generation;
   }
}
