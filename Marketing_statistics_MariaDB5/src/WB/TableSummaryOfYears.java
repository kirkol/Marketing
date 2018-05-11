package WB;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.oxbow.swingbits.table.filter.TableRowFilterSupport;

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


public class TableSummaryOfYears extends JFrame {

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
					TableSummaryOfYears frame = new TableSummaryOfYears(connection, "WSZYSTKIE");
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
	public TableSummaryOfYears(final Connection connection, final String typ_maszyny) {
		
		setBackground(Color.WHITE);
		setTitle("Podsumowanie lat w zale\u017Cno\u015Bci od typu maszyny");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 579, 563);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblMojeMenu = new JLabel("Podsumowanie lat w zale\u017Cno\u015Bci od typu");
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
		TableRowFilterSupport.forTable(table).apply();
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
		ShowTable(connection, table, typ_maszyny, "wszystkie");
		
		JLabel lblUwaga = new JLabel("<html>UWAGA: Program analizuje jedynie maszyny (artyku\u0142y), kt\u00F3re w swoim opisie maj\u0105 ustawiony typ maszyny = 12 (FAT LATHES) w HacoSofcie</html>");
		lblUwaga.setVerticalAlignment(SwingConstants.TOP);
		lblUwaga.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblTypMaszyny = new JLabel(typ_maszyny);
		lblTypMaszyny.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypMaszyny.setFont(new Font("Century", Font.BOLD, 18));
		
