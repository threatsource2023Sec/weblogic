package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.CookieParserUtils;

public class CookiesBuilder {
   public static ClientCookiesBuilder client() {
      return client(false, false);
   }

   public static ClientCookiesBuilder client(boolean strictVersionOneCompliant) {
      return new ClientCookiesBuilder(strictVersionOneCompliant, false);
   }

   public static ClientCookiesBuilder client(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
      return new ClientCookiesBuilder(strictVersionOneCompliant, rfc6265Enabled);
   }

   public static ServerCookiesBuilder server() {
      return server(false, false);
   }

   public static ServerCookiesBuilder server(boolean strictVersionOneCompliant) {
      return new ServerCookiesBuilder(strictVersionOneCompliant, false);
   }

   public static ServerCookiesBuilder server(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
      return new ServerCookiesBuilder(strictVersionOneCompliant, rfc6265Enabled);
   }

   public abstract static class AbstractCookiesBuilder {
      protected final boolean strictVersionOneCompliant;
      protected final boolean rfc6265Enabled;
      protected final Cookies cookies = new Cookies();

      public AbstractCookiesBuilder(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
         this.strictVersionOneCompliant = strictVersionOneCompliant;
         this.rfc6265Enabled = rfc6265Enabled;
      }

      public abstract AbstractCookiesBuilder parse(Buffer var1);

      public abstract AbstractCookiesBuilder parse(Buffer var1, int var2, int var3);

      public abstract AbstractCookiesBuilder parse(String var1);

      public Cookies build() {
         return this.cookies;
      }
   }

   public static class ServerCookiesBuilder extends AbstractCookiesBuilder {
      public ServerCookiesBuilder(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
         super(strictVersionOneCompliant, rfc6265Enabled);
      }

      public ServerCookiesBuilder parse(Buffer cookiesHeader) {
         return this.parse(cookiesHeader, cookiesHeader.position(), cookiesHeader.limit());
      }

      public ServerCookiesBuilder parse(Buffer cookiesHeader, int position, int limit) {
         CookieParserUtils.parseServerCookies(this.cookies, cookiesHeader, position, limit - position, this.strictVersionOneCompliant, this.rfc6265Enabled);
         return this;
      }

      public ServerCookiesBuilder parse(String cookiesHeader) {
         CookieParserUtils.parseServerCookies(this.cookies, cookiesHeader, this.strictVersionOneCompliant, this.rfc6265Enabled);
         return this;
      }
   }

   public static class ClientCookiesBuilder extends AbstractCookiesBuilder {
      public ClientCookiesBuilder(boolean strictVersionOneCompliant, boolean rfc6265Enabled) {
         super(strictVersionOneCompliant, rfc6265Enabled);
      }

      public ClientCookiesBuilder parse(Buffer cookiesHeader) {
         return this.parse(cookiesHeader, cookiesHeader.position(), cookiesHeader.limit());
      }

      public ClientCookiesBuilder parse(Buffer cookiesHeader, int position, int limit) {
         CookieParserUtils.parseClientCookies(this.cookies, cookiesHeader, position, limit - position, this.strictVersionOneCompliant, this.rfc6265Enabled);
         return this;
      }

      public ClientCookiesBuilder parse(String cookiesHeader) {
         CookieParserUtils.parseClientCookies(this.cookies, cookiesHeader, this.strictVersionOneCompliant, this.rfc6265Enabled);
         return this;
      }
   }
}
