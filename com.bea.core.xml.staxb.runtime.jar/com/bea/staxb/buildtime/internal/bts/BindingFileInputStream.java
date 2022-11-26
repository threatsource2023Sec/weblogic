package com.bea.staxb.buildtime.internal.bts;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import javax.xml.namespace.QName;

class BindingFileInputStream extends ObjectInputStream {
   private static final long QNAME_VER1_SVUID = 4418622981026545151L;
   private static final long QNAME_VER2_SVUID = -9120448754896609940L;
   private static final ObjectStreamClass qsc = ObjectStreamClass.lookup(QName.class);

   BindingFileInputStream(InputStream in) throws IOException {
      super(in);
   }

   protected ObjectStreamClass readClassDescriptor() throws ClassNotFoundException, IOException {
      ObjectStreamClass osc = super.readClassDescriptor();
      if ("javax.xml.namespace.QName".equals(osc.getName())) {
         long readSVUID = osc.getSerialVersionUID();
         if (readSVUID == 4418622981026545151L || readSVUID == -9120448754896609940L) {
            return qsc;
         }
      }

      return osc;
   }
}
