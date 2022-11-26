package com.sun.faces.config.manager.documents;

import java.net.URI;
import org.w3c.dom.Document;

public class DocumentInfo {
   private final Document document;
   private final URI sourceURI;

   public DocumentInfo(Document document, URI sourceURL) {
      this.document = document;
      this.sourceURI = sourceURL;
   }

   public Document getDocument() {
      return this.document;
   }

   public URI getSourceURI() {
      return this.sourceURI;
   }
}
