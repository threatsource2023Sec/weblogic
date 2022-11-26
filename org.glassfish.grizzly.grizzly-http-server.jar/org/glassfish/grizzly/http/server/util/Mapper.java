package org.glassfish.grizzly.http.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.server.naming.DirContext;
import org.glassfish.grizzly.http.server.naming.NamingContext;
import org.glassfish.grizzly.http.server.naming.NamingException;
import org.glassfish.grizzly.http.util.Ascii;
import org.glassfish.grizzly.http.util.CharChunk;
import org.glassfish.grizzly.http.util.Constants;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.MessageBytes;
import org.glassfish.grizzly.utils.Charsets;

public class Mapper {
   private static final Logger logger = Grizzly.logger(Mapper.class);
   private static final String DEFAULT_SERVLET = System.getProperty("org.glassfish.grizzly.servlet.defaultServlet", "default");
   private static final String JSP_SERVLET = System.getProperty("org.glassfish.grizzly.servlet.jspServlet", "jsp");
   private static final CharChunk SLASH = new CharChunk();
   private static boolean allowReplacement = false;
   protected Host[] hosts = new Host[0];
   protected String defaultHostName = null;
   protected final Context context = new Context();
   private final Map defaultContextPathsMap = new HashMap();
   private int port = 0;

   public static void setAllowReplacement(boolean ar) {
      allowReplacement = ar;
   }

   public static boolean allowReplacement() {
      return allowReplacement;
   }

   public void setPort(int port) {
      this.port = port;
   }

   public int getPort() {
      return this.port;
   }

   public String getDefaultHostName() {
      return this.defaultHostName;
   }

   public void setDefaultHostName(String defaultHostName) {
      this.defaultHostName = defaultHostName;
   }

   public synchronized void addHost(String name, String[] aliases, Object host) {
      Host[] newHosts = new Host[this.hosts.length + 1];
      Host newHost = new Host();
      ContextList contextList = new ContextList();
      Context[] defaultContexts = new Context[1];
      String[] defaultContextPaths = new String[1];
      newHost.name = name;
      newHost.contextList = contextList;
      newHost.object = host;
      newHost.defaultContexts = defaultContexts;
      newHost.defaultContextPaths = defaultContextPaths;
      Host oldElem = (Host)insertMapIgnoreCase(this.hosts, newHosts, newHost);
      if (oldElem == null) {
         this.hosts = newHosts;
      } else if (allowReplacement) {
         oldElem.object = host;
         contextList = oldElem.contextList;
      }

      String[] var10 = aliases;
      int var11 = aliases.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         String alias = var10[var12];
         newHosts = new Host[this.hosts.length + 1];
         newHost = new Host();
         newHost.name = alias;
         newHost.contextList = contextList;
         newHost.defaultContexts = defaultContexts;
         newHost.defaultContextPaths = defaultContextPaths;
         newHost.object = host;
         if (insertMapIgnoreCase(this.hosts, newHosts, newHost) == null) {
            this.hosts = newHosts;
         }
      }

