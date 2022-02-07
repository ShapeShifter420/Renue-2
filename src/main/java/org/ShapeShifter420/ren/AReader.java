package org.ShapeShifter420.ren;

import org.ShapeShifter420.ren.Interfaces.IAutomate;
import org.ShapeShifter420.ren.Interfaces.IReader;
import org.ShapeShifter420.ren.Interfaces.IFoundString;
import org.ShapeShifter420.ren.dataclasses.FoundString;
import org.ShapeShifter420.ren.enums.ReturnSignalsEnum;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AReader implements IReader {
    FileChannel fileChannel; String word; int column;
    public AReader(FileChannel fileChannel, String word, int column){
        this.fileChannel = fileChannel;
        this.word = word;
        this.column = column;
    }
    public List<IFoundString> run() {
        List<IFoundString> result = new ArrayList<>();
        int bufferSize = 8192;
        try {
            if (bufferSize > fileChannel.size()) {
                bufferSize = (int) fileChannel.size();
            }
        ByteBuffer buff = ByteBuffer.allocate(bufferSize);
        IAutomate automate = new FinderAutomate(word.getBytes(StandardCharsets.UTF_8),column);
        byte[] buffarray;
        long offset = 0;
        ReturnSignalsEnum value;
        long startline;
        long endfind = -1;
        int istart;
        int iend =0;
        int iendfind = 0;
        long endline = 0;
        while (fileChannel.read(buff) > 0) {
            buffarray = buff.array();
            for(int pos=0;pos < bufferSize;pos++ )
            {
                value = automate.read(buffarray[pos]);
                if (value == ReturnSignalsEnum.NewLineSignal) {
                    startline = endline;
                    endline = pos + offset;
                    istart = iend;
                    iend = pos;

                    if (endfind != -1){
                        if (startline / offset != endline / offset)
                            result.add(new FoundString(startline,(int)(endfind-startline-word.length()),endline));
                        else
                            result.add(new FoundString((iendfind-istart-word.length()), Arrays.copyOfRange(buffarray, istart, iend)));
                        endfind = -1;
                    }

                }
                else if (value == ReturnSignalsEnum.FoundSignal){
                    endfind = pos+offset;
                    iendfind = pos;
                }
            }
            offset += buff.position();
            buff.clear();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;

    }
}
