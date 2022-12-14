package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xpath.XPath;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

public class ElemTemplate extends ElemTemplateElement {
   static final long serialVersionUID = -5283056789965384058L;
   private String m_publicId;
   private String m_systemId;
   private Stylesheet m_stylesheet;
   private XPath m_matchPattern = null;
   private QName m_name = null;
   private QName m_mode;
   private double m_priority = Double.NEGATIVE_INFINITY;
   public int m_frameSize;
   int m_inArgsSize;
   private int[] m_argsQNameIDs;

   public String getPublicId() {
      return this.m_publicId;
   }

   public String getSystemId() {
      return this.m_systemId;
   }

   public void setLocaterInfo(SourceLocator locator) {
      this.m_publicId = locator.getPublicId();
      this.m_systemId = locator.getSystemId();
      super.setLocaterInfo(locator);
   }

   public StylesheetComposed getStylesheetComposed() {
      return this.m_stylesheet.getStylesheetComposed();
   }

   public Stylesheet getStylesheet() {
      return this.m_stylesheet;
   }

   public void setStylesheet(Stylesheet sheet) {
      this.m_stylesheet = sheet;
   }

   public StylesheetRoot getStylesheetRoot() {
      return this.m_stylesheet.getStylesheetRoot();
   }

   public void setMatch(XPath v) {
      this.m_matchPattern = v;
   }

   public XPath getMatch() {
      return this.m_matchPattern;
   }

   public void setName(QName v) {
      this.m_name = v;
   }

   public QName getName() {
      return this.m_name;
   }

   public void setMode(QName v) {
      this.m_mode = v;
   }

   public QName getMode() {
      return this.m_mode;
   }

   public void setPriority(double v) {
      this.m_priority = v;
   }

   public double getPriority() {
      return this.m_priority;
   }

   public int getXSLToken() {
      return 19;
   }

   public String getNodeName() {
      return "template";
   }

   public void compose(StylesheetRoot sroot) throws TransformerException {
      super.compose(sroot);
      StylesheetRoot.ComposeState cstate = sroot.getComposeState();
      Vector vnames = cstate.getVariableNames();
      if (null != this.m_matchPattern) {
         this.m_matchPattern.fixupVariables(vnames, sroot.getComposeState().getGlobalsSize());
      }

      cstate.resetStackFrameSize();
      this.m_inArgsSize = 0;
   }

   public void endCompose(StylesheetRoot sroot) throws TransformerException {
      StylesheetRoot.ComposeState cstate = sroot.getComposeState();
      super.endCompose(sroot);
      this.m_frameSize = cstate.getFrameSize();
      cstate.resetStackFrameSize();
   }

   public void execute(TransformerImpl transformer) throws TransformerException {
      XPathContext xctxt = transformer.getXPathContext();
      transformer.getStackGuard().checkForInfinateLoop();
      xctxt.pushRTFContext();
      if (transformer.getDebug()) {
         transformer.getTraceManager().fireTraceEvent((ElemTemplateElement)this);
      }

      transformer.executeChildTemplates(this, true);
      if (transformer.getDebug()) {
         transformer.getTraceManager().fireTraceEndEvent((ElemTemplateElement)this);
      }

      xctxt.popRTFContext();
   }

   public void recompose(StylesheetRoot root) {
      root.recomposeTemplates(this);
   }
}
