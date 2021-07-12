package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Sewa {
	public Integer id;
	public String judul;
	public LocalDate tglPinjam;
	public LocalDate tglHarusKembali;
	public LocalDate tglKembali;
	public Integer denda;
	public Integer biayaSewa;
	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public Object[] toRow() {
		Object[] data = {
			this.id.toString(), 
			this.judul,
			this.tglPinjam != null ? this.tglPinjam.format(dateFormat) : null,
			this.tglHarusKembali != null ? this.tglHarusKembali.format(dateFormat) : null,
			this.tglKembali != null ? this.tglKembali.format(dateFormat) : null,
			this.denda != null ? this.denda.toString() : null,
			this.biayaSewa.toString()
		};
		return data;
	}
}