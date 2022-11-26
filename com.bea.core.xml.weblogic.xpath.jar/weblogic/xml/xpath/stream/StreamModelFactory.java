package weblogic.xml.xpath.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import weblogic.xml.xpath.XPathUnsupportedException;
import weblogic.xml.xpath.common.BaseModelFactory;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.parser.LocationPathModel;
import weblogic.xml.xpath.parser.NodeTestModel;
import weblogic.xml.xpath.parser.StepModel;
import weblogic.xml.xpath.stream.axes.AncestorAxis;
import weblogic.xml.xpath.stream.axes.AncestorOrSelfAxis;
import weblogic.xml.xpath.stream.axes.AttributeAxis;
import weblogic.xml.xpath.stream.axes.ChildAxis;
import weblogic.xml.xpath.stream.axes.DescendantAxis;
import weblogic.xml.xpath.stream.axes.DescendantOrSelfAxis;
import weblogic.xml.xpath.stream.axes.DocumentRootAxis;
import weblogic.xml.xpath.stream.axes.EverythingAxis;
import weblogic.xml.xpath.stream.axes.FollowingAxis;
import weblogic.xml.xpath.stream.axes.FollowingSiblingAxis;
import weblogic.xml.xpath.stream.axes.NamespaceAxis;
import weblogic.xml.xpath.stream.axes.SelfAxis;
import weblogic.xml.xpath.stream.nodetests.AttributeNodeTest;
import weblogic.xml.xpath.stream.nodetests.CommentNodeTest;
import weblogic.xml.xpath.stream.nodetests.ElementNodeTest;
import weblogic.xml.xpath.stream.nodetests.LocalNameNodeTest;
import weblogic.xml.xpath.stream.nodetests.NamespaceNodeTest;
import weblogic.xml.xpath.stream.nodetests.NodeNodeTest;
import weblogic.xml.xpath.stream.nodetests.ProcessingInstructionNodeTest;
import weblogic.xml.xpath.stream.nodetests.QNameNodeTest;
import weblogic.xml.xpath.stream.nodetests.TextNodeTest;

public final class StreamModelFactory extends BaseModelFactory {
   private StreamContextRequirements mReqs = new StreamContextRequirements();

   public StreamModelFactory() {
      super(StreamInterrogator.INSTANCE, (Map)null);
      this.setFunctionUnsupported("last");
      this.setFunctionUnsupported("size");
      this.setFunctionUnsupported("count");
      this.setFunctionUnsupported("id");
      this.setFunctionUnsupported("lang");
   }

   public StreamContextRequirements getRequirements() {
      return this.mReqs;
   }

   public NodeTestModel createNameNodeTest(String name) {
      return new LocalNameNodeTest(name);
   }

   public NodeTestModel createNameNodeTest(String prefix, String name) {
      return new QNameNodeTest(prefix, name);
   }

   public LocationPathModel createLocationPath(Collection steps) {
      Step[] array = new Step[steps.size()];
      steps.toArray(array);
      return new LocationPath(array);
   }

