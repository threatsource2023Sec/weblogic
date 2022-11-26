package com.oracle.buzzmessagebus.impl;

import com.oracle.buzzmessagebus.api.BuzzLoggerAbstractBase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuzzLoggerDefaultImpl extends BuzzLoggerAbstractBase {
   private static final Logger l = Logger.getLogger(BuzzLoggerDefaultImpl.class.getName());
   private boolean ie = true;
   private boolean mde = false;

   public Logger getJavaUtilLogger() {
      return l;
   }

   BuzzLoggerDefaultImpl() {
   }

   public void info(String msg) {
      l.info(msg);
   }

   public void error(String msg) {
      l.severe(msg);
   }

   public void error(String msg, Throwable t) {
      l.log(Level.SEVERE, msg, t);
   }

   public boolean infoEnabled() {
      return this.ie;
   }

   public void infoEnabled(boolean x) {
      this.ie = x;
   }

   public boolean msgDumpEnabled() {
      return this.mde;
   }

   public void msgDumpEnabled(boolean x) {
      this.mde = x;
   }
}
