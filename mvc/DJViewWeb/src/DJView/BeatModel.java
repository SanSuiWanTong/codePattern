package DJView;

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

public class BeatModel implements BeatModelInterface,MetaEventListener{
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
		setBPM(85);
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
