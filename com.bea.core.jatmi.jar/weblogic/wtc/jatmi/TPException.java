package weblogic.wtc.jatmi;

import java.io.Serializable;

public class TPException extends Exception implements Serializable {
   private int myTperrno;
   private int myUunixerr;
   private int myTpurcode;
   private int myTperrordetail;
   private int myDiagnostic;
   private int myRevent;
   private Reply rplyRtn;
   private static final long serialVersionUID = 8308308529316740522L;
   public static final int TPMINVAL = 0;
   public static final int TPEABORT = 1;
   public static final int TPEBADDESC = 2;
   public static final int TPEBLOCK = 3;
   public static final int TPEINVAL = 4;
   public static final int TPELIMIT = 5;
   public static final int TPENOENT = 6;
   public static final int TPEOS = 7;
   public static final int TPEPERM = 8;
   public static final int TPEPROTO = 9;
   public static final int TPESVCERR = 10;
   public static final int TPESVCFAIL = 11;
   public static final int TPESYSTEM = 12;
   public static final int TPETIME = 13;
   public static final int TPETRAN = 14;
   public static final int TPGOTSIG = 15;
   public static final int TPERMERR = 16;
   public static final int TPEITYPE = 17;
   public static final int TPEOTYPE = 18;
   public static final int TPERELEASE = 19;
   public static final int TPEHAZARD = 20;
   public static final int TPEHEURISTIC = 21;
   public static final int TPEEVENT = 22;
   public static final int TPEMATCH = 23;
   public static final int TPEDIAGNOSTIC = 24;
   public static final int TPEMIB = 25;
   static final int TPMAXVAL = 26;
   static final int TPED_MINVAL = 0;
   public static final int TPED_SVCTIMEOUT = 1;
   public static final int TPED_TERM = 2;
   public static final int TPED_NOUNSOLHANDLER = 3;
   public static final int TPED_NOCLIENT = 4;
   public static final int TPED_DOMAINUNREACHABLE = 5;
   public static final int TPED_CLIENTDISCONNECTED = 6;
   public static final int TPED_PERM = 7;
   public static final int TPED_OTS_INTERNAL = 8;
   public static final int TPED_INVALID_CERTIFICATE = 9;
   public static final int TPED_INVALID_SIGNATURE = 10;
   public static final int TPED_DECRYPTION_FAILURE = 11;
   public static final int TPED_INVALIDCONTEXT = 12;
   public static final int TPED_INVALID_XA_TRANSACTION = 13;
   static final int TPED_MAXVAL = 14;
   public static final int QMNONE = 0;
   public static final int QMEINVAL = -1;
   public static final int QMEBADRMID = -2;
   public static final int QMENOTOPEN = -3;
   public static final int QMETRAN = -4;
   public static final int QMEBADMSGID = -5;
   public static final int QMESYSTEM = -6;
   public static final int QMEOS = -7;
   public static final int QMEABORTED = -8;
   public static final int QMEPROTO = -9;
   public static final int QMEBADQUEUE = -10;
   public static final int QMENOMSG = -11;
   public static final int QMEINUSE = -12;
   public static final int QMENOSPACE = -13;
   public static final int QMERELEASE = -14;
   public static final int QMEINVHANDLE = -15;
   public static final int QMESHARE = -16;
   public static final int TPEV_DISCONIMM = 1;
   public static final int TPEV_SVCERR = 2;
   public static final int TPEV_SVCFAIL = 4;
   public static final int TPEV_SVCSUCC = 8;
   public static final int TPEV_SENDONLY = 32;

   public TPException() {
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
   }

   public TPException(int tperrno) {
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
   }

   public TPException(int tperrno, String explain) {
      super(explain);
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
   }

   public TPException(int tperrno, int Uunixerr, int tpurcode, int tperrordetail) {
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
      this.myUunixerr = Uunixerr;
      this.myTpurcode = tpurcode;
      this.myTperrordetail = tperrordetail;
   }

