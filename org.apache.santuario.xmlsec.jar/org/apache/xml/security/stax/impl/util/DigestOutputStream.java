package org.apache.xml.security.stax.impl.util;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DigestOutputStream extends OutputStream {
   protected static final transient Logger LOG = LoggerFactory.getLogger(DigestOutputStream.class);
   protected static final transient boolean isDebugEnabled;
   private final MessageDigest messageDigest;
   private StringBuilder stringBuilder;

   public DigestOutputStream(MessageDigest messageDigest) {
      this.messageDigest = messageDigest;
      if (isDebugEnabled) {
         this.stringBuilder = new StringBuilder();
      }

   }

   public void write(byte[] arg0) {
      this.write(arg0, 0, arg0.length);
   }

   public void write(int arg0) {
      byte asByte = (byte)arg0;
      this.messageDigest.update(asByte);
      if (isDebugEnabled) {
         this.stringBuilder.append((char)asByte);
      }

   }

   public void write(byte[] arg0, int arg1, int arg2) {
      this.messageDigest.update(arg0, arg1, arg2);
      if (isDebugEnabled) {
         this.stringBuilder.append(new String(arg0, arg1, arg2, StandardCharsets.UTF_8));
      }

   }

   public byte[] getDigestValue() {
      if (isDebugEnabled) {
         LOG.debug("Pre Digest: ");
         LOG.debug(this.stringBuilder.toString());
         LOG.debug("End pre Digest ");
         this.stringBuilder = new StringBuilder();
      }

      return this.messageDigest.digest();
   }

   static {
      isDebugEnabled = LOG.isDebugEnabled();
   }
}
