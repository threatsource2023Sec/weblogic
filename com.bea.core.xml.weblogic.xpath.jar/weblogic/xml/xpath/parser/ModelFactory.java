package weblogic.xml.xpath.parser;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import weblogic.xml.xpath.XPathException;

public interface ModelFactory {
   int AXIS_SELF = 0;
   int AXIS_ANCESTOR = 1;
   int AXIS_ANCESTOR_OR_SELF = 2;
   int AXIS_ATTRIBUTE = 3;
   int AXIS_CHILD = 4;
   int AXIS_DESCENDANT = 5;
   int AXIS_DESCENDANT_OR_SELF = 6;
   int AXIS_FOLLOWING = 7;
   int AXIS_FOLLOWING_SIBLING = 8;
   int AXIS_NAMESPACE = 9;
   int AXIS_PARENT = 10;
   int AXIS_PRECEDING = 11;
   int AXIS_PRECEDING_SIBLING = 12;
   int AXIS_EVERYTHING = 13;
   int OP_SUBTRACTION = 100;
   int OP_ADDITION = 101;
   int OP_MULTIPLICATION = 102;
   int OP_DIVISION = 103;
   int OP_MODULUS = 104;
   int OP_AND = 105;
   int OP_OR = 106;
   int OP_UNION = 107;
   int OP_INEQUALITY = 199;
   int OP_EQUALITY = 200;
   int OP_LESS = 201;
   int OP_GREATER = 202;
   int OP_GREATER_EQUAL = 203;
   int OP_LESS_EQUAL = 204;
   List AXIS_NAMES = Collections.unmodifiableList(Arrays.asList("self", "ancestor", "ancestor-or-self", "attribute", "child", "descendant", "descendant-or-self", "following", "following-sibling", "namespace", "parent", "preceding", "preceding-sibling", "everything"));

   NodeTestModel getCommentNodeTest() throws XPathException;

   NodeTestModel getTextNodeTest() throws XPathException;

   NodeTestModel getNodeNodeTest() throws XPathException;

   NodeTestModel getEmptyPINodeTest() throws XPathException;

   NodeTestModel getElementNodeTest() throws XPathException;

   NodeTestModel getAttributeNodeTest() throws XPathException;

   NodeTestModel getNamespaceNodeTest() throws XPathException;

   NodeTestModel createNameNodeTest(String var1) throws XPathException;

   NodeTestModel createNameNodeTest(String var1, String var2) throws XPathException;

   NodeTestModel createPINodeTest(String var1) throws XPathException;

   ExpressionModel createVariableReference(String var1) throws XPathException;

   ExpressionModel createComposition(ExpressionModel var1, LocationPathModel var2) throws XPathException;

   StepModel getDoubleSlashStep() throws XPathException;

   StepModel getSelfStep() throws XPathException;

   StepModel getParentStep() throws XPathException;

   StepModel getDocumentRootStep() throws XPathException;

   ExpressionModel createFilterExpression(ExpressionModel var1, Collection var2) throws XPathException;

   ExpressionModel createFunctionExpression(String var1, Collection var2) throws XPathException;

   ExpressionModel createExpression(ExpressionModel var1, int var2, ExpressionModel var3) throws XPathException;

   ExpressionModel createNegativeExpression(ExpressionModel var1) throws XPathException;

   LocationPathModel createLocationPath(Collection var1) throws XPathException;

   StepModel createStep(int var1, NodeTestModel var2, Collection var3) throws XPathException;

   ExpressionModel createLiteralExpression(String var1) throws XPathException;

   ExpressionModel createConstantExpression(String var1) throws XPathException;
}
