package weblogic.iiop.ior;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class SSLSecTransComponent extends TaggedComponent {
   private int port;
   private short supports;
   private short requires;
   private static SSLSecTransComponent singleton;
   private static final short IOP_SIGNED_FLAGS = 314;
   private static final short IOP_SEALED_FLAGS = 318;
   private static int listenPort;
   private static String[] ciphersuites;
   private static boolean clientCertificateEnforced;

   public static SSLSecTransComponent getSingleton() {
      return singleton == null ? createSingleton() : singleton;
   }

   private static synchronized SSLSecTransComponent createSingleton() {
      if (singleton == null) {
         singleton = new SSLSecTransComponent();
      }

      return singleton;
   }

   private SSLSecTransComponent() {
      super(20);
      this.port = listenPort;
      boolean foundEncrypt = false;
      boolean foundNoEncrypt = false;
      String[] s = ciphersuites;
      if (s != null && s.length > 0) {
         String[] var4 = s;
         int var5 = s.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String value = var4[var6];
            if (value != null) {
               if (value.contains("WITH_NULL")) {
                  foundNoEncrypt = true;
               } else {
                  foundEncrypt = true;
               }
            }
         }
      }

      this.supports = 318;
      this.requires = 314;
      if (!foundEncrypt && s != null) {
         this.supports = 314;
      }

      if (!foundNoEncrypt && foundEncrypt) {
         this.requires = 318;
      }

      if (clientCertificateEnforced) {
         this.supports = (short)(this.supports | 64);
         this.requires = (short)(this.requires | 64);
      }

   }

   public static void initialize(int listenPort, boolean clientCertificateEnforced, String... ciphersuites) {
      SSLSecTransComponent.listenPort = listenPort;
      SSLSecTransComponent.clientCertificateEnforced = clientCertificateEnforced;
      SSLSecTransComponent.ciphersuites = ciphersuites;
   }

   SSLSecTransComponent(CorbaInputStream in) {
      super(20);
      this.read(in);
   }

   public final int getPort() {
      return this.port;
   }

   public final short getSupports() {
      return this.supports;
   }

   public final short getRequires() {
      return this.requires;
   }

   public final void read(CorbaInputStream in) {
      long handle = in.startEncapsulation();
      this.supports = in.read_short();
      this.requires = in.read_short();
      this.port = in.read_unsigned_short();
      in.endEncapsulation(handle);
   }

   public final void write(CorbaOutputStream out) {
      out.write_long(this.tag);
      long handle = out.startEncapsulation();
      out.write_short(this.supports);
      out.write_short(this.requires);
      out.write_unsigned_short(this.port);
      out.endEncapsulation(handle);
   }
}
