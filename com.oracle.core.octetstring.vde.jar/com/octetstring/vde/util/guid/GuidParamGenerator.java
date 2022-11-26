package com.octetstring.vde.util.guid;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

class GuidParamGenerator {
   private static final String MESSAGE_DIGEST_ALGORITHM = "SHA-1";
   private static final int NODE_ADDRESS_SIZE_IN_BYTES = 6;
   private static final int BYTE_WITH_MULTICAST_BIT_SET = 1;
   private static final int NUM_BYTES_IN_SHORT = 2;
   private static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

   static byte[] generateNodeID() {
      byte[] nodeHash = computeNodeHash();
      byte[] nodeID = new byte[6];
      System.arraycopy(nodeHash, 0, nodeID, 0, 6);
      nodeID[0] = (byte)(nodeID[0] | 1);
      return nodeID;
   }

   private static byte[] computeNodeHash() {
      byte[] hash = null;

      try {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
         InetAddress nodeAddress = InetAddress.getLocalHost();
         if (!nodeAddress.isLoopbackAddress()) {
            messageDigest.update(nodeAddress.getCanonicalHostName().getBytes());
            messageDigest.update(nodeAddress.getAddress());
         }

         Collection systemValues = new CopyOnWriteArrayList(System.getProperties().values());
         Iterator systemValuesIter = systemValues.iterator();

         while(systemValuesIter.hasNext()) {
            messageDigest.update(systemValuesIter.next().toString().getBytes());
         }

         hash = messageDigest.digest();
      } catch (NoSuchAlgorithmException var5) {
         var5.printStackTrace();
      } catch (UnknownHostException var6) {
      }

      return hash;
   }

   static short generateClockSequence() {
      SecureRandom randomGenerator = null;

      try {
         randomGenerator = SecureRandom.getInstance("SHA1PRNG");
      } catch (NoSuchAlgorithmException var2) {
         var2.printStackTrace();
      }

      byte[] randomBytes = new byte[2];
      randomGenerator.nextBytes(randomBytes);
      return GuidUtils.toShort(randomBytes);
   }
}
