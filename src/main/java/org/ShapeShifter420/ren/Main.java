package org.ShapeShifter420.ren;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

import org.ShapeShifter420.ren.Interfaces.IFoundString;
import org.ShapeShifter420.ren.Interfaces.IReader;
import org.ShapeShifter420.ren.Interfaces.IStringGetter;
import org.ShapeShifter420.ren.dataclasses.RawResult;
import org.yaml.snakeyaml.Yaml;

public class Main {

    public static void main(String[] args) throws IOException {
        int column;
        if (args.length >0){
            column = Integer.parseInt(args[0]);
        }
        else{
            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream("application.yml");
            Map<String, Object> yamlMap = yaml.load(inputStream);
            column = (Integer) yamlMap.get("column");
        }
        System.out.print("Запрос: ");
        Scanner console = new Scanner(System.in);
        String word = console.nextLine();
        Path path = Path.of("airports.dat");
        FileChannel channel1 = FileChannel.open(path, StandardOpenOption.READ);
        IReader reader1 = new AReader(channel1, word, column);

        long startTime = System.currentTimeMillis();
        List<IFoundString> results = reader1.run();
        long endtTime = System.currentTimeMillis();

        IStringGetter getter = new StringGetter(channel1);
        List<RawResult> rawResults = new ArrayList<>();
        for (IFoundString fs : results) {
            rawResults.add(getter.getString(fs));
        }
        Collections.sort(rawResults);
        for (RawResult r : rawResults) {
            System.out.println(r);
        }
        System.out.printf("Количество результатов: %d%n",rawResults.size());
        System.out.printf("Время мс: %d%n",endtTime - startTime);
    }
}


