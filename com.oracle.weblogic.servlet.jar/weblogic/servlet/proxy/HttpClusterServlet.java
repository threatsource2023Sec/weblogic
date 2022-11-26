package weblogic.servlet.proxy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.CharArrayWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.version;
import weblogic.servlet.internal.ChunkInput;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.utils.Hex;
import weblogic.utils.StringUtils;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedInputStream;
import weblogic.utils.io.NullInputStream;
import weblogic.utils.io.NullOutputStream;

public final class HttpClusterServlet extends GenericProxyServlet {
   private static final int DEFAULT_PORT = 80;
   private static final int DEFAULT_SSL_PORT = 443;
   public static final int MAX_POST_IN_MEMORY;
   public static final NullOutputStream NULL_OUTPUT;
   protected int connectTimeoutSecs;
   protected int connectRetrySecs;
   /** @deprecated */
   @Deprecated
   protected int numOfRetries;
   protected boolean idempotent;
   protected boolean ignoreCookie;
   protected boolean useDynamicList;
   protected boolean crossOverProxyEnabled;
   protected int maxSkips = 0;
   protected long maxSkipTime = 0L;
   protected boolean proxyForConnectionResets;
   protected ServerList srvrList;
   protected Map allKnownServers;
   protected List routingHandlers = new ArrayList();
   private byte[] lock = new byte[0];
   private final List _serverListListeners = new ArrayList();

   public void init(ServletConfig sc) throws ServletException {
      super.init(sc);
      if (this.verbose) {
         this.trace("HttpClusterServlet:init()");
      }

      String param = this.getInitParameter("WebLogicCluster");
      if (param == null) {
         param = this.getInitParameter("defaultServers");
      }

      if (param == null) {
         throw new ServletException("WebLogicCluster is not defined, cannot continue");
      } else {
         if (this.srvrList == null) {
            ServerList list = new ServerList(param, true);
            this.srvrList = list;
         }

         param = this.getInitParameter("ConnectTimeoutSecs");
         if (param == null) {
            this.connectTimeoutSecs = 10;
         } else {
            this.connectTimeoutSecs = Integer.parseInt(param);
         }

         param = this.getInitParameter("ConnectRetrySecs");
         if (param == null) {
            this.connectRetrySecs = 2;
         } else {
            this.connectRetrySecs = Integer.parseInt(param);
         }

         param = this.getInitParameter("numOfRetries");
         if (param == null) {
            this.numOfRetries = this.connectTimeoutSecs / this.connectRetrySecs;
         } else {
            this.numOfRetries = Integer.parseInt(param);
         }

         param = this.getInitParameter("Idempotent");
         this.idempotent = isTrue(param, true);
         param = this.getInitParameter("DisableCookie2Server");
         this.ignoreCookie = isTrue(param, false);
         param = this.getInitParameter("RoutingHandlerClassName");
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         if (loader == null) {
            loader = this.getClass().getClassLoader();
         }

         if (param != null) {
            StringTokenizer st = new StringTokenizer(param, ",");

            while(st.hasMoreTokens()) {
               String token = st.nextToken().trim();

               try {
                  Class clazz = loader.loadClass(token);
                  RoutingHandler handler = (RoutingHandler)clazz.newInstance();
                  handler.init(this);
                  this.routingHandlers.add(handler);
               } catch (RoutingHandlerInitException var8) {
                  var8.printStackTrace();
                  throw var8;
               } catch (Exception var9) {
                  var9.printStackTrace();
               }
            }
         }

         if (this.routingHandlers.isEmpty()) {
            param = this.getInitParameter("DynamicServerList");
            this.useDynamicList = isTrue(param, true);
         } else {
            this.useDynamicList = true;
         }

         param = this.getInitParameter("WLCrossOverProxyEnabled");
         this.crossOverProxyEnabled = isTrue(param, false);
         param = this.getInitParameter("MaxSkips");
         if (param != null) {
            this.maxSkips = Integer.parseInt(param);
            if (this.verbose) {
               this.trace("MaxSkips is deprecated and replaced by MaxSkipTime");
            }
         }

         param = this.getInitParameter("MaxSkipTime");
         if (param != null) {
            this.maxSkipTime = (long)(Integer.parseInt(param) * 1000);
         } else {
            this.maxSkipTime = 10000L;
         }

         param = this.getInitParameter("ProxyForConnectionResets");
         this.proxyForConnectionResets = isTrue(param, false);
         this.allKnownServers = createNewMap();
         this.srvrList.addToKnownServersList(this.allKnownServers);
         this.notifyServerListChange();
      }
   }

