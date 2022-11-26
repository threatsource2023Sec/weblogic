package com.oracle.weblogic.lifecycle.provisioning.core.util;

import com.oracle.cie.external.domain.info.DomainInfoException;
import com.oracle.cie.external.domain.info.DomainInformation;
import com.oracle.cie.external.domain.info.DomainInformationFactory;
import com.oracle.cie.external.domain.info.DomainTemplateInfo;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningComponentIdentifier;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningResource;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.core.ComponentMetadata;
import com.oracle.weblogic.lifecycle.provisioning.core.DocumentTransformingDocumentFactory;
import com.oracle.weblogic.lifecycle.provisioning.core.annotations.WebLogicDomainRootDirectory;
import com.oracle.weblogic.lifecycle.provisioning.spi.ProvisioningComponentFactory;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.xml.parsers.ParserConfigurationException;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.ServiceLocator;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
@Singleton
public class TemplateScanner implements ProvisioningComponentFactory {
   public static final String TEMPLATE_DIR_LOCATION = "com.oracle.weblogic.lifecycle.provisioning.template.dir";
   private final ServiceLocator serviceLocator;
   private final DocumentTransformingDocumentFactory documentFactory;
   private final Provider webLogicDomainRootDirectoryProvider;

   @Inject
   public TemplateScanner(ServiceLocator serviceLocator, DocumentTransformingDocumentFactory documentFactory, @Optional @WebLogicDomainRootDirectory Provider webLogicDomainRootDirectoryProvider) {
      Objects.requireNonNull(serviceLocator);
      Objects.requireNonNull(documentFactory);
      this.serviceLocator = serviceLocator;
      this.documentFactory = documentFactory;
      this.webLogicDomainRootDirectoryProvider = webLogicDomainRootDirectoryProvider;
   }

