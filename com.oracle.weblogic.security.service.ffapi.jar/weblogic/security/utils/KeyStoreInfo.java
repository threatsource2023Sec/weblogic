package weblogic.security.utils;

import java.security.KeyStore;
import java.util.Arrays;

public final class KeyStoreInfo {
   private String filename;
   private String type;
   private char[] passphrase;

   KeyStoreInfo(String filename, String type, String passphrase) {
      this(filename, type, passphrase != null && passphrase.length() > 0 ? passphrase.toCharArray() : null);
   }

   KeyStoreInfo(String filename, String type, char[] passphrase) {
      this.filename = filename;
      this.type = type != null && type.length() > 0 ? type : KeyStore.getDefaultType();
      this.passphrase = passphrase != null && passphrase.length > 0 ? passphrase : null;
   }

   KeyStoreInfo(String filename, String type) {
      this(filename, type, (char[])null);
   }

   public String getFileName() {
      return this.filename;
   }

   public String getType() {
      return this.type;
   }

   public char[] getPassPhrase() {
      return this.passphrase;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (!(other instanceof KeyStoreInfo)) {
         return false;
      } else {
         KeyStoreInfo ksi = (KeyStoreInfo)other;
         return equals(this.filename, ksi.filename) && equals(this.type, ksi.type) && Arrays.equals(this.passphrase, ksi.passphrase);
      }
   }

   private static final boolean equals(String v1, String v2) {
      return v1 == v2 || v1 != null && v1.equals(v2);
   }

   public int hashCode() {
      int h = this.filename == null ? 1 : this.filename.hashCode();
      h = h * 31 + (this.type == null ? 1 : this.type.hashCode());
      h = h * 31 + Arrays.hashCode(this.passphrase);
      return h;
   }

   public String toString() {
      return "FileName=" + this.filename + ", Type=" + this.type + ", PassPhraseUsed=" + (this.passphrase != null);
   }
}
