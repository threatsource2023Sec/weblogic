package com.octetstring.vde.replication;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntryChanges;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.Logger;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.security.auth.Subject;

public class Replicator {
   Consumer consumer = null;
   BackendChangeLog bcl = null;
   String replicadataPath = null;
   DirContext ctx = null;
   private Subject sslSubject = null;
   private int changelog_replication_retry_limit = 0;
   private int changelog_replication_retry_count = 0;

   public void setSSLSubject(Subject sslSubject) {
      this.sslSubject = sslSubject;
   }

   public Replicator() {
   }

   public Replicator(String replicadataPath, Consumer consumer, BackendChangeLog bcl) {
      this.consumer = consumer;
      this.bcl = bcl;
      this.replicadataPath = replicadataPath;

      try {
         this.changelog_replication_retry_limit = Integer.parseInt(Messages.getString("Changelog_replication_retry_limit___88"));
      } catch (NumberFormatException var5) {
         this.changelog_replication_retry_limit = 3;
      }

      if (this.changelog_replication_retry_limit <= 0) {
         this.changelog_replication_retry_limit = 0;
      }

   }

   private void increment() {
      int ct = this.consumer.getChangeSent();
      ++ct;
      this.consumer.setChangeSent(ct);
      this.changelog_replication_retry_count = 0;

      try {
         FileOutputStream fos = new FileOutputStream(this.replicadataPath + "/" + this.consumer.getAgreementName() + ".status");
         DataOutputStream dos = new DataOutputStream(fos);
         dos.writeInt(ct);
         dos.close();
      } catch (IOException var4) {
         Logger.getInstance().log(0, this, Messages.getString("Could_not_update_replica_status_file_for__3") + this.consumer.getAgreementName());
         Logger.getInstance().printStackTrace(var4);
      }

   }