   public StepModel createStep(int axisNumber, NodeTestModel nodeTest, Collection predicateExprs) throws XPathUnsupportedException {
      Axis axis = null;
      switch (axisNumber) {
         case 0:
            axis = SelfAxis.INSTANCE;
            break;
         case 1:
            this.mReqs.setAncestorAxisUsed(true);
            axis = AncestorAxis.INSTANCE;
            break;
         case 2:
            this.mReqs.setAncestorAxisUsed(true);
            axis = AncestorOrSelfAxis.INSTANCE;
            break;
         case 3:
            axis = AttributeAxis.INSTANCE;
            break;
         case 4:
            axis = ChildAxis.INSTANCE;
            break;
         case 5:
            axis = DescendantAxis.INSTANCE;
            break;
         case 6:
            axis = DescendantOrSelfAxis.INSTANCE;
            break;
         case 7:
            axis = FollowingAxis.INSTANCE;
            break;
         case 8:
            axis = FollowingSiblingAxis.INSTANCE;
            break;
         case 9:
            this.mReqs.setNamespaceAxisUsed(true);
            axis = NamespaceAxis.INSTANCE;
            break;
         case 10:
         case 11:
         case 12:
         default:
            throw new XPathUnsupportedException("the '" + (axisNumber >= 0 && axisNumber < AXIS_NAMES.size() ? (String)AXIS_NAMES.get(axisNumber) : axisNumber + "?") + "' axis is not supported with an XML stream.");
         case 13:
            axis = EverythingAxis.INSTANCE;
            this.mReqs.setNamespaceAxisUsed(true);
            this.mReqs.setRecordingRequired(true);
            break;
         case 14:
            axis = DocumentRootAxis.INSTANCE;
      }

      if (predicateExprs != null && predicateExprs.size() != 0) {
         Expression[] preds = new Expression[predicateExprs.size()];
         predicateExprs.toArray(preds);

         for(int i = 0; i < preds.length; ++i) {
            this.validateAsPredicateExpr(preds[i], axis, (NodeTest)nodeTest);
         }

         return new Step(axis, (NodeTest)nodeTest, preds);
      } else {
         return new Step(axis, (NodeTest)nodeTest);
      }
   }

   public StepModel getDoubleSlashStep() throws XPathUnsupportedException {
      return this.createStep(6, this.getNodeNodeTest(), (Collection)null);
   }

   public StepModel getSelfStep() throws XPathUnsupportedException {
      return this.createStep(0, this.getNodeNodeTest(), (Collection)null);
   }

   public StepModel getParentStep() throws XPathUnsupportedException {
      return this.createStep(10, this.getNodeNodeTest(), (Collection)null);
   }

   public StepModel getDocumentRootStep() throws XPathUnsupportedException {
      return this.createStep(14, this.getNodeNodeTest(), (Collection)null);
   }

   public NodeTestModel getCommentNodeTest() {
      return CommentNodeTest.INSTANCE;
   }

   public NodeTestModel getTextNodeTest() {
      return TextNodeTest.INSTANCE;
   }

   public NodeTestModel getElementNodeTest() {
      return ElementNodeTest.INSTANCE;
   }

   public NodeTestModel getNodeNodeTest() {
      return NodeNodeTest.INSTANCE;
   }

   public NodeTestModel getEmptyPINodeTest() {
      return ProcessingInstructionNodeTest.INSTANCE;
   }

   public NodeTestModel createPINodeTest(String literal) {
      return new ProcessingInstructionNodeTest(literal);
   }

   public NodeTestModel getAttributeNodeTest() {
      return AttributeNodeTest.INSTANCE;
   }

   public NodeTestModel getNamespaceNodeTest() {
      return NamespaceNodeTest.INSTANCE;
   }

   private void validateAsPredicateExpr(Expression e, Axis axis, NodeTest nt) throws XPathUnsupportedException {
      List list = new ArrayList();
      e.getSubsRequiringStringConversion(2, list);
      int i = 0;

      int iL;
      for(iL = list.size(); i < iL; ++i) {
         Object expr = list.get(i);
         if (expr instanceof LocationPath) {
            ((LocationPath)expr).validateStringConversion();
         } else if (expr == Expression.CONTEXT_NODE_DUMMY && !axis.isStringConvertible() && !nt.isStringConvertible()) {
            throw new XPathUnsupportedException("StreamXPath does not allow the conversion of a the context node to a string if it might be an element node.  Note that this inludes cases where the context node must be converted to a string, e.g. with the 'string()' function.(" + axis + "::" + nt + ")");
         }
      }

      list.clear();
      e.getSubExpressions(list);
      i = 0;

      for(iL = list.size(); i < iL; ++i) {
         Expression expr = (Expression)list.get(i);
         if (expr instanceof LocationPath) {
            ((LocationPath)expr).validateAsPredicateExpr();
         }
      }

   }
}
