package org.python.compiler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

public class LineNumberTable {
   int attName;
   Vector lines = new Vector();

   public LineNumberTable() throws IOException {
   }

   public void write(DataOutputStream stream) throws IOException {
      stream.writeShort(this.attName);
      int n = this.lines.size();
      stream.writeInt(n * 2 + 2);
      stream.writeShort(n / 2);

      for(int i = 0; i < n; i += 2) {
         Short startpc = (Short)this.lines.elementAt(i);
         Short lineno = (Short)this.lines.elementAt(i + 1);
         stream.writeShort(startpc);
         stream.writeShort(lineno);
      }

   }

   public void addLine(int startpc, int lineno) {
      this.lines.addElement(new Short((short)startpc));
      this.lines.addElement(new Short((short)lineno));
   }

   public int length() {
      return this.lines.size() * 2 + 8;
   }
}
