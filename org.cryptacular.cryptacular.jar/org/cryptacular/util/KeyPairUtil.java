package org.cryptacular.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.signers.DSASigner;
import org.bouncycastle.crypto.signers.ECDSASigner;
import org.bouncycastle.crypto.signers.RSADigestSigner;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.adapter.Converter;
import org.cryptacular.asn.OpenSSLPrivateKeyDecoder;
import org.cryptacular.asn.PKCS8PrivateKeyDecoder;
import org.cryptacular.asn.PublicKeyDecoder;

public final class KeyPairUtil {
   private static final byte[] SIGN_BYTES = ByteUtil.toBytes("Mr. Watson--come here--I want to see you.");

   private KeyPairUtil() {
   }

   public static int length(PublicKey pubKey) {
      int size;
      if (pubKey instanceof DSAPublicKey) {
         size = ((DSAPublicKey)pubKey).getParams().getP().bitLength();
      } else if (pubKey instanceof RSAPublicKey) {
         size = ((RSAPublicKey)pubKey).getModulus().bitLength();
      } else {
         if (!(pubKey instanceof ECPublicKey)) {
            throw new IllegalArgumentException(pubKey + " not supported.");
         }

         size = ((ECPublicKey)pubKey).getParams().getCurve().getField().getFieldSize();
      }

      return size;
   }

   public static int length(PrivateKey privKey) {
      int size;
      if (privKey instanceof DSAPrivateKey) {
         size = ((DSAPrivateKey)privKey).getParams().getQ().bitLength();
      } else if (privKey instanceof RSAPrivateKey) {
         size = ((RSAPrivateKey)privKey).getModulus().bitLength();
      } else {
         if (!(privKey instanceof ECPrivateKey)) {
            throw new IllegalArgumentException(privKey + " not supported.");
         }

         size = ((ECPrivateKey)privKey).getParams().getCurve().getField().getFieldSize();
      }

      return size;
   }

   public static boolean isKeyPair(PublicKey pubKey, PrivateKey privKey) throws CryptoException {
      String alg = pubKey.getAlgorithm();
      if (!alg.equals(privKey.getAlgorithm())) {
         return false;
      } else {
         boolean result;
         switch (alg) {
            case "DSA":
               result = isKeyPair((DSAPublicKey)pubKey, (DSAPrivateKey)privKey);
               break;
            case "RSA":
               result = isKeyPair((RSAPublicKey)pubKey, (RSAPrivateKey)privKey);
               break;
            case "EC":
               result = isKeyPair((ECPublicKey)pubKey, (ECPrivateKey)privKey);
               break;
            default:
               throw new IllegalArgumentException(alg + " not supported.");
         }

         return result;
      }
   }

   public static boolean isKeyPair(DSAPublicKey pubKey, DSAPrivateKey privKey) throws CryptoException {
      DSASigner signer = new DSASigner();
      DSAParameters params = new DSAParameters(privKey.getParams().getP(), privKey.getParams().getQ(), privKey.getParams().getG());

      try {
         signer.init(true, new DSAPrivateKeyParameters(privKey.getX(), params));
         BigInteger[] sig = signer.generateSignature(SIGN_BYTES);
         signer.init(false, new DSAPublicKeyParameters(pubKey.getY(), params));
         return signer.verifySignature(SIGN_BYTES, sig[0], sig[1]);
      } catch (RuntimeException var5) {
         throw new CryptoException("Signature computation error", var5);
      }
   }

   public static boolean isKeyPair(RSAPublicKey pubKey, RSAPrivateKey privKey) throws CryptoException {
      RSADigestSigner signer = new RSADigestSigner(new SHA256Digest());

      try {
         signer.init(true, new RSAKeyParameters(true, privKey.getModulus(), privKey.getPrivateExponent()));
         signer.update(SIGN_BYTES, 0, SIGN_BYTES.length);
         byte[] sig = signer.generateSignature();
         signer.init(false, new RSAKeyParameters(false, pubKey.getModulus(), pubKey.getPublicExponent()));
         signer.update(SIGN_BYTES, 0, SIGN_BYTES.length);
         return signer.verifySignature(sig);
      } catch (Exception var4) {
         throw new CryptoException("Signature computation error", var4);
      }
   }

   public static boolean isKeyPair(ECPublicKey pubKey, ECPrivateKey privKey) throws CryptoException {
      ECDSASigner signer = new ECDSASigner();

      try {
         signer.init(true, ECUtil.generatePrivateKeyParameter(privKey));
         BigInteger[] sig = signer.generateSignature(SIGN_BYTES);
         signer.init(false, ECUtil.generatePublicKeyParameter(pubKey));
         return signer.verifySignature(SIGN_BYTES, sig[0], sig[1]);
      } catch (Exception var4) {
         throw new CryptoException("Signature computation error", var4);
      }
   }

   public static PrivateKey readPrivateKey(String path) throws EncodingException, StreamException {
      return readPrivateKey(new File(path));
   }

   public static PrivateKey readPrivateKey(File file) throws EncodingException, StreamException {
      try {
         return readPrivateKey((InputStream)(new FileInputStream(file)));
      } catch (FileNotFoundException var2) {
         throw new StreamException("File not found: " + file);
      }
   }

   public static PrivateKey readPrivateKey(InputStream in) throws EncodingException, StreamException {
      return decodePrivateKey(StreamUtil.readAll(in));
   }

   public static PrivateKey readPrivateKey(String path, char[] password) throws EncodingException, StreamException {
      return readPrivateKey(new File(path), password);
   }

   public static PrivateKey readPrivateKey(File file, char[] password) throws EncodingException, StreamException {
      try {
         return readPrivateKey((InputStream)(new FileInputStream(file)), password);
      } catch (FileNotFoundException var3) {
         throw new StreamException("File not found: " + file);
      }
   }

   public static PrivateKey readPrivateKey(InputStream in, char[] password) throws EncodingException, StreamException {
      return decodePrivateKey(StreamUtil.readAll(in), password);
   }

   public static PrivateKey decodePrivateKey(byte[] encodedKey) throws EncodingException {
      return decodePrivateKey(encodedKey, (char[])null);
   }

   public static PrivateKey decodePrivateKey(byte[] encryptedKey, char[] password) throws EncodingException {
      AsymmetricKeyParameter key;
      try {
         PKCS8PrivateKeyDecoder decoder = new PKCS8PrivateKeyDecoder();
         key = (AsymmetricKeyParameter)decoder.decode(encryptedKey, new Object[]{password});
      } catch (RuntimeException var5) {
         OpenSSLPrivateKeyDecoder decoder = new OpenSSLPrivateKeyDecoder();
         key = (AsymmetricKeyParameter)decoder.decode(encryptedKey, new Object[]{password});
      }

      return Converter.convertPrivateKey(key);
   }

   public static PublicKey readPublicKey(String path) throws EncodingException, StreamException {
      return readPublicKey(new File(path));
   }

   public static PublicKey readPublicKey(File file) throws EncodingException, StreamException {
      try {
         return readPublicKey((InputStream)(new FileInputStream(file)));
      } catch (FileNotFoundException var2) {
         throw new StreamException("File not found: " + file);
      }
   }

   public static PublicKey readPublicKey(InputStream in) throws EncodingException, StreamException {
      return decodePublicKey(StreamUtil.readAll(in));
   }

   public static PublicKey decodePublicKey(byte[] encoded) throws EncodingException {
      return Converter.convertPublicKey((new PublicKeyDecoder()).decode(encoded));
   }
}
