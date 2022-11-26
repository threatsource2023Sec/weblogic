package org.apache.xmlbeans.impl.values;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaType;

public interface TypeStoreUser {
   void attach_store(TypeStore var1);

   SchemaType get_schema_type();

   TypeStore get_store();

   void invalidate_value();

   boolean uses_invalidate_value();

   String build_text(NamespaceManager var1);

   boolean build_nil();

   void invalidate_nilvalue();

   void invalidate_element_order();

   void validate_now();

   void disconnect_store();

   TypeStoreUser create_element_user(QName var1, QName var2);

   TypeStoreUser create_attribute_user(QName var1);

   SchemaType get_element_type(QName var1, QName var2);

   SchemaType get_attribute_type(QName var1);

   String get_default_element_text(QName var1);

   String get_default_attribute_text(QName var1);

   int get_elementflags(QName var1);

   int get_attributeflags(QName var1);

   SchemaField get_attribute_field(QName var1);

   boolean is_child_element_order_sensitive();

   QNameSet get_element_ending_delimiters(QName var1);

   TypeStoreVisitor new_visitor();
}
