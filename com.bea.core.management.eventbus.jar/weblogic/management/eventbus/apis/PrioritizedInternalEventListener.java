package weblogic.management.eventbus.apis;

public interface PrioritizedInternalEventListener extends InternalEventListener {
   int DEFAULT_PRIORITY = 100;

   int priority();
}
