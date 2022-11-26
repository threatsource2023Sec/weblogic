package weblogic.xml.xpath.common;

import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.xml.xpath.XPathException;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.XPathUnsupportedException;
import weblogic.xml.xpath.common.axes.AncestorAxis;
import weblogic.xml.xpath.common.axes.AncestorOrSelfAxis;
import weblogic.xml.xpath.common.axes.ChildAxis;
import weblogic.xml.xpath.common.axes.DescendantAxis;
import weblogic.xml.xpath.common.axes.DescendantOrSelfAxis;
import weblogic.xml.xpath.common.axes.DocumentRootAxis;
import weblogic.xml.xpath.common.axes.ParentAxis;
import weblogic.xml.xpath.common.axes.SelfAxis;
import weblogic.xml.xpath.common.expressions.AdditionExpression;
import weblogic.xml.xpath.common.expressions.AndExpression;
import weblogic.xml.xpath.common.expressions.CompositionExpression;
import weblogic.xml.xpath.common.expressions.ConstantNodesetExpression;
import weblogic.xml.xpath.common.expressions.ConstantNumberExpression;
import weblogic.xml.xpath.common.expressions.DivisionExpression;
import weblogic.xml.xpath.common.expressions.EqualityExpression;
import weblogic.xml.xpath.common.expressions.FilterExpression;
import weblogic.xml.xpath.common.expressions.GreaterThanExpression;
import weblogic.xml.xpath.common.expressions.GreaterThanOrEqualExpression;
import weblogic.xml.xpath.common.expressions.InequalityExpression;
import weblogic.xml.xpath.common.expressions.LessThanExpression;
import weblogic.xml.xpath.common.expressions.LessThanOrEqualExpression;
import weblogic.xml.xpath.common.expressions.LiteralStringExpression;
import weblogic.xml.xpath.common.expressions.ModulusExpression;
import weblogic.xml.xpath.common.expressions.MultiplicationExpression;
import weblogic.xml.xpath.common.expressions.NegativeExpression;
import weblogic.xml.xpath.common.expressions.OrExpression;
import weblogic.xml.xpath.common.expressions.SubtractionExpression;
import weblogic.xml.xpath.common.expressions.UnionExpression;
import weblogic.xml.xpath.common.functions.FalseFunction;
import weblogic.xml.xpath.common.functions.InterrogatingFunction;
import weblogic.xml.xpath.common.functions.TrueFunction;
import weblogic.xml.xpath.common.nodetests.AnyNodeTest;
import weblogic.xml.xpath.common.nodetests.CommentNodeTest;
import weblogic.xml.xpath.common.nodetests.ElementNodeTest;
import weblogic.xml.xpath.common.nodetests.NameNodeTest;
import weblogic.xml.xpath.common.nodetests.NodeNodeTest;
import weblogic.xml.xpath.common.nodetests.ProcessingInstructionNodeTest;
import weblogic.xml.xpath.common.nodetests.TextNodeTest;
import weblogic.xml.xpath.parser.ExpressionModel;
import weblogic.xml.xpath.parser.LocationPathModel;
import weblogic.xml.xpath.parser.ModelFactory;
import weblogic.xml.xpath.parser.NodeTestModel;
import weblogic.xml.xpath.parser.StepModel;

public class BaseModelFactory implements ModelFactory {
   public static final int AXIS_DOCUMENT_ROOT = 14;
   private Set mUnsupportedFunctions = null;
   private Map mVariableBindings = null;
   private Interrogator mInterrogator = null;
   private StepModel mDoubleSlashStep = null;
   private StepModel mSelfStep = null;
   private StepModel mParentStep = null;
   private StepModel mDocumentRootStep = null;
   private NodeTest mCommentNT = null;
   private NodeTest mNodeNT = null;
   private NodeTest mTextNT = null;
   private NodeTest mEmptyPINT = null;
   private NodeTest mElementNodeTest = null;
   private Axis mChildAxis;
   private Axis mParentAxis;
   private Axis mAncestorAxis;
   private Axis mAncestorOrSelfAxis;
   private Axis mDocumentRootAxis;
   private Axis mDescendantAxis;
   private Axis mDescendantOrSelfAxis;
   private Class[] FUNCTION_CONSTRUCTOR_SIG = new Class[]{Expression[].class};

