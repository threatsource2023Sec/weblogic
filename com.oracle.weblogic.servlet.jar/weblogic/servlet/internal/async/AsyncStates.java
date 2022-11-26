package weblogic.servlet.internal.async;

class AsyncStates {
   static final AsyncState ASYNC_STARTED = new AsyncStartedState();
   static final AsyncState ASYNC_WAIT = new AsyncWaitState();
   static final AsyncState COMPLETING = new CompletingState();
   static final AsyncState COMPLETED = new CompletedState();
   static final AsyncState DISPATCHING = new DispatchingState();
   static final AsyncState DISPATCHED = new DispatchedState();
}
