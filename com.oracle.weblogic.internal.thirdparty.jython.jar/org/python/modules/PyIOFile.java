package org.python.modules;

public interface PyIOFile {
   void write(String var1);

   void write(char var1);

   void flush();

   String read(int var1);

   String readlineNoNl();
}
