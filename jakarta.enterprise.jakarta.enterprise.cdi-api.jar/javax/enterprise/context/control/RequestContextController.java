package javax.enterprise.context.control;

import javax.enterprise.context.ContextNotActiveException;

public interface RequestContextController {
   boolean activate();

   void deactivate() throws ContextNotActiveException;
}
