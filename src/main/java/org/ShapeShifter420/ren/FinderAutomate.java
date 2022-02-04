package org.ShapeShifter420.ren;

import org.ShapeShifter420.ren.Interfaces.IAutomate;
import org.ShapeShifter420.ren.enums.ReturnSignalsEnum;
import org.ShapeShifter420.ren.enums.StatesEnum;

public class FinderAutomate implements IAutomate {
    StatesEnum State = StatesEnum.ZeroState;
    byte [] Word;
    int  WordIndex = 0;
    int Column = 0;
    final int TargetColumn;
    public FinderAutomate(byte[] word,int column){
        Word = word;
        TargetColumn = column;
        if (column == 0) State = StatesEnum.FirstState;
    }
    public ReturnSignalsEnum read(byte c){
        if (c =='\n'){State =StatesEnum.ZeroState;Column = 0;return ReturnSignalsEnum.NewLineSignal;}
        switch (State.ordinal()) {
            case(0):
                zeroState(c);
                break;
            case(1):
                firstState(c);
                break;
            case(2):
                State = StatesEnum.ThirdState;
                return ReturnSignalsEnum.FoundSignal;
        }
        return ReturnSignalsEnum.ZeroSignal;
    }

    private void zeroState(byte c){
        if (c == ','){
            Column += 1;
        }
        if (Column == TargetColumn) State =StatesEnum.FirstState;
    }

    private void firstState(byte c){
        if (WordIndex == 0 && c == '\"')
            return;
        if (Word[WordIndex] == c){
            WordIndex+=1;
            if (WordIndex == Word.length){
                WordIndex = 0;
                State = StatesEnum.SecondState;
            }
        }
        else{
            WordIndex = 0;
            State = StatesEnum.ThirdState;
        }
    }

}