   public void destroy() {
      Iterator var1 = this.routingHandlers.iterator();

      while(var1.hasNext()) {
         RoutingHandler handler = (RoutingHandler)var1.next();

         try {
            handler.destroy();
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      super.destroy();
   }

   public ServerList getServerList() {
      return this.srvrList;
   }

   public void addServerListChangeListener(ServerListListener listener) {
      synchronized(this._serverListListeners) {
         if (!this._serverListListeners.contains(listener)) {
            this._serverListListeners.add(listener);
         }

      }
   }

   public void removeServerListChangeListener(ServerListListener listener) {
      synchronized(this._serverListListeners) {
         this._serverListListeners.remove(listener);
      }
   }

   private void notifyServerListChange() {
      ServerListListener[] listeners;
      synchronized(this._serverListListeners) {
         listeners = (ServerListListener[])this._serverListListeners.toArray(new ServerListListener[this._serverListListeners.size()]);
      }

      ServerListListener[] var2 = listeners;
      int var3 = listeners.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServerListListener listener = var2[var4];

         try {
            listener.serverListChanged();
         } catch (Exception var7) {
            var7.printStackTrace();
         }
      }

   }

   private void notifyRoutingDecision(RequestInfo ri, Server s) {
      Map notificationData = ri.getNotificationData();
      Iterator var4 = notificationData.keySet().iterator();

      while(var4.hasNext()) {
         RoutingHandler handler = (RoutingHandler)var4.next();
         Object callbackObject = notificationData.get(handler);
         handler.notifyRoutingDecision(s, callbackObject);
      }

   }

   public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      GenericProxyServlet.ProxyConnection con = null;
      Server s = null;
      RequestInfo ri = new RequestInfo();
      boolean var49 = false;

      boolean changed;
      String dynamicList;
      String serverJVMID;
      ServerList servers;
      label1289: {
         boolean changed;
         String dynamicList;
         ServerList servers;
         String serverJVMID;
         label1290: {
            label1291: {
               label1292: {
                  label1293: {
                     label1294: {
                        try {
                           label1328: {
                              var49 = true;
                              ri.setVerbose(this.verbose);
                              ri.setServerList(this.srvrList);
                              ri.setIsSecureNeeded(this.isSecureProxy);
                              ri.setCanFailover(this.idempotent);
                              ri.setRequest(this.resolveRequest(request));
                              ri.setPost(!request.getMethod().equalsIgnoreCase("GET") && !request.getMethod().equalsIgnoreCase("HEAD") && (request.getHeader("Content-Length") != null || request.getHeader("Transfer-Encoding") != null));
                              if (!this.readPostData(ri, request)) {
                                 if (this.verbose) {
                                    this.trace("Failed to read the post data.");
                                 }

                                 response.sendError(400, "Invalid Post");
                                 var49 = false;
                                 break label1290;
                              }

                              this.getPreferred(ri, request);
                              String queryStr = request.getQueryString();
                              if (queryStr != null && this.debugConfigInfo && queryStr.equals("__WebLogicBridgeConfig")) {
                                 this.printConfigInfo(ri, response.getWriter());
                                 var49 = false;
                                 break label1294;
                              }

                              int numSleeps = 0;
                              boolean nodelay = true;
                              boolean retry = false;
                              s = ri.getPrimaryServer();
                              if (s != null && s.isBad()) {
                                 s = null;
                              }

                              if (this.verbose && s != null) {
                                 this.trace("#### Trying to connect to the primary server:" + s);
                              }

                              while(con == null) {
                                 if (this.verbose) {
                                    this.trace("attempt #" + numSleeps + " out of a max of " + this.numOfRetries);
                                 }

                                 if (s == null) {
                                    s = ri.getServerInList();
                                    if (s == null) {
                                       if (this.verbose) {
                                          this.trace("=== whole list is bad, reverting to the all servers list ===");
                                       }

                                       ri.revertToAllKnownServersList();
                                       s = ri.getServerInList();
                                       if (s == null) {
                                          break;
                                       }

                                       nodelay = false;
                                       if (this.verbose) {
                                          this.trace("#### Trying to connect with server " + s);
                                       }
                                    } else if (this.verbose) {
                                       this.trace("#### Trying to connect with server " + s);
                                    }
                                 }

                                 con = this.attemptConnect(ri, s, request);
                                 if (con == null) {
                                    if (!s.isBad()) {
                                       if (this.verbose) {
                                          this.trace("attemptConnect failed to read post data from client");
                                       }

                                       response.sendError(500, "Error reading post data");
                                       var49 = false;
                                       break label1289;
                                    }

                                    s = null;
                                    nodelay = false;
                                 } else {
                                    this.notifyRoutingDecision(ri, s);
                                    con = this.sendResponse(ri, request, response, con, s);
                                    if (con != null) {
                                       if (this.verbose) {
                                          this.trace("Request successfully processed");
                                       }

                                       this.connPool.requeue(con);
                                       var49 = false;
                                       break label1328;
                                    }

                                    if (!s.isBad()) {
                                       if (this.verbose) {
                                          this.trace("sendResponse failed to write to client");
                                       }

                                       response.sendError(500, "Error writing response data");
                                       var49 = false;
                                       break label1293;
                                    }

                                    if (!ri.canFailover()) {
                                       if (this.verbose) {
                                          this.trace("sendResponse failed to read response from server and cannot failover");
                                       }

                                       response.sendError(500, "Error reading response data");
                                       var49 = false;
                                       break label1291;
                                    }

                                    if (this.verbose) {
                                       this.trace("Error reading response code, or got 503. Trying next server");
                                    }

                                    s = null;
                                    nodelay = false;
                                 }

                                 if (numSleeps >= this.numOfRetries) {
                                    if (this.verbose) {
                                       this.trace("Tried all servers but didn't succeed");
                                    }
                                    break;
                                 }

                                 if (!nodelay) {
                                    nodelay = true;
                                    if (this.verbose) {
                                       this.trace("Sleeping for " + this.connectRetrySecs + " secs .....");
                                    }

                                    try {
                                       ++numSleeps;
                                       Thread.sleep((long)(this.connectRetrySecs * 1000));
                                    } catch (InterruptedException var58) {
                                    }
                                 }
                              }

                              if (con == null) {
                                 response.sendError(503, "No backend servers available");
                                 var49 = false;
                              } else {
                                 var49 = false;
                              }
                              break label1292;
                           }
                        } finally {
                           if (var49) {
                              boolean changed = false;
                              ri.deletePostDataFile();
                              ri.releasePostData();
                              String dynamicList = ri.getDynamicList();
                              if (dynamicList != null) {
                                 ServerList servers = new ServerList(dynamicList, false);
                                 if (!this.useDynamicList) {
                                    if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                                       synchronized(this.lock) {
                                          if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                                             this.srvrList.setHash(ri.getDynamicHash());
                                             this.populateJVMID(this.srvrList, servers);
                                             changed = true;
                                          }
                                       }
                                    }
                                 } else {
                                    servers.setHash(ri.getDynamicHash());
                                    if (this.verbose) {
                                       this.trace("Updating dynamic server list: " + dynamicList);
                                    }

                                    this.srvrList = servers;
                                    servers.addToKnownServersList(this.allKnownServers);
                                    changed = true;
                                 }
                              }

                              String serverJVMID = ri.getServerJVMID();
                              if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
                                 synchronized(this.lock) {
                                    if (!serverJVMID.equals(s.getJVMID())) {
                                       if (this.verbose) {
                                          this.trace("updating JVMID " + serverJVMID + " for " + s);
                                       }

                                       this.srvrList.remove(s);
                                       s.setJVMID(serverJVMID);
                                       this.srvrList.add(s);
                                       changed = true;
                                    }
                                 }
                              }

                              if (changed) {
                                 this.notifyServerListChange();
                              }

                           }
                        }

                        changed = false;
                        ri.deletePostDataFile();
                        ri.releasePostData();
                        dynamicList = ri.getDynamicList();
                        if (dynamicList != null) {
                           servers = new ServerList(dynamicList, false);
                           if (!this.useDynamicList) {
                              if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                                 synchronized(this.lock) {
                                    if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                                       this.srvrList.setHash(ri.getDynamicHash());
                                       this.populateJVMID(this.srvrList, servers);
                                       changed = true;
                                    }
                                 }
                              }
                           } else {
                              servers.setHash(ri.getDynamicHash());
                              if (this.verbose) {
                                 this.trace("Updating dynamic server list: " + dynamicList);
                              }

                              this.srvrList = servers;
                              servers.addToKnownServersList(this.allKnownServers);
                              changed = true;
                           }
                        }

                        serverJVMID = ri.getServerJVMID();
                        if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
                           synchronized(this.lock) {
                              if (!serverJVMID.equals(s.getJVMID())) {
                                 if (this.verbose) {
                                    this.trace("updating JVMID " + serverJVMID + " for " + s);
                                 }

                                 this.srvrList.remove(s);
                                 s.setJVMID(serverJVMID);
                                 this.srvrList.add(s);
                                 changed = true;
                              }
                           }
                        }

                        if (changed) {
                           this.notifyServerListChange();
                        }

                        return;
                     }

