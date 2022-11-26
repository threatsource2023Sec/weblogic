package com.oracle.wls.shaded.org.apache.xalan.xsltc.compiler;

abstract class AttributeValue extends Expression {
   public static final AttributeValue create(SyntaxTreeNode parent, String text, Parser parser) {
      Object result;
      if (text.indexOf(123) != -1) {
         result = new AttributeValueTemplate(text, parser, parent);
      } else if (text.indexOf(125) != -1) {
         result = new AttributeValueTemplate(text, parser, parent);
      } else {
         result = new SimpleAttributeValue(text);
         ((AttributeValue)result).setParser(parser);
         ((AttributeValue)result).setParent(parent);
      }

      return (AttributeValue)result;
   }
}
