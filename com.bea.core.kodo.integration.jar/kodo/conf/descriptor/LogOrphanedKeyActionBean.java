package kodo.conf.descriptor;

public interface LogOrphanedKeyActionBean extends OrphanedKeyActionBean {
   String getChannel();

   void setChannel(String var1);

   String getLevel();

   void setLevel(String var1);
}
