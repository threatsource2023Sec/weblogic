package org.python.bouncycastle.crypto.tls;

import java.io.IOException;

public class DTLSTransport implements DatagramTransport {
   private final DTLSRecordLayer recordLayer;

   DTLSTransport(DTLSRecordLayer var1) {
      this.recordLayer = var1;
   }

   public int getReceiveLimit() throws IOException {
      return this.recordLayer.getReceiveLimit();
   }

   public int getSendLimit() throws IOException {
      return this.recordLayer.getSendLimit();
   }

   public int receive(byte[] var1, int var2, int var3, int var4) throws IOException {
      try {
         return this.recordLayer.receive(var1, var2, var3, var4);
      } catch (TlsFatalAlert var6) {
         this.recordLayer.fail(var6.getAlertDescription());
         throw var6;
      } catch (IOException var7) {
         this.recordLayer.fail((short)80);
         throw var7;
      } catch (RuntimeException var8) {
         this.recordLayer.fail((short)80);
         throw new TlsFatalAlert((short)80, var8);
      }
   }

   public void send(byte[] var1, int var2, int var3) throws IOException {
      try {
         this.recordLayer.send(var1, var2, var3);
      } catch (TlsFatalAlert var5) {
         this.recordLayer.fail(var5.getAlertDescription());
         throw var5;
      } catch (IOException var6) {
         this.recordLayer.fail((short)80);
         throw var6;
      } catch (RuntimeException var7) {
         this.recordLayer.fail((short)80);
         throw new TlsFatalAlert((short)80, var7);
      }
   }

   public void close() throws IOException {
      this.recordLayer.close();
   }
}
