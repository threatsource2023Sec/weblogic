package net.shibboleth.utilities.java.support.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyException;
import java.security.SecureRandom;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.ConstraintViolationException;
import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSealer extends AbstractInitializableComponent {
   private static final int CHUNK_SIZE = 60000;
   @Nonnull
   private Logger log = LoggerFactory.getLogger(DataSealer.class);
   private boolean lockedAtStartup;
   @NonnullAfterInit
   private DataSealerKeyStrategy keyStrategy;
   @NonnullAfterInit
   private SecureRandom random;
   @Nonnull
   private BinaryEncoder encoder = new Base64(0, new byte[]{10});
   @Nonnull
   private BinaryDecoder decoder;

   public DataSealer() {
      this.decoder = (Base64)this.encoder;
   }

   public void setLockedAtStartup(boolean flag) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.lockedAtStartup = flag;
   }

   public void setKeyStrategy(@Nonnull DataSealerKeyStrategy strategy) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keyStrategy = (DataSealerKeyStrategy)Constraint.isNotNull(strategy, "DataSealerKeyStrategy cannot be null");
   }

   public void setRandom(@Nonnull SecureRandom r) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.random = (SecureRandom)Constraint.isNotNull(r, "SecureRandom cannot be null");
   }

   public void setEncoder(@Nonnull BinaryEncoder e) {
      this.encoder = (BinaryEncoder)Constraint.isNotNull(e, "Encoder cannot be null");
   }

   public void setDecoder(@Nonnull BinaryDecoder d) {
      this.decoder = (BinaryDecoder)Constraint.isNotNull(d, "Decoder cannot be null");
   }

   public void doInitialize() throws ComponentInitializationException {
      try {
         try {
            Constraint.isNotNull(this.keyStrategy, "DataSealerKeyStrategy cannot be null");
         } catch (ConstraintViolationException var2) {
            throw new ComponentInitializationException(var2);
         }

         if (this.random == null) {
            this.random = new SecureRandom();
         }

         if (!this.lockedAtStartup) {
            this.testEncryption((SecretKey)this.keyStrategy.getDefaultKey().getSecond());
         }

      } catch (KeyException var3) {
         this.log.error(var3.getMessage());
         throw new ComponentInitializationException("Exception loading the keystore", var3);
      } catch (DataSealerException var4) {
         this.log.error(var4.getMessage());
         throw new ComponentInitializationException("Exception testing the encryption settings used", var4);
      }
   }

   @Nonnull
   public String unwrap(@Nonnull @NotEmpty String wrapped) throws DataSealerException {
      return this.unwrap(wrapped, (StringBuffer)null);
   }

   @Nonnull
   public String unwrap(@Nonnull @NotEmpty String wrapped, @Nullable StringBuffer keyUsed) throws DataSealerException {
      try {
         byte[] in = this.decoder.decode(wrapped.getBytes(StandardCharsets.UTF_8));
         ByteArrayInputStream inputByteStream = new ByteArrayInputStream(in);
         DataInputStream inputDataStream = new DataInputStream(inputByteStream);
         String keyAlias = inputDataStream.readUTF();
         this.log.trace("Data was encrypted by key named '{}'", keyAlias);
         if (keyUsed != null) {
            keyUsed.append(keyAlias);
         }

         SecretKey key = this.keyStrategy.getKey(keyAlias);
         GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
         int ivSize = cipher.getUnderlyingCipher().getBlockSize();
         byte[] iv = new byte[ivSize];
         inputDataStream.readFully(iv);
         AEADParameters aeadParams = new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, keyAlias.getBytes());
         cipher.init(false, aeadParams);
         byte[] data = new byte[in.length - ivSize];
         int dataSize = inputDataStream.read(data);
         byte[] plaintext = new byte[cipher.getOutputSize(dataSize)];
         int outputLen = cipher.processBytes(data, 0, dataSize, plaintext, 0);
         cipher.doFinal(plaintext, outputLen);
         return this.extractAndCheckDecryptedData(plaintext);
      } catch (InvalidCipherTextException | IOException | DecoderException | IllegalStateException var16) {
         this.log.error("Exception unwrapping data", var16);
         throw new DataSealerException("Exception unwrapping data", var16);
      } catch (KeyNotFoundException var17) {
         if (keyUsed != null) {
            this.log.info("Data was wrapped with a key ({}) no longer available", keyUsed.toString());
         } else {
            this.log.info("Data was wrapped with a key no longer available");
         }

         throw new DataExpiredException("Data wrapped with expired key");
      } catch (KeyException var18) {
         this.log.error(var18.getMessage());
         throw new DataSealerException("Exception loading key", var18);
      }
   }

   @Nonnull
   private String extractAndCheckDecryptedData(@Nonnull @NotEmpty byte[] decryptedBytes) throws DataSealerException {
      try {
         ByteArrayInputStream byteStream = new ByteArrayInputStream(decryptedBytes);
         GZIPInputStream compressedData = new GZIPInputStream(byteStream);
         DataInputStream dataInputStream = new DataInputStream(compressedData);
         long decodedExpirationTime = dataInputStream.readLong();
         if (System.currentTimeMillis() > decodedExpirationTime) {
            this.log.debug("Unwrapped data has expired");
            throw new DataExpiredException("Unwrapped data has expired");
         } else {
            StringBuffer accumulator = new StringBuffer();
            int count = 0;

            while(true) {
               try {
                  String decodedData = dataInputStream.readUTF();
                  accumulator.append(decodedData);
                  ++count;
                  this.log.trace("Read chunk #{} from output stream", count);
               } catch (EOFException var10) {
                  this.log.trace("Unwrapped data verified");
                  return accumulator.toString();
               }
            }
         }
      } catch (IOException var11) {
         this.log.error(var11.getMessage());
         throw new DataSealerException("Caught IOException unwrapping data", var11);
      }
   }

   @Nonnull
   public String wrap(@Nonnull @NotEmpty String data, long exp) throws DataSealerException {
      if (data != null && data.length() != 0) {
         try {
            GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
            byte[] iv = new byte[cipher.getUnderlyingCipher().getBlockSize()];
            this.random.nextBytes(iv);
            Pair defaultKey = this.keyStrategy.getDefaultKey();
            AEADParameters aeadParams = new AEADParameters(new KeyParameter(((SecretKey)defaultKey.getSecond()).getEncoded()), 128, iv, ((String)defaultKey.getFirst()).getBytes());
            cipher.init(true, aeadParams);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            GZIPOutputStream compressedStream = new GZIPOutputStream(byteStream);
            DataOutputStream dataStream = new DataOutputStream(compressedStream);
            dataStream.writeLong(exp);
            int count = 0;
            int start = 0;
            int dataLength = data.length();

            while(start < dataLength) {
               dataStream.writeUTF(data.substring(start, start + Math.min(dataLength - start, 60000)));
               start += Math.min(dataLength - start, 60000);
               ++count;
               this.log.trace("Wrote chunk #{} to output stream", count);
            }

            dataStream.flush();
            compressedStream.flush();
            compressedStream.finish();
            byteStream.flush();
            byte[] plaintext = byteStream.toByteArray();
            byte[] encryptedData = new byte[cipher.getOutputSize(plaintext.length)];
            int outputLen = cipher.processBytes(plaintext, 0, plaintext.length, encryptedData, 0);
            outputLen += cipher.doFinal(encryptedData, outputLen);
            ByteArrayOutputStream finalByteStream = new ByteArrayOutputStream();
            DataOutputStream finalDataStream = new DataOutputStream(finalByteStream);
            finalDataStream.writeUTF((String)defaultKey.getFirst());
            finalDataStream.write(iv);
            finalDataStream.write(encryptedData, 0, outputLen);
            finalDataStream.flush();
            finalByteStream.flush();
            return new String(this.encoder.encode(finalByteStream.toByteArray()), StandardCharsets.UTF_8);
         } catch (Exception var19) {
            this.log.error("Exception wrapping data", var19);
            throw new DataSealerException("Exception wrapping data", var19);
         }
      } else {
         throw new IllegalArgumentException("Data must be supplied for the wrapping operation");
      }
   }

   private void testEncryption(@Nonnull SecretKey key) throws DataSealerException {
      String decrypted;
      try {
         GCMBlockCipher cipher = new GCMBlockCipher(new AESEngine());
         byte[] iv = new byte[cipher.getUnderlyingCipher().getBlockSize()];
         this.random.nextBytes(iv);
         AEADParameters aeadParams = new AEADParameters(new KeyParameter(key.getEncoded()), 128, iv, "aad".getBytes(StandardCharsets.UTF_8));
         cipher.init(true, aeadParams);
         byte[] plaintext = "test".getBytes(StandardCharsets.UTF_8);
         byte[] encryptedData = new byte[cipher.getOutputSize(plaintext.length)];
         int outputLen = cipher.processBytes(plaintext, 0, plaintext.length, encryptedData, 0);
         cipher.doFinal(encryptedData, outputLen);
         cipher.init(false, aeadParams);
         plaintext = new byte[cipher.getOutputSize(encryptedData.length)];
         outputLen = cipher.processBytes(encryptedData, 0, encryptedData.length, plaintext, 0);
         cipher.doFinal(plaintext, outputLen);
         decrypted = Strings.fromUTF8ByteArray(plaintext);
      } catch (InvalidCipherTextException | IllegalStateException var9) {
         this.log.error("Round trip encryption/decryption test unsuccessful", var9);
         throw new DataSealerException("Round trip encryption/decryption test unsuccessful", var9);
      }

      if (decrypted == null || !"test".equals(decrypted)) {
         this.log.error("Round trip encryption/decryption test unsuccessful. Decrypted text did not match");
         throw new DataSealerException("Round trip encryption/decryption test unsuccessful");
      }
   }
}
