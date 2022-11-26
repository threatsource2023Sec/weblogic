package weblogic.xml.process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.Debug;
import weblogic.utils.StringUtils;

class PActionCode {
   private static final boolean debug = true;
   private static final boolean verbose = false;
   private static final String FUNCTION_VAR_NAME_PREFIX = "__fn";
   private static final String FUNCTION_PREFIX = "@";
   private static final String FUNCTION_OPEN_BRACE = "{";
   private static final String FUNCTION_CLOSE_BRACE = "}";
   private static final String VALUE_FUNCTION_NAME = "VALUE";
   private String javaCode;
   private PAction action;
   private Map functionRefs = new HashMap();

   public PActionCode(PAction a) {
      Debug.assertion(a != null);
      this.action = a;
      this.javaCode = this.action.getJavaCode();
      if (this.javaCode == null) {
         this.javaCode = new String();
      }

      if (this.action.getValidation() != null) {
         this.addFunctionRef(new XPathFunctionRef("VALUE", ""));
      }

      if (this.javaCode != null) {
         this.initializeRefLists();
      }

   }

   public String getJavaCode() {
      return this.javaCode;
   }

   public Map getFunctionRefs() {
      return this.functionRefs;
   }

   public String getFunctionValueVarName(FunctionRef fref) {
      return (String)this.functionRefs.get(fref);
   }

   public void replaceFunctionRefs() {
      Iterator i = this.functionRefs.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry ent = (Map.Entry)i.next();
         FunctionRef fref = (FunctionRef)ent.getKey();
         String varName = (String)ent.getValue();
         this.replaceGlobal(fref.toString(), " " + varName + " ");
      }

   }

   private void replaceGlobal(String substr, String newSubStr) {
      if (this.javaCode != null) {
         this.javaCode = StringUtils.replaceGlobal(this.javaCode.toString(), substr, newSubStr);
      }

   }

   private void initializeRefLists() {
      Debug.assertion(this.javaCode != null);
      String buf = new String(this.javaCode);
      int startPos = 0;
      int prefixPos = false;
      int openBracePos = false;
      int closeBracePos = false;

      while(true) {
         int prefixPos = buf.indexOf("@", startPos);
         if (prefixPos < 0) {
            break;
         }

         if (inQuote(buf, prefixPos)) {
            ++startPos;
         } else {
            int openBracePos = buf.indexOf("{", prefixPos + 1);
            if (openBracePos < 0) {
               break;
            }

            String funcName = buf.substring(prefixPos + 1, openBracePos);
            if (funcName.equals(funcName.trim())) {
               int closeBracePos = buf.indexOf("}", openBracePos + 1);
               if (closeBracePos < 0) {
                  break;
               }

               String expr = buf.substring(openBracePos + 1, closeBracePos);
               XPathFunctionRef fref = new XPathFunctionRef(funcName, expr);
               this.addFunctionRef(fref);
               startPos = closeBracePos + 1;
            }
         }
      }

   }

   private static boolean inQuote(String checkStr, int pos) {
      boolean inQuote = false;
      Debug.assertion(pos >= 0 && pos < checkStr.length());

      for(int i = 0; i < pos; ++i) {
         if (checkStr.charAt(i) == '"' && (i <= 1 || checkStr.charAt(i - 1) != '\\')) {
            inQuote = !inQuote;
         }
      }

      return inQuote;
   }

   private void addFunctionRef(FunctionRef fref) {
      int varCount = this.functionRefs.size() + 1;
      if (this.functionRefs.get(fref) == null) {
         String varName = "__fn" + varCount + "_";
         this.functionRefs.put(fref, varName);
      }

   }
}
