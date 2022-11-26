package org.python.bouncycastle.cms;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.util.io.Streams;

public class CMSTypedStream {
   private static final int BUF_SIZ = 32768;
   private final ASN1ObjectIdentifier _oid;
   protected InputStream _in;

   public CMSTypedStream(InputStream var1) {
      this((String)PKCSObjectIdentifiers.data.getId(), var1, 32768);
   }

   public CMSTypedStream(String var1, InputStream var2) {
      this((ASN1ObjectIdentifier)(new ASN1ObjectIdentifier(var1)), var2, 32768);
   }

   public CMSTypedStream(String var1, InputStream var2, int var3) {
      this(new ASN1ObjectIdentifier(var1), var2, var3);
   }

   public CMSTypedStream(ASN1ObjectIdentifier var1, InputStream var2) {
      this((ASN1ObjectIdentifier)var1, var2, 32768);
   }

   public CMSTypedStream(ASN1ObjectIdentifier var1, InputStream var2, int var3) {
      this._oid = var1;
      this._in = new FullReaderStream(new BufferedInputStream(var2, var3));
   }

   protected CMSTypedStream(ASN1ObjectIdentifier var1) {
      this._oid = var1;
   }

   public ASN1ObjectIdentifier getContentType() {
      return this._oid;
   }

   public InputStream getContentStream() {
      return this._in;
   }

   public void drain() throws IOException {
      Streams.drain(this._in);
      this._in.close();
   }

   private static class FullReaderStream extends FilterInputStream {
      FullReaderStream(InputStream var1) {
         super(var1);
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         int var4 = Streams.readFully(super.in, var1, var2, var3);
         return var4 > 0 ? var4 : -1;
      }
   }
}
