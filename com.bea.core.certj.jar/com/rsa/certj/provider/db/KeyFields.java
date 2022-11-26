package com.rsa.certj.provider.db;

import codebase.Data4jni;
import codebase.Error4;
import codebase.Field4byteArray;
import codebase.Field4deleteFlag;
import com.rsa.certj.spi.db.DatabaseException;
import java.io.IOException;

final class KeyFields {
   Field4byteArray a;
   Field4byteArray b;
   Field4byteArray c;
   Field4byteArray d;
   Field4deleteFlag e;

   KeyFields(Data4jni var1) throws DatabaseException {
      try {
         this.a = new Field4byteArray(var1, "SPKI");
         this.b = new Field4byteArray(var1, "SALT");
         this.c = new Field4byteArray(var1, "IV");
         this.d = new Field4byteArray(var1, "KEY");
         this.e = new Field4deleteFlag(var1);
      } catch (IOException var3) {
         throw new DatabaseException("KeyFields.KeyFields.", var3);
      } catch (Error4 var4) {
         throw new DatabaseException("KeyFields.KeyFields: " + NativeDB.error4Message(var4));
      }
   }
}
