package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import java.util.Arrays;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.wtc.jatmi.InvokeInfo;
import weblogic.wtc.jatmi.Objrecv;
import weblogic.wtc.jatmi.dsession;
import weblogic.wtc.jatmi.gwatmi;
import weblogic.wtc.wls.WlsAuthenticatedUser;

public final class MethodParameters {
   private Objrecv myObjrecv;
   private ServiceParameters mySvcParms;
   private Object[] myTxInfo;
   private dsession myDsession;
   private int myGIOPRequestID;
   private boolean _debug = false;

   public MethodParameters(ServiceParameters aSP, Objrecv aObjrecv, Object[] aTxInfo, dsession aDsession) {
      this.mySvcParms = aSP;
      this.myObjrecv = aObjrecv;
      this.myTxInfo = aTxInfo;
      this.myDsession = aDsession;
      this.myGIOPRequestID = 0;
   }

   public MethodParameters(int aGIOPRequestID, dsession aDession) {
      this.mySvcParms = null;
      this.myObjrecv = null;
      this.myTxInfo = null;
      this.myGIOPRequestID = aGIOPRequestID;
      this.myDsession = aDession;
   }

   public InvokeInfo get_invokeInfo() {
      return this.mySvcParms.get_invokeInfo();
   }

   public gwatmi get_gwatmi() {
      return this.myDsession;
   }

   public Objrecv getObjrecv() {
      return this.myObjrecv;
   }

   public ServiceParameters getServiceParameters() {
      return this.mySvcParms;
   }

   public Object[] getTxInfo() {
      return this.myTxInfo;
   }

   public AuthenticatedSubject getAuthenticatedSubject() {
      if (this._debug) {
         ntrace.doTrace("[/MethodParameters/getAuthenticatedSubject()");
      }

      TCAuthenticatedUser tuser = this.mySvcParms.getInvokeInfo().getUser();
      if (tuser instanceof WlsAuthenticatedUser) {
         WlsAuthenticatedUser user = (WlsAuthenticatedUser)tuser;
         if (this._debug) {
            ntrace.doTrace("]/MethodParameters/getAuthenticatedSubject(10)/return " + user.getWlsSubject());
         }

         return user.getWlsSubject();
      } else {
         if (this._debug) {
            ntrace.doTrace("]/MethodParameters/getAuthenticatedSubject(20)/return null");
         }

         return null;
      }
   }

   public int getGIOPRequestID() {
      return this.myGIOPRequestID;
   }

   public void setGIOPRequestID(int anID) {
      this.myGIOPRequestID = anID;
   }

   public String toString() {
      return "\nMethodParameters\n  myObjrecv = " + this.myObjrecv + "\n  mySvcParms = " + this.mySvcParms + "\n  myTxInfo = " + Arrays.toString(this.myTxInfo) + "\n  myDsession = " + this.myDsession + "\n  myGIOPRequestID = " + this.myGIOPRequestID + "\n";
   }
}
