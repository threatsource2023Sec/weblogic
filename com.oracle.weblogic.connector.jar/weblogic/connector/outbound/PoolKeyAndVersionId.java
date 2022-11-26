package weblogic.connector.outbound;

import java.io.Serializable;

public class PoolKeyAndVersionId implements Serializable {
   private static final long serialVersionUID = 3023904326924191962L;
   private String key;
   private String versionId;
   private String keyAndVersionId;
   public static final String SPLIT_STR = "|";

   public PoolKeyAndVersionId(String key, String versionId) {
      this.key = key;
      this.versionId = versionId;
      this.keyAndVersionId = getJndiNameAndVersion(key, versionId);
   }

   public static PoolKeyAndVersionId fromString(String theKey) {
      String[] result = splitJndiNameAndVersion(theKey);
      switch (result.length) {
         case 1:
            return new PoolKeyAndVersionId(result[0], (String)null);
         case 2:
            return new PoolKeyAndVersionId(result[0], result[1]);
         default:
            throw new Error("wrong forat, cannot be split into jndiName and versionId![" + theKey + "]");
      }
   }

   public String getKey() {
      return this.key;
   }

   public String getVersionId() {
      return this.versionId;
   }

   public String toString() {
      return this.keyAndVersionId;
   }

   public int hashCode() {
      return this.keyAndVersionId.hashCode();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof PoolKeyAndVersionId)) {
         return false;
      } else {
         PoolKeyAndVersionId thePoolKeyAndVersionId = (PoolKeyAndVersionId)obj;
         return this.keyAndVersionId.equals(thePoolKeyAndVersionId.keyAndVersionId);
      }
   }

   private static String getJndiNameAndVersion(String jndiName, String versionId) {
      return jndiName + "|" + versionId;
   }

   private static String[] splitJndiNameAndVersion(String jndiNameAndVersionId) {
      String[] result = jndiNameAndVersionId.split("\\|");
      return result;
   }
}
