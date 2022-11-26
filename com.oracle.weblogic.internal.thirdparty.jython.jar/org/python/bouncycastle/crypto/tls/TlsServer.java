package org.python.bouncycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public interface TlsServer extends TlsPeer {
   void init(TlsServerContext var1);

   void notifyClientVersion(ProtocolVersion var1) throws IOException;

   void notifyFallback(boolean var1) throws IOException;

   void notifyOfferedCipherSuites(int[] var1) throws IOException;

   void notifyOfferedCompressionMethods(short[] var1) throws IOException;

   void processClientExtensions(Hashtable var1) throws IOException;

   ProtocolVersion getServerVersion() throws IOException;

   int getSelectedCipherSuite() throws IOException;

   short getSelectedCompressionMethod() throws IOException;

   Hashtable getServerExtensions() throws IOException;

   Vector getServerSupplementalData() throws IOException;

   TlsCredentials getCredentials() throws IOException;

   CertificateStatus getCertificateStatus() throws IOException;

   TlsKeyExchange getKeyExchange() throws IOException;

   CertificateRequest getCertificateRequest() throws IOException;

   void processClientSupplementalData(Vector var1) throws IOException;

   void notifyClientCertificate(Certificate var1) throws IOException;

   NewSessionTicket getNewSessionTicket() throws IOException;
}
