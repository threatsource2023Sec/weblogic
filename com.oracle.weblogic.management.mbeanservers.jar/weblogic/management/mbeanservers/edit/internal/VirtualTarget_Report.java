package weblogic.management.mbeanservers.edit.internal;

class VirtualTarget_Report {
   String vtname;
   String targetType;
   String targetName;

   VirtualTarget_Report(String str1, String str2, String str3) {
      this.vtname = str1;
      this.targetType = str2;
      this.targetName = str3;
   }
}
