package weblogic.xml.jaxp;

import java.util.EmptyStackException;
import java.util.ListIterator;
import java.util.Stack;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import weblogic.xml.util.XMLConstants;

public class ChainingXMLResolver implements XMLResolver, XMLConstants {
   private Stack resolvers = new Stack();
   private Stack clonedResolvers;
   private boolean clone = true;

   public void pushXMLResolver(XMLResolver er) {
      this.resolvers.push(er);
      this.clone = true;
   }

   public XMLResolver popXMLResolver() {
      XMLResolver popped = null;

      try {
         popped = (XMLResolver)this.resolvers.pop();
      } catch (EmptyStackException var3) {
      }

      this.clone = true;
      return popped;
   }

   public int getResolverCount() {
      return this.resolvers.size();
   }

   public static String getEntityDescriptor(String publicID, String systemID) {
      return getEntityDescriptor(publicID, systemID, (String)null);
   }

   public static String getEntityDescriptor(String publicID, String systemID, String root) {
      if (root == null) {
         root = "";
      }

      String descr = "publicId = " + publicID + ", systemId = " + systemID;
      if (root != null && root.length() > 0) {
         descr = descr + ", root = " + root;
      }

      return descr;
   }

   public XMLResolver peekXMLResolver() {
      return (XMLResolver)this.resolvers.peek();
   }

   public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
      Object o = null;
      if (this.clone) {
         this.clonedResolvers = (Stack)this.resolvers.clone();
      }

      ListIterator i = this.clonedResolvers.listIterator(this.resolvers.size());

      do {
         if (!i.hasPrevious()) {
            return o;
         }

         XMLResolver xr = (XMLResolver)i.previous();
         o = xr.resolveEntity(publicID, systemID, baseURI, namespace);
      } while(o == null);

      return o;
   }
}
