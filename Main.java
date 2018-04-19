

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;


public class Main extends JFrame {
	@BindListener(ButtonActionListener.class)
	JButton btn;
	@BindListener(ToggleActionListener.class)
	JToggleButton toggle;
	
	public Main() {
		super("测试窗口");
		setSize(250, 250); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();    
        
        btn = new JButton("测试按钮");
        panel.add(btn);

        toggle = new JToggleButton();
        toggle.setText("测试文本");
        panel.add(toggle);
        setContentPane(panel);
        setVisible(true);
        
		BindRuntime.bind(this);

   }
	
	public static void main(String[] args) {
		new Main();
	}
}
