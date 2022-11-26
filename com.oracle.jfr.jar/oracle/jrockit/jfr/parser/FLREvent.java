package oracle.jrockit.jfr.parser;

public interface FLREvent extends FLRStruct, FLREventInfo {
   long getTimestamp();

   long getProducerId();

   int getThreadId();

   FLRStruct getThread();

   FLRStruct getStackTrace();

   long getStartTime();
}
