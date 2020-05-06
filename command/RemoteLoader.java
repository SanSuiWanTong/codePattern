//command java

interface Command{
	public void execute();
	public void undo();
}
//NoCommand
class NoCommand implements Command{
	public void execute(){
		System.out.println("do nothing");
	}
	public void undo(){}
}

//LightOnCommand
class LightOnCommand implements Command{
	Light light;
	public LightOnCommand(Light light){
		this.light = light;
	}
	public void execute(){
		light.on();
	}
	public void undo(){
		light.off();
	}
}
//StereoOnWithCDCommand
class StereoOnWithCDCommand implements Command{
	Stereo stereo;
	public StereoOnWithCDCommand(Stereo stereo){
		this.stereo = stereo;
	}
	public void execute(){
		stereo.on();
		stereo.setCD();
		stereo.setVolume(11);
	}
	public void undo(){ /* ... */ }
}

//
class RemoteControl{
	Command[] onCommands;
	Command[] offCommands;
	Command undoCommand;
	
	public RemoteControl(){
		onCommands  = new Command[7];
		offCommands = new Command[7];
		
		Command noCommand = new NoCommand();
		
		for(int i = 0; i < 7; i++){
			onCommands[i]  = noCommand;
			offCommands[i] = noCommand;
		}
		undoCommand = noCommand;
	}
	public void setCommand(int slot,Command onCommand,Command offCommand){
		if(null != onCommand)
			onCommands[slot]  = onCommand;
		if(null != offCommand)
			offCommands[slot] = offCommand;
	}
	public void onButtonPushed(int slot){
		onCommands[slot].execute();
		undoCommand = onCommands[slot];
	}
	public void offButtonPushed(int slot){
		offCommands[slot].execute();
		undoCommand = offCommands[slot];
	}
	public void undoButtonPushed(){
		undoCommand.undo();
	}
	public String toString(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("\n --- Remote Control ---\n");
		for(int i = 0; i < onCommands.length; i++){
			//not need if(onCommands[i] != null)
				stringBuffer.append("[slot " + i + " ] " + onCommands[i].getClass().getName());
			//if(offCommands[i] != null)
		        stringBuffer.append("  " + offCommands[i].getClass().getName() + "\n");
		}
		return stringBuffer.toString();
	}
}

//Light
class Light{
	private String name;
	public Light(String name) { this.name = name; }
	public void on()          { System.out.println(this.name + "light on");  }
	public void off()         { System.out.println(this.name + "light off"); }
}
//stereo
class Stereo{
	public void on()             { System.out.println("stereo on");            }
	public void setCD()          { System.out.println("stereo setCD");         }
	public void setVolume(int i) { System.out.println("stereo setVolume: " + i);   }
}

//main
public class RemoteLoader{
	public static void main(String[] args){
		
		Light  livingRoomLight = new Light("Living Room");
		Light  kitchenLight    = new Light("Kitchen");
		Stereo stereo          = new Stereo();
		
		LightOnCommand        livingRoomLightOn = new LightOnCommand(livingRoomLight);
		LightOnCommand        kitchenLightOn    = new LightOnCommand(kitchenLight);
		StereoOnWithCDCommand stereoOn          = new StereoOnWithCDCommand(stereo);
		
		RemoteControl remoteControl = new RemoteControl();
		remoteControl.setCommand(0,livingRoomLightOn,null);
		remoteControl.setCommand(1,kitchenLightOn,null);
		remoteControl.setCommand(2,stereoOn,null);
		
		System.out.println(remoteControl);
		
		remoteControl.onButtonPushed(0);
		remoteControl.onButtonPushed(1);
		remoteControl.undoButtonPushed();
		remoteControl.onButtonPushed(2);
	}
}