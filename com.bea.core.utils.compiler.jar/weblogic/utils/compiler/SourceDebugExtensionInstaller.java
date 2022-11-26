package weblogic.utils.compiler;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class SourceDebugExtensionInstaller {
   static final String SDE = "SourceDebugExtension";
   private int sdeIndex;
   private byte[] originalBytes;
   private byte[] sdeAttrBytes;
   private byte[] outputBytes;
   private int originalPos = 0;
   private int outputPos = 0;

   static void installSmap(byte[] classBuffer, byte[] smap, String outFile) throws IOException {
      new SourceDebugExtensionInstaller(classBuffer, smap, outFile);
   }

   SourceDebugExtensionInstaller(byte[] classBuffer, byte[] sdeAttr, String outFileName) throws IOException {
      this.sdeAttrBytes = sdeAttr;
      this.originalBytes = classBuffer;
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFileName));
      if (sdeAttr != null && sdeAttr.length != 0) {
         this.outputBytes = new byte[this.originalBytes.length + sdeAttr.length + 100];
         this.installSourceDebugExtension();
         bos.write(this.outputBytes, 0, this.outputPos);
      } else {
         bos.write(this.originalBytes);
      }

      bos.close();
   }

   void installSourceDebugExtension() throws UnsupportedEncodingException, IOException {
      this.copyBytes(8);
      int constantPoolCountPos = this.outputPos;
      int constantPoolCount = this.readU2();
      this.writeU2(constantPoolCount);
      this.sdeIndex = this.copyConstantPool(constantPoolCount);
      if (this.sdeIndex < 0) {
         this.writeUtf8ForSDE();
         this.sdeIndex = constantPoolCount++;
         this.randomAccessWriteU2(constantPoolCountPos, constantPoolCount);
      }

      this.copyBytes(6);
      int interfaceCount = this.readU2();
      this.writeU2(interfaceCount);
      this.copyBytes(interfaceCount * 2);
      this.copyMembers();
      this.copyMembers();
      int attrCountPos = this.outputPos;
      int attrCount = this.readU2();
      this.writeU2(attrCount);
      if (!this.copyAttributes(attrCount)) {
         ++attrCount;
         this.randomAccessWriteU2(attrCountPos, attrCount);
      }

      this.writeAttrForSourceDebugExtension(this.sdeIndex);
   }

   int copyConstantPool(int constantPoolCount) throws UnsupportedEncodingException, IOException {
      int sdeIndex = -1;

      for(int i = 1; i < constantPoolCount; ++i) {
         int tag = this.readU1();
         this.writeU1(tag);
         switch (tag) {
            case 1:
               int len = this.readU2();
               this.writeU2(len);
               byte[] utf8 = this.readBytes(len);
               String str = new String(utf8, "UTF-8");
               if (str.equals("SourceDebugExtension")) {
                  sdeIndex = i;
               }

               this.writeBytes(utf8);
               break;
            case 2:
            case 13:
            case 14:
            case 17:
            default:
               throw new IOException("unexpected tag: " + tag);
            case 3:
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
            case 18:
               this.copyBytes(4);
               break;
            case 5:
            case 6:
               this.copyBytes(8);
               ++i;
               break;
            case 7:
            case 8:
            case 16:
               this.copyBytes(2);
               break;
            case 15:
               this.copyBytes(3);
         }
      }

      return sdeIndex;
   }

   void copyMembers() {
      int count = this.readU2();
      this.writeU2(count);

      for(int i = 0; i < count; ++i) {
         this.copyBytes(6);
         int attrCount = this.readU2();
         this.writeU2(attrCount);
         this.copyAttributes(attrCount);
      }

   }

   boolean copyAttributes(int attrCount) {
      boolean sdeFound = false;

      for(int i = 0; i < attrCount; ++i) {
         int nameIndex = this.readU2();
         if (nameIndex == this.sdeIndex) {
            sdeFound = true;
         } else {
            this.writeU2(nameIndex);
            int len = this.readU4();
            this.writeU4(len);
            this.copyBytes(len);
         }
      }

      return sdeFound;
   }

   void writeAttrForSourceDebugExtension(int index) {
      this.writeU2(index);
      this.writeU4(this.sdeAttrBytes.length);

      for(int i = 0; i < this.sdeAttrBytes.length; ++i) {
         this.writeU1(this.sdeAttrBytes[i]);
      }

   }

   void randomAccessWriteU2(int pos, int val) {
      int savePos = this.outputPos;
      this.outputPos = pos;
      this.writeU2(val);
      this.outputPos = savePos;
   }

   void copyBytes(int count) {
      for(int i = 0; i < count; ++i) {
         this.outputBytes[this.outputPos++] = this.originalBytes[this.originalPos++];
      }

   }

   byte[] readBytes(int count) {
      byte[] bytes = new byte[count];

      for(int i = 0; i < count; ++i) {
         bytes[i] = this.originalBytes[this.originalPos++];
      }

      return bytes;
   }

   void writeBytes(byte[] bytes) {
      for(int i = 0; i < bytes.length; ++i) {
         this.outputBytes[this.outputPos++] = bytes[i];
      }

   }

   void writeUtf8ForSDE() {
      int len = "SourceDebugExtension".length();
      this.writeU1(1);
      this.writeU2(len);

      for(int i = 0; i < len; ++i) {
         this.writeU1("SourceDebugExtension".charAt(i));
      }

   }

   int readU1() {
      return this.originalBytes[this.originalPos++] & 255;
   }

   int readU2() {
      int res = this.readU1();
      return (res << 8) + this.readU1();
   }

   int readU4() {
      int res = this.readU2();
      return (res << 16) + this.readU2();
   }

   void writeU1(int val) {
      this.outputBytes[this.outputPos++] = (byte)val;
   }

   void writeU2(int val) {
      this.writeU1(val >> 8);
      this.writeU1(val & 255);
   }

   void writeU4(int val) {
      this.writeU2(val >> 16);
      this.writeU2(val & '\uffff');
   }
}
