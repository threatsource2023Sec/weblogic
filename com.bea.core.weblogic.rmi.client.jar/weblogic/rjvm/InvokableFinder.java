package weblogic.rjvm;

public interface InvokableFinder {
   RemoteInvokable lookupRemoteInvokable(int var1);

   void put(int var1, RemoteInvokable var2);

   void remove(int var1);
}
