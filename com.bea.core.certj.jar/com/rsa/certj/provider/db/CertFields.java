package com.rsa.certj.provider.db;

import codebase.Data4jni;
import codebase.Error4;
import codebase.Field4byteArray;
import codebase.Field4deleteFlag;
import com.rsa.certj.spi.db.DatabaseException;
import java.io.IOException;

final class CertFields {
   Field4byteArray a;
   Field4byteArray b;
   Field4byteArray c;
   Field4byteArray d;
   Field4deleteFlag e;

   CertFields(Data4jni var1) throws DatabaseException {
      try {
         this.a = new Field4byteArray(var1, "SUBJECT");
         this.b = new Field4byteArray(var1, "ISSUER");
         this.c = new Field4byteArray(var1, "SERIAL");
         this.d = new Field4byteArray(var1, "CERT");
         this.e = new Field4deleteFlag(var1);
      } catch (IOException var3) {
         throw new DatabaseException("CertFields.CertFields.", var3);
      } catch (Error4 var4) {
         throw new DatabaseException("CertFields.CertFields: " + NativeDB.error4Message(var4));
      }
   }
}
