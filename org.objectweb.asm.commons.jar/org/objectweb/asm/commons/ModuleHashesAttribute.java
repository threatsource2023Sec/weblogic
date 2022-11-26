package org.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ByteVector;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;

public final class ModuleHashesAttribute extends Attribute {
   public String algorithm;
   public List modules;
   public List hashes;

   public ModuleHashesAttribute(String algorithm, List modules, List hashes) {
      super("ModuleHashes");
      this.algorithm = algorithm;
      this.modules = modules;
      this.hashes = hashes;
   }

   public ModuleHashesAttribute() {
      this((String)null, (List)null, (List)null);
   }

   protected Attribute read(ClassReader classReader, int offset, int length, char[] charBuffer, int codeAttributeOffset, Label[] labels) {
      String hashAlgorithm = classReader.readUTF8(offset, charBuffer);
      int currentOffset = offset + 2;
      int numModules = classReader.readUnsignedShort(currentOffset);
      currentOffset += 2;
      ArrayList moduleList = new ArrayList(numModules);
      ArrayList hashList = new ArrayList(numModules);

      for(int i = 0; i < numModules; ++i) {
         String module = classReader.readModule(currentOffset, charBuffer);
         currentOffset += 2;
         moduleList.add(module);
         int hashLength = classReader.readUnsignedShort(currentOffset);
         currentOffset += 2;
         byte[] hash = new byte[hashLength];

         for(int j = 0; j < hashLength; ++j) {
            hash[j] = (byte)(classReader.readByte(currentOffset) & 255);
            ++currentOffset;
         }

         hashList.add(hash);
      }

      return new ModuleHashesAttribute(hashAlgorithm, moduleList, hashList);
   }

   protected ByteVector write(ClassWriter classWriter, byte[] code, int codeLength, int maxStack, int maxLocals) {
      ByteVector byteVector = new ByteVector();
      byteVector.putShort(classWriter.newUTF8(this.algorithm));
      if (this.modules == null) {
         byteVector.putShort(0);
      } else {
         int numModules = this.modules.size();
         byteVector.putShort(numModules);

         for(int i = 0; i < numModules; ++i) {
            String module = (String)this.modules.get(i);
            byte[] hash = (byte[])this.hashes.get(i);
            byteVector.putShort(classWriter.newModule(module)).putShort(hash.length).putByteArray(hash, 0, hash.length);
         }
      }

      return byteVector;
   }
}
