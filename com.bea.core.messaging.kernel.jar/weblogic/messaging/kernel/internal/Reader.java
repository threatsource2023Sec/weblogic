package weblogic.messaging.kernel.internal;

import java.util.List;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.MessageElement;
import weblogic.utils.collections.EmbeddedListElement;

public interface Reader extends EmbeddedListElement {
   Expression getExpression();

   Object getOwner();

   String getConsumerID();

   int deliver(List var1);

   int deliver(MessageElement var1);

   int getCount();

   void incrementReserveCount(int var1);

   boolean acknowledge();

   String getSubjectName();
}
