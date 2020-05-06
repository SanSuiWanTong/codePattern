//java virtual Proxy load Image
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;;

class ImageComponent extends JComponent{
	private Icon icon;
	public ImageComponent(Icon icon){
		this.icon = icon;
	}
	public void setIcon(Icon icon){
		this.icon = icon;
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int x = (800 - w)/2;
		int y = (600 - h)/2;
		icon.paintIcon(this,g,x,y);
	}
}

class ImageProxy implements Icon{
	ImageIcon imageIcon;
	String imageURL;
	Thread retrievalThread;
	boolean retrieving = false;
	
	public ImageProxy(String url){ imageURL = url; }
	public int getIconWidth(){
		if(imageIcon != null){
			return imageIcon.getIconWidth();//
		}else{
			return 800;
		}
	}
	public int getIconHeight(){
		if(imageIcon != null){
			return imageIcon.getIconHeight();
		}else{
			return 600;
		}
	}
	public void paintIcon(final Component c,Graphics g,int x,int y){
		if(imageIcon != null){
			imageIcon.paintIcon(c,g,x,y);
		}else{
			g.drawString("Loading...",x+300,y+190);
			if(!retrieving){
				retrieving = true;
				retrievalThread = new Thread(
					()-> {
						try{
							imageIcon = new ImageIcon(getUrl(imageURL),"CD Cover");//need getUrl again
							if(imageIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
								c.repaint();
							}else{
								System.out.println("error: load image fail,disconnected? move frame to repaint");

								retrieving = false;
								imageIcon = null;
							}
						}catch(Exception e){
							e.printStackTrace();
						}
						System.out.println("finish");
					});
				retrievalThread.start();
			}
		}
	}
	private URL getUrl(String name){
		try{
			return new URL(name);
		}catch(MalformedURLException e){
			e.printStackTrace();
			return null;
		}
	}
}

public class virtualProxy{
	ImageComponent imageComponent;
	JFrame frame = new JFrame("A virtual proxy demo");

	public static void main(String[] args) throws Exception{
		virtualProxy testProxy = new virtualProxy();
	}
	public  virtualProxy() throws Exception{
		
		String initialURL = "http://images.amazon.com/images/P/B000005IRM.01.LZZZZZZZ.jpg";
		
		Icon icon = new ImageProxy(initialURL);
		imageComponent = new ImageComponent(icon);
		frame.getContentPane().add(imageComponent);
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}