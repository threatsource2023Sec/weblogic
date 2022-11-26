package weblogic.servlet.ejb2jsp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;

class TLDOutputter {
   static final String DTD = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n<!DOCTYPE taglib\nPUBLIC \"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN\" \"http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd\">\n";
   ByteArrayOutputStream baos = new ByteArrayOutputStream();
   PrintStream ps;
   List methods;

   TLDOutputter(List methods) {
      this.ps = new PrintStream(this.baos);
      this.methods = methods;
   }

   void p(String s) {
      this.ps.print(s);
   }

   String generate() {
      this.p("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n<!DOCTYPE taglib\nPUBLIC \"-//Sun Microsystems, Inc.//DTD JSP Tag Library 1.1//EN\" \"http://java.sun.com/j2ee/dtds/web-jsptaglibrary_1_1.dtd\">\n");
      this.p("<taglib>\n");
      this.p("<tlibversion>1.0</tlibversion>\n");
      this.p("<shortname>ejb2jsp generated tag library</shortname>\n");
      Iterator I = this.methods.iterator();

      while(I.hasNext()) {
         this.doMethod((EJBMethodGenerator)I.next());
      }

      this.p("</taglib>");
      this.ps.flush();
      return this.baos.toString();
   }

   void doMethod(EJBMethodGenerator m) {
      this.p(" <tag>\n");
      this.p("  <name>" + m.getTagName() + "</name>\n");
      this.p("  <tagclass>" + m.generated_class_name() + "</tagclass>\n");
      MethodParamDescriptor[] tmp = m.getDescriptor().getParams();
      String[] ptypes = new String[tmp.length];
      String[] atts = new String[tmp.length];
      boolean[] req = new boolean[tmp.length];

      int i;
      for(i = 0; i < tmp.length; ++i) {
         ptypes[i] = tmp[i].getType();
         atts[i] = tmp[i].getName();
         if (!"EXPRESSION".equalsIgnoreCase(tmp[i].getDefault()) && !"METHOD".equalsIgnoreCase(tmp[i].getDefault())) {
            req[i] = true;
         } else {
            req[i] = false;
         }
      }

      if (!m.needExtraInfoClass()) {
         this.p("  <info>this calls " + m.getMethodName() + "()</info>\n");
      } else {
         this.p("  <teiclass>" + m.generated_class_name() + "TEI</teiclass> \n");
         this.p("  <info>\n");

         for(i = 0; i < ptypes.length; ++i) {
            this.p("   attribute '" + atts[i] + "' expects java type '" + ptypes[i] + "'\n");
         }

         this.p("  </info>\n");
      }

      for(i = 0; i < atts.length; ++i) {
         this.p("  <attribute>\n");
         this.p("   <name>" + atts[i] + "</name>\n");
         this.p("   <required>" + req[i] + "</required>\n");
         this.p("   <rtexprvalue>true</rtexprvalue>\n");
         this.p("  </attribute>\n");
      }

      if (!"void".equalsIgnoreCase(m.getReturnType())) {
         this.p("  <attribute>\n");
         this.p("   <name>_return</name>\n");
         this.p("   <required>false</required>\n");
         this.p("   <rtexprvalue>false</rtexprvalue>\n");
         this.p("  </attribute>\n");
      }

      this.p(" </tag>\n");
   }
}
