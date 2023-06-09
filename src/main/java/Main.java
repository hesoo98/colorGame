import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame {
    int a = 0;
    int playCount = 0;
    int highScore = 0;
    // 멤버변수
    Container pane = getContentPane();
    JPanel panel_top = new JPanel();
    JPanel panel_right = new JPanel();
    JPanel panel_center = new JPanel();

    JLabel timer_label = new JLabel();
    JLabel right_label = new JLabel();
    JLabel preview_label = new JLabel();

    JLabel[] content = new JLabel[1];
    Color[] preview_color = new Color[1];
    int color_count = 0;

    // 시작 화면
    JPanel first_panel = new JPanel();
    JPanel second_panel = new JPanel();
    JLabel name_label = new JLabel();
    JLabel time_label = new JLabel("시작을 위해서 원하는 색깔 개열을 눌러주세요");
    JLabel[] color_choice = new JLabel[3];
    JLabel tuto = new JLabel(new ImageIcon("tuto.png"));
    int color = 0;

    // JMenu
    JMenuBar mb = new JMenuBar();
    JMenu[] menu = new JMenu[2];
    String menu_String[] = { "File", "about this" };
    String filemenu_String[] = { "New", "EXIT" };
    // about_ this dialog
    JDialog about_dialog = new JDialog(this, "프로그램 설명 ", true);
    JTextArea about_ta = new JTextArea("\n" + "만든이 : 강전완, 김희수 \n");

    // 타이머 에요 !
    Timer timer = new Timer();
    int timer_count = 5;

    Timer timer_X = new Timer();
    Timer timer_O = new Timer();

    // ingame
    JLabel center_label = new JLabel();

    // score_label
    JLabel score_label = new JLabel();

    // content_count
    int content_count = 0;

    // score
    int score = 0;

    // 생성자
    public Main() {
        a = 4;
        System.out.println("Main");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("같은색깔찾기");
        setSize(800, 660);
        setLocation(600, 300);
        first_panel();
        dialog_new();
        jmenu_add();
        about_dialog_new();// about this-> about this 창뜨개하는거
        setVisible(true);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (a == 0) {
                    timer_label.setSize(new Dimension(timer_count++, 70));
                    if(timer_count > 700){
                        timer_label.setBackground(Color.red);
                    }
                    if (timer_count > 750) {
                        timer_count = 5;
                        gameOut();
                    }
                }
            }
        }, 0, 10);// default 100

        // o-o
        pane.setLayout(new BorderLayout());
        pane.add(panel_top, "North");
        pane.add(panel_right, "East");
        pane.add(panel_center, "Center");
        panel_top.add(timer_label);

        panel_right.setLayout(new FlowLayout());
        panel_right.add(right_label);

        panel_top.setLayout(new FlowLayout(FlowLayout.LEFT));

        right_label.setPreferredSize(new Dimension(200, 500));
        right_label.setLayout(new FlowLayout());

        right_label.add(preview_label);

        preview_label.setPreferredSize(new Dimension(150, 150));
        preview_label.setOpaque(true);
        preview_label.setBackground(Color.white);

        timer_label.setOpaque(true);
        timer_label.setBackground(Color.pink);
        timer_label.setPreferredSize(new Dimension(5, 70));

        // 선
        panel_top.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        panel_right.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        panel_center.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        preview_label.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        score_label.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        center_label.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        // 컨텐츠
        panel_center.add(center_label);
        center_label.setPreferredSize(new Dimension(500, 500));

        // 스코어
        right_label.add(score_label);
        right_label.setLayout(new FlowLayout());
        score_label.setPreferredSize(new Dimension(150, 50));
        score_label.setOpaque(true);
        score_label.setBackground(Color.white);

        panel_top.setVisible(false);
        panel_right.setVisible(false);
        panel_center.setVisible(false);
        first_panel.setVisible(true);
        second_panel.setVisible(true);
    }

    // 메서드
    public void update() {
        System.out.println("update");
        content_count++;
        content = new JLabel[(int) Math.pow(content_count, 2)];
        preview_color = new Color[(int) Math.pow(content_count, 2)];
        center_label.updateUI();
        center_label.setLayout(new GridLayout(content_count, content_count, 1,
                1));
        for (int i = 0; i < (int) Math.pow(content_count, 2); i++) {
            center_label.removeAll();
        }
        for (int i = 0; i < (int) Math.pow(content_count, 2); i++) {

            int random1 = (int) (Math.random() * 256);
            int random2 = (int) (Math.random() * 256);
            int random3 = (int) (Math.random() * 256);

            content[i] = new JLabel();
            content[i].setOpaque(true);
            content[i].setPreferredSize(new Dimension(150, 150));
            switch(color){
                case 1 : random1 = 255; break;
                case 2 : random2 = 255; break;
                case 3 : random3 = 255; break;
            }
            content[i].setBackground(new Color(random1, random2, random3));
            content[i].setBorder(BorderFactory.createLineBorder(Color.gray, 1));
            center_label.add(content[i]);

            content[i].addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    Object cmd = e.getSource();
                    if (((JLabel) cmd).getBackground() == preview_label
                            .getBackground()) {
                        score++;
                        if(score > highScore){
                            score_label.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
                        }
                        score_label.setText("Score : " + score);
                        ((JLabel) cmd).setBackground(Color.black);
                        PaintColor();
                        timer_reset();
                    } else {
                        score--;
                        if (score < 1) {
                            gameOut();
                        }
                        score_label.setText("Score : " + score);

                        timer_X.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Color getColor = ((JLabel) cmd).getBackground();
                                ((JLabel) cmd).setBackground(Color.red);
                                timer_X.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        ((JLabel) cmd).setBackground(getColor);
                                    }
                                }, 100);
                            }
                        }, 0);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

            });
        }
    }

    public void timer_reset() {
        timer_count = 5;
    }

    // 게임 시작 눌렀을 때
    public void gameOn() {
        a = 0;
        System.out.println("gameOn");
        score = 0;
        score_label.setText("Score : " + score);
        first_panel.setVisible(false);
        second_panel.setVisible(false);
        panel_top.setVisible(true);
        panel_right.setVisible(true);
        panel_center.setVisible(true);
        System.out.println("A");
        update();
        timer_reset();
        PaintColor();

    }

    public void gameRe() {
        a = 2;
        System.out.println("gameReplay");
        content_count = 0;
        panel_top.setVisible(false);
        panel_right.setVisible(false);
        panel_center.setVisible(false);

        first_panel.setVisible(true);
        second_panel.setVisible(true);
        timer_label.setSize(new Dimension(0, 70));
    }

    public void gameOut() {
        timer_label.setBackground(Color.red);
        score_label.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
        a = 1;
        System.out.println("gameOut");
        content_count = 0;
        panel_top.setVisible(false);
        panel_right.setVisible(false);
        panel_center.setVisible(false);

        first_panel.setVisible(true);
        second_panel.setVisible(true);
        timer_label.setSize(new Dimension(0, 70));

        System.out.println("dialog_show");

        if (highScore == 0 && playCount == 0) {
            dialog_show();
            highScore = score;
            time_label.setText(name_winner + "님의 Score는" + score + "점입니다. !");
        } else if (playCount != 0 && highScore < score) {
            dialog_show();
            highScore = score;
            time_label.setText("기록갱신!!" + name_winner + "님의 Score는" + score
                    + "점입니다. !");
        } else if (highScore > score) {
            time_label.setText("당신의 Score는" + score + "점입니다. !      1등은  무려 "
                    + highScore + "점!");
        }
        playCount++;
    }

    public void PaintColor() {
        System.out.println("PaintColor");
        int black_count = 0;
        for (int i = 0; i < Math.pow(content_count, 2); i++) {
            if (content[i].getBackground() == Color.black)
                black_count++;
        }
        if (black_count == Math.pow(content_count, 2)) {
            update();
        }
        color_count = 0;
        for (int i = 0; i < Math.pow(content_count, 2); i++) {
            if (!(content[i].getBackground() == Color.black)) {
                preview_color[color_count] = content[i].getBackground();
                color_count++;
            }
        }
        int preview_color_random = (int) (Math.random() * color_count);
        preview_label.setBackground(preview_color[preview_color_random]);
    }

    public void first_panel() {
        System.out.println("first_panel");
        first_panel.setBackground(Color.white);
        second_panel.setBackground(Color.white);
        pane.add(first_panel,"North");
        pane.add(second_panel,"Center");
        first_panel.add(name_label);
        first_panel.add(time_label);


        for (int j = 0; j < color_choice.length; j++) {
            color_choice[j] = new JLabel();
            color_choice[j].setPreferredSize(new Dimension(150, 150));
            color_choice[j].setOpaque(true);
            switch (j) {
                case 0:
                    color_choice[j].setBackground(Color.red);
                    break;
                case 1:
                    color_choice[j].setBackground(Color.green);
                    break;
                case 2:
                    color_choice[j].setBackground(Color.blue);
                    break;
            }
            second_panel.add(color_choice[j]);
            color_choice[j].addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Object cmd = e.getSource();
                    ((JLabel) cmd).setText("선택?");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Object cmd = e.getSource();
                    ((JLabel) cmd).setText("");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    Object cmd = e.getSource();
                    if(((JLabel) cmd).getBackground() == Color.red)
                        color = 1;
                    else if(((JLabel) cmd).getBackground() == Color.green)
                        color = 2;
                    else if(((JLabel) cmd).getBackground() == Color.blue)
                        color = 3;
                    gameOn();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

            });
            second_panel.add(tuto);
        }
    }

    // 메뉴 바
    public void jmenu_add() {
        JMenuItem[] JFMItem = new JMenuItem[filemenu_String.length];
        for (int i = 0; i < menu.length; i++) {
            menu[i] = new JMenu(menu_String[i]);
            mb.add(menu[i]);
        }

        for (int i = 0; i < JFMItem.length; i++) {
            JFMItem[i] = new JMenuItem(filemenu_String[i]);
            menu[0].add(JFMItem[i]);
            JFMItem[i].addActionListener(new MenuActionListner());
        }
        JMenuItem JMenu_aboutItem = new JMenuItem("about this");
        JMenu_aboutItem.addActionListener(new MenuActionListner());
        menu[1].add(JMenu_aboutItem);

        this.setJMenuBar(mb);
    }

    // about this-> about this 창뜨개하는거
    public void about_dialog_new() {
        about_dialog.setSize(300, 180);
        about_ta.setEditable(false);
        about_dialog.add(about_ta);
    }

    // 내부 클래스
    class MenuActionListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if (cmd.equals(filemenu_String[0])) {
                gameRe();
                time_label.setText("올바른 색깔을 골라주세요");
            } else if (cmd.equals(filemenu_String[1])) {
                System.exit(0);
            } else if (cmd.equals("about this")) {
                about_dialog.setVisible(true);
            }

        }
    }

    // -----------------------------------------------------------------------

    // 완료시 1등 이름 입력 dialog
    JDialog name_dialog = new JDialog(this, "1등을 축하합니다.", true);
    JLabel dia_name = new JLabel("이름 : ");
    JTextField dia_textField = new JTextField(10);
    JButton dia_bt = new JButton("완료");
    String name_winner = new String("");

    public void dialog_new() {
        name_dialog.setLocation(this.getX() + 200, this.getY() + 200);
        name_dialog.setSize(300, 180);
        name_dialog.setLayout(new FlowLayout());
        name_dialog.add(dia_name);
        name_dialog.add(dia_textField);
        name_dialog.add(dia_bt);

        dia_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name_winner = dia_textField.getText();
                name_dialog.setVisible(false);
            }
        });
    }

    public void dialog_show() {
        dia_textField.setText("");
        name_winner = "";
        name_dialog.setVisible(true);
    }

    // -----------------------------------------------------------------------
    // psvm
    public static void main(String[] args){
        new Main();
    }

}