package com.bea.staxb.buildtime.internal.tylar;

import com.bea.staxb.buildtime.internal.bts.BindingFile;
import com.bea.staxb.buildtime.internal.joust.JavaOutputStream;
import com.bea.xbean.xb.xsdschema.SchemaDocument;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/** @deprecated */
@Deprecated
public class TylarImpl extends BaseTylarImpl implements Tylar, TylarWriter {
   private URL[] mLocations;
   private BindingFile mBindingFile;
   private Collection mSchemas;
   private SchemaTypeSystem mSts;

   public TylarImpl() {
      this.mBindingFile = null;
      this.mSchemas = null;
      this.mSts = null;
   }

   public TylarImpl(URL[] locations, BindingFile bf, Collection schemas, SchemaTypeSystem sts) {
      this.mBindingFile = null;
      this.mSchemas = null;
      this.mSts = null;
      this.mLocations = locations;
      this.mBindingFile = bf;
      this.mSchemas = schemas;
      this.mSts = sts;
   }

   public BindingFile[] getBindingFiles() {
      return new BindingFile[]{this.mBindingFile};
   }

   public SchemaDocument[] getSchemas() {
      if (this.mSchemas == null) {
         return new SchemaDocument[0];
      } else {
         SchemaDocument[] out = new SchemaDocument[this.mSchemas.size()];
         this.mSchemas.toArray(out);
         return out;
      }
   }

   public SchemaTypeLoader getSchemaTypeLoader() throws IOException, XmlException {
      if (this.mSts == null) {
         this.mSts = this.getDefaultSchemaTypeSystem();
      }

      return this.mSts;
   }

   public URL[] getLocations() {
      return this.mLocations;
   }

   public void writeBindingFile(BindingFile bf) {
      this.mBindingFile = bf;
   }

   public void writeSchema(SchemaDocument xsd, String path, Map tns2prefix) {
      if (this.mSchemas == null) {
         this.mSchemas = new ArrayList();
      }

      this.mSchemas.add(xsd);
   }

   public void writeSchemaTypeSystem(SchemaTypeSystem sts) {
      this.mSts = sts;
   }

   public JavaOutputStream getJavaOutputStream() {
      throw new UnsupportedOperationException("In-memory tylar does not support java code generation.");
   }

   public void close() {
   }

   /** @deprecated */
   @Deprecated
   public TylarImpl(URL[] sourceUrls, BindingFile bf, Collection schemas) {
      this(sourceUrls, bf, schemas, (SchemaTypeSystem)null);
   }
}
