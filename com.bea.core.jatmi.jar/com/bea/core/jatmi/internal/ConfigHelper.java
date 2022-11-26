package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import com.bea.core.jatmi.intf.ConfigHelperDelegate;
import com.bea.core.jatmi.intf.TuxedoLoggable;
import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.OatmialServices;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.gwatmi;

public final class ConfigHelper {
   private static ConfigHelperDelegate _delegate = null;
   private static long _capabilities = 0L;
   private static int _impl_type = 0;

   public static void initialize(ConfigHelperDelegate d) throws TPException {
      _delegate = d;
      _capabilities = d.getImplementedCapabilities();
      _impl_type = d.getImplementationId();
      ntrace.doTrace("INFO: TC Configuration Helper instantiated!");
   }

   public static int getImplementationId() {
      return _impl_type;
   }

   public static long getImplementedCapabilities() {
      return _capabilities;
   }

   public static boolean isImplemented(long cap) {
      return (_capabilities & cap) != 0L;
   }

   public static String getGlobalMBEncodingMapFile() {
      return _delegate != null ? _delegate.getGlobalMBEncodingMapFile() : null;
   }

   public static String getGlobalRemoteMBEncoding() {
      return _delegate != null ? _delegate.getGlobalRemoteMBEncoding() : null;
   }

   public static String getHomePath() {
      return _delegate != null ? _delegate.getHomePath() : null;
   }

   public static Objinfo createObjinfo() {
      return _delegate != null ? _delegate.createObjinfo() : null;
   }

   public static Xid[] getRecoveredXids() {
      return _delegate != null ? _delegate.getRecoveredXids() : null;
   }

   public static void forgetRecoveredXid(Xid xid) {
      if (_delegate != null) {
         _delegate.forgetRecoveredXid(xid);
      }

   }

   public static OatmialServices getTuxedoServices() {
      return _delegate != null ? _delegate.getTuxedoServices() : null;
   }

   public static gwatmi getRAPSession(TuxedoConnectorRAP rap, boolean mode) {
      return _delegate != null ? _delegate.getRAPSession(rap, mode) : null;
   }

   public static boolean updateRuntimeViewList(String vname, Class c, int f) {
      return _delegate != null ? _delegate.updateRuntimeViewList(vname, c, f) : false;
   }

   public static void addXidTLogMap(Xid xid, TuxedoLoggable tl) {
      if (_delegate != null) {
         _delegate.addXidTLogMap(xid, tl);
      }

   }

   public static TuxedoLoggable removeXidTLogMap(Xid xid, int type) {
      return _delegate != null ? _delegate.removeXidTLogMap(xid, type) : null;
   }
}
