package weblogic.servlet.internal;

public class ReadListenerStates {
   static final NIOListenerState READ_WAIT = new ReadWaitState();
   static final NIOListenerState READ_READY = new ReadReadyState();
   static final NIOListenerState READ_COMPLETE = new ReadCompleteState();
   static final NIOListenerState FINISHED = new FinishedState();
   static final NIOListenerState ERROR = new ReadErrorState();
}