   protected BaseModelFactory(Interrogator i, Map vb) {
      this.mVariableBindings = vb;
      this.mInterrogator = i;
      this.mChildAxis = new ChildAxis(i);
      this.mParentAxis = new ParentAxis(i);
      this.mDocumentRootAxis = new DocumentRootAxis(i);
      this.mDescendantAxis = new DescendantAxis(i);
      this.mDescendantOrSelfAxis = new DescendantOrSelfAxis(i);
      this.mAncestorAxis = new AncestorAxis(i);
      this.mAncestorOrSelfAxis = new AncestorOrSelfAxis(i);
   }

   protected void setFunctionUnsupported(String fnName) {
      if (this.mUnsupportedFunctions == null) {
         this.mUnsupportedFunctions = new HashSet();
      }

      this.mUnsupportedFunctions.add(fnName);
   }

   protected Axis getAxis(int axisNumber) throws XPathUnsupportedException {
      switch (axisNumber) {
         case 0:
            return SelfAxis.INSTANCE;
         case 1:
            return this.mAncestorAxis;
         case 2:
            return this.mAncestorOrSelfAxis;
         case 3:
         case 7:
         case 8:
         case 9:
         case 11:
         case 12:
         case 13:
         default:
            throw new XPathUnsupportedException("the '" + (axisNumber >= 0 && axisNumber < AXIS_NAMES.size() ? (String)AXIS_NAMES.get(axisNumber) : axisNumber + "?") + "' axis is not supported.");
         case 4:
            return this.mChildAxis;
         case 5:
            return this.mDescendantAxis;
         case 6:
            return this.mDescendantOrSelfAxis;
         case 10:
            return this.mParentAxis;
         case 14:
            return this.mDocumentRootAxis;
      }
   }

   public NodeTestModel getCommentNodeTest() throws XPathUnsupportedException {
      if (this.mCommentNT == null) {
         this.mCommentNT = new CommentNodeTest(this.mInterrogator);
      }

      return this.mCommentNT;
   }

   public NodeTestModel getTextNodeTest() throws XPathUnsupportedException {
      if (this.mTextNT == null) {
         this.mTextNT = new TextNodeTest(this.mInterrogator);
      }

      return this.mTextNT;
   }

   public NodeTestModel getElementNodeTest() throws XPathUnsupportedException {
      if (this.mElementNodeTest == null) {
         this.mElementNodeTest = new ElementNodeTest(this.mInterrogator);
      }

      return this.mElementNodeTest;
   }

   public NodeTestModel getAttributeNodeTest() throws XPathException {
      return AnyNodeTest.INSTANCE;
   }

   public NodeTestModel getNamespaceNodeTest() throws XPathException {
      return AnyNodeTest.INSTANCE;
   }

   public NodeTestModel getNodeNodeTest() throws XPathUnsupportedException {
      if (this.mNodeNT == null) {
         this.mNodeNT = new NodeNodeTest(this.mInterrogator);
      }

      return this.mNodeNT;
   }

   public NodeTestModel getEmptyPINodeTest() throws XPathUnsupportedException {
      if (this.mEmptyPINT == null) {
         this.mEmptyPINT = new ProcessingInstructionNodeTest(this.mInterrogator);
      }

      return this.mEmptyPINT;
   }

   public NodeTestModel createPINodeTest(String literal) throws XPathUnsupportedException {
      return new ProcessingInstructionNodeTest(this.mInterrogator, literal);
   }

   public StepModel getDoubleSlashStep() throws XPathUnsupportedException {
      if (this.mDoubleSlashStep == null) {
         this.mDoubleSlashStep = this.createStep(6, this.getNodeNodeTest(), (Collection)null);
      }

      return this.mDoubleSlashStep;
   }

