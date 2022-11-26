package weblogic.rjvm;

import java.math.BigInteger;
import java.security.SecureRandom;
import weblogic.common.internal.PeerInfo;

public class LocalRJVM extends RJVMImpl {
   private final SecureRandom masterRandom = new SecureRandom();
   private final BigInteger privateKey;
   private final byte[] privateKeyBytes = new byte[25];
   private final byte[] publicKeyBytes;

   private static BigInteger getdhModulus() {
      return LocalRJVM.DhModulusMaker.dhModulus;
   }

   private static BigInteger getdhBase() {
      return LocalRJVM.DhBaseMaker.dhBase;
   }

   public static LocalRJVM getLocalRJVM() {
      return LocalRJVM.LocalRJVMMaker.localRJVM;
   }

   public SecureRandom getSecureRandom() {
      return this.masterRandom;
   }

   public byte[] getPublicKey() {
      return this.publicKeyBytes;
   }

   byte[] getSharedKey(byte[] remotePublicKey) {
      return (new BigInteger(remotePublicKey)).modPow(this.privateKey, getdhModulus()).toByteArray();
   }

   protected LocalRJVM() {
      super((JVMID)null, new Finder());
      this.masterRandom.nextBytes(this.privateKeyBytes);
      this.privateKeyBytes[0] = 0;
      this.privateKey = new BigInteger(this.privateKeyBytes);
      this.publicKeyBytes = getdhBase().modPow(this.privateKey, getdhModulus()).toByteArray();
      RJVMManager.getRJVMManager().initialize();
   }

   public JVMID getID() {
      return JVMID.localID();
   }

   public PeerInfo getPeerInfo() {
      return PeerInfo.getPeerInfoForWire();
   }

   private static final class DhBaseMaker {
      private static final BigInteger dhBase = new BigInteger(new byte[]{3});
   }

   private static final class DhModulusMaker {
      private static final BigInteger dhModulus = new BigInteger(new byte[]{0, -44, -96, -70, 2, 80, -74, -3, 46, -58, 38, -25, -17, -42, 55, -33, 118, -57, 22, -30, 45, 9, 68, -72, -117});
   }

   private static final class LocalRJVMMaker {
      private static final LocalRJVM localRJVM = new LocalRJVM();
   }
}
