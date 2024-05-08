package com.ple.visur;

public class ChangeQuantumOp implements Operator {

  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler ems = ServiceHolder.editorModelCoupler;
    String quantumName = (String)ems.getExecutionDataStack().pop();
    String quantumNameWithoutQuotes = quantumName.substring(1, quantumName.length() - 1);
    Quantum targetQuantum = ems.getQuantumMap().get(quantumNameWithoutQuotes);
    int[] bounds = targetQuantum.getBoundaries(ems.getEditorContent(), ems.getNewlineIndices(), false);
    ems.putQuantumStart(bounds[0]);
    ems.putQuantumEnd(bounds[1]);
    ems.putCurrentQuantum(targetQuantum);
  }

}
