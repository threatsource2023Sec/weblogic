package weblogic.utils.compiler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArgfileParser {
   private String argfileName;
   private boolean inQuotation = false;

   public ArgfileParser(String argfile) {
      this.argfileName = argfile;
   }

   public List parse() throws ToolFailureException {
      List args = new ArrayList();
      StringBuilder sb = new StringBuilder();
      BufferedReader reader = null;

      try {
         reader = new BufferedReader(new FileReader(this.argfileName));

         while(true) {
            while(true) {
               int i = reader.read();
               if (i < 0) {
                  if (sb.length() > 0) {
                     args.add(sb.toString());
                     sb.setLength(0);
                  }

                  return args;
               }

               char c = (char)i;
               if (c == '"') {
                  this.inQuotation = !this.inQuotation;
               } else if (c != ' ' && c != '\t') {
                  if (c != '\r' && c != '\n') {
                     sb.append(c);
                  } else if (sb.length() > 0) {
                     args.add(sb.toString());
                     sb.setLength(0);
                  }
               } else if (this.inQuotation) {
                  sb.append(c);
               } else if (sb.length() > 0) {
                  args.add(sb.toString());
                  sb.setLength(0);
               }
            }
         }
      } catch (FileNotFoundException var14) {
         throw new ToolFailureException("Failed to parse argfile!", var14);
      } catch (IOException var15) {
         throw new ToolFailureException("Failed to parse argfile!", var15);
      } finally {
         if (reader != null) {
            try {
               reader.close();
            } catch (IOException var13) {
            }
         }

      }
   }
}
