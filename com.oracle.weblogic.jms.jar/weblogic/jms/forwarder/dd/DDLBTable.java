package weblogic.jms.forwarder.dd;

import java.io.Externalizable;
import java.util.HashMap;
import java.util.List;

public interface DDLBTable extends Externalizable {
   void addFailedDDMemberInfo(long var1, DDMemberInfo var3);

   DDMemberInfo getFailedDDMemberInfo(long var1);

   void removeFailedDDMemberInfo(long var1);

   HashMap getFailedDDMemberInfosBySeqNum();

   void setFailedDDMemberInfosBySeqNum(HashMap var1);

   List getFailedDDMemberInfos();

   void setFailedDDMemberInfos(List var1);

   void addInDoubtDDMemberInfos(DDMemberInfo[] var1);

   DDMemberInfo[] getInDoubtDDMemberInfos();

   void removeInDoubtDDMemberInfos();

   long getInDoubtDDMemberInfosTimestamp();

   void addDDMemberInfos(DDMemberInfo[] var1);

   DDMemberInfo[] getDDMemberInfos();

   void removeDDMemberInfos();

   void addDDMemberInfo(DDMemberInfo var1);

   void removeDDMemberInfo(DDMemberInfo var1);

   void setPerJVMLBEnabled(boolean var1);
}
