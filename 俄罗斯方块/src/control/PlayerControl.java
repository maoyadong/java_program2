package control;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayerControl extends KeyAdapter {

	private GameControl gameControl;

	public PlayerControl(GameControl gameControl) {
		this.gameControl = gameControl;
	}	

	@Override
	public void keyPressed(KeyEvent e) {
		
		gameControl.actionByKeyCode(e.getKeyCode());
		//System.out.println(e.getKeyCode());
//		switch (e.getKeyCode()) {
//		case KeyEvent.VK_UP:
//			this.gameControl.keyUp();
//			break;
//		case KeyEvent.VK_DOWN:
//			this.gameControl.keyDown();
//			break;
//		case KeyEvent.VK_LEFT:
//			this.gameControl.keyLeft();
//			break;
//		case KeyEvent.VK_RIGHT:
//			this.gameControl.keyRight();
//			break;
//		case KeyEvent.VK_W:
//			this.gameControl.keyUp();
//			break;
//		case KeyEvent.VK_S:
//			this.gameControl.keyDown();
//			break;
//		case KeyEvent.VK_A:
//			this.gameControl.keyLeft();
//			break;
//		case KeyEvent.VK_D:
//			this.gameControl.keyRight();
//			break;
//		case KeyEvent.VK_Z:
//			this.gameControl.test();
//		default:
//			break;
//		}
	}

}
