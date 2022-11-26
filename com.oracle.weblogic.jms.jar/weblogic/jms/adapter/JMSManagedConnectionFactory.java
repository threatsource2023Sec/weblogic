package weblogic.jms.adapter;

import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import javax.jms.JMSException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.security.PasswordCredential;
import javax.security.auth.Subject;
import weblogic.jms.JMSLogger;
import weblogic.jms.bridge.AdapterConnection;
import weblogic.jms.bridge.AdapterConnectionFactory;
import weblogic.jms.bridge.ConnectionSpec;
import weblogic.jms.bridge.SourceConnection;
import weblogic.jms.bridge.TargetConnection;

public class JMSManagedConnectionFactory implements ManagedConnectionFactory, Serializable {
   static final long serialVersionUID = -8319737096410419555L;
   private static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private String url;
   private String icFactory;
   private String cfJNDI;
   private String destJNDI;
   private String destType;
   private String adapterType;
   private AdapterConnectionFactory factory;
   private boolean isXA;
   private transient PrintWriter logWriter;
   private final Object logWriterLock = new Object();
   private static transient DateFormat dformat;
   public static final transient Locale MY_LOCALE = Locale.getDefault();

   public JMSManagedConnectionFactory() {
      dformat = DateFormat.getDateTimeInstance(2, 1, MY_LOCALE);
   }

   public Object createConnectionFactory() throws ResourceException {
      this.factory = new JMSBaseConnectionFactory(this, (ConnectionManager)null);
      return this.factory;
   }

