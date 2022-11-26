package weblogic.messaging.dispatcher;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import java.security.Principal;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.jms.common.JMSDebug;
import weblogic.kernel.KernelStatus;
import weblogic.security.service.PrivilegedActions;

public class DispatcherUtils {
   private static final byte WITHOUT_PARTITION_1221 = 0;
   private static final byte WITH_PARTITION_1221 = 1;
   private static final String NO_PARTITION_ID = null;
   private static final String EMPTY_PARTITION_ID = "";
   private static final String NO_PARTITION_NAME = null;
   private static final String EMPTY_PARTITION_NAME = "";
   private static final ComponentInvocationContextManager CICManagerInstance;

   public static String getPartitionId() {
      String partitionId;
      if (CICManagerInstance != null) {
         partitionId = CICManagerInstance.getCurrentComponentInvocationContext().getPartitionId();
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Returning partition id " + partitionId);
         }
      } else {
         partitionId = NO_PARTITION_ID;
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Returning the null id (" + partitionId + ")");
         }
      }

      return partitionId;
   }

   public static String getPartitionName() {
      String partitionName;
      if (CICManagerInstance != null) {
         partitionName = CICManagerInstance.getCurrentComponentInvocationContext().getPartitionName();
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Returning the partition name (" + partitionName + ")");
         }
      } else {
         partitionName = NO_PARTITION_NAME;
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Returning the domain name (" + partitionName + ")");
         }
      }

      return partitionName;
   }

   public static Object getCurrentCIC() {
      Object currentCIC = null;
      if (CICManagerInstance != null) {
         currentCIC = CICManagerInstance.getCurrentComponentInvocationContext();
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Returning the CIC");
         }
      } else if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatherUtilsVerbose.debug("Returning a null CIC");
      }

      return currentCIC;
   }

   public static void writeVersionedPartitionInfo(ObjectOutput out, PartitionAware partAware) throws IOException {
      if (out instanceof PeerInfoable && ((PeerInfoable)out).getPeerInfo().compareTo(PeerInfo.VERSION_1221) < 0) {
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Pre 12.2.1 output stream; nothing written to the output stream.");
         }
      } else {
         String partitionId = partAware.getPartitionId();
         String partitionName = partAware.getPartitionName();
         String connectionPartitionName = partAware.getConnectionPartitionName();
         boolean mustWritePartitionInfo = connectionPartitionName != null && !connectionPartitionName.trim().isEmpty();
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("12.2.1 or later output stream.");
         }

         if (!mustWritePartitionInfo) {
            out.writeByte(0);
            if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatherUtilsVerbose.debug("Partition data not available; wrote WITHOUT flag to the output stream.");
            }
         } else {
            out.writeByte(1);
            out.writeUTF(partitionId != null && !partitionId.trim().isEmpty() ? partitionId : "");
            out.writeUTF(partitionName != null && !partitionName.trim().isEmpty() ? partitionName : "");
            out.writeUTF(connectionPartitionName);
            if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatherUtilsVerbose.debug("Wrote Partition data to the ouput stream");
               JMSDebug.JMSDispatherUtilsVerbose.debug("Partition id: " + (partitionId != null && !partitionId.trim().isEmpty() ? partitionId : "<EMPTY_PARTITION_ID>"));
               JMSDebug.JMSDispatherUtilsVerbose.debug("Partition name: " + (partitionName != null && !partitionName.trim().isEmpty() ? partitionName : "<EMPTY_PARTITION_NAME>"));
               JMSDebug.JMSDispatherUtilsVerbose.debug("Connection partition name: " + connectionPartitionName);
            }
         }
      }

   }

   public static void readVersionedPartitionInfo(ObjectInput in, PartitionAware partAware, DispatcherWrapper dispatcherWrapper, PeerInfo peerInfo) throws IOException {
      if (PeerInfo.VERSION_1221.compareTo(peerInfo) <= 0) {
         if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("12.2.1 or later input stream.");
         }

         byte b = in.readByte();
         if (b == 1) {
            String inString = in.readUTF();
            if ("".equals(inString)) {
               inString = null;
            }

            String id = inString;
            inString = in.readUTF();
            if ("".equals(inString)) {
               inString = null;
            }

            String connName = in.readUTF();
            if (dispatcherWrapper != null) {
               dispatcherWrapper.partitionAwareAssign(id, inString, connName);
            }

            if (partAware instanceof PartitionAwareSetter) {
               assignPartitionAware((PartitionAwareSetter)partAware, id, inString, connName);
            }

            if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatherUtilsVerbose.debug("Partition data read from input stream");
               String s = partAware.getPartitionId();
               JMSDebug.JMSDispatherUtilsVerbose.debug("Partition id: " + (s != null && !s.trim().isEmpty() ? s : "<EMPTY_PARTITION_ID>"));
               s = partAware.getPartitionName();
               JMSDebug.JMSDispatherUtilsVerbose.debug("Partition name: " + (s != null && !s.trim().isEmpty() ? s : "<EMPTY_PARTITION_NAME>"));
               JMSDebug.JMSDispatherUtilsVerbose.debug("Connection partition name: " + partAware.getConnectionPartitionName());
            }
         } else if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatherUtilsVerbose.debug("Partition data not available in the input stream.");
         }
      } else if (JMSDebug.JMSDispatherUtilsVerbose.isDebugEnabled()) {
         JMSDebug.JMSDispatherUtilsVerbose.debug("Pre 12.2.1 input stream; nothing to read from the input stream.");
      }

   }

   public static void assignPartitionAware(PartitionAwareSetter partitionAwareSetter, String id, String partName, String connName) throws IOException {
      partitionAwareSetter.setPartitionId(id);
      partitionAwareSetter.setPartitionName(partName);
      partitionAwareSetter.setConnectionPartitionName(connName);
   }

   static {
      if (KernelStatus.isServer()) {
         Principal kernelId = (Principal)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         CICManagerInstance = ComponentInvocationContextManager.getInstance(kernelId);
      } else {
         CICManagerInstance = null;
      }

   }
}
