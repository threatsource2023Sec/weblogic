package org.python.bouncycastle.crypto.examples;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CryptoException;
import org.python.bouncycastle.crypto.agreement.jpake.JPAKEParticipant;
import org.python.bouncycastle.crypto.agreement.jpake.JPAKEPrimeOrderGroup;
import org.python.bouncycastle.crypto.agreement.jpake.JPAKEPrimeOrderGroups;
import org.python.bouncycastle.crypto.agreement.jpake.JPAKERound1Payload;
import org.python.bouncycastle.crypto.agreement.jpake.JPAKERound2Payload;
import org.python.bouncycastle.crypto.agreement.jpake.JPAKERound3Payload;
import org.python.bouncycastle.crypto.digests.SHA256Digest;

public class JPAKEExample {
   public static void main(String[] var0) throws CryptoException {
      JPAKEPrimeOrderGroup var1 = JPAKEPrimeOrderGroups.NIST_3072;
      BigInteger var2 = var1.getP();
      BigInteger var3 = var1.getQ();
      BigInteger var4 = var1.getG();
      String var5 = "password";
      String var6 = "password";
      System.out.println("********* Initialization **********");
      System.out.println("Public parameters for the cyclic group:");
      System.out.println("p (" + var2.bitLength() + " bits): " + var2.toString(16));
      System.out.println("q (" + var3.bitLength() + " bits): " + var3.toString(16));
      System.out.println("g (" + var2.bitLength() + " bits): " + var4.toString(16));
      System.out.println("p mod q = " + var2.mod(var3).toString(16));
      System.out.println("g^{q} mod p = " + var4.modPow(var3, var2).toString(16));
      System.out.println("");
      System.out.println("(Secret passwords used by Alice and Bob: \"" + var5 + "\" and \"" + var6 + "\")\n");
      SHA256Digest var7 = new SHA256Digest();
      SecureRandom var8 = new SecureRandom();
      JPAKEParticipant var9 = new JPAKEParticipant("alice", var5.toCharArray(), var1, var7, var8);
      JPAKEParticipant var10 = new JPAKEParticipant("bob", var6.toCharArray(), var1, var7, var8);
      JPAKERound1Payload var11 = var9.createRound1PayloadToSend();
      JPAKERound1Payload var12 = var10.createRound1PayloadToSend();
      System.out.println("************ Round 1 **************");
      System.out.println("Alice sends to Bob: ");
      System.out.println("g^{x1}=" + var11.getGx1().toString(16));
      System.out.println("g^{x2}=" + var11.getGx2().toString(16));
      System.out.println("KP{x1}={" + var11.getKnowledgeProofForX1()[0].toString(16) + "};{" + var11.getKnowledgeProofForX1()[1].toString(16) + "}");
      System.out.println("KP{x2}={" + var11.getKnowledgeProofForX2()[0].toString(16) + "};{" + var11.getKnowledgeProofForX2()[1].toString(16) + "}");
      System.out.println("");
      System.out.println("Bob sends to Alice: ");
      System.out.println("g^{x3}=" + var12.getGx1().toString(16));
      System.out.println("g^{x4}=" + var12.getGx2().toString(16));
      System.out.println("KP{x3}={" + var12.getKnowledgeProofForX1()[0].toString(16) + "};{" + var12.getKnowledgeProofForX1()[1].toString(16) + "}");
      System.out.println("KP{x4}={" + var12.getKnowledgeProofForX2()[0].toString(16) + "};{" + var12.getKnowledgeProofForX2()[1].toString(16) + "}");
      System.out.println("");
      var9.validateRound1PayloadReceived(var12);
      System.out.println("Alice checks g^{x4}!=1: OK");
      System.out.println("Alice checks KP{x3}: OK");
      System.out.println("Alice checks KP{x4}: OK");
      System.out.println("");
      var10.validateRound1PayloadReceived(var11);
      System.out.println("Bob checks g^{x2}!=1: OK");
      System.out.println("Bob checks KP{x1},: OK");
      System.out.println("Bob checks KP{x2},: OK");
      System.out.println("");
      JPAKERound2Payload var13 = var9.createRound2PayloadToSend();
      JPAKERound2Payload var14 = var10.createRound2PayloadToSend();
      System.out.println("************ Round 2 **************");
      System.out.println("Alice sends to Bob: ");
      System.out.println("A=" + var13.getA().toString(16));
      System.out.println("KP{x2*s}={" + var13.getKnowledgeProofForX2s()[0].toString(16) + "},{" + var13.getKnowledgeProofForX2s()[1].toString(16) + "}");
      System.out.println("");
      System.out.println("Bob sends to Alice");
      System.out.println("B=" + var14.getA().toString(16));
      System.out.println("KP{x4*s}={" + var14.getKnowledgeProofForX2s()[0].toString(16) + "},{" + var14.getKnowledgeProofForX2s()[1].toString(16) + "}");
      System.out.println("");
      var9.validateRound2PayloadReceived(var14);
      System.out.println("Alice checks KP{x4*s}: OK\n");
      var10.validateRound2PayloadReceived(var13);
      System.out.println("Bob checks KP{x2*s}: OK\n");
      BigInteger var15 = var9.calculateKeyingMaterial();
      BigInteger var16 = var10.calculateKeyingMaterial();
      System.out.println("********* After round 2 ***********");
      System.out.println("Alice computes key material \t K=" + var15.toString(16));
      System.out.println("Bob computes key material \t K=" + var16.toString(16));
      System.out.println();
      BigInteger var17 = deriveSessionKey(var15);
      BigInteger var18 = deriveSessionKey(var16);
      JPAKERound3Payload var19 = var9.createRound3PayloadToSend(var15);
      JPAKERound3Payload var20 = var10.createRound3PayloadToSend(var16);
      System.out.println("************ Round 3 **************");
      System.out.println("Alice sends to Bob: ");
      System.out.println("MacTag=" + var19.getMacTag().toString(16));
      System.out.println("");
      System.out.println("Bob sends to Alice: ");
      System.out.println("MacTag=" + var20.getMacTag().toString(16));
      System.out.println("");
      var9.validateRound3PayloadReceived(var20, var15);
      System.out.println("Alice checks MacTag: OK\n");
      var10.validateRound3PayloadReceived(var19, var16);
      System.out.println("Bob checks MacTag: OK\n");
      System.out.println();
      System.out.println("MacTags validated, therefore the keying material matches.");
   }

   private static BigInteger deriveSessionKey(BigInteger var0) {
      SHA256Digest var1 = new SHA256Digest();
      byte[] var2 = var0.toByteArray();
      byte[] var3 = new byte[var1.getDigestSize()];
      var1.update(var2, 0, var2.length);
      var1.doFinal(var3, 0);
      return new BigInteger(var3);
   }
}
