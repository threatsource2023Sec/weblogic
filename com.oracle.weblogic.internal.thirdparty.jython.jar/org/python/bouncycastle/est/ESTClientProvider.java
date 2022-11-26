package org.python.bouncycastle.est;

public interface ESTClientProvider {
   ESTClient makeClient() throws ESTException;

   boolean isTrusted();
}
