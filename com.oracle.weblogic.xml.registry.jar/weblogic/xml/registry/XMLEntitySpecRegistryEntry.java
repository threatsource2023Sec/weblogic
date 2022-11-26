package weblogic.xml.registry;

public class XMLEntitySpecRegistryEntry extends XMLAbstractRegistryEntry {
   private String entityURI;
   private String whenToCache;
   private int cacheTimeoutInterval;
   private String handleEntityInvalidation;

   public XMLEntitySpecRegistryEntry(String pubId, String sysId, ConfigAbstraction.EntryConfig mbean) {
      super(pubId, sysId, mbean);
   }

   public String getEntityURI() {
      return this.entityURI;
   }

   public void setEntityURI(String val) {
      this.entityURI = val;
   }

   public String getWhenToCache() {
      return this.whenToCache;
   }

   public void setWhenToCache(String val) {
      this.whenToCache = val;
   }

   public int getCacheTimeoutInterval() {
      return this.cacheTimeoutInterval;
   }

   public void setCacheTimeoutInterval(int val) {
      this.cacheTimeoutInterval = val;
   }

   public String getHandleEntityInvalidation() {
      return this.handleEntityInvalidation;
   }

   public void setHandleEntityInvalidation(String handleEntityInvalidation) {
      this.handleEntityInvalidation = handleEntityInvalidation;
   }

   public String toString() {
      StringBuffer sbuf = new StringBuffer();
      sbuf.append("publicId = ");
      sbuf.append(this.getPublicId() == null ? "null, " : "\"" + this.getPublicId() + "\", ");
      sbuf.append("systemId = ");
      sbuf.append(this.getSystemId() == null ? "null, " : "\"" + this.getSystemId() + "\", ");
      sbuf.append("isPrivate = " + this.isPrivate() + ", ");
      sbuf.append("entityPath = " + this.getEntityURI() + ", ");
      sbuf.append("whenToCache = " + this.getWhenToCache() + ", ");
      sbuf.append("cacheTimeoutInterval = " + this.getCacheTimeoutInterval());
      sbuf.append("handleEntityInvalidation = " + this.getHandleEntityInvalidation());
      return sbuf.toString();
   }
}
