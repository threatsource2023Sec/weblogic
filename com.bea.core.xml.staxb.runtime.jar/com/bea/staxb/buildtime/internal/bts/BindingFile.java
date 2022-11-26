package com.bea.staxb.buildtime.internal.bts;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class BindingFile extends BaseBindingLoader {
   private static final long serialVersionUID = 1L;

   public void addBindingType(BindingType bType, boolean fromJavaDefault, boolean fromXmlDefault) {
      this.addBindingType(bType);
      if (fromXmlDefault) {
         if (bType.getName().getJavaName().isXmlObject()) {
            this.addXmlObjectFor(bType.getName().getXmlName(), bType.getName());
         } else {
            this.addPojoFor(bType.getName().getXmlName(), bType.getName());
         }
      }

      if (fromJavaDefault) {
         if (bType.getName().getXmlName().getComponentType() == 101) {
            this.addElementFor(bType.getName().getJavaName(), bType.getName());
         } else {
            this.addTypeFor(bType.getName().getJavaName(), bType.getName());
         }
      }

   }

   public static BindingFile forSer(InputStream ser) throws IOException, ClassNotFoundException {
      ObjectInputStream ois = new BindingFileInputStream(ser);
      Object obj = ois.readObject();
      BindingFile bf = (BindingFile)obj;
      ois.close();
      return bf;
   }
}
