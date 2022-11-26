package weblogic.management.patching.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;

public class Drainer extends Thread {
   private BufferedReader in;
   Writer writer;
   ZdtAgentOutputHandler outputHandler;

   public Drainer(InputStream is, Writer writer, ZdtAgentOutputHandler outputHandler) {
      this.in = new BufferedReader(new InputStreamReader(is));
      this.writer = writer;
      this.outputHandler = outputHandler;
   }

   public void run() {
      boolean first = true;

      String line;
      try {
         while((line = this.in.readLine()) != null) {
            try {
               if (!first) {
                  this.writer.write("\n");
               } else {
                  first = false;
               }

               this.writer.write(line);
            } catch (IOException var4) {
               this.outputHandler.error("Could not write all of the script output", var4);
            }
         }
      } catch (IOException var5) {
         this.outputHandler.debug("problem logging script output due to closed script output" + var5);
      }

   }

   public void cleanup() {
      try {
         this.writer.flush();
      } catch (IOException var3) {
      }

      try {
         this.in.close();
      } catch (IOException var2) {
      }

   }
}
