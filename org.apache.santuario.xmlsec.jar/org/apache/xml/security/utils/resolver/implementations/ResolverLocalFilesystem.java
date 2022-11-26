package org.apache.xml.security.utils.resolver.implementations;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolverLocalFilesystem extends ResourceResolverSpi {
   private static final int FILE_URI_LENGTH = "file:/".length();
   private static final Logger LOG = LoggerFactory.getLogger(ResolverLocalFilesystem.class);

   public boolean engineIsThreadSafe() {
      return true;
   }

   public XMLSignatureInput engineResolveURI(ResourceResolverContext context) throws ResourceResolverException {
      try {
         URI uriNew = getNewURI(context.uriToResolve, context.baseUri);
         String fileName = translateUriToFilename(uriNew.toString());
         InputStream inputStream = Files.newInputStream(Paths.get(fileName));
         XMLSignatureInput result = new XMLSignatureInput(inputStream);
         result.setSecureValidation(context.secureValidation);
         result.setSourceURI(uriNew.toString());
         return result;
      } catch (Exception var6) {
         throw new ResourceResolverException(var6, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
      }
   }

   private static String translateUriToFilename(String uri) {
      String subStr = uri.substring(FILE_URI_LENGTH);
      if (subStr.indexOf("%20") > -1) {
         int offset = 0;
         int index = false;
         StringBuilder temp = new StringBuilder(subStr.length());

         int index;
         do {
            index = subStr.indexOf("%20", offset);
            if (index == -1) {
               temp.append(subStr.substring(offset));
            } else {
               temp.append(subStr.substring(offset, index));
               temp.append(' ');
               offset = index + 3;
            }
         } while(index != -1);

         subStr = temp.toString();
      }

      return subStr.charAt(1) == ':' ? subStr : "/" + subStr;
   }

   public boolean engineCanResolveURI(ResourceResolverContext context) {
      if (context.uriToResolve == null) {
         return false;
      } else if (!context.uriToResolve.equals("") && context.uriToResolve.charAt(0) != '#' && !context.uriToResolve.startsWith("http:")) {
         try {
            LOG.debug("I was asked whether I can resolve {}", context.uriToResolve);
            if (context.uriToResolve.startsWith("file:") || context.baseUri.startsWith("file:")) {
               LOG.debug("I state that I can resolve {}", context.uriToResolve);
               return true;
            }
         } catch (Exception var3) {
            LOG.debug(var3.getMessage(), var3);
         }

         LOG.debug("But I can't");
         return false;
      } else {
         return false;
      }
   }

   private static URI getNewURI(String uri, String baseURI) throws URISyntaxException {
      URI newUri = null;
      if (baseURI != null && !"".equals(baseURI)) {
         newUri = (new URI(baseURI)).resolve(uri);
      } else {
         newUri = new URI(uri);
      }

      if (newUri.getFragment() != null) {
         URI uriNewNoFrag = new URI(newUri.getScheme(), newUri.getSchemeSpecificPart(), (String)null);
         return uriNewNoFrag;
      } else {
         return newUri;
      }
   }
}
