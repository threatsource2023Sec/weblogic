package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import weblogic.common.internal.ClusterMessagePeerInfoable;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfoFactory;
import weblogic.common.internal.WLObjectOutputStream;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StringUtils;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.UnsyncByteArrayOutputStream;
import weblogic.work.WorkManagerFactory;

public final class UpgradeUtils implements ClusterMembersChangeListener {
   private static UpgradeUtils THE_ONE;
   private Map peerInfoMap = new HashMap();
   private PeerInfo clusterVersion;
   private PeerInfoComparator peerInfoComparator = new PeerInfoComparator();
   private PeerInfoComparator patchPeerInfoComparator = new PeerInfoComparator(true);
   private static Date upgradeExpirationDate = null;
   protected static final String EXPIRATION_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm";
   protected static final String UPGRADE_EXPIRATION_DATE = "weblogic.upgradeExpirationDate";

   private UpgradeUtils() {
      this.peerInfoMap.put("_local_", VersionInfoFactory.getPeerInfo());
      this.clusterVersion = this.getClusterVersion();
      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("[UPGRADE] startup peer info=" + this.clusterVersion);
      }

      if (KernelStatus.isServer()) {
         MemberManager.theOne().addClusterMembersListener(this);
         upgradeExpirationDate = readUpgradeExpirationDateIfDefined();
      }

   }

   protected static Date readUpgradeExpirationDateIfDefined() {
      String strUpgradeExpirationDate = System.getProperty("weblogic.upgradeExpirationDate");
      if (VersionInfoFactory.getPeerInfo().equals(PeerInfo.VERSION_122140) && strUpgradeExpirationDate != null && !strUpgradeExpirationDate.isEmpty()) {
         try {
            return calculateUpgradeExpirationDate(strUpgradeExpirationDate);
         } catch (ParseException var2) {
            throw new RuntimeException("Upgrade expiration date must be specified in the format: yyyy-MM-dd'T'HH:mm", var2);
         }
      } else {
         return null;
      }
   }

   private static Date calculateUpgradeExpirationDate(String upgradeExpirationDate) throws ParseException {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
      Date d = sdf.parse(upgradeExpirationDate);
      return d;
   }

   public static synchronized UpgradeUtils getInstance() {
      if (THE_ONE != null) {
         return THE_ONE;
      } else {
         THE_ONE = new UpgradeUtils();
         return THE_ONE;
      }
   }

   public synchronized PeerInfo getClusterVersion() {
      if (this.clusterVersion == null) {
         this.clusterVersion = (PeerInfo)Collections.min(this.peerInfoMap.values(), this.peerInfoComparator);
      }

      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("[UPGRADE] cluster version string=" + this.clusterVersion);
      }

      return this.clusterVersion;
   }

   public synchronized boolean isUpgradeMapNotEmpty() {
      return this.peerInfoMap.size() > 1;
   }

   public synchronized PeerInfo getServerVersion(String serverName) {
      return (PeerInfo)this.peerInfoMap.get(serverName);
   }

   public synchronized PeerInfo getNewestServerVersion() {
      return (PeerInfo)Collections.max(this.peerInfoMap.values(), this.peerInfoComparator);
   }

   public synchronized void clusterMembersChanged(ClusterMembersChangeEvent cece) {
      if (cece.getAction() == 0) {
         if (this.peerInfoMap.get(cece.getClusterMemberInfo().serverName()) == null) {
            if (ClusterDebugLogger.isDebugEnabled()) {
               ClusterDebugLogger.debug("[UPGRADE] cluster member added=" + cece.getClusterMemberInfo().peerInfo() + ", serverName=" + cece.getClusterMemberInfo().serverName());
            }

            this.peerInfoMap.put(cece.getClusterMemberInfo().serverName(), cece.getClusterMemberInfo().peerInfo());
         }
      } else if (cece.getAction() == 1 && this.peerInfoMap.remove(cece.getClusterMemberInfo().serverName()) != null && ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("[UPGRADE] cluster member removed=" + cece.getClusterMemberInfo().peerInfo() + ", serverName=" + cece.getClusterMemberInfo().serverName());
      }

      this.clusterVersion = (PeerInfo)Collections.min(this.peerInfoMap.values(), this.peerInfoComparator);
      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("[UPGRADE] new cluster version=" + this.clusterVersion);
      }

   }

   synchronized Replacer getInteropReplacer() {
      return RemoteObjectReplacer.getReplacer(this.clusterVersion);
   }

   public void test() {
      PeerInfo oldInfo = this.clusterVersion;
      this.clusterVersion = VersionInfoFactory.getPeerInfo("9.0.2.0");
      this.rewriteServiceOffersAtNewVersion(oldInfo, this.clusterVersion);
   }

   static boolean needsRewrite(Object service, PeerInfo oldVersion, PeerInfo newVersion) throws IOException {
      if (!(service instanceof Externalizable) && !(service instanceof InteropWriteReplaceable)) {
         return false;
      } else {
         UnsyncByteArrayOutputStream oldBaos = null;
         UnsyncByteArrayOutputStream newBoas = null;
         WLObjectOutputStream oos = null;

         boolean var6;
         try {
            oldBaos = new UnsyncByteArrayOutputStream();
            oos = new PeerInfoableObjectOutput(oldBaos, oldVersion);
            oos.setReplacer(new MulticastReplacer(LocalServerIdentity.getIdentity()));
            oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
            oos.writeObjectWL(service);
            oos.flush();
            oos.close();
            newBoas = new UnsyncByteArrayOutputStream();
            oos = new PeerInfoableObjectOutput(newBoas, newVersion);
            oos.setReplacer(new MulticastReplacer(LocalServerIdentity.getIdentity()));
            oos.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
            oos.writeObjectWL(service);
            oos.flush();
            var6 = !Arrays.equals(oldBaos.toRawBytes(), newBoas.toRawBytes());
         } finally {
            if (oldBaos != null) {
               oldBaos.close();
            }

            if (newBoas != null) {
               newBoas.close();
            }

            if (oos != null) {
               oos.close();
            }

         }

         return var6;
      }
   }

   private void rewriteServiceOffersAtNewVersion(final PeerInfo oldVersion, final PeerInfo newVersion) {
      WorkManagerFactory.getInstance().getSystem().schedule(new Runnable() {
         public void run() {
            ServiceAdvertiserImpl advertiser = (ServiceAdvertiserImpl)GlobalServiceLocator.getServiceLocator().getService(ServiceAdvertiserImpl.class, new Annotation[0]);
            advertiser.rewriteServicesAtNewVersion(oldVersion, newVersion);
         }
      });
   }

   synchronized WLObjectOutputStream getOutputStream(UnsyncByteArrayOutputStream baos, ServerChannel channel) throws IOException {
      return this.getOutputStream(baos, channel, this.clusterVersion);
   }

   synchronized WLObjectOutputStream getOutputStream(UnsyncByteArrayOutputStream baos, ServerChannel channel, PeerInfo info) throws IOException {
      WLObjectOutputStream oos = new PeerInfoableObjectOutput(baos, info);
      oos.setReplacer(new MulticastReplacer(LocalServerIdentity.getIdentity()));
      if (channel != null) {
         oos.setServerChannel(channel);
      }

      return oos;
   }

   protected boolean acceptLesserThanOrEqualVersion(String messageVersion) {
      PeerInfo peerInfo = VersionInfoFactory.getPeerInfo(messageVersion);
      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("[UPGRADE] acceptLesserThanOrEqualVersion comparing [" + stringfyPeerInfo(VersionInfoFactory.getPeerInfo()) + "] with remote version [" + peerInfo + "] with result=" + this.patchPeerInfoComparator.compare(VersionInfoFactory.getPeerInfo(), peerInfo));
      }

      return this.patchPeerInfoComparator.compare(VersionInfoFactory.getPeerInfo(), peerInfo) >= 0;
   }

   protected boolean acceptGreaterThanOrEqualVersion(String messageVersion) {
      PeerInfo peerInfo = VersionInfoFactory.getPeerInfo(messageVersion);
      if (ClusterDebugLogger.isDebugEnabled()) {
         ClusterDebugLogger.debug("[UPGRADE] acceptGreaterThanOrEqualVersion comparing [" + stringfyPeerInfo(VersionInfoFactory.getPeerInfo()) + "] with remote version [" + peerInfo + "] with result=" + this.patchPeerInfoComparator.compare(VersionInfoFactory.getPeerInfo(), peerInfo));
      }

      return this.patchPeerInfoComparator.compare(VersionInfoFactory.getPeerInfo(), peerInfo) <= 0;
   }

   public boolean acceptVersion(String messageVersion) {
      return this.isHeartbeatMessageVersion(messageVersion) || this.isInRollingWindowMode() || this.acceptGreaterThanOrEqualVersion(messageVersion);
   }

   public String getLocalServerVersion() {
      return ClusterHelper.STRINGFIED_PEERINFO;
   }

   static String stringfyPeerInfo(PeerInfo peerInfo) {
      return stringfyPeerInfo(peerInfo, true);
   }

   static String stringfyPeerInfo(PeerInfo peerInfo, boolean includePatchVersions) {
      String version = peerInfo.getMajor() + "," + peerInfo.getMinor() + "," + peerInfo.getServicePack();
      if (includePatchVersions) {
         version = version + "," + peerInfo.getRollingPatch() + "," + peerInfo.getPatchUpdate();
      }

      return version;
   }

   protected boolean isInRollingWindowMode() {
      if (upgradeExpirationDate != null) {
         Date current = new Date(System.currentTimeMillis());
         if (current.before(upgradeExpirationDate)) {
            return true;
         }
      }

      return false;
   }

   protected boolean shouldSign(String msgVersion) {
      msgVersion = msgVersion.replaceAll(Pattern.quote("."), ",");
      if (this.isHeartbeatMessageVersion(msgVersion)) {
         return this.isSignedHeartbeatMessage(msgVersion);
      } else {
         return this.acceptGreaterThanOrEqualVersion(msgVersion);
      }
   }

   boolean isSignedHeartbeatMessage(String msgVersion) {
      String[] versionParts = StringUtils.splitCompletely(msgVersion, ",.");
      return versionParts.length == 5;
   }

   boolean isHeartbeatMessageVersion(String messageVersion) {
      return messageVersion.startsWith("0,0,0");
   }

   private static class PeerInfoComparator implements Comparator {
      private boolean comparePatchVersions = false;

      PeerInfoComparator() {
      }

      PeerInfoComparator(boolean comparePatchVersions) {
         this.comparePatchVersions = comparePatchVersions;
      }

      public int compare(Object o, Object o1) {
         PeerInfo peerInfo = (PeerInfo)o;
         PeerInfo peerInfo1 = (PeerInfo)o1;
         int i = peerInfo.getMajor() - peerInfo1.getMajor();
         if (i != 0) {
            return i;
         } else {
            i = peerInfo.getMinor() - peerInfo1.getMinor();
            if (i != 0) {
               return i;
            } else {
               i = peerInfo.getServicePack() - peerInfo1.getServicePack();
               if (i != 0) {
                  return i;
               } else {
                  if (this.comparePatchVersions) {
                     i = peerInfo.getRollingPatch() - peerInfo1.getRollingPatch();
                     if (i != 0) {
                        return i;
                     }

                     i = peerInfo.getPatchUpdate() - peerInfo1.getPatchUpdate();
                     if (i != 0) {
                        return i;
                     }
                  }

                  return 0;
               }
            }
         }
      }
   }

   static final class PeerInfoableObjectOutput extends WLObjectOutputStream implements ClusterMessagePeerInfoable {
      private PeerInfo peerInfo;

      PeerInfoableObjectOutput(UnsyncByteArrayOutputStream baos, PeerInfo peerInfo) throws IOException {
         super(baos);
         this.peerInfo = peerInfo;
      }

      public PeerInfo getPeerInfo() {
         return this.peerInfo;
      }

      public String getClusterVersion() {
         return UpgradeUtils.stringfyPeerInfo(this.peerInfo);
      }
   }
}
