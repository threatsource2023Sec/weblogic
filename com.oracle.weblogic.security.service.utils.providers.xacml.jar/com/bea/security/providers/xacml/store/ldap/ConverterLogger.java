package com.bea.security.providers.xacml.store.ldap;

import com.bea.common.logger.spi.LoggerSpi;

public class ConverterLogger implements LoggerSpi {
   public boolean isDebugEnabled() {
      return false;
   }

   public void debug(Object msg) {
      this.write("DEBUG: " + msg.toString());
   }

   public void debug(Object msg, Throwable th) {
      this.write("DEBUG: " + msg.toString() + ": " + th.getMessage());
   }

   public void info(Object msg) {
      this.write("INFO: " + msg.toString());
   }

   public void info(Object msg, Throwable th) {
      this.write("INFO: " + msg.toString() + ": " + th.getMessage());
   }

   public void warn(Object msg) {
      this.write("WARN: " + msg.toString());
   }

   public void warn(Object msg, Throwable th) {
      this.write("WARN: " + msg.toString() + ": " + th.getMessage());
   }

   public void error(Object msg) {
      this.write("ERROR: " + msg.toString());
   }

   public void error(Object msg, Throwable th) {
      this.write("ERROR: " + msg.toString() + ": " + th.getMessage());
   }

   public void severe(Object msg) {
      this.write("SEVERE: " + msg.toString());
   }

   public void severe(Object msg, Throwable th) {
      this.write("SEVERE: " + msg.toString() + ": " + th.getMessage());
   }

   private void write(String msg) {
      System.out.println(msg);
   }
}