   public TPException(int tperrno, int Uunixerr, int tpurcode, int tperrordetail, Reply aRplyRtn) {
      this(tperrno, Uunixerr, tpurcode, tperrordetail);
      this.rplyRtn = aRplyRtn;
   }

   public TPException(int tperrno, int Uunixerr, int tpurcode, int tperrordetail, int diagnostic) {
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
      this.myUunixerr = Uunixerr;
      this.myTpurcode = tpurcode;
      this.myTperrordetail = tperrordetail;
      this.myDiagnostic = diagnostic;
   }

   public TPException(int tperrno, int Uunixerr, int tpurcode, int tperrordetail, int diagnostic, int revent) {
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
      this.myUunixerr = Uunixerr;
      this.myTpurcode = tpurcode;
      this.myTperrordetail = tperrordetail;
      this.myDiagnostic = diagnostic;
      this.myRevent = revent;
   }

   public TPException(int tperrno, int Uunixerr, int tpurcode, int tperrordetail, int diagnostic, int revent, Reply aRplyRtn) {
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
      this.myUunixerr = Uunixerr;
      this.myTpurcode = tpurcode;
      this.myTperrordetail = tperrordetail;
      this.myDiagnostic = diagnostic;
      this.myRevent = revent;
      this.rplyRtn = aRplyRtn;
   }

   public TPException(int tperrno, int Uunixerr, int tpurcode, int tperrordetail, String expl) {
      super(expl);
      this.myTperrno = 0;
      this.myUunixerr = 0;
      this.myTpurcode = 0;
      this.myTperrordetail = 0;
      this.myDiagnostic = 0;
      this.myTperrno = tperrno;
      this.myUunixerr = Uunixerr;
      this.myTpurcode = tpurcode;
      this.myTperrordetail = tperrordetail;
   }

   public int gettperrno() {
      return this.myTperrno;
   }

   public int getUunixerr() {
      return this.myUunixerr;
   }

   public int gettpurcode() {
      return this.myTpurcode;
   }

   public int gettperrordetail() {
      return this.myTperrordetail;
   }

   public int getdiagnostic() {
      return this.myDiagnostic;
   }

   public int getrevent() {
      return this.myRevent;
   }

   public Reply getReplyRtn() {
      return this.rplyRtn;
   }

   public static String tpstrerror(int errno) {
      String expl;
      switch (errno) {
         case 1:
            expl = new String("TPEABORT - transaction cannot commit");
            break;
         case 2:
            expl = new String("TPEBADDESC - bad communication descriptor");
            break;
         case 3:
            expl = new String("TPEBLOCK - blocking condition found");
            break;
         case 4:
            expl = new String("TPEINVAL - invalid arguments given");
            break;
         case 5:
            expl = new String("TPELIMIT - a system limit has been reached");
            break;
         case 6:
            expl = new String("TPENOENT - no entry found");
            break;
         case 7:
            expl = new String("TPEOS - operating system error");
            break;
         case 8:
            expl = new String("TPEPERM - bad permissions");
            break;
         case 9:
            expl = new String("TPEPROTO - protocol error");
            break;
         case 10:
            expl = new String("TPESVCERR - server error while handling request");
            break;
         case 11:
            expl = new String("TPESVCFAIL - application level service failure");
            break;
         case 12:
            expl = new String("TPESYSTEM - internal system error");
            break;
         case 13:
            expl = new String("TPETIME - timeout occured");
            break;
         case 14:
            expl = new String("TPETRAN - error starting transaction");
            break;
         case 15:
            expl = new String("TPGOTSIG - signal received and TPSIGRSTRT not specified");
            break;
         case 16:
            expl = new String("TPERMERR - resource manager error");
            break;
         case 17:
            expl = new String("TPEITYPE - type and/or subtype do not match service's");
            break;
         case 18:
            expl = new String("TPEOTYPE - type and/or subtype do not match buffer's or unknown");
            break;
         case 19:
            expl = new String("TPERELEASE - invalid release");
            break;
         case 20:
            expl = new String("TPEHAZARD - hazard exists that transaction heuristically completed");
            break;
         case 21:
            expl = new String("TPEHEURISTIC - transaction heuristically completed");
            break;
         case 22:
            expl = new String("TPEEVENT - event occurred");
            break;
         case 23:
            expl = new String("TPEMATCH - service name cannot be advertised due to matching conflict");
            break;
         case 24:
            expl = new String("TPEDIAGNOSTIC - function failed - check diagnostic value");
            break;
         case 25:
            expl = new String("TPEMIB - Management Information Base access error");
            break;
         default:
            expl = new String("Unknown error number: (" + errno + ")");
      }

      return expl;
   }

