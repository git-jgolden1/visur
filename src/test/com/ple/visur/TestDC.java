package com.ple.visur;

import CursorPositionDC.*;
import DataClass.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TestDC {
  CursorPositionDCHolder cursorPositionDCHolder = CursorPositionDCHolder.make();
  final CompoundDataClassBrick initialCursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);

  @Test void cdcMakeBrick() {
    //after running cursorPositionDC.makeBrick (the only method that directly gets called in TestDC), cursorPositionDCB is incomplete
    assertFalse(initialCursorPositionDCB.isComplete());

  }

//  @Test void pdcbMakeBrick() {
//    //niDC.makeBrick works
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, initialCursorPositionDCB, cursorPositionDCHolder);
//    assertEquals(cursorPositionDCHolder.wholeNumberListDC, niDCB.getPDC());
//    assertEquals(newlineIndices, niDCB.getDFB().getVal());
//    assertEquals(cursorPositionDCHolder.cursorPositionDC, niDCB.getOuter().getCDC());
//    assertEquals("ni", niDCB.getName());
//
//    //wholeNumberDC.makeBrick works for cxDCB
//    int cx = 0;
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    assertEquals(cursorPositionDCHolder.wholeNumberDC, cxDCB.getPDC());
//    assertEquals(cx, cxDCB.getDFB().getVal());
//    assertEquals(cursorPositionDCHolder.wholePairDC, cxDCB.getOuter().getCDC());
//    assertEquals("cx", cxDCB.getName());
//
//    //wholeNumberDC.makeBrick works for cyDCB
//    int cy = 1;
//    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    assertEquals(cursorPositionDCHolder.wholeNumberDC, cyDCB.getPDC());
//    assertEquals(cy, cyDCB.getDFB().getVal());
//    assertEquals(cursorPositionDCHolder.wholePairDC, cyDCB.getOuter().getCDC());
//    assertEquals("cy", cyDCB.getName());
//
//    //wholeNumberDC.makeBrick works for caDCB
//    int ca = 2;
//    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", ca, cxcycaDCB, cursorPositionDCHolder);
//    assertEquals(cursorPositionDCHolder.wholeNumberDC, caDCB.getPDC());
//    assertEquals(ca, caDCB.getDFB().getVal());
//    assertEquals(cursorPositionDCHolder.cxcycaDC, caDCB.getOuter().getCDC());
//    assertEquals("ca", caDCB.getName());
//  }

