import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BeatBox extends JFrame {

    private Button start;
    private Button stop;
    private Button tempUp;
    private Button tempDown;
    private Sequencer sequencer;
    private Sequence sequence;
    private Track track;
    private int [] instruments={35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};
    private ArrayList<JCheckBox> checkList;

    private GridLayout gridLayout;
    private Box boxName;
    private labelString []lbString;
    private JPanel panelCheck;
    private JCheckBox c;
    private Name name=new Name();
    private Box boxBtn;
    private ImageIcon icon;
    public BeatBox(){
        super("Cyber Beat Box");
        super.setVisible(true);
        icon=new ImageIcon("music.png");
        super.getContentPane().setBackground(new Color(204,255,229));
        super.setIconImage(icon.getImage());
        super.setSize(1000,665);
        super.setLayout(new BorderLayout());
        super.setDefaultCloseOperation(3);
        gridLayout=new GridLayout(16,16);
        boxBtn=new Box(BoxLayout.Y_AXIS);
        boxName=new Box(BoxLayout.Y_AXIS);
        //boxBtn.setLayout(new FlowLayout(5));
        lbString=new labelString[name.nameLength()];
        start=new Button(100,75,"Start");
        stop=new Button(100,75,"Stop");
        tempUp=new Button(100,75,"Temp Up");
        tempDown=new Button(100,75,"Temp Down");
        boxBtn.add(start);
        boxBtn.add(stop);
        boxBtn.add(tempUp);
        boxBtn.add(tempDown);
        super.add(boxBtn,BorderLayout.LINE_END);
        for(int i=0;i<name.nameLength();i++){
            lbString[i]=new labelString(name.nameString(i));
            boxName.add(lbString[i]);
        }
        super.add(boxName,BorderLayout.LINE_START);
        checkList=new ArrayList<JCheckBox>();
        panelCheck=new JPanel();
        panelCheck.setLayout(gridLayout);
        panelCheck.setBackground(new Color(204,255,229));
        super.add(panelCheck,BorderLayout.CENTER);
        for(int i=0;i<256;i++){
            c=new JCheckBox();
            checkList.add(c);
            panelCheck.add(checkList.get(i));
        }
        SetUpMidi();
        start.addActionListener(new StartButton());
        stop.addActionListener(new StopButton());
        tempUp.addActionListener(new UpButton());
        tempDown.addActionListener(new DownButton());
    }

    public void SetUpMidi(){
        try{
            sequencer= (Sequencer) MidiSystem.getSequencer();
            sequencer.open();
            sequence=new Sequence(Sequence.PPQ,4);
            track=sequence.createTrack();
            sequencer.setTempoInBPM(120);


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void buildTrackAndStart(){
        int []tracklist=null;
        sequence.deleteTrack(track);
        track= sequence.createTrack();
        for(int i=0;i<16;i++){
            tracklist=new int[16];

            int key=instruments[i];
            for(int j=0;j<16;j++){
                JCheckBox jc=(JCheckBox) checkList.get(j+(16*i));
                if(jc.isSelected()){
                    tracklist[j]=key;
                }else{
                    tracklist[j]=0;
                }
            }
            makeTracks(tracklist);
            track.add(makeEvent(176,1,127,0,16));
        }
        try{
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
            sequencer.start();
            sequencer.setTempoInBPM(120);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void makeTracks(int [] list){
        for(int i=0;i<16;i++){
            int key=list[i];
            if(key!=0){
                track.add(makeEvent(144,9,key,100,i));
                track.add(makeEvent(128,9,key,100,i+1));
            }
        }
    }

    public MidiEvent makeEvent(int comd, int chan, int one, int two, int tick){
        MidiEvent event=null;
        try{
            ShortMessage a=new ShortMessage();
            a.setMessage(comd,chan,one,two);
            event=new MidiEvent(a,tick);
        }catch(Exception e){
            e.printStackTrace();
        };
        return event;
    }
    public class StartButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            buildTrackAndStart();
        }
    }

    public class StopButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            sequencer.stop();
        }
    }
    public class UpButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor= sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor*1.03));
        }
    }

    public class DownButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            float tempoFactor= sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor*0.97));
        }
    }
}