   public final Set getProvisioningComponents() throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "getProvisioningComponents";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getProvisioningComponents");
      }

      Set componentMetadatas = this.getComponentMetadata();
      Set returnValue;
      if (componentMetadatas != null && !componentMetadatas.isEmpty()) {
         Set temp = new HashSet();
         Iterator var7 = componentMetadatas.iterator();

         while(var7.hasNext()) {
            ComponentMetadata cmd = (ComponentMetadata)var7.next();
            if (cmd != null) {
               temp.add(cmd);
            }
         }

         if (temp.isEmpty()) {
            returnValue = Collections.emptySet();
         } else {
            returnValue = Collections.unmodifiableSet(temp);
         }
      } else {
         returnValue = Collections.emptySet();
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getProvisioningComponents", returnValue);
      }

      return returnValue;
   }

   /** @deprecated */
   @Deprecated
   public Set getComponentMetadata() throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "getComponentMetadata";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getComponentMetadata");
      }

      Set returnValue = new HashSet();
      Set knownComponentNames = new HashSet();
      Iterable templateUris = this.getTemplateURIs();
      if (templateUris != null) {
         Iterator var7 = templateUris.iterator();

         while(var7.hasNext()) {
            URI templateUri = (URI)var7.next();
            if (templateUri != null) {
               assert templateUri.isAbsolute();

               ComponentMetadata componentMetadata = this.describeOneTemplate(templateUri);
               if (componentMetadata != null) {
                  String name = componentMetadata.getName();
                  if (name != null && !knownComponentNames.contains(name)) {
                     returnValue.add(componentMetadata);
                     knownComponentNames.add(name);
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getComponentMetadata", returnValue);
      }

      return returnValue;
   }

   private final ComponentMetadata describeOneTemplate(URI templateUri) throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "describeOneTemplate";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "describeOneTemplate", templateUri);
      }

      Objects.requireNonNull(templateUri);
      if (!templateUri.isAbsolute()) {
         throw new IllegalArgumentException("!templateUri.isAbsolute(): " + templateUri);
      } else {
         ComponentMetadata returnValue = null;
         String path = templateUri.getPath();
         if (path != null && path.endsWith(".jar")) {
            returnValue = this.describeOneTemplate(URI.create("jar:" + templateUri + "!/"));
         } else {
            boolean create = false;
            String scheme = templateUri.getScheme();
            File templateFile;
            if ("jar".equals(scheme)) {
               create = true;
            } else if ("file".equals(scheme)) {
               templateFile = new File(templateUri);
               if (!templateFile.isDirectory()) {
                  throw new ProvisioningException(new IllegalArgumentException(templateUri.toString()));
               }

               if (!templateFile.canRead()) {
                  throw new ProvisioningException(new IllegalArgumentException("Cannot read " + templateUri.toString()));
               }

               create = true;
            }

            if (create) {
               templateFile = null;

               URI templateInfoUri;
               try {
                  templateInfoUri = new URI(templateUri.toString() + "template-info.xml");
               } catch (URISyntaxException var11) {
                  throw new ProvisioningException(var11);
               }

               returnValue = this.createProvisioningComponent(templateUri, templateInfoUri);
            }
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "describeOneTemplate", returnValue);
         }

         return returnValue;
      }
   }

   private final ComponentMetadata createProvisioningComponent(URI templateUri, URI templateInfoUri) throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "createProvisioningComponent";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "createProvisioningComponent", templateInfoUri);
      }

      Objects.requireNonNull(templateUri);
      Objects.requireNonNull(templateInfoUri);
      ComponentMetadata returnValue = null;

      assert this.documentFactory != null;

      Document document = null;

      try {
         document = this.documentFactory.getDocument(templateInfoUri, false);
      } catch (ParserConfigurationException | SAXException | IOException var25) {
         throw new ProvisioningException(var25);
      }

      if (document != null) {
         Node root = document.getDocumentElement();
         if (root != null && isExtensionTemplate(root)) {
            NamedNodeMap namedNodeMap = root.getAttributes();
            if (namedNodeMap != null) {
               Node nameNode = namedNodeMap.getNamedItem("name");
               if (nameNode != null) {
                  String name = nameNode.getNodeValue();
                  if (name != null) {
                     Node selectableNode = namedNodeMap.getNamedItem("selectable");
                     boolean selectable;
                     String version;
                     if (selectableNode == null) {
                        selectable = false;
                     } else {
                        version = selectableNode.getNodeValue();
                        if (version == null) {
                           selectable = false;
                        } else {
                           selectable = Boolean.valueOf(version);
                        }
                     }

                     Node versionNode = namedNodeMap.getNamedItem("version");
                     if (versionNode == null) {
                        version = null;
                     } else {
                        version = versionNode.getNodeValue();
                     }

                     ProvisioningComponentIdentifier id = new ProvisioningComponentIdentifier(name, version);
                     Node descriptionNode = namedNodeMap.getNamedItem("description");
                     String description;
                     if (descriptionNode == null) {
                        description = null;
                     } else {
                        description = descriptionNode.getNodeValue();
                     }

                     Iterable templateContents = this.getTemplateResources(templateUri);
                     Set provisioningResources = new HashSet();
                     if (templateContents != null) {
                        Iterator var21 = templateContents.iterator();

                        label79:
                        while(true) {
                           URI templateResource;
                           do {
                              if (!var21.hasNext()) {
                                 break label79;
                              }

                              templateResource = (URI)var21.next();
                           } while(templateResource == null);

                           Properties properties = new Properties();
                           String templateResourceString = templateResource.toString();
                           if (templateResourceString != null) {
                              if (templateResourceString.indexOf("!/_partition/") < 0 && templateResourceString.indexOf("/_partition/") < 0) {
                                 properties.setProperty("scope", "domain");
                              } else {
                                 properties.setProperty("scope", "partition");
                              }
                           }

                           provisioningResources.add(new ProvisioningResource(properties, templateResource));
                        }
                     }

                     Set affiliates = getAffiliates(id, document);
                     returnValue = new ComponentMetadata(id, description, selectable, provisioningResources, affiliates, (Set)null);
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "createProvisioningComponent", returnValue);
      }

      return returnValue;
   }

   private final Collection getTemplateResources(URI templateUri) throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "getTemplateResources";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getTemplateResources", templateUri);
      }

      Objects.requireNonNull(templateUri);
      if (!templateUri.isAbsolute()) {
         throw new IllegalArgumentException("!templateUri.isAbsolute(): " + templateUri);
      } else {
         Collection returnValue = null;
         String path = templateUri.getPath();
         if (path != null && path.endsWith(".jar")) {
            returnValue = this.getTemplateResources(URI.create("jar:" + templateUri + "!/"));
         } else {
            String scheme = templateUri.getScheme();
            if ("jar".equals(scheme)) {
               JarURLConnection templateJarUrlConnection = null;

               try {
                  URL templateJarUrl = templateUri.toURL();

                  assert templateJarUrl != null;

                  templateJarUrlConnection = (JarURLConnection)templateJarUrl.openConnection();

                  assert templateJarUrlConnection != null;
               } catch (IOException var26) {
                  throw new ProvisioningException(var26);
               }

               try {
                  JarFile jarFile = templateJarUrlConnection.getJarFile();
                  Throwable var10 = null;

                  try {
                     if (jarFile != null) {
                        Enumeration entries = jarFile.entries();
                        if (entries != null) {
                           while(entries.hasMoreElements()) {
                              JarEntry jarEntry = (JarEntry)entries.nextElement();
                              if (jarEntry != null && !jarEntry.isDirectory()) {
                                 String jarEntryName = jarEntry.getName();
                                 if (jarEntryName != null) {
                                    assert !jarEntryName.startsWith("/");

                                    if (returnValue == null) {
                                       returnValue = new ArrayList();
                                    }

                                    ((Collection)returnValue).add(new URI("jar", templateUri.getSchemeSpecificPart() + jarEntryName, (String)null));
                                 }
                              }
                           }
                        }
                     }
                  } catch (Throwable var23) {
                     var10 = var23;
                     throw var23;
                  } finally {
                     if (jarFile != null) {
                        if (var10 != null) {
                           try {
                              jarFile.close();
                           } catch (Throwable var22) {
                              var10.addSuppressed(var22);
                           }
                        } else {
                           jarFile.close();
                        }
                     }

                  }
               } catch (URISyntaxException | IOException var25) {
                  throw new ProvisioningException(var25);
               }
            } else {
               if (!"file".equals(scheme)) {
                  throw new ProvisioningException(new IllegalArgumentException("Unsupported template URI scheme: " + scheme + "; templateUri: " + templateUri.toString()));
               }

               File templateFile = new File(templateUri);
               if (!templateFile.isDirectory()) {
                  throw new ProvisioningException(new IllegalArgumentException(templateUri.toString()));
               }

               assert path.endsWith("/");

               if (!templateFile.canRead()) {
                  throw new ProvisioningException(new IllegalArgumentException("Cannot read " + templateUri.toString()));
               }

               ArrayList files = new ArrayList();
               ArrayList dirs = new ArrayList();
               dirs.add(templateFile);

               while(!dirs.isEmpty()) {
                  File dir = (File)dirs.remove(0);
                  extractFilesAndDirectories(dir, files, dirs);
               }

               if (!files.isEmpty()) {
                  Iterator var32 = files.iterator();

                  while(var32.hasNext()) {
                     File f = (File)var32.next();
                     if (f != null) {
                        if (returnValue == null) {
                           returnValue = new ArrayList();
                        }

                        ((Collection)returnValue).add(f.toURI());
                     }
                  }
               }
            }
         }

         if (returnValue != null && !((Collection)returnValue).isEmpty()) {
            returnValue = Collections.unmodifiableCollection((Collection)returnValue);
         } else {
            returnValue = Collections.emptySet();
         }

         if (logger != null && logger.isLoggable(Level.FINER)) {
            logger.exiting(className, "getTemplateResources", returnValue);
         }

         return (Collection)returnValue;
      }
   }

   static final Set getAffiliates(ProvisioningComponentIdentifier id, Document root) throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "getAffiliates";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getAffiliates", new Object[]{id, root});
      }

      Objects.requireNonNull(root);
      Set returnValue = new HashSet();
      NodeList dependencies = root.getElementsByTagNameNS("*", "dependency");
      if (dependencies != null && dependencies.getLength() > 0) {
         int dependenciesSize = dependencies.getLength();

         assert dependenciesSize > 0;

         for(int i = 0; i < dependenciesSize; ++i) {
            Node dependency = dependencies.item(i);
            if (dependency instanceof Element) {
               Element dependencyElement = (Element)dependency;

               assert "dependency".equals(dependencyElement.getTagName());

               NodeList requiresNodes = dependencyElement.getElementsByTagNameNS("*", "requires");
               if (requiresNodes != null && requiresNodes.getLength() > 0) {
                  int requiresNodesSize = requiresNodes.getLength();

                  assert requiresNodesSize > 0;

                  for(int k = 0; k < requiresNodesSize; ++k) {
                     Node requiresNode = requiresNodes.item(k);
                     if (requiresNode instanceof Element) {
                        Element requiresElement = (Element)requiresNode;

                        assert "requires".equals(requiresElement.getTagName());

                        String affiliateName = requiresElement.getAttribute("name");
                        if (affiliateName != null && !affiliateName.isEmpty()) {
                           String version = requiresElement.getAttribute("version");
                           returnValue.add(new ProvisioningComponentIdentifier(affiliateName, version));
                        }
                     }
                  }
               }
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getAffiliates", returnValue);
      }

      return returnValue;
   }

   protected Collection getTemplateURIs() throws ProvisioningException {
      String className = TemplateScanner.class.getName();
      String methodName = "getTemplateURIs";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "getTemplateURIs");
      }

      Collection returnValue = null;
      String explicitTemplateDirectory = System.getProperty("com.oracle.weblogic.lifecycle.provisioning.template.dir");
      File template;
      if (explicitTemplateDirectory == null) {
         if (this.webLogicDomainRootDirectoryProvider == null) {
            throw new ProvisioningException(new IllegalStateException("No template directory was specified via the com.oracle.weblogic.lifecycle.provisioning.template.dir system property and the WebLogic domain directory could not be determined."));
         }

         Path webLogicDomainRootDirectory = (Path)this.webLogicDomainRootDirectoryProvider.get();
         if (webLogicDomainRootDirectory == null) {
            throw new ProvisioningException(new IllegalStateException("No template directory was specified via the com.oracle.weblogic.lifecycle.provisioning.template.dir system property and the WebLogic domain directory could not be determined from its HK2 Provider."));
         }

         DomainInformation domain = null;

         try {
            domain = DomainInformationFactory.getDomainInformation(webLogicDomainRootDirectory.toString());
         } catch (DomainInfoException var12) {
            throw new ProvisioningException("Information about the current domain rooted at " + webLogicDomainRootDirectory.toString() + " could not be acquired.", var12);
         }

         assert domain != null;

         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "getTemplateURIs", "Using DomainInformation to locate domain templates: {0}", domain);
         }

         Iterable templateInfos = domain.getDomainTemplateInfo();

         assert templateInfos != null;

         Iterator var9 = templateInfos.iterator();

         while(var9.hasNext()) {
            DomainTemplateInfo templateInfo = (DomainTemplateInfo)var9.next();
            if (templateInfo != null) {
               template = templateInfo.getLocation();
               if (template != null) {
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "getTemplateURIs", "Found domain template location: {0}", template);
                  }

                  if (returnValue == null) {
                     returnValue = new ArrayList();
                  }

                  returnValue.add(template.toURI());
               }
            }
         }
      } else {
         assert explicitTemplateDirectory != null;

         File templateDirectory = new File(explicitTemplateDirectory);
         if (!templateDirectory.isDirectory()) {
            throw new ProvisioningException("The specified template directory, " + templateDirectory + ", is not a directory.");
         }

         if (logger != null && logger.isLoggable(Level.FINE)) {
            logger.logp(Level.FINE, className, "getTemplateURIs", "Using System-property-supplied template directory to locate domain templates: {0}", templateDirectory);
         }

         File[] templateFiles = templateDirectory.listFiles(new TemplateFilter());
         if (templateFiles != null && templateFiles.length > 0) {
            File[] var16 = templateFiles;
            int var17 = templateFiles.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               template = var16[var18];

               assert template != null;

               assert template.canRead();

               if (logger != null && logger.isLoggable(Level.FINE)) {
                  logger.logp(Level.FINE, className, "getTemplateURIs", "Found domain template location: {0}", template);
               }

               if (returnValue == null) {
                  returnValue = new ArrayList();
               }

               returnValue.add(template.toURI());
            }
         }
      }

      Object returnValue;
      if (returnValue != null && !returnValue.isEmpty()) {
         returnValue = Collections.unmodifiableCollection(returnValue);
      } else {
         returnValue = Collections.emptySet();
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "getTemplateURIs", returnValue);
      }

      return (Collection)returnValue;
   }

   private static final boolean isExtensionTemplate(Node root) {
      String className = TemplateScanner.class.getName();
      String methodName = "isExtensionTemplate";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "isExtensionTemplate", root);
      }

      boolean returnValue;
      if (root == null) {
         returnValue = false;
      } else {
         NamedNodeMap namedNodeMap = root.getAttributes();
         if (namedNodeMap == null) {
            returnValue = false;
         } else {
            Node node = namedNodeMap.getNamedItem("type");
            returnValue = node != null && "Extension Template".equals(node.getNodeValue());
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "isExtensionTemplate", returnValue);
      }

      return returnValue;
   }

   private static final void extractFilesAndDirectories(File root, Collection files, Collection directories) {
      Objects.requireNonNull(root);
      Objects.requireNonNull(files);
      Objects.requireNonNull(directories);
      File[] directoryContents = root.listFiles();
      if (directoryContents != null && directoryContents.length > 0) {
         File[] var4 = directoryContents;
         int var5 = directoryContents.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File file = var4[var6];
            if (file != null && file.isFile()) {
               files.add(file.getAbsoluteFile());
            } else if (file.isDirectory()) {
               directories.add(file.getAbsoluteFile());
            }
         }
      }

   }

   private static final class TemplateFilter implements FileFilter {
      private TemplateFilter() {
      }

      public final boolean accept(File template) {
         boolean returnValue = false;
         if (template != null && template.canRead()) {
            if (template.isDirectory()) {
               if ((new File(template, "template-info.xml")).isFile()) {
                  returnValue = true;
               }
            } else if (template.isFile()) {
               String templateName = template.getName();
               if (templateName != null && templateName.endsWith(".jar")) {
                  URI templateInfoUri = URI.create("jar:" + template.toURI() + "!/template-info.xml");

                  assert templateInfoUri != null;

                  try {
                     InputStream templateInfoInputStream = templateInfoUri.toURL().openStream();
                     Throwable var6 = null;

                     try {
                        assert templateInfoInputStream != null;

                        returnValue = true;
                     } catch (Throwable var16) {
                        var6 = var16;
                        throw var16;
                     } finally {
                        if (templateInfoInputStream != null) {
                           if (var6 != null) {
                              try {
                                 templateInfoInputStream.close();
                              } catch (Throwable var15) {
                                 var6.addSuppressed(var15);
                              }
                           } else {
                              templateInfoInputStream.close();
                           }
                        }

                     }
                  } catch (IOException var18) {
                  }
               }
            }
         }

         return returnValue;
      }

      // $FF: synthetic method
      TemplateFilter(Object x0) {
         this();
      }
   }

   private static final class HandlerFilter implements Filter {
      public final boolean matches(Descriptor descriptor) {
         boolean returnValue = false;
         if (descriptor != null) {
            Set qualifiers = descriptor.getQualifiers();
            if (qualifiers != null && !qualifiers.isEmpty() && qualifiers.contains(Handler.class.getName()) && qualifiers.contains(Component.class.getName())) {
               returnValue = true;
            }
         }

         return returnValue;
      }
   }
}
