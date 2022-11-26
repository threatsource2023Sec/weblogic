package weblogic.apache.org.apache.velocity.convert;

import java.io.File;
import java.io.FileWriter;
import weblogic.apache.org.apache.oro.text.perl.Perl5Util;
import weblogic.apache.org.apache.tools.ant.DirectoryScanner;
import weblogic.apache.org.apache.velocity.util.StringUtils;

public class WebMacro {
   protected static final String VM_EXT = ".vm";
   protected static final String WM_EXT = ".wm";
   protected static String[] perLineREs = new String[]{"#if\\s*[(]\\s*(.*\\S)\\s*[)]\\s*(#begin|{)[ \\t]?", "#if( $1 )", "[ \\t]?(#end|})[ \\t]*\n(\\s*)#else\\s*(#begin|{)[ \\t]?(\\w)", "$2#else#**#$4", "[ \\t]?(#end|})[ \\t]*\n(\\s*)#else\\s*(#begin|{)[ \\t]?", "$2#else", "(#end|})(\\s*#else)\\s*(#begin|{)[ \\t]?", "$1\n$2", "#foreach\\s+(\\$\\w+)\\s+in\\s+(\\$[^\\s#]+)\\s*(#begin|{)[ \\t]?", "#foreach( $1 in $2 )", "#set\\s+(\\$[^\\s=]+)\\s*=\\s*([\\S \\t]+)", "#set( $1 = $2 )", "(##[# \\t\\w]*)\\)", ")$1", "#parse\\s+([^\\s#]+)[ \\t]?", "#parse( $1 )", "#include\\s+([^\\s#]+)[ \\t]?", "#include( $1 )", "\\$\\(([^\\)]+)\\)", "${$1}", "\\${([^}\\(]+)\\(([^}]+)}\\)", "${$1($2)}", "\\$_", "$l_", "\\${(_[^}]+)}", "${l$1}", "(#set\\s*\\([^;]+);(\\s*\\))", "$1$2", "(^|[^\\\\])\\$(\\w[^=\n;'\"]*);", "$1${$2}", "\\.wm", ".vm"};

   public void convert(String target) {
      File file = new File(target);
      if (!file.exists()) {
         System.err.println("The specified template or directory does not exist");
         System.exit(1);
      }

      if (file.isDirectory()) {
         String basedir = file.getAbsolutePath();
         String newBasedir = basedir + ".vm";
         DirectoryScanner ds = new DirectoryScanner();
         ds.setBasedir(basedir);
         ds.addDefaultExcludes();
         ds.scan();
         String[] files = ds.getIncludedFiles();

         for(int i = 0; i < files.length; ++i) {
            this.writeTemplate(files[i], basedir, newBasedir);
         }
      } else {
         this.writeTemplate(file.getAbsolutePath(), "", "");
      }

   }

   private boolean writeTemplate(String file, String basedir, String newBasedir) {
      if (file.indexOf(".wm") < 0) {
         return false;
      } else {
         System.out.println("Converting " + file + "...");
         String template;
         String templateDir;
         String newTemplate;
         if (basedir.length() == 0) {
            template = file;
            templateDir = "";
            newTemplate = this.convertName(file);
         } else {
            template = basedir + File.separator + file;
            templateDir = newBasedir + this.extractPath(file);
            File outputDirectory = new File(templateDir);
            if (!outputDirectory.exists()) {
               outputDirectory.mkdirs();
            }

            newTemplate = newBasedir + File.separator + this.convertName(file);
         }

         String convertedTemplate = this.convertTemplate(template);

         try {
            FileWriter fw = new FileWriter(newTemplate);
            fw.write(convertedTemplate);
            fw.close();
         } catch (Exception var10) {
            var10.printStackTrace();
         }

         return true;
      }
   }

   private final String extractPath(String file) {
      int lastSepPos = file.lastIndexOf(File.separator);
      return lastSepPos == -1 ? "" : File.separator + file.substring(0, lastSepPos);
   }

   private String convertName(String name) {
      return name.indexOf(".wm") > 0 ? name.substring(0, name.indexOf(".wm")) + ".vm" : name;
   }

   private static final void usage() {
      System.err.println("Usage: convert-wm <template.wm | directory>");
      System.exit(1);
   }

   public String convertTemplate(String template) {
      String contents = StringUtils.fileContentsToString(template);
      if (!contents.endsWith("\n")) {
         contents = contents + "\n";
      }

      Perl5Util perl = new Perl5Util();

      for(int i = 0; i < perLineREs.length; i += 2) {
         contents = perl.substitute(this.makeSubstRE(i), contents);
      }

      if (perl.match("m/javascript/i", contents)) {
         contents = perl.substitute("s/\n}/\n#end/g", contents);
      } else {
         contents = perl.substitute("s/(\n\\s*)}/$1#end/g", contents);
         contents = perl.substitute("s/#end\\s*\n\\s*#else/#else/g", contents);
      }

      return contents;
   }

   private final String makeSubstRE(int i) {
      return "s/" + perLineREs[i] + '/' + perLineREs[i + 1] + "/g";
   }

   public static void main(String[] args) {
      if (args.length > 0) {
         for(int x = 0; x < args.length; ++x) {
            WebMacro converter = new WebMacro();
            converter.convert(args[x]);
         }
      } else {
         usage();
      }

   }
}
