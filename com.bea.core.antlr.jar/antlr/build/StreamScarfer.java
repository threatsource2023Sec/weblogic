package antlr.build;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class StreamScarfer extends Thread {
   InputStream is;
   String type;
   Tool tool;

   StreamScarfer(InputStream var1, String var2, Tool var3) {
      this.is = var1;
      this.type = var2;
      this.tool = var3;
   }

   public void run() {
      try {
         InputStreamReader var1 = new InputStreamReader(this.is);
         BufferedReader var2 = new BufferedReader(var1);
         String var3 = null;

         while(true) {
            while((var3 = var2.readLine()) != null) {
               if (this.type != null && !this.type.equals("stdout")) {
                  this.tool.stderr(var3);
               } else {
                  this.tool.stdout(var3);
               }
            }

            return;
         }
      } catch (IOException var4) {
         var4.printStackTrace();
      }
   }
}
