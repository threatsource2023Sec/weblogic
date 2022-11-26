package weblogic.jms.forwarder.dd.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.PerJVMLBHelper;
import weblogic.jms.forwarder.dd.DDInfo;
import weblogic.jms.forwarder.dd.DDLBTable;
import weblogic.jms.forwarder.dd.DDMemberInfo;

public class DDLBTableImpl implements DDLBTable {
   static final long serialVersionUID = 3958010586618335512L;
   private static final byte EXTVERSION1 = 1;
   private static int versionmask = 255;
   private static final int _HASDDLBMembers = 256;
   private static final int _HASFAILEDDESTINATIONS = 512;
   private String name;
   private DDInfo ddInfo;
   private ArrayList ddMemberInfoArrayList;
   private DDMemberInfo[] ddMemberInfos;
   private DDMemberInfo[] ddMemberInfosInDoubt;
   private long ddMemberInfosInDoubtTimestamp;
   private boolean perJVMLBEnabled;
   private PerJVMLBHelper helper;
   private HashMap failedDDMemberInfosbySeqNum;
   private List failedDestinations;

   public DDLBTableImpl() {
      this.ddMemberInfoArrayList = new ArrayList();
      this.perJVMLBEnabled = false;
      this.failedDDMemberInfosbySeqNum = new HashMap();
      this.failedDestinations = new ArrayList();
      this.helper = new PerJVMLBHelper(this);
   }

   public DDLBTableImpl(String name, DDInfo ddInfo) {
      this();
      this.ddInfo = ddInfo;
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public DDInfo getDDInfo() {
      return this.ddInfo;
   }

   public void addFailedDDMemberInfo(MessageImpl message, DDMemberInfo ddMemberInfo) {
      long seqNumber = message.getSAFSeqNumber();
      this.addFailedDDMemberInfo(seqNumber, ddMemberInfo);
   }

   public void addFailedDDMemberInfo(long seqNumber, DDMemberInfo ddMemberInfo) {
      synchronized(this) {
         Long seqN = new Long(seqNumber);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("DDLBTableImpl: addFailedDDMemberInfo():  sequenceNumber= " + seqNumber + " ddMemberInfo =" + ddMemberInfo);
         }

         DDMemberInfo ddInfoOnList = this.getFailedDDMemberInfo(seqN);
         if (ddInfoOnList == null) {
            this.failedDDMemberInfosbySeqNum.put(seqN, ddMemberInfo);
            this.failedDestinations.add(ddMemberInfo);
         }

      }
   }

   public synchronized DDMemberInfo getFailedDDMemberInfo(long safSeqNumber) {
      return (DDMemberInfo)this.failedDDMemberInfosbySeqNum.get(new Long(safSeqNumber));
   }

