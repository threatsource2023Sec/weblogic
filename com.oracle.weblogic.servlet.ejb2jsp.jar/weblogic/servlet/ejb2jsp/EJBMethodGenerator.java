package weblogic.servlet.ejb2jsp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import weblogic.servlet.ejb2jsp.dd.EJBMethodDescriptor;
import weblogic.servlet.ejb2jsp.dd.MethodParamDescriptor;
import weblogic.utils.Getopt2;
import weblogic.utils.compiler.CodeGenerationException;
import weblogic.utils.compiler.CodeGenerator;
import weblogic.utils.compiler.IndentingWriter;

public class EJBMethodGenerator extends CodeGenerator {
   protected Hashtable rules = new Hashtable();
   protected BeanGenerator bg;
   protected String generatedClassName;
   protected EJBMethodDescriptor md;

   public EJBMethodDescriptor getDescriptor() {
      return this.md;
   }

   static void p(String s) {
      System.err.println("[EJBMethodDesc]: " + s);
   }

   public EJBMethodGenerator(Getopt2 opts, BeanGenerator bg, EJBMethodDescriptor md) {
      super(opts);
      this.md = md;
      this.bg = bg;
   }

   public String getTagName() {
      return this.md.getTagName();
   }

   protected String getTemplatePath() {
      return "/weblogic/servlet/ejb2jsp/ejbtag.j";
   }

   public String getRemoteClassName() {
      return this.bg.getDD().getRemoteType();
   }

   public String getMethodName() {
      return this.md.getName();
   }

   public String getReturnType() {
      return this.md.getReturnType();
   }

   public String toString() {
      return this.getReturnType() + " " + this.getMethodName() + "(" + this.getArgListString() + ")";
   }

   protected Enumeration outputs(Object[] inputs) throws IOException, CodeGenerationException {
      CodeGenerator.Output o = new TaglibOutput(this.simpleClassName() + ".java", this.getTemplatePath(), this.bg.getPackage());
      this.processTemplate(this.rules, o, this.getTemplatePath());
      Vector outputs = new Vector();
      outputs.addElement(o);
      if (this.needExtraInfoClass()) {
         CodeGenerator.Output o1 = new TaglibOutput(this.simpleTagClassName() + ".java", this.getTagExtraTemplatePath(), this.bg.getPackage());
         outputs.addElement(o1);
      }

      return outputs.elements();
   }

