package WB;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.util.Timer;
import java.util.TimerTask;


public class TableOfSales extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Timer timer;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
			public void run() {
				try {
					TableOfSales frame = new TableOfSales(connection);
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
	public TableOfSales(final Connection connection) {
		
		setBackground(Color.WHITE);
		setTitle("Tabela maszyn");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 579, 563);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMojeMenu = new JLabel("Tabela sprzeda\u017Cy maszyn");
		lblMojeMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMojeMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		final SimpleDateFormat doNazwy = new SimpleDateFormat("yyyy.MM.dd");
		final Calendar date = Calendar.getInstance();
		Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		
		// co jeœli u¿ytkownik jest adminem
		
		if (Login.getAdmin()==true)
		{
		
		}
		
		JScrollPane scrollPane = new JScrollPane();
		
		table = new JTable();
		table.setBackground(Color.WHITE);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				table.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent me){
						if (me.getClickCount()==1){
						try {
							
						//po kliknieciu cos tu zrobi z tymi danymi
						int row = table.getSelectedRow();
						//setPaleta1(Integer.parseInt((table.getModel().getValueAt(row, 0)).toString()));
						//setPaleta2(Integer.parseInt((table.getModel().getValueAt(row, 0)).toString()));
						
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						}
					}
				}
						);
			}});
		scrollPane.setViewportView(table);
		ShowTable(connection, table);
		
		JLabel lblUwaga = new JLabel("<html>UWAGA: Program analizuje jedynie maszyny (artyku\u0142y), kt\u00F3re w swoim opisie maj\u0105 ustawiony typ maszyny = 12 (FAT LATHES) w HacoSofcie</html>");
		lblUwaga.setVerticalAlignment(SwingConstants.TOP);
		lblUwaga.setHorizontalAlignment(SwingConstants.LEFT);
//						Image img3 = new ImageIcon(this.getClass().getResource("/PdfIcon_mini.png")).getImage();
//						WylaczAlarm.setIcon(new ImageIcon(img3));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblUwaga, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 533, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lblMojeMenu, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(45)
					.addComponent(lblMojeMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUwaga, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addGap(55))
		);
		contentPane.setLayout(gl_contentPane);
		
		}
	
	public void ShowTable(Connection connection, JTable table)
	{
		try {
			String query="SELECT verkoopdetail.klantnr AS NrKlienta, verkoopdetail.BESTELLINGNR AS NrZamowienia, verkoopdetail.klantnaam AS Klient, verkoopdetail.artikelcode AS TypMaszyny, "+
						"verkoopdetail.artikelomschrijving AS Opis, verkoop.SERIENUMMER AS NrSeryjny, verkoopdetail.LEVERDATUM AS DataDostarczenia, verkoop.VERKOOPPRIJS AS CenaSprzedazy, "+
						"verkoop.MUNT AS Waluta, klant.LANDCODE AS KrajKlienta FROM verkoopdetail LEFT JOIN artikel_algemeen " +
						"ON verkoopdetail.ARTIKELCODE = artikel_algemeen.ARTIKELCODE LEFT JOIN verkoop ON verkoopdetail.KLANTNR = verkoop.KLANTNR "+
						"AND verkoopdetail.BESTELLINGNR = verkoop.BESTELLINGNR LEFT JOIN klant ON verkoopdetail.klantnr = klant.KLANTNR WHERE artikel_algemeen.MACHINETYPE=12;";
			PreparedStatement pst=connection.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
