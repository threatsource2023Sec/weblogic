package weblogic.management.mbeanservers.edit.internal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.TargetMBean;

public final class MigrationReportHandler {
   ArrayList vtlist;
   ArrayList availabletargets;
   String partitionName;
   String rgtName;
   ArrayList defaultTargets;
   ArrayList resourceGroupList;
   String htmlreportDir;
   Map mapSuccessfulImport;
   Map mapJsonSkippedList;
   Map mapUnsupportedList;

   MigrationReportHandler(String reportDir, String partName) {
      this.htmlreportDir = reportDir;
      this.partitionName = partName;
      this.vtlist = new ArrayList();
      this.availabletargets = new ArrayList();
      this.defaultTargets = new ArrayList();
      this.mapSuccessfulImport = new HashMap();
      this.mapJsonSkippedList = new HashMap();
      this.resourceGroupList = new ArrayList();
   }

   void addResourceGroup_ToReport(ResourceGroupMBean rgMbean) {
      this.resourceGroupList.add(rgMbean);
   }

   void addVirtualTarget_ToReport(String vtName, String targetName, String targetType) {
      this.vtlist.add(new VirtualTarget_Report(vtName, targetName, targetType));
   }

   void addAvaliableTarget_ToReport(String str) {
      this.availabletargets.add(str);
   }

   void addefaultTarget_ToReport(String str) {
      this.defaultTargets.add(str);
   }

   void addRgt_ToReport(String str) {
      this.rgtName = str;
   }

   void addImportSucess_ToReport(String resourceType, String resourceName, String mtConfig) {
      Resource_S_Imported_Report successImport = new Resource_S_Imported_Report(resourceName, mtConfig);
      ArrayList list;
      if (this.mapSuccessfulImport.containsKey(resourceType)) {
         list = (ArrayList)this.mapSuccessfulImport.get(resourceType);
         list.add(successImport);
         this.mapSuccessfulImport.put(resourceType, list);
      } else {
         list = new ArrayList();
         list.add(successImport);
         this.mapSuccessfulImport.put(resourceType, list);
      }

   }

   void addImportSkipInJson_ToReport(String resourceType, String resourceName) {
      ArrayList list;
      if (this.mapJsonSkippedList.containsKey(resourceType)) {
         list = (ArrayList)this.mapJsonSkippedList.get(resourceType);
         list.add(resourceName);
         this.mapJsonSkippedList.put(resourceType, list);
      } else {
         list = new ArrayList();
         list.add(resourceName);
         this.mapJsonSkippedList.put(resourceType, list);
      }

   }

