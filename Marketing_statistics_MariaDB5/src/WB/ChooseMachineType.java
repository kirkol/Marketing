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
import javax.swing.JComboBox;

public class ChooseMachineType extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	
	public static int a;
	public static String TypMaszyny = "";

	public static String getTypMaszyny() {
		return TypMaszyny;
	}

	public static void setTypMaszyny(String typMaszyny) {
		TypMaszyny = typMaszyny;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
				try {
					ChooseMachineType frame = new ChooseMachineType(connection, "ogolne");
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
	public ChooseMachineType(final Connection connection, final String option) {
		
		Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Wybor typu maszyny");
		setBounds(200, 200, 408, 222);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMenu = new JLabel("Wybierz typ maszyny");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addItem("typ maszyny");
		comboBox.addItem("WSZYSTKIE");
		comboBox.addItem("MN 560/630/710");
		comboBox.addItem("MN 800/930/1100");
		comboBox.addItem("MN 1150/1350/1550");
		comboBox.addItem("SC 560/630/710");
		comboBox.addItem("SC 800/930/1100");
		comboBox.addItem("SC 1150/1350/1550");
		comboBox.addItem("FCT");
		comboBox.addItem("FTM");
		comboBox.addItem("CONV");
		comboBox.addItem("SPECIALS MACHINES");
		
		JButton btnTabelaSprzedayMaszyn_1 = new JButton("Poka\u017C tabel\u0119");
		btnTabelaSprzedayMaszyn_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTypMaszyny(comboBox.getSelectedItem().toString());
				switch(option){
					case "ogolne":
						TableAccOfMachineType okno = new TableAccOfMachineType(connection, getTypMaszyny());
						okno.setVisible(true);
						break;
					case "podsumowanie lat":
						TableSummaryOfYears okno2 = new TableSummaryOfYears(connection, getTypMaszyny());
						okno2.setVisible(true);
						break;
					default:
						TableAccOfMachineType okno3 = new TableAccOfMachineType(connection, getTypMaszyny());
						okno3.setVisible(true);
				}
			}
		});
		btnTabelaSprzedayMaszyn_1.setToolTipText("");
		btnTabelaSprzedayMaszyn_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBox, Alignment.LEADING, 0, 364, Short.MAX_VALUE)
						.addComponent(lblMenu, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
						.addComponent(btnTabelaSprzedayMaszyn_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
					.addGap(8))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnTabelaSprzedayMaszyn_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(296, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-420, -100, 893, 621);
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel);
	}
}
