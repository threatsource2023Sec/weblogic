package com.octetstring.vde.util;

public class CreateServerConfig {
   public static void main(String[] args) {
      ServerConfig sc = ServerConfig.getInstance();
      sc.write();
   }
}
