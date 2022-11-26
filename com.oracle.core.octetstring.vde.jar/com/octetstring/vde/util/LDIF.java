package com.octetstring.vde.util;

import com.asn1c.core.Int8;
import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Attribute;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.acl.ACLChecker;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.backend.Mapper;
import com.octetstring.vde.operation.LDAPResult;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.InitSchema;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class LDIF {
   public static final String UTF8_ENCODING = "UTF-8";
   private static final DirectoryString DN_ATTR = new DirectoryString("dn");
   private static final String OVERWRITE_CONSTRAINT = "overwrite";
   private static final String OVERWRITE_CONSTRAINT_VALUE = "true";
   private static final String UNIQUEMEMBER_ATTR = "uniquemember";
   private static final String[] ENTRY_ATTRIBUTE_TYPE = new String[]{"memberURL", "description", "objectclass", "cn", "createTimestamp", "creatorsName", "uniquemember", "modifyTimeStamp", "modifiersName"};

   public static void main(String[] args) {
      try {
         ServerConfig.getInstance().init();
      } catch (IOException var2) {
         System.out.println("Error initializing " + var2);
         System.exit(-1);
      }

      (new InitSchema()).init();
      ACLChecker.getInstance().initialize();
      BackendHandler.getInstance();
      (new LDIF()).importLDIF(args[0]);
      System.exit(0);
   }

   public synchronized void exportLDIF(String base, String filename) {
      this.exportLDIF(base, (String)null, filename, (String[])null, (EncryptionHelper)null, (ErrorCollection)null);
   }

   public synchronized boolean exportLDIF(String base, String search, String filename, String[] encryptedAttribs, EncryptionHelper encrypter, ErrorCollection errors) {
      return this.exportLDIF(base, search, filename, encryptedAttribs, encrypter, errors, false);
   }

   public synchronized boolean exportLDIF(String base, String search, String filename, String[] encryptedAttribs, EncryptionHelper encrypter, ErrorCollection errors, boolean ignoreGuid) {
      Credentials creds = new Credentials();
      creds.setUser(new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser")));
      creds.setRoot(true);
      new Mapper();
      String filter = "(objectclass=*)";
      boolean searchSucceeded = false;
      if (search != null) {
         filter = "(&" + filter + search + ")";
      }

      Vector returnattrs = new Vector();
      Vector entrysets = null;
      DirectoryString realbase = new DirectoryString(base);
      Filter tmpfilter = null;

      try {
         tmpfilter = ParseFilter.parse(filter);
      } catch (DirectoryException var27) {
         if (errors != null) {
            IOException ioe = new IOException(Messages.getString("Bad_LDAP_Filter___3") + filter);
            errors.add(ioe);
         } else {
            Logger.getInstance().log(0, this, Messages.getString("Bad_LDAP_Filter___3") + filter);
         }

         return false;
      }

      int realscope = 2;
      Logger.getInstance().log(5, this, Messages.getString("Exporting____4") + base + Messages.getString("___branch_to_LDIF___5") + filename);

      try {
         entrysets = BackendHandler.getInstance().get(creds.getUser(), realbase, realscope, tmpfilter, false, returnattrs);
      } catch (DirectoryException var24) {
         if (errors != null) {
            EOFException ioe = new EOFException(Messages.getString("Failed_LDAP_Search___6") + var24.getMessage());
            errors.add(ioe);
         } else {
            Logger.getInstance().log(0, this, Messages.getString("Failed_LDAP_Search___6") + var24.getMessage());
         }

         return false;
      }

      boolean haveentry = false;
      boolean hasmore = true;
      Enumeration entsetenum = entrysets.elements();
      EntrySet myEntrySet = null;
      PrintWriter pw = null;

      try {
         pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
      } catch (IOException var26) {
         if (errors != null) {
            errors.add(var26);
         } else {
            Logger.getInstance().log(0, this, Messages.getString("Error_opening____7") + filename + Messages.getString("___for_writing___8") + var26.getMessage());
         }

         return false;
      }

      while(hasmore) {
         if (myEntrySet != null && !myEntrySet.hasMore()) {
            haveentry = false;
         }

         while(!haveentry) {
            if (entsetenum.hasMoreElements()) {
               myEntrySet = (EntrySet)entsetenum.nextElement();
               if (myEntrySet.hasMore()) {
                  haveentry = true;
               }
            } else {
               hasmore = false;
               haveentry = true;
            }
         }

         if (hasmore) {
            try {
               Entry myEntry = myEntrySet.getNext();
               if (myEntry != null) {
                  pw.println("dn: " + myEntry.getName());
                  pw.println(myEntry.toLDIF(encryptedAttribs, encrypter, ignoreGuid));
                  searchSucceeded = true;
               }
            } catch (DirectoryException var25) {
               if (errors != null) {
                  IOException ioe = new IOException(Messages.getString("Database_Export_Failed___10") + var25.getMessage());
                  errors.add(ioe);
               } else {
                  Logger.getInstance().log(0, this, Messages.getString("Database_Export_Failed___10") + var25.getMessage());
               }

               pw.flush();
               pw.close();
               return false;
            }
         }
      }

      pw.flush();
      pw.close();
      Logger.getInstance().log(5, this, Messages.getString("Database_Export_to_LDIF_Complete_11"));
      if (search != null && !searchSucceeded) {
         if (errors != null) {
            EOFException ioe = new EOFException(Messages.getString("Failed_LDAP_Search___6") + Messages.getString("Search_filter_yielded_no_data"));
            errors.add(ioe);
         } else {
            Logger.getInstance().log(0, this, Messages.getString("Failed_LDAP_Search___6") + Messages.getString("Search_filter_yielded_no_data"));
         }

         return false;
      } else {
         return true;
      }
   }

   public synchronized void importLDIF(String filename) {
      this.importLDIF(filename, (InputStream)null, false, (String[])null, (EncryptionHelper)null, (ErrorCollection)null, (DuplicateEntryCollection)null);
   }

   public synchronized boolean importLDIF(String filename, InputStream inputStream, boolean ignoreDuplicateErrors) {
      return this.importLDIF(filename, inputStream, ignoreDuplicateErrors, (String[])null, (EncryptionHelper)null, (ErrorCollection)null, (DuplicateEntryCollection)null);
   }

   public synchronized boolean importLDIF(String filename, InputStream inputStream, boolean ignoreDuplicateErrors, String[] encryptedAttribs, EncryptionHelper encrypter, ErrorCollection errors, DuplicateEntryCollection duplicates) {
      return this.importLDIF(filename, inputStream, ignoreDuplicateErrors, encryptedAttribs, encrypter, errors, duplicates, (Properties)null);
   }

   public synchronized boolean importLDIF(String filename, InputStream inputStream, boolean ignoreDuplicateErrors, String[] encryptedAttribs, EncryptionHelper encrypter, ErrorCollection errors, DuplicateEntryCollection duplicates, Properties constraints) {
      boolean success = true;
      if (inputStream == null) {
         Logger.getInstance().log(5, this, Messages.getString("Importing_from_LDIF___12") + filename);
      } else {
         Logger.getInstance().log(5, this, Messages.getString("Importing_from_LDIF___12") + " inputstream");
      }

      Credentials creds = new Credentials();
      creds.setUser(new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser")));
      creds.setRoot(true);

      try {
         BufferedReader br = null;
         if (inputStream == null) {
            if (isValidUTF8(filename)) {
               br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename)), "UTF-8"));
            } else {
               br = new BufferedReader(new FileReader(filename));
            }
         } else {
            br = new BufferedReader(new InputStreamReader(inputStream));
         }

         String line = br.readLine();

         StringBuffer curentry;
         Entry ent;
         Int8 lr;
         IOException ioe;
         for(curentry = new StringBuffer(); line != null; line = br.readLine()) {
            Logger.getInstance().log(5, this, "Read import line: " + line);
            if (line.length() != 0) {
               curentry.append(line);
               curentry.append("\n");
            } else {
               ent = toEntry(curentry, encryptedAttribs, encrypter);
               if (ent.getName() != null) {
                  try {
                     lr = BackendHandler.getInstance().add(creds, ent);
                     if (lr != LDAPResult.SUCCESS && (lr != LDAPResult.ENTRY_ALREADY_EXISTS || !ignoreDuplicateErrors)) {
                        if (duplicates != null && lr == LDAPResult.ENTRY_ALREADY_EXISTS) {
                           boolean overwrite = false;
                           String attributeType = null;
                           if (constraints != null && !constraints.isEmpty()) {
                              String overwriteValue = constraints.getProperty("overwrite");
                              if (overwriteValue != null && "true".equals(overwriteValue)) {
                                 overwrite = true;
                              }

                              if (overwrite) {
                                 String[] var19 = ENTRY_ATTRIBUTE_TYPE;
                                 int var20 = var19.length;

                                 for(int var21 = 0; var21 < var20; ++var21) {
                                    String name = var19[var21];
                                    if (constraints.containsKey(name)) {
                                       attributeType = name;
                                       this.modifyAttributesOfEntry(creds, ent, name);
                                    }
                                 }

                                 if (attributeType == null) {
                                    this.modifyAttributesOfEntry(creds, ent, "uniquemember");
                                 }
                              }
                           }

                           duplicates.add(ent.getName().toString());
                        } else if (errors != null) {
                           ioe = new IOException(Messages.getString("Error_adding_entry___13") + ent.getName());
                           errors.add(ioe);
                        } else {
                           Logger.getInstance().log(0, this, Messages.getString("Error_adding_entry___13") + ent.getName());
                        }

                        success = false;
                     }
                  } catch (DirectorySchemaViolation var24) {
                     if (errors != null) {
                        ioe = new IOException(Messages.getString("Schema_Violation_adding____14") + ent.getName() + "': " + var24.getMessage());
                        errors.add(ioe);
                     } else {
                        Logger.getInstance().log(0, this, Messages.getString("Schema_Violation_adding____14") + ent.getName() + "': " + var24.getMessage());
                     }

                     success = false;
                  } catch (DirectoryException var25) {
                     if (errors != null) {
                        ioe = new IOException(Messages.getString("Error_modifying_entry") + ent.getName() + "': " + var25.getMessage());
                        errors.add(ioe);
                     } else {
                        Logger.getInstance().log(0, this, Messages.getString("Error_modifying_entry") + ent.getName() + "': " + var25.getMessage());
                     }

                     success = false;
                  }
               }

               curentry = new StringBuffer();
            }
         }

         if (curentry.length() > 0) {
            ent = toEntry(curentry, encryptedAttribs, encrypter);
            if (ent.getName() != null) {
               try {
                  lr = BackendHandler.getInstance().add(creds, ent);
                  if (lr != LDAPResult.SUCCESS && (lr != LDAPResult.ENTRY_ALREADY_EXISTS || !ignoreDuplicateErrors)) {
                     if (duplicates != null && lr == LDAPResult.ENTRY_ALREADY_EXISTS) {
                        duplicates.add(ent.getName().toString());
                     } else if (errors != null) {
                        ioe = new IOException(Messages.getString("Error_adding_entry___17") + ent.getName());
                        errors.add(ioe);
                     } else {
                        Logger.getInstance().log(0, this, Messages.getString("Error_adding_entry___17") + ent.getName());
                     }

                     success = false;
                  }
               } catch (DirectorySchemaViolation var23) {
                  if (errors != null) {
                     ioe = new IOException(Messages.getString("Schema_Violation_adding____18") + ent.getName() + "': " + var23.getMessage());
                     errors.add(ioe);
                  } else {
                     Logger.getInstance().log(0, this, Messages.getString("Schema_Violation_adding____18") + ent.getName() + "': " + var23.getMessage());
                  }

                  success = false;
               }
            }
         }

         br.close();
      } catch (IOException var26) {
         if (errors != null) {
            errors.add(var26);
         } else {
            Logger.getInstance().log(0, this, Messages.getString("Error_reading_input___20") + var26.getMessage());
         }

         success = false;
      }

      Logger.getInstance().log(5, this, Messages.getString("Finished_Importing_from_LDIF_21"));
      return success;
   }

   private void modifyAttributesOfEntry(Credentials creds, Entry ent, String attributeType) throws DirectoryException {
      Logger.getInstance().log(5, this, Messages.getString("Begin_modifying_the_attribute") + attributeType + Messages.getString("Of_the_entry") + ent.getName());
      DirectoryString ds = new DirectoryString(attributeType);
      if (ent.get(ds) != null) {
         Vector modValues = new Vector();
         Iterator it = ent.getAttributes().iterator();
         Vector changeVector = new Vector();

         label36:
         while(true) {
            Attribute attribute;
            do {
               if (!it.hasNext()) {
                  EntryChange oneChange = new EntryChange(2, ds, modValues);
                  changeVector.addElement(oneChange);
                  BackendHandler.getInstance().modify(creds, ent.getName(), changeVector);
                  break label36;
               }

               attribute = (Attribute)it.next();
            } while(!attribute.type.toString().equals(attributeType));

            Iterator enumVals = attribute.values.iterator();

            while(enumVals.hasNext()) {
               byte[] thisVal = ((DirectoryString)enumVals.next()).getDirectoryBytes();
               if (thisVal.length > 0) {
                  try {
                     Syntax modValue = (Syntax)DirectoryString.class.newInstance();
                     modValue.setValue(thisVal);
                     modValues.addElement(modValue);
                  } catch (InstantiationException var12) {
                     Logger.getInstance().printStackTrace(var12);
                  } catch (IllegalAccessException var13) {
                     Logger.getInstance().printStackTrace(var13);
                  }
               }
            }
         }
      }

      Logger.getInstance().log(5, this, Messages.getString("End_modifying_the_attribute") + attributeType + Messages.getString("Of_the_entry") + ent.getName());
   }

   public static Entry toEntry(StringBuffer sb) {
      return toEntry(sb, (String[])null, (EncryptionHelper)null);
   }

   public static Entry toEntry(StringBuffer sb, String[] encryptedAttribs, EncryptionHelper encrypter) {
      Entry entry = new Entry();
      int len = sb.length();
      char[] ic = new char[len];
      sb.getChars(0, len, ic, 0);
      StringBuffer curattr = new StringBuffer();
      StringBuffer curval = new StringBuffer();
      boolean binary = false;
      boolean newline = true;
      boolean inattr = true;
      boolean lastcolon = false;
      boolean firstval = false;

      AttributeType at;
      for(int i = 0; i < len; ++i) {
         if (ic[i] != '\r' && ic[i] != '\n') {
            if (newline) {
               if (ic[i] == ' ') {
                  newline = false;
               } else {
                  if (curattr.length() > 0) {
                     DirectoryString atname = new DirectoryString(curattr.toString());
                     if (!atname.equals(DN_ATTR)) {
                        at = SchemaChecker.getInstance().getAttributeType(atname);
                        Syntax atval = null;
                        if (at == null) {
                           atval = new DirectoryString();
                        } else {
                           atval = at.getSyntaxInstance();
                        }

                        if (encryptedAttribs != null && encrypter != null) {
                           doEncryption(atname, curval, binary, (Syntax)atval, encryptedAttribs, encrypter);
                        } else if (binary) {
                           byte[] binval = Base64.decode(curval.toString().trim());
                           ((Syntax)atval).setValue(binval);
                        } else {
                           try {
                              ((Syntax)atval).setValue(curval.toString().getBytes("UTF8"));
                           } catch (UnsupportedEncodingException var21) {
                              ((Syntax)atval).setValue(curval.toString().getBytes());
                           }
                        }

                        Vector vals = entry.get(atname);
                        if (vals == null) {
                           vals = new Vector();
                           entry.put(atname, vals);
                        }

                        vals.addElement(atval);
                     } else {
                        try {
                           entry.setName(new DirectoryString(curval.toString()));
                        } catch (InvalidDNException var20) {
                           return null;
                        }
                     }

                     curattr = new StringBuffer();
                     curval = new StringBuffer();
                  }

                  newline = false;
                  inattr = true;
                  binary = false;
                  firstval = false;
                  lastcolon = false;
                  curattr.append(ic[i]);
               }
            } else if (inattr) {
               if (ic[i] == ':') {
                  if (!lastcolon) {
                     lastcolon = true;
                  }

                  inattr = false;
                  firstval = true;
               } else {
                  curattr.append(ic[i]);
               }
            } else if (ic[i] == ':' && lastcolon) {
               binary = true;
               lastcolon = false;
            } else {
               lastcolon = false;
               if (firstval) {
                  if (ic[i] != ' ') {
                     curval.append(ic[i]);
                  }

                  firstval = false;
               } else {
                  curval.append(ic[i]);
               }
            }
         } else {
            newline = true;
         }
      }

      if (curattr.length() > 0) {
         DirectoryString atname = new DirectoryString(curattr.toString());
         if (!atname.equals(DN_ATTR)) {
            AttributeType at = SchemaChecker.getInstance().getAttributeType(atname);
            at = null;
            Object atval;
            if (at == null) {
               atval = new DirectoryString();
            } else {
               atval = at.getSyntaxInstance();
            }

            if (encryptedAttribs != null && encrypter != null) {
               doEncryption(atname, curval, binary, (Syntax)atval, encryptedAttribs, encrypter);
            } else if (binary) {
               byte[] binval = Base64.decode(curval.toString().trim());
               ((Syntax)atval).setValue(binval);
            } else {
               try {
                  ((Syntax)atval).setValue(curval.toString().getBytes("UTF8"));
               } catch (UnsupportedEncodingException var19) {
                  ((Syntax)atval).setValue(curval.toString().getBytes());
               }
            }

            Vector vals = entry.get(atname);
            if (vals == null) {
               vals = new Vector();
               entry.put(atname, vals);
            }

            vals.addElement(atval);
         } else {
            try {
               entry.setName(new DirectoryString(curval.toString()));
            } catch (InvalidDNException var18) {
               return null;
            }
         }
      }

      return entry;
   }

   private static boolean isValidUTF8(String filename) {
      FileInputStream fileInputStream = null;
      File file = new File(filename);
      byte[] bFile = new byte[(int)file.length()];

      try {
         fileInputStream = new FileInputStream(file);
         fileInputStream.read(bFile);
         fileInputStream.close();
         ((Charset)Charset.availableCharsets().get("UTF-8")).newDecoder().decode(ByteBuffer.wrap(bFile));
         return true;
      } catch (CharacterCodingException var5) {
         System.out.println("UTF-8 validation fails: " + var5.getMessage());
         return false;
      } catch (Exception var6) {
         System.out.println("Error processing import file: " + var6.getMessage());
         return false;
      }
   }

   private static void doEncryption(DirectoryString atname, StringBuffer curval, boolean binary, Syntax atval, String[] encryptedAttribs, EncryptionHelper encrypter) {
      boolean doEncrypt = false;
      String attrName = atname.getDirectoryString();

      for(int i = 0; i < encryptedAttribs.length; ++i) {
         if (encryptedAttribs[i].equals(attrName)) {
            doEncrypt = true;
            break;
         }
      }

      byte[] byteval;
      if (binary) {
         byteval = Base64.decode(curval.toString().trim());
      } else {
         try {
            byteval = curval.toString().getBytes("UTF8");
         } catch (UnsupportedEncodingException var13) {
            byteval = curval.toString().getBytes();
         }
      }

      if (doEncrypt) {
         String strval;
         try {
            strval = new String(byteval, "UTF8");
         } catch (UnsupportedEncodingException var12) {
            strval = new String(byteval);
         }

         if (!encrypter.isEncrypted(strval)) {
            try {
               byteval = encrypter.encrypt(strval).getBytes("UTF8");
            } catch (UnsupportedEncodingException var11) {
               byteval = encrypter.encrypt(strval).getBytes();
            }
         }
      }

      atval.setValue(byteval);
   }
}
