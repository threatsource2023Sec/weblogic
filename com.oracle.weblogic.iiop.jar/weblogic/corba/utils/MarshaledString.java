package weblogic.corba.utils;

import java.io.Serializable;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public class MarshaledString implements Serializable {
   static final int MAX_STRING_SIZE = 524288;
   public static final MarshaledString EMPTY = new MarshaledString("");
   protected byte[] encodedString;
   private String string;
   protected int hash;

   public MarshaledString(InputStream in, int len) {
      this.read(in, len);
   }

   MarshaledString(MarshaledString mString) {
      this.encodedString = mString.encodedString;
      this.string = mString.string;
      this.hash = mString.hash;
   }

   public MarshaledString(InputStream in) {
      this.read(in, in.read_ulong());
   }

   MarshaledString() {
   }

   public MarshaledString(String str) {
      this.string = str;
      int len = this.string.length();
      this.encodedString = new byte[len];
      this.string.getBytes(0, len, this.encodedString, 0);
   }

   public final int length() {
      return this.encodedString.length;
   }

   public final byte[] getEncoded() {
      return this.encodedString;
   }

   public int hashCode() {
      if (this.hash == 0) {
         synchronized(this) {
            if (this.hash == 0) {
               int h = 0;
               int len = this.encodedString.length;

               for(int i = 0; i < len; ++i) {
                  h = 31 * h + this.encodedString[i];
               }

               this.hash = h;
            }
         }
      }

      return this.hash;
   }

   public boolean equals(Object other) {
      if (!(other instanceof MarshaledString)) {
         return false;
      } else {
         MarshaledString rep = (MarshaledString)other;
         return this.compareStrings(rep);
      }
   }

   public boolean compareStrings(MarshaledString other) {
      if (other == this) {
         return true;
      } else if (other != null && this.hashCode() == other.hashCode()) {
         if (other.encodedString.length == this.encodedString.length) {
            int i = this.encodedString.length;

            do {
               if (i-- <= 0) {
                  return true;
               }
            } while(this.encodedString[i] == other.encodedString[i]);

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public final String toString() {
      if (this.string == null) {
         this.string = new String(this.encodedString, 0);
      }

      return this.string;
   }

   public final void write(OutputStream out) {
      out.write_ulong(this.encodedString.length + 1);
      out.write_octet_array(this.encodedString, 0, this.encodedString.length);
      out.write_octet((byte)0);
   }

   public final void read(InputStream in, int len) {
      if (len > 524288) {
         throw new MARSHAL("Stream corrupted: tried to read string of length " + Integer.toHexString(len));
      } else {
         this.encodedString = new byte[len - 1];
         in.read_octet_array(this.encodedString, 0, this.encodedString.length);
         in.read_octet();
      }
   }
}
