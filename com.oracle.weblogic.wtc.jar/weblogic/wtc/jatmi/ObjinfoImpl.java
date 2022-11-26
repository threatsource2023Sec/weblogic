package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.Tpconvert;
import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import weblogic.wtc.gwt.TDMLocalTDomain;
import weblogic.wtc.gwt.TuxedoConnection;
import weblogic.wtc.gwt.TuxedoConnectionFactory;
import weblogic.wtc.gwt.WTCService;

public class ObjinfoImpl implements Objinfo {
   private int keyType;
   private ObjectId oid;
   private String domainId;
   private int groupId;
   private String intfId;
   private String implId = new String("");
   private int scaIntfBkt = -1;
   private int aomHandle = -1;
   private int[] svcTmid;
   private int connId = -1;
   private int connGen = -1;
   private int appKey = -1;
   private int islGrpno = -1;
   private int islSrvId = -1;
   private ClientInfo sendSrcCltinfo = new ClientInfo();
   private ClientInfo recvSrcCltinfo = new ClientInfo();
   private ClientInfo cltinfo = new ClientInfo();
   private String origDomain;
   private short isMyDomain = 1;
   private int isACallout;

   public ObjinfoImpl() {
   }

   public ObjinfoImpl(BEAObjectKey objKey, ClientInfo currCltinfo, BindInfo currBindinfo, int flags) throws TPException, IOException {
      StandardTypes sData = null;
      tfmh tmmsg = null;
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/ObjinfoImpl/ObjinfoImpl/0");
      }

      if (objKey == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/5");
         }