   void dumpHtmlFile() throws IOException {
      BufferedWriter out = new BufferedWriter(new FileWriter(this.htmlreportDir + "/" + this.partitionName + "_MigrationImportReport.html"));
      String htmlHeader = "<html xmlns:t='http://xmlns.oracle.com/liftandshift/activity'><body><h2 align='center'>Domain to Partition Import Activity Report</h2><div style='float:left;font-size:14px'><strong>Name of the Imported Partition : <strong></div><div style='float:left;font-size:14px'><strong>" + this.partitionName + "</strong></div><br>";
      out.write(htmlHeader);
      out.write("</br>");
      String rowtemplate = "bgcolor='#F2F5A9' style='outline: thin solid'";
      String rowheadertemplate = "style='outline: thin solid'";
      String resourceTable;
      if (this.rgtName != null) {
         resourceTable = "<div style='float:left;font-size:14px'><strong>Resource Group Template : <strong></div><div style='float:left;font-size:14px'><strong>" + this.rgtName + "</strong></div><br>";
         out.write(resourceTable);
         out.write("</br>");
      }

      int j;
      String resourceType;
      String resourceType;
      String targetinfo;
      String resourceNames;
      if (this.vtlist.size() > 0) {
         resourceTable = "<div style='float:left;font-size:14px'><strong>Virtual Targets<strong></div></br><table cellpadding='5'><tr bgcolor='#F2F5A9' style='outline: thin solid'><th  style='outline: thin solid'>Virtual Target</th><th  style='outline: thin solid'>Target</th><th>ServerType</th></tr>";
         out.write(resourceTable);

         for(j = 0; j < this.vtlist.size(); ++j) {
            resourceType = ((VirtualTarget_Report)this.vtlist.get(j)).vtname;
            resourceType = ((VirtualTarget_Report)this.vtlist.get(j)).targetName;
            targetinfo = ((VirtualTarget_Report)this.vtlist.get(j)).targetType;
            resourceNames = "<tr><td  style='outline: thin solid'>" + resourceType + "</td><td   style='outline: thin solid'>" + resourceType + "</td><td   style='outline: thin solid'>" + targetinfo + "</td></tr>";
            out.write(resourceNames);
         }

         out.write(" </table></br>");
      }

      if (this.availabletargets.size() > 0) {
         resourceTable = "<div style='float:left;font-size:14px'><strong> Available Targets Information <strong></div> </br><table cellpadding='5'><tr  bgcolor='#F2F5A9' style='outline: thin solid'><th  style='outline: thin solid'>Partition Name</th><th  style='outline: thin solid'>Available Targets </th><th  style='outline: thin solid'>Default Targets</th></tr> </br>";
         String availabletargetslist = (String)this.availabletargets.get(0);

         for(int i = 1; i < this.availabletargets.size(); ++i) {
            availabletargetslist = availabletargetslist + ", " + (String)this.availabletargets.get(i);
         }

         resourceType = (String)this.defaultTargets.get(0);

         for(int i = 1; i < this.availabletargets.size(); ++i) {
            resourceType = resourceType + ", " + (String)this.defaultTargets.get(i);
         }

         resourceTable = resourceTable + "<tr><td style='outline: thin solid'>" + this.partitionName + "</td><td style='outline: thin solid'>" + availabletargetslist + "</td><td style='outline: thin solid'>" + resourceType + "</td></tr></br></table>";
         out.write(resourceTable);
      }

      int i;
      if (this.resourceGroupList.size() > 0) {
         resourceTable = "<div style='float:left;font-size:14px'><strong> Resource Group Targeting Information <strong></div> </br><table cellpadding='5'><tr  bgcolor='#F2F5A9' style='outline: thin solid'><th  style='outline: thin solid'>Resource Group</th><th  style='outline: thin solid'>Virtual Targets </th></tr> </br>";
         out.write(resourceTable);

         for(j = 0; j < this.resourceGroupList.size(); ++j) {
            ResourceGroupMBean rgMbean = (ResourceGroupMBean)this.resourceGroupList.get(j);
            TargetMBean[] targets = rgMbean.getTargets();
            targetinfo = "";
            if (targets != null && targets.length != 0) {
               targetinfo = targets[0].getName();

               for(i = 1; i < targets.length; ++i) {
                  targetinfo = targetinfo + targets[i].getName();
               }
            }

            resourceNames = "<tr  style='outline: thin solid'><td   style='outline: thin solid'>" + rgMbean.getName() + "</td><td   style='outline: thin solid'>" + targetinfo + "</td></tr>";
            out.write(resourceNames);
         }

         out.write("</table></br>");
      }

      String resourceTableData;
      if (this.mapSuccessfulImport.size() > 0) {
         Iterator var15 = this.mapSuccessfulImport.entrySet().iterator();

         while(var15.hasNext()) {
            Map.Entry entry = (Map.Entry)var15.next();
            resourceType = (String)entry.getKey();
            ArrayList list = (ArrayList)entry.getValue();
            targetinfo = "<div style='float:left;font-size:14px;'><strong>Successfully Imported " + resourceType + "</strong></div><table cellpadding='5'><tr bgcolor='#F2F5A9' style='outline: thin solid'><th  style='outline: thin solid'>Resource Name in the Previous Release </th><th  style='outline: thin solid'>Equivalent Multitenant Configuration</th></tr></br>";
            out.write(targetinfo);

            for(i = 0; i < list.size(); ++i) {
               resourceTableData = ((Resource_S_Imported_Report)list.get(i)).resourceName;
               String equalivantMTconfi = ((Resource_S_Imported_Report)list.get(i)).mtConfig;
               String resourceTableData = "<tr  style='outline: thin solid'><td   style='outline: thin solid'>" + resourceTableData + "</td><td   style='outline: thin solid'>" + equalivantMTconfi + "</td></tr>";
               out.write(resourceTableData);
            }

            out.write("</table></br>");
         }
      }

      if (this.mapJsonSkippedList.size() > 0) {
         resourceTable = "<div style='float:left;font-size:14px;'><strong>Resources Avoided by the Import Tool </strong></div><table><tr bgcolor='#FF0033' style='outline: thin solid'><th  style='outline: thin solid'>Resource Type </th><th  style='outline: thin solid'>Resource Name </th></tr></br>";
         out.write(resourceTable);
         Iterator var19 = this.mapJsonSkippedList.entrySet().iterator();

         while(var19.hasNext()) {
            Map.Entry entry = (Map.Entry)var19.next();
            resourceType = (String)entry.getKey();
            ArrayList list = (ArrayList)entry.getValue();
            resourceNames = (String)list.get(0);

            for(int j = 1; j < list.size(); ++j) {
               resourceNames = resourceNames + " ," + (String)list.get(j);
            }

            resourceTableData = "<tr  style='outline: thin solid'><td  style='outline: thin solid'>" + resourceType + "</td><td  style='outline: thin solid'>" + resourceNames + "</td></tr></table>";
            out.write(resourceTableData);
         }
      }

      out.write("</body></html>");
      out.close();
   }
}