   public void run() {
      Class cls;
      String dn;
      if (this.ctx == null) {
         Hashtable env = new Hashtable();
         env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
         env.put("java.naming.security.principal", this.consumer.getBinddn().toString());
         env.put("java.naming.security.credentials", this.consumer.getBindpw());
         env.put("java.naming.provider.url", "ldap://" + this.consumer.getHostname() + ":" + this.consumer.getPort() + "/");
         if (this.consumer.isSecure()) {
            env.put("java.naming.security.protocol", "ssl");
            env.put("java.naming.ldap.factory.socket", "weblogic.security.SSL.ServerSSLSocketFactory");
         }

         Logger.getInstance().log(5, this, "Creating context to url ldap://" + this.consumer.getHostname() + ":" + this.consumer.getPort() + "/");

         Throwable cause;
         try {
            if (this.consumer.isSecure() && this.sslSubject != null) {
               try {
                  cls = Class.forName("weblogic.security.Security");
                  dn = "runAs";
                  Class[] params = new Class[]{Subject.class, PrivilegedAction.class};
                  Method runas = cls.getMethod(dn, params);
                  Object privAction = new MyPrivilegedAction(env, this.consumer);
                  Object[] parameters = new Object[]{this.sslSubject, privAction};
                  this.ctx = (DirContext)runas.invoke((Object)null, parameters);
                  if (this.ctx == null) {
                     return;
                  }
               } catch (Exception var24) {
                  cause = var24.getCause();
                  if (cause == null) {
                     ;
                  }

                  Logger.getInstance().log(5, this, Messages.getString("Error_Connecting_to_Consumer___9") + this.consumer.getHostname() + Messages.getString("_port__10") + this.consumer.getPort());
                  this.consumer.setRunning(false);
                  return;
               }
            } else {
               this.ctx = new InitialDirContext(env);
            }
         } catch (NamingException var27) {
            cause = var27.getRootCause();
            if (cause == null) {
               ;
            }

            Logger.getInstance().log(5, this, Messages.getString("Error_Connecting_to_Consumer___9") + this.consumer.getHostname() + Messages.getString("_port__10") + this.consumer.getPort());
            this.consumer.setRunning(false);
            return;
         }
      }

      while(this.consumer.getChangeSent() < this.bcl.getChangeHigh()) {
         int nextChange = this.consumer.getChangeSent() + 1;
         cls = null;

         EntryChanges ec;
         try {
            ec = this.bcl.getChange(nextChange);
         } catch (IOException | NegativeArraySizeException | IndexOutOfBoundsException | InvalidChangeLogIndexException var25) {
            Logger.getInstance().log(0, this, Messages.getString("Skip_replicating_changelog_entry#___90") + nextChange);
            this.increment();
            continue;
         } catch (Exception var26) {
            if (this.changelog_replication_retry_count < this.changelog_replication_retry_limit) {
               ++this.changelog_replication_retry_count;
               continue;
            }

            Logger.getInstance().log(0, this, Messages.getString("Skip_replicating_changelog_entry#___90") + nextChange);
            this.increment();
            continue;
         }

         Logger.getInstance().log(5, this, "Replicating change no " + nextChange + " name " + ec.getName() + " type " + ec.getChangeType());
         if (ec.getChangeType() == 1) {
            Entry myEntry = ec.getFullEntry();
            Logger.getInstance().log(5, this, "Replicating add entry " + myEntry.getName());
            if (myEntry.getName().endsWith(this.consumer.getReplicaBase())) {
               Attributes newAttrs = new BasicAttributes();
               Enumeration attrEnum = myEntry.keys();

               while(attrEnum.hasMoreElements()) {
                  DirectoryString type = (DirectoryString)attrEnum.nextElement();
                  Attribute oneAttr = new BasicAttribute(type.toString());
                  Vector vals = myEntry.get(type);
                  Enumeration valEnum = vals.elements();

                  while(valEnum.hasMoreElements()) {
                     oneAttr.add(((Syntax)valEnum.nextElement()).getValue());
                  }

                  newAttrs.put(oneAttr);
               }

               try {
                  String name = this.filterName(myEntry.getName().toString());
                  DirContext ndc = this.ctx.createSubcontext(name, newAttrs);
                  if (ndc != null) {
                     ndc.close();
                  }
               } catch (NameAlreadyBoundException var22) {
                  Logger.getInstance().log(5, this, Messages.getString("Agreement____11") + this.consumer.getAgreementName() + "': " + Messages.getString("Skipping_Change#_13") + nextChange + Messages.getString("-_Already_Exists_on_Consumer_14"));
               } catch (NamingException var23) {
                  try {
                     this.ctx.close();
                  } catch (NamingException var13) {
                  }

                  Logger.getInstance().log(0, this, Messages.getString("Agreement____15") + this.consumer.getAgreementName() + "': " + Messages.getString("Error_Transmitting_Change#_17") + nextChange + "- " + var23.getMessage());
                  this.consumer.setRunning(false);
                  this.ctx = null;
                  return;
               }
            }

            this.increment();
         } else if (ec.getChangeType() != 2) {
            if (ec.getChangeType() == 3) {
               if (ec.getName().endsWith(this.consumer.getReplicaBase())) {
                  dn = ec.getName().toString();

                  try {
                     this.ctx.destroySubcontext(dn);
                  } catch (NameNotFoundException var17) {
                     Logger.getInstance().log(5, this, Messages.getString("Agreement____23") + this.consumer.getAgreementName() + "': " + Messages.getString("Skipping_Change#_25") + nextChange + Messages.getString("-_Doesn__t_Exist_on_Consumer_26"));
                  } catch (NamingException var18) {
                     try {
                        this.ctx.close();
                     } catch (NamingException var15) {
                     }

                     Logger.getInstance().log(0, this, Messages.getString("Agreement____27") + this.consumer.getAgreementName() + "': " + Messages.getString("Error_Transmitting_Change#_29") + nextChange + "- " + var18.getMessage());
                     this.consumer.setRunning(false);
                     this.ctx = null;
                     return;
                  }
               }

               this.increment();
            } else if (ec.getChangeType() == 4) {
               if (ec.getName().endsWith(this.consumer.getReplicaBase()) && ec.getNewName().endsWith(this.consumer.getReplicaBase())) {
                  dn = ec.getName().toString();
                  String newdn = ec.getNewName().toString();

                  try {
                     this.ctx.rename(dn, newdn);
                  } catch (NamingException var16) {
                     try {
                        this.ctx.close();
                     } catch (NamingException var14) {
                     }

                     Logger.getInstance().log(0, this, Messages.getString("Agreement____31") + this.consumer.getAgreementName() + "': " + Messages.getString("Error_Transmitting_Change#_33") + nextChange + "- " + var16.getMessage());
                     this.consumer.setRunning(false);
                     this.ctx = null;
                     return;
                  }
               }

               this.increment();
            } else {
               Logger.getInstance().log(0, this, Messages.getString("Agreement____35") + this.consumer.getAgreementName() + "': " + Messages.getString("Invalid_Changetype___37") + ec.getChangeType() + Messages.getString("in_change#_38") + nextChange);
               this.increment();
            }
         } else {
            if (ec.getName().endsWith(this.consumer.getReplicaBase())) {
               dn = ec.getName().toString();
               EntryChange[] entch = ec.getEntryChanges();
               ModificationItem[] mods = new ModificationItem[entch.length];

               for(int ce = 0; ce < entch.length; ++ce) {
                  int modType = entch[ce].getModType();
                  int modOp = -1;
                  if (modType == 0) {
                     modOp = 1;
                  } else if (modType == 2) {
                     modOp = 2;
                  } else if (modType == 1) {
                     modOp = 3;
                  }

                  Attribute oneAttr = new BasicAttribute(entch[ce].getAttr().toString());
                  Vector vals = entch[ce].getValues();
                  Enumeration valEnum = vals.elements();

                  while(valEnum.hasMoreElements()) {
                     oneAttr.add(((Syntax)valEnum.nextElement()).getValue());
                  }

                  mods[ce] = new ModificationItem(modOp, oneAttr);
               }

               try {
                  this.ctx.modifyAttributes(dn, mods);
               } catch (AttributeInUseException var19) {
                  Logger.getInstance().log(5, this, "Agreement '" + this.consumer.getAgreementName() + "': Skipping Change#" + nextChange + "- Already Exists on Consumer");
               } catch (NameNotFoundException var20) {
                  Logger.getInstance().log(5, this, "Agreement '" + this.consumer.getAgreementName() + "': Skipping Change#" + nextChange + "- Doesn't Exist on Consumer");
               } catch (NamingException var21) {
                  try {
                     this.ctx.close();
                  } catch (NamingException var12) {
                  }

                  Logger.getInstance().log(5, this, Messages.getString("Agreement____19") + this.consumer.getAgreementName() + "': " + Messages.getString("Error_Transmitting_Change#_21") + nextChange + "- " + var21.getMessage());
                  this.consumer.setRunning(false);
                  this.ctx = null;
                  return;
               }
            }

            this.increment();
         }
      }

      this.consumer.setRunning(false);
   }

   private String filterName(String name) {
      return name.indexOf(47) >= 0 ? name.replaceAll("/", "\\\\/") : name;
   }

   private static class MyPrivilegedAction implements PrivilegedAction {
      private Hashtable env = null;
      private Consumer consumer = null;

      MyPrivilegedAction(Hashtable env, Consumer consumer) {
         this.env = env;
         this.consumer = consumer;
      }

      public Object run() {
         try {
            return new InitialDirContext(this.env);
         } catch (NamingException var3) {
            Throwable rc = var3.getRootCause();
            if (rc == null) {
               ;
            }

            Logger.getInstance().log(5, this, Messages.getString("Error_Connecting_to_Consumer___9") + this.consumer.getHostname() + Messages.getString("_port__10") + this.consumer.getPort());
            this.consumer.setRunning(false);
            return null;
         }
      }
   }
}
