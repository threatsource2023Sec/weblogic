package com.trilead.ssh2.crypto.dh;

import com.trilead.ssh2.crypto.digest.HashForSSH2Types;
import com.trilead.ssh2.log.Logger;
import java.math.BigInteger;
import java.security.SecureRandom;

public class DhExchange {
   private static final Logger log;
   static final BigInteger p1;
   static final BigInteger p14;
   static final BigInteger g;
   BigInteger p;
   BigInteger e;
   BigInteger x;
   BigInteger f;
   BigInteger k;
   // $FF: synthetic field
   static Class class$com$trilead$ssh2$crypto$dh$DhExchange;

   public void init(int group, SecureRandom rnd) {
      this.k = null;
      if (group == 1) {
         this.p = p1;
      } else {
         if (group != 14) {
            throw new IllegalArgumentException("Unknown DH group " + group);
         }

         this.p = p14;
      }

      this.x = new BigInteger(this.p.bitLength() - 1, rnd);
      this.e = g.modPow(this.x, this.p);
   }

   public BigInteger getE() {
      if (this.e == null) {
         throw new IllegalStateException("DhDsaExchange not initialized!");
      } else {
         return this.e;
      }
   }

   public BigInteger getK() {
      if (this.k == null) {
         throw new IllegalStateException("Shared secret not yet known, need f first!");
      } else {
         return this.k;
      }
   }

   public void setF(BigInteger f) {
      if (this.e == null) {
         throw new IllegalStateException("DhDsaExchange not initialized!");
      } else {
         BigInteger zero = BigInteger.valueOf(0L);
         if (zero.compareTo(f) < 0 && this.p.compareTo(f) > 0) {
            this.f = f;
            this.k = f.modPow(this.x, this.p);
         } else {
            throw new IllegalArgumentException("Invalid f specified!");
         }
      }
   }

   public byte[] calculateH(byte[] clientversion, byte[] serverversion, byte[] clientKexPayload, byte[] serverKexPayload, byte[] hostKey) {
      HashForSSH2Types hash = new HashForSSH2Types("SHA1");
      if (log.isEnabled()) {
         log.log(90, "Client: '" + new String(clientversion) + "'");
         log.log(90, "Server: '" + new String(serverversion) + "'");
      }

      hash.updateByteString(clientversion);
      hash.updateByteString(serverversion);
      hash.updateByteString(clientKexPayload);
      hash.updateByteString(serverKexPayload);
      hash.updateByteString(hostKey);
      hash.updateBigInt(this.e);
      hash.updateBigInt(this.f);
      hash.updateBigInt(this.k);
      return hash.getDigest();
   }

   // $FF: synthetic method
   static Class class$(String x0) {
      try {
         return Class.forName(x0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   static {
      log = Logger.getLogger(class$com$trilead$ssh2$crypto$dh$DhExchange == null ? (class$com$trilead$ssh2$crypto$dh$DhExchange = class$("com.trilead.ssh2.crypto.dh.DhExchange")) : class$com$trilead$ssh2$crypto$dh$DhExchange);
      String p1_string = "179769313486231590770839156793787453197860296048756011706444423684197180216158519368947833795864925541502180565485980503646440548199239100050792877003355816639229553136239076508735759914822574862575007425302077447712589550957937778424442426617334727629299387668709205606050270810842907692932019128194467627007";
      String p14_string = "FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF";
      p1 = new BigInteger("179769313486231590770839156793787453197860296048756011706444423684197180216158519368947833795864925541502180565485980503646440548199239100050792877003355816639229553136239076508735759914822574862575007425302077447712589550957937778424442426617334727629299387668709205606050270810842907692932019128194467627007");
      p14 = new BigInteger("FFFFFFFFFFFFFFFFC90FDAA22168C234C4C6628B80DC1CD129024E088A67CC74020BBEA63B139B22514A08798E3404DDEF9519B3CD3A431B302B0A6DF25F14374FE1356D6D51C245E485B576625E7EC6F44C42E9A637ED6B0BFF5CB6F406B7EDEE386BFB5A899FA5AE9F24117C4B1FE649286651ECE45B3DC2007CB8A163BF0598DA48361C55D39A69163FA8FD24CF5F83655D23DCA3AD961C62F356208552BB9ED529077096966D670C354E4ABC9804F1746C08CA18217C32905E462E36CE3BE39E772C180E86039B2783A2EC07A28FB5C55DF06F4C52C9DE2BCBF6955817183995497CEA956AE515D2261898FA051015728E5A8AACAA68FFFFFFFFFFFFFFFF", 16);
      g = new BigInteger("2");
   }
}
