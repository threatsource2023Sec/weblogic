package org.apache.openjpa.jdbc.meta.strats;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.apache.openjpa.jdbc.kernel.JDBCStore;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ColumnIO;
import org.apache.openjpa.util.InternalException;

public class XMLValueHandler extends AbstractValueHandler {
   private static final String PROXY_SUFFIX = "$proxy";

   public Column[] map(ValueMapping vm, String name, ColumnIO io, boolean adapt) {
      Column col = new Column();
      col.setName(name);
      col.setJavaType(9);
      col.setSize(-1);
      col.setTypeName(vm.getMappingRepository().getDBDictionary().xmlTypeName);
      return new Column[]{col};
   }

   public Object toDataStoreValue(ValueMapping vm, Object val, JDBCStore store) {
      if (val == null) {
         return null;
      } else {
         try {
            JAXBContext jc = JAXBContext.newInstance(new Class[]{val.getClass().getName().endsWith("$proxy") ? val.getClass().getSuperclass() : val.getClass()});
            Marshaller m = jc.createMarshaller();
            Writer result = new StringWriter();
            m.marshal(val, result);
            return result.toString();
         } catch (JAXBException var7) {
            throw new InternalException(var7);
         }
      }
   }

   public Object toObjectValue(ValueMapping vm, Object val) {
      if (val == null) {
         return null;
      } else {
         try {
            String packageName = vm.getDeclaredType().getPackage().getName();
            JAXBContext jc = JAXBContext.newInstance(packageName);
            Unmarshaller u = jc.createUnmarshaller();
            return u.unmarshal(new StreamSource(new StringReader(val.toString())));
         } catch (JAXBException var6) {
            throw new InternalException(var6);
         }
      }
   }
}
