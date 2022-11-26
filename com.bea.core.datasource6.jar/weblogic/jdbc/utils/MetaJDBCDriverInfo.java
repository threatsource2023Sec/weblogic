package weblogic.jdbc.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class MetaJDBCDriverInfo implements Serializable {
   private static final long serialVersionUID = 184401685166623934L;
   private String dbmsVendor;
   private String dbmsDriverVendor;
   private String driverClassName;
   private String driverType;
   private String urlHelperClassname;
   private String testSQL;
   private String versionString;
   private String installURL;
   private String description;
   private String datasourceTemplateName = null;
   private String jdbcProviderTemplateName = null;
   private List versionList = new ArrayList();
   private Map driverAttributes = new LinkedHashMap(20);
   private Map unknownDriverAttributeKeys = new LinkedHashMap(20);
   private boolean forXA;
   private boolean cert = false;

   MetaJDBCDriverInfo() {
   }

   public void setDbmsVersion(String versions) {
      this.versionString = versions;
      StringTokenizer tokenizer = new StringTokenizer(versions, ",");

      while(tokenizer.hasMoreTokens()) {
         String token = tokenizer.nextToken();
         this.versionList.add(token);
      }

   }

   public List getDbmsVersionList() {
      return this.versionList;
   }

   public String getDbmsVersion() {
      return this.versionString;
   }

   public void setDbmsVendor(String dbVendor) {
      this.dbmsVendor = dbVendor;
   }

   public String getDbmsVendor() {
      return this.dbmsVendor;
   }

   public void setDriverVendor(String driverVendor) {
      this.dbmsDriverVendor = driverVendor;
   }

   public String getDriverVendor() {
      return this.dbmsDriverVendor;
   }

   public void setDriverClassName(String className) {
      this.driverClassName = className;
   }

   public String getDriverClassName() {
      return this.driverClassName;
   }

   public void setURLHelperClassName(String urlClassName) {
      this.urlHelperClassname = urlClassName;
   }

   public String getURLHelperClassName() {
      return this.urlHelperClassname;
   }

   public void setType(String type) {
      this.driverType = type;
   }

   public String getType() {
      return this.driverType;
   }

   public void setTestSQL(String sql) {
      this.testSQL = sql;
   }

   public String getTestSQL() {
      return this.testSQL;
   }

   public void setInstallURL(String url) {
      this.installURL = url;
   }

   public String getInstallURL() {
      return this.installURL;
   }

   public void setDescription(String driverDescription) {
      this.description = driverDescription;
   }

   public String getDescription() {
      return this.description;
   }

   public void setJdbcProviderTemplateName(String providerTemplateName) {
      this.jdbcProviderTemplateName = providerTemplateName;
   }

   public String getJdbcProviderTemplateName() {
      return this.jdbcProviderTemplateName;
   }

   public void setDatasourceTemplateName(String dsTemplateName) {
      this.datasourceTemplateName = dsTemplateName;
   }

   public String getDatasourceTemplateName() {
      return this.datasourceTemplateName;
   }

   public void setForXA(String xaArg) {
      this.setForXA(Boolean.valueOf(xaArg));
   }

   public void setForXA(boolean xaArg) {
      this.forXA = xaArg;
   }

   public boolean isForXA() {
      return this.forXA;
   }

   public void setCert(String certArg) {
      this.setCert(Boolean.valueOf(certArg));
   }

   public void setCert(boolean certArg) {
      this.cert = certArg;
   }

   public boolean isCert() {
      return this.cert;
   }

   public void setDriverAttribute(String attributeName, JDBCDriverAttribute attribute) {
      boolean unknown = true;

      for(int i = 0; i < JDBCDriverInfo.WELL_KNOWN_KEYS.length; ++i) {
         if (JDBCDriverInfo.WELL_KNOWN_KEYS[i].equals(attributeName)) {
            unknown = false;
         }
      }

      if (unknown) {
         this.unknownDriverAttributeKeys.put(attributeName, attribute);
      }

      this.driverAttributes.put(attributeName, attribute);
   }

   public HashMap getDriverAttributes() {
      HashMap attackOfTheClones = new LinkedHashMap(this.driverAttributes.size());

      String key;
      JDBCDriverAttribute cloner;
      for(Iterator i = this.driverAttributes.keySet().iterator(); i.hasNext(); attackOfTheClones.put(key, cloner)) {
         key = (String)i.next();
         cloner = null;

         try {
            cloner = (JDBCDriverAttribute)((JDBCDriverAttribute)this.driverAttributes.get(key)).clone();
         } catch (CloneNotSupportedException var6) {
         }
      }

      return attackOfTheClones;
   }

   public Set getUnknownDriverAttributesKeys() {
      return this.unknownDriverAttributeKeys.keySet();
   }

   public String toString() {
      StringBuffer buffy = new StringBuffer();
      if (this.isCert()) {
         buffy.append("*");
      }

      buffy.append(this.getDriverVendor());
      buffy.append("'s");
      if (!this.getDriverVendor().equals(this.getDbmsVendor())) {
         buffy.append(" " + this.getDbmsVendor());
      }

      buffy.append(" Driver ");
      if (this.isForXA() || this.getType() != null) {
         buffy.append("(");
         if (this.getType() != null) {
            buffy.append(this.getType());
            if (this.isForXA()) {
               buffy.append(" XA");
            }

            buffy.append(") ");
         } else {
            buffy.append("XA) ");
         }
      }

      if (this.getDescription() != null) {
         buffy.append(this.getDescription() + " ");
      }

      String ver = this.getDbmsVersion();
      if (ver != null && !ver.trim().equals("")) {
         buffy.append("Versions:");
         buffy.append(this.getDbmsVersion());
      }

      return buffy.toString();
   }
}