//  @Test void dcbRemove() {
//    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", 12, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", 1, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 1, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//
//    //cxDCB.remove works when cxDCB was set
//    Result r = cxDCB.remove();
//    assertNull(cxDCB.getDFB());
//    assertNull(r.getError());
//
//    //cyDCB.remove works when cyDCB was unset
//    cyDCB.remove();
//    r = cyDCB.remove();
//    assertNull(cyDCB.getDFB());
//    assertNull(r.getError());
//
//    //cxcyDCB.remove works when complete
//    int cx = 10;
//    int cy = 1;
//    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    assertTrue(cxcyDCB.isComplete());
//    cxcyDCB.remove();
//    assertNull(cxDCB.getDFB());
//    assertNull(cyDCB.getDFB());
//    assertFalse(cxcyDCB.isComplete());
//
//    //cxcyDCB.remove works when incomplete
//    cxcyDCB.remove();
//    assertNull(cxDCB.getDFB());
//    assertNull(cyDCB.getDFB());
//    assertFalse(cxcyDCB.isComplete());
//
//    //caDCB.remove works when complete
//    assertTrue(caDCB.isComplete());
//    caDCB.remove();
//    assertNull(caDCB.getDFB()); //in future tests, this condition will be replaced by the next line, but this ensures they both work the same
//    assertFalse(caDCB.isComplete());
//
//    //cxcycaDCB.remove works when complete [cxcy complete, ca incomplete]
//    cx = 0;
//    cy = 2;
//    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    assertFalse(caDCB.isComplete());
//    assertTrue(cxcyDCB.isComplete());
//    assertTrue(cxcycaDCB.isComplete());
//    assertTrue(niDCB.isComplete());
//    cxcycaDCB.remove(); //actual test post-setup starts here
//    assertFalse(caDCB.isComplete());
//    assertFalse(cxcyDCB.isComplete());
//    assertFalse(cxcycaDCB.isComplete());
//    assertTrue(niDCB.isComplete());
//
//    //niDCB.remove works when niDCB is set
//    assertTrue(niDCB.isComplete());
//    niDCB.remove();
//    assertFalse(niDCB.isComplete());
//
//    //cursorPositionDCB.remove works when cursorPositionDCB is set
//    cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition", null, cursorPositionDCHolder);
//    newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", 12, cxcyDCB, cursorPositionDCHolder);
//    cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", 1, cxcyDCB, cursorPositionDCHolder);
//    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 1, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB); cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//    assertTrue(cursorPositionDCB.isComplete());
//    cursorPositionDCB.remove(); //actual test begins here
//    assertFalse(niDCB.isComplete());
//    assertFalse(cxcycaDCB.isComplete());
//    assertFalse(cxcyDCB.isComplete());
//    assertFalse(cxDCB.isComplete());
//    assertFalse(cyDCB.isComplete());
//    assertFalse(caDCB.isComplete());
//    assertFalse(cursorPositionDCB.isComplete());
//
//    //cursorPositionDCB.remove works when cursorPositionDCB is unset
//    cursorPositionDCB.remove();
//    assertFalse(cursorPositionDCB.isComplete());
//  }

//  @Test void pdcbGet() {
//    int cx = 3;
//    int cy = 1;
//    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition",  null, cursorPositionDCHolder);
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", cx, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", cy, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", null, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//
//    //cxDCB.get produces accurate cx val
//    Result r = cxDCB.get();
//    assertEquals(cx, r.getVal());
//
//    //cyDCB.get accurately fetches an unset dcb, and includes a result with an error message
//    cyDCB.remove();
//    r = cyDCB.get();
//    assertNotNull(r.getError());
//    assertFalse(cyDCB.isComplete());
//
//    //caDCB.get() returns null when cxcycaDCB is complete but ca is unset
//    r = caDCB.get();
//    assertNull(r.getVal());
//    assertNotNull(r.getError());
//
//    //caDCB.get() returns ca int val when cxcycaDCB is complete and ca is set
//    cxcyDCB.remove();
//    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 10, cxcycaDCB, cursorPositionDCHolder);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    assertFalse(cxcyDCB.isComplete());
//    assertTrue(caDCB.isComplete());
//    assertTrue(cxcycaDCB.isComplete());
//    r = caDCB.get(); //actual test
//    assertEquals(10, r.getVal());
//    assertNull(r.getError());
//
//    //niDCB.get() returns newlineIndices arrayList exactly as declared when niDCB is complete
//    assertTrue(niDCB.isComplete());
//    r = niDCB.get(); //actual test
//    assertEquals(newlineIndices, r.getVal());
//    assertNull(r.getError());
//
//  }

