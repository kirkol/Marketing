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
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;


public class TableSummaryByMonths extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Timer timer;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
				try {
					TableSummaryByMonths frame = new TableSummaryByMonths(connection);
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
	public TableSummaryByMonths(final Connection connection) {
		
		setBackground(Color.WHITE);
		setTitle("Podsumowanie lat wg miesiecy");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 579, 563);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==1){
					ShowTable(connection, table, e.getItem().toString());
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"wszystkie", "H", "O"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblMojeMenu = new JLabel("Podsumowanie sprzeda\u017Cy wg miesiecy");
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
		ShowTable(connection, table, "wszystkie");
		
		JLabel lblUwaga = new JLabel("<html>UWAGA: Program analizuje jedynie maszyny (artyku\u0142y), kt\u00F3re w swoim opisie maj\u0105 ustawiony typ maszyny = 12 (FAT LATHES) w HacoSofcie</html>");
		lblUwaga.setVerticalAlignment(SwingConstants.TOP);
		lblUwaga.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lbluwagaProgram = new JLabel("<html>UWAGA 2: Program korzysta z bazy danych HacoSofta z dnia poprzedniego</html>");
		lbluwagaProgram.setVerticalAlignment(SwingConstants.TOP);
		lbluwagaProgram.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lbluwagaCeny = new JLabel("<html>UWAGA 3: Ceny zam\u00F3wie\u0144 w PLN przeliczane s\u0105 na EUR (1EUR = 4,2PLN)</html>");
		lbluwagaCeny.setVerticalAlignment(SwingConstants.TOP);
		lbluwagaCeny.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel label = new JLabel("Status:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		

//						Image img3 = new ImageIcon(this.getClass().getResource("/PdfIcon_mini.png")).getImage();
//						WylaczAlarm.setIcon(new ImageIcon(img3));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lblMojeMenu, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lbluwagaProgram, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lbluwagaCeny, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUwaga, GroupLayout.PREFERRED_SIZE, 533, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(45)
					.addComponent(lblMojeMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(3)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUwaga)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbluwagaProgram)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbluwagaCeny)
					.addGap(19))
		);
		contentPane.setLayout(gl_contentPane);
		
		}
	
	public void ShowTable(Connection connection, JTable table, String status)
	{	
		if(status=="H"){
			status = "AND verkoop.statuscode = 'H' ";
		}
		else if(status=="O"){
			status = "AND verkoop.statuscode = 'O' ";	
		}
		else{
			status = "";
		}
		
		String BasicQuery4NewTable = "SELECT SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 7) AS rok, COUNT(SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 7)) AS iloscWTabeli, "+
				"CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END AS CenaSprzedazyEUR, "
				+"SUM(CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END) AS SumaCenySprzedazyEURWTabeli  "+
				"FROM verkoopdetail "
				+ "LEFT JOIN artikel_algemeen ON verkoopdetail.ARTIKELCODE = artikel_algemeen.ARTIKELCODE "
				+ "LEFT JOIN verkoop ON verkoopdetail.KLANTNR = verkoop.KLANTNR AND verkoopdetail.BESTELLINGNR = verkoop.BESTELLINGNR "
				+ "LEFT JOIN klant ON verkoopdetail.klantnr = klant.KLANTNR "
				+ "WHERE artikel_algemeen.MACHINETYPE=12 AND klant.naam<>'TOKARKI MAGAZYNOWE' AND verkoop.DUMMYSTRING<>'WZ' AND verkoopdetail.BESTELD<>0 "+status;

		String BasicQuery4OldTable = "SELECT SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 7) AS rok, COUNT(SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 7)) AS iloscWTabeli, "+
				"CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END AS CenaSprzedazyEUR, "+
				"SUM(CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END) AS SumaCenySprzedazyEURWTabeli "+
				"FROM verkoopdetail_old "
				+ "LEFT JOIN artikel_algemeen ON verkoopdetail_old.ARTIKELCODE = artikel_algemeen.ARTIKELCODE "
				+ "LEFT JOIN verkoop ON verkoopdetail_old.KLANTNR = verkoop.KLANTNR AND verkoopdetail_old.BESTELLINGNR = verkoop.BESTELLINGNR "
				+ "LEFT JOIN klant ON verkoopdetail_old.klantnr = klant.KLANTNR "
				+ "WHERE artikel_algemeen.MACHINETYPE=12 AND klant.naam<>'TOKARKI MAGAZYNOWE' AND verkoop.DUMMYSTRING<>'WZ' AND verkoopdetail_old.BESTELD<>0 "+status;
		
			//wszystkie maszyny
			try {
				// DO DEBUGINGU W RAZIE CZEGO
				
				//String query= BasicQuery4NewTable +" GROUP BY klant.LANDCODE "+" UNION ALL " + BasicQuery4OldTable +" GROUP BY klant.LANDCODE ";
				//String query= BasicQuery4NewTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+" UNION ALL " + BasicQuery4OldTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4)";
				//String query= "SELECT *, SUM(iloscWTabeli) AS ilosc FROM ("+ BasicQuery4NewTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) " +" UNION ALL " + BasicQuery4OldTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok";
				
				String query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ BasicQuery4NewTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 7) " +" UNION ALL " + BasicQuery4OldTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 7) "+ ") tabela GROUP BY tabela.rok";
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
