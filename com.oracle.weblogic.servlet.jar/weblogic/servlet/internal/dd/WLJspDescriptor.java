package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.JspDescriptorMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class WLJspDescriptor extends BaseServletDescriptor implements ToXML, JspDescriptorMBean {
   private static final String JSP_PARAM = "jsp-param";
   private List jspParams;

   public WLJspDescriptor() {
      this.jspParams = new ArrayList();
   }

   public WLJspDescriptor(List jp) {
      this.jspParams = jp;
   }

   public WLJspDescriptor(JspDescriptorMBean mbean) {
      this();
      if (mbean != null) {
         this.setDefaultFileName(mbean.getDefaultFileName());
         this.setCompileCommand(mbean.getCompileCommand());
         this.setPrecompile(mbean.isPrecompile());
         this.setPrecompile(mbean.isPrecompileContinue());
         this.setCompilerClass(mbean.getCompilerClass());
         this.setCompileFlags(mbean.getCompileFlags());
         this.setWorkingDir(mbean.getWorkingDir());
         this.setVerbose(mbean.isVerbose());
         this.setCompilerSupportsEncoding(mbean.getCompilerSupportsEncoding());
         this.setKeepgenerated(mbean.isKeepgenerated());
         this.setPageCheckSeconds(mbean.getPageCheckSeconds());
         this.setEncoding(mbean.getEncoding());
         this.setPackagePrefix(mbean.getPackagePrefix());
         this.setSuperclass(mbean.getSuperclass());
         this.setNoTryBlocks(mbean.isNoTryBlocks());
         this.setDebugEnabled(mbean.isDebugEnabled());
         this.setBackwardCompatible(mbean.isBackwardCompatible());
         this.setPrintNulls(mbean.getPrintNulls());
         this.setJspServlet(mbean.getJspServlet());
         this.setJspPrecompiler(mbean.getJspPrecompiler());
      }

   }

   public WLJspDescriptor(Element parentElement) throws DOMProcessingException {
      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "jsp-param");
      Iterator i = elts.iterator();
      this.jspParams = new ArrayList(elts.size());

      while(i.hasNext()) {
         ParameterDescriptor pd = new ParameterDescriptor((Element)i.next());
         String paramName = pd.getParamName();
         if (!isValidParam(paramName)) {
            if (paramName != null && paramName.length() == 0) {
               HTTPLogger.logEmptyJspParamName();
            } else {
               HTTPLogger.logInvalidJspParamName(paramName);
            }

            throw new DOMProcessingException();
         }

         this.jspParams.add(pd);
      }

   }

   private static boolean isValidParam(String name) {
      if (name == null) {
         return false;
      } else {
         return name.equalsIgnoreCase("defaultFileName") || name.equalsIgnoreCase("compileCommand") || name.equalsIgnoreCase("compileFlags") || name.equalsIgnoreCase("compilerclass") || name.equalsIgnoreCase("workingDir") || name.equalsIgnoreCase("verbose") || name.equalsIgnoreCase("keepgenerated") || name.equalsIgnoreCase("compilerSupportsEncoding") || name.equalsIgnoreCase("pageCheckSeconds") || name.equalsIgnoreCase("encoding") || name.equalsIgnoreCase("packagePrefix") || name.equalsIgnoreCase("noTryBlocks") || name.equalsIgnoreCase("precompile") || name.equalsIgnoreCase("precompileContinue") || name.equalsIgnoreCase("exactMapping") || name.equalsIgnoreCase("superclass") || name.equalsIgnoreCase("debug") || name.equalsIgnoreCase("backwardCompatible") || name.equalsIgnoreCase("printNulls") || name.equalsIgnoreCase("jspServlet") || name.equalsIgnoreCase("jspPrecompiler");
      }
   }

   private static boolean isDefaultValue(String name, String value) {
      if (name == null) {
         return false;
      } else {
         return name.equalsIgnoreCase("verbose") && value.equals("true") || name.equalsIgnoreCase("keepgenerated") && value.equals("false") || name.equalsIgnoreCase("compilerSupportsEncoding") && value.equals("true") || name.equalsIgnoreCase("pageCheckSeconds") && value.equals("1") || name.equalsIgnoreCase("packagePrefix") && value.equals("jsp_servlet") || name.equalsIgnoreCase("noTryBlocks") && value.equals("false") || name.equalsIgnoreCase("precompile") && value.equals("false") || name.equalsIgnoreCase("precompileContinue") && value.equals("false") || name.equalsIgnoreCase("exactMapping") && value.equals("true") || name.equalsIgnoreCase("superclass") && value.equals("weblogic.servlet.jsp.JspBase") || name.equalsIgnoreCase("debug") && value.equals("false") || name.equalsIgnoreCase("backwardCompatible") && value.equals("false") || name.equalsIgnoreCase("printNulls") && value.equals("true");
      }
   }

   public String getDefaultFileName() {
      return this.getProp("defaultFilename", "index.jsp");
   }

   public void setDefaultFileName(String s) {
      String old = this.getDefaultFileName();
      this.setProp("defaultFilename", s);
      if (!comp(old, s)) {
         this.firePropertyChange("defaultFilename", old, s);
      }

   }

   public boolean isNoTryBlocks() {
      return "true".equalsIgnoreCase(this.getProp("noTryBlocks", "false"));
   }

   public void setNoTryBlocks(boolean b) {
      boolean old = this.isNoTryBlocks();
      this.setProp("noTryBlocks", "" + b);
      if (old != b) {
         this.firePropertyChange("noTryBlocks", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isPrecompile() {
      boolean ret = "true".equalsIgnoreCase(this.getProp("precompile", "false"));
      return ret;
   }

   public void setPrecompile(boolean b) {
      boolean old = this.isPrecompile();
      this.setProp("precompile", "" + b);
      if (old != b) {
         this.firePropertyChange("precompile", new Boolean(old), new Boolean(b));
      }

   }

   public boolean isPrecompileContinue() {
      boolean ret = "true".equalsIgnoreCase(this.getProp("precompileContinue", "false"));
      return ret;
   }

   public void setPrecompileContinue(boolean b) {
      boolean old = this.isPrecompileContinue();
      this.setProp("precompileContinue", "" + b);
      if (old != b) {
         this.firePropertyChange("precompileContinue", new Boolean(old), new Boolean(b));
      }

   }

   public boolean isExactMapping() {
      boolean ret = "true".equalsIgnoreCase(this.getProp("exactMapping", "true"));
      return ret;
   }

   public void setExactMapping(boolean b) {
      boolean old = this.isExactMapping();
      this.setProp("exactMapping", "" + b);
      if (b != old) {
         this.firePropertyChange("exactMapping", new Boolean(old), new Boolean(b));
      }

   }

   public String getSuperclass() {
      return this.getProp("superclass", "weblogic.servlet.jsp.JspBase");
   }

   public void setSuperclass(String superc) {
      String old = this.getSuperclass();
      this.setProp("superclass", superc);
      if (!comp(old, superc)) {
         this.firePropertyChange("superclass", old, superc);
      }

   }

   public String getCompileCommand() {
      return this.getProp("compileCommand", (String)null);
   }

   public void setCompileCommand(String s) {
      String old = this.getCompileCommand();
      this.setProp("compileCommand", s);
      if (!comp(old, s)) {
         this.firePropertyChange("compileCommand", old, s);
      }

   }

   public String getCompileFlags() {
      return this.getProp("compileFlags", (String)null);
   }

   public void setCompileFlags(String s) {
      String old = this.getCompileFlags();
      this.setProp("compileFlags", s);
      if (!comp(old, s)) {
         this.firePropertyChange("compileFlags", old, s);
      }

   }

   public String getCompilerClass() {
      return this.getProp("compilerclass", (String)null);
   }

   public void setCompilerClass(String s) {
      String old = this.getCompilerClass();
      this.setProp("compilerclass", s);
      if (!comp(old, s)) {
         this.firePropertyChange("compilerclass", old, s);
      }

   }

   public String getWorkingDir() {
      return this.getProp("workingDir", (String)null);
   }

   public void setWorkingDir(String s) {
      if (s != null && (s = s.trim()).length() > 0) {
         String old = this.getWorkingDir();
         this.setProp("workingDir", s);
         if (!comp(old, s)) {
            this.firePropertyChange("workingDir", old, s);
         }
      }

   }

   public String getEncoding() {
      return this.getProp("encoding", (String)null);
   }

   public void setEncoding(String s) {
      if (s != null && (s = s.trim()).length() > 0) {
         String old = this.getEncoding();
         this.setProp("encoding", s);
         if (!comp(old, s)) {
            this.firePropertyChange("encoding", old, s);
         }
      }

   }

   public boolean getCompilerSupportsEncoding() {
      return !"false".equalsIgnoreCase(this.getProp("compilerSupportsEncoding", "true"));
   }

   public void setCompilerSupportsEncoding(boolean b) {
      boolean old = this.getCompilerSupportsEncoding();
      this.setProp("compilerSupportsEncoding", "" + b);
      if (b != old) {
         this.firePropertyChange("compilerSupportsEncoding", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean getVerbose() {
      return this.isVerbose();
   }

   public boolean isVerbose() {
      return !"false".equalsIgnoreCase(this.getProp("verbose", "true"));
   }

   public void setVerbose(boolean b) {
      boolean old = this.isVerbose();
      this.setProp("verbose", "" + b);
      if (old != b) {
         this.firePropertyChange("verbose", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean getKeepgenerated() {
      return this.isKeepgenerated();
   }

   public boolean isKeepgenerated() {
      return "true".equalsIgnoreCase(this.getProp("keepgenerated", "false"));
   }

   public void setKeepgenerated(boolean b) {
      boolean old = this.isKeepgenerated();
      this.setProp("keepgenerated", "" + b);
      if (old != b) {
         this.firePropertyChange("keepgenerated", new Boolean(!b), new Boolean(b));
      }

   }

   public int getPageCheckSeconds() {
      String s = this.getProp("pageCheckSeconds", "1");

      try {
         return Integer.parseInt(s.trim());
      } catch (NumberFormatException var3) {
         return 1;
      }
   }

   public void setPageCheckSeconds(int i) {
      int old = this.getPageCheckSeconds();
      this.setProp("pageCheckSeconds", "" + i);
      if (old != i) {
         this.firePropertyChange("pageCheckSeconds", new Integer(old), new Integer(i));
      }

   }

   public String getPackagePrefix() {
      return this.getProp("packagePrefix", "jsp_servlet");
   }

   public void setPackagePrefix(String s) {
      if (s != null && (s = s.trim()).length() > 0) {
         String old = this.getPackagePrefix();
         this.setProp("packagePrefix", s);
         if (!comp(old, s)) {
            this.firePropertyChange("packagePrefix", old, s);
         }
      }

   }

   public void setDebugEnabled(boolean b) {
      if (b != this.isDebugEnabled()) {
         this.setProp("debug", "" + b);
         this.firePropertyChange("debug", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isDebugEnabled() {
      return "true".equalsIgnoreCase(this.getProp("debug", "false"));
   }

   public boolean getDebugEnabled() {
      return this.isDebugEnabled();
   }

   public void setBackwardCompatible(boolean b) {
      if (b != this.isBackwardCompatible()) {
         this.setProp("backwardCompatible", "" + b);
         this.firePropertyChange("backwardCompatible", new Boolean(!b), new Boolean(b));
      }

   }

   public boolean isBackwardCompatible() {
      return "true".equalsIgnoreCase(this.getProp("backwardCompatible", "false"));
   }

   public boolean getBackwardCompatible() {
      return this.isBackwardCompatible();
   }

   public void setPrintNulls(boolean u) {
      if (u != this.getPrintNulls()) {
         this.setProp("printNulls", "" + u);
         this.firePropertyChange("printNulls", new Boolean(!u), new Boolean(u));
      }

   }

   public boolean getPrintNulls() {
      return !"false".equalsIgnoreCase(this.getProp("printNulls", "true"));
   }

   public String getJspServlet() {
      return this.getProp("jspServlet", "");
   }

   public void setJspServlet(String clazz) {
      String old = this.getJspServlet();
      this.setProp("jspServlet", clazz);
      if (!comp(old, clazz)) {
         this.firePropertyChange("jspServlet", old, clazz);
      }

   }

   public String getJspPrecompiler() {
      return this.getProp("jspPrecompiler", "");
   }

   public void setJspPrecompiler(String clazz) {
      String old = this.getJspPrecompiler();
      this.setProp("jspPrecompiler", clazz);
      if (!comp(old, clazz)) {
         this.firePropertyChange("jspPrecompiler", old, clazz);
      }

   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   private void setProp(String name, String val) {
      Iterator i = this.jspParams.iterator();

      ParameterDescriptor pd;
      do {
         if (!i.hasNext()) {
            if (val != null) {
               pd = new ParameterDescriptor();
               pd.setParamName(name);
               pd.setParamValue(val);
               this.jspParams.add(pd);
            }

            return;
         }

         pd = (ParameterDescriptor)i.next();
      } while(!name.equalsIgnoreCase(pd.getParamName()));

      if (val == null) {
         this.jspParams.remove(pd);
      } else {
         pd.setParamName(name);
         pd.setParamValue(val);
      }

   }

   private String getProp(String name, String defalt) {
      Iterator i = this.jspParams.iterator();

      ParameterDescriptor pd;
      do {
         if (!i.hasNext()) {
            if (defalt != null && defalt.length() > 0) {
               pd = new ParameterDescriptor();
               pd.setParamName(name);
               pd.setParamValue(defalt);
               this.jspParams.add(pd);
            }

            return defalt;
         }

         pd = (ParameterDescriptor)i.next();
      } while(!name.equalsIgnoreCase(pd.getParamName()));

      pd.setParamName(name);
      return pd.getParamValue();
   }

   public String toXML(int indent) {
      String result = "";
      boolean found = false;
      if (this.jspParams != null && this.jspParams.size() > 0) {
         Iterator i = this.jspParams.iterator();

         while(i.hasNext()) {
            ParameterDescriptor p = (ParameterDescriptor)i.next();
            if (!isDefaultValue(p.getParamName(), p.getParamValue())) {
               if (!found) {
                  result = result + this.indentStr(indent) + "<jsp-descriptor>\n";
                  indent += 2;
                  found = true;
               }

               result = result + this.indentStr(indent) + "<jsp-param>\n";
               indent += 2;
               result = result + this.indentStr(indent) + "<param-name>" + p.getParamName() + "</param-name>\n";
               result = result + this.indentStr(indent) + "<param-value>" + p.getParamValue() + "</param-value>\n";
               indent -= 2;
               result = result + this.indentStr(indent) + "</jsp-param>\n";
            }
         }

         if (found) {
            indent -= 2;
            result = result + this.indentStr(indent) + "</jsp-descriptor>\n";
         }
      }

      return result;
   }
}
