package WB;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class MenuWykresy extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	public static int a;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
				try {
					MenuWykresy frame = new MenuWykresy(connection);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuWykresy(final Connection connection) {
		setResizable(false);
		
		Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Menu wykresy");
		setBounds(100, 100, 408, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMenu = new JLabel("Wykresy");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		JButton ilosc_klient_w_czasie = new JButton("Wykres ilo\u015B\u0107 (klient) w czasie");
		ilosc_klient_w_czasie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseTimePeriod_ilosc_klient okno = new ChooseTimePeriod_ilosc_klient(connection);
				okno.setVisible(true);
			}
		});
	
		ilosc_klient_w_czasie.setToolTipText("Wy\u015Bwietla wykres ilo\u015B\u0107(klient) w zale\u017Cno\u015Bci od wybranego przedzia\u0142u czasu");
		ilosc_klient_w_czasie.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton wartosc_rok_typ = new JButton("Wykres warto\u015B\u0107 (rok) dla danego typu");
		wartosc_rok_typ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseMachineType_wartosc_rok okno = new ChooseMachineType_wartosc_rok(connection);
				okno.setVisible(true);
			}
		});
		wartosc_rok_typ.setToolTipText("");
		wartosc_rok_typ.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton ilosc_rok_typ = new JButton("Wykres ilo\u015B\u0107 (rok) dla danego typu");
		ilosc_rok_typ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChooseMachineType_ilosc_rok okno = new ChooseMachineType_ilosc_rok(connection);
				okno.setVisible(true);
			}
		});
		ilosc_rok_typ.setToolTipText("Program bierze pod uwage date potwierdzonej daty dostawy");
		ilosc_rok_typ.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton ilosc_kraj_w_czasie = new JButton("Wykres ilo\u015B\u0107 (kraj) w czasie");
		ilosc_kraj_w_czasie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChooseTimePeriod_ilosc_kraj okno = new ChooseTimePeriod_ilosc_kraj(connection);
				okno.setVisible(true);
			}
		});
		ilosc_kraj_w_czasie.setToolTipText("Wy\u015Bwietla wykres ilo\u015B\u0107(kraj) w zale\u017Cno\u015Bci od wybranego przedzia\u0142u czasu");
		ilosc_kraj_w_czasie.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton wartosc_klient_w_czasie = new JButton("Wykres warto\u015B\u0107 (klient) w czasie");
		wartosc_klient_w_czasie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseTimePeriod_wartosc_klient okno = new ChooseTimePeriod_wartosc_klient(connection);
				okno.setVisible(true);
			}
		});
		wartosc_klient_w_czasie.setToolTipText("Wy\u015Bwietla wykres warto\u015B\u0107(klient) w zale\u017Cno\u015Bci od wybranego przedzia\u0142u czasu");
		wartosc_klient_w_czasie.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton wartosc_kraj_w_czasie = new JButton("Wykres warto\u015B\u0107 (kraj) w czasie");
		wartosc_kraj_w_czasie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseTimePeriod_wartosc_kraj okno = new ChooseTimePeriod_wartosc_kraj(connection);
				okno.setVisible(true);
			}
		});
		wartosc_kraj_w_czasie.setToolTipText("Wy\u015Bwietla wykres warto\u015B\u0107(kraj) w zale\u017Cno\u015Bci od wybranego przedzia\u0142u czasu");
		wartosc_kraj_w_czasie.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMenu, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
							.addGap(8))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(wartosc_kraj_w_czasie, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addGap(10))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(wartosc_klient_w_czasie, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
								.addComponent(ilosc_klient_w_czasie, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
								.addComponent(ilosc_kraj_w_czasie, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
							.addGap(10))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(ilosc_rok_typ, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(wartosc_rok_typ, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ilosc_klient_w_czasie, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ilosc_kraj_w_czasie, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wartosc_klient_w_czasie, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wartosc_kraj_w_czasie, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ilosc_rok_typ, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(wartosc_rok_typ, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(118, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-420, -100, 893, 621);
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel);
	}
}
