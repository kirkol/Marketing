package WB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import net.proteanit.sql.DbUtils;

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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ChooseMachineType_ilosc_rok extends JFrame {

	private JPanel contentPane;
	
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
					ChooseMachineType_ilosc_rok frame = new ChooseMachineType_ilosc_rok(connection);
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
	public ChooseMachineType_ilosc_rok(final Connection connection) {
		
		Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Wybor typu maszyny");
		setBounds(200, 200, 408, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"wszystkie", "H", "O"}));
		comboBox_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblMenu = new JLabel("Wybierz typ maszyny");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
			public void actionPerformed(ActionEvent e2) {
				
				String status = comboBox_1.getSelectedItem().toString();
				if(status=="H"){
					status = "AND verkoop.statuscode = 'H' ";
				}
				else if(status=="O"){
					status = "AND verkoop.statuscode = 'O' ";	
				}
				else{
					status = "";
				}
				
				int wartosc = 0;
				String rok = "";
				String query = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				if(comboBox.getSelectedItem().equals("typ maszyny")){
					JOptionPane.showMessageDialog(null, "Wybierz typ maszyny");
				}else{
						try {
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
							
							if(comboBox.getSelectedItem().equals("WSZYSTKIE")){
								//wszystkie maszyny
								try {
									
									query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ BasicQuery4NewTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) " +" UNION ALL " + BasicQuery4OldTable +" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("typ maszyny")){
								// gdy nie wybrano zadnego typu maszyny
								JOptionPane.showMessageDialog(null, "Nie wybrano typu maszyny");
							
							}else if(comboBox.getSelectedItem().equals("MN 560/630/710")){
								
								// gdy wybrano MN 560/630/710
								// szuka artikelcode like:
								// TURMN560%, TURMN630%, TURMN710%, TURMN660%, TURMN720%, CONTURH56%, CONTURH66%, CONTURH72%, CONTURHD560%, CONTURHD660%, CONTURHD720%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+
												BasicQuery4NewTable +
												"AND (verkoopdetail.artikelcode LIKE 'TURMN560%' OR verkoopdetail.artikelcode LIKE 'TURMN630%' OR verkoopdetail.artikelcode LIKE 'TURMN710%' OR verkoopdetail.artikelcode LIKE 'TURMN720%' "+
												"OR verkoopdetail.artikelcode LIKE 'TURMN660%' OR verkoopdetail.artikelcode LIKE 'CONTURH56%' OR verkoopdetail.artikelcode LIKE 'CONTURH66%' OR verkoopdetail.artikelcode LIKE 'CONTURH72%' "+
												"OR verkoopdetail.artikelcode LIKE 'CONTURHD560%' OR verkoopdetail.artikelcode LIKE 'CONTURHD660%' OR verkoopdetail.artikelcode LIKE 'CONTURHD720%' "+
												"OR verkoopdetail.artikelcode LIKE 'TURSMN560%' OR verkoopdetail.artikelcode LIKE 'TURSMN630%' OR verkoopdetail.artikelcode LIKE 'TURSMN710%')"+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL  "+
												BasicQuery4OldTable +
												"AND (verkoopdetail_old.artikelcode LIKE 'TURMN560%' OR verkoopdetail_old.artikelcode LIKE 'TURMN630%' OR verkoopdetail_old.artikelcode LIKE 'TURMN710%' OR "+
												"verkoopdetail_old.artikelcode LIKE 'TURMN720%' OR verkoopdetail_old.artikelcode LIKE 'TURMN660%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH56%' OR "+
												"verkoopdetail_old.artikelcode LIKE 'CONTURH66%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH72%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD560%' "+
												"OR verkoopdetail_old.artikelcode LIKE 'CONTURHD660%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD720%' "+
												"OR verkoopdetail_old.artikelcode LIKE 'TURSMN560%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN630%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN710%')"
												+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("MN 800/930/1100")){
								
								// gdy wybrano MN 800/930/1100
								// szuka artikelcode like:
								// TURMN800%, TURMN930%, TURMN1100%, CONTURH83%, CONTURHD830%, CONTURHD970%. CONTURHD1100, CONTURHDS830%, CONTURHS83%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'TURMN800%' OR verkoopdetail.artikelcode LIKE 'TURMN930%' OR verkoopdetail.artikelcode LIKE 'TURMN1100%' OR verkoopdetail.artikelcode LIKE 'CONTURH83%' OR "+
												"verkoopdetail.artikelcode LIKE 'CONTURHD830%' OR verkoopdetail.artikelcode LIKE 'CONTURHD970%' OR verkoopdetail.artikelcode LIKE 'CONTURHD1100%' OR verkoopdetail.artikelcode LIKE 'CONTURHDS830%' OR "+
												"verkoopdetail.artikelcode LIKE 'CONTURHS83%' OR verkoopdetail.artikelcode LIKE 'TURSMN800%' OR verkoopdetail.artikelcode LIKE 'TURSMN930%' OR verkoopdetail.artikelcode LIKE 'TURSMN1100%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												"UNION ALL "+
												BasicQuery4OldTable +
												"AND (verkoopdetail_old.artikelcode LIKE 'TURMN800%' OR verkoopdetail_old.artikelcode LIKE 'TURMN930%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1100%' OR verkoopdetail_old.artikelcode LIKE 'CONTURH83%' "+
												"OR verkoopdetail_old.artikelcode LIKE 'CONTURHD830%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD970%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHD1100%' OR verkoopdetail_old.artikelcode LIKE 'CONTURHDS830%' "+
												"OR verkoopdetail_old.artikelcode LIKE 'CONTURHS83%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN800%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN930%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1100%') "
												+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("MN 1150/1350/1550")){
								
								// gdy wybrano MN 1150/1350/1550
								// szuka artikelcode like:
								// TURMN1150%, TURMN1350%, TURMN1550%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'TURMN1150%' OR verkoopdetail.artikelcode LIKE 'TURMN1350%' OR verkoopdetail.artikelcode LIKE 'TURMN1550%' OR verkoopdetail.artikelcode LIKE 'TURSMN1150%' "+
												"OR verkoopdetail.artikelcode LIKE 'TURSMN1350%' OR verkoopdetail.artikelcode LIKE 'TURSMN1550%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'TURMN1150%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1350%' OR verkoopdetail_old.artikelcode LIKE 'TURMN1550%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1150%' "+
												"OR verkoopdetail_old.artikelcode LIKE 'TURSMN1350%' OR verkoopdetail_old.artikelcode LIKE 'TURSMN1550%') "
												+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("SC 560/630/710")){
								
								// gdy wybrano SC 560/630/710
								// szuka artikelcode like:
								// TURSC560%, TURSC630%, TURSC710%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'TURSC560%' OR verkoopdetail.artikelcode LIKE 'TURSC630%' OR verkoopdetail.artikelcode LIKE 'TURSC710%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'TURSC560%' OR verkoopdetail_old.artikelcode LIKE 'TURSC630%' OR verkoopdetail_old.artikelcode LIKE 'TURSC710%') "
												+" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("SC 800/930/1100")){
								
								// gdy wybrano SC 800/930/1100
								// szuka artikelcode like:
								// TURSC800%, TURSC930%, TURSC1100%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'TURSC800%' OR verkoopdetail.artikelcode LIKE 'TURSC930%' OR verkoopdetail.artikelcode LIKE 'TURSC1100%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'TURSC800%' OR verkoopdetail_old.artikelcode LIKE 'TURSC930%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1100%') "
												+ "GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("SC 1150/1350/1550")){
								
								// gdy wybrano SC 1150/1350/1550
								// szuka artikelcode like:
								// TURSC1150%, TURSC1350%, TURSC1550%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'TURSC1150%' OR verkoopdetail.artikelcode LIKE 'TURSC1350%' OR verkoopdetail.artikelcode LIKE 'TURSC1550%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'TURSC1150%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1350%' OR verkoopdetail_old.artikelcode LIKE 'TURSC1550%') "
												+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("FCT")){
								
								// gdy wybrano FCT
								// szuka artikelcode like:
								// FCT%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'FCT%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'FCT%') "
												+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("FTM")){
								
								// gdy wybrano FTM
								// szuka artikelcode like:
								// FTM%
								try {
									query="SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'FTM%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'FTM%') "
												+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("CONV")){
								
								// gdy wybrano CONV
								// szuka artikelcode like:
								// TUR560%, TUR630%, TUR 630%, TUR710%
								try {
									query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ 
												BasicQuery4NewTable +
												" AND (verkoopdetail.artikelcode LIKE 'TUR560%' OR verkoopdetail.artikelcode LIKE 'TUR630%' OR verkoopdetail.artikelcode LIKE 'TUR710%' OR verkoopdetail.artikelcode LIKE 'TUR 630%') "+
												" GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+
												" UNION ALL "+
												BasicQuery4OldTable +
												" AND (verkoopdetail_old.artikelcode LIKE 'TUR560%' OR verkoopdetail_old.artikelcode LIKE 'TUR630%' OR verkoopdetail_old.artikelcode LIKE 'TUR710%' OR verkoopdetail_old.artikelcode LIKE 'TUR 630%') "
												+"GROUP BY SUBSTRING(verkoop.LEVERDATUM_BEVESTIGD, 1, 4) "+ ") tabela GROUP BY tabela.rok ;";
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}else if(comboBox.getSelectedItem().equals("SPECIALS MACHINES")){
								
								// gdy wybrano SPECIAL MACHINES
								// szuka artikelcode like:
								// inne nic te co wyszukiwane wyzej
								try {
									query= "SELECT rok, SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+
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
												" UNION ALL  "+
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
									
									
								} catch (Exception e) {
									e.printStackTrace();
								}
								
							}
							
							PreparedStatement pst=connection.prepareStatement(query);
							ResultSet rs=pst.executeQuery();
							
							while (rs.next()){
								wartosc = rs.getInt("ilosc");
								rok = rs.getString("rok");
								dataset.setValue(wartosc, rok, rok);
							}
							JFreeChart chart = ChartFactory.createBarChart("Podsumowanie sprzedazy w roku", "rok", "Ilosc sprzedanych maszyn [EUR]", dataset, PlotOrientation.VERTICAL, false, true, true);
							CategoryPlot catPlot = chart.getCategoryPlot();
							CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
							ValueAxis axis2 = chart.getCategoryPlot().getRangeAxis();
							BarRenderer br = (BarRenderer) chart.getCategoryPlot().getRenderer();					
							br.setItemMargin(-2);
							Color kolor = new Color(102,179,255);
							br.setPaint(kolor);
							axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
							
							DecimalFormat decimalformat = new DecimalFormat("#");
							br.setItemLabelGenerator(new StandardCategoryItemLabelGenerator("{2}", decimalformat));;
							catPlot.setRenderer(br);
							br.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
							br.setItemLabelsVisible(true);
							chart.getCategoryPlot().setRenderer(br);
							
							axis2.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
							
							Plot plot = chart.getPlot();
							plot.setBackgroundPaint(Color.WHITE);
							Font ffont = new Font("Calibri", Font.PLAIN, 9);
							axis.setTickLabelFont(ffont);
							
							
							ChartFrame okienko = new ChartFrame("Wykres wartosc(rok)", chart);
							okienko.setVisible(true);
							okienko.setSize(600,600);
					
							
							pst.close();
							rs.close();
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		btnTabelaSprzedayMaszyn_1.setToolTipText("");
		btnTabelaSprzedayMaszyn_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(8)
					.addComponent(btnTabelaSprzedayMaszyn_1, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(comboBox, 0, 366, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMenu, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
							.addGap(8))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(btnTabelaSprzedayMaszyn_1, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-420, -100, 893, 621);
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel);
	}
}
