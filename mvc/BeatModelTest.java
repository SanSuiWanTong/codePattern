//beatModel java
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.sound.midi.*;

interface BeatObserver {
	void updateBeat();
}
interface BPMObserver {
	void updateBPM();
}
interface BeatModelInterface{
	void initialize();
	void on();
	void off();
	
	void setBPM(int bpm);
	int  getBPM();
	
	void registerObserver(BeatObserver o);
	void removeObserver  (BeatObserver o);
	
	void registerObserver(BPMObserver o);
	void removeObserver  (BPMObserver o);
}
interface ControllerInterface{
	void start();
	void stop();
	void increaseBPM();
	void decreaseBPM();
	void setBPM(int bpm);
}
class BeatModel implements BeatModelInterface,MetaEventListener{
	Sequencer sequencer;
	Sequence sequence;
	Track track;
	
	ArrayList<BeatObserver> beatObservers = new ArrayList<BeatObserver>();
	ArrayList<BPMObserver> bpmObservers = new ArrayList<BPMObserver>();
	int bpm = 90;
	
	public void initialize(){
		setUpMidi();
		buildTrackAndStart();
	}
	public void on(){
	    System.out.println("Starting the sequencer");
		sequencer.start();
		setBPM(90);
	}
	public void off(){
		setBPM(0);
		sequencer.stop();
	}
	public void setBPM(int bpm){
		this.bpm = bpm;
		sequencer.setTempoInBPM(getBPM());
		notifyBPMObservers();
	}
	public int getBPM(){
		return bpm;
	}

	void beatEvent() {
		notifyBeatObservers();
	}
	public void registerObserver(BeatObserver o) {
		beatObservers.add(o);
	}

	public void notifyBeatObservers() {
		for(int i = 0; i < beatObservers.size(); i++) {
			BeatObserver observer = (BeatObserver)beatObservers.get(i);
			observer.updateBeat();
		}
	}

	public void registerObserver(BPMObserver o) {
		bpmObservers.add(o);
	}

	public void notifyBPMObservers() {
		for(int i = 0; i < bpmObservers.size(); i++) {
			BPMObserver observer = (BPMObserver)bpmObservers.get(i);
			observer.updateBPM();
		}
	}
	public void removeObserver(BPMObserver o){
		int i = bpmObservers.indexOf(o);
		if(i >= 0)
			bpmObservers.remove(i);
	}
	public void removeObserver(BeatObserver o) {
		int i = beatObservers.indexOf(o);
		if (i >= 0) {
			beatObservers.remove(i);
		}
	}
	
	public void meta(MetaMessage message) {
		
		if (message.getType() == 47) {
			beatEvent();
			sequencer.setMicrosecondPosition(0);    //without it do not work
			sequencer.start();
			setBPM(getBPM());
		}
	}
	
	public void setUpMidi(){
		try{
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			sequencer.addMetaEventListener(this);
			sequence = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(getBPM());
			//sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);//it continuous but not receive event
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void buildTrackAndStart() {
		int[] trackList = {35, 0, 46, 0};

		sequence.deleteTrack(null);
		track = sequence.createTrack();

		makeTracks(trackList);
		track.add(makeEvent(192,9,1,0,4));      
		try {
			sequencer.setSequence(sequence);                    
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void makeTracks(int[] list) {        

		for (int i = 0; i < list.length; i++) {
			int key = list[i];

			if (key != 0) {
				track.add(makeEvent(144,9,key, 100, i));
				track.add(makeEvent(128,9,key, 100, i+1));
			}
		}
	}
	public  MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
		MidiEvent event = null;
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(comd, chan, one, two);
			event = new MidiEvent(a, tick);
		} catch(Exception e) {
			e.printStackTrace(); 
		}
		return event;
	}
}

class DJView implements ActionListener,BeatObserver,BPMObserver{
	BeatModelInterface model;
	ControllerInterface controller;
	
	JPanel viewPanel;
	JPanel controlPanel;
	
	BeatBar beatBar;
	JLabel bpmOutputLabel;
	JLabel bpmLabel;
	
	JTextField bpmTextField;
	JButton setBPMButton;
	JButton increaseBPMButton;
	JButton decreaseBPMButton;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem startMenuItem;
	JMenuItem stopMenuItem;
	
	JFrame controlFrame;
	JFrame viewFrame;
		
	public DJView(ControllerInterface controller,BeatModelInterface model){
		this.controller = controller;
		this.model = model;
		model.registerObserver((BeatObserver)this);
		model.registerObserver((BPMObserver)this);
	}
	public void createControls(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		controlFrame = new JFrame("Control");
		controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controlFrame.setSize(new Dimension(100,80));
		
		menuBar = new JMenuBar();
		menu = new JMenu("DJ Control");
		
		startMenuItem = new JMenuItem("Start");
		menu.add(startMenuItem);
		startMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				controller.start();
			}
		});
		
