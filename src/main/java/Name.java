import java.util.ArrayList;
import java.util.Arrays;

public class Name {

    private String []name={"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Hand Clap","High Tom","Hi Bongo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
    Name(){};

    public  String []nameArrays(){
        return name;
    }
    public String nameString(int index){
        return name[index];
    }
    public int nameLength(){
        return name.length;
    }
}
