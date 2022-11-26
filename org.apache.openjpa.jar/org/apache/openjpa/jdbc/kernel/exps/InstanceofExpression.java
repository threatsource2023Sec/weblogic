package org.apache.openjpa.jdbc.kernel.exps;

import java.util.Map;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.Discriminator;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.sql.Joins;
import org.apache.openjpa.jdbc.sql.SQLBuffer;
import org.apache.openjpa.jdbc.sql.Select;
import org.apache.openjpa.kernel.exps.Expression;
import org.apache.openjpa.kernel.exps.ExpressionVisitor;

class InstanceofExpression implements Exp {
   private final PCPath _path;
   private final Class _cls;

   public InstanceofExpression(PCPath path, Class cls) {
      this._path = path;
      this._cls = cls;
   }

   public ExpState initialize(Select sel, ExpContext ctx, Map contains) {
      ExpState pathState = this._path.initialize(sel, ctx, 4);
      ClassMapping relMapping = this._path.getClassMapping(pathState);
      Class rel = null;
      if (relMapping == null) {
         FieldMapping field = this._path.getFieldMapping(pathState);
         switch (field.getTypeCode()) {
            case 13:
               if (this._path.isKey()) {
                  rel = field.getKey().getDeclaredType();
               }
            case 11:
            case 12:
               rel = field.getElement().getDeclaredType();
               break;
            default:
               rel = field.getDeclaredType();
         }
      } else {
         rel = relMapping.getDescribedType();
      }

      Discriminator discrim = relMapping != null && relMapping.getDescribedType().isAssignableFrom(this._cls) ? relMapping.getDiscriminator() : null;
      ClassMapping mapping = null;
      Joins joins = pathState.joins;
      if (discrim != null) {
         MappingRepository repos = ctx.store.getConfiguration().getMappingRepositoryInstance();
         mapping = repos.getMapping(this._cls, ctx.store.getContext().getClassLoader(), false);
         if (mapping != null && discrim.hasClassConditions(mapping, true)) {
            ClassMapping owner = discrim.getClassMapping();
            ClassMapping from;
            ClassMapping to;
            if (relMapping.getDescribedType().isAssignableFrom(owner.getDescribedType())) {
               from = owner;
               to = relMapping;
            } else {
               from = relMapping;
               to = owner;
            }

            while(from != null && from != to) {
               joins = from.joinSuperclass(joins, false);
               from = from.getJoinablePCSuperclassMapping();
            }
         } else {
            discrim = null;
         }
      }

      return new InstanceofExpState(joins, pathState, mapping, discrim, rel);
   }

   public void appendTo(Select sel, ExpContext ctx, ExpState state, SQLBuffer sql) {
      InstanceofExpState istate = (InstanceofExpState)state;
      if (istate.discrim == null) {
         if (this._cls.isAssignableFrom(istate.rel)) {
            sql.append("1 = 1");
         } else {
            sql.append("1 <> 1");
         }
      } else {
         ctx.store.loadSubclasses(istate.discrim.getClassMapping());
         SQLBuffer buf = istate.discrim.getClassConditions(sel, istate.joins, istate.mapping, true);
         sql.append(buf);
      }

      sel.append(sql, istate.joins);
   }

   public void selectColumns(Select sel, ExpContext ctx, ExpState state, boolean pks) {
      InstanceofExpState istate = (InstanceofExpState)state;
      if (istate.discrim != null) {
         sel.select(istate.discrim.getColumns(), istate.joins);
      }

   }

   public void acceptVisit(ExpressionVisitor visitor) {
      visitor.enter((Expression)this);
      this._path.acceptVisit(visitor);
      visitor.exit((Expression)this);
   }

   private static class InstanceofExpState extends ExpState {
      public final ExpState pathState;
      public final ClassMapping mapping;
      public final Discriminator discrim;
      public final Class rel;

      public InstanceofExpState(Joins joins, ExpState pathState, ClassMapping mapping, Discriminator discrim, Class rel) {
         super(joins);
         this.pathState = pathState;
         this.mapping = mapping;
         this.discrim = discrim;
         this.rel = rel;
      }
   }
}
