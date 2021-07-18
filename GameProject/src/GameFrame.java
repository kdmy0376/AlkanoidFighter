import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class GameFrame extends JFrame
{
	private static final int WIDTH = 600;
	private static final int HEIGHT = 500;
	
	private Container contentPane;	//�۾� ������ ���� �⺻ Panel�� ���� ��ü
	
	private CardLayout cLayout;
	private JPanel cPanel;
	private JPanel loginScreen;
	private JPanel introScreen1;
	private JPanel introScreen2;
	private JPanel menuScreen;
	private JPanel menuScreenNorth;
	private JPanel menuScreenCenter;
	private JPanel menuScreenBottom;
	private JPanel playScreen;
	private JPanel playScreenEast;
	private JPanel playScreenCenter;
	private JPanel multiplayScreen1;
	private JPanel multiplayScreen2;
	private JPanel multiplayScreen3;
	private JPanel multiplayScreenCenter;
	private JPanel multiplayScreenEast;
	private JPanel multiplayScreenBottom;
	private JPanel scoreScreen;
	private JPanel registerScreen;
	private JPanel versionInfoScreen;
	
	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenuItem quitItem;
	private JMenuItem infoItem;
	private JMenuBar bar;	
	
	private boolean loginChk;
	private boolean intro1Chk;
	private boolean intro2Chk;
	private boolean menuChk;
	private GameFrame gameFrame;
	
	private DrawIntro1Canvas drawIntro1;
	private DrawIntro2Canvas drawIntro2;
	private DemoCanvas demoCanvas;
	
	/*DB���� ���� ����*/	
	private ServiceQuery serviceQuery;
	private List<Users> results1;	
	
	private Cursor waitCursor;
	private Cursor handCursor;
		
	/*������*/
	public GameFrame(String title)
	{
		super(title);
		
		/*�����ͺ��̽� �����ϰ� PreparedStatements �¾�*/
		//serviceQuery = new ServiceQuery(); 
		
		this.initMember();
		this.etStart();
		
		this.setSize(WIDTH, HEIGHT);
		this.setLocation(300,200);
		this.setResizable(false);
		this.setVisible(true);			
		
		screenSlide();
	}
	/*�ʱ�ȭ*/
	public void initMember()
	{
		contentPane = this.getContentPane();	//���� Panel������ �⺻ �۾� ���� ȹ��
		
		/*Ŀ�� ���*/
		waitCursor = new Cursor(Cursor.WAIT_CURSOR);
		handCursor = new Cursor(Cursor.HAND_CURSOR);
		
		gameFrame = this;
		drawIntro2 = new DrawIntro2Canvas();
		drawIntro1 = new DrawIntro1Canvas();
		loginChk = false;
		intro1Chk = false;	
		intro2Chk = false;
		menuChk = false;
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('f');
		quitItem = new JMenuItem("Quit");
		quitItem.setMnemonic('q');		 
		fileMenu.add(quitItem);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('h');
		infoItem = new JMenuItem("Version Infomation");
		infoItem.setMnemonic('v');
		helpMenu.add(infoItem);
		
		bar = new JMenuBar();
		this.setJMenuBar(bar);
		bar.add(fileMenu); 
		bar.add(helpMenu);
		
		cLayout = new CardLayout();
		cPanel = new JPanel();
		cPanel.setLayout(cLayout);
		
		loginScreen = new JPanel();
		loginScreen.setLayout(new BorderLayout());
		
		introScreen1 = new JPanel();
		introScreen1.setBackground(Color.black);
		introScreen1.setLayout(new BorderLayout());
		introScreen1.add(drawIntro1, BorderLayout.CENTER);
		
		introScreen2 = new JPanel();
		introScreen2.setBackground(Color.black);
		introScreen2.setLayout(new BorderLayout());
		introScreen2.add(drawIntro2, BorderLayout.CENTER);
		
		
		menuScreen = new JPanel();
		menuScreen.setBackground(Color.white);
		menuScreen.setLayout(new BorderLayout());
		menuScreenNorth = new JPanel();
		menuScreenNorth.setBackground(Color.white);
		menuScreenCenter = new JPanel();
		menuScreenCenter.setBackground(Color.gray);
		menuScreenBottom = new JPanel();
		menuScreenBottom.setBackground(Color.lightGray);
		
		playScreen = new JPanel();
		playScreen.setBackground(Color.lightGray);
		playScreen.setLayout(new BorderLayout());
		playScreenEast = new JPanel();
		playScreenEast.setBackground(Color.gray);
		playScreenEast.setLayout(new GridLayout(0,1));
		playScreenCenter = new JPanel();
		playScreenCenter.setBackground(Color.white);
		playScreenCenter.setLayout(new BorderLayout());
		
		multiplayScreen1 = new JPanel();
		multiplayScreen1.setLayout(new BorderLayout());
		
		multiplayScreen2 = new JPanel();
		multiplayScreen2.setBackground(Color.orange);		
		multiplayScreen2.setLayout(new BorderLayout());
		
		multiplayScreen3 = new JPanel();
		multiplayScreen3.setBackground(Color.yellow);
		multiplayScreenCenter = new JPanel();
		multiplayScreenCenter.setBackground(Color.white);
		multiplayScreenEast = new JPanel();
		multiplayScreenEast.setBackground(Color.lightGray);
		multiplayScreenBottom = new JPanel();
		multiplayScreenBottom.setBackground(Color.gray);
		multiplayScreenBottom.setLayout(new FlowLayout());
		
		scoreScreen = new JPanel();
		scoreScreen.setBackground(Color.darkGray);
		
		registerScreen = new JPanel();
		registerScreen.setLayout(new FlowLayout());
		
		versionInfoScreen = new JPanel();
		versionInfoScreen.setBackground(Color.darkGray);
		
		this.introScreen1Set();
		this.registerScreenSet();
		this.menuScreenSet();		
		this.playScreenSet();
		this.multiplayScreenSet1();
		this.multiplayScreenSet2();
		this.multiplayScreenSet3();
		this.scoreScreen();

		cPanel.add(loginScreen, "login");		
		cPanel.add(introScreen1, "intro1");
		cPanel.add(introScreen2, "intro2");
		cPanel.add(menuScreen, "menu");
		cPanel.add(playScreen, "play");
		cPanel.add(multiplayScreen1, "multiplay1");
		cPanel.add(multiplayScreen2, "multiplay2");
		cPanel.add(multiplayScreen3, "multiplay3");
		cPanel.add(versionInfoScreen, "versionInfo");
		cPanel.add(scoreScreen, "score");
		cPanel.add(registerScreen, "register");
		
		contentPane.add(cPanel);
	}
	/*�̺�Ʈ �� ������ ó��*/
	public void etStart()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//X��ư �̺�Ʈ		
		
		quitItem.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.exit(0);
					}
				}
		);
		infoItem.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						JOptionPane.showMessageDialog(GameFrame.this, "Alkanoid Fighter Ver 0.1",
          						"Information", JOptionPane.PLAIN_MESSAGE);
					}
				}
		);
		
	}
	/*������ ����*/
	public void threadSleep(int mill)
	{
		try
		{
			Thread.sleep(mill);
		}
		catch(InterruptedException e){}
	}
	/*ȭ�� ��Ʈ��*/
	public void screenSlide()
	{				
		threadSleep(5000);
		cLayout.show(cPanel, "intro1");	//2��° ȭ��(introScreen2)ǥ��
		loginChk = false;
		intro1Chk = true;		
		
		threadSleep(5000);
		cLayout.show(cPanel, "intro2");	//3��° ȭ��(introScreen2)ǥ��
		drawIntro2.strThdStart();
		intro1Chk = false;
		intro2Chk = true;		
		
		threadSleep(12000);		
		cLayout.show(cPanel, "menu");	//4��° ȭ��(menuScreen)ǥ��
		demoCanvas.menuScreenThreadSet();	//�޴� ���𾲷��� ����
		contentPane.setCursor(handCursor);
		intro2Chk = false;
		menuChk = true;
		repaint();
		
	}		
	
	/*�α��� ȭ��*/
	public void introScreen1Set()
	{		
		JPanel loginPanel = new JPanel(new BorderLayout(5, 5));	
		ImageIcon introImage = new ImageIcon("space4.jpg");
		JLabel introLb = new JLabel(introImage);		
		final JButton registerBtn = new JButton("ȸ������");
		final JButton loginBtn = new JButton("�α���");
		final JTextField idField = new JTextField(10);
		final JPasswordField passField = new JPasswordField(10);		
		
		loginPanel.add("Center", introLb);
		JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel jp1 = new JPanel(new BorderLayout());
		JPanel jp2 = new JPanel(new GridLayout(2, 1));
		jp2.add(idField);
		jp2.add(passField);
		idField.setBorder(new TitledBorder("ID"));
		passField.setBorder(new TitledBorder("PASS"));
		jp1.add("Center", jp2);
		JPanel jp3 = new JPanel(new GridLayout(1, 2));
		jp3.add(registerBtn);
		jp3.add(loginBtn);
		jp1.add("South", jp3);
		jp1.setBorder(new TitledBorder("Login Module"));
		jp.add(jp1);
		loginPanel.add("South", jp);
		
		/*�α��� ��ư*/
		loginBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String id = idField.getText();
						String pass = passField.getText();
						results1 = serviceQuery.getPersonByID(id);
						
						if(id == null || pass == null || 
						   id.trim().length() == 0 || pass.trim().length() == 0 || results1.isEmpty()) 
						{
							JOptionPane.showMessageDialog(GameFrame.this, "ID�� PASS�� ������ϴ�.", "���", 
															JOptionPane.ERROR_MESSAGE);
							return;
						}
						else
						{
							contentPane.setCursor(waitCursor);
						}
						
					}
				}
		);
		/*���� ��� ��ư*/
		registerBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cLayout.show(cPanel, "register"); //ȸ����� ȭ������ �̵�
					}
				}
		);
		
		loginScreen.add(loginPanel);
	}
	
	/*ȸ�� ��� ȭ��*/
	public void registerScreenSet()
	{
		final String[] homeNumbers = {"02", "051", "053", "031"};
		final String[] celNumbers = {"010", "011", "016", "019"};				
					
		//����� �̸�
		JLabel perName = new JLabel("*Name");
		perName.setToolTipText("����ϴ� ����� �̸�");		//���̺� ���� ���� �߰�
		perName.setOpaque(true);
		perName.setBackground(Color.lightGray);
		final JTextField perNameText = new JTextField(4);
								
		//���̵�
		JLabel perID = new JLabel("*ID");
		perID.setToolTipText("����ϰ��� �ϴ� ���̵�");
		perID.setOpaque(true);
		perID.setBackground(Color.lightGray);
		final JTextField perIDText = new JTextField(4);
		JButton perIDBtn = new JButton("CHECK");
				
		//����
		JLabel perNickName = new JLabel("*Nickname");
		perNickName.setToolTipText("����ϰ��� �ϴ� ����");
		perNickName.setOpaque(true);
		perNickName.setBackground(Color.lightGray);
		final JTextField perNickNameText = new JTextField(4);
		JButton	perNickNameBtn = new JButton("CHECK");
				
		//��й�ȣ
		JLabel perPw = new JLabel("*Password");
		perPw.setToolTipText("��й�ȣ 4~8");
		perPw.setOpaque(true);
		perPw.setBackground(Color.lightGray);
		final JPasswordField perPwPass = new JPasswordField(4);
				
		//��й�ȣȮ��
		JLabel perPwRe = new JLabel("*Password Re");
		perPwRe.setToolTipText("��й�ȣ 4~8");
		perPwRe.setOpaque(true);
		perPwRe.setBackground(Color.lightGray);
		final JPasswordField perPwRePass = new JPasswordField(4);

		//�ֹε�Ϲ�ȣ
		JLabel perRegNum = new JLabel("*Person Num");
		perRegNum.setToolTipText("�ֹε�Ϲ�ȣ 13�ڸ�");
		perRegNum.setOpaque(true);
		perRegNum.setBackground(Color.lightGray);
		final JTextField perRegNumText = new JTextField(4);
		JLabel perRegNumBar = new JLabel(" - ", SwingConstants.CENTER); //���̺� ��� ����
		perRegNumBar.setOpaque(true);
		perRegNumBar.setBackground(Color.lightGray);
		final JPasswordField perRegNumPass = new JPasswordField(4);
				
		//��ȭ��ȣ
		JLabel phoneNum = new JLabel(" TEL");
		phoneNum.setToolTipText("������� ��ȭ��ȣ");
		phoneNum.setOpaque(true);
		phoneNum.setBackground(Color.lightGray);
		Choice choiceHomeNum = new Choice();

		for(int i=0; i<homeNumbers.length; i++)
			choiceHomeNum.add(homeNumbers[i]); //������ȣ �Է�

		JLabel phoneHBar1 = new JLabel(" - ", SwingConstants.CENTER);
		phoneHBar1.setOpaque(true);
		phoneHBar1.setBackground(Color.lightGray);
		JLabel phoneHBar2 = new JLabel(" - ", SwingConstants.CENTER);
		phoneHBar2.setOpaque(true);
		phoneHBar2.setBackground(Color.lightGray);
		final JTextField phoneHTextFirst = new JTextField(4);
		final JTextField phoneHTextSecond = new JTextField(4);

		//�ڵ�����ȣ
		JLabel celPhoneNum = new JLabel(" Cellphone");
		celPhoneNum.setToolTipText("������� �ڵ�����ȣ");
		celPhoneNum.setOpaque(true);
		celPhoneNum.setBackground(Color.lightGray);
		Choice choiceCelNum = new Choice();
		for(int i=0; i<celNumbers.length; i++)
			choiceCelNum.add(celNumbers[i]); //�ڵ��� ���ڸ� ��ȣ �Է�
		JLabel phoneCBar1 = new JLabel(" - ", SwingConstants.CENTER);
		phoneCBar1.setOpaque(true);
		phoneCBar1.setBackground(Color.lightGray);
		JLabel phoneCBar2 = new JLabel(" - ", SwingConstants.CENTER);
		phoneCBar2.setOpaque(true);
		phoneCBar2.setBackground(Color.lightGray);
		final JTextField phoneCTextFirst = new JTextField(4);
		final JTextField phoneCTextSecond = new JTextField(4);		

		//�̸���
		JLabel perEmail = new JLabel(" E-mail");
		perEmail.setToolTipText("���� ������� �̸���");
		perEmail.setOpaque(true);
		perEmail.setBackground(Color.lightGray);
		final JTextField perEmailText1 = new JTextField(4);
		JLabel emailWhelk = new JLabel("@", SwingConstants.CENTER);
		emailWhelk.setOpaque(true);
		emailWhelk.setBackground(Color.lightGray);
		final JTextField perEmailText2 = new JTextField(4);					
				
		//����ڵ��, �ʱ�ȭ, ��� ��ҹ�ư
		JButton regBtn = new JButton("Register");
		JButton initialBtn = new JButton("Reset");
		JButton cancelBtn = new JButton("Cancel");
		
		/*����� ���̵� �ߺ���ư*/
		perIDBtn.addActionListener(				
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						results1 = serviceQuery.getPersonByID(perIDText.getText());
						if(results1.isEmpty())
						{
							JOptionPane.showMessageDialog(null, "��� ������ ���̵��Դϴ�.", "�ߺ�Ȯ��", 
									JOptionPane.PLAIN_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "�̹� ����ϴ� ���̵� �ֽ��ϴ�.", "�ߺ�Ȯ��", 
														JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
		);
		/*�г��� �ߺ� ��ư*/
		perNickNameBtn.addActionListener(				
				new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						results1 = serviceQuery.getPersonByNickName(perNickNameText.getText());
						if(results1.isEmpty())
						{
							JOptionPane.showMessageDialog(null, "��� ������ �����Դϴ�.", "�ߺ�Ȯ��", 
									JOptionPane.PLAIN_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(null, "�̹� ����ϴ� ������ �ֽ��ϴ�.", "�ߺ�Ȯ��", 
														JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
		);

		//'���� ���'��ư
		regBtn.addActionListener(				
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					serviceQuery.addUsers(perNameText.getText(), perIDText.getText(), perNickNameText.getText(), 
							perPwPass.getText(), perPwRePass.getText(), perRegNumText.getText(), 
							perRegNumPass.getText(), phoneHTextFirst.getText(), phoneHTextSecond.getText(),
							phoneCTextFirst.getText(), phoneCTextSecond.getText(), perEmailText1.getText(),
							perEmailText2.getText());
					JOptionPane.showMessageDialog(null, "Register Complete.", "User Register", 
													JOptionPane.PLAIN_MESSAGE);	
					cLayout.show(cPanel, "login");
					
				}
			}
		);
		//'�ʱ�ȭ'��ư
		initialBtn.addActionListener(			
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					/*�����*/
					perNameText.setText("");
					perIDText.setText("");
					perNickNameText.setText("");
					perPwPass.setText("");
					perPwRePass.setText("");
					perRegNumText.setText("");
					perRegNumPass.setText("");
					phoneHTextFirst.setText("");
					phoneHTextSecond.setText("");
					phoneCTextFirst.setText("");
					phoneCTextSecond.setText("");
					perEmailText1.setText("");
					perEmailText2.setText("");						
				}
			}
		);
		//'������'��ư
		cancelBtn.addActionListener(				
			new ActionListener()
			{
				public void actionPerformed(ActionEvent event)
				{
					cLayout.show(cPanel, "login");
				}
			}
		);		
				
		Border regBorder1 = BorderFactory.createEtchedBorder(); //������ �����մϴ�.
		regBorder1 = BorderFactory.createTitledBorder(regBorder1, "Critical Items");
		JPanel regBorderPanel = new JPanel();
		regBorderPanel.setLayout(new GridLayout(0,6));
		regBorderPanel.setBorder(regBorder1);

		/*���� ���̺��� ����(GridBagLayout ����� �ͼ�ġ �ʾƼ� �̷������� �Ͽ���)*/
		JLabel[] blankSpace = new JLabel[50];

		for(int i=0; i<blankSpace.length; i++)
		{
			blankSpace[i] = new JLabel(" ");
			blankSpace[i].setOpaque(true);
			blankSpace[i].setBackground(Color.lightGray);
		}

		/*����� ��� ���*/
		regBorderPanel.add(perName);		//�̸�
		regBorderPanel.add(perNameText);
		regBorderPanel.add(blankSpace[0]);	//���鷹�̺�
		regBorderPanel.add(blankSpace[1]);
		regBorderPanel.add(blankSpace[2]);
		regBorderPanel.add(blankSpace[3]);

		regBorderPanel.add(perID);		//���̵�
		regBorderPanel.add(perIDText);
		regBorderPanel.add(perIDBtn);
		regBorderPanel.add(blankSpace[4]);
		regBorderPanel.add(blankSpace[5]);
		regBorderPanel.add(blankSpace[6]);

		regBorderPanel.add(perNickName);	//����
		regBorderPanel.add(perNickNameText);
		regBorderPanel.add(perNickNameBtn);
		regBorderPanel.add(blankSpace[7]);
		regBorderPanel.add(blankSpace[8]);
		regBorderPanel.add(blankSpace[9]);

		regBorderPanel.add(perPw);		//��й�ȣ
		regBorderPanel.add(perPwPass);
		regBorderPanel.add(blankSpace[10]);
		regBorderPanel.add(blankSpace[11]);
		regBorderPanel.add(blankSpace[12]);
		regBorderPanel.add(blankSpace[13]);

		regBorderPanel.add(perPwRe);		//��й�ȣ Ȯ��
		regBorderPanel.add(perPwRePass);
		regBorderPanel.add(blankSpace[14]);
		regBorderPanel.add(blankSpace[15]);
		regBorderPanel.add(blankSpace[16]);
		regBorderPanel.add(blankSpace[17]);

		regBorderPanel.add(perRegNum);		//�ֹε�Ϲ�ȣ
		regBorderPanel.add(perRegNumText);
		regBorderPanel.add(perRegNumBar);
		regBorderPanel.add(perRegNumPass);
		regBorderPanel.add(blankSpace[18]);
		regBorderPanel.add(blankSpace[19]);

		regBorderPanel.add(phoneNum);		//����ȭ
		regBorderPanel.add(choiceHomeNum);		
		regBorderPanel.add(phoneHBar1);
		regBorderPanel.add(phoneHTextFirst);
		regBorderPanel.add(phoneHBar2);
		regBorderPanel.add(phoneHTextSecond);

		regBorderPanel.add(celPhoneNum);	//�ڵ���
		regBorderPanel.add(choiceCelNum);
		regBorderPanel.add(phoneCBar1);
		regBorderPanel.add(phoneCTextFirst);
		regBorderPanel.add(phoneCBar2);
		regBorderPanel.add(phoneCTextSecond);
			
		regBorderPanel.add(perEmail);		//�̸���
		regBorderPanel.add(perEmailText1);
		regBorderPanel.add(emailWhelk);
		regBorderPanel.add(perEmailText2);
		regBorderPanel.add(blankSpace[20]);
		regBorderPanel.add(blankSpace[21]);		

		/*��ư �г�*/
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout());

		btnPanel.add(regBtn);				//����� ���
		btnPanel.add(initialBtn);			//�ʱ�ȭ
		btnPanel.add(cancelBtn);			//��� ���							
		
		registerScreen.add(regBorderPanel);
		registerScreen.add(btnPanel);			
	}

	/*�޴� ���� ȭ��*/
	public void menuScreenSet()
	{		
		demoCanvas = new DemoCanvas(menuScreenCenter);		
				
		JLabel title = new JLabel("A l k a n o i d  F i g h t e r", JLabel.CENTER);
		title.setFont(new Font("SanSerif", Font.BOLD, 22));
		
		JButton playBtn = new JButton("SINGLE GAME PLAY");
		playBtn.setBackground(Color.lightGray);
		playBtn.setFont(new Font("SanSerif", Font.BOLD, 13));
		
		JButton multiplayBtn = new JButton("MULTI GAME PLAY");
		multiplayBtn.setBackground(Color.lightGray);
		multiplayBtn.setFont(new Font("SanSerif", Font.BOLD, 13));
		
		JButton scoreBtn = new JButton("GAME SCORE");
		scoreBtn.setBackground(Color.lightGray);
		scoreBtn.setFont(new Font("SanSerif", Font.BOLD, 13));
		
		JButton versionBtn = new JButton("VERSION INFO");
		versionBtn.setBackground(Color.lightGray);
		versionBtn.setFont(new Font("SanSerif", Font.BOLD, 13));
		
		JButton quitBtn = new JButton("QUIT");
		quitBtn.setBackground(Color.lightGray);
		quitBtn.setFont(new Font("SanSerif", Font.BOLD, 13));
		
		JLabel copyrightLb = new JLabel("Copyright(C)2012 Dong Wan Koo All Rights Reserved");
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		menuScreenBottom.setLayout(gridBag);
		
		gc.gridx = 3; 
		gc.gridy = 0;
		gc.gridwidth = 5;
		gc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(playBtn, gc);
		
		gc.gridx = 0; 
		gc.gridy = 1;
		gc.gridwidth = 5;
		gc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(multiplayBtn, gc);
		
		gc.gridx = 0; 
		gc.gridy = 2;
		gc.gridwidth = 5;
		gc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(scoreBtn, gc);
		
		gc.gridx = 0; 
		gc.gridy = 3;
		gc.gridwidth = 5;
		gc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(versionBtn, gc);
		
		gc.gridx = 0; 
		gc.gridy = 4;
		gc.gridwidth = 5;
		gc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(quitBtn, gc);
		
		gc.gridx = 0; 
		gc.gridy = 5;
		gc.gridwidth = 5;
		gc.fill = GridBagConstraints.BOTH;
		gridBag.setConstraints(copyrightLb, gc);
			
		this.menuScreenEvent(playBtn, quitBtn, multiplayBtn, scoreBtn, versionBtn);
				
		menuScreenBottom.add(playBtn);
		menuScreenBottom.add(multiplayBtn);
		menuScreenBottom.add(scoreBtn);
		menuScreenBottom.add(versionBtn);
		menuScreenBottom.add(quitBtn);
		menuScreenBottom.add(copyrightLb);
		
		menuScreenCenter.add(demoCanvas, BorderLayout.CENTER);		
		menuScreenNorth.add(title);
		
		menuScreen.add(menuScreenNorth, BorderLayout.NORTH);
		menuScreen.add(menuScreenCenter, BorderLayout.CENTER);
		menuScreen.add(menuScreenBottom, BorderLayout.SOUTH);
		
	}
	/*�޴� ���� ȭ�� �̺�Ʈ ó��*/
	public void menuScreenEvent(JButton playBtn, JButton quitBtn, JButton multiplayBtn, JButton scoreBtn, JButton versionBtn)
	{
		playBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{								
						cLayout.show(cPanel, "play");
					}
				}
		);
		multiplayBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cLayout.show(cPanel, "multiplay1");
					}
				}
		);
		versionBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cLayout.show(cPanel, "versionInfo");
					}
				}
		);
		scoreBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cLayout.show(cPanel, "score");
					}
				}
		);
		quitBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						System.exit(0);
					}
				}
		);
	}
	/*���� �÷��� ȭ��*/
	public void playScreenSet()
	{					
		final PlayScreenCanvas playCanvas = new PlayScreenCanvas(playScreenCenter);
		
		playScreenCenter.add(playCanvas, BorderLayout.CENTER);		
		
		JButton startBtn = new JButton("START");
		JButton reStartBtn = new JButton("RESTART");
		JButton pauseBtn = new JButton("PAUSE");
		JButton resumeBtn = new JButton("RESUME");
		JLabel speedLb = new JLabel("SPEED", JLabel.CENTER);
		final Choice speedChoice = new Choice();
		speedChoice.addItem("FAST");
		speedChoice.addItem("NORMAL");
		speedChoice.addItem("SLOW");
		speedChoice.select(1);
				
		JLabel[] blankLb = new JLabel[8];
		for(int i=0; i<8; i++)
			blankLb[i] = new JLabel(" ");
				
		JButton playQuitBtn = new JButton("������");
		
		playScreenEast.add(startBtn);
		playScreenEast.add(reStartBtn);
		playScreenEast.add(pauseBtn);
		playScreenEast.add(resumeBtn);
		playScreenEast.add(speedLb);
		playScreenEast.add(speedChoice);
		for(int i=0; i<5; i++)
			playScreenEast.add(blankLb[i]);
		playScreenEast.add(playQuitBtn);		
		
		
		startBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						playCanvas.playScreenThreadSet();
					}
				}
		);
		reStartBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						playCanvas.playScreenThreadStop();
						playCanvas.playScreenThreadSet();
					}
				}
		);
		pauseBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						playCanvas.bThread.suspend();						
					}
				}
		);
		resumeBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						playCanvas.bThread.resume();
					}
				}
		);	
		speedChoice.addItemListener(
				new ItemListener()
				{
					public void itemStateChanged(ItemEvent e)
					{
						int idx = speedChoice.getSelectedIndex();
						
						if(idx == 0)
							playCanvas.setBallSpeed(0);
						else if(idx == 1)
							playCanvas.setBallSpeed(1);
						else
							playCanvas.setBallSpeed(2);
					}
				}
		);
		playQuitBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cLayout.previous(cPanel);
					}
				}
		);
		
		playScreen.add(playScreenCenter, BorderLayout.CENTER);
		playScreen.add(playScreenEast, BorderLayout.EAST);
	}
	/*��Ƽ �÷��� �������� ȭ��*/
	public void multiplayScreenSet1()
	{
		/*�̹��� ���̺� �г�*/
		JPanel mulImgPanel = new JPanel();
		mulImgPanel.setBackground(Color.black);
		ImageIcon multiImage = new ImageIcon("space2.jpg");
		JLabel imageLb = new JLabel(multiImage);
		
		/*���� ���� �г�*/
		JPanel serConnPanel = new JPanel();
		serConnPanel.setBackground(Color.black);
		
		JLabel serIP = new JLabel("IP ADDRESS : ");
		serIP.setForeground(Color.white);
		serIP.setFont(new Font("SanSerif", Font.BOLD, 12));
		final JTextField ipField = new JTextField(20);
		ipField.setText("127.0.0.1");		
		
		JButton connBtn = new JButton("Connect");
		JButton backBtn = new JButton("Back");
		backBtn.setBackground(Color.LIGHT_GRAY);
		
		mulImgPanel.add(imageLb);
		
		serConnPanel.add(serIP);
		serConnPanel.add(ipField);		
		serConnPanel.add(connBtn);
		serConnPanel.add(backBtn);
		multiplayScreen1.add(mulImgPanel, BorderLayout.NORTH);
		multiplayScreen1.add(serConnPanel, BorderLayout.CENTER);
		
		//���� ���� ��ư
		connBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String ipAddress = ipField.getText();
						if(!(ipAddress.isEmpty()))
						{
							Socket1 sok = new Socket1(gameFrame, ipAddress);
							cLayout.show(cPanel, "multiplay2");
						}
					}
				}
		);
		//Back��ư
		backBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						cLayout.show(cPanel, "menu");
					}
				}
		);
	}
	/*��Ƽ �÷��� ȭ��2*/
	public void multiplayScreenSet2()
	{		
		//cLayout.show(cPanel, "multiplay3");
	}
	/*��Ƽ �÷��� ȭ��3*/
	public void multiplayScreenSet3()
	{		
		MPlayScreenCanvas mPlayCanvas = new MPlayScreenCanvas(multiplayScreenCenter);
		multiplayScreenCenter.add(mPlayCanvas);
		
		JButton player1 = new JButton("Player1");
		multiplayScreenEast.add(player1);
		
		JTextField tArea = new JTextField(100);
		multiplayScreenBottom.add(tArea);		
		
		multiplayScreen2.add(multiplayScreenCenter, BorderLayout.CENTER);
		multiplayScreen2.add(multiplayScreenEast, BorderLayout.EAST);
		multiplayScreen2.add(multiplayScreenBottom, BorderLayout.SOUTH);
	}
	/*���� ȭ��*/
	public void scoreScreen()
	{
		
	}	
	
}
