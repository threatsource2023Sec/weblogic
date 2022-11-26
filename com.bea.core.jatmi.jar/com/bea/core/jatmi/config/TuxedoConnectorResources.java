package com.bea.core.jatmi.config;

import weblogic.wtc.jatmi.TPException;

public interface TuxedoConnectorResources {
   void setTuxedoConnectorName(String var1);

   String getTuxedoConnectorName();

   void setFldTbl16Classes(String[] var1) throws TPException;

   String[] getFldTbl16Classes();

   void setFldTbl32Classes(String[] var1) throws TPException;

   String[] getFldTbl32Classes();

   void setViewTbl16Classes(String[] var1) throws TPException;

   String[] getViewTbl16Classes();

   void setViewTbl32Classes(String[] var1) throws TPException;

   String[] getViewTbl32Classes();

   void setAppPassword(String var1);

   String getAppPassword();

   void setTpUsrFile(String var1);

   String getTpUsrFile();

   void setRemoteMBEncoding(String var1);

   String getRemoteMBEncoding();

   void setMBEncodingMapFile(String var1);

   String getMBEncodingMapFile();
}
