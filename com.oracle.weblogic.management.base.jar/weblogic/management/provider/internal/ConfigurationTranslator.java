package weblogic.management.provider.internal;

import org.w3c.dom.Document;

public interface ConfigurationTranslator {
   String sourceNamespace();

   String targetNamespace();

   void translate(Document var1);
}
