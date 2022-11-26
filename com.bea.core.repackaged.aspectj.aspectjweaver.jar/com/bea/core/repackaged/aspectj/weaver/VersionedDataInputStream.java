package com.bea.core.repackaged.aspectj.weaver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class VersionedDataInputStream extends DataInputStream {
   private AjAttribute.WeaverVersionInfo version = new AjAttribute.WeaverVersionInfo();
   private ConstantPoolReader constantPoolReader;

   public VersionedDataInputStream(InputStream is, ConstantPoolReader constantPoolReader) {
      super(is);
      this.constantPoolReader = constantPoolReader;
   }

   public int getMajorVersion() {
      return this.version.getMajorVersion();
   }

   public int getMinorVersion() {
      return this.version.getMinorVersion();
   }

   public long getBuildstamp() {
      return this.version.getBuildstamp();
   }

   public void setVersion(AjAttribute.WeaverVersionInfo version) {
      this.version = version;
   }

   public String readUtf8(int cpIndex) {
      if (this.constantPoolReader == null) {
         throw new IllegalStateException();
      } else if (cpIndex < 0) {
         throw new IllegalStateException(cpIndex + "");
      } else {
         return this.constantPoolReader.readUtf8(cpIndex);
      }
   }

   public boolean canDecompress() {
      return this.constantPoolReader != null;
   }

   public boolean isAtLeast169() {
      return this.getMajorVersion() >= 7;
   }

   public String readPath() throws IOException {
      return this.readUtf8(this.readShort());
   }

   public String readSignature() throws IOException {
      return this.readUtf8(this.readShort());
   }

   public UnresolvedType readSignatureAsUnresolvedType() throws IOException {
      return UnresolvedType.forSignature(this.readUtf8(this.readShort()));
   }

   public String toString() {
      return "VersionedDataInputStream: version=" + this.version + " constantPoolReader?" + (this.constantPoolReader != null);
   }
}
