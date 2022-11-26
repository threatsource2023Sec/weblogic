package weblogic.servlet.ejb2jsp;

import java.io.File;
import java.io.FileOutputStream;
import weblogic.servlet.ejb2jsp.dd.EJBTaglibDescriptor;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.CompilerInvoker;
import weblogic.utils.compiler.Tool;
import weblogic.utils.io.XMLWriter;

public class Main extends Tool {
   static void p(String s) {
      System.err.println("[Main]: " + s);
   }

   public static void main(String[] a) throws Exception {
      System.setProperty("line.separator", "\n");
      System.setProperty("javax.xml.parsers.SAXParserFactory", "weblogic.apache.xerces.jaxp.SAXParserFactoryImpl");
      System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "weblogic.apache.xerces.jaxp.DocumentBuilderFactoryImpl");

      for(int i = 0; i < a.length; ++i) {
         if (a[i].equals("-projectfile")) {
            String[] tmp = new String[a.length + 1];
            System.arraycopy(a, 0, tmp, 0, a.length);
            tmp[a.length] = "_BOGUS_";
            a = tmp;
            break;
         }
      }

      try {
         (new Main(a)).run();
      } catch (Throwable var3) {
         var3.printStackTrace();
         System.exit(1);
      }

   }

   Main(String[] a) throws Exception {
      super(a);
      this.opts.addOption("sourcePath", "directory path", "look in this path for source files of EJB interface(s)");
      this.opts.addAlias("sourceDir", "sourcePath");
      this.opts.addOption("package", "java-pkg", "put results into this java pkg");
      this.opts.addOption("tld", "tld-path", "save the taglib's tld to this file");
      this.opts.addFlag("nokeepgenerated", "delete the generated java files");
      this.opts.addFlag("enableBaseEJB", "enable generation of tags for base EJB methods (e.g., EJBObject.remove(), EJBHome.getHomeHandle(), etc).  These are disabled by default, as they typically cause conflicts in projects with multiple EJBs.");
      this.opts.addOption("projectfile", "project descriptor file", "generate using parameters in previously generated descriptor file");
      this.opts.addOption("saveproject", "project file location", "save generated descriptor file to this location");
      new CompilerInvoker(this.opts);
      CodeGenerator var10001 = new CodeGenerator(this.opts) {
      };
   }

   private void fail(String msg) {
      System.err.println("Fatal error: " + msg);
   }

   public void prepare() throws Exception {
   }

   public void runBody() throws Exception {
      EJBTaglibDescriptor desc = null;
      String xmlFile = null;
      if ((xmlFile = this.opts.getOption("projectfile")) != null) {
         desc = EJBTaglibDescriptor.load(new File(xmlFile));
         byte status = 0;
         boolean var10 = false;

         label95: {
            XMLWriter x;
            label96: {
               try {
                  var10 = true;
                  Utils.compile(desc, System.out);
                  var10 = false;
                  break label96;
               } catch (Exception var11) {
                  String errMsg = var11.toString();
                  if (var11 instanceof RuntimeException) {
                     errMsg = var11.getMessage();
                  }

                  System.err.println("project build failed: " + errMsg);
                  status = 1;
                  var10 = false;
               } finally {
                  if (var10) {
                     if ((xmlFile = this.opts.getOption("saveproject")) == null) {
                        System.exit(status);
                     }

                     XMLWriter x = new XMLWriter(new FileOutputStream(xmlFile));
                     desc.toXML(x);
                     x.flush();
                     x.close();
                     System.out.println("project file written to: " + xmlFile);
                  }
               }

               if ((xmlFile = this.opts.getOption("saveproject")) == null) {
                  System.exit(status);
               }

               x = new XMLWriter(new FileOutputStream(xmlFile));
               desc.toXML(x);
               x.flush();
               x.close();
               System.out.println("project file written to: " + xmlFile);
               break label95;
            }

            if ((xmlFile = this.opts.getOption("saveproject")) == null) {
               System.exit(status);
            }

            x = new XMLWriter(new FileOutputStream(xmlFile));
            desc.toXML(x);
            x.flush();
            x.close();
            System.out.println("project file written to: " + xmlFile);
         }

         System.exit(status);
      } else {
         throw new RuntimeException("");
      }
   }
}
