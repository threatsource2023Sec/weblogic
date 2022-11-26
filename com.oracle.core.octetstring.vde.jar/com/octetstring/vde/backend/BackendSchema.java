package com.octetstring.vde.backend;

import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.ObjectClass;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.schema.SchemaXMLWriter;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class BackendSchema extends BaseBackend {
   public BackendSchema(Hashtable config) {
      super(config);
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      return false;
   }

   public boolean doBind() {
      return false;
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean attrsOnly, Vector attrs) throws DirectoryException {
      if (scope == 0 && base.equals(new DirectoryString("cn=schema"))) {
         Vector entries = new Vector();
         entries.addElement(new Integer(1));
         return new GenericEntrySet(this, entries);
      } else {
         return new GenericEntrySet(this, new Vector());
      }
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) {
      return this.getByID(new Integer(1));
   }

   public Entry getByID(Integer id) {
      Entry schemaEntry = null;

      try {
         schemaEntry = new Entry(new DirectoryString("cn=schema"));
      } catch (InvalidDNException var11) {
      }

      Vector objClass = new Vector();
      objClass.addElement(new DirectoryString("top"));
      objClass.addElement(new DirectoryString("subschemaSubentry"));
      schemaEntry.put(new DirectoryString("objectclass"), objClass);
      Vector objClasses = new Vector();
      Hashtable classes = SchemaChecker.getInstance().getObjectClasses();
      Enumeration classEnum = classes.keys();

      while(classEnum.hasMoreElements()) {
         ObjectClass aClass = (ObjectClass)classes.get((DirectoryString)classEnum.nextElement());
         objClasses.addElement(new DirectoryString(aClass.toString()));
      }

      schemaEntry.put(new DirectoryString("objectclasses"), objClasses);
      Vector attrTypes = new Vector();
      Hashtable attrs = SchemaChecker.getInstance().getAttributeTypes();
      Enumeration attrEnum = attrs.keys();

      while(attrEnum.hasMoreElements()) {
         AttributeType at = (AttributeType)attrs.get((DirectoryString)attrEnum.nextElement());
         attrTypes.addElement(new DirectoryString(at.toString()));
      }

      schemaEntry.put(new DirectoryString("attributetypes"), attrTypes);
      return schemaEntry;
   }

   public void modify(DirectoryString binddn, DirectoryString name, Vector changeEntries) throws DirectoryException {
      Vector addocs = new Vector();
      Vector addats = new Vector();
      Vector delocs = new Vector();
      Vector delats = new Vector();
      Enumeration ecenum = changeEntries.elements();
      int ce = false;

      while(true) {
         while(true) {
            while(ecenum.hasMoreElements()) {
               EntryChange oneEc = (EntryChange)ecenum.nextElement();
               int modType = oneEc.getModType();
               int modOp = true;
               Vector vals;
               Enumeration ve;
               AttributeType at;
               ObjectClass oc;
               if (modType == 0) {
                  if (oneEc.getAttr().equals(new DirectoryString("objectclasses"))) {
                     vals = oneEc.getValues();
                     ve = vals.elements();

                     while(ve.hasMoreElements()) {
                        try {
                           oc = new ObjectClass(((Syntax)ve.nextElement()).toString());
                           if (oc == null) {
                              throw new DirectoryException(53, Messages.getString("Bad_Value_9"));
                           }

                           addocs.addElement(oc);
                        } catch (Exception var22) {
                           throw new DirectoryException(53, Messages.getString("Bad_Value_10"));
                        }
                     }
                  } else if (oneEc.getAttr().equals(new DirectoryString("attributetypes"))) {
                     vals = oneEc.getValues();
                     ve = vals.elements();

                     while(ve.hasMoreElements()) {
                        try {
                           at = new AttributeType(((Syntax)ve.nextElement()).toString());
                           if (at == null) {
                              throw new DirectoryException(53, Messages.getString("Bad_Value_12"));
                           }

                           addats.addElement(at);
                        } catch (Exception var21) {
                           throw new DirectoryException(53, Messages.getString("Bad_Value_13"));
                        }
                     }
                  }
               } else {
                  if (modType == 2) {
                     throw new DirectoryException(53, Messages.getString("Replace_not_suported_on_object_14"));
                  }

                  if (modType == 1) {
                     if (oneEc.getAttr().equals(new DirectoryString("objectclasses"))) {
                        vals = oneEc.getValues();
                        ve = vals.elements();

                        while(ve.hasMoreElements()) {
                           try {
                              oc = new ObjectClass(((Syntax)ve.nextElement()).toString());
                              if (oc == null) {
                                 throw new DirectoryException(53, Messages.getString("Bad_Value_16"));
                              }

                              delocs.addElement(oc.getName());
                           } catch (Exception var20) {
                              throw new DirectoryException(53, Messages.getString("Bad_Value_17"));
                           }
                        }
                     } else if (oneEc.getAttr().equals(new DirectoryString("attributetypes"))) {
                        vals = oneEc.getValues();
                        ve = vals.elements();

                        while(ve.hasMoreElements()) {
                           try {
                              at = new AttributeType(((Syntax)ve.nextElement()).toString());
                              if (at == null) {
                                 throw new DirectoryException(53, Messages.getString("Bad_Value_19"));
                              }

                              delats.addElement(at.getName());
                           } catch (Exception var19) {
                              throw new DirectoryException(53, Messages.getString("Bad_Value_20"));
                           }
                        }
                     }
                  }
               }
            }

            synchronized(this) {
               try {
                  SchemaXMLWriter sxw = new SchemaXMLWriter();
                  String filename = SchemaChecker.getInstance().getSchemaFilename();
                  Document mydoc = sxw.load(filename);
                  Element ds = sxw.getSchemaElement(mydoc);
                  Enumeration de;
                  Hashtable athash;
                  DirectoryString delat;
                  if (!addocs.isEmpty() || !delocs.isEmpty()) {
                     athash = SchemaChecker.getInstance().getObjectClasses();
                     if (!addocs.isEmpty()) {
                        de = addocs.elements();

                        while(de.hasMoreElements()) {
                           ObjectClass aoc = (ObjectClass)de.nextElement();
                           athash.put(aoc.getName(), aoc);
                           sxw.setObjectClass(mydoc, ds, aoc);
                        }
                     }

                     if (!delocs.isEmpty()) {
                        de = delocs.elements();

                        while(de.hasMoreElements()) {
                           delat = (DirectoryString)de.nextElement();
                           athash.remove(delat);
                           sxw.deleteObjectClass(mydoc, ds, delat.toString());
                        }
                     }
                  }

                  if (!addats.isEmpty() || !delats.isEmpty()) {
                     athash = SchemaChecker.getInstance().getAttributeTypes();
                     if (!addats.isEmpty()) {
                        de = addats.elements();

                        while(de.hasMoreElements()) {
                           AttributeType aat = (AttributeType)de.nextElement();
                           athash.put(aat.getName(), aat);
                           sxw.setAttributeType(mydoc, ds, aat);
                        }
                     }

                     if (!delats.isEmpty()) {
                        de = delats.elements();

                        while(de.hasMoreElements()) {
                           delat = (DirectoryString)de.nextElement();
                           athash.remove(delat);
                           sxw.deleteAttributeType(mydoc, ds, delat.toString());
                        }
                     }
                  }

                  sxw.write(filename, mydoc);
               } catch (Exception var23) {
                  Logger.getInstance().log(0, this, Messages.getString("Failed_to_write_schema_file___21") + var23.getMessage());
                  throw new DirectoryException(53, Messages.getString("Unable_to_Modify_22"));
               }

               return;
            }
         }
      }
   }
}
