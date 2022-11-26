package weblogic.diagnostics.descriptor;

public interface WLDFScalingActionBean extends WLDFNotificationBean {
   String getClusterName();

   void setClusterName(String var1);

   int getScalingSize();

   void setScalingSize(int var1);
}
