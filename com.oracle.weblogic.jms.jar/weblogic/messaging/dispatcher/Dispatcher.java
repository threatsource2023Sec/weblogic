package weblogic.messaging.dispatcher;

public interface Dispatcher extends DispatcherCommon {
   void dispatchNoReply(Request var1) throws DispatcherException;

   void dispatchNoReplyWithId(Request var1, int var2) throws DispatcherException;

   Response dispatchSync(Request var1) throws DispatcherException;

   Response dispatchSyncTran(Request var1) throws DispatcherException;

   Response dispatchSyncNoTran(Request var1) throws DispatcherException;

   Response dispatchSyncNoTranWithId(Request var1, int var2) throws DispatcherException;
}