   public StepModel getSelfStep() throws XPathUnsupportedException {
      if (this.mSelfStep == null) {
         this.mSelfStep = this.createStep(0, this.getNodeNodeTest(), (Collection)null);
      }

      return this.mSelfStep;
   }

   public StepModel getParentStep() throws XPathUnsupportedException {
      if (this.mParentStep == null) {
         this.mParentStep = this.createStep(10, this.getNodeNodeTest(), (Collection)null);
      }

      return this.mParentStep;
   }

   public StepModel getDocumentRootStep() throws XPathUnsupportedException {
      if (this.mDocumentRootStep == null) {
         this.mDocumentRootStep = this.createStep(14, this.getNodeNodeTest(), (Collection)null);
      }

      return this.mDocumentRootStep;
   }

   public NodeTestModel createNameNodeTest(String name) {
      return new NameNodeTest(this.mInterrogator, name);
   }

   public NodeTestModel createNameNodeTest(String prefix, String name) {
      return new NameNodeTest(this.mInterrogator, prefix, name);
   }

   public LocationPathModel createLocationPath(Collection steps) {
      Step[] array = new Step[steps.size()];
      steps.toArray(array);
      return new LocationPath(array, this.mInterrogator);
   }

   public StepModel createStep(int axis, NodeTestModel nodeTest, Collection predicateExprs) throws XPathUnsupportedException {
      if (predicateExprs != null && predicateExprs.size() != 0) {
         Expression[] predArray = new Expression[predicateExprs.size()];
         predicateExprs.toArray(predArray);
         return new Step(this.getAxis(axis), (NodeTest)nodeTest, predArray);
      } else {
         return new Step(this.getAxis(axis), (NodeTest)nodeTest);
      }
   }

   public ExpressionModel createComposition(ExpressionModel filterExpr, LocationPathModel path) throws XPathParsingException {
      if (((Expression)filterExpr).getType() != 1) {
         throw new XPathParsingException(filterExpr.toString() + " must return a nodeset");
      } else {
         return new CompositionExpression((Expression)filterExpr, (Expression)path, this.mInterrogator);
      }
   }

   public ExpressionModel createFilterExpression(ExpressionModel primary, Collection predExprs) {
      Expression[] predArray = new Expression[predExprs.size()];
      predExprs.toArray(predArray);
      return new FilterExpression((Expression)primary, predArray, this.mInterrogator);
   }

   public ExpressionModel createFunctionExpression(String name, Collection argExprs) throws XPathException {
      if (this.mUnsupportedFunctions != null && this.mUnsupportedFunctions.contains(name)) {
         throw new XPathUnsupportedException("the '" + name + "' function is not supported with this document model.");
      } else {
         Constructor constructor = null;

         Class clazz;
         try {
            clazz = Class.forName("weblogic.xml.xpath.common.functions." + function2classname(name));
         } catch (ClassNotFoundException var8) {
            throw new XPathParsingException("Unknown function '" + name + "'", var8);
         }

         try {
            constructor = clazz.getConstructor(this.FUNCTION_CONSTRUCTOR_SIG);
         } catch (NoSuchMethodException var14) {
            if (name.equals("true")) {
               return TrueFunction.INSTANCE;
            }

            if (name.equals("false")) {
               return FalseFunction.INSTANCE;
            }

            throw new XPathParsingException("Unknown function '" + name + "'", var14);
         }

         Expression[] exprArray;
         if (argExprs != null && argExprs.size() != 0) {
            exprArray = new Expression[argExprs.size()];
            argExprs.toArray(exprArray);
         } else {
            exprArray = new Expression[0];
         }

         Object[] argArray = new Object[]{exprArray};
         argArray[0] = exprArray;

         try {
            Expression function = (Expression)constructor.newInstance(argArray);
            if (function instanceof InterrogatingFunction) {
               ((InterrogatingFunction)function).setInterrogator(this.mInterrogator);
            }

            return function;
         } catch (SecurityException var9) {
            throw new XPathParsingException("Unable to create function '" + name + "'", var9);
         } catch (InstantiationException var10) {
            throw new XPathParsingException("Unable to create function '" + name + "'", var10);
         } catch (IllegalArgumentException var11) {
            throw new XPathParsingException("Unable to create function '" + name + "'", var11);
         } catch (IllegalAccessException var12) {
            throw new XPathParsingException("Unable to create function '" + name + "'", var12);
         } catch (InvocationTargetException var13) {
            if (var13.getTargetException() instanceof XPathParsingException) {
               throw (XPathParsingException)var13.getTargetException();
            } else {
               throw new XPathParsingException("Unable to create function '" + name + "'", var13);
            }
         }
      }
   }

