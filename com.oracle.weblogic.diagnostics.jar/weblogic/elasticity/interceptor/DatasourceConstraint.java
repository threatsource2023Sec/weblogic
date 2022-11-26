package weblogic.elasticity.interceptor;

import java.util.regex.Pattern;

public class DatasourceConstraint {
   private String name;
   private String pattern;
   private Pattern urlPattern;
   private int quota;

   public DatasourceConstraint(String name, String pattern, int quota) {
      this.name = name;
      this.pattern = pattern;
      this.urlPattern = Pattern.compile(pattern);
      this.quota = quota;
   }

   public String getName() {
      return this.name;
   }

   public String getPattern() {
      return this.pattern;
   }

   public int getQuota() {
      return this.quota;
   }

   public boolean matches(String url) {
      return this.urlPattern.matcher(url).matches();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("DatasourceConstraint{").append(this.name).append(", ").append(this.urlPattern.toString()).append(", ").append(this.quota).append("}");
      return buf.toString();
   }
}
