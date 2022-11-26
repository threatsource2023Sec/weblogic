package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.internal.ConfigHelper;

public final class Objrecv {
   Objinfo myObjinfo = ConfigHelper.createObjinfo();
   String host = new String("");
   int port = 0;
   int SSLPort = 0;
   int SSLSupports = 0;
   int SSLRequires = 0;

   public Objrecv(tfmh tmmsg) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/Objrecv/Objrecv/0");
      }

      RouteTcb currRouteTcb = null;
      CalloutTcb currCalloutTcb = null;
      TdomValsTcb currTdomValsTcb = null;
      String svDom;
      if (tmmsg.route != null) {
         currRouteTcb = (RouteTcb)tmmsg.route.body;
         if (traceEnabled) {
            ntrace.doTrace("/Objrecv/Objrecv/10");
         }

         if (currRouteTcb.getHostLen() != 0) {
            this.host = new String(currRouteTcb.getHost());
            this.port = currRouteTcb.getPort();
         }

         this.myObjinfo.setAOMHandle(currRouteTcb.getAOMHandle());
         if (currRouteTcb.getVersion() > 1) {
            svDom = this.myObjinfo.getRecvSrcCltinfo().getDomain();
            this.myObjinfo.setRecvSrcCltinfo(new ClientInfo(currRouteTcb.getRecvSrcCltinfo()));
            if (svDom != null) {
               this.myObjinfo.getRecvSrcCltinfo().setDomain(new String(svDom));
            }

            this.myObjinfo.getRecvSrcCltinfo().setVersion(1);
         }

         if ((currRouteTcb.getFlags() & 1) == 0) {
            this.myObjinfo.getRecvSrcCltinfo().setDomain(new String(""));
            this.myObjinfo.getCltinfo().setDomain(new String(""));
            this.myObjinfo.setDomainId((String)null);
            this.myObjinfo.setOrigDomain(new String(""));
         }

         if (currRouteTcb.getNTmids() > 0) {
            this.myObjinfo.setSvcTmid(currRouteTcb.getSvcTmid());
         }

         this.SSLPort = this.myObjinfo.getRecvSrcCltinfo().getSSLPort();
         this.SSLSupports = this.myObjinfo.getRecvSrcCltinfo().getSSLSupports();
         this.SSLRequires = this.myObjinfo.getRecvSrcCltinfo().getSSLRequires();
      }

      if (tmmsg.callout != null) {
         currCalloutTcb = (CalloutTcb)tmmsg.callout.body;
         if (traceEnabled) {
            ntrace.doTrace("/Objrecv/Objrecv/20");
         }

         if (currRouteTcb != null && traceEnabled) {
            ntrace.doTrace("/Objrecv/Objrecv/30:WARNING! Found ROUTE TCM also in Inbound msg..");
         }

         if (currCalloutTcb.getHostLen() != 0) {
            this.host = new String(currCalloutTcb.getHost());
            this.port = currCalloutTcb.getPort();
         }

         this.myObjinfo.setAppKey(currCalloutTcb.getAppkey());
         this.myObjinfo.setConnGen(currCalloutTcb.getConnGen());
         this.myObjinfo.setConnId(currCalloutTcb.getConnId());
         this.myObjinfo.setAOMHandle(-1);
         svDom = this.myObjinfo.getRecvSrcCltinfo().getDomain();
         this.myObjinfo.setRecvSrcCltinfo(new ClientInfo(currCalloutTcb.getSrc()));
         this.myObjinfo.getRecvSrcCltinfo().setDomain(new String(svDom));
         this.myObjinfo.getRecvSrcCltinfo().setVersion(1);
         svDom = this.myObjinfo.getCltinfo().getDomain();
         this.myObjinfo.setCltinfo(new ClientInfo(currCalloutTcb.getDest()));
         this.myObjinfo.getCltinfo().setDomain(new String(svDom));
         this.myObjinfo.getCltinfo().setVersion(1);
         if ((currCalloutTcb.getFlags() & 1) == 0) {
            this.myObjinfo.getRecvSrcCltinfo().setDomain(new String(""));
            this.myObjinfo.getCltinfo().setDomain(new String(""));
            this.myObjinfo.setDomainId((String)null);
            this.myObjinfo.setOrigDomain(new String(""));
         }

         this.SSLPort = this.myObjinfo.getRecvSrcCltinfo().getSSLPort();
         this.SSLSupports = this.myObjinfo.getRecvSrcCltinfo().getSSLSupports();
         this.SSLRequires = this.myObjinfo.getRecvSrcCltinfo().getSSLRequires();
      }

      if (tmmsg.tdom_vals != null) {
         currTdomValsTcb = (TdomValsTcb)tmmsg.tdom_vals.body;
         if (traceEnabled) {
            ntrace.doTrace("/Objrecv/Objrecv/40");
         }

         if (currTdomValsTcb.getDescrim() == 2) {
            this.myObjinfo.getRecvSrcCltinfo().setDomain(new String(currTdomValsTcb.getSrcDomain()));
            this.myObjinfo.getCltinfo().setDomain(new String(currTdomValsTcb.getDestDomain()));
            this.myObjinfo.setDomainId(this.myObjinfo.getCltinfo().getDomain());
            this.myObjinfo.setOrigDomain(new String(currTdomValsTcb.getOrigDomain()));
         }
      }

      if (traceEnabled) {
         ntrace.doTrace("]/Objrecv/Objrecv/50");
      }

   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public int getSSLPort() {
      return this.SSLPort;
   }

   public int getSSLSupports() {
      return this.SSLSupports;
   }

   public int getSSLRequires() {
      return this.SSLRequires;
   }

   public Objinfo getObjinfo() {
      return this.myObjinfo;
   }
}
