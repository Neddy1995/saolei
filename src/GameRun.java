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
//		设置窗大小
		jf.setSize(a[0],a[1]);
//		设置标题
		jf.setTitle("扫雷");
//		设置关闭则程序退出
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = jf.getContentPane();
//		将面板添加到容器内
		container.add(game);
//		设置可视
		jf.setVisible(true);
	}
}
