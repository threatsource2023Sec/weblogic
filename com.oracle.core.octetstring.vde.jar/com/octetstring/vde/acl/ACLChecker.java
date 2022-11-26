package com.octetstring.vde.acl;

import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.SubstringFilter_substrings_Seq;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.BackendHandler;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.LDAPURL;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.oro.text.perl.Perl5Util;

public class ACLChecker {
   private static Hashtable entryacls = null;
   private static Hashtable subtreeacls = null;
   private static ACLChecker instance = null;
   private boolean aclCheck = true;
   private DirectoryString rootUser = null;
   private static final DirectoryString ATTR_ALL = new DirectoryString("[all]");
   private static final DirectoryString ROOTENTRY = new DirectoryString("[root]");
   private static final DirectoryString EMPTY_DS = new DirectoryString("");
   private static final DirectoryString ATTR_MEMBER = new DirectoryString("member");
   private static final DirectoryString ATTR_UNIQUEMEMBER = new DirectoryString("uniquemember");
   private static final DirectoryString ATTR_MEMBERURL = new DirectoryString("memberurl");
   public static final Character PERM_ADD = new Character('a');
   public static final Character PERM_DELETE = new Character('d');
   public static final Character PERM_EXPORT = new Character('e');
   public static final Character PERM_IMPORT = new Character('i');
   public static final Character PERM_RENAMEDN = new Character('n');
   public static final Character PERM_BROWSEDN = new Character('b');
   public static final Character PERM_RETURNDN = new Character('t');
   public static final Character PERM_READ = new Character('r');
   public static final Character PERM_SEARCH = new Character('s');
   public static final Character PERM_WRITE = new Character('w');
   public static final Character PERM_OBLITERATE = new Character('o');
   public static final Character PERM_COMPARE = new Character('c');
   public static final Character PERM_MAKE = new Character('m');

   private ACLChecker() {
      if (((String)ServerConfig.getInstance().get("vde.aclcheck")).equals("0")) {
         this.aclCheck = false;
      } else {
         this.rootUser = new DirectoryString((String)ServerConfig.getInstance().get("vde.rootuser"));
         Logger.getInstance().log(7, this, "Root User is: " + this.rootUser);
      }

   }

   public static ACLChecker getInstance() {
      if (instance == null) {
         instance = new ACLChecker();
      }

      return instance;
   }

   public void addACL(DirectoryString aclLoc, ACL acl) {
      Hashtable acls = null;
      if (acl.isScopeSubtree()) {
         acls = subtreeacls;
      } else {
         acls = entryacls;
      }

      Vector aclVec = (Vector)acls.get(aclLoc);
      if (aclVec == null) {
         aclVec = new Vector();
         acls.put(aclLoc, aclVec);
      } else if (aclVec.contains(acl)) {
         return;
      }

      aclVec.addElement(acl);
   }

   public void deleteACL(DirectoryString aclLoc) {
      boolean writeit = false;
      if (entryacls.containsKey(aclLoc)) {
         entryacls.remove(aclLoc);
         writeit = true;
      }

      if (subtreeacls.containsKey(aclLoc)) {
         subtreeacls.remove(aclLoc);
         writeit = true;
      }

   }

   public void deleteACL(DirectoryString aclLoc, ACL acl) {
      Hashtable acls = null;
      if (acl.isScopeSubtree()) {
         acls = subtreeacls;
      } else {
         acls = entryacls;
      }

      Vector aclVec = (Vector)acls.get(aclLoc);
      if (aclVec != null && aclVec.contains(acl)) {
         aclVec.removeElement(acl);
         if (aclVec.isEmpty()) {
            acls.remove(aclLoc);
         } else {
            acls.put(aclLoc, aclVec);
         }
      }

   }

   public Vector getEntryACLs(DirectoryString aclLoc) {
      Vector myAcls = (Vector)entryacls.get(aclLoc);
      return myAcls;
   }

   public Vector getSubtreeACLs(DirectoryString aclLoc) {
      Vector myAcls = (Vector)subtreeacls.get(aclLoc);
      return myAcls;
   }

