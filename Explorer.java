package I_O;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Explorer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtpath;
	private JTextField txtsearch;
	private JTable table;
	public DefaultTableModel model = new DefaultTableModel(0, 0);
	public DefaultTreeModel treemodel;
	public DefaultMutableTreeNode root;
	public File currentFile;
	public static String value = "";
	public static String current_path = "";
	public static String Filename = "";
	public static String destination_path = "";
	public static String Current_Folder = "D:\\";
	public String HEADER[] = new String[] { "Name", "Date modified", "Type", "Size" };
	public Path currentpath;

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
		this.setSize(1000, 700);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane scrollPane_1 = new JScrollPane();
		panel_1.add(scrollPane_1);

		JTree tree = new JTree();
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane_1.setViewportView(tree);

		// TẠO NODE CHO TREE
		root = new DefaultMutableTreeNode("this PC");
		// File diskC = new File("C:\\");
		File diskE = new File("E:\\");
		File diskD = new File("D:\\");

		// DefaultMutableTreeNode DiskC = new DefaultMutableTreeNode("C:\\");
		DefaultMutableTreeNode DiskD = new DefaultMutableTreeNode("D:\\");
		DefaultMutableTreeNode DiskE = new DefaultMutableTreeNode("E:\\");

		// createChildren(diskC, DiskC);

		createChildren(diskD, DiskD);
		createChildren(diskE, DiskE);
		// root.add(DiskC);
		root.add(DiskD);
		root.add(DiskE);

		// add data cho jtree
		treemodel = new DefaultTreeModel(root);
		tree.setModel(treemodel);
		// remove Default Icon của Jtree
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);

		JLabel lblname = new JLabel("Name");
		// Function RENAME
		JButton btnRename = new JButton("rename");
		btnRename.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Lấy tên mới
				String newname = JOptionPane.showInputDialog("input younewname");

				if (newname.equals("")) {
					JOptionPane.showConfirmDialog(null, "You need type your new name in this field !!");
				} else {
					// đường dẫn chỉ đến File có Tên củ
					String nameofFile = txtpath.getText() + "\\" + lblname.getText();
					// đường dẫn chỉ đến File có Tên mới
					String newpathname = txtpath.getText() + "\\" + newname;
					File file = new File(nameofFile);
					// Kiểm tra đã hoàn thành chưa
					boolean renameto = file.renameTo(new File(newpathname));
					if (renameto) {
						JOptionPane.showConfirmDialog(null, "rename success");

					} else
						JOptionPane.showConfirmDialog(null, "rename false");
				}
			}
		});

		JLabel lblSize_1 = new JLabel("size");

		JLabel lblType_1 = new JLabel("type");

		JLabel lblDateModified = new JLabel("date modified");
		// Copy Function
		JButton btnCopy = new JButton("copy");
		btnCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Đường dẫn gốc của File
				currentpath = Paths.get(current_path = txtpath.getText() + "\\" + lblname.getText());
				// Lưu tên củ
				Filename = lblname.getText();

				JOptionPane.showConfirmDialog(null, "Your file you want to copy is :" + lblname.getText());
			}
		});
		// Copy function 2
		JButton btnPaste = new JButton("paste");
		btnPaste.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Đường dẫn mới
				Path newpath = Paths.get(txtpath.getText() + "\\" + Filename);
				try {
					// thực hiện copy
					copyFile(currentpath, newpath);
					JOptionPane.showConfirmDialog(null, "Copy success");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup().addGap(102)
				.addComponent(lblname, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(lblSize_1, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE).addGap(37)
				.addComponent(lblType_1, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup()
						.addGap(90)
						.addComponent(lblDateModified, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
						.addGap(69).addComponent(btnPaste, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGap(37).addComponent(btnCopy, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
						.addGroup(Alignment.TRAILING,
								gl_panel_2.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED, 340, Short.MAX_VALUE)
										.addComponent(btnRename).addGap(145)))));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2
				.createSequentialGroup()
				.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_2.createSequentialGroup()
						.addGap(7)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSize_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblname, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblType_1, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDateModified, GroupLayout.PREFERRED_SIZE, 27,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_2.createSequentialGroup().addContainerGap().addComponent(btnRename)
								.addGap(35).addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnPaste).addComponent(btnCopy))))
				.addContainerGap(20, Short.MAX_VALUE)));
		panel_2.setLayout(gl_panel_2);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
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
		table.setShowGrid(false);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable) e.getSource();
				int row = table.getSelectedRow();
				String name = table.getModel().getValueAt(row, 0).toString();
				String date = table.getModel().getValueAt(row, 1).toString();

				String type = table.getModel().getValueAt(row, 2).toString();
				String size = table.getModel().getValueAt(row, 3).toString();

				lblname.setText(name);
				lblSize_1.setText(size);
				lblDateModified.setText(date);
				lblType_1.setText(type);
				// double click on jtable to open this file
				if (e.getClickCount() == 2) {

					Current_Folder = txtpath.getText() + "\\" + name;
					File file = new File(Current_Folder);
					if (file.isDirectory()) {
						if (file.exists()) {
							model = CreateTableData(model, Current_Folder);
							txtpath.setText(Current_Folder);
						}
					}
				}
			}
		});
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(HEADER);

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode nodeselected = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				String name = nodeselected.getUserObject().toString();
				lblname.setText(name);
				TreePath treepath = e.getPath();
				txtpath.setText(getTreepath(treepath));
				model = CreateTableData(model, txtpath.getText());
				table.setModel(model);

			}
		});

	}

	// Nạp data cho table
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

	// lấy File name or path
	public String FileNode(File file) {
		String name = file.getName();
		if (name.equals("")) {
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}

	// Tạo node con cho tree
	public void createChildren(File fileRoot, DefaultMutableTreeNode node) {
		File[] files = fileRoot.listFiles();
		if (files == null)
			return;
		for (File file : files) {
			if (file.isDirectory()) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(FileNode(file));
				node.add(childNode);
				if (file.isDirectory())
					createChildren(file, childNode);
			}

		}
	}

	// xử lý String đễ lấy path
	public String getTreepath(TreePath path) {
		String value = path.toString();
		value = value.replace("[this PC, ", "");
		value = value.replace(", ", "\\");
		value = value.replace("]", "");

		return value;

	}

	// copy File chức năng thêm
	public static void copyFile(Path source, Path destination) throws IOException {
		Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
	}
}
