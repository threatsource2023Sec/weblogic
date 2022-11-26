package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import serp.bytecode.lowlevel.UTF8Entry;
import serp.bytecode.visitor.BCVisitor;

public class SourceFile extends Attribute {
   int _sourceFileIndex = 0;

   SourceFile(int nameIndex, Attributes owner) {
      super(nameIndex, owner);
   }

   int getLength() {
      return 2;
   }

   public int getFileIndex() {
      return this._sourceFileIndex;
   }

   public void setFileIndex(int sourceFileIndex) {
      if (sourceFileIndex < 0) {
         sourceFileIndex = 0;
      }

      this._sourceFileIndex = sourceFileIndex;
   }

   public String getFileName() {
      return this._sourceFileIndex == 0 ? null : ((UTF8Entry)this.getPool().getEntry(this._sourceFileIndex)).getValue();
   }

   public File getFile(File dir) {
      String name = this.getFileName();
      return name == null ? null : new File(dir, name);
   }

   public void setFile(String name) {
      if (name == null) {
         this.setFileIndex(0);
      } else {
         this.setFileIndex(this.getPool().findUTF8Entry(name, true));
      }

   }

   public void setFile(File file) {
      if (file == null) {
         this.setFile((String)null);
      } else {
         this.setFile(file.getName());
      }

   }

   public void setFromClassName() {
      this.setFile(((BCClass)this.getOwner()).getClassName() + ".java");
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterSourceFile(this);
      visit.exitSourceFile(this);
   }

   void read(Attribute other) {
      this.setFile(((SourceFile)other).getFileName());
   }

   void read(DataInput in, int length) throws IOException {
      this.setFileIndex(in.readUnsignedShort());
   }

   void write(DataOutput out, int length) throws IOException {
      out.writeShort(this.getFileIndex());
   }
}
