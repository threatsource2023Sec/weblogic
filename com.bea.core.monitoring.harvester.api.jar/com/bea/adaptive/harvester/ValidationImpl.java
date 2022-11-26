package com.bea.adaptive.harvester;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ValidationImpl implements WatchedValues.Validation {
   private WatchedValues.Values metric;
   private Set issues;
   private int status;

   public ValidationImpl(WatchedValues.Values metric, int status, String issue) {
      this.metric = metric;
      this.status = status;
      if (issue != null) {
         this.issues = new HashSet();
         this.issues.add(issue);
      }

   }

   public WatchedValues.Values getMetric() {
      return this.metric;
   }

   public int getStatus() {
      return this.status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public Set getIssues() {
      return this.issues;
   }

   public void addIssue(String issue) {
      if (this.issues == null) {
         this.issues = new HashSet();
      }

      this.issues.add(issue);
   }

   public String dump(String indent) {
      StringBuffer str = new StringBuffer(this.metric.dump(indent, false, false));
      str.append(indent).append("STATUS=").append(this.status == 2 ? "YES" : (this.status == -1 ? "NO" : "MAYBE")).append("\n");
      str.append(indent).append("ISSUES:");
      if (this.issues != null) {
         Iterator var3 = this.issues.iterator();

         while(var3.hasNext()) {
            String issue = (String)var3.next();
            str.append("\n").append(indent).append("  ").append(issue);
         }
      }

      str.append("\n");
      return str.toString();
   }
}
