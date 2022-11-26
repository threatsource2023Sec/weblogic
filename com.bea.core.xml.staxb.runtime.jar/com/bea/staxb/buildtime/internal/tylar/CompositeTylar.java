package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CompositeTylar extends BaseTylarImpl {
   private Tylar[] mTylars;

   public CompositeTylar(Tylar[] tylars) {
      if (tylars == null) {
         throw new IllegalArgumentException("null tylars");
      } else {
         this.mTylars = tylars;
      }
   }

   public String getDescription() {
      return "CompositeTylar containing " + this.mTylars.length + " tylars";
   }

   public BindingFile[] getBindingFiles() {
      Collection all = new ArrayList();

      for(int i = 0; i < this.mTylars.length; ++i) {
         all.addAll(Arrays.asList(this.mTylars[i].getBindingFiles()));
      }

      BindingFile[] out = new BindingFile[all.size()];
      all.toArray(out);
      return out;
   }

   public SchemaDocument[] getSchemas() {
      Collection all = new ArrayList();

      for(int i = 0; i < this.mTylars.length; ++i) {
         all.addAll(Arrays.asList(this.mTylars[i].getSchemas()));
      }

      SchemaDocument[] out = new SchemaDocument[all.size()];
      all.toArray(out);
      return out;
   }

   public SchemaTypeLoader getSchemaTypeLoader() throws IOException, XmlException {
      if (this.mTylars.length == 0) {
         return XmlBeans.getBuiltinTypeSystem();
      } else if (this.mTylars.length == 1) {
         return this.mTylars[0].getSchemaTypeLoader();
      } else {
         SchemaTypeLoader[] sts = new SchemaTypeLoader[this.mTylars.length];

         for(int i = 0; i < this.mTylars.length; ++i) {
            sts[i] = this.mTylars[i].getSchemaTypeLoader();
         }

         return XmlBeans.typeLoaderUnion(sts);
      }
   }
}
