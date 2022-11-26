package weblogic.servlet.security.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class HttpMethodCombination {
   private String action;
   private HashSet httpMethods;
   private HashSet httpMethodOmissions;
   private Set uncoveredHttpMethods;
   private Set coveredHttpMethods;
   private boolean isAuthConstraintNull;
   private boolean dirty = true;

   public void addHttpMethodList(String[] httpMethods) {
      if (this.httpMethods == null) {
         this.httpMethods = new HashSet();
      }

      if (httpMethods != null) {
         this.httpMethods.addAll(Arrays.asList(httpMethods));
      } else {
         this.httpMethods.clear();
      }

      this.dirty = true;
   }

   public void addHttpMethodOmissionList(String[] httpMethodOmissions) {
      if (this.httpMethodOmissions == null) {
         this.httpMethodOmissions = new HashSet();
      }

      if (httpMethodOmissions != null && httpMethodOmissions.length != 0) {
         HashSet localOmissions = new HashSet();
         String[] var3 = httpMethodOmissions;
         int var4 = httpMethodOmissions.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String oneMethod = var3[var5];
            if (this.httpMethodOmissions.isEmpty() || this.httpMethodOmissions.contains(oneMethod)) {
               localOmissions.add(oneMethod);
            }
         }

         this.httpMethodOmissions.clear();
         this.httpMethodOmissions.addAll(localOmissions);
      } else {
         this.httpMethodOmissions.clear();
      }

      this.dirty = true;
   }

   public String[] getHttpMethods() {
      if (this.httpMethods == null) {
         return null;
      } else {
         String[] methods = new String[this.httpMethods.size()];
         return (String[])this.httpMethods.toArray(methods);
      }
   }

   public String[] getHttpMethodOmissions() {
      if (this.httpMethodOmissions == null) {
         return null;
      } else {
         String[] methods = new String[this.httpMethodOmissions.size()];
         return (String[])this.httpMethodOmissions.toArray(methods);
      }
   }

   private String formatMethods(Set methods, String bang) {
      if (methods == null) {
         this.action = null;
      } else if (methods.size() == 0) {
         this.action = "";
      } else {
         StringBuffer buf = new StringBuffer(101);
         int i = 0;

         for(Iterator var5 = methods.iterator(); var5.hasNext(); ++i) {
            String one = (String)var5.next();
            if (i > 0) {
               buf.append(',');
            }

            buf.append(one);
         }

         this.action = bang + buf.toString();
      }

      return this.action;
   }

   public String getFlippedAction() {
      String action = this.getAction();
      if (action == null) {
         return "";
      } else if (action.equals("")) {
         return null;
      } else {
         if (action.startsWith("!")) {
            action = action.substring(1);
         } else {
            action = "!" + action;
         }

         return action;
      }
   }

   public String getAction() {
      if (this.dirty) {
         this.dirty = false;
         if (this.httpMethodOmissions == null) {
            return this.formatMethods(this.httpMethods, "");
         } else if (this.httpMethods != null && this.httpMethods.size() != 0) {
            HashSet localOmissions = new HashSet();
            localOmissions.addAll(this.httpMethodOmissions);
            Iterator var2 = this.httpMethods.iterator();

            while(var2.hasNext()) {
               String oneMethod = (String)var2.next();
               localOmissions.remove(oneMethod);
            }

            return this.formatMethods(localOmissions, "!");
         } else {
            return this.formatMethods(this.httpMethodOmissions, "!");
         }
      } else {
         return this.action;
      }
   }

   public String getActionString(String bang, Set methods) {
      return this.formatMethods(methods, bang);
   }

   public Set getUncoveredHttpMethods() {
      return this.uncoveredHttpMethods;
   }

   public void setUncoveredHttpMethods(Set uncoveredHttpMethods) {
      this.uncoveredHttpMethods = uncoveredHttpMethods;
   }

   public Set getCoveredHttpMethods() {
      return this.coveredHttpMethods;
   }

   public void setCoveredHttpMethods(Set coveredHttpMethods) {
      this.coveredHttpMethods = coveredHttpMethods;
   }

   public boolean isAuthConstraintNull() {
      return this.isAuthConstraintNull;
   }

   public void setAuthConstraintNull(boolean authConstraintNull) {
      this.isAuthConstraintNull = authConstraintNull;
   }
}
