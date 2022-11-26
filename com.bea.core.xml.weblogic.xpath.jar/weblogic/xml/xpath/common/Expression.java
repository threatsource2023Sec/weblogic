package weblogic.xml.xpath.common;

import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.parser.ExpressionModel;

public interface Expression extends ExpressionModel {
   int OTHER = 0;
   int NODESET = 1;
   int BOOLEAN = 2;
   int NUMBER = 3;
   int STRING = 4;
   Object CONTEXT_NODE_DUMMY = new Object();

   int getType();

   boolean evaluateAsBoolean(Context var1);

   String evaluateAsString(Context var1);

   double evaluateAsNumber(Context var1);

   List evaluateAsNodeset(Context var1);

   void getSubExpressions(Collection var1);

   void getSubsRequiringStringConversion(int var1, Collection var2);
}