//  @Test void dcbPutSafe() {
//    int cx = 3;
//    int cy = 1;
//    CompoundDataClassBrick cursorPositionDCB = cursorPositionDCHolder.cursorPositionDC.makeBrick("cursorPosition",  null, cursorPositionDCHolder);
//    ArrayList<Integer> newlineIndices = new ArrayList<>();
//    newlineIndices.add(11);
//    newlineIndices.add(24);
//    PrimitiveDataClassBrick niDCB = cursorPositionDCHolder.wholeNumberListDC.makeBrick("ni", newlineIndices, cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcycaDCB = cursorPositionDCHolder.cxcycaDC.makeBrick("cxcyca", cursorPositionDCB, cursorPositionDCHolder);
//    CompoundDataClassBrick cxcyDCB = cursorPositionDCHolder.wholePairDC.makeBrick("cxcy", cxcycaDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cxDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cx", null, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick cyDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("cy", null, cxcyDCB, cursorPositionDCHolder);
//    PrimitiveDataClassBrick caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", null, cxcycaDCB, cursorPositionDCHolder);
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    cursorPositionDCB.putInner(niDCB);
//    cursorPositionDCB.putInner(cxcycaDCB);
//
//    //cxDCB.putSafe(cx) when cxcycaDCB is not complete (conflicts method is not used)
//    assertFalse(cxcycaDCB.isComplete());
//    Result<PrimitiveDataClassBrick> r = cxDCB.putSafe(cursorPositionDCHolder.wholeNumberDC, "cx", 12, cxcyDCB, cursorPositionDCHolder); //test
//    assertNull(r.getError());
//    cxDCB = r.getVal();
//    assertEquals(12, cxDCB.getVal());
//
//    //cyDCB.putSafe(cy) when cxcycaDCB is not complete (conflicts method is not used)
//    assertFalse(caDCB.getOuter().isComplete());
//    r = cyDCB.putSafe(cursorPositionDCHolder.wholeNumberDC, "cy", 1, cxcyDCB, cursorPositionDCHolder); //test
//    assertNull(r.getError());
//    cyDCB = r.getVal();
//    assertEquals(1, cyDCB.getVal());
//
//    //caDCB.putSafe(ca) when cxcycaDCB is complete and cxcyca are both set [conflicts method works]
//    caDCB = cursorPositionDCHolder.wholeNumberDC.makeBrick("ca", 24, cxcycaDCB, cursorPositionDCHolder);
//    assertTrue(cxDCB.isComplete());
//    assertTrue(cyDCB.isComplete());
//    assertTrue(caDCB.isComplete());
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    assertTrue(cxcycaDCB.isComplete());
//    r = caDCB.putSafe(cursorPositionDCHolder.wholeNumberDC, "ca", 24, cxcycaDCB, cursorPositionDCHolder); //test
//    assertNull(r.getError());
//    caDCB = r.getVal();
//    assertEquals(24, caDCB.getVal());
//
//    //cxDCB.putSafe(cx) and cyDCB.putSafe(cy) when cxcycaDCB is complete and all inners are set without conflicts
//    cxcyDCB.remove();
//    cxcycaDCB.putInner(cxcyDCB);
//    assertFalse(cxDCB.isComplete());
//    assertFalse(cyDCB.isComplete());
//    assertTrue(caDCB.isComplete());
//    cxcyDCB.putInner(cxDCB);
//    cxcyDCB.putInner(cyDCB);
//    cxcycaDCB.putInner(cxcyDCB);
//    cxcycaDCB.putInner(caDCB);
//    assertTrue(cxcycaDCB.isComplete());
//    r = cxDCB.putSafe(cursorPositionDCHolder.wholeNumberDC, "cx", 12, cxcyDCB, cursorPositionDCHolder); //test 1/2
//    assertNull(r.getError());
//    cxDCB = r.getVal();
//    r = cyDCB.putSafe(cursorPositionDCHolder.wholeNumberDC, "cy", 1, cxcyDCB, cursorPositionDCHolder); //test 2/2
//    assertNull(r.getError());
//    cyDCB = r.getVal();
//    assertEquals(12, cxDCB.getVal());
//    assertEquals(1, cyDCB.getVal());
//
//    //caDCB.putSafe(ca) does NOT work when cxcycaDCB is complete and all inners are set WITH conflicts
//    assertTrue(cxDCB.isComplete());
//    assertTrue(cyDCB.isComplete());
//    assertTrue(caDCB.isComplete());
//    assertTrue(cxcycaDCB.isComplete());
//    r = caDCB.putSafe(cursorPositionDCHolder.wholeNumberDC, "ca", 25, cxcycaDCB, cursorPositionDCHolder); //test
//    assertNotNull(r.getError());
//    caDCB = r.getVal();
//    assertNotEquals(25, caDCB.getVal());
//    assertEquals(24, caDCB.getVal());
//
//  }

}
