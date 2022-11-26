package weblogic.management.provider.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.management.ManagementLogger;

public class CAMReplicationExclusive {
   static final String EXCLUSIVE_LIST_FILE = ".wls.replication.exclusive.list";
   private static final Pattern DIR_ENTRY_PATTERN = Pattern.compile("dir\\s*=\\s*([^,]*)(\\s*,\\s*pattern\\s*=\\s*(.*)\\s*)?");
   private static final Pattern FILE_ENTRY_PATTERN = Pattern.compile("file\\s*=\\s*([^,]*)\\s*");
   private String base;
   private ArrayList patterns = new ArrayList();

   public CAMReplicationExclusive(File file) {
      if (!".wls.replication.exclusive.list".equals(file.getName())) {
         throw new IllegalArgumentException("invalid CAM exclusive list file: " + file.getAbsolutePath());
      } else {
         File parent = file.getParentFile();
         File gparent = parent.getParentFile();
         File ggparent = gparent.getParentFile();
         this.base = ggparent.getName() + "/" + gparent.getName() + "/" + parent.getName();
         this.parseFile(file);
      }
   }

   public CAMReplicationExclusive(String base, InputStream in) {
      this.base = base;

      try {
         this.parseStream(in, base + "/" + ".wls.replication.exclusive.list");
      } catch (IOException var4) {
         throw new RuntimeException(var4);
      }
   }

   private void parseFile(File f) {
      FileInputStream in = null;

      try {
         in = new FileInputStream(f);
         this.parseStream(in, f.getCanonicalPath());
      } catch (IOException var11) {
         throw new RuntimeException(var11);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var10) {
            }
         }

      }

   }

   private void parseStream(InputStream in, String fileName) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      int cnt = 0;
      StringBuffer buf = new StringBuffer();

      while(true) {
         String line;
         while(true) {
            do {
               do {
                  if ((line = br.readLine()) == null) {
                     if (buf.length() > 0) {
                        ManagementLogger.logBadCAMReplicaitonExclusiveFileContent(fileName, buf.toString());
                     }

                     return;
                  }

                  ++cnt;
                  line = line.trim();
               } while(line.length() < 1);
            } while(line.charAt(0) == '#');

            try {
               Matcher matcher = DIR_ENTRY_PATTERN.matcher(line);
               FileNamePattern fpatn;
               if (matcher.matches()) {
                  fpatn = new FileNamePattern(matcher.group(1), matcher.group(3));
                  this.patterns.add(fpatn);
               } else {
                  matcher = FILE_ENTRY_PATTERN.matcher(line);
                  if (!matcher.matches()) {
                     break;
                  }

                  fpatn = new FileNamePattern(matcher.group(1));
                  this.patterns.add(fpatn);
               }
            } catch (RuntimeException var9) {
               break;
            }
         }

         buf.append("  ").append(cnt).append(": ").append(line).append("\n");
      }
   }

   public boolean matches(String fileName) {
      fileName = fileName == null ? null : fileName.replace('\\', '/');
      if (fileName != null && fileName.startsWith(this.base) && fileName.length() >= this.base.length() + 1) {
         fileName = fileName.substring(this.base.length() + 1);
         Iterator var2 = this.patterns.iterator();

         FileNamePattern pattern;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            pattern = (FileNamePattern)var2.next();
         } while(!pattern.matches(fileName));

         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("CAMReplicationExclusive base=").append(this.base);
      Iterator var2 = this.patterns.iterator();

      while(var2.hasNext()) {
         FileNamePattern pattern = (FileNamePattern)var2.next();
         buf.append("; ").append(pattern);
      }

      return buf.toString();
   }
}