   public void removeFailedDDMemberInfo(long safSeqNumber) {
      synchronized(this) {
         DDMemberInfo ddInfo = (DDMemberInfo)this.failedDDMemberInfosbySeqNum.remove(new Long(safSeqNumber));
         this.failedDestinations.remove(ddInfo);
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("DDLBTableImpl: removeFailedDDMemberInfo():  sequenceNumber= " + safSeqNumber + " ddMemberInfo =" + ddInfo);
         }

      }
   }

   public void addDDMemberInfo(DDMemberInfo ddMemberInfo) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("DDLBTableImpl: addDDMemberInfo(): ddMemberInfo= " + ddMemberInfo);
      }

      synchronized(this) {
         if (this.ddMemberInfoArrayList.contains(ddMemberInfo)) {
            this.ddMemberInfoArrayList.remove(ddMemberInfo);
         }

         this.ddMemberInfoArrayList.add(ddMemberInfo);
         DDMemberInfo[] ddMemberInfosl = new DDMemberInfo[this.ddMemberInfoArrayList.size()];
         if (this.perJVMLBEnabled) {
            this.ddMemberInfos = this.helper.createPerJVMLBDDMemberInfos((DDMemberInfo[])((DDMemberInfo[])this.ddMemberInfoArrayList.toArray(ddMemberInfosl)));
         } else {
            this.ddMemberInfos = (DDMemberInfo[])((DDMemberInfo[])this.ddMemberInfoArrayList.toArray(ddMemberInfosl));
         }

      }
   }

   public void removeDDMemberInfo(DDMemberInfo ddMemberInfo) {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("DDLBTableImpl: removeDDMemberInfo():   ddMemberInfo= " + ddMemberInfo);
      }

      synchronized(this) {
         this.ddMemberInfoArrayList.remove(ddMemberInfo);
         DDMemberInfo[] ddMemberInfosl = new DDMemberInfo[this.ddMemberInfoArrayList.size()];
         if (this.perJVMLBEnabled) {
            this.ddMemberInfos = this.helper.createPerJVMLBDDMemberInfos((DDMemberInfo[])((DDMemberInfo[])this.ddMemberInfoArrayList.toArray(ddMemberInfosl)));
         } else {
            this.ddMemberInfos = (DDMemberInfo[])((DDMemberInfo[])this.ddMemberInfoArrayList.toArray(ddMemberInfosl));
         }

      }
   }

   public synchronized void addDDMemberInfos(DDMemberInfo[] ddMemberInfos) {
      this.ddMemberInfos = ddMemberInfos;
   }

   public synchronized DDMemberInfo[] getDDMemberInfos() {
      return this.ddMemberInfos;
   }

   public synchronized void removeDDMemberInfos() {
      this.ddMemberInfos = null;
   }

   public void setPerJVMLBEnabled(boolean perJVMLBEnabled) {
      this.perJVMLBEnabled = perJVMLBEnabled;
   }

   public synchronized void addInDoubtDDMemberInfos(DDMemberInfo[] ddMemberInfo) {
      this.ddMemberInfosInDoubtTimestamp = System.currentTimeMillis();
      this.ddMemberInfosInDoubt = ddMemberInfo;
   }

   public synchronized DDMemberInfo[] getInDoubtDDMemberInfos() {
      return this.ddMemberInfosInDoubt;
   }

   public synchronized void removeInDoubtDDMemberInfos() {
      this.ddMemberInfosInDoubt = null;
   }

   public synchronized long getInDoubtDDMemberInfosTimestamp() {
      return this.ddMemberInfosInDoubtTimestamp;
   }

   public synchronized HashMap getFailedDDMemberInfosBySeqNum() {
      return this.failedDDMemberInfosbySeqNum;
   }

   public synchronized void setFailedDDMemberInfosBySeqNum(HashMap failedDDMemberInfos) {
      this.failedDDMemberInfosbySeqNum = failedDDMemberInfos;
   }

   public synchronized void setFailedDDMemberInfos(List failedDestinations) {
      this.failedDestinations = failedDestinations;
   }

   public synchronized List getFailedDDMemberInfos() {
      return this.failedDestinations;
   }

   public String toString() {
      String str = "\n <DDLBTableImpl> = { name=" + this.name + ",\n ddInfo=" + this.ddInfo + ",\n ddMemberInfos=";
      if (this.ddMemberInfos != null && this.ddMemberInfos.length != 0) {
         for(int i = 0; i < this.ddMemberInfos.length; ++i) {
            str = str + this.ddMemberInfos[i] + "\n";
         }
      } else {
         str = str + "null";
      }

      str = str + "\n failedDDMemberInfosbySeqNum=" + this.failedDDMemberInfosbySeqNum;
      str = str + "\n failedDestinations=" + this.failedDestinations;
      str = str + "\n ddMemberInfosInDoubt=" + Arrays.toString(this.ddMemberInfosInDoubt) + "}";
      return str;
   }

   protected int getVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         int majorVer = pi.getMajor();
         if (majorVer < 9) {
            throw new IOException(JMSClientExceptionLogger.logIncompatibleVersion9Loggable((byte)1, (byte)1, (byte)1, (byte)1, pi.toString()).getMessage());
         }
      }

      return 1;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = this.getVersion(out);
      synchronized(this) {
         if (this.ddMemberInfos.length > 0) {
            flags |= 256;
         }

         int numFailedDestinations = this.failedDestinations.size();
         if (numFailedDestinations > 0) {
            flags |= 512;
         }

         out.writeInt(flags);
         out.writeObject(this.failedDDMemberInfosbySeqNum);
         if (numFailedDestinations > 0) {
            out.writeObject(this.failedDestinations);
         }

         if (this.ddMemberInfos.length > 0) {
            out.writeInt(this.ddMemberInfos.length);

            for(int i = 0; i < this.ddMemberInfos.length; ++i) {
               this.ddMemberInfos[i].writeExternal(out);
            }
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int vrsn = flags & versionmask;
      synchronized(this) {
         if (vrsn < 1) {
            throw JMSUtilities.versionIOException(vrsn, 1, 1);
         } else {
            this.failedDDMemberInfosbySeqNum = (HashMap)in.readObject();
            if ((flags & 512) != 0) {
               this.failedDestinations = (List)in.readObject();
            }

            if ((flags & 256) != 0) {
               int length = in.readInt();

               for(int i = 0; i < length; ++i) {
                  DDMemberInfo ddMemberInfo = new DDMemberInfoImpl();
                  ddMemberInfo.readExternal(in);
                  this.ddMemberInfoArrayList.add(ddMemberInfo);
               }

               DDMemberInfo[] ddMemberInfosl = new DDMemberInfo[this.ddMemberInfoArrayList.size()];
               this.ddMemberInfos = (DDMemberInfo[])((DDMemberInfo[])this.ddMemberInfoArrayList.toArray(ddMemberInfosl));
            }

         }
      }
   }
}
