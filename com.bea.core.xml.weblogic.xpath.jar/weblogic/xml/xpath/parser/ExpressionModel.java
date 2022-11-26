package weblogic.xml.xpath.parser;

public interface ExpressionModel {
   int OTHER = 0;
   int NODESET = 1;
   int BOOLEAN = 2;
   int NUMBER = 3;
   int STRING = 4;

   int getType();
}
