package org.cryptacular.spec;

import org.cryptacular.codec.Base32Codec;
import org.cryptacular.codec.Base64Codec;
import org.cryptacular.codec.Codec;
import org.cryptacular.codec.HexCodec;

public class CodecSpec implements Spec {
   public static final CodecSpec HEX = new CodecSpec("Hex");
   public static final CodecSpec HEX_LOWER = new CodecSpec("Hex-Lower");
   public static final CodecSpec HEX_UPPER = new CodecSpec("Hex-Upper");
   public static final CodecSpec BASE32 = new CodecSpec("Base32");
   public static final CodecSpec BASE32_UNPADDED = new CodecSpec("Base32-Unpadded");
   public static final CodecSpec BASE64 = new CodecSpec("Base64");
   public static final CodecSpec BASE64_URLSAFE = new CodecSpec("Base64-URLSafe");
   public static final CodecSpec BASE64_UNPADDED = new CodecSpec("Base64-Unpadded");
   private String encoding;

   public CodecSpec(String encoding) {
      if (encoding == null) {
         throw new IllegalArgumentException("Encoding cannot be null.");
      } else {
         this.encoding = encoding;
      }
   }

   public String getAlgorithm() {
      return this.encoding;
   }

   public Codec newInstance() {
      Object codec;
      if (!"Hex".equalsIgnoreCase(this.encoding) && !"Hex-Lower".equalsIgnoreCase(this.encoding)) {
         if ("Hex-Upper".equalsIgnoreCase(this.encoding)) {
            codec = new HexCodec(true);
         } else if (!"Base32".equalsIgnoreCase(this.encoding) && !"Base-32".equalsIgnoreCase(this.encoding)) {
            if ("Base32-Unpadded".equalsIgnoreCase(this.encoding)) {
               codec = new Base32Codec("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", true);
            } else if (!"Base64".equalsIgnoreCase(this.encoding) && !"Base-64".equalsIgnoreCase(this.encoding)) {
               if ("Base64-URLSafe".equalsIgnoreCase(this.encoding)) {
                  codec = new Base64Codec("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_");
               } else {
                  if (!"Base64-Unpadded".equalsIgnoreCase(this.encoding)) {
                     throw new IllegalArgumentException("Invalid encoding.");
                  }

                  codec = new Base64Codec("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", false);
               }
            } else {
               codec = new Base64Codec();
            }
         } else {
            codec = new Base32Codec();
         }
      } else {
         codec = new HexCodec();
      }

      return (Codec)codec;
   }

   public String toString() {
      return this.encoding;
   }
}
