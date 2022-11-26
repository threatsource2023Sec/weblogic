package com.octetstring.vde.schema;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DirectorySchemaViolation;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class SchemaChecker {
   private static Hashtable attributeTypes = null;
   private static Hashtable objectClasses = null;
   private static Hashtable oidmap = null;
   private static Hashtable revoidmap = null;
   private static Hashtable fullOCTable = null;
   private static SchemaChecker instance;
   private static boolean schemaCheckOn = true;
   private static String schemaFilename = null;
   private static final DirectoryString OBJECTCLASSATTR = new DirectoryString("objectclass");
   private static final DirectoryString DOT = new DirectoryString(".");

   private SchemaChecker() {
      objectClasses = new Hashtable();
      attributeTypes = new Hashtable();
      fullOCTable = new Hashtable();
      oidmap = new Hashtable();
      revoidmap = new Hashtable();
      if (ServerConfig.getInstance().get("vde.schemacheck").equals("0")) {
         schemaCheckOn = false;
      }

   }

   public static SchemaChecker getInstance() {
      if (instance == null) {
         instance = new SchemaChecker();
      }

      return instance;
   }

   public Hashtable getAttributeTypes() {
      return attributeTypes;
   }

   public Hashtable getObjectClasses() {
      return objectClasses;
   }

   public void setSchemaFilename(String filename) {
      schemaFilename = filename;
   }

   public String getSchemaFilename() {
      return schemaFilename;
   }

   public boolean isSchemaCheckOn() {
      return schemaCheckOn;
   }

   public void setSchemaCheckOn(boolean schemaCheckOn) {
      SchemaChecker.schemaCheckOn = schemaCheckOn;
   }

   public void addAttributeType(AttributeType attributeType) {
      attributeTypes.put(attributeType.getName(), attributeType);
      oidmap.put(attributeType.getOid(), attributeType.getName());
      revoidmap.put(attributeType.getName(), attributeType.getOid());
   }

   public void addObjectClass(ObjectClass objectClass) {
      objectClasses.put(objectClass.getName(), objectClass);
   }

   public void checkEntry(Entry entry) throws DirectorySchemaViolation {
      if (this.isSchemaCheckOn()) {
         Vector objectClass = entry.get(OBJECTCLASSATTR);
         if (objectClass == null) {
            throw new DirectorySchemaViolation(Messages.getString("Missing_Object_Class_4"));
         } else {
            Vector missingAttr = new Vector();
            Vector invalidAttr = new Vector();
            Enumeration ocEnum = objectClass.elements();

            label103:
            while(true) {
               ObjectClass oc;
               Vector must;
               Enumeration mustEnum;
               DirectoryString aMust;
               if (ocEnum.hasMoreElements()) {
                  DirectoryString ocname = (DirectoryString)ocEnum.nextElement();
                  if (Logger.getInstance().isLogable(9)) {
                     Logger.getInstance().log(9, this, Messages.getString("Checking_Class___5") + ocname);
                  }

                  oc = this.getObjectClass(ocname);
                  if (oc == null) {
                     Logger.getInstance().log(7, this, Messages.getString("Object_Class_Not_Found___6") + ocname);
                     throw new DirectorySchemaViolation(Messages.getString("Object_Class_Not_Found___7") + ocname);
                  }

                  must = null;
                  if (oc != null) {
                     must = oc.getMust();
                  }

                  if (must != null) {
                     mustEnum = must.elements();

                     label93:
                     while(true) {
                        do {
                           if (!mustEnum.hasMoreElements()) {
                              break label93;
                           }

                           aMust = (DirectoryString)mustEnum.nextElement();
                        } while(entry.containsKey(aMust) && !entry.get(aMust).isEmpty());

                        missingAttr.addElement(aMust);
                     }
                  }

                  while(true) {
                     if (oc == null || oc.getSuperior() == null || objectClass.contains(oc.getSuperior())) {
                        continue label103;
                     }

                     DirectoryString aSup = oc.getSuperior();
                     objectClass.addElement(aSup);
                     oc = this.getObjectClass(aSup);
                  }
               }

               ocEnum = objectClass.elements();
               boolean firstOC = true;

               label74:
               while(ocEnum.hasMoreElements()) {
                  oc = this.getObjectClass((DirectoryString)ocEnum.nextElement());
                  must = null;
                  mustEnum = null;
                  if (firstOC) {
                     mustEnum = entry.keys();
                     firstOC = false;
                  } else {
                     must = (Vector)invalidAttr.clone();
                     mustEnum = must.elements();
                  }

                  while(true) {
                     while(true) {
                        if (!mustEnum.hasMoreElements()) {
                           continue label74;
                        }

                        aMust = (DirectoryString)mustEnum.nextElement();
                        if (oc != null && (oc.getMust().contains(aMust) || oc.getMay().contains(aMust))) {
                           if (invalidAttr.contains(aMust)) {
                              invalidAttr.removeElement(aMust);
                           }
                        } else if (!invalidAttr.contains(aMust)) {
                           invalidAttr.addElement(aMust);
                        }
                     }
                  }
               }

               if (invalidAttr.isEmpty() && missingAttr.isEmpty()) {
                  return;
               }

               Logger.getInstance().log(7, this, Messages.getString("Missing_Attribues___8") + missingAttr.toString() + Messages.getString(",_Not_Allowed_Attributes___9") + invalidAttr.toString());
               throw new DirectorySchemaViolation(Messages.getString("Missing_Attributes___10") + missingAttr.toString() + Messages.getString(",_Not_Allowed_Attributes___11") + invalidAttr.toString());
            }
         }
      }
   }

   public AttributeType getAttributeType(DirectoryString attributeType) {
      if (attributeType.indexOf(DOT) == -1) {
         return (AttributeType)attributeTypes.get(attributeType);
      } else {
         DirectoryString an = (DirectoryString)oidmap.get(attributeType.toString());
         return an != null ? (AttributeType)attributeTypes.get(an) : null;
      }
   }

   public ObjectClass getObjectClass(DirectoryString objectClass) {
      if (objectClass.indexOf(DOT) == -1) {
         return (ObjectClass)objectClasses.get(objectClass);
      } else {
         DirectoryString on = (DirectoryString)oidmap.get(objectClass.toString());
         return on != null ? (ObjectClass)objectClasses.get(on) : null;
      }
   }

   public void removeAttributeType(DirectoryString name) {
      attributeTypes.remove(name);
   }

   public void removeObjectClass(DirectoryString name) {
      objectClasses.remove(name);
   }

   public DirectoryString nameFromOID(DirectoryString oid) {
      return (DirectoryString)oidmap.get(oid.toString());
   }
}
