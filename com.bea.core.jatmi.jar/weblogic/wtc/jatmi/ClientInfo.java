package weblogic.wtc.jatmi;

import java.io.Serializable;

public final class ClientInfo implements Serializable {
   private int cliVersion;
   private ClientId cliId = new ClientId();
   private int cliPid;
   private int cliQaddr;
   private int cliCntxt;
   private int cliSSLPort;
   private int cliSSLSupports;
   private int cliSSLRequires;
   private int unused1;
   private String cliDomain;

   public ClientInfo() {
      this.cliVersion = 1;
   }

   public ClientInfo(ClientInfo aCltInfo) {
      this.cliId = new ClientId(aCltInfo.getId());
      this.cliVersion = aCltInfo.getVersion();
      this.cliPid = aCltInfo.getPid();
      this.cliQaddr = aCltInfo.getQaddr();
      this.cliCntxt = aCltInfo.getCntxt();
      this.cliSSLPort = aCltInfo.getSSLPort();
      this.cliSSLSupports = aCltInfo.getSSLSupports();
      this.cliSSLRequires = aCltInfo.getSSLRequires();
      if (aCltInfo.getDomain() != null) {
         this.cliDomain = new String(aCltInfo.getDomain());
      } else {
         this.cliDomain = null;
      }

   }

   public ClientInfo(Objrecv currObjrecv, int flags, String local_domain_name) throws TPException {
      if (currObjrecv != null) {
         this.cliId.setMchid(currObjrecv.getObjinfo().getRecvSrcCltinfo().getId().getMchid());
         this.cliId.setSlot(currObjrecv.getObjinfo().getRecvSrcCltinfo().getId().getSlot());
         this.cliId.setTimestamp(currObjrecv.getObjinfo().getRecvSrcCltinfo().getId().getTimestamp());
         this.cliPid = currObjrecv.getObjinfo().getRecvSrcCltinfo().getPid();
         this.cliQaddr = currObjrecv.getObjinfo().getRecvSrcCltinfo().getQaddr();
         this.cliCntxt = currObjrecv.getObjinfo().getRecvSrcCltinfo().getCntxt();
         this.cliSSLPort = currObjrecv.getObjinfo().getRecvSrcCltinfo().getSSLPort();
         this.cliSSLSupports = currObjrecv.getObjinfo().getRecvSrcCltinfo().getSSLSupports();
         this.cliSSLRequires = currObjrecv.getObjinfo().getRecvSrcCltinfo().getSSLRequires();
         if (currObjrecv.getObjinfo().getRecvSrcCltinfo().getDomain().equals("")) {
            this.cliDomain = new String(local_domain_name);
         } else {
            this.cliDomain = new String(currObjrecv.getObjinfo().getRecvSrcCltinfo().getDomain());
         }
      } else {
         this.cliId.setMchid(-2);
         this.cliId.setSlot(-1);
         this.cliId.setTimestamp(0);
         this.cliPid = -1;
         this.cliQaddr = -1;
         this.cliCntxt = -1;
         this.cliSSLPort = 0;
         this.cliSSLSupports = 0;
         this.cliSSLRequires = 0;
      }

   }

   public int getVersion() {
      return this.cliVersion;
   }

   public void setVersion(int newVersion) {
      this.cliVersion = newVersion;
   }

   public int getCntxt() {
      return this.cliCntxt;
   }

   public void setCntxt(int newCntxt) {
      this.cliCntxt = newCntxt;
   }

   public String getDomain() {
      return this.cliDomain;
   }

   public void setDomain(String newCliDomain) {
      this.cliDomain = newCliDomain;
   }

   public int getSSLRequires() {
      return this.cliSSLRequires;
   }

   public void setSSLRequires(int newCliSSLRequires) {
      this.cliSSLRequires = newCliSSLRequires;
   }

   public int getSSLSupports() {
      return this.cliSSLSupports;
   }

   public void setSSLSupports(int newCliSSLSupports) {
      this.cliSSLSupports = newCliSSLSupports;
   }

   public int getSSLPort() {
      return this.cliSSLPort;
   }

   public void setSSLPort(int newCliSSLPort) {
      this.cliSSLPort = newCliSSLPort;
   }

   public int getQaddr() {
      return this.cliQaddr;
   }

   public void setQaddr(int newCliQaddr) {
      this.cliQaddr = newCliQaddr;
   }

   public int getPid() {
      return this.cliPid;
   }

   public void setPid(int newCliPid) {
      this.cliPid = newCliPid;
   }

   public ClientId getId() {
      return this.cliId;
   }

   public void setId(ClientId newCliId) {
      this.cliId = newCliId;
   }
}
