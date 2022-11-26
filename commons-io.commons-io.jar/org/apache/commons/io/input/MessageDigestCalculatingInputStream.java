package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestCalculatingInputStream extends ObservableInputStream {
   private final MessageDigest messageDigest;

   public MessageDigestCalculatingInputStream(InputStream pStream, MessageDigest pDigest) {
      super(pStream);
      this.messageDigest = pDigest;
      this.add(new MessageDigestMaintainingObserver(pDigest));
   }

   public MessageDigestCalculatingInputStream(InputStream pStream, String pAlgorithm) throws NoSuchAlgorithmException {
      this(pStream, MessageDigest.getInstance(pAlgorithm));
   }

   public MessageDigestCalculatingInputStream(InputStream pStream) throws NoSuchAlgorithmException {
      this(pStream, MessageDigest.getInstance("MD5"));
   }

   public MessageDigest getMessageDigest() {
      return this.messageDigest;
   }

   public static class MessageDigestMaintainingObserver extends ObservableInputStream.Observer {
      private final MessageDigest md;

      public MessageDigestMaintainingObserver(MessageDigest pMd) {
         this.md = pMd;
      }

      void data(int pByte) throws IOException {
         this.md.update((byte)pByte);
      }

      void data(byte[] pBuffer, int pOffset, int pLength) throws IOException {
         this.md.update(pBuffer, pOffset, pLength);
      }
   }
}
