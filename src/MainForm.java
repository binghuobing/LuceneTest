import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.SwingUtilities;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class MainForm extends javax.swing.JFrame {
	private JPanel jPanel1;
	private JTextField jTextFieldQuery;
	private JTextPane jTextPane1;
	private JComboBox jComboBoxPart;
	private JComboBox jComboBoxsufix;
	private JButton jButtonSearch;

	TikaIndexer indexer;
	Searcher searcher;

	public MainForm() {
		super();
		initGUI();
		try {
			searcher = new Searcher(Constants.indexDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			this.setFont(new java.awt.Font("Arial", 0, 10));
			this.setTitle("\u6587\u4ef6\u68c0\u7d22");
			{
				jPanel1 = new JPanel();
				getContentPane().add(jPanel1);
				jPanel1.setBounds(12, 27, 769, 45);
				jPanel1.setLayout(null);
				{
					jButtonSearch = new JButton();
					jPanel1.add(jButtonSearch);
					jButtonSearch.setText("\u641c\u7d22");
					jButtonSearch.setBounds(650, 5, 77, 34);
					jButtonSearch.addMouseListener(new MouseListenSearch());
				}
				{
					jTextFieldQuery = new JTextField();
					jPanel1.add(jTextFieldQuery);
					jTextFieldQuery.setBounds(213, 6, 431, 32);
				}
				{
					ComboBoxModel jComboBoxsufixModel = new DefaultComboBoxModel(
							new String[] { "所有类型", ".txt", ".doc", ".ppt",
									".mp3", ".html" });
					jComboBoxsufix = new JComboBox();
					jPanel1.add(jComboBoxsufix);
					jComboBoxsufix.setModel(jComboBoxsufixModel);
					jComboBoxsufix.setBounds(19, 9, 81, 24);
				}
				{
					ComboBoxModel jComboBoxPartModel = new DefaultComboBoxModel(
							new String[] { "全文", "文件名" });
					jComboBoxPart = new JComboBox();
					jPanel1.add(jComboBoxPart);
					jComboBoxPart.setModel(jComboBoxPartModel);
					jComboBoxPart.setBounds(117, 9, 84, 24);
				}
			}
			{
				jTextPane1 = new JTextPane();
				getContentPane().add(jTextPane1);
				jTextPane1.setBounds(12, 84, 781, 397);
				jTextPane1.setEditable(false);
			}

			pack();
			this.setSize(821, 531);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private class MouseListenSearch implements MouseListener {

		public void mouseClicked(MouseEvent e) {
			String query = jTextFieldQuery.getText();

			String field = Constants.FieldContent;
			if (jComboBoxPart.getSelectedIndex() == 1) {
				field = Constants.FieldFilename;
			}
			
			String sufix = null;
			if (jComboBoxsufix.getSelectedIndex() != 0)
				sufix = jComboBoxsufix.getSelectedItem().toString();

			try {
				if (sufix == null) {
					String result = searcher.search(field, query);
					jTextPane1.setText(result);
				} else {
					String result = searcher.search(sufix, field, query);
					jTextPane1.setText(result);
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

	}

}
