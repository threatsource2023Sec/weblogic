package weblogic.messaging.kernel;

import java.util.Collection;

public interface Filter {
   void subscribe(Sink var1, Expression var2) throws KernelException;

   void resubscribe(Sink var1, Expression var2) throws KernelException;

   void unsubscribe(Sink var1);

   Collection match(MessageElement var1);

   boolean match(MessageElement var1, Expression var2);

   Expression createExpression(Object var1) throws KernelException;
}
