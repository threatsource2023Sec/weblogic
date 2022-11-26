package weblogic.corba.j2ee.naming;

import javax.naming.CannotProceedException;
import javax.naming.CommunicationException;
import javax.naming.CompositeName;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.CosNaming.NamingContextPackage.AlreadyBound;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.InvalidName;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.CosNaming.NamingContextPackage.NotFoundReason;
import weblogic.corba.cos.naming.NamingContextAnyHelper;
import weblogic.corba.cos.naming.NamingContextAnyPackage.AppException;
import weblogic.corba.cos.naming.NamingContextAnyPackage.WNameComponent;

public final class Utils {
   private static final boolean DEBUG = false;

   public static NamingException wrapNamingException(Exception e, String msg) throws NamingException {
      NamingException ne = null;
      if (e instanceof InvalidName) {
         ne = new InvalidNameException(msg);
      } else if (e instanceof org.omg.CORBA.ORBPackage.InvalidName) {
         ne = new InvalidNameException(msg);
      } else if (e instanceof NotFound) {
         NotFound notFound = (NotFound)e;
         msg = msg + ": `" + nameComponentToString(notFound.rest_of_name) + "'" + notFoundReasonToString(notFound.why);
         ne = new NameNotFoundException(msg);
      } else if (e instanceof weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound) {
         weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound notFound = (weblogic.corba.cos.naming.NamingContextAnyPackage.NotFound)e;
         msg = msg + ": `" + nameComponentToString(notFound.rest_of_name) + "'" + notFoundReasonToString(notFound.why);
         ne = new NameNotFoundException(msg);
      } else if (e instanceof CannotProceed) {
         CannotProceed cpe = (CannotProceed)e;
         msg = msg + ": unresolved name `" + nameComponentToString(cpe.rest_of_name) + "'";
         ne = new CannotProceedException(msg);
      } else if (e instanceof weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed) {
         weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed cpe = (weblogic.corba.cos.naming.NamingContextAnyPackage.CannotProceed)e;
         msg = msg + ": unresolved name `" + nameComponentToString(cpe.rest_of_name) + "'";
         ne = new CannotProceedException(msg);
      } else if (e instanceof AlreadyBound) {
         ne = new NameAlreadyBoundException(msg);
      } else if (e instanceof COMM_FAILURE) {
         ne = new CommunicationException(msg);
      } else if (e instanceof AppException) {
         ne = new NamingException(String.format("%s %s (caused by %s)", msg, e.getClass().getCanonicalName(), ((AppException)e).name));
      } else {
         ne = new NamingException(msg);
      }

      ((NamingException)ne).setRootCause(e);
      return (NamingException)ne;
   }

   public static SystemException unwrapNamingException(SystemException fallback, NamingException ne) {
      if (ne.getRootCause() instanceof SystemException) {
         return (SystemException)ne.getRootCause();
      } else {
         fallback.initCause(ne);
         return fallback;
      }
   }

   public static SystemException initCORBAExceptionWithCause(SystemException se, Throwable cause) {
      se.initCause(cause);
      return se;
   }

   public static final NamingContext narrowContext(Object obj) {
      return (NamingContext)(obj._is_a(NamingContextAnyHelper.id()) ? NamingContextAnyHelper.narrow(obj) : NamingContextHelper.narrow(obj));
   }

   public static final NameComponent[] stringToNameComponent(String resolvePath) throws NamingException {
      return nameToName(stringToName(resolvePath));
   }

   public static final Name stringToName(String resolvePath) throws NamingException {
      resolvePath = NameParser.getNameString(resolvePath);

      CompositeName name;
      for(name = new CompositeName(); resolvePath.length() > 0; resolvePath = getSuffix(resolvePath)) {
         name.add(getPrefix(resolvePath));
      }

      return name;
   }

   public static final WNameComponent[] stringToWNameComponent(String resolvePath) throws NamingException {
      return nameToWName(stringToNameComponent(resolvePath));
   }

   public static final WNameComponent[] nameToWName(NameComponent[] nc) throws NamingException {
      WNameComponent[] wnc = new WNameComponent[nc.length];

      for(int i = 0; i < nc.length; ++i) {
         wnc[i] = new WNameComponent(nc[i].id, nc[i].kind);
      }

      return wnc;
   }

   public static final WNameComponent[] nameToWName(Name nc) throws NamingException {
      WNameComponent[] wnc = new WNameComponent[nc.size()];

      for(int i = 0; i < nc.size(); ++i) {
         wnc[i] = new WNameComponent(nc.get(i), "");
      }

      return wnc;
   }

   public static final NameComponent[] nameToName(Name nc) throws NamingException {
      NameComponent[] wnc = new NameComponent[nc.size()];

      for(int i = 0; i < nc.size(); ++i) {
         wnc[i] = new NameComponent(nc.get(i), "");
      }

      return wnc;
   }

