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
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

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
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ChooseTimePeriod_wartosc_klient extends JFrame {

	private JPanel contentPane;
	private JFrame frame;
	public static int a;
	JDateChooser dateChooserOD;
	JDateChooser dateChooserDO;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Connection connection = RCPdatabaseConnection.dbConnector("tosia", "1234");
				try {
					ChooseTimePeriod_wartosc_klient frame = new ChooseTimePeriod_wartosc_klient(connection);
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
	public ChooseTimePeriod_wartosc_klient(final Connection connection) {
		
		Image img = new ImageIcon(this.getClass().getResource("/BackgroundImage.jpg")).getImage();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Przedzial czasu");
		setBounds(200, 200, 408, 222);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"wszystkie", "H", "O"}));
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblMenu = new JLabel("Wybierz przedzia\u0142 czasu");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Century", Font.BOLD, 24));
		
		JLabel lblOd = new JLabel("OD:");
		lblOd.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		dateChooserOD = new JDateChooser();
		dateChooserOD.setDateFormatString("yyyy-MM-dd");
		dateChooserDO = new JDateChooser();
		dateChooserDO.setDateFormatString("yyyy-MM-dd");
		
		JButton btnStwrzWykres = new JButton("Stw\u00F3rz wykres");
		btnStwrzWykres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String status = comboBox.getSelectedItem().toString();
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
				String klient = "";
				DefaultCategoryDataset dataset = new DefaultCategoryDataset();
				
				String dataODString;
				String dataDOString;
				Date dataOD = dateChooserOD.getDate();
				Date dataDO = dateChooserDO.getDate();
				DateFormat FD = new SimpleDateFormat("yyyy-MM-dd");
				if(dataOD==null || dataDO==null){
					dataODString = "";
					dataDOString = "";
				}else{
					dataODString = FD.format(dataOD);
					dataDOString = FD.format(dataDO);
				}
				
				System.out.println(dataODString);
				System.out.println(dataDOString);
				
				if(dataODString.equals("")||dataDOString.equals("")){
					// ma nic nie robic, bo pola daty sa puste
				}else{
					if(dataOD.compareTo(dataDO) > 0){
						JOptionPane.showMessageDialog(null, "Data OD musi byæ przed dat¹ DO");
					}else{
				
						try {
							
							String BasicQuery4NewTable = "SELECT verkoop.KLANTNR AS NrKlienta, verkoopdetail.KLANTNAAM AS Klient, COUNT(verkoop.KLANTNR) AS iloscWTabeli, "+
									"CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END AS CenaSprzedazyEUR, "
									+"SUM(CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END) AS SumaCenySprzedazyEURWTabeli  "+
									"FROM verkoopdetail "
									+ "LEFT JOIN artikel_algemeen ON verkoopdetail.ARTIKELCODE = artikel_algemeen.ARTIKELCODE "
									+ "LEFT JOIN verkoop ON verkoopdetail.KLANTNR = verkoop.KLANTNR AND verkoopdetail.BESTELLINGNR = verkoop.BESTELLINGNR "
									+ "LEFT JOIN klant ON verkoopdetail.klantnr = klant.KLANTNR "
									+ "WHERE artikel_algemeen.MACHINETYPE=12 AND verkoop.LEVERDATUM_BEVESTIGD BETWEEN '"+dataODString+"' AND '"+dataDOString+"' AND klant.naam<>'TOKARKI MAGAZYNOWE' AND verkoop.DUMMYSTRING<>'WZ' AND verkoopdetail.BESTELD<>0  "+status;
					
							String BasicQuery4OldTable = "SELECT verkoop.KLANTNR AS NrKlienta, verkoopdetail_old.KLANTNAAM AS Klient, COUNT(verkoop.KLANTNR) AS iloscWTabeli, "+
									"CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END AS CenaSprzedazyEUR, "+
									"SUM(CASE WHEN verkoop.MUNT='PLN' THEN ROUND(verkoop.VERKOOPPRIJS/4.2, 2) ELSE verkoop.VERKOOPPRIJS END) AS SumaCenySprzedazyEURWTabeli "+
									"FROM verkoopdetail_old "
									+ "LEFT JOIN artikel_algemeen ON verkoopdetail_old.ARTIKELCODE = artikel_algemeen.ARTIKELCODE "
									+ "LEFT JOIN verkoop ON verkoopdetail_old.KLANTNR = verkoop.KLANTNR AND verkoopdetail_old.BESTELLINGNR = verkoop.BESTELLINGNR "
									+ "LEFT JOIN klant ON verkoopdetail_old.klantnr = klant.KLANTNR "
									+ "WHERE artikel_algemeen.MACHINETYPE=12 AND verkoop.LEVERDATUM_BEVESTIGD BETWEEN '"+dataODString+"' AND '"+dataDOString+"' AND klant.naam<>'TOKARKI MAGAZYNOWE' AND verkoop.DUMMYSTRING<>'WZ' AND verkoopdetail_old.BESTELD<>0 "+status;
							
							String query="SELECT tabela.NrKlienta, tabela.Klient , SUM(iloscWTabeli) AS ilosc, CAST(SUM(SumaCenySprzedazyEURWTabeli) AS DECIMAL(10,2)) AS SumaCenySprzedazyEUR FROM ("+ BasicQuery4NewTable +" GROUP BY verkoop.KLANTNR " +" UNION " + BasicQuery4OldTable +" GROUP BY verkoop.KLANTNR "+ ") tabela GROUP BY tabela.NrKlienta";;
							PreparedStatement pst=connection.prepareStatement(query);
							ResultSet rs=pst.executeQuery();
							
							while (rs.next()){
								wartosc = rs.getInt("SumaCenySprzedazyEUR");
								klient = rs.getString("Klient");
								dataset.setValue(wartosc, klient, klient);
							}
							JFreeChart chart = ChartFactory.createBarChart("Podsumowanie sprzedazy klienta", "Klient", "Wartosc sprzedanych maszyn [EUR]", dataset, PlotOrientation.VERTICAL, false, true, true);
							CategoryPlot catPlot = chart.getCategoryPlot();
							CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
							ValueAxis axis2 = chart.getCategoryPlot().getRangeAxis();
							BarRenderer br = (BarRenderer) chart.getCategoryPlot().getRenderer();					
							br.setItemMargin(-10);
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
							
							
							ChartFrame okienko = new ChartFrame("Wykres wartosc(klient)", chart);
							okienko.setVisible(true);
							okienko.setSize(600,600);
					
							
							pst.close();
							rs.close();
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnStwrzWykres.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel label = new JLabel("OD:");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMenu, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
					.addGap(8))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(89)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblOd)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(dateChooserOD, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(dateChooserDO, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)))
					.addGap(88))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(btnStwrzWykres)
					.addContainerGap(133, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(6)
					.addComponent(lblMenu, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblOd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(dateChooserOD, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(dateChooserDO, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnStwrzWykres, GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(-420, -100, 893, 621);
		lblNewLabel.setIcon(new ImageIcon(img));
		contentPane.add(lblNewLabel);
	}
}
