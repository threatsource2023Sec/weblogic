package org.cryptacular.bean;

import org.bouncycastle.util.Arrays;
import org.cryptacular.CryptoException;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.codec.Codec;
import org.cryptacular.spec.Spec;
import org.cryptacular.util.CodecUtil;

public class EncodingHashBean extends AbstractHashBean implements HashBean {
   private Spec codecSpec;
   private boolean salted;

   public EncodingHashBean() {
   }

   public EncodingHashBean(Spec codecSpec, Spec digestSpec) {
      this(codecSpec, digestSpec, 1, false);
   }

   public EncodingHashBean(Spec codecSpec, Spec digestSpec, int iterations) {
      this(codecSpec, digestSpec, iterations, false);
   }

   public EncodingHashBean(Spec codecSpec, Spec digestSpec, int iterations, boolean salted) {
      super(digestSpec, iterations);
      this.setCodecSpec(codecSpec);
      this.setSalted(salted);
   }

   public Spec getCodecSpec() {
      return this.codecSpec;
   }

   public void setCodecSpec(Spec codecSpec) {
      this.codecSpec = codecSpec;
   }

   public boolean isSalted() {
      return this.salted;
   }

   public void setSalted(boolean salted) {
      this.salted = salted;
   }

   public String hash(Object... data) throws CryptoException, EncodingException, StreamException {
      if (this.salted) {
         if (data.length >= 2 && data[data.length - 1] instanceof byte[]) {
            byte[] hashSalt = (byte[])((byte[])data[data.length - 1]);
            return CodecUtil.encode(((Codec)this.codecSpec.newInstance()).getEncoder(), Arrays.concatenate(this.hashInternal(data), hashSalt));
         } else {
            throw new IllegalArgumentException("Last parameter must be a salt of type byte[]");
         }
      } else {
         return CodecUtil.encode(((Codec)this.codecSpec.newInstance()).getEncoder(), this.hashInternal(data));
      }
   }

   public boolean compare(String hash, Object... data) throws CryptoException, EncodingException, StreamException {
      return this.compareInternal(CodecUtil.decode(((Codec)this.codecSpec.newInstance()).getDecoder(), hash), data);
   }
}
