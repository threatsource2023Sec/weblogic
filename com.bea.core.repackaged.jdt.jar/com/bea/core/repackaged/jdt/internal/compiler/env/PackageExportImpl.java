package com.bea.core.repackaged.jdt.internal.compiler.env;

public class PackageExportImpl implements IModule.IPackageExport {
   public char[] pack;
   public char[][] exportedTo;

   public char[] name() {
      return this.pack;
   }

   public char[][] targets() {
      return this.exportedTo;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      buffer.append(this.pack);
      buffer.append(" to ");
      if (this.exportedTo != null) {
         for(int i = 0; i < this.exportedTo.length; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            char[] cs = this.exportedTo[i];
            buffer.append(cs);
         }
      }

      buffer.append(';');
      return buffer.toString();
   }
}
