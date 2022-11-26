package com.octetstring.vde;

import com.octetstring.nls.Messages;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.backend.standard.BackendStandard;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.EncryptionHelper;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Vector;

public class Entry implements Serializable {
   private DirectoryString name = null;
   private DirectoryString base = null;
   private int id = -1;
   private static final DirectoryString OBJECTCLASS = new DirectoryString("objectclass");
   private static final int byte1 = 255;
   private static final int byte2 = 65280;
   private static final int byte3 = 16711680;
   private static final int byte4 = -16777216;
   private Vector attributes = null;

   public Entry() {
      this.attributes = new Vector();
   }

   public Entry(byte[] entryBytes) {
      this.attributes = new Vector();
      this.readBytes(entryBytes);
   }

   public Entry(Entry entry) {
      this.id = entry.getID();

      try {
         this.setName(entry.getName(), true);
         this.setBase(entry.getBase());
      } catch (InvalidDNException var3) {
      }

      this.attributes = new Vector();
      Enumeration attrs = entry.getAttributes().elements();

      while(attrs.hasMoreElements()) {
         this.attributes.addElement(((Attribute)attrs.nextElement()).clone());
      }

   }

   public Entry(DirectoryString name) throws InvalidDNException {
      this.setName(name);
      this.attributes = new Vector();
   }

   public Vector getAttributes() {
      return this.attributes;
   }

   public void setAttributes(Vector attributes) {
      this.attributes = attributes;
   }

   public DirectoryString getBase() {
      return this.base;
   }

   public void setBase(DirectoryString base) {
      this.base = base;
   }

   public DirectoryString getName() {
      return this.name;
   }

   public void setName(DirectoryString name) throws InvalidDNException {
      Vector rdnComponents = DNUtility.getInstance().explodeDN(name);
      this.name = DNUtility.getInstance().createDN(rdnComponents);
      if (!rdnComponents.isEmpty()) {
         rdnComponents.removeElementAt(0);
      }

      this.setBase(DNUtility.getInstance().createDN(rdnComponents));
      if (Logger.getInstance().isLogable(9)) {
         Logger.getInstance().log(9, this, Messages.getString("Entry_Name_Constructed_for____2") + name + "'");
      }

   }

   public void setName(DirectoryString name, boolean bypassConstruct) throws InvalidDNException {
      if (bypassConstruct) {
         this.name = name;
      } else {
         this.setName(name);
      }

   }

   private static int bytesToInt(byte[] eb, int bc) {
      return (eb[bc] & 255) << 24 | (eb[bc + 1] & 255) << 16 | (eb[bc + 2] & 255) << 8 | eb[bc + 3] & 255;
   }

   public Object clone() {
      return new Entry(this);
   }

   public boolean containsKey(DirectoryString key) {
      Enumeration attrEnum = this.attributes.elements();

      Attribute thisAttr;
      do {
         if (!attrEnum.hasMoreElements()) {
            return false;
         }

         thisAttr = (Attribute)attrEnum.nextElement();
      } while(!thisAttr.equals(key));

      return true;
   }

   public Vector get(DirectoryString key) {
      Enumeration attrEnum = this.attributes.elements();

      Attribute thisAttr;
      do {
         if (!attrEnum.hasMoreElements()) {
            return null;
         }

         thisAttr = (Attribute)attrEnum.nextElement();
      } while(!thisAttr.equals(key));

      return thisAttr.values;
   }

   public byte[] getAsByteArray() {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      int nameLen = this.getName().length();
      int hc = this.getName().hashCode();
      baos.write((nameLen & -16777216) >> 24);
      baos.write((nameLen & 16711680) >> 16);
      baos.write((nameLen & '\uff00') >> 8);
      baos.write(nameLen & 255);
      baos.write((hc & -16777216) >> 24);
      baos.write((hc & 16711680) >> 16);
      baos.write((hc & '\uff00') >> 8);
      baos.write(hc & 255);
      baos.write(this.getName().getBytes(), 0, nameLen);
      int baseLen = this.getBase().length();
      baos.write((baseLen & -16777216) >> 24);
      baos.write((baseLen & 16711680) >> 16);
      baos.write((baseLen & '\uff00') >> 8);
      baos.write(baseLen & 255);
      hc = this.getBase().hashCode();
      baos.write((hc & -16777216) >> 24);
      baos.write((hc & 16711680) >> 16);
      baos.write((hc & '\uff00') >> 8);
      baos.write(hc & 255);
      baos.write(this.getBase().getBytes(), 0, baseLen);
      String idString = String.valueOf(this.getID());
      int idLen = idString.length();
      baos.write(idLen);
      baos.write(idString.getBytes(), 0, idLen);
      baos.write((this.attributes.size() & '\uff00') >> 8);
      baos.write(this.attributes.size() & 255);
      Enumeration keyEnum = this.attributes.elements();

      while(keyEnum.hasMoreElements()) {
         Attribute aAttr = (Attribute)keyEnum.nextElement();
         DirectoryString aKey = aAttr.type;
         baos.write(aKey.length());
         hc = aKey.hashCode();
         baos.write((hc & -16777216) >> 24);
         baos.write((hc & 16711680) >> 16);
         baos.write((hc & '\uff00') >> 8);
         baos.write(hc & 255);
         baos.write(aKey.getBytes(), 0, aKey.length());
         Vector vals = aAttr.values;
         baos.write((vals.size() & '\uff00') >> 8);
         baos.write(vals.size() & 255);
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
      }

      byte[] entryBytes = baos.toByteArray();

      try {
         baos.close();
      } catch (IOException var14) {
         Logger.getInstance().log(0, this, Messages.getString("Error_closing_ByteArrayOutputStream_4"));
      }

      return entryBytes;
   }

