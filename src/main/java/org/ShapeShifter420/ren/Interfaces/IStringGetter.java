package org.ShapeShifter420.ren.Interfaces;

import org.ShapeShifter420.ren.dataclasses.FoundString;
import org.ShapeShifter420.ren.dataclasses.RawResult;

import java.io.IOException;

public interface IStringGetter {
    RawResult getString(IFoundString fs) throws IOException;
}
