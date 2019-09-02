package com.xc.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//	行数
	private int rows;
//	列数
	private int cols;
//	炸弹数
	private int bombCount;
//	方格的长度
	private final int BLOCKWIDTH = 20;
//	方格的宽度
	private final int BLOCKHIGHT = 20;
//	每个方格存储的信息
	private JLabel[][] labels;
	
	private GameButton[][] buttons;
	
//	周围的坐标
	int[][] offset = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

	//未设置炸弹数
	public GamePanel(int rows, int cols) {
		this(rows,cols,(rows*cols)/10);
	}
	
	//自定义设置炸弹数，长度，宽度
	public GamePanel(int rows, int cols, int bombCount) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.bombCount = bombCount;
		this.labels = new JLabel[rows][cols];
		this.setLayout(null);
//		调用初始化函数（为什么需要先初始化按钮，再初始化底层界面）
		this.initButtons();
		this.initLabel();
		this.initBomb();
		this.initNumber();
	}
	
//	初始化底层背景
	public void initLabel() {
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < cols;j++) {
//				设置每格为对象，初始化为空，居中
				JLabel l = new JLabel("",JLabel.CENTER);
//				设置方格的位置和大小
				l.setBounds(j*BLOCKHIGHT, i*BLOCKHIGHT, BLOCKWIDTH, BLOCKHIGHT);
//				设置方格边框颜色
				l.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//				设置透明
				l.setOpaque(true);
//				设置背景色为黄色
				l.setBackground(Color.yellow);
//				将该方格添加到面板中
				this.add(l);
				labels[i][j]=l;
			}
		}
	}
	
//	计算整体的大小
	public int[] returnSize() {
		int[] a = {(this.cols+1)*BLOCKWIDTH-4,(this.rows+2)*BLOCKHIGHT-1};
		return a;
	}
	
//	初始化炸弹
	public void initBomb() {
		for(int i = 0;i < bombCount; i++) {
			int bRow = (int) (Math.random() * this.rows);
			int bCol = (int) (Math.random() * this.cols);
//			判断当前炸弹是否重叠
			if("*".equals(this.labels[bRow][bCol].getText())) {
				i--;
				System.out.println(""+this.labels[bRow][bCol].getText());
			}
			System.out.println(""+this.labels[bRow][bCol].getText());
			this.labels[bRow][bCol].setText("*");
			this.labels[bRow][bCol].setForeground(Color.RED);
			this.labels[bRow][bCol].setBackground(Color.BLACK);
		}
	}
	
//	添加按钮
	public void initButtons() {
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < cols;j++) {
				GameButton button = new GameButton();
//				设置按钮的大小和边界
				button.setBounds(j*BLOCKHIGHT, i*BLOCKHIGHT, BLOCKWIDTH, BLOCKHIGHT);
				button.setContentAreaFilled(true);
				this.add(button);
				buttons[i][j]=button;
				button.row=i;
				button.col=j;
//				添加监听事件
				button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						open(button);
					}
				});
			}
		}
	}
	
//	判断越界
	public boolean verify(int row ,int col) {
		return row>=0 && col>=0 && row<this.rows && col<this.cols;
	}
	
//	点击按钮事件
	public void open(GameButton button) {
//		System.out.println("打开了");
		button.setVisible(false);
		switch (labels[button.row][button.col].getText()) {
		case "*":
			System.out.println("boomb!游戏结束");
			for(int i=0;i<rows;i++) {
				for(int j=0;j<cols;j++) {
					if(labels[i][j].getText().equals("*")) {
						buttons[i][j].setVisible(false);
					}
				}
			}
			break;
		case "":
			System.out.println("打开了");
			for(int[] off : offset) {
				int row = button.row + off[0];
				int col = button.col + off[1];
				if(verify(row, col)){
					System.out.println("即将打开周围的");
					GameButton button2 = new GameButton();
					button2.row=row;
					button2.col=col;
					if(button2.isVisible()) {
						open(button2);
					}
				}
			}
			break;
		default:
			break;
		}
	}
	
//	处理炸弹周围的数字
	public void initNumber() {
		for(int i=0 ;i <this.rows; i++) {
			for(int j=0; j<this.cols;j++) {
				if(!labels[i][j].getText().equals("*")) {
					int number = 0;
					for(int[] off :offset) {
						int row = i+off[0];
						int col = j+off[1];
						if(verify(row, col) && labels[row][col].getText().equals("*")) {
							number++;
						}
					}
					if(number!=0) {
					this.labels[i][j].setText(""+number);
					}
				}
					
			}
		}
	}
}