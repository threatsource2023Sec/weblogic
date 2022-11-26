package weblogic.ant.taskdefs.iiop;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class CppIdlSetup extends Task {
   private PrintWriter pw;
   private String idlFile;
   private String rootDir;
   private final String CL_COMMAND = "cl -EP -I. -Iidl -nologo ";
   private final String CLIENT_IDL = "idl/client.idl";

   public void setDir(String pDir) {
      this.rootDir = pDir;
   }

   public void setFile(String pFile) {
      this.idlFile = pFile;
   }

   public void execute() throws BuildException {
      this.createClientIdlFile();
   }

   private void createClientIdlFile() {
      Process p = null;

      try {
         p = Runtime.getRuntime().exec("cl -EP -I. -Iidl -nologo " + this.idlFile);
      } catch (Exception var4) {
         throw new BuildException("Unable to find command: cl.  This example was developed for Windows only and requires Microsoft Visual C++ and Visibroker 4.1.");
      }

      try {
         BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
         this.pw = new PrintWriter(new FileWriter(this.rootDir + "/" + "idl/client.idl"));

         String s;
         while((s = stdInput.readLine()) != null) {
            this.pw.println(s);
         }

         this.pw.close();
      } catch (IOException var5) {
         throw new BuildException("Unable to write client.idl.  Check file and directory permissions.");
      }
   }
}
