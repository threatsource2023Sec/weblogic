package org.apache.xmlbeans.impl.values;

import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.common.ValidatorListener;
import org.apache.xmlbeans.impl.common.XmlLocale;

public interface TypeStore extends NamespaceManager {
   int WS_UNSPECIFIED = 0;
   int WS_PRESERVE = 1;
   int WS_REPLACE = 2;
   int WS_COLLAPSE = 3;
   int NILLABLE = 1;
   int HASDEFAULT = 2;
   int FIXED = 4;

   XmlCursor new_cursor();

   void validate(ValidatorListener var1);

   SchemaTypeLoader get_schematypeloader();

   TypeStoreUser change_type(SchemaType var1);

   TypeStoreUser substitute(QName var1, SchemaType var2);

   boolean is_attribute();

   QName get_xsi_type();

   void invalidate_text();

   String fetch_text(int var1);

   void store_text(String var1);

   String compute_default_text();

   int compute_flags();

   boolean validate_on_set();

   SchemaField get_schema_field();

   void invalidate_nil();

   boolean find_nil();

   int count_elements(QName var1);

   int count_elements(QNameSet var1);

   TypeStoreUser find_element_user(QName var1, int var2);

   TypeStoreUser find_element_user(QNameSet var1, int var2);

   void find_all_element_users(QName var1, List var2);

   void find_all_element_users(QNameSet var1, List var2);

   TypeStoreUser insert_element_user(QName var1, int var2);

   TypeStoreUser insert_element_user(QNameSet var1, QName var2, int var3);

   TypeStoreUser add_element_user(QName var1);

   void remove_element(QName var1, int var2);

   void remove_element(QNameSet var1, int var2);

   TypeStoreUser find_attribute_user(QName var1);

   TypeStoreUser add_attribute_user(QName var1);

   void remove_attribute(QName var1);

   TypeStoreUser copy_contents_from(TypeStore var1);

   TypeStoreUser copy(SchemaTypeLoader var1, SchemaType var2, XmlOptions var3);

   void array_setter(XmlObject[] var1, QName var2);

   void visit_elements(TypeStoreVisitor var1);

   XmlObject[] exec_query(String var1, XmlOptions var2) throws XmlException;

   /** @deprecated */
   Object get_root_object();

   XmlLocale get_locale();
}