   public ExpressionModel createExpression(ExpressionModel l, int op, ExpressionModel r) {
      switch (op) {
         case 100:
            return new SubtractionExpression((Expression)l, (Expression)r);
         case 101:
            return new AdditionExpression((Expression)l, (Expression)r);
         case 102:
            return new MultiplicationExpression((Expression)l, (Expression)r);
         case 103:
            return new DivisionExpression((Expression)l, (Expression)r);
         case 104:
            return new ModulusExpression((Expression)l, (Expression)r);
         case 105:
            return new AndExpression((Expression)l, (Expression)r);
         case 106:
            return new OrExpression((Expression)l, (Expression)r);
         case 107:
            return new UnionExpression((Expression)l, (Expression)r, this.mInterrogator);
         case 199:
            return new InequalityExpression((Expression)l, (Expression)r, this.mInterrogator);
         case 200:
            return new EqualityExpression((Expression)l, (Expression)r, this.mInterrogator);
         case 201:
            return new LessThanExpression((Expression)l, (Expression)r);
         case 202:
            return new GreaterThanExpression((Expression)l, (Expression)r);
         case 203:
            return new GreaterThanOrEqualExpression((Expression)l, (Expression)r);
         case 204:
            return new LessThanOrEqualExpression((Expression)l, (Expression)r);
         default:
            throw new IllegalArgumentException("unknown op '" + op + "'");
      }
   }

   public ExpressionModel createNegativeExpression(ExpressionModel l) {
      return new NegativeExpression((Expression)l);
   }

   public ExpressionModel createLiteralExpression(String text) {
      return new LiteralStringExpression(text);
   }

   public ExpressionModel createConstantExpression(String number) {
      return new ConstantNumberExpression(number);
   }

   public ExpressionModel createVariableReference(String name) throws XPathUnsupportedException {
      Object val = null;
      if (this.mVariableBindings != null && (val = this.mVariableBindings.get(name)) != null) {
         if (val instanceof Expression) {
            return (Expression)val;
         } else {
            Expression cacheit = null;
            if (val instanceof String) {
               cacheit = new LiteralStringExpression((String)val);
            } else if (val instanceof Number) {
               cacheit = new ConstantNumberExpression(((Number)val).doubleValue());
            } else if (val instanceof Boolean) {
               if ((Boolean)val) {
                  cacheit = TrueFunction.INSTANCE;
               } else {
                  cacheit = FalseFunction.INSTANCE;
               }
            } else {
               if (!(val instanceof List)) {
                  return null;
               }

               cacheit = new ConstantNodesetExpression((List)val, this.mInterrogator);
            }

            this.mVariableBindings.put(name, cacheit);
            return (ExpressionModel)cacheit;
         }
      } else {
         throw new XPathUnsupportedException("No value defined for variable ${" + name + "}");
      }
   }

   private static String function2classname(String fnName) {
      StringWriter sw = new StringWriter();
      sw.write(Character.toUpperCase(fnName.charAt(0)));

      for(int i = 1; i < fnName.length(); ++i) {
         char c = fnName.charAt(i);
         if (c == '-') {
            ++i;
            if (i < fnName.length()) {
               sw.write(Character.toUpperCase(fnName.charAt(i)));
            }
         } else {
            sw.write(c);
         }
      }

      sw.write("Function");
      return sw.toString();
   }
}
