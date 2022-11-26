package weblogic.xml.xpath.xmlnode;

import java.util.HashMap;
import java.util.Map;
import weblogic.xml.xpath.XPathUnsupportedException;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.BaseModelFactory;
import weblogic.xml.xpath.parser.NodeTestModel;
import weblogic.xml.xpath.xmlnode.axes.DocumentRootAxis;
import weblogic.xml.xpath.xmlnode.axes.ParentAxis;

public final class XMLNodeModelFactory extends BaseModelFactory {
   private final Map mAxesMap = new HashMap();

   public XMLNodeModelFactory(Map vb) {
      super(XMLNodeInterrogator.INSTANCE, vb);
      this.setFunctionUnsupported("id");
   }

   protected Axis getAxis(int axis) throws XPathUnsupportedException {
      switch (axis) {
         case 3:
            throw new XPathUnsupportedException("attribute axis not yet supported.");
         case 10:
            return ParentAxis.INSTANCE;
         case 14:
            return DocumentRootAxis.INSTANCE;
         default:
            return super.getAxis(axis);
      }
   }

   public NodeTestModel getCommentNodeTest() throws XPathUnsupportedException {
      throw new XPathUnsupportedException("comment() node test is not supported by XMLNode.");
   }

   public NodeTestModel getEmptyPINodeTest() throws XPathUnsupportedException {
      throw new XPathUnsupportedException("processing-instruction() node test is not supported by XMLNode.");
   }

   public NodeTestModel createPINodeTest(String literal) throws XPathUnsupportedException {
      throw new XPathUnsupportedException("processing-instruction() node test is not supported by XMLNode.");
   }
}
