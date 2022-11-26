package org.cryptacular.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.cryptacular.CryptoException;
import org.cryptacular.SaltedHash;
import org.cryptacular.StreamException;
import org.cryptacular.io.Resource;

public final class HashUtil {
   private HashUtil() {
   }

   public static byte[] hash(Digest digest, Object... data) throws CryptoException, StreamException {
      Object[] var2 = data;
      int var3 = data.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object o = var2[var4];
         byte[] bytes;
         if (o instanceof byte[]) {
            bytes = (byte[])((byte[])o);
            digest.update(bytes, 0, bytes.length);
         } else if (o instanceof String) {
            bytes = ByteUtil.toBytes((String)o);
            digest.update(bytes, 0, bytes.length);
         } else if (o instanceof InputStream) {
            hashStream(digest, (InputStream)o);
         } else {
            if (!(o instanceof Resource)) {
               throw new IllegalArgumentException("Invalid input data type " + o);
            }

            InputStream in;
            try {
               in = ((Resource)o).getInputStream();
            } catch (IOException var9) {
               throw new StreamException(var9);
            }

            hashStream(digest, in);
         }
      }

      byte[] output = new byte[digest.getDigestSize()];

      try {
         digest.doFinal(output, 0);
         return output;
      } catch (RuntimeException var8) {
         throw new CryptoException("Hash computation error", var8);
      }
   }

   public static byte[] hash(Digest digest, int iterations, Object... data) throws CryptoException, StreamException {
      if (iterations < 1) {
         throw new IllegalArgumentException("Iterations must be positive");
      } else {
         byte[] output = hash(digest, data);

         try {
            for(int i = 1; i < iterations; ++i) {
               digest.update(output, 0, output.length);
               digest.doFinal(output, 0);
            }

            return output;
         } catch (RuntimeException var5) {
            throw new CryptoException("Hash computation error", var5);
         }
      }
   }

   public static boolean compareHash(Digest digest, byte[] hash, int iterations, Object... data) throws CryptoException, StreamException {
      if (hash.length > digest.getDigestSize()) {
         byte[] hashPart = Arrays.copyOfRange(hash, 0, digest.getDigestSize());
         byte[] saltPart = Arrays.copyOfRange(hash, digest.getDigestSize(), hash.length);
         Object[] dataWithSalt = Arrays.copyOf(data, data.length + 1);
         dataWithSalt[data.length] = saltPart;
         return Arrays.equals(hash(digest, iterations, dataWithSalt), hashPart);
      } else {
         return Arrays.equals(hash(digest, iterations, data), hash);
      }
   }

   public static boolean compareHash(Digest digest, SaltedHash hash, int iterations, boolean saltAfterData, Object... data) throws CryptoException, StreamException {
      Object[] dataWithSalt;
      if (saltAfterData) {
         dataWithSalt = Arrays.copyOf(data, data.length + 1);
         dataWithSalt[data.length] = hash.getSalt();
      } else {
         dataWithSalt = new Object[data.length + 1];
         dataWithSalt[0] = hash.getSalt();
         System.arraycopy(data, 0, dataWithSalt, 1, data.length);
      }

      return Arrays.equals(hash(digest, iterations, dataWithSalt), hash.getHash());
   }

   public static byte[] sha1(Object... data) {
      return hash(new SHA1Digest(), data);
   }

   public static byte[] sha256(Object... data) {
      return hash(new SHA256Digest(), data);
   }

   public static byte[] sha512(Object... data) {
      return hash(new SHA512Digest(), data);
   }

   public static byte[] sha3(int bitLength, Object... data) {
      return hash(new SHA3Digest(bitLength), data);
   }

   private static void hashStream(Digest digest, InputStream in) {
      byte[] buffer = new byte[1024];

      try {
         int length;
         while((length = in.read(buffer)) > 0) {
            digest.update(buffer, 0, length);
         }

      } catch (IOException var5) {
         throw new StreamException(var5);
      }
   }
}
