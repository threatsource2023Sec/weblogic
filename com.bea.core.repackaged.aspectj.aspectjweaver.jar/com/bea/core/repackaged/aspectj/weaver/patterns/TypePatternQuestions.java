package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TypePatternQuestions {
   private Map questionsAndAnswers = new HashMap();

   public FuzzyBoolean askQuestion(TypePattern pattern, ResolvedType type, TypePattern.MatchKind kind) {
      Question question = new Question(pattern, type, kind);
      FuzzyBoolean answer = question.ask();
      this.questionsAndAnswers.put(question, answer);
      return answer;
   }

   public Question anyChanges() {
      Iterator i = this.questionsAndAnswers.entrySet().iterator();

      Question question;
      FuzzyBoolean expectedAnswer;
      FuzzyBoolean currentAnswer;
      do {
         if (!i.hasNext()) {
            return null;
         }

         Map.Entry entry = (Map.Entry)i.next();
         question = (Question)entry.getKey();
         expectedAnswer = (FuzzyBoolean)entry.getValue();
         currentAnswer = question.ask();
      } while(currentAnswer == expectedAnswer);

      return question;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("TypePatternQuestions{");
      Iterator i$ = this.questionsAndAnswers.entrySet().iterator();

      while(i$.hasNext()) {
         Map.Entry entry = (Map.Entry)i$.next();
         Question question = (Question)entry.getKey();
         FuzzyBoolean expectedAnswer = (FuzzyBoolean)entry.getValue();
         buf.append(question);
         buf.append(":");
         buf.append(expectedAnswer);
         buf.append(", ");
      }

      buf.append("}");
      return buf.toString();
   }

   public class Question {
      TypePattern pattern;
      ResolvedType type;
      TypePattern.MatchKind kind;

      public Question(TypePattern pattern, ResolvedType type, TypePattern.MatchKind kind) {
         this.pattern = pattern;
         this.type = type;
         this.kind = kind;
      }

      public FuzzyBoolean ask() {
         return this.pattern.matches(this.type, this.kind);
      }

      public boolean equals(Object other) {
         if (!(other instanceof Question)) {
            return false;
         } else {
            Question o = (Question)other;
            return o.pattern.equals(this.pattern) && o.type.equals(this.type) && o.kind == this.kind;
         }
      }

      public int hashCode() {
         int result = 17;
         result = 37 * result + this.kind.hashCode();
         result = 37 * result + this.pattern.hashCode();
         result = 37 * result + this.type.hashCode();
         return result;
      }

      public String toString() {
         return "?(" + this.pattern + ", " + this.type + ", " + this.kind + ")";
      }
   }
}
