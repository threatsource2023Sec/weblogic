package org.python.bouncycastle.est.jcajce;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.python.bouncycastle.est.ESTClient;
import org.python.bouncycastle.est.ESTClientSourceProvider;
import org.python.bouncycastle.est.ESTException;
import org.python.bouncycastle.est.ESTRequest;
import org.python.bouncycastle.est.ESTRequestBuilder;
import org.python.bouncycastle.est.ESTResponse;
import org.python.bouncycastle.est.Source;
import org.python.bouncycastle.util.Properties;

class DefaultESTClient implements ESTClient {
   private static final Charset utf8 = Charset.forName("UTF-8");
   private static byte[] CRLF = new byte[]{13, 10};
   private final ESTClientSourceProvider sslSocketProvider;

   public DefaultESTClient(ESTClientSourceProvider var1) {
      this.sslSocketProvider = var1;
   }

   private static void writeLine(OutputStream var0, String var1) throws IOException {
      var0.write(var1.getBytes());
      var0.write(CRLF);
   }

   public ESTResponse doRequest(ESTRequest var1) throws IOException {
      ESTResponse var2 = null;
      ESTRequest var3 = var1;
      int var4 = 15;

      do {
         var2 = this.performRequest(var3);
         var3 = this.redirectURL(var2);
         if (var3 == null) {
            break;
         }

         --var4;
      } while(var4 > 0);

      if (var4 == 0) {
         throw new ESTException("Too many redirects..");
      } else {
         return var2;
      }
   }

   protected ESTRequest redirectURL(ESTResponse var1) throws IOException {
      ESTRequest var2 = null;
      if (var1.getStatusCode() >= 300 && var1.getStatusCode() <= 399) {
         switch (var1.getStatusCode()) {
            case 301:
            case 302:
            case 303:
            case 306:
            case 307:
               String var3 = var1.getHeader("Location");
               if ("".equals(var3)) {
                  throw new ESTException("Redirect status type: " + var1.getStatusCode() + " but no location header");
               }

               ESTRequestBuilder var4 = new ESTRequestBuilder(var1.getOriginalRequest());
               if (var3.startsWith("http")) {
                  var2 = var4.withURL(new URL(var3)).build();
               } else {
                  URL var5 = var1.getOriginalRequest().getURL();
                  var2 = var4.withURL(new URL(var5.getProtocol(), var5.getHost(), var5.getPort(), var3)).build();
               }
               break;
            case 304:
            case 305:
            default:
               throw new ESTException("Client does not handle http status code: " + var1.getStatusCode());
         }
      }

      if (var2 != null) {
         var1.close();
      }

      return var2;
   }

   public ESTResponse performRequest(ESTRequest var1) throws IOException {
      ESTResponse var2 = null;
      Source var3 = null;

      ESTResponse var18;
      try {
         var3 = this.sslSocketProvider.makeSource(var1.getURL().getHost(), var1.getURL().getPort());
         if (var1.getListener() != null) {
            var1 = var1.getListener().onConnection(var3, var1);
         }

         Object var4 = null;
         Set var5 = Properties.asKeySet("org.python.bouncycastle.debug.est");
         if (!var5.contains("output") && !var5.contains("all")) {
            var4 = var3.getOutputStream();
         } else {
            var4 = new PrintingOutputStream(var3.getOutputStream());
         }

         String var6 = var1.getURL().getPath() + (var1.getURL().getQuery() != null ? var1.getURL().getQuery() : "");
         ESTRequestBuilder var7 = new ESTRequestBuilder(var1);
         Map var8 = var1.getHeaders();
         if (!var8.containsKey("Connection")) {
            var7.addHeader("Connection", "close");
         }

         URL var9 = var1.getURL();
         if (var9.getPort() > -1) {
            var7.setHeader("Host", String.format("%s:%d", var9.getHost(), var9.getPort()));
         } else {
            var7.setHeader("Host", var9.getHost());
         }

         ESTRequest var10 = var7.build();
         writeLine((OutputStream)var4, var10.getMethod() + " " + var6 + " HTTP/1.1");
         Iterator var11 = var10.getHeaders().entrySet().iterator();

         while(var11.hasNext()) {
            Map.Entry var12 = (Map.Entry)var11.next();
            String[] var13 = (String[])((String[])var12.getValue());

            for(int var14 = 0; var14 != var13.length; ++var14) {
               writeLine((OutputStream)var4, (String)var12.getKey() + ": " + var13[var14]);
            }
         }

         ((OutputStream)var4).write(CRLF);
         ((OutputStream)var4).flush();
         var10.writeData((OutputStream)var4);
         ((OutputStream)var4).flush();
         if (var10.getHijacker() == null) {
            var2 = new ESTResponse(var10, var3);
            var18 = var2;
            return var18;
         }

         var2 = var10.getHijacker().hijack(var10, var3);
         var18 = var2;
      } finally {
         if (var3 != null && var2 == null) {
            var3.close();
         }

      }

      return var18;
   }

   private class PrintingOutputStream extends OutputStream {
      private final OutputStream tgt;

      public PrintingOutputStream(OutputStream var2) {
         this.tgt = var2;
      }

      public void write(int var1) throws IOException {
         System.out.print(String.valueOf((char)var1));
         this.tgt.write(var1);
      }
   }
}