   public String getArgListString() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] mds = this.md.getParams();
      int sz = mds.length;

      for(int i = 0; i < sz; ++i) {
         sb.append(mds[i].getType());
         sb.append(' ');
         sb.append(mds[i].getName());
         if (i + 1 != sz) {
            sb.append(',');
         }
      }

      return sb.toString();
   }

   public int hashCode() {
      return this.getMethodName().hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof EJBMethodGenerator)) {
         return false;
      } else {
         EJBMethodGenerator em = (EJBMethodGenerator)o;
         if (!this.getMethodName().equals(em.getMethodName())) {
            return false;
         } else if (!this.getReturnType().equals(em.getReturnType())) {
            return false;
         } else {
            MethodParamDescriptor[] x = this.md.getParams();
            MethodParamDescriptor[] y = em.md.getParams();
            if (x.length != y.length) {
               return false;
            } else {
               for(int i = 0; i < x.length; ++i) {
                  if (!x[i].getType().equals(y[i].getType())) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   protected PrintWriter makeOutputStream(File f) throws IOException {
      try {
         return "false".equalsIgnoreCase(System.getProperty("weblogic.codegen.indent")) ? super.makeOutputStream(f) : new PrintWriter(new IndentingWriter(new FileWriter(f)));
      } catch (SecurityException var3) {
         return super.makeOutputStream(f);
      }
   }

   public void setGeneratedClassName(String s) {
      this.generatedClassName = s;
   }

   public String generated_class_name() {
      if (this.generatedClassName == null) {
         String ejbtype = this.getRemoteClassName();
         int ind = ejbtype.lastIndexOf(46);
         if (ind > 0) {
            ejbtype = ejbtype.substring(ind + 1);
         }

         this.generatedClassName = this.bg.getPackage() + "._" + ejbtype + "_" + this.getMethodName() + "Tag";
      }

      return this.generatedClassName;
   }

   public String packageStatement() {
      return "package " + this.bg.getPackage() + ";";
   }

   public String simpleClassName() {
      String s = this.generated_class_name();
      int ind = s.lastIndexOf(46);
      if (ind > 0) {
         s = s.substring(ind + 1);
      }

      return s;
   }

   public String evalOut() {
      if (Utils.isVoid(this.md.getReturnType())) {
         return "";
      } else {
         boolean evalout = this.md.isEvalOut();
         return !evalout ? "// don't eval return to output" : "// eval-out is true - print result\npageContext.getOut().print(_ret);\npageContext.getOut().flush();\n";
      }
   }

   public String importStatements() {
      return this.bg.getImportString();
   }

   public String varDeclaration() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         sb.append(p[i].getType() + " " + p[i].getName() + ";\n");
      }

      return sb.toString();
   }

   public String boolDeclaration() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         sb.append("boolean " + p[i].getName() + "_isSet = false;\n");
      }

      return sb.toString();
   }

   public String setterMethods() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         sb.append("public void set" + p[i].getName() + "(" + p[i].getType() + " x) {\n");
         sb.append("this." + p[i].getName() + " = x;\n");
         sb.append("this." + p[i].getName() + "_isSet = true;\n}\n");
      }

      return sb.toString();
   }

   public String remoteType() {
      return this.getRemoteClassName();
   }

   public String invoke() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getMethodName());
      sb.append("(");
      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         sb.append(p[i].getName());
         if (i != sz - 1) {
            sb.append(", ");
         }
      }

      sb.append(")");
      return sb.toString();
   }

   public String releaseBody() {
      StringBuffer sb = new StringBuffer();
      if (!Utils.isVoid(this.md.getReturnType())) {
         sb.append("_return = null;\n");
      }

      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         sb.append(p[i].getName() + "_isSet = false;\n");
      }

      sb.append("super.release();\n");
      return sb.toString();
   }

   public String verifyAttributes() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         String s = p[i].getDefault();
         sb.append("if (!" + p[i].getName() + "_isSet) ");
         if ("EXPRESSION".equalsIgnoreCase(s)) {
            sb.append(p[i].getName() + " = " + p[i].getDefaultValue() + ";\n");
         } else if ("METHOD".equalsIgnoreCase(s)) {
            sb.append(p[i].getName() + " = _get_" + p[i].getName() + "_default();\n");
         } else {
            sb.append("throw new JspException(\"" + p[i].getName() + "not set and has no default\");\n");
         }
      }

      return sb.toString();
   }

   public String retDecl() {
      String c = this.md.getReturnType();
      return Utils.isVoid(c) ? "" : c + " _ret = ";
   }

   public String retSet() {
      String c = this.md.getReturnType();
      if (Utils.isVoid(c)) {
         return "";
      } else {
         return Utils.isPrimitive(c) ? "if (_return != null) { pageContext.setAttribute(_return, " + Utils.primitive2Object(c, "_ret") + "); }\n" : "if (_return != null) { pageContext.setAttribute(_return, _ret); }\n";
      }
   }

   public String homeJNDIName() {
      return '"' + this.bg.getDD().getJNDIName() + '"';
   }

   public String homeType() {
      return this.bg.getDD().getHomeType();
   }

   public String homeTagInterfaceName() {
      return this.bg.getHomeTagInterfaceName();
   }

   public String createInvoke() {
      return "create()";
   }

   public String returnAttribute() {
      return Utils.isVoid(this.md.getReturnType()) ? "" : "String _return = null;\npublic void set_return(String r) { _return = r; }";
   }

   public String defaultMethods() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] p = this.md.getParams();
      int sz = p.length;

      for(int i = 0; i < sz; ++i) {
         if ("METHOD".equalsIgnoreCase(p[i].getDefault())) {
            sb.append("private " + p[i].getType() + " _get_" + p[i].getName() + "_default() throws Exception {\n");
            sb.append("//begin user code....\n");
            sb.append(p[i].getDefaultMethod());
            sb.append("//end user code....\n");
            sb.append("\n}\n");
         }
      }

      return sb.toString();
   }

   public String getEJB() throws CodeGenerationException {
      return this.bg.getDD().isStatefulBean() ? this.parse((String)this.rules.get("entityBeanGetEJB")) : this.parse((String)this.rules.get("sessionBeanGetEJB"));
   }

   public boolean needExtraInfoClass() {
      int sz = this.md.getParams().length;
      if (sz > 0) {
         return true;
      } else {
         return !Utils.isVoid(this.md.getReturnType());
      }
   }

   public String getTagExtraTemplatePath() {
      return "/weblogic/servlet/ejb2jsp/ejbTEI.j";
   }

   public String defaultReturnVar() {
      if (Utils.isVoid(this.md.getReturnType())) {
         return "";
      } else {
         String ret = "private static final String DEFAULT_RETURN_VAR = ";
         String var = this.md.getReturnVarName();
         if (var == null) {
            ret = ret + "null;";
         } else {
            ret = ret + "\"" + var + "\";";
         }

         return ret;
      }
   }

   public String getVariableInfoBody() {
      if (Utils.isVoid(this.md.getReturnType())) {
         return "//returns void\nreturn null;";
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("String varname = data.getAttributeString(\"_return\");\n");
         sb.append("if (varname == null) return null;\n");
         sb.append("VariableInfo[] info = new VariableInfo[1];\n");
         if (this instanceof HomeCollectionGenerator) {
            sb.append("info[0] = new VariableInfo(varname, \"" + this.bg.getDD().getRemoteType() + "\", true, VariableInfo.NESTED);\n");
         } else {
            String vartype = this.md.getReturnType();
            if (Utils.isPrimitive(vartype)) {
               vartype = Utils.primitive2Object(vartype);
            }

            sb.append("info[0] = new VariableInfo (varname,");
            sb.append("\"" + vartype + "\",");
            sb.append("true,VariableInfo.AT_BEGIN);\n");
         }

         sb.append("return info;");
         return sb.toString();
      }
   }

   public String simpleTagClassName() {
      return this.simpleClassName() + "TEI";
   }

   public String isValidBody() {
      StringBuffer sb = new StringBuffer();
      MethodParamDescriptor[] mds = this.md.getParams();
      int sz = mds.length;
      if (sz == 0) {
         return "return true ;";
      } else {
         for(int i = 0; i < sz; ++i) {
            if (!"EXPRESSION".equalsIgnoreCase(mds[i].getDefault()) && !"METHOD".equalsIgnoreCase(mds[i].getDefault())) {
               sb.append("if (data.getAttribute(\"" + mds[i].getName() + "\") == null) return false;\n");
            }
         }

         sb.append("return true;");
         return sb.toString();
      }
   }
}
