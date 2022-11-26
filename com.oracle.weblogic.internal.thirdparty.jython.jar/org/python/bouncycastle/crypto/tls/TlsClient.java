package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public interface TlsClient extends TlsPeer {
   void init(TlsClientContext var1);

   TlsSession getSessionToResume();

   ProtocolVersion getClientHelloRecordLayerVersion();

   ProtocolVersion getClientVersion();

   boolean isFallback();

   int[] getCipherSuites();

   short[] getCompressionMethods();

   Hashtable getClientExtensions() throws IOException;

   void notifyServerVersion(ProtocolVersion var1) throws IOException;

   void notifySessionID(byte[] var1);

   void notifySelectedCipherSuite(int var1);

   void notifySelectedCompressionMethod(short var1);

   void processServerExtensions(Hashtable var1) throws IOException;

   void processServerSupplementalData(Vector var1) throws IOException;

   TlsKeyExchange getKeyExchange() throws IOException;

   TlsAuthentication getAuthentication() throws IOException;

   Vector getClientSupplementalData() throws IOException;

   void notifyNewSessionTicket(NewSessionTicket var1) throws IOException;
}
