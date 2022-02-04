package org.ShapeShifter420.ren.dataclasses;

import org.ShapeShifter420.ren.Interfaces.IFoundString;

public class FoundString implements IFoundString {
    private final long StartPos;
    private final int FindPos;
    private final long EndPos;
    public FoundString(long startpos, int findpos, long endpos){
        StartPos = startpos;
        FindPos = findpos;
        EndPos = endpos;
    }

    public int getFindPos() {
        return FindPos;
    }

    public long getStartPos() {
        return StartPos;
    }

    public long getEndPos() {
        return EndPos;
    }
}