   public int getID() {
      return this.id;
   }

   public Enumeration keys() {
      return new KeyEnumeration();
   }

   public void put(DirectoryString key, Vector values) {
      this.put(key, values, true);
   }

   public void put(DirectoryString key, Vector values, boolean checkExist) {
      Attribute thisAttr = null;
      if (checkExist) {
         Enumeration attrEnum = this.attributes.elements();

         while(attrEnum.hasMoreElements()) {
            Attribute oneAttr = (Attribute)attrEnum.nextElement();
            if (oneAttr.equals(key)) {
               thisAttr = oneAttr;
            }
         }
      }

      if (thisAttr != null) {
         this.attributes.removeElement(thisAttr);
      }

      this.attributes.addElement(new Attribute(key, values));
   }

   private void readBytes(byte[] eb) {
      int ebSize = null == eb ? 0 : eb.length;
      int bc = 0;
      int len = bytesToInt(eb, bc);
      bc += 4;

      try {
         checkEntryFieldLength(ebSize, bc, len, "name");
      } catch (IndexOutOfBoundsException var30) {
         BackendHandler.setReplicaInvalid();
         throw var30;
      }

      int hcode = bytesToInt(eb, bc);
      bc += 4;
      byte[] nameBytes = new byte[len];
      System.arraycopy(eb, bc, nameBytes, 0, len);
      this.name = new DirectoryString(nameBytes, hcode);
      bc += len;
      len = bytesToInt(eb, bc);
      bc += 4;

      try {
         checkEntryFieldLength(ebSize, bc, len, "base");
      } catch (IndexOutOfBoundsException var29) {
         BackendHandler.setReplicaInvalid();
         throw var29;
      }

      hcode = bytesToInt(eb, bc);
      bc += 4;
      byte[] baseBytes = new byte[len];
      System.arraycopy(eb, bc, baseBytes, 0, len);
      this.base = new DirectoryString(baseBytes, hcode);
      bc += len;
      int len = eb[bc];
      ++bc;

      try {
         checkEntryFieldLength(ebSize, bc, len, "id");
      } catch (IndexOutOfBoundsException var28) {
         BackendHandler.setReplicaInvalid();
         throw var28;
      }

      byte[] idBytes = new byte[len];
      System.arraycopy(eb, bc, idBytes, 0, len);
      this.id = Integer.parseInt(new String(idBytes));
      bc += len;
      int numKeys = (eb[bc] & 255) << 8 | eb[bc + 1] & 255;

      try {
         checkEntryFieldLength(ebSize, bc, numKeys, "numKeys");
      } catch (IndexOutOfBoundsException var27) {
         BackendHandler.setReplicaInvalid();
         throw var27;
      }

      this.attributes = new Vector(numKeys);
      byte[] keyBytes = null;
      byte[] valBytes = null;
      Syntax synVal = null;
      Vector values = null;
      DirectoryString key = null;
      Class synClass = null;
      AttributeType at = null;
      bc += 2;

      for(int keyCount = 0; keyCount < numKeys; ++keyCount) {
         len = eb[bc];
         ++bc;

         try {
            checkEntryFieldLength(ebSize, bc, len, "key" + keyCount);
         } catch (IndexOutOfBoundsException var26) {
            BackendHandler.setReplicaInvalid();
            throw var26;
         }

         int keyHC = bytesToInt(eb, bc);
         bc += 4;
         byte[] keyBytes = new byte[len];
         System.arraycopy(eb, bc, keyBytes, 0, len);
         bc += len;
         key = new DirectoryString(keyBytes, keyHC);
         int numValues = (eb[bc] & 255) << 8 | eb[bc + 1] & 255;

         try {
            checkEntryFieldLength(ebSize, bc, numValues, "numValues");
         } catch (IndexOutOfBoundsException var25) {
            BackendHandler.setReplicaInvalid();
            throw var25;
         }

         values = new Vector(numValues);
         bc += 2;
         at = SchemaChecker.getInstance().getAttributeType(key);
         synClass = null;
         if (at != null) {
            synClass = at.getSyntaxClass();
            if (synClass == null) {
               synClass = DirectoryString.class;
            }
         } else {
            synClass = DirectoryString.class;
         }

         for(int valCount = 0; valCount < numValues; ++valCount) {
            len = bytesToInt(eb, bc);
            bc += 4;

            try {
               checkEntryFieldLength(ebSize, bc, len, "value" + valCount);
            } catch (IndexOutOfBoundsException var24) {
               BackendHandler.setReplicaInvalid();
               throw var24;
            }

            int valHC = bytesToInt(eb, bc);
            bc += 4;
            byte[] valBytes = new byte[len];
            System.arraycopy(eb, bc, valBytes, 0, len);
            synVal = null;

            try {
               synVal = (Syntax)synClass.newInstance();
            } catch (Exception var23) {
               Logger.getInstance().log(0, this, Messages.getString("Unable_to_create_new_value_with_Syntax____5") + synClass.getName() + "'");
               synVal = new DirectoryString();
            }

            ((Syntax)synVal).setValue(valBytes, valHC);
            values.addElement(synVal);
            bc += len;
         }

         this.put(key, values, false);
      }

   }

