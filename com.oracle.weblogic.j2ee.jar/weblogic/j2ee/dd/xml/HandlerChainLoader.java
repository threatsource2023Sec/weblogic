package weblogic.j2ee.dd.xml;

import com.bea.xml.XmlException;
import com.sun.java.xml.ns.javaee.HandlerChainType;
import com.sun.java.xml.ns.javaee.HandlerChainsDocument;
import com.sun.java.xml.ns.javaee.HandlerChainsDocument.Factory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.jws.HandlerChain;

public class HandlerChainLoader {
   private final HandlerChainType[] handlerChains;

   public HandlerChainLoader(HandlerChain handlerChain, Class declaringClass) throws XmlException, IOException {
      this.handlerChains = this.buildHandlerChains(handlerChain, declaringClass);
   }

   private HandlerChainType[] buildHandlerChains(HandlerChain handlerChainAnn, Class declaringClass) throws XmlException, IOException {
      HandlerChainType[] handlerChainTypes = null;
      if (handlerChainAnn != null && handlerChainAnn.file() != null && handlerChainAnn.file().length() > 0) {
         URL url = this.getURL(handlerChainAnn.file(), declaringClass);
         if (url != null) {
            HandlerChainsDocument chains = Factory.parse(url);
            chains.validate();
            handlerChainTypes = chains.getHandlerChains().getHandlerChainArray();
         }
      }

      return handlerChainTypes;
   }

   private boolean isRelativeUrl(String url) {
      if (url.indexOf(58) > 0) {
         return false;
      } else {
         return !url.startsWith("/");
      }
   }

   private URL getURL(String sUrl, Class declaringClass) throws MalformedURLException {
      URL url = null;
      if (this.isRelativeUrl(sUrl)) {
         url = declaringClass.getResource(sUrl);
      } else {
         url = new URL(sUrl);
      }

      return url;
   }

   public HandlerChainType[] getHandlerChains() {
      return this.handlerChains;
   }
}