   public static final String nameComponentToString(NameComponent[] nc) {
      StringBuffer s = new StringBuffer("");
      if (nc != null) {
         for(int i = 0; i < nc.length; ++i) {
            if (i > 0) {
               s.append('/');
            }

            String n = nc[i].id;

            for(int j = 0; j < n.length(); ++j) {
               if (j != 0 && j != n.length() - 1 && isQuote(n.charAt(j)) || isSeparator(n.charAt(j))) {
                  s.append('\\');
               }

               s.append(n.charAt(j));
            }

            if (nc[i].kind.length() > 0) {
               s.append('/').append(nc[i].kind);
            }
         }
      }

      return s.toString();
   }

   private static boolean isSeparator(char c) {
      return c == '.' || c == '/';
   }

   private static boolean isQuote(char c) {
      return c == '"' || c == '\'';
   }

   public static final String nameComponentToString(WNameComponent[] nc) {
      StringBuffer s = new StringBuffer("");
      if (nc != null) {
         for(int i = 0; i < nc.length; ++i) {
            if (i > 0) {
               s.append('/');
            }

            String n = nc[i].id;

            for(int j = 0; j < n.length(); ++j) {
               if (j != 0 && j != n.length() - 1 && isQuote(n.charAt(j)) || isSeparator(n.charAt(j))) {
                  s.append('\\');
               }

               s.append(n.charAt(j));
            }

            if (nc[i].kind.length() > 0) {
               s.append('/').append(nc[i].kind);
            }
         }
      }

      return s.toString();
   }

   private static final String notFoundReasonToString(NotFoundReason nfr) {
      switch (nfr.value()) {
         case 0:
            return " could not be found.";
         case 1:
            return " is not a naming context.";
         case 2:
            return " is not a remote object.";
         default:
            return null;
      }
   }

   private static String getPrefix(String name) throws NamingException {
      if (name.length() == 0) {
         return name;
      } else {
         int idx;
         if (name.charAt(0) == '"') {
            idx = name.indexOf(34, 1);
            if (idx < 0) {
               throw new InvalidNameException("No closing quote");
            } else if (idx < name.length() - 1 && !isSeparator(name.charAt(idx + 1))) {
               throw new InvalidNameException("Closing quote must be at component end");
            } else {
               return name.substring(1, idx);
            }
         } else if (name.charAt(0) == '\'') {
            idx = name.indexOf(39, 1);
            if (idx < 0) {
               throw new InvalidNameException("No closing quote");
            } else if (idx < name.length() - 1 && !isSeparator(name.charAt(idx + 1))) {
               throw new InvalidNameException("Closing quote must be at component end");
            } else {
               return name.substring(1, idx);
            }
         } else {
            StringBuffer prefix = new StringBuffer();

            for(int i = 0; i < name.length(); ++i) {
               switch (name.charAt(i)) {
                  case '"':
                  case '\'':
                     throw new InvalidNameException("Unescaped quote in a component");
                  case '\\':
                     ++i;
                     if (i == name.length()) {
                        throw new InvalidNameException("An escape at the end of a name must be escaped");
                     }

                     prefix.append(name.charAt(i));
                     break;
                  default:
                     if (isSeparator(name.charAt(i))) {
                        return prefix.toString();
                     }

                     prefix.append(name.charAt(i));
               }
            }

            return prefix.toString();
         }
      }
   }

   private static String getSuffix(String name) throws NamingException {
      if (name.length() == 0) {
         return name;
      } else {
         int i;
         if (name.charAt(0) == '"') {
            i = name.indexOf(34, 1);
            if (i < 0) {
               throw new InvalidNameException("No closing quote");
            } else if (i < name.length() - 1 && !isSeparator(name.charAt(i + 1))) {
               throw new InvalidNameException("Closing quote must be at component end");
            } else {
               ++i;
               if (i < name.length() && isSeparator(name.charAt(i))) {
                  ++i;
               }

               return name.substring(i);
            }
         } else if (name.charAt(0) == '\'') {
            i = name.indexOf(39, 1);
            if (i < 0) {
               throw new InvalidNameException("No closing quote");
            } else if (i < name.length() - 1 && !isSeparator(name.charAt(i + 1))) {
               throw new InvalidNameException("Closing quote must be at component end");
            } else {
               ++i;
               if (i < name.length() && isSeparator(name.charAt(i))) {
                  ++i;
               }

               return name.substring(i);
            }
         } else {
            for(i = 0; i < name.length(); ++i) {
               switch (name.charAt(i)) {
                  case '"':
                  case '\'':
                     throw new InvalidNameException("Unescaped quote in a component");
                  case '\\':
                     ++i;
                     if (i == name.length()) {
                        throw new InvalidNameException("An escape at the end of a name must be escaped");
                     }
                     break;
                  default:
                     if (isSeparator(name.charAt(i))) {
                        return name.substring(i + 1);
                     }
               }
            }

            return "";
         }
      }
   }
}