         throw new TPException(4);
      } else {
         objKey.getInfo(this);
         if (currCltinfo == null) {
            if ((flags & 1) != 0) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/10");
               }

               throw new TPException(9);
            }
         } else if (currCltinfo.getId().getTimestamp() != 0) {
            this.cltinfo = new ClientInfo(currCltinfo);
            this.domainId = this.cltinfo.getDomain();
            this.isACallout = 1;
         }

         if (this.domainId != null && !this.domainId.equals("")) {
            TDMLocalTDomain ldom = WTCService.getWTCService().getLocalDomain(this.domainId);
            if (null == ldom) {
               this.isMyDomain = 0;
               if (traceEnabled) {
                  ntrace.doTrace("/ObjinfoImpl/ObjinfoImpl/20");
               }
            }
         }

         if ((flags & 1) != 0) {
            if (currBindinfo == null) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/30");
               }

               throw new TPException(9);
            }

            this.cltinfo.setCntxt(0);
            TypedFML32 reqBuf = new TypedFML32();

            try {
               reqBuf.Fchg(new FmlKey(167772261, 0), currBindinfo.getHost());
               reqBuf.Fchg(new FmlKey(33554534, 0), new Short(currBindinfo.getPort()));
               reqBuf.Fchg(new FmlKey(33554544, 0), new Short(currBindinfo.getSSLSupports()));
               reqBuf.Fchg(new FmlKey(33554545, 0), new Short(currBindinfo.getSSLRequires()));
            } catch (Ferror var21) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/35");
               }

               throw new TPException(12);
            }

            String srvc;
            if (this.isMyDomain == 1) {
               srvc = new String(".O_BIND");
            } else {
               if (this.domainId == null) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/40");
                  }

                  throw new TPException(12);
               }

               srvc = new String("//" + this.domainId);
               if (reqBuf instanceof StandardTypes) {
                  sData = reqBuf;
                  tmmsg = (tfmh)reqBuf.getTfmhCache();
               }

               if (tmmsg == null) {
                  tcm user = new tcm((short)0, new UserTcb(reqBuf));
                  tmmsg = new tfmh(reqBuf.getHintIndex(), user, 1);
               }

               if (tmmsg.tdom_vals == null) {
                  tmmsg.tdom_vals = new tcm((short)17, new TdomValsTcb());
                  TdomValsTcb currTdomValsTcb = (TdomValsTcb)tmmsg.tdom_vals.body;
                  currTdomValsTcb.setDescrim(1);
                  currTdomValsTcb.setSrvc(new String(".O_BIND"));
               }

               if (sData != null) {
                  sData.setTfmhCache(tmmsg);
               }
            }

            TuxedoConnectionFactory tcf;
            try {
               Context ctx = new InitialContext();
               tcf = (TuxedoConnectionFactory)ctx.lookup("tuxedo.services.TuxedoConnection");
            } catch (NamingException var19) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/45");
               }

               throw new TPException(12, "Could not get TuxedoConnectionFactory : " + var19);
            }

            TuxedoConnection myTux = tcf.getTuxedoConnection();
            if (traceEnabled) {
               ntrace.doTrace("/ObjinfoImpl/ObjinfoImpl/50 srvc = " + srvc);
            }

            Reply myRtn;
            try {
               myRtn = myTux.tpcall(srvc, reqBuf, 8);
            } catch (TPException var20) {
               int tperrno = var20.gettperrno();
               if (tperrno != 10 && tperrno != 11) {
                  if (tperrno == 6) {
                     if (traceEnabled) {
                        ntrace.doTrace("/ObjinfoImpl/ObjinfoImpl/70:ERROR: Asymmetric outbound IIOP not enabled - add the -O option to the ISL in the Tuxedo Domain " + this.domainId);
                     }

                     throw new TPException(6);
                  }

                  if (traceEnabled) {
                     ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/75");
                  }

                  throw new TPException(12);
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/60");
               }

               throw new TPException(5);
            }

            if (traceEnabled) {
               ntrace.doTrace("/ObjinfoImpl/ObjinfoImpl/80");
            }

            myTux.tpterm();
            TypedFML32 rplyBuf = (TypedFML32)myRtn.getReplyBuffer();

            try {
               this.connId = ((Short)rplyBuf.Fget(new FmlKey(104, 0))).intValue();
               this.connGen = ((Long)rplyBuf.Fget(new FmlKey(33554537, 0))).intValue();
               String cltid = new String((String)rplyBuf.Fget(new FmlKey(167772266, 0)));
               if (traceEnabled) {
                  ntrace.doTrace("/ObjinfoImpl/ObjinfoImpl/85 cltId = " + cltid);
               }

               this.cltinfo.setId(Tpconvert.getClientId(cltid));
               this.cltinfo.setDomain(new String((String)rplyBuf.Fget(new FmlKey(167772267, 0))));
               this.domainId = this.cltinfo.getDomain();
               this.cltinfo.setPid(((Long)rplyBuf.Fget(new FmlKey(33554540, 0))).intValue());
               this.cltinfo.setQaddr(((Long)rplyBuf.Fget(new FmlKey(33554541, 0))).intValue());
               this.islGrpno = ((Long)rplyBuf.Fget(new FmlKey(33554542, 0))).intValue();
               this.islSrvId = ((Long)rplyBuf.Fget(new FmlKey(33554543, 0))).intValue();
               new ClientInfo(this.cltinfo);
               this.isACallout = 1;
            } catch (Ferror var22) {
               if (traceEnabled) {
                  ntrace.doTrace("*]/ObjinfoImpl/ObjinfoImpl/90");
               }

               throw new TPException(12);
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("]/ObjinfoImpl/ObjinfoImpl/100");
         }

      }
   }

   public ObjectId getObjectId() {
      return this.oid;
   }

   public void setObjectId(ObjectId newOid) {
      this.oid = newOid;
   }

   public int getKeyType() {
      return this.keyType;
   }

   public void setKeyType(int newKeyType) {
      this.keyType = newKeyType;
   }

   public String getDomainId() {
      return this.domainId;
   }

   public void setOrigDomain(String newOrigDomain) {
      this.origDomain = newOrigDomain;
   }

   public void setDomainId(String newDomainId) {
      this.domainId = newDomainId;
   }

   public String getIntfId() {
      return this.intfId;
   }

   public void setIntfId(String newIntfId) {
      this.intfId = newIntfId;
   }

   public int getGroupId() {
      return this.groupId;
   }

   public void setGroupId(int newGroupId) {
      this.groupId = newGroupId;
   }

   public short getIsMyDomain() {
      return this.isMyDomain;
   }

   public void setIsMyDomain(short newIsMyDomain) {
      this.isMyDomain = newIsMyDomain;
   }

   public ClientInfo getRecvSrcCltinfo() {
      return this.recvSrcCltinfo;
   }

   public ClientInfo getSendSrcCltinfo() {
      return this.sendSrcCltinfo;
   }

   public void setRecvSrcCltinfo(ClientInfo newRecvSrcCltinfo) {
      this.recvSrcCltinfo = newRecvSrcCltinfo;
   }

   public void setSendSrcCltinfo(ClientInfo newSendSrcCltinfo) {
      this.sendSrcCltinfo = newSendSrcCltinfo;
   }

   public void setCltinfo(ClientInfo newCltinfo) {
      this.cltinfo = newCltinfo;
   }

   public ClientInfo getCltinfo() {
      return this.cltinfo;
   }

   public int getIsACallout() {
      return this.isACallout;
   }

   public void setIsACallout(int newIsACallout) {
      this.isACallout = newIsACallout;
   }

   public int getScaIntfBkt() {
      return this.scaIntfBkt;
   }

   public void setScaIntfBkt(int newScaIntfBkt) {
      this.scaIntfBkt = newScaIntfBkt;
   }

   public int getConnGen() {
      return this.connGen;
   }

   public void setConnGen(int newConnGen) {
      this.connGen = newConnGen;
   }

   public int getAppKey() {
      return this.appKey;
   }

   public void setAppKey(int newAppKey) {
      this.appKey = newAppKey;
   }

   public int getConnId() {
      return this.connId;
   }

   public void setConnId(int newConnId) {
      this.connId = newConnId;
   }

   public void setSvcTmid(int[] newSvcTmid) {
      this.svcTmid = newSvcTmid;
   }

   public void setAOMHandle(int newAOMHandle) {
      this.aomHandle = newAOMHandle;
   }
}
