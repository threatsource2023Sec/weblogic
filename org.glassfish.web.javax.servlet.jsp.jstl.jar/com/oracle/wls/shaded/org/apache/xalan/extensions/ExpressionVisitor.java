package com.oracle.wls.shaded.org.apache.xalan.extensions;

import com.oracle.wls.shaded.org.apache.xalan.templates.StylesheetRoot;
import com.oracle.wls.shaded.org.apache.xpath.ExpressionOwner;
import com.oracle.wls.shaded.org.apache.xpath.XPathVisitor;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncExtFunction;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncExtFunctionAvailable;
import com.oracle.wls.shaded.org.apache.xpath.functions.Function;

public class ExpressionVisitor extends XPathVisitor {
   private StylesheetRoot m_sroot;

   public ExpressionVisitor(StylesheetRoot sroot) {
      this.m_sroot = sroot;
   }

   public boolean visitFunction(ExpressionOwner owner, Function func) {
      String arg;
      if (func instanceof FuncExtFunction) {
         arg = ((FuncExtFunction)func).getNamespace();
         this.m_sroot.getExtensionNamespacesManager().registerExtension(arg);
      } else if (func instanceof FuncExtFunctionAvailable) {
         arg = ((FuncExtFunctionAvailable)func).getArg0().toString();
         if (arg.indexOf(":") > 0) {
            String prefix = arg.substring(0, arg.indexOf(":"));
            String namespace = this.m_sroot.getNamespaceForPrefix(prefix);
            this.m_sroot.getExtensionNamespacesManager().registerExtension(namespace);
         }
      }

      return true;
   }
}
