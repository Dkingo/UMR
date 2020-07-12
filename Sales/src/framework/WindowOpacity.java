package framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.sun.awt.AWTUtilities;

public class WindowOpacity {
	public WindowOpacity(final JFrame jframe) {
		// �������õ��뵭�������
		AWTUtilities.setWindowOpacity(jframe, 0f);
		ActionListener lisener = new ActionListener() {

			float alpha = 0;

			@Override
			public void actionPerformed(ActionEvent e) {
				if (alpha < 0.9) {
					AWTUtilities.setWindowOpacity(jframe, alpha += 0.1);
				} else {
					AWTUtilities.setWindowOpacity(jframe, 1);
					Timer source = (Timer) e.getSource();
					source.stop();
				}
			}
		};
		// �����߳̿���
		new Timer(50, lisener).start();
	}
}
