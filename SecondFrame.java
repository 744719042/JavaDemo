

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;


public class SecondFrame extends JFrame {
	@BindListener(SecondButtonActionListener.class)
	JButton btn;
	@BindListener(ToggleActionListener.class)
	JToggleButton toggle;
	
	public SecondFrame() {
		super("测试窗口");
		setSize(250, 250); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();    
        
        btn = new JButton("第二个按钮");
        panel.add(btn);

        toggle = new JToggleButton();
        toggle.setText("第二个Toggle");
        panel.add(toggle);
        setContentPane(panel);
        setVisible(true);
        
		BindRuntime.bind(this);

   }
	
	public static void main(String[] args) {
		new SecondFrame();
	}
}
