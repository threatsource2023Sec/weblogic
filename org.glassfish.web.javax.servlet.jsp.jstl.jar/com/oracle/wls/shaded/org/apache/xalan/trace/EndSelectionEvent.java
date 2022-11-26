package com.oracle.wls.shaded.org.apache.xalan.trace;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xpath.XPath;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;

public class EndSelectionEvent extends SelectionEvent {
   public EndSelectionEvent(TransformerImpl processor, Node sourceNode, ElemTemplateElement styleNode, String attributeName, XPath xpath, XObject selection) {
      super(processor, sourceNode, styleNode, attributeName, xpath, selection);
   }
}
