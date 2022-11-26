package weblogic.servlet.http2.hpack;

import weblogic.servlet.http2.StreamException;

public interface HeaderListener {
   void onRead(String var1, byte[] var2) throws HpackException;

   boolean hasHeaderException();

   void throwIfHeaderException() throws StreamException;
}
