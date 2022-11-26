package com.octetstring.vde.syntax;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

public class DirectoryString extends Syntax implements Serializable, Comparable {
   byte[] directoryBytes;
   int hashCode = 0;
   private String directoryString = null;

   public DirectoryString() {
   }

   public DirectoryString(byte[] bytes) {
      this.setDirectoryBytes(bytes);
   }

   public DirectoryString(byte[] bytes, int hashCode) {
      this.directoryBytes = bytes;
      this.hashCode = hashCode;
   }

   public DirectoryString(String data) {
      if (data != null) {
         this.setDirectoryString(data);
      }

   }

   public byte[] getDirectoryBytes() {
      return this.directoryBytes;
   }

   public void setDirectoryBytes(byte[] directoryBytes) {
      this.directoryString = null;
      this.directoryBytes = directoryBytes;
      this.hashCode = 0;
      int byteNo = false;
      if (directoryBytes != null) {
         for(int byteNo = 0; byteNo < directoryBytes.length; ++byteNo) {
            this.hashCode = this.hashCode + Character.toUpperCase((char)directoryBytes[byteNo]) * 31 ^ directoryBytes.length - byteNo + 1;
         }
      }

   }

   public int compareTo(DirectoryString ds) {
      return this.compareToIgnoreCase(ds);
   }

   public int compareTo(Syntax val) {
      return this.compareToIgnoreCase((DirectoryString)val);
   }

   public int compareTo(Object obj) {
      return this.compareToIgnoreCase((DirectoryString)obj);
   }

   private int compareToIgnoreCase(DirectoryString compDS) {
      int equal = 0;
      byte[] stringOne = this.getDirectoryBytes();
      byte[] stringTwo = compDS.getDirectoryBytes();
      int stwoSize = stringTwo.length;

      for(int byteNo = 0; byteNo < stringOne.length; ++byteNo) {
         if (byteNo >= stwoSize) {
            equal = 1;
            break;
         }

         equal = Character.toLowerCase((char)stringOne[byteNo]) - Character.toLowerCase((char)stringTwo[byteNo]);
         if (equal != 0) {
            break;
         }
      }

      if (equal == 0 && stwoSize > stringOne.length) {
         equal = -1;
      }

      return equal;
   }

   public boolean endsWith(DirectoryString endsString) {
      int equal = 0;
      byte[] stringOne = this.getDirectoryBytes();
      byte[] stringTwo = endsString.getDirectoryBytes();
      int soneSize = stringOne.length;
      int stwoSize = stringTwo.length;
      if (stwoSize > soneSize) {
         return false;
      } else {
         int startOne = soneSize - stwoSize;

         for(int byteNo = stwoSize - 1; byteNo >= 0; --byteNo) {
            equal = Character.toLowerCase((char)stringOne[startOne + byteNo]) - Character.toLowerCase((char)stringTwo[byteNo]);
            if (equal != 0) {
               break;
            }
         }

         return equal == 0;
      }
   }

   public boolean endsWith(Syntax val) {
      return this.endsWith((DirectoryString)val);
   }

   public boolean equals(DirectoryString ds) {
      if (this.hashCode != ds.hashCode()) {
         return false;
      } else {
         return this.compareToIgnoreCase(ds) == 0;
      }
   }

   public boolean equals(Object obj) {
      if (this.hashCode != obj.hashCode()) {
         return false;
      } else {
         return this.compareToIgnoreCase((DirectoryString)obj) == 0;
      }
   }

   public byte[] getBytes() {
      return this.directoryBytes;
   }

   public String getDirectoryString() {
      if (this.directoryString == null && this.getDirectoryBytes() != null) {
         try {
            this.directoryString = new String(this.getDirectoryBytes(), "UTF8");
         } catch (UnsupportedEncodingException var2) {
            this.directoryString = new String(this.getDirectoryBytes());
         }
      }

      return this.directoryString;
   }

   public byte[] getValue() {
      return this.getBytes();
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int indexOf(DirectoryString indexString) {
      byte[] thebytes = indexString.getDirectoryBytes();
      if (thebytes.length >= 1 && this.directoryBytes.length >= 1) {
         byte tb = thebytes[0];

         for(int i = 0; i < this.directoryBytes.length; ++i) {
            if (this.directoryBytes[i] == tb) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   public int indexOf(Syntax val) {
      return this.indexOf((DirectoryString)val);
   }

   public int length() {
      return this.getDirectoryBytes().length;
   }

   public String normalize() {
      return this.getDirectoryString().toUpperCase();
   }

   public Syntax reverse() {
      byte[] orig = this.getDirectoryBytes();
      byte[] reverse = new byte[orig.length];

      for(int byteNo = 0; byteNo < orig.length; ++byteNo) {
         reverse[byteNo] = orig[orig.length - byteNo - 1];
      }

      return new DirectoryString(reverse);
   }

   public void setDirectoryString(String directoryString) {
      try {
         this.setDirectoryBytes(directoryString.getBytes("UTF8"));
      } catch (UnsupportedEncodingException var3) {
         this.setDirectoryBytes(directoryString.getBytes());
      }

      this.directoryString = directoryString;
   }

   public void setValue(byte[] value) {
      this.setDirectoryBytes(value);
   }

   public void setValue(byte[] value, int hashCode) {
      this.directoryString = null;
      this.directoryBytes = value;
      this.hashCode = hashCode;
   }

   public boolean startsWith(DirectoryString startString) {
      int equal = 0;
      byte[] stringOne = this.getDirectoryBytes();
      byte[] stringTwo = startString.getDirectoryBytes();
      int soneSize = stringOne.length;
      int stwoSize = stringTwo.length;
      if (stwoSize > soneSize) {
         return false;
      } else {
         for(int byteNo = 0; byteNo < stwoSize; ++byteNo) {
            equal = Character.toLowerCase((char)stringOne[byteNo]) - Character.toLowerCase((char)stringTwo[byteNo]);
            if (equal != 0) {
               break;
            }
         }

         return equal == 0;
      }
   }

   public DirectoryString substring(int first, int last) {
      if (last > first && first <= this.directoryBytes.length && last <= this.directoryBytes.length) {
         byte[] newarray = new byte[last - first];
         System.arraycopy(this.directoryBytes, first, newarray, 0, last - first);
         return new DirectoryString(newarray);
      } else {
         return new DirectoryString("");
      }
   }

   public boolean startsWith(Syntax val) {
      return this.startsWith((DirectoryString)val);
   }

   public String toString() {
      return this.getDirectoryString();
   }
}
