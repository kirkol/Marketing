package WB;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class Menu extends JFrame {

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
					Menu frame = new Menu(connection);
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
	public Menu(final Connection connection) {
		setResizable(false);
		
		Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Menu");
		setBounds(100, 100, 408, 503);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		JButton btnNewButton = new JButton("Tabela maszyn z HacoSofta");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableOfOurMachines okno = new TableOfOurMachines(connection);
				okno.setVisible(true);
			}
		});
		btnNewButton.setToolTipText("Wy\u015Bwietla tabel\u0119 maszyn z HacoSofta");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnTabelaSprzedayMaszyn_1 = new JButton("Tabela sprzeda\u017Cy maszyn wg typu");
		btnTabelaSprzedayMaszyn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChooseMachineType okno = new ChooseMachineType(connection, "ogolne");
				okno.setVisible(true);
			}
		});
		btnTabelaSprzedayMaszyn_1.setToolTipText("");
		btnTabelaSprzedayMaszyn_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnPodumowanieLat = new JButton("Tabela sprzeda\u017Cy maszyn wg typu podsumowanie lat");
		btnPodumowanieLat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ChooseMachineType okno = new ChooseMachineType(connection, "podsumowanie lat");
				okno.setVisible(true);
			}
		});
		btnPodumowanieLat.setToolTipText("Program bierze pod uwage date potwierdzonej daty dostawy");
		btnPodumowanieLat.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnTabelaSprzedayMaszyn = new JButton("Tabela sprzeda\u017Cy maszyn wg miesiecy");
		btnTabelaSprzedayMaszyn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TableSummaryByMonths okno = new TableSummaryByMonths(connection);
				okno.setVisible(true);
			}
		});
		btnTabelaSprzedayMaszyn.setToolTipText("");
		btnTabelaSprzedayMaszyn.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnTabelaSprzedayMaszyn_2 = new JButton("Tabela sprzeda\u017Cy maszyn wg dealera");
		btnTabelaSprzedayMaszyn_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableSummaryByDealer okno = new TableSummaryByDealer(connection);
				okno.setVisible(true);
			}
		});
		btnTabelaSprzedayMaszyn_2.setToolTipText("");
		btnTabelaSprzedayMaszyn_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnWykresy = new JButton("Wykresy");
		btnWykresy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuWykresy okno = new MenuWykresy(connection);
				okno.setVisible(true);
			}
		});
		btnWykresy.setToolTipText("");
		btnWykresy.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JButton btnTabelaSprzedayMaszyn_3 = new JButton("Tabela sprzeda\u017Cy maszyn wg kraju");
		btnTabelaSprzedayMaszyn_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableSummaryByCountry okno = new TableSummaryByCountry(connection);
				okno.setVisible(true);
			}
		});
		btnTabelaSprzedayMaszyn_3.setToolTipText("");
		btnTabelaSprzedayMaszyn_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblMenu, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
								.addComponent(btnNewButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
								.addComponent(btnTabelaSprzedayMaszyn_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
								.addComponent(btnPodumowanieLat, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
							.addGap(8))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnTabelaSprzedayMaszyn_2, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnTabelaSprzedayMaszyn, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnTabelaSprzedayMaszyn_3, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnWykresy, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTabelaSprzedayMaszyn_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPodumowanieLat, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTabelaSprzedayMaszyn, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTabelaSprzedayMaszyn_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnTabelaSprzedayMaszyn_3, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnWykresy, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(70, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-420, -100, 893, 621);
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel);
	}
}
