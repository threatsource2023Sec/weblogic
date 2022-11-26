package com.bea.core.jatmi.config;

public interface TuxedoConnectorSession {
   void setSessionName(String var1);

   String getSessionName();

   void setLocalAccessPoint(String var1);

   String getLocalAccessPoint();

   void setRemoteAccessPoint(String var1);

   String getRemoteAccessPoint();

   void setProfileName(String var1);

   String getProfileName();

   void setLocalAccessPointService(TuxedoConnectorLAP var1);

   void setRemoteAccessPointService(TuxedoConnectorRAP var1);

   void setProfileService(TuxedoConnectorSessionProfile var1);
}
