package com.oracle.wls.shaded.org.apache.xalan.extensions;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xalan.templates.Stylesheet;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xpath.functions.FuncExtFunction;
import java.io.IOException;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public abstract class ExtensionHandler {
   protected String m_namespaceUri;
   protected String m_scriptLang;

   static Class getClassForName(String className) throws ClassNotFoundException {
      if (className.equals("com.oracle.wls.shaded.org.apache.xalan.xslt.extensions.Redirect")) {
         className = "com.oracle.wls.shaded.org.apache.xalan.lib.Redirect";
      }

      return ObjectFactory.findProviderClass(className, ObjectFactory.findClassLoader(), true);
   }

   protected ExtensionHandler(String namespaceUri, String scriptLang) {
      this.m_namespaceUri = namespaceUri;
      this.m_scriptLang = scriptLang;
   }

   public abstract boolean isFunctionAvailable(String var1);

   public abstract boolean isElementAvailable(String var1);

   public abstract Object callFunction(String var1, Vector var2, Object var3, ExpressionContext var4) throws TransformerException;

   public abstract Object callFunction(FuncExtFunction var1, Vector var2, ExpressionContext var3) throws TransformerException;

   public abstract void processElement(String var1, ElemTemplateElement var2, TransformerImpl var3, Stylesheet var4, Object var5) throws TransformerException, IOException;
}
