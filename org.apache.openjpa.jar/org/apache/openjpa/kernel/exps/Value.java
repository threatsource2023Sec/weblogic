package org.apache.openjpa.kernel.exps;

import java.io.Serializable;
import org.apache.openjpa.meta.ClassMetaData;

public interface Value extends Serializable {
   Class getType();

   void setImplicitType(Class var1);

   boolean isVariable();

   boolean isAggregate();

   boolean isXPath();

   ClassMetaData getMetaData();

   void setMetaData(ClassMetaData var1);

   void acceptVisit(ExpressionVisitor var1);
}