   public static String tpstrerrordetail(int errno) {
      String expl;
      switch (errno) {
         case 1:
            expl = new String("TPED_SVCTIMEOUT - Service timeout occured");
            break;
         case 2:
            expl = new String("TPED_TERM - Terminated from the application");
            break;
         case 3:
            expl = new String("TPED_NOUNSOLHANDLER - No unsolicited hander set by client");
            break;
         case 4:
            expl = new String("TPED_NOCLIENT - Specified client does not exist");
            break;
         case 5:
            expl = new String("TPED_DOMAINUNREACHABLE - Remote domain is unreachable");
            break;
         case 6:
            expl = new String("TPED_CLIENTDISCONNECTED - Specified client is logged on, but currently disconnected");
            break;
         case 7:
            expl = new String("TPED_PERM - No permission for the operation");
            break;
         case 8:
            expl = new String("TPED_OTS_INTERNAL - Object Transaction System internal error");
            break;
         case 9:
            expl = new String("TPED_INVALID_CERTIFICATE - Invalid security certificate");
            break;
         case 10:
            expl = new String("TPED_INVALID_SIGNATURE - Invalid security signature");
            break;
         case 11:
            expl = new String("TPED_DECRYPTION_FAILURE - Decryption failure");
            break;
         case 12:
            expl = new String("TPED_INVALIDCONTEXT - Context termintated by another thread");
            break;
         case 13:
            expl = new String("TPED_INVALID_XA_TRANSACTION - NO_XA option set but transaction attempted");
            break;
         default:
            expl = new String("Unknown tperrordetail number: (" + errno + ")");
      }

      return expl;
   }

