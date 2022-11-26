package org.glassfish.soteria.identitystores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStorePermission;
import javax.security.enterprise.identitystore.PasswordHash;
import javax.security.enterprise.identitystore.IdentityStore.ValidationType;
import javax.sql.DataSource;
import org.glassfish.soteria.cdi.AnnotationELPProcessor;
import org.glassfish.soteria.cdi.CdiUtils;

public class DatabaseIdentityStore implements IdentityStore {
   private final DatabaseIdentityStoreDefinition dataBaseIdentityStoreDefinition;
   private final Set validationTypes;
   private final PasswordHash hashAlgorithm;

   public DatabaseIdentityStore(DatabaseIdentityStoreDefinition dataBaseIdentityStoreDefinition) {
      this.dataBaseIdentityStoreDefinition = dataBaseIdentityStoreDefinition;
      this.validationTypes = Collections.unmodifiableSet(new HashSet(Arrays.asList(dataBaseIdentityStoreDefinition.useFor())));
      this.hashAlgorithm = (PasswordHash)CdiUtils.getBeanReference(dataBaseIdentityStoreDefinition.hashAlgorithm());
      this.hashAlgorithm.initialize(Collections.unmodifiableMap((Map)Arrays.stream(dataBaseIdentityStoreDefinition.hashAlgorithmParameters()).flatMap((s) -> {
         return this.toStream(AnnotationELPProcessor.evalImmediate((String)s, (Object)s));
      }).collect(Collectors.toMap((s) -> {
         return s.substring(0, s.indexOf(61));
      }, (s) -> {
         return AnnotationELPProcessor.evalImmediate(s.substring(s.indexOf(61) + 1));
      }))));
   }

   public CredentialValidationResult validate(Credential credential) {
      return credential instanceof UsernamePasswordCredential ? this.validate((UsernamePasswordCredential)credential) : CredentialValidationResult.NOT_VALIDATED_RESULT;
   }

   public CredentialValidationResult validate(UsernamePasswordCredential usernamePasswordCredential) {
      DataSource dataSource = this.getDataSource();
      List passwords = this.executeQuery(dataSource, this.dataBaseIdentityStoreDefinition.callerQuery(), usernamePasswordCredential.getCaller());
      if (passwords.isEmpty()) {
         return CredentialValidationResult.INVALID_RESULT;
      } else if (this.hashAlgorithm.verify(usernamePasswordCredential.getPassword().getValue(), (String)passwords.get(0))) {
         Set groups = Collections.emptySet();
         if (this.validationTypes.contains(ValidationType.PROVIDE_GROUPS)) {
            groups = new HashSet(this.executeQuery(dataSource, this.dataBaseIdentityStoreDefinition.groupsQuery(), usernamePasswordCredential.getCaller()));
         }

         return new CredentialValidationResult(new CallerPrincipal(usernamePasswordCredential.getCaller()), (Set)groups);
      } else {
         return CredentialValidationResult.INVALID_RESULT;
      }
   }

   public Set getCallerGroups(CredentialValidationResult validationResult) {
      SecurityManager securityManager = System.getSecurityManager();
      if (securityManager != null) {
         securityManager.checkPermission(new IdentityStorePermission("getGroups"));
      }

      DataSource dataSource = this.getDataSource();
      return new HashSet(this.executeQuery(dataSource, this.dataBaseIdentityStoreDefinition.groupsQuery(), validationResult.getCallerPrincipal().getName()));
   }

   private List executeQuery(DataSource dataSource, String query, String parameter) {
      List result = new ArrayList();

      try {
         Connection connection = dataSource.getConnection();
         Throwable var6 = null;

         try {
            PreparedStatement statement = connection.prepareStatement(query);
            Throwable var8 = null;

            try {
               statement.setString(1, parameter);
               ResultSet resultSet = statement.executeQuery();
               Throwable var10 = null;

               try {
                  while(resultSet.next()) {
                     result.add(resultSet.getString(1));
                  }
               } catch (Throwable var57) {
                  var10 = var57;
                  throw var57;
               } finally {
                  if (resultSet != null) {
                     if (var10 != null) {
                        try {
                           resultSet.close();
                        } catch (Throwable var56) {
                           var10.addSuppressed(var56);
                        }
                     } else {
                        resultSet.close();
                     }
                  }

               }
            } catch (Throwable var59) {
               var8 = var59;
               throw var59;
            } finally {
               if (statement != null) {
                  if (var8 != null) {
                     try {
                        statement.close();
                     } catch (Throwable var55) {
                        var8.addSuppressed(var55);
                     }
                  } else {
                     statement.close();
                  }
               }

            }
         } catch (Throwable var61) {
            var6 = var61;
            throw var61;
         } finally {
            if (connection != null) {
               if (var6 != null) {
                  try {
                     connection.close();
                  } catch (Throwable var54) {
                     var6.addSuppressed(var54);
                  }
               } else {
                  connection.close();
               }
            }

         }

         return result;
      } catch (SQLException var63) {
         throw new IdentityStoreConfigurationException(var63.getMessage(), var63);
      }
   }

   public int priority() {
      return this.dataBaseIdentityStoreDefinition.priority();
   }

   public Set validationTypes() {
      return this.validationTypes;
   }

   private Stream toStream(Object raw) {
      if (raw instanceof String[]) {
         return Arrays.stream((String[])((String[])raw));
      } else {
         return raw instanceof Stream ? ((Stream)raw).map((s) -> {
            return s.toString();
         }) : Arrays.asList(raw.toString()).stream();
      }
   }

   private DataSource getDataSource() {
      DataSource dataSource = null;

      try {
         dataSource = (DataSource)CdiUtils.jndiLookup(this.dataBaseIdentityStoreDefinition.dataSourceLookup());
         if (dataSource == null) {
            throw new IdentityStoreConfigurationException("Jndi lookup failed for DataSource " + this.dataBaseIdentityStoreDefinition.dataSourceLookup());
         } else {
            return dataSource;
         }
      } catch (IdentityStoreConfigurationException var3) {
         throw var3;
      } catch (Exception var4) {
         throw new IdentityStoreRuntimeException(var4);
      }
   }
}
