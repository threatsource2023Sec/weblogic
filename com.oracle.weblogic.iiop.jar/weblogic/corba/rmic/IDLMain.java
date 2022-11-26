package weblogic.corba.rmic;

import weblogic.utils.Getopt2;

public final class IDLMain {
   Getopt2 m_opts;
   IDLGenerator m_idlGenerator;

   public IDLMain(Getopt2 opts) {
      this.m_opts = opts;
      this.m_idlGenerator = new IDLGenerator(opts);
   }
}