   public void remove(DirectoryString key) {
      Attribute thisAttr = null;
      Enumeration attrEnum = this.attributes.elements();

      while(attrEnum.hasMoreElements()) {
         Attribute oneAttr = (Attribute)attrEnum.nextElement();
         if (oneAttr.equals(key)) {
            thisAttr = oneAttr;
         }
      }

      if (thisAttr != null) {
         this.attributes.removeElement(thisAttr);
      }

   }

   public void setID(int id) {
      this.id = id;
   }

   public String toDSML() {
      StringBuffer dsmlString = new StringBuffer();
      dsmlString.append("<dsml:entry dn=\"").append(this.getName()).append("\">\n");
      Vector ocs = this.get(OBJECTCLASS);
      Enumeration enumAttrs;
      if (ocs != null) {
         dsmlString.append("  <dsml:objectclass>\n");
         enumAttrs = ocs.elements();

         while(enumAttrs.hasMoreElements()) {
            dsmlString.append("    <dsml:oc-value>").append((DirectoryString)enumAttrs.nextElement());
            dsmlString.append("</dsml:oc-value>\n");
         }

         dsmlString.append("  </dsml:objectclass>\n");
      }

      enumAttrs = this.attributes.elements();

      while(true) {
         Attribute nextAttr;
         do {
            if (!enumAttrs.hasMoreElements()) {
               dsmlString.append("</dsml:entry>\n");
               return dsmlString.toString();
            }

            nextAttr = (Attribute)enumAttrs.nextElement();
         } while(nextAttr.equals(OBJECTCLASS));

         dsmlString.append("  <dsml:attr name=\"").append(nextAttr.type);
         dsmlString.append("\">\n");
         Enumeration valEnum = nextAttr.values.elements();

         while(valEnum.hasMoreElements()) {
            dsmlString.append("    <dsml:value>");
            String val = valEnum.nextElement().toString();
            if (val.indexOf("<") >= 0 || val.indexOf(">") >= 0 || val.indexOf("&") >= 0 || val.indexOf("\"") >= 0) {
               int oldloc = 0;
               int loc = false;
               StringBuffer sb = null;
               int loc = val.indexOf("<");

               for(sb = new StringBuffer(); loc >= 0; loc = val.indexOf("<", oldloc)) {
                  if (loc - oldloc > 0) {
                     sb.append(val.substring(oldloc, loc));
                     sb.append("&lt;");
                  } else {
                     sb.append("&lt;");
                  }

                  oldloc = loc + 1;
               }

               sb.append(val.substring(oldloc));
               val = sb.toString();
               sb = new StringBuffer();
               oldloc = 0;

               for(loc = val.indexOf(">"); loc >= 0; loc = val.indexOf(">", oldloc)) {
                  if (loc - oldloc > 0) {
                     sb.append(val.substring(oldloc, loc));
                     sb.append("&gt;");
                  } else {
                     sb.append("&gt;");
                  }

                  oldloc = loc + 1;
               }

               sb.append(val.substring(oldloc));
               val = sb.toString();
               sb = new StringBuffer();
               oldloc = 0;

               for(loc = val.indexOf("\""); loc >= 0; loc = val.indexOf("\"", oldloc)) {
                  if (loc - oldloc > 0) {
                     sb.append(val.substring(oldloc, loc));
                     sb.append("&quot;");
                  } else {
                     sb.append("&quot;");
                  }

                  oldloc = loc + 1;
               }

               sb.append(val.substring(oldloc));
               val = sb.toString();
               sb = new StringBuffer();
               oldloc = 0;

               for(loc = val.indexOf("&"); loc >= 0; loc = val.indexOf("&", oldloc)) {
                  if (loc - oldloc > 0) {
                     sb.append(val.substring(oldloc, loc));
                     sb.append("&amp;");
                  } else {
                     sb.append("&amp;");
                  }

                  oldloc = loc + 1;
               }

               sb.append(val.substring(oldloc));
               val = sb.toString();
            }

            dsmlString.append(val);
            dsmlString.append("</dsml:value>\n");
         }

         dsmlString.append("  </dsml:attr>\n");
      }
   }

