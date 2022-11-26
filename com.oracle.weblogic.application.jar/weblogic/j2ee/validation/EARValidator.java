package weblogic.j2ee.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.spi.EJBValidationInfo;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.J2EEUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.wl.ApplicationEntityCacheBean;
import weblogic.j2ee.descriptor.wl.EjbBean;
import weblogic.j2ee.descriptor.wl.JDBCDataSourceBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class EARValidator {
   private static final boolean debug = false;
   ApplicationBean dd = null;
   WeblogicApplicationBean wldd = null;
   Map moduleInfos = new HashMap();
   Map name2ModuleInfos = new HashMap();

   public EARValidator(ApplicationBean dd, WeblogicApplicationBean wldd) {
      this.dd = dd;
      this.wldd = wldd;
   }

   public void addModuleValidationInfo(ModuleValidationInfo info) {
      this.moduleInfos.put(info.getURI(), info);
      if (info.getModuleName() != null) {
         this.name2ModuleInfos.put(info.getModuleName(), info);
      }

   }

   public void validate() throws ErrorCollectionException {
      Set cacheNames = null;
      if (this.wldd != null) {
         cacheNames = new HashSet();
         EjbBean wlEJB = this.wldd.getEjb();
         if (wlEJB != null) {
            ApplicationEntityCacheBean[] ec = wlEJB.getEntityCaches();

            for(int i = 0; i < ec.length; ++i) {
               cacheNames.add(ec[i].getEntityCacheName());
            }
         }
      }

      ErrorCollectionException ece = new ErrorCollectionException("");
      Iterator it = this.moduleInfos.values().iterator();

      while(it.hasNext()) {
         ModuleValidationInfo mvi = (ModuleValidationInfo)it.next();
         this.validateEJBLinks(mvi, ece);
         this.ValidateAppScopedCacheRefs(cacheNames, mvi, ece);
         this.validateJMSLinkRefs(mvi, ece);
         this.validateJDBCLinkRefs(mvi, ece);
      }

      if (!ece.isEmpty()) {
         throw ece;
      }
   }

   private void validateEJBLinks(ModuleValidationInfo mvi, ErrorCollectionException ece) {
      Collection ejbRefs = mvi.getEJBRefs();
      Iterator links = ejbRefs.iterator();

      while(links.hasNext()) {
         ModuleValidationInfo.EJBRef link = (ModuleValidationInfo.EJBRef)links.next();

         try {
            String ejbLink = link.getEJBLink();
            if (ejbLink.indexOf(35) < 0) {
               this.validateUnqualifiedEJBLink(mvi, link, ejbLink);
            } else {
               if (ejbLink.startsWith("../")) {
                  ejbLink = J2EEUtils.makePathAbsolute(ejbLink, mvi.getURI());
               }

               int delim = ejbLink.indexOf(35);
               String uri = ejbLink.substring(0, delim);
               String linkName = ejbLink.substring(delim + 1, ejbLink.length());
               ModuleValidationInfo ref = (ModuleValidationInfo)this.moduleInfos.get(uri);
               if (ref == null) {
                  Loggable l;
                  if (link.getEJBName() != null) {
                     l = J2EELogger.logInvalidEJBLinkQualificationInEJBDescriptorLoggable(link.getEJBLink(), link.getEJBRefName(), link.getEJBName(), mvi.getURI(), uri);
                     throw new ComplianceException(l.getMessage());
                  }

                  l = J2EELogger.logInvalidEJBLinkQualificationLoggable(link.getEJBLink(), link.getEJBRefName(), mvi.getURI(), uri);
                  throw new ComplianceException(l.getMessage());
               }

               EJBValidationInfo ejbInfo = ref.getEJBValidationInfo(linkName);
               Loggable l;
               if (ejbInfo == null) {
                  if (link.getEJBName() != null) {
                     l = J2EELogger.logInvalidQualifiedEJBLinkInEJBDescriptorLoggable(link.getEJBLink(), link.getEJBRefName(), link.getEJBName(), mvi.getURI(), uri, linkName);
                     throw new ComplianceException(l.getMessage());
                  }

                  l = J2EELogger.logInvalidQualifiedEJBLinkLoggable(link.getEJBLink(), link.getEJBRefName(), mvi.getURI(), uri, linkName);
                  throw new ComplianceException(l.getMessage());
               }

               if (!ejbInfo.isClientDriven()) {
                  if (link.getEJBName() != null) {
                     l = J2EELogger.logEJBLinkInEJBDescriptorPointsToInvalidBeanLoggable(link.getEJBLink(), link.getEJBRefName(), link.getEJBName(), mvi.getURI());
                     throw new ComplianceException(l.getMessage());
                  }

                  l = J2EELogger.logEJBLinkPointsToInvalidBeanLoggable(link.getEJBLink(), link.getEJBRefName(), mvi.getURI());
                  throw new ComplianceException(l.getMessage());
               }

               this.validateResolvedEJBLink(mvi, link, ejbInfo);
            }
         } catch (ComplianceException var13) {
            ece.add(var13);
         }
      }

   }

   private void validateUnqualifiedEJBLink(ModuleValidationInfo mvi, ModuleValidationInfo.EJBRef link, String ejbLink) throws ComplianceException {
      List matches = new ArrayList();
      Iterator it = this.moduleInfos.values().iterator();

      ModuleValidationInfo match;
      EJBValidationInfo ejbInfo;
      while(it.hasNext()) {
         match = (ModuleValidationInfo)it.next();
         ejbInfo = match.getEJBValidationInfo(ejbLink);
         if (ejbInfo != null && ejbInfo.isClientDriven()) {
            matches.add(match);
         }
      }

      if (matches.size() == 1) {
         match = (ModuleValidationInfo)matches.get(0);
         ejbInfo = match.getEJBValidationInfo(ejbLink);
         this.validateResolvedEJBLink(mvi, link, ejbInfo);
      } else if (matches.size() == 0) {
         Loggable l;
         if (link.getEJBName() != null) {
            l = J2EELogger.logInvalidUnqualifiedEJBLinkInEJBDescriptorLoggable(link.getEJBLink(), link.getEJBRefName(), link.getEJBName(), mvi.getURI());
            throw new ComplianceException(l.getMessage());
         } else {
            l = J2EELogger.logInvalidUnqualifiedEJBLinkLoggable(link.getEJBLink(), link.getEJBRefName(), mvi.getURI());
            throw new ComplianceException(l.getMessage());
         }
      } else {
         StringBuffer sb = new StringBuffer();

         for(Iterator mit = matches.iterator(); mit.hasNext(); sb.append(((ModuleValidationInfo)mit.next()).getURI())) {
            if (sb.length() > 0) {
               sb.append(", ");
            }
         }

         Loggable l;
         if (link.getEJBName() != null) {
            l = J2EELogger.logAmbiguousEJBLinkInEJBDescriptorLoggable(link.getEJBLink(), link.getEJBRefName(), link.getEJBName(), mvi.getURI(), sb.toString());
            throw new ComplianceException(l.getMessage());
         } else {
            l = J2EELogger.logAmbiguousEJBLinkLoggable(link.getEJBLink(), link.getEJBRefName(), mvi.getURI(), sb.toString());
            throw new ComplianceException(l.getMessage());
         }
      }
   }

   private boolean ejbImplementsBusinessInterface(String ifaceName, boolean isLocal, EJBValidationInfo ejbInfo) {
      Set businessIfaces = null;
      if (isLocal) {
         businessIfaces = ejbInfo.getBusinessLocals();
      } else {
         businessIfaces = ejbInfo.getBusinessRemotes();
      }

      Iterator it = businessIfaces.iterator();

      Class iface;
      do {
         if (!it.hasNext()) {
            return false;
         }

         iface = (Class)it.next();
      } while(!ifaceName.equals(iface.getName()));

      return true;
   }

   private void validateResolvedEJBLink(ModuleValidationInfo mvi, ModuleValidationInfo.EJBRef link, EJBValidationInfo ejbInfo) throws ComplianceException {
      this.validateEJBRefType(mvi, link, ejbInfo);
      Loggable l;
      if (link.getHomeInterfaceName() == null) {
         if (!this.ejbImplementsBusinessInterface(link.getComponentInterfaceName(), true, ejbInfo) && !this.ejbImplementsBusinessInterface(link.getComponentInterfaceName(), false, ejbInfo)) {
            l = J2EELogger.logIncorrectInterfaceForEJBAnnotationTargetLoggable(mvi.getURI(), link.getEJBLink(), link.getComponentInterfaceName(), link.getEJBRefName());
            throw new ComplianceException(l.getMessage());
         }
      } else {
         String ifaceName;
         boolean match;
         String homeIfaceName;
         if (link.isLocal()) {
            if (!ejbInfo.hasLocalClientView()) {
               if (link.getEJBName() != null) {
                  l = J2EELogger.logIncorrectInterfacesForEJBRefTypeInEJBDescriptorLoggable(link.getEJBLink(), "ejb-local-ref", link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "local");
                  throw new ComplianceException(l.getMessage());
               }

               l = J2EELogger.logIncorrectInterfacesForEJBRefTypeLoggable(link.getEJBLink(), "ejb-local-ref", link.getEJBRefName(), mvi.getURI(), "local");
               throw new ComplianceException(l.getMessage());
            }

            ifaceName = link.getComponentInterfaceName();
            if (ifaceName != null) {
               match = false;
               match = ifaceName.equals(ejbInfo.getLocalInterfaceName());
               if (!match) {
                  match = this.ejbImplementsBusinessInterface(ifaceName, true, ejbInfo);
               }

               if (!match) {
                  if (link.getEJBName() != null) {
                     J2EELogger.logIncorrectInterfaceNameForEJBRefInEJBDescriptor("ejb-local-ref", link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "local", ifaceName);
                  } else {
                     J2EELogger.logIncorrectInterfaceNameForEJBRef("ejb-local-ref", link.getEJBRefName(), mvi.getURI(), "local", ifaceName);
                  }
               }
            }

            homeIfaceName = link.getHomeInterfaceName();
            if (homeIfaceName != null && !homeIfaceName.equals(ejbInfo.getLocalHomeInterfaceName())) {
               if (link.getEJBName() != null) {
                  J2EELogger.logIncorrectInterfaceNameForEJBRefInEJBDescriptor("ejb-local-ref", link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "local-home", link.getHomeInterfaceName());
               } else {
                  J2EELogger.logIncorrectInterfaceNameForEJBRef("ejb-local-ref", link.getEJBRefName(), mvi.getURI(), "local-home", link.getHomeInterfaceName());
               }
            }
         } else {
            if (!ejbInfo.hasRemoteClientView()) {
               if (link.getEJBName() != null) {
                  l = J2EELogger.logIncorrectInterfacesForEJBRefTypeInEJBDescriptorLoggable(link.getEJBLink(), "ejb-ref", link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "remote");
                  throw new ComplianceException(l.getMessage());
               }

               l = J2EELogger.logIncorrectInterfacesForEJBRefTypeLoggable(link.getEJBLink(), "ejb-ref", link.getEJBRefName(), mvi.getURI(), "remote");
               throw new ComplianceException(l.getMessage());
            }

            ifaceName = link.getComponentInterfaceName();
            if (ifaceName != null) {
               match = false;
               match = ifaceName.equals(ejbInfo.getRemoteInterfaceName());
               if (!match) {
                  match = this.ejbImplementsBusinessInterface(ifaceName, false, ejbInfo);
               }

               if (!match) {
                  if (link.getEJBName() != null) {
                     J2EELogger.logIncorrectInterfaceNameForEJBRefInEJBDescriptor("ejb-ref", link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "remote", link.getComponentInterfaceName());
                  } else {
                     J2EELogger.logIncorrectInterfaceNameForEJBRef("ejb-ref", link.getEJBRefName(), mvi.getURI(), "remote", link.getComponentInterfaceName());
                  }
               }
            }

            homeIfaceName = link.getHomeInterfaceName();
            if (homeIfaceName != null && !homeIfaceName.equals(ejbInfo.getHomeInterfaceName())) {
               if (link.getEJBName() != null) {
                  J2EELogger.logIncorrectInterfaceNameForEJBRefInEJBDescriptor("ejb-ref", link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "home", link.getHomeInterfaceName());
               } else {
                  J2EELogger.logIncorrectInterfaceNameForEJBRef("ejb-ref", link.getEJBRefName(), mvi.getURI(), "home", link.getHomeInterfaceName());
               }
            }
         }

      }
   }

   private void validateEJBRefType(ModuleValidationInfo mvi, ModuleValidationInfo.EJBRef link, EJBValidationInfo ejbInfo) {
      String refType = link.getRefType();
      String refElement;
      if ("Session".equalsIgnoreCase(refType)) {
         if (!ejbInfo.isSessionBean()) {
            refElement = "ejb-local-ref";
            if (!link.isLocal()) {
               refElement = "ejb-ref";
            }

            if (link.getEJBName() != null) {
               J2EELogger.logIncorrectRefTypeForEJBRefInEJBDescriptor(refElement, link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "Session");
            } else {
               J2EELogger.logIncorrectRefTypeForEJBRef(refElement, link.getEJBRefName(), mvi.getURI(), "Session");
            }
         }
      } else if ("Entity".equalsIgnoreCase(refType) && !ejbInfo.isEntityBean()) {
         refElement = "ejb-local-ref";
         if (!link.isLocal()) {
            refElement = "ejb-ref";
         }

         if (link.getEJBName() != null) {
            J2EELogger.logIncorrectRefTypeForEJBRefInEJBDescriptor(refElement, link.getEJBRefName(), link.getEJBName(), mvi.getURI(), "Entity");
         } else {
            J2EELogger.logIncorrectRefTypeForEJBRef(refElement, link.getEJBRefName(), mvi.getURI(), "Entity");
         }
      }

   }

   private void ValidateAppScopedCacheRefs(Collection cacheNames, ModuleValidationInfo mvi, ErrorCollectionException ece) {
      Map cacheRefs = mvi.getAppScopedCacheReferences();
      if (cacheRefs != null) {
         Iterator it = cacheRefs.keySet().iterator();

         while(it.hasNext()) {
            String cacheName = (String)it.next();
            Loggable l;
            if (cacheNames != null) {
               if (!cacheNames.contains(cacheName)) {
                  l = J2EELogger.logInvalidEntityCacheRefDeclaredLoggable((String)cacheRefs.get(cacheName), mvi.getURI(), cacheName);
                  ece.add(new ComplianceException(l.getMessage()));
               }
            } else {
               l = J2EELogger.logInvalidEntityCacheRefDeclaredLoggable((String)cacheRefs.get(cacheName), mvi.getURI(), cacheName);
               ece.add(new ComplianceException(l.getMessage()));
            }
         }
      }

   }

   private void validateJMSLinkRefs(ModuleValidationInfo mvi, ErrorCollectionException ece) {
      Collection jmsLinkRefs = mvi.getJMSLinkRefs();
      Iterator jmsResLinks = jmsLinkRefs.iterator();

      while(true) {
         while(true) {
            String componentName;
            String componentType;
            String resRefType;
            String resLinkName;
            do {
               if (!jmsResLinks.hasNext()) {
                  return;
               }

               ModuleValidationInfo.JLinkRef link = (ModuleValidationInfo.JLinkRef)jmsResLinks.next();
               componentName = link.getAppComponentName();
               componentType = link.getAppComponentType();
               String resRefName = link.getResRefName();
               resRefType = link.getResRefType();
               resLinkName = link.getResLinkName();
            } while(resLinkName.indexOf(35) <= 0);

            int delim = resLinkName.indexOf(35);
            String uri = resLinkName.substring(0, delim);
            String resourceName = resLinkName.substring(delim + 1, resLinkName.length());
            ModuleValidationInfo ref = (ModuleValidationInfo)this.name2ModuleInfos.get(uri);
            if (ref == null) {
               Loggable l = J2EELogger.logInvalidJMSResourceLinkInJ2EEComponentLoggable(componentName, componentType, uri, resLinkName);
               ece.add(new ComplianceException(l.getMessage()));
            } else {
               JMSBean jmsBean = ref.getJMSBean();
               Loggable l;
               if (jmsBean == null) {
                  l = J2EELogger.logInvalidJMSResourceLinkInJ2EEComponentLoggable(componentName, componentType, uri, resLinkName);
                  ece.add(new ComplianceException(l.getMessage()));
               } else if (resRefType.equals("javax.jms.Queue")) {
                  if (jmsBean.lookupQueue(resourceName) == null && jmsBean.lookupDistributedQueue(resourceName) == null && jmsBean.lookupUniformDistributedQueue(resourceName) == null) {
                     l = J2EELogger.logJMSResourceSpecifiedInResourceLinkNotFoundLoggable(componentName, componentType, uri, resLinkName, resourceName, "javax.jms.Queue");
                     ece.add(new ComplianceException(l.getMessage()));
                  }
               } else if (resRefType.equals("javax.jms.Topic")) {
                  if (jmsBean.lookupTopic(resourceName) == null && jmsBean.lookupDistributedTopic(resourceName) == null && jmsBean.lookupUniformDistributedTopic(resourceName) == null) {
                     l = J2EELogger.logJMSResourceSpecifiedInResourceLinkNotFoundLoggable(componentName, componentType, uri, resLinkName, resourceName, "javax.jms.Topic");
                     ece.add(new ComplianceException(l.getMessage()));
                  }
               } else if ((resRefType.equals("javax.jms.ConnectionFactory") || resRefType.equals("javax.jms.QueueConnectionFactory") || resRefType.equals("javax.jms.TopicConnectionFactory")) && jmsBean.lookupConnectionFactory(resourceName) == null) {
                  l = J2EELogger.logJMSResourceSpecifiedInResourceLinkNotFoundLoggable(componentName, componentType, uri, resLinkName, resourceName, "javax.jms.ConnectionFactory");
                  ece.add(new ComplianceException(l.getMessage()));
               }
            }
         }
      }
   }

   private void validateJDBCLinkRefs(ModuleValidationInfo mvi, ErrorCollectionException ece) {
      Collection JDBCLinkRefs = mvi.getJDBCLinkRefs();
      Iterator JDBCResLinks = JDBCLinkRefs.iterator();

      while(true) {
         while(true) {
            String componentName;
            String componentType;
            String resRefType;
            String resLinkName;
            do {
               if (!JDBCResLinks.hasNext()) {
                  return;
               }

               ModuleValidationInfo.JLinkRef link = (ModuleValidationInfo.JLinkRef)JDBCResLinks.next();
               componentName = link.getAppComponentName();
               componentType = link.getAppComponentType();
               String resRefName = link.getResRefName();
               resRefType = link.getResRefType();
               resLinkName = link.getResLinkName();
            } while(resLinkName.indexOf(35) <= 0);

            int delim = resLinkName.indexOf(35);
            String uri = resLinkName.substring(0, delim);
            String resourceName = resLinkName.substring(delim + 1, resLinkName.length());
            ModuleValidationInfo ref = (ModuleValidationInfo)this.name2ModuleInfos.get(uri);
            if (ref == null) {
               Loggable l = J2EELogger.logInvalidJDBCResourceLinkInJ2EEComponentLoggable(componentName, componentType, uri, resLinkName);
               ece.add(new ComplianceException(l.getMessage()));
            } else {
               JDBCDataSourceBean jdbcDataSourceBean = ref.getJDBCDataSourceBean();
               Loggable l;
               if (jdbcDataSourceBean == null) {
                  l = J2EELogger.logInvalidJDBCResourceLinkInJ2EEComponentLoggable(componentName, componentType, uri, resLinkName);
                  ece.add(new ComplianceException(l.getMessage()));
               } else if (resRefType.equals("javax.sql.DataSource") && (jdbcDataSourceBean.getName() == null || !jdbcDataSourceBean.getName().equals(resourceName))) {
                  l = J2EELogger.logJDBCResourceSpecifiedInResourceLinkNotFoundLoggable(componentName, componentType, uri, resLinkName, resourceName, "javax.sql.DataSource");
                  ece.add(new ComplianceException(l.getMessage()));
               }
            }
         }
      }
   }
}