		stopMenuItem = new JMenuItem("Stop");
		menu.add(stopMenuItem);
		stopMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				controller.stop();
			}
		});
		
		menuBar.add(menu);
		controlFrame.setJMenuBar(menuBar);
		
		setBPMButton = new JButton("Set");
		setBPMButton.setSize(new Dimension(10,40));
		increaseBPMButton = new JButton(">>");
		decreaseBPMButton = new JButton("<<");
		setBPMButton.addActionListener(this);
		increaseBPMButton.addActionListener(this);
		decreaseBPMButton.addActionListener(this);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(decreaseBPMButton);
		buttonPanel.add(increaseBPMButton);
		
		bpmTextField = new JTextField(2);
		bpmLabel = new JLabel("Enter BPM:",SwingConstants.RIGHT);
		JPanel enterPanel = new JPanel(new GridLayout(1, 2));
        enterPanel.add(bpmLabel);
        enterPanel.add(bpmTextField);
		
		JPanel insideControlPanel = new JPanel(new GridLayout(3, 1));
        insideControlPanel.add(enterPanel);
        insideControlPanel.add(setBPMButton);
        insideControlPanel.add(buttonPanel);
		
		controlPanel = new JPanel(new GridLayout(1,2));
        controlPanel.add(insideControlPanel);
		
		bpmLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        bpmOutputLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        controlFrame.getRootPane().setDefaultButton(setBPMButton);
        controlFrame.getContentPane().add(controlPanel, BorderLayout.CENTER);

        controlFrame.pack();
        controlFrame.setVisible(true);
	}
	public void enableStopMenuItem(){
		stopMenuItem.setEnabled(true);
	}
	public void disableStopMenuItem(){
		stopMenuItem.setEnabled(false);
	}
	public void enableStartMenuItem(){
		startMenuItem.setEnabled(true);
	}
	public void disableStartMenuItem(){
		startMenuItem.setEnabled(false);
	}
	public void actionPerformed(ActionEvent event){
		if(event.getSource() == setBPMButton){
			int bpm = Integer.parseInt(bpmTextField.getText());
			controller.setBPM(bpm);
		}else if(event.getSource() == increaseBPMButton){
			controller.increaseBPM();
		}else if(event.getSource() == decreaseBPMButton){
			controller.decreaseBPM();
		}
	}
	public void createView(){
		viewFrame = new JFrame("View");
		viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		viewFrame.setSize(new Dimension(100,80));
		
		bpmOutputLabel = new JLabel("offline",SwingConstants.CENTER);
		
		beatBar = new BeatBar();
		beatBar.setValue(60);
		
		JPanel bpmPanel = new JPanel(new GridLayout(2,1));
		bpmPanel.add(beatBar);
		bpmPanel.add(bpmOutputLabel);
		
		viewPanel = new JPanel(new GridLayout(1,2));
		viewPanel.add(bpmPanel);
		
		viewFrame.getContentPane().add(viewPanel,BorderLayout.CENTER);
		viewFrame.pack();
		viewFrame.setVisible(true);
	}
	public void updateBPM(){
		bpmOutputLabel.setText("Current BPM: " + model.getBPM());
	}
	public void updateBeat(){
		beatBar.setValue(100);
	}
}

class BeatController implements ControllerInterface{
	BeatModelInterface model;
	DJView view;
	public BeatController(BeatModelInterface model){
		this.model = model;
		view = new DJView(this,model);
		view.createView();
		view.createControls();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
		model.initialize();
	}
	public void start(){
		model.on();
		view.disableStartMenuItem();
		view.enableStopMenuItem();
	}
	public void stop(){
		model.off();
		view.disableStopMenuItem();
		view.enableStartMenuItem();
	}
	public void increaseBPM(){
		model.setBPM(model.getBPM()+1);
	}
	public void decreaseBPM(){
		model.setBPM(model.getBPM()-1);
	}
	public void setBPM(int bpm){
		model.setBPM(bpm);
	}
}
//BeatBar the code in the book may be not work,change a little above
class BeatBar extends JProgressBar implements Runnable{
	private static final long serialVersionUID = 2L;
	JProgressBar progressBar;
	Thread thread;
	public BeatBar(){
		thread = new Thread(this);
		setMaximum(100);
		thread.start();
	}
	public void run(){
		while(true){
			int value = getValue();
			value = (int)(value * 0.75);
			setValue(value);
			repaint();
			try{
				Thread.sleep(50);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

public class BeatModelTest{
	public static void main(String[] args){
		BeatModelInterface model = new BeatModel();
		ControllerInterface controller = new BeatController(model);
	}
}
