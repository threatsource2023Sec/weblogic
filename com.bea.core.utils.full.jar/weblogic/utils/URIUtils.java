package weblogic.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URIUtils {
   private static boolean isDebug = false;

   public static URI getResourceURI(URL resourceURL) throws IOException {
      String file = resourceURL.getFile();
      if ("jar".equals(resourceURL.getProtocol())) {
         file = file.substring(5);
      }

      return (new File(file)).getCanonicalFile().toURI();
   }

   public static URI getRelativeURI(URI rootURI, URI resourceURI) {
      URI relativeURI = rootURI.relativize(resourceURI);
      debug("Relativizing " + resourceURI + " to " + rootURI);
      if (relativeURI.equals(resourceURI)) {
         debug("No initial match, schemes are " + resourceURI.getScheme() + " and " + rootURI.getScheme());
         if ((resourceURI.getScheme().equals("jar") || resourceURI.getScheme().equals("zip")) && rootURI.getScheme().equals("file")) {
            debug("Schema mismatch detected");
            String resourceURIAsString = resourceURI.toString();
            String descriptorURI = resourceURIAsString.substring(resourceURIAsString.indexOf("!") + 1);
            debug("Descriptor URI is " + descriptorURI);
            String jarURIAsString = null;
            if (resourceURI.getScheme().equals("zip")) {
               jarURIAsString = "file:/" + resourceURIAsString.substring(4, resourceURIAsString.indexOf("!"));
            } else {
               jarURIAsString = resourceURIAsString.substring(4, resourceURIAsString.indexOf("!"));
            }

            debug("Jar URI is " + jarURIAsString);

            try {
               URI jarURI = new URI(jarURIAsString);
               URI relativeJarURI = rootURI.relativize(jarURI);
               debug("Relative jar URI is " + relativeJarURI.toString());
               return new URI(relativeJarURI + "!" + descriptorURI);
            } catch (URISyntaxException var8) {
               debug("Unable to relativize URI", var8);
               return relativeURI;
            }
         }
      }

      return relativeURI;
   }

   public static URI getRelativeURI(File[] rootFiles, URI resourceURI) {
      if (rootFiles != null) {
         for(int count = 0; count < rootFiles.length; ++count) {
            URI relativeURI = getRelativeURI(rootFiles[count].toURI(), resourceURI);
            if (!relativeURI.equals(resourceURI)) {
               return relativeURI;
            }
         }
      }

      return resourceURI;
   }

   private static void debug(String msg, Exception e) {
      if (isDebug) {
         System.out.println(msg);
         if (e != null) {
            e.printStackTrace();
         }
      }

   }

   private static void debug(String msg) {
      debug(msg, (Exception)null);
   }
}
