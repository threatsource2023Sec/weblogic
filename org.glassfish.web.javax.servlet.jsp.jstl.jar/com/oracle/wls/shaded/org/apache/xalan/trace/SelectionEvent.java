package com.oracle.wls.shaded.org.apache.xalan.trace;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xpath.XPath;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.EventListener;
import org.w3c.dom.Node;

public class SelectionEvent implements EventListener {
   public final ElemTemplateElement m_styleNode;
   public final TransformerImpl m_processor;
   public final Node m_sourceNode;
   public final String m_attributeName;
   public final XPath m_xpath;
   public final XObject m_selection;

   public SelectionEvent(TransformerImpl processor, Node sourceNode, ElemTemplateElement styleNode, String attributeName, XPath xpath, XObject selection) {
      this.m_processor = processor;
      this.m_sourceNode = sourceNode;
      this.m_styleNode = styleNode;
      this.m_attributeName = attributeName;
      this.m_xpath = xpath;
      this.m_selection = selection;
   }
}