   public String toString() {
      String expl;
      switch (this.myTperrno) {
         case 0:
            expl = new String("TPMINVAL(0)");
            break;
         case 1:
            expl = new String("TPEABORT(1)");
            break;
         case 2:
            expl = new String("TPEBADDESC(2)");
            break;
         case 3:
            expl = new String("TPEBLOCK(3)");
            break;
         case 4:
            expl = new String("TPEINVAL(4)");
            break;
         case 5:
            expl = new String("TPELIMIT(5)");
            break;
         case 6:
            expl = new String("TPENOENT(6)");
            break;
         case 7:
            expl = new String("TPEOS(7)");
            break;
         case 8:
            expl = new String("TPEPERM(8)");
            break;
         case 9:
            expl = new String("TPEPROTO(9)");
            break;
         case 10:
            expl = new String("TPESVCERR(10)");
            break;
         case 11:
            expl = new String("TPESVCFAIL(11)");
            break;
         case 12:
            expl = new String("TPESYSTEM(12)");
            break;
         case 13:
            expl = new String("TPETIME(13)");
            break;
         case 14:
            expl = new String("TPETRAN(14)");
            break;
         case 15:
            expl = new String("TPGOTSIG(15)");
            break;
         case 16:
            expl = new String("TPERMERR(16)");
            break;
         case 17:
            expl = new String("TPEITYPE(17)");
            break;
         case 18:
            expl = new String("TPEOTYPE(18)");
            break;
         case 19:
            expl = new String("TPERELEASE(19)");
            break;
         case 20:
            expl = new String("TPEHAZARD(20)");
            break;
         case 21:
            expl = new String("TPEHEURISTIC(21)");
            break;
         case 22:
            expl = new String("TPEEVENT(22)");
            break;
         case 23:
            expl = new String("TPEMATCH(23)");
            break;
         case 24:
            expl = new String("TPEDIAGNOSTIC(24)");
            break;
         case 25:
            expl = new String("TPEMIB(25)");
            break;
         case 26:
            expl = new String("TPMAXVAL(26)");
            break;
         default:
            expl = new String("Unknown(" + this.myTperrno + ")");
      }

      String expl2;
      switch (this.myTperrordetail) {
         case 0:
            expl2 = new String("TPED_MINVAL(0)");
            break;
         case 1:
            expl2 = new String("TPED_SVCTIMEOUT(1)");
            break;
         case 2:
            expl2 = new String("TPED_TERM(2)");
            break;
         case 3:
            expl2 = new String("TPED_NOUNSOLHANDLER(3)");
            break;
         case 4:
            expl2 = new String("TPED_NOCLIENT(4)");
            break;
         case 5:
            expl2 = new String("TPED_DOMAINUNREACHABLE(5)");
            break;
         case 6:
            expl2 = new String("TPED_CLIENTDISCONNECTED(6)");
            break;
         case 7:
            expl2 = new String("TPED_PERM(7)");
            break;
         case 8:
            expl2 = new String("TPED_OTS_INTERNAL(8)");
            break;
         case 9:
            expl2 = new String("TPED_INVALID_CERTIFICATE(9)");
            break;
         case 10:
            expl2 = new String("TPED_INVALID_SIGNATURE(10)");
            break;
         case 11:
            expl2 = new String("TPED_DECRYPTION_FAILURE(11)");
            break;
         case 12:
            expl2 = new String("TPED_INVALIDCONTEXT(12)");
            break;
         case 13:
            expl2 = new String("TPED_INVALID_XA_TRANSACTION(13)");
            break;
         default:
            expl2 = new String("Unknown(" + this.myTperrordetail + ")");
      }

      String expl3;
      switch (this.myDiagnostic) {
         case -16:
            expl3 = new String("QMESHARE(-16)");
            break;
         case -15:
            expl3 = new String("QMEINVHANDLE(-15)");
            break;
         case -14:
            expl3 = new String("QMERELEASE(-14)");
            break;
         case -13:
            expl3 = new String("QMENOSPACE(-13)");
            break;
         case -12:
            expl3 = new String("QMEINUSE(-12)");
            break;
         case -11:
            expl3 = new String("QMENOMSG(-11)");
            break;
         case -10:
            expl3 = new String("QMEBADQUEUE(-10)");
            break;
         case -9:
            expl3 = new String("QMEPROTO(-9)");
            break;
         case -8:
            expl3 = new String("QMEABORTED(-8)");
            break;
         case -7:
            expl3 = new String("QMEOS(-7)");
            break;
         case -6:
            expl3 = new String("QMESYSTEM(-6)");
            break;
         case -5:
            expl3 = new String("QMEBADMSGID(-5)");
            break;
         case -4:
            expl3 = new String("QMETRAN(-4)");
            break;
         case -3:
            expl3 = new String("QMENOTOPEN(-3)");
            break;
         case -2:
            expl3 = new String("QMEBADRMID(-2)");
            break;
         case -1:
            expl3 = new String("QMEINVAL(-1)");
            break;
         case 0:
            expl3 = new String("QMNONE(0)");
            break;
         default:
            expl3 = new String("Unknown(" + this.myDiagnostic + ")");
      }

      String super_expl;
      return (super_expl = this.getMessage()) != null ? expl + ":" + this.myUunixerr + ":" + this.myTpurcode + ":" + expl2 + ":" + expl3 + ":" + this.myRevent + ":" + super_expl : expl + ":" + this.myUunixerr + ":" + this.myTpurcode + ":" + expl2 + ":" + expl3 + ":" + this.myRevent;
   }
}