   public void initialize() {
      entryacls = new Hashtable();
      subtreeacls = new Hashtable();
      BufferedReader br = null;

      String acl;
      try {
         acl = (String)ServerConfig.getInstance().get("vde.aclfile");
         String ihome = System.getProperty("vde.home");
         String fullname = null;
         File file = new File(acl);
         if (!file.isAbsolute() && ihome != null) {
            fullname = ihome + "/" + acl;
         } else {
            fullname = acl;
         }

         br = new BufferedReader(new FileReader(fullname));
      } catch (FileNotFoundException var8) {
         Logger.getInstance().log(0, this, "File containing ACLs not found");
         return;
      }

      try {
         while(br.ready()) {
            acl = br.readLine();
            if (acl != null && acl.charAt(0) != '#' && acl.indexOf(124) != -1) {
               StringTokenizer aclTok = new StringTokenizer(acl, "|");
               DirectoryString aclLoc = new DirectoryString(aclTok.nextToken());
               Logger.getInstance().log(7, this, "Found ACL for: " + aclLoc);
               if (aclLoc.equals(ROOTENTRY)) {
                  aclLoc = EMPTY_DS;
               }

               try {
                  aclLoc = DNUtility.getInstance().normalize(aclLoc);
                  StringBuffer aclSB = new StringBuffer();

                  while(aclTok.hasMoreTokens()) {
                     aclSB.append(aclTok.nextToken());
                     if (aclTok.hasMoreTokens()) {
                        aclSB.append('|');
                     }
                  }

                  this.addACL(aclLoc, new ACL(aclSB.toString()));
               } catch (InvalidDNException var6) {
                  Logger.getInstance().log(0, this, "Invalid ACL Location: " + aclLoc);
               }
            }
         }

         br.close();
      } catch (IOException var7) {
         Logger.getInstance().log(0, this, "Error reading ACL file.");
      }

   }

