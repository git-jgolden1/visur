package com.ple.visur;

public class DeleteCursorQuantumOp implements Operator {
  @Override
  public void execute(Object opInfo) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int span = emc.getSpan();
    if(span > 0) {
      int startBound = emc.getCursorQuantumStart();
      int endBound = emc.getCursorQuantumEnd();
      String editorContent = emc.getEditorContent();
      String editorContentBeforeDeletedPortion = editorContent.substring(0, startBound);
      String editorContentAfterDeletedPortion = editorContent.substring(endBound, editorContent.length());
      String resultingEditorContent = editorContentBeforeDeletedPortion + editorContentAfterDeletedPortion;
      emc.putEditorContent(resultingEditorContent);
      int ca = emc.getCA();
      if(ca > resultingEditorContent.length()) {
        ca = resultingEditorContent.length();
        emc.putCA(ca);
      }
      if(ca < emc.getCanvasStart()) {
        emc.putCanvasStart(ca);
        emc.putFY(emc.getCY());
      }
      Quantum cursorQuantum = emc.getCursorQuantum();
      int[] bounds = cursorQuantum.getBoundaries(ca, emc.getNextLineIndices(), span, false);
      emc.putCursorQuantumStartAndScroll(bounds[0]);
      emc.putCursorQuantumEndAndScroll(bounds[1]);
    }
  }
}
