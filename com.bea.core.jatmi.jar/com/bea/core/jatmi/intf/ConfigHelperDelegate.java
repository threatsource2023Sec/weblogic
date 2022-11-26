package com.bea.core.jatmi.intf;

import com.bea.core.jatmi.config.TuxedoConnectorRAP;
import javax.transaction.xa.Xid;
import weblogic.wtc.gwt.OatmialServices;
import weblogic.wtc.jatmi.Objinfo;
import weblogic.wtc.jatmi.gwatmi;

public interface ConfigHelperDelegate {
   int CONFIG_ENV_WLS = 0;
   int CONFIG_ENV_CE = 1;
   long CONFIG_CAP_INBOUND_XA = 1L;
   long CONFIG_CAP_OUTBOUND_XA = 2L;
   long CONFIG_CAP_INBOUND_ACALL = 4L;
   long CONFIG_CAP_OUTBOUND_ACALL = 8L;
   long CONFIG_CAP_EJB = 16L;
   long CONFIG_CAP_POJO = 32L;
   long CONFIG_CAP_INBOUND_CRED = 64L;
   long CONFIG_CAP_OUTBOUND_CRED = 128L;
   long CONFIG_CAP_INBOUND_QUEUE = 256L;
   long CONFIG_CAP_OUTBOUND_QUEUE = 512L;
   long CONFIG_CAP_INBOUND_CONV = 1024L;
   long CONFIG_CAP_OUTBOUND_CONV = 2048L;
   long CONFIG_CAP_INBOUND_CORBA = 4096L;
   long CONFIG_CAP_OUTBOUND_CORBA = 8192L;
   long CONFIG_CAP_ALL = 16383L;

   int getImplementationId();

   long getImplementedCapabilities();

   String getGlobalMBEncodingMapFile();

   String getGlobalRemoteMBEncoding();

   String getHomePath();

   Objinfo createObjinfo();

   Xid[] getRecoveredXids();

   void forgetRecoveredXid(Xid var1);

   OatmialServices getTuxedoServices();

   gwatmi getRAPSession(TuxedoConnectorRAP var1, boolean var2);

   boolean updateRuntimeViewList(String var1, Class var2, int var3);

   void addXidTLogMap(Xid var1, TuxedoLoggable var2);

   TuxedoLoggable removeXidTLogMap(Xid var1, int var2);
}
