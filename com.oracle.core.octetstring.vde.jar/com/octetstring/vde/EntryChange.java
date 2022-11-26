package com.octetstring.vde;

import com.octetstring.nls.Messages;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class EntryChange {
   private int modType;
   public DirectoryString attr;
   public Vector values;
   private static final int byte1 = 255;
   private static final int byte2 = 65280;
   private static final int byte3 = 16711680;
   private static final int byte4 = -16777216;

   public EntryChange() {
   }

   public EntryChange(byte[] entryChangeBytes, boolean oldVersion) {
      this.readBytes(entryChangeBytes, oldVersion);
   }

   public EntryChange(int modType, DirectoryString attr, Vector values) {
      this.setModType(modType);
      this.setAttr(attr);
      this.setValues(values);
   }

   public DirectoryString getAttr() {
      return this.attr;
   }

   public void setAttr(DirectoryString newAttr) {
      this.attr = newAttr;
   }

   public int getModType() {
      return this.modType;
   }

   public void setModType(int newModType) {
      this.modType = newModType;
   }

   public Vector getValues() {
      return this.values;
   }

   public void setValues(Vector newValues) {
      this.values = newValues;
   }

   private int bytesToInt(byte[] eb, int bc) {
      return (eb[bc] & 255) << 24 | (eb[bc + 1] & 255) << 16 | (eb[bc + 2] & 255) << 8 | eb[bc + 3] & 255;
   }

   public byte[] getAsByteArray() {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int hc = false;
      baos.write(this.modType);
      DirectoryString aKey = this.attr;
      baos.write(aKey.length());
      int hc = aKey.hashCode();
      baos.write((hc & -16777216) >> 24);
      baos.write((hc & 16711680) >> 16);
      baos.write((hc & '\uff00') >> 8);
      baos.write(hc & 255);
      baos.write(aKey.getBytes(), 0, aKey.length());
      Vector vals = this.values;
      baos.write(vals.size());
      Enumeration valEnum = vals.elements();
      int valLen = false;

      while(valEnum.hasMoreElements()) {
         Syntax aVal = (Syntax)valEnum.nextElement();
         int valLen = aVal.getValue().length;
         baos.write((valLen & -16777216) >> 24);
         baos.write((valLen & 16711680) >> 16);
         baos.write((valLen & '\uff00') >> 8);
         baos.write(valLen & 255);
         hc = aVal.hashCode();
         baos.write((hc & -16777216) >> 24);
         baos.write((hc & 16711680) >> 16);
         baos.write((hc & '\uff00') >> 8);
         baos.write(hc & 255);
         baos.write(aVal.getValue(), 0, valLen);
      }

      byte[] entryBytes = baos.toByteArray();

      try {
         baos.close();
      } catch (IOException var9) {
         Logger.getInstance().log(0, this, Messages.getString("Error_closing_ByteArrayOutputStream_1"));
      }

      return entryBytes;
   }

   private void readBytes(byte[] eb, boolean oldVersion) {
      int ebSize = null == eb ? 0 : eb.length;
      int bc = 0;
      this.modType = eb[bc];
      ++bc;
      int len = eb[bc];
      ++bc;

      try {
         Entry.checkEntryFieldLength(ebSize, bc, len, "key");
      } catch (IndexOutOfBoundsException var21) {
         BackendHandler.setReplicaInvalid();
         throw var21;
      }

      int keyHC = this.bytesToInt(eb, bc);
      bc += 4;
      byte[] keyBytes = new byte[len];
      System.arraycopy(eb, bc, keyBytes, 0, len);
      bc += len;
      DirectoryString key = new DirectoryString(keyBytes, keyHC);
      this.attr = key;
      Vector values = new Vector();
      int numValues = eb[bc];
      ++bc;
      if (numValues < 0) {
         numValues = (numValues & 127) + 128;
      }

      try {
         Entry.checkEntryFieldLength(ebSize, bc, numValues, "numValues");
      } catch (IndexOutOfBoundsException var20) {
         BackendHandler.setReplicaInvalid();
         throw var20;
      }

      AttributeType at = SchemaChecker.getInstance().getAttributeType(key);
      Class synClass = null;
      if (at != null) {
         synClass = at.getSyntaxClass();
         if (synClass == null) {
            synClass = DirectoryString.class;
         }
      } else {
         synClass = DirectoryString.class;
      }

      for(int valCount = 0; bc < eb.length; bc += len) {
         if (oldVersion) {
            len = eb[bc];
            if (len < 0) {
               len = (eb[bc] & 127) + 128;
            }

            ++bc;
         } else {
            len = this.bytesToInt(eb, bc);
            bc += 4;
         }

         try {
            Entry.checkEntryFieldLength(ebSize, bc, len, "value" + valCount);
         } catch (IndexOutOfBoundsException var19) {
            BackendHandler.setReplicaInvalid();
            throw var19;
         }

         this.bytesToInt(eb, bc);
         bc += 4;
         byte[] valBytes = new byte[len];
         System.arraycopy(eb, bc, valBytes, 0, len);
         Syntax synVal = null;

         try {
            synVal = (Syntax)synClass.newInstance();
         } catch (Exception var18) {
            Logger.getInstance().log(0, this, Messages.getString("Unable_to_create_new_value_with_Syntax____2") + synClass.getName() + "'");
            synVal = new DirectoryString();
         }

         ((Syntax)synVal).setValue(valBytes);
         values.addElement(synVal);
      }

      this.values = values;
   }

   public String toLDIF() {
      StringBuffer echange = new StringBuffer();
      if (this.modType == 0) {
         echange.append("add: ");
      }

      if (this.modType == 1) {
         echange.append("replace: ");
      }

      if (this.modType == 2) {
         echange.append("delete: ");
      }

      echange.append(this.attr).append("\n");
      Enumeration valEnum = this.values.elements();

      while(valEnum.hasMoreElements()) {
         echange.append(this.attr).append(": ").append(valEnum.nextElement()).append("\n");
      }

      echange.append("-\n");
      return echange.toString();
   }
}
