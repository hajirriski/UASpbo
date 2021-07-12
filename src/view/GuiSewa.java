package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.Sewa;
import controller.SewaControl;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.Color;

public class GuiSewa {

	private JFrame frame;
	private JTextField judultextField;
	private JTable table;
	private DefaultTableModel tableModel;
	private String currentEditedidSewa = null;

	private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
	private JButton cancelEditBtn;
	private JButton simpanBtn;
	private JButton deleteBtn;
	private JButton kembaliBtn;
	private JButton editBtn;
	private JLabel biayaValLbl;
	private JButton biayaBtn;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiSewa window = new GuiSewa();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiSewa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				show();
			}
		});
		frame.setBounds(100, 100, 807, 501);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel titleLbl = new JLabel("Persewaan Buku GHz");
		titleLbl.setHorizontalAlignment(SwingConstants.CENTER);
		titleLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		titleLbl.setBounds(234, 11, 214, 14);
		frame.getContentPane().add(titleLbl);
		
		judultextField = new JTextField();
		judultextField.setBounds(90, 95, 197, 20);
		frame.getContentPane().add(judultextField);
		judultextField.setColumns(10);
		
		JLabel tglLbl = new JLabel("Tanggal :");
		tglLbl.setVerticalAlignment(SwingConstants.TOP);
		tglLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		tglLbl.setBounds(10, 53, 70, 21);
		frame.getContentPane().add(tglLbl);
		
		JLabel tglValLbl = new JLabel("");
		tglValLbl.setVerticalAlignment(SwingConstants.TOP);
		tglValLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		tglValLbl.setBounds(79, 53, 121, 21);
		frame.getContentPane().add(tglValLbl);
		String tglSekarang = LocalDate.now().format(dateFormat);
		tglValLbl.setText(tglSekarang);
		
		JLabel jamLbl = new JLabel("Jam : ");
		jamLbl.setVerticalAlignment(SwingConstants.TOP);
		jamLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		jamLbl.setBounds(521, 53, 54, 21);
		frame.getContentPane().add(jamLbl);
		
		JLabel jamValLbl = new JLabel("");
		jamValLbl.setVerticalAlignment(SwingConstants.TOP);
		jamValLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		jamValLbl.setBounds(569, 53, 70, 21);
		frame.getContentPane().add(jamValLbl);
		String waktuSekarang = LocalTime.now().format(timeFormat);
		jamValLbl.setText(waktuSekarang);
		
		JLabel judulLbl = new JLabel("Judul Buku");
		judulLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		judulLbl.setBounds(10, 97, 70, 14);
		frame.getContentPane().add(judulLbl);
		
		JLabel biayaLbl = new JLabel("Biaya : ");
		biayaLbl.setVerticalAlignment(SwingConstants.TOP);
		biayaLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		biayaLbl.setBounds(336, 97, 70, 21);
		frame.getContentPane().add(biayaLbl);
		
		JLabel rpLbl = new JLabel("Rp.");
		rpLbl.setVerticalAlignment(SwingConstants.TOP);
		rpLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		rpLbl.setBounds(336, 123, 34, 21);
		frame.getContentPane().add(rpLbl);
		
		biayaValLbl = new JLabel("");
		biayaValLbl.setVerticalAlignment(SwingConstants.TOP);
		biayaValLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		biayaValLbl.setBounds(372, 123, 130, 21);
		frame.getContentPane().add(biayaValLbl);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 216, 771, 235);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		scrollPane.setViewportView(table);
		
		JLabel notifTrueLbl = new JLabel("");
		notifTrueLbl.setForeground(new Color(0, 128, 0));
		notifTrueLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		notifTrueLbl.setBounds(10, 136, 277, 14);
		frame.getContentPane().add(notifTrueLbl);
		
		JLabel notifFalseLbl = new JLabel("");
		notifFalseLbl.setForeground(new Color(255, 0, 0));
		notifFalseLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		notifFalseLbl.setBounds(10, 137, 277, 14);
		frame.getContentPane().add(notifFalseLbl);
		
		kembaliBtn = new JButton("Kembalikan Buku");
		kembaliBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Kembali();
			}
		});
		kembaliBtn.setBounds(207, 174, 139, 31);
		frame.getContentPane().add(kembaliBtn);
		
		simpanBtn = new JButton("Simpan");
		simpanBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});
		simpanBtn.setBounds(21, 174, 142, 31);
		frame.getContentPane().add(simpanBtn);
		
		editBtn = new JButton("Edit");
		editBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Edit();
			}
		});
		editBtn.setBounds(400, 174, 111, 31);
		frame.getContentPane().add(editBtn);
		
		cancelEditBtn = new JButton("Batalkan Edit");
		cancelEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelEdit();
			}
		});
		cancelEditBtn.setBounds(172, 126, 115, 32);
		frame.getContentPane().add(cancelEditBtn);
		this.cancelEditBtn.setVisible(false);
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Delete();
			}
		});
		deleteBtn.setBounds(564, 174, 111, 31);
		frame.getContentPane().add(deleteBtn);
		
		biayaBtn = new JButton("Lihat Biaya");
		biayaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Biaya();
			}
		});
		biayaBtn.setBounds(400, 94, 111, 23);
		frame.getContentPane().add(biayaBtn);
		
		
	}
	public void show() {
		try {
			tableModel = new DefaultTableModel();
			tableModel.addColumn("ID");
			tableModel.addColumn("Judul Buku");
			tableModel.addColumn("Tanggal Pinjam");
			tableModel.addColumn("Tanggal Harus Kembali");
			tableModel.addColumn("Tanggal Kembali");
			tableModel.addColumn("Denda");
			tableModel.addColumn("Biaya Sewa");

			table.setModel(tableModel);
			table.setAutoResizeMode(0);
			table.getColumnModel().getColumn(0).setPreferredWidth(30);
			table.getColumnModel().getColumn(1).setPreferredWidth(100);
			table.getColumnModel().getColumn(2).setPreferredWidth(150);
			table.getColumnModel().getColumn(3).setPreferredWidth(150);
			table.getColumnModel().getColumn(4).setPreferredWidth(150);
			table.getColumnModel().getColumn(5).setPreferredWidth(100);
			table.getColumnModel().getColumn(6).setPreferredWidth(100);
			
			this.fetchTableData();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void fetchTableData() {
		ArrayList<Sewa> listSewa = SewaControl.fetchSewa();
		for(Sewa sewa :listSewa) {
			tableModel.addRow(sewa.toRow());
		}
	}
	public void Biaya() {
		int row = table.getSelectedRow();
		String SbiayaSewa = (String) this.tableModel.getValueAt(row, 6);
		String Sdenda = (String) this.tableModel.getValueAt(row, 5);
		int biayaSewa = Integer.parseInt(SbiayaSewa);
		int denda = Integer.parseInt(Sdenda);
		int total = biayaSewa + denda;
		String Stotal=Integer.toString(total);
		this.biayaValLbl.setText(Stotal);
	}
	public void Save() {
		String saveSewa = "f";
		if(this.currentEditedidSewa == null) {
			saveSewa = SewaControl.tambahSewa(judultextField.getText());
		} else {
			saveSewa = SewaControl.updateJudulBuku(this.currentEditedidSewa, this.judultextField.getText());
		}
		if(saveSewa == "t") {
			JOptionPane.showMessageDialog(null, "Data Berhasil disimpan");
			this.judultextField.setText("");
			this.cancelEditBtn.setVisible(false);
			this.simpanBtn.setText("Simpan");
			this.currentEditedidSewa = null;
			this.tableModel.setRowCount(0);
			this.fetchTableData();
		} else {
			JOptionPane.showMessageDialog(null, "Data Gagal disimpan");
		}
	}
	public void Delete() {
		int row = this.table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "Pilih salah satu untuk dihapus");
			return;
		}
		String idSewa = this.tableModel.getValueAt(row, 0).toString();
		String hapusSewa = SewaControl.hapusSewa(idSewa);
		if(hapusSewa == "t") {
			JOptionPane.showMessageDialog(null, "Data Berhasil dihapus");
			this.tableModel.setRowCount(0);
			this.fetchTableData();
		} else {
			JOptionPane.showMessageDialog(null, "Data Gagal dihapus");
		}
	}
	public void Kembali() {
		int row = this.table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "Pilih salah satu untuk dikembalikan");
			return;
		}
		String tglKembali = (String) this.tableModel.getValueAt(row, 4);
		if(tglKembali != null) {
			JOptionPane.showMessageDialog(null, "Buku sudah pernah dikembalikan");
			return;
		}
		String idSewa = this.tableModel.getValueAt(row, 0).toString();
		LocalDate tglHarusKembali = LocalDate.parse(tableModel.getValueAt(row, 3).toString(), this.dateFormat);
		String kembalikanBuku = SewaControl.kembalikanBuku(idSewa, tglHarusKembali);
		if(kembalikanBuku == "t") {
			JOptionPane.showMessageDialog(null, "Buku Berhasil dikembalikan");
			tableModel.setRowCount(0);
			this.fetchTableData();
		} else {
			JOptionPane.showMessageDialog(null, "Buku gagal dikembalikan");
		}
	}
	public void Edit() {
		int row = table.getSelectedRow();
		if(row == -1) {
			JOptionPane.showMessageDialog(null, "Pilih salah satu untuk diedit");
			return;
		}
		String idSewa = (String) this.tableModel.getValueAt(row, 0);
		String judul = (String) this.tableModel.getValueAt(row, 1);
		String tglPinjam = (String) this.tableModel.getValueAt(row, 2);
		String tglHarusKembali = (String) this.tableModel.getValueAt(row, 3);
		String tglKembali = (String) this.tableModel.getValueAt(row, 4);
		String denda = (String) this.tableModel.getValueAt(row, 5);
		String biayaSewa = (String) this.tableModel.getValueAt(row, 6);
		if(idSewa != null && judul != null && tglPinjam != null && tglHarusKembali != null && tglKembali != null && denda != null && biayaSewa != null) {
			JOptionPane.showMessageDialog(null, "Data sudah lengkap dan tidak bisa diedit");
			return;
		}
		this.judultextField.setText(judul);
		this.currentEditedidSewa = idSewa;
		this.simpanBtn.setText("Simpan Perubahan");
		this.cancelEditBtn.setVisible(true);
	}
	public void cancelEdit() {
		this.cancelEditBtn.setVisible(false);
		this.currentEditedidSewa = null;
		this.simpanBtn.setText("Simpan");
		this.judultextField.setText("");
	}
}

