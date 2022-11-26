package weblogic.rmi.rmic.bootstrap;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import weblogic.rmi.rmic.Remote2Java;
import weblogic.utils.BadOptionException;
import weblogic.utils.Getopt2;

public class BootstrapRMIC extends Task {
   private String destDir;
   private String descriptor;
   private String classname;
   private String dgcPolicy;
   private String nontransactional;
   private String serverSideStubs;

   public void setDestdir(String dest) {
      this.destDir = dest;
   }

   public void setDescriptor(String disc) {
      this.descriptor = disc;
   }

   public void setClassname(String cn) {
      this.classname = cn;
   }

   public void setDgcPolicy(String dgc) {
      this.dgcPolicy = dgc;
   }

   public void setNontransactional(String nontran) {
      this.nontransactional = nontran;
   }

   public void setServerSideStubs(String stubs) {
      this.serverSideStubs = stubs;
   }

   public void execute() throws BuildException {
      Getopt2 opts = new Getopt2();
      Remote2Java rmic = new Remote2Java(opts);

      try {
         opts.setFlag("disableHotCodeGen", true);
         if (this.descriptor != null) {
            opts.setOption("descriptor", this.descriptor);
         }

         if (this.destDir != null) {
            rmic.setRootDirectoryName(this.destDir);
         }

         if (this.dgcPolicy != null) {
            opts.setOption("dgcPolicy", this.dgcPolicy);
         }

         if (this.nontransactional != null) {
            opts.setFlag("nontransactional", Boolean.parseBoolean(this.nontransactional));
         }

         if (this.serverSideStubs != null) {
            opts.setFlag("serverSideStubs", Boolean.parseBoolean(this.serverSideStubs));
         }
      } catch (BadOptionException var5) {
         throw new BuildException(var5);
      }

      try {
         rmic.generate(new String[]{this.classname});
      } catch (Exception var4) {
         throw new BuildException(var4);
      }
   }
}
