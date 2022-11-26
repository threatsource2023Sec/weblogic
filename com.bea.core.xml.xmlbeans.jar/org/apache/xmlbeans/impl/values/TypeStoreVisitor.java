package org.apache.xmlbeans.impl.values;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaField;

public interface TypeStoreVisitor {
   boolean visit(QName var1);

   int get_elementflags();

   String get_default_text();

   SchemaField get_schema_field();
}
