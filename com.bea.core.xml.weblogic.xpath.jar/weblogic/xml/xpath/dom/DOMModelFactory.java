package weblogic.xml.xpath.dom;

import java.util.HashMap;
import java.util.Map;
import weblogic.xml.xpath.XPathUnsupportedException;
import weblogic.xml.xpath.common.Axis;
import weblogic.xml.xpath.common.BaseModelFactory;
import weblogic.xml.xpath.dom.axes.AncestorAxis;
import weblogic.xml.xpath.dom.axes.AncestorOrSelfAxis;
import weblogic.xml.xpath.dom.axes.AttributeAxis;
import weblogic.xml.xpath.dom.axes.ChildAxis;
import weblogic.xml.xpath.dom.axes.DescendantAxis;
import weblogic.xml.xpath.dom.axes.DescendantOrSelfAxis;
import weblogic.xml.xpath.dom.axes.DocumentRootAxis;
import weblogic.xml.xpath.dom.axes.FollowingAxis;
import weblogic.xml.xpath.dom.axes.FollowingSiblingAxis;
import weblogic.xml.xpath.dom.axes.NamespaceAxis;
import weblogic.xml.xpath.dom.axes.ParentAxis;
import weblogic.xml.xpath.dom.axes.PrecedingAxis;
import weblogic.xml.xpath.dom.axes.PrecedingSiblingAxis;

public final class DOMModelFactory extends BaseModelFactory {
   private final Map mAxesMap = new HashMap();

   public DOMModelFactory(Map vb) {
      super(DOMInterrogator.INSTANCE, vb);
   }

   protected Axis getAxis(int a) throws XPathUnsupportedException {
      switch (a) {
         case 1:
            return AncestorAxis.INSTANCE;
         case 2:
            return AncestorOrSelfAxis.INSTANCE;
         case 3:
            return AttributeAxis.INSTANCE;
         case 4:
            return ChildAxis.INSTANCE;
         case 5:
            return DescendantAxis.INSTANCE;
         case 6:
            return DescendantOrSelfAxis.INSTANCE;
         case 7:
            return FollowingAxis.INSTANCE;
         case 8:
            return FollowingSiblingAxis.INSTANCE;
         case 9:
            return NamespaceAxis.INSTANCE;
         case 10:
            return ParentAxis.INSTANCE;
         case 11:
            return PrecedingAxis.INSTANCE;
         case 12:
            return PrecedingSiblingAxis.INSTANCE;
         case 13:
         default:
            return super.getAxis(a);
         case 14:
            return DocumentRootAxis.INSTANCE;
      }
   }
}
