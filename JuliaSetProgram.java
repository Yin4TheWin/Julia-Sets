import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class JuliaSetProgram extends JPanel implements AdjustmentListener, ActionListener {
    JFrame frame;
    JPanel labelPanel;
    JScrollBar aBar, zoomBar, bBar, iterBar;
    JCheckBox[] boxes;
    //BufferedImage image;
    int redValue=0;
    int blueValue=0;
    int greenValue=0;
    double a=0;
    double b=0;
    double zoom=1;
    float iter=300,i;
    int w,h,c;
    double zx, zy;
    GridLayout layout, boxLayout;
    JPanel scrollerPanel, checkPanel, bigSouthPanel;

    public JuliaSetProgram() {
        frame=new JFrame("Julia Set Program");
        frame.add(this);
        aBar=new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -1500, 1500);
        aBar.addAdjustmentListener(this);
        //aBar.setUnitIncrement(100);
        bBar=new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -1500, 1500);
        bBar.addAdjustmentListener(this);
        zoomBar=new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 0, 200);
        zoomBar.addAdjustmentListener(this);
        iterBar=new JScrollBar(JScrollBar.HORIZONTAL, 300, 0, 0, 600);
        iterBar.addAdjustmentListener(this);

        layout=new GridLayout(4, 1);
        scrollerPanel=new JPanel();
        scrollerPanel.setLayout(layout);
        scrollerPanel.add(aBar);
        scrollerPanel.add(bBar);
        scrollerPanel.add(zoomBar);
        scrollerPanel.add(iterBar);


        labelPanel = new JPanel();
		labelPanel.setLayout(layout);
		labelPanel.add(new JLabel("A value of a+bi"));
		labelPanel.add(new JLabel("B value of a+bi"));
		labelPanel.add(new JLabel("Zoom value"));
		labelPanel.add(new JLabel("Number of iterations"));

        bigSouthPanel=new JPanel();
        bigSouthPanel.setLayout(new BorderLayout());
        bigSouthPanel.add(labelPanel, BorderLayout.WEST);
        bigSouthPanel.add(scrollerPanel, BorderLayout.CENTER);
        //bigSouthPanel.add(checkPanel, BorderLayout.EAST);

        frame.add(bigSouthPanel, BorderLayout.SOUTH);
        frame.setSize(1000, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println(a);
        g.drawImage(drawJulia(),0,0,null);

    }
    public BufferedImage drawJulia(){
		w=frame.getWidth();
		h=frame.getHeight();
		BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<w;x++){
			for(int y=0;y<h;y++){
				zx=1.5*((x-w/2.0)/(zoom*w/2.0));
				zy=((y-h/2.0)/(zoom*h/2.0));
				i=iter;
				while(zx*zx+zy*zy<6&&i>0){
					double temp=zx*zx-zy*zy+a;
					zy=2*zx*zy+b;
					zx=temp;
					i--;
				}
				int c;
				if(i>0)
					c=Color.HSBtoRGB((iter/i)%1,1,1);
				else
					c=Color.HSBtoRGB(iter/i,1,0);
				image.setRGB(x,y,c);
			}
		}
		return image;
	}
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == aBar) {
            a=aBar.getValue()/1000.0;
        }
        if (e.getSource() == bBar) {
            b=bBar.getValue()/1000.0;
        }
        if (e.getSource() == zoomBar) {
            zoom=zoomBar.getValue()/100.0;
        }
        if(e.getSource()==iterBar){
			iter=(float)iterBar.getValue();
		}
        repaint();
    }
    public void actionPerformed(ActionEvent e) {
        if (boxes[0].isSelected()) {
            redValue=0;
        } else
            redValue=aBar.getValue();
        repaint();
    }
    public static void main(String[] args) {
        JuliaSetProgram app=new JuliaSetProgram();
    }

}