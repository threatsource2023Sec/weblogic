package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.BERSequenceGenerator;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.operator.OutputCompressor;

public class CMSCompressedDataStreamGenerator {
   public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";
   private int _bufferSize;

   public void setBufferSize(int var1) {
      this._bufferSize = var1;
   }

   public OutputStream open(OutputStream var1, OutputCompressor var2) throws IOException {
      return this.open(CMSObjectIdentifiers.data, var1, var2);
   }

   public OutputStream open(ASN1ObjectIdentifier var1, OutputStream var2, OutputCompressor var3) throws IOException {
      BERSequenceGenerator var4 = new BERSequenceGenerator(var2);
      var4.addObject(CMSObjectIdentifiers.compressedData);
      BERSequenceGenerator var5 = new BERSequenceGenerator(var4.getRawOutputStream(), 0, true);
      var5.addObject(new ASN1Integer(0L));
      var5.addObject(var3.getAlgorithmIdentifier());
      BERSequenceGenerator var6 = new BERSequenceGenerator(var5.getRawOutputStream());
      var6.addObject(var1);
      OutputStream var7 = CMSUtils.createBEROctetOutputStream(var6.getRawOutputStream(), 0, true, this._bufferSize);
      return new CmsCompressedOutputStream(var3.getOutputStream(var7), var4, var5, var6);
   }

   private class CmsCompressedOutputStream extends OutputStream {
      private OutputStream _out;
      private BERSequenceGenerator _sGen;
      private BERSequenceGenerator _cGen;
      private BERSequenceGenerator _eiGen;

      CmsCompressedOutputStream(OutputStream var2, BERSequenceGenerator var3, BERSequenceGenerator var4, BERSequenceGenerator var5) {
         this._out = var2;
         this._sGen = var3;
         this._cGen = var4;
         this._eiGen = var5;
      }

      public void write(int var1) throws IOException {
         this._out.write(var1);
      }

      public void write(byte[] var1, int var2, int var3) throws IOException {
         this._out.write(var1, var2, var3);
      }

      public void write(byte[] var1) throws IOException {
         this._out.write(var1);
      }

      public void close() throws IOException {
         this._out.close();
         this._eiGen.close();
         this._cGen.close();
         this._sGen.close();
      }
   }
}
