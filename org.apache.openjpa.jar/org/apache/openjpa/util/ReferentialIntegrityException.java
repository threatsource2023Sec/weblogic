package org.apache.openjpa.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.apache.openjpa.lib.util.Localizer;

public class ReferentialIntegrityException extends StoreException {
   public static final int IV_UNKNOWN = 0;
   public static final int IV_DUPLICATE_OID = 1;
   public static final int IV_UNIQUE = 2;
   public static final int IV_REFERENCE = 3;
   public static final int IV_MIXED = 4;
   private static final transient Localizer _loc = Localizer.forPackage(ReferentialIntegrityException.class);
   private int _iv;

   public ReferentialIntegrityException(String msg) {
      super(msg);
      this._iv = 0;
   }

   public ReferentialIntegrityException(int iv) {
      this(getMessage(iv));
      this.setIntegrityViolation(iv);
   }

   private static String getMessage(int iv) {
      switch (iv) {
         case 1:
            return _loc.get("dup-oid").getMessage();
         case 2:
            return _loc.get("unique").getMessage();
         default:
            return _loc.get("ref-integrity").getMessage();
      }
   }

   public int getSubtype() {
      return 4;
   }

   public int getIntegrityViolation() {
      return this._iv;
   }

   public ReferentialIntegrityException setIntegrityViolation(int iv) {
      this._iv = iv;
      return this;
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeInt(this._iv);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this._iv = in.readInt();
   }
}
