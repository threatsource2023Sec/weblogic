package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.tylar.Tylar;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import java.io.IOException;

final class TylarSchemaTypeLoaderProvider implements SchemaTypeLoaderProvider {
   private final Tylar tylar;
   private SchemaTypeLoader schemaTypeLoader;

   TylarSchemaTypeLoaderProvider(Tylar tylar) {
      this.tylar = tylar;
   }

   public SchemaTypeLoader getSchemaTypeLoader() throws XmlException {
      if (this.schemaTypeLoader == null) {
         try {
            SchemaTypeLoader[] sts = new SchemaTypeLoader[]{this.tylar.getSchemaTypeLoader(), XmlBeans.getBuiltinTypeSystem()};
            this.schemaTypeLoader = XmlBeans.typeLoaderUnion(sts);
         } catch (IOException var2) {
            throw new XmlException(var2);
         } catch (XmlException var3) {
            throw new XmlException(var3);
         }
      }

      assert this.schemaTypeLoader != null;

      return this.schemaTypeLoader;
   }
}