   public boolean isAllowed(Credentials creds, Character action, Entry targetEntry) {
      DirectoryString target = targetEntry.getName();
      boolean allowed = false;
      ACL matchedACL = null;
      DirectoryString matchedLoc = null;
      DirectoryString subject = null;
      if (!this.aclCheck) {
         return true;
      } else {
         if (creds != null) {
            subject = creds.getUser();
         } else {
            subject = EMPTY_DS;
         }

         if (creds.isRoot()) {
            return true;
         } else {
            Vector entryAcls = (Vector)entryacls.get(target);
            Enumeration eaEnum = null;
            if (entryAcls != null && !entryAcls.isEmpty()) {
               eaEnum = entryAcls.elements();
            }

            Enumeration aclEnum = subtreeacls.keys();
            DirectoryString aclLoc = null;

            label162:
            while(true) {
               Enumeration oneAclSet;
               boolean processAcl;
               do {
                  if (!aclEnum.hasMoreElements() && eaEnum == null) {
                     if (Logger.getInstance().isLogable(9)) {
                        if (allowed) {
                           Logger.getInstance().log(9, this, "allow-" + action + "--" + creds + "-" + target);
                        } else {
                           Logger.getInstance().log(9, this, "deny-" + action + "--" + creds + "-" + target);
                        }
                     }

                     return allowed;
                  }

                  oneAclSet = null;
                  processAcl = false;
                  if (eaEnum != null) {
                     aclLoc = target;
                     oneAclSet = eaEnum;
                     processAcl = true;
                  } else {
                     aclLoc = (DirectoryString)aclEnum.nextElement();
                     if (target.endsWith(aclLoc)) {
                        oneAclSet = ((Vector)subtreeacls.get(aclLoc)).elements();
                        processAcl = true;
                     }
                  }
               } while(!processAcl);

               while(true) {
                  while(true) {
                     ACL oneAcl;
                     do {
                        do {
                           do {
                              if (!oneAclSet.hasMoreElements()) {
                                 eaEnum = null;
                                 continue label162;
                              }

                              oneAcl = (ACL)oneAclSet.nextElement();
                           } while(!oneAcl.getPermission().contains(action));
                        } while(oneAcl.getSubjectType() != 2 && (oneAcl.getSubjectType() != 64 || !oneAcl.isAuthzDN() || !oneAcl.getSubject().equals(subject)) && (oneAcl.getSubjectType() != 32 || !subject.equals(target)) && (oneAcl.getSubjectType() != 4 || !subject.endsWith(oneAcl.getSubject())) && (oneAcl.getSubjectType() != 16 || !this.userInGroup(subject, oneAcl.getSubject())));
                     } while(oneAcl.getTargetFilter() != null && !this.evalFilter(targetEntry, oneAcl.getTargetFilter()));

                     if (matchedACL == null) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (!oneAcl.isScopeSubtree()) {
                        if (!matchedACL.isScopeSubtree()) {
                           if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                              if (!oneAcl.isGrant()) {
                                 allowed = false;
                              } else {
                                 allowed = true;
                              }

                              matchedACL = oneAcl;
                              matchedLoc = aclLoc;
                           } else if (oneAcl.isGrant() && matchedACL.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     } else if (matchedLoc.length() < aclLoc.length()) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (matchedLoc.length() == aclLoc.length()) {
                        if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else if (matchedACL.getSubjectType() == oneAcl.getSubjectType()) {
                           if (matchedACL.isGrant() && oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean isAllowed(Credentials creds, Character action, DirectoryString target) {
      boolean allowed = false;
      ACL matchedACL = null;
      DirectoryString matchedLoc = null;
      DirectoryString subject = null;
      if (!this.aclCheck) {
         return true;
      } else {
         if (creds != null) {
            subject = creds.getUser();
         } else {
            subject = EMPTY_DS;
         }

         if (creds.isRoot()) {
            return true;
         } else {
            Vector entryAcls = (Vector)entryacls.get(target);
            Enumeration eaEnum = null;
            if (entryAcls != null && !entryAcls.isEmpty()) {
               eaEnum = entryAcls.elements();
            }

            Enumeration aclEnum = subtreeacls.keys();
            DirectoryString aclLoc = null;

            label154:
            while(true) {
               Enumeration oneAclSet;
               boolean processAcl;
               do {
                  if (!aclEnum.hasMoreElements() && eaEnum == null) {
                     if (Logger.getInstance().isLogable(9)) {
                        if (allowed) {
                           Logger.getInstance().log(9, this, "allow-" + action + "--" + creds + "-" + target);
                        } else {
                           Logger.getInstance().log(9, this, "deny-" + action + "--" + creds + "-" + target);
                        }
                     }

                     return allowed;
                  }

                  oneAclSet = null;
                  processAcl = false;
                  if (eaEnum != null) {
                     aclLoc = target;
                     oneAclSet = eaEnum;
                     processAcl = true;
                  } else {
                     aclLoc = (DirectoryString)aclEnum.nextElement();
                     if (target.endsWith(aclLoc)) {
                        oneAclSet = ((Vector)subtreeacls.get(aclLoc)).elements();
                        processAcl = true;
                     }
                  }
               } while(!processAcl);

               while(true) {
                  while(true) {
                     ACL oneAcl;
                     do {
                        do {
                           if (!oneAclSet.hasMoreElements()) {
                              eaEnum = null;
                              continue label154;
                           }

                           oneAcl = (ACL)oneAclSet.nextElement();
                        } while(!oneAcl.getPermission().contains(action));
                     } while(oneAcl.getSubjectType() != 2 && (oneAcl.getSubjectType() != 64 || !oneAcl.isAuthzDN() || !oneAcl.getSubject().equals(subject)) && (oneAcl.getSubjectType() != 32 || !subject.equals(target)) && (oneAcl.getSubjectType() != 4 || !subject.endsWith(oneAcl.getSubject())) && (oneAcl.getSubjectType() != 16 || !this.userInGroup(subject, oneAcl.getSubject())));

                     if (matchedACL == null) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (!oneAcl.isScopeSubtree()) {
                        if (!matchedACL.isScopeSubtree()) {
                           if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                              if (!oneAcl.isGrant()) {
                                 allowed = false;
                              } else {
                                 allowed = true;
                              }

                              matchedACL = oneAcl;
                              matchedLoc = aclLoc;
                           } else if (oneAcl.isGrant() && matchedACL.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     } else if (matchedLoc.length() < aclLoc.length()) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (matchedLoc.length() == aclLoc.length()) {
                        if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else if (matchedACL.getSubjectType() == oneAcl.getSubjectType()) {
                           if (matchedACL.isGrant() && oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean isAllowed(Credentials creds, Character action, Entry targetEntry, DirectoryString attr) {
      DirectoryString target = targetEntry.getName();
      boolean allowed = false;
      ACL matchedACL = null;
      DirectoryString matchedLoc = null;
      DirectoryString subject = null;
      if (!this.aclCheck) {
         return true;
      } else {
         if (creds != null) {
            subject = creds.getUser();
         } else {
            subject = new DirectoryString("");
         }

         if (creds.isRoot()) {
            return true;
         } else {
            Vector entryAcls = (Vector)entryacls.get(target);
            Enumeration eaEnum = null;
            if (entryAcls != null && !entryAcls.isEmpty()) {
               eaEnum = entryAcls.elements();
            }

            Enumeration aclEnum = subtreeacls.keys();

            label170:
            while(true) {
               Enumeration oneAclSet;
               boolean processAcl;
               DirectoryString aclLoc;
               do {
                  if (!aclEnum.hasMoreElements() && eaEnum == null) {
                     if (Logger.getInstance().isLogable(9)) {
                        if (allowed) {
                           Logger.getInstance().log(9, this, "allow-" + action + "-" + attr + "-" + creds + "-" + target);
                        } else {
                           Logger.getInstance().log(7, this, "deny-" + action + "-" + attr + "-" + creds + "-" + target);
                        }
                     }

                     return allowed;
                  }

                  oneAclSet = null;
                  processAcl = false;
                  aclLoc = null;
                  if (eaEnum != null) {
                     aclLoc = target;
                     oneAclSet = eaEnum;
                     processAcl = true;
                  } else {
                     aclLoc = (DirectoryString)aclEnum.nextElement();
                     if (target.endsWith(aclLoc)) {
                        oneAclSet = ((Vector)subtreeacls.get(aclLoc)).elements();
                        processAcl = true;
                     }
                  }
               } while(!processAcl);

               while(true) {
                  while(true) {
                     ACL oneAcl;
                     do {
                        do {
                           do {
                              do {
                                 if (!oneAclSet.hasMoreElements()) {
                                    eaEnum = null;
                                    continue label170;
                                 }

                                 oneAcl = (ACL)oneAclSet.nextElement();
                              } while(!oneAcl.getPermission().contains(action));
                           } while(!oneAcl.getAttr().contains(attr) && !oneAcl.getAttr().contains(ATTR_ALL));
                        } while(oneAcl.getSubjectType() != 2 && (oneAcl.getSubjectType() != 64 || !oneAcl.isAuthzDN() || !oneAcl.getSubject().equals(subject)) && (oneAcl.getSubjectType() != 32 || !subject.equals(target)) && (oneAcl.getSubjectType() != 4 || !subject.endsWith(oneAcl.getSubject())) && (oneAcl.getSubjectType() != 16 || !this.userInGroup(subject, oneAcl.getSubject())));
                     } while(oneAcl.getTargetFilter() != null && !this.evalFilter(targetEntry, oneAcl.getTargetFilter()));

                     if (matchedACL == null) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (!oneAcl.isScopeSubtree()) {
                        if (!matchedACL.isScopeSubtree()) {
                           if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                              if (!oneAcl.isGrant()) {
                                 allowed = false;
                              } else {
                                 allowed = true;
                              }

                              matchedACL = oneAcl;
                              matchedLoc = aclLoc;
                           } else if (oneAcl.isGrant() && matchedACL.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     } else if (matchedLoc.length() < aclLoc.length()) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (matchedLoc.length() == aclLoc.length()) {
                        if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else if (matchedACL.getSubjectType() == oneAcl.getSubjectType()) {
                           if (matchedACL.isGrant() && oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean isAllowed(Credentials creds, Character action, DirectoryString target, DirectoryString attr) {
      boolean allowed = false;
      ACL matchedACL = null;
      DirectoryString matchedLoc = null;
      DirectoryString subject = null;
      if (!this.aclCheck) {
         return true;
      } else {
         if (creds != null) {
            subject = creds.getUser();
         } else {
            subject = new DirectoryString("");
         }

         if (creds.isRoot()) {
            return true;
         } else {
            Vector entryAcls = (Vector)entryacls.get(target);
            Enumeration eaEnum = null;
            if (entryAcls != null && !entryAcls.isEmpty()) {
               eaEnum = entryAcls.elements();
            }

            Enumeration aclEnum = subtreeacls.keys();

            label162:
            while(true) {
               Enumeration oneAclSet;
               boolean processAcl;
               DirectoryString aclLoc;
               do {
                  if (!aclEnum.hasMoreElements() && eaEnum == null) {
                     if (Logger.getInstance().isLogable(9)) {
                        if (allowed) {
                           Logger.getInstance().log(9, this, "allow-" + action + "-" + attr + "-" + creds + "-" + target);
                        } else {
                           Logger.getInstance().log(7, this, "deny-" + action + "-" + attr + "-" + creds + "-" + target);
                        }
                     }

                     return allowed;
                  }

                  oneAclSet = null;
                  processAcl = false;
                  aclLoc = null;
                  if (eaEnum != null) {
                     aclLoc = target;
                     oneAclSet = eaEnum;
                     processAcl = true;
                  } else {
                     aclLoc = (DirectoryString)aclEnum.nextElement();
                     if (target.endsWith(aclLoc)) {
                        oneAclSet = ((Vector)subtreeacls.get(aclLoc)).elements();
                        processAcl = true;
                     }
                  }
               } while(!processAcl);

               while(true) {
                  while(true) {
                     ACL oneAcl;
                     do {
                        do {
                           do {
                              if (!oneAclSet.hasMoreElements()) {
                                 eaEnum = null;
                                 continue label162;
                              }

                              oneAcl = (ACL)oneAclSet.nextElement();
                           } while(!oneAcl.getPermission().contains(action));
                        } while(!oneAcl.getAttr().contains(attr) && !oneAcl.getAttr().contains(ATTR_ALL));
                     } while(oneAcl.getSubjectType() != 2 && (oneAcl.getSubjectType() != 64 || !oneAcl.isAuthzDN() || !oneAcl.getSubject().equals(subject)) && (oneAcl.getSubjectType() != 32 || !subject.equals(target)) && (oneAcl.getSubjectType() != 4 || !subject.endsWith(oneAcl.getSubject())) && (oneAcl.getSubjectType() != 16 || !this.userInGroup(subject, oneAcl.getSubject())));

                     if (matchedACL == null) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (!oneAcl.isScopeSubtree()) {
                        if (!matchedACL.isScopeSubtree()) {
                           if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                              if (!oneAcl.isGrant()) {
                                 allowed = false;
                              } else {
                                 allowed = true;
                              }

                              matchedACL = oneAcl;
                              matchedLoc = aclLoc;
                           } else if (oneAcl.isGrant() && matchedACL.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     } else if (matchedLoc.length() < aclLoc.length()) {
                        matchedACL = oneAcl;
                        matchedLoc = aclLoc;
                        if (oneAcl.isGrant()) {
                           allowed = true;
                        } else {
                           allowed = false;
                        }
                     } else if (matchedLoc.length() == aclLoc.length()) {
                        if (matchedACL.getSubjectType() < oneAcl.getSubjectType()) {
                           matchedACL = oneAcl;
                           matchedLoc = aclLoc;
                           if (oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        } else if (matchedACL.getSubjectType() == oneAcl.getSubjectType()) {
                           if (matchedACL.isGrant() && oneAcl.isGrant()) {
                              allowed = true;
                           } else {
                              allowed = false;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean userInGroup(DirectoryString subject, DirectoryString group) {
      Entry groupent = null;

      try {
         groupent = BackendHandler.getInstance().getByDN((DirectoryString)null, group);
      } catch (DirectoryException var10) {
         Logger.getInstance().log(0, this, "Error searching for " + group + ": " + var10.getMessage());
         return false;
      }

      if (groupent == null) {
         return false;
      } else {
         Vector vals = groupent.get(ATTR_MEMBER);
         if (vals == null) {
            vals = groupent.get(ATTR_UNIQUEMEMBER);
         }

         if (vals != null) {
            return vals != null && vals.contains(subject);
         } else {
            vals = groupent.get(ATTR_MEMBERURL);
            if (vals != null) {
               Enumeration gmue = vals.elements();

               while(gmue.hasMoreElements()) {
                  String strurl = ((Syntax)gmue.nextElement()).toString();

                  try {
                     LDAPURL url = LDAPURL.fromString(strurl);
                     if (!subject.endsWith(url.getBase())) {
                        return false;
                     }

                     Vector tmpesets = BackendHandler.getInstance().get((DirectoryString)null, subject, 0, url.getFilter(), true, new Vector());
                     if (!tmpesets.isEmpty() && ((EntrySet)tmpesets.firstElement()).hasMore()) {
                        return true;
                     }
                  } catch (DirectoryException var9) {
                     return false;
                  }
               }
            }

            return false;
         }
      }
   }

   public synchronized void writeACLFile() {
      BufferedWriter bw = null;

      try {
         bw = new BufferedWriter(new FileWriter((String)ServerConfig.getInstance().get("vde.aclfile")));
         Enumeration acllocEnum = entryacls.keys();

         DirectoryString loc;
         Vector list;
         Enumeration rules;
         ACL oneAcl;
         while(acllocEnum.hasMoreElements()) {
            loc = (DirectoryString)acllocEnum.nextElement();
            list = (Vector)entryacls.get(loc);
            rules = list.elements();

            while(rules.hasMoreElements()) {
               oneAcl = (ACL)rules.nextElement();
               bw.write(loc + "|" + oneAcl.toString());
               bw.newLine();
            }
         }

         acllocEnum = subtreeacls.keys();

         while(acllocEnum.hasMoreElements()) {
            loc = (DirectoryString)acllocEnum.nextElement();
            list = (Vector)subtreeacls.get(loc);
            rules = list.elements();

            while(rules.hasMoreElements()) {
               oneAcl = (ACL)rules.nextElement();
               bw.write(loc + "|" + oneAcl.toString());
               bw.newLine();
            }
         }

         bw.close();
      } catch (IOException var7) {
         Logger.getInstance().printStackTrace(var7);
      }

   }

   private boolean evalFilter(Entry entry, Filter filter) {
      DirectoryString type = null;
      Syntax val = null;
      Vector attrVals = null;

      try {
         Enumeration ve;
         Syntax init;
         Iterator orEnum;
         switch (filter.getSelector()) {
            case 0:
               orEnum = filter.getAnd().iterator();

               do {
                  if (!orEnum.hasNext()) {
                     return true;
                  }
               } while(this.evalFilter(entry, (Filter)orEnum.next()));

               return false;
            case 1:
               orEnum = filter.getOr().iterator();

               do {
                  if (!orEnum.hasNext()) {
                     return false;
                  }
               } while(!this.evalFilter(entry, (Filter)orEnum.next()));

               return true;
            case 2:
               if (this.evalFilter(entry, filter.getNot())) {
                  return false;
               }

               return true;
            case 3:
               type = new DirectoryString(filter.getEqualityMatch().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
                  val.setValue(filter.getEqualityMatch().getAssertionValue().toByteArray());
                  if (attrVals.contains(val)) {
                     return true;
                  }

                  return false;
               }

               return false;
            case 5:
               type = new DirectoryString(filter.getGreaterOrEqual().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals == null || attrVals.isEmpty()) {
                  return false;
               }

               val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
               val.setValue(filter.getGreaterOrEqual().getAssertionValue().toByteArray());
               ve = attrVals.elements();
               if (ve.hasMoreElements()) {
                  init = (Syntax)ve.nextElement();
                  if (val.compareTo(init) >= 0) {
                     return true;
                  }

                  return false;
               }
            case 6:
               type = new DirectoryString(filter.getLessOrEqual().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals == null || attrVals.isEmpty()) {
                  return false;
               }

               val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
               val.setValue(filter.getLessOrEqual().getAssertionValue().toByteArray());
               ve = attrVals.elements();
               if (ve.hasMoreElements()) {
                  init = (Syntax)ve.nextElement();
                  if (val.compareTo(init) <= 0) {
                     return true;
                  }

                  return false;
               }
            case 4:
               type = new DirectoryString(filter.getSubstrings().getType().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  init = null;
                  Syntax any = null;
                  Syntax fin = null;
                  Class aclass = ((Syntax)attrVals.firstElement()).getClass();
                  Iterator substrEnum = filter.getSubstrings().getSubstrings().iterator();

                  while(substrEnum.hasNext()) {
                     SubstringFilter_substrings_Seq oneSubFilter = (SubstringFilter_substrings_Seq)substrEnum.next();
                     if (oneSubFilter.getSelector() == 0) {
                        init = (Syntax)aclass.newInstance();
                        init.setValue(oneSubFilter.getInitial().toByteArray());
                     } else if (oneSubFilter.getSelector() == 1) {
                        any = (Syntax)aclass.newInstance();
                        any.setValue(oneSubFilter.getAny().toByteArray());
                     } else if (oneSubFilter.getSelector() == 2) {
                        fin = (Syntax)aclass.newInstance();
                        fin.setValue(oneSubFilter.getFinal_().toByteArray());
                     }
                  }

                  StringBuffer regexbuf = new StringBuffer();
                  regexbuf.append("/^");
                  if (init != null) {
                     regexbuf.append(init.normalize());
                     regexbuf.append(".*");
                  }

                  if (any != null) {
                     if (regexbuf.length() < 3) {
                        regexbuf.append(".*");
                     }

                     regexbuf.append(any.normalize());
                     regexbuf.append(".*");
                  }

                  if (fin != null) {
                     if (regexbuf.length() < 3) {
                        regexbuf.append(".*");
                     }

                     regexbuf.append(fin.normalize());
                  }

                  regexbuf.append("$/");
                  String regex = regexbuf.toString();
                  ve = attrVals.elements();
                  Perl5Util p5u = new Perl5Util();

                  Syntax aval;
                  do {
                     if (!ve.hasMoreElements()) {
                        return false;
                     }

                     aval = (Syntax)ve.nextElement();
                  } while(!p5u.match(regex, aval.normalize()));

                  return true;
               }

               return false;
            case 7:
               type = new DirectoryString(filter.getPresent().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  return true;
               }

               return false;
            case 8:
               type = new DirectoryString(filter.getApproxMatch().getAttributeDesc().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
                  val.setValue(filter.getApproxMatch().getAssertionValue().toByteArray());
                  if (attrVals.contains(val)) {
                     return true;
                  }

                  return false;
               }

               return false;
            case 9:
               type = new DirectoryString(filter.getExtensibleMatch().getType().toByteArray());
               attrVals = entry.get(type);
               if (attrVals != null && !attrVals.isEmpty()) {
                  val = (Syntax)((Syntax)attrVals.firstElement()).getClass().newInstance();
                  val.setValue(filter.getExtensibleMatch().getMatchValue().toByteArray());
                  if (attrVals.contains(val)) {
                     return true;
                  }

                  return false;
               }

               return false;
         }
      } catch (InstantiationException var15) {
      } catch (IllegalAccessException var16) {
      }

      return false;
   }

   private Syntax getAttributeClass(DirectoryString type) {
      AttributeType at = null;
      at = SchemaChecker.getInstance().getAttributeType(type);
      if (at == null) {
         return new DirectoryString();
      } else {
         try {
            return (Syntax)at.getSyntaxClass().newInstance();
         } catch (Exception var4) {
            return new DirectoryString();
         }
      }
   }
}
