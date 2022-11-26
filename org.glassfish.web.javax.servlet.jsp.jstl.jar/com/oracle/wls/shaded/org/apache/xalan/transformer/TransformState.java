package com.oracle.wls.shaded.org.apache.xalan.transformer;

import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplate;
import com.oracle.wls.shaded.org.apache.xalan.templates.ElemTemplateElement;
import com.oracle.wls.shaded.org.apache.xml.serializer.TransformStateSetter;
import javax.xml.transform.Transformer;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface TransformState extends TransformStateSetter {
   ElemTemplateElement getCurrentElement();

   Node getCurrentNode();

   ElemTemplate getCurrentTemplate();

   ElemTemplate getMatchedTemplate();

   Node getMatchedNode();

   NodeIterator getContextNodeList();

   Transformer getTransformer();
}
