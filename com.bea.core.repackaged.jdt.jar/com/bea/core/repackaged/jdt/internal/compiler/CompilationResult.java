package com.bea.core.repackaged.jdt.internal.compiler;

import com.bea.core.repackaged.jdt.core.compiler.CategorizedProblem;
import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.env.ICompilationUnit;
import com.bea.core.repackaged.jdt.internal.compiler.impl.ReferenceContext;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.SourceTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeConstants;
import com.bea.core.repackaged.jdt.internal.compiler.parser.RecoveryScannerData;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class CompilationResult {
   public CategorizedProblem[] problems;
   public CategorizedProblem[] tasks;
   public int problemCount;
   public int taskCount;
   public ICompilationUnit compilationUnit;
   private Map problemsMap;
   private Set firstErrors;
   private int maxProblemPerUnit;
   public char[][][] qualifiedReferences;
   public char[][] simpleNameReferences;
   public char[][] rootReferences;
   public boolean hasAnnotations = false;
   public boolean hasFunctionalTypes = false;
   public int[] lineSeparatorPositions;
   public RecoveryScannerData recoveryScannerData;
   public Map compiledTypes = new Hashtable(11);
   public int unitIndex;
   public int totalUnitsKnown;
   public boolean hasBeenAccepted = false;
   public char[] fileName;
   public boolean hasInconsistentToplevelHierarchies = false;
   public boolean hasSyntaxError = false;
   public char[][] packageName;
   public boolean checkSecondaryTypes = false;
   private int numberOfErrors;
   private boolean hasMandatoryErrors;
   private static final int[] EMPTY_LINE_ENDS;
   private static final Comparator PROBLEM_COMPARATOR;

   static {
      EMPTY_LINE_ENDS = Util.EMPTY_INT_ARRAY;
      PROBLEM_COMPARATOR = new Comparator() {
         public int compare(Object o1, Object o2) {
            return ((CategorizedProblem)o1).getSourceStart() - ((CategorizedProblem)o2).getSourceStart();
         }
      };
   }

   public CompilationResult(char[] fileName, int unitIndex, int totalUnitsKnown, int maxProblemPerUnit) {
      this.fileName = fileName;
      this.unitIndex = unitIndex;
      this.totalUnitsKnown = totalUnitsKnown;
      this.maxProblemPerUnit = maxProblemPerUnit;
   }

   public CompilationResult(ICompilationUnit compilationUnit, int unitIndex, int totalUnitsKnown, int maxProblemPerUnit) {
      this.fileName = compilationUnit.getFileName();
      this.compilationUnit = compilationUnit;
      this.unitIndex = unitIndex;
      this.totalUnitsKnown = totalUnitsKnown;
      this.maxProblemPerUnit = maxProblemPerUnit;
   }

   private int computePriority(CategorizedProblem problem) {
      int priority = 10000 - problem.getSourceLineNumber();
      if (priority < 0) {
         priority = 0;
      }

      if (problem.isError()) {
         priority += 100000;
      }

      ReferenceContext context = this.problemsMap == null ? null : (ReferenceContext)this.problemsMap.get(problem);
      if (context != null) {
         if (context instanceof AbstractMethodDeclaration) {
            AbstractMethodDeclaration method = (AbstractMethodDeclaration)context;
            if (method.isStatic()) {
               priority += 10000;
            }
         } else {
            priority += 40000;
         }

         if (this.firstErrors.contains(problem)) {
            priority += 20000;
         }
      } else {
         priority += 40000;
      }

      return priority;
   }

   public CategorizedProblem[] getAllProblems() {
      CategorizedProblem[] onlyProblems = this.getProblems();
      int onlyProblemCount = onlyProblems != null ? onlyProblems.length : 0;
      CategorizedProblem[] onlyTasks = this.getTasks();
      int onlyTaskCount = onlyTasks != null ? onlyTasks.length : 0;
      if (onlyTaskCount == 0) {
         return onlyProblems;
      } else if (onlyProblemCount == 0) {
         return onlyTasks;
      } else {
         int totalNumberOfProblem = onlyProblemCount + onlyTaskCount;
         CategorizedProblem[] allProblems = new CategorizedProblem[totalNumberOfProblem];
         int allProblemIndex = 0;
         int taskIndex = 0;

         CategorizedProblem currentProblem;
         for(int problemIndex = 0; taskIndex + problemIndex < totalNumberOfProblem; allProblems[allProblemIndex++] = currentProblem) {
            CategorizedProblem nextTask = null;
            CategorizedProblem nextProblem = null;
            if (taskIndex < onlyTaskCount) {
               nextTask = onlyTasks[taskIndex];
            }

            if (problemIndex < onlyProblemCount) {
               nextProblem = onlyProblems[problemIndex];
            }

            currentProblem = null;
            if (nextProblem != null) {
               if (nextTask != null) {
                  if (nextProblem.getSourceStart() < nextTask.getSourceStart()) {
                     currentProblem = nextProblem;
                     ++problemIndex;
                  } else {
                     currentProblem = nextTask;
                     ++taskIndex;
                  }
               } else {
                  currentProblem = nextProblem;
                  ++problemIndex;
               }
            } else if (nextTask != null) {
               currentProblem = nextTask;
               ++taskIndex;
            }
         }

         return allProblems;
      }
   }

   public ClassFile[] getClassFiles() {
      ClassFile[] classFiles = new ClassFile[this.compiledTypes.size()];
      this.compiledTypes.values().toArray(classFiles);
      return classFiles;
   }

   public ICompilationUnit getCompilationUnit() {
      return this.compilationUnit;
   }

   public CategorizedProblem[] getErrors() {
      CategorizedProblem[] reportedProblems = this.getProblems();
      int errorCount = 0;

      for(int i = 0; i < this.problemCount; ++i) {
         if (reportedProblems[i].isError()) {
            ++errorCount;
         }
      }

      if (errorCount == this.problemCount) {
         return reportedProblems;
      } else {
         CategorizedProblem[] errors = new CategorizedProblem[errorCount];
         int index = 0;

         for(int i = 0; i < this.problemCount; ++i) {
            if (reportedProblems[i].isError()) {
               errors[index++] = reportedProblems[i];
            }
         }

         return errors;
      }
   }

   public char[] getFileName() {
      return this.fileName;
   }

   public int[] getLineSeparatorPositions() {
      return this.lineSeparatorPositions == null ? EMPTY_LINE_ENDS : this.lineSeparatorPositions;
   }

   public CategorizedProblem[] getProblems() {
      if (this.problems != null) {
         if (this.problemCount != this.problems.length) {
            System.arraycopy(this.problems, 0, this.problems = new CategorizedProblem[this.problemCount], 0, this.problemCount);
         }

         if (this.maxProblemPerUnit > 0 && this.problemCount > this.maxProblemPerUnit) {
            this.quickPrioritize(this.problems, 0, this.problemCount - 1);
            this.problemCount = this.maxProblemPerUnit;
            System.arraycopy(this.problems, 0, this.problems = new CategorizedProblem[this.problemCount], 0, this.problemCount);
         }

         Arrays.sort(this.problems, 0, this.problems.length, PROBLEM_COMPARATOR);
      }

      return this.problems;
   }

   public CategorizedProblem[] getCUProblems() {
      if (this.problems != null) {
         CategorizedProblem[] filteredProblems = new CategorizedProblem[this.problemCount];
         int keep = 0;

         for(int i = 0; i < this.problemCount; ++i) {
            CategorizedProblem problem = this.problems[i];
            if (problem.getID() != 536871825) {
               filteredProblems[keep++] = problem;
            } else if (this.compilationUnit != null && CharOperation.equals(this.compilationUnit.getMainTypeName(), TypeConstants.PACKAGE_INFO_NAME)) {
               filteredProblems[keep++] = problem;
            }
         }

         if (keep < this.problemCount) {
            System.arraycopy(filteredProblems, 0, filteredProblems = new CategorizedProblem[keep], 0, keep);
            this.problemCount = keep;
         }

         this.problems = filteredProblems;
         if (this.maxProblemPerUnit > 0 && this.problemCount > this.maxProblemPerUnit) {
            this.quickPrioritize(this.problems, 0, this.problemCount - 1);
            this.problemCount = this.maxProblemPerUnit;
            System.arraycopy(this.problems, 0, this.problems = new CategorizedProblem[this.problemCount], 0, this.problemCount);
         }

         Arrays.sort(this.problems, 0, this.problems.length, PROBLEM_COMPARATOR);
      }

      return this.problems;
   }

   public CategorizedProblem[] getTasks() {
      if (this.tasks != null) {
         if (this.taskCount != this.tasks.length) {
            System.arraycopy(this.tasks, 0, this.tasks = new CategorizedProblem[this.taskCount], 0, this.taskCount);
         }

         Arrays.sort(this.tasks, 0, this.tasks.length, PROBLEM_COMPARATOR);
      }

      return this.tasks;
   }

   public boolean hasErrors() {
      return this.numberOfErrors != 0;
   }

   public boolean hasMandatoryErrors() {
      return this.hasMandatoryErrors;
   }

   public boolean hasProblems() {
      return this.problemCount != 0;
   }

   public boolean hasTasks() {
      return this.taskCount != 0;
   }

   public boolean hasWarnings() {
      if (this.problems != null) {
         for(int i = 0; i < this.problemCount; ++i) {
            if (this.problems[i].isWarning()) {
               return true;
            }
         }
      }

      return false;
   }

   private void quickPrioritize(CategorizedProblem[] problemList, int left, int right) {
      if (left < right) {
         int original_left = left;
         int original_right = right;
         int mid = this.computePriority(problemList[left + (right - left) / 2]);

         do {
            while(this.computePriority(problemList[right]) < mid) {
               --right;
            }

            while(mid < this.computePriority(problemList[left])) {
               ++left;
            }

            if (left <= right) {
               CategorizedProblem tmp = problemList[left];
               problemList[left] = problemList[right];
               problemList[right] = tmp;
               ++left;
               --right;
            }
         } while(left <= right);

         if (original_left < right) {
            this.quickPrioritize(problemList, original_left, right);
         }

         if (left < original_right) {
            this.quickPrioritize(problemList, left, original_right);
         }

      }
   }

   public void recordPackageName(char[][] packName) {
      this.packageName = packName;
   }

   public void record(CategorizedProblem newProblem, ReferenceContext referenceContext) {
      this.record(newProblem, referenceContext, true);
   }

   public void record(CategorizedProblem newProblem, ReferenceContext referenceContext, boolean mandatoryError) {
      if (newProblem.getID() == 536871362) {
         this.recordTask(newProblem);
      } else {
         if (this.problemCount == 0) {
            this.problems = new CategorizedProblem[5];
         } else if (this.problemCount == this.problems.length) {
            System.arraycopy(this.problems, 0, this.problems = new CategorizedProblem[this.problemCount * 2], 0, this.problemCount);
         }

         this.problems[this.problemCount++] = newProblem;
         if (referenceContext != null) {
            if (this.problemsMap == null) {
               this.problemsMap = new HashMap(5);
            }

            if (this.firstErrors == null) {
               this.firstErrors = new HashSet(5);
            }

            if (newProblem.isError() && !referenceContext.hasErrors()) {
               this.firstErrors.add(newProblem);
            }

            this.problemsMap.put(newProblem, referenceContext);
         }

         if (newProblem.isError()) {
            ++this.numberOfErrors;
            if (mandatoryError) {
               this.hasMandatoryErrors = true;
            }

            if ((newProblem.getID() & 1073741824) != 0) {
               this.hasSyntaxError = true;
            }
         }

      }
   }

   ReferenceContext getContext(CategorizedProblem problem) {
      return problem != null ? (ReferenceContext)this.problemsMap.get(problem) : null;
   }

   public void record(char[] typeName, ClassFile classFile) {
      SourceTypeBinding sourceType = classFile.referenceBinding;
      if (sourceType != null && !sourceType.isLocalType() && sourceType.isHierarchyInconsistent()) {
         this.hasInconsistentToplevelHierarchies = true;
      }

      this.compiledTypes.put(typeName, classFile);
   }

   private void recordTask(CategorizedProblem newProblem) {
      if (this.taskCount == 0) {
         this.tasks = new CategorizedProblem[5];
      } else if (this.taskCount == this.tasks.length) {
         System.arraycopy(this.tasks, 0, this.tasks = new CategorizedProblem[this.taskCount * 2], 0, this.taskCount);
      }

      this.tasks[this.taskCount++] = newProblem;
   }

   public void removeProblem(CategorizedProblem problem) {
      if (this.problemsMap != null) {
         this.problemsMap.remove(problem);
      }

      if (this.firstErrors != null) {
         this.firstErrors.remove(problem);
      }

      if (problem.isError()) {
         --this.numberOfErrors;
      }

      --this.problemCount;
   }

   public CompilationResult tagAsAccepted() {
      this.hasBeenAccepted = true;
      this.problemsMap = null;
      this.firstErrors = null;
      return this;
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      if (this.fileName != null) {
         buffer.append("Filename : ").append(this.fileName).append('\n');
      }

      if (this.compiledTypes != null) {
         buffer.append("COMPILED type(s)\t\n");
         Iterator keys = this.compiledTypes.keySet().iterator();

         while(keys.hasNext()) {
            char[] typeName = (char[])keys.next();
            buffer.append("\t - ").append(typeName).append('\n');
         }
      } else {
         buffer.append("No COMPILED type\n");
      }

      if (this.problems != null) {
         buffer.append(this.problemCount).append(" PROBLEM(s) detected \n");

         for(int i = 0; i < this.problemCount; ++i) {
            buffer.append("\t - ").append(this.problems[i]).append('\n');
         }
      } else {
         buffer.append("No PROBLEM\n");
      }

      return buffer.toString();
   }
}