                     boolean changed = false;
                     ri.deletePostDataFile();
                     ri.releasePostData();
                     serverJVMID = ri.getDynamicList();
                     if (serverJVMID != null) {
                        ServerList servers = new ServerList(serverJVMID, false);
                        if (!this.useDynamicList) {
                           if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                              synchronized(this.lock) {
                                 if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                                    this.srvrList.setHash(ri.getDynamicHash());
                                    this.populateJVMID(this.srvrList, servers);
                                    changed = true;
                                 }
                              }
                           }
                        } else {
                           servers.setHash(ri.getDynamicHash());
                           if (this.verbose) {
                              this.trace("Updating dynamic server list: " + serverJVMID);
                           }

                           this.srvrList = servers;
                           servers.addToKnownServersList(this.allKnownServers);
                           changed = true;
                        }
                     }

                     String serverJVMID = ri.getServerJVMID();
                     if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
                        synchronized(this.lock) {
                           if (!serverJVMID.equals(s.getJVMID())) {
                              if (this.verbose) {
                                 this.trace("updating JVMID " + serverJVMID + " for " + s);
                              }

                              this.srvrList.remove(s);
                              s.setJVMID(serverJVMID);
                              this.srvrList.add(s);
                              changed = true;
                           }
                        }
                     }

                     if (changed) {
                        this.notifyServerListChange();
                     }

                     return;
                  }

                  changed = false;
                  ri.deletePostDataFile();
                  ri.releasePostData();
                  dynamicList = ri.getDynamicList();
                  if (dynamicList != null) {
                     servers = new ServerList(dynamicList, false);
                     if (!this.useDynamicList) {
                        if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                           synchronized(this.lock) {
                              if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                                 this.srvrList.setHash(ri.getDynamicHash());
                                 this.populateJVMID(this.srvrList, servers);
                                 changed = true;
                              }
                           }
                        }
                     } else {
                        servers.setHash(ri.getDynamicHash());
                        if (this.verbose) {
                           this.trace("Updating dynamic server list: " + dynamicList);
                        }

                        this.srvrList = servers;
                        servers.addToKnownServersList(this.allKnownServers);
                        changed = true;
                     }
                  }

                  serverJVMID = ri.getServerJVMID();
                  if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
                     synchronized(this.lock) {
                        if (!serverJVMID.equals(s.getJVMID())) {
                           if (this.verbose) {
                              this.trace("updating JVMID " + serverJVMID + " for " + s);
                           }

                           this.srvrList.remove(s);
                           s.setJVMID(serverJVMID);
                           this.srvrList.add(s);
                           changed = true;
                        }
                     }
                  }

                  if (changed) {
                     this.notifyServerListChange();
                  }

                  return;
               }

               changed = false;
               ri.deletePostDataFile();
               ri.releasePostData();
               dynamicList = ri.getDynamicList();
               if (dynamicList != null) {
                  servers = new ServerList(dynamicList, false);
                  if (!this.useDynamicList) {
                     if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                        synchronized(this.lock) {
                           if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                              this.srvrList.setHash(ri.getDynamicHash());
                              this.populateJVMID(this.srvrList, servers);
                              changed = true;
                           }
                        }
                     }
                  } else {
                     servers.setHash(ri.getDynamicHash());
                     if (this.verbose) {
                        this.trace("Updating dynamic server list: " + dynamicList);
                     }

                     this.srvrList = servers;
                     servers.addToKnownServersList(this.allKnownServers);
                     changed = true;
                  }
               }

               serverJVMID = ri.getServerJVMID();
               if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
                  synchronized(this.lock) {
                     if (!serverJVMID.equals(s.getJVMID())) {
                        if (this.verbose) {
                           this.trace("updating JVMID " + serverJVMID + " for " + s);
                        }

                        this.srvrList.remove(s);
                        s.setJVMID(serverJVMID);
                        this.srvrList.add(s);
                        changed = true;
                     }
                  }
               }

               if (changed) {
                  this.notifyServerListChange();
               }

               return;
            }

            changed = false;
            ri.deletePostDataFile();
            ri.releasePostData();
            dynamicList = ri.getDynamicList();
            if (dynamicList != null) {
               servers = new ServerList(dynamicList, false);
               if (!this.useDynamicList) {
                  if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                     synchronized(this.lock) {
                        if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                           this.srvrList.setHash(ri.getDynamicHash());
                           this.populateJVMID(this.srvrList, servers);
                           changed = true;
                        }
                     }
                  }
               } else {
                  servers.setHash(ri.getDynamicHash());
                  if (this.verbose) {
                     this.trace("Updating dynamic server list: " + dynamicList);
                  }

                  this.srvrList = servers;
                  servers.addToKnownServersList(this.allKnownServers);
                  changed = true;
               }
            }

            serverJVMID = ri.getServerJVMID();
            if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
               synchronized(this.lock) {
                  if (!serverJVMID.equals(s.getJVMID())) {
                     if (this.verbose) {
                        this.trace("updating JVMID " + serverJVMID + " for " + s);
                     }

                     this.srvrList.remove(s);
                     s.setJVMID(serverJVMID);
                     this.srvrList.add(s);
                     changed = true;
                  }
               }
            }

            if (changed) {
               this.notifyServerListChange();
            }

            return;
         }

         changed = false;
         ri.deletePostDataFile();
         ri.releasePostData();
         dynamicList = ri.getDynamicList();
         if (dynamicList != null) {
            servers = new ServerList(dynamicList, false);
            if (!this.useDynamicList) {
               if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                  synchronized(this.lock) {
                     if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                        this.srvrList.setHash(ri.getDynamicHash());
                        this.populateJVMID(this.srvrList, servers);
                        changed = true;
                     }
                  }
               }
            } else {
               servers.setHash(ri.getDynamicHash());
               if (this.verbose) {
                  this.trace("Updating dynamic server list: " + dynamicList);
               }

               this.srvrList = servers;
               servers.addToKnownServersList(this.allKnownServers);
               changed = true;
            }
         }

         serverJVMID = ri.getServerJVMID();
         if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
            synchronized(this.lock) {
               if (!serverJVMID.equals(s.getJVMID())) {
                  if (this.verbose) {
                     this.trace("updating JVMID " + serverJVMID + " for " + s);
                  }

                  this.srvrList.remove(s);
                  s.setJVMID(serverJVMID);
                  this.srvrList.add(s);
                  changed = true;
               }
            }
         }

         if (changed) {
            this.notifyServerListChange();
         }

         return;
      }

      changed = false;
      ri.deletePostDataFile();
      ri.releasePostData();
      dynamicList = ri.getDynamicList();
      if (dynamicList != null) {
         servers = new ServerList(dynamicList, false);
         if (!this.useDynamicList) {
            if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
               synchronized(this.lock) {
                  if (this.srvrList.getHash() == null || !this.srvrList.getHash().equals(ri.getDynamicHash())) {
                     this.srvrList.setHash(ri.getDynamicHash());
                     this.populateJVMID(this.srvrList, servers);
                     changed = true;
                  }
               }
            }
         } else {
            servers.setHash(ri.getDynamicHash());
            if (this.verbose) {
               this.trace("Updating dynamic server list: " + dynamicList);
            }

            this.srvrList = servers;
            servers.addToKnownServersList(this.allKnownServers);
            changed = true;
         }
      }

      serverJVMID = ri.getServerJVMID();
      if (serverJVMID != null && s != null && !serverJVMID.equals(s.getJVMID())) {
         synchronized(this.lock) {
            if (!serverJVMID.equals(s.getJVMID())) {
               if (this.verbose) {
                  this.trace("updating JVMID " + serverJVMID + " for " + s);
               }

               this.srvrList.remove(s);
               s.setJVMID(serverJVMID);
               this.srvrList.add(s);
               changed = true;
            }
         }
      }

      if (changed) {
         this.notifyServerListChange();
      }

   }

   private void populateJVMID(ServerList origList, ServerList newlist) {
      TreeMap servermap = new TreeMap(new Comparator() {
         public int compare(Object o1, Object o2) {
            Server s1 = (Server)o1;
            Server s2 = (Server)o2;
            String hostip1 = s1.getHostIp();
            String hostip2 = s2.getHostIp();
            int port1 = s1.getPort();
            int port2 = s2.getPort();
            int sslPort1 = s1.getSecurePort();
            int sslPort2 = s2.getSecurePort();
            if (hostip1 != null && hostip2 != null) {
               int comp = hostip1.compareTo(hostip2);
               if (comp == 0) {
                  comp = port1 - port2;
                  if (comp == 0 && port1 == -1) {
                     return sslPort1 - sslPort2;
                  }

                  return comp;
               }
            }

            return -1;
         }
      });
      Server[] oldservers = origList.toArray();

      for(int i = 0; i < oldservers.length; ++i) {
         servermap.put(oldservers[i], oldservers[i]);
      }

      Iterator it = newlist.iterator();

      while(it.hasNext()) {
         Server s = (Server)it.next();
         if (servermap.containsKey(s)) {
            Server olds = (Server)servermap.get(s);
            origList.remove(olds);
            origList.add(s);
         }
      }

   }

   public void printConfigInfo(RequestInfo ri, PrintWriter out) {
      out.write("<HTML><TITLE>WEBLOGIC PROXY DEBUG INFO</TITLE>");
      out.write("<FONT FACE=\"Tahoma\">");
      out.write("<BODY>Query String: __WebLogicBridgeConfig");
      if (ri.getPrimaryServer() != null) {
         Server s = ri.getPrimaryServer();
         StringBuilder sb = new StringBuilder(256);
         sb.append("<BR><BR><B>Primary Server:</B> <FONT COLOR=\"#0000ff\">");
         sb.append(s.getHost());
         sb.append(":");
         sb.append(s.getPort());
         sb.append(":");
         sb.append(s.getSecurePort());
         sb.append("</FONT>");
         out.write(sb.toString());
         if (ri.getSecondaryServer() != null) {
            s = ri.getSecondaryServer();
            sb = new StringBuilder(516);
            sb.append("<BR><BR><B>Secondary Server:</B> <FONT COLOR=\"#0000ff\">");
            sb.append(s.getHost());
            sb.append(":");
            sb.append(s.getPort());
            sb.append(":");
            sb.append(s.getSecurePort());
            sb.append("</FONT>");
            out.write(sb.toString());
         }
      }

      ServerList list = ri.getServerList();
      out.write("<BR><BR><B>General Server List:</B><OL>");

      for(int i = 0; i < list.size(); ++i) {
         StringBuilder sb = new StringBuilder(256);
         Server s = list.next();
         sb.append("<LI> <FONT COLOR=\"#0000ff\">");
         sb.append(s.getHost());
         sb.append(":");
         sb.append(s.getPort());
         sb.append(":");
         sb.append(s.getSecurePort());
         sb.append("</FONT></LI>");
         out.write(sb.toString());
      }

      out.write("</OL><BR><B>ConnectRetrySecs: </B>" + this.connectRetrySecs);
      out.write("<BR><B>ConnectTimeoutSecs: </B>" + this.connectTimeoutSecs);
      if (this.cookieName != null) {
         out.write("<BR><B>CookieName: </B><font color=#0000ff> deprecated</font>");
      }

      out.write("<BR><B>WLCookieName: </B>" + this.wlCookieName);
      out.write("<BR><B>Debug: </B>" + this.verbose);
      out.write("<BR><B>DebugConfigInfo: </B>" + this.debugConfigInfo);
      out.write("<BR><B>DefaultFileName: </B>" + this.defaultFileName);
      out.write("<BR><B>DisableCookie2Server: </B>" + this.ignoreCookie);
      out.write("<BR><B>DynamicServerList: </B>" + this.useDynamicList);
      out.write("<BR><B>FileCaching: </B>" + this.fileCaching);
      out.write("<BR><B>WLIOTimeoutSecs: </B>" + this.socketTimeout);
      out.write("<BR><B>Idempotent: </B>" + this.idempotent);
      out.write("<BR><B>KeepAliveEnabled: </B>" + this.keepAliveEnabled);
      out.write("<BR><B>KeepAliveSecs: </B>" + this.keepAliveSecs);
      out.write("<BR><B>MaxPostSize: </B>" + this.maxPostSize);
      if (this.maxSkips != 0) {
         out.write("<BR><B>MaxSkips: </B>deprecated");
      }

      out.write("<BR><B>MaxSkipTime: </B>" + this.maxSkipTime / 1000L);
      out.write("<BR><B>PathPrepend: </B>" + this.pathPrepend);
      out.write("<BR><B>PathTrim: </B>" + this.pathTrim);
      out.write("<BR><B>TrimExt: </B>" + this.trimExt);
      out.write("<BR><B>SecureProxy: </B>" + this.isSecureProxy);
      out.write("<BR><B>WLLogFile: </B>" + this.logFileName);
      out.write("<BR><B>WLProxySSL: </B>" + this.wlProxySSL);
      out.write("<BR><B>ProxyForConnectionResets: </B>" + this.proxyForConnectionResets);
      out.write("<BR>_____________________________________________________");
      out.write("<BR><BR>Last Modified: " + version.getBuildVersion());
      out.write("</BODY></HTML>");
      out.close();
   }

   protected void addRequestHeaders(HttpServletRequest request, PrintStream headerOut, Object o1, Object o2) {
      super.addRequestHeaders(request, headerOut, o1, o2);
      Iterator var5 = this.routingHandlers.iterator();

      while(var5.hasNext()) {
         RoutingHandler handler = (RoutingHandler)var5.next();

         try {
            handler.addRequestHeaders(request, headerOut, (RequestInfo)o1, (Server)o2);
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }

      if (this.srvrList.getHash() != null) {
         headerOut.print("X-WebLogic-Cluster-Hash: " + this.srvrList.getHash());
         headerOut.print("\r\n");
      }

      int clen = ((RequestInfo)o1).getContentLen();
      if (clen != -1) {
         headerOut.print("Content-Length: " + clen);
         headerOut.print("\r\n");
      }

      if (this.srvrList.getHash() == null) {
         if (((Server)o2).getJVMID() == null) {
            headerOut.print("X-WebLogic-Force-JVMID: unset");
         } else {
            headerOut.print("X-WebLogic-Force-JVMID: " + ((Server)o2).getJVMID());
         }

         headerOut.print("\r\n");
      }

      headerOut.print("X-WebLogic-Request-ClusterInfo: true");
      headerOut.print("\r\n");
   }

   public void addResponseHeaders(HttpServletResponse response, String name, String value, Object o) {
      RequestInfo ri = (RequestInfo)o;
      if (ri.needToUpdateDynamicList() && name.equals("X-WebLogic-Cluster-List")) {
         ri.setDynamicList(value);
      } else if (ri.needToUpdateDynamicList() && name.equals("X-WebLogic-Cluster-Hash")) {
         ri.setDynamicHash(value);
      } else if (name.equals("X-WebLogic-JVMID")) {
         ri.setServerJVMID(value);
      } else {
         boolean handled = false;
         Iterator var7 = this.routingHandlers.iterator();

         while(var7.hasNext()) {
            RoutingHandler handler = (RoutingHandler)var7.next();

            try {
               handled |= handler.handleResponseHeader(response, name, value, ri);
            } catch (Exception var10) {
               var10.printStackTrace();
            }
         }

         if (!handled) {
            super.addResponseHeaders(response, name, value, o);
         }
      }
   }

   public boolean getPreferred(RequestInfo ri, HttpServletRequest request) {
      ri.setNotificationData(new HashMap());
      Iterator var3 = this.routingHandlers.iterator();

      while(var3.hasNext()) {
         RoutingHandler handler = (RoutingHandler)var3.next();

         try {
            RequestInfo ri2 = handler.route(ri, request);
            if (ri2 != null) {
               return true;
            }
         } catch (Exception var19) {
            var19.printStackTrace();
         }
      }

      String cookie = null;
      int size;
      int cookieLen;
      if (!this.ignoreCookie) {
         Enumeration cookies = request.getHeaders("Cookie");

         label326:
         while(true) {
            do {
               if (!cookies.hasMoreElements() || cookie != null) {
                  break label326;
               }

               cookie = (String)cookies.nextElement();
            } while(cookie == null);

            cookieLen = -1;

            for(int k = 0; k < cookie.length() - this.wlCookieName.length(); cookieLen = -1) {
               cookieLen = cookie.indexOf(this.wlCookieName, k);
               if (cookieLen < 1) {
                  break;
               }

               for(size = cookieLen - 1; size > -1 && cookie.charAt(size) == ' '; --size) {
               }

               if (size < 0 || cookie.charAt(size) == ';') {
                  break;
               }

               k = cookieLen + this.wlCookieName.length();
            }

            if (cookieLen >= 0) {
               try {
                  cookie = cookie.substring(cookieLen + this.wlCookieName.length() + 1);
                  cookieLen = cookie.indexOf(";");
                  if (cookieLen != -1) {
                     cookie = cookie.substring(0, cookieLen);
                  }

                  cookieLen = cookie.indexOf("!");
                  cookie = cookie.substring(cookieLen + 1);
                  if (this.verbose) {
                     this.trace("Found cookie: " + cookie);
                  }
               } catch (ArrayIndexOutOfBoundsException var18) {
                  cookie = null;
               }
            } else {
               cookie = null;
            }
         }
      }

      if (cookie == null) {
         cookie = ServletRequestImpl.getOriginalRequest(request).getSessionHelper().getEncodedSessionID();
         if (cookie != null) {
            int index = cookie.indexOf("!");
            if (cookie.endsWith("|")) {
               cookie = cookie.substring(index + 1, cookie.length() - 1);
            } else {
               cookie = cookie.substring(index + 1);
            }
         }

         if (this.verbose) {
            this.trace("Found session from url: " + cookie);
         }
      }

      if (cookie == null && ri.isPost()) {
         String ctype = request.getContentType();
         cookieLen = this.wlCookieName.length();
         if (ctype != null && ctype.startsWith("application/x-www-form-urlencoded")) {
            Chunk c = ri.getPostData();
            if (c != null) {
               size = Chunk.size(c);
               byte[] postData = new byte[Chunk.size(c)];
               Chunk tmp = c;

               for(int pos = 0; tmp != null; tmp = tmp.next) {
                  System.arraycopy(tmp.buf, 0, postData, pos, tmp.end);
                  pos += tmp.end;
               }

               int offset = 0;

               for(int i = 0; i < size; ++i) {
                  if (postData[i] == 37 && i + 2 < size && Hex.isHexChar(postData[i + 1]) && Hex.isHexChar(postData[i + 2])) {
                     postData[offset++] = (byte)((Hex.hexValueOf(postData[i + 1]) << 4) + (Hex.hexValueOf(postData[i + 2]) << 0));
                     i += 2;
                  } else if (postData[i] == 43) {
                     postData[offset++] = 32;
                  } else {
                     postData[offset++] = postData[i];
                  }
               }

               char startChar = this.wlCookieName.charAt(0);

               for(int i = 0; i < offset; ++i) {
                  if (postData[i] != startChar) {
                     if ((char)postData[i] != ' ') {
                        while(i < offset && (char)postData[i] != '&') {
                           ++i;
                        }
                     }
                  } else {
                     int z = 0;

                     int j;
                     for(j = 1; j < cookieLen && postData[i + j] == this.wlCookieName.charAt(j); ++j) {
                     }

                     if (j >= cookieLen && (char)postData[i + j] == '=') {
                        i += j;
                        boolean foundTerminator = false;
                        if (j >= cookieLen) {
                           while(i < offset && postData[i] == 32) {
                              ++i;
                           }

                           while(i < offset && postData[i] == 61) {
                              ++i;
                           }

                           while(i < offset && postData[i] == 32) {
                              ++i;
                           }

                           while(i < offset && postData[i] == 34) {
                              ++i;
                           }

                           while(i < offset && postData[i] != "!".charAt(0)) {
                              ++i;
                           }

                           j = 0;

                           for(z = i + j; z < offset; ++z) {
                              byte charAt = postData[z];
                              if (charAt == 59 || charAt == 34 || charAt == 38) {
                                 foundTerminator = true;
                                 break;
                              }

                              ++j;
                           }
                        }

                        if (postData[i] == "!".charAt(0)) {
                           ++i;
                        }

                        if (foundTerminator) {
                           cookie = new String(postData, i, z - i);
                        } else {
                           cookie = new String(postData, i, offset - i);
                        }

                        if (this.verbose) {
                           this.trace("Found session in the post data: " + cookie);
                        }
                     }
                  }
               }
            }
         }
      }

      if (cookie != null) {
         String[] parts = StringUtils.splitCompletely(new String(cookie), "!");
         Server s;
         switch (parts.length) {
            case 1:
               if (this.useDynamicList) {
                  ServerList dynamicList = this.initDynamicServerList(this.srvrList);
                  if (dynamicList != null) {
                     this.srvrList = dynamicList;
                     ri.setServerList(this.srvrList);
                  }
               }

               s = new Server(parts[0], (String)null, 0, 0);
               ri.setPrimaryServer(s);
               return true;
            case 2:
            case 3:
               if (this.srvrList.isStatic()) {
                  ServerList dynamicList = this.initDynamicServerList(this.srvrList);
                  if (dynamicList != null) {
                     this.srvrList = dynamicList;
                     ri.setServerList(this.srvrList);
                  }
               }

               s = new Server(parts[0], (String)null, 0, 0);
               ri.setPrimaryServer(s);
               if (ri.getPrimaryServer() != null && !parts[1].regionMatches(true, 0, "NONE", 0, 4) && parts[1].length() <= 11) {
                  s = new Server(parts[1], (String)null, 0, 0);
                  ri.setSecondaryServer(s);
               }

               return true;
            case 4:
            case 5:
            case 6:
               if (parts.length != 4 && !parts[4].regionMatches(true, 0, "NONE", 0, 4)) {
                  if (parts[4].length() > 6) {
                     s = new Server(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                     ri.setPrimaryServer(s);
                     if (ri.getPrimaryServer() != null) {
                        s = new Server(parts[4], (String)null, 0, 0);
                        ri.setSecondaryServer(s);
                     }
                  } else {
                     s = new Server(parts[0], (String)null, 0, 0);
                     ri.setPrimaryServer(s);
                     if (ri.getPrimaryServer() != null) {
                        s = new Server(parts[1], parts[2], Integer.parseInt(parts[3]), Integer.parseInt(parts[4]));
                        ri.setSecondaryServer(s);
                     }
                  }
               } else {
                  s = new Server(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
                  ri.setPrimaryServer(s);
               }

               return true;
            case 7:
            default:
               if (this.verbose) {
                  this.trace("malformed cookie: " + cookie);
               }

               return false;
            case 8:
            case 9:
               s = new Server(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
               ri.setPrimaryServer(s);
               s = new Server(parts[4], parts[5], Integer.parseInt(parts[6]), Integer.parseInt(parts[7]));
               ri.setSecondaryServer(s);
               return true;
         }
      } else {
         return false;
      }
   }

   private ServerList initDynamicServerList(ServerList origList) {
      ServerList list = null;
      String clusterHash = null;
      GenericProxyServlet.ProxyConnection con = null;
      String firstLine = "HEAD /bea_wls_internal/WLDummyInitJVMIDs HTTP/1.0\r\n";
      Server[] servers = origList.toArray();
      if (servers == null) {
         return null;
      } else {
         int len = servers.length;
         if (len < 1) {
            return null;
         } else {
            for(int i = 0; i < len; ++i) {
               if (servers[i].getJVMID() == null) {
                  long startMarkBad = servers[i].getStartMarkBad();
                  if (startMarkBad > 0L) {
                     long now = System.currentTimeMillis();
                     long delta = now - startMarkBad;
                     if (delta < this.maxSkipTime) {
                        if (this.verbose) {
                           this.trace("Skip retrieving JVMID for " + servers[i] + ", skipTime=" + delta / 1000L + ", max=" + this.maxSkipTime / 1000L);
                        }
                        continue;
                     }

                     if (this.verbose) {
                        this.trace("Reset retrieving JVMID for " + servers[i] + ", mark good after " + this.maxSkipTime / 1000L);
                     }

                     servers[i].markGood();
                  }

                  if (this.verbose) {
                     this.trace("Trying to get JVMID from server: " + servers[i]);
                  }

                  try {
                     if (this.isSecureProxy) {
                        con = new GenericProxyServlet.ProxyConnection(servers[i].getHost(), servers[i].getSecurePort(), true, 10000);
                     } else {
                        con = new GenericProxyServlet.ProxyConnection(servers[i].getHost(), servers[i].getPort(), false, 10000);
                     }

                     BufferedOutputStream out = new BufferedOutputStream(con.getSocket().getOutputStream());
                     PrintStream headerOut = new PrintStream(out);
                     headerOut.print(firstLine);
                     headerOut.print("X-WebLogic-Request-ClusterInfo: true");
                     headerOut.print("\r\n");
                     headerOut.print("X-WebLogic-Force-JVMID: unset");
                     headerOut.print("\r\n");
                     headerOut.print("\r\n");
                     headerOut.flush();
                     DataInputStream in = new DataInputStream(new BufferedInputStream(con.getSocket().getInputStream(), 100));
                     String header = in.readLine();

                     while((header = in.readLine()) != null && header.length() > 0) {
                        String[] kv = StringUtils.split(header, ':');
                        String name = kv[0].trim();
                        String value = kv[1].trim();
                        if (name.equals("X-WebLogic-Cluster-List")) {
                           if (this.verbose) {
                              this.trace("Update dynmaic list: " + value);
                           }

                           list = new ServerList(value, false);
                           if (clusterHash != null) {
                              if (this.verbose) {
                                 this.trace("Update dynmaic hash: " + clusterHash);
                              }

                              list.setHash(clusterHash);
                              break;
                           }
                        } else if (name.equals("X-WebLogic-Cluster-Hash")) {
                           if (list != null) {
                              if (this.verbose) {
                                 this.trace("Update dynmaic hash: " + value);
                              }

                              list.setHash(value);
                              break;
                           }

                           clusterHash = value;
                        } else if (name.equals("X-WebLogic-JVMID") && value != null && !value.equals(servers[i].getJVMID())) {
                           synchronized(origList) {
                              if (!value.equals(servers[i].getJVMID())) {
                                 if (this.verbose) {
                                    this.trace("Update jvmid " + value + " for " + servers[i]);
                                 }

                                 origList.remove(servers[i]);
                                 servers[i].setJVMID(value);
                                 origList.add(servers[i]);
                              }
                           }
                        }
                     }

                     if (list != null) {
                        break;
                     }
                  } catch (IOException var25) {
                     if (this.verbose) {
                        this.trace("Failed to update jvmid for " + servers[i]);
                     }
                  } finally {
                     if (con != null) {
                        con.close();
                     }

                  }
               }
            }

            return list;
         }
      }
   }

   protected boolean readPostData(RequestInfo ri, HttpServletRequest request) {
      if (!ri.isPost()) {
         return true;
      } else {
         long len = ServletRequestImpl.getOriginalRequest(request).getContentLengthLong();
         if (this.maxPostSize > 0 && len > (long)this.maxPostSize) {
            if (this.verbose) {
               this.trace("Content Length exceeded the MaxPostSize: " + this.maxPostSize);
            }

            return false;
         } else if (len > (long)MAX_POST_IN_MEMORY) {
            return this.readPostDataToFile(ri, request, len);
         } else if (len >= 0L) {
            return this.readPostDataToMemory(ri, request, (int)len);
         } else {
            String chunkEnc = request.getHeader("Transfer-Encoding");
            if (chunkEnc == null) {
               return true;
            } else if (chunkEnc.equalsIgnoreCase("Chunked")) {
               ri.setIsChunked(true);
               return this.readChunkedPostData(ri, request);
            } else {
               if (this.verbose) {
                  this.trace("Transfer-Encoding not set or encountered an unsupported value " + chunkEnc);
               }

               return false;
            }
         }
      }
   }

   protected boolean readChunkedPostData(RequestInfo ri, HttpServletRequest request) {
      if (this.fileCaching) {
         File f = null;
         FileOutputStream fos = null;
         Chunk postData = null;
         boolean releaseChunk = true;

         boolean var26;
         try {
            try {
               f = File.createTempFile("proxy", (String)null, (File)null);
               if (f != null) {
                  fos = new FileOutputStream(f);
                  InputStream in = request.getInputStream();
                  postData = Chunk.getChunk();
                  int total = 0;
                  int wtptr = 0;
                  int b = true;

                  boolean var11;
                  int b;
                  while((b = in.read()) != -1) {
                     postData.buf[postData.end++] = (byte)b;
                     if (wtptr == MAX_POST_IN_MEMORY - 1) {
                        fos.write(postData.buf, 0, MAX_POST_IN_MEMORY);
                        wtptr = -1;
                        postData.end = 0;
                     }

                     ++wtptr;
                     ++total;
                     if (this.maxPostSize > 0 && total > this.maxPostSize) {
                        if (this.verbose) {
                           this.trace("Chunked post data exceeded MaxPostSize: " + this.maxPostSize);
                        }

                        try {
                           if (fos != null) {
                              fos.close();
                           }

                           if (f != null) {
                              f.delete();
                           }
                        } catch (Exception var20) {
                        }

                        var11 = false;
                        return var11;
                     }
                  }

                  if (total > MAX_POST_IN_MEMORY) {
                     if (wtptr > 0) {
                        fos.write(postData.buf, 0, postData.end);
                     }

                     fos.flush();
                     fos.close();
                     ri.setPostDataFile(f);
                  } else {
                     if (total <= 0) {
                        if (this.verbose) {
                           this.trace("Failed to read chunked post data");
                        }

                        try {
                           if (fos != null) {
                              fos.close();
                           }

                           if (f != null) {
                              f.delete();
                           }
                        } catch (Exception var22) {
                        }

                        var11 = false;
                        return var11;
                     }

                     releaseChunk = false;
                     ri.setPostData(postData);

                     try {
                        if (fos != null) {
                           fos.close();
                        }

                        if (f != null) {
                           f.delete();
                        }
                     } catch (Exception var21) {
                     }
                  }

                  ri.setContentLen(total);
                  return true;
               }

               boolean var7 = false;
               return var7;
            } catch (IOException var23) {
               if (this.verbose) {
                  CharArrayWriter out = new CharArrayWriter();
                  var23.printStackTrace(new PrintWriter(out));
                  this.trace("Failed to read chunked post data: " + out.toString());
               }
            }

            try {
               if (fos != null) {
                  fos.close();
               }

               if (f != null) {
                  f.delete();
               }
            } catch (Exception var19) {
            }

            if (this.proxyForConnectionResets) {
               if (this.verbose) {
                  this.trace("ProxyForConnectionResets enabled");
               }

               var26 = true;
               return var26;
            }

            var26 = false;
         } finally {
            if (releaseChunk && postData != null) {
               Chunk.releaseChunk(postData);
            }

         }

         return var26;
      } else {
         return true;
      }
   }

   protected boolean readPostDataToMemory(RequestInfo ri, HttpServletRequest request, int len) {
      if (len == 0) {
         return true;
      } else {
         try {
            Chunk postData = this.readPostDataToMemory(request, len);
            if (postData == null) {
               return false;
            } else {
               ri.setPostData(postData);
               return true;
            }
         } catch (IOException var6) {
            if (this.verbose) {
               CharArrayWriter out = new CharArrayWriter();
               var6.printStackTrace(new PrintWriter(out));
               this.trace("Failed to read post data in memory: " + out.toString());
            }

            if (this.proxyForConnectionResets) {
               if (this.verbose) {
                  this.trace("ProxyForConnectionResets enabled");
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   protected boolean readPostDataToFile(RequestInfo ri, HttpServletRequest request, long len) {
      if (this.fileCaching) {
         try {
            File f = this.readPostDataToFile(request, len);
            if (f == null) {
               return false;
            }

            ri.setPostDataFile(f);
         } catch (IOException var7) {
            if (this.verbose) {
               CharArrayWriter out = new CharArrayWriter();
               var7.printStackTrace(new PrintWriter(out));
               this.trace("Failed to read post data into file: " + out.toString());
            }

            if (this.proxyForConnectionResets) {
               if (this.verbose) {
                  this.trace("ProxyForConnectionResets enabled");
               }

               return true;
            }

            return false;
         }
      }

      return true;
   }

   protected boolean sendPostData(HttpServletRequest request, RequestInfo ri, PrintStream out) {
      Chunk postData = ri.getPostData();
      if (postData != null) {
         while(true) {
            if (postData == null) {
               out.flush();
               break;
            }

            out.write(postData.buf, 0, postData.end);
            postData = postData.next;
         }
      } else {
         File postDataFile;
         boolean lenRead;
         if (!this.fileCaching) {
            if (this.verbose) {
               this.trace("FileCaching is OFF, no failover");
            }

            ri.setCanFailover(false);
            postDataFile = null;

            try {
               postData = Chunk.getChunk();
               InputStream in = request.getInputStream();
               if (!ri.isChunked()) {
                  int lenRead;
                  for(long len = ServletRequestImpl.getOriginalRequest(request).getContentLengthLong(); len > 0L; len -= (long)lenRead) {
                     lenRead = in.read(postData.buf, 0, postData.buf.length);
                     out.write(postData.buf, 0, lenRead);
                     out.flush();
                  }

                  return true;
               }

               int b = true;

               int b;
               while((b = in.read()) != -1) {
                  postData.buf[postData.end++] = (byte)b;
                  if (postData.end == Chunk.CHUNK_SIZE) {
                     out.write(postData.buf, 0, postData.end);
                     out.flush();
                     postData.end = 0;
                  }
               }

               if (postData.end > 0) {
                  out.write(postData.buf, 0, postData.end);
                  out.flush();
               }

               return true;
            } catch (IOException var19) {
               if (this.verbose) {
                  this.trace("Error reading Post data from client");
               }

               lenRead = false;
            } finally {
               Chunk.releaseChunk(postData);
            }

            return lenRead;
         } else {
            postDataFile = ri.getPostDataFile();
            if (postDataFile != null) {
               try {
                  FileInputStream fin = new FileInputStream(postDataFile);
                  postData = Chunk.getChunk();
                  lenRead = false;

                  int lenRead;
                  while((lenRead = fin.read(postData.buf, 0, postData.buf.length)) != -1) {
                     out.write(postData.buf, 0, lenRead);
                     out.flush();
                  }

                  fin.close();
                  return true;
               } catch (IOException var21) {
                  ri.setCanFailover(false);
                  if (this.verbose) {
                     this.trace("Error reading Post data from tmp file");
                  }

                  lenRead = false;
               } finally {
                  Chunk.releaseChunk(postData);
               }

               return lenRead;
            }
         }
      }

      return true;
   }

   protected GenericProxyServlet.ProxyConnection attemptConnect(RequestInfo ri, Server s, HttpServletRequest request) {
      GenericProxyServlet.ProxyConnection con = null;
      boolean secure = ri.isSecureNeeded();

      try {
         con = this.connPool.getProxyConnection(s.getHost(), secure ? s.getSecurePort() : s.getPort(), secure, this.connectTimeoutSecs);
         con.setTimeout(this.socketTimeout);
      } catch (IOException var11) {
         if (this.verbose) {
            CharArrayWriter err = new CharArrayWriter();
            var11.printStackTrace(new PrintWriter(err));
            this.trace("Caught exception while trying to connect to server " + s + ": " + err.toString());
         }

         ri.markServerBad(s);
         if (con != null) {
            con.close();
         }

         return null;
      }

      BufferedOutputStream out = null;

      CharArrayWriter err;
      try {
         out = new BufferedOutputStream(con.getSocket().getOutputStream());
      } catch (IOException var13) {
         if (this.verbose) {
            CharArrayWriter err = new CharArrayWriter();
            var13.printStackTrace(new PrintWriter(err));
            this.trace("Caught exception while trying to get an output stream from the connection object: " + err.toString());
         }

         if (con.getLastUsed() == 0L) {
            if (this.verbose) {
               this.trace("New connection threw IOException, Mark Server as BAD");
            }

            ri.markServerBad(s);
            if (con != null) {
               con.close();
            }

            return null;
         }

         if (con != null) {
            con.close();
         }

         if (this.verbose) {
            this.trace("Recycled connection could be BAD, so try creating a new connection to ensure server is down");
         }

         try {
            con = this.connPool.getNewProxyConnection(s.getHost(), secure ? s.getSecurePort() : s.getPort(), secure, this.connectTimeoutSecs);
            con.setTimeout(this.socketTimeout);
            out = new BufferedOutputStream(con.getSocket().getOutputStream());
         } catch (IOException var10) {
            if (this.verbose) {
               err = new CharArrayWriter();
               var10.printStackTrace(new PrintWriter(err));
               this.trace("Caught exception while trying to connect to server " + s + ": " + err.toString());
            }

            ri.markServerBad(s);
            if (con != null) {
               con.close();
            }

            return null;
         }
      }

      PrintStream headerOut = new PrintStream(out);
      headerOut.print(ri.getRequest());
      this.sendRequestHeaders(request, headerOut, ri, s);
      if (!headerOut.checkError()) {
         if (this.verbose) {
            this.trace("Successfully send request headers to server: " + s);
         }
      } else {
         if (con.getLastUsed() == 0L) {
            if (this.verbose) {
               this.trace("New Connection reported error while forwarding request headers to server: " + s + "; Mark Server as BAD");
            }

            ri.markServerBad(s);
            if (con != null) {
               con.close();
            }

            return null;
         }

         if (con != null) {
            con.close();
         }

         if (this.verbose) {
            this.trace("Error while forwarding request headers. Recycled connection could be BAD, so try creating a new connection");
         }

         try {
            con = this.connPool.getNewProxyConnection(s.getHost(), secure ? s.getSecurePort() : s.getPort(), secure, this.connectTimeoutSecs);
            con.setTimeout(this.socketTimeout);
            out = new BufferedOutputStream(con.getSocket().getOutputStream());
         } catch (IOException var12) {
            if (this.verbose) {
               err = new CharArrayWriter();
               var12.printStackTrace(new PrintWriter(err));
               this.trace("Caught exception while trying to connect to server " + s + ": " + err.toString());
            }

            ri.markServerBad(s);
            if (con != null) {
               con.close();
            }

            return null;
         }

         headerOut = new PrintStream(out);
         headerOut.print(ri.getRequest());
         this.sendRequestHeaders(request, headerOut, ri, s);
      }

      if (ri.isPost()) {
         boolean flag = this.sendPostData(request, ri, headerOut);
         if (!flag) {
            if (this.verbose) {
               this.trace("Error while reading post data from client");
            }

            if (con != null) {
               con.close();
            }

            return null;
         }

         if (headerOut.checkError()) {
            if (this.verbose) {
               this.trace("Error while forwarding post data to server: " + s);
            }

            ri.markServerBad(s);
            if (con != null) {
               con.close();
            }

            return null;
         }
      }

      return con;
   }

   public GenericProxyServlet.ProxyConnection sendResponse(RequestInfo ri, HttpServletRequest request, HttpServletResponse response, GenericProxyServlet.ProxyConnection con, Server s) {
      try {
         DataInputStream in = new DataInputStream(new BufferedInputStream(con.getSocket().getInputStream(), 100));
         int status = this.readStatus(request, response, in, con.canRecycle());
         if (status == 503) {
            if (this.verbose) {
               this.trace("Got 503 from server: " + s);
            }

            ri.markServerBad(s);
            if (con != null) {
               con.close();
            }

            return null;
         } else {
            ri.setCanFailover(false);
            int contentLength = this.sendResponseHeaders(response, in, con, ri);
            if (status == 100) {
               status = this.readStatus(request, response, in);
               contentLength = this.sendResponseHeaders(response, in, con, ri);
            }

            if (status != 204 && status != 304 && !"HEAD".equalsIgnoreCase(request.getMethod())) {
               OutputStream os = null;

               try {
                  os = response.getOutputStream();
               } catch (IOException var11) {
                  if (con != null) {
                     con.close();
                  }

                  return null;
               }

               if (contentLength == -9999) {
                  ChunkInput.readCTE((OutputStream)(ri.discardResponse ? NULL_OUTPUT : os), in, true);
               } else if (contentLength != 0) {
                  this.readAndWriteResponseData(in, (OutputStream)(ri.discardResponse ? NULL_OUTPUT : os), contentLength);
               }

               return con;
            } else {
               return con;
            }
         }
      } catch (weblogic.servlet.internal.WriteClientIOException var12) {
         if (con != null) {
            con.close();
         }

         return null;
      } catch (IOException var13) {
         if (this.verbose) {
            CharArrayWriter err = new CharArrayWriter();
            var13.printStackTrace(new PrintWriter(err));
            this.trace("Caught exception while reading response status : " + err.toString());
         }

         ri.markServerBad(s);
         if (con != null) {
            con.close();
         }

         return null;
      }
   }

   protected static Map createNewMap() {
      return Collections.synchronizedMap(new TreeMap(new Comparator() {
         public int compare(Object o1, Object o2) {
            Server s1 = (Server)o1;
            Server s2 = (Server)o2;
            return s1.compareTo(s2);
         }
      }));
   }

   public Server createServer(String s, boolean isStaticList) {
      return new Server(s, isStaticList);
   }

   public Server createServer(String jvmid, String host, int port, int sslPort) {
      return new Server(jvmid, host, port, sslPort);
   }

   static {
      MAX_POST_IN_MEMORY = Chunk.CHUNK_SIZE;
      NULL_OUTPUT = new NullOutputStream();
   }

   public class ServerList {
      private String hash;
      private int index;
      private boolean isStaticList;
      private TreeMap list = new TreeMap(new Comparator() {
         public int compare(Object o1, Object o2) {
            Server s1 = (Server)o1;
            Server s2 = (Server)o2;
            return s1.compareTo(s2);
         }
      });

      public ServerList(boolean b) {
         this.isStaticList = b;
         this.index = -1;
      }

      public ServerList(String servers, boolean b) {
         this.isStaticList = b;
         this.index = -1;
         StringTokenizer st = new StringTokenizer(servers, "|");

         while(st.hasMoreTokens()) {
            String server = st.nextToken();
            Server s = HttpClusterServlet.this.new Server(server, this.isStaticList);
            this.list.put(s, s);
         }

      }

      public Server getServer(Server s) {
         return (Server)this.list.get(s);
      }

      public Server[] toArray() {
         Set keys = this.list.keySet();
         if (keys != null && !keys.isEmpty()) {
            int sz = this.size();
            if (sz <= 0) {
               return null;
            } else {
               Iterator iter = keys.iterator();
               Server[] servers = new Server[sz];

               for(int i = 0; iter.hasNext(); ++i) {
                  servers[i] = (Server)iter.next();
               }

               return servers;
            }
         } else {
            return null;
         }
      }

      public Iterator iterator() {
         return this.list.values().iterator();
      }

      public boolean isStatic() {
         return this.isStaticList;
      }

      public String getHash() {
         return this.hash;
      }

      public void setHash(String hash) {
         this.hash = hash;
      }

      public int size() {
         return this.list.size();
      }

      public void add(Server s) {
         this.list.put(s, s);
      }

      public void remove(Server s) {
         this.list.remove(s);
      }

      public synchronized Server next() {
         if (this.list.size() == 0) {
            return null;
         } else {
            if (this.index == -1) {
               this.index = (int)(Math.random() * (double)this.list.size());
            } else {
               this.index = ++this.index % this.list.size();
            }

            Object[] servers = this.list.values().toArray();
            return (Server)servers[this.index];
         }
      }

      public void addToKnownServersList(Map m) {
         Object[] servers = this.list.values().toArray();

         for(int i = 0; i < this.list.size(); ++i) {
            Object server = servers[i];
            if (!m.containsKey(server)) {
               m.put(server, server);
            }
         }

      }

      public Server getByHostAndPort(String host, int port) {
         Iterator var3 = this.list.values().iterator();

         Server server;
         do {
            do {
               if (!var3.hasNext()) {
                  return null;
               }

               Object obj = var3.next();
               server = (Server)obj;
            } while(!server.getHost().equals(host) && !server.getHostIp().equals(host));
         } while(server.getPort() != port);

         return server;
      }
   }

   public class Server {
      private String jvmid;
      private String host;
      private String hostip;
      private int port;
      private int sslPort;
      private boolean isBad = false;
      private long startMarkBad = 0L;

      public Server(String s, boolean isStaticList) {
         if (isStaticList) {
            int firstCol;
            if (s.indexOf("[") != -1) {
               firstCol = s.indexOf("[");
               int endipv6Index = s.indexOf("]");
               this.setHost(s.substring(firstCol + 1, endipv6Index));
               String portString = s.substring(endipv6Index + 1);
               this.parsePorts(portString);
            } else {
               firstCol = s.indexOf(58);
               if (firstCol == -1) {
                  this.setHost(s);
                  this.port = 80;
                  this.sslPort = 443;
               } else {
                  this.setHost(s.substring(0, firstCol));
                  this.parsePorts(s.substring(firstCol));
               }
            }
         } else {
            String[] parts = StringUtils.splitCompletely(s, "!");
            if (parts.length == 4) {
               this.jvmid = parts[0];
               this.setHost(parts[1]);
               this.port = Integer.parseInt(parts[2]);
               this.sslPort = Integer.parseInt(parts[3]);
            }
         }

      }

      public Server(String jvmid, String host, int port, int sslPort) {
         this.jvmid = jvmid;
         this.setHost(host);
         this.port = port;
         this.sslPort = sslPort;
      }

      public void markGood() {
         this.startMarkBad = 0L;
         this.isBad = false;
      }

      public void markBad() {
         this.startMarkBad = System.currentTimeMillis();
         this.isBad = true;
      }

      public boolean isBad() {
         if (!this.isBad) {
            return false;
         } else {
            if (HttpClusterServlet.this.maxSkipTime > 0L) {
               long now = System.currentTimeMillis();
               if (now - this.startMarkBad >= HttpClusterServlet.this.maxSkipTime) {
                  this.startMarkBad = 0L;
                  this.isBad = false;
               }
            }

            return this.isBad;
         }
      }

      public long getStartMarkBad() {
         return this.startMarkBad;
      }

      public String getJVMID() {
         return this.jvmid;
      }

      public void setJVMID(String jvmid) {
         this.jvmid = jvmid;
      }

      public void setHost(String host) {
         if (host != null) {
            try {
               int hashCode = Integer.parseInt(host);
               this.hostip = (hashCode >> 24 & 255) + "." + (hashCode >> 16 & 255) + "." + (hashCode >> 8 & 255) + "." + (hashCode >> 0 & 255);
               this.host = this.hostip;
            } catch (NumberFormatException var5) {
               this.host = host;

               try {
                  this.hostip = InetAddress.getByName(host).getHostAddress();
               } catch (UnknownHostException var4) {
               }
            }

         }
      }

      public String getHost() {
         return this.host;
      }

      public String getHostIp() {
         return this.hostip;
      }

      public int getPort() {
         return this.port;
      }

      public int getSecurePort() {
         return this.sslPort;
      }

      public int compareTo(Server s) {
         String hostStr = s.getHostIp();
         String jvmidStr = s.getJVMID();
         if (this.jvmid != null && jvmidStr != null) {
            return this.jvmid.compareToIgnoreCase(jvmidStr);
         } else if (hostStr != null && this.hostip != null) {
            int comp = this.hostip.compareTo(hostStr);
            if (comp == 0) {
               comp = this.port - s.getPort();
               return comp == 0 ? this.sslPort - s.getSecurePort() : comp;
            } else {
               return comp;
            }
         } else {
            return -1;
         }
      }

      public String toString() {
         StringBuilder sb = new StringBuilder(100);
         if (this.jvmid != null) {
            sb.append(this.jvmid);
         }

         if (this.host != null) {
            if (this.jvmid != null) {
               sb.append("!");
            }

            sb.append(this.host);
            sb.append("!");
            sb.append(this.port);
            sb.append("!");
            sb.append(this.sslPort);
         }

         return sb.toString();
      }

      private void parsePorts(String portString) {
         int firstCol = portString.indexOf(":");
         int lastCol = portString.lastIndexOf(":");
         if (firstCol == -1) {
            this.port = 80;
            this.sslPort = 443;
         } else if (lastCol != -1 && firstCol != lastCol) {
            this.port = Integer.parseInt(portString.substring(firstCol + 1, lastCol));
            this.sslPort = Integer.parseInt(portString.substring(lastCol + 1));
         } else if (HttpClusterServlet.this.isSecureProxy) {
            this.port = 80;
            this.sslPort = Integer.parseInt(portString.substring(firstCol + 1));
         } else {
            this.port = Integer.parseInt(portString.substring(firstCol + 1));
            this.sslPort = 443;
         }

      }
   }

   public class RequestInfo {
      private ServerList list;
      private boolean isSecure = false;
      private File postDataFile = null;
      private Server primary;
      private Server secondary;
      private String request = null;
      private Chunk postData = null;
      private boolean post = false;
      private boolean canFailover = true;
      private boolean verbose;
      private boolean needToUpdate = true;
      private String dynamicHash = null;
      private String dynamicList = null;
      private boolean chunked = false;
      private int contentLen = -1;
      private String serverJVMID = null;
      private Map notificationData = null;
      public boolean discardResponse;

      public void setServerList(ServerList list) {
         this.list = list;
      }

      public ServerList getServerList() {
         return this.list;
      }

      public Server getServerInList() {
         for(int i = 0; i < this.list.size(); ++i) {
            Server s = this.list.next();
            if (!s.isBad()) {
               return s;
            }
         }

         return null;
      }

      public void revertToAllKnownServersList() {
         this.list = HttpClusterServlet.this.new ServerList(true);
         Iterator iter = HttpClusterServlet.this.allKnownServers.values().iterator();

         while(iter.hasNext()) {
            Server s = (Server)iter.next();
            s.markGood();
            this.list.add(s);
         }

      }

      public void markServerBad(Server s) {
         if (this.primary != null && s.equals(this.primary)) {
            this.primary.markBad();
         }

         if (this.secondary != null && s.equals(this.secondary)) {
            this.secondary.markBad();
         }

         s.markBad();
         if (this.verbose) {
            HttpClusterServlet.this.trace("marked bad: " + s);
         }

      }

      public boolean isSecureNeeded() {
         return this.isSecure;
      }

      public void setIsSecureNeeded(boolean b) {
         this.isSecure = b;
      }

      public File getPostDataFile() {
         return this.postDataFile;
      }

      public void setPostDataFile(File f) {
         this.postDataFile = f;
      }

      public void deletePostDataFile() {
         if (this.postDataFile != null) {
            if (this.verbose) {
               HttpClusterServlet.this.trace("Remove temp file: " + this.postDataFile.getAbsolutePath());
            }

            this.postDataFile.delete();
         }

      }

      public Server getPrimaryServer() {
         return this.primary;
      }

      public void setPrimaryServer(Server s) {
         this.primary = this.list.getServer(s);
         if (this.primary == null && HttpClusterServlet.this.crossOverProxyEnabled) {
            this.primary = s;
            this.needToUpdate = false;
         }

      }

      public Server getSecondaryServer() {
         return this.secondary;
      }

      public void setSecondaryServer(Server s) {
         this.secondary = this.list.getServer(s);
         if (this.secondary == null && HttpClusterServlet.this.crossOverProxyEnabled) {
            this.secondary = s;
            this.needToUpdate = false;
         }

      }

      public String getRequest() {
         return this.request;
      }

      public void setRequest(String s) {
         this.request = s;
      }

      public Chunk getPostData() {
         return this.postData;
      }

      public void setPostData(Chunk c) {
         this.postData = c;
      }

      public void releasePostData() {
         Chunk.releaseChunks(this.postData);
      }

      public boolean isPost() {
         return this.post;
      }

      public void setPost(boolean b) {
         this.post = b;
      }

      public void setCanFailover(boolean b) {
         this.canFailover = b;
      }

      public boolean canFailover() {
         return this.canFailover;
      }

      public void setVerbose(boolean b) {
         this.verbose = b;
      }

      public void setNeedToUpdateDynamicList(boolean b) {
         this.needToUpdate = b;
      }

      public boolean needToUpdateDynamicList() {
         return this.needToUpdate;
      }

      public void setDynamicHash(String s) {
         this.dynamicHash = s;
      }

      public String getDynamicHash() {
         return this.dynamicHash;
      }

      public void setDynamicList(String s) {
         this.dynamicList = s;
      }

      public String getDynamicList() {
         return this.dynamicList;
      }

      public void setIsChunked(boolean b) {
         this.chunked = b;
      }

      public boolean isChunked() {
         return this.chunked;
      }

      public void setContentLen(int len) {
         this.contentLen = len;
      }

      public int getContentLen() {
         return this.contentLen;
      }

      public String getServerJVMID() {
         return this.serverJVMID;
      }

      public void setServerJVMID(String s) {
         this.serverJVMID = s;
      }

      public Map getNotificationData() {
         return this.notificationData;
      }

      public void setNotificationData(Map map) {
         this.notificationData = map;
      }

      public InputStream getInputStream() throws IOException {
         if (this.postData != null) {
            return new ChunkedInputStream(this.postData, 0);
         } else {
            return (InputStream)(this.postDataFile != null ? new BufferedInputStream(new FileInputStream(this.postDataFile)) : new NullInputStream());
         }
      }
   }

   public interface ServerListListener {
      void serverListChanged();
   }
}