   public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
      this.factory = new JMSBaseConnectionFactory(this, cxManager);
      return this.factory;
   }

   public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo info) throws ResourceException {
      try {
         AdapterConnection con = null;
         String userName = null;
         String name = "UNDEFINED";
         int type = 0;
         PrintWriter logWriter = this.getLogWriter();
         PasswordCredential pc = Util.getPasswordCredential(this, subject, info);
         JMSConnectionRequestInfo myinfo = (JMSConnectionRequestInfo)info;
         JMSConnectionSpec spec;
         if (pc == null) {
            if (info == null) {
               spec = (JMSConnectionSpec)null;
            } else {
               spec = new JMSConnectionSpec((String)null, (String)null, myinfo.getUrl(), myinfo.getInitialContextFactory(), myinfo.getSelector(), myinfo.getFactoryJndi(), myinfo.getDestJndi(), myinfo.getDestType(), myinfo.getName(), myinfo.getFullName(), myinfo.getDurability(), myinfo.getClasspath(), myinfo.getPreserveMsgProperty());
               type = myinfo.getType();
               name = myinfo.getName();
            }
         } else {
            userName = pc.getUserName();
            char[] ps = pc.getPassword();
            String password = new String(ps);
            if (info == null) {
               spec = new JMSConnectionSpec(userName, password);
            } else {
               spec = new JMSConnectionSpec(userName, password, myinfo.getUrl(), myinfo.getInitialContextFactory(), myinfo.getSelector(), myinfo.getFactoryJndi(), myinfo.getDestJndi(), myinfo.getDestType(), myinfo.getName(), myinfo.getFullName(), myinfo.getDurability(), myinfo.getClasspath(), myinfo.getPreserveMsgProperty());
               type = myinfo.getType();
               name = myinfo.getName();
            }
         }

         if (type == 1) {
            con = this.createSourceConnection(spec);
         } else if (type == 2) {
            con = this.createTargetConnection(spec);
         } else {
            con = this.createConnection(spec);
         }

         JMSManagedConnection mc = new JMSManagedConnection(this, userName, (AdapterConnection)con, myinfo, true, true);
         ((JMSBaseConnection)con).setManagedConnection(mc);
         if (logWriter != null) {
            mc.setLogWriter(logWriter);
         }

         if (type == 1) {
            if (myinfo.getDestJndi() == null) {
               printInfo(logWriter, name, "Source connection created to " + this.getDestinationJNDI());
            } else {
               printInfo(logWriter, name, "Source connection created to " + myinfo.getDestJndi());
            }
         } else if (type == 2) {
            if (myinfo.getDestJndi() == null) {
               printInfo(logWriter, name, "Target connection created to " + this.getDestinationJNDI());
            } else {
               printInfo(logWriter, name, "Target connection created to " + myinfo.getDestJndi());
            }
         }

         ((AdapterConnection)con).start();
         return mc;
      } catch (JMSException var13) {
         ResourceException re = new EISSystemException("JMSException: " + var13.getMessage());
         re.setLinkedException(var13);
         throw re;
      }
   }

   public synchronized ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo info) throws ResourceException {
      PasswordCredential pc = Util.getPasswordCredential(this, subject, info);
      String name = "UNDEFINED";
      JMSConnectionRequestInfo myinfo = (JMSConnectionRequestInfo)info;
      if (myinfo != null) {
         name = myinfo.getName();
      }

      String userName = null;
      if (pc != null) {
         userName = pc.getUserName();
      }

      Iterator it = connectionSet.iterator();

      while(it.hasNext()) {
         Object obj = it.next();
         if (obj instanceof JMSManagedConnection) {
            JMSManagedConnection mc = (JMSManagedConnection)obj;
            ManagedConnectionFactory mcf = mc.getManagedConnectionFactory();
            if (Util.isEqual(mc.getUserName(), userName) && mcf.equals(this) && mc.getConnectionRequestInfo().equals(info)) {
               printInfo(this.getLogWriter(), name, "(ManagedConnectionFactory) Found a matched managed connection");
               return mc;
            }
         }
      }

      return null;
   }

   public void setLogWriter(PrintWriter out) throws ResourceException {
      synchronized(this.logWriterLock) {
         this.logWriter = out;

         try {
            if (this.factory != null) {
               this.factory.setLogWriter(out);
            }
         } catch (Exception var6) {
            ResourceException rex = new ResourceException("Failed to set ManagedConnectionFactory's log writer");
            rex.setLinkedException(var6);
            throw rex;
         }

      }
   }

   public PrintWriter getLogWriter() throws ResourceException {
      synchronized(this.logWriterLock) {
         return this.logWriter;
      }
   }

   public synchronized String getConnectionURL() {
      return this.url;
   }

   public synchronized void setConnectionURL(String url) {
      if (url != null && url.length() != 0) {
         this.url = url;
      }

   }

   public synchronized String getInitialContextFactory() {
      return this.icFactory;
   }

   public synchronized void setInitialContextFactory(String icFactory) {
      this.icFactory = icFactory;
   }

   public synchronized String getConnectionFactoryJNDI() {
      return this.cfJNDI;
   }

   public synchronized void setConnectionFactoryJNDI(String jndi) {
      if (this.cfJNDI == null) {
         this.cfJNDI = jndi;
      }

   }

   public synchronized String getConnectionFactoryJNDIName() {
      return this.cfJNDI;
   }

   public synchronized void setConnectionFactoryJNDIName(String jndi) {
      this.cfJNDI = jndi;
   }

   public synchronized String getDestinationJNDI() {
      return this.destJNDI;
   }

   public synchronized void setDestinationJNDI(String jndi) {
      if (this.destJNDI == null) {
         this.destJNDI = jndi;
      }

   }

   public synchronized String getDestinationJNDIName() {
      return this.destJNDI;
   }

   public synchronized void setDestinationJNDIName(String jndi) {
      this.destJNDI = jndi;
   }

   public synchronized String getDestinationType() {
      return this.destType;
   }

   public synchronized void setDestinationType(String type) {
      this.destType = type;
   }

   public synchronized String getAdapterType() {
      return this.adapterType;
   }

   public synchronized void setAdapterType(String atype) {
      this.adapterType = atype;
   }

   private AdapterConnection createConnectionInternal(ConnectionSpec connSpec, int type) throws JMSException {
      AdapterConnection con = null;

      Exception le;
      try {
         String factoryJNDI = null;
         le = null;
         String connUrl = null;
         String destinationJNDI = null;
         String destinationType = null;
         String selector = null;
         boolean durable = false;
         String userName = null;
         String password = null;
         String classPath = null;
         String name = null;
         String clientName = null;
         boolean preserveMsgProperty = false;
         String icf;
         if (connSpec == null) {
            destinationJNDI = this.getDestinationJNDIName();
            factoryJNDI = this.getConnectionFactoryJNDIName();
            connUrl = this.getConnectionURL();
            icf = this.getInitialContextFactory();
            destinationType = this.getDestinationType();
         } else {
            if (!(connSpec instanceof JMSConnectionSpec)) {
               throw new JMSException("Illegal ConnectionSpec format");
            }

            JMSConnectionSpec spec = (JMSConnectionSpec)connSpec;
            if ((destinationJNDI = spec.getDestJndi()) == null) {
               destinationJNDI = this.getDestinationJNDIName();
            }

            if ((destinationType = spec.getDestType()) == null) {
               destinationType = this.getDestinationType();
            }

            if ((connUrl = spec.getUrl()) == null) {
               connUrl = this.getConnectionURL();
            }

            if ((icf = spec.getInitialContextFactory()) == null) {
               icf = this.getInitialContextFactory();
            }

            if ((factoryJNDI = spec.getFactoryJndi()) == null) {
               factoryJNDI = this.getConnectionFactoryJNDIName();
            }

            selector = spec.getSelector();
            name = spec.getName();
            clientName = spec.getFullName();
            durable = spec.getDurability();
            userName = spec.getUser();
            password = spec.getPassword();
            classPath = spec.getClasspath();
            preserveMsgProperty = spec.getPreserveMsgProperty();
         }

         con = new JMSBaseConnection(userName, password, this, name, clientName, connUrl, icf, factoryJNDI, destinationJNDI, destinationType, selector, durable, classPath, preserveMsgProperty);
      } catch (JMSException var18) {
         le = var18.getLinkedException();
         JMSLogger.logStackTrace(var18);
         if (le != null) {
            JMSLogger.logStackTraceLinked(le);
         }

         throw var18;
      } catch (Throwable var19) {
         JMSLogger.logStackTrace(var19);
         this.throwJMSException("Failed to get connection to an adapter", (Exception)null);
      }

      return con;
   }

   private AdapterConnection createConnection(ConnectionSpec connSpec) throws JMSException {
      return this.createConnectionInternal(connSpec, 0);
   }

   private SourceConnection createSourceConnection(ConnectionSpec connSpec) throws JMSException {
      return (SourceConnection)this.createConnectionInternal(connSpec, 1);
   }

   private TargetConnection createTargetConnection(ConnectionSpec connSpec) throws JMSException {
      return (TargetConnection)this.createConnectionInternal(connSpec, 2);
   }

   synchronized boolean isWLSAdapter() {
      return this.icFactory.equals("weblogic.jndi.WLInitialContextFactory");
   }

   private void throwJMSException(String info, Exception e) throws JMSException {
      JMSException jmse = new JMSException(info);
      if (e != null) {
         jmse.setLinkedException(e);
      }

      throw jmse;
   }

   public static synchronized void printInfo(PrintWriter out, String name, String info) {
      if (out != null) {
         out.checkError();
         long currentTime = System.currentTimeMillis();
         Date date = new Date(currentTime);
         out.println("<" + dformat.format(date) + "> Info: " + name + " : " + info);
      }

   }

   public static synchronized void printError(PrintWriter out, String name, String info) {
      if (out != null) {
         out.checkError();
         long currentTime = System.currentTimeMillis();
         Date date = new Date(currentTime);
         out.println("<" + dformat.format(date) + "> Error: " + name + " : " + info);
      }

   }

   public synchronized boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (!(obj instanceof JMSManagedConnectionFactory)) {
         return false;
      } else {
         boolean var10000;
         label76: {
            label69: {
               String v1 = ((JMSManagedConnectionFactory)obj).url;
               String v2 = this.url;
               String v3 = ((JMSManagedConnectionFactory)obj).icFactory;
               String v4 = this.icFactory;
               String v5 = ((JMSManagedConnectionFactory)obj).cfJNDI;
               String v6 = this.cfJNDI;
               String v7 = ((JMSManagedConnectionFactory)obj).destJNDI;
               String v8 = this.destJNDI;
               String v9 = ((JMSManagedConnectionFactory)obj).destType;
               String v10 = this.destType;
               String v11 = ((JMSManagedConnectionFactory)obj).adapterType;
               String v12 = this.adapterType;
               if (v1 == null) {
                  if (v2 != null) {
                     break label69;
                  }
               } else if (!v1.equals(v2)) {
                  break label69;
               }

               if (v3 == null) {
                  if (v4 != null) {
                     break label69;
                  }
               } else if (!v3.equals(v4)) {
                  break label69;
               }

               if (v5 == null) {
                  if (v6 != null) {
                     break label69;
                  }
               } else if (!v5.equals(v6)) {
                  break label69;
               }

               if (v7 == null) {
                  if (v8 != null) {
                     break label69;
                  }
               } else if (!v7.equals(v8)) {
                  break label69;
               }

               if (v9 == null) {
                  if (v10 != null) {
                     break label69;
                  }
               } else if (!v9.equals(v10)) {
                  break label69;
               }

               if (v11 == null) {
                  if (v12 == null) {
                     break label76;
                  }
               } else if (v11.equals(v12)) {
                  break label76;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public synchronized int hashCode() {
      String code = "";
      if (this.url != null) {
         code = code + this.url;
      }

      if (this.icFactory != null) {
         code = code + this.icFactory;
      }

      if (this.cfJNDI != null) {
         code = code + this.cfJNDI;
      }

      if (this.destJNDI != null) {
         code = code + this.destJNDI;
      }

      if (this.destType != null) {
         code = code + this.destType;
      }

      if (this.adapterType != null) {
         code = code + this.adapterType;
      }

      return code.hashCode();
   }
}
