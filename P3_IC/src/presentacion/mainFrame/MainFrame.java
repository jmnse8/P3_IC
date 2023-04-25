package presentacion.mainFrame;

import javax.swing.JFrame;

import presentacion.mainFrame.mainFrameImp.MainFrameImp;

public abstract class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private static MainFrame instance;
	
	public synchronized static MainFrame getInstance()  {
		if (instance == null)
			instance = new MainFrameImp();
		return instance;
	}
}
