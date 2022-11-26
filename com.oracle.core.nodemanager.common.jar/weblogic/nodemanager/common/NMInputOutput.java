package weblogic.nodemanager.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

public class NMInputOutput {
   private final NMReader in;
   private final NMWriter out;
   private NMProtocol protocolVersion;

   public NMInputOutput(InputStream in, OutputStream out) {
      this.protocolVersion = NMProtocol.v2;
      this.in = new NMReader(in);
      this.out = new NMWriter(out);
   }

   public NMProtocol getNMProtocol() {
      return this.protocolVersion;
   }

   public void setNMProtocolVersion(NMProtocol version) {
      this.protocolVersion = version;
   }

   public String readLine() throws IOException {
      return this.in.readLine();
   }

   public void writeLine(String line) throws IOException {
      this.out.writeLine(line);
   }

   public void copy(Writer dest) throws IOException {
      this.in.copy(dest);
      dest.flush();
   }

   public void copy(InputStream is) throws IOException {
      this.out.copy(is);
   }

   public void copy(InputStream is, long bytesToCopy) throws IOException {
      this.out.copy(is, bytesToCopy);
   }

   public OutputStream getOutputStream() {
      return this.out.getOutputStream();
   }

   public void copy(OutputStream dest) throws IOException {
      this.in.copy(dest);
      dest.flush();
   }

   public void writeObject(Object o) throws IOException {
      this.out.writeObject(o);
   }

   public Object readObject() throws IOException, ClassNotFoundException {
      return this.in.readObject();
   }
}
