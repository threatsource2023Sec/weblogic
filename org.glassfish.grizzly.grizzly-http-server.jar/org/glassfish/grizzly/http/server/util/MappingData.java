package org.glassfish.grizzly.http.server.util;

import org.glassfish.grizzly.http.util.DataChunk;

public class MappingData {
   private static final String CONTEXT_DESC = "context";
   private static final String DEFAULT_DESC = "default";
   private static final String EXACT_DESC = "exact";
   private static final String EXTENSION_DESC = "extension";
   private static final String PATH_DESC = "path";
   private static final String UNKNOWN_DESC = "unknown";
   public static final byte CONTEXT_ROOT = 1;
   public static final byte DEFAULT = 2;
   public static final byte EXACT = 4;
   public static final byte EXTENSION = 8;
   public static final byte PATH = 16;
   public static final byte UNKNOWN = 32;
   public byte mappingType = 32;
   public Object host = null;
   public Object context = null;
   public Object wrapper = null;
   public String servletName = null;
   public String descriptorPath = null;
   public String matchedPath = null;
   public boolean jspWildCard = false;
   public boolean isDefaultContext = false;
   public final DataChunk contextPath = DataChunk.newInstance();
   public final DataChunk requestPath = DataChunk.newInstance();
   public final DataChunk wrapperPath = DataChunk.newInstance();
   public final DataChunk pathInfo = DataChunk.newInstance();
   public final DataChunk redirectPath = DataChunk.newInstance();
   public final DataChunk tmpMapperDC = DataChunk.newInstance();

   public void recycle() {
      this.mappingType = 32;
      this.host = null;
      this.context = null;
      this.wrapper = null;
      this.servletName = null;
      this.pathInfo.recycle();
      this.requestPath.recycle();
      this.wrapperPath.recycle();
      this.contextPath.recycle();
      this.redirectPath.recycle();
      this.jspWildCard = false;
      this.isDefaultContext = false;
      this.descriptorPath = null;
      this.matchedPath = null;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("host: ").append(this.host);
      sb.append("\ncontext: ").append(this.context);
      sb.append("\nwrapper: ").append(this.wrapper);
      sb.append("\nservletName: ").append(this.servletName);
      sb.append("\ncontextPath: ").append(this.contextPath);
      sb.append("\nrequestPath: ").append(this.requestPath);
      sb.append("\nwrapperPath: ").append(this.wrapperPath);
      sb.append("\npathInfo: ").append(this.pathInfo);
      sb.append("\nredirectPath: ").append(this.redirectPath);
      sb.append("\nmappingType: ").append(this.getMappingDescription());
      sb.append("\ndescriptorPath: ").append(this.descriptorPath);
      sb.append("\nmatchedPath: ").append(this.matchedPath);
      return sb.toString();
   }

   private String getMappingDescription() {
      switch (this.mappingType) {
         case 1:
            return "context";
         case 2:
            return "default";
         case 4:
            return "exact";
         case 8:
            return "extension";
         case 16:
            return "path";
         default:
            return "unknown";
      }
   }
}
