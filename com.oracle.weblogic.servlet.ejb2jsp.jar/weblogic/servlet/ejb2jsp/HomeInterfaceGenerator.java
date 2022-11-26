package weblogic.servlet.ejb2jsp;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;

public class HomeInterfaceGenerator extends CodeGenerator {
   String imports;
   String iface;
   String rtype;
   String pkg;
   String simpleName;

   static void p(String s) {
      System.err.println("[HIG]: " + s);
   }

   public HomeInterfaceGenerator(Getopt2 opts, String imports, String iface, String remoteType) {
      super(opts);
      this.imports = imports;
      int ind = iface.lastIndexOf(46);
      this.pkg = iface.substring(0, ind);
      this.iface = iface.substring(ind + 1);
      this.rtype = remoteType;
   }

   public String importStatements() {
      return this.imports;
   }

   public String homeTagInterfaceName() {
      return this.iface;
   }

   public String remoteType() {
      return this.rtype;
   }

   public String packageStatement() {
      return "package " + this.pkg + ";";
   }

   public String getTemplatePath() {
      return "/weblogic/servlet/ejb2jsp/hometaginterface.j";
   }

   protected Enumeration outputs(Object[] inputs) throws IOException, CodeGenerationException {
      CodeGenerator.Output o = new TaglibOutput(this.iface + ".java", this.getTemplatePath(), this.pkg);
      Vector outputs = new Vector();
      outputs.addElement(o);
      return outputs.elements();
   }
}
