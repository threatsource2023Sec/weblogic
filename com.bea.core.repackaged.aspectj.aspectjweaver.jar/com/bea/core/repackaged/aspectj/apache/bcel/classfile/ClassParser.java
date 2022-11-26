package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ClassParser {
   private DataInputStream file;
   private String filename;
   private int classnameIndex;
   private int superclassnameIndex;
   private int major;
   private int minor;
   private int accessflags;
   private int[] interfaceIndices;
   private ConstantPool cpool;
   private Field[] fields;
   private Method[] methods;
   private Attribute[] attributes;
   private static final int BUFSIZE = 8192;
   private static final int[] NO_INTERFACES = new int[0];

   public ClassParser(InputStream file, String filename) {
      this.filename = filename;
      if (file instanceof DataInputStream) {
         this.file = (DataInputStream)file;
      } else {
         this.file = new DataInputStream(new BufferedInputStream(file, 8192));
      }

   }

   public ClassParser(ByteArrayInputStream baos, String filename) {
      this.filename = filename;
      this.file = new DataInputStream(baos);
   }

   public ClassParser(String file_name) throws IOException {
      this.filename = file_name;
      this.file = new DataInputStream(new BufferedInputStream(new FileInputStream(file_name), 8192));
   }

   public JavaClass parse() throws IOException, ClassFormatException {
      this.readID();
      this.readVersion();
      this.readConstantPool();
      this.readClassInfo();
      this.readInterfaces();
      this.readFields();
      this.readMethods();
      this.readAttributes();
      this.file.close();
      JavaClass jc = new JavaClass(this.classnameIndex, this.superclassnameIndex, this.filename, this.major, this.minor, this.accessflags, this.cpool, this.interfaceIndices, this.fields, this.methods, this.attributes);
      return jc;
   }

   private final void readAttributes() {
      this.attributes = AttributeUtils.readAttributes(this.file, this.cpool);
   }

   private final void readClassInfo() throws IOException {
      this.accessflags = this.file.readUnsignedShort();
      if ((this.accessflags & 512) != 0) {
         this.accessflags |= 1024;
      }

      this.classnameIndex = this.file.readUnsignedShort();
      this.superclassnameIndex = this.file.readUnsignedShort();
   }

   private final void readConstantPool() throws IOException {
      try {
         this.cpool = new ConstantPool(this.file);
      } catch (ClassFormatException var3) {
         var3.printStackTrace();
         if (this.filename != null) {
            String newmessage = "File: '" + this.filename + "': " + var3.getMessage();
            throw new ClassFormatException(newmessage);
         } else {
            throw var3;
         }
      }
   }

   private final void readFields() throws IOException, ClassFormatException {
      int fieldCount = this.file.readUnsignedShort();
      if (fieldCount == 0) {
         this.fields = Field.NoFields;
      } else {
         this.fields = new Field[fieldCount];

         for(int i = 0; i < fieldCount; ++i) {
            this.fields[i] = new Field(this.file, this.cpool);
         }
      }

   }

   private final void readID() throws IOException {
      int magic = -889275714;
      if (this.file.readInt() != magic) {
         throw new ClassFormatException(this.filename + " is not a Java .class file");
      }
   }

   private final void readInterfaces() throws IOException {
      int interfacesCount = this.file.readUnsignedShort();
      if (interfacesCount == 0) {
         this.interfaceIndices = NO_INTERFACES;
      } else {
         this.interfaceIndices = new int[interfacesCount];

         for(int i = 0; i < interfacesCount; ++i) {
            this.interfaceIndices[i] = this.file.readUnsignedShort();
         }
      }

   }

   private final void readMethods() throws IOException {
      int methodsCount = this.file.readUnsignedShort();
      if (methodsCount == 0) {
         this.methods = Method.NoMethods;
      } else {
         this.methods = new Method[methodsCount];

         for(int i = 0; i < methodsCount; ++i) {
            this.methods[i] = new Method(this.file, this.cpool);
         }
      }

   }

   private final void readVersion() throws IOException {
      this.minor = this.file.readUnsignedShort();
      this.major = this.file.readUnsignedShort();
   }
}
