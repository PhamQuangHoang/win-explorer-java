package I_O;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.omg.CORBA.OBJECT_NOT_EXIST;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Explorer extends JFrame {

	private JPanel contentPane;
	private JTextField txtpath;
	private JTextField txtsearch;
	private JTable table;
	public DefaultTableModel model = new DefaultTableModel(0, 0);
	private JTree tree;
	public DefaultTreeModel treemodel;
	public DefaultMutableTreeNode root;
	public static String Current_Folder = "C:\\";
	public static String Previos_Folder = "C:\\";
	public String HEADER[] = new String[] { "Name", "Date modified", "Type", "Size" };

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Explorer frame = new Explorer();
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
	public Explorer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 854, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setSize(800, 500);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);

		JTree tree = new JTree();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane_1.setViewportView(tree);

		// the File tree
		// Add a listener
		root = new DefaultMutableTreeNode("this PC");
		File diskC = new File(Current_Folder);
		DefaultMutableTreeNode DiskC = new DefaultMutableTreeNode(FileNode(diskC));
		DefaultMutableTreeNode DiskD = new DefaultMutableTreeNode("Local Disk (D:)");
		DefaultMutableTreeNode DiskE = new DefaultMutableTreeNode("Local Disk (E:)");

		createChildren(diskC, DiskC);
		root.add(DiskC);
		root.add(DiskD);
		root.add(DiskE);

		treemodel = new DefaultTreeModel(root);
		tree.setModel(treemodel);
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JLabel lblname = new JLabel("NameOfFile");
		panel_2.add(lblname);

		JButton btnRename = new JButton("rename");
		btnRename.addMouseListener(new MouseAdapter() {
			@Override
		 	public void mouseClicked(MouseEvent arg0) {
            	  String  newname = JOptionPane.showInputDialog("input younewname");
            	  String nameofFile =txtpath.getText()+"\\"+lblname.getText(); 
            	  String newpathname = txtpath.getText()+"\\"+newname;
                  File file = new File(nameofFile);
                  boolean renameto = file.renameTo(new File(newpathname));
//            		if(file.isDirectory()) {
//            			File newdir = new File(file.getParent() + "\\" + newname );
//            			System.out.println(file.getParent());
//            		}
                  if(renameto) {
            			  JOptionPane.showConfirmDialog(null, "rename success");
            			  
            		  }else JOptionPane.showConfirmDialog(null, "rename false");
					
			
			}
		});
		panel_2.add(btnRename);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);

		JPanel panel_3 = new JPanel();
		panel.add(panel_3);

		JButton btnundo = new JButton("<<");
		btnundo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				Current_Folder = Previos_Folder;
				model = CreateTableData(model, Current_Folder);
				txtpath.setText(Current_Folder);
			}
		});
		panel_3.add(btnundo);

		JButton btnNext = new JButton(">>");
		panel_3.add(btnNext);
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
       
			}
		});
		txtpath = new JTextField();
		panel.add(txtpath);
		txtpath.setColumns(40);

		txtsearch = new JTextField();
		panel.add(txtsearch);
		txtsearch.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				// int row = table.rowAtPoint(point);
				int row = table.getSelectedRow();
				String name = table.getModel().getValueAt(row, 0).toString();
				lblname.setText(name);
				if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
					Previos_Folder = Current_Folder;
					Current_Folder = Current_Folder + name + "\\";
					File file = new File(Current_Folder);
					if (file.isDirectory()) {
						if(file.exists()) {
							model = CreateTableData(model, Current_Folder);
							txtpath.setText(Current_Folder);
						}
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(HEADER);
		model = CreateTableData(model, Current_Folder);
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode nodeselected = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String name = nodeselected.getUserObject().toString();
				Previos_Folder = Current_Folder;
				Current_Folder = Current_Folder + name + "\\";
				model = CreateTableData(model, Current_Folder.substring(3));
				txtpath.setText(Current_Folder.substring(3));
                lblname.setText(name);
			}
		});

	}

	public DefaultTableModel CreateTableData(DefaultTableModel tb, String folder) {
		if (tb.getRowCount() > 0) {
			for (int i = tb.getRowCount() - 1; i > -1; i--) {
				tb.removeRow(i);
			}
		}
		File file = new File(folder);
		File[] list = file.listFiles();
		for (File files : list) {
			String name = files.getName();
			String type = "";
			String size = "";
			if (files.isFile()) {
				type = "." + name.substring(name.lastIndexOf(".") + 1);
				long fileSizeInBytes = files.length();
				long fileSizeInKB = fileSizeInBytes / 1024;
				size = "" + fileSizeInKB + " kb";
			}
			if (files.isDirectory()) {
				type = "Folder";
			}
			SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			String lastmodifiedate = date.format(files.lastModified());
			tb.addRow(new Object[] { name, lastmodifiedate, type, size });
		}
		return tb;
	}

	public String FileNode(File file) {
		String name = file.getName();
		if (name.equals("")) {
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}

	public void createChildren(File fileRoot, DefaultMutableTreeNode node) {
		File[] files = fileRoot.listFiles();
		if (files == null)
			return;

		for (File file : files) {
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(FileNode(file));
			node.add(childNode);
			if (file.isDirectory()) {
				createChildren(file, childNode);
			}
          
		}
	}

}
