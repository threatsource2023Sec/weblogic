package org.apache.taglibs.standard.tag.common.xml;

import java.util.List;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.LoopTagSupport;
import org.apache.taglibs.standard.resources.Resources;
import org.w3c.dom.Node;

public class ForEachTag extends LoopTagSupport {
   private String select;
   private List nodes;
   private int nodesIndex;
   private Node current;

   protected void prepare() throws JspTagException {
      this.nodesIndex = 0;
      XPathUtil xu = new XPathUtil(this.pageContext);
      this.nodes = xu.selectNodes(XPathUtil.getContext(this), this.select);
   }

   protected boolean hasNext() throws JspTagException {
      return this.nodesIndex < this.nodes.size();
   }

   protected Object next() throws JspTagException {
      Object o = this.nodes.get(this.nodesIndex++);
      if (!(o instanceof Node)) {
         throw new JspTagException(Resources.getMessage("FOREACH_NOT_NODESET"));
      } else {
         this.current = (Node)o;
         return this.current;
      }
   }

   public void release() {
      this.init();
      super.release();
   }

   public void setSelect(String select) {
      this.select = select;
   }

   public void setBegin(int begin) throws JspTagException {
      this.beginSpecified = true;
      this.begin = begin;
      this.validateBegin();
   }

   public void setEnd(int end) throws JspTagException {
      this.endSpecified = true;
      this.end = end;
      this.validateEnd();
   }

   public void setStep(int step) throws JspTagException {
      this.stepSpecified = true;
      this.step = step;
      this.validateStep();
   }

   public Node getContext() throws JspTagException {
      return this.current;
   }

   private void init() {
      this.select = null;
      this.nodes = null;
      this.nodesIndex = 0;
      this.current = null;
   }
}
