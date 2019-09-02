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
	
	//	����
	private int rows;
//	����
	private int cols;
//	ը����
	private int bombCount;
//	����ĳ���
	private final int BLOCKWIDTH = 20;
//	����Ŀ��
	private final int BLOCKHIGHT = 20;
//	ÿ������洢����Ϣ
	private JLabel[][] labels;
	
	private GameButton[][] buttons;
	
//	��Χ������
	int[][] offset = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

	//δ����ը����
	public GamePanel(int rows, int cols) {
		this(rows,cols,(rows*cols)/10);
	}
	
	//�Զ�������ը���������ȣ����
	public GamePanel(int rows, int cols, int bombCount) {
		super();
		this.rows = rows;
		this.cols = cols;
		this.bombCount = bombCount;
		this.labels = new JLabel[rows][cols];
		this.setLayout(null);
//		���ó�ʼ��������Ϊʲô��Ҫ�ȳ�ʼ����ť���ٳ�ʼ���ײ���棩
		this.initButtons();
		this.initLabel();
		this.initBomb();
		this.initNumber();
	}
	
//	��ʼ���ײ㱳��
	public void initLabel() {
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < cols;j++) {
//				����ÿ��Ϊ���󣬳�ʼ��Ϊ�գ�����
				JLabel l = new JLabel("",JLabel.CENTER);
//				���÷����λ�úʹ�С
				l.setBounds(j*BLOCKHIGHT, i*BLOCKHIGHT, BLOCKWIDTH, BLOCKHIGHT);
//				���÷���߿���ɫ
				l.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//				����͸��
				l.setOpaque(true);
//				���ñ���ɫΪ��ɫ
				l.setBackground(Color.yellow);
//				���÷�����ӵ������
				this.add(l);
				labels[i][j]=l;
			}
		}
	}
	
//	��������Ĵ�С
	public int[] returnSize() {
		int[] a = {(this.cols+1)*BLOCKWIDTH-4,(this.rows+2)*BLOCKHIGHT-1};
		return a;
	}
	
//	��ʼ��ը��
	public void initBomb() {
		for(int i = 0;i < bombCount; i++) {
			int bRow = (int) (Math.random() * this.rows);
			int bCol = (int) (Math.random() * this.cols);
//			�жϵ�ǰը���Ƿ��ص�
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
	
//	��Ӱ�ť
	public void initButtons() {
		for(int i = 0;i < rows;i++) {
			for(int j = 0;j < cols;j++) {
				GameButton button = new GameButton();
//				���ð�ť�Ĵ�С�ͱ߽�
				button.setBounds(j*BLOCKHIGHT, i*BLOCKHIGHT, BLOCKWIDTH, BLOCKHIGHT);
				button.setContentAreaFilled(true);
				this.add(button);
				buttons[i][j]=button;
				button.row=i;
				button.col=j;
//				��Ӽ����¼�
				button.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						open(button);
					}
				});
			}
		}
	}
	
//	�ж�Խ��
	public boolean verify(int row ,int col) {
		return row>=0 && col>=0 && row<this.rows && col<this.cols;
	}
	
//	�����ť�¼�
	public void open(GameButton button) {
//		System.out.println("����");
		button.setVisible(false);
		switch (labels[button.row][button.col].getText()) {
		case "*":
			System.out.println("boomb!��Ϸ����");
			for(int i=0;i<rows;i++) {
				for(int j=0;j<cols;j++) {
					if(labels[i][j].getText().equals("*")) {
						buttons[i][j].setVisible(false);
					}
				}
			}
			break;
		case "":
			System.out.println("����");
			for(int[] off : offset) {
				int row = button.row + off[0];
				int col = button.col + off[1];
				if(verify(row, col)){
					System.out.println("��������Χ��");
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
	
//	����ը����Χ������
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