package com.ple.visur;

import java.util.ArrayList;

public class LineBoundCalculator {

  public static int[] getLong(int cy, ArrayList<Integer> ni) {
    int[] longBounds = new int[2];
    longBounds[0] = cy > 0 ? ni.get(cy - 1) : 0;
    longBounds[1] = cy < ni.size() ? ni.get(cy) : ni.get(ni.size() - 1);
    return longBounds;
  }

  public static int[] getShort(int cx, int cy, ArrayList<Integer> ni) {
    EditorModelCoupler emc = ServiceHolder.editorModelCoupler;
    int[] shortBounds = new int[2];
    int canvasWidth = emc.getCanvasWidth();
    shortBounds[0] = getShortLineStart(canvasWidth, cx);
    shortBounds[1] = getShortLineEnd(shortBounds[0], canvasWidth, cx, cy, ni);
    return shortBounds;
  }

  private static int getShortLineStart(int cw, int cx) {
    int shortLineStart = 0;
    for(int i = cx; i > 0; i--) {
      if (i % cw == 0) {
        shortLineStart = i;
        break;
      }
    }
    return shortLineStart;
  }

  private static int getShortLineEnd(int shortLineStart, int cw, int cx, int cy, ArrayList<Integer> ni) {
    int shortLineEnd = ni.get(cy) - 1;
    if(shortLineEnd != shortLineStart) {
      for (int i = cx + 1; i < ni.get(cy); i++) {
        if (i % cw == 0) {
          shortLineEnd = i + 1;
          break;
        }
      }
    }
    return shortLineEnd;
  }

}
