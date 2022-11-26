package org.python.bouncycastle.cms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;

public class CMSProcessableFile implements CMSTypedData, CMSReadable {
   private static final int DEFAULT_BUF_SIZE = 32768;
   private final ASN1ObjectIdentifier type;
   private final File file;
   private final byte[] buf;

   public CMSProcessableFile(File var1) {
      this(var1, 32768);
   }

   public CMSProcessableFile(File var1, int var2) {
      this(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), var1, var2);
   }

   public CMSProcessableFile(ASN1ObjectIdentifier var1, File var2, int var3) {
      this.type = var1;
      this.file = var2;
      this.buf = new byte[var3];
   }

   public InputStream getInputStream() throws IOException, CMSException {
      return new BufferedInputStream(new FileInputStream(this.file), 32768);
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      FileInputStream var2 = new FileInputStream(this.file);

      int var3;
      while((var3 = var2.read(this.buf, 0, this.buf.length)) > 0) {
         var1.write(this.buf, 0, var3);
      }

      var2.close();
   }

   public Object getContent() {
      return this.file;
   }

   public ASN1ObjectIdentifier getContentType() {
      return this.type;
   }
}