   public String toLDIF() {
      return this.toLDIF((String[])null, (EncryptionHelper)null);
   }

   public String toLDIF(String[] encryptedAttribs, EncryptionHelper encrypter) {
      return this.toLDIF(encryptedAttribs, encrypter, false);
   }

   public String toLDIF(String[] encryptedAttribs, EncryptionHelper encrypter, boolean ignoreGuid) {
      StringBuffer ldifString = new StringBuffer();
      Enumeration enumAttrs = this.attributes.elements();

      while(true) {
         Attribute nextAttr;
         DirectoryString thisAttr;
         do {
            if (!enumAttrs.hasMoreElements()) {
               return ldifString.toString();
            }

            nextAttr = (Attribute)enumAttrs.nextElement();
            thisAttr = nextAttr.type;
         } while(BackendStandard.GUID.equals(thisAttr) && ignoreGuid);

         Enumeration valEnum = nextAttr.values.elements();

         while(valEnum.hasMoreElements()) {
            ldifString.append(thisAttr);
            Syntax sval = (Syntax)valEnum.nextElement();
            byte[] decryptedValue = null;
            if (encryptedAttribs != null) {
               decryptedValue = this.checkEncryption(thisAttr, sval, encryptedAttribs, encrypter);
            }

            if (sval instanceof BinarySyntax) {
               ldifString.append(":: ");
            } else {
               ldifString.append(": ");
            }

            if (decryptedValue != null) {
               ldifString.append(sval.returnAttributeValue(decryptedValue)).append("\n");
            } else {
               ldifString.append(sval.toString()).append("\n");
            }
         }
      }
   }

   private byte[] checkEncryption(DirectoryString dsVal, Syntax sVal, String[] encryptedAttribs, EncryptionHelper encrypter) {
      if (dsVal != null && sVal != null && encryptedAttribs != null && encrypter != null) {
         String attrName = dsVal.getDirectoryString();

         for(int i = 0; i < encryptedAttribs.length; ++i) {
            if (encryptedAttribs[i].equals(attrName)) {
               String encryptedString;
               try {
                  encryptedString = new String(sVal.getValue(), "UTF8");
               } catch (UnsupportedEncodingException var10) {
                  encryptedString = new String(sVal.getValue());
               }

               try {
                  return encrypter.decrypt(encryptedString).getBytes("UTF8");
               } catch (UnsupportedEncodingException var9) {
                  return encrypter.decrypt(encryptedString).getBytes();
               }
            }
         }

         return null;
      } else {
         return null;
      }
   }

   static void checkEntryFieldLength(int arraySize, int arrayOffset, int length, String fieldName) {
      if (length < 0) {
         throw new ArrayIndexOutOfBoundsException(MessageFormat.format(Messages.getString("Entry_checkEntryFieldLength_negativeLength"), fieldName, length));
      } else {
         if (arraySize >= 0 && arrayOffset >= 0) {
            int remaining = arraySize - arrayOffset;
            if (length > remaining) {
               throw new ArrayIndexOutOfBoundsException(MessageFormat.format(Messages.getString("Entry_checkEntryFieldLength_nonNegativeLength"), fieldName, length, remaining));
            }
         }

      }
   }

   class KeyEnumeration implements Enumeration {
      Enumeration atEnum = null;

      KeyEnumeration() {
         this.atEnum = Entry.this.attributes.elements();
      }

      public boolean hasMoreElements() {
         return this.atEnum.hasMoreElements();
      }

      public Object nextElement() {
         return ((Attribute)this.atEnum.nextElement()).type;
      }
   }
}
