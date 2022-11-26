package com.bea.xbean.values;

import com.bea.xml.SchemaField;
import javax.xml.namespace.QName;

public interface TypeStoreVisitor {
   boolean visit(QName var1);

   int get_elementflags();

   String get_default_text();

   SchemaField get_schema_field();
}
