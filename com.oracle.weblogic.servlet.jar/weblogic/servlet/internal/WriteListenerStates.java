package weblogic.servlet.internal;

public class WriteListenerStates {
   static final NIOListenerState WRITE_READY = new WriteReadyState();
   static final NIOListenerState WRITE_WAIT = new WriteWaitState();
   static final NIOListenerState ERROR = new WriteErrorState();
   static final NIOListenerState FINISHED = new Finished();
}
