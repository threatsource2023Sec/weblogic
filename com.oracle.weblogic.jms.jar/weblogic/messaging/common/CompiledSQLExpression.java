package weblogic.messaging.common;

import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.MessageElement;

public interface CompiledSQLExpression extends Expression {
   boolean evaluate(MessageElement var1);
}
