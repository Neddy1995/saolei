import java.awt.Container;

import javax.swing.JFrame;

import com.xc.swing.GamePanel;

public class GameRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame jf = new JFrame();
		GamePanel game = new GamePanel(10, 10);
		int[] a=game.returnSize();
//		���ô���С
		jf.setSize(a[0],a[1]);
//		���ñ���
		jf.setTitle("ɨ��");
//		���ùر�������˳�
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = jf.getContentPane();
//		�������ӵ�������
		container.add(game);
//		���ÿ���
		jf.setVisible(true);
	}
}
