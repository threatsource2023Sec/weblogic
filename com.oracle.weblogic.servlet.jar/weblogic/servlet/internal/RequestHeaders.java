package weblogic.servlet.internal;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import weblogic.servlet.security.Utils;
import weblogic.utils.StringUtils;
import weblogic.utils.enumerations.EmptyEnumerator;
import weblogic.utils.enumerations.IteratorEnumerator;
import weblogic.utils.http.BytesToString;

public final class RequestHeaders {
   private ArrayList headerNames;
   private ArrayList headerValues;
   private final ArrayList cookies = new ArrayList();
   private long contentLength = -1L;
   private String contentType;
   private String proxyAuthType;
   private String remoteUser = null;
   private String host;
   private int port = -1;
   private String transferEncoding;
   private boolean isChunked = false;
   private ArrayList trailerHeaders;
   private String acceptLanguages;
   private String expect;
   private boolean contentLengthSet = false;
   private String connection;
   private String userAgent;
   private boolean wlProxyFound = false;
   private String authorization;
   private String xWeblogicRequestClusterInfo;
   private String xWeblogicClusterHash;
   private String xWeblogicClusterList;
   private String xWeblogicLoad;
   private String xWeblogicJvmId;
   private String xWeblogicForceJvmId;
   private String xWeblogicKeepaliveSecs;
   private String xWeblogicBuzzAddress;

   public void reset() {
      this.headerNames = null;
      this.headerValues = null;
      this.cookies.clear();
      this.contentLength = -1L;
      this.contentType = null;
      this.proxyAuthType = null;
      this.host = null;
      this.port = -1;
      this.transferEncoding = null;
      this.isChunked = false;
      this.acceptLanguages = null;
      this.expect = null;
      this.contentLengthSet = false;
      this.remoteUser = null;
      this.connection = null;
      this.userAgent = null;
      this.wlProxyFound = false;
      this.authorization = null;
      this.trailerHeaders = null;
      this.xWeblogicRequestClusterInfo = null;
      this.xWeblogicClusterHash = null;
      this.xWeblogicClusterList = null;
      this.xWeblogicLoad = null;
      this.xWeblogicJvmId = null;
      this.xWeblogicForceJvmId = null;
      this.xWeblogicKeepaliveSecs = null;
      this.xWeblogicBuzzAddress = null;
   }

   public String getHeader(String name, String encoding) {
      if (this.headerNames == null) {
         return null;
      } else {
         int i = 0;

         for(int j = this.headerNames.size(); i < j; ++i) {
            if (name.equalsIgnoreCase((String)this.headerNames.get(i))) {
               return this.getHeaderValue(i, encoding);
            }
         }

         return null;
      }
   }

   public byte[] getHeaderAsBytes(String name) {
      if (this.headerNames == null) {
         return null;
      } else {
         int i = 0;

         for(int j = this.headerNames.size(); i < j; ++i) {
            if (name.equalsIgnoreCase((String)this.headerNames.get(i))) {
               return (byte[])((byte[])this.headerValues.get(i));
            }
         }

         return null;
      }
   }

   public Enumeration getHeaderNames() {
      ArrayList headerNames = this.getHeaderNamesAsArrayList();
      return (Enumeration)(headerNames == null ? new EmptyEnumerator() : new IteratorEnumerator(headerNames.iterator()));
   }

   public Enumeration getHeaders(String name, String encoding) {
      if (this.headerNames == null) {
         return new EmptyEnumerator();
      } else {
         ArrayList allValues = new ArrayList(16);
         int i = 0;

         for(int j = this.headerNames.size(); i < j; ++i) {
            if (name.equalsIgnoreCase((String)this.headerNames.get(i))) {
               allValues.add(this.getHeaderValue(i, encoding));
            }
         }

         return new IteratorEnumerator(allValues.iterator());
      }
   }