		JLabel lbluwagaProgram = new JLabel("<html>UWAGA 2: Program korzysta z bazy danych HacoSofta z dnia poprzedniego</html>");
		lbluwagaProgram.setVerticalAlignment(SwingConstants.TOP);
		lbluwagaProgram.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lbluwagaCeny = new JLabel("<html>UWAGA 3: Ceny zam\u00F3wie\u0144 w PLN przeliczane s\u0105 na EUR (1EUR = 4,2PLN)</html>");
		lbluwagaCeny.setVerticalAlignment(SwingConstants.TOP);
		lbluwagaCeny.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()==1){
					ShowTable(connection, table, typ_maszyny, arg0.getItem().toString());
				}
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"wszystkie", "H", "O"}));
//						Image img3 = new ImageIcon(this.getClass().getResource("/PdfIcon_mini.png")).getImage();
//						WylaczAlarm.setIcon(new ImageIcon(img3));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lblTypMaszyny, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lblMojeMenu, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lbluwagaProgram, GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
						.addComponent(lbluwagaCeny, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUwaga, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 533, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblStatus)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(45)
					.addComponent(lblMojeMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTypMaszyny, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatus)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
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
	
	public void ShowTable(Connection connection, JTable table, String typMaszyny, String status)
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
		
		String BasicQuery4NewTable = "SELECT SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) AS rok, COUNT(SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4)) AS iloscWTabeli, "+
				"CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END AS CenaSprzedazyEUR, "
				+"SUM(CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END) AS SumaCenySprzedazyEURWTabeli  "+
				"FROM verkoopdetail "
				+ "LEFT JOIN artikel_algemeen ON verkoopdetail.ARTIKELCODE = artikel_algemeen.ARTIKELCODE "
				+ "LEFT JOIN verkoop ON verkoopdetail.KLANTNR = verkoop.KLANTNR AND verkoopdetail.BESTELLINGNR = verkoop.BESTELLINGNR "
				+ "LEFT JOIN klant ON verkoopdetail.klantnr = klant.KLANTNR "
				+ "WHERE artikel_algemeen.MACHINETYPE=12 AND klant.naam<>'TOKARKI MAGAZYNOWE' AND verkoop.DUMMYSTRING<>'WZ' AND verkoopdetail.BESTELD<>0 "+status;

		String BasicQuery4OldTable = "SELECT SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) AS rok, COUNT(SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4)) AS iloscWTabeli, "+
				"CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END AS CenaSprzedazyEUR, "+
				"SUM(CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END) AS SumaCenySprzedazyEURWTabeli "+
				"FROM verkoopdetail_old "
				+ "LEFT JOIN artikel_algemeen ON verkoopdetail_old.ARTIKELCODE = artikel_algemeen.ARTIKELCODE "
				+ "LEFT JOIN verkoop ON verkoopdetail_old.KLANTNR = verkoop.KLANTNR AND verkoopdetail_old.BESTELLINGNR = verkoop.BESTELLINGNR "
				+ "LEFT JOIN klant ON verkoopdetail_old.klantnr = klant.KLANTNR "
				+ "WHERE artikel_algemeen.MACHINETYPE=12 AND klant.naam<>'TOKARKI MAGAZYNOWE' AND verkoop.DUMMYSTRING<>'WZ' AND verkoopdetail_old.BESTELD<>0 "+status;
		
		if(typMaszyny == "WSZYSTKIE"){
			//wszystkie maszyny
			try {
				
				String query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ BasicQuery4NewTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) " +" UNION " + BasicQuery4OldTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "typ maszyny"){
			// gdy nie wybrano zadnego typu maszyny
			JOptionPane.showMessageDialog(null, "Nie wybrano typu maszyny");
		
		}else if(typMaszyny == "MN 560/630/710"){
			
			// gdy wybrano MN 560/630/710
			// szuka artikelcode like:
			// TURMN560%, TURMN630%, TURMN710%, TURMN660%, TURMN720%, CONTURH56%, CONTURH66%, CONTURH72%, CONTURHD560%, CONTURHD660%, CONTURHD720%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+
							BasicQuery4NewTable +
							"AND (verkoopdetail.artikelcode LIKE 'TURMN560%' OR verkoopdetail.artikelcode LIKE 'TURMN630%' OR verkoopdetail.artikelcode LIKE 'TURMN710%' OR verkoopdetail.artikelcode LIKE 'TURMN720%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURMN660%' OR verkoopdetail.artikelcode LIKE 'CONTURH56%' OR verkoopdetail.artikelcode LIKE 'CONTURH66%' OR verkoopdetail.artikelcode LIKE 'CONTURH72%' "+
							"OR verkoopdetail.artikelcode LIKE 'CONTURHD560%' OR verkoopdetail.artikelcode LIKE 'CONTURHD660%' OR verkoopdetail.artikelcode LIKE 'CONTURHD720%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSMN560%' OR verkoopdetail.artikelcode LIKE 'TURSMN630%' OR verkoopdetail.artikelcode LIKE 'TURSMN710%')"+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION  "+
							BasicQuery4OldTable +
							"AND (verkoopdetail_old.artikelcode LIKE 'TURMN560%' OR verkoopdetail_old.artikelcode LIKE 'TURMN630%' OR verkoopdetail_old.artikelcode LIKE 'TURMN710%' OR "+
							"verkoopdetail_old.artikelcode LIKE 'TURMN720%' OR verkoopdetail_old.artikelcode LIKE 'TURMN660%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH56%' OR "+
							"verkoopdetail_old.artikelcode LIKE 'CONTURH66%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH72%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD560%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'CONTURHD660%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD720%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSMN560%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN630%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN710%')"
							+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "MN 800/930/1100"){
			
			// gdy wybrano MN 800/930/1100
			// szuka artikelcode like:
			// TURMN800%, TURMN930%, TURMN1100%, CONTURH83%, CONTURHD830%, CONTURHD970%. CONTURHD1100, CONTURHDS830%, CONTURHS83%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'TURMN800%' OR verkoopdetail.artikelcode LIKE 'TURMN930%' OR verkoopdetail.artikelcode LIKE 'TURMN1100%' OR verkoopdetail.artikelcode LIKE 'CONTURH83%' OR "+
							"verkoopdetail.artikelcode LIKE 'CONTURHD830%' OR verkoopdetail.artikelcode LIKE 'CONTURHD970%' OR verkoopdetail.artikelcode LIKE 'CONTURHD1100%' OR verkoopdetail.artikelcode LIKE 'CONTURHDS830%' OR "+
							"verkoopdetail.artikelcode LIKE 'CONTURHS83%' OR verkoopdetail.artikelcode LIKE 'TURSMN800%' OR verkoopdetail.artikelcode LIKE 'TURSMN930%' OR verkoopdetail.artikelcode LIKE 'TURSMN1100%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							"UNION "+
							BasicQuery4OldTable +
							"AND (verkoopdetail_old.artikelcode LIKE 'TURMN800%' OR verkoopdetail_old.artikelcode LIKE 'TURMN930%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1100%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH83%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'CONTURHD830%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD970%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD1100%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHDS830%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'CONTURHS83%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN800%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN930%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1100%') "
							+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "MN 1150/1350/1550"){
			
			// gdy wybrano MN 1150/1350/1550
			// szuka artikelcode like:
			// TURMN1150%, TURMN1350%, TURMN1550%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'TURMN1150%' OR verkoopdetail.artikelcode LIKE 'TURMN1350%' OR verkoopdetail.artikelcode LIKE 'TURMN1550%' OR verkoopdetail.artikelcode LIKE 'TURSMN1150%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSMN1350%' OR verkoopdetail.artikelcode LIKE 'TURSMN1550%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'TURMN1150%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1350%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1550%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1150%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSMN1350%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1550%') "
							+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "SC 560/630/710"){
			
			// gdy wybrano SC 560/630/710
			// szuka artikelcode like:
			// TURSC560%, TURSC630%, TURSC710%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'TURSC560%' OR verkoopdetail.artikelcode LIKE 'TURSC630%' OR verkoopdetail.artikelcode LIKE 'TURSC710%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'TURSC560%' OR verkoopdetail_old.artikelcode LIKE 'TURSC630%' OR verkoopdetail_old.artikelcode LIKE 'TURSC710%') "
							+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "SC 800/930/1100"){
			
			// gdy wybrano SC 800/930/1100
			// szuka artikelcode like:
			// TURSC800%, TURSC930%, TURSC1100%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'TURSC800%' OR verkoopdetail.artikelcode LIKE 'TURSC930%' OR verkoopdetail.artikelcode LIKE 'TURSC1100%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'TURSC800%' OR verkoopdetail_old.artikelcode LIKE 'TURSC930%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1100%') "
							+ "GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "SC 1150/1350/1550"){
			
			// gdy wybrano SC 1150/1350/1550
			// szuka artikelcode like:
			// TURSC1150%, TURSC1350%, TURSC1550%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'TURSC1150%' OR verkoopdetail.artikelcode LIKE 'TURSC1350%' OR verkoopdetail.artikelcode LIKE 'TURSC1550%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'TURSC1150%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1350%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1550%') "
							+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "FCT"){
			
			// gdy wybrano FCT
			// szuka artikelcode like:
			// FCT%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'FCT%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'FCT%') "
							+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "FTM"){
			
			// gdy wybrano FTM
			// szuka artikelcode like:
			// FTM%
			try {
				String query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'FTM%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'FTM%') "
							+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "CONV"){
			
			// gdy wybrano CONV
			// szuka artikelcode like:
			// TUR560%, TUR630%, TUR 630%, TUR710%
			try {
				String query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
							BasicQuery4NewTable +
							" AND (verkoopdetail.artikelcode LIKE 'TUR560%' OR verkoopdetail.artikelcode LIKE 'TUR630%' OR verkoopdetail.artikelcode LIKE 'TUR710%' OR verkoopdetail.artikelcode LIKE 'TUR 630%') "+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION "+
							BasicQuery4OldTable +
							" AND (verkoopdetail_old.artikelcode LIKE 'TUR560%' OR verkoopdetail_old.artikelcode LIKE 'TUR630%' OR verkoopdetail_old.artikelcode LIKE 'TUR710%' OR verkoopdetail_old.artikelcode LIKE 'TUR 630%') "
							+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
				PreparedStatement pst=connection.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(typMaszyny == "SPECIALS MACHINES"){
			
			// gdy wybrano SPECIAL MACHINES
			// szuka artikelcode like:
			// inne nic te co wyszukiwane wyzej
			try {
				String query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+
							BasicQuery4NewTable +
							"AND NOT (verkoopdetail.artikelcode LIKE 'TURMN560%' OR verkoopdetail.artikelcode LIKE 'TURMN630%' OR verkoopdetail.artikelcode LIKE 'TURMN710%' OR verkoopdetail.artikelcode LIKE 'TURMN720%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURMN660%' OR verkoopdetail.artikelcode LIKE 'CONTURH56%' OR verkoopdetail.artikelcode LIKE 'CONTURH66%' OR verkoopdetail.artikelcode LIKE 'CONTURH72%' "+
							"OR verkoopdetail.artikelcode LIKE 'CONTURHD560%' OR verkoopdetail.artikelcode LIKE 'CONTURHD660%' OR verkoopdetail.artikelcode LIKE 'CONTURHD720%' OR verkoopdetail.artikelcode LIKE 'TURMN800%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURMN930%' OR verkoopdetail.artikelcode LIKE 'TURMN1100%' OR verkoopdetail.artikelcode LIKE 'CONTURH83%' OR "+
							"verkoopdetail.artikelcode LIKE 'CONTURHD830%' OR verkoopdetail.artikelcode LIKE 'CONTURHD970%' OR verkoopdetail.artikelcode LIKE 'CONTURHD1100%' OR verkoopdetail.artikelcode LIKE 'CONTURHDS830%' OR "+
							"verkoopdetail.artikelcode LIKE 'CONTURHS83%' OR verkoopdetail.artikelcode LIKE 'TURMN1150%' OR verkoopdetail.artikelcode LIKE 'TURMN1350%' OR verkoopdetail.artikelcode LIKE 'TURMN1550%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSC560%' OR verkoopdetail.artikelcode LIKE 'TURSC630%' OR verkoopdetail.artikelcode LIKE 'TURSC710%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSC800%' OR verkoopdetail.artikelcode LIKE 'TURSC930%' OR verkoopdetail.artikelcode LIKE 'TURSC1100%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSC1150%' OR verkoopdetail.artikelcode LIKE 'TURSC1350%' OR verkoopdetail.artikelcode LIKE 'TURSC1550%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSMN560%' OR verkoopdetail.artikelcode LIKE 'TURSMN630%' OR verkoopdetail.artikelcode LIKE 'TURSMN710%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSMN800%' OR verkoopdetail.artikelcode LIKE 'TURSMN930%' OR verkoopdetail.artikelcode LIKE 'TURSMN1100%' "+
							"OR verkoopdetail.artikelcode LIKE 'TURSMN1150%' OR verkoopdetail.artikelcode LIKE 'TURSMN1350%' OR verkoopdetail.artikelcode LIKE 'TURSMN1550%' "+
							"OR verkoopdetail.artikelcode LIKE 'FCT%' OR verkoopdetail.artikelcode LIKE 'FTM%' "+
							"OR verkoopdetail.artikelcode LIKE 'TUR560%' OR verkoopdetail.artikelcode LIKE 'TUR630%' OR verkoopdetail.artikelcode LIKE 'TUR710%' OR verkoopdetail.artikelcode LIKE 'TUR 630%')"+
							" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
							" UNION  "+
							BasicQuery4OldTable +
							"AND NOT (verkoopdetail_old.artikelcode LIKE 'TURMN560%' OR verkoopdetail_old.artikelcode LIKE 'TURMN630%' OR verkoopdetail_old.artikelcode LIKE 'TURMN710%' OR "+
							"verkoopdetail_old.artikelcode LIKE 'TURMN720%' OR verkoopdetail_old.artikelcode LIKE 'TURMN660%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH56%' OR "+
							"verkoopdetail_old.artikelcode LIKE 'CONTURH66%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH72%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD560%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'CONTURHD660%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD720%' OR verkoopdetail_old.artikelcode LIKE 'TURMN800%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURMN930%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1100%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH83%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'CONTURHD830%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD970%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD1100%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHDS830%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'CONTURHS83%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1150%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1350%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1550%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSC560%' OR verkoopdetail_old.artikelcode LIKE 'TURSC630%' OR verkoopdetail_old.artikelcode LIKE 'TURSC710%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSC800%' OR verkoopdetail_old.artikelcode LIKE 'TURSC930%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1100%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSC1150%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1350%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1550%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSMN560%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN630%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN710%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSMN800%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN930%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1100%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TURSMN1150%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1350%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1550%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'FCT%' OR verkoopdetail_old.artikelcode LIKE 'FTM%' "+
							"OR verkoopdetail_old.artikelcode LIKE 'TUR560%' OR verkoopdetail_old.artikelcode LIKE 'TUR630%' OR verkoopdetail_old.artikelcode LIKE 'TUR710%' OR verkoopdetail_old.artikelcode LIKE 'TUR 630%') "
							+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
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
	}
