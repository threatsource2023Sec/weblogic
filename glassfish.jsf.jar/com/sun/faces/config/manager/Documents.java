package com.sun.faces.config.manager;

import com.sun.faces.config.ConfigurationException;
import com.sun.faces.config.manager.documents.DocumentInfo;
import com.sun.faces.config.manager.documents.DocumentOrderingWrapper;
import com.sun.faces.config.manager.tasks.FindConfigResourceURIsTask;
import com.sun.faces.config.manager.tasks.ParseConfigResourceToDOMTask;
import com.sun.faces.spi.ConfigurationResourceProvider;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.ApplicationConfigurationPopulator;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

public class Documents {
   private static final Logger LOGGER;

   public static DocumentInfo[] getXMLDocuments(ServletContext servletContext, List providers, ExecutorService executor, boolean validating) {
      List uriTasks = new ArrayList(providers.size());
      Iterator var5 = providers.iterator();

      FutureTask uriTask;
      while(var5.hasNext()) {
         ConfigurationResourceProvider provider = (ConfigurationResourceProvider)var5.next();
         uriTask = new FutureTask(new FindConfigResourceURIsTask(provider, servletContext));
         uriTasks.add(uriTask);
         if (executor != null) {
            executor.execute(uriTask);
         } else {
            uriTask.run();
         }
      }

      List docTasks = new ArrayList(providers.size() << 1);
      Iterator var16 = uriTasks.iterator();

      while(var16.hasNext()) {
         uriTask = (FutureTask)var16.next();

         try {
            Iterator var8 = ((Collection)uriTask.get()).iterator();

            while(var8.hasNext()) {
               URI uri = (URI)var8.next();
               FutureTask docTask = new FutureTask(new ParseConfigResourceToDOMTask(servletContext, validating, uri));
               docTasks.add(docTask);
               if (executor != null) {
                  executor.execute(docTask);
               } else {
                  docTask.run();
               }
            }
         } catch (InterruptedException var13) {
         } catch (Exception var14) {
            throw new ConfigurationException(var14);
         }
      }

      List docs = new ArrayList(docTasks.size());
      Iterator var18 = docTasks.iterator();

      while(var18.hasNext()) {
         FutureTask docTask = (FutureTask)var18.next();

         try {
            docs.add(docTask.get());
         } catch (ExecutionException var11) {
            throw new ConfigurationException(var11);
         } catch (InterruptedException var12) {
         }
      }

      return (DocumentInfo[])docs.toArray(new DocumentInfo[docs.size()]);
   }

   public static List getProgrammaticDocuments(List configPopulators) throws ParserConfigurationException {
      List programmaticDocuments = new ArrayList();
      DOMImplementation domImpl = createDOMImplementation();
      Iterator var3 = configPopulators.iterator();

      while(var3.hasNext()) {
         ApplicationConfigurationPopulator populator = (ApplicationConfigurationPopulator)var3.next();
         Document facesConfigDoc = createEmptyFacesConfigDocument(domImpl);

         try {
            populator.populateApplicationConfiguration(facesConfigDoc);
            programmaticDocuments.add(new DocumentInfo(facesConfigDoc, (URI)null));
         } catch (Throwable var7) {
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "{0} thrown when invoking {1}.populateApplicationConfigurationResources: {2}", new String[]{var7.getClass().getName(), populator.getClass().getName(), var7.getMessage()});
            }
         }
      }

      return programmaticDocuments;
   }

   public static DocumentInfo[] mergeDocuments(DocumentInfo[] facesDocuments, List programmaticDocuments) {
      if (programmaticDocuments.isEmpty()) {
         return facesDocuments;
      } else if (Util.isEmpty((Object[])facesDocuments)) {
         return (DocumentInfo[])programmaticDocuments.toArray(new DocumentInfo[0]);
      } else {
         List mergedDocuments = new ArrayList(facesDocuments.length + programmaticDocuments.size());
         mergedDocuments.add(programmaticDocuments.get(0));
         mergedDocuments.addAll(Arrays.asList(facesDocuments));
         mergedDocuments.addAll(programmaticDocuments.subList(1, programmaticDocuments.size()));
         return (DocumentInfo[])mergedDocuments.toArray(new DocumentInfo[0]);
      }
   }

   public static DocumentInfo[] sortDocuments(DocumentInfo[] facesDocuments, FacesConfigInfo webInfFacesConfig) {
      int len = webInfFacesConfig.isWebInfFacesConfig() ? facesDocuments.length - 1 : facesDocuments.length;
      List absoluteOrdering = webInfFacesConfig.getAbsoluteOrdering();
      if (len <= 1) {
         return facesDocuments;
      } else {
         List list = new ArrayList();

         for(int i = 1; i < len; ++i) {
            list.add(new DocumentOrderingWrapper(facesDocuments[i]));
         }

         DocumentOrderingWrapper[] ordering = (DocumentOrderingWrapper[])list.toArray(new DocumentOrderingWrapper[list.size()]);
         if (absoluteOrdering == null) {
            DocumentOrderingWrapper.sort(ordering);

            for(int i = 1; i < len; ++i) {
               facesDocuments[i] = ordering[i - 1].getDocument();
            }

            return facesDocuments;
         } else {
            DocumentOrderingWrapper[] result = DocumentOrderingWrapper.sort(ordering, absoluteOrdering);
            DocumentInfo[] ret = new DocumentInfo[webInfFacesConfig.isWebInfFacesConfig() ? result.length + 2 : result.length + 1];

            for(int i = 1; i < len; ++i) {
               ret[i] = result[i - 1].getDocument();
            }

            ret[0] = facesDocuments[0];
            if (webInfFacesConfig.isWebInfFacesConfig()) {
               ret[ret.length - 1] = facesDocuments[facesDocuments.length - 1];
            }

            return ret;
         }
      }
   }

   private static DOMImplementation createDOMImplementation() throws ParserConfigurationException {
      DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilderFactory.setNamespaceAware(true);
      return documentBuilderFactory.newDocumentBuilder().getDOMImplementation();
   }

   private static Document createEmptyFacesConfigDocument(DOMImplementation domImpl) {
      Document document = domImpl.createDocument("http://xmlns.jcp.org/xml/ns/javaee", "faces-config", (DocumentType)null);
      Attr versionAttribute = document.createAttribute("version");
      versionAttribute.setValue("2.2");
      document.getDocumentElement().getAttributes().setNamedItem(versionAttribute);
      return document;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }
}
