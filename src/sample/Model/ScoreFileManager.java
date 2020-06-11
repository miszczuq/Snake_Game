package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ListView;
import sample.view.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreFileManager {
    private static final String PATH = "src\\sample\\scores.txt";

    public static void addScore(String name, int score){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(PATH,true));

            bufferedWriter.write(name);
            bufferedWriter.write(' ');
            bufferedWriter.write(Integer.toString(score));
            bufferedWriter.newLine();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ListView getScoreList(){
        ObservableList<Score> names = FXCollections.observableArrayList();
        ListView<Score> listView = new ListView<>();

        try {
            FileReader reader = new FileReader(PATH);
            BufferedReader br = new BufferedReader(reader);

            String line;
            Pattern pattern = Pattern.compile("(\\w+) (\\d+)");
            Matcher matcher ;

            while ((line = br.readLine())!= null) {
                matcher = pattern.matcher(line);
                if(matcher.find()) {
                   names.add(new Score(matcher.group(1), Integer.parseInt(matcher.group(2))));
                }
                SortedList<Score> list= new SortedList(names);
                listView.setItems(list);
                list.setComparator(new Comparator<Score>() {
                    @Override
                    public int compare(Score s1, Score s2) {
                        return s2.getScore() - s1.getScore();
                    }
                });
            }

            br.close();
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }

        return listView;
    }
}
