package oracle.jrockit.jfr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import oracle.jrockit.jfr.settings.EventDefaultSet;

public class PresetFile {
   private final String name;
   private final String desc;
   private final EventDefaultSet settings;

   PresetFile(File path) throws IOException, URISyntaxException, ParseException {
      String s = path.getName();
      if (s.toLowerCase().endsWith(".jfs")) {
         s = s.substring(0, s.length() - ".jfs".length());
      }

      this.name = s;
      BufferedReader r = new BufferedReader(new FileReader(path));
      StringBuilder buf = new StringBuilder();

      while(true) {
         s = r.readLine();
         if (s == null) {
            break;
         }

         s = s.trim();
         if (!s.startsWith("//")) {
            break;
         }

         if (buf.length() > 0 && s.charAt(s.length() - 1) != ' ') {
            buf.append(' ');
         }

         buf.append(s.substring(2).trim());
      }

      this.desc = buf.toString();
      this.settings = new EventDefaultSet(path);
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.desc;
   }

   public EventDefaultSet getSettings() {
      return this.settings;
   }
}