   String getHeaderValue(int i, String encoding) {
      byte[] b = (byte[])((byte[])this.headerValues.get(i));
      return b == null ? null : BytesToString.newString(b, encoding);
   }

   public ArrayList getHeaderNamesAsArrayList() {
      return this.headerNames;
   }

   public ArrayList getHeaderValuesAsArrayList() {
      return this.headerValues;
   }

   public void setHeaders(ArrayList names, ArrayList values) {
      this.headerNames = names;
      this.headerValues = values;

      for(int i = 0; i < names.size(); ++i) {
         String name = (String)this.headerNames.get(i);
         byte[] value = (byte[])((byte[])this.headerValues.get(i));
         this.processHeader(name, value);
      }

   }

   private void processHeader(String name, byte[] valueBytes) {
      int namelen = name.length();
      String hostHeader;
      switch (namelen) {
         case 4:
            if (ServletRequestImpl.eq(name, "Host", 4)) {
               hostHeader = StringUtils.getString(valueBytes);
               if (hostHeader.endsWith("]")) {
                  this.host = Utils.encodeXSS(hostHeader);
                  this.port = -1;
                  return;
               }

               int pos = hostHeader.lastIndexOf(58);
               if (pos < 0) {
                  this.host = Utils.encodeXSS(hostHeader);
                  this.port = -1;
               } else {
                  if (pos != 0) {
                     this.host = Utils.encodeXSS(hostHeader.substring(0, pos));
                  }

                  if (pos < hostHeader.length() - 1) {
                     try {
                        this.port = Integer.parseInt(hostHeader.substring(pos + 1));
                     } catch (NumberFormatException var10) {
                        this.port = -1;
                     }
                  }
               }

               return;
            }
         case 5:
         case 8:
         case 10:
         case 18:
         case 19:
         case 20:
         case 21:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         default:
            break;
         case 6:
            if (ServletRequestImpl.eq(name, "Cookie", 6)) {
               this.cookies.add(valueBytes);
               return;
            }

            if (ServletRequestImpl.eq(name, "Expect", 6)) {
               this.expect = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 7:
            if (ServletRequestImpl.eq(name, "Trailer", 7)) {
               if (this.trailerHeaders == null) {
                  this.trailerHeaders = new ArrayList();
               }

               String[] trailers = StringUtils.getString(valueBytes).split(",");
               String[] var5 = trailers;
               int var6 = trailers.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String trailerHeader = var5[var7];
                  trailerHeader = trailerHeader.trim();
                  if (!trailerHeader.isEmpty()) {
                     this.trailerHeaders.add(trailerHeader);
                  }
               }

               return;
            }
            break;
         case 9:
            if (ServletRequestImpl.eq(name, "AUTH_TYPE", 9)) {
               this.proxyAuthType = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 11:
            if (ServletRequestImpl.eq(name, "REMOTE_USER", 11)) {
               this.remoteUser = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 12:
            if (ServletRequestImpl.eq(name, "Content-Type", 12)) {
               this.contentType = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 13:
            if (ServletRequestImpl.eq(name, "Authorization", 13)) {
               this.authorization = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 14:
            if (ServletRequestImpl.eq(name, "Content-Length", 14)) {
               if (this.contentLengthSet) {
                  throw new IllegalArgumentException("Duplicate content-length header. This request may be malicious.");
               }

               try {
                  hostHeader = StringUtils.getString(valueBytes);
                  this.contentLength = Long.parseLong(hostHeader.trim());
                  this.contentLengthSet = true;
               } catch (NumberFormatException var9) {
               }

               return;
            }
            break;
         case 15:
            if (ServletRequestImpl.eq(name, "Proxy-Auth-Type", 15)) {
               this.proxyAuthType = StringUtils.getString(valueBytes);
               return;
            }

            if (ServletRequestImpl.eq(name, "Accept-Language", 15)) {
               this.acceptLanguages = StringUtils.getString(valueBytes);
               return;
            }

            if (ServletRequestImpl.eq(name, "X-WebLogic-Load", 15)) {
               this.xWeblogicLoad = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 16:
            if (ServletRequestImpl.eq(name, "X-WebLogic-JVMID", 16)) {
               this.xWeblogicJvmId = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 17:
            if (ServletRequestImpl.eq(name, "Transfer-Encoding", 17)) {
               this.transferEncoding = StringUtils.getString(valueBytes);
               this.isChunked = ServletRequestImpl.eq(this.transferEncoding, "chunked", 7);
               return;
            }

            if (ServletRequestImpl.eq(name, "Proxy-Remote-User", 17)) {
               this.remoteUser = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 22:
            if (ServletRequestImpl.eq(name, "X-WebLogic-Force-JVMID", 22)) {
               this.xWeblogicForceJvmId = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 23:
            if (ServletRequestImpl.eq(name, "X-WebLogic-Cluster-Hash", 23)) {
               this.xWeblogicClusterHash = StringUtils.getString(valueBytes);
               return;
            }

            if (ServletRequestImpl.eq(name, "X-WebLogic-Cluster-List", 23)) {
               this.xWeblogicClusterList = StringUtils.getString(valueBytes);
               return;
            }

            if (ServletRequestImpl.eq(name, "X-WebLogic-Buzz-Address", 23)) {
               this.xWeblogicBuzzAddress = StringUtils.getString(valueBytes);
            }
            break;
         case 24:
            if (ServletRequestImpl.eq(name, "X-WebLogic-KeepAliveSecs", 24)) {
               this.xWeblogicKeepaliveSecs = StringUtils.getString(valueBytes);
               return;
            }
            break;
         case 30:
            if (ServletRequestImpl.eq(name, "X-WebLogic-Request-ClusterInfo", 30)) {
               this.xWeblogicRequestClusterInfo = StringUtils.getString(valueBytes);
               return;
            }
      }

      if (StringUtils.indexOfIgnoreCase(name, "WL-Proxy-") >= 0) {
         this.wlProxyFound = true;
      }

   }

   public long getContentLength() {
      return this.contentLength;
   }

   public void ignoreContentLength() {
      this.contentLength = -1L;
   }

   public String getContentType() {
      return this.contentType;
   }

   public String getProxyAuthType() {
      return this.proxyAuthType;
   }

   String getRemoteUser() {
      return this.remoteUser;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public String getTransferEncoding() {
      return this.transferEncoding;
   }

   public boolean isChunked() {
      return this.isChunked;
   }

   public List getTrailerHeaders() {
      return this.trailerHeaders;
   }

   public boolean hasTrailer() {
      return this.trailerHeaders != null && this.trailerHeaders.size() > 0;
   }

   public String getAcceptLanguages() {
      return this.acceptLanguages;
   }

   public String getExpect() {
      return this.expect;
   }

   public List getCookieHeaders() {
      return this.cookies;
   }

   public String getConnection() {
      return this.connection;
   }

   public String getUserAgent() {
      return this.userAgent;
   }

   public boolean isWlProxyFound() {
      return this.wlProxyFound;
   }

   public String getAuthorization() {
      return this.authorization;
   }

   public String getXWeblogicRequestClusterInfo() {
      return this.xWeblogicRequestClusterInfo;
   }

   public String getXWeblogicClusterHash() {
      return this.xWeblogicClusterHash;
   }

   public String getXWeblogicClusterList() {
      return this.xWeblogicClusterList;
   }

   public String getXWeblogicLoad() {
      return this.xWeblogicLoad;
   }

   public String getXWeblogicForceJvmId() {
      return this.xWeblogicForceJvmId;
   }

   public String getXWeblogicJvmId() {
      return this.xWeblogicJvmId;
   }

   public String getXWeblogicKeepaliveSecs() {
      return this.xWeblogicKeepaliveSecs;
   }

   public String getXWeblogicBuzzAddress() {
      return this.xWeblogicBuzzAddress;
   }
}