      String defaultContextPath = (String)this.defaultContextPathsMap.get(name);
      if (defaultContextPath != null) {
         newHost.defaultContextPaths[0] = defaultContextPath;
      }

   }

   public synchronized void removeHost(String name) {
      int pos = findIgnoreCase(this.hosts, (String)name);
      if (pos >= 0) {
         Object host = this.hosts[pos].object;
         Host[] newHosts = new Host[this.hosts.length - 1];
         if (removeMapIgnoreCase(this.hosts, newHosts, name)) {
            this.hosts = newHosts;
         }

         Host[] var5 = newHosts;
         int var6 = newHosts.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Host newHost = var5[var7];
            if (newHost.object == host) {
               Host[] newHosts2 = new Host[this.hosts.length - 1];
               if (removeMapIgnoreCase(this.hosts, newHosts2, newHost.name)) {
                  this.hosts = newHosts2;
               }
            }
         }

         this.defaultContextPathsMap.remove(name);
      }
   }

   public String[] getHosts() {
      String[] hostN = new String[this.hosts.length];

      for(int i = 0; i < this.hosts.length; ++i) {
         hostN[i] = this.hosts[i].name;
      }

      return hostN;
   }

   public void setContext(String path, String[] welcomeResources, NamingContext resources) {
      this.context.name = path;
      this.context.welcomeResources = welcomeResources;
      this.context.resources = resources;
   }

   public void addContext(String hostName, String path, Object context, String[] welcomeResources, NamingContext resources) {
      this.addContext(hostName, path, context, welcomeResources, resources, (List)null);
   }

   public void addContext(String hostName, String path, Object context, String[] welcomeResources, NamingContext resources, List alternateDocBases) {
      Host[] newHosts = this.hosts;
      int pos = findIgnoreCase(newHosts, (String)hostName);
      if (pos < 0) {
         this.addHost(hostName, new String[0], "");
         newHosts = this.hosts;
         pos = findIgnoreCase(newHosts, (String)hostName);
      }

      if (pos < 0) {
         logger.log(Level.FINE, "No host found: {0} for Mapper listening on port: {1}", new Object[]{hostName, this.port});
      } else {
         Host host = newHosts[pos];
         if (host.name.equalsIgnoreCase(hostName)) {
            int slashCount = slashCount(path);
            synchronized(host) {
               Context[] contexts = host.contextList.contexts;
               if (slashCount > host.contextList.nesting) {
                  host.contextList.nesting = slashCount;
               }

               Context[] newContexts = new Context[contexts.length + 1];
               Context newContext = new Context();
               newContext.name = path;
               newContext.object = context;
               newContext.welcomeResources = welcomeResources;
               newContext.resources = resources;
               newContext.alternateDocBases = alternateDocBases;
               Context oldElem = (Context)insertMap(contexts, newContexts, newContext);
               if (oldElem == null) {
                  host.contextList.contexts = newContexts;
                  if (path.equals(host.defaultContextPaths[0])) {
                     host.defaultContexts[0] = newContext;
                  }
               } else if (allowReplacement) {
                  oldElem.object = context;
                  oldElem.welcomeResources = welcomeResources;
                  oldElem.resources = resources;
               }
            }
         }

      }
   }

   public void removeContext(String hostName, String path) {
      Host[] newHosts = this.hosts;
      int pos = findIgnoreCase(newHosts, (String)hostName);
      if (pos >= 0) {
         Host host = newHosts[pos];
         if (host.name.equalsIgnoreCase(hostName)) {
            synchronized(host) {
               Context[] contexts = host.contextList.contexts;
               if (contexts.length == 0) {
                  return;
               }

               Context[] newContexts = new Context[contexts.length - 1];
               if (removeMap(contexts, newContexts, path)) {
                  host.contextList.contexts = newContexts;
                  host.contextList.nesting = 0;
                  Context[] var9 = newContexts;
                  int var10 = newContexts.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     Context newContext = var9[var11];
                     int slashCount = slashCount(newContext.name);
                     if (slashCount > host.contextList.nesting) {
                        host.contextList.nesting = slashCount;
                     }
                  }
               }
            }
         }

      }
   }

   public String[] getContextNames() {
      List list = new ArrayList();
      Host[] var2 = this.hosts;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Host host = var2[var4];

         for(int j = 0; j < host.contextList.contexts.length; ++j) {
            String cname = host.contextList.contexts[j].name;
            list.add("//" + host.name + (cname.startsWith("/") ? cname : "/"));
         }
      }

      String[] res = new String[list.size()];
      return (String[])list.toArray(res);
   }

   public void addWrapper(String hostName, String contextPath, String path, Object wrapper) {
      this.addWrapper(hostName, contextPath, path, wrapper, false);
   }

   public void addWrapper(String hostName, String contextPath, String path, Object wrapper, boolean jspWildCard) {
      this.addWrapper(hostName, contextPath, path, wrapper, jspWildCard, (String)null, false);
   }

   public void addWrapper(String hostName, String contextPath, String path, Object wrapper, boolean jspWildCard, String servletName, boolean isEmptyPathSpecial) {
      Host[] newHosts = this.hosts;
      int pos = findIgnoreCase(newHosts, (String)hostName);
      if (pos >= 0) {
         Host host = newHosts[pos];
         if (host.name.equalsIgnoreCase(hostName)) {
            Context[] contexts = host.contextList.contexts;
            int pos2 = find(contexts, (String)contextPath);
            if (pos2 < 0) {
               logger.log(Level.SEVERE, "No context found: {0}", contextPath);
               return;
            }

            Context ctx = contexts[pos2];
            if (ctx.name.equals(contextPath)) {
               this.addWrapper(ctx, path, wrapper, jspWildCard, servletName, isEmptyPathSpecial);
            }
         }

      }
   }

   public void addWrapper(String path, Object wrapper) {
      this.addWrapper(this.context, path, wrapper);
   }

   public void addWrapper(String path, Object wrapper, boolean jspWildCard, boolean isEmptyPathSpecial) {
      this.addWrapper(this.context, path, wrapper, jspWildCard, isEmptyPathSpecial);
   }

   public void addWrapper(String path, Object wrapper, boolean jspWildCard, String servletName, boolean isEmptyPathSpecial) {
      this.addWrapper(this.context, path, wrapper, jspWildCard, servletName, isEmptyPathSpecial);
   }

   protected void addWrapper(Context context, String path, Object wrapper) {
      this.addWrapper(context, path, wrapper, false, false);
   }

   protected void addWrapper(Context context, String path, Object wrapper, boolean jspWildCard, boolean isEmptyPathSpecial) {
      this.addWrapper(context, path, wrapper, jspWildCard, (String)null, isEmptyPathSpecial);
   }

   protected void addWrapper(Context context, String path, Object wrapper, boolean jspWildCard, String servletName, boolean isEmptyPathSpecial) {
      synchronized(context) {
         Wrapper newWrapper = new Wrapper();
         newWrapper.object = wrapper;
         newWrapper.jspWildCard = jspWildCard;
         newWrapper.servletName = servletName;
         newWrapper.path = path;
         Wrapper[] oldWrappers;
         Wrapper[] oldWrappers;
         Wrapper oldElem;
         if (path.endsWith("/*")) {
            newWrapper.name = path.substring(0, path.length() - 2);
            oldWrappers = context.wildcardWrappers;
            oldWrappers = new Wrapper[oldWrappers.length + 1];
            oldElem = (Wrapper)insertMap(oldWrappers, oldWrappers, newWrapper);
            if (oldElem == null) {
               context.wildcardWrappers = oldWrappers;
               int slashCount = slashCount(newWrapper.name);
               if (slashCount > context.nesting) {
                  context.nesting = slashCount;
               }
            } else if (allowReplacement) {
               oldElem.object = wrapper;
               oldElem.jspWildCard = jspWildCard;
            }
         } else if (path.startsWith("*.")) {
            newWrapper.name = path.substring(2);
            oldWrappers = context.extensionWrappers;
            oldWrappers = new Wrapper[oldWrappers.length + 1];
            oldElem = (Wrapper)insertMap(oldWrappers, oldWrappers, newWrapper);
            if (oldElem == null) {
               context.extensionWrappers = oldWrappers;
            } else if (allowReplacement) {
               oldElem.object = wrapper;
               oldElem.jspWildCard = jspWildCard;
            }
         } else {
            boolean isSlashPath = "/".equals(path);
            if (isSlashPath) {
               newWrapper.name = "";
               context.defaultWrapper = newWrapper;
            }

            if (!isSlashPath || !DEFAULT_SERVLET.equals(servletName)) {
               newWrapper.name = path;
               if (isEmptyPathSpecial && path.length() == 0) {
                  context.emptyPathWrapper = newWrapper;
               } else {
                  oldWrappers = context.exactWrappers;
                  Wrapper[] newWrappers = new Wrapper[oldWrappers.length + 1];
                  Wrapper oldElem = (Wrapper)insertMap(oldWrappers, newWrappers, newWrapper);
                  if (oldElem == null) {
                     context.exactWrappers = newWrappers;
                  } else if (allowReplacement) {
                     oldElem.object = wrapper;
                     oldElem.jspWildCard = jspWildCard;
                  }
               }
            }
         }

      }
   }

   public void removeWrapper(String path) {
      this.removeWrapper(this.context, path);
   }

   public void removeWrapper(String hostName, String contextPath, String path) {
      Host[] newHosts = this.hosts;
      int pos = findIgnoreCase(newHosts, (String)hostName);
      if (pos >= 0) {
         Host host = newHosts[pos];
         if (host.name.equalsIgnoreCase(hostName)) {
            Context[] contexts = host.contextList.contexts;
            int pos2 = find(contexts, (String)contextPath);
            if (pos2 < 0) {
               return;
            }

            Context ctx = contexts[pos2];
            if (ctx.name.equals(contextPath)) {
               this.removeWrapper(ctx, path);
            }
         }

      }
   }

   protected void removeWrapper(Context context, String path) {
      synchronized(context) {
         String name;
         Wrapper[] newWrappers;
         Wrapper[] newWrappers;
         if (path.endsWith("/*")) {
            name = path.substring(0, path.length() - 2);
            newWrappers = context.wildcardWrappers;
            newWrappers = new Wrapper[newWrappers.length - 1];
            if (removeMap(newWrappers, newWrappers, name)) {
               context.nesting = 0;
               Wrapper[] var7 = newWrappers;
               int var8 = newWrappers.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Wrapper newWrapper = var7[var9];
                  int slashCount = slashCount(newWrapper.name);
                  if (slashCount > context.nesting) {
                     context.nesting = slashCount;
                  }
               }

               context.wildcardWrappers = newWrappers;
            }
         } else if (path.startsWith("*.")) {
            name = path.substring(2);
            newWrappers = context.extensionWrappers;
            newWrappers = new Wrapper[newWrappers.length - 1];
            if (removeMap(newWrappers, newWrappers, name)) {
               context.extensionWrappers = newWrappers;
            }
         } else if ("/".equals(path)) {
            context.defaultWrapper = null;
         } else {
            Wrapper[] oldWrappers = context.exactWrappers;
            newWrappers = new Wrapper[oldWrappers.length - 1];
            if (removeMap(oldWrappers, newWrappers, path)) {
               context.exactWrappers = newWrappers;
            }
         }

      }
   }

   public String getWrappersString(String host, String context) {
      String[] names = this.getWrapperNames(host, context);
      StringBuilder sb = new StringBuilder();
      String[] var5 = names;
      int var6 = names.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String name = var5[var7];
         sb.append(name).append(":");
      }

      return sb.toString();
   }

   public String[] getWrapperNames(String host, String context) {
      List list = new ArrayList();
      if (host == null) {
         host = "";
      }

      if (context == null) {
         context = "";
      }

      Host[] var4 = this.hosts;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Host host1 = var4[var6];
         if (host.equals(host1.name)) {
            for(int j = 0; j < host1.contextList.contexts.length; ++j) {
               if (context.equals(host1.contextList.contexts[j].name)) {
                  Context ctx = host1.contextList.contexts[j];
                  list.add(ctx.defaultWrapper.path);

                  int k;
                  for(k = 0; k < ctx.exactWrappers.length; ++k) {
                     list.add(ctx.exactWrappers[k].path);
                  }

                  for(k = 0; k < ctx.wildcardWrappers.length; ++k) {
                     list.add(ctx.wildcardWrappers[k].path + "*");
                  }

                  for(k = 0; k < ctx.extensionWrappers.length; ++k) {
                     list.add("*." + ctx.extensionWrappers[k].path);
                  }
               }
            }
         }
      }

      String[] res = new String[list.size()];
      return (String[])list.toArray(res);
   }

   public void setDefaultContextPath(String hostName, String defaultContextPath) throws Exception {
      if (defaultContextPath != null) {
         this.defaultContextPathsMap.put(hostName, defaultContextPath);
      }

      int pos = findIgnoreCase(this.hosts, (String)hostName);
      if (pos >= 0) {
         this.hosts[pos].defaultContextPaths[0] = defaultContextPath;
         if (defaultContextPath != null) {
            this.addDefaultContext(this.hosts[pos], defaultContextPath);
         } else {
            this.hosts[pos].defaultContexts[0] = null;
            this.defaultContextPathsMap.remove(hostName);
         }

      }
   }

   private void addDefaultContext(Host host, String defaultContextPath) throws Exception {
      boolean defaultContextFound = false;
      Context[] contexts = host.contextList.contexts;
      if (contexts != null) {
         Context[] var5 = contexts;
         int var6 = contexts.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Context context1 = var5[var7];
            if (context1.name.equals(defaultContextPath)) {
               host.defaultContexts[0] = context1;
               defaultContextFound = true;
               break;
            }
         }
      }

      if (!defaultContextFound) {
         throw new Exception("No context matching " + defaultContextPath + " deployed on virtual server " + host.name);
      }
   }

   public void mapUriWithSemicolon(HttpRequestPacket requestPacket, DataChunk decodedURI, MappingData mappingData, int semicolonPos) throws Exception {
      CharChunk charChunk = decodedURI.getCharChunk();
      int oldEnd = charChunk.getEnd();
      if (semicolonPos == 0) {
         semicolonPos = decodedURI.indexOf(';', 0);
      }

      DataChunk localDecodedURI = decodedURI;
      if (semicolonPos >= 0) {
         charChunk.setEnd(semicolonPos);
         localDecodedURI = mappingData.tmpMapperDC;
         localDecodedURI.duplicate(decodedURI);
      }

      this.map(requestPacket, localDecodedURI, mappingData);
      charChunk.setEnd(oldEnd);
   }

   public void mapUriWithSemicolon(DataChunk serverName, DataChunk decodedURI, MappingData mappingData, int semicolonPos) throws Exception {
      CharChunk charChunk = decodedURI.getCharChunk();
      int oldEnd = charChunk.getEnd();
      if (semicolonPos == 0) {
         semicolonPos = decodedURI.indexOf(';', 0);
      }

      DataChunk localDecodedURI = decodedURI;
      if (semicolonPos >= 0) {
         charChunk.setEnd(semicolonPos);
         localDecodedURI = mappingData.tmpMapperDC;
         localDecodedURI.duplicate(decodedURI);
      }

      this.map(serverName, localDecodedURI, mappingData);
      charChunk.setEnd(oldEnd);
   }

   public void map(DataChunk host, DataChunk uri, MappingData mappingData) throws Exception {
      if (host.isNull()) {
         host.getCharChunk().append(this.defaultHostName);
      } else if (host.getLength() == 0) {
         throw new Exception("Host is not set");
      }

      host.toChars(Constants.DEFAULT_HTTP_CHARSET);
      uri.toChars(Charsets.UTF8_CHARSET);
      this.internalMap(host.getCharChunk(), uri.getCharChunk(), mappingData);
   }

   public void map(HttpRequestPacket requestPacket, DataChunk uri, MappingData mappingData) throws Exception {
      CharChunk hostCC;
      if (this.hosts.length > 1) {
         DataChunk host = requestPacket.serverName();
         if (host.isNull()) {
            host.getCharChunk().append(this.defaultHostName);
         } else {
            if (host.getLength() == 0) {
               throw new Exception("Host is not set");
            }

            host.toChars(Constants.DEFAULT_HTTP_CHARSET);
         }

         hostCC = host.getCharChunk();
      } else {
         hostCC = null;
      }

      uri.toChars(Charsets.UTF8_CHARSET);
      this.internalMap(hostCC, uri.getCharChunk(), mappingData);
   }

   public void map(MessageBytes uri, MappingData mappingData) throws Exception {
      uri.toChars();
      CharChunk uricc = uri.getCharChunk();
      uricc.setLimit(-1);
      this.internalMapWrapper(this.context, uricc, mappingData);
   }

   private void internalMap(CharChunk host, CharChunk uri, MappingData mappingData) throws Exception {
      uri.setLimit(-1);
      Context[] contexts = null;
      Context ctx = null;
      int nesting = 0;
      int hostPos = -1;
      int pos;
      if (mappingData.host == null) {
         Host[] newHosts = this.hosts;
         pos = host != null && !host.isNull() ? findIgnoreCase(newHosts, (CharChunk)host) : -1;
         if (pos != -1 && host.equalsIgnoreCase(newHosts[pos].name)) {
            mappingData.host = newHosts[pos].object;
            hostPos = pos;
            contexts = newHosts[pos].contextList.contexts;
            nesting = newHosts[pos].contextList.nesting;
         } else {
            if (this.defaultHostName == null) {
               return;
            }

            pos = findIgnoreCase(newHosts, (String)this.defaultHostName);
            if (pos == -1 || !this.defaultHostName.equalsIgnoreCase(newHosts[pos].name)) {
               return;
            }

            mappingData.host = newHosts[pos].object;
            hostPos = pos;
            contexts = newHosts[pos].contextList.contexts;
            nesting = newHosts[pos].contextList.nesting;
         }
      }

      if (mappingData.context == null) {
         boolean found = false;
         pos = find(contexts, (CharChunk)uri);
         if (pos == -1) {
            if (this.hosts[hostPos].defaultContexts[0] == null) {
               return;
            }

            ctx = this.hosts[hostPos].defaultContexts[0];
            mappingData.context = ctx.object;
            mappingData.contextPath.setString(ctx.name);
            found = true;
            mappingData.isDefaultContext = true;
         }

         if (!found) {
            int lastSlash = -1;

            int uriEnd;
            for(uriEnd = uri.getEnd(); pos >= 0; pos = find(contexts, (CharChunk)uri)) {
               if (contexts != null && uri.startsWith(contexts[pos].name)) {
                  int length = contexts[pos].name.length();
                  if (uri.getLength() == length) {
                     found = true;
                     break;
                  }

                  if (uri.startsWithIgnoreCase("/", length)) {
                     found = true;
                     break;
                  }
               }

               if (lastSlash == -1) {
                  lastSlash = nthSlash(uri, nesting + 1);
               } else {
                  lastSlash = lastSlash(uri);
               }

               uri.setEnd(lastSlash);
            }

            uri.setEnd(uriEnd);
            if (!found) {
               if (contexts != null && "".equals(contexts[0].name)) {
                  ctx = contexts[0];
               } else if (this.hosts[hostPos].defaultContexts[0] != null) {
                  ctx = this.hosts[hostPos].defaultContexts[0];
                  mappingData.isDefaultContext = true;
               }
            } else {
               ctx = contexts[pos];
            }

            if (ctx != null) {
               mappingData.context = ctx.object;
               mappingData.contextPath.setString(ctx.name);
            }
         }
      }

      if (ctx != null && mappingData.wrapper == null) {
         this.internalMapWrapper(ctx, uri, mappingData);
      }

   }

   private void internalMapWrapper(Context context, CharChunk path, MappingData mappingData) throws Exception {
      int pathOffset = path.getStart();
      int pathEnd = path.getEnd();
      boolean noServletPath = false;
      int servletPath;
      if (mappingData.isDefaultContext) {
         servletPath = pathOffset;
      } else {
         int length = context.name.length();
         if (length != pathEnd - pathOffset) {
            servletPath = pathOffset + length;
         } else {
            noServletPath = true;
            path.append('/');
            pathOffset = path.getStart();
            pathEnd = path.getEnd();
            servletPath = pathOffset + length;
         }
      }

      path.setStart(servletPath);
      if (context.emptyPathWrapper != null && path.equals(SLASH)) {
         mappingData.wrapper = context.emptyPathWrapper.object;
         mappingData.requestPath.setString("");
         mappingData.wrapperPath.setString("");
         mappingData.pathInfo.setString("/");
         mappingData.mappingType = 1;
         mappingData.descriptorPath = "/";
         mappingData.matchedPath = "/";
      }

      Wrapper[] exactWrappers = context.exactWrappers;
      if (mappingData.wrapper == null) {
         this.internalMapExactWrapper(exactWrappers, path, mappingData);
      }

      boolean checkJspWelcomeFiles = false;
      Wrapper[] wildcardWrappers = context.wildcardWrappers;
      if (mappingData.wrapper == null) {
         this.internalMapWildcardWrapper(wildcardWrappers, context.nesting, path, mappingData);
         if (mappingData.wrapper != null && mappingData.jspWildCard) {
            char[] buf = path.getBuffer();
            if (buf[pathEnd - 1] == '/') {
               mappingData.wrapper = null;
               checkJspWelcomeFiles = true;
            } else {
               mappingData.wrapperPath.setChars(buf, path.getStart(), path.getEnd());
               mappingData.pathInfo.recycle();
            }
         }
      }

      if (noServletPath) {
         boolean redirect = mappingData.wrapper == null;
         if (!redirect) {
            String wpath = mappingData.wrapperPath.toString();
            redirect = wpath != null && wpath.length() == 0;
         }

         if (redirect) {
            mappingData.redirectPath.setChars(path.getBuffer(), pathOffset, pathEnd);
            path.setEnd(pathEnd - 1);
            return;
         }
      }

      Wrapper[] extensionWrappers = context.extensionWrappers;
      if (mappingData.wrapper == null && !checkJspWelcomeFiles) {
         this.internalMapExtensionWrapper(extensionWrappers, path, mappingData);
      }

      if (mappingData.wrapper == null) {
         boolean checkWelcomeFiles = checkJspWelcomeFiles;
         if (!checkJspWelcomeFiles && pathEnd > 0) {
            char[] buf = path.getBuffer();
            checkWelcomeFiles = buf[pathEnd - 1] == '/';
         }

         if (checkWelcomeFiles) {
            int i;
            for(i = 0; i < context.welcomeResources.length && mappingData.wrapper == null; ++i) {
               path.setStart(pathOffset);
               path.setEnd(pathEnd);
               path.append(context.welcomeResources[i], 0, context.welcomeResources[i].length());
               path.setStart(servletPath);
               if (mappingData.wrapper == null && context.resources != null) {
                  Object file = null;
                  String pathStr = path.toString();
                  if (context.alternateDocBases != null && !context.alternateDocBases.isEmpty()) {
                     AlternateDocBase match = AlternateDocBase.findMatch(pathStr, context.alternateDocBases);
                     if (match != null) {
                        try {
                           file = match.getResources().lookup(pathStr);
                        } catch (NamingException var22) {
                        }
                     } else {
                        try {
                           file = context.resources.lookup(pathStr);
                        } catch (NamingException var21) {
                        }
                     }
                  } else {
                     try {
                        file = context.resources.lookup(pathStr);
                     } catch (NamingException var23) {
                     }
                  }

                  if (file != null && !(file instanceof DirContext)) {
                     this.internalMapExactWrapper(exactWrappers, path, mappingData);
                     if (mappingData.wrapper == null) {
                        this.internalMapWildcardWrapper(wildcardWrappers, context.nesting, path, mappingData);
                     }

                     if (mappingData.wrapper == null) {
                        this.internalMapExtensionWrapper(extensionWrappers, path, mappingData);
                     }

                     if (mappingData.wrapper == null && context.defaultWrapper != null) {
                        mappingData.wrapper = context.defaultWrapper.object;
                        mappingData.servletName = context.defaultWrapper.servletName;
                        mappingData.requestPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
                        mappingData.wrapperPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
                        mappingData.requestPath.setString(pathStr);
                        mappingData.wrapperPath.setString(pathStr);
                        mappingData.mappingType = 2;
                        mappingData.descriptorPath = "/";
                        mappingData.matchedPath = "/";
                     }
                  }
               }
            }

            if (mappingData.wrapper == null) {
               for(i = 0; i < context.welcomeResources.length && mappingData.wrapper == null; ++i) {
                  path.setStart(pathOffset);
                  path.setEnd(pathEnd);
                  path.append(context.welcomeResources[i], 0, context.welcomeResources[i].length());
                  path.setStart(servletPath);
                  this.internalMapExactWrapper(exactWrappers, path, mappingData);
                  if (mappingData.wrapper == null) {
                     this.internalMapWildcardWrapper(wildcardWrappers, context.nesting, path, mappingData);
                  }

                  if (mappingData.wrapper == null) {
                     this.internalMapExtensionWrapper(extensionWrappers, path, mappingData);
                  }

                  if (mappingData.wrapper != null && JSP_SERVLET.equals(mappingData.servletName)) {
                     mappingData.wrapper = null;
                  }
               }
            }

            path.setStart(servletPath);
            path.setEnd(pathEnd);
         }
      }

      if (mappingData.wrapper == null && !checkJspWelcomeFiles) {
         if (context.defaultWrapper != null) {
            mappingData.wrapper = context.defaultWrapper.object;
            mappingData.servletName = context.defaultWrapper.servletName;
            mappingData.requestPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
            mappingData.wrapperPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
            mappingData.mappingType = 2;
            mappingData.descriptorPath = "/";
            mappingData.matchedPath = mappingData.requestPath.toString();
         }

         char[] buf = path.getBuffer();
         if (context.resources != null && pathEnd > 0 && buf[pathEnd - 1] != '/') {
            Object file = null;
            String pathStr = path.toString();
            if (context.alternateDocBases != null && !context.alternateDocBases.isEmpty()) {
               AlternateDocBase match = AlternateDocBase.findMatch(pathStr, context.alternateDocBases);
               if (match != null) {
                  try {
                     file = match.getResources().lookup(pathStr);
                  } catch (NamingException var19) {
                  }
               } else {
                  try {
                     file = context.resources.lookup(pathStr);
                  } catch (NamingException var18) {
                  }
               }
            } else {
               try {
                  file = context.resources.lookup(pathStr);
               } catch (NamingException var20) {
               }
            }

            if (file != null && file instanceof DirContext) {
               path.setStart(pathOffset);
               path.append('/');
               mappingData.redirectPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
            } else {
               mappingData.requestPath.setString(pathStr);
               mappingData.wrapperPath.setString(pathStr);
            }
         }
      }

      path.setStart(pathOffset);
      path.setEnd(pathEnd);
   }

   private void internalMapExactWrapper(Wrapper[] wrappers, CharChunk path, MappingData mappingData) {
      int pos = find(wrappers, (CharChunk)path);
      if (pos != -1 && path.equals(wrappers[pos].name)) {
         mappingData.requestPath.setString(wrappers[pos].name);
         mappingData.wrapperPath.setString(wrappers[pos].name);
         mappingData.wrapper = wrappers[pos].object;
         mappingData.servletName = wrappers[pos].servletName;
         mappingData.descriptorPath = wrappers[pos].path;
         mappingData.matchedPath = path.toString();
         mappingData.mappingType = (byte)("/".equals(mappingData.matchedPath) ? 2 : 4);
      }

   }

   private void internalMapWildcardWrapper(Wrapper[] wrappers, int nesting, CharChunk path, MappingData mappingData) {
      int pathEnd = path.getEnd();
      int lastSlash = -1;
      int length = -1;
      int pos = find(wrappers, (CharChunk)path);
      if (pos != -1) {
         boolean found;
         for(found = false; pos >= 0; pos = find(wrappers, (CharChunk)path)) {
            if (path.startsWith(wrappers[pos].name)) {
               length = wrappers[pos].name.length();
               if (path.getLength() == length) {
                  found = true;
                  break;
               }

               if (path.startsWithIgnoreCase("/", length)) {
                  found = true;
                  break;
               }
            }

            if (lastSlash == -1) {
               lastSlash = nthSlash(path, nesting + 1);
            } else {
               lastSlash = lastSlash(path);
            }

            path.setEnd(lastSlash);
         }

         path.setEnd(pathEnd);
         if (found) {
            mappingData.wrapperPath.setString(wrappers[pos].name);
            if (path.getLength() > length) {
               mappingData.pathInfo.setChars(path.getBuffer(), path.getStart() + length, path.getEnd());
            }

            mappingData.requestPath.setChars(path.getBuffer(), path.getStart(), path.getEnd());
            mappingData.wrapper = wrappers[pos].object;
            mappingData.servletName = wrappers[pos].servletName;
            mappingData.jspWildCard = wrappers[pos].jspWildCard;
            mappingData.mappingType = 16;
            mappingData.descriptorPath = wrappers[pos].path;
            mappingData.matchedPath = path.toString();
         }
      }

   }

   private void internalMapExtensionWrapper(Wrapper[] wrappers, CharChunk path, MappingData mappingData) {
      char[] buf = path.getBuffer();
      int pathEnd = path.getEnd();
      int servletPath = path.getStart();
      int slash = -1;

      int period;
      for(period = pathEnd - 1; period >= servletPath; --period) {
         if (buf[period] == '/') {
            slash = period;
            break;
         }
      }

      if (slash >= 0) {
         period = -1;

         int pos;
         for(pos = pathEnd - 1; pos > slash; --pos) {
            if (buf[pos] == '.') {
               period = pos;
               break;
            }
         }

         if (period >= 0) {
            path.setStart(period + 1);
            path.setEnd(pathEnd);
            pos = find(wrappers, (CharChunk)path);
            if (pos != -1 && path.equals(wrappers[pos].name)) {
               mappingData.wrapperPath.setChars(buf, servletPath, pathEnd);
               mappingData.requestPath.setChars(buf, servletPath, pathEnd);
               mappingData.wrapper = wrappers[pos].object;
               mappingData.servletName = wrappers[pos].servletName;
               mappingData.mappingType = 8;
               mappingData.descriptorPath = wrappers[pos].path;
            }

            path.setStart(servletPath);
            path.setEnd(pathEnd);
            mappingData.matchedPath = path.toString();
         }
      }

   }

   private static int find(MapElement[] map, CharChunk name) {
      return find(map, name, name.getStart(), name.getEnd());
   }

   private static int find(MapElement[] map, CharChunk name, int start, int end) {
      int a = 0;
      int b = map.length - 1;
      if (b == -1) {
         return -1;
      } else if (compare(name, start, end, map[0].name) < 0) {
         return -1;
      } else if (b == 0) {
         return 0;
      } else {
         do {
            int i = b + a >>> 1;
            int result = compare(name, start, end, map[i].name);
            if (result == 1) {
               a = i;
            } else {
               if (result == 0) {
                  return i;
               }

               b = i;
            }
         } while(b - a != 1);

         int result2 = compare(name, start, end, map[b].name);
         if (result2 < 0) {
            return a;
         } else {
            return b;
         }
      }
   }

   private static int findIgnoreCase(MapElement[] map, CharChunk name) {
      return findIgnoreCase(map, name, name.getStart(), name.getEnd());
   }

   private static int findIgnoreCase(MapElement[] map, CharChunk name, int start, int end) {
      int a = 0;
      int b = map.length - 1;
      if (b == -1) {
         return -1;
      } else if (compareIgnoreCase(name, start, end, map[0].name) < 0) {
         return -1;
      } else if (b == 0) {
         return 0;
      } else {
         do {
            int i = b + a >>> 1;
            int result = compareIgnoreCase(name, start, end, map[i].name);
            if (result == 1) {
               a = i;
            } else {
               if (result == 0) {
                  return i;
               }

               b = i;
            }
         } while(b - a != 1);

         int result2 = compareIgnoreCase(name, start, end, map[b].name);
         if (result2 < 0) {
            return a;
         } else {
            return b;
         }
      }
   }

   private static int findIgnoreCase(MapElement[] map, String name) {
      int a = 0;
      int b = map.length - 1;
      if (b == -1) {
         return -1;
      } else if (compareIgnoreCase(name, map[0].name) < 0) {
         return -1;
      } else if (b == 0) {
         return 0;
      } else {
         do {
            int i = b + a >>> 1;
            int result = compareIgnoreCase(name, map[i].name);
            if (result == 1) {
               a = i;
            } else {
               if (result == 0) {
                  return i;
               }

               b = i;
            }
         } while(b - a != 1);

         int result2 = compareIgnoreCase(name, map[b].name);
         if (result2 < 0) {
            return a;
         } else {
            return b;
         }
      }
   }

   private static int find(MapElement[] map, String name) {
      int a = 0;
      int b = map.length - 1;
      if (b == -1) {
         return -1;
      } else if (name.compareTo(map[0].name) < 0) {
         return -1;
      } else if (b == 0) {
         return 0;
      } else {
         do {
            int i = b + a >>> 1;
            int result = name.compareTo(map[i].name);
            if (result > 0) {
               a = i;
            } else {
               if (result == 0) {
                  return i;
               }

               b = i;
            }
         } while(b - a != 1);

         int result2 = name.compareTo(map[b].name);
         if (result2 < 0) {
            return a;
         } else {
            return b;
         }
      }
   }

   private static int compare(CharChunk name, int start, int end, String compareTo) {
      int result = 0;
      char[] c = name.getBuffer();
      int len = compareTo.length();
      if (end - start < len) {
         len = end - start;
      }

      for(int i = 0; i < len && result == 0; ++i) {
         if (c[i + start] > compareTo.charAt(i)) {
            result = 1;
         } else if (c[i + start] < compareTo.charAt(i)) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareTo.length() > end - start) {
            result = -1;
         } else if (compareTo.length() < end - start) {
            result = 1;
         }
      }

      return result;
   }

   private static int compareIgnoreCase(CharChunk name, int start, int end, String compareTo) {
      int result = 0;
      char[] c = name.getBuffer();
      int len = compareTo.length();
      if (end - start < len) {
         len = end - start;
      }

      for(int i = 0; i < len && result == 0; ++i) {
         if (Ascii.toLower(c[i + start]) > Ascii.toLower(compareTo.charAt(i))) {
            result = 1;
         } else if (Ascii.toLower(c[i + start]) < Ascii.toLower(compareTo.charAt(i))) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareTo.length() > end - start) {
            result = -1;
         } else if (compareTo.length() < end - start) {
            result = 1;
         }
      }

      return result;
   }

   private static int compareIgnoreCase(String name, String compareTo) {
      int result = 0;
      int nameLen = name.length();
      int compareToLen = compareTo.length();
      int len = nameLen < compareToLen ? nameLen : compareToLen;

      for(int i = 0; i < len && result == 0; ++i) {
         int nameLower = Ascii.toLower(name.charAt(i));
         int compareToLower = Ascii.toLower(compareTo.charAt(i));
         if (nameLower > compareToLower) {
            result = 1;
         } else if (nameLower < compareToLower) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareToLen > nameLen) {
            result = -1;
         } else if (compareToLen < nameLen) {
            result = 1;
         }
      }

      return result;
   }

   private static int lastSlash(CharChunk name) {
      char[] c = name.getBuffer();
      int end = name.getEnd();
      int start = name.getStart();
      int pos = end;

      while(pos > start) {
         --pos;
         if (c[pos] == '/') {
            break;
         }
      }

      return pos;
   }

   private static int nthSlash(CharChunk name, int n) {
      char[] c = name.getBuffer();
      int end = name.getEnd();
      int pos = name.getStart();
      int count = 0;

      while(pos < end) {
         if (c[pos++] == '/') {
            ++count;
            if (count == n) {
               --pos;
               break;
            }
         }
      }

      return pos;
   }

   private static int slashCount(String name) {
      int pos = -1;

      int count;
      for(count = 0; (pos = name.indexOf(47, pos + 1)) != -1; ++count) {
      }

      return count;
   }

   private static MapElement insertMap(MapElement[] oldMap, MapElement[] newMap, MapElement newElement) {
      int pos = find(oldMap, newElement.name);
      if (pos != -1 && newElement.name.equals(oldMap[pos].name)) {
         return oldMap[pos];
      } else {
         System.arraycopy(oldMap, 0, newMap, 0, pos + 1);
         newMap[pos + 1] = newElement;
         System.arraycopy(oldMap, pos + 1, newMap, pos + 2, oldMap.length - pos - 1);
         return null;
      }
   }

   private static MapElement insertMapIgnoreCase(MapElement[] oldMap, MapElement[] newMap, MapElement newElement) {
      CharChunk cc = new CharChunk();
      char[] chars = newElement.name.toCharArray();
      cc.setChars(chars, 0, chars.length);
      int pos = findIgnoreCase(oldMap, cc);
      if (pos != -1 && newElement.name.equalsIgnoreCase(oldMap[pos].name)) {
         return oldMap[pos];
      } else {
         System.arraycopy(oldMap, 0, newMap, 0, pos + 1);
         newMap[pos + 1] = newElement;
         System.arraycopy(oldMap, pos + 1, newMap, pos + 2, oldMap.length - pos - 1);
         return null;
      }
   }

   private static boolean removeMap(MapElement[] oldMap, MapElement[] newMap, String name) {
      int pos = find(oldMap, name);
      if (pos != -1 && name.equals(oldMap[pos].name)) {
         System.arraycopy(oldMap, 0, newMap, 0, pos);
         System.arraycopy(oldMap, pos + 1, newMap, pos, oldMap.length - pos - 1);
         return true;
      } else {
         return false;
      }
   }

   private static boolean removeMapIgnoreCase(MapElement[] oldMap, MapElement[] newMap, String name) {
      CharChunk cc = new CharChunk();
      char[] chars = name.toCharArray();
      cc.setChars(chars, 0, chars.length);
      int pos = findIgnoreCase(oldMap, cc);
      if (pos != -1 && name.equalsIgnoreCase(oldMap[pos].name)) {
         System.arraycopy(oldMap, 0, newMap, 0, pos);
         System.arraycopy(oldMap, pos + 1, newMap, pos, oldMap.length - pos - 1);
         return true;
      } else {
         return false;
      }
   }

   static {
      try {
         SLASH.append('/');
      } catch (IOException var1) {
         throw new RuntimeException(var1);
      }
   }

   protected static class Wrapper extends MapElement {
      public String path = null;
      public boolean jspWildCard = false;
      public String servletName = null;
   }

   protected static final class Context extends MapElement {
      public String path = null;
      public String[] welcomeResources = new String[0];
      public NamingContext resources = null;
      public List alternateDocBases = null;
      public Wrapper defaultWrapper = null;
      public Wrapper emptyPathWrapper = null;
      public Wrapper[] exactWrappers = new Wrapper[0];
      public Wrapper[] wildcardWrappers = new Wrapper[0];
      public Wrapper[] extensionWrappers = new Wrapper[0];
      public int nesting = 0;
   }

   protected static final class ContextList {
      public Context[] contexts = new Context[0];
      public int nesting = 0;
   }

   protected static final class Host extends MapElement {
      public ContextList contextList = null;
      public String[] defaultContextPaths = null;
      public Context[] defaultContexts = null;
   }

   protected abstract static class MapElement {
      public String name = null;
      public Object object = null;
   }
}
