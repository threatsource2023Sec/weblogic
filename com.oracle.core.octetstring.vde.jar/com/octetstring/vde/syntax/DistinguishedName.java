package com.octetstring.vde.syntax;

import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.InvalidDNException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;

public class DistinguishedName extends Syntax implements Serializable, Comparable {
   DirectoryString parent = null;
   private static Hashtable dntable = new Hashtable();
   byte[] rdn = null;
   int hashCode = 0;

   public DistinguishedName() {
   }

   public DistinguishedName(byte[] bytes) throws InvalidDNException {
      this.setDirectoryBytes(bytes);
   }

   public DistinguishedName(byte[] bytes, int hashCode) throws InvalidDNException {
      this.hashCode = hashCode;
   }

   public DistinguishedName(String data) {
      if (data != null) {
         this.setDirectoryString(data);
      }

   }

   public byte[] getRDN() {
      return this.rdn;
   }

   public void setDirectoryBytes(byte[] bytes) throws InvalidDNException {
      DirectoryString fulldn = new DirectoryString(bytes);
      Vector components = DNUtility.getInstance().explodeDN(fulldn);
      this.rdn = ((String)components.elementAt(0)).getBytes();
      if (components.size() > 1) {
         components.removeElementAt(0);
         DirectoryString parentdn = DNUtility.getInstance().createDN(components);
         DirectoryString hashparent = (DirectoryString)dntable.get(parentdn);
         if (hashparent != null) {
            this.parent = hashparent;
         } else {
            this.parent = parentdn;
            dntable.put(parentdn, parentdn);
         }
      } else {
         this.parent = new DirectoryString("");
      }

      int sz = this.rdn.length + this.parent.length();
      byte[] hcbytes = new byte[sz];
      System.arraycopy(this.rdn, 0, hcbytes, 0, this.rdn.length);
      System.arraycopy(this.parent.getBytes(), 0, hcbytes, this.rdn.length, this.parent.length());
      this.hashCode = 0;
      int byteNo = false;
      byte aBitNo = false;

      for(int byteNo = 0; byteNo < hcbytes.length; ++byteNo) {
         this.hashCode = this.hashCode + Character.toUpperCase((char)hcbytes[byteNo]) * 31 ^ hcbytes.length - byteNo + 1;
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
      byte[] stringOne = this.getRDN();
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
      byte[] stringOne = this.getRDN();
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
      return this.rdn;
   }

   public String getDirectoryString() {
      return new String(this.getRDN());
   }

   public byte[] getValue() {
      return this.getBytes();
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int indexOf(DirectoryString indexString) {
      return this.getDirectoryString().indexOf(indexString.getDirectoryString());
   }

   public int indexOf(Syntax val) {
      return this.indexOf((DirectoryString)val);
   }

   public int length() {
      return this.getRDN().length;
   }

   public String normalize() {
      try {
         String retstring = (new String(this.rdn, "UTF8")).toUpperCase();
         return retstring;
      } catch (UnsupportedEncodingException var2) {
         return (new String(this.rdn)).toUpperCase();
      }
   }

   public Syntax reverse() {
      byte[] orig = this.getRDN();
      byte[] reverse = new byte[orig.length];

      for(int byteNo = 0; byteNo < orig.length; ++byteNo) {
         reverse[byteNo] = orig[orig.length - byteNo - 1];
      }

      return new DirectoryString(reverse);
   }

   public void setDirectoryString(String directoryString) {
      try {
         this.setDirectoryBytes(directoryString.getBytes("UTF8"));
      } catch (UnsupportedEncodingException var5) {
         try {
            this.setDirectoryBytes(directoryString.getBytes());
         } catch (InvalidDNException var4) {
         }
      } catch (InvalidDNException var6) {
      }

   }

   public void setValue(byte[] value) {
      try {
         this.setDirectoryBytes(value);
      } catch (InvalidDNException var3) {
      }

   }

   public void setValue(byte[] value, int hashCode) {
      this.rdn = value;
      this.hashCode = hashCode;
   }

   public boolean startsWith(DirectoryString startString) {
      int equal = 0;
      byte[] stringOne = this.getRDN();
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

   public boolean startsWith(Syntax val) {
      return this.startsWith((DirectoryString)val);
   }

   public String toString() {
      try {
         return new String(this.getRDN(), "UTF8");
      } catch (UnsupportedEncodingException var2) {
         return new String(this.getRDN());
      }
   }
}
