package com.bea.core.repackaged.aspectj.weaver;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CompressingDataOutputStream extends DataOutputStream {
   private ConstantPoolWriter constantPoolWriter;
   public boolean compressionEnabled = true;

   public CompressingDataOutputStream(ByteArrayOutputStream baos, ConstantPoolWriter constantPoolWriter) {
      super(baos);
      this.constantPoolWriter = constantPoolWriter;
   }

   public CompressingDataOutputStream(FileOutputStream fos) {
      super(fos);
   }

   public boolean canCompress() {
      return this.constantPoolWriter != null && this.compressionEnabled;
   }

   public int compressSignature(String signature) {
      if (this.constantPoolWriter == null) {
         throw new IllegalStateException();
      } else {
         return this.constantPoolWriter.writeUtf8(signature);
      }
   }

   public int compressFilepath(String filepath) {
      if (this.constantPoolWriter == null) {
         throw new IllegalStateException();
      } else {
         return this.constantPoolWriter.writeUtf8(filepath);
      }
   }

   public int compressName(String name) {
      if (this.constantPoolWriter == null) {
         throw new IllegalStateException();
      } else {
         return this.constantPoolWriter.writeUtf8(name);
      }
   }

   public void writeCompressedName(String name) throws IOException {
      this.writeShort(this.compressName(name));
   }

   public void writeCompressedSignature(String signature) throws IOException {
      this.writeShort(this.compressSignature(signature));
   }

   public void writeCompressedPath(String path) throws IOException {
      this.writeShort(this.compressFilepath(path));
   }
}
