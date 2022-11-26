package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xml.utils.FastStringBuffer;
import com.oracle.wls.shaded.org.apache.xml.utils.PrefixResolver;
import com.oracle.wls.shaded.org.apache.xpath.XPath;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.XPathFactory;
import com.oracle.wls.shaded.org.apache.xpath.compiler.XPathParser;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

public class AVTPartXPath extends AVTPart {
   static final long serialVersionUID = -4460373807550527675L;
   private XPath m_xpath;

   public void fixupVariables(Vector vars, int globalsSize) {
      this.m_xpath.fixupVariables(vars, globalsSize);
   }

   public boolean canTraverseOutsideSubtree() {
      return this.m_xpath.getExpression().canTraverseOutsideSubtree();
   }

   public AVTPartXPath(XPath xpath) {
      this.m_xpath = xpath;
   }

   public AVTPartXPath(String val, PrefixResolver nsNode, XPathParser xpathProcessor, XPathFactory factory, XPathContext liaison) throws TransformerException {
      this.m_xpath = new XPath(val, (SourceLocator)null, nsNode, 0, liaison.getErrorListener());
   }

   public String getSimpleString() {
      return "{" + this.m_xpath.getPatternString() + "}";
   }

   public void evaluate(XPathContext xctxt, FastStringBuffer buf, int context, PrefixResolver nsNode) throws TransformerException {
      XObject xobj = this.m_xpath.execute(xctxt, context, nsNode);
      if (null != xobj) {
         xobj.appendToFsb(buf);
      }

   }

   public void callVisitors(XSLTVisitor visitor) {
      this.m_xpath.getExpression().callVisitors(this.m_xpath, visitor);
   }
}
