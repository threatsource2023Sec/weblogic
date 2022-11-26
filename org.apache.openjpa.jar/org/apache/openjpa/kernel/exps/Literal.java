package org.apache.openjpa.kernel.exps;

public interface Literal extends Value, Constant {
   int TYPE_UNKNOWN = 0;
   int TYPE_NUMBER = 1;
   int TYPE_BOOLEAN = 2;
   int TYPE_STRING = 3;
   int TYPE_SQ_STRING = 4;

   Object getValue();

   void setValue(Object var1);

   int getParseType();
}
