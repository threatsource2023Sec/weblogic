package weblogic.servlet.internal;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.DescriptorCache;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.utils.ResourceLocation;

public class TldCacheHelper {
   private static final String IMPLICIT_TLD = "implicit.tld";
   private static final String TLD_CACHE_DIR = ".tld_cache";
   public static final String LISTENER_CLASS = "listener-class";
   public static final String TAG_CLASS = "tag-class";

   public static Map parseTagLibraries(Collection tldURIs, String tempDir, String logCtx) {
      return parseTagLibraries(tldURIs, new File(tempDir), logCtx);
   }

   public static Map parseTagLibraries(Collection tldURIs, File tempDir, String logCtx) {
      if (tldURIs != null && tldURIs.size() != 0) {
         Set tagListeners = null;
         Set tagHandlers = null;
         long t = System.currentTimeMillis();
         File rootTempDir = new File(tempDir, ".tld_cache");
         rootTempDir.mkdirs();
         Iterator i = tldURIs.iterator();

         while(true) {
            ResourceLocation tld;
            do {
               if (!i.hasNext()) {
                  Map result = new HashMap(2);
                  if (tagListeners == null) {
                     tagListeners = Collections.EMPTY_SET;
                  }

                  if (tagHandlers == null) {
                     tagHandlers = Collections.EMPTY_SET;
                  }

                  result.put("listener-class", tagListeners);
                  result.put("tag-class", tagHandlers);
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("[TldCacheHelper] parseTagLibraries() took : " + (System.currentTimeMillis() - t));
                  }

                  return result;
               }

               tld = (ResourceLocation)i.next();
            } while(tld.getURI().endsWith("implicit.tld"));

            TldIOHelper helper = new TldIOHelper(tld);

            try {
               File cacheDir = new File(rootTempDir, tld.getURI().replace('\\', '/'));
               Map m = (Map)DescriptorCache.getInstance().parseXML(cacheDir, helper);
               Collection listeners = (Collection)m.get("listener-class");
               if (listeners != null && !listeners.isEmpty()) {
                  if (tagListeners == null) {
                     tagListeners = new HashSet();
                  }

                  ((Set)tagListeners).addAll(listeners);
                  if (HTTPDebugLogger.isEnabled()) {
                     Iterator iter = listeners.iterator();

                     while(iter.hasNext()) {
                        HTTPDebugLogger.debug("[TldCacheHelper] Found listener '" + iter.next() + "' in tld at " + tld.getLocation());
                     }
                  }
               }

               Collection handlers = (Collection)m.get("tag-class");
               if (handlers != null && !handlers.isEmpty()) {
                  if (tagHandlers == null) {
                     tagHandlers = new HashSet();
                  }

                  ((Set)tagHandlers).addAll(handlers);
                  if (HTTPDebugLogger.isEnabled()) {
                     Iterator iter = handlers.iterator();

                     while(iter.hasNext()) {
                        HTTPDebugLogger.debug("[TldCacheHelper] Found tag handler '" + iter.next() + "' in tld at " + tld.getLocation());
                     }
                  }
               }
            } catch (XMLStreamException var16) {
               if (logCtx != null) {
                  HTTPLogger.logListenerParseException(logCtx, tld.getLocation(), var16);
               }
            } catch (IOException var17) {
               if (logCtx != null) {
                  HTTPLogger.logListenerParseException(logCtx, tld.getLocation(), var17);
               }
            }
         }
      } else {
         return Collections.EMPTY_MAP;
      }
   }

   private static class TldIOHelper extends WebAppIOHelper {
      TldIOHelper(ResourceLocation tld) {
         super(tld);
      }

      protected Object parseXMLInternal(XMLStreamReader parser) throws IOException, XMLStreamException {
         Set listenerList = new HashSet();
         Set tagClassList = new HashSet();
         String className = null;
         boolean inListener = false;
         boolean inTagClass = false;

         for(int event = parser.next(); event != 8; event = parser.next()) {
            switch (event) {
               case 1:
                  if ("listener-class".equals(parser.getLocalName())) {
                     inListener = true;
                  } else if ("tag-class".equals(parser.getLocalName())) {
                     inTagClass = true;
                  }
                  break;
               case 2:
                  if ("listener-class".equals(parser.getLocalName())) {
                     inListener = false;
                  } else if ("tag-class".equals(parser.getLocalName())) {
                     inTagClass = false;
                  }
                  break;
               case 4:
               case 12:
                  className = parser.getText().trim();
                  if (className.length() != 0) {
                     if (inListener) {
                        listenerList.add(className);
                     }

                     if (inTagClass) {
                        tagClassList.add(className);
                     }
                  }
            }
         }

         Map map = new HashMap(2);
         map.put("listener-class", listenerList);
         map.put("tag-class", tagClassList);
         return map;
      }
   }
}
