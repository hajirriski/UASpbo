package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.Sewa;

public class SewaControl {
	public static Statement st;
	public static PreparedStatement pms;
    public static ResultSet rs;
    static Connection cn = database.DBkonek.Koneksi();
    
	public static  ArrayList<Sewa> fetchSewa() {
		ArrayList<Sewa> listSewa = new ArrayList<Sewa>();
		try {
			st = cn.createStatement();
			rs = st.executeQuery("SELECT * FROM sewabuku");
			 while (rs.next()) {
				 Sewa dataSewa = new Sewa();
				 dataSewa.id = rs.getInt("id");
				 dataSewa.judul = rs.getString("judul");
				 dataSewa.tglPinjam = rs.getDate("tanggal_harus_kembali") != null ? rs.getDate("tanggal_pinjam").toLocalDate() : null;
				 dataSewa.tglHarusKembali = rs.getDate("tanggal_harus_kembali") != null ? rs.getDate("tanggal_harus_kembali").toLocalDate() : null;
				 dataSewa.tglKembali = rs.getDate("tanggal_kembali") != null ? rs.getDate("tanggal_kembali").toLocalDate() : null;
				 dataSewa.biayaSewa = rs.getInt("biaya_sewa");
				 dataSewa.denda = rs.getInt("denda");
			     listSewa.add(dataSewa);
			    }
		} catch(Exception e) {
		    e.printStackTrace();
		}
		
		return listSewa;
		
	}
	public static String tambahSewa(String title) {
		LocalDate tglPinjam = LocalDate.now();
		LocalDate tglHarusKembali = LocalDate.now().plusDays(7);
		try {
			pms = cn.prepareStatement("INSERT INTO sewabuku (judul, tanggal_pinjam, tanggal_harus_kembali) VALUES (?,?,?)");
			pms.setString(1, title);
			pms.setObject(2, tglPinjam);
			pms.setObject(3, tglHarusKembali);
			pms.execute();
			return "t";
		} catch(Exception e) {
			e.printStackTrace();
		    return "f";
		}
	}
	public static String hapusSewa(String idSewa) {
		try {
			pms = cn.prepareStatement("DELETE FROM sewabuku WHERE id=?");
			pms.setString(1, idSewa);
			pms.execute();
			return "t";
		} catch(Exception e) {
			e.printStackTrace();
		    return "f";
		}
	}
	public static String kembalikanBuku(String idSewa, LocalDate tglHarusKembali) {
		LocalDate tglKembali = LocalDate.now();
		int keterlambatan = tglKembali.compareTo(tglHarusKembali);
		int denda = 0;
		if (keterlambatan > 0) {
			denda = 2000 * keterlambatan;
		}
		try {
			pms = cn.prepareStatement("UPDATE sewabuku SET tanggal_kembali = ?, denda = ?, biaya_sewa = 5000 WHERE id = ?");
			pms.setObject(1, tglKembali);
			pms.setInt(2, denda);
			pms.setString(3, idSewa);
			pms.execute();
			return "t";
		}catch(Exception e) {
			return "f";
		}
	}
	public static String updateJudulBuku(String idSewa, String title) {
		try {
			pms = cn.prepareStatement("UPDATE sewabuku SET judul = ? WHERE id = ?");
			pms.setString(1, title);
			pms.setString(2, idSewa);
			pms.execute();
			return "t";
		}catch(Exception e) {
			return "f";
		}
	}
}
